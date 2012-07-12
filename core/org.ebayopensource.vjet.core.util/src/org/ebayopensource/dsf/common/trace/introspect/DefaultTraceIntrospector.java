/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace.introspect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ebayopensource.dsf.common.xml.IXmlStreamWriter;
import org.ebayopensource.dsf.common.xml.XmlEncoder;


/**
 * Default introspector that writes the state of passed in object into passed in writer.
 * Please be aware of the following:
 * <li>Object fields with name containing "logger", "helper" etc will be excluded.</li>
 * <li>Object fields with name not starting with "m_" or "s_" will be excluded.</li>
 * <li>Child objects exceeding max depth will be marked with empty "CHILD_CUTOFF" element.</li>
 * <li>Super classes exceeding max depth will be marked with empty "SUPER_CUTOFF" element.</li>
 * <li>Cross references will be marked as "CROSS_REF".</li>
 * <li>Throttle for child object and super classes lookup depth is <code>MAX_DEPTH_THROTTLE</code></li>
 * <li>Throttle for collection size is <code>MAX_COLLECTION_SIZE_THROTTLE</code></li>
 * <li>This class is not thread-safe</li>
 */
public class DefaultTraceIntrospector implements ITraceObjectIntrospector {
	
	public static final int MAX_DEPTH_THROTTLE = 10;
	public static final int MAX_COLLECTION_SIZE_THROTTLE = 50;
	private static Logger s_logger = Logger.getLogger(DefaultTraceIntrospector.class.getName());
	
	protected static final List<Class> s_primitiveTypes = new ArrayList<Class>(10);
	static {
		s_primitiveTypes.add(String.class);
		s_primitiveTypes.add(Boolean.class);
		s_primitiveTypes.add(Integer.class);
		s_primitiveTypes.add(Long.class);
		s_primitiveTypes.add(Float.class);
		s_primitiveTypes.add(Double.class);
		s_primitiveTypes.add(Date.class);
	}
	
	private static final List<String> s_includeStartWith = new ArrayList<String>(10);
	static {
		s_includeStartWith.add("m_");
		s_includeStartWith.add("s_");
	}
	
	private static final List<String> s_excludedContainWith = new ArrayList<String>(10);
	static {
		s_excludedContainWith.add("Logger");
		s_excludedContainWith.add("logger");
		s_excludedContainWith.add("Helper");
		s_excludedContainWith.add("helper");
	}
	
	protected static final String TAG_ENTRY = "Entry";
	protected static final String TAG_ARRAY = "Array";
	protected static final String ATTR_SIZE = "size";
	
	protected static final String CROSS_REF = "CROSS_REF";
	protected static final String INTROSPECT_CUTOFF = "INTROSPECT_CUTOFF";
	protected static final String CHILD_CUTOFF = "CHILD_CUTOFF";
	protected static final String SUPER_CUTOFF = "SUPER_CUTOFF";
	protected static final String ITEM_MORE = "...";
	protected static final String NULL_MARK = "null";
	protected static final String NULL_TAG = "NULL";
	
	protected int m_maxDepth = MAX_DEPTH_THROTTLE;
	protected List<Object> m_objs = new ArrayList<Object>(10);

	//
	// Constructors
	//
	public DefaultTraceIntrospector(){
	}
	
	public DefaultTraceIntrospector(int maxDepth){
		if (maxDepth > 0 && maxDepth < MAX_DEPTH_THROTTLE){
			m_maxDepth = maxDepth;
		}
	}
	
	//
	// Satisfy ITraceInspector
	//
	public void writeState(final Object obj, final IXmlStreamWriter writer){
		try {
			m_objs.clear();
			writeObjectValue(obj, writer, 0);
		}
		catch(Throwable t){
			//t.printStackTrace();
			s_logger.log(Level.SEVERE, "could not write state", t);
		}
	}
	
	//
	// Private
	//
	private void writeObjectValue(
			final Object obj, 
			final IXmlStreamWriter writer, 
			int curDepth){
		
		if (obj == null){
			return;
		}
		
		if (m_objs.contains(obj)){
			writer.writeRaw(CROSS_REF);
			return;
		}
	
		if (curDepth >= m_maxDepth){
			writer.writeStartElement(CHILD_CUTOFF);
			writer.writeEndElement();
			return;
		}
		
		if (obj.getClass().isArray()){
			// TODO
			return;
		}

		m_objs.add(obj);
		final Class type = obj.getClass();
		writer.writeStartElement(type.getSimpleName());
		
		if (obj instanceof ITraceable){
			((ITraceable)obj).writeState(writer);
		}
		else if (type.isPrimitive() || s_primitiveTypes.contains(type)){
			writer.writeCharacters( XmlEncoder.encode(obj.toString()) );
		}
		else if (List.class.isAssignableFrom(type)){
			List list = (List)obj;
			int size = list.size();
			writer.writeAttribute(ATTR_SIZE, String.valueOf(size));
			int counter = 0;
			for (Object item: list){
				if (counter > MAX_COLLECTION_SIZE_THROTTLE){
					writer.writeCharacters(ITEM_MORE);
					break;
				}
				writeObjectValue(item, writer, curDepth+1);
				counter++;
			}
		}
		else if (Map.class.isAssignableFrom(type)){
			final Map<?,?> map = (Map<?,?>)obj;
			int size = map.size();
			int counter = 0;
			writer.writeAttribute(ATTR_SIZE, String.valueOf(size));
			for (Map.Entry<?,?> entry: map.entrySet()){
				if (counter > MAX_COLLECTION_SIZE_THROTTLE){
					writer.writeCharacters(ITEM_MORE);
					break;
				}
				writer.writeStartElement(TAG_ENTRY);
				writeObjectValue(entry.getKey(), writer, curDepth+1);
				writeObjectValue(entry.getValue(), writer, curDepth+1);
				writer.writeEndElement();
				counter++;
			}
		}
		else {
			// Fields of this class
			Field[] fields = type.getDeclaredFields();
			if (fields != null && fields.length > 0){
				for (Field f: fields){
					writeFieldValue(f, obj, writer, curDepth);
				}
			}
			
			// Fields of super classes
			Class superType = type.getSuperclass();
			int superLevel = 0;
			while (superType != null && superType != Object.class){
				superLevel++;
				if (superLevel > m_maxDepth){
					writer.writeStartElement(SUPER_CUTOFF);
					writer.writeEndElement();
					break;
				}
				fields = superType.getDeclaredFields();
				if (fields != null && fields.length > 0){
					for (Field f: fields){
						writeFieldValue(f, obj, writer, curDepth);
					}
				}
				superType = superType.getSuperclass();
			}
		}

		writer.writeEndElement();
		return;
	}
	
	private void writeFieldValue(
			final Field field, 
			final Object obj, 
			final IXmlStreamWriter writer, 
			int curDepth){
		
		String name = field.getName();
		if (!includeField(name)){
			return;
		}
		
		Class type = field.getType();
		writer.writeStartElement(name);
		
		field.setAccessible(true); 

		try {
			Object value = field.get(obj);
			if (value == null){
				writer.writeRaw("null");
			}
			else {
				if (type.isPrimitive() || s_primitiveTypes.contains(type)){
					writer.writeCharacters( XmlEncoder.encode(value.toString()) );
				}
				else {
					writeObjectValue(value, writer, curDepth+1);
				}
			}
		} 
		catch (IllegalArgumentException e) {
			s_logger.log(Level.SEVERE,"",e);
		} 
		catch (IllegalAccessException e) {
			s_logger.log(Level.SEVERE,"",e);
		}
		finally {
			writer.writeEndElement();
		}
	}
	
	//
	// Private
	//
	private boolean includeField(final String name){
		
		if (name == null){
			return false;
		}
		
		for (String fragment: s_excludedContainWith){
			if (name.contains(fragment)){
				return false;
			}
		}
		
		for (String prefix: s_includeStartWith){
			if (name.startsWith(prefix)){
				return true;
			}
		}
		
		return false;
	}
}

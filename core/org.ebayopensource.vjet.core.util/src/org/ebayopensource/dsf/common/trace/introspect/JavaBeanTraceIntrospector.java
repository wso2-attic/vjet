/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace.introspect;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ebayopensource.dsf.common.xml.IXmlStreamWriter;
import org.ebayopensource.dsf.common.xml.XmlEncoder;

/**
 * Java Bean introspector writes the state of target object into writer.
 * V4 data model dump is an application of this introspector.
 * For Java Bean and its introspection:
 *   Java Bean is of a public class;
 *   Introspection is done through invoking its public method w/ no parameters;
 *   no Java Bean fields(properties) are introspected. 
 *
 * refer
 *  http://java.sun.com/docs/books/tutorial/javabeans/ 
 *  java.beans.XMLEncoder and ReflectionUtils
 */
public class JavaBeanTraceIntrospector extends DefaultTraceIntrospector {
	
	static private JavaBeanTraceIntrospector s_default = new JavaBeanTraceIntrospector();
	private static Logger s_logger = Logger.getLogger(JavaBeanTraceIntrospector.class.getName());
	
	private static final String[] getterPrefixes = { "get", "is", "has" };
	private static final String GetterException ="getter-exception";
	
	protected static final List<String> s_terminationTypes = new ArrayList<String>(10);
	static {
		s_terminationTypes.add("java.lang.Class");
		s_terminationTypes.add("java.util.Locale");
	}
	
	protected static final List<String> s_terminationPkgs = new ArrayList<String>(10);
	static {
		s_terminationPkgs.add("java.lang.reflect");
		s_terminationPkgs.add("org.ebayopensource.dsf.dom");
		s_terminationPkgs.add("org.ebayopensource.dsf.html.dom");
		s_terminationPkgs.add("org.ebayopensource.dsf.resource");
		s_terminationPkgs.add("com.ebay.shared.resources");
	}
	
	/**
	 *  the common used JavaBeanTraceIntrospector
	 */
	static public JavaBeanTraceIntrospector getDefault() {
		return s_default;
	}

	/**
	 *  JavaBeanTraceIntrospector with 
	 *    default depth throttle
	 *    default collection size throttle
	 */
	public JavaBeanTraceIntrospector(){		
	}
	
	/**
	 * @param maxDepth max inspection depth
	 */
	public JavaBeanTraceIntrospector(int maxDepth){
		if (maxDepth > 0 && maxDepth < MAX_DEPTH_THROTTLE){
			m_maxDepth = maxDepth;
		}
	}
	
	/**
	 * implement method specified in ITraceInspector
	 * 
	 * "writeObjectValue" to be invoked by this method is not part of a 
	 * PrivilededAction and we don't need to invoke it as 
	 * AccessController.doPriviledged(privilededAction).
	 * It is because we only invoke public Java Bean object and its public methods.
	 * This mimics the v4 application's behavior when it access V4 data model.
	 * 
	 * @param obj     the java bean object,collection object or primitive java object.
	 *                if null is passed in, nothing is written into the writer.
	 *                
	 * @param writer  the xml writer to hold java bean introspection result
	 */
	public void writeState(final Object obj, final IXmlStreamWriter writer){
		try {
			m_objs.clear();
			writeObjectValue(obj, writer, 0);
		}
		catch(Throwable t){
			//t.printStackTrace();
			s_logger.log(Level.SEVERE, "could not write ", t);
		}
	}
	
	/**
	 * !Modifier.isPublic(type.getModifiers() doesn't disqualify an obj for introspect.
	 * An instance of a private class still can be introspected for the public 
	 * interface it implements. 
	 * refer ArrayList.java and the iterator impl in it. 
	 * 
	 */
	private boolean isTerminated(final Object obj) {
		final Class type = obj.getClass();
		
		//if (!Modifier.isPublic(type.getModifiers()) ) {
		//	return true; 
		//}
		
		if (type.getPackage()!=null) {
			for (String pkg: s_terminationPkgs) {
				if (type.getPackage().getName().startsWith(pkg)) {
					return true;
				}
			}
		}

		return false;
	
	}
	
	
	private String getClassTag(Class clz) {
		String s = clz.getSimpleName();
		while (s==null || "".equals(s))  { //anonymous class
			Class enclosingClass = clz.getEnclosingClass();
			if (enclosingClass==null) {
				break; //should never reach here
			}
			s = enclosingClass.getSimpleName();
		}
		if ("".equals(s))
			s = null;
		return s;
	}
	/**
	 *  dumping examples
	 *  (1) for null object returned by getter method, it will be dumped it as
	 *      <method>null</method>
	 *  (2) for empty string ("") object returned by getter method, it will be dumped as
	 *      <method><string></string></method>
	 */
	private void writeObjectValue(
			final Object obj, 
			final IXmlStreamWriter writer, 
			int curDepth)
    throws IntrospectionException
	{
		if (obj == null){
			if (curDepth<=0) {
				writer.writeStartElement(NULL_TAG);
				writer.writeEndElement();	
			} else {
				writer.writeRaw(NULL_MARK); //java.beans.XMLEncoder encode such as <null/>	
			}
			return;
		}

		final Class type = obj.getClass();

		if (curDepth >= m_maxDepth){
			writer.writeStartElement(CHILD_CUTOFF);
			writer.writeEndElement();
			return;
		}
		
		if (isTerminated(obj)) {
			String s = getClassTag(type);
			if (s==null)
				return; //should never happen
			writer.writeStartElement(s);
			writer.writeCharacters(INTROSPECT_CUTOFF);
			writer.writeEndElement();

			return;
		}

		if (type.isPrimitive() || 
			s_primitiveTypes.contains(type) || 
			s_terminationTypes.contains(type.getName()) ){
			writer.writeStartElement(type.getSimpleName());
			writer.writeCharacters(XmlEncoder.encode(obj.toString()));
			writer.writeEndElement();
			return;
		}
		
		if (m_objs.contains(obj)){
			writer.writeRaw(CROSS_REF);
			return;
		}
	
		m_objs.add(obj);
		if (obj.getClass().isArray()){
			writer.writeStartElement(TAG_ARRAY);
		} else {
			String s = getClassTag(type);
			if (s==null)
				return; //should never happen
			writer.writeStartElement(s);
		}

		
		if (obj.getClass().isArray()){
			int len = Array.getLength(obj);
			writer.writeAttribute(ATTR_SIZE, String.valueOf(len));
			for (int i=0;i<len;i++) {
				if (i > MAX_COLLECTION_SIZE_THROTTLE){
					writer.writeCharacters(ITEM_MORE);
					break;
				}
				Object entry = Array.get(obj,i);
				writeObjectValue(entry,writer,curDepth+1);
			}
		} else if (Iterable.class.isAssignableFrom(type)){   //Collection class, vector,list,set...
			Iterable it = (Iterable)obj;

			if (List.class.isAssignableFrom(type)) {
				int size = ((List)obj).size();
				writer.writeAttribute(ATTR_SIZE, String.valueOf(size));
			}
			int counter = 0;
			for (Object item: it){
				if (counter > MAX_COLLECTION_SIZE_THROTTLE){
					writer.writeCharacters(ITEM_MORE);
					break;
				}
				writeObjectValue(item, writer, curDepth+1);
				counter++;
			}
		} else if (Map.class.isAssignableFrom(type)){
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
		} else if (Iterator.class.isAssignableFrom(type)){
			Iterator it = (Iterator)obj;
			int counter = 0;
			while (it.hasNext()){
				Object entry = it.next();
				if (counter > MAX_COLLECTION_SIZE_THROTTLE){
					writer.writeCharacters(ITEM_MORE);
					break;
				}
				writeObjectValue(entry, writer, curDepth+1);
				counter++;
			}
		} else if (Enumeration.class.isAssignableFrom(type)){
			Enumeration it = (Enumeration)obj;
			int counter = 0;
			while (it.hasMoreElements()){
				Object entry = it.nextElement();
				if (counter > MAX_COLLECTION_SIZE_THROTTLE){
					writer.writeCharacters(ITEM_MORE);
					break;
				}
				writeObjectValue(entry, writer, curDepth+1);
				counter++;
			}
		}else {
			//invoking public method of this object
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass(),Object.class );
			MethodDescriptor[] mdArray = beanInfo.getMethodDescriptors();  //including methods from super class
			
			if (mdArray != null) {
				for (MethodDescriptor md : mdArray ) {				
					Method m = md.getMethod();
					Class[] paramTypes = m.getParameterTypes();
					if (paramTypes!=null && paramTypes.length >0)
						continue;
					
					if ( isGetterMethod(m.getName()) &&					 
						 Modifier.isPublic(m.getModifiers()) ) {
						//m.setAccessible(true); //suppress Java language access checking 
						Object returnObj = doInvocation(writer,obj,m,(Object[])null);
						if (! (returnObj instanceof Throwable)) {
							writer.writeStartElement(m.getName());
							writeObjectValue(returnObj,writer,curDepth+1);
							writer.writeEndElement();
						}
					}
				}
			}
		}

		writer.writeEndElement();
		return;
	}
	
	/**
	 * wrapper the invocation of getter.
	 * note: a getter may designed to throw exception always, such as in DDocument.getXmlVersion()
	 * 
	 * @param obj
	 * @param m
	 * @return
	 */
	public Object doInvocation(final IXmlStreamWriter writer,Object obj, Method m,Object[] params) {
		Object returnObj = null;
		try {
			returnObj = m.invoke(obj, params);
		} catch (IllegalAccessException e1) {
			returnObj = e1;
		} catch (Exception e2) {
			returnObj = e2;
			writer.writeStartElement(m.getName());
			String err = GetterException + ":" + e2.toString();
			writer.writeRaw(err);
			writer.writeEndElement();
		}
		return returnObj;
	}
	
	
	
	private boolean isGetterMethod(String methodName) {
		for (int i=0; i<getterPrefixes.length; ++i) {
			if (methodName.toLowerCase().startsWith(getterPrefixes[i])) {
				return true;
			}
		}
		return false;
	}
}

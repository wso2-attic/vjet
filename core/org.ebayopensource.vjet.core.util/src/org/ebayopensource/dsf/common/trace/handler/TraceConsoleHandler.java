/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;
import org.ebayopensource.dsf.common.exceptions.DsfExceptionHelper;
import org.ebayopensource.dsf.common.trace.event.TraceEvent;
import org.ebayopensource.dsf.common.trace.event.TraceType;
import org.ebayopensource.dsf.common.trace.introspect.DefaultTraceIntrospector;
import org.ebayopensource.dsf.common.trace.introspect.ITraceObjectIntrospector;
import org.ebayopensource.dsf.common.tracer.TraceUtil;

public class TraceConsoleHandler implements ITraceEventHandler {
	
	private static final String ENTER_COLON = "Enter: ";
	private static final String EXIT_COLON = "Exit: ";
	private static final String EXIT = "Exit";
	
	private static final String CALL_COLON = "Call: ";
	private static final String END_CALL_COLON = "End Call: ";
	private static final String END_CALL = "End Call";
	
	private static final String LOOP_COLON = "Loop: ";
	private static final String LOOP_STEP = "Step: ";
	private static final String END_LOOP = "End Loop";
	
	private static final String DOT = ".";
//	private static final String COMMA = ",";
	private static final String TAB = "\t";
	
	private HandlerId m_handlerId;
	private final Map<Class,ITraceObjectIntrospector> m_inspectors = 
		new HashMap<Class,ITraceObjectIntrospector>(10);
	private DefaultTraceIntrospector m_defaultIntrospector;

	// Indent Support
	private int m_traceDepth = -1;
	private ListOrderedMap m_stackLabels = new ListOrderedMap();
	
	private boolean m_active = true;

	//
	// Constructor
	//
	public TraceConsoleHandler(final HandlerId handlerId) { 

		if (handlerId == null){
			DsfExceptionHelper.chuck("handlerId is null");
		}
		
		m_handlerId = handlerId;
	}
	
	//
	// Satisfy ITraceHandler
	//
	public HandlerId getId(){
		return m_handlerId;
	}
	
	public void handle(final TraceEvent event){
		
		TraceType type = event.getType();

		if (TraceType.ENTER_METHOD.equals(type)){
			traceEnterMethod(event);
		}
		else if (TraceType.EXIT_METHOD.equals(type)){
			traceExitMethod(event);
		}
//		else if (TraceType.OBJECT_TYPE.equals(type)){
//			traceObjectType(event);
//		}
//		else if (TraceType.OBJECT_STATE.equals(type)){
//				traceObjectState(event);
//		}
//		else if (TraceType.DATAMODEL.equals(type)){
//				traceJavaBean(event);
//		}
//		else if (TraceType.NV.equals(type)){
//			traceNV(event);
//		}
//		else if (TraceType.MSG.equals(type)){
//			traceMsg(event);
//		}
//		else if (TraceType.START_LOOP.equals(type)){
//			traceStartLoop(event);
//		}
//		else if (TraceType.END_LOOP.equals(type)){
//			traceEndLoop(event);
//		}
//		else if (TraceType.START_CALL.equals(type)){
//			traceStartCall(event);
//		}
//		else if (TraceType.END_CALL.equals(type)){
//			traceEndCall(event);
//		}
	}

	public void close(){
	}
	
	//
	// Private
	//
	private static final String TAG_MSG = "Msg";
	private static final String TAG_LOOP = "Loop";
	private static final String TAG_CALL = "Call";
	
	private static final String ATTR_METHOD = "mtd";
	private static final String ATTR_OBJ = "obj";
	
	private void traceEnterMethod(TraceEvent event){
		
		final Throwable t = new Throwable();
		t.fillInStackTrace();
		
		Object caller = event.getSource();
		
		final String clsName = TraceUtil.getClassName(caller);
		final String methodName = TraceUtil.getMethodName(caller, t);
		
		push(clsName + DOT + methodName);
		
		write(getPadding(m_traceDepth) + ENTER_COLON + clsName + DOT + methodName);
		
//		m_xmlWriter.handleEnterMethod(m_traceDepth, clsName, methodName);
//		m_xmlWriter.writeStartElement(clsName);
//		m_xmlWriter.writeAttribute(ATTR_METHOD, methodName);
		
//		Object[] args = event.getArgs();
//		if (args == null || args.length == 0){
//			return;
//		}
//		
//		for (Object obj: args){
//			m_xmlWriter.writeAttribute("param", TraceUtil.getType(obj));
//		}
	}
	
	private void traceExitMethod(TraceEvent event){
			
		final Throwable t = new Throwable();
		t.fillInStackTrace();
		
		Object caller = event.getSource();
		
		final String clsName = TraceUtil.getClassName(caller);
		final String methodName = TraceUtil.getMethodName(caller, t);
		
		pop(clsName + DOT + methodName);
		
		write(getPadding(m_traceDepth) + EXIT);
		
//		m_xmlWriter.handleExitMethod(m_traceDepth, clsName, methodName);
//		m_xmlWriter.writeEndElement();
	}
	
//	private void traceObjectType(TraceEvent event){
//		
//		if (event.getArgs() == null || event.getArgs().length == 0){
//			return;
//		}
//
//		for (Object obj: event.getArgs()){
////			m_xmlWriter.handleObject(m_traceDepth, obj.getClass());
//			m_xmlWriter.writeStartElement(TraceUtil.getType(obj));
//			m_xmlWriter.writeEndElement();
//		}
//	}
//	
//
//	private void traceObjectState(TraceEvent event){
//		
//		if (event.getArgs() == null || event.getArgs().length == 0){
//			return;
//		}
//		
//		Class type;
//		ITraceObjectIntrospector inspector;
//
//		for (Object obj: event.getArgs()){
//			type = obj.getClass();
//			inspector = m_inspectors.get(type);
//			if (inspector == null){
//				inspector = m_defaultIntrospector;
//			}
//			inspector.writeState(obj, m_xmlWriter);
////			m_xmlWriter.handleObject(m_traceDepth, inspector.getState(obj));
//		}
//	}
//	
//	
//	private void traceJavaBean(TraceEvent event){
//		JavaBeanTraceIntrospector inspector = JavaBeanTraceIntrospector.getDefault();
//		
//		if (event.getArgs() == null || event.getArgs().length == 0){
//			return;
//		}
//
//		for (Object obj: event.getArgs()){
//			inspector.writeState(obj, m_xmlWriter);
//		}
//	}
//	
//	
//	private void traceNV(TraceEvent event){
//		
//		if (event.getArgs() == null || event.getArgs().length == 0){
//			return;
//		}
//		
//		Object name = event.getArgs()[0];
//		Object value = event.getArgs()[1];
//		if (name == null){
//			return;
//		}
//		m_xmlWriter.writeStartElement(XmlEncoder.encode(name.toString()));
//		if (value != null){
//			m_xmlWriter.writeRaw( XmlEncoder.encode(value.toString()) );
//		}
//		m_xmlWriter.writeEndElement();
//	}
//	
//	private void traceMsg(TraceEvent event){
//		
//		if (event.getArgs() == null || event.getArgs().length == 0){
//			return;
//		}
//		
//		m_xmlWriter.writeStartElement(TAG_MSG);
//		Object msg = event.getArgs()[0];
//		if (msg != null){
//			m_xmlWriter.writeCData(msg.toString());
//		}
//		m_xmlWriter.writeEndElement();
//	}
//	
//	private void traceStartLoop(TraceEvent event){
//		
//		Object[] args = event.getArgs();
//		if (args == null || args.length == 0){
//			return;
//		}
//		
//		String objType = TraceUtil.getType(args[0]);
//		
//		push(TAG_LOOP + DOT + objType);
//		
////		m_xmlWriter.handleEnterMethod(m_traceDepth, clsName, methodName);
//		m_xmlWriter.writeStartElement(TAG_LOOP);
//		m_xmlWriter.writeAttribute(ATTR_OBJ, objType);
//	}
//	
//	private void traceEndLoop(TraceEvent event){
//		
//		Object[] args = event.getArgs();
//		if (args == null || args.length == 0){
//			return;
//		}
//		
//		String objType = TraceUtil.getType(args[0]);
//	
//		pop(TAG_LOOP + DOT + objType);
//		m_xmlWriter.writeEndElement();
//	}
//
//	private void traceStartCall(TraceEvent event){
//		
//		Object[] args = event.getArgs();
//		if (args == null || args.length == 0){
//			return;
//		}
//		
//		String objType = TraceUtil.getType(args[0]);
//		
//		push(TAG_CALL + DOT + objType);
//		
//		m_xmlWriter.writeStartElement(TAG_CALL);
//		m_xmlWriter.writeAttribute("cls", objType);
//	}
//	
//	private void traceEndCall(TraceEvent event){
//		
//		Object[] args = event.getArgs();
//		if (args == null || args.length == 0){
//			return;
//		}
//		
//		String objType = TraceUtil.getType(args[0]);
//	
//		pop(TAG_CALL + DOT + objType);
//		m_xmlWriter.writeEndElement();
//	}

	//
	// Private
	//
//	private void addInspectors(final ListenerConfig config){
//		if (config != null){
//			List<IntrospectorKey> inspectorKeys = config.getIntrospectors();
//			if (!inspectorKeys.isEmpty()){
//				for (IntrospectorKey key: inspectorKeys){
//					m_inspectors.put(key.getTargetType(), Factory.createInspector(key));
//				}
//			}
//		}
//	}
	
	private void push(final String label){
		if (label == null || label.trim().length() == 0){
			DsfExceptionHelper.chuck("label is null");
		}
		m_traceDepth++;
		m_stackLabels.put(Integer.valueOf(m_traceDepth), label);
	}
	
	private void pop(final String label){
		if (m_stackLabels.remove(m_stackLabels.lastKey()) != null){
			m_traceDepth--;
		}
	}
	
	private String getPadding(int depth){
		
		StringBuffer padding = new StringBuffer();
		for (int i=0; i<depth;i++){
			padding.append(TAB);
		}
		
		return padding.toString();
	}
	
	private void write(String text){
		System.out.println(text);
	}
}

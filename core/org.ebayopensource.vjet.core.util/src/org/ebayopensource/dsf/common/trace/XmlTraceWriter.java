/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace;

import java.io.StringWriter;

import org.ebayopensource.dsf.common.trace.IDsfTracer.ExitStatus;
import org.ebayopensource.dsf.common.xml.IIndenter;
import org.ebayopensource.dsf.common.xml.IXmlStreamWriter;
import org.ebayopensource.dsf.common.xml.XmlStreamWriter;


public class XmlTraceWriter implements ITraceWriter {

	private static final String TAG_CALL = "Call";
	private static final String TAG_LOOP = "Loop";
	private static final String TAG_STEP = "Step";
	private static final String TAG_MSG = "Msg";
	
	private static final String ATTR_CLASS = "cls";
	private static final String ATTR_METHOD = "mtd";
	private static final String ATTR_GROUP = "grp";
	private static final String ATTR_STATUS = "exit_status";
	private static final String ATTR_IN_MSG = "in";
	private static final String ATTR_EXIT_MSG = "exit_msg";
	
	private IXmlStreamWriter m_xmlWriter;
	
	//
	// Constructor
	//
	public XmlTraceWriter(){

		m_xmlWriter = new XmlStreamWriter(new StringWriter(1024), new IIndenter.Pretty());
		m_xmlWriter.writeStartElement("V4Trace");
		
//		CalTransaction transaction = CalTransactionHelper.findTransaction("URL");
//		if (transaction != null) {
//			m_xmlWriter.writeAttribute("calid", transaction.getName());
//		}	
	}
	
	//
	// Satisfying ITraceWriter
	//
	public void handleEnterMethod(
		final int depth, final String className, final String methodName)
	{
		startEnterTag(className, methodName);
	}
	
	public void handleEnterMethod(
		final int depth, final String className, 
		final String methodName, final String msg)
	{	
		startEnterTag(className, methodName);
		if (msg != null && msg.length() > 0){
			m_xmlWriter.writeAttribute(ATTR_IN_MSG, msg);
		}
	}
	
	public void handleExitMethod(
		final int depth, final String className, final String methodName)
	{
		endEnterTag();
	}
	public void handleExitMethod(
		final int depth, final String className, final String methodName, 
		final ExitStatus status)
	{
		if (status != null){
			m_xmlWriter.writeAttribute(ATTR_STATUS, status.toString());
		}
		m_xmlWriter.writeEndElement();
	}
	
	public void handleExitMethod(
		final int depth, final String className, 
		final String methodName, final String msg)
	{
		if (msg != null){
			m_xmlWriter.writeAttribute(ATTR_EXIT_MSG, msg);
		}
		m_xmlWriter.writeEndElement();
	}
	
	public void handleExitMethod(
		final int depth, final String className, 
		final String methodName, final ExitStatus status, final String msg)
	{
		if (status != null){
			m_xmlWriter.writeAttribute(ATTR_STATUS, status.toString());
		}
		if (msg != null){
			m_xmlWriter.writeAttribute(ATTR_EXIT_MSG, msg);
		}
		m_xmlWriter.writeEndElement();
	}
	
	public void handleObject(final int depth, final Class objType){
		m_xmlWriter.writeStartElement(objType.getSimpleName());
		m_xmlWriter.writeEndElement();
	}
	
//	public void handleObject(final int depth, final Element objState){
//		if (objState == null){
//			return;
//		}
//		m_currentNode.addContent(objState);
//	}
	
	public void handleStartCall(
		final int depth, final String className, final String methodName)
	{
		startCallTag(className, methodName);
	}
	
	public void handleStartCall(
		final int depth, final String className, 
		final String methodName, final String msg)
	{
		startCallTag(className, methodName);
		if (msg != null){
			m_xmlWriter.writeAttribute(ATTR_IN_MSG, msg);
		}
	}
	
	public void handleEndCall(
		final int depth, final String className, 
		final String methodName, final String msg)
	{
		if (msg != null){
			m_xmlWriter.writeAttribute(ATTR_EXIT_MSG, msg);
		}
		m_xmlWriter.writeEndElement();
	}
	
	public void handleEndCall(
		final int depth, final String className, final String methodName)
	{
		endCallTag();
	}
	
	public void handleStartLoop(final int depth, final String group){
		startLoopTag(group);
	}
	
	public void handleLoopStep(final int depth, final String msg){
		m_xmlWriter.writeStartElement(TAG_STEP);
		m_xmlWriter.writeCharacters(msg);
	}
	
	public void handleEndLoop(final int depth, final String group){
		endLoopTag();
	}
	
	public void handleMsg(final int depth, final String msg){
		m_xmlWriter.writeStartElement(TAG_MSG);
		m_xmlWriter.writeCharacters(msg);
	}
	
	public void reset(){
		m_xmlWriter = null;
	}
	
	//
	// API
	//
//	public Element getRoot(){
//		return m_rootNode;
//	}
	
	// 
	// Override Object
	@Override
	public String toString(){
		return m_xmlWriter.toString();
	}
	
	//
	// Private
	//
	private void startEnterTag(final String className, final String methodName){
		m_xmlWriter.writeStartElement(className);
//		m_xmlWriter.writeAttribute(ATTR_CLASS, className);
		m_xmlWriter.writeAttribute(ATTR_METHOD, methodName);
	}
	
	private void endEnterTag(){
		m_xmlWriter.writeEndElement();
	}
	
	private void startCallTag(final String className, final String methodName){
		m_xmlWriter.writeStartElement(TAG_CALL);
		
		if (className != null){
			m_xmlWriter.writeAttribute(ATTR_CLASS, className);
		}
		if (methodName != null){
			m_xmlWriter.writeAttribute(ATTR_METHOD, methodName);
		}
	}
	
	private void endCallTag(){
		m_xmlWriter.writeEndElement();
	}
	
	private void startLoopTag(final String group){
		m_xmlWriter.writeStartElement(TAG_LOOP);
		m_xmlWriter.writeAttribute(ATTR_GROUP, group);
	}
	
	private void endLoopTag(){
		m_xmlWriter.writeEndElement();
	}
}

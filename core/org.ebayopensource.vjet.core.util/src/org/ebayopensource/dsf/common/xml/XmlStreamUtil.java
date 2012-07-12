/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.xml;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * There are helper or utility functions that make using IXmlStreamWriter easier
 */
public class XmlStreamUtil {
	public static void safeWriteAttrObject(
		final IXmlStreamWriter xmlStreamWriter,
		final String attributeName,
		final Object attributeObjValue)
	{
		safeWriteAttr(xmlStreamWriter, attributeName,
			attributeObjValue == null? "" : attributeObjValue.toString());
	}
	public static void safeWriteAttr(
		final IXmlStreamWriter xmlStreamWriter,
		final String attributeName,
		final String attributeValue)
	{
		xmlStreamWriter.writeAttribute(attributeName,
			attributeValue == null? "" : attributeValue);
	}
	public static void safeWriteChildCData(
		final IXmlStreamWriter xmlWriter,
		final String elementName,
		final String value)
	{
		writeChildCData(xmlWriter, elementName, value == null?"(null)":value);
	}

//	public static void write(final IXmlStreamWriter xmlStreamWriter, final int num){
//		
//	}
	public static void writeChildText(
		final IXmlStreamWriter xmlWriter,
		final String elementName,
		final String data)
	{
		xmlWriter.writeStartElement(elementName);
		xmlWriter.writeCharacters(data);
		xmlWriter.writeEndElement();
	}
	public static void writeChildCData(
		final IXmlStreamWriter xmlWriter,
		final String elementName,
		final String value)
	{
		xmlWriter.writeStartElement(elementName);
		xmlWriter.writeCData(value);
		xmlWriter.writeEndElement();
	}
	public static void writeChildElement(
		final IXmlStreamWriter xmlWriter,
		final String elementName,
		final boolean value)
	{
		writeChildText(xmlWriter, elementName, Boolean.toString(value));
	}
	public static void writeChildElement(
		final IXmlStreamWriter xmlWriter,
		final String elementName,
		final int value)
	{
		writeChildText(xmlWriter, elementName, Integer.toString(value));
	}
	public static void writeChildElement(
		final IXmlStreamWriter xmlWriter,
		final String elementName,
		final long value)
	{
		writeChildText(xmlWriter, elementName, Long.toString(value));
	}

	public static void writeThrowable(
		final IXmlStreamWriter xmlWriter,
		final String elementName,
		final Throwable e)
	{
		xmlWriter.writeStartElement(elementName);
		final String stackString = stringForm(e);
		xmlWriter.writeCData(stackString);
		xmlWriter.writeEndElement();
	}

	public static void renderTimeElementAsDuration(
		final IXmlStreamWriter xmlWriter,
		final String elemName,
		final long time,
		final long curTime,
		final boolean future)
	{
		final String str = getTimeElementAsDuration(time, curTime, future);
		xmlWriter.writeStartElement(elemName);
		xmlWriter.writeCharacters(str);
		xmlWriter.writeEndElement();
	}

	public static String getTimeElementAsDuration(
		long time, long curTime, boolean future)
	{
		if (time < 0) {
			return "N/A";
		}

		if (time == 0) {
			return "unset";
		}

		long d;
		if (future) {
			d = time - curTime;
		} else {
			d = curTime - time;
		}
		return Long.toString(d);
	}
	/**
	 * Create and return an error XML element containing the stack trace
	 */
	public static void renderErrorXml(
		final IXmlStreamWriter xmlWriter,
		final String elemName, 
		final Throwable e)
	{
		renderErrorXml(xmlWriter, elemName, null, e);
	}

	public static void renderErrorXml(
		final IXmlStreamWriter xmlWriter,
		final String elemName, 
		final String text,
		final Throwable e)
	{
		if (text == null && e == null) {
			throw new NullPointerException();
		}

		StringBuilder sb = new StringBuilder();

		if (text != null) {
			sb.append(text);
		}

		if (e != null) {
			if (sb.length() != 0) {
				sb.append(": ");
			}
			sb.append(e.toString());
			sb.append('\n');
			sb.append(stringForm(e));
		}

		writeChildCData(xmlWriter, elemName, sb.toString());
	}

	private static String stringForm(Throwable e) {
		StringWriter sw = new StringWriter(256);
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.getBuffer().toString();
	}
}

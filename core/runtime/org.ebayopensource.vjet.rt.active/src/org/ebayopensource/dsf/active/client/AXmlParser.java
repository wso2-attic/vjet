/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.client;

import java.io.ByteArrayInputStream;
import java.util.Stack;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import org.ebayopensource.dsf.active.dom.html.AAttr;
import org.ebayopensource.dsf.active.dom.html.ADocument;
import org.ebayopensource.dsf.active.dom.html.AElement;
import org.ebayopensource.dsf.active.dom.html.ANode;
import org.ebayopensource.dsf.active.dom.html.AText;
import org.ebayopensource.dsf.dom.DAttr;
import org.ebayopensource.dsf.dom.DDocument;
import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AXmlParser {
	
	public static ADocument parse(byte[] data){
		
		final AXmlSaxHandler handler = new AXmlSaxHandler();
		
		try {
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			parser.parse(new ByteArrayInputStream(data), handler);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return handler.getDocument();
	}
	
	private static class AXmlSaxHandler extends DefaultHandler {
		
		private ADocument m_doc = new ADocument(new DDocument(), BrowserType.NONE);
		protected Stack<AElement> m_elemStack = new Stack<AElement>();
	
		@Override
		public void startElement(
			final String uri, 
			final String localName, 
			final String qName, 
			final Attributes attributes) throws SAXException
		{
			AElement elem = new AElement(m_doc, new DElement(qName));
			if (m_elemStack.isEmpty()){
				m_doc.appendChild(elem);
			}
			else {
				m_elemStack.peek().appendChild(elem);
			}
			m_elemStack.push(elem);
	
			// Add the attributes 
			if (attributes != null) {
				for (int i = 0; i < attributes.getLength(); i++) {
					AAttr attr = new AAttr(m_doc, new DAttr(attributes.getQName(i), attributes.getValue(i)));
					elem.setAttributeNode(attr);
				}
			}
		}
		
		public void characters(final char[] text, final int start, final int length) throws SAXException {
			
			String txt = new String(text, start, length);
			
			final ANode child = (ANode) m_elemStack.peek().getLastChild();
			if (child instanceof AText) {
				((AText) child).appendData(txt);
			} 
			else {
				m_elemStack.peek().appendChild(m_doc.createTextNode(txt));
			}
		}
	
		@Override
		public void endElement(
			final String uri, 
			final String localName, 
			final String qName) throws SAXException {
			
			if (!m_elemStack.isEmpty()){
				m_elemStack.pop();
			}
		}	
		
		private ADocument getDocument(){
			return m_doc;
		}
	}
}

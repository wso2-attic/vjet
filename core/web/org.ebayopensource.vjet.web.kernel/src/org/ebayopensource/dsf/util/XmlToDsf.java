/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.util;

import java.io.StringReader;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.dom.DText;
import org.ebayopensource.kernel.util.xml.rt.Sax2DefaultEventHandler;
import org.ebayopensource.kernel.util.xml.rt.XmlReaderHelper;

public class XmlToDsf {
	public static void main(String
		[] args) {
		String xmlString =
"<person age='32'>cool<!-- fonzy is cool--></person>" ;
//"<person age='32'><salary grade='senior' yearly='50000'/></person>" ;
//"<person age='32'><salary grade='senior' yearly='50000'/><salary grade='senior' yearly='50000'/></person>" ;

		DElement root = convertToDsfGraph(xmlString) ;
		
		out(root.getTagName()) ;
	}
	
	/**
	 * Answers a DsfComponent that is the corresponding Dsf representation
	 * for each element in the passed in XML.  The "name" of the element is
	 * stored as the localName in the DsfComponent -- the localName can be
	 * retrieved via comp.getDsfName().getLocalName().  Attributes of the 
	 * element are stored as DsfAttributes.
	 * 
	 * Look at FileUtils for helper methods to get the
	 * contents of a file into a String.
	 */
	public static DElement convertToDsfGraph(String xmlString) {
		
		final StringReader reader = new StringReader(xmlString);
		final InputSource inputSource = new InputSource(reader);

		Xml2DsfContentHandler h = new Xml2DsfContentHandler() ;
		try {
			final XMLReader xmlReader = XmlReaderHelper.createXmlReader();
//			xmlReader.setFeature(
//				"http://apache.org/xml/features/dom/include-ignorable-whitespace" 
//				"http://xml.org/sax/features/include-ignorable-whitespace"
//				,true) ;
			XmlReaderHelper.setDefaultParams(xmlReader, true);
			try {
				xmlReader.setErrorHandler(h);
				xmlReader.setContentHandler(h) ;
				xmlReader.setDTDHandler(h) ;
				
				xmlReader.parse(inputSource);
			} finally {
				if (reader != null ) {
					reader.close();
				}
			}
		}
		catch(Exception e) {
			throw new RuntimeException(e.getMessage()) ;
		}
		
		DElement root = h.getRoot() ;
		
		return root ;
	}

	public static class Xml2DsfContentHandler extends Sax2DefaultEventHandler {
		private DsfStack m_stack = new DsfStack() ;
		private DElement m_root ;
		
		public DElement getRoot() {
			return m_root ;
		}
		
		// Handle comments
		public void comment(char[] ch, int start, int length) {
//			String commentText = new String(ch, start, length) ;
		}
		
		// Only validating processors are required to do this
		public void ignorableWhitespace(char[] ch, int start, int length) {
//			String ignorableText = new String(ch, start, length) ;
		}
		
		// Handle text nodes
		public void characters (char[] ch, int start, int length) {
			// Discard whitespace that is tabs or end of lines
			String str = "";
			for (int i=start;i<start+length;i++) {
				if (ch[i] == '\t' || ch[i] == '\n') {continue;}
				str = str + ch[i];
			}
			// If we have residual text, attach to it's parent
			if (str.length() > 0) {
				// if stack not empty, then use what's on top as parent of text node
				if (m_stack.isEmpty() == false) {
					DText textNode = new DText(str);
					m_stack.dpeek().add(textNode) ;
				}
			}
		}
		
		public void startElement(
			String uri, String localName, String qName, Attributes attributes) 
		{
			// create node
			DElement c = new DElement(localName) ;
//			c.getDsfName().setLocalName() ;
			
			// if first node, then set as root
			if (m_root == null) {
				m_root = c ;
			}
			
			// if stack not empty, then use what's on top as parent
			if (m_stack.isEmpty() == false) {
				m_stack.dpeek().add(c) ;
			}
			
			// push onto stack
			m_stack.push(c) ;
			
			// process attributes
			for(int i = 0; i < attributes.getLength(); i++) {
				String attrName = attributes.getQName(i) ;
				String attrValue = attributes.getValue(i) ;
				c.getDsfAttributes().put(attrName, attrValue) ;
			}
		}

		public void endElement(String uri, String localName, String qName) {
			m_stack.pop() ;
		}
					
		/** Warning. */
		public void warning(SAXParseException e) throws SAXException {
			throw e;
		}

		/** Error. */
		public void error(SAXParseException e) throws SAXException {
			throw e;
		}

		/** Fatal error. */
		public void fatalError(SAXParseException e) throws SAXException {
			throw e;
		} 
	}
	
	private static class DsfStack extends Stack<DNode> {
		private static final long serialVersionUID = 1L;
		public DNode dpop() { return pop(); }
		public DNode dpeek() { return peek(); }
	}
	
	private static void out(Object o)  { }
}
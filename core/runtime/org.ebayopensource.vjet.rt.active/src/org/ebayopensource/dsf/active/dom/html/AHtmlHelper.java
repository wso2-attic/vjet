/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.w3c.dom.DOMException;

import org.ebayopensource.dsf.active.client.AHtmlParser;
import org.ebayopensource.dsf.active.client.ALocation;
import org.ebayopensource.dsf.active.client.AWindow;
import org.ebayopensource.dsf.active.dom.html.AJavaScriptHandlerHolder.JAVASCRIPT_HANDLER_TYPE;
import org.ebayopensource.dsf.dap.rt.DapCtx;
import org.ebayopensource.dsf.dom.support.XmlChar;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.html.js.URLUtil;
import org.ebayopensource.dsf.jsnative.HtmlBody;
import org.ebayopensource.dsf.jsnative.HtmlDocument;
import org.ebayopensource.dsf.jsnative.HtmlElement;
import org.ebayopensource.dsf.jsnative.HtmlSelect;
import org.ebayopensource.dsf.jsnative.HtmlSpan;
import org.ebayopensource.dsf.jsnative.Node;
import org.ebayopensource.dsf.jsnative.NodeList;
import org.ebayopensource.dsf.jsnative.Text;
import org.ebayopensource.dsf.jsnative.events.EventTarget;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.Undefined;
import org.mozilla.mod.javascript.UniqueTag;


public class AHtmlHelper {
	
	private static final String DOCUMENT_DOT_BODY = "document.body";
	private static final String DOCUMENT_DOT_DOCUMENTELEMENT = "document.documentElement";
	
	// List of JsNative property names with first letter already capitalized
	private static List<String> m_jsNativeSpecialProps = new ArrayList<String>();
	static{
		m_jsNativeSpecialProps.add("NaN");
		m_jsNativeSpecialProps.add("Infinity");
		m_jsNativeSpecialProps.add("URLUnencoded");
//		m_jsNativeSpecialProps.add("Top");
//		m_jsNativeSpecialProps.add("Left");
//		m_jsNativeSpecialProps.add("Bottom");
//		m_jsNativeSpecialProps.add("Right");
	}
	/**
	 * Returns the HtmlElement as the result of parsing a given JavaScript statement.
	 * This parser is very specialized for JS statements in the following format.
	 * 'document.body.childNodes[n].childNodes[m]....<element-tag-name>'
	 * @param doc HtmlDocument
	 * @param jsString string containing JS statement
	 * 	
	 * @return HtmlElement
	 */
	public static ANode getElementByPath(HtmlDocument doc, String jsString) {
		if (doc == null || jsString == null) {
			return null;
		}
		if (!jsString.startsWith("document.body")) {
			throw new RuntimeException("Invalid JavaScript input statement");
		}
		StringTokenizer st = new StringTokenizer(jsString, ".");
		st.nextToken(); // skip document
		st.nextToken(); // skip body
		HtmlBody body = doc.getBody();
		if (body == null) {
			return null;
		}
		
		Node currNode = body;
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (token.startsWith("childNodes")) {
				int index = Integer.parseInt(
					token.substring(token.indexOf('[')+1, token.indexOf(']')));
				NodeList nl = currNode.getChildNodes();
				currNode = nl.item(index);
				if (currNode == null) {
					return null;
				}
			} else { // This must be the last token
				if (st.hasMoreTokens()) {
					throw new RuntimeException("Invalid JavaScript input statement");
				}
				Node child = currNode.getFirstChild();
				while (child != null) {
					if (child.getNodeName().equals(token)) {
						currNode = child;
						break;
					}
					child = child.getNextSibling();
				}
			}
		}
		
		return (ANode)currNode;
	}
	
	/**
	 * Returns the HtmlElement as the result of parsing a given JavaScript statement.
	 * This parser is very specialized for JS statements in the following format.
	 * 'document.documentElement.childNodes[n].childNodes[m]....<element-tag-name>'
	 * @param doc HtmlDocument
	 * @param jsString string containing JS statement
	 * 	
	 * @return HtmlElement
	 */
	public static ANode getElementByPath(HtmlElement docElem, String jsString) {
		if (docElem == null || jsString == null) {
			return null;
		}
		if (!jsString.startsWith("document.documentElement")) {
			throw new RuntimeException("Invalid JavaScript input statement");
		}
		StringTokenizer st = new StringTokenizer(jsString, ".");
		st.nextToken(); // skip document
		st.nextToken(); // skip body
		if (docElem == null) {
			return null;
		}
		
		Node currNode = docElem;
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (token.startsWith("childNodes")) {
				int index = Integer.parseInt(
					token.substring(token.indexOf('[')+1, token.indexOf(']')));
				NodeList nl = currNode.getChildNodes();
				currNode = nl.item(index);
				if (currNode == null) {
					return null;
				}
			} else { // This must be the last token
				if (st.hasMoreTokens()) {
					throw new RuntimeException("Invalid JavaScript input statement");
				}
				Node child = currNode.getFirstChild();
				while (child != null) {
					if (child.getNodeName().equals(token)) {
						currNode = child;
						break;
					}
					child = child.getNextSibling();
				}
			}
		}
		
		return (ANode)currNode;
	}
	public static String getElementPath(AHtmlElement node){
		if(node != null){
			if (node.getId() != null && node.getId() != "") {
//				if(node.getId().equalsIgnoreCase("body")){
//					return "window";
//				}else{
					return "document.getElementById('" + node.getId() + "')";
//				}
				
			}
			return getPath(node);
		}
		return null;
	}
	
	public static String getFormPath(final EventTarget target){
		if (!(target instanceof AHtmlButton) && !(target instanceof AHtmlInput)){
			return null;
		}
		ANode node = (ANode)target;
		AHtmlForm form = getContainingForm(node);
		if (form == null){
			return null;
		}
		if ((node instanceof AHtmlButton || 
				(node instanceof AHtmlInput && ((AHtmlInput) node).getType().equals("submit")))) {
			return AHtmlHelper.getElementPath(form);
		}
		
		return null;
	}
	
	public static AHtmlForm getContainingForm(ANode node){
		while (node != null){
			ANode parent = (ANode)node.getParentNode();
			if (parent != null && parent instanceof AHtmlForm){
				return (AHtmlForm)parent;
			}
			node = parent;
		}
		return null;
	}

	//
	// Private
	//
	private static String getPath(ANode node){
		if(node instanceof AHtmlBody ){
			return "document.body";
		}else if(node instanceof AHtmlHead){
			return "document.getElementsByTagName('head').item(0)";
		}else 
			//if(!(node instanceof AHtmlBody))
			{
			int ind = getIndex(node);
			String path = getPath((AHtmlElement)node.getParentNode());
			path += ".childNodes[" + ind + "]"; 
			return path;  
		}
//		return "";
	}

	private static int getIndex(ANode node) {
		if (node.getPreviousSibling() == null) {
			return 0;
		}
		return getIndex((ANode)node.getPreviousSibling()) + 1;
	}

	public static void setText(HtmlSpan span, String text){
		Text textNode = getTextNode(span);
		if (text == null || text.length() == 0){
			if (textNode == null){
				return;
			}
			else {
				span.removeChild(textNode);
			}
		}
		else {
			if (textNode == null) {
				AHtmlDocument doc = (AHtmlDocument)span.getOwnerDocument();
				textNode = doc.createTextNode(text);
				span.appendChild(textNode);
			}
			else {
				textNode.setData(text);
			}
		}
	}
	
	public static Text getTextNode(Node node){
		NodeList children = node.getChildNodes();
		if (children.getLength() > 0){
			for (int i=0; i<children.getLength(); i++){
				if (children.item(i) instanceof AText){
					return (AText)children.item(i);
				}
			}
		} 
		return null;
	}
	
	public static void removeAllOptions(HtmlSelect selectElem){
		for (int i = selectElem.getLength()-1; i>=0; i--){
			selectElem.removeByIndex(i);
		}
	}
	
	public static void setContentDocument(AHtmlFrame elem, AHtmlDocument doc) {
		elem.setContentDocument(doc);
	}
	
	public static void setContentDocument(AHtmlIFrame elem, AHtmlDocument doc) {
		elem.setContentDocument(doc);
	}
	
	public static boolean booleanValueOf(EHtmlAttr attr, String val){
		if(val.equals(attr.getAttributeName()))
			return true;
		else
			return Boolean.valueOf(val);
	}

	public static AHtmlDocument getContentDocument(AHtmlElement frame, String src) {
		if (frame == null) {
			return null;
		}
		AHtmlDocument doc = frame.getOwnerDocument();
		if (doc == null) {
			return null;
		}
		ALocation location = (ALocation) doc.getLocation();
		if (location == null) {
			return null;
		}
		AWindow ParentWindow = location.getWindow();
		if (ParentWindow == null) {
			return null;
		}
		String pageUrl = null;
		if (location.getHref() != null) {
			pageUrl = URLUtil.getBaseURL(location.getHref());
		}
		URL url;
		try {
			url = new URL(URLUtil.getAbsoluteURL(src, pageUrl));
		} catch (MalformedURLException e) {
			e.printStackTrace();	//KEEPME
			throw new RuntimeException(e);
		}
		AWindow frameWindow; 
		try {
			frameWindow = (AWindow) AHtmlParser.parse(url);
			frameWindow.setParent(ParentWindow);
			ParentWindow.addChildWindow(frameWindow);
			frameWindow.setName(frame.getAttribute(EHtmlAttr.name.name()));
		} catch (Exception e) {
			e.printStackTrace(); //KEEPME
			throw new RuntimeException(e);
		}
		
		AHtmlDocument frameDoc = (AHtmlDocument) frameWindow.getDocument();
		return frameDoc;
	}

	public static ANode getElementReference(String elementByPath, String elementId){
		ANode elem = null;
		HtmlDocument doc = DapCtx.ctx().getWindow().getDocument();
		if(elementByPath != null && elementByPath.indexOf(DOCUMENT_DOT_BODY)!=-1){
			elem = getElementByPath(doc, elementByPath);
		}else if(elementByPath != null && elementByPath.indexOf(DOCUMENT_DOT_DOCUMENTELEMENT)!=-1){
			elem = getElementByPath((HtmlElement) doc.getDocumentElement(), elementByPath);
		}else if(elementId!= null){
			elem = (ANode)doc.getElementById(elementId);
		}
		return elem;
	}
	
	public static ANode getElementByPath(String jsString) {
		HtmlDocument doc = DapCtx.ctx().getWindow().getDocument();
		return getElementByPath(doc, jsString);
	}
	
	public static void checkQualifiedName(String qname){
		if (qname == null || qname.length() == 0) {
			throw new ADOMException(
	            new DOMException(DOMException.NAMESPACE_ERR, 
	            	"QName has invalid format"));
		}
        int index = qname.indexOf(':');
        int lastIndex = qname.lastIndexOf(':');
        int length = qname.length();

        // it is an error for NCName to have more than one ':'
        // check if it is valid QName
        if (index == 0 || index == length - 1 || lastIndex != index) {
            throw new ADOMException(
            	new DOMException(DOMException.NAMESPACE_ERR, 
            		"QName has invalid format"));
        }
        int start = 0;
        // Namespace in XML production [6]
        if (index > 0) {
            // check that prefix is NCName
            if (!XmlChar.isNCNameStart(qname.charAt(start))) {
                throw new ADOMException(
                	new DOMException(DOMException.INVALID_CHARACTER_ERR, 
                			"QName has invalid format"));
            }
            for (int i = 1; i < index; i++) {
                if (!XmlChar.isNCName(qname.charAt(i))) {
                    throw new ADOMException(
                    	new DOMException(DOMException.INVALID_CHARACTER_ERR, 
                    			"QName has invalid format"));
                }
            }
            start = index + 1;
        }

        // check local part
        if (!XmlChar.isNCNameStart(qname.charAt(start))) {
            // REVISIT: add qname parameter to the message
        	throw new ADOMException(
                	new DOMException(DOMException.INVALID_CHARACTER_ERR, 
                		"QName has invalid format"));
        }
        for (int i = start + 1; i < length; i++) {
            if (!XmlChar.isNCName(qname.charAt(i))) {
            	throw new ADOMException(
                    new DOMException(DOMException.INVALID_CHARACTER_ERR, 
                    	"QName has invalid format"));
            }
        }
    }

	/**
	 * Returns the original property name.
	 * - if property name is all capitalized, returns as is.
	 * - if original property name starts with capitalized letter, returns as is.
	 * - otherwise, the first letter of property name is lowercased.
	 * @param propName property name
	 * @return String original property name
	 */
	public static String getOriginalPropertyName(String propName) {
		if (propName == null) {
			return null;
		}
		if (m_jsNativeSpecialProps.contains(propName)) {
			return propName;
		}
		if (isNameAllCapitalized(propName)) {
			return propName;
		}
		return propName.substring(0, 1).toLowerCase() + propName.substring(1);
	}

	public static boolean isNameAllCapitalized(String name) {
		char [] chars = name.toCharArray();
		for (char ch : chars) {
			if (Character.isLetter(ch) && !Character.isUpperCase(ch)) {
				return false;
			}
		}
		return true;
	}
	
	public static String getCorrectType(String type){
		if(type.substring(0,2).equals("on")){
			type = type.substring(2);
		}
		return type;
	}

	public static double getDoubleAttributeValue(String atrrName, Scriptable scriptableObject){
		Object val = getAttributeValue(atrrName,scriptableObject);
		if(val!=null){
			return (Double)val;
		}
		return 0.0;
	}

	public static String getStringAttributeValue(String atrrName, Scriptable scriptableObject){
		Object val = getAttributeValue(atrrName,scriptableObject);
		if(val!=null){
			return val.toString();
		}
		return null;
	}
	
	public static boolean getBooleanAttributeValue(String atrrName, Scriptable scriptableObject){
		Object val = getAttributeValue(atrrName,scriptableObject);
		if(val!=null){
			return (Boolean)val;
		}
		return false;
	}

	public static long getLongAttributeValue(String atrrName, Scriptable scriptableObject){
		Object val = getAttributeValue(atrrName,scriptableObject);
		if(val!=null){
			try {
				Double dblVal = Double.parseDouble(val.toString()); 
				return dblVal.longValue();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return 0;
	}

	public static int getIntAttributeValue(String atrrName, Scriptable scriptableObject){
		Object val = getAttributeValue(atrrName,scriptableObject);
		if(val!=null){
			String strVal = val.toString();
			int idx = strVal.indexOf(".");
			if(idx!=-1){
				strVal = strVal.substring(0,idx);
			}
			try {
				return Integer.parseInt(strVal);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return 0;
	}
	
	public static Object getAttributeValue(String atrrName, Scriptable scriptableObject) {
		try {
			Object value = scriptableObject.get(atrrName, scriptableObject);
			if (value instanceof UniqueTag || value instanceof Undefined) {
				return null;
			}
			return value;
		}
		catch (Exception e) {
			//DO NOTHING
		}
		return null;
	}
	
	public static void addEventHandlerListeners( Map<String, List<AJavaScriptHandlerHolder>> listeners,String type, Object listener, JAVASCRIPT_HANDLER_TYPE handlerType){
		type = AHtmlHelper.getCorrectType(type);
		List<AJavaScriptHandlerHolder> lst = listeners.get(type);
		if(lst==null){
			lst = new ArrayList<AJavaScriptHandlerHolder>(2);
			listeners.put(type, lst);
		}		
		AJavaScriptHandlerHolder holder = new AJavaScriptHandlerHolder(listener,handlerType);
		if(handlerType.equals(JAVASCRIPT_HANDLER_TYPE.INLINE)){
			if(lst.size()==0){
				lst.add(0,holder);
			}else{
				lst.set(0,holder);	
			}
		}else{
			lst.add(holder);
		}
	}
}

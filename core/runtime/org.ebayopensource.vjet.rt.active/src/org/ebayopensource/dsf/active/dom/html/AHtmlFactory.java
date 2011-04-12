/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.NodeList;

import org.ebayopensource.dsf.active.dom.html.AHtmlType.Type;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.dom.DDocument;
import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.dom.DElementConstructor;
import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.dom.DText;
import org.ebayopensource.dsf.html.dom.BaseHeading;
import org.ebayopensource.dsf.html.dom.BaseTableSection;
import org.ebayopensource.dsf.html.dom.DObject;
import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;
import org.ebayopensource.dsf.jsnative.HtmlDocument;
import org.ebayopensource.dsf.jsnative.HtmlElement;

public class AHtmlFactory {			
	
	private static HashMap<String,Constructor<?> > s_elementTypesHTML;
	private static HashMap<String,Constructor<?> > s_objectConstructors = new HashMap<String,Constructor<?>>();

	static {
		populateElementTypes();
	}
	
	public synchronized static void setObjectConstructor(String objectId, Constructor<?> objConstructor){
		if (objectId == null || objConstructor == null){
			throw new RuntimeException("objectId or objConstructor is null: objectId=" 
					+ objectId + "; objConstructor=" + objConstructor);
		}
		s_objectConstructors.put(objectId, objConstructor);
	}
	
//	public static HtmlElement createElement(final String tagName) {
//		return createElement(null, tagName);
//	}
//	
//	public static HtmlElement createElement(final HtmlTypeEnum htmlType) {
//		return createElement(null, htmlType);
//	}
	
	public static HtmlElement createElement(final HtmlDocument owner, final String tagName) {
		AHtmlType<?> htmlType = Type.get(tagName);
		if (htmlType == null) {
			return createUnknownElement(owner, tagName);
		}
		return createElement(owner, htmlType);
	}

	public static HtmlElement createElement(final HtmlDocument owner, final HtmlTypeEnum htmlType) {
		if (htmlType == null) {
			throw new RuntimeException("htmlType is null");
		}

		DNode dNode;
		try {
			dNode = (DNode) htmlType.getTypeClass().newInstance();
			if (owner != null) {
				DomHelper.setOwnerDocument(((DDocument) ANodeHelper.getDNode((ANode) owner)), dNode);
			}
			return createElement(owner, dNode);
		} catch (Exception e) {
			throw new DsfRuntimeException(e);
		}
	}
	
	public static HtmlElement createElement(HtmlDocument owner, AHtmlType<?> htmlType) {
		if (htmlType == null) {
			throw new RuntimeException("htmlType is null");
		}
		if (owner == null) {
			throw new RuntimeException("owner document is null");
		}

		DNode dNode;
		try {
			dNode = (DElement) htmlType.getType().getTypeClass().newInstance();
			if (owner != null) {
				DomHelper.setOwnerDocument(((DDocument) ANodeHelper.getDNode((ANode) owner)), dNode);
			}
		} catch (Exception e) {
			throw new DsfRuntimeException(e);
		}
		return createElement(htmlType.getTagName(), owner, dNode);
	}
	
	public static HtmlElement createElement(final HtmlDocument owner, final DNode dNode) {
		return createElement(dNode.getNodeName(), owner, dNode);
	}
	
	static HtmlElement createUnknownElement(final HtmlDocument owner, final String tagName) {
		
		return new AHtmlUnknown((AHtmlDocument) owner, new DElement(tagName));
	}

	static HtmlElement createElement(
		final String tagName, final HtmlDocument owner, final DNode dNode) 
	{
		if (dNode == null) {
			throw new RuntimeException("DNode is null");
		}
		Constructor<?> cnst = null;
		if ("object".equals(tagName)){
			cnst = s_objectConstructors.get(((DObject)dNode).getHtmlId());
		}

		if (cnst == null) {
			cnst = s_elementTypesHTML.get(tagName);
		}
		
		if (cnst == null) {
			throw new RuntimeException("Unknown tagName " + tagName);
		}
		
		final Object[] args = new Object[] {owner, dNode};
		// Get the constructor for the element. The signature specifies an
		// owner document and a tag name. Use the constructor to instantiate
		// a new object and return it.
		try {
			return (HtmlElement) cnst.newInstance(args);
		} 
		catch (Exception ex) {
			ex.printStackTrace();	// KEEPME
			throw new RuntimeException(
				"Tag '"
				+ tagName
				+ "' associated with an Element class failed to construct"
				+ " with following message: "
				+ ex.getMessage(),
				ex);
		}
	}
	
	static void appendChild(AElement parent, DNode child) {
		if (child instanceof DText) {
			AText text = new AText((AHtmlDocument) parent.getOwnerDocument(), (DText)child);
			parent.appendChild(text);
		}
		else {
			boolean hasChildren = child.hasChildNodes();
			List<DNode> dNodeChildren = null;
			if (hasChildren) {
				//save the children to temp list
				NodeList children = child.getChildNodes();
				int size = children.getLength();
				dNodeChildren = new ArrayList<DNode>(size);
				for (int i = 0; i < size; i++) {
					dNodeChildren.add((DNode)children.item(i));
				}
				//remove the children first
				for (int i = 0; i < size; i++) {
					child.removeChild(dNodeChildren.get(i));
				}
			}
			
			//append child to the current node
			AHtmlElement element = 
				(AHtmlElement) createElement((AHtmlDocument)parent.getOwnerDocument(), child);
			if (hasChildren) {
				//add back the children
				for (int i = 0; i < dNodeChildren.size(); i++) {
					child.appendChild(dNodeChildren.get(i));
					appendChild(element, dNodeChildren.get(i));
				}
			}
			//parent.getDNode().appendChild(child);
			parent.appendChild(element);			
		}
	}
	
	private static void populateElementTypes() {
		Iterable<AHtmlType<?>> iter = Type.valueIterable();
		s_elementTypesHTML = new HashMap<String,Constructor<?> >(64);
		for (AHtmlType<?> htmlType : iter) {
			populateElementType(htmlType);
		}
	}

	private static void populateElementType(final AHtmlType<?> htmlType) {
		String tagName = htmlType.getTagName();
//		// handle tag names that end in "_ie" by stripping it off
//		if (tagName.endsWith("_IE")) {
//			tagName = tagName.substring(0, tagName.length() - 3) ;
//		}
		Class<?> elemClz = htmlType.getTypeClass();
		Class<?>[] elemClassSig = new Class[] {AHtmlDocument.class, getDNodeType(htmlType.getType())};
		try {
			s_elementTypesHTML.put(
				tagName,
				elemClz.getDeclaredConstructor(elemClassSig));
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Could not find proper constructor for "
					+ elemClz.getName()
					+ " for "
					+ tagName,
				e);
		}
	}

	private static Class<?> getDNodeType(HtmlTypeEnum htmlType) {
		if (htmlType == HtmlTypeEnum.TBODY ||
			htmlType == HtmlTypeEnum.TFOOT ||
			htmlType == HtmlTypeEnum.THEAD) 
		{
			return BaseTableSection.class;
		} else if (htmlType == HtmlTypeEnum.H1 ||
				htmlType == HtmlTypeEnum.H2 ||
				htmlType == HtmlTypeEnum.H3 ||
				htmlType == HtmlTypeEnum.H4 ||
				htmlType == HtmlTypeEnum.H5 ||
				htmlType == HtmlTypeEnum.H6) 
		{
			return BaseHeading.class;
		}
		else {
			return htmlType.getTypeClass();
		}
	}
	
	private static class DomHelper extends DElementConstructor {
		protected static void setOwnerDocument(DDocument doc, DNode node) {
			DElementConstructor.setOwnerDocument(doc, node);
		}
	}
	
	private static class ANodeHelper extends ANodeInternal {
		private static DNode getDNode(ANode node){
			return ANodeInternal.getInternalNode(node);
		}
	}
}

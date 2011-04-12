/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.ebayopensource.dsf.dom.DComment;
import org.ebayopensource.dsf.dom.DDocument;
import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.dom.DProcessingInstruction;
import org.ebayopensource.dsf.dom.support.DNamespace;

public class DHtmlDocument extends DDocument {

	private static final long serialVersionUID = 3258132457579427892L;
	
	private static final Html s_html = new Html() ;

	/**
	 * Constructor(s)
	 */
	public DHtmlDocument() {
		super();
	}
	public DHtmlDocument(final DHtmlDocType.Type docType) {
		this();
		setDocType(docType);
	}
	
	//
	// Framework
	//
	public NodeList getElementsByTagName(final HtmlTypeEnum type) {
		return getElementsByTagName(type.getName());
	}
	 
	/**
	 * Framework use only
	 */
	protected DDocument setDOMImplementation(final DHtmlDOMImplementation impl) {
		super.setDOMImplementation(impl) ;
		return this ;
	}
	
	//
	// DDocument overrides
	//
//	/**
//	 * Add namespace declaration to this document
//	 * @param declaration
//	 */
//	@Override
//	public DHtmlDocument dsfAddNamespaceDeclaration(DNamespace declaration) {
//		super.dsfAddNamespaceDeclaration(declaration) ;
//		return this ;
//	}
	
	@Override
	public DElement createElement(final String tagName) {
		return s_html.domCreateDynamicElement(tagName) ;
	}
	
	@Override 
	public DElement createElementNS(
		final DNamespace namespace, final String tagName)
	{
		return createElement(tagName).setDsfNamespace(namespace) ;
	}
	
	@Override
	public DElement createElementNS(
		final String namespaceUri, final String possibleQualifiedTagName)
	{
		return s_html.domCreateDynamicElement(namespaceUri, possibleQualifiedTagName) ;
	}
	//
	// DNode overrides
	//
	@Override
	public DHtmlDocument add(final DNamespace ns) {
		getDsfNamespaceDeclarations().add(ns) ;
		return this ;
	}
	
	@Override
	public DHtmlDocument add(final DNode newChild) throws DOMException {
		appendChild(newChild) ;
		return this ;
	}
		
	@Override
	public Node appendChild(final Node newChild) throws DOMException {
		if (newChild == null) {
			// let super add() deal with null
			super.appendChild(null) ;
			return this ;
		}
			
		final Class<?> newChildsClass = newChild.getClass();
		
		if	(DHtmlDocType.class.isAssignableFrom(newChildsClass)
			|| DHtml.class.isAssignableFrom(newChildsClass)
			|| DComment.class.isAssignableFrom(newChildsClass)
			|| DProcessingInstruction.class.isAssignableFrom(newChildsClass)) 
		{
			super.appendChild(newChild) ;
			return this ;
		}

		throw new DOMException(
			DOMException.VALIDATION_ERR, 
			"A DHtmlDocuments children can be DHtmlDocType(0..1), DHtml(0..1), DProcessingInstruction(0..n), DComment(0..n)") ;
	}
	
	@Override
	public DHtmlDocType getDoctype() {
		return (DHtmlDocType)super.getDoctype();
	}
	
	/** This will set the document type.  If there was a previous document
	 * type, it will be replaced.
	 */
	public void setDocType(final DHtmlDocType.Type desiredDocType){
		final DocumentType oldDocType = getDoctype();
		final DHtmlDocType docType =
			DHtmlDocType.createDocType(this, desiredDocType);
		// if new doctype is null and has an old doctype, then remove it
		if (null == docType) {
			if (null != oldDocType) {
				removeChild(oldDocType);
			}
		}
		else {
			if (oldDocType == null) {
				insertBefore(docType, getFirstChild());
			} else {
				replaceChild(docType, oldDocType);
			}
		}
	}
	
	//	@Override
	public DHtmlDocType createDocumentType(
		final String qualifiedName,
		final String publicId,
		final String systemId)
	{
		return new DHtmlDocType(this, qualifiedName, publicId, systemId);
	}

	/**
	 * @deprecated - use getHtml() instead
	 * @return
	 */
	public DHtml getDomDocumentElement() {
		return getHtml() ;
	}
	
	public DHtml getHtml() {
		// The document element is the top-level HTML element of the HTML
		// document. Only this element should exist at the top level.
		// If the HTML element is found, all other elements that might
		// precede it are placed inside the HTML element.
		for (Node node=getFirstChild();node!=null;node=node.getNextSibling()){
			if (node instanceof DHtml) {
				return (DHtml)node;
			}
		}
		// could not find the html document.

		// HTML element must exist. Create a new element and dump the
		// entire contents of the document into it in the same order as
		// they appear now.
		final DHtml html = new DHtml(this);
		for (Node node=getFirstChild();node!=null;node=node.getNextSibling()) {
			if (!(node instanceof DocumentType)) {
				html.appendChild(node);
			}
		}
		add(html);
		return html;
	}

	public DHead getHead() {
		// Call getDocumentElement() to get the HTML element that is also the
		// top-level element in the document. Get the first element in the
		// document that is called HEAD. Work with that.
		Node html = getHtml() ; //getDocumentElement();
		Node head = html.getFirstChild();
		while (head != null && !(head instanceof DHead)) {
			head = head.getNextSibling();
		}
		if (head != null) {
			Node child = html.getFirstChild();
			Node firstChild = head.getFirstChild();
			while (child != null && child != head) {
				Node next = child.getNextSibling();
				head.insertBefore(child, firstChild);
				child = next;
			}
			return (DHead) head;
		}

		// Head does not exist, create a new one, place it at the top of the
		// HTML element and return it.
		head = new DHead(this);
		html.insertBefore(head, html.getFirstChild());
		return (DHead) head;
	}

	public String getTitle() {
		// Get the HEAD element and look for the TITLE element within.
		// When found, make sure the TITLE is a direct child of HEAD,
		// and return the title's text (the Text node contained within).
		final DHead head = getHead();
		final NodeList list = head.getElementsByTagName(HtmlTypeEnum.TITLE);
		if (list.getLength() > 0) {
			final DTitle title = (DTitle)list.item(0);
			return title.getHtmlText();
		}
		// No TITLE found, return an empty string.
		return "";
	}

	public DBody getBody() {
		// Call getDocumentElement() to get the HTML element that is also the
		// top-level element in the document. Get the first element in the
		// document that is called BODY. Work with that.
		final DHtml html = getHtml();
		final DHead head = getHead();
		Node body = head.getNextSibling();
		while (body != null
			&& !(body instanceof DBody)
			&& !(body instanceof DFrameSet))
			body = body.getNextSibling();

		// BODY/FRAMESET exists but might not be second element in HTML
		// (after HEAD): make sure it is and return it.
		if (body != null) {
			Node child = head.getNextSibling();
			Node firstChild = body.getFirstChild();
			while (child != null && child != body) {
				Node next = child.getNextSibling();
				body.insertBefore(child, firstChild);
				child = next;
			}
			return (DBody) body;
		}

		// BODY does not exist, create a new one, place it in the HTML element
		// right after the HEAD and return it.
		body = new DBody(this);
		html.appendChild(body);
		
		return (DBody) body;
	}

//	public synchronized DElement getDomElementById(String elementId) {
//		return super.getElementById(elementId);
//	}

//	public final List getDomElementsByTagName(String tagName) {
//		return super.getDomElementsByTagName(tagName.toUpperCase(Locale.ENGLISH));
//	}
	
//	public NList getElementsByName(String elementName) {
//		//return new NameNodeListImpl(this, elementName);
//		return null;
//	}

	public DHtmlCollection getImages() {
		// For more information see HTMLCollection#collectionMatch
//		if (m_images == null)
		return new DHtmlCollection(getBody(), DHtmlCollection.IMAGE);
//		return m_images;
	}

	public DHtmlCollection getApplets() {
		// For more information see HTMLCollection#collectionMatch
//		if (m_applets == null)
		return new DHtmlCollection(getBody(), DHtmlCollection.APPLET);
//		return m_applets;
	}

	public DHtmlCollection getLinks() {
		// For more information see HTMLCollection#collectionMatch
//		if (m_links == null)
		return new DHtmlCollection(getBody(), DHtmlCollection.LINK);
//		return m_links;
	}

	public DHtmlCollection getForms() {
		// For more information see HTMLCollection#collectionMatch
//		if (m_forms == null)
		return new DHtmlCollection(getBody(), DHtmlCollection.FORM);
//		return m_forms;
	}
	
	public DHtmlCollection getAnchors() {
		// For more information see HTMLCollection#collectionMatch
//		if (m_anchors == null)
		return new DHtmlCollection(getBody(), DHtmlCollection.ANCHOR);
//		return m_anchors;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		final DHtmlDocument newDoc = new DHtmlDocument();
		super.cloneNode(newDoc);
		return newDoc;
	}
	
	@Override
	public DHtmlDocument jif(final String jif) { 
		super.jif(jif) ;
		return this ;
	}
}

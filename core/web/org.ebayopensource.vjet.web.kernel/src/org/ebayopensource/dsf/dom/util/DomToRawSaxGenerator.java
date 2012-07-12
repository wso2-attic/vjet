/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom.util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.xml.IXmlStreamWriter;
import org.ebayopensource.dsf.dom.DCDATASection;
import org.ebayopensource.dsf.dom.DComment;
import org.ebayopensource.dsf.dom.DDocument;
import org.ebayopensource.dsf.dom.DDocumentType;
import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.dom.DEntity;
import org.ebayopensource.dsf.dom.DEntityReference;
import org.ebayopensource.dsf.dom.DProcessingInstruction;
import org.ebayopensource.dsf.dom.DRawString;
import org.ebayopensource.dsf.dom.DText;
import org.ebayopensource.dsf.html.dom.util.IChildIntercepter;
import org.ebayopensource.dsf.html.dom.util.INodeEmitter;
import org.ebayopensource.dsf.html.dom.util.IRawSaxHandler;


/**
 * This implementation is highly recursive.  We haven't seen stack maintenance
 * show up on any scope, so trying to optimize or do some tail recursion that the
 * VM "may" optimize may not buy you much.  
 */
public class DomToRawSaxGenerator implements INodeEmitter {

	private final IRawSaxHandler m_rawSaxHandler;
	
	// These two members are used to avoid instanceof and cast operations in 
	// some of the implementation loop processing.
	private final boolean m_handlerIsChildInterceptor ;
	private final IChildIntercepter m_saxHandlerCastToInterceptor ;
	protected boolean debugChildIntercepter = false;
	
	//
	// Constructor(s)
	//
	public DomToRawSaxGenerator(final IRawSaxHandler rawSaxHandler) {
		m_rawSaxHandler = rawSaxHandler;
		m_handlerIsChildInterceptor = rawSaxHandler instanceof IChildIntercepter;
		m_saxHandlerCastToInterceptor = m_handlerIsChildInterceptor
			? (IChildIntercepter)m_rawSaxHandler	
			: null ; // Allows us to mark member field as final
	}

	//
	// Satisfy INodeEmitter
	//
	public void genEvents(final Node node, final IXmlStreamWriter writer) {
		final int nodeType = node.getNodeType();
		// Keep this order as element node is most common and then text etc...
		switch(nodeType) {
			case Node.ELEMENT_NODE:
				// Need cast to bind to correct implementation
				DElement elem = (DElement)node ;
				genEventsForElement(elem, writer);
				break ;
				
			case Node.TEXT_NODE:
				// DText and DRawString have the "same" getNodeType() by design
				// so we have to differentiate via so other mechanism.
				if (node instanceof DRawString) {
					m_rawSaxHandler.handleRawString((DRawString)node);
				}
				else{
					m_rawSaxHandler.handleText((DText)node);
				}
				break ;
				
			case Node.CDATA_SECTION_NODE:
				m_rawSaxHandler.handleCData((DCDATASection)node);
				break ;
				
			case Node.COMMENT_NODE:
				m_rawSaxHandler.handleComment((DComment)node);
				break ;
				
			case Node.DOCUMENT_NODE:
				genEventsForDocument((DDocument)node, writer);
				break ;
				
			case Node.DOCUMENT_TYPE_NODE:
				m_rawSaxHandler.handleDocType((DDocumentType)node);
				break ;
				
			case Node.ENTITY_NODE:
				m_rawSaxHandler.handleEntity((DEntity)node);
				break ;	
				
			case Node.ENTITY_REFERENCE_NODE:
				m_rawSaxHandler.handleEntityReference((DEntityReference)node);
				break ;				
				
			case Node.DOCUMENT_FRAGMENT_NODE:
				genChildEvents(node, writer);
				break;
				
			case Node.PROCESSING_INSTRUCTION_NODE:
				m_rawSaxHandler.handleProcessingInstruction((DProcessingInstruction)node) ;
				break;
				
			default:
				throw new DsfRuntimeException("unknown node type of " + nodeType);
		}
	}
	
	//
	// API
	//
	public void genEventsForElement(
		final DElement element, final IXmlStreamWriter writer)
	{
		if (m_handlerIsChildInterceptor) {
			boolean handled = debugChildIntercepter ?
				m_saxHandlerCastToInterceptor.genDebugEvents(element, this, writer) :
				m_saxHandlerCastToInterceptor.genEvents(element, this, writer);
			if (handled) {
				// return because it emitted the element and all children
				return ;
			}
		}
		genEventsForElementInternal(element, writer);
	}
	
	protected void genEventsForElementInternal(
		final DElement element, final IXmlStreamWriter writer)
	{
//TODO: MrP - How do we figure out that a DScript element with a DocType that is
// XML, SVG or XHTML needs to be in a CDATA section.  Doing a String check is
// pretty slow, at this level we don't know about HtmlType enums or such due to
// dependencies.  Perhaps DScript implements ISelfRender...?
		m_rawSaxHandler.startElement(element);
		genChildEvents(element, writer);
		m_rawSaxHandler.endElement(element);		
	}
	
	public void genEventsForDocument(
		final DDocument document, final IXmlStreamWriter writer)
	{
		m_rawSaxHandler.startDocument(document);
		genChildEvents(document, writer);
		m_rawSaxHandler.endDocument(document);
	}

	protected void genChildEvents(
		final Node node, final IXmlStreamWriter writer)
	{
		if (!node.hasChildNodes()) {
			return ;
		}
		
		final NodeList children = node.getChildNodes();
		final int size = children.getLength();
		Node lastChild = null;
		boolean expended = false;
		for (int i = 0; i < size; i++) {
			lastChild = children.item(i);
			genEvents(lastChild, writer);
			//to check if the node list had been expended
			if (size < children.getLength()) {
				expended = true;
				break;
			}
		}
		//support dynamic doc for progressive render
		if (expended) {
			Node next = lastChild.getNextSibling();
			while (next != null) {
				genEvents(next, writer);
				next = next.getNextSibling();
			}
		}
	}
}

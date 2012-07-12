/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Set;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.ebayopensource.dsf.common.Z;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.node.IAttributeMap;
import org.ebayopensource.dsf.common.trace.TraceCtx;
import org.ebayopensource.dsf.common.xml.IIndenter;
import org.ebayopensource.dsf.common.xml.IXmlStreamWriter;
import org.ebayopensource.dsf.common.xml.XmlStreamException;
import org.ebayopensource.dsf.dom.DAttr;
import org.ebayopensource.dsf.dom.DCDATASection;
import org.ebayopensource.dsf.dom.DComment;
import org.ebayopensource.dsf.dom.DDocument;
import org.ebayopensource.dsf.dom.DDocumentType;
import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.dom.DEntity;
import org.ebayopensource.dsf.dom.DEntityReference;
import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.dom.DNotation;
import org.ebayopensource.dsf.dom.DProcessingInstruction;
import org.ebayopensource.dsf.dom.DRawString;
import org.ebayopensource.dsf.dom.DText;
import org.ebayopensource.dsf.dom.support.DNamespace;
import org.ebayopensource.dsf.html.dom.util.HtmlNSHelper;
import org.ebayopensource.dsf.html.dom.util.IChildIntercepter;
import org.ebayopensource.dsf.html.dom.util.INodeEmitter;
import org.ebayopensource.dsf.html.dom.util.IRawSaxHandler;
import org.ebayopensource.dsf.html.dom.util.ISelfRender;



public abstract class BaseDomWriter implements IRawSaxHandler, IChildIntercepter{
	/**
	 * Dsf defined key to associate the   
	 * element start tag line number in DOMUserData.
	 */
	public static final String START_LINE = "dsfSL";
	/**
	 * Dsf defined key to associate the 
	 * element end tag line number in DOMUserData.
	 */
	public static final String END_LINE = "dsfEL";
	protected static char[][] s_attribute_names_as_chars ; //= createAttrNameToChars() ;
	
//	protected ISchema m_schema;

	protected DDocument localtmpDoc; //local variable
	protected ILineLocateIndenter m_lineLocator;  //line locator
	protected String m_elementStartLineNum; //tracking the current element start line number
	
	private IXmlStreamWriter m_writer ;
	
	private DomWriterCtx m_writerCtx;
	
	//
	// Constructor(s)
	//
	protected BaseDomWriter(final IXmlStreamWriter writer) {
		m_writer = writer ;
	}
	
	protected BaseDomWriter(final IXmlStreamWriter writer, final DomWriterCtx ctx) {
		this(writer) ;
		m_writerCtx = ctx ;
	}
	
	public void endElement(final DElement element) {
//		final IElementInfo elementInfo = getElementInfo(element) ;
		
//		if (shouldEndTag(elementInfo)) {
			addLine(element, false, true);
			try {
				m_writer.writeEndElement();
			} 
			catch (Exception e) {
				throw new DsfRuntimeException(
					"error writing end tag for '" + element.getTagName() + "'", e);
			}
//		} 
//		else {
//			addLine(element, false, false);
//			m_writer.ignoreCurrentEndTag();
//		}
	}
	
	//
	// Framework
	//
//	public abstract IElementInfo getElementInfo(DElement element) ;
	
	//
	// Satisfy IChildIntercepter
	//

	public boolean genEvents(
		final DElement node,
		final INodeEmitter nodeEmitter, 
		final IXmlStreamWriter writer)
	{
		if (node instanceof ISelfRender) {
			final ISelfRender selfRender = (ISelfRender)node;
//			CalTransaction calTx = null;
			boolean retcode = false;
			try {
//				if (DomWriterBean.getInstance().isCalLogEnabled()) {
//					calTx = CalTransactionFactory.create(this.getClass().getSimpleName());
//					calTx.setName(node.getClass().getName());
//				}
		        // Let the component render itself. If it returns false,
				// it wants us to render for it.
				retcode = selfRender.render(this, m_writer, nodeEmitter);
			} finally {
//				if (calTx != null){
//					calTx.setStatus("0");
//					calTx.completed();
//				}
			}
			if(!retcode) {
				final NodeList kids = node.getChildNodes();
				final int len = kids.getLength() ;
				for (int i = 0; i < len; i++) {
					final Node childNode = kids.item(i);
					nodeEmitter.genEvents(childNode, writer);
				}
			}
			return true;
		}
		return false;
	}

	// Run all instrumenters interested in SelfRender time processing.
	public boolean genDebugEvents(
		final DElement node, final INodeEmitter nodeEmitter, final IXmlStreamWriter writer)
	{
		if (node instanceof ISelfRender) {
			TraceCtx.ctx().getInstrumenter().runStartSelfRenderInstrumenters(node,m_writer);
			final boolean result = genEvents(node, nodeEmitter, writer);
			TraceCtx.ctx().getInstrumenter().runEndSelfRenderInstrumenters(node,m_writer);
			return result;
		}
		return false;
	}
	
	//
	// Satisfy IRawSaxHandler
	//
	/**
	 * Write the XML declaration at the beginning of the document.
	 *
	 * Pass the event on down the filter chain for further processing.
	 *
	 * @exception org.xml.sax.SAXException If there is an error
	 *            writing the XML declaration, or if a handler further down
	 *            the filter chain raises an exception.
	 * @see org.xml.sax.ContentHandler#startDocument
	 */
	public void startDocument(final DDocument document) {
		if(!m_writerCtx.isRenderXmlDoc()){
			return;
		}
		m_writer.writeStartDocument(document.getXmlEncoding(), document.getXmlVersion());							
	}
	
	/**
	 * Write a newline at the end of the document.
	 *
	 * Pass the event on down the filter chain for further processing.
	 *
	 * @exception org.xml.sax.SAXException If there is an error
	 *            writing the newline, or if a handler further down
	 *            the filter chain raises an exception.
	 * @see org.xml.sax.ContentHandler#endDocument
	 */
	public void endDocument(final DDocument document) {
		m_writer.writeEndDocument();
//			flush();
	}
	
	public void handleDocType(final DDocumentType doctype) {
//		final String dtdText = doctype.toString();
//		m_writer.writeDtd(dtdText);
		final String publicId = doctype.getPublicId();
		final String systemId = doctype.getSystemId();
		final String internalSubset = doctype.getInternalSubset();
		IIndenter indenter = m_writerCtx.getIndenter() ;
		
		boolean hasPublic = false;

		m_writer.writeRaw("<!DOCTYPE ");
//			m_writer.writeRaw(super.getNodeName());  // TODO: is this the right calls???
		m_writer.writeRaw(doctype.getNodeName()) ; // ???
		
		if (publicId != null) {
			m_writer.writeRaw(" PUBLIC \"");
			m_writer.writeRaw(publicId);
			m_writer.writeRaw("\"");
			hasPublic = true;
		}
		
		if (systemId != null) {
			if (!hasPublic) {
				m_writer.writeRaw(" SYSTEM");
			}
			m_writer.writeRaw(" \"");
			m_writer.writeRaw(systemId);
			m_writer.writeRaw("\"");
		}
			
		/*
		 * We now have 3 other pieces to worry about:
		 * 1. internal subset
		 * 2. Entity definitions
		 * 3. Notation definitions
		 * 
		 * If we have any of these we need to emit the '[' and ']'
		 */
		boolean extraOutput = false ;
		if ( ((internalSubset != null) && (!internalSubset.equals(""))
			|| doctype.getEntities().getLength() > 0
			|| doctype.getNotations().getLength() > 0))
		{
			extraOutput = true ;
		}
		
		if (!extraOutput) {
			m_writer.writeRaw(">");
			return ;
		}
			
		m_writer.writeRaw(" [");
		if ( (internalSubset != null) && (!internalSubset.equals("")) ) {
			m_writer.writeRaw(Z.NL);
			m_writer.writeRaw(doctype.getInternalSubset());
		}
	
		// Handle notations first since they may influence other generations
		// This is not scientific, just a best guess on processors...
		int len = doctype.getNotations().getLength() ;
		for(int i = 0; i < len; i++) {
			final Object obj = doctype.getNotations().item(i) ;	
			handleNotation((DNotation)obj) ;
		}		

		// Output the Entities
		len = doctype.getEntities().getLength() ;
		for(int i = 0; i < len; i++) {
			final Object obj = doctype.getEntities().item(i) ;	
//				if (obj instanceof DEntity) {
				handleEntity((DEntity)obj); //.write(out) ;
//				}
//				else {
//					((DEntityInternal)obj).write(out) ;
//				}
		}
		
		m_writer.writeRaw("]");
		m_writer.writeRaw(">");
		
		if (indenter != IIndenter.COMPACT) {
			m_writer.writeRaw("\n") ;
		}
	}
	
	/**
	 * Write a start tag.
	 *
	 * Pass the event on down the filter chain for further processing.
	 *
	 * @param uri The Namespace URI, or the empty string if none
	 *        is available.
	 * @param localName The element's local (unprefixed) name (required).
	 * @param qName The element's qualified (prefixed) name, or the
	 *        empty string is none is available.  This method will
	 *        use the qName as a template for generating a prefix
	 *        if necessary, but it is not guaranteed to use the
	 *        same qName.
	 * @param atts The element's attribute list (must not be null).
	 * @exception org.xml.sax.SAXException If there is an error
	 *            writing the start tag, or if a handler further down
	 *            the filter chain raises an exception.
	 * @see org.xml.sax.ContentHandler#startElement
	 */
	public void startElement(final DElement element) {
		// MrPperf - use char[] lookup rather than tagName
		// final String localName = element.getTagName();
		addLine(element, true, false);
		// final char[] tagNameAsChars;
		String tagName = element.getTagName() ;
//		if (m_writerCtx.isRenderNS()) {
//			// tagNameAsChars = element.getTagName().toCharArray();
//			tagName = element.getTagName();
//		} else {
//			if (element instanceof BaseHtmlElement) {
//				// final HtmlTypeEnum htmlType =
//				// ((BaseHtmlElement)element).htmlType();
//				// tagNameAsChars =
//				// HtmlTypeEnumAssociator.getNameCharArray(htmlType);
//				tagName = ((BaseHtmlElement) element).htmlType().getName();
//			} else {
//				// // yes this is a copy but that's ok since this is an 'HTML'
//				// writer
//				// // so we are favoring HTML element names as char[]
//				// // tagNameAsChars = element.getTagName().toCharArray();
//				tagName = element.getTagName();
//			}
//		}
		
// MrPperf - use char[] vs. tagName String
		m_writer.writeStartElement(tagName /*tagNameAsChars*/) ; // (localName);
		//has doc and element is doc root element
		if (element.getOwnerDocument()!=null 
			&& element.getOwnerDocument().getDocumentElement()== element)
		{
			if (element.getOwnerDocument().getBaseURI() != null){
				m_writer.writeAttribute(
					"xmlns:base", element.getOwnerDocument().getBaseURI());
			}
// MrPperf - todo: should use loop vs. iterator
			Set<DNamespace> nsDecls 
				= ((DDocument)element.getOwnerDocument()).getDsfNamespaceDeclarations() ;
			for (DNamespace nsObj : nsDecls) {
				writeNSAttr(nsObj.getPrefix(), nsObj.getNamespaceKey());
			}
		}
		writeAttributes(element);
	}
	
	public void handleNotation(final DNotation notation) {
		String entityNodeName = notation.getNodeName();
		String entityPublicId = notation.getPublicId() ;
		String entitySystemId = notation.getSystemId() ;
		boolean entityHasPublic = false ;
		
		IIndenter indenter = m_writerCtx.getIndenter() ;
		
		if (indenter != IIndenter.COMPACT) {
			m_writer.writeRaw("\n") ;
		}
		
		m_writer.writeRaw("<!NOTATION ");

		if (entityNodeName != null) {
			m_writer.writeRaw(entityNodeName);
		}
		
		if (entityPublicId != null) {
			m_writer.writeRaw(" PUBLIC \"");
			m_writer.writeRaw(entityPublicId);
			m_writer.writeRaw("\"");
			entityHasPublic = true;
		}
		
		if (entitySystemId != null) {
			if (!entityHasPublic) {
				m_writer.writeRaw(" SYSTEM");
			}
			m_writer.writeRaw(" \"");
			m_writer.writeRaw(entitySystemId);
			m_writer.writeRaw("\"");
		}
		
		m_writer.writeRaw(">");
	}
	
	public void handleProcessingInstruction(final DProcessingInstruction pi) {
		pi.render(null, m_writer, null) ;
	}
	
	public void handleEntity(final DEntity entity) {
		final String entityNodeName = entity.getNodeName();
		final String entityPublicId = entity.getPublicId() ;
		final String entitySystemId = entity.getSystemId() ;
		final String entityNotationName = entity.getNotationName() ;
		boolean entityHasPublic = false ;
		final IIndenter indenter = m_writerCtx.getIndenter() ;
		
		if (indenter != IIndenter.COMPACT) {
			m_writer.writeRaw("\n") ;
		}
		
		m_writer.writeRaw("<!ENTITY ");
		if (entity.isParameterEntity()) {
			m_writer.writeRaw("% ") ;
		}
		if (entityNodeName != null) {
			m_writer.writeRaw(entityNodeName);
		}
		
		if (entityPublicId != null) {
			m_writer.writeRaw(" PUBLIC \"");
			m_writer.writeRaw(entityPublicId);
			m_writer.writeRaw("\"");
			entityHasPublic = true;
		}
		
		if (entitySystemId != null) {
			if (!entityHasPublic) {
				m_writer.writeRaw(" SYSTEM");
			}
			m_writer.writeRaw(" \"");
			m_writer.writeRaw(entitySystemId);
			m_writer.writeRaw("\"");
		}
			
		// An entities value is the value of its children.  So we need to
		// process the children like a mini-render and use that result String
		// as the escaped value
		if (entity.hasChildNodes()) {				
			m_writer.writeRaw(" '") ;
			
			final NodeList children = entity.getChildNodes() ;
			final int len = children.getLength() ;
			// HtmlWriterHelper can use the m_writer type...
			final Writer tempWriter = new StringWriter(200) ;
			for(int i = 0; i < len; i++) {
				Node child = children.item(i) ;
				XmlWriterHelper.write(child, tempWriter, indenter);  //, m_schema) ;
			}
			m_writer.writeRaw(tempWriter.toString()) ;
			
			m_writer.writeRaw("'") ;
		}
		
		if (entityNotationName != null) {
			m_writer.writeRaw(" ") ;
			m_writer.writeRaw(entityNotationName);
		}
		
		m_writer.writeRaw(">");
	}
	
	public void handleEntityReference(final DEntityReference reference) {
		m_writer.writeRaw("&") ;
		final String data = reference.getNodeName();
		m_writer.writeRaw(data);
		m_writer.writeRaw(";") ;
	}
	
	public void handleCData(final DCDATASection cdata) {
		m_writer.writeCData(cdata.getData());
	}

	public void handleComment(final DComment comment) {
		m_writer.writeComment(comment.getData());
		try {
			m_writerCtx.getIndenter().indent(m_writerCtx.getWriter(), 0);
		} 
		catch (IOException e) {
			throw new DsfRuntimeException(e.getMessage(), e);
		}
	}

	public void handleRawString(final DRawString rawString) {
		//BUGDB00593573 (see bug for details).
		if (m_writerCtx.isAddLineNumber()){
			m_writer.writeRaw(m_lineLocator.lineNumberText(
				m_writerCtx.isTrimDText()
					? rawString.getRawString().getString().trim()
					: rawString.getRawString().getString()));				
		} 
		else {
			m_writer.writeRaw(
				m_writerCtx.isTrimDText()
					? rawString.getRawString().getString().trim()
					: rawString.getRawString().getString());
		}			
	}

	public void handleText(final DText text) {				
		//m_dWriterOpt.isTrimDText()				
		//BUGDB00593573 (see bug for details).
		//if the DText is empty, then this htmlWriter will skip it.
		//So the final html text, after many round trips of HtmlBuild/HtmlWrite
		//will not have many extra new lines.
		if (m_writerCtx.isAddLineNumber()){				
			m_writer.writeCharacters(m_lineLocator.lineNumberText(
				m_writerCtx.isTrimDText()
					?text.getData().trim()
					:text.getData()));				
		} 
		else {
			m_writer.writeCharacters(
				m_writerCtx.isTrimDText()? text.getData().trim() :text.getData());
		}			
	}
	
	/**
	 * Flush the output.
	 *
	 * <p>This method flushes the output stream.  It is especially useful
	 * when you need to make certain that the entire document has
	 * been written to output but do not want to close the output
	 * stream.</p>
	 *
	 * <p>This method is invoked automatically by the
	 * {@link #endDocument endDocument} method after writing a
	 * document.</p>
	 *
	 * @see #reset
	 */
	public void flush() throws XmlStreamException {
		m_writer.flush();
	}
	
	//think about promoting to interface
	protected void addLine(DNode n, boolean bStart, boolean bHasEndTag){
		if(! m_writerCtx.isAddLineNumber()) return ;
		
		if(bStart){
			m_elementStartLineNum = m_lineLocator.getLineNumber();
			n.setUserData(START_LINE, m_elementStartLineNum, null);
		}
		else {
			if (bHasEndTag){
				n.setUserData(END_LINE, m_lineLocator.getLineNumber(), null);
			} 
			else {
				//No end tag case. 
				//for nodes don't have closeTag, node.getUserData(END_TAG_LINE) 
				//should be the same value as the open tag start line.
				//suggested by QE and aggreed by application team
				n.setUserData(END_LINE, m_elementStartLineNum, null);
			}				
		}			
	}
	
//	protected boolean shouldEndTag(final IElementInfo elementInfo) {		
//		return
//			elementInfo == null 
//			|| elementInfo.requireEndTag() 
//			|| elementInfo.getContentModel() != IContentModel.EMPTY ;
//	}
	
	/**
	 * Write out an attribute list, escaping values.
	 *
	 * The names will have prefixes added to them.
	 *
	 * @param atts The attribute list to write.
	 * @exception org.xml.SAXException If there is an error writing
	 *            the attribute list, this method will throw an
	 *            IOException wrapped in a SAXException.
	 */
	protected void writeAttributes(final DElement element)
		throws XmlStreamException
	{
		if (element == null) {
			return;
		}
		
		//if has namespaceUri
		if (element.getDsfNamespace() != null){
			writeNSAttr(element);
		}

// MrPperf - don't create attributes if possible
		if (element.hasAttributes() == false) {
			return ;
		}
		
// MrPperf - no easy way to optimize this 
		final IAttributeMap attrs = element.getDsfAttributes();
		for (final DAttr attr:attrs) {
			m_writer.writeAttribute(attr.getNodeName(), attr.getValue());
		}
	}
	
	protected void writeNSAttr(final DElement element) {
		if (m_writerCtx == null || !m_writerCtx.isRenderNS())	{
			return;
		}
		//doc namespaceObjects	
		localtmpDoc = (DDocument)element.getOwnerDocument();
		if (localtmpDoc != null 
			&& localtmpDoc.getDsfNamespaceDeclarations().contains(
				element.getDsfNamespace()))
		{
			return;
		}
		String prefix = element.getPrefix();
		
		//optimization code to avoid generate redundant data
		if (m_writerCtx.isOptimization() 
			&& (localtmpDoc ==null 
			|| (localtmpDoc !=null 
			&& localtmpDoc.getDocumentElement() != element)))
		{
			final DNamespace ns = element.dsfLookupNamespaceURI(prefix);			
			if (prefix != null && element.isDsfDefaultNamespace(ns)) {
				return;
			}	
			//no prefix case, condition for none root element
			if (ns != null 
				&& ns == element.getDsfBaseURI() 
				&& element.getParentNode() != null) 
			{
				return;			
			}			
		}	
		//DsfQ109_NS
		if (HtmlNSHelper.isPrefixOnly(element.getDsfNamespace())){
			return;
		}
		
		writeNSAttr(prefix,  element.getNamespaceURI());
	}
	
	protected void writeNSAttr(String prefix, String uri){		
		if (prefix == null || prefix.equals("xmlns")){
			m_writer.writeAttribute("xmlns", uri);
		} 
		else if (prefix.equals("xml")){
			m_writer.writeAttribute("xml", uri);
		} 
		else {			
			m_writer.writeAttribute("xmlns:"+prefix, uri);
		}
	}
	
	//
	// Helper class(es)
	//
//	static class DomAssociator extends Associator {
//		protected static int getNameAsCharIndex(final DAttr attr) {
//			return Associator.getNameAsCharIndex(attr) ;
//		}
//	}	
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom.util;

import java.util.Set;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.dom.DDocument;
import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.dom.support.DNamespace;
import org.ebayopensource.dsf.dom.util.BaseDomWriter;
import org.ebayopensource.dsf.dom.util.ILineLocateIndenter;
import org.ebayopensource.dsf.html.HtmlWriterCtx;
import org.ebayopensource.dsf.html.dom.BaseHtmlElement;
import org.ebayopensource.dsf.html.schemas.IContentModel;
import org.ebayopensource.dsf.html.schemas.IElementInfo;
import org.ebayopensource.dsf.html.schemas.ISchema;

public class HtmlWriter extends BaseDomWriter  {
//	/**
//	 * Dsf defined key to associate the  
//	 * element start tag line number in DOMUserData.
//	 */
//	public static final String START_LINE = "dsfSL";
//	/**
//	 * Dsf defined key to associate the 
//	 * element end tag line number in DOMUserData.
//	 */
//	public static final String END_LINE = "dsfEL";
//	private static char[][] s_attribute_names_as_chars ; //= createAttrNameToChars() ;
	
	private final IHtmlStreamWriter m_writer;
	private  ISchema m_schema;
	private final HtmlWriterCtx m_writerCtx;

//	private DDocument localtmpDoc; //local variable
//	private final ILineLocateIndenter m_lineLocator;  //line locator
//	private String m_elementStartLineNum; //tracking the current element start line number
	
	/**
	 * Create a new XML writer.
	 *
	 * <p>Write to the writer provided.</p>
	 *
	 * @param writer The output destination, or null to use standard
	 *        output.
	 */
	public HtmlWriter(final IHtmlStreamWriter writer) {
		//this(Html401Transitional.getInstance(), writer);
		this(writer, new HtmlWriterCtx());
	}
	public HtmlWriter(final ISchema schema, final IHtmlStreamWriter writer) {
		this(writer,  new HtmlWriterCtx(schema));
	}
	
	public HtmlWriter(final IHtmlStreamWriter writer, HtmlWriterCtx ctx) {
		super(writer, ctx) ;
		
		m_writer = writer;
		m_schema = ctx.getSchema();
		m_writerCtx = ctx;
		if (ctx.getIndenter() instanceof ILineLocateIndenter){
			m_lineLocator = (ILineLocateIndenter)ctx.getIndenter();
		} else {
			m_lineLocator = null;
		}
		if (ctx.isAddLineNumber() && m_lineLocator==null){
			throw new DsfRuntimeException("lineLocation is null.");
		}
	}


	////////////////////////////////////////////////////////////////////
	// Public methods.
	////////////////////////////////////////////////////////////////////
	public HtmlWriter setSchema(final ISchema schema) {
		m_schema = schema ;
		return this ;
	}
	public ISchema getSchema() {		
		return m_schema;
	}
	/**
	 * Reset the writer.
	 *
	 * <p>This method is especially useful if the writer throws an
	 * exception before it is finished, and you want to reuse the
	 * writer for a new document.  It is usually a good idea to
	 * invoke {@link #flush flush} before resetting the writer,
	 * to make sure that no output is lost.</p>
	 *
	 * <p>This method is invoked automatically by the
	 * {@link #startDocument startDocument} method before writing
	 * a new document.</p>
	 *
	 * <p><strong>Note:</strong> this method will <em>not</em>
	 * clear the prefix or URI information in the writer or
	 * the selected output writer.</p>
	 *
	 * @see #flush
	 */
//	public void reset() {
//		m_elementLevel = 0;
//	}

//	/**
//	 * Flush the output.
//	 *
//	 * <p>This method flushes the output stream.  It is especially useful
//	 * when you need to make certain that the entire document has
//	 * been written to output but do not want to close the output
//	 * stream.</p>
//	 *
//	 * <p>This method is invoked automatically by the
//	 * {@link #endDocument endDocument} method after writing a
//	 * document.</p>
//	 *
//	 * @see #reset
//	 */
//	public void flush() throws XmlStreamException {
//		m_writer.flush();
//	}

	/**
	 * Set a new output destination for the document.
	 *
	 * @param writer The output destination, or null to use
	 *        standard output.
	 * @return The current output writer.
	 * @see #flush
	 */
//	public void setOutput(Writer writer) {
//		if (writer == null) {
//			m_output = new OutputStreamWriter(System.out);
//		} else {
//			m_output = writer;
//		}
//	}

//	/**
//	 * Write the XML declaration at the beginning of the document.
//	 *
//	 * Pass the event on down the filter chain for further processing.
//	 *
//	 * @exception org.xml.sax.SAXException If there is an error
//	 *            writing the XML declaration, or if a handler further down
//	 *            the filter chain raises an exception.
//	 * @see org.xml.sax.ContentHandler#startDocument
//	 */
//	public void startDocument(final DDocument document) {
//		if(!m_dWriterOpt.isRenderXmlDoc()){
//			return;
//		}
//
//
//		try {
//			m_writer.writeStartDocument(document.getXmlEncoding(), document.getXmlVersion());					
//		} catch (XmlStreamException e) {
//			throw new DsfRuntimeException(e.getMessage(), e);
//		}		
//	}

//	/**
//	 * Write a newline at the end of the document.
//	 *
//	 * Pass the event on down the filter chain for further processing.
//	 *
//	 * @exception org.xml.sax.SAXException If there is an error
//	 *            writing the newline, or if a handler further down
//	 *            the filter chain raises an exception.
//	 * @see org.xml.sax.ContentHandler#endDocument
//	 */
//	public void endDocument(final DDocument document) {
//		try {
//			m_writer.writeEndDocument();
////			flush();
//		} 
//		catch (XmlStreamException e) {
//			throw new DsfRuntimeException(e.getMessage(), e);
//		}
//	}
	
//	public void handleDocType(final DDocumentType doctype) {
////		final String dtdText = doctype.toString();
////		m_writer.writeDtd(dtdText);
//		final String publicId = doctype.getPublicId();
//		final String systemId = doctype.getSystemId();
//		final String internalSubset = doctype.getInternalSubset();
//		IIndenter indenter = m_dWriterOpt.getIndenter() ;
//		
//		boolean hasPublic = false;
//
//		try {
//			m_writer.writeRaw("<!DOCTYPE ");
////			m_writer.writeRaw(super.getNodeName());  // TODO: is this the right calls???
//			m_writer.writeRaw(doctype.getNodeName()) ; // ???
//			
//			if (publicId != null) {
//				m_writer.writeRaw(" PUBLIC \"");
//				m_writer.writeRaw(publicId);
//				m_writer.writeRaw("\"");
//				hasPublic = true;
//			}
//			
//			if (systemId != null) {
//				if (!hasPublic) {
//					m_writer.writeRaw(" SYSTEM");
//				}
//				m_writer.writeRaw(" \"");
//				m_writer.writeRaw(systemId);
//				m_writer.writeRaw("\"");
//			}
//			
//			/*
//			 * We now have 3 other pieces to worry about:
//			 * 1. internal subset
//			 * 2. Entity definitions
//			 * 3. Notation definitions
//			 * 
//			 * If we have any of these we need to emit the '[' and ']'
//			 */
//			boolean extraOutput = false ;
//			if ( ((internalSubset != null) && (!internalSubset.equals(""))
//				|| doctype.getEntities().getLength() > 0
//				|| doctype.getNotations().getLength() > 0))
//			{
//				extraOutput = true ;
//			}
//			
//			if (!extraOutput) {
//				m_writer.writeRaw(">");
//				return ;
//			}
//			
//			m_writer.writeRaw(" [");
//			if ( (internalSubset != null) && (!internalSubset.equals("")) ) {
//				m_writer.writeRaw(Z.NL);
//				m_writer.writeRaw(doctype.getInternalSubset());
//			}
//		
//			// Handle notations first since they may influence other generations
//			// This is not scientific, just a best guess on processors...
//			int len = doctype.getNotations().getLength() ;
//			for(int i = 0; i < len; i++) {
//				final Object obj = doctype.getNotations().item(i) ;	
//				handleNotation((DNotation)obj) ;
//			}		
//	
//			// Output the Entities
//			len = doctype.getEntities().getLength() ;
//			for(int i = 0; i < len; i++) {
//				final Object obj = doctype.getEntities().item(i) ;	
////				if (obj instanceof DEntity) {
//					handleEntity((DEntity)obj); //.write(out) ;
////				}
////				else {
////					((DEntityInternal)obj).write(out) ;
////				}
//			}
//			
//			m_writer.writeRaw("]");
//			m_writer.writeRaw(">");
//		}
//		catch (XmlStreamException e) {
//			throw new DsfRuntimeException(e.getMessage(), e);
//		}
//		
//		if (indenter != IIndenter.COMPACT) {
//			m_writer.writeRaw("\n") ;
//		}
//	}
//	
//	public void handleNotation(final DNotation notation) {
//		String entityNodeName = notation.getNodeName();
//		String entityPublicId = notation.getPublicId() ;
//		String entitySystemId = notation.getSystemId() ;
//		boolean entityHasPublic = false ;
//		
//		IIndenter indenter = m_dWriterOpt.getIndenter() ;
//		
//		if (indenter != IIndenter.COMPACT) {
//			m_writer.writeRaw("\n") ;
//		}
//		
//		try {
//			m_writer.writeRaw("<!NOTATION ");
//	
//			if (entityNodeName != null) {
//				m_writer.writeRaw(entityNodeName);
//			}
//			
//			if (entityPublicId != null) {
//				m_writer.writeRaw(" PUBLIC \"");
//				m_writer.writeRaw(entityPublicId);
//				m_writer.writeRaw("\"");
//				entityHasPublic = true;
//			}
//			
//			if (entitySystemId != null) {
//				if (!entityHasPublic) {
//					m_writer.writeRaw(" SYSTEM");
//				}
//				m_writer.writeRaw(" \"");
//				m_writer.writeRaw(entitySystemId);
//				m_writer.writeRaw("\"");
//			}
//			
//			m_writer.writeRaw(">");
//		} 
//		catch (XmlStreamException e) {
//			throw new DsfRuntimeException(e.getMessage(), e);
//		}
//	}
	
//	public void handleEntity(final DEntity entity) {
//		String entityNodeName = entity.getNodeName();
//		String entityPublicId = entity.getPublicId() ;
//		String entitySystemId = entity.getSystemId() ;
//		String entityNotationName = entity.getNotationName() ;
//		boolean entityHasPublic = false ;
//		IIndenter indenter = m_dWriterOpt.getIndenter() ;
//		
//		if (indenter != IIndenter.COMPACT) {
//			m_writer.writeRaw("\n") ;
//		}
//		
//		try {
//			m_writer.writeRaw("<!ENTITY ");
//			if (entity.isParameterEntity()) {
//				m_writer.writeRaw("% ") ;
//			}
//			if (entityNodeName != null) {
//				m_writer.writeRaw(entityNodeName);
//			}
//			
//			if (entityPublicId != null) {
//				m_writer.writeRaw(" PUBLIC \"");
//				m_writer.writeRaw(entityPublicId);
//				m_writer.writeRaw("\"");
//				entityHasPublic = true;
//			}
//			
//			if (entitySystemId != null) {
//				if (!entityHasPublic) {
//					m_writer.writeRaw(" SYSTEM");
//				}
//				m_writer.writeRaw(" \"");
//				m_writer.writeRaw(entitySystemId);
//				m_writer.writeRaw("\"");
//			}
//			
//			// An entities value is the value of its children.  So we need to
//			// process the children like a mini-render and use that result String
//			// as the escaped value
//			if (entity.hasChildNodes()) {				
//				m_writer.writeRaw(" '") ;
//				
//				NodeList children = entity.getChildNodes() ;
//				int len = children.getLength() ;
//				// HtmlWriterHelper can use the m_writer type...
//				Writer tempWriter = new StringWriter(200) ;
//				for(int i = 0; i < len; i++) {
//					Node child = children.item(i) ;
//					HtmlWriterHelper.write(child, tempWriter, indenter, m_schema) ;
//				}
//				m_writer.writeRaw(tempWriter.toString()) ;
//				
//				m_writer.writeRaw("'") ;
//			}
//			
//			if (entityNotationName != null) {
//				m_writer.writeRaw(" ") ;
//				m_writer.writeRaw(entityNotationName);
//			}
//			
//			m_writer.writeRaw(">");
//		}
//		catch (XmlStreamException e) {
//			throw new DsfRuntimeException(e.getMessage(), e);
//		}
//	}
	
//	public boolean genEvents(
//		final DElement node,
//		final INodeEmitter nodeEmitter, 
//		final IXmlStreamWriter writer)
//	{
//		if (node instanceof ISelfRender) {
//			final ISelfRender selfRender = (ISelfRender)node;
//			// Let the component render itself. If it returns false,
//			// it wants us to render for it.
//				if(!selfRender.render(this, m_writer, nodeEmitter)) {
//					final NodeList kids = node.getChildNodes();
//					final int len = kids.getLength() ;
//					for (int i = 0; i < len; i++) {
//						final Node childNode = kids.item(i);
//						nodeEmitter.genEvents(childNode, writer);
//					}
//				}
//				return true;
//		}
//		return false;
//	}
	
//	// Run all instrumenters interested in SelfRender time processing.
//	public boolean genDebugEvents(
//		final DElement node, final INodeEmitter nodeEmitter, final IXmlStreamWriter writer)
//	{
//		if (node instanceof ISelfRender) {
//			TraceCtx.ctx().getInstrumenter().runStartSelfRenderInstrumenters(node,m_writer);
//			boolean result = genEvents(node, nodeEmitter, writer);
//			TraceCtx.ctx().getInstrumenter().runEndSelfRenderInstrumenters(node,m_writer);
//			return result;
//		}
//		return false;
//	}

//	/**
//	 * Write a start tag.
//	 *
//	 * Pass the event on down the filter chain for further processing.
//	 *
//	 * @param uri The Namespace URI, or the empty string if none
//	 *        is available.
//	 * @param localName The element's local (unprefixed) name (required).
//	 * @param qName The element's qualified (prefixed) name, or the
//	 *        empty string is none is available.  This method will
//	 *        use the qName as a template for generating a prefix
//	 *        if necessary, but it is not guaranteed to use the
//	 *        same qName.
//	 * @param atts The element's attribute list (must not be null).
//	 * @exception org.xml.sax.SAXException If there is an error
//	 *            writing the start tag, or if a handler further down
//	 *            the filter chain raises an exception.
//	 * @see org.xml.sax.ContentHandler#startElement
//	 */
//	public void startElement(final DElement element) {
//// MrPperf - use char[] lookup rather than tagName
////		final String localName = element.getTagName();
//		addLine(element, true, false);
//		final char[] tagNameAsChars;
//		if(m_dWriterOpt.isRenderNS()){	
//			tagNameAsChars = element.getTagName().toCharArray();
//		} else{		
//			if (element instanceof BaseHtmlElement) {
//				final HtmlTypeEnum htmlType = ((BaseHtmlElement)element).htmlType();
//				tagNameAsChars = HtmlTypeEnumAssociator.getNameCharArray(htmlType);
//			}
//			else {
//				// yes this is a copy but that's ok since this is an 'HTML' writer
//				// so we are favoring HTML element names as char[]
//				tagNameAsChars = element.getTagName().toCharArray();
//			}			
//		}
//		try {
//// MrPperf - use char[] vs. tagName String
//			m_writer.writeStartElement(tagNameAsChars) ; // (localName);
//			//has doc and element is doc root element
//			if (element.getOwnerDocument()!=null && element.getOwnerDocument().getDocumentElement()== element){
//				if (element.getOwnerDocument().getBaseURI() != null){
//					m_writer.writeAttribute("xmlns:base", element.getOwnerDocument().getBaseURI());
//				}
//				for (DNamespace nsObj : ((DDocument)element.getOwnerDocument()).getDsfNamespaceDeclarations()){
//					writeNSAttr(nsObj.getPrefix(), nsObj.getNamespaceKey());
//				}
//			}
//			writeAttributes(element);
//		} 
//		catch (XmlStreamException e) {
//			throw new DsfRuntimeException(e.getMessage(), e);
//		}
//	}
//	
//	//think about promoting to interface
//	private void addLine(DNode n, boolean bStart, boolean bHasEndTag){
//		if(! m_dWriterOpt.isAddLineNumber()) return ;
//		
//		if(bStart){
//			m_elementStartLineNum = m_lineLocator.getLineNumber();
//			n.setUserData(START_LINE, m_elementStartLineNum, null);
//		}
//		else {
//			if (bHasEndTag){
//				n.setUserData(END_LINE, m_lineLocator.getLineNumber(), null);
//			} 
//			else {
//				//No end tag case. 
//				//for nodes don't have closeTag, node.getUserData(END_TAG_LINE) 
//				//should be the same value as the open tag start line.
//				//suggested by QE and aggreed by application team
//				n.setUserData(END_LINE, m_elementStartLineNum, null);
//			}				
//		}			
//	}

	public void startElement(final DElement element) {
		// MrPperf - use char[] lookup rather than tagName
		// final String localName = element.getTagName();
		addLine(element, true, false);
		// final char[] tagNameAsChars;
		String tagName; // = element.getTagName() ;
		if (m_writerCtx.isRenderNS()) {
			// tagNameAsChars = element.getTagName().toCharArray();
			tagName = element.getTagName();
		} else {
			if (element instanceof BaseHtmlElement) {
				// final HtmlTypeEnum htmlType =
				// ((BaseHtmlElement)element).htmlType();
				// tagNameAsChars =
				// HtmlTypeEnumAssociator.getNameCharArray(htmlType);
				tagName = ((BaseHtmlElement) element).htmlType().getName();
			} else {
				// // yes this is a copy but that's ok since this is an 'HTML'
				// writer
				// // so we are favoring HTML element names as char[]
				// // tagNameAsChars = element.getTagName().toCharArray();
				tagName = element.getTagName();
			}
		}
		
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
	
	/**
	 * Write an end tag.
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
	 * @exception org.xml.sax.SAXException If there is an error
	 *            writing the end tag, or if a handler further down
	 *            the filter chain raises an exception.
	 * @see org.xml.sax.ContentHandler#endElement
	 */
// TODO: Big assumption that a DElement "is a" html element which it is not
// thus the enum lookup based on tagname can easily fail and NPE
	public void endElement(final DElement element) {
		final IElementInfo elementInfo = getElementInfo(element) ;
		
		if (shouldEndTag(elementInfo)) {
			addLine(element, false, true);
			try {
				m_writer.writeEndElement();
			} 
			catch (Exception e) {
				throw new DsfRuntimeException(
					"error writing end tag for '" + element.getTagName() + "'", e);
			}
		} 
		else {
			addLine(element, false, false);
			m_writer.ignoreCurrentEndTag();
		}
	}
	
	public IElementInfo getElementInfo(final DElement element) {
		if (m_schema == null) {
			return null;
		}

// MrPperf -- do faster single static array lookup for BaseHtmlElements elementInfo
// This will save two hashmap lookups for each BaseHtmlElement type	
		if (element instanceof BaseHtmlElement) {
			return m_schema.getElementInfo(((BaseHtmlElement)element).htmlType());
		}

		return null ;
//		final String localName = element.getTagName();
//		final HtmlTypeEnum htmlType =
//			HtmlTypeEnum.getEnum(localName);
//		final IElementInfo answer 
//			= m_schema.getElementInfo(htmlType);
//		return answer ;
	}
	
	private boolean shouldEndTag(final IElementInfo elementInfo) {		
		return
			elementInfo == null 
			|| elementInfo.requireEndTag() 
			|| elementInfo.getContentModel() != IContentModel.EMPTY ;
	}

	////////////////////////////////////////////////////////////////////
	// Internal methods.
	////////////////////////////////////////////////////////////////////
//	/**
//	 * Write out an attribute list, escaping values.
//	 *
//	 * The names will have prefixes added to them.
//	 *
//	 * @param atts The attribute list to write.
//	 * @exception org.xml.SAXException If there is an error writing
//	 *            the attribute list, this method will throw an
//	 *            IOException wrapped in a SAXException.
//	 */
//	private void writeAttributes(final DElement element)
//		throws XmlStreamException
//	{
//		if (element == null) {
//			return;
//		}
//		
//		//if has namespaceUri
//		if (element.getDsfNamespace() != null){
//			writeNSAttr(element);
//		}
//
//// MrPperf - don't create attributes if possible
//		if (element.hasAttributes() == false) {
//			return ;
//		}
//		
//		final IAttributeMap attrs = element.getDsfAttributes();
//		for (final DAttr attr:attrs) {
//			int attrNamesAsCharIndex = DomAssociator.getNameAsCharIndex(attr) ;
//			if (attrNamesAsCharIndex != -1) {
//				final char[] chars 
//					= getAttributeNamesAsChars()[attrNamesAsCharIndex];
//				m_writer.writeAttribute(chars, attr.getValue());
//			}
//			else {
//				m_writer.writeAttribute(attr.getNodeName(), attr.getValue());
//			}
//		}
//	}
	
//	protected void writeNSAttr(final DElement element) {
//		if (m_dWriterOpt == null || !m_dWriterOpt.isRenderNS())				 
//		{
//			return;
//		}
//		//doc namespaceObjects	
//		localtmpDoc = (DDocument)element.getOwnerDocument();
//		if (localtmpDoc != null 
//			&& localtmpDoc.getDsfNamespaceDeclarations().contains(
//				element.getDsfNamespace()))
//		{
//			return;
//		}
//		String prefix = element.getPrefix();
//		
//		//optimization code to avoid generate redundant data
//		if (m_dWriterOpt.isOptimization() 
//			&& (localtmpDoc ==null 
//			|| (localtmpDoc !=null 
//			&& localtmpDoc.getDocumentElement() != element)))
//		{
//			DNamespace ns = element.dsfLookupNamespaceURI(prefix);			
//			if (prefix != null && element.isDsfDefaultNamespace(ns)) {
//				return;
//			}	
//			//no prefix case, condition for none root element
//			if (ns != null 
//				&& ns == element.getDsfBaseURI() 
//				&& element.getParentNode() != null) 
//			{
//				return;			
//			}			
//		}	
//		//DsfQ109_NS
//		if (HtmlNSHelper.isPrefixOnly(element.getDsfNamespace())){
//			return;
//		}
//		
//		writeNSAttr(prefix,  element.getNamespaceURI());
//	}
	
//	private void writeNSAttr(String prefix, String uri){		
//		if (prefix == null || prefix.equals("xmlns")){
//			m_writer.writeAttribute("xmlns", uri);
//		} 
//		else if (prefix.equals("xml")){
//			m_writer.writeAttribute("xml", uri);
//		} 
//		else {			
//			m_writer.writeAttribute("xmlns:"+prefix, uri);
//		}
//	}

	////////////////////////////////////////////////////////////////////
	// Internal state.
	////////////////////////////////////////////////////////////////////
//	public void handleEntityReference(final DEntityReference reference) {
//		try {
//			m_writer.writeRaw("&") ;
//			String data = reference.getNodeName();
//			m_writer.writeRaw(data);
//			m_writer.writeRaw(";") ;
//		} 
//		catch (XmlStreamException e) {
//			throw new DsfRuntimeException(e.getMessage(), e);
//		}
//	}
//	
//	public void handleCData(final DCDATASection cdata) {
//		try {
//			m_writer.writeCData(cdata.getData());
//		} 
//		catch (XmlStreamException e) {
//			throw new DsfRuntimeException(e.getMessage(), e);
//		}
//	}
//
//	public void handleComment(final DComment comment) {
//		try {
//			m_writer.writeComment(comment.getData());
//		} 
//		catch (XmlStreamException e) {
//			throw new DsfRuntimeException(e.getMessage(), e);
//		}
//		try {
//			m_dWriterOpt.getIndenter().indent(m_dWriterOpt.getWriter(), 0);
//		} catch (IOException e) {
//			throw new DsfRuntimeException(e.getMessage(), e);
//		}
//	}
//
//	public void handleRawString(final DRawString rawString) {
//		try {
//			//BUGDB00593573 (see bug for details).
//			if (m_dWriterOpt.isAddLineNumber()){
//				m_writer.writeRaw(m_lineLocator.lineNumberText(
//					m_dWriterOpt.isTrimDText()
//						? rawString.getRawString().getString().trim()
//						: rawString.getRawString().getString()));				
//			} else {
//				m_writer.writeRaw(
//					m_dWriterOpt.isTrimDText()
//						? rawString.getRawString().getString().trim()
//						: rawString.getRawString().getString());
//			}			
//		} 
//		catch (XmlStreamException e) {
//			throw new DsfRuntimeException(e.getMessage(), e);
//		}
//	}
//
//	public void handleText(final DText text) {	
//		try {			
//			//m_dWriterOpt.isTrimDText()				
//			//BUGDB00593573 (see bug for details).
//			//if the DText is empty, then this htmlWriter will skip it.
//			//So the final html text, after many round trips of HtmlBuild/HtmlWrite
//			//will not have many extra new lines.
//			if (m_dWriterOpt.isAddLineNumber()){				
//				m_writer.writeCharacters(m_lineLocator.lineNumberText(
//						m_dWriterOpt.isTrimDText()?text.getData().trim()
//							:text.getData()));				
//			} else {
//				m_writer.writeCharacters(
//						m_dWriterOpt.isTrimDText()?text.getData().trim()
//							:text.getData());
//			}			
//		} catch (XmlStreamException e) {
//			throw new DsfRuntimeException(e.getMessage(), e);
//		}
//	}

	//
	// Private
	//	
	
//	// We need to do a lazy mechanism here else we get into a mess with static
//	// initialization order problems with EHtmlAttr, sigh...
//	private static char[][] getAttributeNamesAsChars() {
//		if (s_attribute_names_as_chars == null) {
//			s_attribute_names_as_chars = createAttrNameToChars() ;
//		}
//		return s_attribute_names_as_chars ;
//	}
//	
//	private static char[][] createAttrNameToChars() {
//		final char[][] answer = new char[EHtmlAttr.LAST.ordinal() + 1][] ;
//		for(EHtmlAttr attr : EnumSet.range(EHtmlAttr.FIRST, EHtmlAttr.LAST)) {
//			answer[attr.ordinal()] = attr.getAsChars();
//		}
//		return answer ;
//	}
	
//	//
//	// Helper class(es)
//	//
//	static class DomAssociator extends Associator {
//		protected static int getNameAsCharIndex(final DAttr attr) {
//			return Associator.getNameAsCharIndex(attr) ;
//		}
//	}	


}

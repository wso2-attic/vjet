/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom.util;

import java.io.StringWriter;
import java.io.Writer;

import org.ebayopensource.dsf.common.Z;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.xml.IIndenter;
import org.ebayopensource.dsf.common.xml.XmlStreamWriterOptions;


/**
 * BaseDomWriter helper class, holds information used by various writers to specify
 * and control how they work.
 * 
 */

public class DomWriterCtx{
	private final static int STRING_WRITER_INIT_SIZE = 128;
	
	private int m_flags;
	private final static short FLAG_OPTIMIZATION = 0x1<<0;  //avoid emit redundant data
	private final static short FLAG_ADD_LINE_NUM = 0x1<<1;
	private final static short FLAG_RENDER_XML_DOC = 0x1<<2;// render doc section for xml, when schema is placed, remove this
	private final static short FLAG_RENDER_NS = 0x1<<3;// render namespace attribute, when schema is placed, remove this
	private final static short FLAG_TRIM_DTEXT = 0x1<<4;// trim DText during render
	
//	private ISchema m_schema;
	private Writer m_writer;
	private IIndenter m_indenter;

	//
	// Constructor(s)
	//
	/**
	 * Default XmlStreamWriterOption: 
	 * default RopeBufferWriter, default COMPACT indenter, and  escape=false. 
	 * Default 
	 */
	public DomWriterCtx(){
		this( 
			createWriter(),
			IIndenter.COMPACT) ;
//			getDefaultSchema());		
	}
	
	/**
	 * User specified schema, used by DWriterF to handle endElement event
	 * Default XmlStreamWriterOption: 
	 * @param schema
	 */
//	public DomWriterCtx(final ISchema schema){
//		this(
//			createWriter(),
//			IIndenter.COMPACT,
//			schema);
//	}
		
	/**
	 * 
	 * User specified XmlStreamWriterOption and notEscapeTags
	 * @param type 
	 * @param streamOption  
	 * @param notEscapeTags
	 * @exception throw DsfRuntimeException if type or streamOption is null
	 */
	public DomWriterCtx(final 
		Writer writer,
		IIndenter indenter)
//		final ISchema schema)
	{		
		if (writer == null ){
			throw new DsfRuntimeException("Writer is null");
		}	
		if (indenter == null ){
			throw new DsfRuntimeException("indenter is null");
		}	
		
		m_writer = writer;
		m_indenter = indenter;
//		m_schema = schema;				
	}	
	
	//
	// Static
	//
	/**
	 * 
	 * @param type
	 * @return
	 * @exception throw DsfRuntimeException if type is null
	 */
	static XmlStreamWriterOptions createDefaultStreamOpt(){
		return new XmlStreamWriterOptions(
			createWriter(), 
			IIndenter.COMPACT,
			true);
	}
		
	public static Writer createWriter(){
		return new StringWriter(STRING_WRITER_INIT_SIZE);
	}
	
//	public static ISchema getDefaultSchema(){			
//		return Html401Transitional.getInstance();				
//	}	
	
	//
	// API
	//
//	public DomWriterCtx setSchema(final ISchema schema) {
//		m_schema = schema ;
//		return this ;
//	}
//	public ISchema getSchema() {		
//		return m_schema;
//	}
		
	public char[][] getNotEscapeTags() {
		return null ;
	}
	
	/**
	 * return true, htmlWriter will optimize rendering of 
	 * namespace attributes. 
	 * @return
	 */
	public boolean isOptimization() {
		return (m_flags&FLAG_OPTIMIZATION) !=0;
	}	
	
	/**
	 * set true, optimize namespace during rendering, 
	 * to avoid redundant namespace attribute in DOM tree.	 * 
	 * @param value
	 * @return HtmlWriterOption
	 */
	public DomWriterCtx setOptimization(final boolean value){
		m_flags =  (value ? m_flags | FLAG_OPTIMIZATION : m_flags & ~FLAG_OPTIMIZATION);
		return this;
	}	
	
	/**
	 * Returns <code>true</code> if the addLineNumber option is set,
	 * <code>false</code> otherwise.
	 * 
	 * @see #setAddLineNumber(boolean)
	 * 
	 * @return boolean option value for addLineNumber
	 */
	public boolean isAddLineNumber() {
		return (m_flags&FLAG_ADD_LINE_NUM) !=0;
	}	
	
	/**
	 * Set or reset option value for the addLineNumber option. If set to
	 * <code>true</code>, user data with line number information is added to
	 * the nodes during the HTML parsing operation.
	 * <p>
	 * The line number information is of two types and is added as user data to
	 * the nodes during the parsing operation
	 * <ul>
	 * <li> starting line number - the line number in the source HTML fragment
	 * where the start tag for this element was found. The key "startTagLine"
	 * can be used to retrieve this user data
	 * <li> ending line number - the line number in the source HTML fragment
	 * where the eng tag for this element was found. The key "endTagLine" can be
	 * used to retrieve this user data
	 * </ul>
	 * <p>
	 * The line number assigned to a tag is the line number of the line it would
	 * appear on if the HTML fragment was printed using either the Pretty
	 * Indenter or the LineBreak Indenter. The line numbers start at
	 * <code>1</code>
	 * <p>
	 * The start and end line numbers for a tag would be the same regardless of
	 * whether the LineBreak Identer or the Pretty indenter is used
	 * 
	 * @see IIndenter.LineBreak
	 * @see IIndenter.Pretty
	 * 
	 * @param addLineNumber
	 *            boolean value for this addLineNumber option
	 * 
	 * @return instance of this options object
	 */
	public DomWriterCtx setAddLineNumber(final boolean value){
		m_flags =  (value ? m_flags | FLAG_ADD_LINE_NUM : m_flags & ~FLAG_ADD_LINE_NUM);
		return this;
	}	
	
	public DomWriterCtx setWriter(final Writer writer) {
		m_writer = writer ;
		return this ;
	}
	public Writer getWriter(){
		return m_writer;
	}
	
	public DomWriterCtx setIndenter(final IIndenter indenter) {
		m_indenter = indenter ;
		return this ;
	}
	public IIndenter getIndenter(){
		return m_indenter;
	}
	
	/**
	 * check whether to render document section or not.
	 * @return
	 */
	public boolean isRenderXmlDoc() {
		return (m_flags&FLAG_RENDER_XML_DOC) !=0;
	}	
	
	/**
	 * set true, HtmlWriter will render document section, such as xml version
	 * @param value
	 * @return 
	 */
	public DomWriterCtx setRenderXmlDoc(final boolean value){
		m_flags =  (value ? m_flags | FLAG_RENDER_XML_DOC : m_flags & ~FLAG_RENDER_XML_DOC);
		return this;
	}	
	
	/**
	 * Check whether to render namespace information or not.
	 * @return 
	 */
	public boolean isRenderNS() {
		return (m_flags&FLAG_RENDER_NS) !=0;
	}
	/**
	 * set true, HtmlWriter will trim empty DText.
	 * @param value
	 * @return
	 */
	//BUGDB00593573 
	//During V4 parser, new line is converted to Dtext("\n"), as a valid child node for  body.
	//Because Dtext is not a valid child for html, so between html and body, not additional new lines added.
	//IIndenter.Pretty() is implemented as: always add a new line to the beginning of a new element tag.

	public DomWriterCtx setTrimDText(final boolean value){
		m_flags =  (value ? m_flags | FLAG_TRIM_DTEXT : m_flags & ~FLAG_TRIM_DTEXT);
		return this;
	}
	
	/**
	 * Check whether to trim empty DText or not.
	 * @return
	 */
	public boolean isTrimDText() {
		return (m_flags&FLAG_TRIM_DTEXT) !=0;
	}	
	
	/**
	 * set true, HtmlWriter will render namespace information, 
	 * include tag name (prefix:localName) and namespace attribute
	 * (xmlns:prefix="namespaceUri")
	 * @param value
	 * @return
	 */
	public DomWriterCtx setRenderNS(final boolean value){
		m_flags =  (value ? m_flags | FLAG_RENDER_NS : m_flags & ~FLAG_RENDER_NS);
		return this;
	}	
	
	//
	// Override(s) from Object
	//
	@Override
	public String toString() {
		Z z = new Z() ;
		z.format("render XML doc", isRenderXmlDoc());
		z.format("render NS", isRenderNS());
		z.format("add line numbers", isAddLineNumber());
		z.format("trim DText", isTrimDText());
		z.format("optimize", isOptimization());
		if (m_indenter != null) z.format("indenter", m_indenter.getClass()) ;
//		if (m_schema != null) z.format("schema", m_schema.getClass()) ;
		if (m_writer != null) z.format("writer", m_writer.getClass()) ;
		return z.toString() ;
	}
}



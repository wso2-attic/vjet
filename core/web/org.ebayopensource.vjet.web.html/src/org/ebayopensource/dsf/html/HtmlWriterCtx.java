/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html;

import java.io.Writer;

import org.ebayopensource.dsf.dom.util.DomWriterCtx;
import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;
import org.ebayopensource.dsf.html.dom.HtmlTypeEnumAssociator;
import org.ebayopensource.dsf.html.schemas.Html401Transitional;
import org.ebayopensource.dsf.html.schemas.ISchema;
import org.ebayopensource.dsf.common.xml.IIndenter;

public class HtmlWriterCtx extends DomWriterCtx {
	private ISchema m_schema ; 
	private final HtmlTypeEnum[] m_notEscapeTags;   //used to avoid escape processing for <>&\

	static final HtmlTypeEnum[] TAGS_TO_NOT_ESCAPE_TXT = {
		HtmlTypeEnum.SCRIPT,
		HtmlTypeEnum.STYLE,
	};
	
	//
	// Constructor(s)
	//
	public HtmlWriterCtx() {
		this(createWriter(), IIndenter.COMPACT, getDefaultSchema(), TAGS_TO_NOT_ESCAPE_TXT) ;
	}
	
	public HtmlWriterCtx(final Writer writer) {
		this(writer, IIndenter.COMPACT, getDefaultSchema(), TAGS_TO_NOT_ESCAPE_TXT) ;
	}
	
	/**
	 * User specified schema, used by DWriterF to handle endElement event
	 * Default XmlStreamWriterOption: 
	 * @param schema
	 */
	public HtmlWriterCtx(final ISchema schema){
		this(
			createWriter(),
			IIndenter.COMPACT,
			schema, 
			TAGS_TO_NOT_ESCAPE_TXT);
	}

	public HtmlWriterCtx(final IIndenter indenter){
		this(
			createWriter(),
			indenter,
			getDefaultSchema(), 
			TAGS_TO_NOT_ESCAPE_TXT);
	}
	
	public HtmlWriterCtx(final HtmlTypeEnum[] notEscapeTags) {
		if (notEscapeTags != null) {
			if (notEscapeTags == TAGS_TO_NOT_ESCAPE_TXT){
				m_notEscapeTags = TAGS_TO_NOT_ESCAPE_TXT;
			} else {
				m_notEscapeTags = new HtmlTypeEnum[notEscapeTags.length];
				for (int i = 0; i < notEscapeTags.length; i ++){
					m_notEscapeTags[i] = notEscapeTags[i];
				}
			}
		} 
		else {
			m_notEscapeTags = null;
		}
	}
	
	/**
	 * Schema used by DWriterF to handle endElement event
	 * User specified XmlStreamWriterOption: 
	 * @param streamOption  
	 * @param schema
	 */
	//TODO need to verify schema first
	public HtmlWriterCtx( 
		final Writer writer,
		final IIndenter indenter,
		final ISchema schema)
	{
		this(writer, indenter, schema, TAGS_TO_NOT_ESCAPE_TXT);
	}
	
	public HtmlWriterCtx( 
		final Writer writer,
		final IIndenter indenter)
	{
		this(writer, indenter, getDefaultSchema(), TAGS_TO_NOT_ESCAPE_TXT);
	}
	
	 /* User specified XmlStreamWriterOption and notEscapeTags
	 * @param type 
	 * @param streamOption  
	 * @param notEscapeTags
	 * @exception throw DsfRuntimeException if type or streamOption is null
	 */
	HtmlWriterCtx(final 
		Writer writer,
		IIndenter indenter, 
		final ISchema schema, 
		final HtmlTypeEnum[] notEscapeTags)

	{		
//		if (writer == null ){
//			throw new DsfRuntimeException("Writer is null");
//		}	
//		if (indenter == null ){
//			throw new DsfRuntimeException("indenter is null");
//		}	
//		
//		m_writer = writer;
//		m_indenter = indenter;

		super(writer, indenter) ; //, schema) ;
		 
		m_schema = schema;
		
		if (notEscapeTags != null) {
			if (notEscapeTags == TAGS_TO_NOT_ESCAPE_TXT){
				m_notEscapeTags = TAGS_TO_NOT_ESCAPE_TXT;
			} else {
				m_notEscapeTags = new HtmlTypeEnum[notEscapeTags.length];
				for (int i = 0; i < notEscapeTags.length; i ++){
					m_notEscapeTags[i] = notEscapeTags[i];
				}
			}
		} else {
			m_notEscapeTags = null;
		}				
	}	

	
	//
	// API
	//
	public static ISchema getDefaultSchema(){			
		return Html401Transitional.getInstance();				
	}	
	
	public char[][] getNotEscapeTags(){
		if (m_notEscapeTags ==null){
			return null;
		}
		char[][] notEscapeTagNames = new char[m_notEscapeTags.length][];
		for (int i = 0; i < m_notEscapeTags.length; i ++){				
			notEscapeTagNames[i]=HtmlTypeEnumAssociator.getNameCharArray(m_notEscapeTags[i]);
		}
		return notEscapeTagNames;
	}
	
	//
	// Overrides(s) from DomWriterCtx
	//
	@Override
	public HtmlWriterCtx setAddLineNumber(final boolean value){
		super.setAddLineNumber(value) ;
		return this;
	}	
	
	@Override
	public HtmlWriterCtx setWriter(final Writer writer) {
		super.setWriter(writer) ;
		return this ;
	}

	@Override
	public HtmlWriterCtx setIndenter(final IIndenter indenter) {
		super.setIndenter(indenter) ;
		return this ;
	}	
	
	/**
	 * set true, HtmlWriter will render document section, such as xml version
	 * @param value
	 * @return 
	 */
	@Override
	public HtmlWriterCtx setRenderXmlDoc(final boolean value){
		super.setRenderXmlDoc(value) ;
		return this;
	}	
	
	/**
	 * set true, HtmlWriter will trim empty DText.
	 * @param value
	 * @return
	 */
	//BUGDB00593573 
	//During V4 parser, new line is converted to Dtext("\n"), as a valid child node for  body.
	//Because Dtext is not a valid child for html, so between html and body, not additional new lines added.
	//Iindenter.Pretty() is implemented as: always add a new line to the beginning of a new element tag.
	@Override
	public HtmlWriterCtx setTrimDText(final boolean value){
		super.setTrimDText(value) ;
		return this;
	}
	
	/**
	 * set true, HtmlWriter will render namespace information, 
	 * include tag name (prefix:localName) and namespace attribute
	 * (xmlns:prefix="namespaceUri")
	 * @param value
	 * @return
	 */
	@Override
	public HtmlWriterCtx setRenderNS(final boolean value){
		super.setRenderNS(value) ;
		return this;
	}
	
	/**
	 * set true, optimize namespace during rendering, 
	 * to avoid redundant namespace attribute in DOM tree.	 * 
	 * @param value
	 * @return HtmlWriterOption
	 */
	public HtmlWriterCtx setOptimization(final boolean value){
		super.setOptimization(value) ;
		return this;
	}
	
	public ISchema getSchema() {		
		return m_schema;
	}
}

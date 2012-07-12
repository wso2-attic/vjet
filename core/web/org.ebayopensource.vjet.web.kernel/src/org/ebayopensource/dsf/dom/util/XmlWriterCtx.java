/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom.util;

import java.io.Writer;

//import org.ebayopensource.dsf.html.schemas.ISchema;
import org.ebayopensource.dsf.common.xml.IIndenter;

public class XmlWriterCtx extends DomWriterCtx {
	//
	// API
	//
//	public XmlWriterCtx setSchema(final ISchema schema) {
//		super.setSchema(schema) ;
//		return this ;
//	}
	
	/**
	 * set true, optimize namespace during rendering, 
	 * to avoid redundant namespace attribute in DOM tree.	 * 
	 * @param value
	 * @return HtmlWriterOption
	 */
	public XmlWriterCtx setOptimization(final boolean value){
		super.setOptimization(value) ;
		return this ;
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
	public XmlWriterCtx setAddLineNumber(final boolean value){
		super.setAddLineNumber(value) ;
		return this;
	}	
	
	public XmlWriterCtx setWriter(final Writer writer) {
		super.setWriter(writer) ;
		return this ;
	}
	
	public XmlWriterCtx setIndenter(final IIndenter indenter) {
		super.setIndenter(indenter) ;
		return this ;
	}	
	
	/**
	 * set true, HtmlWriter will render document section, such as xml version
	 * @param value
	 * @return 
	 */
	public XmlWriterCtx setRenderXmlDoc(final boolean value){
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
	//IIndenter.Pretty() is implemented as: always add a new line to the beginning of a new element tag.

	public XmlWriterCtx setTrimDText(final boolean value){
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
	public XmlWriterCtx setRenderNS(final boolean value){
		super.setRenderNS(value) ;
		return this;
	}	
	
}

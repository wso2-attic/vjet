/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.dom.DDocumentType;
import org.ebayopensource.dsf.html.schemas.Html401Strict;
import org.ebayopensource.dsf.html.schemas.Html401Transitional;
import org.ebayopensource.dsf.html.schemas.ISchema;

public class DHtmlDocType extends DDocumentType {
	public static final String QUALIFIED_NAME = "html"; // ignoreHtmlKeyword
	
	// All the HTML doc types text is not case sensitive but we had started
	// with uppercase and will keep that convention
	public static final Type HTML_FRAMESET = new Type("HTML_FRAMESET");
	public static final Type HTML_STRICT = new Type("HTML_STRICT");
	public static final Type HTML_TRANSITIONAL =
		new Type("HTML_TRANSITIONAL");
	public static final Type XHTML_STRICT = new Type("XHTML_STRICT");
	public static final Type XHTML_TRANSITIONAL =
		new Type("XHTML_TRANSITIONAL");
	public static final Type XHTML_FRAMESET =new Type("XHTML_FRAMESET");
	
	// http://dev.w3.org/html5/spec/Overview.html#the-doctype
	public static final Type HTML_50 = new Type("HTML") ;
	/**
	 * No DocType is added to the HTML document.
	 * The HTML document is in quirks mode.
	 * Use only under exceptional circumstances.
	 */
	public static final Type NONE = new Type("NONE");

	private ISchema m_schema;
//	public DHtmlDocType(String qualifiedName, String publicId, String systemId){
//		this(null, qualifiedName, publicId, systemId);
//	}
	DHtmlDocType(
		final DHtmlDocument doc, 
		final String qualifiedName, 
		final String publicId, 
		final String systemId)
	{
		super(doc, qualifiedName, publicId, systemId);
	}
//	public static DHtmlDocType createDocType(final Type desiredDocTypeEnum) {
//		return createDocType(null, desiredDocTypeEnum);
//	}
	public static DHtmlDocType createDocType(
		final DHtmlDocument doc,
		final Type desiredDocTypeEnum)
	{
		final DHtmlDocType docType;
		if (desiredDocTypeEnum == HTML_STRICT) {
			/*
			 * HTML Strict DTD
			 * Use this when you want clean markup, free of presentational 
			 * clutter. Use this together with Cascading Style Sheets (CSS):
			 * 
			 * <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd"> 
			 * 
			 */
			docType = new DHtmlDocType(doc, QUALIFIED_NAME,
				"-//W3C//DTD HTML 4.01//EN",
				"http://www.w3.org/TR/html4/strict.dtd");
			docType.setSchema(Html401Strict.getInstance());
		} 
		else if (desiredDocTypeEnum == HTML_TRANSITIONAL) {
			/*
			 * HTML Transitional DTD
			 * The Transitional DTD includes presentation attributes and 
			 * elements that W3C expects to move to a style sheet. Use this 
			 * when you need to use HTML's presentational features because 
			 * your readers don't have browsers that support Cascading Style 
			 * Sheets (CSS):
			 * 
			 * <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
			 * 
			 */
			docType = new DHtmlDocType(doc, QUALIFIED_NAME,
				"-//W3C//DTD HTML 4.01 Transitional//EN",
				"http://www.w3.org/TR/html4/loose.dtd");
			docType.setSchema(Html401Transitional.getInstance());
		} 
		else if (desiredDocTypeEnum == HTML_FRAMESET) {
			/*
			 * Frameset DTD
			 * The Frameset DTD should be used for documents with frames. 
			 * The Frameset DTD is equal to the Transitional DTD except 
			 * for the frameset element replaces the body element: 
			 * 
			 * <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd"> 
			 * 
			 */
			docType = new DHtmlDocType(doc, QUALIFIED_NAME,
				"-//W3C//DTD HTML 4.01 Frameset//EN",
				"http://www.w3.org/TR/html4/frameset.dtd");
		} 
		else if (desiredDocTypeEnum == XHTML_STRICT) {
			/*
			 * XHTML Strict DTD
			 * Use this DTD when you want clean markup, free of presentational
			 *  clutter. Use this together with Cascading Style Sheets (CSS):
			 *  
			 *  <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">  
			 * 
			 * Will cause problems is used - use with CAUTION
			 * 
			 */
			docType = new DHtmlDocType(doc, QUALIFIED_NAME,
				"-//W3C//DTD XHTML 1.0 Strict//EN",
				"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd");
		} 
		else if (desiredDocTypeEnum == XHTML_TRANSITIONAL) {
			/*
			 * XHTML Transitional DTD
			 * Use this DTD when you need to use XHTML's presentational features 
			 * because your readers don't have browsers that support Cascading 
			 * Style Sheets (CSS):
			 * 
			 * <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
			 * 
			 */
			docType = new DHtmlDocType(doc, QUALIFIED_NAME,
				"-//W3C//DTD XHTML 1.0 Transitional//EN",
				"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd");
		} 
		else if (desiredDocTypeEnum == XHTML_FRAMESET) {
			/*
			 * XHTML Frameset DTD
			 * Use this DTD when you want to use frames!
			 * 
			 * <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">  
			 * 
			 * To check that you have written a valid XHTML document with a correct DTD,  you can link your XHTML page to an XHTML validator.
			 * 
			 */
			docType = new DHtmlDocType(doc, QUALIFIED_NAME,
				"-//W3C//DTD XHTML 1.0 Frameset//EN",
				"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd");
		} 
		else if (desiredDocTypeEnum == NONE) {
			docType = null;
		}
		else {
			throw new DsfRuntimeException("unexpected document type '" +
				desiredDocTypeEnum + "'");
		}
		return docType;
	}
	
	public static final class Type {
		final String m_name;
		private Type(final String name) {
			m_name = name;
		}
		public String getName() {
			return m_name;
		}
		public String toString() {
			return getName();
		}
	}
	
	public ISchema getSchema() {
		return m_schema;
	}
	
	public void setSchema(final ISchema schema) {
		m_schema = schema;
	}
	
	@Override
	public DHtmlDocType jif(final String jif) { 
		super.jif(jif) ;
		return this ;
	}
}

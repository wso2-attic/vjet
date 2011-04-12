/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.html.sax.HtmlSchema;
import org.ebayopensource.dsf.html.sax.Schema;

/**
 * Options to be used during HTML parsing.
 * 
 * @see #setFixDuplicateIds(boolean)
 * @see #setIgnoreDuplicateIds(boolean)
 * @see #preProcessInput()
 * @see #preProcessTagName()
 * @see #preDecode()
 * @see #checkWellform(String...)
 * @see HtmlBuilderHelper#parseHtmlFragment(String, HtmlParserOptions)
 * 
 */
public class HtmlParserOptions implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	private List<String> m_wellformTags = new ArrayList<String>();

	transient final Schema m_schema;

	/** flag value storing various option values */
	int m_flags;

	/** ignoreDuplicateId option mask */
	public final static short IGNORE_DUP_ID = 0x1 << 0;

	/** fixDuplicateId option mask */
	public final static short FIX_DUP_ID = 0x1 << 1;
	
	/** pre process input text */
	public final static short PRE_PROCESS_DECODE = 0x1 << 2;
		
	/** pre process input text,
	 * replace "<tag/attr" by "<tag attr" */
	public final static short PRE_PROCESS_TAG_NAME = 0x1 << 3;
	
	/** pre process input text 
	 * If the first tag is unknown, prefix input with <html><body>*/
	public final static short PRE_PROC_UNKNOWN_TAG = 0x1 << 4;
	
	/** during parser, don't emit default attributes*/
	public final static short NO_DEFAULT_ATTR = 0x1 << 5;
		
	/**
	 * Default constructor.
	 * 
	 */
	public HtmlParserOptions() {
		this(new HtmlSchema());
	}

	/**
	 * Constructor
	 * 
	 * @param schema
	 *            schema object to use
	 */
	public HtmlParserOptions(final Schema schema) {
		m_schema = schema;
	}

	/**
	 * Returns <code>true</code> if the ignoreDuplicateId option is set,
	 * <code>false</code> otherwise (default value).
	 * 
	 * @see #setIgnoreDuplicateIds(boolean)
	 * 
	 * @return boolean value of option ignoreDuplicateId
	 */
	public boolean isIgnoreDuplicateIds() {
		return (m_flags & IGNORE_DUP_ID) != 0;
	}

	/**
	 * Set or reset the ignoreDuplicateId option. If set to <code>true</code>
	 * the presence of duplicate IDs in the HTML is ignored. The default
	 * behavior of the HTML parser is to throw an Exception if it encounters
	 * duplicate IDs. The setting overrides this behavior
	 * <p>
	 * If option fixDuplicateIds is <code>true</code> then a request to set
	 * ignoreDuplicateIds to <code>true</code> is ignored
	 * 
	 * @param ignoreDuplicateIds
	 *            boolean value for the ignoreDuplicateId option
	 * 
	 * @return this html parser options object
	 */
	public HtmlParserOptions setIgnoreDuplicateIds(boolean ignoreDuplicateIds) {
		if (ignoreDuplicateIds && isFixDuplicateIds()) {
			// ignore this request
			return this;
		}
		m_flags = (ignoreDuplicateIds ? m_flags | IGNORE_DUP_ID : m_flags
				& ~IGNORE_DUP_ID);
		return this;
	}
	
	/**
	 * @deprecated replaced by preProcessTagName();
	 */
	public HtmlParserOptions relaxElementName(final String... elementNames) {
		return preProcessTagName();		
	}

	/**
	 * Ask parser to check well-formedness for the element names in the list. If
	 * any element name in this list is not wellformed the parser will throw a
	 * DsfRuntimeException
	 * 
	 * @param elementNames
	 *            element name list
	 * @exception DsfRuntimeException
	 *                if elementName is unknown Html element or elementName is not wellformed.
	 * @return this parser options object
	 */
	public HtmlParserOptions checkWellform(final String... elementNames) {		
		if (elementNames == null) {
			return this;
		}
		for (String e : elementNames) {
			if (e != null && e.trim().length() > 0) {
				String small = e.toLowerCase();
				if (m_schema.getElementType(small) == null) {
					throw new DsfRuntimeException("Unknown Html Element " + small);
				}

				if (m_wellformTags.indexOf(small) == -1) {
					m_wellformTags.add(small);
				}	
			}
		}
		return this;
	}


	/**
	 * Get value of scan filter map entries as an unmodifiable map
	 * 
	 * @return unmodifiable map with scan filter entries
	 */
	public List<String> getWellformTags() {
		return Collections.unmodifiableList(m_wellformTags);
	}

	/**
	 * Returns <code>true</code> if the fixDuplicateId option is set,
	 * <code>false</code> otherwise (default value).
	 * 
	 * @see #setFixDuplicateIds(boolean)
	 * 
	 * @return boolean value of option fixDuplicateId
	 */

	public boolean isFixDuplicateIds() {
		return (m_flags & FIX_DUP_ID) != 0;
	}

	/**
	 * Set or reset the fixDuplicateIds option. If this option is set to
	 * <code>true</code> the HTML parser is instructed to fix the duplication
	 * of the IDs.
	 * <p>
	 * If the fixDuplicateIds option is reset, it also resets the
	 * ignoreDuplicateIds option
	 * 
	 * @param fixDuplicateIds
	 *            value for the fixDuplicateIds option
	 * 
	 * @return this parser options object
	 */
	public HtmlParserOptions setFixDuplicateIds(boolean fixDuplicateIds) {
		if (fixDuplicateIds && isIgnoreDuplicateIds()) {
			setIgnoreDuplicateIds(false);
		}
		m_flags = (fixDuplicateIds ? m_flags | FIX_DUP_ID : m_flags
				& ~FIX_DUP_ID);

		return this;
	}

	/**
	 * Return a duplicate of this options object.
	 * 
	 * @throws CloneNotSupportedException
	 */

	@Override
	public HtmlParserOptions clone() throws CloneNotSupportedException {
		HtmlParserOptions copy = (HtmlParserOptions) super.clone();
		copy.m_wellformTags = new ArrayList<String>(m_wellformTags.size());
		for (String e : m_wellformTags) {
			copy.m_wellformTags.add(e);
		}
		return copy;
	}

	/**
	 * Return Schema object
	 * 
	 * @return Schema
	 */
	public Schema getSchema() {
		return m_schema;
	}
	
	/**
	 * Returns <code>true</code> if the preDecode option is true,
	 * <code>false</code> otherwise (default value).	 
	 * 
	 * @return boolean value of option preDecode
	 */

	public boolean isPreDecode() {
		return (m_flags & PRE_PROCESS_DECODE) != 0;
	}


	
	/**
	 * Returns <code>true</code> if the preProcessUnkownTag option is true,
	 * <code>false</code> otherwise (default).
	 */

	public boolean isPreProcessUnkownTag() {
		return (m_flags & PRE_PROC_UNKNOWN_TAG) != 0;
	}
	
	/**
	 * Returns <code>true</code> if the preProcessTagName option is true,
	 * <code>false</code> otherwise (default value). 
	 * 
	 */

	public boolean isPreProcessTagName() {
		return (m_flags & PRE_PROCESS_TAG_NAME) != 0;
	}
	
	/**
	 * Returns <code>true</code> if the noDefaultAttr option is true,
	 * <code>false</code> otherwise (default value). 
	 * 
	 */
	public boolean isNoDefaultAttr() {
		return (m_flags & NO_DEFAULT_ATTR) != 0;
	}
	/**
	 * Process input html tags before parsing:
	 * replacing "<tag/attr" by "<tag attr". 
	 * 		
	 * @return this parser options object
	 */
	public HtmlParserOptions preProcessTagName() {		
		m_flags =  m_flags | PRE_PROCESS_TAG_NAME;
		return this;
	}
	
	/**
	 * Process unknown tag that is the first tag of input html before parsing.  
	 * If first tag is unknown (undefined in schema), 
	 * Append input string to <html><body>. 
	 * Note, Enable this option will alter the structure of original html.	 
	 * 		
	 * @return this parser options object
	 */
	public HtmlParserOptions preProcessUnkownTag() {		
		m_flags =  m_flags | PRE_PROC_UNKNOWN_TAG;
		return preProcessTagName();		
	}
	
	/**
	 * Decode the input html string before parsing.
	 * @return this parser options object
	 */
	public HtmlParserOptions preDecode() {		
		m_flags =  m_flags | PRE_PROCESS_DECODE;
		return this;
	}	
	
	/**
	 * Ask parser don't emit default attributes during parsing.
	 * @return this parser options object
	 */
	public HtmlParserOptions noDefaultAttr() {		
		m_flags =  m_flags | NO_DEFAULT_ATTR;
		return this;
	}	
}

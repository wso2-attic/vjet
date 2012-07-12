/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import org.ebayopensource.af.common.types.RawString;
import org.ebayopensource.dsf.common.Z;

public class DRawString extends DText {
	private RawString m_rawString ;
	
	static final long serialVersionUID = -5294980852957403469L;
	
	//
	// Constructor(s)
	//
	/**
	 * Answer a new instance with the passed in raw data and encoding.  Since
	 * we are dealing with byte[] and not a String, we require that the 
	 * encoding be not null.  Throws a DsfRuntimeException if the raw data or
	 * encoding is null.
	 */
	public DRawString(final byte[] rawData, final String encoding) {
		if (rawData == null) {
			chuck("rawData is null");
		}
		if (encoding == null) {
			chuck("encoding is null");
		}
		m_rawString = new RawString(rawData, encoding);
	}
	
	/**
	 * Answer a new instance for the passed in String.  This has the same
	 * effect as DRawString(str, null).  A DsfRuntimeException is thrown
	 * if the passed in String is null.
	 */
	public DRawString(final String str) {
		this(str, null) ;
	}
	
	public DRawString(final String str, final String encoding) {
		if (str == null) {
			chuck("str is null");
		}
		m_rawString = new RawString(str, encoding) ;
	}
	
	public DRawString(final RawString rawString) {
		m_rawString = rawString ;
	}

	//
	// Framework
	//
//	@Override
//	public int getTypeId() {
//		return INodeType.RAWSTRING;
//	}
	// just inherit the DOM2 node type otherwise apache/xerces will get
	// confused, hence why the following method is not overridden.
//	@Override
//	public short getNodeType() {
//		return INodeType.RAWSTRING ;
//	}
	
	//
	// API
	//	
	public RawString getRawString() {
		return m_rawString ;
	}
	
	public DRawString setRawString(final RawString rawString) {
		m_rawString = rawString ;
		return this ;
	}
	
	//
	// Satisfy DNode Framework requirements
	//
	/**
	 * Answers the DNodeType for this instance.  
	 * <br>node.getNodeType() == node.getNodeType() will always be true.
	 * <br>The node type will never be null.
	 * @return The DNodeType for this instance
	 * @see DNodeType
	 */	
//	@Override
//	public DNodeType getDomNodeType() {
//		return DNodeType.RAW;
//	}
	
	@Override
	public DRawString jif(final String jif) { 
		super.jif(jif) ;
		return this ;
	}
	
	//
	// Overrides from Object
	//
	@Override
	public String toString() {
		return Z.fmt("raw string", m_rawString) ;	
	}
}

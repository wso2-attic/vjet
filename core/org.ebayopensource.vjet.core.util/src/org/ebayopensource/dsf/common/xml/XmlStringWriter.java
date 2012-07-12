/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.xml;

import java.io.StringWriter;



public class XmlStringWriter extends XmlStreamWriter {
	final StringWriter m_stringWriter;
//	public XmlStringWriter(){
//		this(1024);
//	}
//	public XmlStringWriter(final int stringBufferSize){
//		this(stringBufferSize, IIndenter.COMPACT);
//	}
	public XmlStringWriter(final int stringBufferSize, final IIndenter indender){
		super(new StringWriter(stringBufferSize), indender);
		m_stringWriter = (StringWriter)m_writer;
	}
	@Override
	public String toString(){
		return m_stringWriter.toString();
	}
}

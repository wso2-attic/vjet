/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.js.dbgp;

import java.io.IOException;
import java.io.Reader;

public class CombinedReader extends Reader{

	private final Reader m_bf;
	private int m_count = 0;
	
	public CombinedReader(Reader bf) {
		super();
		this.m_bf = bf;
	}

	public void close() throws IOException {
		m_bf.close();
	}
	
	public int read(char[] cbuf, int off, int len) throws IOException {
		if (m_count<1){
			cbuf[off++]='\r';
			len--;
			m_count=1;
			if (len==0) {
				return 1;
			}
			
			cbuf[off++]='\n';
			len--;
			m_count=2;
			if (len==0) {
				return 2;
			}
		}
		return m_bf.read(cbuf, off, len);
	}
}

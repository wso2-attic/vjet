/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.sac;

import java.io.InputStream;
import java.io.Reader;

public class InputSource {
	private String m_uri;
	private InputStream m_byteStream;
	private String m_encoding;
	private Reader m_characterStream;
	private String m_title;
	private String m_media;
	
	public InputSource() {
		/* empty */
	}
	
	public InputSource(String string) {
		setURI(string);
	}
	
	public InputSource(Reader reader) {
		setCharacterStream(reader);
	}
	
	public void setURI(String string) {
		m_uri = string;
	}
	
	public String getURI() {
		return m_uri;
	}
	
	public void setByteStream(InputStream inputstream) {
		m_byteStream = inputstream;
	}
	
	public InputStream getByteStream() {
		return m_byteStream;
	}
	
	public void setEncoding(String string) {
		m_encoding = string;
	}
	
	public String getEncoding() {
		return m_encoding;
	}
	
	public void setCharacterStream(Reader reader) {
		m_characterStream = reader;
	}
	
	public Reader getCharacterStream() {
		return m_characterStream;
	}
	
	public void setTitle(String string) {
		m_title = string;
	}
	
	public String getTitle() {
		return m_title;
	}
	
	public void setMedia(String string) {
		m_media = string;
	}
	
	public String getMedia() {
		if (m_media == null)
			return "all";
		return m_media;
	}
}

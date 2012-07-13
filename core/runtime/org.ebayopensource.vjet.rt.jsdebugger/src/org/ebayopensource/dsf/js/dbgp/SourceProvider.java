/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.js.dbgp;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.common.StringUtils;
import org.ebayopensource.dsf.logger.LogLevel;
import org.ebayopensource.dsf.logger.Logger;


/**
 * 
 * 
 *  Ouyang
 * 
 */
public class SourceProvider implements ISourceProvider {

	private static final char CHAR_NEW_LINE = '\n';
	private Map<String, String> m_fileMapping = new HashMap<String, String>();
	private static Logger logger = Logger.getInstance(SourceProvider.class);
	private static final int B_UF_SIZE = 1024;

	public void addFile(String filePath) {
		m_fileMapping.put(filePath, PathUtil.normalize(filePath));
	}

	public boolean containsFile(String filePath) {
		return m_fileMapping.get(filePath) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.dsf.js.dbgp.ISourceProvider#getSource(java.lang.String,
	 * int, int)
	 */
	@Override
	public String getSource(String filePath, int beginLine, int endLine) {
		if (filePath == null) {
			throw new IllegalArgumentException("File path shouldn't be null.");
		}
		URI fileURI = URI.create(filePath);
		try {
			return getContents(resolveURI(fileURI), beginLine, endLine);
		} catch (Exception e) {
			logger.log(LogLevel.ERROR, e.getLocalizedMessage(), e);
			return "";
		}
	}

	@Override
	public String[] list() {
		// filter duplicates
		return new HashSet<String>(m_fileMapping.values())
				.toArray(new String[0]);
	}

	protected String getContents(String contents, int beginLine, int endLine) {
		if (contents == null || contents.length() == 0) {
			return "";
		}
		if ((beginLine == -1) && (endLine == -1)) {
			return contents;
		}
		List<String> lines = StringUtils.splitStr(contents, CHAR_NEW_LINE);
		final int lineLength = lines.size();
		endLine = endLine > lineLength + 1 ? -1 : endLine;
		StringBuilder builder = new StringBuilder();
		if ((beginLine != -1) && (endLine == -1)) {
			for (int i = beginLine - 1; i < lineLength; i++) {
				appendLine(builder, lines.get(i));
			}
		} else if ((beginLine == -1) && (endLine != -1)) {
			final int len = endLine - 1;
			for (int i = 0; i < len; i++) {
				appendLine(builder, lines.get(i));
			}
		} else if ((beginLine != -1) && (endLine != -1)) {
			final int len = endLine - 1;
			for (int i = beginLine - 1; i < len; i++) {
				appendLine(builder, lines.get(i));
			}
		}
		return builder.toString();
	}

	private StringBuilder appendLine(StringBuilder builder, String line) {
		return builder.append(line).append(CHAR_NEW_LINE);
	}

	private String resolveURI(URI uri) throws Exception {
		StringBuilder b = new StringBuilder();
		InputStream is = null;
		try {
			URL url = uri.toURL();
			is = new BufferedInputStream(url.openStream());
			byte[] cache = new byte[B_UF_SIZE];
			int count = 0;
			while ((count = is.read(cache)) > 0) {
				b.append(new String(cache, "UTF-8"), 0, count);
			}
		} finally {
			is.close();
		}
		return b.toString();
	}

}

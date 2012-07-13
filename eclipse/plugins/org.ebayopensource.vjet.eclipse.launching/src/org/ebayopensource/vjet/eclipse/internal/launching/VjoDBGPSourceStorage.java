/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.launching;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.mod.dbgp.IDbgpSession;
import org.eclipse.dltk.mod.dbgp.exceptions.DbgpException;
import org.eclipse.dltk.mod.launching.sourcelookup.Messages;

import org.ebayopensource.dsf.common.StringUtils;
import org.ebayopensource.vjet.eclipse.launching.VjetLaunchingPlugin;

public class VjoDBGPSourceStorage implements IStorage {

	private static final char	CHAR_NEW_LINE	= '\n';
	private String				m_cachedSource	= null;
	private IDbgpSession		m_session;
	private URI					m_uri;
	private String				m_name;

	public VjoDBGPSourceStorage(URI uri, IDbgpSession session) {
		this.m_uri = uri;
		this.m_session = session;
	}

	@Override
	public Object getAdapter(Class adapter) {
		return adapter.isAssignableFrom(this.getClass());
	}

	@Override
	public InputStream getContents() throws CoreException {
		try {
			byte[] contents = lookupSource().getBytes();
			return new ByteArrayInputStream(contents);
		} catch (DbgpException e) {
			throw new CoreException(new Status(IStatus.ERROR,
					VjetLaunchingPlugin.PLUGIN_ID,
					Messages.DBGPSourceModule_dbgpSourceLookup, e));
		}
	}

	@Override
	public IPath getFullPath() {
		return new Path(m_uri.toString());
	}

	@Override
	public String getName() {
		try {
			if (m_name == null) {
				String source = lookupSource();
				m_name = getCodeSnippt(source);
			}
			return m_name;
		} catch (DbgpException e) {
			VjetLaunchingPlugin.error(e.getLocalizedMessage(), e);
			return "";
		}
	}

	private String getCodeSnippt(String source) {
		return getContents(source, 1, 2);
	}

	private String getContents(String source, int beginLine, int endLine) {
		if (source == null || source.length() == 0) {
			return "";
		}
		if ((beginLine == -1) && (endLine == -1)) {
			return source;
		}
		List<String> lines = StringUtils.splitStr(source, CHAR_NEW_LINE);
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

	public URI getURI() {
		return m_uri;
	}

	@Override
	public boolean isReadOnly() {
		return true;
	}

	public void setSession(IDbgpSession session) {
		this.m_session = session;
	}

	private String lookupSource() throws DbgpException {
		if (m_cachedSource == null) {
			m_cachedSource = m_session.getCoreCommands().getSource(m_uri);
		}
		return m_cachedSource;
	}
}

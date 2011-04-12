/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.command;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.dltk.mod.dbgp.exceptions.DbgpException;
import org.eclipse.dltk.mod.dbgp.internal.DbgpRequest;
import org.eclipse.dltk.mod.dbgp.internal.commands.DbgpBaseCommands;
import org.eclipse.dltk.mod.dbgp.internal.commands.IDbgpCommunicator;
import org.eclipse.dltk.mod.dbgp.internal.utils.DbgpXmlParser;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class VjetSourceCommands extends DbgpBaseCommands implements
		IVjetSourceCommands {

	private static final String	SOURCE_LIST		= "source_list";
	private static final String	FILE_SCHEME		= "file:///";
	private static final String	ATTR_FILENAME	= "name";

	public VjetSourceCommands(IDbgpCommunicator communicator) {
		super(communicator);
	}

	@Override
	public URI[] list() throws DbgpException {
		DbgpRequest request = createRequest(SOURCE_LIST);

		Element response = communicate(request);
		return parseFilesFromResponseXml(response);
	}

	private URI[] parseFilesFromResponseXml(Element response) {
		boolean success = DbgpXmlParser.parseSuccess(response);
		if (!success) {
			return new URI[0];
		}
		Set<URI> fileURIs = new HashSet<URI>();
		Node node = response.getFirstChild();
		// first child is a text node
		while ((node = getFileNode(node.getNextSibling())) != null) {
			URI uri = parseURI(node.getAttributes().getNamedItem(ATTR_FILENAME)
					.getNodeValue());
			if (uri != null) {
				fileURIs.add(uri);
			}
		}

		return fileURIs.toArray(new URI[0]);
	}

	private Node getFileNode(Node node) {
		while ((node != null) && (Node.TEXT_NODE == node.getNodeType())) {
			node = node.getNextSibling();
		}
		return node;
	}

	private static URI parseURI(String fileName) {
		/*
		 * ActiveState python debugger on windows sends URI as
		 * "file:///C|/path/to/file.py" we need to convert it.
		 */
		if (fileName.startsWith(FILE_SCHEME)) {
			final int pos = FILE_SCHEME.length();
			if (fileName.length() > pos + 3) {
				if (Character.isLetter(fileName.charAt(pos))
						&& fileName.charAt(pos + 1) == '|'
						&& fileName.charAt(pos + 2) == '/') {
					fileName = fileName.substring(0, pos + 1) + ':'
							+ fileName.substring(pos + 2);
				}
			}
		}
		return URI.create(fileName);
	}
}

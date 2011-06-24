/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.handler;

import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCommentCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstFieldOrMethodCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstKeywordCompletion;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcHandler;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * 
 *  Ouyang
 * 
 */
public class VjoCcCommentHandlerTestUtil extends VjoCcBaseHandlerTestUtil {

	private Map<Integer, String>	commentMapping	= new HashMap<Integer, String>();
	private String					commentTag		= "commentText";

	protected VjoCcCtx createCcContext(int testPosition, int position,
			String str) {
		VjoCcCtx ccCtx = super.createCcContext(testPosition, position, str);
		String commentString = commentMapping.get(testPosition);
		int commentOffset = position - str.indexOf(commentString);
		if (!(ccCtx.getCompletion() instanceof JstCommentCompletion)) {
			JstCommentCompletion commentCompletion = new JstCommentCompletion(
					(JstType) ccCtx.getActingType(), commentString,
					commentOffset);
			if (ccCtx.getCompletion() instanceof JstFieldOrMethodCompletion) {
				// the comment is "//>public f<cursor>static" before function
				// declaration
				commentCompletion.pushScope(ScopeIds.METHOD);
			} else if (ccCtx.getCompletion() == null
					|| ccCtx.getCompletion() instanceof JstKeywordCompletion) {
				commentCompletion.pushScope(ScopeIds.TYPE);
			} else {// the comment is "//<Type::"
				commentCompletion.pushScope(ScopeIds.VAR);
			}
			ccCtx.setCompletion(commentCompletion);

		}
		return ccCtx;
	}

	protected IVjoCcHandler createHandler() {
		return new VjoCcCommentHandler();
	}

	protected void processNode(Node node) {
		Integer position = getPosition(node);
		populateXmlMap(xmlMapping, node, position);
		populateCommentMap(commentMapping, node, position);
	}

	private void populateCommentMap(Map<Integer, String> mapping, Node node,
			Integer position) {
		NodeList childNodes = node.getChildNodes();
		final int len = childNodes.getLength();
		for (int i = 0; i < len; i++) {
			Node child = childNodes.item(i);
			if (commentTag.equalsIgnoreCase(child.getNodeName())) {
				mapping.put(position, child.getChildNodes().item(0)
						.getNodeValue());
			}
		}
	}
}

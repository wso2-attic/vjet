/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletionOnSingleNameReference;
import org.ebayopensource.vjo.meta.VjoConvention;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.SingleNameReference;

public class SingleNameReferenceTranslator extends
		BaseAst2JstTranslator<SingleNameReference, JstIdentifier> {

	private JstIdentifier _jstIdentifier;
	
	@Override
	protected JstIdentifier doTranslate(SingleNameReference expr) {
		_jstIdentifier = null;
		
		String identifier = String.valueOf(expr.getToken());
		JstIdentifier jstIdentifier = new JstIdentifier(identifier);
		if (VjoConvention.getVjoNS().equals(identifier)) {
			IJstType type = JstCache.getInstance()
			.getType(VjoConvention.getVjoNS());
			if (type != null) {
				type = JstTypeHelper.getJstTypeRefType(type);
			}
			jstIdentifier.setType(type);
		}
		JstSource source = TranslateHelper.getSource(expr, m_ctx
				.getSourceUtil());
		jstIdentifier.setSource(source);
		
		_jstIdentifier = jstIdentifier;
		return jstIdentifier;
	}

	@Override
	protected JstCompletion createCompletion(SingleNameReference astNode,
			boolean isAfterSource) {

		JstCompletion completion = null;
		String identifier = String.valueOf(astNode.getToken());
		final BaseJstNode node = _jstIdentifier != null ? _jstIdentifier : m_ctx.getCurrentType();

		if (!(isMessageCallBlock() && isMissing(identifier))) {
			completion = new JstCompletionOnSingleNameReference(node);
			if (!identifier.equals(TranslateHelper.MISSING_TOKEN)) {
				// trim the astNode, get the token before cursor
				int tempInt = m_ctx.getCompletionPos() - astNode.sourceStart;
				if (tempInt > identifier.length() || tempInt < 0) {
					return null;
				} else {
					identifier = identifier.substring(0, m_ctx
							.getCompletionPos()
							- astNode.sourceStart);
				}
				completion.setToken(identifier);
				m_ctx.setCreatedCompletion(true);
			} else {
				completion.setToken("");
				return null;
			}
		}

		return completion;
	}

	private boolean isMessageCallBlock() {
		return m_ctx.getCurrentScope() == ScopeIds.METHOD_CALL;
	}

	private boolean isMissing(String identifier) {
		return TranslateHelper.MISSING_TOKEN.equals(identifier);
	}

	protected int getSourceEnd(SingleNameReference astNode) {
		int sourceEnd = astNode.sourceEnd();
		int realSourceEnd = astNode.sourceStart() + astNode.toString().length()
				- 1;
		if (sourceEnd < realSourceEnd) {
			sourceEnd = realSourceEnd;
		}
		return sourceEnd;
	}
}

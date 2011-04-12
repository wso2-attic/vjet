/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletionOnSingleNameReference;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.ThisReference;

public class ThisReferenceTranslator extends
		BaseAst2JstTranslator<ThisReference, JstIdentifier> {

	private static final String THIS = "this";

	@Override
	protected JstIdentifier doTranslate(ThisReference astNode) {
		JstIdentifier identifier = new JstIdentifier(THIS);
		identifier.setSource(TranslateHelper.getSource(astNode, m_ctx
				.getSourceUtil()));
		return identifier;
	}

	@Override
	protected JstCompletion createCompletion(ThisReference astNode,
			boolean isAfterSource) {
		JstCompletion completion = null;
		JstType node = m_ctx.getCurrentType();

		completion = new JstCompletionOnSingleNameReference(node);
		// Here, What astNode contains is "(this)", so need some preprocess
		String token = TranslateHelper.calculatePrefix(m_ctx.getOriginalSource(),m_ctx.getCompletionPos(),  astNode.sourceStart);
		completion.setToken(token);
		completion.setCompositeToken(THIS);
		m_ctx.setCreatedCompletion(true);

		return completion;
	}
}

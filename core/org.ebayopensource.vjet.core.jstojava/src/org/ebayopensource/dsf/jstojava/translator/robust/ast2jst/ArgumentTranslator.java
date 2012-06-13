/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.meta.IJsCommentMeta;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.eclipse.mod.wst.jsdt.core.ast.IArgument;

public class ArgumentTranslator extends
		BaseAst2JstTranslator<IArgument, JstArg> {

	private IJsCommentMeta m_commentMeta;

	private int m_argIdex;


	public JstArg doTranslate(JstMethod jstMethod, IArgument astArgument) {
		return TranslateHelper.createJstArg(jstMethod, astArgument.toString(), astArgument, m_commentMeta, m_argIdex,m_ctx);
	}

	public void setCommentMetaAndIndex(IJsCommentMeta meta, int argIndex) {
		this.m_commentMeta = meta;
		this.m_argIdex = argIndex;
	}

	@Override
	protected JstArg doTranslate(IArgument astNode) {
		return null;
		// this shouldn't be used but it may be called in a case such as (function(a){}())
	}
}

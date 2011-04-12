/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.ast2jst;

import org.ebayopensource.dsf.json.JsonObject;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.term.JstLiteral;
import org.ebayopensource.dsf.jst.term.RegexpLiteral;
import org.ebayopensource.dsf.jst.term.SimpleLiteral;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletionOnLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CharLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.DoubleLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.FalseLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.IntLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Literal;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.NullLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.RegExLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.StringLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.TrueLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.UndefinedLiteral;

public class LiteralTranslator extends
		BaseAst2JstTranslator<Literal, JstLiteral> {
	
	private JstLiteral lit;

	@Override
	protected JstLiteral doTranslate(Literal literal) {
		assert literal != null;
		
		String value = String.valueOf(literal.source());
		
		if (literal instanceof IntLiteral) {
			lit = SimpleLiteral.getIntLiteral(value);
		} else if (literal instanceof CharLiteral) {
			if (value.startsWith("'") && value.endsWith("'")) {
				// remove ' '
				if (value.length() == 2) {
					value = "";
				}
				else {
					if(value.length()>2){
						value = value.substring(1, value.length() - 1);
					}
				}
			}
			value = JsonObject.escape(value, true);
			lit = SimpleLiteral.getStringLiteral(value);
		} else if (literal instanceof StringLiteral) {
			value = JsonObject.escape(value);
			lit = SimpleLiteral.getStringLiteral( value);
		} else if (literal instanceof TrueLiteral
				|| literal instanceof FalseLiteral) {
			lit = SimpleLiteral.getBooleanLiteral(value);
		} else if (literal instanceof DoubleLiteral) {
			lit = SimpleLiteral.getDoubleLiteral(value);
		} else if (literal instanceof RegExLiteral) {
			lit = new RegexpLiteral(value);
		} else if (literal instanceof NullLiteral) {
			lit = SimpleLiteral.getNullLiteral();
		} else if (literal instanceof UndefinedLiteral) {
			lit = SimpleLiteral.getUndefinedLiteral();
		}
		else {
			lit = new SimpleLiteral(Object.class, JstCache.getInstance().getType(org.ebayopensource.dsf.jsnative.global.Object.class.getSimpleName()), value);
		}
		TranslateHelper.addSourceInfo(literal, lit, m_ctx.getSourceUtil());

		return lit;
	}

	@Override
	protected JstCompletion createCompletion(Literal astNode,
			boolean isAfterSource) {
		if (astNode instanceof NullLiteral) {
			return null;
		}
		JstCompletionOnLiteral literal = null;
//		literal = new JstCompletionOnLiteral(lit);		
//		literal.setToken(astNode.toString());
		return literal;
	}
}

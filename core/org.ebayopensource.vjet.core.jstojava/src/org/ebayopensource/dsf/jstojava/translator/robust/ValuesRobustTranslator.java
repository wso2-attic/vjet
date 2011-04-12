/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust;


import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.ebayopensource.dsf.jstojava.translator.ValuesTranslator;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CharLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Expression;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Literal;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MessageSend;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.StringLiteral;

public class ValuesRobustTranslator extends BaseRobustTranslator {

	public ValuesRobustTranslator(TranslateCtx ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

//	private static final Map<String, Class> VALUES_SUB_TRANSLATORS_MAP = new HashMap<String, Class>();

//	static {
//		//Jack: add protos, inits and props as sub translator
//		VALUES_SUB_TRANSLATORS_MAP.put(VjoKeywords.ENDTYPE,
//				EndTypeRobustTranslator.class);
//		VALUES_SUB_TRANSLATORS_MAP.put(VjoKeywords.PROTOS,
//				ProtosRobustTranslator.class);
//		VALUES_SUB_TRANSLATORS_MAP.put(VjoKeywords.INITS,
//				InitsRobustTranslator.class);
//		VALUES_SUB_TRANSLATORS_MAP.put(VjoKeywords.PROPS,
//				PropsRobustTranslator.class);
//
//	}

//	@Override
//	protected Map<String, Class> getTranslatorMap() {
//		return VALUES_SUB_TRANSLATORS_MAP;
//	}

	public boolean transform() {

		current = astElements.pop();

		if (((MessageSend) current).arguments != null) {
			ValuesTranslator translator = weakTranslator.getProvider().getValuesTranslator();
			Expression expr = ((MessageSend) current).arguments[0];
			if (expr instanceof CharLiteral 
					|| expr instanceof StringLiteral) {
				//.values('ONE,TWO')
				translator.processValues((Literal)expr, jst);
			} else {
				//.values({ ONE: [0, 'one'], TWO: [1, 'two']})
				translator.process(expr, jst);
			}
		}

		lookupEmptyCompletion();

		return super.transform();

	}

}

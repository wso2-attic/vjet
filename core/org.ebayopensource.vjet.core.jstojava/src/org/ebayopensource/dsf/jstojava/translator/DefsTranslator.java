/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.traversal.JstDepthFirstTraversal;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Expression;

public class DefsTranslator extends BasePropsProtosTranslator {
	public DefsTranslator(TranslateCtx ctx) {
		super(ctx);
		type = ScopeIds.DEFS;
	}
	
	@Override
	public void process(Expression expr, JstType jstType) {
		// TODO Auto-generated method stub
		super.process(expr, jstType);
		
		if(m_ctx.hasFunctionTypeRefReplacements()){
			JstDepthFirstTraversal.accept(jstType, new FunctionTypeRefVisitor(m_ctx.getFunctionTypeRefReplacements()));
		}
		
		
		
	}

}

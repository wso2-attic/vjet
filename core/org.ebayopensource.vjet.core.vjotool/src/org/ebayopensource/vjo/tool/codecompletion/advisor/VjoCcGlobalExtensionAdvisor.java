/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstGlobalFunc;
import org.ebayopensource.dsf.jst.IJstGlobalProp;
import org.ebayopensource.dsf.jst.IJstGlobalVar;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;

public class VjoCcGlobalExtensionAdvisor extends AbstractVjoCcAdvisor
	implements IVjoCcAdvisor {
	
	public static final String ID = VjoCcGlobalExtensionAdvisor.class.getName();

	public void advise(VjoCcCtx ctx) {
		String token = ctx.getToken();
		FieldAccessExpr fae = (FieldAccessExpr)ctx.getCompletion().getRealParent();
		JstIdentifier qualifier = (JstIdentifier)fae.getExpr();
		IJstNode binding = qualifier.getJstBinding();

		String globalVarName = null;
		if (binding instanceof IJstGlobalProp) {
			globalVarName = ((IJstGlobalProp)binding).getName().getName();
		} else if (binding instanceof IJstGlobalFunc) {
			globalVarName = ((IJstGlobalFunc)binding).getName().getName();
		}
		if (globalVarName == null) {
			return;
		}
		
		List<IJstNode> extensions =
			ctx.getJstTypeSpaceMgr().getQueryExecutor().getGlobalExtensions(globalVarName);
	
		for (IJstNode node : extensions) {
			if (node instanceof IJstGlobalVar) {
				IJstGlobalVar jsvar = (IJstGlobalVar) node;
				if (jsvar.getName().getName().startsWith(token)) {
					if(jsvar.isFunc()){
						appendData(ctx, jsvar.getFunction());
					} else {
						appendData(ctx, jsvar.getProperty());
					}
				}
			}		
		}	
	}
}
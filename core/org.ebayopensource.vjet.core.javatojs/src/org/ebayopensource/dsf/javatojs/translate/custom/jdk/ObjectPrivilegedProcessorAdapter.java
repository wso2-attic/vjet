/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.custom.jdk;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;

import org.ebayopensource.dsf.javatojs.translate.DataTypeTranslator;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomMethod;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomType;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.PrivilegedProcessorAdapter;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.vjo.meta.VjoConvention;

public class ObjectPrivilegedProcessorAdapter extends PrivilegedProcessorAdapter{
	private static String OBJECT_UTIL_TYPE_SIMPLE_NAME = "ObjectUtil";
	private static String OBJECT_UTIL_TYPE_NAME = VjoConvention.getVjoExtScope() + ".java.lang.ObjectUtil";
	public IExpr processMtdInvocation(
			final ASTNode astNode,
			final JstIdentifier identifier, 
			final IExpr optionalExpr, 
			final List<IExpr> args, 
			final boolean isSuper,
			final BaseJstNode jstNode,
			final CustomType cType,
			final CustomMethod cMtd){
		
		if (optionalExpr == null || identifier == null){
			return null;
		}

		String mtdName = identifier.getName();

		MtdInvocationExpr mtdCall = null;
		List<IExpr> newArgs;
		JstIdentifier ouIdentifier = new JstIdentifier(OBJECT_UTIL_TYPE_NAME);
		ouIdentifier.setJstBinding(JstCache.getInstance().getType(OBJECT_UTIL_TYPE_NAME, false));
		if (mtdName.equals("equals")) {
			//There should be only one param
			newArgs = new ArrayList<IExpr>(2);
			newArgs.add(optionalExpr);
			newArgs.add(args.get(0));
	
			mtdCall = new MtdInvocationExpr(identifier);
			mtdCall.setQualifyExpr(ouIdentifier);
			mtdCall.setArgs(newArgs);
			
		} else if (mtdName.equals("hashCode")) {
			//There should be only one param
			newArgs = new ArrayList<IExpr>(1);
			newArgs.add(optionalExpr);
	
			mtdCall = new MtdInvocationExpr(identifier);
			mtdCall.setQualifyExpr(ouIdentifier);
			mtdCall.setArgs(newArgs);
		} 
		if (mtdCall != null) {
			DataTypeTranslator dataTypeTranslator = TranslateCtx.ctx().getProvider().getDataTypeTranslator();
			IJstType jstOUType = dataTypeTranslator.toJstType(OBJECT_UTIL_TYPE_NAME, jstNode.getOwnerType());
			dataTypeTranslator.addImport(jstOUType, jstNode.getOwnerType(), OBJECT_UTIL_TYPE_SIMPLE_NAME);
			
			// fix bugzilla 4676:
			// http://quickbugstage.arch.ebay.com/show_bug.cgi?id=4676
			if (mtdName.equals("hashCode")) {
				IJstMethod equalMtd = jstOUType.getMethod("hashCode");
				identifier.setJstBinding(equalMtd);
			} else if(mtdName.equals("equals")){
				IJstMethod equalMtd = jstOUType.getMethod("equals");
				identifier.setJstBinding(equalMtd);
			}
		}
		
		return mtdCall;
		
	}
}

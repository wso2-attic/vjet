/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.jstvalidator.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jsgen.shared.ids.FieldProbIds;
import org.ebayopensource.dsf.jsgen.shared.ids.VarProbIds;
import org.ebayopensource.dsf.jsgen.shared.jstvalidator.ValidationCtx;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.declaration.JstArg;

public class UniquenessValidator {


	public static void assertUniqueMembers(ValidationCtx ctx,
			List<IJstProperty> props, List<? extends IJstMethod> methods) {
		// check if static property names are duplicated in static method names
		if(props.size()==0 && methods.size()==0){
			return;
		}
		
		for (IJstProperty p : props) {

			IJstMethod instanceMethod = JstBasicUtils.getMethod(p.getName()
					.getName(), methods);
			if (instanceMethod != null) {
				ctx.getProblems().add(
						ProblemUtil.problem(FieldProbIds.DuplicateField,
								instanceMethod, ctx));
			}
		}

		// check if static method names are duplicated in instance property
		// names
		for (IJstMethod m : methods) {
			IJstProperty instanceMethod = JstBasicUtils.getProp(m.getName()
					.getName(), props);
			if (instanceMethod != null) {
				ctx.getProblems().add(
						ProblemUtil.problem(FieldProbIds.DuplicateField,
								instanceMethod, ctx));
			}
		}
	}
	
	public static void assertUniqueParams(ValidationCtx ctx, IJstMethod method){
		Map<String,IJstNode> nodes = new HashMap<String, IJstNode>();
		List<JstArg> arg = method.getArgs();
//		JstBlock b = method.getBlock();
		for(JstArg a:arg){
			if(nodes.containsKey(a.getName())){
				ctx.getProblems().add(ProblemUtil.problem(VarProbIds.RedefinedLocal, a, ctx));
			}else{
				nodes.put(a.getName(), a);				
			}
		}
		
//		JstBlock b = method.getBlock();
//		VarTable t = b.getVarTable();
//		for(String n:t.getVars()){
//			if(nodes.containsKey(n)){
//				ctx.getProblems().add(ProblemUtil.problem(VarProbIds.RedefinedLocal, a, ctx));
//			}else{
//				nodes.put(n, a);				
//			}
//		}
//		
	}

}

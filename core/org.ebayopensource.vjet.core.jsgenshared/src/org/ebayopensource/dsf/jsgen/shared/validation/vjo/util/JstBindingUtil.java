/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.util;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.expr.FuncExpr;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;

public class JstBindingUtil {

	public static IJstNode getJstBinding(final IJstNode jstNode){
		if(jstNode == null){
			return null;
		}
		else if(jstNode instanceof JstIdentifier){
			return getJstBinding((JstIdentifier)jstNode);
		}
		else if(jstNode instanceof FieldAccessExpr){
			final FieldAccessExpr jstFieldAccessExpr = (FieldAccessExpr)jstNode;
			return getJstBinding(jstFieldAccessExpr.getName());
		}
		else if(jstNode instanceof MtdInvocationExpr){
			final MtdInvocationExpr jstMtdInvocationExpr = (MtdInvocationExpr)jstNode;
			return jstMtdInvocationExpr.getMethod();
		}
		//enhancement by huzhou for local var as function declared bindings
		else if(jstNode instanceof FuncExpr){
			final FuncExpr funcExpr = (FuncExpr)jstNode;
			return funcExpr.getFunc();
		}
		else{
			return null;
		}
	}
	
	public static IJstNode getJstBinding(final JstIdentifier jstIdentifier){
		if(jstIdentifier == null){
			return null;
		}
		
		return jstIdentifier.getJstBinding();
	}
}

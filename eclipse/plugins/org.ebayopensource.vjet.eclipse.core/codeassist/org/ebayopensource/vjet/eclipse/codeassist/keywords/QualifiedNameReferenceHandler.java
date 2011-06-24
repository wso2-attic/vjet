/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.keywords;

import java.util.List;

import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.expr.ObjCreationExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletionOnQualifiedNameReference;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.CompletionProposal;

public class QualifiedNameReferenceHandler implements ICompletionHandler {

	private MemberAccessHandler handler = new MemberAccessHandler();

	public void complete(ISourceModule module, int position,
			JstCompletion completion, List<CompletionProposal> list) {

		String token = completion.getToken();
		Object jstNode = completion.getRealParent();

		if (jstNode instanceof FieldAccessExpr) {

			// token = ((FieldAccessExpr) jstNode).getName().getName();
			String fullCompletionToken = completion.getToken();
			token = fullCompletionToken.substring(fullCompletionToken
					.lastIndexOf(CompletionConstants.DOT) + 1);
			jstNode = ((FieldAccessExpr) jstNode).getExpr();
		} else if (jstNode instanceof MtdInvocationExpr) {
			jstNode = ((MtdInvocationExpr) jstNode).getQualifyExpr();
		} else if (jstNode instanceof JstIdentifier){
			String fullCompletionToken = completion.getToken();
			token = fullCompletionToken.substring(fullCompletionToken
					.lastIndexOf(CompletionConstants.DOT) + 1);
		}
		
		if (jstNode instanceof ObjCreationExpr){
			ObjCreationExpr expr = (ObjCreationExpr) jstNode;			
			jstNode = expr.getInvocationExpr();
		}
		
		CompletionContext.setVariableContext(true);
		completion.setToken(token);
		handler.init(module, position, completion, list);
		handler.complete(jstNode);

	}

	public Class getCompletionClass() {
		return JstCompletionOnQualifiedNameReference.class;
	}

}

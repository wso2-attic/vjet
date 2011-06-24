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

import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.eclipse.dltk.mod.core.CompletionProposal;
import org.eclipse.dltk.mod.core.IMember;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.IType;

public class InstanceMemberAccessHandler extends MethodCompletionHandler {
	@Override
	protected void addLocalVarProposals(IMethod method, String token,
			List<CompletionProposal> list, int position) {
		// TODO Don't add local vars
	}

	@Override
	protected TypeMembersRequestor createTypeMemberRequestor(
			JstCompletion completion, String token, IType type) {

		//CompletionContext.isInstanceContext = true;
		TypeMembersRequestor requestor;
		requestor = new TypeMembersRequestor(token, false, type, completion) {
			@Override
			protected boolean accept(IMember member, int flags) {
				boolean accept = super.accept(member, flags);
				return accept && !isStatic(flags);
			}
		};
		return requestor;
	}
}

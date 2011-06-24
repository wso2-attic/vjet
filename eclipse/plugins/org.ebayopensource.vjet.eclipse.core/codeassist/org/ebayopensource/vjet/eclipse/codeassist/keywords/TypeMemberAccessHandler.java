/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.keywords;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.CompletionProposal;

public class TypeMemberAccessHandler implements ICompletionHandler {

	public void complete(ISourceModule module, int position,
			JstCompletion completion, List<CompletionProposal> list) {

		IJstType ownerType = completion.getOwnerType();
		String token = completion.getToken();
		List<String> types = getTypeList(ownerType);
		
		for(String type: types) {
			CompletionProposal proposal = CompletionProposal.create(
							CompletionProposal.TYPE_REF, position);
			proposal.setName(type.toCharArray());
			proposal.setCompletion(type.toCharArray());
			if ((token != null)
					&& ((token.length() > 0 && type.startsWith(token)) || token
							.length() == 0)) {
				list.add(proposal);
			}
		}
	}

	private List<String> getTypeList(IJstType type) {
		List<String> types = new ArrayList<String>();
		types.add(type.getSimpleName());
		
		// imports
		Set<String> collection = type.getImportsMap().keySet();
		for (String alias : collection) {
			if (!"*".equals(alias)) {
				types.add(alias);				
			} else {
				// gets all types from current package
				
			}
		}

		// supeclasses
		IJstType superType = type.getExtend();
		if (superType != null && !types.contains(superType.getSimpleName())) {
			types.add(superType.getSimpleName());
		}
		
		// mixins
		List<? extends IJstTypeReference> mixinTypes = type.getMixinsRef();
		for (IJstTypeReference mixinType : mixinTypes) {
			types.add(mixinType.getOwnerType().getName());
		}
		
		return types;
	}
	
	public Class getCompletionClass() {
		// TODO Auto-generated method stub
		return null;
	}
}

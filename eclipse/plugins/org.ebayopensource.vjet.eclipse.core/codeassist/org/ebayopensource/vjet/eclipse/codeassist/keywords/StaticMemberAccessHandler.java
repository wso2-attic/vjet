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

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.CompletionProposal;
import org.eclipse.dltk.mod.core.IMember;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.IType;

public class StaticMemberAccessHandler extends MethodCompletionHandler {

	private IJstType jstType;

	private String typeName;

	@Override
	protected TypeMembersRequestor createTypeMemberRequestor(
			JstCompletion completion, String token, IType type) {

		CompletionContext.setStaticContext(true);
		TypeMembersRequestor requestor;
		requestor = new TypeMembersRequestor(token, false, type, completion) {
			@Override
			protected boolean accept(IMember member, int flags) {

				String name = member.getDeclaringType().getElementName();
				boolean accept = super.accept(member, flags)
						&& (getTypeName().equals(name) || name
								.equals(TypeSpaceMgr.GLOBAL));
				
				// FIXME: Why instanceOf isn't static property of Global Object?
				return accept
						&& (isStatic(flags) || member.getElementName()
								.equalsIgnoreCase("instanceOf"));
			}
		};
		return requestor;
	}

	@Override
	protected void addLocalVarProposals(IMethod method, String token,
			List<CompletionProposal> list, int position) {
		// TODO don't add local vars to this.vjo
	}

	public IJstType getJstType() {
		return jstType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setJstType(IJstType jstType) {
		this.jstType = jstType;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	protected IType getType(ISourceModule module) {
		IType type = null;
		if (jstType != null) {
			String nullPackagePrefix = "null.";
			String typeName = jstType.getName();
			if (typeName.startsWith(nullPackagePrefix)) {
				typeName = typeName.substring(nullPackagePrefix.length());
			}
			type = CodeassistUtils.findResourceType(module, typeName);
			if (type == null) { // add by Jack, to make sure, w
				type = super.getType(module);
			}
		} else {
			type = super.getType(module);
		}
		return type;
	}
}

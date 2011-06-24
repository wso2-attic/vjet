/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.keywords;

import static org.eclipse.dltk.mod.core.CompletionProposal.METHOD_REF;

import java.util.List;

import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstComletionOnMessageSend;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.CompletionProposal;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;

/**
 * Creates object methods completions
 * 
 * 
 */
public class MessageSendCompletionHandler extends
		FieldOrMethodCompletionHandler {

	private String typeName;

	private Object jstNode;

	/* (non-Javadoc)
	 * @see org.ebayopensource.vjet.eclipse.codeassist.keywords.FieldOrMethodCompletionHandler#complete(org.eclipse.dltk.mod.compiler.env.ISourceModule, int, org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion, java.util.List)
	 */
	public void complete(ISourceModule module, int position,
			JstCompletion completion, List<CompletionProposal> list) {

		this.jstNode = completion.getRealParent();
		this.typeName = completion.getOwnerType().getName();

		MethodRequestor requestor;
		requestor = new MethodRequestor(completion.getToken(), true) {
			@Override
			protected boolean acceptStatic(int flags) {
				return true;
			}
		};
		requestMethods(requestor, module);

		List<IMethod> methods = requestor.getMethods();
		for (IMethod method : methods) {

			CompletionProposal data;
			data = CompletionProposal.create(METHOD_REF, position);
			data.setCompletion("".toCharArray());
			data.setReplaceRange(position, position);
			data.setName(completion.getToken().toCharArray());

			try {

				String[] paramNames = method.getParameters();
				char[][] params = createParameters(paramNames);
				data.setParameterNames(params);

			} catch (ModelException e) {
				DLTKCore.error(e.toString(), e);
			}

			data.setModelElement(method);
			list.add(data);
		}
	}

	public Class getCompletionClass() {
		return JstComletionOnMessageSend.class;
	}

	@Override
	protected void requestMethods(MethodRequestor requestor,
			ISourceModule module) {
		IType type = FieldExpressionHandler.getType(module, jstNode, typeName);
		getMethods(requestor, type);
	}

}

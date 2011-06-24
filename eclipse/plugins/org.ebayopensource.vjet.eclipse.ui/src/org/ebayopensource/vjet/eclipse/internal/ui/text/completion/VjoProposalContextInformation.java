/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.text.completion;

import org.ebayopensource.vjet.eclipse.core.IJSMethod;
import org.eclipse.dltk.mod.core.CompletionProposal;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.ui.text.completion.ProposalContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.swt.graphics.Image;

public class VjoProposalContextInformation extends ProposalContextInformation {

	private static final char COMMA = ',';
	private static final String SPACE = " ";
	private final String contextDisplayString;
	private final String informationDisplayString;
	private final Image image;
	private int position;

	public VjoProposalContextInformation(CompletionProposal proposal) {
		super(proposal);
		String res = createParametersList(proposal);
		informationDisplayString = res;
		image = null;
		if (proposal.getCompletion().length == 0)
			position = proposal.getCompletionLocation() + 1;
		else
			position = -1;
		contextDisplayString = res;
	}

	private String createParametersList(CompletionProposal proposal) {
		String res = null;
		IModelElement element = proposal.getModelElement();
		if (element instanceof IJSMethod) {
			res = createParameterList(element);
		} else {
			res = createParameterList(proposal);
		}
		return res;
	}

	private String createParameterList(IModelElement element) {
		String res = null;
		IJSMethod method = (IJSMethod) element;
		try {
			res = createParameterList(method);
		} catch (ModelException e) {
			e.printStackTrace();
		}
		return res;
	}

	private String createParameterList(IJSMethod method) throws ModelException {
		
		String[] names = method.getParameters();
		String[] types = method.getParameterTypes();
		
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < types.length; i++) {
			String type = types[i];
			String name = names[i];

			builder.append(type);
			builder.append(SPACE);
			builder.append(name);

			if (i != types.length - 1)
				builder.append(COMMA);
		}
		
		return builder.toString();
	}

	private String createParameterList(CompletionProposal proposal) {
		StringBuffer bf = new StringBuffer();
		char[][] pNames = proposal.findParameterNames(null);
		for (int a = 0; a < pNames.length; a++) {
			bf.append(pNames[a]);
			if (a != pNames.length - 1)
				bf.append(',');
		}
		return bf.toString();
	}

	/*
	 * @see IContextInformation#equals
	 */
	public boolean equals(Object object) {
		if (object instanceof IContextInformation) {
			IContextInformation contextInformation = (IContextInformation) object;
			boolean equals = getInformationDisplayString().equalsIgnoreCase(
					contextInformation.getInformationDisplayString());
			if (getContextDisplayString() != null)
				equals = equals
						&& getContextDisplayString().equalsIgnoreCase(
								contextInformation.getContextDisplayString());
			return equals;
		}
		return false;
	}

	/*
	 * @see IContextInformation#getInformationDisplayString()
	 */
	public String getInformationDisplayString() {
		return informationDisplayString;
	}

	/*
	 * @see IContextInformation#getImage()
	 */
	public Image getImage() {
		return image;
	}

	/*
	 * @see IContextInformation#getContextDisplayString()
	 */
	public String getContextDisplayString() {
		return contextDisplayString;
	}

	/*
	 * @see IContextInformationExtension#getContextInformationPosition()
	 */
	public int getContextInformationPosition() {
		return position;
	}

	/**
	 * Sets the context information position.
	 * 
	 * @param position
	 *            the new position, or -1 for unknown.
	 * 
	 */
	public void setContextInformationPosition(int position) {
		this.position = position;
	}

}

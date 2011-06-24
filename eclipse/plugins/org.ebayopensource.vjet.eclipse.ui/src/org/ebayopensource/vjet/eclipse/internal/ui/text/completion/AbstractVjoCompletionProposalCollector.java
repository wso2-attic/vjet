/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.vjet.eclipse.internal.ui.text.completion;

import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.codeassist.keywords.CompletionContext;
import org.ebayopensource.vjet.eclipse.codeassist.keywords.IVjoCompletionData;
import org.ebayopensource.vjet.eclipse.codeassist.keywords.VjoKeywordFactory;
import org.ebayopensource.vjet.eclipse.core.search.matching.ICategoryRequestor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.mod.core.CompletionProposal;
import org.eclipse.dltk.mod.core.IMember;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.internal.core.JSSourceField;
import org.eclipse.dltk.mod.internal.core.NativeVjoSourceModule;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.dltk.mod.ui.text.completion.CompletionProposalLabelProvider;
import org.eclipse.dltk.mod.ui.text.completion.FieldProposalInfo;
import org.eclipse.dltk.mod.ui.text.completion.IScriptCompletionProposal;
import org.eclipse.dltk.mod.ui.text.completion.MethodDeclarationCompletionProposal;
import org.eclipse.dltk.mod.ui.text.completion.ScriptCompletionProposal;
import org.eclipse.dltk.mod.ui.text.completion.ScriptCompletionProposalCollector;
import org.eclipse.dltk.mod.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.dltk.mod.ui.text.completion.VjoCompletionProposalLabelProvider;
import org.eclipse.dltk.mod.ui.text.completion.VjoOverrideCompletionProposal;
import org.eclipse.dltk.mod.ui.text.completion.VjoScriptMethodCompletionProposal;
import org.eclipse.swt.graphics.Image;

/**
 * 
 * 
 * 
 */
// FIXME fill this class
public abstract class AbstractVjoCompletionProposalCollector extends
		ScriptCompletionProposalCollector implements ICategoryRequestor {
	protected final static char[] VAR_TRIGGER = new char[] { '\t', ' ', '=',
			';', '.' };

	public AbstractVjoCompletionProposalCollector(ISourceModule cu) {
		super(cu);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.text.completion.ScriptCompletionProposalCollector#createLabelProvider()
	 */
	@Override
	protected CompletionProposalLabelProvider createLabelProvider() {
		return new VjoCompletionProposalLabelProvider();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.text.completion.ScriptCompletionProposalCollector#createOverrideCompletionProposal(org.eclipse.dltk.mod.core.IScriptProject,
	 *      org.eclipse.dltk.mod.core.ISourceModule, java.lang.String,
	 *      java.lang.String[], int, int, java.lang.String, java.lang.String)
	 */
	@Override
	protected ScriptCompletionProposal createOverrideCompletionProposal(
			IScriptProject scriptProject, ISourceModule compilationUnit,
			String name, String[] paramTypes, int start, int length,
			String label, String string) {

		return new VjoOverrideCompletionProposal(scriptProject,
				compilationUnit, name, paramTypes, start, length, label, string);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.text.completion.ScriptCompletionProposalCollector#createScriptCompletionProposal(java.lang.String,
	 *      int, int, org.eclipse.swt.graphics.Image, java.lang.String, int)
	 */
	@Override
	protected ScriptCompletionProposal createScriptCompletionProposal(
			String completion, int replaceStart, int length, Image image,
			String displayString, int i) {
		VjoCompletionProposal javaScriptCompletionProposal = new VjoCompletionProposal(
				completion, replaceStart, length, image, displayString, i);

		return javaScriptCompletionProposal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.text.completion.ScriptCompletionProposalCollector#createScriptCompletionProposal(java.lang.String,
	 *      int, int, org.eclipse.swt.graphics.Image, java.lang.String, int,
	 *      boolean)
	 */
	@Override
	protected ScriptCompletionProposal createScriptCompletionProposal(
			String completion, int replaceStart, int length, Image image,
			String displayString, int i, boolean isInDoc) {
		VjoCompletionProposal javaScriptCompletionProposal = new VjoCompletionProposal(
				completion, replaceStart, length, image, displayString, i,
				isInDoc);
		return javaScriptCompletionProposal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.text.completion.ScriptCompletionProposalCollector#createScriptContentAssistInvocationContext(org.eclipse.dltk.mod.core.ISourceModule)
	 */
	@Override
	protected ScriptContentAssistInvocationContext createScriptContentAssistInvocationContext(
			ISourceModule sourceModule) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.text.completion.ScriptCompletionProposalCollector#getVarTrigger()
	 */
	@Override
	protected char[] getVarTrigger() {
		return VAR_TRIGGER;
	}

	protected IScriptCompletionProposal createScriptCompletionProposal(
			CompletionProposal proposal) {

		IScriptCompletionProposal scriptCompletionProposal = super
				.createScriptCompletionProposal(proposal);

		if (proposal.getKind() == CompletionProposal.KEYWORD) {

			VjoCompletionProposal vjoCompletionProposal = (VjoCompletionProposal) scriptCompletionProposal;
			IVjoCompletionData vjoKeyword = VjoKeywordFactory
					.getKeywordByName(vjoCompletionProposal.getDisplayString());
			if (vjoKeyword != null) {
				vjoCompletionProposal.setCursorPosition(vjoKeyword
						.getCursorOffsetAfterCompletion());
			}

		}

		if (scriptCompletionProposal instanceof VjoCompletionProposal) {
			VjoCompletionProposal p = (VjoCompletionProposal) scriptCompletionProposal;
			p.setExtraInfo(proposal.extraInfo);
		}

		return scriptCompletionProposal;

	}

	@Override
	public void accept(CompletionProposal proposal) {
		if (proposal.getKind() == CompletionProposal.POTENTIAL_METHOD_DECLARATION) {
			acceptPotentialMethodDeclaration(proposal);
		}
		super.accept(proposal);
	}

	private void acceptPotentialMethodDeclaration(CompletionProposal proposal) {
		if (getSourceModule() == null)
			return;

		String prefix = String.valueOf(proposal.getName());
		int completionStart = proposal.getReplaceStart();
		int completionEnd = proposal.getReplaceEnd();
		int relevance = computeRelevance(proposal);

		try {
			IModelElement element = getSourceModule().getElementAt(
					proposal.getCompletionLocation() + 1);
			if (element != null) {
				IType type = (IType) element.getAncestor(IModelElement.TYPE);
				if (type != null) {
					// GetterSetterCompletionProposal.evaluateProposals(type,
					// prefix, completionStart, completionEnd - completionStart,
					// relevance + 1, fSuggestedMethodNames, fJavaProposals);
					MethodDeclarationCompletionProposal.evaluateProposals(type,
							prefix, completionStart, completionEnd
									- completionStart, relevance,
							fSuggestedMethodNames, fScriptProposals,
							isStaticBlock(proposal));
				}
			}
		} catch (CoreException e) {
			DLTKUIPlugin.log(e);
		}
	}

	private boolean isStaticBlock(CompletionProposal proposal) {
		boolean isStaticBlock = false;
		if (proposal.extraInfo instanceof Boolean) {
			isStaticBlock = (Boolean) proposal.extraInfo;
		}
		return isStaticBlock;
	}

	@Override
	protected IScriptCompletionProposal createMethodReferenceProposal(
			CompletionProposal methodProposal) {
		VjoScriptMethodCompletionProposal proposal = new VjoScriptMethodCompletionProposal(
				methodProposal, getInvocationContext());
		proposal.setSourceModule(getSourceModule());
		adaptLength(proposal, methodProposal);
		return proposal;
	}

	protected IScriptCompletionProposal createFieldProposal(
			CompletionProposal proposal, CompletionContext context) {
		String completion = createCompletionString(proposal, context);
		int start = proposal.getReplaceStart();
		int length = getLength(proposal);
		String label = getLabelProvider().createLabelWithTypeAndDeclaration(
				proposal);
		Image image = getImage(getLabelProvider().createFieldImageDescriptor(
				proposal));
		int relevance = computeRelevance(proposal);
		// CompletionContext context = getContext();
		ScriptCompletionProposal scriptProposal = createScriptCompletionProposal(
				completion, start, length, image, label, relevance, /*
																	 * context
																	 * .isInDoc ()
																	 */false);
		if (fScriptProject != null)
			scriptProposal.setProposalInfo(new FieldProposalInfo(
					fScriptProject, proposal));
		scriptProposal.setTriggerCharacters(getVarTrigger());
		return scriptProposal;
	}

	private String createCompletionString(CompletionProposal proposal,
			CompletionContext completionContext) {
		IMember member = (IMember) proposal.getModelElement();
		StringBuffer buffer = new StringBuffer();
		if (CodeassistUtils.isStatic(member)) {
			buffer.append(CodeassistUtils.getCompletionToken(member,
					getSourceModule()));
		} else if (!CodeassistUtils.isStatic(member)
				&& !completionContext.isInstanceContext()
				&& !isNativeObject(member)) {
			buffer.append(CodeassistUtils.THIS_STATIC);
		}
		buffer.append(proposal.getCompletion());
		return buffer.toString();
	}

	private static boolean isNativeObject(IMember member) {
		return member.getAncestor(IModelElement.SOURCE_MODULE) instanceof NativeVjoSourceModule;
	}

	// eBay mod start
	// transfer relevance info for proposal;
	// eBay mod end
	protected int computeRelevance(CompletionProposal proposal) {
		int baseRelevance = CodeassistUtils.getRelevance(proposal);
		switch (proposal.getKind()) {
		case CompletionProposal.LABEL_REF:
			return baseRelevance + 0;
		case CompletionProposal.KEYWORD:
			return baseRelevance + 1;
		case CompletionProposal.PACKAGE_REF:
			return baseRelevance + 2;
		case CompletionProposal.TYPE_REF:
			return baseRelevance + 3;
		case CompletionProposal.METHOD_REF:
		case CompletionProposal.METHOD_NAME_REFERENCE:
		case CompletionProposal.METHOD_DECLARATION:
			return baseRelevance + 4;
		case CompletionProposal.POTENTIAL_METHOD_DECLARATION:
			return baseRelevance + 4 /* + 99 */;
		case CompletionProposal.FIELD_REF:
			return baseRelevance + 5;
		case CompletionProposal.LOCAL_VARIABLE_REF:
		case CompletionProposal.VARIABLE_DECLARATION:
			return baseRelevance + 6;
		default:
			return baseRelevance;
		}
		// eBay mod end

	}
	
public String computeReplacementString(
		CompletionProposal proposal) {
	IModelElement element = proposal.getModelElement();
	if (element instanceof JSSourceField) {
		JSSourceField method = (JSSourceField)element;
		StringBuffer buffer = new StringBuffer();
		if (CodeassistUtils.isStatic(method)) {
			buffer.append(CodeassistUtils.getCompletionToken(method,
					method.getSourceModule()));
		} else if (!CodeassistUtils.isStatic(method) && !CompletionContext.isInstanceContext()
				&& !isNativeObject(method)) {
			buffer.append(CodeassistUtils.THIS_STATIC);
		}
		return buffer.append(proposal.getCompletion()).toString();
		
	} else {
		return String.valueOf(proposal.getCompletion());
	}
	
}
	
	protected IScriptCompletionProposal createFieldProposal(
					CompletionProposal proposal) {
		String completion = computeReplacementString(proposal);
		int start = proposal.getReplaceStart();
		int length = getLength(proposal);
		String label = getLabelProvider().createLabelWithTypeAndDeclaration(
				proposal);
		Image image = getImage(getLabelProvider().createFieldImageDescriptor(
				proposal));
		int relevance = computeRelevance(proposal);
		// CompletionContext context = getContext();
		ScriptCompletionProposal scriptProposal = createScriptCompletionProposal(
				completion, start, length, image, label, relevance, /*
				 * context
				 * .isInDoc ()
				 */false);
		if (fScriptProject != null)
			scriptProposal.setProposalInfo(new FieldProposalInfo(
					fScriptProject, proposal));
		scriptProposal.setTriggerCharacters(getVarTrigger());
		return scriptProposal;
	}
}

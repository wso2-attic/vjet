/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.mod.ui.text.completion;

import org.ebayopensource.vjet.eclipse.codeassist.keywords.FieldOrMethodCompletionHandler;
import org.ebayopensource.vjet.eclipse.codeassist.keywords.FieldOrMethodCompletionHandler.MethodCompletionExtraInfo;
import org.ebayopensource.vjet.eclipse.core.IJSMethod;
import org.ebayopensource.vjet.eclipse.ui.VjoElementImageDescriptor;
import org.ebayopensource.vjet.eclipse.ui.VjoElementImageProvider;
import org.eclipse.dltk.mod.core.CompletionProposal;
import org.eclipse.dltk.mod.core.Flags;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.ui.ScriptElementImageDescriptor;
import org.eclipse.dltk.mod.ui.ScriptElementImageProvider;
import org.eclipse.jface.resource.ImageDescriptor;

public class VjoCompletionProposalLabelProvider extends
		CompletionProposalLabelProvider {

	private static final char COMMA = ',';
	private static final String SPACE = " ";


//	@Override
//	public ImageDescriptor createImageDescriptor(CompletionProposal proposal) {
//		ImageDescriptor descriptor;
//		switch (proposal.getKind()) {
//		case CompletionProposal.TYPE_REF:
//			descriptor = VjoElementImageProvider.getTypeImageDescriptor(
//					proposal.getFlags(), false);
//			break;
//		case CompletionProposal.FIELD_REF:
//			descriptor = VjoElementImageProvider.getFieldImageDescriptor(proposal.getFlags());
//			break;
//		default: 
//			return super.createImageDescriptor(proposal);
//		}
//		
//		return decorateImageDescriptor(descriptor, proposal);
//	}
	
	protected ImageDescriptor createTypeImageDescriptor(CompletionProposal proposal) {
		final int flags= proposal.getFlags();
		return decorateImageDescriptor(VjoElementImageProvider.getTypeImageDescriptor(flags, false), proposal);
	}
	
	/**
	 * Returns a version of <code>descriptor</code> decorated according to the
	 * passed <code>modifier</code> flags.
	 * 
	 * @param descriptor
	 *            the image descriptor to decorate
	 * @param proposal
	 *            the proposal
	 * @return an image descriptor for a method proposal
	 * @see Flags
	 */
	public ImageDescriptor decorateImageDescriptor(ImageDescriptor descriptor,
			CompletionProposal proposal) {
//		int adornments = 0;
		int adornments = ScriptElementImageProvider.computeAdornmentFlags(
				proposal.getModelElement(),
				ScriptElementImageProvider.SMALL_ICONS
						| ScriptElementImageProvider.OVERLAY_ICONS);

		if (proposal.isConstructor()) {
			adornments |= ScriptElementImageDescriptor.CONSTRUCTOR;
		}
		return new VjoElementImageDescriptor(descriptor, adornments,
				VjoElementImageProvider.SMALL_SIZE);
	}
	
	@Override
	protected String createSimpleLabelWithType(CompletionProposal proposal) {
		StringBuffer nameBuffer= new StringBuffer();
		
		String name = super.createSimpleLabelWithType(proposal);
		nameBuffer.append(name);
		
		if (proposal.extraInfo != null && proposal.extraInfo instanceof String) {
			// add the type
			nameBuffer.append(" ");
			nameBuffer.append(proposal.extraInfo);
		}
		return nameBuffer.toString();
	}
	
	@Override
	protected String createMethodProposalLabel(CompletionProposal methodProposal) {
		StringBuffer nameBuffer= new StringBuffer();
		
		String name = super.createMethodProposalLabel(methodProposal);
		nameBuffer.append(name);
		
		if (methodProposal.extraInfo != null && methodProposal.extraInfo instanceof String) {
			// add a return type
			nameBuffer.append(" ");
			nameBuffer.append(methodProposal.extraInfo);
		}
		return nameBuffer.toString();
	}
	
	
	@Override
	public String createLabelWithTypeAndDeclaration(CompletionProposal proposal) {
		StringBuffer nameBuffer = new StringBuffer();
		
		String name = super.createLabelWithTypeAndDeclaration(proposal);
		nameBuffer.append(name);
		
		if (proposal.extraInfo != null && proposal.extraInfo instanceof String) {
			// add a return type
			nameBuffer.append(" ");
			nameBuffer.append(proposal.extraInfo);
		}
		return nameBuffer.toString();
	}
	
	@Override
	protected String createOverrideMethodProposalLabel(
			CompletionProposal methodProposal) {
		StringBuffer nameBuffer= new StringBuffer();
		char[][] parameterNames = methodProposal.findParameterNames(null);
		char[][] parameterTypes = null;
		String returnType = null;
		String inType = null;
		if (methodProposal.extraInfo != null && methodProposal.extraInfo instanceof FieldOrMethodCompletionHandler.MethodCompletionExtraInfo) {
			FieldOrMethodCompletionHandler.MethodCompletionExtraInfo info = (MethodCompletionExtraInfo) methodProposal.extraInfo;
			if (info.parameterTypes != null) {
				parameterTypes = new char[info.parameterTypes.length][];
				for (int i = 0; i < info.parameterTypes.length; i++) {
					parameterTypes[i] = info.parameterTypes[i].toCharArray();
				}
			}
			returnType = info.returnType;
			inType = info.inType;
		}
		
		// method name
		nameBuffer.append(methodProposal.getName());
		
		// parameters
		nameBuffer.append('(');
		if (parameterTypes != null && parameterNames != null && parameterNames.length == parameterTypes.length) {
			appendParameterSignature(nameBuffer, parameterTypes, parameterNames);
		}
		nameBuffer.append(")  "); //$NON-NLS-1$
		
		// return type
		nameBuffer.append(returnType);

		// declaring type
		nameBuffer.append(" - "); //$NON-NLS-1$

		nameBuffer.append("Override method in '").append(inType).append("'");

		return nameBuffer.toString();
		
	}
		
	
	@Override
	protected StringBuffer appendUnboundedParameterList(StringBuffer buffer,
			CompletionProposal methodProposal) {
		
		StringBuffer buff = buffer;
		
		IModelElement element = methodProposal.getModelElement();
		if (element instanceof IJSMethod) {
			buff = appendUnboundedParameterList(buffer, element);
		}else{
			buff = super.appendUnboundedParameterList(buffer, methodProposal); 
		}
		return buff; 
	}

	private StringBuffer appendUnboundedParameterList(StringBuffer buffer,
			IModelElement element)  {
		IJSMethod method = (IJSMethod) element;
		try {
			buffer = appendUnboundedParameterList(buffer, method);
		} catch (ModelException e) {		
			e.printStackTrace();
		}
		return buffer;
	}

	private StringBuffer appendUnboundedParameterList(StringBuffer buffer,
			IJSMethod method) throws ModelException {
		String[] names = method.getParameters();
		String[] types = method.getParameterTypes();
		for (int i = 0; i < types.length; i++) {				
			if (i > 0) {
				buffer.append(COMMA);
				buffer.append(SPACE);
			}			
			buffer.append(types[i]);
			buffer.append(SPACE);
			buffer.append(names[i]);
		}
		return buffer;
	}
	
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.mod.ui.text.completion;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjet.eclipse.codeassist.keywords.FieldOrMethodCompletionHandler;
import org.ebayopensource.vjet.eclipse.core.IJSType;
import org.ebayopensource.vjet.eclipse.core.IVjoSourceModule;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.ui.DLTKPluginImages;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension4;
import org.eclipse.jface.text.contentassist.IContextInformation;

public class MethodDeclarationCompletionProposal extends
		ScriptTypeCompletionProposal implements ICompletionProposalExtension4 {

	// modifiers
	private static final String PRIVATE_MODIFIER = "private";

	private static final String PROTECTED_MODIFIER = "protected";

	private static final String PUBLIC_MODIFIER = "public";

	public static void evaluateProposals(IType type, String prefix, int offset,
			int length, int relevance, Set suggestedMethods, Collection result,
			boolean isStatic) throws CoreException {
		IMethod[] methods = type.getMethods();
		if (type instanceof IJSType && !((IJSType) type).isInterface()) {
			final String constructorName = "constructs";
			if (constructorName.startsWith(prefix)
					&& !hasMethod(methods, constructorName)
					&& suggestedMethods.add(constructorName)) {
				result.add(new MethodDeclarationCompletionProposal(type,
						constructorName, PUBLIC_MODIFIER, null, offset, length,
						relevance + 500, isStatic));
			}
		}

		if (prefix.length() > 0
				&& !"base".equals(prefix) && !hasMethod(methods, prefix) && suggestedMethods.add(prefix)) { //$NON-NLS-1$
			// TODO add name validation
			// if
			// (!JavaConventions.validateMethodName(prefix).matches(IStatus.ERROR))
			// {
			result.add(new MethodDeclarationCompletionProposal(type, prefix,
					PUBLIC_MODIFIER, "void", offset, length, relevance,
					isStatic));
			if (!(FieldOrMethodCompletionHandler.MAIN_METHOD.equals(prefix) && isStatic)) {
				result.add(new MethodDeclarationCompletionProposal(type,
						prefix, PROTECTED_MODIFIER, "void", offset, length,
						relevance, isStatic));
				result.add(new MethodDeclarationCompletionProposal(type,
						prefix, PRIVATE_MODIFIER, "void", offset, length,
						relevance, isStatic));
			}
			// }
		}
	}

	private static boolean hasMethod(IMethod[] methods, String name) {
		for (int i = 0; i < methods.length; i++) {
			IMethod curr = methods[i];
			try {
				if (curr.getElementName().equals(name)
						&& curr.getParameters().length == 0) {
					return true;
				}
			} catch (ModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	private final IType fType;

	private final String fModifier;

	private final String fReturnTypeSig;

	private final String fMethodName;

	private final boolean fIsStatic;

	public MethodDeclarationCompletionProposal(IType type, String methodName,
			String modifiers, String returnTypeSig, int start, int length,
			int relevance, boolean isStatic) {
		super(
				"", type.getSourceModule(), start, length, null, getDisplayName(methodName, returnTypeSig, modifiers), relevance); //$NON-NLS-1$
		Assert.isNotNull(type);
		Assert.isNotNull(methodName);

		fType = type;
		fMethodName = methodName;
		fReturnTypeSig = returnTypeSig;
		fModifier = modifiers;
		fIsStatic = isStatic;

		if (fModifier.equals(PRIVATE_MODIFIER)) {
			setImage(DLTKPluginImages.get(DLTKPluginImages.IMG_METHOD_PRIVATE));
		} else if (fModifier.equals(PROTECTED_MODIFIER)) {
			setImage(DLTKPluginImages
					.get(DLTKPluginImages.IMG_METHOD_PROTECTED));
		} else if (fModifier.equals(PUBLIC_MODIFIER)) {
			setImage(DLTKPluginImages.get(DLTKPluginImages.IMG_METHOD_PUBLIC));
		} else {
			setImage(DLTKPluginImages.get(DLTKPluginImages.IMG_METHOD_PRIVATE));
		}

		if (returnTypeSig == null)
			setProposalInfo(new ProposalInfo(type));
	}

	private static String getDisplayName(String methodName,
			String returnTypeSig, String modifier) {
		StringBuffer buf = new StringBuffer();
		buf.append(methodName);
		buf.append('(');
		buf.append(')');
		if (returnTypeSig != null) {
			buf.append("  "); //$NON-NLS-1$
			buf.append(returnTypeSig);
			buf.append(" - "); //$NON-NLS-1$
			// if (modifier != null)
			// buf.append(modifier + " ");
			buf.append("method stub");
		} else {
			buf.append(" - "); //$NON-NLS-1$
			buf.append("Default constructor");
		}
		return buf.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.mod.ui.text.completion.ScriptTypeCompletionProposal#updateReplacementString(org.eclipse.jface.text.IDocument,
	 *      char, int)
	 */
	protected boolean updateReplacementString(IDocument document, char trigger,
			int offset) throws CoreException, BadLocationException {

		String lineDelim = TextUtilities.getDefaultLineDelimiter(document);
		String declTypeName = fType.getTypeQualifiedName(".");
		boolean isInterface = false;
		if (fType instanceof IJSType) {
			isInterface = ((IJSType) fType).isInterface();
		}

		StringBuffer comment = new StringBuffer("//>");
		if (fModifier != null)
			comment.append(fModifier);
		else if ((fReturnTypeSig != null) && (!isInterface)) {
			comment.append(PRIVATE_MODIFIER); //$NON-NLS-1$
		} else {
			comment.append(PUBLIC_MODIFIER); //$NON-NLS-1$
		}

		// generate comment
		comment.append(" ");
		if (fReturnTypeSig != null) {
			comment.append(fReturnTypeSig);
			comment.append(" ");
		} else if (fMethodName.indexOf("constructs") != -1) {
			comment.append("");
		} else {
			comment.append(fType.getElementName());
			comment.append(" ");
		}
		comment.append(fMethodName);
		if (isMainMethod()) {
			comment.append("(String ... arguments)");
		} else {
			comment.append("()");
		}
		comment.append(CompletionUtils.NEWLINE_1);
		comment.append(CompletionUtils.NEWLINE_2);

		// generate method stub
		String ident = CompletionUtils.calculateIndent(document, offset);
		StringBuffer completionText = new StringBuffer(comment);
		completionText.append(ident);
		completionText.append(fMethodName);
		completionText.append(" : function(");
		if (isMainMethod()) {
			completionText.append("");
		}
		completionText.append(") {");
		completionText.append(CompletionUtils.NEWLINE_1);
		completionText.append(CompletionUtils.NEWLINE_2);
		completionText.append(ident);
		completionText.append(CompletionUtils.TAB);
		final int cursorPosition = completionText.length();
		completionText.append(CompletionUtils.NEWLINE_1);
		completionText.append(CompletionUtils.NEWLINE_2);
		completionText.append(ident);
		completionText.append("}");

		if (!isInEndOfBlock(offset))
			completionText.append(",");

		setReplacementString(completionText.toString());
		// TODO move this to another place
		setCursorPosition(cursorPosition);
		return false;
	}

	private boolean isMainMethod() {
		return FieldOrMethodCompletionHandler.MAIN_METHOD.equals(fMethodName)
				&& fIsStatic;
	}

	public CharSequence getPrefixCompletionText(IDocument document,
			int completionOffset) {
		return new String(); // don't let method stub proposals complete
		// incrementally
	}

	/*
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposalExtension4#isAutoInsertable()
	 */
	public boolean isAutoInsertable() {
		return false;
	}

	@Override
	protected boolean isValidPrefix(String prefix) {
		return super.isValidPrefix(prefix) || true;
	}

	@Override
	public IContextInformation getContextInformation() {
		return null;
	}

	private boolean isInEndOfBlock(int offset) {

		IJstType type = ((IVjoSourceModule) fSourceModule).getJstType();
		List<? extends IJstMethod> methods = type.getMethods(fIsStatic);

		for (IJstMethod method : methods) {
			if (method.getSource().getEndOffSet() > offset)
				return false;
		}

		return true;
	}
}

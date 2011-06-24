/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.text.completion;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.vjet.eclipse.ui.VjoElementImageDescriptor;
import org.ebayopensource.vjo.tool.codecompletion.StringUtils;
import org.eclipse.dltk.mod.ast.Modifiers;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.ui.templates.IScriptTemplateIndenter;
import org.eclipse.dltk.mod.ui.templates.NopScriptTemplateIndenter;
import org.eclipse.dltk.mod.utils.TextUtils;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextUtilities;

public class VjoProposalLabelUtil {

	// public static void initLineSeperator(IDocument document) {
	// lineSeperator = TextUtilities.getDefaultLineDelimiter(document);
	// }

	public static String evaluateIndent(String str, IDocument document,
			int replaceOffset) {
		if (document == null) {
			return str;
		}
		final String[] lines = TextUtils.splitLines(str);
		if (lines.length > 1) {
			final String delimeter = TextUtilities
					.getDefaultLineDelimiter(document);
			final String indent = calculateIndent(document, replaceOffset);
			final IScriptTemplateIndenter indenter = getIndenter();
			final StringBuffer buffer = new StringBuffer(lines[0]);

			// Except first line
			for (int i = 1; i < lines.length; i++) {
				buffer.append(delimeter);
				indenter.indentLine(buffer, indent, lines[i]);
			}
			return buffer.toString();
		} else {
			return str;
		}
	}

	protected static String calculateIndent(IDocument document, int offset) {
		try {
			final IRegion region = document.getLineInformationOfOffset(offset);
			String indent = document.get(region.getOffset(), offset
					- region.getOffset());
			int i = 0;
			while (i < indent.length()
					&& StringUtils.isSpaceOrTab(indent.charAt(i))) {
				++i;
			}
			if (i > 0) {
				return indent.substring(0, i);
			}
		} catch (BadLocationException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}

		return ""; //$NON-NLS-1$
	}

	/**
	 * Tranlate JstModifiers to DLTK modifier
	 * 
	 * @param jstModifer
	 * @return
	 */
	public static int translateModifers(int jstModifer) {
		if ((jstModifer & JstModifiers.PUBLIC) != 0) {
			return Modifiers.AccPublic;
		}

		if ((jstModifer & JstModifiers.PROTECTED) != 0) {
			return Modifiers.AccProtected;
		}

		if ((jstModifer & JstModifiers.PRIVATE) != 0) {
			return Modifiers.AccPrivate;
		}

		return Modifiers.AccDefault;
	}

	/**
	 * @return
	 */
	protected static IScriptTemplateIndenter getIndenter() {
		return new NopScriptTemplateIndenter();
	}
	

	public static int getVjoModifierForImage(JstModifiers modifiers) {
		int result = 0;
		if (modifiers == null) {
			return result;
		}
		if (modifiers.isStatic()) {
			result = result | VjoElementImageDescriptor.STATIC;
		}
		if (modifiers.isAbstract()) {
			result = result | VjoElementImageDescriptor.ABSTRACT;
		}
		if (modifiers.isFinal()) {
			result = result | VjoElementImageDescriptor.FINAL;
		}
		return result;

	}
	
	public static int getDltkModifyFlag(JstModifiers modifiers) {
		if (modifiers.isPublic()) {
			return Modifiers.AccPublic;
		} else if (modifiers.isProtected()) {
			return Modifiers.AccProtected;
		} else if (modifiers.isPrivate()) {
			return Modifiers.AccPrivate;
		} else {
			return Modifiers.AccDefault;
		}
	}
	

	public static JstModifiers getModifiers(IJstNode node) {
		JstModifiers modifies = null;
		if (node instanceof IJstMethod) {
			IJstMethod method = (IJstMethod) node;
			modifies = method.getModifiers();
		} else if (node instanceof IJstProperty) {
			IJstProperty property = (IJstProperty) node;
			modifies = property.getModifiers();
		} else if (node instanceof IJstType) {
			IJstType type = (IJstType) node;
			modifies = type.getModifiers();
		}
		return modifies;
	}



}

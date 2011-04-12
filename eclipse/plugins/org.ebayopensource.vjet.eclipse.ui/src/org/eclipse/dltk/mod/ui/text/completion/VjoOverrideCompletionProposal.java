/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.mod.ui.text.completion;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextUtilities;

public class VjoOverrideCompletionProposal extends
		ScriptOverrideCompletionProposal {
	
	public VjoOverrideCompletionProposal(IScriptProject jproject,
			ISourceModule cu, String methodName, String[] paramTypes,
			int start, int length, String displayName, String completionProposal) {
		super(jproject, cu, methodName, paramTypes, start, length, displayName,
				completionProposal);
	}

	@Override
	protected boolean updateReplacementString(IDocument document, char trigger,
			int offset) throws CoreException, BadLocationException {
		boolean sup = super.updateReplacementString(document, trigger, offset);
		String completion = getReplacementString();
		final String delimeter = TextUtilities
			.getDefaultLineDelimiter(document);
		final String indent = CompletionUtils.calculateIndent(document, offset);
		String[] lines = completion.split("\r\n");
		if (lines.length > 1) {
			StringBuffer newCompl = new StringBuffer(lines[0]);
			newCompl.append(delimeter);
			for (int i = 1; i < lines.length; i++) {
				newCompl.append(indent).append(lines[i]).append(delimeter);
			}
			setReplacementString(newCompl.substring(0, newCompl.length() - 2).toString());
			return true;
		} else {
			return sup;
		}
	}
}

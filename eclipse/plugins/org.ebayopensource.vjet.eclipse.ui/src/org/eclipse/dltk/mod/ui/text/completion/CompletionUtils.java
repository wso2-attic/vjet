/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.mod.ui.text.completion;

import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;

public class CompletionUtils {
	public static final char TAB = '\t';
	public static final char NEWLINE_1 = '\r';
	public static final char NEWLINE_2 = '\n';
	
	public static String calculateIndent(IDocument document, int offset) {
		try {
			final IRegion region = document.getLineInformationOfOffset(offset);
			String indent = document.get(region.getOffset(), offset
					- region.getOffset());
			int i = 0;
			while (i < indent.length() && isSpaceOrTab(indent.charAt(i))) {
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
	 * Tests if specified char is tab or space
	 * 
	 * @param ch
	 * @return
	 */
	private static boolean isSpaceOrTab(char ch) {
		return ch == ' ' || ch == '\t';
	}
}

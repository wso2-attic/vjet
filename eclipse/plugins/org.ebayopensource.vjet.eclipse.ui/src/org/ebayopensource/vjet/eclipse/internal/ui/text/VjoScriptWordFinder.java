/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.text;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;

public class VjoScriptWordFinder {
	
	/**
	 * Finds a word by specified offset and returns the region of word.
	 * 
	 * @param source
	 * @param offset
	 * 
	 * @return the region of found word
	 */
	public static IRegion findWord(IDocument document, int offset) {

		int start= -2;
		int end= -1;
		
		try {
			int pos = offset;
			char c;
			
			while (pos > 0 && pos < document.getLength()) {
				c = document.getChar(pos);
				if (Character.isWhitespace(c) && !Character.isLetter(c))
					break;
				--pos;
			}
			start = pos;
			pos = offset;
			int length= document.getLength();

			while (pos < length - 1) {
				c= document.getChar(pos);
				if (!Character.isJavaIdentifierPart(c))
					break;
				++pos;
			}
			end= pos;

		} catch (BadLocationException x) {
		}

		if (start >= -1 && end > -1) {
			return new Region(start, end - start);
		}

		return null;
	}
	
}

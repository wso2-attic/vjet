/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.text.completion;

import org.eclipse.dltk.mod.corext.documentation.SingleCharReader;

/**
 * Reads a java doc comment from a java doc comment. Skips star-character on
 * begin of line
 */
public class JavaDocCommentReader extends SingleCharReader {

	private String fBuffer;

	private int fCurrPos;

	private int fStartPos;

	private int fEndPos;

	private boolean fWasNewLine;

	public JavaDocCommentReader(String buf, int start, int end) {
		fBuffer = buf;
		fStartPos = start + 3;
		fEndPos = end - 2;

		reset();
	}

	public JavaDocCommentReader(String info) {
		this(info, 0, info.length());
	}

	/**
	 * @see java.io.Reader#read()
	 */
	public int read() {
		if (fCurrPos < fEndPos) {
			char ch;
			if (fWasNewLine) {
				do {
					ch = fBuffer.charAt(fCurrPos++);
				} while (fCurrPos < fEndPos && Character.isWhitespace(ch));
				if (ch == '*') {
					if (fCurrPos < fEndPos) {
						do {
							ch = fBuffer.charAt(fCurrPos++);
						} while (ch == '*');
					} else {
						return -1;
					}
				}
			} else {
				ch = fBuffer.charAt(fCurrPos++);
			}
			fWasNewLine = isLineDelimiterChar(ch);

			return ch;
		}
		return -1;
	}

	private static boolean isLineDelimiterChar(char ch) {
		return ch == '\n' || ch == '\r';
	}

	/**
	 * @see java.io.Reader#close()
	 */
	public void close() {
		fBuffer = null;
	}

	/**
	 * @see java.io.Reader#reset()
	 */
	public void reset() {
		fCurrPos = fStartPos;
		fWasNewLine = true;
	}

	/**
	 * Returns the offset of the last read character in the passed buffer.
	 */
	public int getOffset() {
		return fCurrPos;
	}
}

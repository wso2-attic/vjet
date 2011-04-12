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
package org.ebayopensource.vjet.eclipse.internal.ui.text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

/**
 * 
 * 
 */
public class VjoKeywordRule implements IRule {

	private List<String[]> m_keywordParts;
	protected IToken m_token;

	/**
	 * 
	 */
	public VjoKeywordRule(String[] keywords, IToken token) {
		this.m_token = token;
		initKeywords(keywords);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.text.rules.IRule#evaluate(org.eclipse.jface.text.rules.ICharacterScanner)
	 */
	public IToken evaluate(ICharacterScanner scanner) {
		Iterator<String[]> it = m_keywordParts.iterator();
		while (it.hasNext()) {
			String[] ss = it.next();
			IToken token = evaluateKeyword(scanner, ss);
			if (token != Token.UNDEFINED) {
				return token;
			}
		}
		return Token.UNDEFINED;
	}

	public IToken evaluateKeyword(ICharacterScanner scanner, String[] m_parts) {

		int pos = 0;
		int partNo = 0;
		StringBuilder buffer = new StringBuilder();

		char c = (char) scanner.read();
		if (m_parts[partNo].charAt(pos) == c) {
			pos++;
			boolean cleanBuffer = false;
			boolean canContinue = true;
			boolean allowWhiteSpaces = false;
			do {
				buffer.append(c);
				c = (char) scanner.read();
				if (pos > m_parts[partNo].length() - 1) {
					// goto next partition
					partNo++;
					pos = 0;
					allowWhiteSpaces = true;
					if (partNo > m_parts.length - 1) {
						// everything is OK - time to exit
						canContinue = false;
						break;
					}
				}
				if (m_parts[partNo].charAt(pos) == c) {
					allowWhiteSpaces = false;
					pos++;
				} else if (!allowWhiteSpaces || !Character.isWhitespace(c)) {
					canContinue = false;
					cleanBuffer = true;
				}
			} while (canContinue);
			scanner.unread();
			if (cleanBuffer) {
				unreadBuffer(scanner, buffer.length());
			} else {
				preExit(scanner);
				return m_token;
			}
		} else {
			scanner.unread();
		}

		return Token.UNDEFINED;
	}

	/**
	 * Do necessary read/unread if required
	 */
	protected void preExit(ICharacterScanner scanner) {

	}

	private void initKeywords(String[] keywords) {
		m_keywordParts = new ArrayList<String[]>();
		for (String keyword : keywords) {
			List<String> list = initParts(keyword);
			String[] ss = new String[list.size()];
			ss = list.toArray(ss);
			m_keywordParts.add(ss);
		}
	}

	protected List<String> initParts(String word) {
		StringTokenizer tokenizer = new StringTokenizer(word, ".", true);
		List<String> parts = new ArrayList<String>();
		while (tokenizer.hasMoreElements()) {
			String token = tokenizer.nextToken();
			parts.add(token);
		}
		return parts;
	}

	/**
	 * Returns the characters in the buffer to the scanner.
	 * 
	 * @param scanner
	 *            the scanner to be used
	 */
	protected void unreadBuffer(ICharacterScanner scanner, int bufferLen) {
		for (int i = bufferLen - 1; i >= 0; i--)
			scanner.unread();
	}
}

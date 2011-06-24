/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.text;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.dltk.mod.javascript.internal.ui.text.JavascriptWhitespaceDetector;
import org.eclipse.dltk.mod.ui.text.IColorManager;
import org.eclipse.dltk.mod.ui.text.rules.CombinedWordRule;
import org.eclipse.dltk.mod.ui.text.rules.CombinedWordRule.CharacterBuffer;
import org.eclipse.dltk.mod.ui.text.rules.CombinedWordRule.WordMatcher;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;

/**
 * A rule based JavaDoc scanner.
 */
public final class VjoJavaDocScanner extends VjoCommentScanner {


	/**
	 * Detector for HTML comment delimiters.
	 */
	static class HTMLCommentDetector implements IWordDetector {

		/**
		 * @see IWordDetector#isWordStart(char)
		 */
		public boolean isWordStart(char c) {
			return (c == '<' || c == '-');
		}

		/**
		 * @see IWordDetector#isWordPart(char)
		 */
		public boolean isWordPart(char c) {
			return (c == '-' || c == '!' || c == '>');
		}
	}

	class TagRule extends SingleLineRule {

		/*
		 * @see SingleLineRule
		 */
		public TagRule(IToken token) {
			super("<", ">", token, (char) 0); //$NON-NLS-2$ //$NON-NLS-1$
		}

		/*
		 * @see SingleLineRule
		 */
		public TagRule(IToken token, char escapeCharacter) {
			super("<", ">", token, escapeCharacter); //$NON-NLS-2$ //$NON-NLS-1$
		}

		private IToken evaluateToken() {
			try {
				final String token= getDocument().get(getTokenOffset(), getTokenLength()) + "."; //$NON-NLS-1$

				int offset= 0;
				char character= token.charAt(++offset);

				if (character == '/')
					character= token.charAt(++offset);

				while (Character.isWhitespace(character))
					character= token.charAt(++offset);

				while (Character.isLetterOrDigit(character))
					character= token.charAt(++offset);

				while (Character.isWhitespace(character))
					character= token.charAt(++offset);

				if (offset >= 2 && token.charAt(offset) == fEndSequence[0])
					return fToken;

			} catch (BadLocationException exception) {
				// Do nothing
			}
			return getToken(VjetColorConstants.VJET_JAVADOC_OTHERS);
		}

		/*
		 * @see PatternRule#evaluate(ICharacterScanner)
		 */
		public IToken evaluate(ICharacterScanner scanner) {
			IToken result= super.evaluate(scanner);
			if (result == fToken)
				return evaluateToken();
			return result;
		}
	}

	private static String[] fgTokenProperties= {
		VjetColorConstants.VJET_JAVADOC_LINKS,//@link
		VjetColorConstants.VJET_JAVADOC_OTHERS,//other java doc
		VjetColorConstants.VJET_JAVADOC_TAGS,//key word like "@author"
		VjetColorConstants.VJET_JAVADOC_HTML_MARKUP,//like <code></code>
		TASK_TAG
	};


	public VjoJavaDocScanner(IColorManager manager, IPreferenceStore store, Preferences coreStore) {
		super(manager, store, coreStore, VjetColorConstants.VJET_JAVADOC_OTHERS, fgTokenProperties);
	}

	/**
	 * Initialize with the given arguments
	 * @param manager	Color manager
	 * @param store	Preference store
	 *
	 * @since 3.0
	 */
	public VjoJavaDocScanner(IColorManager manager, IPreferenceStore store) {
		this(manager, store, null);
	}

	public IDocument getDocument() {
		return fDocument;
	}

	/*
	 * @see AbstractJavaScanner#createRules()
	 */
	protected List createRules() {

		List list= new ArrayList();

		// Add rule for tags.
		Token token= getToken(VjetColorConstants.VJET_JAVADOC_HTML_MARKUP);
		list.add(new TagRule(token));


		// Add rule for HTML comments
		WordRule wordRule= new WordRule(new HTMLCommentDetector(), token);
		wordRule.addWord("<!--", token); //$NON-NLS-1$
		wordRule.addWord("--!>", token); //$NON-NLS-1$
		list.add(wordRule);


		// Add rule for links.
		token= getToken(VjetColorConstants.VJET_JAVADOC_LINKS);
		list.add(new SingleLineRule("{@link", "}", token)); //$NON-NLS-2$ //$NON-NLS-1$
		list.add(new SingleLineRule("{@value", "}", token)); //$NON-NLS-2$ //$NON-NLS-1$


		// Add generic whitespace rule.
		list.add(new WhitespaceRule(new JavascriptWhitespaceDetector()));


		list.addAll(super.createRules());
		return list;
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.text.JavaCommentScanner#createMatchers()
	 */
	protected List createMatchers() {
		List list= super.createMatchers();

		// Add word rule for keywords.
		final IToken token= getToken(VjetColorConstants.VJET_JAVADOC_TAGS);
		WordMatcher matcher= new CombinedWordRule.WordMatcher() {
			public IToken evaluate(ICharacterScanner scanner, CharacterBuffer word) {
				int length= word.length();
				if (length > 1 && word.charAt(0) == '@') {
					int i= 0;
					try {
						for (; i <= length; i++)
							scanner.unread();
						int c= scanner.read();
						i--;
						if (c == '*' || Character.isWhitespace((char)c)) {
							scanner.unread();
							return token;
						}
					} finally {
						for (; i >= 0; i--)
							scanner.read();
					}
				}
				return Token.UNDEFINED;
			}
		};
		list.add(matcher);

		return list;
	}
}



/*******************************************************************************
 * Copyright (c) 2000-2011 IBM Corporation and others, eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     eBay Inc - modification
 *******************************************************************************/
package org.eclipse.dltk.mod.javascript.internal.ui.rules;

import org.eclipse.jface.text.Assert;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

/**
 * An implementation of <code>IRule</code> detecting a numerical value.
 */
public class FloatNumberRule implements IRule {
	protected static final int UNDEFINED = -1;
	protected IToken fToken;
	protected int fColumn = UNDEFINED;

	public FloatNumberRule(IToken token) {
		Assert.isNotNull(token);
		fToken = token;
	}

	public void setColumnConstraint(int column) {
		if (column < 0)
			column = UNDEFINED;
		fColumn = column;
	}

	public IToken evaluate(ICharacterScanner scanner) {
		if (scanner.getColumn() > 0) {
			scanner.unread();
			int cc = scanner.read();
			if (!Character.isWhitespace((char) cc)) {
				return Token.UNDEFINED;
			}
		}
		int c = scanner.read();
		int p = c;
		if (Character.isDigit((char) c) || c == '.') {
			boolean hex = false;
			if (fColumn == UNDEFINED || (fColumn == scanner.getColumn() - 1)) {
				do {
					p = c;
					c = scanner.read();
					if (c == 'x' || c == 'X' && !hex) {
						hex = true;
						p = c;
						c = scanner.read();
					}
				} while (Character.isDigit((char) c));
				if (c != 'e' && c != 'E') {
					scanner.unread();
				} else {
					/**
					 * Begin<Jack@eBay: xxx\r\n.endType(). The 'e' will be lost,
					 * because this rule considers the 'e' as part of float.
					 */
					c = scanner.read();
					if (Character.isDigit((char) c)) {
						scanner.unread();
					} else {
						scanner.unread();
						scanner.unread();
					}
					// >End
				}
				if (p == '.') {
					scanner.unread();
					return Token.UNDEFINED;
				}
				return fToken;
			}
		}
		scanner.unread();
		return Token.UNDEFINED;
	}
}

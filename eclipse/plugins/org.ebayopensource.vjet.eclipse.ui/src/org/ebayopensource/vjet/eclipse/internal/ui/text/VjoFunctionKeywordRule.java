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

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.swt.graphics.Point;

/**
 * 
 * 
 */
public class VjoFunctionKeywordRule extends VjoKeywordRule {

	public VjoFunctionKeywordRule(String[] keywords, IToken token) {
		super(keywords, token);
	}

	protected void preExit(ICharacterScanner scanner) {
//		scanner.unread();// unread "("
	}
	
	private IToken evaluateOnJst(VjoCodeScanner vScanner) {
		Point[] points = vScanner.getHighlightPoints(); 
		int offset = vScanner.getTokenOffset();
		for (Point p : points) {
			if (p.y < offset) {
				continue;
			} else if (p.x > offset) {
				break;
			} else {
				int length = p.y - offset + 1;
				
				for (int i=0; i< length; i++) {
					vScanner.read();
				}
				return m_token;
			}
		}
		return Token.UNDEFINED;
	}
	
	public IToken evaluate(ICharacterScanner scanner) {
		VjoCodeScanner vScanner = (VjoCodeScanner)scanner;
		if (vScanner.isValidVjo()) {
			return evaluateOnJst(vScanner);
		} 
		
		return Token.UNDEFINED;
//		else {
//			return super.evaluate(scanner);
//		}
	}
}

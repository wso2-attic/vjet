/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.completion;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jstojava.parser.comments.CommentUtil;

/**
 * Describe the position information when cursor is in a comment
 * 
 * 
 * 
 */
public class JstCommentCompletion extends JstCompletion {

	private static final char	SLASH_CHAR	= '/';
	/**
	 * the whole comment string
	 */
	private String m_commentString;
	/**
	 * the relative offset in comment string
	 */
	private int m_commentOffset;

	private String m_token;

	/**
	 * @param parent
	 *            parent node
	 * @param commentString
	 *            the whole comment string
	 * @param commentOffset
	 *            the relative cursor offset in comment string
	 */
	public JstCommentCompletion(BaseJstNode parent, String commentString,
			int commentOffset) {
		super(parent, new String[0]);
		// modify by patrick
		this.m_commentString = leftTrim(commentString);
		// end modify
		this.m_commentOffset = commentOffset;
		initToken();
	}

	/**
	 * Sample:</br> //>public Stri{cursor}ng getC </br> Result: </br> >public
	 * Stri
	 * 
	 * @return
	 */
	@Override
	public String getIncompletePart() {
		String commentBeforCursor = getCommentBeforeCursor();
		String declarationBeforeCursor = CommentUtil
				.getDeclaration(commentBeforCursor);
		return declarationBeforeCursor;
	}

	/**
	 * Sample:</br> //>public Stri{cursor}ng getC </br> Result: </br> //>public
	 * Stri
	 * 
	 * @return
	 */
	public String getCommentBeforeCursor() {
		if (m_commentOffset < m_commentString.length()) {
			return m_commentString.substring(0, m_commentOffset);
		} else {
			return m_commentString;
		}
	}

	@Override
	public String getToken() {
		return m_token;
	}
	
	private void initToken() {
		StringBuilder buffer = new StringBuilder();
		int begin = this.m_commentOffset;
		if (begin == 0) {
			this.m_token = "";
			return;
		}
		if (begin > m_commentString.length()) {
			m_token = m_commentString;
			return;
		}
		char ch = this.m_commentString.charAt(--begin);
		while ((Character.isJavaIdentifierPart(ch)
				|| ch == '.'
				|| ch == ':')//bugfix by huzhou@ebay.com to support attributed type
				&& begin > 0) {
			buffer.insert(0, ch);
			ch = this.m_commentString.charAt(--begin);
		}
		//Support "@SUPPRESSTYPECHECK"
		if (ch == '@') {
			buffer.insert(0, ch);
		}
		this.m_token =  buffer.toString();
	}

	private String leftTrim(String commentString) {
		return commentString.substring(commentString.indexOf(SLASH_CHAR));
	}

}

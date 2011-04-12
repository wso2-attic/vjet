/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;

public class TypeSearcher {

	private static TypeSearcher s_searcher = new TypeSearcher();

	private TypeSpaceMgr mgr = TypeSpaceMgr.getInstance();

	public List<IJstType> search(String pattern) {

		List<IJstType> list = new ArrayList<IJstType>();

		Collection<IJstType> list2 = mgr.getAllTypes();
		for (IJstType jstType : list2) {
			if (match(pattern, jstType)) {
				list.add(jstType);
			}
		}

		return list;
	}

	/**
	 * @param pattern
	 * @param type
	 * @return
	 */
	private boolean match(String pattern, IJstType type) {
		String name = type.getName();
		String simpleName = type.getSimpleName();

		boolean isFullQualifiedNameMatch = false;
		if (name != null) {
			isFullQualifiedNameMatch = Pattern.matches(wildcardToRegex(pattern
					.toLowerCase()), name.toLowerCase());
		}

		boolean isSimpleNameMatch = false;
		if (simpleName != null) {
			isSimpleNameMatch = Pattern.matches(wildcardToRegex(pattern
					.toLowerCase()), simpleName.toLowerCase());
		}

		return isFullQualifiedNameMatch || isSimpleNameMatch;
	}

	/**
	 * @param wildcard
	 * @return
	 */
	public static String wildcardToRegex(String wildcard) {
		StringBuffer s = new StringBuffer(wildcard.length());
		s.append('^');
		for (int i = 0, is = wildcard.length(); i < is; i++) {
			char c = wildcard.charAt(i);
			switch (c) {
			case '*':
				s.append(".*");
				break;
			case '?':
				s.append(".");
				break;
			// escape special regexp-characters
			case '(':
			case ')':
			case '[':
			case ']':
			case '$':
			case '^':
			case '.':
			case '{':
			case '}':
			case '|':
			case '\\':
				s.append("\\");
				s.append(c);
				break;
			default:
				s.append(c);
				break;
			}
		}
		if (s.indexOf("*") < 0) {
			s.append(".*");
		}
		s.append('$');
		return s.toString();
	}

	private TypeSearcher() {
		super();
	}

	public static TypeSearcher getInstance() {
		return s_searcher;
	}

}

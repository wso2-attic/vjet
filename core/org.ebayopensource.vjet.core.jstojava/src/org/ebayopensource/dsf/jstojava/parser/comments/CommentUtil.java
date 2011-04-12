/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.parser.comments;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


public class CommentUtil {
	private static final String SINGLE_LINE = "//";
	private static final String MULTI_LINE = "/*";
	private static final String ASTERISK = "*";
	private static final String END_MULTILINE = "*/";
	private static final String END_COMMENT = ";";
	private static final String BACK = "<";
	private static final String FORWARD = ">";
	private static final String NEEDS = "needs";
	
	public static String getDeclaration(String comment) {
		comment = comment.trim();
		String decl = null;
		if (comment.startsWith(SINGLE_LINE)) {
			decl = getSingleLineDeclaration(comment);
		} else if (comment.startsWith(MULTI_LINE)) {
			decl = getMultiLineDeclaration(comment);
		}
		if (decl!=null && (decl.equals(FORWARD) || comment.equals(BACK))) {
			decl = null;
		}
		return decl;
	}
	
	private static String getSingleLineDeclaration(String comment) {
		comment = comment.trim();
		comment = comment.substring(SINGLE_LINE.length()).trim();
		if (!comment.startsWith(FORWARD) && !comment.startsWith(BACK)) {
			return null;
		}
		return getLine(comment,false);
	}


	private static String getMultiLineDeclaration(String comment) {
		LineNumberReader reader = new LineNumberReader(new StringReader(comment));
		String line;
		String decl = null;
		try {
			while((line=reader.readLine())!=null) {
				line = line.trim();
				if (reader.getLineNumber()==1) {
					if (line.startsWith(MULTI_LINE)) {
						line = line.substring(MULTI_LINE.length()).trim();
						if (line.startsWith(ASTERISK)) {
							line = line.substring(1).trim();
						}
						if (!line.startsWith(FORWARD) && !line.startsWith(BACK)) {
							return null;
						}
						decl = getLine(line,true);
						if (line.contains(END_COMMENT)) {
							return decl;
						}
					} else {
						return null;
					}
					
				} else {
					while (!line.startsWith(END_MULTILINE) && line.startsWith(ASTERISK)) {
						line = line.substring(1).trim();
					}
					decl += " " + getLine(line,true);
				}
				
			}
		} catch (IOException e) {
			return null;
		}
		return decl;
	}
	
	private static String getLine(String comment, boolean multiLine) {
		comment = comment.trim();
		int start = 0;
		int end = comment.indexOf(END_COMMENT);
		if (multiLine && end==-1) {
			end = comment.indexOf(END_MULTILINE);
		}
		if (end==-1) {
			end = comment.length();
		}
		return comment.substring(start, end);
	}

	public static List<String> getNeeds(String comment) {
		int idx = comment.indexOf(NEEDS);
		if (idx<0) {
			return null;
		}
		String needs = getDeclaration(comment);
		if (needs==null) {
			return null;
		}
		idx = needs.indexOf(NEEDS);
		needs = needs.substring(idx+NEEDS.length()).trim();
		String[] arr = needs.split(",");
		ArrayList<String> list = new ArrayList<String>(4);
		for (String need : arr) {
			String n = need.trim();
			if (!"".equals(n)){
				list.add(n);
			}
		}
		return list;
		
	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.comment;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jsgen.shared.validation.common.ScopeId;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCommentCompletion;
import org.ebayopensource.vjo.meta.VjoKeywords;
import org.ebayopensource.vjo.tool.codecompletion.advisor.keyword.IVjoKeywordCompletionData;
import org.ebayopensource.vjo.tool.codecompletion.advisor.keyword.VjoKeywordFactory;

public class VjoCcCommentUtil {

	public static boolean isInactiveNeed(JstCommentCompletion completion) {
		if (completion == null) {
			return false;
		}
		String commentBeforCursor = completion.getCommentBeforeCursor();
		return commentBeforCursor.contains(VjoKeywords.NEEDS);
	}
	
	public static boolean needProposal(JstCommentCompletion completion) {
		String commentBeforCursor = completion.getCommentBeforeCursor();
		if(commentBeforCursor.contains("(") && !commentBeforCursor.contains(")")) {
			return true;
		} else {
			return false;
		}
		
	}

	public static ScopeId getScope(JstCommentCompletion completion) {
		if (completion.getScopeStack().isEmpty()) {
			return ScopeIds.GLOBAL;
		}
		return completion.getScopeStack().peek();
	}

	public static boolean containsKeyword(JstCommentCompletion completion,
			IVjoKeywordCompletionData keyword) {
		String comment = completion.getCommentBeforeCursor();
		if (comment.contains(keyword.getName())) {
			return true;
		} else if (isModifierKeyword(keyword)) {
			return checkModifierKeyword(comment);
		} else if (isFinalKeyword(keyword)) {
			return checkFinalKeyword(comment);
			
		}
		return false;

	}
	
	public static boolean isAfterTypeRefKeyword(JstCommentCompletion completion) {
		String s = completion.getCommentBeforeCursor();
		if (s.toLowerCase().contains(VjoKeywordFactory.KWD_TYPE.getName().toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean checkFinalKeyword(String comment) {
		return comment.contains(VjoKeywordFactory.KWD_ABSTRACT.getName())
		|| comment.contains(VjoKeywordFactory.KWD_FINAL.getName());
}

	private static boolean isFinalKeyword(IVjoKeywordCompletionData keyword) {
		String name = keyword.getName();
		return VjoKeywordFactory.KWD_ABSTRACT.getName().equals(name)
				|| VjoKeywordFactory.KWD_FINAL.getName().equals(name);
	}

	private static boolean checkModifierKeyword(String comment) {
		return comment.contains(VjoKeywordFactory.KWD_PUBLIC.getName())
				|| comment.contains(VjoKeywordFactory.KWD_PROTECTED.getName())
				|| comment.contains(VjoKeywordFactory.KWD_PRIVATE.getName())
				|| comment.contains(VjoKeywordFactory.KWD_NEEDS.getName());
	}

	private static boolean isModifierKeyword(IVjoKeywordCompletionData keyword) {
		String name = keyword.getName();
		return VjoKeywordFactory.KWD_PUBLIC.getName().equals(name)
				|| VjoKeywordFactory.KWD_PROTECTED.getName().equals(name)
				|| VjoKeywordFactory.KWD_PRIVATE.getName().equals(name)
				|| VjoKeywordFactory.KWD_NEEDS.getName().equals(name);
	}
	
	

	/**
	 * Simple way to check if the comment begin with "//>" or "//<"
	 * Now it can not check "// abc > {cursor}"
	 * @param commentTxt
	 * @param relativeCursorPos
	 * @return
	 */
	public static boolean isVjoComment(String commentTxt, int relativeCursorPos) {
		int pos1 = commentTxt.indexOf("//");
		int pos2 = commentTxt.indexOf(">");
		int pos3 = commentTxt.indexOf("<");
		if (pos2 == -1 && pos3 == -1 ) { // "//{cursor}"
			return false;
		}
		if (pos1 >= relativeCursorPos || pos2 >= relativeCursorPos || pos3 >= relativeCursorPos) { //"//{cursor}>"
			return false;
		}
		if (pos1 >= pos2 && pos2 != -1) { //">//{cursor}"
			return false;
		}
		if (pos1 >= pos3 && pos3 != -1) { //"<//{cursor}"
			return false;
		}
		return true;
	}


}

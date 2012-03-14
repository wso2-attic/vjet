/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.proposaldata;

import java.util.Iterator;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjo.tool.codecompletion.CodeCompletionUtils;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcOuterPropMethodProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.keyword.CompletionConstants;

public abstract class AbstractVjoProposalPresenter {
	/**
	 * VjoCcKeywordAdvisor.ID.equals(advisor) ||
	 * VjoCcThisProposalAdvisor.ID.equals(advisor) ||
	 * VjoCCVjoUtilityAdvisor.ID.equals(advisor) ||
	 * VjoCcVj$ProposalAdvisor.ID.equals(advisor)
	 * 
	 * @return
	 */
	protected String getKeywordReplaceString(String name, String typeName, boolean inCommentArea, String indent) {
		String str = "";
		if (inCommentArea) {
			str = CodeCompletionUtils.getCommentKeywordReplaceString(name,
					typeName, true, indent);
		} else {
			str = CodeCompletionUtils.getKeywordReplaceString(name,
					typeName, true, indent);
		}
		return replaceSeperatorToken(str);
	}
	
	protected String getCommentTypeReplaceString(IJstType type, VjoCcCtx vjoCcCtx2) {
		String name = type.getSimpleName();
		// if current type is fake type replace with full type
//		if(vjoCcCtx2.getJstType().isFakeType()){
			name = type.getName();
//		}
		List<String> paras = type.getParamNames();
		if (!paras.isEmpty()) {
			Iterator<String> it = paras.iterator();
			StringBuffer buffer = new StringBuffer();
			buffer.append("<");
			while(it.hasNext()) {
				buffer.append(it.next() + ", ");
			}
			name = name + buffer.substring(0, buffer.length() - 2) + ">";
		}
		return name;
	}


	/**
	 * VjoCcFunctionGenProposalAdvisor
	 * 
	 * @param identifier
	 * @param functionName
	 * @return //> public void xxx() xxx:function(){}
	 */
	protected String getFunctionString(int identifier, String functionName,
			boolean isInterface, String indent) {
		if (isInterface) {
			return replaceSeperatorToken(CodeCompletionUtils
					.getInterfaceFunctionString(identifier, functionName));
		} else {
			return replaceSeperatorToken(CodeCompletionUtils.getFunctionString(
					identifier, functionName, indent));
		}
	}

	/**
	 * VjoCcFunctionGenProposalAdvisor
	 * 
	 * @param identifier
	 * @param functionName
	 * @return //> public void xxx() xxx:function(){}
	 */
	protected String getMainFunctionString(String indent) {
		return replaceSeperatorToken(CodeCompletionUtils
				.getMainFunctionString(indent));
	}

	/**
	 * VjoCcConstructorGenProposalAdvisor
	 * 
	 * @param modifier
	 * @return
	 */
	protected String getConstructorString(String modifier, String indent) {
		return replaceSeperatorToken(CodeCompletionUtils
				.getConstructorString(modifier, indent));
	}

	/**
	 * VjoCcPropMethodProposalAdvisor.ID.equals(advisor) ||
	 * VjoCcStaticPropMethodProposalAdvisor.ID.equals(advisor) ||
	 * VjoCcEnumElementAdvisor.ID.equals(advisor) ||
	 * VjoCcGlobalAdvisor.ID.equals(advisor)
	 * 
	 * @param isGlobal
	 * @param method
	 * @param vjoCcCtx
	 * @return
	 */
	protected String getMethodProposalReplaceStr(boolean isGlobal,
			IJstMethod method, VjoCcCtx vjoCcCtx) {
		return getMethodProposalReplaceStr(isGlobal, method, method.getName().getName(), vjoCcCtx);
	}
	
	protected String getMethodProposalReplaceStr(boolean isGlobal,
			IJstMethod method, String name, VjoCcCtx vjoCcCtx) {
		return replaceSeperatorToken(CodeCompletionUtils
				.getMethodProposalReplaceStr(isGlobal, method, name, vjoCcCtx));
	}

	/**
	 * @see VjoCcOuterPropMethodProposalAdvisor
	 * @param method
	 * @param vjoCcCtx
	 * @return
	 */
	protected String getOuterMethodProposalReplaceStr(IJstMethod method) {
		return replaceSeperatorToken(CodeCompletionUtils
				.getOuterMethodProposalReplaceStr(method));
	}

	/**
	 * VjoCcPropMethodProposalAdvisor.ID.equals(advisor) ||
	 * VjoCcStaticPropMethodProposalAdvisor.ID.equals(advisor) ||
	 * VjoCcEnumElementAdvisor.ID.equals(advisor) ||
	 * VjoCcGlobalAdvisor.ID.equals(advisor)
	 * 
	 * @param isGlobal
	 * @param method
	 * @param vjoCcCtx
	 * @return
	 */
	protected String getPropertyProposalReplaceStr(boolean isGlobal,
			IJstProperty property, VjoCcCtx vjoCcCtx) {
		return CodeCompletionUtils.getPropertyProposalReplaceStr(isGlobal,
				property, vjoCcCtx);
	}

	/**
	 * @see VjoCcOuterPropMethodProposalAdvisor
	 * @param isGlobal
	 * @param property
	 * @param vjoCcCtx
	 * @return
	 */
	protected String getOuterPropertyProposalReplaceStr(IJstProperty property) {
		return CodeCompletionUtils.getOuterPropertyProposalReplaceStr(property);
	}
	/**
	 * @see VjoCcOuterPropMethodProposalAdvisor
	 * @param isGlobal
	 * @param property
	 * @param vjoCcCtx
	 * @return
	 */
	protected String getOuterInnerTypeAsPropertyProposalReplaceStr(IJstType type) {
		return CodeCompletionUtils.getOuterPropertyProposalReplaceStr(type);
	}

	protected String getInnerTypeAsPropertyProposalReplaceStr(boolean isGlobal,
			IJstType jstType, VjoCcCtx vjoCcCtx) {
		return CodeCompletionUtils.getInnerTypeAsPropertyProposalReplaceStr(
				isGlobal, jstType, vjoCcCtx);
	}

	/**
	 * VjoCcOverrideProposalAdvisor
	 * 
	 * @param method
	 * @return
	 */
	protected String getReplaceStringForOverrideProposal(IJstMethod method, String indent) {
		return replaceSeperatorToken(CodeCompletionUtils
				.getReplaceStringForOverrideProposal(method, indent));
	}

	/**
	 * For different UI or OS system, different chars will be returned. "\n",
	 * "\n\r", "\r",
	 * 
	 * @return
	 */
	protected String getLineSeperator() {
		return "\n";
	}

	protected String getSimpleTypeName(IJstType type) {
		IJstNode node = type.getParentNode();
		String str = type.getSimpleName();
		while (node != null && node instanceof IJstType) {
			IJstType ttype = (IJstType) node;
			str = ttype.getSimpleName() + "." + str;
			node = ttype.getParentNode();
		}
		return str;
	}

	private String replaceSeperatorToken(String str) {
		return str.replaceAll(CodeCompletionUtils.SEPERATE_TOKEN,
				getLineSeperator());
	}

	protected String getTypeReplaceString(IJstType type, VjoCcCtx vjoCcCtx) {
		String replaceString = getSimpleTypeName(type);
		// see if need add package name
		boolean isInSyntaxScope = vjoCcCtx.isInSyntaxScope();
		if (isInSyntaxScope || vjoCcCtx.isInInactiveArea()) {
				replaceString = type.getName();
		} else if (vjoCcCtx.isInCommentArea()){
			String token = vjoCcCtx.getActingToken();
			if (token.contains(".")) {
				replaceString = type.getName();
			} else {
				replaceString = type.getSimpleName();
			}
		} else if (CodeCompletionUtils.isNativeType(type)) {
			replaceString = type.getName();
		} else {
			// TODO this.vj$ translation move down to advisor level 
			// the scope is not known here
			// see if need add this.vj$
//			if (vjoCcCtx.getPositionType() != VjoCcCtx.POSITION_AFTER_THISVJO) {
//				if (vjoCcCtx.isAfterFieldAccess()) {
//					replaceString = type.getName();
//				} else {
//					replaceString = CompletionConstants.THIS_VJO
//					+ CompletionConstants.DOT + type.getSimpleName();
//				}
//			} else {
				replaceString = type.getName();
//			}
		}
		return replaceString;

	}

	/**
	 * replace the cursor position token. It should be called after
	 * getCursorPosition(String str)
	 * 
	 * @param str
	 * @return
	 */
	protected String replaceCursorPositionToken(String str) {
		return str.replaceAll(CodeCompletionUtils.CURSOR_POSITION_TOKEN, "");
	}

	/**
	 * Get cursor position, it is defined in the generated string as token It
	 * should be called before replaceCursorPositionToken(String str) and after
	 * replaceSeperatorToken(String str)
	 * 
	 * @param str
	 * @return
	 */
	protected int getCursorPosition(String str) {
		int index = str.indexOf(CodeCompletionUtils.CURSOR_POSITION_TOKEN);
		if (index == -1) {
			return str.length();
		} else {
			return index;
		}
	}

}

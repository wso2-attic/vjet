/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.presenter;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.proposaldata.AbstractVjoProposalPresenter;

public class MockVjoCcPresenter extends AbstractVjoProposalPresenter {

	@Override
	public String getConstructorString(String modifier, String indent) {
		// TODO Auto-generated method stub
		return super.getConstructorString(modifier, indent);
	}

	@Override
	public String getFunctionString(int identifier, String functionName, boolean isInterface, String indent) {
		// TODO Auto-generated method stub
		return super.getFunctionString(identifier, functionName, isInterface, indent);
	}

	@Override
	public String getKeywordReplaceString(String name, String typeName, boolean inCommentArea, String indent) {
		// TODO Auto-generated method stub
		return super.getKeywordReplaceString(name, typeName, inCommentArea, indent);
	}

	@Override
	public String getLineSeperator() {
		// TODO Auto-generated method stub
		return super.getLineSeperator();
	}

	@Override
	public String getMethodProposalReplaceStr(boolean isGlobal,
			IJstMethod method, VjoCcCtx vjoCcCtx) {
		// TODO Auto-generated method stub
		return super.getMethodProposalReplaceStr(isGlobal, method, vjoCcCtx);
	}

	@Override
	public String getPropertyProposalReplaceStr(boolean isGlobal,
			IJstProperty property, VjoCcCtx vjoCcCtx) {
		// TODO Auto-generated method stub
		return super.getPropertyProposalReplaceStr(isGlobal, property, vjoCcCtx);
	}

	@Override
	public String getReplaceStringForOverrideProposal(IJstMethod method, String indent) {
		// TODO Auto-generated method stub
		return super.getReplaceStringForOverrideProposal(method, indent);
	}

	@Override
	public String getSimpleTypeName(IJstType type) {
		// TODO Auto-generated method stub
		return super.getSimpleTypeName(type);
	}

}

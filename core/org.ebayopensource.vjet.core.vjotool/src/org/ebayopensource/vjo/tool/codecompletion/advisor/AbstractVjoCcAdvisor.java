/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.vjo.tool.codecompletion.CodeCompletionUtils;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.proposaldata.VjoCcProposalData;

public abstract class AbstractVjoCcAdvisor implements IVjoCcAdvisor {

	public String abc;

	public String getId() {
		return getClass().getName();
	}

	protected void appendData(VjoCcCtx ctx, IJstNode node) {
		ctx.getReporter().addPropsal(new VjoCcProposalData(node, ctx, getId()));
	}
	
	protected void appendData(VjoCcCtx ctx, IJstNode node, boolean exactMatch) {
		VjoCcProposalData data = new VjoCcProposalData(node, ctx, getId());
		data.setAccurateMatch(exactMatch);
		ctx.getReporter().addPropsal(data);
	}

	protected boolean levelCheck(JstModifiers modifies, int[] levels) {
		return CodeCompletionUtils.levelCheck(modifies, levels);
	}



	/**
	 * the level should be public, protected, and private. current type (this.),
	 * should show all the method and propertyes. superType: public, and
	 * protected Type in same package: public, default. others: public
	 * 
	 * @return
	 */
	protected int[] getCallLevel(IJstType callingType, IJstType calledType) {
		return new int[0];
	}
	
	/**
	 * Compare exactly, consider case
	 * @param name
	 * @param token
	 * @return
	 */
	protected boolean exactMatch(String name, String token) {
		return name.startsWith(token);
	}
	
	/**
	 * Compare and ignore case
	 * @param name
	 * @param token
	 * @return
	 */
	protected boolean basicMatch(String name, String token) {
		return name.toLowerCase().startsWith(token.toLowerCase());
	}
	
	
}

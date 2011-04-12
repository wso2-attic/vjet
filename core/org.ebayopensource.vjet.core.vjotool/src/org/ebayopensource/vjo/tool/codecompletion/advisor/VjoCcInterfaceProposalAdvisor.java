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
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.proposaldata.VjoCcProposalData;

/**
 * advise interface found from type space.
 * example1:
 * ctype().satisfies('<cursor>')
 * 
 * example2:
 * itype().extends('<cursor>')
 * 
 *  need attributes:
 *  1.ctx.actingToken
 *  
 * 
 *
 */
public class VjoCcInterfaceProposalAdvisor extends VjoCcTypeProposalAdvisor {
	public static final String ID = VjoCcInterfaceProposalAdvisor.class.getName();
	protected boolean typeTypeCheck(IJstType jstType) {
		if (jstType.isInterface()) {
			return true;
		} else {
			return false;
		}
	}
	
	protected void appendData(VjoCcCtx ctx, IJstNode node, boolean exactMatch) {
		if(node instanceof IJstType){
			IJstType type = (IJstType)node;
			if(typeTypeCheck(type)){
				VjoCcProposalData data = new VjoCcProposalData(node, ctx, getId());
				data.setAccurateMatch(exactMatch);
				ctx.getReporter().addPropsal(data);
			}
		}
		
	}

}

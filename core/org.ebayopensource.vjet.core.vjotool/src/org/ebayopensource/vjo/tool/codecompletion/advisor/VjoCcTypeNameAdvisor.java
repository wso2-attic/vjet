/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor;

import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.proposaldata.VjoCcStringProposalData;

/**
 * advise proposal in vjo.ctype('<cursor>')<br>
 * or for keyword: itype, mtype, etype, atype, otype
 * 
 * needs attributes:
 * none
 * 
 * ProposalData: VjoCcStringProposalData
 * ProposalData.data: String
 * 
 * 
 *
 */
public class VjoCcTypeNameAdvisor extends AbstractVjoCcAdvisor {
	public static final String ID = VjoCcTypeNameAdvisor.class.getName();
	public void advise(VjoCcCtx ctx) {
		ctx.getReporter().addPropsal(new VjoCcStringProposalData(ctx.getTypeName(), ctx, getId()));
	}

}

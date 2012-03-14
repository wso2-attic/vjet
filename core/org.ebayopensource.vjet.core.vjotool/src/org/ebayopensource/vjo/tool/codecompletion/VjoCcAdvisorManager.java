/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoAttributedProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCCVjoUtilityAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcAliasProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcCTypeProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcConstructorGenProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcDerivedPropMethodAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcEnumElementAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcFunctionArgumentAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcFunctionGenProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcGlobalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcGlobalExtensionAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcInterfaceProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcKeywordAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcKeywordInCommentProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcKeywordInMethodProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcMTypeProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcNeedsItemProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcObjLiteralAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcOuterPropMethodProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcOverrideProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcOwnerTypeProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcPackageProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcParameterHintAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcParameterProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcPropMethodProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcStaticPropMethodProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcThisProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcTypeNameAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcTypeNameAliasProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcTypeProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcVariableProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.handler.VjoCcCommentHandler;
import org.ebayopensource.vjo.tool.codecompletion.handler.VjoCcHandler;

/**
 * Advisor Manager class. 1. registry or unregistiry advisor. 2. registry
 * proposal type policy 3. call advisors and generate proposal data
 * 
 * 
 * 
 */
public class VjoCcAdvisorManager {

	private static final boolean DEBUG = false;
	private static Map<String, IVjoCcAdvisor> s_advisorMap = new HashMap<String, IVjoCcAdvisor>();
	private IVjoCcHandler m_handler;
	private IVjoCcHandler m_commentHandler;

	static {
		registryAdvisor(new VjoCcPackageProposalAdvisor());
		registryAdvisor(new VjoCcTypeProposalAdvisor());
		registryAdvisor(new VjoCcTypeNameAliasProposalAdvisor());
		registryAdvisor(new VjoAttributedProposalAdvisor());
		registryAdvisor(new VjoCcOwnerTypeProposalAdvisor());
		registryAdvisor(new VjoCcAliasProposalAdvisor());
		registryAdvisor(new VjoCcOverrideProposalAdvisor());
		registryAdvisor(new VjoCcConstructorGenProposalAdvisor());
		registryAdvisor(new VjoCcFunctionGenProposalAdvisor());
		registryAdvisor(new VjoCcNeedsItemProposalAdvisor());
		registryAdvisor(new VjoCcCTypeProposalAdvisor());
		registryAdvisor(new VjoCcInterfaceProposalAdvisor());
		registryAdvisor(new VjoCcMTypeProposalAdvisor());
		registryAdvisor(new VjoCcKeywordAdvisor());
		registryAdvisor(new VjoCcKeywordInMethodProposalAdvisor());
		registryAdvisor(new VjoCcKeywordInCommentProposalAdvisor());
		registryAdvisor(new VjoCCVjoUtilityAdvisor());
		registryAdvisor(new VjoCcThisProposalAdvisor());
		registryAdvisor(new VjoCcVariableProposalAdvisor());
		registryAdvisor(new VjoCcOuterPropMethodProposalAdvisor());
		registryAdvisor(new VjoCcPropMethodProposalAdvisor());
		registryAdvisor(new VjoCcDerivedPropMethodAdvisor());
		registryAdvisor(new VjoCcParameterHintAdvisor());
		registryAdvisor(new VjoCcParameterProposalAdvisor());
		registryAdvisor(new VjoCcStaticPropMethodProposalAdvisor());
		registryAdvisor(new VjoCcEnumElementAdvisor());
		registryAdvisor(new VjoCcGlobalAdvisor());
		registryAdvisor(new VjoCcGlobalExtensionAdvisor());
		registryAdvisor(new VjoCcTypeNameAdvisor());
		registryAdvisor(new VjoCcObjLiteralAdvisor());
		registryAdvisor(new VjoCcFunctionArgumentAdvisor());
	}

	/**
	 * Create new instance with default VjoCcHandler
	 */
	public VjoCcAdvisorManager() {
		this.m_handler = new VjoCcHandler();
		this.m_commentHandler = new VjoCcCommentHandler();
	}

	/**
	 * @param handler Handler for completion in syntax part
	 */
	public VjoCcAdvisorManager(IVjoCcHandler handler) {
		if (handler != null) {
			this.m_handler = handler;
		} else {
			this.m_handler= new VjoCcHandler();
		}
		
	}
	/**
	 * @param handler Handler for completion in syntax part
	 * @param commentHandler Handler for completion in comment part
	 */
	public VjoCcAdvisorManager(IVjoCcHandler handler, IVjoCcHandler commentHandler) {
		this(handler);
		if (commentHandler != null) {
			this.m_commentHandler = commentHandler;
		} else {
			this.m_commentHandler = new VjoCcCommentHandler();
		}
	}
	
	public void advise(VjoCcCtx ctx) {
		String[] advisorIds = getAdvisorIds(ctx);
		ctx.setAdvisors(advisorIds);
		if (advisorIds.length == 0) {
			return;
		}
		for (String id : advisorIds) {
			IVjoCcAdvisor advisor = getAdvisor(id);
			
			if(DEBUG){
				System.out.println("using advisor: " + advisor.getId());
			}
			if (advisor != null) {
				advisor.advise(ctx);
			}
		}
	}
	
	public String[] getAdvisorIds (VjoCcCtx ctx) {
		if (ctx.isInCommentArea()) {
			return m_commentHandler.handle(ctx);
		} else {
			return m_handler.handle(ctx);
		}
	}

	protected List<IVjoCcAdvisor> getAllAdvisors() {
		Iterator<IVjoCcAdvisor> it = s_advisorMap.values().iterator();
		List<IVjoCcAdvisor> result = new ArrayList<IVjoCcAdvisor>();
		while (it.hasNext()) {
			result.add(it.next());
		}
		return result;
	}

	public static void registryAdvisor(IVjoCcAdvisor advisor) {
		String id = advisor.getId();
		if (id != null && !s_advisorMap.containsKey(id)) {
			s_advisorMap.put(id, advisor);
		}
	}

	public static IVjoCcAdvisor getAdvisor(String id) {
		if (s_advisorMap.containsKey(id)) {
			return s_advisorMap.get(id);
		} else {
			return null;
		}
	}

	protected boolean accept(VjoCcCtx ctx, IVjoCcAdvisor advisor) {
		return true;
	}

}

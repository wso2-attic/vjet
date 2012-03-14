/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.handler;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoAttributedProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCCVjoUtilityAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcAliasProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcCTypeProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcConstructorGenProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcDerivedPropMethodAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcEnumElementAdvisor;
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
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcTypeNameAliasProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcTypeProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcVariableProposalAdvisor;

public class VjoCcAdvisorSorter implements Comparator<IVjoCcProposalData>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Map<String, Integer> SORT_MAP = new HashMap<String, Integer>();
	private static final int EXACT_CON = -20;
	static {
		int i = 0;
		// inner function
		SORT_MAP.put(VjoCcVariableProposalAdvisor.ID, i);
		SORT_MAP.put(VjoCcParameterProposalAdvisor.ID, ++i);
		SORT_MAP.put(VjoCcObjLiteralAdvisor.ID, i++);

		// prop and method
		SORT_MAP.put(VjoCcPropMethodProposalAdvisor.ID, i);
	
		
		SORT_MAP.put(VjoCcParameterHintAdvisor.ID, ++i);
		SORT_MAP.put(VjoCcStaticPropMethodProposalAdvisor.ID, ++i);
		++i;
		++i;
		SORT_MAP.put(VjoCcOuterPropMethodProposalAdvisor.ID, ++i);
		++i;
		++i;
		++i;
		++i;
		SORT_MAP.put(VjoCcDerivedPropMethodAdvisor.ID, ++i);
		SORT_MAP.put(VjoCcEnumElementAdvisor.ID, ++i);
		
		SORT_MAP.put(VjoCcKeywordInCommentProposalAdvisor.ID, ++i);

		SORT_MAP.put(VjoCcOwnerTypeProposalAdvisor.ID, ++i);
		SORT_MAP.put(VjoCcAliasProposalAdvisor.ID, ++i);
		SORT_MAP.put(VjoCcTypeNameAliasProposalAdvisor.ID, ++i);
		SORT_MAP.put(VjoCcTypeProposalAdvisor.ID, ++i);
		SORT_MAP.put(VjoAttributedProposalAdvisor.ID, ++i);
		SORT_MAP.put(VjoCcNeedsItemProposalAdvisor.ID, i);
		SORT_MAP.put(VjoCcCTypeProposalAdvisor.ID, i);
		SORT_MAP.put(VjoCcInterfaceProposalAdvisor.ID, i);
		SORT_MAP.put(VjoCcMTypeProposalAdvisor.ID, i);
		// override
		SORT_MAP.put(VjoCcFunctionGenProposalAdvisor.ID, ++i);
		SORT_MAP.put(VjoCcOverrideProposalAdvisor.ID, ++i);
		SORT_MAP.put(VjoCcConstructorGenProposalAdvisor.ID, ++i);
		// global
		SORT_MAP.put(VjoCcGlobalAdvisor.ID, ++i);
		SORT_MAP.put(VjoCcGlobalExtensionAdvisor.ID, ++i);
		// keyword
		SORT_MAP.put(VjoCcKeywordAdvisor.ID, ++i);
		SORT_MAP.put(VjoCcKeywordInMethodProposalAdvisor.ID, i);
		SORT_MAP.put(VjoCcKeywordInMethodProposalAdvisor.ID, i);
		SORT_MAP.put(VjoCcThisProposalAdvisor.ID, i);
		SORT_MAP.put(VjoCCVjoUtilityAdvisor.ID, i);
		
		// package
		SORT_MAP.put(VjoCcPackageProposalAdvisor.ID, ++i);
	}
	private String token = "";

	public VjoCcAdvisorSorter(String token) {
		if (token == null || "".equals(token.trim())) {
			return;
		}
		this.token = token;
	}
	
	private int getOriginalRace(IVjoCcProposalData data) {
		String ad = data.getAdvisor();
		int i = SORT_MAP.get(ad);
		if (VjoCcPropMethodProposalAdvisor.ID.equals(ad) || VjoCcStaticPropMethodProposalAdvisor.ID.equals(ad)) {
			Object obj = data.getData();
			if (obj instanceof IJstMethod) {
				i = i + 2;
			}
		} else if (VjoCcOuterPropMethodProposalAdvisor.ID.equals(ad)) {
			Object obj = data.getData();
			if (obj instanceof IJstMethod) {
				i = i + 1;
				if (((IJstMethod)obj).isStatic()) {
					i = i+ 2;
				}
			} else if(obj instanceof IJstProperty) {
				if (((IJstProperty)obj).isStatic()) {
					i = i + 2;
				}
			}
			
		}
		return i;
		
	}

	public int compare(IVjoCcProposalData o1, IVjoCcProposalData o2) {
		int i1 = getOriginalRace(o1);
		int i2 = getOriginalRace(o2);
		//judge equal to token exactly
		if (isExactProposal(o1)) {
			i1 = EXACT_CON + i1;
		}
		if (isExactProposal(o2)) {
			i2 = EXACT_CON + i2;
		}
		//judge exact match (consider case info)
		if (!o1.isAccurateMatch()) {
			i1 = i1 - EXACT_CON;
		}
		if (!o2.isAccurateMatch()) {
			i2 = i2 - EXACT_CON;
		}
		if (i2 != i1) {
			return i1 - i2;
		} else{
			String name1 = o1.getName();
			String name2 = o2.getName();
			int val  =  name1.compareTo(name2);
//			System.out.println(name1 + ":" +name2 +"val=" + val);
			if(!name1.startsWith("_") && name2.startsWith("_")){
				return -100;
			}
			if(name1.startsWith("_") && !name2.startsWith("_")){
				return 10;
			}
			
			return val;
		}
		
	}

	private int clarifyPropertyAndMethod(IVjoCcProposalData o1, int i1) {
		// TODO Auto-generated method stub
		return 0;
	}

	private boolean isExactProposal(IVjoCcProposalData o2) {
		if ("".equals(token)) {
			return false;
		} else {
			String name = o2.getName();
			return name.equals(token);

		}
	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.statistics;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.dom.util.XmlWriterHelper;

/**
 * To house unspec'd component detection result
 * 
 * @since e605
 */
public class UnspecdCmpStatistics implements IV4Statistics{

	public String asString() {
		return XmlWriterHelper.asString(toXml());
	}

	public static final String NAME = "v4-unspec";
	
	public String getName() {
		return NAME;
	}

	public void reset() {
		m_unspecdInfo.clear();
	}

	/*
	 * To fit monitoring hub server existing structure, use element instead of attribute 
	 * <v4-unspec>
	 * 	<page>
	 * 		<pageName>pageName</pageName>
	 * 		<parentCmp>
	 * 			<parentName>parentName</parentName>
	 * 			<unspecdCmp>cmp name</unspecCmp>
	 * 		</parentCmp>
	 * 	</page>
	 * </v4-unspec>
	 */
	public DElement toXml() {
		DElement tagUnSpec = new DElement(getName());
		if(m_unspecdInfo != null){
			for (Entry<String, Map<String, Set<String>>> entryPage : m_unspecdInfo.entrySet()) {				
				DElement tagPage = new DElement("page");
				DElement tagPageName = new DElement("pageName");
				tagPageName.addRaw(entryPage.getKey());
				tagPage.add(tagPageName);
				tagUnSpec.add(tagPage);
				Map<String, Set<String>> pageInfo = entryPage.getValue();
				if(pageInfo != null){
					for(Entry<String, Set<String>> entryNode : pageInfo.entrySet()){
						DElement tagParentCmp = new DElement("parentCmp");
						DElement tagParentName = new DElement("parentName");
						tagParentName.addRaw(entryNode.getKey());
						tagParentCmp.add(tagParentName);
						tagPage.add(tagParentCmp);
						Set<String> unspecCmps = entryNode.getValue();
						if(unspecCmps != null){
							for (String cmp : unspecCmps) {
								DElement tagCmp = new DElement("unspecdCmp");
								tagCmp.addRaw(cmp);
								tagParentCmp.add(tagCmp);
							}
						}
					}
				}
			}
		}
		return tagUnSpec;
	}

	//=============
	//Unspec'd Component Detection
	//=============
	
	private Map<String, Map<String, Set<String>>> m_unspecdInfo;
	
	private Map<String, Map<String, Set<String>>> getUnspecdInfo(){
		if(m_unspecdInfo == null){
			m_unspecdInfo = new ConcurrentHashMap<String, Map<String,Set<String>>>();
		}
		return m_unspecdInfo;
	}
	
	private Map<String, Set<String>> getPageInfo(String page){
		Map<String, Map<String, Set<String>>> info = getUnspecdInfo();
		Map<String, Set<String>> pageInfo = info.get(page);
		if(pageInfo == null){
			pageInfo = new ConcurrentHashMap<String, Set<String>>();
			info.put(page, pageInfo);
		}
		return pageInfo;
	}
	
	public void markUnspecdUsage(String page, String parentCmp, String unspecdCmp){
		Map<String, Set<String>> pageInfo = getPageInfo(page);
		Set<String> info = pageInfo.get(parentCmp);
		if(info == null){
			info = new HashSet<String>();
			pageInfo.put(parentCmp, info);
		}
		info.add(unspecdCmp);
	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.resolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.meta.IJsCommentMeta;
import org.ebayopensource.dsf.jstojava.parser.comments.ParseException;
import org.ebayopensource.dsf.jstojava.parser.comments.VjComment;
import org.ebayopensource.dsf.jstojava.translator.BaseFindTypeSupport;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;

public class FunctionParamsMetaMapping {
	
	private Map<String, Map<String, MetaExtension>> m_maps =
		new HashMap<String, Map<String, MetaExtension>>();
	
	private final String m_groupId;
	
	public FunctionParamsMetaMapping(String groupId) {
		m_groupId = groupId;
	}
	
	public String getGroupId() {
		return m_groupId;
	}
	
	public boolean hasMetaExtension(String targetFunc) {
		return m_maps.containsKey(targetFunc);
	}
	
	public IMetaExtension getExtension(String targetFunc, String key) {
		Map<String, MetaExtension> funcMetaMap = m_maps.get(targetFunc);
		if (funcMetaMap == null) {
			return null;
		}
		return funcMetaMap.get(key);
	}
	
	public FunctionParamsMetaMapping addMapping(
		String targetFunc, String key, String[] metaArr) {
		List<IJsCommentMeta> metaList = new ArrayList<IJsCommentMeta>();
		for (String metaDef : metaArr) {
			try {
				metaList.add(VjComment.parse("//>" + metaDef));
			} catch (ParseException e) {
				// TODO add error handling
				e.printStackTrace();
			};
		}
		if (metaList.isEmpty()) {
			return this;
		}
		Map<String, MetaExtension> funcMetaMap = m_maps.get(targetFunc);
		if (funcMetaMap == null) {
			funcMetaMap = new HashMap<String, MetaExtension>();
			m_maps.put(targetFunc, funcMetaMap);
		}
		funcMetaMap.put(key, new MetaExtension(metaList));
		return this;
	}
	
	public IMetaExtension getExtentedArgBinding(String targetFunc, String key) {
		return getExtension(targetFunc, key);
	}
	
	public Set<String> getSupportedTargetFuncs() {
		return m_maps.keySet();
	}
	
	public static class MetaExtension implements IMetaExtension {
		private List<IJsCommentMeta> m_metaList;
		
		MetaExtension(List<IJsCommentMeta> metaList) {
			m_metaList = metaList;
		}
		
		public IJstMethod getMethod() {
			return TranslateHelper.MethodTranslateHelper
				.createJstSynthesizedMethod(m_metaList,
					new BaseFindTypeSupport(), "_fn_");
		}
	}
}

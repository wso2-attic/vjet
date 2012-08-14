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

public class FunctionMetaMapping implements IFunctionMetaMapping {
	
	private Map<String, Map<String, MetaExtension>> m_maps =
		new HashMap<String, Map<String, MetaExtension>>();
	
	private final String[] m_groupId;
	
	public FunctionMetaMapping(String groupId) {
		m_groupId = new String[]{groupId};
	}
	
	/* (non-Javadoc)
	 * @see org.ebayopensource.dsf.jstojava.resolver.IFunctionMetaMapping#getGroupId()
	 */
	@Override
	public String[] getGroupIds() {
		return m_groupId;
	}
	
	/* (non-Javadoc)
	 * @see org.ebayopensource.dsf.jstojava.resolver.IFunctionMetaMapping#hasMetaExtension(java.lang.String)
	 */
	@Override
	public boolean hasMetaExtension(String targetFunc) {
		return m_maps.containsKey(targetFunc);
	}
	
	/* (non-Javadoc)
	 * @see org.ebayopensource.dsf.jstojava.resolver.IFunctionMetaMapping#getExtension(java.lang.String, java.lang.String)
	 */
	@Override
	public IMetaExtension getExtension(String targetFunc, String key) {
		Map<String, MetaExtension> funcMetaMap = m_maps.get(targetFunc);
		if (funcMetaMap == null) {
			return null;
		}
		return funcMetaMap.get(key);
	}
	
	public IFunctionMetaMapping addMapping(
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
	
	/* (non-Javadoc)
	 * @see org.ebayopensource.dsf.jstojava.resolver.IFunctionMetaMapping#getExtentedArgBinding(java.lang.String, java.lang.String)
	 */
	@Override
	public IMetaExtension getExtentedArgBinding(String targetFunc, String key) {
		return getExtension(targetFunc, key);
	}
	
	/* (non-Javadoc)
	 * @see org.ebayopensource.dsf.jstojava.resolver.IFunctionMetaMapping#getSupportedTargetFuncs()
	 */
	@Override
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

	@Override
	public boolean isFirstArgumentType(String targetFunc) {
		// TODO Auto-generated method stub
		return false;
	}
}

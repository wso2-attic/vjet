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

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.meta.JsTypingMeta;
import org.ebayopensource.dsf.jstojava.parser.comments.JsCommentMeta;
import org.ebayopensource.dsf.jstojava.parser.comments.ParseException;
import org.ebayopensource.dsf.jstojava.parser.comments.VjComment;
import org.ebayopensource.dsf.jstojava.report.ErrorReporter;
import org.ebayopensource.dsf.jstojava.translator.IFindTypeSupport;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;

/**
 * Registry of function-return-type resolvers at Lib/Group level from meta bootstrap.
 * 
 * Say you take document.create("div") 
 * 
 * return value should be HTMLDivElement based on "div" string
 * 
 * There is a way to do this in JS and through a Java extension
 * 
 */
public class TypeResolverRegistry {
	
	private static final TypeResolverRegistry s_instance = new TypeResolverRegistry();
	private Map<String, List<ITypeResolver>> m_resolvers = new HashMap<String, List<ITypeResolver>>();
	
	public static TypeResolverRegistry getInstance() {
		return s_instance;
	}
	
	public TypeResolverRegistry addResolver(String key, ITypeResolver resolver) {
		List<ITypeResolver> resolverList = m_resolvers.get(key);
		if (resolverList == null) {
			resolverList = new ArrayList<ITypeResolver>(1);
			m_resolvers.put(key, resolverList);
		}
		resolverList.add(resolver);
		return this;
	}
	
	public IJstType resolve(String key, String[] args) {
		List<ITypeResolver> resolverList = m_resolvers.get(key);
		if (resolverList == null) {
			return null;
		}
		for (int i = 0; i < resolverList.size(); i++) {
			ITypeResolver resolver = resolverList.get(i);
			String typeName = resolver.resolve(args);
			if(typeName==null || typeName.equals("")){
				return null;
			}
			if(typeName.contains("org.mozilla.mod.javascript.Undefined")){
				return null;
			}
			
			try {
				final JsCommentMeta commentMeta = VjComment.parse("//>" + typeName);;
				final JsTypingMeta typingMeta = commentMeta.getTyping();
				IFindTypeSupport findSupport = new IFindTypeSupport() {
					
					@Override
					public char[] getOriginalSource() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public ILineInfoProvider getLineInfoProvider() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public ErrorReporter getErrorReporter() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public IJstType getCurrentType() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public IJstType findTypeByName(String name) {
						// TODO Auto-generated method stub
						return null;
					}
				};;;
				return TranslateHelper.findType(findSupport, typingMeta, commentMeta);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		}
		return null;
	}
	
	public boolean hasResolver(String key) {
		return m_resolvers.containsKey(key);
	}
	
	public void clear(String groupId) {
		for (List<ITypeResolver> resolverList : m_resolvers.values()) {
			for (int i = resolverList.size() - 1; i >=0; i--) {
				ITypeResolver resolver = resolverList.get(i);
				for(String group : resolver.getGroupIds()){
					if (groupId.endsWith(group)) {
						resolverList.remove(resolver);
					}
				}
				
			}
		}
	}
	
	public void clearAll() {
		m_resolvers.clear();
	}
}

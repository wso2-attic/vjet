/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import org.ebayopensource.dsf.jsnative.anno.Constructor;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.ebayopensource.dsf.jsnative.anno.Static;
import org.ebayopensource.dsf.jst.IJstParseController;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IScriptUnit;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.dsf.util.JavaSourceLocator;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.eclipse.core.runtime.FileLocator;

public abstract class VjoCcBaseTest{

	private static JstTypeSpaceMgr m_jstTypeSpaceMgr;
	
	public VjoCcCtxForTest getEmptyContext() {
		return getEmptyContext(new TypeName(CodeCompletionUtil.GROUP_NAME, ""));
	}

	public void reset(){
		//m_jstTypeSpaceMgr = null;
	}
	
	
	
	/**
	 * @param typeName
	 *            the correct name of current jst type, it must obey the package +
	 *            name, and consistance with the file path
	 * @return
	 */
	protected VjoCcCtxForTest getEmptyContext(TypeName typeName) {
		VjoCcCtxForTest ctx = new VjoCcCtxForTest(getJstTypeSpaceMgr(), typeName);
		return ctx;
	}

	public JstTypeSpaceMgr getJstTypeSpaceMgr() {
		if (m_jstTypeSpaceMgr == null) {
			m_jstTypeSpaceMgr = CodeCompletionUtil.loadJsToTypeSpace();
		}
//		CodeCompletionUtil.printTypes(m_jstTypeSpaceMgr);
		return m_jstTypeSpaceMgr;
	}
	
	public static IJstParseController getJstParseController() {
		if (m_jstTypeSpaceMgr == null) {
			m_jstTypeSpaceMgr = CodeCompletionUtil.loadJsToTypeSpace();
		}
		return CodeCompletionUtil.getJstParseController();
	}
	
	public void loadSingleJs(String jsName, String suffix) {
		CodeCompletionUtil.addJsToTypeSpace(jsName, suffix, getJstTypeSpaceMgr());
	}

	public IJstType getJstType(TypeName typeName) {
		IJstType type = getJstTypeSpaceMgr().getQueryExecutor().findType(typeName);
		return type;
	}
	
	
	protected IJstType getJstType(String group, String js) {
		TypeName typeName = new TypeName(group, js);
		IJstType type = getJstTypeSpaceMgr().getQueryExecutor().findType(typeName);
		return type;
	}
	
	protected IScriptUnit getScriptUnit(String groupName, String fileName, String source) {
		VjoParser parser = new VjoParser();
		return parser.parse(groupName, fileName, source);
	}
	
	protected List<String> getSuggestions(Class clazz) {
		Method[] methods = clazz.getDeclaredMethods();

		List<String> list = new ArrayList<String>();
		int counter = 0;
		for (Method m : methods) {
			// remove get from method name if it is property
			if (m.isAnnotationPresent(Property.class)) {
				String removeMe = "get";
				String propName = m.getName().substring(removeMe.length());
				if (!m.isAnnotationPresent(Static.class)
						&& !propName.equals("URL")
						&& !propName.equals("URLUnencoded")
						&& !propName.equals("Infinity")
						&& !propName.equals("NaN")) {
					propName = propName.substring(0, 1).toLowerCase()
							+ propName.substring(1);
				}
				if (!list.contains(propName))
					list.add(propName);
			} else if (m.isAnnotationPresent(Function.class)
					|| m.isAnnotationPresent(Constructor.class)) {
				if (m.getName().equalsIgnoreCase("_void")) {
					list.add("void");
				} else {
					list.add(m.getName());
				}
			}
			counter++;
		}
		return list;
	}
	
	public int firstPositionInFile(String string, IJstType type) {
		URL url = getSourceUrl(type.getName(), ".js");
		String content = VjoParser.getContent(url);

		int position = content.indexOf(string);
		if (position >= 0) {
			return position + string.length();
		}
		return -1;
	}
	
	public int firstBeforePositionInFile(String string, IJstType type) {
		URL url = getSourceUrl(type.getName(), ".js");
		String content = VjoParser.getContent(url);

		int position = content.indexOf(string);
		if (position >= 0) {
			return position - 1;
		}
		return -1;
	}

	public int lastPositionInFile(String string, IJstType type) {
		URL url = getSourceUrl(type.getName(), ".js");
		System.out.println("source url = "+ url);
		String content = VjoParser.getContent(url);

		if (string == null)
			return content.length();

		int position = content.lastIndexOf(string);
		if (position >= 0) {
			return position + string.length();
		}

		return -1;
	}
	
	public int lastBeforePositionInFile(String string, IJstType type) {
		URL url = getSourceUrl(type.getName(), ".js");
		String content = VjoParser.getContent(url);

		if (string == null)
			return content.length();

		int position = content.lastIndexOf(string);
		if (position >= 0) {
			return position - 1;
		}

		return -1;
	}
	
	protected URL getSourceUrl(String typeName, String suffix) {
		URL url = JavaSourceLocator.getInstance().getSourceUrl(typeName, suffix);
		if (url == null) {
			typeName = typeName.replace(".", "/");
			url = this.getClass().getClassLoader().getResource(typeName + suffix);
		}
		if(url.getProtocol().contains("bundleresource")){
			try {
				url = FileLocator.resolve(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return url;
	}
}

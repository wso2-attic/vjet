/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.overloadtests;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.util.JavaSourceLocator;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.engine.VjoCcEngine;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.junit.Assert;

public class VjoCcOverloadUtil {
	
	private VjoCcEngine engine;
	
	
	public VjoCcOverloadUtil() {
		initEngine();
	}
	
	private final void initEngine(){
		if(engine==null){
			engine = new VjoCcEngine(CodeCompletionUtil.getJstParseController());			
		}
	}
	
	
	// Get actual proposal list from engine
	public List<String> getActMethParamList(Proposals prop){
		initEngine();
		URL url = getSourceUrl(prop.jstType.getName(), ".js");
		String content = VjoParser.getContent(url);
		List<IVjoCcProposalData> propList = engine.complete(
				CodeCompletionUtil.GROUP_NAME,prop.jstType.getName(), content, prop.position);
		
		List<String> strList = getStringProposals(propList);
		List<String> actMethList = new ArrayList<String>();
		String args = "";
		
		Assert.assertTrue("proposal : " + prop.funcName + 
				" is not included in proposal list" + 
				strList, strList.contains(prop.funcName));
		
		for (IVjoCcProposalData data : propList){
			Object obj = data.getData();
			
			if (obj instanceof IJstMethod){
				IJstMethod m = (IJstMethod) obj;
				if(m.getName().getName().equalsIgnoreCase(prop.funcName)){
					List<JstArg> argList = m.getArgs();
					
					if (argList.size() == 0){
						actMethList.add(null);
					}
					else{
						for (JstArg arg : argList){
							IJstType type = arg.getType();
							String name = arg.getName();
							args += type.getSimpleName() + " " + name +", ";
						}
						args = args.substring(0, args.length()-2);
						actMethList.add(args);
						args = "";
					}
				}
			}
		}
		
		return actMethList;
	}
	
	public void checkProposals(List<String> expectedList, List<String> actualList){
		initEngine();
		Assert.assertTrue("actual proposals "+actualList+
				" does not match with expected proposals "+expectedList,
				(expectedList.containsAll(actualList)) && actualList.containsAll(expectedList));
	}
	
	public class Proposals{
		
		IJstType jstType;
		int position;
		String funcName;
		
		public Proposals(IJstType jstType, int position, String funcName){
			this.jstType = jstType;
			this.position = position;
			this.funcName = funcName;
		}
		
	}

	public static List<String> getStringProposals(List<IVjoCcProposalData> dataList){
		List<String> propList = new ArrayList<String>();
		for (IVjoCcProposalData data : dataList){
			Object obj = data.getData();
			if (obj instanceof IJstMethod){
				obj = ((IJstMethod) obj).getName().getName();
			} else if (obj instanceof IJstProperty){
				obj = ((IJstProperty) obj).getName().getName();
			} else if (obj instanceof JstArg){
				obj = ((JstArg) obj).getName();
			} else if (obj instanceof IJstType){
				obj = ((IJstType) obj).getSimpleName();
			} else if (obj instanceof JstIdentifier){
				obj = ((JstIdentifier) obj).getName();
			} else if (obj instanceof JstPackage){
				obj = ((JstPackage) obj).getName();
			}
			propList.add((String)obj);
		}
		return propList;
	}

	public URL getSourceUrl(String typeName, String suffix) {
		URL url = JavaSourceLocator.getInstance().getSourceUrl(typeName, suffix);
		if (url == null) {
			typeName = typeName.replace(".", "/");
			url = this.getClass().getClassLoader().getResource(typeName + suffix);
		}
		return url;
	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.engine;




import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import junit.framework.AssertionFailedError;

import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



//@Category({P1,FAST,UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcJsNativeTypeGlobalTests extends VjoCcBaseTest {
	private VjoCcEngine engine;
	
	@Before
	public void setUp() throws Exception {
		engine = new VjoCcEngine(CodeCompletionUtil.getJstParseController());
	}
	
	
	@Test
	public void testJsNativeMethodsProps() throws Exception {
		String js = "engine.JsNativeTypesGlobalProps";
		String pkg = "org.ebayopensource.dsf.jsnative.global";
		Class<?> [] classes = VjoCcJsNativeTypeTests.getClasses(pkg);
		List<JsNativeResults> results = new ArrayList<JsNativeResults>();
		
		for (Class<?> clazz : classes){
			String typeName = getTypeName(clazz);
			if (typeName == null) {
				continue;
			}			
			IJstType type = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, typeName);
			List<IJstType> typeList = new ArrayList<IJstType>();
			typeList.add(type);
			populateExtendedTypes(type, typeList);
			
			List<String> expectedList = new ArrayList<String>();
			for (IJstType tp : typeList){
//				System.err.println(tp.getSimpleName());
				populateExpectedMethodList(tp, expectedList);
			}
			
			IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
			int position = firstPositionInFile(typeName+"Var.", jstType);
			String[] names = new String[] {};
			if (position > 0)
				checkProposals(jstType, position, expectedList.toArray(names), results, type);
		}
		
		if (results.size() > 0){
			StringBuffer sb = new StringBuffer();
			for (JsNativeResults res : results){
				sb = sb.append(res.toString() + "\n");
			}
			throw new AssertionFailedError("JsNativeTest failed : \n" + sb);
		}
	}
	
	@Test
	public void testJsNativeAllProps() throws Exception {
		String js = "engine.JsNativeTypesGlobalProps";
		String pkg = "org.ebayopensource.dsf.jsnative.global";
		Class<?> [] classes = VjoCcJsNativeTypeTests.getClasses(pkg);
		List<JsNativeResults> results = new ArrayList<JsNativeResults>();
		
		for (Class<?> clazz : classes){
			String typeName = getTypeName(clazz);
			if (typeName == null) {
				continue;
			}
			
			IJstType type = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, typeName);
			List<IJstType> typeList = new ArrayList<IJstType>();
			typeList.add(type);
			populateExtendedTypes(type, typeList);
			
			List<String> expectedList = new ArrayList<String>();
			for (IJstType tp : typeList){
//				System.err.println(tp.getSimpleName());
				populateExpectedList(tp, expectedList);
			}
			
			IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
			int position = lastPositionInFile(typeName+"Var.", jstType);
			String[] names = new String[] {};
			if (position > 0)
				checkProposals(jstType, position, expectedList.toArray(names), results, type);
		}
		
		if (results.size() > 0){
			StringBuffer sb = new StringBuffer();
			for (JsNativeResults res : results){
				sb = sb.append(res.toString() + "\n");
			}
			throw new AssertionFailedError("JsNativeTest failed : \n" + sb);
		}
	}
	
	@Test
	public void testJsNativePropertyProps() throws Exception {
		String js = "engine.JsNativeTypesGlobalProps";
		String pkg = "org.ebayopensource.dsf.jsnative.global";
		Class<?> [] classes = VjoCcJsNativeTypeTests.getClasses(pkg);
		List<JsNativeResults> results = new ArrayList<JsNativeResults>();
		
		for (Class<?> clazz : classes){
			String typeName = getTypeName(clazz);
			if (typeName == null) {
				continue;
			}
			
			IJstType type = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, typeName);
			List<IJstType> typeList = new ArrayList<IJstType>();
			typeList.add(type);
			populateExtendedTypes(type, typeList);
			
			List<String> expectedList = new ArrayList<String>();
			for (IJstType tp : typeList){
//				System.err.println(tp.getSimpleName());
				populateExpectedList(tp, expectedList);
			}
			
			IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
			int position = 0;
			if (typeName.equalsIgnoreCase("Enumerator") || typeName.equalsIgnoreCase("Function"))
				position = firstPositionInFile(typeName+"(new String()).", jstType);
			else 
				position = firstPositionInFile(typeName+"().", jstType);
			String[] names = new String[] {};
			if (position > 0)
				checkProposals(jstType, position, expectedList.toArray(names), results, type);
		}
		
		if (results.size() > 0){
			StringBuffer sb = new StringBuffer();
			for (JsNativeResults res : results){
				sb = sb.append(res.toString() + "\n");
			}
			throw new AssertionFailedError("JsNativeTest failed : \n" + sb);
		}
	}
	
	@Test
	public void testJsNativeMethodsProtos() throws Exception {
		String js = "engine.JsNativeTypesGlobalProtos";
		String pkg = "org.ebayopensource.dsf.jsnative.global";
		Class<?> [] classes = VjoCcJsNativeTypeTests.getClasses(pkg);
		List<JsNativeResults> results = new ArrayList<JsNativeResults>();
		
		for (Class<?> clazz : classes){
			String typeName = getTypeName(clazz);
			if (typeName == null) {
				continue;
			}
			
			IJstType type = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, typeName);
			List<IJstType> typeList = new ArrayList<IJstType>();
			typeList.add(type);
			populateExtendedTypes(type, typeList);
			
			List<String> expectedList = new ArrayList<String>();
			for (IJstType tp : typeList){
//				System.err.println(tp.getSimpleName());
				populateExpectedMethodList(tp, expectedList);
			}
			
			IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
			int position = firstPositionInFile(typeName+"Var.", jstType);
			String[] names = new String[] {};
			if (position > 0)
				checkProposals(jstType, position, expectedList.toArray(names), results, type);
		}
		
		if (results.size() > 0){
			StringBuffer sb = new StringBuffer();
			for (JsNativeResults res : results){
				sb = sb.append(res.toString() + "\n");
			}
			throw new AssertionFailedError("JsNativeTest failed : \n" + sb);
		}
	}
	
	@Test
	public void testJsNativeAllProtos() throws Exception {
		String js = "engine.JsNativeTypesGlobalProtos";
		String pkg = "org.ebayopensource.dsf.jsnative.global";
		Class<?> [] classes = VjoCcJsNativeTypeTests.getClasses(pkg);
		List<JsNativeResults> results = new ArrayList<JsNativeResults>();
		
		for (Class<?> clazz : classes){
			String typeName = getTypeName(clazz);
			if (typeName == null) {
				continue;
			}
			
			IJstType type = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, typeName);
			List<IJstType> typeList = new ArrayList<IJstType>();
			typeList.add(type);
			populateExtendedTypes(type, typeList);
			
			List<String> expectedList = new ArrayList<String>();
			for (IJstType tp : typeList){
//				System.err.println(tp.getSimpleName());
				populateExpectedList(tp, expectedList);
			}
			
			IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
			int position = lastPositionInFile(typeName+"Var.", jstType);
			String[] names = new String[] {};
			if (position > 0)
				checkProposals(jstType, position, expectedList.toArray(names), results, type);
		}
		
		if (results.size() > 0){
			StringBuffer sb = new StringBuffer();
			for (JsNativeResults res : results){
				sb = sb.append(res.toString() + "\n");
			}
			throw new AssertionFailedError("JsNativeTest failed : \n" + sb);
		}
	}
	
	@Test
	public void testJsNativePropertyProtos() throws Exception {
		String js = "engine.JsNativeTypesGlobalProtos";
		String pkg = "org.ebayopensource.dsf.jsnative.global";
		Class<?> [] classes = VjoCcJsNativeTypeTests.getClasses(pkg);
		List<JsNativeResults> results = new ArrayList<JsNativeResults>();
		
		for (Class<?> clazz : classes){
			String typeName = getTypeName(clazz);
			if (typeName == null) {
				continue;
			}
			
			IJstType type = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, typeName);
			List<IJstType> typeList = new ArrayList<IJstType>();
			typeList.add(type);
			populateExtendedTypes(type, typeList);
			
			List<String> expectedList = new ArrayList<String>();
			for (IJstType tp : typeList){
//				System.err.println(tp.getSimpleName());
				populateExpectedList(tp, expectedList);
			}
			
			IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
			int position = 0;
			if (typeName.equalsIgnoreCase("Enumerator") || typeName.equalsIgnoreCase("Function"))
				position = firstPositionInFile(typeName+"(new String()).", jstType);
			else 
				position = firstPositionInFile(typeName+"().", jstType);
			String[] names = new String[] {};
			if (position > 0)
				checkProposals(jstType, position, expectedList.toArray(names), results, type);
		}
		
		if (results.size() > 0){
			StringBuffer sb = new StringBuffer();
			for (JsNativeResults res : results){
				sb = sb.append(res.toString() + "\n");
			}
			throw new AssertionFailedError("JsNativeTest failed : \n" + sb);
		}
	}
	
	private void populateExtendedTypes(IJstType type, List<IJstType> typeList){
		for (IJstType t : type.getExtends()){
			if (!typeList.contains(t))
				typeList.add(t);
			if (t.getExtends() != null && t.getExtends().size() > 0){
				populateExtendedTypes(t, typeList);
			}
		}
	}
	
	private void populateExpectedMethodList(IJstType type, List<String> list){
		for (IJstMethod m : type.getMethods(false)){
			if (!(m.getOriginalName().startsWith("_")
					|| m.getOriginalName().startsWith("__")))
				if (isSupportedAnnotation(m))
					list.add(m.getOriginalName());
		}
	}
	
	private void populateExpectedList(IJstType type, List<String> list){
		for (IJstProperty prop : type.getProperties(false)){
			if (!(prop.getName().getName().startsWith("_")
					|| prop.getName().getName().startsWith("__")))
				if (isSupportedAnnotation(prop))
					list.add(prop.getName().getName());
		}
		for (IJstMethod m : type.getMethods(false)){
			if (!(m.getOriginalName().startsWith("_")
					|| m.getOriginalName().startsWith("__")))
				if (isSupportedAnnotation(m))
					list.add(m.getOriginalName());
		}
	}
	
	private boolean isSupportedAnnotation(IJstNode node){
		
		return true;
		
//		boolean supported = true;;
//		if (node.getAnnotation(BrowserSupport.class.getSimpleName()) != null){
//			IJstAnnotation ann1 = node.getAnnotation(SupportedBy.class.getSimpleName());
//			List<IExpr> ll = ann1.values();
//			for (IExpr pr : ll){
//				if (pr.toExprText().contains("BrowserType.NONE")
//						|| pr.toExprText().contains("DomLevel.THREE"))
//					supported = false;
//			}
//		}
//		return supported;
	}
	
	public void checkProposals(IJstType jstType, int position, String [] names,
			List<JsNativeResults> results, IJstType testedNativeType){
		URL url = getSourceUrl(jstType.getName(), ".js");
		String content = VjoParser.getContent(url);
		List<IVjoCcProposalData> propList = engine.complete(
				CodeCompletionUtil.GROUP_NAME,jstType.getName(),content, position);
		List<String> strList = getStringProposals(propList);
		
		JsNativeResults res = new JsNativeResults();
		res.setType(testedNativeType.getName());
		for (String name : names){
			if (!strList.contains(name)){
				res.addNoProposals(name);
			}
		}
		if (res.getNoProposals().size() > 0)
			results.add(res);
	}
	
	private static List<String> getStringProposals(List<IVjoCcProposalData> dataList){
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
	
	public class JsNativeResults {
    	public List<String> noProposals = new ArrayList<String>();
    	public String type  = "";
    	
    	public void setType(String typeName){
    		this.type = typeName;
    	}
    	public void addNoProposals(String prop){
    		noProposals.add(prop);
    	}
    	public List<String> getNoProposals(){
    		return this.noProposals;
    	}
    	public String toString(){
    		return "Type # " + this.type + 
    		" : Code Proposals do not contain the following proposals : " + noProposals;
    	}
    }
	
	private static String getTypeName(Class<?> clz) {
		String clzSimpleName = clz.getSimpleName();
		if (clzSimpleName.equalsIgnoreCase("Global")
			|| clzSimpleName.equalsIgnoreCase("Math")
			|| clzSimpleName.equalsIgnoreCase("Object")
			|| clzSimpleName.equalsIgnoreCase("ObjLiteral")
			|| clzSimpleName.equalsIgnoreCase("PrimitiveBoolean")){
			return null;
		}
		Annotation ann = clz.getAnnotation(Alias.class);
		return ann == null ? clzSimpleName : ((Alias)ann).value();
	}
}

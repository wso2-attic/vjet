/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.engine;





import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
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
public class VjoCcJsNativeTypeTests extends VjoCcBaseTest {
	private VjoCcEngine engine;
	
	@Before
	public void setUp() throws Exception {
		engine = new VjoCcEngine(CodeCompletionUtil.getJstParseController());
	}
	
	
	@Test //@Category({SLOW})
	public void testJsNativeProps() throws Exception {
		String js = "engine.NativeTypeTest";
		String pkg = "org.ebayopensource.dsf.jsnative";
		Class<?> [] classes = getClasses(pkg);
		List<JsNativeResults> results = new ArrayList<JsNativeResults>();
		
		for (Class<?> clazz : classes){
			String typeName = "";
			if (clazz.getAnnotation(Alias.class) != null){
				typeName = ((Alias)clazz.getAnnotation(Alias.class)).value();
			}
			else {
				typeName = clazz.getSimpleName();
			}
			
			IJstType type = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, typeName);
			
			if(type==null){
				type = getJstType(JstTypeSpaceMgr.JS_BROWSER_GRP, typeName);
			}
			
			List<IJstType> typeList = new ArrayList<IJstType>();
			typeList.add(type);
			populateExtendedTypes(type, typeList);
			
			List<String> expectedList = new ArrayList<String>();
			for (IJstType tp : typeList){
//				System.err.println(tp.getSimpleName());
				populateExpectedList(tp, expectedList);
			}
			
			IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
			String posString = typeName.substring(0, 1).toLowerCase() + typeName.substring(1);
			int position = firstPositionInFile(posString+".", jstType);
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
	
	@Test //@Category({SLOW})
	public void testJsNativeProtos() throws Exception {
		String js = "engine.NativeTypeProtosTest";
		String pkg = "org.ebayopensource.dsf.jsnative";
		Class<?> [] classes = getClasses(pkg);
		List<JsNativeResults> results = new ArrayList<JsNativeResults>();
		
		for (Class<?> clazz : classes){
			String typeName = "";
			if (clazz.getAnnotation(Alias.class) != null){
				typeName = ((Alias)clazz.getAnnotation(Alias.class)).value();
			}
			else {
				typeName = clazz.getSimpleName();
			}
			
			IJstType type = getJstType(JstTypeSpaceMgr.JS_NATIVE_GRP, typeName);
			if(type==null){
				type = getJstType(JstTypeSpaceMgr.JS_BROWSER_GRP, typeName);
				
			}
			
			List<IJstType> typeList = new ArrayList<IJstType>();
			typeList.add(type);
			populateExtendedTypes(type, typeList);
			
			List<String> expectedList = new ArrayList<String>();
			for (IJstType tp : typeList){
//				System.err.println(tp.getSimpleName());
				populateExpectedList(tp, expectedList);
			}
			
			IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
			String posString = typeName.substring(0, 1).toLowerCase() + typeName.substring(1);
			int position = lastPositionInFile(posString+".", jstType);
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
	
	private void populateExpectedList(IJstType type, List<String> list){
		if (type.getName().startsWith("HTMLSelectElement")){
//			System.out.println("");
		}
		for (IJstProperty prop : type.getProperties()){
			if (!(prop.getName().getName().startsWith("_")
					|| prop.getName().getName().startsWith("__")
					|| list.contains(prop.getName().getName())))
				if(isSupportedAnnotation(prop))
					list.add(prop.getName().getName());
		}
		for (IJstMethod m : type.getMethods()){
			if (!(m.getOriginalName().startsWith("_")
					|| m.getOriginalName().startsWith("__")
					|| list.contains(m.getOriginalName())))
				if(isSupportedAnnotation(m))
					list.add(m.getOriginalName());
		}
	}
	
	private boolean isSupportedAnnotation(IJstNode node){
		boolean supported = true;;
//		if (node.getAnnotation(SupportedBy.class.getSimpleName()) != null){
//			IJstAnnotation ann1 = node.getAnnotation(SupportedBy.class.getSimpleName());
//			List<IExpr> ll = ann1.values();
//			for (IExpr pr : ll){
//				if (pr.toExprText().contains("BrowserType.NONE")
//						|| pr.toExprText().contains("DomLevel.THREE"))
//					supported = false;
//			}
//		}
		return supported;
	}
	
//	private void populateExtraMethods(List<String> list, Class<?> [] classes){
//		for(Class<?> s : classes){
//			for (Method method : s.getMethods()){
//				String entry = method.getDeclaringClass().getSimpleName() 
//					+ "." + method.getName();
//				if(method.getAnnotation(ARename.class) != null && !list.contains(entry)){
//					list.add(entry);
//				}
//				if(method.getAnnotation(AProperty.class) != null && !list.contains(entry)){
//					list.add(entry);
//				}
//				if(method.getAnnotation(SupportedBy.class) != null){
//					SupportedBy ann = (SupportedBy) method.getAnnotation(SupportedBy.class);
//					for (BrowserType brType : ann.browsers()){
//						if (brType.equals(BrowserType.NONE) && !list.contains(entry)){
//							list.add(entry);
//							break;
//						}
//					}
//					if (ann.domLevel().equals(DomLevel.THREE) && !list.contains(entry))
//						list.add(entry);
//				}
//			}
//		}
//	}
	
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
	
	/**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    protected static Class<?>[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
//                assert !file.getName().contains(".");
//                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
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

}

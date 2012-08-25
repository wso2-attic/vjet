/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative.tests;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import junit.framework.TestCase;

import org.ebayopensource.dsf.jsnative.Window;
import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.SupportedBy;
import org.ebayopensource.dsf.jst.IJstAnnotation;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.ts.util.JstTypeSerializer;
import org.ebayopensource.dsf.util.JavaSourceLocator;
import org.ebayopensource.vjet.prebuild.JsNativeLibBuildTask;
import org.ebayopensource.vjo.lib.ResourceHelper;
import org.ebayopensource.vjo.meta.VjoKeywords;
import org.junit.BeforeClass;
import org.junit.Test;



//@ModuleInfo(value="JsNativeResource",subModuleId="JsNativeResource")
public class JsNativePreBuildTests  extends TestCase {
	
	private static final String YELL = "@@@@@@@@@@@@@@@@@@@@@@@@@@@";
	
	private static List<File> fileList = null;
	private static List<JstType> originalJstTypeList = null;
	private static List<IJstType> deserializedJstTypeList = new ArrayList<IJstType>();
	
	private static Map<String,List<String>> functionMap = null;
	private static Map<String,List<String>> propertyMap = null;
	private static Map<String,List<String>>	supportedByMap = null;
	private static Map<String,List<String>> constructorMap = null;
	private static String s_outputFolder;
	
	static{

		try {
			s_outputFolder = File.createTempFile("test", ".test").getParent();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void initAnnotationMaps() {
		
		functionMap = new HashMap<String,List<String>>();	
		propertyMap = new HashMap<String,List<String>>();
		supportedByMap = new HashMap<String,List<String>>();
		constructorMap = new HashMap<String,List<String>>();
		
		//for all files...
		for (File file : fileList) {
			String name = file.getAbsolutePath(); //"Window.java"
			name = name.substring(name.indexOf("org"), name.lastIndexOf('.'));
			name = name.replace(File.separatorChar, '.');
			
			//System.out.println("Get annotations for: " + name);
		
			//"org.ebayopensource.dsf.jsnative.Window"
//			String fullyQualifiedJavaName = JSNATIVE_PACKAGE + "."
//									+ name.substring(0,name.indexOf(".java"));
		
			Class klass = null;
			try {
				klass = Class.forName(name);
				System.out.println("Loaded: " + name);
			} catch (ClassNotFoundException cnfe) {
				System.err.println("Could not load: " + name);
				continue;
			}
			
			// skip annotation classes
			if (klass.getPackage().getName().equals(SupportedBy.class.getPackage().getName())) {
				System.out.println("Skipped " + klass.getName());
				continue;
			}
		
			//get functions and properties and supportedBy
			List<String> functions = Annotations.getFunctions(klass); 			
			System.out.println("fc: " + functions.size());
			List<String> properties = Annotations.getProperties(klass); 
			System.out.println("pc: " + properties.size());
			List<String> supportedBys = Annotations.getSupportedBy(klass);
			System.out.println("sc: " + supportedBys.size());
			List<String> constructors = Annotations.getConstructors(klass);
			System.out.println("con: " + constructors.size());
		
			//put them into map indexed by java class
			String key = getTypeName(klass);
			
			
			System.out.println("KEY:" + key);
//			assertFalse("Function key already exists - " + key, functionMap.containsKey(key));
//			assertFalse("Property key already exists - " + key, propertyMap.containsKey(key));
//			assertFalse("SupportedBy key already exists - " + key, supportedByMap.containsKey(key));
			functionMap.put(key, functions);
			propertyMap.put(key, properties);
			supportedByMap.put(key, supportedBys);
			constructorMap.put(key, constructors);
		} //for f
		
	} 
	
	private static String getTypeName(Class c) {
		Alias alias = (Alias) c.getAnnotation(Alias.class);
		if (alias == null) {
			return c.getSimpleName();
		}
		return alias.value();
	}

	//JsNativeCustomTranslator
	@BeforeClass
	public static void testJsNativeBuildTask() throws IOException  {
		JsNativeLibBuildTask task = new JsNativeLibBuildTask();
		Class anchor = Window.class;
		URL url = JavaSourceLocator.getInstance().getSourceUrl(anchor);
		if (url == null) {
			System.out.println(">>>>> java.source.path = " + System.getProperty("java.source.path"));
			System.out.println(">>>>> java.class.path = " + System.getProperty("java.class.path"));
			fail("Could not locate source URL for " + anchor.getName());
		}
		else {
			System.out.println(">>>>> url = " + url.toExternalForm());
		}
		String path = null;
		// Check if we found the Window.class in a source jar
		if ("jar".equalsIgnoreCase(url.getProtocol())) {
			// extrace the org.ebayopensource.dsf.jsnative.* files from the source jar
			// write to the temp directory
			path = extractSource(url);
		} else {
			path = url.getPath();
			path = path.substring(0, path.indexOf("/src/"));
		}
		task.setSourceDirs("src");
		task.setProjectDir(path);
		task.setEnableParallel(false);
		task.setEnableTrace(false);
		task.setJsNativePkgNames("org.ebayopensource.dsf.jsnative.global,org.ebayopensource.dsf.jsnative");
		task.setExcludePkgName("org.ebayopensource.dsf.jsnative.anno");
		
		
		task.setOutputDirectory(s_outputFolder);
		task.setEnableDebug(true);
		task.execute();

		fileList = task.getFileList();	//includes *.anno.* and *.events.*
		originalJstTypeList = task.getJstTypeList();
//		deserializedJstTypeList.addAll(JstTypeSerializer.deserialize(getJsNativeSerializedStreamFromSource()));
		deserializedJstTypeList.addAll(JstTypeSerializer.getInstance().deserialize(getJsNativeGlobalSerializedStreamFromSource()));
		deserializedJstTypeList.addAll(JstTypeSerializer.getInstance().deserialize(getJsBrowserObjectsSerializedStreamFromSource()));
		
		visitList(originalJstTypeList);
		
		initAnnotationMaps();
		
		System.out.println("F> " + functionMap.get("HtmlElementStyle"));
		System.out.println("P> " + propertyMap.get("HtmlElementStyle"));
	}

	private static void visitList(List<JstType> originalJstTypeList2) {
		boolean failed = false;
		for(JstType t:originalJstTypeList2){
			for(IJstMethod m : t.getMethods()){
				if(m.getRtnType().getName().equals("String") && m.getRtnType().getAlias().equals(m.getRtnType().getName())){
					failed=true;
					System.err.println("type failed has wrong String " + t.getName() + " method :"+ m.getName());
				}
			}
			
			for(IJstProperty p:t.getProperties()){
				if(p.getType().getName().equals("String") && p.getType().getAlias().equals(p.getType().getName())){
					failed = true;
					System.err.println("type failed has wrong String " + t.getName() + " property :"+ p.getName());
				}
			}
			
			
			
		}
		if(failed){
			fail("");
		}
		
	}

	static final int BUFFER = 2048;
	
	private static String extractSource(URL url) {
		String path = s_outputFolder + "/JsNative";
		String outDir = path+"/src";
		makeOutputDirectory(outDir);
		String jarPath = url.getPath();
		int index = jarPath.indexOf('!');
		if (index > 0 ) {
			jarPath = jarPath.substring(jarPath.indexOf('/')+1, index);
		}
		try {
			ZipFile jarFile = new ZipFile(new File(jarPath));
			Enumeration<? extends ZipEntry> enumeration = jarFile.entries();
			
			while (enumeration.hasMoreElements()) {
				ZipEntry elem = enumeration.nextElement();
				if (!elem.isDirectory() &&
						elem.getName().startsWith("org.ebayopensource.dsf/jsnative")) {
					BufferedInputStream is = 
						new BufferedInputStream(jarFile.getInputStream(elem));
					int count;
					byte[] data = new byte[BUFFER];
					File file = new File(outDir+"/"+elem.getName());
					file.getParentFile().mkdirs();
					BufferedOutputStream dest = 
						new BufferedOutputStream(new FileOutputStream(file));
					while ((count = is.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, count);
					}
					dest.flush();
					dest.close();
					is.close();
					
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();	//KEEPME
			return null;
		} 
		return path;
	}
	
	@Test
	public void testPackageIsNull() {
		
		for (JstType jstType : originalJstTypeList) {
			// verify JsNative JstType don't have package information
			if(jstType.getPackage()!=null && jstType.getPackage().getName().equals("js")){
				continue;
			}
			assertNull("Package is not null for " + jstType.getName(), jstType.getPackage());
		}
		
	}
	
	@Test
	public void testJstCategory() {
		
		for (JstType jstType : originalJstTypeList) {
			if (jstType.getConstructor() != null) {
				// verify JsNative JstType is a class category
				assertTrue(jstType.isClass());
			}
		}
		
	}
	

	@Test
	public void testDeserializedFile() {
		assertNotNull(originalJstTypeList);
		assertNotNull(deserializedJstTypeList);
		
		assertEquals(originalJstTypeList.size(), deserializedJstTypeList.size());
//		System.out.println("count:" + originalJstTypeList.size());
//		System.out.println("count2:" + jstTypeList.size());
		
		//note: new java 5 foreach loop doesn't allow for parallel looping
		for (int ii = 0; ii < originalJstTypeList.size(); ii++) {
			JstType jst0 = originalJstTypeList.get(ii);
			
			System.out.println("[" + ii + "]: " + jst0.getName()); 
			
			JstType jst1 = locateInDeserializedJstTypeList(jst0);
			assertNotNull(jst1);
			
			//verify name of type
			assertEquals(jst0.getName(), jst1.getName());
			assertEquals(jst0.getSimpleName(), jst1.getSimpleName());
			
			//verify extends
			List<IJstType> ijst0 = jst0.getExtends();
			List<IJstType> ijst1 = jst1.getExtends();
//			System.out.println("icount:" + ijst0.size());
			assertEquals(ijst0.size(), ijst1.size());
			
			//for all extends...(at most 1 for classes, more for interfaces)
			for (int jj = 0; jj < ijst0.size(); jj++) {
				IJstType e0 = ijst0.get(jj);
				IJstType e1 = ijst1.get(jj);
				
				assertEquals(e0.getName().toString(), e1.getName().toString());			
				assertEquals(e1.getSimpleName(), e1.getSimpleName());
			} //jj
				
			//for all instance properties...
			for (int pp = 0; pp < jst0.getAllPossibleProperties(false, true).size(); pp++) {
				IJstProperty p0 = jst0.getAllPossibleProperties(false, true).get(pp);
				IJstProperty p1 = jst1.getAllPossibleProperties(false, true).get(pp);

				verifyListOfAnnotations(p0.getAnnotations(), 
					    				p1.getAnnotations());
				
				//verify that the property's type and name must be the same
				assertEquals(p0.getType().getSimpleName(), p1.getType().getSimpleName());
				assertEquals(p0.getName().toString(), p1.getName().toString());
				
			} //pp
			
			//for all instance methods...
			for (int mm = 0; mm < jst0.getMethods(false, true).size(); mm++)	 {
				IJstMethod m0 = jst0.getMethods(false, true).get(mm);
				IJstMethod m1 = jst0.getMethods(false, true).get(mm);
				
				verifyListOfAnnotations(m0.getAnnotations(), 
					    				m1.getAnnotations());
				
				//verify return type
				if (m0.getRtnType() != null) {
					assertEquals(m0.getRtnType().getSimpleName(), m1.getRtnType().getSimpleName());
				}
				//verify method name
				assertEquals(m0.getName().toString(), m1.getName().toString());
				
				//verify args
				for (int aa = 0; aa < m0.getArgs().size(); aa++) {
					JstArg arg0 = m0.getArgs().get(aa);
					JstArg arg1 = m1.getArgs().get(aa);
					
					//verify arg's type and name
					assertEquals(arg0.getType().getSimpleName(), arg1.getType().getSimpleName());
					assertEquals(arg0.getName().toString(), arg1.getName().toString());
					
				}
			} //mm

		} //ii
		
	}
	
	private static void makeOutputDirectory(String path) {
		File f = new File(path);
		if (f.exists() && f.isDirectory()) {
			// directory exist. must delete it first
			delete(f);
		}
		f.mkdir();
	}
	
    private static void delete(File f) {
        if (f.isDirectory()) {
            File[] children = f.listFiles();
            for (int i = 0; i < children.length; i++) {
                delete(children[i]);
            }
        }
        f.delete();
    }
	
	private JstType locateInDeserializedJstTypeList(JstType jst0) {
		for (IJstType jstType : deserializedJstTypeList) {
			if (jst0.getName().equals(jstType.getName())) {
				return (JstType) jstType;
			}
		}
		return null;
	}

	//verify both lists of annotations
	private void verifyListOfAnnotations(List<IJstAnnotation>list0,
										 List<IJstAnnotation> list1) {
		assertEquals(list0.size(), list1.size());
	
		for (int ii = 0; ii < list0.size(); ii++) {
			IJstAnnotation anno0 = list0.get(ii);
			IJstAnnotation anno1 = list1.get(ii);
			
			assertEquals("Name-value pair must be equal", 
						 anno0.toString(), anno1.toString());
			
			assertEquals(anno0.getName().toString() 
						 + " should equals " + anno1.getName().toString(),
						 anno0.getName().toString(), anno1.getName().toString());
		}

	}

	private void verifyFunctionAnnotations(JstType jstType) {
		//TODO: remove when bug 760 is fixed
//		if (jstType.getName().contains("HtmlElementStyle")) {
//			System.out.print("HESf: ");
//			List<IJstMethod> mList = jstType.getMethods(false,true);
//			for (IJstMethod m: mList) {
//				System.out.print(" " + m.getName().toString());
//			}
//			System.out.println();
//			return;
//		}
		
		if(functionMap==null){
			return;
		}
		
		List<String> functions = functionMap.get(jstType.getName());	
		System.out.println("\tFunctions: " + functions);
		if (functions == null) {
			return;
		}
		// Get instance methods
		List<IJstMethod> mList = jstType.getMethods(false,true);
		// as well as static methods
		List<IJstMethod> smList = jstType.getMethods(true,true);
		if (smList != null) {
			mList.addAll(smList);
		}
		for (String func : functions) {
			
			boolean isFound = false;
			for (IJstMethod m : mList) {
				
				if (m.getName().getName().equals(func)) {
					isFound = true;
					break;
				} 
			} //for m
			if (isFound) {
				System.out.println("\t\t" + func + " is found");
			} else {
				System.out.println("\t\t" + func + " not found " + YELL);	
			}
			
			assertTrue("\t\t" + jstType.getName() +":"+func + " not found", isFound);
		} // for func
	}
	
	private void verifyConstructorAnnotations(JstType jstType) {
		
		List<String> constructors = constructorMap.get(jstType.getName());	
		System.out.println("\tConstructors: " + constructors);
		if (constructors == null) {
			return;
		}
		List<IJstMethod> answer = new ArrayList<IJstMethod>();
		getConstructors(jstType, answer);
		assertEquals("Number of constructors don't match for " + jstType.getName(),
				constructors.size(), answer.size());
		for (IJstMethod c : answer) {
			assertEquals("Constructor name not correct for " + jstType.getName(),
					VjoKeywords.CONSTRUCTS, c.getName().toString());
		}
	}
	
	private void getConstructors(IJstType jstType, 
				List<IJstMethod> answer) {
		 IJstMethod c = jstType.getConstructor();
		 if (c != null) {
			 if (c.isDispatcher()) {
				 for (IJstMethod m : c.getOverloaded()) {
					 answer.add(m);
				 }
			 } else {
				 answer.add(c);
			 }
			 
		 }
//		 if (jstType.getExtend() != null ) {
//			 getConstructors(jstType.getExtend(), answer);
//		 }
	}

	private void verifyPropertyAnnotations(JstType jstType) {
		List<String> properties = propertyMap.get(jstType.getName());	
		System.out.println("\tProperties: " + properties);
		if (properties == null) {
			return;
		}
		// Get instance properties
		List<IJstProperty> pList = jstType.getAllPossibleProperties(false,true);
		// as well as static properties
		List<IJstProperty> spList = jstType.getAllPossibleProperties(true,true);
		if (spList != null) {
			pList.addAll(spList);
		}
		for (String prop : properties) {
			
			boolean isFound = false;
			for (IJstProperty p : pList) {
				
				if (p.getName().getName().equals(prop)) {
					isFound = true;
					break;
				} 
			} //for m
			if (isFound) {
				System.out.println("\t\t" + prop + " is found");
			} else {
				System.out.println("\t\t" + prop + " not found " + YELL);	
			}
			
			assertTrue("\t\t" + prop + " not found", isFound);
		} // for prop	
	}
	
	//TODO: this needs to verify the values too	
	private void verifySupportedByAnnotations(JstType jstType) {
		List<String> supportedBys = supportedByMap.get(jstType.getName());	
		System.out.println("\tSupportedBys " + supportedBys);
		if (supportedBys == null) {
			return;
		}
		List<IJstMethod> sList = jstType.getMethods();
		List<IJstProperty> pList = jstType.getProperties();
		for (String func : supportedBys) {
			
			boolean isFound = false;
			for (IJstMethod m : sList) {
				if (m.getName().toString().equals(func)) {
					isFound = true;
					break;
				} 
			} //for m
			if (isFound) {
				System.out.println("\t\t" + func + " is found");
			} else {
				for (IJstProperty p : pList) {
					if (p.getName().toString().equals(func)) {
						isFound = true;
						break;
					}
				}
				if (isFound) {
					System.out.println("\t\t" + func + " is found");	
				} else {
					System.out.println("\t\t" + func + " not found " + YELL);
				}
			}
			
			assertTrue("\t\t" + func + " not found", isFound);
		} //for func		
	}
	
	private boolean isFromAnnoFolder(IJstType jstType) {
		return jstType.getName().startsWith("org.ebayopensource.dsf.jsnative.anno"); 

	}
	
	private boolean isFromEventsFolder(JstType jstType) {
		if (jstType.getName().startsWith("org.ebayopensource.dsf.jsnative.events")) {
			return true;
		}
		
		List<IJstType> importList = jstType.getImports();
		boolean isImportEvent = false;
		for (IJstType j : importList) {
			if (j.getName().startsWith("org.ebayopensource.dsf.jsnative.events")) {
				isImportEvent = true;
				break;	
			}
		}
		
		return isImportEvent;
	}

	
	@Test
	public void testAnnotations() {
		assertNotNull(deserializedJstTypeList);
		
		for (IJstType jstType : deserializedJstTypeList) {
			System.out.println("=============================================");
			System.out.println("JstName: " + jstType.getName());

			if (isFromAnnoFolder(jstType)) {
				System.out.println("\tSkipped anno: " + jstType.getName());
				continue;
			}
			
			//TODO: to be removed
			if (isFromEventsFolder((JstType) jstType)) {
				System.out.println("\tSkipped events: " + jstType.getName());
				continue;
			}
				
			verifyFunctionAnnotations((JstType) jstType);
			
			verifyConstructorAnnotations((JstType) jstType);
			
			verifyPropertyAnnotations((JstType) jstType);
			
			verifySupportedByAnnotations((JstType) jstType);
				
		}
		
	}
	
	private static InputStream getJsNativeGlobalSerializedStreamFromSource() {
		String jsNativeFile = "org_ebayopensource_dsf_jsnative_global.ser";
		return getJsNativeSerializedStream(s_outputFolder, jsNativeFile);
	}
	
	private static InputStream getJsBrowserObjectsSerializedStreamFromSource() {
		String jsNativeFile = "org_ebayopensource_dsf_jsnative.ser";
		return getJsNativeSerializedStream(s_outputFolder, jsNativeFile);
	}
	
	private static InputStream getJsNativeSerializedStream(String outputFolder, String jsNativeFile) {
		File file = null;
		file = new File(outputFolder +"/"+jsNativeFile);

		if (file == null) {
			throw new RuntimeException("Could not load JsNative resource - " + 
					file);
		}
		try {
			URL url = file.toURL();
			return url.openStream();
		} catch (IOException e) {
			throw new RuntimeException("Could not load JsNative resource - " + 
					file, e);
		}
	}
}

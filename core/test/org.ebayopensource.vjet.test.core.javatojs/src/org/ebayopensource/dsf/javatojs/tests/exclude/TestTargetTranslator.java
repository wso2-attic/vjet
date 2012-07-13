/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.exclude;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.javatojs.control.BuildController;
import org.ebayopensource.dsf.javatojs.control.ICodeGenPathResolver;
import org.ebayopensource.dsf.javatojs.tests.exclude.anno.clz.TestFieldCreation;
import org.ebayopensource.dsf.javatojs.tests.exclude.anno.clz.TestObjectCreation;
import org.ebayopensource.dsf.javatojs.tests.exclude.anno.clz.TestReturnType;
import org.ebayopensource.dsf.javatojs.tests.exclude.anno.clz.TestStaticAccess;
import org.ebayopensource.dsf.javatojs.tests.exclude.anno.clz.TestStaticInit;
import org.ebayopensource.dsf.javatojs.trace.TranslateError;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.util.JavaSourceLocator;
import org.ebayopensource.dsf.logger.LogLevel;

public class TestTargetTranslator {

	private static final File  CODE_GEN_DIR= new File("CODEGENDIR");
	static{
		CODE_GEN_DIR.mkdirs();
	}
	public static void translate(Class ancher) {
		ClassList sourceList = TestTargetTranslator.getSourceList();
		sourceList
		.addToList(ancher);
		translate(sourceList);		
	}
	
	public static void translate(ClassList a_sourceList, BuildController build ) {
		List<Class> sourceList = a_sourceList.list;
		if( build==null){
			build = new BuildController();		
		}
		
		build.setCodeGenPathResolver(new ICodeGenPathResolver() {
			@Override
			public URL getJavaFilePath(JstType type)
					throws MalformedURLException {
				
				URL url = ICodeGenPathResolver.DEFAULT
						.getJavaFilePath(type);
				System.out.println("Java : "+url);
				return  url;
			}

			@Override
			public URL getJsrFilePath(JstType type)
					throws MalformedURLException {
				final URL url = ICodeGenPathResolver.DEFAULT
						.getJsrFilePath(type);
				System.out.println("JSR  : "+url);
				if (url.getProtocol().equalsIgnoreCase("file")) {
					return url;
				} else {
					return new File(CODE_GEN_DIR, ((type.getName().replace(
							'.', '/')) + "Jsr.java")).toURI().toURL();
				}
			}

			@Override
			public URL getVjoFilePath(JstType type)
					throws MalformedURLException {
				final URL url = ICodeGenPathResolver.DEFAULT
						.getVjoFilePath(type);
				System.out.println("JS   : "+url);
				if (url.getProtocol().equalsIgnoreCase("file")) {
					return url;
				} else {
					return new File(CODE_GEN_DIR, ((type.getName().replace(
							'.', '/')) + ".js")).toURI().toURL();
				}
			}
		});
		
		List<URL> fileList = new ArrayList<URL>();
		
		for(Class classObj: sourceList){
			URL url = JavaSourceLocator.getInstance().getSourceUrl(classObj);
				fileList.add(url);
		}		
		build.buildFiles(fileList);
		//printErrors(build.getAllErrors());
	}
	
	
	public static void translate(ClassList a_sourceList ) {
		BuildController build = new BuildController();		
		translate(a_sourceList,null);		
	}
	
	public static void printErrors(List<TranslateError> errors) {
		System.err.println("Error size ="+ errors.size());
		for (TranslateError e : errors) {
			System.err.println("Error>>"+ e.toString());	
			if (e.getLevel() == LogLevel.ERROR) {
				//System.err.println(e.toString());				
			}
		}
		System.err.println("-------------------");	
	}
	
	public static ClassList getSourceList(){
		return new ClassList();
	}
	
	
	public static class ClassList {
		List<Class> list = new ArrayList<Class>();
		public ClassList addToList(Class ancher){
			list.add(ancher);
			return this;
		}
	}
	
	public static List<String> getGeneratedFile(ClassList a_sourceList){
		
		List<String> generatedFiles = new ArrayList<String>();
		for(Class classObj: a_sourceList.list){
			final File jsrFile = new File(
					CODE_GEN_DIR,
					(classObj.getPackage().getName().replace('.',
							File.separatorChar)
							+ File.separatorChar + classObj.getSimpleName() + "Jsr.java"));	
			final File jsFile = new File(
					CODE_GEN_DIR,
					(classObj.getPackage().getName().replace('.',
							File.separatorChar)
							+ File.separatorChar + classObj.getSimpleName() + "js"));	
			if(jsrFile.exists()||jsFile.exists()){
				generatedFiles.add(classObj.getName());
			}
		}
		return generatedFiles;
		
	}
	public static void deleteGenerated(ClassList a_sourceList){
		List<File> generatedFiles = new ArrayList<File>();
		for(Class classObj: a_sourceList.list){
			final File jsrFile = new File(
					CODE_GEN_DIR,
					(classObj.getPackage().getName().replace('.',
							File.separatorChar)
							+ File.separatorChar + classObj.getSimpleName() + "Jsr.java"));	

			if(jsrFile.exists()){
				generatedFiles.add(jsrFile);
			}
			final File jsFile = new File(
					CODE_GEN_DIR,
					(classObj.getPackage().getName().replace('.',
							File.separatorChar)
							+ File.separatorChar + classObj.getSimpleName() + "js"));	
			if(jsFile.exists()){
				generatedFiles.add(jsFile);
			}
		}
		if(generatedFiles.size()<=0){
			System.err.println("No files generated...");
		}else{
			System.err.println("Removing generated files....");
		}
		for( File file: generatedFiles){
			System.err.println(String.format(" Delete [%s] Status[%b]",file.getAbsolutePath(),file.delete()));		
		}
		
		
		
		
	}
	
	public static boolean hasErrorFor(List<TranslateError> errors, String firstStr, int lineNumber){
		
		for (TranslateError e : errors) {
			if (e.getLevel() == LogLevel.ERROR) {
				String errorMsg = e.toString();	
				if( errorMsg.indexOf(firstStr)>=0 && e.getSrcLineNo() ==lineNumber) {					
					return true;				
				}
			}
		}
		return false;		
	}
	
	
public static boolean hasErrorFor(List<TranslateError> errors, String firstStr){
		
		for (TranslateError e : errors) {
			if (e.getLevel() == LogLevel.ERROR) {
				String errorMsg = e.toString();	
				if( errorMsg.indexOf(firstStr)>=0) {					
					return true;				
				}
			}
		}
		return false;		
	}
	
	public static void main(String[] args) {
		ClassList sourceList = TestTargetTranslator.getSourceList();
		sourceList
		.addToList(TestObjectCreation.class)
		.addToList(TestStaticAccess.class)
		.addToList(TestFieldCreation.class)
		.addToList(TestStaticInit.class)
		//TODO: not working
		.addToList(TestReturnType.class)
		;
		deleteGenerated(sourceList);
	}
}


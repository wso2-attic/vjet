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
import java.util.List;

import org.ebayopensource.dsf.javatojs.FileUtils;
import org.ebayopensource.dsf.javatojs.anno.AJavaOnly;
import org.ebayopensource.dsf.javatojs.control.BaseTranslationInitializer;
import org.ebayopensource.dsf.javatojs.control.BuildController;
import org.ebayopensource.dsf.javatojs.control.ITranslationInitializer;
import org.ebayopensource.dsf.javatojs.trace.TranslateError;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.logger.LogLevel;
@AJavaOnly
public class TestTranslator {
	public TranslateCtx s_ctx;
	List<TranslateError> errors;
	
	private static ITranslationInitializer s_initializer;
	
	public static ITranslationInitializer getInitializer() {
	
		if (s_initializer == null) {
			s_initializer = new BaseTranslationInitializer(){
				public void initialize(){
					TranslateCtx ctx = TranslateCtx.createCtx();
					ctx.getConfig().getPolicy()
						.addExcludeClass(this.getClass());
					ctx.enableParallel(false);
					ctx.enableTrace(true);
				}
			};
			
		}
		return s_initializer;
	}

	public TestTranslator translate(Class anchor, boolean onDemand){
		String path = FileUtils.getPath(anchor, null, "src");
		BuildController controller = new BuildController(getInitializer());
		controller.setIncludeChildPkgs(true);
		controller.setUseOnDemand(onDemand);
		 try {
			controller.buildPackage(new File(path).toURI().toURL());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		 
		errors = controller.getAllErrors();
		return this;
	}
	public TestTranslator translate(Class anchor){
		return translate(anchor,false);
				
	}
	
	public boolean hasErrorFor(String firstStr, Class p_class, boolean display){
		
		for (TranslateError e : errors) {
			if (e.getLevel() == LogLevel.ERROR) {
				String errorMsg = e.toString();
				String name = p_class.getName();
				int index1 = errorMsg.indexOf(p_class.getName());
				int index2 = errorMsg.indexOf(firstStr);
				if( errorMsg.indexOf(p_class.getName()) >=0 
						&& errorMsg.indexOf(firstStr)>=0) {
					if(display){
						System.err.println(e.toString());
					}
					return true;				
				}
			}
		}
		return false;
		
	}
	
public boolean hasErrorFor(String firstStr, boolean display){
		
		for (TranslateError e : errors) {
			if (e.getLevel() == LogLevel.ERROR) {
				String errorMsg = e.toString();	
				if( errorMsg.indexOf(firstStr)>=0) {
					if(display){
						System.err.println(e.toString());
					}
					return true;				
				}
			}
		}
		return false;
		
	}
	
	public boolean hasErrorFor(Class p_class, boolean display){
		for (TranslateError e : errors) {
			if (e.getLevel() == LogLevel.ERROR) {
				String errorMsg = e.getMsg();
				if( errorMsg.indexOf(p_class.getName().replace("$",".")) >=0) {
					if(display){
						System.err.println(e.toString());
					}
					return true;				
				}
			}
		}
		return false;
		
	}
	
	public void printErrors() {
		//String packageToCheck = clz.getPackage().getName();
		for (TranslateError e : errors) {
			if (e.getLevel() == LogLevel.ERROR) {
				System.err.println(e.toString());				
			}
		}
		System.err.println("-------------------");	
	}
	
	public static void printErrors(List<TranslateError> m_errors) {
		//String packageToCheck = clz.getPackage().getName();
		for (TranslateError e : m_errors) {
			if (e.getLevel() == LogLevel.ERROR) {
				System.err.println(e.toString());				
			}
		}
		System.err.println("-------------------");	
	}
}

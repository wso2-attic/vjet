/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.parser.bootstrap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.generate.JsrGenerator;
import org.ebayopensource.dsf.jsgen.shared.generate.JsrTypeProvider;
import org.ebayopensource.dsf.jst.IJstLib;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstName;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.vjo.lib.LibManager;

public class GenJsBrowserJsr {

	private static final String JSNATIVE_JSR = "org.ebayopensource.vjet.jsbrowser.jsr";


	
	public static void main(String[] args) throws FileNotFoundException {
		
		String outputDir = args[0];
		IJstLib globals = LibManager.getInstance().getJsNativeGlobalLib();
		IJstLib lib = LibManager.getInstance().getBrowserTypesLib();
		List<JstType> types = lib.getAllTypes(true);
		
		JstCache.getInstance().addLib(globals);
		JstCache.getInstance().addLib(lib);
		
		for(IJstType type: types){
		
			
			try{
			JstPackage package1 = type.getPackage();
			if(package1==null){
				package1 = new JstPackage(JSNATIVE_JSR);
			}
////			
//			type.setPackage(package1);
			
//			File jsFile = createFile(outputDir, null, type.getSimpleName());
//			PrintWriter pw = new PrintWriter(jsFile);
//			VjoGenerator gen = new VjoGenerator(new GeneratorCtx(CodeStyle.PRETTY));
//			gen.writeVjo(type);
//			String generatedText = gen.getGeneratedText();
//			pw.append(generatedText);
//			pw.flush();
//			
//			IScriptUnit isu = new VjoParser().parse("Js", jsFile.getAbsolutePath(), generatedText, new TranslateCtx());
//			
			
//			IJstType type2 = isu.getType();
			IJstType type2 = type;
			 JstType type3 = null;
			if(type2 instanceof JstType) {
				type3 = (JstType) type2;
				type3.setImpliedImport(true);
				type3.clearMixins();
				
				clearSatisfies(type3);
				
				type3 = Utils.removeGuts(type3);
				
			}
			
			
			
			File jsrFile = createJsrFile(outputDir, package1, type.getSimpleName());
		
			System.out.println("writing:" + jsrFile);
			PrintWriter pw2 = new PrintWriter(jsrFile);
			pw2.append("package " + package1.getName()+ ";");
			JsrGenerator gen2 = Utils.createGenerator(pw2);
			JsrTypeProvider jsrTypeProvider = new JsrTypeProvider();
			jsrTypeProvider.setEnableTypeMapping(false);
			gen2.setJsToJavaMapper(jsrTypeProvider);
			gen2.writeJsr(type3);
			pw2.flush();
			
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}




	private static void clearSatisfies(JstType type3) {
		if(type3.isMixin()){
			type3.clearSatisfies();
		}
	
	}




	private static void addUnderBar(final IJstProperty property) {
		if(property instanceof JstProperty){
			JstProperty p = (JstProperty)property;
			p.setName(new JstName("_" + property.getName().getName()));
		}
		
	}
	private static void addUnderBar(final IJstMethod mtd) {
		if(mtd instanceof JstMethod){
			JstMethod m = (JstMethod)mtd;
			m.setName(new JstName("_" + mtd.getName().getName()));
		}
		
	}

	private static boolean isBrowserType(IJstType type) {
		if(type.getPackage()!=null && type.getPackage().getGroupName().contains("JsNative")){
			return true;
		}
		return false;
			
	}

	private static File createJsrFile(String outputDir, JstPackage package1,
			String simpleName) {
		String packageURI = "";
		if(package1!=null){
			packageURI = package1.getName().replace('.', File.separatorChar);
		}
		File dir = new File(outputDir + File.separatorChar + packageURI + File.separatorChar);
		if(!dir.exists()){
			dir.mkdirs();
		}
		File file = new File(dir,  simpleName +"Jsr.java");
		
		return file;
	}
	
	private static File createFile(String outputDir, JstPackage package1,
			String simpleName) {
		String packageURI = "";
		if(package1!=null){
			packageURI = package1.getName().replace('.', File.separatorChar);
		}
		File dir = new File(outputDir + File.separatorChar + packageURI + File.separatorChar);
		if(!dir.exists()){
			dir.mkdirs();
		}
		File file = new File(dir,  simpleName + ".js");
		
		return file;
	}
	
}

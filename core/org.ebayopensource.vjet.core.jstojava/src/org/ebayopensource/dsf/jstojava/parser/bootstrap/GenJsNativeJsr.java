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
import org.ebayopensource.dsf.jst.IJstLib;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.vjo.lib.LibManager;

public class GenJsNativeJsr {

	private static final String COM_EBAY_JSNATIVE_JSR = "com.ebay.jsnative.jsr";

	/** TODO
	 * 
	 * ..1. Arguments is not resolved to ArgumentsJsr..
	 * ..2. Array can not be resolved to type .. ArrayJsr now
	 * ..3. Double methods length in RegExp... mixin issue
	 * ..4. Number should not be converted to NumberJsr
	 * ..5. Array was abstract now is not abstract during jsr gen only may be problem with Jst however
	STOPPED HERE
	 * ..6. TODO fix the Jsr imports for native types removing them only solves package issue
	 * ..7. TODO create jsr for browser types and vjo lib 
	 * ..8. Hook up to prebuild task. Seperate projects for each one Vjo,JsNative, Browser
	 * ..9. Add these projects to Dsf library for now.
	 * ..10. Fix tests ... run more tests
	 * ..11. run build with new jsr generator
	 */
	
	
	
	public static void main(String[] args) throws FileNotFoundException {
		
		String outputDir = args[0];
		IJstLib lib = LibManager.getInstance().getJsNativeGlobalLib();
		List<JstType> types = lib.getAllTypes(true);
		
		JstCache.getInstance().addLib(lib);
		
		for(IJstType type: types){
			
		
			
			try{
			JstPackage package1 = type.getPackage();
			if(package1==null){
				package1 = new JstPackage(COM_EBAY_JSNATIVE_JSR);
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
				
				
				if(type3.getName().contains("Function")){
					type3.removeExtend(type3.getExtend());
					type3.removeMethod("call", true);
					type3.removeMethod("apply", true);
					type3.removeProperty("arguments", true);
					type3.removeProperty("prototype", true);
					type3.removeProperty("caller", true);
					type3.removeProperty("length", true);
					type3.removeProperty("name", true);
					
				}
			}
			
			
			type3 = Utils.removeGuts(type3);
			
			File jsrFile = createJsrFile(outputDir, package1, type.getSimpleName());
			PrintWriter pw2 = new PrintWriter(jsrFile);
			pw2.append("package " + package1.getName()+ ";");
			
			
			JsrGenerator gen2 = Utils.createGenerator(pw2);
			gen2.writeJsr(type3);
			pw2.flush();
			
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
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

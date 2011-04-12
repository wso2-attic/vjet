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

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.jsgen.shared.generate.JsrGenerator;
import org.ebayopensource.dsf.jst.IJstLib;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.vjo.lib.LibManager;

public class GenVjoBootstrapJsr {

//	private static final String COM_EBAY_JSNATIVE_JSR = "org.ebayopensource.vjobootstrap.jsr";
	private static final String[] TYPES = { "vjo.ctype","vjo.mtype", "vjo.etype","vjo.otype", "vjo.itype", "vjo.ltype", "vjo.ftype" };

	
	
	public static void main(String[] args) throws FileNotFoundException {
		
		String outputDir = args[0];
		IJstLib jsnative = LibManager.getInstance().getJsNativeGlobalLib();
		IJstLib jsbrowser = LibManager.getInstance().getBrowserTypesLib();
		IJstLib lib = LibManager.getInstance().getVjoSelfDescLib();
		List<JstType> types = lib.getAllTypes(true);
		
		JstCache.getInstance().addLib(jsnative);
		JstCache.getInstance().addLib(jsbrowser);
		JstCache.getInstance().addLib(lib);
		
		for(IJstType type: types){
			
			if(isExcluded(type)){
				continue;
			}

			
			
			try{
			JstPackage package1 = type.getPackage();
			
			
			
			package1 = new JstPackage("vjo");
			
			JstType wc = null;
			if (type instanceof JstType) {
				wc = (JstType)type;
				fixType(wc);
				Utils.removeGuts(wc);
				
				File jsrFile = createJsrFile(outputDir, package1, type.getSimpleName());
				System.out.println("gen jsr :" + jsrFile.getAbsolutePath());
				PrintWriter pw2 = new PrintWriter(jsrFile);
//			pw2.append("package " + package1.getName()+ ";");
				JsrGenerator gen2 = Utils.createGenerator(pw2);
				gen2.writeJsr(wc);
				pw2.flush();
//				wc.setPackage(null);
			}else{
				throw new DsfRuntimeException("type was not a JstType as expected");
			}
			
	
			
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}

	private static void fixType(JstType type) {
		if("vjo".equals(type.getName())){
			type.setPackage(new JstPackage("vjo"));
		}
		
	}

	private static boolean isExcluded(IJstType type) {
		if("vjo".equals(type.getName())){
			return true;
		}
		
		for(String t: TYPES){
			if(type.getName().contains(t)){
				return true;
			}	
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


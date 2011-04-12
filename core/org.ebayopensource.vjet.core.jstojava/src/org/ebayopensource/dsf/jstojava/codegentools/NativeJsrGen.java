/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.codegentools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.generate.CodeStyle;
import org.ebayopensource.dsf.jsgen.shared.generate.DefaultJsrFilters;
import org.ebayopensource.dsf.jsgen.shared.generate.JsrGenerator;
import org.ebayopensource.dsf.jst.IJstLib;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.vjo.lib.LibManager;

public class NativeJsrGen {

	public static void main(String[] args) throws IOException {

		IJstLib lib = LibManager.getInstance().getBrowserTypesLib();
		List<JstType> types = lib.getAllTypes(true);

		String root = "D:\\cc\\m_v4_soa\\v4darwin\\DSFAggregator\\src\\com\\ebay\\dsf\\jsnative\\jsr\\";

		File rootFilePath = new File(root);
		rootFilePath.mkdirs();
		
		for (JstType t : types) {
//			if(t.getName().contains("Exception")){
//				continue;
//			}
			String substring = t.getAlias().substring(t.getAlias().lastIndexOf(".")+1, t.getAlias().length());
			t.setPackage(new JstPackage("org.ebayopensource.dsf.jsnative.jsr"));
//			t.setSimpleName(substring);
			
		}
		
		for(JstType t: types){
//			if(t.getName().contains("Exception")){
//				continue;
//			}
		
			String substring = t.getSimpleName();
			File file = new File(root + substring + "Jsr" + ".java");
			if(!file.exists()){
				file.createNewFile();
			}
			FileWriter jsrWriter = new FileWriter(file);
			System.out.println("writing out " + file.getPath());

			PrintWriter out = new PrintWriter(jsrWriter);
			try {
				JsrGenerator gen = new JsrGenerator(out, CodeStyle.PRETTY, new DefaultJsrFilters());
				
				gen.writeJsr(t);
			} catch (RuntimeException e) {
				e.printStackTrace();
				continue;
			} finally {
				jsrWriter.close();
			}

		}

	}

}

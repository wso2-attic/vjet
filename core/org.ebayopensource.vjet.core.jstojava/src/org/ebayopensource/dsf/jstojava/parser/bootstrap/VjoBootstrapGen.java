/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.parser.bootstrap;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ts.util.JstTypeSerializer;
import org.ebayopensource.vjo.VjBootStrapDef;
import org.ebayopensource.vjo.VjBootstrapJsr;

public class VjoBootstrapGen {

	/**
	 * Note: Need to add JsNativeResource library to the classpath(in Run dialog) 
	 * when running this VjoBootstrapGen.main. It fails without this dependency.
	 */
	public static void main(String[] args) {

		if(args.length==0){
			//System.out.println("must enter location of ser file");
			throw new RuntimeException("Must provide location of ser file to generate");
		}
		
		String filePath = args[0];
		System.out.println("Target file: " + filePath);
		URL f = VjBootstrapJsr.getJsAsUrl();
		URL bapi = VjBootstrapJsr.getVjoApIAsUrl();
	
		try {
			Collection<? extends IJstType> types = BootstrapParser.createJstType("vjo", f,
					VjBootStrapDef.SCHEMA, bapi, 
					VjBootstrapJsr.getVjoConsoleAsUrl(), 
					VjBootstrapJsr.getVjoClassAsUrl(), 
					VjBootstrapJsr.getVjoEnumAsUrl(), 
					VjBootstrapJsr.getVjoOptionsOLUrl(), 
					VjBootstrapJsr.getVjoObjectAsUrl()).values();
			IJstType[] typeAry = new IJstType[types.size()];
			types.toArray(typeAry);
			
			List<IJstType> typeList = new ArrayList<IJstType>(types);
			
			serialize(typeList, filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void serialize(List<IJstType> types, String filePath) {
		// TODO Auto-generated method stub
//		String filePath = "D:/cc/jearly_v4_d9/v4darwin/DSFVjoDef/src/org.ebayopensource.vjo/lib/VjoApi.ser";
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePath);
			JstTypeSerializer.getInstance().serialize(types, fos);
			System.out.println("JsLibPreBuildTask: wrote " + filePath);
		} catch (Exception e) {
			e.printStackTrace(); // KEEPME
			System.out.println("Error serializing JstType - " + e.getMessage());
		}
	}

}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.parser.comments;

import java.io.File;
import java.lang.reflect.Method;

/**
 * This utility class is for generating a parser based on JsCommentMeta.jj
 * by using javacc.
 */
public class ParserGenerator {
	
	/**
	 * To run this main, please add javacc.jar (version 5 or above) into the
	 * classpath of eclipse launcher for this java application (from Run Configurations).
	 * 
	 * Sample location: \libs\javacc.jar in current project
	 * 
	 * Please also make the following three java files writable:
	 * VjComment, VjCommentConstants, VjCommentTokenManager.
	 */
	public static void main(String[] args) {
		String grammarFilePath = getPath();
		String grammarFileName = grammarFilePath + "VjComment.jj";
		try {
			Class<?> javaccMain = Class.forName("org.javacc.parser.Main");
			Method mainProgram = javaccMain.getMethod("mainProgram", String[].class);
			int retCode = (Integer) mainProgram.invoke(null,
				new Object[] {new String[]{"-OUTPUT_DIRECTORY=" + grammarFilePath, grammarFileName}});
			System.out.println("javacc return code: " + retCode) ;	//KEEPME
		}
		catch(Exception e) {
			throw new RuntimeException(e) ;
		}
	}
	
	private static String getPath() {
		File root = new File(".");
		char c = File.separatorChar;
		String clzName = ParserGenerator.class.getName() ;
		try {
			String path = root.getCanonicalPath() + c + "src" + c 
				+ clzName.substring(0, clzName.lastIndexOf('.')).replace('.', c) + c;
			return path ;
		}
		catch(Exception e) {
			throw new RuntimeException(e) ;
		}
	}
}

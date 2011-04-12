/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.util;

import java.util.HashMap;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

public class AstParserHelper {
	
//	private final static String OPTION_SOURCE_LEVEL = 
//        "org.eclipse.jdt.core.compiler.source";
//    private final static String OPTION_COMPILER_COMPLIANCE = 
//        "org.eclipse.jdt.core.compiler.compliance";
//    private final static String JAVA_LEVEL = "1.5";
    
    private final static HashMap<String,String> COMPILER_OPTION = new HashMap<String,String>();
    static {
    	// Specify which source level compatibility is used; default is 1.3
    	COMPILER_OPTION.put(CompilerOptions.OPTION_Source, CompilerOptions.VERSION_1_5);
        // Select the compliance level for the compiler; default is 1.4
    	COMPILER_OPTION.put(CompilerOptions.OPTION_Source, CompilerOptions.VERSION_1_5);
    }
    
    /**
     * create AND CONFIGURE an AST Parser using Java5 compliance levels...
     */
	public static ASTParser newParser() {
		return newParser(false);
	}
    
    /**
     * create AND CONFIGURE an AST Parser using Java5 compliance levels...
     */
	public static ASTParser newParser(boolean parseComments) {
	 	// Enable/disable JavaDoc parsing
    	COMPILER_OPTION.put(CompilerOptions.OPTION_DocCommentSupport, 
    		parseComments ? CompilerOptions.ENABLED : CompilerOptions.DISABLED);
		// Create the AST: AST.JLS3 handles JDK 1.0, 1.1, 1.2, 1.3, 1.4, 1.5
        ASTParser astParser = ASTParser.newParser(AST.JLS3);
        astParser.setCompilerOptions(COMPILER_OPTION);
        astParser.setKind(ASTParser.K_COMPILATION_UNIT);
        return astParser;
	}
}

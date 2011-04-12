/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: GenerateJsKeyWordHelper.java, May 19, 2009, 10:49:19 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.syntax.unique;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Generate js key word test file
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class GenerateJSKeyWordHelper {

    private static File jsKeyWordFile1;
    
    private static File jsKeyWordFile2;

    /**
     * The value is used for character storage.
     */
    public static final String [] jsKeyWord = { 
    "break", "delete", "function", "return", "typeof",  
    "case", "do", "if", "switch", "var",  
    "catch", "else", "in", "this", "void",  
    "continue", "false", "instanceof", "throw", "while",  
    "debugger", "finally", "new", "true", "with",  
    "default", "for", "null", "try",    
    "abstract", "double", "goto", "native", "static",  
    "boolean", "enum", "implements", "package", "super",  
    "byte", "export", "import", "private", "synchronized",  
    "char", "extends", "int", "protected", "throws",  
    "class", "final", "interface", "public", "transient",  
    "const", "float", "long", "short", "volatile",
    "arguments", "encodeURI","Infinity","Object","String",
    "Array", "Error","isFinite","parseFloat","SyntaxError", 
    "Boolean","escape","isNaN","parseInt","TypeError",
    "Date","eval","Math","RangeError","undefined",     
    "decodeURI","EvalError","NaN","ReferenceError", "unescape",                   
    "decodeURICompo", "Function", "Number", "RegExp", "URIError" 
    };
    
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        initTestFile();
        if(jsKeyWordFile1 == null || jsKeyWordFile2 == null)
            return;
        
        StringBuffer sb1 = fillVar1();
        StringBuffer sb2 = fillVar2();
        try {
            FileWriter fw = new FileWriter(jsKeyWordFile1);
            fw.write(sb1.toString());
            fw.flush();
            fw.close();
            
            fw = new FileWriter(jsKeyWordFile2);
            fw.write(sb2.toString());
            fw.flush();
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }


    private static StringBuffer fillVar1() {
        StringBuffer sb = new StringBuffer("vjo.ctype('syntax.unique.jsKeyWord1')\n");
        sb.append(".protos({\n");
        for (int i = 0; i < jsKeyWord.length; i++) {
            if(i == jsKeyWord.length -1){
                sb.append("     "+jsKeyWord[i] + " : \"String\" //< public String \n" );
            }else{
                sb.append("     "+jsKeyWord[i] + " : \"String\", //< public String \n" );
            }
        }
        sb.append("})\n");
        sb.append(".endType();");
        return sb;
    }
    
    private static StringBuffer fillVar2() {
        StringBuffer sb = new StringBuffer("vjo.ctype('syntax.unique.jsKeyWord2')\n");
        sb.append(".protos({\n");
        sb.append("//>public void getName() \n");
        sb.append("getName : function(){ \n");
        
        for (int i = 0; i < jsKeyWord.length; i++) {
            sb.append("     var "+jsKeyWord[i] + " = \"String\"; //< public String \n" );
        }
        sb.append("}\n");
        sb.append("})\n");
        sb.append(".endType();");
        return sb;
    }


    /**
     * Prepare test file
     */
    private static void initTestFile() {
        StringBuffer sb = new StringBuffer(new File("testFiles").getAbsolutePath());
        sb.append(File.separatorChar);
        sb.append("syntax");
        sb.append(File.separatorChar);
        sb.append("unique");
        sb.append(File.separatorChar);
        System.out.println(sb);
        jsKeyWordFile1 = new File(sb.append("jsKeyWord1.js").toString());
        jsKeyWordFile2 = new File(sb.toString().replace("jsKeyWord1.js", "jsKeyWord2.js"));
    }

}

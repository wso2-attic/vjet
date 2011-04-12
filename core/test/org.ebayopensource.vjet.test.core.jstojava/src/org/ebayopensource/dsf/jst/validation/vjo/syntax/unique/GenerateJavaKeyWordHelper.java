/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: GenerateJavaKeyWordHelper.java, May 19, 2009, 10:49:19 PM, liama. Exp$
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
 * Generate java key word test file
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class GenerateJavaKeyWordHelper {

    private static File javaKeyWordFile1;
    
    private static File javaKeyWordFile2;

    /**
     * The value is used for character storage.
     */
    public static final String [] javaKeyWord = {  "abstract",    "default",   "if",
        "private",     "this",
        "boolean",     "do",         "implements",    "protected",    "throw",
        "break",       "double",     "import",        "public",       "throws",
        "byte",        "else",       "instanceof",    "return",       "transient",
        "case",        "extends",    "int",           "short",        "try",
        "catch",       "final",      "interface",     "static",       "void",
        "char",        "finally",    "long",          "strictfp",     "volatile",
        "class",       "float",      "native",        "super",        "while",
        "const",       "for",        "new",           "switch",
        "continue",   "goto",       "package",       "synchronized"};
    
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        initTestFile();
        if(javaKeyWordFile1 == null || javaKeyWordFile2 == null)
            return;
        
        StringBuffer sb1 = fillVar1();
        StringBuffer sb2 = fillVar2();
        try {
            FileWriter fw = new FileWriter(javaKeyWordFile1);
            fw.write(sb1.toString());
            fw.flush();
            fw.close();
            
            fw = new FileWriter(javaKeyWordFile2);
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
        StringBuffer sb = new StringBuffer("vjo.ctype('syntax.unique.javaKeyWord1')\n");
        sb.append(".protos({\n");
        for (int i = 0; i < javaKeyWord.length; i++) {
            if(i == javaKeyWord.length -1){
                sb.append(javaKeyWord[i] + " : \"String\" //< public String \n" );
            }else{
                sb.append(javaKeyWord[i] + " : \"String\", //< public String \n" );
            }
        }
        sb.append("})\n");
        sb.append(".endType();");
        return sb;
    }
    
    private static StringBuffer fillVar2() {
        StringBuffer sb = new StringBuffer("vjo.ctype('syntax.unique.javaKeyWord2')\n");
        sb.append(".protos({\n");
        sb.append("//>public void getName() \n");
        sb.append("getName : function(){ \n");
        
        for (int i = 0; i < javaKeyWord.length; i++) {
            sb.append("var "+javaKeyWord[i] + " = \"String\"; //< public String \n" );
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
        javaKeyWordFile1 = new File(sb.append("javaKeyWord1.js").toString());
        javaKeyWordFile2 = new File(sb.toString().replace("javaKeyWord1.js", "javaKeyWord2.js"));
    }

}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

public class JSDebug {

    public static void print(String s) {
        if (ActiveJsExecutionControlCtx.ctx().needVerbose()){
        	System.err.print(s); //KEEPME
        }          
    }
    
    public final static void println(String s) {
        if (ActiveJsExecutionControlCtx.ctx().needVerbose()){
        	System.err.print(s); //KEEPME
        }      
    }
    
    public static void printJavaScriptException(Exception ex, String js) {
        if (ActiveJsExecutionControlCtx.ctx().needPrintJavaScriptException()) {
        	 System.err.print("****** Following javascript source causes exception ******"); //KEEPME
             System.err.print(js); //KEEPME
             ex.printStackTrace(); //KEEPME
        }
    }
}

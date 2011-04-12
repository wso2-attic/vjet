/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.meta;


public class VjoConvention {
	
	private static final String VJO = "vjo";
	private static final String VJOX = "vjo";
	private static final String CLASS = "clazz";
	
	private static final String THIS = "this";
	private static final String STATIC_THIS = "this.vj$";	
	private static final String BASE = THIS + "." + VjoKeywords.BASE;	
	private static final String INNER_STATIC = "";
	private static final String OUTER_STATIC = ".vj$";
	private static final String OUTER_INSTANCE = ".vj$.outer";
	private static final String PARENT_INSTANCE = ".vj$.parent";
	private static final String SH_NAMESPACE = "this.vj$";	
	private static final String TEMP_VAR = "_$Temp";
	private static final String SURFIX_RESERVED_WORDS = "_";
	private static final String VJO_CLASS = "vjo.Class";
	private static final String VJO_OBJECT = "vjo.Object";
	private static final String NEEDS_IMPL = "vjo.NEEDS_IMPL";
	
	public static String getVjoNS(){
		return VJO;
	}
	
	public static String getThisPrefix() {
		return THIS;
	}
	
	public String getStaticThisPrefix() {
		return STATIC_THIS;
	}
	
	public static String getNameWithStaticThis(String name) {
		return STATIC_THIS + "." + name;
	}
	
	public String getBasePrefix() {
		return BASE;
	}
	
	public String getInnerStaticPrefix(){
		return INNER_STATIC;
	}

	public String getOuterStaticPrefix(){
		return OUTER_STATIC;
	}
	
	public String getOuterInstancePrefix(){
		return OUTER_INSTANCE;
	}
	
	public String getParentInstancePrefix(){
		return PARENT_INSTANCE;
	}
	
	public String getShortHandNS() {
		return SH_NAMESPACE;
	}
	
	public String getTempVar() {
		return TEMP_VAR;
	}
	
	public String getSurfixReservedWord(){
		return SURFIX_RESERVED_WORDS;
	}
	
	public static String getAnonymousType(String name, String args) {
		return VjoKeywords.VJO + "." + VjoKeywords.MAKE + "(" + THIS + "," + 
				 name + ((args == null || args.equals("")) ? "" : ","+args) + ")";
	}
	
	public static String getType(String type){
		return VjoKeywords.VJO_GET_TYPE + "('" + type + "')";
	}
	
	public String getClassKeyword(){
		return CLASS;
	}

	public static String getVjoClass() {
		return VJO_CLASS;
	}

	public static String getVjoObject() {
		return VJO_OBJECT;
	}
	
	public static String getVjoExtScope() {
		return VJOX;
	}
	
	public static String getVjoNeedsImpl() {
		return NEEDS_IMPL;
	}
}

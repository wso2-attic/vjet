/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.astjst;

public final class TypeConstants {
	
	public enum Types {
		CTYPE,ITYPE,ETYPE,ATYPE,OTYPE,NONE
	}
	
	public enum TypeSections {
		NEEDS, INHERITS, SATISFIES, EXPECTS, DEFS, 
		MIXIN, PROPS, PROTOS, VALUES, INITS
	}
	
	public enum StatementSections {
		PROPS_PROPERTY, PROPS_METHOD, PROTOS_PROPERTY, 
		PROTOS_METHOD, INITS, DEFS, VALUES, NONE
	}
	
	public enum TypeRef {
		NEEDS, INHERITS, SATISFIES, EXPECTS, MIXIN
	}
	
	//Types
	public static final String ctypeStart = "vjo.ctype('";
	public static final String ctypeEnd = "') //< public";
	public static final String etypeStart = "vjo.etype('";
	public static final String etypeEnd = "') //< public";
	public static final String itypeStart = "vjo.itype('";
	public static final String itypeEnd = "') //< public";
	public static final String atypeStart = "vjo.ctype('";
	public static final String atypeEnd = "') //< public abstract";
	public static final String otypeStart = "vjo.otype('";
	public static final String otypeEnd = "') //< public";
	public static final String typeEnd = "') //< public";
	public static final String typeName = "CodeGenVJETSample";
	
	//Sections
	public static final String needsStart = ".needs('";
	public static final String needsEnd = "')";
	
	public static final String inheritsStart = ".inherits('";
	public static final String inheritsEnd = "')";
	
	public static final String satisfiesStart = ".satisfies('";
	public static final String satisfiesEnd = "')";
	
	public static final String expectsStart = ".expects('";
	public static final String expectsEnd = "')";
	
	public static final String defsStart = ".defs({\n\t'";
	public static final String defsEnd = "})";
	
	public static final String mixinStart = ".mixin('";
	public static final String mixinEnd = "')";
	
	public static final String propsStart = ".props({\n\t";
	public static final String propsEnd = "})";
	
	public static final String protosStart = ".protos({\n\t";
	public static final String protosEnd = "})";
	
	public static final String valuesStart = ".values('";
	public static final String valuesEnd = "')";
	
	public static final String initsStart = ".inits(function(){\n\t";
	public static final String initsEnd = "})";
	
	public static final String endType = ".endType();";
	
	//Dummy statements
	public static final String method1Start = "//>public void method1()\n\t method1 : function() { \n\t\t ";
	public static final String method1End = "\t}\n";
	
	public static final String method2Start = "//>public void method2()\n method2 : function() { \n ";
	public static final String method2End = "\t}\n";
	
	public static final String method3WithArgsStart = "//>public void method3(int intarg, String strarg)\n method3 : function(intarg, strarg) { \n ";
	public static final String method3End = "\t}\n";
	
	public static final String method4WithReturnTypeStart = "//>public String method4()\n method4 : function() { \n ";
	public static final String method4End = "return \"Hi\" \n \t}\n";
	
	public static final String mainMethodStart = "//>public void main(String [] args)\n method3 : function(args) { \n ";
	public static final String mainMethodEnd = "\t}\n";
	
	public static final String intProperty = "intProperty : 10 //<int \n";
	public static final String intVarStatement = "var intvar = 10;//<int \n";
	public static final String stringProperty = "stringProperty : \"string\" //<String \n";
	public static final String StringVarStatement = "var strvar = \"This is String\";//<String \n";
	public static final String forLoopStatementStart = "var arr = [1,2,3,4];//<Array \n " +
			"var i = 0 //<int; \n " +
			"for(i = 0; i<arr.length; i++){\n";
	public static final String forLoopStatementEnd = "} \n";
	public static final String ifStatementStart = "var i = 10; \n" +
			"if ( i == 10 ) {";
	public static final String ifStatementEnd = "} \n";
}

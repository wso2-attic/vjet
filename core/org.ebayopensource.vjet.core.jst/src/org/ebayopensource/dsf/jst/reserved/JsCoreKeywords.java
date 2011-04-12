/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.reserved;

import java.util.HashSet;
import java.util.Set;

import org.ebayopensource.dsf.jsnative.anno.JsVersion;

/**
 * Defines JavaScript reserved words that may not be used as variables, functions, methods, 
 * or object identifiers.
 * @see https://developer.mozilla.org/En/Core_JavaScript_1.5_Reference/Reserved_Words
 */
public class JsCoreKeywords {
	
	private static Set<String> s_JsReservedKeywords = new HashSet<String>();
	
	public static boolean isReservedKeyword(String s) {
		return s_JsReservedKeywords.contains(s);
	}
	
	// The following are reserved as existing keywords by the ECMAScript specification. 
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_ZERO)
	public static final String BREAK = reserve("break");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_TWO)
	public static final String CASE = reserve("case");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_FOUR)
	public static final String CATCH = reserve("catch");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_ZERO)
	public static final String CONTINUE = reserve("continue");
	
	@ImplementedBy(jsVersions = JsVersion.NONE)
	public static final String DEBUGGER = reserve("debugger");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_TWO)
	public static final String DEFAULT = reserve("default");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_TWO)
	public static final String DELETE = reserve("delete");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_TWO)
	public static final String DO = reserve("do");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_TWO)
	public static final String ELSE = reserve("else");
	
	@ImplementedBy(jsVersions = JsVersion.NONE)
	public static final String FALSE = reserve("false");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_FOUR)
	public static final String FINALLY = reserve("finally");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_ZERO)
	public static final String FOR = reserve("for");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_ZERO)
	public static final String FUNCTION = reserve("function");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_ZERO)
	public static final String IF = reserve("if");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_ZERO)
	public static final String IN = reserve("in");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_FOUR)
	public static final String INSTANCEOF = reserve("instanceof");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_ZERO)
	public static final String NEW = reserve("new");
	
	@ImplementedBy(jsVersions = JsVersion.NONE)
	public static final String NULL = reserve("null");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_ZERO)
	public static final String RETURN = reserve("return");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_TWO)
	public static final String SWITCH = reserve("switch");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_ZERO)
	public static final String THIS = reserve("this");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_FOUR)
	public static final String THROW = reserve("throw");
	
	@ImplementedBy(jsVersions = JsVersion.NONE)
	public static final String TRUE = reserve("true");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_FOUR)
	public static final String TRY = reserve("try");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_ONE)
	public static final String TYPEOF = reserve("typeof");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_ZERO)
	public static final String VAR = reserve("var");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_ONE)
	public static final String VOID = reserve("void");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_ZERO)
	public static final String WHILE = reserve("while");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_ZERO)
	public static final String WITH = reserve("with");
	
	// The following are reserved as future keywords by the ECMAScript specification
	public static final String EXT_ABSTRACT = reserve("abstract");
	public static final String EXT_BOOLEAN = reserve("boolean");
	public static final String EXT_BYTE = reserve("byte");
	public static final String EXT_CHAR = reserve("char");
	public static final String EXT_CLASS = reserve("class");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_FIVE)
	public static final String EXT_CONST = reserve("const"); // implemented in Mozilla but not specified in ECMA-262
	
	public static final String EXT_DEBUGGER = reserve("debugger");
	public static final String EXT_DOUBLE = reserve("double");
	public static final String EXT_ENUM = reserve("enum");
	public static final String EXT_EXTENDS = reserve("extends");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_FIVE)
	public static final String EXT_EXPORT = reserve("export"); // implemented in Mozilla but not specified in ECMA-262
	
	public static final String EXT_FINAL = reserve("final");
	public static final String EXT_FLOAT = reserve("float");
	public static final String EXT_GOTO = reserve("goto");
	public static final String EXT_IMPLEMENTS = reserve("implements");
	
	@ImplementedBy(jsVersions = JsVersion.MOZILLA_ONE_DOT_FIVE)
	public static final String EXT_IMPORT = reserve("import"); // implemented in Mozilla but not specified in ECMA-262
	
	public static final String EXT_INT = reserve("int");
	public static final String EXT_INTERFACE = reserve("interface");
	public static final String EXT_LONG = reserve("long");
	public static final String EXT_NATIVE = reserve("native");
	public static final String EXT_PACKAGE = reserve("package");
	public static final String EXT_PRIVATE = reserve("private");
	public static final String EXT_PROTECTED = reserve("protected");
	public static final String EXT_PUBLIC = reserve("public");
	public static final String EXT_SHORT = reserve("short");
	public static final String EXT_STATIC = reserve("static");
	public static final String EXT_SUPER = reserve("super");
	public static final String EXT_SYNCHRONIZED = reserve("synchronized");
	public static final String EXT_THROWS = reserve("throws");
	public static final String EXT_TRANSIENT = reserve("transient");
	public static final String EXT_VOLATILE = reserve("volatile");
			
	private static String reserve(String keyword) {
		s_JsReservedKeywords.add(keyword);
		return keyword;
	}
}

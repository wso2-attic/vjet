/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.meta;


public class VjoKeywords {
	
	// VJO Annotation keywords
	public static final String ANNO_START_NEXT = "//>";
	public static final String ANNO_START_PREVIOUS = "//<";
	public static final String ANNO_MULTI_START_NEXT = "/*>";
	public static final String ANNO_MULTI_END = "*/";
	
	// VJO type keywords
	public static final String VJO = "vjo";
	public static final String VJ$ = "vj$";
	public static final String INHERITS = "inherits"; // extends
	public static final String SATISFIES = "satisfies"; // implements
	public static final String MIXIN = "mixin"; //add instance (+ static inferred) members/methods from module
	public static final String GLOBALS = "globals"; //add only static members/methods from module
//	public static final String MIXINPROPS = "mixinProps"; //add only static members/methods from module
	public static final String EXPECTS = "expects"; //Used only by a mixin
	public static final String MAKE_FINAL = "makeFinal"; //final
	public static final String NEEDS = "needs"; // import
	public static final String NEEDSLIB = "needslib"; // import
	public static final String TYPE = "type";	// class type (old)
	public static final String CTYPE = "ctype";	// class type
	public static final String ITYPE = "itype"; // interface type
	public static final String LTYPE = "ltype"; // library type
	public static final String MTYPE = "mtype"; // mixin type
	public static final String ETYPE = "etype"; // enum type
	public static final String OTYPE = "otype"; // object type
	public static final String FTYPE = "ftype"; // function type
	public static final String NEEDS_IMPL = "NEEDS_IMPL"; // NEEDS_IMPL
	public static final String DEFS = "defs"; // defs
	public static final String VALUES = "values"; // used for enum values
	public static final String BASE = "base"; //super
	public static final String OUTER = "outer"; //inner type
	public static final String PARENT = "parent"; //annonymous type
	public static final String PROPS = "props"; // static section
	public static final String PROTOS = "protos"; // instance section
	public static final String OPTIONS = "options"; // options section
	public static final String CONSTRUCTS = "constructs"; // constructor name
	public static final String INITS = "inits"; // static init section
	public static final String MAKE = "make"; // make, for use to make anonymous types
	public static final String ENDTYPE = "endType"; // endType marks the end of a vjo definition
	public static final String IS_INSTANCE = "isInstance"; // instanceOf
	public static final String CLASS = "clazz"; // .class equivalent
	public static final String VJO_OBJECT = VJO + "." + "Object";
	public static final String VJO_ENUM = VJO + "." + "Enum";
	public static final String VJO_GET_TYPE = VJO + "." + "getType";
	public static final String METATYPE_OPTION = "metatype";
}

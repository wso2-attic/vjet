/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstObjectLiteralType;

public interface VjoConstants {

	IJstType NULL = new JstObjectLiteralType("null");
	IJstType UNDEFINED = new JstObjectLiteralType("undefined");
	IJstType ARBITARY = new JstObjectLiteralType("arbitary");
	
	public static class Keyword{
		private static List<String> JAVA_ONLY_KEYWORDS_HIDDEN = new ArrayList<String>();
		
		static{
			JAVA_ONLY_KEYWORDS_HIDDEN.add("private");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("boolean");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("abstract");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("implements");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("protected");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("throws");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("double");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("import");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("public");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("byte");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("transient");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("int");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("short");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("final");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("interface");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("extends");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("static");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("char");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("long");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("strictfp");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("volatile");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("class");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("float");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("native");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("super");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("const");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("package");
			JAVA_ONLY_KEYWORDS_HIDDEN.add("synchronized");
		}
		
		private static List<String> JAVA_FULL_KEYWORDS_HIDDEN = new ArrayList<String>();
		
		static{
			JAVA_FULL_KEYWORDS_HIDDEN.add("private");
			JAVA_FULL_KEYWORDS_HIDDEN.add("default");
			JAVA_FULL_KEYWORDS_HIDDEN.add("if");
			JAVA_FULL_KEYWORDS_HIDDEN.add("this");
			JAVA_FULL_KEYWORDS_HIDDEN.add("do");
			JAVA_FULL_KEYWORDS_HIDDEN.add("throw");
			JAVA_FULL_KEYWORDS_HIDDEN.add("break");
			JAVA_FULL_KEYWORDS_HIDDEN.add("else");
			JAVA_FULL_KEYWORDS_HIDDEN.add("instanceof");
			JAVA_FULL_KEYWORDS_HIDDEN.add("return");
			JAVA_FULL_KEYWORDS_HIDDEN.add("case");
			JAVA_FULL_KEYWORDS_HIDDEN.add("try");
			JAVA_FULL_KEYWORDS_HIDDEN.add("catch");
			JAVA_FULL_KEYWORDS_HIDDEN.add("finally");
			JAVA_FULL_KEYWORDS_HIDDEN.add("while");
			JAVA_FULL_KEYWORDS_HIDDEN.add("for");
			JAVA_FULL_KEYWORDS_HIDDEN.add("new");
			JAVA_FULL_KEYWORDS_HIDDEN.add("switch");
			JAVA_FULL_KEYWORDS_HIDDEN.add("continue");
			JAVA_FULL_KEYWORDS_HIDDEN.add("boolean");
			JAVA_FULL_KEYWORDS_HIDDEN.add("abstract");
			JAVA_FULL_KEYWORDS_HIDDEN.add("implements");
			JAVA_FULL_KEYWORDS_HIDDEN.add("protected");
			JAVA_FULL_KEYWORDS_HIDDEN.add("throws");
			JAVA_FULL_KEYWORDS_HIDDEN.add("double");
			JAVA_FULL_KEYWORDS_HIDDEN.add("import");
			JAVA_FULL_KEYWORDS_HIDDEN.add("public");
			JAVA_FULL_KEYWORDS_HIDDEN.add("byte");
			JAVA_FULL_KEYWORDS_HIDDEN.add("transient");
			JAVA_FULL_KEYWORDS_HIDDEN.add("int");
			JAVA_FULL_KEYWORDS_HIDDEN.add("short");
			JAVA_FULL_KEYWORDS_HIDDEN.add("final");
			JAVA_FULL_KEYWORDS_HIDDEN.add("interface");
			JAVA_FULL_KEYWORDS_HIDDEN.add("extends");
			JAVA_FULL_KEYWORDS_HIDDEN.add("static");
			JAVA_FULL_KEYWORDS_HIDDEN.add("char");
			JAVA_FULL_KEYWORDS_HIDDEN.add("long");
			JAVA_FULL_KEYWORDS_HIDDEN.add("strictfp");
			JAVA_FULL_KEYWORDS_HIDDEN.add("volatile");
			JAVA_FULL_KEYWORDS_HIDDEN.add("class");
			JAVA_FULL_KEYWORDS_HIDDEN.add("float");
			JAVA_FULL_KEYWORDS_HIDDEN.add("native");
			JAVA_FULL_KEYWORDS_HIDDEN.add("super");
			JAVA_FULL_KEYWORDS_HIDDEN.add("const");
			JAVA_FULL_KEYWORDS_HIDDEN.add("package");
			JAVA_FULL_KEYWORDS_HIDDEN.add("synchronized");
		}
	}
	
	List<String> JAVA_ONLY_KEYWORDS = Collections.unmodifiableList(Keyword.JAVA_ONLY_KEYWORDS_HIDDEN);
	List<String> JAVA_FULL_KEYWORDS = Collections.unmodifiableList(Keyword.JAVA_FULL_KEYWORDS_HIDDEN);

	public static final class NativeTypes{
		
		public static IJstType getStringJstType(){
			return JstCache.getInstance().getType(
					org.ebayopensource.dsf.jsnative.global.String.class.getSimpleName());
		}
		
		public static IJstType getNumberJstType(){
			return JstCache.getInstance().getType(
					org.ebayopensource.dsf.jsnative.global.Number.class.getSimpleName());
		}
		
		public static IJstType getIntJstType(){
			return getNumberJstType();
		}
		
		public static IJstType getDoubleJstType(){
			return getNumberJstType();
		}
		
		public static IJstType getBooleanJstType(){
			return JstCache.getInstance().getType(
					org.ebayopensource.dsf.jsnative.global.Boolean.class.getSimpleName());
		}
		
		public static IJstType getPrimitiveBooleanJstType(){
			return JstCache.getInstance().getType(
					org.ebayopensource.dsf.jsnative.global.PrimitiveBoolean.class.getSimpleName());
		}
		
		public static IJstType getDateJstType(){
			return JstCache.getInstance().getType(
					org.ebayopensource.dsf.jsnative.global.Date.class.getSimpleName());
		}
		
		public static IJstType getObjectJstType(){
			return JstCache.getInstance().getType(
					org.ebayopensource.dsf.jsnative.global.Object.class.getSimpleName());
		}
		
		public static IJstType getRegExpJstType(){
			return JstCache.getInstance().getType(
					org.ebayopensource.dsf.jsnative.global.RegExp.class.getSimpleName());
		}
		
		public static IJstType getArrayJstType(){
			return JstCache.getInstance().getType(
					org.ebayopensource.dsf.jsnative.global.Array.class.getSimpleName());
		}
		
		public static IJstType[] getJstNativeTypes(){
			return new IJstType[]{
					getPrimitiveBooleanJstType(),
					getBooleanJstType(),
					getAliasBooleanJstType(),
					getNumberJstType(),
					getAliasNumberJstType(),
					getStringJstType(),
					getAliasStringJstType(),
					getObjectJstType(),
					getAliasObjectJstType(),
					getRegExpJstType(),
					getDateJstType(),
					getAliasDateJstType(),
					getArrayJstType(),
					getAliasArrayJstType()
			};
		}

		public static IJstType getAliasObjectJstType() {
			return JstCache.getInstance().getType(
					"js."+org.ebayopensource.dsf.jsnative.global.Object.class.getSimpleName());
		}

		public static IJstType getAliasDateJstType() {
			return JstCache.getInstance().getType(
					"js."+org.ebayopensource.dsf.jsnative.global.Date.class.getSimpleName());
		}

		public static IJstType getAliasStringJstType() {
			return JstCache.getInstance().getType(
					"js."+org.ebayopensource.dsf.jsnative.global.String.class.getSimpleName());
		}

		public static IJstType getAliasArrayJstType() {
			return JstCache.getInstance().getType(
					"js."+org.ebayopensource.dsf.jsnative.global.String.class.getSimpleName());
		}
		
		public static IJstType getAliasNumberJstType() {
			return JstCache.getInstance().getType(
					"js."+org.ebayopensource.dsf.jsnative.global.Number.class.getSimpleName());
		}
		
		public static IJstType getAliasBooleanJstType() {
			return JstCache.getInstance().getType(
					"js."+org.ebayopensource.dsf.jsnative.global.Boolean.class.getSimpleName());
		}
	}
	
}

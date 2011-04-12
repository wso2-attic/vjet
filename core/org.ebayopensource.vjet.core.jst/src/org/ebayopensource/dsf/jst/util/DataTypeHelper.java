/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.ebayopensource.dsf.jsnative.anno.JsNativeMeta;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.datatype.JstReservedTypes;
import org.ebayopensource.dsf.jst.declaration.JstType;

public class DataTypeHelper {
	private static Set<String> s_primitiveTypes = new HashSet<String>();
	static {
		s_primitiveTypes.add("boolean");
		s_primitiveTypes.add("PrimitiveBoolean");
		s_primitiveTypes.add("byte");
		s_primitiveTypes.add("short");
		s_primitiveTypes.add("int");
		s_primitiveTypes.add("long");
		s_primitiveTypes.add("float");
		s_primitiveTypes.add("double");
		s_primitiveTypes.add("char");
		s_primitiveTypes.add("String");
		s_primitiveTypes.add("void");
	}
	private static final List<String> s_numericPrimitiveTypes = new ArrayList<String>();
	static {
		s_numericPrimitiveTypes.add("byte");
		s_numericPrimitiveTypes.add("short");
//		s_numericPrimitiveTypes.add("char");
		s_numericPrimitiveTypes.add("int");
		s_numericPrimitiveTypes.add("float");
		s_numericPrimitiveTypes.add("long");
		s_numericPrimitiveTypes.add("double");
	}
	private static final List<String> s_numericWrapperTypes = new ArrayList<String>();
	static {
		s_numericWrapperTypes.add("Byte");
		s_numericWrapperTypes.add("Short");
//		s_numericWrapperTypes.add("Character");
		s_numericWrapperTypes.add("Integer");
		s_numericWrapperTypes.add("Float");
		s_numericWrapperTypes.add("Long");
		s_numericWrapperTypes.add("Double");
	}	
	private static final Map<String,JstType> s_wrapperToPrimitiveTypes = new HashMap<String,JstType>();
	static {
		s_wrapperToPrimitiveTypes.put("Boolean", JstReservedTypes.JavaPrimitive.BOOLEAN);
		s_wrapperToPrimitiveTypes.put("Byte", JstReservedTypes.JavaPrimitive.BYTE);
		s_wrapperToPrimitiveTypes.put("Short", JstReservedTypes.JavaPrimitive.SHORT);
		s_wrapperToPrimitiveTypes.put("Character", JstReservedTypes.JavaPrimitive.CHAR);
		s_wrapperToPrimitiveTypes.put("Integer", JstReservedTypes.JavaPrimitive.INT);
		s_wrapperToPrimitiveTypes.put("Float", JstReservedTypes.JavaPrimitive.FLOAT);
		s_wrapperToPrimitiveTypes.put("Long", JstReservedTypes.JavaPrimitive.LONG);
		s_wrapperToPrimitiveTypes.put("Double", JstReservedTypes.JavaPrimitive.DOUBLE);
	}
	private static final List<String> s_stringTypes = new ArrayList<String>();
	static {
//		s_stringTypes.add("char");
		s_stringTypes.add("String");
	}	
	private static List<String> s_javaLangType = new ArrayList<String>();
	static {
		s_javaLangType.add(Boolean.class.getSimpleName());
		s_javaLangType.add(Byte.class.getSimpleName());
		s_javaLangType.add(Short.class.getSimpleName());
		s_javaLangType.add(Integer.class.getSimpleName());
		s_javaLangType.add(Long.class.getSimpleName());
		s_javaLangType.add(Float.class.getSimpleName());
		s_javaLangType.add(Double.class.getSimpleName());
		s_javaLangType.add(Number.class.getSimpleName());
		s_javaLangType.add(Character.class.getSimpleName());
		s_javaLangType.add(String.class.getSimpleName());
		
		s_javaLangType.add(Void.class.getSimpleName());
		s_javaLangType.add(Object.class.getSimpleName());
		s_javaLangType.add(Class.class.getSimpleName());
		s_javaLangType.add(Enum.class.getSimpleName());
		s_javaLangType.add(Math.class.getSimpleName());
		s_javaLangType.add(StringBuffer.class.getSimpleName());
		s_javaLangType.add(StringBuilder.class.getSimpleName());
		s_javaLangType.add(System.class.getSimpleName());
		
		s_javaLangType.add(Throwable.class.getSimpleName());
		s_javaLangType.add(Exception.class.getSimpleName());
		s_javaLangType.add(RuntimeException.class.getSimpleName());
		s_javaLangType.add(Error.class.getSimpleName());
		
		s_javaLangType.add(ArithmeticException.class.getSimpleName());
		s_javaLangType.add(ArrayIndexOutOfBoundsException.class.getSimpleName());
		s_javaLangType.add(ArrayStoreException.class.getSimpleName());
		s_javaLangType.add(ClassCastException.class.getSimpleName());
		s_javaLangType.add(ClassNotFoundException.class.getSimpleName());		
		s_javaLangType.add(CloneNotSupportedException.class.getSimpleName());
		s_javaLangType.add(EnumConstantNotPresentException.class.getSimpleName());
		s_javaLangType.add(IllegalAccessException.class.getSimpleName());
		s_javaLangType.add(IllegalArgumentException.class.getSimpleName());
		s_javaLangType.add(IllegalMonitorStateException.class.getSimpleName());
		s_javaLangType.add(IllegalStateException.class.getSimpleName());
		s_javaLangType.add(IndexOutOfBoundsException.class.getSimpleName());
		s_javaLangType.add(InstantiationException.class.getSimpleName());
		s_javaLangType.add(NegativeArraySizeException.class.getSimpleName());
		s_javaLangType.add(NullPointerException.class.getSimpleName());
		s_javaLangType.add(NumberFormatException.class.getSimpleName());	
		s_javaLangType.add(StringIndexOutOfBoundsException.class.getSimpleName());
		s_javaLangType.add(AbstractMethodError.class.getSimpleName());
		s_javaLangType.add(AssertionError.class.getSimpleName());	
	}
	
	public static boolean isPrimitiveType(String name){
		return s_primitiveTypes.contains(name);
	}
	
	public static boolean isPrimitiveType(IJstType type){
		JstType jstType = JstTypeHelper.getJstType(type);
		return jstType == null ? false : s_primitiveTypes.contains(jstType.getSimpleName());
	}
	
	public static boolean isBooleanPrimitiveType(IJstType type){
		JstType jstType = JstTypeHelper.getJstType(type);
		return jstType == null ? false : "boolean".equals(jstType.getSimpleName());
	}
	
	public static boolean isBooleanWrapperType(final IJstType type){
		JstType jstType = JstTypeHelper.getJstType(type);
		return jstType == null ? false : "Boolean".equals(jstType.getSimpleName());
	}
	
	public static boolean isNumericPrimitiveType(final IJstType type){
		JstType jstType = JstTypeHelper.getJstType(type);
		return jstType == null ? false : s_numericPrimitiveTypes.contains(jstType.getSimpleName());
	}
	
	public static boolean isNumericWrapperType(final IJstType type){
		JstType jstType = JstTypeHelper.getJstType(type);
		return jstType == null ? false : s_numericWrapperTypes.contains(jstType.getSimpleName());
	}
	
	public static boolean isCharPrimitiveType(final IJstType type){
		JstType jstType = JstTypeHelper.getJstType(type);
		return jstType == null ? false : "char".equals(jstType.getSimpleName());
	}
	
	public static boolean isCharWrapperType(final IJstType type){
		JstType jstType = JstTypeHelper.getJstType(type);
		return jstType == null ? false : "Character".equals(jstType.getSimpleName());
	}
	
	public static JstType getPrimitivetype(IJstType wrapperType){
		return wrapperType == null ? null : s_wrapperToPrimitiveTypes.get(wrapperType.getSimpleName());
	}
	
	public static boolean isString(IJstType type){
		JstType jstType = JstTypeHelper.getJstType(type);
		return jstType == null ? false : s_stringTypes.contains(jstType.getSimpleName());
	}
	
	public static boolean isInJavaLang(String name){
		return s_javaLangType.contains(name);
	}
	
	public static boolean isInJDK(String name){
		return name != null && name.startsWith("java.");
	}
	
	public static String getDefaultValue(String name){
		if ("boolean".equals(name)){
			return "false";
		}
		else if ("char".equals(name)){
			return "\"\"";
		}
		else if (s_numericPrimitiveTypes.contains(name)) {
			return "0";
		}
		else {
			return null;
		}
	}
	
	public static boolean canPromote(final IJstType aType, final IJstType bType){
		JstType jstTypeA = JstTypeHelper.getJstType(aType);
		JstType jstTypeB = JstTypeHelper.getJstType(bType);
		if (isNumericPrimitiveType(jstTypeA) && isNumericPrimitiveType(jstTypeB)){
			int indexA = -1;
			int indexB = -1;
			for (int i=0; i<s_numericPrimitiveTypes.size(); i++){
				if (s_numericPrimitiveTypes.get(i).equals(jstTypeA.getSimpleName())){
					indexA = i;
				}
				if (s_numericPrimitiveTypes.get(i).equals(jstTypeB.getSimpleName())){
					indexB = i;
				}
			}
			return indexA < indexB;
		}
		return false;
	}
	
	public static String getWrapperTypeName(String str,boolean fullName){
		if (str.equals("boolean")){
			if (fullName)
				return "vjo.java.lang.Boolean";
			else
				return "Boolean";
		}
		else if (str.equals("byte")){
			if (fullName)
				return "vjo.java.lang.Byte";
			else 
				return "Byte";
		} 
		else if (str.equals("char")){
			if (fullName)
				return "vjo.java.lang.Character";
			else 
				return "Character";
		}
		else if (str.equals("short")){
			if (fullName)
				return "vjo.java.lang.Short";
			else 
				return "Short";
		} 
		else if (str.equals("int")){
			if (fullName)
				return "vjo.java.lang.Integer";
			else 
				return "Integer";
		} 
		else if (str.equals("long")){
			if (fullName)
				return "vjo.java.lang.Long";
			else 
				return "Long";
		} 
		else if (str.equals("float")){
			if (fullName)
				return "vjo.java.lang.Float";
			else
				return "Float";
		} 
		else if (str.equals("double")){
			if (fullName)
				return "vjo.java.lang.Double";
			else
				return "Double";
		} 
		return null;		
	}
	
	private static final Map<String, Class<?>> s_primitiveClasses = new HashMap<String, Class<?>>(10);
	static {
		s_primitiveClasses.put(String.class.getSimpleName(), String.class);
		s_primitiveClasses.put(Number.class.getSimpleName(), Number.class);
		s_primitiveClasses.put(int.class.getSimpleName(), int.class);
		s_primitiveClasses.put(void.class.getSimpleName(), void.class);
		s_primitiveClasses.put(boolean.class.getSimpleName(), boolean.class);
		s_primitiveClasses.put(short.class.getSimpleName(), short.class);
		s_primitiveClasses.put(long.class.getSimpleName(), long.class);
		s_primitiveClasses.put(float.class.getSimpleName(), float.class);
		s_primitiveClasses.put(double.class.getSimpleName(), double.class);
		s_primitiveClasses.put(char.class.getSimpleName(), char.class);
		s_primitiveClasses.put(byte.class.getSimpleName(), byte.class);
	}
	
	public static Class<?> getPrimitiveClass(String name) {
		return s_primitiveClasses.get(name);
	}
	

	private static final Map<String, String> s_javaToNativeMap = new HashMap<String, String>(10);
	static {
		s_javaToNativeMap.put(Object.class.getName(), org.ebayopensource.dsf.jsnative.global.Object.class.getName());
		s_javaToNativeMap.put(String.class.getName(), org.ebayopensource.dsf.jsnative.global.String.class.getName());
		s_javaToNativeMap.put(Boolean.class.getName(), org.ebayopensource.dsf.jsnative.global.Boolean.class.getName());
		s_javaToNativeMap.put(Date.class.getName(), org.ebayopensource.dsf.jsnative.global.Date.class.getName());
		s_javaToNativeMap.put(Number.class.getName(), org.ebayopensource.dsf.jsnative.global.Number.class.getName());
	}
	
	public static boolean isJavaMappedToNative(String javaName) {
		return s_javaToNativeMap.containsKey(javaName);
	}
	
	/**
	 ** This would get the most suitable name for VJO from JST.
	 **/
	public static String getTypeName(String javaName) {
		String name = javaName;
		
		if (s_javaToNativeMap.containsKey(javaName)) {
			name = s_javaToNativeMap.get(javaName);
		}

		if (getNativeType(name) != null) {
			String alias = JsNativeMeta.getNativeTypeAlias(javaName);
			if (alias == null) {
				//Alias not found!
				//If it's native type then return simple name as a fallback. 
				// It's not guaranteed that it would work in JS. Alias is the recommended way
				
				if (name.indexOf(".") > 0) {
					name = name.substring(name.lastIndexOf(".") + 1);
				}
			} else {
				name = alias;
			}
		}
		
		return name;
	}
	
	public static String getJavaTypeNameForNative(String nativeTypeName) {
		if (nativeTypeName != null && s_javaToNativeMap.containsValue(nativeTypeName)) {
			for (Entry<String, String> itm : s_javaToNativeMap.entrySet()) {
				if (nativeTypeName.equals(itm.getValue())) {
					return itm.getKey();
				}
			}
		}
		
		return null;
	}
	
	public static Class<?> getNativeType(String name){
		return JsNativeMeta.getNativeClass(name);
	}
}

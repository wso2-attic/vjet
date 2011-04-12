/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.generate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.serializers.ISerializableForVjo;

public class DefaultJsrFilters implements IJsrFilters {

	public static String VJO = "vjo";
	public static final String METHOD_SUFFIX = "_";
	private static final List<String> s_satisfies = Arrays.asList(
			"vjo.java.io.Serializable", 
			"org.ebayopensource.dsf.serializers.ISerializableForVjo",
			"vjo.java.lang.Appendable",
			"java.lang.CharSequence");

	private static final List<String> s_extends = Arrays.asList(
			"Object", 
			"vjo.Object",
			"vjo.Enum",
			"org.ebayopensource.dsf.aggregator.serializable.BaseVjoSerializable",
			"java.security.BasicPermission");
	
	private static final List<String> RESERVED_WORDS = Arrays.asList( "abstract", "assert",
			"boolean", "break", "byte", "case", "catch", "char", "class",
			"const", "continue", "default", "do", "double", "else", "enum",
			"extends", "false", "final", "finally", "float", "for", "goto",
			"if", "implements", "import", "instanceof", "int", "interface",
			"long", "native", "null", "package", "private", "protected",
			"public", "return", "short", "strictfp", "super",
			"switch", "synchronized", "this", "throw", "throws", "transient",
			"true", "try", "void", "volatile", "while" );

	private static final List<String> s_reserved_methods = Arrays.asList(
			"hashCode", 
			"toString", 
			"getClass",
			"getClassName",
			"finalize",
			//Methods of JsObj
			"call",
			"chuck",
			"getProp",
			"hasReturn",
			"setProp",
			"generate",
			"getClassName",
			"getCmpMeta",
			"getCompSpec",
			"getInstanceId",
			"getInstancePropertySetters",
			"getInstantiationJs",
			"getJsScopedVarRef",
			"getParams",
			"getRefJs",
			"getValue",
			"getValueType",
			"getVariableRef",
			"isGenned",
			"isHandler",
			"isInstance",
			"setInstanceId",
			"setIsHandler",
			"setValue"
		);
	
	public boolean isJsr(String jstTypeName) {
		return !(
				jstTypeName.startsWith("java.")
//				|| jstTypeName.startsWith(VJO + ".java.")
				|| isJavaPrimitiveOrWrapper(jstTypeName)
				);
	}

	public boolean isSkipImport(IJstType jstType) {
		String name = normalize(jstType.getName());
		return isJavaPrimitiveOrWrapper(name) || isJavaLang(name);
	}
	
	private static final Set<String> s_primitives = new HashSet<String>(23);
	static {
		s_primitives.add(String.class.getSimpleName());
		s_primitives.add(int.class.getSimpleName());
		s_primitives.add(void.class.getSimpleName());
		s_primitives.add(boolean.class.getSimpleName());
		s_primitives.add(short.class.getSimpleName());
		s_primitives.add(long.class.getSimpleName());
		s_primitives.add(float.class.getSimpleName());
		s_primitives.add(double.class.getSimpleName());
		s_primitives.add(char.class.getSimpleName());
		s_primitives.add(byte.class.getSimpleName());
	}
	
	private static final Set<String> s_wrappers = new HashSet<String>(23);
	static {
		s_wrappers.add(Boolean.class.getSimpleName());
		s_wrappers.add(Integer.class.getSimpleName());
		s_wrappers.add(Long.class.getSimpleName());
		s_wrappers.add(Float.class.getSimpleName());
		s_wrappers.add(Double.class.getSimpleName());
		s_wrappers.add(Short.class.getSimpleName());
		s_wrappers.add(Byte.class.getSimpleName());
		s_wrappers.add(Character.class.getSimpleName());
		s_wrappers.add(Object.class.getSimpleName());
	}

	public boolean isJavaPrimitiveOrWrapper(String name) {
		return isJavaPrimitive(name) || isJavaWrapper(name);				
	}
	
	public boolean isJavaPrimitive(String name) {
		return s_primitives.contains(name);
	}
	
	public boolean isJavaWrapper(String name) {
		return s_wrappers.contains(name);
	}

	public boolean isJavaLang(String typeName) {
		int packageIndex = typeName.lastIndexOf(".");
		if (packageIndex < 1) {
			return false;
		}
		return "java.lang".equals(typeName.substring(0, packageIndex));
	}

	public String normalize(String sourceName) {
		if (sourceName.contains(VJO + ".java.logging.")) {
			return sourceName.replace(VJO + ".java.logging.", "java.util.logging.");
		}
		return sourceName.replace(VJO + ".java.", "java.");
	}
	public boolean isSkipSatisfies(IJstType jstType) {
		return (s_satisfies.contains(jstType.getName()));
	}

	public boolean isSkipMethod(IJstMethod jstMethod) {
		return false;
	}
	
	public boolean isSkipExtends(IJstType jstType) {
		return (s_extends.contains(jstType.getName()));
	}


	
	public String decorateMethod(IJstMethod jstMethod) {
		String mtdName = jstMethod.getOriginalName();
		List<JstArg> args = jstMethod.getArgs();
		
		if(RESERVED_WORDS.contains(mtdName)){
			return mtdName + METHOD_SUFFIX;
		}
	
		
		if (
				(s_reserved_methods.contains(mtdName) /*&& jstMethod.getArgs().size() == 0*/) ||
				("equals".equals(mtdName) && args.size() == 1 && args.get(0).getType().getSimpleName().equals("Object"))
		) {
			return mtdName + METHOD_SUFFIX;
		}	
		return mtdName;
	}
	public boolean isSkipImportJsr(IJstType jstType){
		String name = jstType.getName();
		return s_excludeList.contains(name);		
	}
	private static final Set<String> s_excludeList = new HashSet<String>(3);
	static {
		s_excludeList.add(String.class.getName());
		s_excludeList.add(Object.class.getName());
		s_excludeList.add(Math.class.getName());
	}

	private static final Map<String, Class<?>> s_javaSerializable = new HashMap<String, Class<?>>(3);
	static {
		s_javaSerializable.put("vjo." + ArrayList.class.getName(), ArrayList.class);
		s_javaSerializable.put("vjo." + HashMap.class.getName(), HashMap.class);
	}
	
	public boolean isSerializableForVjo(IJstType type, boolean isRoot) {
		boolean ret = false;
		if (type == null) return false;
		
		if (isRoot) {
			if (getJavaTypeForSerialable(type) != null){
				return true;
			}
		}

		if (isJavaPrimitiveOrWrapper(type.getName()) || type.isEnum()) {
			return false;
		}
		
		String forName = ISerializableForVjo.class.getName();
		if (type.isInterface()) {
			//Check extends of Interface
			if (type.getName().equals(forName)) {
				return true;
			}
		} else {
			if (type.getName().equals(forName)) {
				return true;
			}
			//Check interfaces of the class
			for (IJstType iType : type.getSatisfies()) {
				if (iType.getName().equals(forName)) {
					return true;
				}
				ret = isSerializableForVjo(iType, false);
				if (ret) return ret;
			}
		}
		for (IJstType eType : type.getExtends()) {
			ret = isSerializableForVjo(eType, false);
			if (ret) return ret;
		}
		
		return false;
	}

	public Class<?> getJavaTypeForSerialable(IJstType type) {
		return s_javaSerializable.get(type.getName());
	}

	public boolean isSkipMethodAndProperties(String fullName) {
		return fullName.startsWith("vjo.java.");
	}

	@Override
	public String decorateProperty(String name) {
		
		if(RESERVED_WORDS.contains(name)){
			return name + METHOD_SUFFIX;
		}
		return name;
	}
}

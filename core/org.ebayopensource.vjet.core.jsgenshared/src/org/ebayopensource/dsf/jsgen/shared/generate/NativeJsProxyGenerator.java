/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.generate;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.ebayopensource.dsf.jsgen.shared.classref.IClassR;
import org.ebayopensource.dsf.jsgen.shared.util.GeneratorJstHelper;
import org.ebayopensource.dsf.jsnative.anno.JsNativeMeta;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstOType;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstRefType;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ISynthesized;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstArray;
import org.ebayopensource.dsf.jst.declaration.JstFunctionRefType;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstObjectLiteralType;
import org.ebayopensource.dsf.jst.declaration.JstParamType;
import org.ebayopensource.dsf.jst.declaration.JstProxyMethod;
import org.ebayopensource.dsf.jst.declaration.JstProxyProperty;
import org.ebayopensource.dsf.jst.declaration.JstProxyType;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstTypeWithArgs;
import org.ebayopensource.dsf.jst.declaration.JstWildcardType;
import org.ebayopensource.dsf.jst.util.DataTypeHelper;
import org.ebayopensource.vjo.meta.VjoConvention;

/**
 * An utility class to generate NativeJsProxy class from a
 * JavaScript file (via its JST).
 */
public class NativeJsProxyGenerator extends SourceGenerator {
	
	private static final String IMPORT = "import ";
	private static final String PACKAGE = "package ";
	private static final String VJOX = VjoConvention.getVjoExtScope() + ".";
	private static final String VJOX_JAVA = VJOX + "java.";
	private static final String VJOX_JAVA_LANG = VJOX_JAVA + "lang.";
	private static final String DEFAULT_PARAM_PREFIX = "p";
	
	private IJstType m_clzType = null;
	private TypeMetaMgr m_javaTypeMgr = null;
	private TypeMetaMgr m_customTypeMgr = null;
	private TypeMetaMgr m_jsNativeTypeMgr = null;
	private List<MethodMeta> m_constructors = null;
	private List<MethodMeta> m_staticMethods = null;
	private List<MethodMeta> m_instanceMethods = null;
	private List<IJstProperty> m_constants = null;
	private List<IJstProperty> m_staticProperties = null;
	private List<IJstProperty> m_instanceProperties = null;
	private LinkedHashSet<String> m_frameworkImports = null;
	private Set<String> m_currentDefinedTypes = null;
	private InnerTypeInfo m_innerTypeInfo = null;
	private Map<IJstType, InnerTypeInfo> m_innerTypeInfoMap = null;
	private boolean m_hasOlType = false;
	private boolean m_hasFnType = false;
	private boolean m_needsIJsJavaProxy = false;
	private boolean m_hasJsTypeRef = false;
	private boolean m_hasNonFnRef = false;
	
	public NativeJsProxyGenerator(final PrintWriter writer, final CodeStyle style) {
		super(writer, new Indenter(writer, style), style);
	}
	
	public NativeJsProxyGenerator writeProxy(final IJstType type) {
		return writeProxy(type, null);
	}
	
	private NativeJsProxyGenerator writeProxy(final IJstType type, InnerTypeInfo innerTypeInfo) {	
		m_innerTypeInfo = innerTypeInfo;
		
		setUp(type);

		if (m_innerTypeInfo == null) {
			writePkg();
			writeNewline();
			writeImports();
			writeNewline();
			writeClassLevelComments();
		}
		
		writeClassDefinition();		
		getWriter().append(" {");
		indent();
		if (m_clzType.isOType()) {
			writeOTypeDef();
		} else {
			writeFrameworkConstructors(m_clzType.getSimpleName());
			// Constructor
			writeConstructors();

			writeConstants();

			writeProperties(true);

			writeProperties(false);

			if (!m_clzType.isInterface()) {
				for (MethodMeta meta : m_staticMethods) {
					writeMethods(meta);
				}
			}

			for (MethodMeta meta : m_instanceMethods) {
				writeMethods(meta);
			}

			if (m_clzType.isClass()) {
				writeTypeRef();
				Set<String> methodNames = new HashSet<String>();
				for (MethodMeta meta : m_staticMethods) {
					if (!meta.m_isOverload) {
						writeStaticFuncProxy(meta, methodNames);
					}
				}
				String clzName = m_clzType.getSimpleName();
				if (!m_clzType.getParamNames().isEmpty()) {
					clzName += ((JstType) m_clzType).getParamsDecoration();
				}
				for (MethodMeta meta : m_instanceMethods) {
					if (!meta.m_isOverload) {
						writeInstanceFuncProxy(meta, methodNames, clzName);
					}
				}
			}

			List<? extends IJstType> embededTypes = m_clzType.getEmbededTypes();
			if (embededTypes.size() > 0) {
				writeNewline();
				for (IJstType embededType : embededTypes) {
					if (!embededType.isMixin()
							&& embededType.getModifiers().isPublic()) {
						writeNewline();
						NativeJsProxyGenerator innerJsr = new NativeJsProxyGenerator(
								getWriter(), getStyle());
						innerJsr.indent();
						innerJsr.writeProxy(embededType, m_innerTypeInfoMap.get(embededType));
						writeNewline();
					}
				}
			}
			outdent();
			writeNewline();
			if (m_clzType.isEmbededType()) {
				writeIndent();
			}
		}
		
		getWriter().append("}");
		return this;
	}
	
	

	private void setUp(final IJstType type) {
		m_clzType = type;
		if (m_innerTypeInfo == null) {
			Set<String> simpleNames = new HashSet<String>();
			m_javaTypeMgr = new TypeMetaMgr(simpleNames);
			m_customTypeMgr = new TypeMetaMgr(simpleNames);
			m_jsNativeTypeMgr = new TypeMetaMgr(simpleNames);
			m_constructors = new ArrayList<MethodMeta>();
			m_staticMethods = new ArrayList<MethodMeta>();
			m_instanceMethods = new ArrayList<MethodMeta>();
			m_constants = new ArrayList<IJstProperty>();
			m_staticProperties = new ArrayList<IJstProperty>();
			m_instanceProperties = new ArrayList<IJstProperty>();
			m_frameworkImports = new LinkedHashSet<String>();
			m_currentDefinedTypes = new HashSet<String>(3);
			m_currentDefinedTypes.add(m_clzType.getName());
			if (!m_clzType.getEmbededTypes().isEmpty()) {
				m_innerTypeInfoMap = new HashMap<IJstType, InnerTypeInfo>(3);
			}
		}
		else {
			m_javaTypeMgr = m_innerTypeInfo.m_javaTypeMgr;
			m_customTypeMgr = m_innerTypeInfo.m_customTypeMgr;
			m_jsNativeTypeMgr = m_innerTypeInfo.m_jsNativeTypeMgr;
			m_constructors = m_innerTypeInfo.m_constructors;
			m_staticMethods = m_innerTypeInfo.m_staticMethods;
			m_instanceMethods = m_innerTypeInfo.m_instanceMethods;
			m_constants = m_innerTypeInfo.m_constants;
			m_staticProperties = m_innerTypeInfo.m_staticProperties;
			m_instanceProperties = m_innerTypeInfo.m_instanceProperties;
			m_currentDefinedTypes = m_innerTypeInfo.m_currentDefinedTypes;
			m_innerTypeInfoMap = m_innerTypeInfo.m_innerTypeInfoMap;
		}
		
		analyze();
	}
	
	private void analyze() {
		if (m_innerTypeInfo != null) {
			return;
		}
		
		m_customTypeMgr.addToSimpleNameMap
			(new TypeMeta(m_clzType.getName(), m_clzType.getSimpleName(), false));
		
		collectFrameworkImports(m_clzType);
		
		addCustomTypeImport(m_clzType.getExtends());
		addCustomTypeImport(m_clzType.getSatisfies());
		
		Set<IJstType> typeSet = new LinkedHashSet<IJstType>();
		
		if (!m_clzType.isOType()) {
			collectTypeFromProperties(m_clzType.getProperties(), typeSet, null);
			IJstMethod constructor = m_clzType.getConstructor();
			if (constructor != null && !(constructor instanceof ISynthesized)) {
				collectTypeFromMethod(constructor, typeSet, null);
			}
			for (IJstMethod method : m_clzType.getMethods()) {
				collectTypeFromMethod(method, typeSet, null);
				if (!method.isOType()) {
					if(hasFunctionArg(method.getArgs())){
						m_needsIJsJavaProxy = true;
					}
					m_hasNonFnRef = true;
				}
			}
		} else {
			for (IJstOType oType : m_clzType.getOTypes()) {
				if (oType instanceof JstObjectLiteralType) {
					collectTypeFromProperties(oType.getProperties(), typeSet, null);
					m_hasOlType = true;
				}
				if (oType instanceof JstFunctionRefType) {
					m_needsIJsJavaProxy = true;
					collectTypeFromMethod(((JstFunctionRefType)oType).getMethodRef(), typeSet, null);
					m_hasFnType = true;
				}
			}
			collectTypeFromOTypes(m_clzType.getOTypes(), typeSet);
		}
				
		collectTypesFromEmbeded(m_clzType.getEmbededTypes(), typeSet);
		addTypeImport(typeSet);
	}
	
	

	private boolean hasFunctionArg(List<JstArg> args) {
		if(args.size()==0){
			return false;
		}
		for(JstArg a: args){
			if(a.getType() instanceof JstFunctionRefType){
				return true;
			}
		}
		
		return false;
	}

	private void collectFrameworkImports(IJstType type) {
		m_frameworkImports.add(IClassR.AJsProxyName);
		if (type.isClass() || type.isEnum()) {			
			IJstType extend = type.getExtend();
			if (extend == null
				|| isObject(extend) 
				|| isEnum(extend)) {
				if (!m_frameworkImports.contains(IClassR.NativeJsProxyName)) {
					m_frameworkImports.add(IClassR.NativeJsProxyName);
				}
			}
			if (!m_frameworkImports.contains(IClassR.ScriptableName)) {
				m_frameworkImports.add(IClassR.ScriptableName);
				m_frameworkImports.add(IClassR.AExcludeName);
			}
		}
		else if (type.isInterface() && type.getExtend() == null) {
			if (!m_frameworkImports.contains(IClassR.IJsJavaProxyName)) {
				m_frameworkImports.add(IClassR.IJsJavaProxyName);
			}
		}
	}
	
	private void collectTypesFromEmbeded(List<? extends IJstType> embededTypes, Set<IJstType> typeSet) {
		if (!embededTypes.isEmpty()) {
			for (IJstType embededType : embededTypes) {
				m_currentDefinedTypes.add(embededType.getName());
				if (!embededType.isMixin() && embededType.getModifiers().isPublic()) {
					collectFrameworkImports(embededType);
					m_customTypeMgr.addToSimpleNameMap
						(new TypeMeta(embededType.getName(), m_clzType.getSimpleName(), false));				
					addCustomTypeImport(embededType.getExtends());
					addCustomTypeImport(embededType.getSatisfies());
					InnerTypeInfo info = new InnerTypeInfo(
							embededType,
							m_javaTypeMgr,
							m_customTypeMgr,
							m_jsNativeTypeMgr,
							m_currentDefinedTypes,
							m_innerTypeInfoMap);
					
					collectTypeFromProperties(embededType.getProperties(), typeSet, info);
					
					collectTypeFromMethod(embededType.getConstructor(), typeSet, info);
					for (IJstMethod method : embededType.getMethods()) {
						collectTypeFromMethod(method, typeSet, info);
						if (!method.isOType()) {
							m_hasNonFnRef = true;
						}
					}
					
					collectTypesFromEmbeded(embededType.getEmbededTypes(), typeSet);
				}				
			}
		}
	}
	
	private void collectTypeFromOTypes(List<IJstOType> types, Set<IJstType> typeSet) {
		for (IJstOType otype : types) {
			if (otype instanceof JstObjectLiteralType) {
				for (IJstProperty prop : ((IJstOType)otype).getProperties()) {
					collectType(prop.getType(), typeSet);
				}
				//add for method
			}
		}
		
	}
	private void writePkg(){
		//http://quickbugstage.arch.ebay.com/show_bug.cgi?id=3014
		if (m_clzType.getPackage() != null
				&& m_clzType.getPackage().getName().length() > 0) {
			getWriter().append(PACKAGE).append(m_clzType.getPackage().getName()).append(SEMI_COLON);
			writeNewline();
		}
	}
	
	private void writeImports() {
		//import java type
		writeImports(m_javaTypeMgr.getMetaItr());
		
		Set<String> writtenImports = writeFrameworkImports();
		
		writeImports(m_jsNativeTypeMgr.getMetaItr(), writtenImports);
		
		writeImports(m_customTypeMgr.getMetaItr());
	}
	
	private Set<String> writeFrameworkImports() {
		Set<String> writtenImports = new HashSet<String>();
		for (String importType : m_frameworkImports) {
			writeImport(importType, writtenImports);
		}
		if (m_clzType.isClass()) {			
			if (hasNonAbstractMethod()) {
				if (m_hasNonFnRef) {
					writeImport(IClassR.INativeJsFuncProxy, writtenImports);
				}
				writeImport(IClassR.NativeJsFuncProxy, writtenImports);
			}
			writeImport(IClassR.NativeJsTypeRef, writtenImports);
			writeImport(IClassR.AJavaOnlyName, writtenImports);
		}
		if (m_hasJsTypeRef) {
			writeImport(IClassR.NativeJsTypeRef, writtenImports);
		}
		if (!m_clzType.isOType() && hasProperty()) {
			writeImport(IClassR.APropertyName, writtenImports);
		}
		
		if (m_clzType.isOType()) {
			writeImport(IClassR.AIsOTypeAnno, writtenImports);
		}
		if (m_hasOlType) {
			writeImport(IClassR.OlName, writtenImports);
			writeImport(IClassR.NativeOlKeysAnno, writtenImports);
		}
		if (m_hasFnType) {
			writeImport(IClassR.FuncRef, writtenImports);
			writeImport(IClassR.INativeJsFuncProxy, writtenImports);
			writeImport(IClassR.NativeJsHelper, writtenImports);
		}
		if (hasConstantInInterface()) {
			writeImport(IClassR.NativeJsHelper, writtenImports);
		}
		
		if ((m_needsIJsJavaProxy || m_hasFnType) && !m_frameworkImports.contains(IClassR.IJsJavaProxyName)) {
			writeImport(IClassR.IJsJavaProxyName, writtenImports);
		}
		return writtenImports;
	}
	
	private void writeImport(String importType, Set<String> writtenImports) {
		if (writtenImports.contains(importType)) {
			return;
		}
		writeImport(importType);
		writtenImports.add(importType);
	}
	
	private void writeImports(Iterator<TypeMeta> itr) {
		while (itr.hasNext()) {
			TypeMeta meta = itr.next();
			if (!meta.m_useFullName) {
				writeImport(meta.m_fullName);
			}
		}
	}
	
	private void writeImports(Iterator<TypeMeta> itr, Set<String> exclude) {
		while (itr.hasNext()) {
			TypeMeta meta = itr.next();
			if (!meta.m_useFullName && !exclude.contains(meta.m_fullName)) {
				writeImport(meta.m_fullName);
			}
		}
	}
	
	private void writeImport(final String fullName) {
		getWriter().append(IMPORT).append(getType(fullName)).append(SEMI_COLON);
		writeNewline();
	}
	
	private String getType(final String fullName) {
		String[] names = fullName.split("\\.");
		if (names.length==2) {//check for otype
			IJstType type = m_clzType.getInactiveImport(names[0]);
			if (type!=null && type.getOType(names[1])!=null) {
				return type.getPackage().getName() + "." + fullName;
			}
		}
		return fullName;
	}
	private void writeClassLevelComments() {
		PrintWriter writer = getWriter();
		writer.append("//NativeJsProxy for " + m_clzType.getName() + ".js");
		writeNewline();
		writeCodeGenMarker(NativeJsProxyGenerator.class);
		writeNewline();
		writer.append("@").append(IClassR.AJsProxySimpleName);
		writeNewline();
	}
	
	private void writeOTypeDef() {
		writeNewlineAndIndent();
		List<IJstOType> oTypes = m_clzType.getOTypes();
		for (IJstOType oType : oTypes) {
			if (oType instanceof JstObjectLiteralType) {
				writeOlType((JstObjectLiteralType)oType);
			} else if (oType instanceof JstFunctionRefType) {
				writeOlType((JstFunctionRefType)oType);
			}
			writeNewlineAndIndent();
		}
		writeNewline();
	}
	
	private void writeOlType(JstFunctionRefType type) {
		PrintWriter writer = getWriter();
		boolean isPublic = type.getModifiers().isPublic();
		if (isPublic) {
			writer.append("public ");
		}
		writer.append("static class ").append(type.getSimpleName())
				.append("<T extends ").append(
						IClassR.IJsJavaProxySimpleName).append(
						"> extends ").append(IClassR.FuncRefSimpleName).append("<T> {");
		indent();
		writeNewlineAndIndent();
		writer.append("public ").append(type.getSimpleName()).append("(").append(IClassR.INativeJsFuncProxySimpleName)
		.append("<T> proxy) {");
		indent();
		writeNewlineAndIndent();
		writer.append("super(proxy);");
		outdent();
		writeNewlineAndIndent();
		writer.append("}");
		writeNewlineAndIndent();
		IJstMethod meth = type.getMethodRef();
		String rtnType = getWrapperType(getTypeName(meth.getRtnType()),false);
		boolean isTypeRef = meth.getRtnType() instanceof IJstRefType;
		if (isTypeRef) {
			writer.append("@SuppressWarnings(\"unchecked\")");
			writeNewlineAndIndent();
		}
		writer.append("public ").append(rtnType).append(" call(T thisObj");
		List<JstArg>args = meth.getArgs();
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (JstArg arg : args) {
			writer.append(COMMA).append(
					getWrapperType(getTypeName(arg.getType()), false)).append(
					SPACE).append(arg.getName()==null?DEFAULT_PARAM_PREFIX + i : arg.getName());
			sb.append(COMMA).append(arg.getName()==null?DEFAULT_PARAM_PREFIX + i : arg.getName());
			i++;
		}
		writer.append(") {");
		indent();
//		String rtnType = getWrapperType(getTypeName(meth.getRtnType()),false);
		boolean isVoid = ("void".equals(rtnType));
		writeNewlineAndIndent();
		
		if (isVoid) {
			rtnType = "Void";
		} else {
			writer.append("return ");
		}
		writer
				.append(IClassR.NativeJsHelperSimpleName)
				.append(".convert(");
		if (isTypeRef) {
			writer.append(IClassR.NativeJsTypeRefSimpleName);
		} else {
			writer.append(rtnType);
		}
		writer.append(".class").append(COMMA).append("super.call(thisObj").append(sb).append("));");
		outdent();
		writeNewlineAndIndent();
		writer.append("}");
		outdent();
		writeNewlineAndIndent();
		writer.append("}");
	}
	
	private ObjectLiteralGenStrings getOlGeneratedStrings(IJstMethod constructor) {
		ObjectLiteralGenStrings o = new ObjectLiteralGenStrings();
		o.annoSig.append("@").append(IClassR.NativeOlKeysAnnoSimpleName).append("(values={");
		boolean first = true;
		for (JstArg prop : constructor.getArgs()) {
			String name = prop.getName();
			String typeName = getTypeName(prop.getType());
			if (!first) {
				o.paramSig.append(COMMA);
				o.superSig.append(COMMA);
				o.newSig.append(COMMA);
				o.annoSig.append(COMMA);
			}
			o.paramSig.append(typeName).append(SPACE).append(name);
			o.superSig.append("\"").append(name).append("\"").append(COMMA).append(name);
			o.newSig.append(name);
			o.annoSig.append("\"").append(name).append("\"");
			first = false;
		}
		o.annoSig.append("})");
		
		return o;
	}
	private void writeOlType(JstObjectLiteralType type) {
		/*StringBuilder paramSig = new StringBuilder();
		StringBuilder superSig = new StringBuilder();
		StringBuilder newSig = new StringBuilder();
		StringBuilder annoSig = new StringBuilder();
		annoSig.append("@").append(IClassR.NativeOlKeysAnnoSimpleName).append("(values={");
		boolean first = true;
		for (IJstProperty prop : type.getProperties()) {
			String name = prop.getName().getName();
			String typeName = getTypeName(prop.getType());
			if (!first) {
				paramSig.append(COMMA);
				superSig.append(COMMA);
				newSig.append(COMMA);
				annoSig.append(COMMA);
			}
			paramSig.append(typeName).append(SPACE).append(name);
			superSig.append("\"").append(name).append("\"").append(COMMA).append(name);
			newSig.append(name);
			annoSig.append("\"").append(name).append("\"");
			first = false;
		}
		annoSig.append("})");*/
		boolean isPublic = type.getModifiers().isPublic();
		PrintWriter writer = getWriter();
		writeNewlineAndIndent();
		if (isPublic) {
			writer.append("public ");
		}
		String otypeName = type.getSimpleName();
		writer.append("static class ").append(otypeName)
				.append(" extends ").append(
						IClassR.OlSimpleName).append(" { ");

		generateOLContructors(otypeName, getOlGeneratedStrings(type.getConstructor()));
		if (type.hasOptionalFields()) {
			for (IJstMethod overloaded : type.getConstructor().getOverloaded()) {
				generateOLContructors(otypeName, getOlGeneratedStrings(overloaded));
			}
		}
		writer.append("}");
	}

	private void generateOLContructors(String otypeName, ObjectLiteralGenStrings genStrings) {
		PrintWriter writer = getWriter();
		indent();
		writeNewlineAndIndent();
		writer.append("private ").append(otypeName).append("(")
				.append(genStrings.paramSig).append(") {");
		indent();
		writeNewlineAndIndent();
		writer.append("super(").append(genStrings.superSig).append(");");
		outdent();
		writeNewlineAndIndent();
		writer.append("}");
		writeNewlineAndIndent();
		writer.append(genStrings.annoSig);
		writeNewlineAndIndent();
		writer.append("public static ").append(otypeName).append(SPACE).append(
				"obj(").append(genStrings.paramSig).append(") {");
		indent();
		writeNewlineAndIndent();
		writer.append("return new ").append(otypeName).append("(")
				.append(genStrings.newSig).append(");");
		outdent();
		writeNewlineAndIndent();
		writer.append("}");
		outdent();
		writeNewlineAndIndent();
	}
	
	private void writeClassDefinition() {
		if (m_innerTypeInfo != null) {
			writeIndent();
		}
		PrintWriter writer = getWriter();
		if (m_clzType.isOType()) {
			writer.append("@").append(IClassR.AIsOTypeAnnoSimpleName);
			writeNewlineAndIndent();
		}
		writer.append("public ");
		if (m_clzType.isEmbededType() && m_clzType.getModifiers().isStatic()) {
			writer.append("static ");
		}
		if (m_clzType.isInterface()) {
			writer.append("interface ");
		} else if (m_clzType.isOType()) {
			writer.append("abstract class ");
		} else {
			if (m_clzType.getModifiers().isFinal()) {
				writer.append("final ");
			}
			if (m_clzType.getModifiers().isAbstract()) {
				writer.append("abstract ");
			}
			writer.append("class ");
		}
		
		writer.append(m_clzType.getSimpleName());
		if (!m_clzType.getParamNames().isEmpty()) {
			writer.append(((JstType)m_clzType).getParamsDecoration());		
		}
		
		if (m_clzType.isInterface()) {
			List<? extends IJstType> extendedTypes = m_clzType.getExtends();
			if (!extendedTypes.isEmpty()) {
				writer.append(" extends ");
				for (int i = 0 ; i < extendedTypes.size(); i++) {
					if (i > 0) {
						writer.append(COMMA).append(" ");
					}
					IJstType extType = extendedTypes.get(i);
					writer.append(extType.getSimpleName());
					if (extType instanceof JstTypeWithArgs) {
						JstTypeWithArgs jstTypeWithArgs = ((JstTypeWithArgs)extType);
						if (!jstTypeWithArgs.getArgTypes().isEmpty()) {
							writer.append(jstTypeWithArgs.getArgsDecoration());
						}
					} else {
						if (!extType.getParamNames().isEmpty()) {
							writer.append(((JstType)extType).getParamsDecoration());
						}
					}
				}
			}
			else {
				writer.append(" extends ").append(IClassR.IJsJavaProxySimpleName);
			}
		} else if (m_clzType.isOType()) {
			
		}else if (!m_clzType.isMixin()) {
			IJstType extend = m_clzType.getExtend();
			if (extend != null && !isObject(extend) && !isEnum(extend)) {
				writer.append(" extends ");
				writer.append(extend.getSimpleName());
				writer.append(GeneratorJstHelper.getArgsDecoration(extend));
			} else {
				writer.append(" extends ").append(IClassR.NativeJsProxySimpleName);
			}
		}
		
		List<? extends IJstType> interfaceTypes = m_clzType.getSatisfies();
		if (!interfaceTypes.isEmpty()) {
			boolean addComma = false;
			StringBuilder buf = new StringBuilder();
			for (int i = 0 ; i < interfaceTypes.size(); i++) {
				IJstType itfType = interfaceTypes.get(i);
				if (addComma) {
					buf.append(COMMA).append(" ");
				}
				buf.append(itfType.getSimpleName());
				if (itfType instanceof JstTypeWithArgs) {
					buf.append(GeneratorJstHelper.getArgsDecoration(itfType));		
				}
				addComma = true;
			}
			if (buf.length() > 0) {
				writer.append(" implements ").append(buf.toString());
			}
		}
	}
	
	private void writeConstructors() {
		if (m_clzType.isInterface()) {
			return;
		}		
		
		if (m_constructors.isEmpty()) {
			//create default constructor
			writeConstructor(m_clzType.getSimpleName(), "", "");
			return;
		}
		List<IJstMethod> writtenConstructors = new ArrayList<IJstMethod>();
		for (MethodMeta meta : m_constructors) {
			if(!writtenConstructors.contains(meta.m_method)){
				for (List<SimpleParam> paramList : meta.m_argListPermutation) {
					writeConstructorForNativeJsProxy(paramList);
				}
				writtenConstructors.add(meta.m_method);
			}
		}
		writtenConstructors = null;
	}
	
	private void writeConstructorForNativeJsProxy(List<SimpleParam> paramList) {
		StringBuilder params = new StringBuilder();
		StringBuilder args = new StringBuilder();
		collectParamAndArgList(paramList, params, args);
		writeConstructor
			(m_clzType.getSimpleName(), params.toString(), args.toString());
	}

	private void writeConstants() {
		if (m_constants.isEmpty()) {
			return;
		}
		PrintWriter writer = getWriter();
		boolean isInterface = m_clzType.isInterface();
		for (IJstProperty property : m_constants) {
			writeNewline();
			writeNewlineAndIndent();			
			if (!isInterface) {
				writer.append("public static final ");
			}
			String typeName = getTypeName(property.getType(), true);
			String typeNameWithArgs = getTypeName(property.getType());
			
			String constantName = property.getName().getName();
							
			writer.append(typeNameWithArgs).append(" ")
				.append(constantName).append(" = ");
			if (m_clzType.isInterface()) {
				writer.append(IClassR.NativeJsHelperSimpleName).append(".");
				writer.append("getStaticProperty(");
				writer.append(m_clzType.getSimpleName()).append(".class, ");
				writer.append("\"").append(constantName).append("\", ")
				.append(typeName).append(".class);");
			} else {
				writer.append("getStaticProperty(");
				writer.append("\"").append(m_clzType.getName()).append("\", ");
				writer.append("\"").append(constantName).append("\", ")
					.append(typeName).append(".class);");
			}
		}
	}

	private static final String PROP_MODIFIER = "public ";
	private static final String STATIC_PROP_MODIFIER = "public static ";
	private static final String PROP_GETTER = "return getProperty(";
	private static final String STATIC_PROP_GETTER = "return getStaticProperty(";
	private static final String PROP_SETTER = "setProperty(";
	private static final String STATIC_PROP_SETTER = "setStaticProperty(";
	
	private void writeProperties(boolean isStatic) {
		List<IJstProperty> properties = isStatic ? 
			m_staticProperties : m_instanceProperties;
		
		String modifier = isStatic ? STATIC_PROP_MODIFIER : PROP_MODIFIER;
		String getter = isStatic ? STATIC_PROP_GETTER : PROP_GETTER;
		String setter = isStatic ? STATIC_PROP_SETTER : PROP_SETTER;
		
		if (m_clzType.isInterface() || properties.isEmpty()) {
			return;
		}
		PrintWriter writer = getWriter();
		for (IJstProperty property : properties) {						
			String typeName = getTypeName(property.getType());
			String propertyName = property.getName().getName();
			boolean needEscape = needEscape(propertyName);
			boolean isTypeRef = property.getType() instanceof IJstRefType;
			//getter
			writeNewline();
			writeNewlineAndIndent();
			writer.append("@").append(IClassR.APropertySimpleName);
			if (needEscape) {
				writer.append("(name=\"").append(propertyName).append("\")");
			}
			if (isTypeRef) {
				writeNewlineAndIndent();
				writer.append("@SuppressWarnings(\"unchecked\")");
			}
			writeNewlineAndIndent();
			writer.append(modifier)
				.append(typeName)
				.append(" ")
				.append(needEscape ? escape(propertyName) : propertyName)
				.append("() {");
			indent();
			writeNewlineAndIndent();
			writer.append(getter);
			if(isStatic) {
				writer.append("\"").append(m_clzType.getName()).append("\", ");
			}
			//if (needEscape) {
			writer.append("\"").append(propertyName).append("\", ");
			//}
			if (isTypeRef) {
				writer.append(IClassR.NativeJsTypeRefSimpleName).append(".class);");
			} else {
				writer.append(getClassName(typeName)).append(".class);");
			}
			outdent();
			writeNewlineAndIndent();
			writer.append("}");
			
			//setter
			writeNewline();
			writeNewlineAndIndent();
			writer.append("@").append(IClassR.APropertySimpleName);
			if (needEscape) {
				writer.append("(name=\"").append(propertyName).append("\")");
			}
			
			writeNewlineAndIndent();
			writer.append(modifier)
				.append("void ")
				.append(needEscape ? escape(propertyName) : propertyName)
				.append("(").append(typeName)
				.append(" value) {");
			indent();
			writeNewlineAndIndent();
			writer.append(setter);
			if(isStatic) {
				writer.append("\"").append(m_clzType.getName()).append("\", ");
			}
			//if (needEscape) {
			writer.append("\"").append(propertyName).append("\", ");
			//}
			writer.append("value);");
			outdent();
			writeNewlineAndIndent();
			writer.append("}");
		}
	}
	
	private void writeMethods(MethodMeta meta) {
		if (meta.m_method instanceof ISynthesized) {
        	return;
        }
		for (List<SimpleParam> paramList : meta.m_argListPermutation) {
			writeMethod(meta.m_method, paramList);
		}
	}
	
	private void writeMethod(IJstMethod method, List<SimpleParam> paramList) {
		StringBuilder methodDeclBegin= new StringBuilder();
		if (!m_clzType.isInterface()) {
			methodDeclBegin.append("public ");
		}
		if (method.isAbstract()) {
			methodDeclBegin.append("abstract ");
		}
		if (method.isStatic()){
			methodDeclBegin.append("static ");
		}
		if (method.isFinal()) {
			methodDeclBegin.append("final ");
		}
		if (!method.getParamNames().isEmpty()) {
			methodDeclBegin.append(method.getParamsDecoration()).append(" ");			
		}		
		
		IJstType rtnType = method.getRtnType();
		String rtnTypeName = "void";
		String rtnTypeWrapperName = null;
		boolean isTypeRef = method.getRtnType() instanceof IJstRefType;
		boolean suppress = isTypeRef;
		if (rtnType != null && !"void".equals(rtnType.getName())) {
			rtnTypeName = getTypeName(rtnType);
			if (isTypeRef) {
				rtnTypeName = IClassR.NativeJsTypeRefSimpleName + "<" + rtnTypeName + ">";
				rtnTypeWrapperName = IClassR.NativeJsTypeRefSimpleName;
			} else {
				String rtn = rtnTypeName;
				if (rtnType instanceof JstTypeWithArgs) {
					suppress = true;
					rtn = getTypeName(rtnType,true);
				}
				rtnTypeWrapperName = getWrapperType(rtn, false);
			}
		}
		methodDeclBegin.append(rtnTypeName).append(" ");
		
		String methodName = method.getOriginalName();
		boolean escaped = false;
		if (needEscape(methodName)) {
			escaped = true;
			methodDeclBegin.append(escape(methodName));
		}
		else {
			methodDeclBegin.append(methodName);
		}
		
		StringBuilder params = new StringBuilder();
		StringBuilder args = new StringBuilder();
		collectParamAndArgList(paramList, params, args);
		
		writeMethod(
			methodDeclBegin.toString(),
			rtnTypeWrapperName,
			params.toString(),
			args.toString(),
			method.isStatic(),
			m_clzType.isInterface() || method.isAbstract(),
			suppress,
			escaped,
			methodName);
	}
	
	private void collectParamAndArgList(
		List<SimpleParam> paramList,
		StringBuilder params,
		StringBuilder args) {
		int argSize = paramList.size();
		for (int i = 0; i < argSize; i++) {
			SimpleParam param = paramList.get(i);
			if (i > 0) {
				params.append(", ");
				args.append(", ");
			}
			
			String pType = getTypeName(param.m_type);
//			if (param.m_isTypeRef) {
//				pType = IClassR.NativeJsTypeRefSimpleName + "<" + pType + ">";
//			}
			params.append(pType);
			if (param.m_type instanceof JstFunctionRefType) {
				params.append("<? extends IJsJavaProxy>");
			}
			if (param.m_arg.isVariable()) {
				params.append("...");
			}
			String pName = param.m_arg.getName();
			//If no name available then create one
			if (pName == null) {
				pName = DEFAULT_PARAM_PREFIX + i;
			}
			params.append(" ").append(pName);
			args.append(pName);
		}
	}
	
	private void writeFrameworkConstructors(String name) {
		if (m_clzType.isInterface()) {
			return;
		}				
		PrintWriter writer = getWriter();
		writeNewline();
		writeNewlineAndIndent();
		writer.append("/** for framework use only */");
		writeNewlineAndIndent();
		writer.append("@").append(IClassR.AExcludeSimpleName);
		writeNewlineAndIndent();
		writer.append("public ").append(name)
			.append("(").append(IClassR.ScriptableSimpleName).append(" nativeObj){");
		indent();
		writeNewlineAndIndent();
		writer.append("super(nativeObj);");
		outdent();
		writeNewlineAndIndent();
		writer.append("}");
		
		if (!hasVarArgConstructor()) {
			//protected constructor
			writeNewline();		
			writeNewlineAndIndent();
			writer.append("/** internal use only */");
			writeNewlineAndIndent();
			writer.append("protected ").append(name)
				.append("(Object ...args){");
			indent();
			writeNewlineAndIndent();
			writer.append("super(args);");
			outdent();
			writeNewlineAndIndent();
			writer.append("}");
		}
	}
	
	private boolean hasVarArgConstructor() {
		if (m_constructors.isEmpty()) {
			return false;
		}
		for (MethodMeta meta : m_constructors) {
			for (List<SimpleParam> paramList : meta.m_argListPermutation) {
				if (paramList.size() != 1) {
					continue;
				}
				SimpleParam param = paramList.get(0);
				JstArg arg = param.m_arg;
				if (arg.isVariable() && "Object".equals(arg.getType().getName())) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void writeConstructor(String name, String paramList, String argList) {
		writeNewline();
		writeNewlineAndIndent();
		PrintWriter writer = getWriter();
		writer.append("public ").append(name)
			.append("(").append(paramList).append(") {");
		indent();
		writeNewlineAndIndent();
		writer.append("super(").append(argList).append(");");
		outdent();
		writeNewlineAndIndent();
		writer.append("}");
	}
	
	private void writeMethod(
		String methodDecl,
		String returnTypeWrapper,
		String paramList,
		String argList,
		boolean isStatic,
		boolean isAbstruct,
		boolean addSuppress,
		boolean escaped,
		String methodName) {
		
		writeNewline();
		writeNewlineAndIndent();
		PrintWriter writer = getWriter();
		if (escaped) {
			writer.append("@").append(IClassR.ARenameName)
				.append("(name=\"").append(methodName).append("\")");
			writeNewlineAndIndent();
		}
		if (addSuppress) {
			writer.append("@SuppressWarnings(\"unchecked\")");
			writeNewlineAndIndent();
		}
		writer.append(methodDecl)
			.append("(").append(paramList).append(")");
		if (isAbstruct) {
			writer.append(";");
			return;
		}
		
		boolean returnsTemplateType = m_clzType.getParamNames().contains(returnTypeWrapper);
		writer.append(" {");
		indent();
		writeNewlineAndIndent();
		if (returnTypeWrapper != null) {
			writer.append("return ");
		}
		if (returnsTemplateType) {
			writer.append("(").append(returnTypeWrapper).append(")");
		}
		if (isStatic) {
			writer.append("callStaticWithName(")
			  .append("\"").append(m_clzType.getName()).append("\", ")
			  .append("\"").append(methodName).append("\"");
			if (returnTypeWrapper != null || (argList != null && argList.length() != 0)) {
				writer.append(", ");
			}
		}
		else {
			writer.append("callWithName(\"").append(methodName).append("\"");
			if (returnTypeWrapper != null || (argList != null && argList.length() != 0)) {
				writer.append(", ");
			}
		}
		if (returnTypeWrapper != null) {
			writer.append((returnsTemplateType)?"Object":returnTypeWrapper).append(".class");
			if (argList != null && argList.length() != 0) {
				writer.append(", ");
			}
		}
		writer.append(argList).append(");");
		outdent();
		writeNewlineAndIndent();
		writer.append("}");
	}
	
	private void writeTypeRef() {
		if (m_innerTypeInfo != null && !m_clzType.getModifiers().isStatic()) {
			return; //for non-static inner class, there can't be any static property
		}
		writeNewline();
		writeNewlineAndIndent();
		PrintWriter writer = getWriter();
		writer.append("@").append(IClassR.AJavaOnlySimpleName);
		writeNewlineAndIndent();
		writer.append("public static final ")
			.append(IClassR.NativeJsTypeRefSimpleName)
			.append("<").append(m_clzType.getSimpleName()).append("> ")
			.append("prototype = NativeJsTypeRef.get(")
			.append(m_clzType.getSimpleName()).append(".class);");
	}
	
	private void writeStaticFuncProxy(MethodMeta meta, Set<String> methodNames) {
		IJstMethod method = meta.m_method;
		if (method instanceof ISynthesized) {
			return;
		}
		String typeName = null;
		if (method.isOType()) {
			typeName = ((JstMethod)method).getOType().getName();
		}
		String methodName = method.getName().getName();
		if (methodNames.contains(methodName)) {
			return;
		}
		if (method.isAbstract() || !method.isPublic()) {
			return;
		}
		
		StringBuilder nativeRef = new StringBuilder();
		nativeRef.append("<")
		.append(IClassR.NativeJsTypeRefSimpleName)
		.append("<").append(m_clzType.getSimpleName()).append(">")
		.append(">");
		
		methodNames.add(methodName);
		writeNewline();
		writeNewlineAndIndent();
		PrintWriter writer = getWriter();
		writer.append("public static final ");
		if (typeName!=null) {
			writer.append(typeName);
		} else {
			writer.append(IClassR.INativeJsFuncProxySimpleName);
		}
		writer.append(nativeRef).append(SPACE)
			.append(methodName).append(" = ");
		if (typeName!=null) {
			writer.append("new ").append(typeName).append(nativeRef).append("(");
		}
		writer.append(IClassR.NativeJsFuncProxySimpleName)
			.append(".create(prototype, \"").append(methodName).append("\")");
		if (typeName!=null) {
			writer.append(")");
		}
		writer.append(";");
	}
	
	private void writeInstanceFuncProxy(MethodMeta meta, Set<String> methodNames, String clzName) {
		IJstMethod method = meta.m_method;
		if (method instanceof JstProxyMethod) {
			method = ((JstProxyMethod)method).getTargetMethod();
		}
		String methodName = method.getName().getName();
		String typeName = null;
		if (method.isOType()) {
			typeName = ((JstMethod) method).getOType().getName() + "<"
					+ m_clzType.getSimpleName() + ">";
		}
		if (methodNames.contains(methodName)) {
			return;
		}
		if (method.isAbstract() || !method.isPublic()) {
			return;
		}
		
		StringBuilder nativeRef = new StringBuilder();
		nativeRef.append("<")
		.append(typeName)
		.append("<").append(m_clzType.getSimpleName()).append(">")
		.append(">");
		
		methodNames.add(methodName);
		writeNewline();
		writeNewlineAndIndent();
		PrintWriter writer = getWriter();
		writer.append("public final ");
		if (typeName!=null) {
			writer.append(typeName);
		} else {
			writer.append(IClassR.INativeJsFuncProxySimpleName)
				.append("<").append(clzName).append(">");
		}
		writer.append(SPACE).append(methodName).append(" = ");
		if (typeName!=null) {
			writer.append("new ").append(typeName).append("(");
		}
		writer.append(IClassR.NativeJsFuncProxySimpleName)
			.append(".create(this, \"").append(methodName).append("\")");
		if (typeName!=null) {
			writer.append(")");
		}
		writer.append(";");
	}

	private NativeJsProxyGenerator writeNewlineAndIndent(){
		super.writeNewline().writeIndent();
		return this;
	}
	
	private static class TypeMetaMgr {
		private Map<String, TypeMeta> m_fullNameMap = new LinkedHashMap<String, TypeMeta>();
		private Set<String> m_simpleNames = null;
		
		TypeMetaMgr(Set<String> simpleNames) {
			m_simpleNames = simpleNames;
		}
		
		TypeMeta get(String fullName) {
			return m_fullNameMap.get(fullName);
		}
		
		void add(String fullName, String simpleName, boolean usedFullName) {
			if (!m_fullNameMap.containsKey(fullName)) {
				TypeMeta meta = new TypeMeta(fullName, simpleName, usedFullName);
				add(meta);
			}
		}
		
		private void add(TypeMeta type) {
			if (m_simpleNames.contains(type.m_simpleName)) {
				//simpleName conflict
				type.m_useFullName = true;
			}
			else {
				m_simpleNames.add(type.m_simpleName);
			}
			m_fullNameMap.put(type.m_fullName, type);
		}
		
		void addToSimpleNameMap(TypeMeta type) {
			m_simpleNames.add(type.m_simpleName);
		}
		
		Iterator<TypeMeta> getMetaItr() {
			return m_fullNameMap.values().iterator();
		}
	}
	
	private static class TypeMeta {
		String m_fullName;
		String m_simpleName;
		boolean m_useFullName = false;
		
		TypeMeta(String fullName, String simpleName, boolean usedFullName) {
			m_fullName = fullName;
			m_simpleName = simpleName;
			if (usedFullName || simpleName.equals(fullName)) {
				m_useFullName = true;
			}
		}
	}
	
	private static class MethodMeta {
		IJstMethod m_method;
		List<List<SimpleParam>> m_argListPermutation;
		boolean m_isOverload;
		@SuppressWarnings("unchecked")
		MethodMeta(final IJstMethod method, final List<Stack<SimpleParam>> params) {
			m_method = method;
			m_argListPermutation = new ArrayList<List<SimpleParam>>(params.size());
			for (Stack<SimpleParam> paramStack : params) {
				List<SimpleParam> argList = new ArrayList<SimpleParam>(paramStack.size());
				m_argListPermutation.add(argList);
				while (!paramStack.isEmpty()) {
					argList.add(paramStack.pop());
				}
			}
			List<JstArg> args = method.getArgs();
//			int size = args.size();
//			int firstOptional = size;
//			for (int i = size - 1; i >= 0; i--) {
//				if (args.get(i).isOptional()) {
//					firstOptional = i;
//				}
//				else {
//					break;
//				}
//			}
//			if (firstOptional < size) {
//				//temp list for deduping
//				List<List<SimpleParam>> newlyAddedPermutations = new ArrayList<List<SimpleParam>>();
//				
//				//create permutations for optional params
//				int permutationSize = m_argListPermutation.size();
//				for (int i = 0; i < permutationSize; i++) {
//					List<SimpleParam> argList = m_argListPermutation.get(i);
//					for (int j = size - 1; j >= firstOptional; j--) {
//						//create new list by remove the last optional param
//						argList = (List<SimpleParam>)((ArrayList<SimpleParam>)argList).clone();
//						argList.remove(j);
//						if (!isDuplicate(newlyAddedPermutations, argList)) {
//							m_argListPermutation.add(argList);
//							newlyAddedPermutations.add(argList);
//						}					
//					}
//				}
//			}
		}
	}
	
	private static class SimpleParam {
		JstArg m_arg;
		IJstType m_type;		
		boolean m_isOptional = false;
		//commented out as it is not used by anything.
//		boolean m_isTypeRef = false;

		SimpleParam(JstArg arg, IJstType type, boolean isOptional, boolean isTypeRef) {
			m_arg = arg;
			m_type = type;			
			m_isOptional = isOptional;
//			m_isTypeRef = isTypeRef;
		}
		@Override
		public String toString() {
			return m_type.getName() + (m_isOptional ? "?" : "") + " " + m_arg.getName();
		}
	}
	
	private static boolean isDuplicate(List<List<SimpleParam>> existedLists, List<SimpleParam> theList) {
		for (List<SimpleParam> existedOne : existedLists) {
			if (existedOne.size() != theList.size()) {
				continue;
			}
			boolean isDuplicate = true;
			for (int i = 0; i < theList.size(); i++) {
				if (theList.get(i) != existedOne.get(i)) {
					isDuplicate = false;
					break;
				}
			}
			if (isDuplicate) {
				return true;
			}
		}
		return false;
	}
	
	private void addCustomTypeImport(List<? extends IJstType> types) {
		if (types == null) {
			return;
		}
		for (IJstType type: types){
			addCustomTypeImport(type);
		}
	}
	
	private void addCustomTypeImport(IJstType type) {
		if (m_currentDefinedTypes.contains(type.getName())) {
			return;
		}
		if (isObject(type) || isEnum(type) || isVjoClass(type)) {
			return;
		}
		String name = type.getName();
		boolean usedFullName = m_clzType.getImportsMap().containsKey(name);
		if (name.startsWith(VJOX_JAVA_LANG)) {
			usedFullName = true;
		}
		m_customTypeMgr.add(name, type.getSimpleName(), usedFullName);
	}
	
	private void addTypeImport(Set<IJstType> typeSet) {	
		for (IJstType type : typeSet) {
			if (isObject(type)) {
				continue;
			}
			if (m_clzType.isOType() && m_clzType.equals(type.getParentNode())) {
				continue;
			}
			String name = type.getName();

			if (isPrimitiveType(name)) {
				continue;
			}
			else if (isJavaType(name)) {
				if (!isJavaLangType(name)) {
					if (name.startsWith(VJOX)) {
						name = name.substring(4);
					}
					m_javaTypeMgr.add(name, type.getSimpleName(), false);
				}
			}
			else {
				Class<?> clz = JsNativeMeta.getClass(name);
				if (clz != null) {
					String typeName = clz.getName();
					String simpleName = clz.getSimpleName();
					String proxyName = getNativeProxyType(typeName);
					String javaForNative = DataTypeHelper.getJavaTypeNameForNative(typeName);// getJavaForNative(typeName);
					if (javaForNative != null) {
						if (!isJavaLangType(javaForNative)) {
							m_javaTypeMgr.add(javaForNative, simpleName, false);
						}
						continue;
					}
					if (proxyName != null) {
						typeName = proxyName;
						simpleName = proxyName.substring(proxyName.lastIndexOf(".") + 1);
						if (typeName == IClassR.INativeJsFuncProxy) {
							simpleName = simpleName + "<?>";
						}
					}
					m_jsNativeTypeMgr.add(typeName, simpleName, false);
				}
				else {
					addCustomTypeImport(type);
				}
			}			
		}		
	}
	
	private void collectTypeFromProperties(
		List< IJstProperty> properties, Set<IJstType> typeSet, InnerTypeInfo innerTypeInfo) {
		IJstType type = innerTypeInfo == null ? m_clzType : innerTypeInfo.m_clzType;
		List<IJstProperty> constants =
			innerTypeInfo == null ? m_constants : innerTypeInfo.m_constants;;
		List<IJstProperty> staticProperties =
			innerTypeInfo == null ? m_staticProperties : innerTypeInfo.m_staticProperties;;
		List<IJstProperty> instanceProperties =
			innerTypeInfo == null ? m_instanceProperties : innerTypeInfo.m_instanceProperties;
		
		boolean isInterface = type.isInterface();
		for (IJstProperty prop : properties) {
			if (!prop.isPublic() 
					|| prop instanceof ISynthesized
					|| (prop instanceof JstProxyProperty && ((JstProxyProperty)prop).getTargetProperty() instanceof ISynthesized)) {
				continue;
			}
			if (isInterface || (prop.isFinal() && prop.isStatic())) {
				constants.add(prop);
			}
			else if (prop.isStatic()) {
				staticProperties.add(prop);
			}
			else {
				instanceProperties.add(prop);
			}
			if (prop.getType() instanceof IJstRefType) {
				m_hasJsTypeRef = true;
			}
			collectType(prop.getType(), typeSet);	
		}
	}
	
	private void collectTypeFromMethod(
		IJstMethod method,
		Set<IJstType> typeSet,
		InnerTypeInfo innerTypeInfo) {
		
		if (method == null || !method.isPublic()) {
			return;
		}		
		List<MethodMeta> metaList = null;
		if (method.isConstructor()) {
			metaList = innerTypeInfo == null ? m_constructors : innerTypeInfo.m_constructors;
		}
		else if (method.isStatic()) {
			metaList = innerTypeInfo == null ? m_staticMethods : innerTypeInfo.m_staticMethods;
		}
		else {
			metaList = innerTypeInfo == null ? m_instanceMethods : innerTypeInfo.m_instanceMethods;
		}
		if (method.isDispatcher()) {
			//skipt vjo's dispatcher method for overloading methods and constructors
			List<IJstMethod> olmethods = method.getOverloaded();
			if(olmethods!=null){
				int indx = 0;
				for(IJstMethod mtd: olmethods){
					List<JstArg> args = mtd.getArgs();
					List<Stack<SimpleParam>> olParamsPermutation 
						= collectArgTypesAndPermutation(0, args, typeSet);
					if(mtd.isPublic()){
						MethodMeta m = new MethodMeta(mtd, olParamsPermutation);
						if (indx > 0) {
							m.m_isOverload = true;
						}
						indx++;
						metaList.add(m);
					}
					if (!method.isConstructor()) {
						IJstType returnType = mtd.getRtnType();
						if (returnType != null) {
							if (isPublic(returnType)) {
								collectType(returnType, typeSet);
							}
						}
					}
				}
				if (!isDispatcher(method) && !isExists(metaList, method)) {
					List<Stack<SimpleParam>> paramsPermutation 
						= collectArgTypesAndPermutation(0, method.getArgs(), typeSet);
					if(method.isPublic()){
						MethodMeta methodMeta = new MethodMeta(method, paramsPermutation);
						metaList.add(methodMeta);
					}
				}
			}
			return;
		}
		
		if (!method.isConstructor()) {
			IJstType returnType = method.getRtnType();
			if (returnType != null) {
				if (!isPublic(returnType)) {
					return;
				}
				collectType(returnType, typeSet);
			}
		}
		
		List<Stack<SimpleParam>> paramsPermutation 
			= collectArgTypesAndPermutation(0, method.getArgs(), typeSet);
		if (paramsPermutation == null) {
			return;
		}
		MethodMeta methodMeta = new MethodMeta(method, paramsPermutation);
		metaList.add(methodMeta);			
	}
	

   private boolean isDispatcher(IJstMethod method) {
		if (!method.isDispatcher()) {
			return false;
		}
		if (method.getOverloaded().size() > 1) {
			return true;
		}
		JstMethod mtd = (JstMethod) method.getOverloaded().get(0);
		if (mtd.getSurffix() == null) {
			return true;
		}
		return false;
	}
	
	private static final Stack<SimpleParam> EMPTY_STACK = new Stack<SimpleParam>();
	/**
	 * It recursively builds the permutation of param lists, where the size of
	 * the return list is the permutation size and each stack inside the return list
	 * represents one permutation.
	 * 
	 * The recursion starts with index = 0
	 * 
	 * During the analysis, it also collects data type into typeSet.
	 */
	@SuppressWarnings("unchecked")
	private List<Stack<SimpleParam>> collectArgTypesAndPermutation
		(int index, List<JstArg> args, Set<IJstType> typeSet) {

		List<Stack<SimpleParam>> paramStackPermutation = new ArrayList<Stack<SimpleParam>>();
		if (index == args.size()) { //empty params
			paramStackPermutation.add(EMPTY_STACK);
			return paramStackPermutation;
		} 
		
		JstArg arg = args.get(index);
		List<? extends IJstType> argTypes = arg.getTypes();
		boolean isTypeRef = arg.getType() instanceof IJstRefType;
		if (index == args.size() - 1) { //last param
			for (IJstType argType : argTypes) {
				if (!isPublic(argType)) {
					return null;
				}
				collectType(argType, typeSet);
				Stack<SimpleParam> paramStack = new Stack<SimpleParam>();
				paramStack.push(new SimpleParam(arg, argType, arg.isOptional(), isTypeRef));
				paramStackPermutation.add(paramStack);
			}
		}
		else {
			List<Stack<SimpleParam>> tList = collectArgTypesAndPermutation(index + 1, args, typeSet);
			if (tList == null) {
				return null;
			}
			int size = argTypes.size();
			for (int i = 0; i < size; i++) {
				IJstType argType = argTypes.get(i);
				if (!isPublic(argType)) {
					return null;
				}
				collectType(argType, typeSet);				
				SimpleParam param = new SimpleParam(arg, argType, arg.isOptional(), isTypeRef);
				for (Stack<SimpleParam> paramStack : tList) {
					if (i != size -1) {
						//clone stack for new permutations, reuse the existing stack for the last argType
						paramStack = (Stack<SimpleParam>)paramStack.clone();
					}
					paramStack.push(param);
					paramStackPermutation.add(paramStack);
				}
			}
		}
		return paramStackPermutation;
	}

	private void collectType(IJstType type, Set<IJstType> typeSet) {
		if (type instanceof IJstRefType) {
			m_hasJsTypeRef = true;
			IJstRefType refType = (IJstRefType)type;
			typeSet.add(refType.getReferencedNode());
			addCustomTypeImport(refType.getReferencedNode());
			return;
		}
		if (typeSet.contains(type)) {
			return;
		}
//		if (type instanceof JstFunctionRefType
//				&& (!m_clzType.isOType() || m_clzType.equals(type
//						.getParentNode()))) {
//			m_needsIJsJavaProxy = true;
//		}
		if (type instanceof JstArray) {
			type = ((JstArray)type).getElementType();
		}
		if (!type.getModifiers().isPublic()) {
			return;
		}
		typeSet.add(type);
		if (type instanceof JstTypeWithArgs) {
			JstTypeWithArgs jstTypeWArgs = (JstTypeWithArgs) type;
			for (IJstType argType : jstTypeWArgs.getArgTypes()) {
				collectType(argType, typeSet);
			}
		}
	}
	
	private static boolean isPublic(IJstType type) {
		if (type instanceof JstArray) {
			type = ((JstArray)type).getElementType();
		}
		if (type instanceof JstParamType) {
			return true;
		}
		return type.getModifiers().isPublic();
	}
	
	private static boolean isExists(List<MethodMeta> list, IJstMethod method){
		for(MethodMeta meta : list){
			if(isEqual(meta.m_method, method)){
				return true;
			}
		}
		return false;
	}
	
	private static boolean isEqual(IJstMethod source, IJstMethod target){
		if (source == target) {
			return true;
		}
		if (!source.getOriginalName().equals(target.getOriginalName())) {
			return false;
		}
		List<JstArg> sourceArgs = source.getArgs();
		List<JstArg> targetArgs = target.getArgs();
		int sourceArgsCount = sourceArgs.size();
		int targetArgsCount = targetArgs.size();
		if(sourceArgsCount==0 && targetArgsCount==0){
			return true;
		}else if(sourceArgsCount==targetArgsCount){
			for(int i = 0;i<sourceArgsCount;i++){
				if(!sourceArgs.get(i).equals(targetArgs.get(i))){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	private String getTypeName(IJstType type) {
		return getTypeName(type, false);
	}
	
	private String getTypeRefType(String type, boolean noArgs) {
		return IClassR.NativeJsTypeRefSimpleName + (noArgs? "":("<" + type + ">"));
	}

	private String getTypeName(IJstType type, boolean noArgs) {
		if (type == null) {
			return null;
		}
		if (type == m_clzType) {
			return type.getSimpleName();
		}
		if (type instanceof JstArray) {
			return getTypeName(((JstArray)type).getComponentType()) + "[]";
		}
		String name = type.getName();
		String sname = type.getSimpleName();
		if (type instanceof IJstRefType) {
			IJstType target = ((IJstRefType)type).getReferencedNode();
			name = target.getName();
			sname = target.getSimpleName();
			return getTypeRefType(sname, noArgs);
			
		}
		if ("void".equals(name)) {
			return name;
		}
		if (isObject(sname) || isEnum(name)) {
			return "Object";
		}
		if (isVjoClass(name)) {
			return "Class";
		}
		
		TypeMeta meta = null;
		if (isJavaType(name)) {
			if (name.startsWith(VJOX)) {
				name = name.substring(4);
			}
			meta = m_javaTypeMgr.get(name);
			if (meta == null && isJavaLangType(name)) {
				return type.getSimpleName();
			}
		}
		else {
			Class<?> clz = JsNativeMeta.getClass(name);
			if (clz != null) {
				String typeName = clz.getName();
				String proxyName = getNativeProxyType(typeName);
				if (proxyName != null) {
					typeName = proxyName;
				}
				meta = m_jsNativeTypeMgr.get(typeName);
			}
			if (meta == null) {
				meta = m_customTypeMgr.get(name);
			}
		}

		if (meta != null) {
			if (meta.m_useFullName) {
				name = meta.m_fullName;
			} else {
				name = meta.m_simpleName;
			}
		}
		else {
			if (type instanceof JstProxyType && ((JstProxyType)type).getType() == m_clzType) {
				name = m_clzType.getSimpleName();
			}
		}
		if (type instanceof JstTypeWithArgs && !noArgs) {
			name += getArgsDecoration((JstTypeWithArgs)type);
		}
		return name;
	}
	
	private String getArgsDecoration(JstTypeWithArgs templatedType){
		StringBuilder sb = new StringBuilder(OPEN_ANGLE_BRACKET);
		int i=0;
		for (IJstType p: templatedType.getArgTypes()) {
			if (i++ > 0){
				sb.append(COMMA);
			}
			if (p instanceof JstWildcardType){
				sb.append(QUESTION_MARK);
				if (!JstWildcardType.DEFAULT_NAME.equals(p.getSimpleName())){
					if (((JstWildcardType)p).isUpperBound()){
						sb.append(" extends ").append(getTypeName(p));
					}
					else if (((JstWildcardType)p).isLowerBound()){
						sb.append(" super ").append(getTypeName(p));
					}
				}
			}
			else {
				sb.append(getTypeName(p));
			}
		}
		sb.append(CLOSE_ANGLE_BRACKET);		
		return sb.toString();
	}
	
	private static String getWrapperType(String typeName, boolean useFullName) {
		String wrapper = null;
		if (useFullName) {
			wrapper = s_javaWrapperTypeFullNames.get(typeName);
		}
		else {
			wrapper = s_javaWrapperTypes.get(typeName);
		}
		if (wrapper != null) {
			return wrapper;
		}
		return typeName;
	}
	
	private static final Map<String, String> s_javaWrapperTypes = new HashMap<String, String>();
	static {
		s_javaWrapperTypes.put(boolean.class.getName(), Boolean.class.getSimpleName());
		s_javaWrapperTypes.put(byte.class.getName(), Byte.class.getSimpleName());
		s_javaWrapperTypes.put(short.class.getName(), Short.class.getSimpleName());
		s_javaWrapperTypes.put(int.class.getName(), Integer.class.getSimpleName());
		s_javaWrapperTypes.put(long.class.getName(), Long.class.getSimpleName());
		s_javaWrapperTypes.put(float.class.getName(), Float.class.getSimpleName());
		s_javaWrapperTypes.put(double.class.getName(), Double.class.getSimpleName());
		s_javaWrapperTypes.put(char.class.getName(), Character.class.getSimpleName());
		s_javaWrapperTypes.put("INativeJsFuncProxy<?>", "INativeJsFuncProxy");
	}
	
	private static final Map<String, String> s_javaWrapperTypeFullNames = new HashMap<String, String>();
	static {
		s_javaWrapperTypeFullNames.put(boolean.class.getName(), Boolean.class.getName());
		s_javaWrapperTypeFullNames.put(byte.class.getName(), Byte.class.getName());
		s_javaWrapperTypeFullNames.put(short.class.getName(), Short.class.getName());
		s_javaWrapperTypeFullNames.put(int.class.getName(), Integer.class.getName());
		s_javaWrapperTypeFullNames.put(long.class.getName(), Long.class.getName());
		s_javaWrapperTypeFullNames.put(float.class.getName(), Float.class.getName());
		s_javaWrapperTypeFullNames.put(double.class.getName(), Double.class.getName());
		s_javaWrapperTypeFullNames.put(char.class.getName(), Character.class.getName());
		s_javaWrapperTypeFullNames.put("org.ebayopensource.dsf.dap.proxy.INativeJsFuncProxy<?>", "org.ebayopensource.dsf.dap.proxy.INativeJsFuncProxy");
	}
	
	private static boolean isPrimitiveType(String typeName) {
		return s_primitiveTypes.contains(typeName);
	}
	
	private static final Set<String> s_primitiveTypes = new HashSet<String>();
	static {
		s_primitiveTypes.add(String.class.getSimpleName());
		s_primitiveTypes.add(String.class.getName());
		s_primitiveTypes.add(boolean.class.getName());
		s_primitiveTypes.add(byte.class.getName());
		s_primitiveTypes.add(short.class.getName());
		s_primitiveTypes.add(int.class.getName());
		s_primitiveTypes.add(long.class.getName());
		s_primitiveTypes.add(float.class.getName());
		s_primitiveTypes.add(double.class.getName());
		s_primitiveTypes.add(char.class.getName());
	}
	
	private static boolean needEscape(String name) {
		return s_reservedName.contains(name);
	}
	
	private static String escape(String name) {
		return name + "__";
	}
	
	private static final Set<String> s_reservedName = new HashSet<String>();
	static {
		s_reservedName.add(String.class.getSimpleName());
		s_reservedName.add(Class.class.getSimpleName());
		s_reservedName.add(byte.class.getName());
		s_reservedName.add(short.class.getName());
		s_reservedName.add(int.class.getName());
		s_reservedName.add(long.class.getName());
		s_reservedName.add(float.class.getName());
		s_reservedName.add(double.class.getName());
		s_reservedName.add(char.class.getName());
		s_reservedName.add("class");
		s_reservedName.add("interface");
		s_reservedName.add("static");
		s_reservedName.add("public");
		s_reservedName.add("private");
		s_reservedName.add("protected");
		s_reservedName.add("final");
		s_reservedName.add("synchronized");
		s_reservedName.add("extends");
		s_reservedName.add("implements");
	}
	
	private static String getClassName(String name) {
		int idx = 0;
		if ((idx=name.indexOf("<"))!=-1) {
			return name.substring(0,idx).trim();
		}
		return name;
	}
	private static boolean isJavaType(String typeName) {
		return typeName.startsWith("java.") || typeName.startsWith(VJOX_JAVA);
	}
	
	private static boolean isJavaLangType(String typeName) {
		return typeName.startsWith("java.lang.") || typeName.startsWith(VJOX_JAVA);
	}
	
	private static boolean isObject(IJstType type) {
		return isObject(type.getSimpleName());
	}
	
	private static boolean isObject(String typeName) {
		return "Object".equals(typeName) || "JsObj".equals(typeName);
	}
	
	private static boolean isVjoClass(IJstType type) {
		return isVjoClass(type.getName());
	}
	
	private static boolean isVjoClass(String typeName) {
		return "vjo.Class".equals(typeName);
	}
	
	private static boolean isEnum(IJstType type) {
		return isEnum(type.getName());
	}
	
	private static boolean isEnum(String typeName) {
		return "vjo.Enum".equals(typeName);
	}
	
	private static final Map<String, String> s_nativeToProxyMapping = new HashMap<String, String>();
	static {
		s_nativeToProxyMapping.put(IClassR.NativeArrayName, IClassR.ArrayProxyName);
		s_nativeToProxyMapping.put(IClassR.ObjLiteralName, IClassR.OlName);
		s_nativeToProxyMapping.put(IClassR.NativeFunctionName, IClassR.INativeJsFuncProxy);		
	}
	
	private static String getNativeProxyType(String nativeType) {
		return s_nativeToProxyMapping.get(nativeType);
	}
	
	private static boolean hasNonAbstractMethod(List<MethodMeta> methods) {
		
		if (methods.isEmpty()) {
			return false;
		}
		for (MethodMeta meta : methods) {
			if (!meta.m_method.isAbstract()) {
				return true;
			}
		}			
		return false;
	}
	
	private boolean hasNonAbstractMethod() {
		if (hasNonAbstractMethod(m_staticMethods) ||
			hasNonAbstractMethod(m_instanceMethods)) {
			return true;
		}
		if (m_innerTypeInfoMap == null) {
			return false;
		}
		for (InnerTypeInfo info : m_innerTypeInfoMap.values()) {
			if (info.m_clzType.isClass()) {
				if (hasNonAbstractMethod(info.m_staticMethods) ||
					hasNonAbstractMethod(info.m_instanceMethods)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean hasProperty() {
		if (!m_staticProperties.isEmpty() || !m_instanceProperties.isEmpty()) {
			return true;
		}
		if (m_innerTypeInfoMap == null) {
			return false;
		}
		for (InnerTypeInfo info : m_innerTypeInfoMap.values()) {
			if (!info.m_staticProperties.isEmpty() || !info.m_instanceProperties.isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	private boolean hasConstantInInterface() {
		if (m_clzType.isInterface() && !m_constants.isEmpty()) {
			return true;
		}
		if (m_innerTypeInfoMap == null) {
			return false;
		}
		for (Map.Entry<IJstType, InnerTypeInfo> entry : m_innerTypeInfoMap.entrySet()) {
			if (entry.getKey().isInterface() && !entry.getValue().m_constants.isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	private static class InnerTypeInfo {
		private IJstType m_clzType = null;
		private TypeMetaMgr m_javaTypeMgr = null;
		private TypeMetaMgr m_customTypeMgr = null;
		private TypeMetaMgr m_jsNativeTypeMgr = null;
		private List<MethodMeta> m_constructors = new ArrayList<MethodMeta>();
		private List<MethodMeta> m_staticMethods = new ArrayList<MethodMeta>();
		private List<MethodMeta> m_instanceMethods = new ArrayList<MethodMeta>();
		private List<IJstProperty> m_constants = new ArrayList<IJstProperty>();
		private List<IJstProperty> m_staticProperties = new ArrayList<IJstProperty>();
		private List<IJstProperty> m_instanceProperties = new ArrayList<IJstProperty>();
		private Set<String> m_currentDefinedTypes = null;
		private Map<IJstType, InnerTypeInfo> m_innerTypeInfoMap = null;
		
		InnerTypeInfo(
			IJstType clzType,
			TypeMetaMgr javaTypeMgr,
			TypeMetaMgr customTypeMgr,
			TypeMetaMgr jsNativeTypeMgr,
			Set<String> currentDefinedTypes,
			Map<IJstType, InnerTypeInfo> innerTypeInfoMap) {
			m_clzType = clzType;
			m_javaTypeMgr = javaTypeMgr;
			m_customTypeMgr = customTypeMgr;
			m_jsNativeTypeMgr = jsNativeTypeMgr;
			m_currentDefinedTypes = currentDefinedTypes;
			m_innerTypeInfoMap = innerTypeInfoMap;			
			m_innerTypeInfoMap.put(clzType, this);
		}
	}
	
	private static class ObjectLiteralGenStrings {
		StringBuilder paramSig = new StringBuilder();
		StringBuilder superSig = new StringBuilder();
		StringBuilder newSig = new StringBuilder();
		StringBuilder annoSig = new StringBuilder();
	}
}

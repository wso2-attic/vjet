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
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

import org.ebayopensource.dsf.jsgen.shared.classref.IClassR;
import org.ebayopensource.dsf.jsgen.shared.generate.IJsrTypeProvider.Type;
import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.JsNativeMeta;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstOType;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstRefType;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.ISynthesized;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstArray;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstFactory;
import org.ebayopensource.dsf.jst.declaration.JstFunctionRefType;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstObjectLiteralType;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstParamType;
import org.ebayopensource.dsf.jst.declaration.JstProxyMethod;
import org.ebayopensource.dsf.jst.declaration.JstProxyProperty;
import org.ebayopensource.dsf.jst.declaration.JstProxyType;
import org.ebayopensource.dsf.jst.declaration.JstRefType;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstTypeWithArgs;
import org.ebayopensource.dsf.jst.declaration.JstWildcardType;
import org.ebayopensource.dsf.jst.util.DataTypeHelper;

public class JsrGenerator extends SourceGenerator {

	private static final String DEFAULT_PARAM_PREFIX = "p";
	private static final String SERIAL_ID = "private static final long serialVersionUID = 1L;";
	private static final String NATIVE_OBJECT = "Object";
	private static final String JS_OBJ = "JsObj";
	// private static final String JS_ENUM = "JsEnum";
	// private static final String VJO_ENUM = "vjo.Enum";
	//
	//    
	// private static final String VJO_OBJECT = "vjo.Object";
	//
	// private static final String JS_CLASS = "JsVjoClass";
	// private static final String VJO_CLASS = "vjo.Class";

	private static final String JSR_SUFFIX = "Jsr";
	private static final String JSOBJDATA_VAR_NAME = "S";
	private static final String IMPORT = "import ";
	private static final String PACKAGE = "package ";

	private boolean m_skip_methods = false;
	private GeneratorConfig m_config;
	private ICustomJsrProvider m_customJsrProvider;
	private List<IJsrGenListener> m_listeners = new ArrayList<IJsrGenListener>();
	private IJsrTypeProvider m_JsToJavaMapper = new JsrTypeProvider();

	// per-type state which needs to be init by setup
	private IJstType m_clzType = null;
	private TypeMetaMgr m_javaTypeMgr = null;
	private TypeMetaMgr m_jsNativeTypeMgr = null;
	private TypeMetaMgr m_jsrTypeMgr = null;
	private TypeMetaMgr m_vjoSerializableTypeMgr = null;
	private List<MethodMeta> m_constructors = null;
	private List<MethodMeta> m_staticMethods = null;
	private List<MethodMeta> m_instanceMethods = null;
	private List<IJstProperty> m_staticProperties = null;
	private List<IJstProperty> m_instanceProperties = null;
	private Map<String, IJstType> m_inactiveNeedsFromMixin = null;
	private Set<IJstType> m_importExclusionList = null;
	private Map<String, Integer> m_overloadedMap = null;
	// private boolean m_hasMappedEvent = false;
	private boolean m_needsPropSetter = false;
	private boolean m_needsJsEnum = false;
	// private boolean m_needsJsObject = false;
	// private boolean m_needsJsClass = false;
	private boolean m_hasProp = false;
	private boolean m_hasFunction = false;
	private boolean m_needsIValueBinding = false;
	private boolean m_needsJsObj = false;
	private boolean m_needsObjectLiteral = false;
	private boolean m_needsFuncRef = false;
	private boolean m_oTypeNeedsJsObj = false;
	private boolean m_enableScriptingJava = false;
	private int m_initialIndent = 0;
	private boolean m_hasGenericParams = false;
	private boolean m_needsTypeRef = false;
	// private JsrGenerator m_rootTypeGenerator;

	private static final String SUPER_FNREF_CALL = "super(obj,funcName);";
	private static final String FNREF_JSOBJ_ARGS = "(JsObj obj, String funcName)";
	private static final String FNREF_JSOBJDATA_ARGS = "(JsObjData obj, String funcName)";

	// private static final String SERIALIZE_UTIL_SUFFIX = "Util";

	public JsrGenerator(final PrintWriter writer, final CodeStyle style,
			IJsrFilters filters) {
		super(writer, new Indenter(writer, style), style);

		m_config = new GeneratorConfig(filters);
		m_customJsrProvider = m_config.getJsrGenConfig().getCustomJsrProvider();
	}

	public JsrGenerator(final PrintWriter writer, final CodeStyle style,
			GeneratorConfig config) {
		super(writer, new Indenter(writer, style), style);

		m_config = config;
		m_customJsrProvider = config.getJsrGenConfig().getCustomJsrProvider();
	}

	public JsrGenerator(final PrintWriter writer, final CodeStyle style) {
		this(writer, style, new DefaultJsrFilters());
	}

	public void addListener(IJsrGenListener listener) {
		if (listener == null) {
			return;
		}
		m_listeners.add(listener);
	}

	public JsrGenerator writeJsr(final IJstType type) {
		return writeJsr(type, false);
	}

	public JsrGenerator writeJsr(final IJstType type, JsrGenerator parentJsr) {
		// m_rootTypeGenerator = parentJsr;
		return writeJsr(type, false);
	}

	public JsrGenerator writeJsr(final IJstType type,
			boolean enableScriptingJava) {

		if (type == null) {
			throw new NullPointerException(
					"Invalid input, JstType cannot be null");
		}

		m_enableScriptingJava = enableScriptingJava;
		for (IJsrGenListener listener : m_listeners) {
			listener.initialize(type);
		}

		setUp(type);
		if (type.isOType()) {
			return writeOTypeJsr(type);
		}

		for (int i = 0; i < m_initialIndent; i++) {
			indent();
		}

		if (m_clzType.isEmbededType()) {
			writeIndent();
			setupDefaultExtend(true);
		} else {
			writePkg();
			writeNewline();
			writeImports();
			writeNewline();
			writeCodeGenMarker(JsrGenerator.class);
			writeNewline();
		}

		String jsrName = type.getSimpleName() + JSR_SUFFIX;
		writeJsrClassDefinition(jsrName);
		getWriter().append(" {");
		indent();
		boolean writeStatic = false;
		if (type.isInterface() || type.isMixin()) {
			if (!m_clzType.isEmbededType()) {
				writeJsrStaticDeclaration(jsrName);

				writeJsrResourceSpec(type);
			}
		} else {
			writeNewlineAndIndent();
			getWriter().append(SERIAL_ID);
			writeNewline();
			writeStatic = m_clzType.getModifiers().isStatic()
					|| !m_clzType.isEmbededType();
			if (writeStatic) {
				writeJsrStaticDeclaration(jsrName);
			}
			if (!m_clzType.isEmbededType()) {
				writeJsrResourceSpec(type);
			}
			// Constructor
			if (!m_skip_methods) {
				writeConstructors(jsrName);
			}
			writeJsObjProtectedConstructor(jsrName);

			for (IJsrGenListener listener : m_listeners) {
				listener.postConstructors(getWriter(), getStyle());
			}
		}
		if (!type.isMixin() && !m_skip_methods) {
			writeProps();
			writeProtos();
		}

		List<? extends IJstType> embededTypes = m_clzType.getEmbededTypes();
		if (!m_clzType.isMixin() && embededTypes.size() > 0) {
			writeNewline();
			for (IJstType embededType : embededTypes) {
				if (!isSameGroup(m_clzType, embededType)) {
					continue;
				}
				if (!embededType.isMixin()
						&& !embededType.getModifiers().isPrivate()) {
					writeNewline();
					JsrGenerator sibJsr = new JsrGenerator(getWriter(),
							getStyle(), m_config.getFilters());
					sibJsr.setNewline(getNewline());
					sibJsr.setInitialIndent(m_initialIndent + 1);
					sibJsr.writeJsr(embededType, this);
				}
			}
		}

		// if ((writeStatic) {
		if (!m_skip_methods && needPrototype(m_clzType)) {
			writeTypeRef();
		}

		outdent();
		writeNewline();
		if (m_clzType.isEmbededType()) {
			writeIndent();
		}
		getWriter().append("}");
		return this;
	}

	private boolean needPrototype(IJstType type) {
		if (type.isOType() || type.isMixin()) {
			return false;
		}
		if (type.isEmbededType()) {
			return (type.getModifiers().isStatic() || type.getOuterType()
					.isInterface());
		}
		return true;
	}

	public JsrGenerator writeOTypeJsr(final IJstType type) {
		writePkg();
		writeNewline();
		writeImports();
		writeNewline();
		writeCodeGenMarker(JsrGenerator.class);
		writeNewline();
		String jsrName = type.getSimpleName() + JSR_SUFFIX;
		writeOTypeJsrClassDefinition(jsrName);

		getWriter().append(" {");

		indent();
		writeNewlineAndIndent();
		writeJsrStaticDeclaration(jsrName);
		writeJsrResourceSpec(type);

		List<IJstOType> otypes = type.getOTypes();
		outdent();
		writeNewline();
		if (otypes.size() > 0) {
			writeOTypes(otypes);
		}
		outdent();
		writeNewline();
		getWriter().append("}");
		return this;
	}

	private void setUp(final IJstType type) {
		m_clzType = type;
		if (m_config.getFilters().isSkipMethodAndProperties(type.getName())) {
			m_skip_methods = true;
		}
		m_JsToJavaMapper.setCurrentType(type);
		m_javaTypeMgr = new TypeMetaMgr(m_config.getFilters());
		m_jsNativeTypeMgr = new TypeMetaMgr(m_config.getFilters());
		m_jsrTypeMgr = new TypeMetaMgr();
		m_vjoSerializableTypeMgr = new TypeMetaMgr();
		m_constructors = new ArrayList<MethodMeta>();
		m_staticMethods = new ArrayList<MethodMeta>();
		m_instanceMethods = new ArrayList<MethodMeta>();
		m_staticProperties = new ArrayList<IJstProperty>();
		m_instanceProperties = new ArrayList<IJstProperty>();
		m_importExclusionList = new HashSet<IJstType>();
		m_overloadedMap = new HashMap<String, Integer>();
		m_inactiveNeedsFromMixin = new HashMap<String, IJstType>();
		// m_hasMappedEvent = false;
		m_needsPropSetter = false;
		m_hasProp = false;
		m_hasFunction = false;
		m_needsIValueBinding = false;
		// m_isDefaultExtend = false;
		m_needsJsObj = false;
		m_hasGenericParams = false;

		analyze();
	}

	private void analyze() {
		m_needsJsEnum = m_clzType.isEnum();
		m_needsJsObj = !m_needsJsEnum && !m_clzType.isInterface()
				&& !hasNonDefaultExtend(m_clzType);

		m_jsrTypeMgr.addToSimpleNameMap(new TypeMeta(m_clzType.getName()
				+ JSR_SUFFIX, m_clzType.getSimpleName() + JSR_SUFFIX, false));

		// if(!m_clzType.getExtends().isEmpty()){
		// for(IJstType t:m_clzType.getExtends()){
		// m_jsrTypeMgr.addToSimpleNameMap(new TypeMeta(getTypeName(t,
		// Type.Wrapper), getWrapperType(t),false));
		// }
		// }

		if (m_clzType.isEnum() && m_clzType.getEnumValues().size() > 0) {
			m_hasProp = true;
		}
		processJsrTypeImports();

		m_hasGenericParams = (m_clzType.getParamTypes().size() > 0);
		if (!m_hasGenericParams) {
			m_hasGenericParams = processGenericParams(m_clzType);
		}

		Set<IJstType> typeSet = new LinkedHashSet<IJstType>();
		for (IJstOType type : m_clzType.getOTypes()) {
			if (type instanceof JstObjectLiteralType) {
				m_needsObjectLiteral = true;
				if (((JstObjectLiteralType) type).getProperties().size() > 0) {
					m_needsIValueBinding = true;
					collectTypeFromProperties(typeSet,
							((JstObjectLiteralType) type).getProperties(),
							false, false);
				}
			}
			if (type instanceof JstFunctionRefType) {
				m_needsFuncRef = true;
				m_oTypeNeedsJsObj = true;
				collectTypeFromMethod(((JstFunctionRefType) type)
						.getMethodRef(), typeSet, false);
			}
		}

		processMixins(typeSet);

		if (!m_skip_methods) {
			if (needPrototype(m_clzType)) {
				m_needsTypeRef = true;
			}
			collectTypeFromProperties(typeSet, m_clzType.getProperties(),
					false, m_clzType.isInterface());
			collectTypeFromMethods(typeSet, m_clzType.getMethods(), false);
			IJstMethod constructor = m_clzType.getConstructor();
			if (constructor != null && !(constructor instanceof ISynthesized)) {
				collectTypeFromMethod(constructor, typeSet, false);
			}

			// Collect data for embedded types:
			List<? extends IJstType> embededTypes = m_clzType.getEmbededTypes();
			collectTypesFromEmbeded(typeSet, embededTypes);

			addJavaTypeImport(typeSet);
		} else {
			processEmbeddedForJsrTypes(m_clzType.getEmbededTypes());
		}

		processInnersForJsrType();
	}

	// private List<? extends IJstMethod> fixUpMethodsForEvent() {
	// List<IJstMethod> methods = new ArrayList<IJstMethod>();
	// methods.addAll(m_clzType.getMethods());
	//
	//		
	// for(IJstMethod m : m_clzType.getMethods()){
	// if(!(m instanceof JstMethod)){
	// continue;
	// }
	// JstMethod jstMethod = (JstMethod)m;
	//			
	// if(m.getArgs().size()>1){
	// JstArg jstArg = m.getArgs().get(0);
	//					
	// if(jstArg!=null && isBrowserEventType(jstArg.getType())){
	// JstType type = (JstType)m_clzType;
	// int count = 0;
	//						
	// JstMethod method = new JstMethod(m.getName().getName());
	// method.setParent(type);
	// for(JstArg arg: m.getArgs()){
	// if(count>=1){
	// method.addArg(arg);
	// }
	// count++;
	// }
	// jstMethod.addOverloaded(method);
	// }
	// }
	// }
	//		
	//		
	// return methods;
	// }

	private void processEmbeddedForJsrTypes(
			List<? extends IJstType> embededTypes) {
		for (IJstType innerType : embededTypes) {
			if (innerType.getModifiers().isPrivate()) {
				continue;
			}
			if (innerType.isEnum()) {
				m_needsJsEnum = true;
			} else {
				if (!hasNonDefaultExtend(innerType) && !innerType.isInterface()) {
					m_needsJsObj = true;
				}
			}
			if (!m_skip_methods && innerType.isEnum()
					&& innerType.getEnumValues().size() > 0) {
				m_hasProp = true;
			}
			if (!m_skip_methods && needPrototype(innerType)) {
				m_needsTypeRef = true;
			}
			if (innerType.getEmbededTypes().size() > 0) {
				processEmbeddedForJsrTypes(innerType.getEmbededTypes());
			}
		}

	}

	private boolean processGenericParams(IJstType type) {
		for (IJstType t : type.getExtends()) {
			if (t.getParamTypes().size() > 0) {
				return true;
			}
			if (processGenericParams(t)) {
				return true;
			}
		}
		for (IJstType t : type.getSatisfies()) {
			if (t.getParamTypes().size() > 0) {
				return true;
			}
			if (processGenericParams(t)) {
				return true;
			}
		}
		return false;
	}

	private void collectTypesFromEmbeded(Set<IJstType> typeSet,
			List<? extends IJstType> embededTypes) {
		processEmbeddedForJsrTypes(embededTypes);
		for (IJstType innerType : embededTypes) {
			if (innerType.getModifiers().isPrivate()) {
				continue;
			}

			collectTypeFromProperties(typeSet, innerType.getProperties(), true,
					innerType.isInterface());
			collectTypeFromMethods(typeSet, innerType.getMethods(), true);
			collectTypeFromMethod(innerType.getConstructor(), typeSet, true);
			if (innerType.getEmbededTypes().size() > 0) {
				collectTypesFromEmbeded(typeSet, innerType.getEmbededTypes());
			}
		}
	}

	private boolean hasNonDefaultExtend(IJstType type) {
		IJstType extend = type.getExtend();
		if (extend == null) {
			return false;
		}
		return (!m_config.getFilters().isSkipExtends(extend) && !isObjectType(extend));
	}

	private void writeImports() {
		// import java type
		// Iterator<TypeMeta> itr = m_javaTypeMgr.getMetaItr();
		// while (itr.hasNext()) {
		// TypeMeta meta = itr.next();
		// if (!meta.m_useFullName) {
		// if (!meta.m_fullNameInJava.startsWith("java.lang.")) {
		// writeImport(meta.m_fullNameInJava);
		// }
		// }
		// }

		// Write imports for native types
		// itr = m_jsNativeTypeMgr.getMetaItr();
		// while (itr.hasNext()) {
		// TypeMeta meta = itr.next();
		// if (!meta.m_useFullName) {
		// writeImport(meta.m_fullNameInJava);
		// }
		// }

		// import framework type
		if (m_clzType.isOType()) {
			writeOtypeImports();
		} else {
			if (!m_clzType.isMixin()) {
				writeFrameworkImports();

			}

			writeImport(IClassR.JsObjData);

			writeResourceSpecImports();

		}

		// import Jsr type
		Iterator<TypeMeta> itr = m_jsrTypeMgr.getMetaItr();
		while (itr.hasNext()) {
			TypeMeta meta = itr.next();
			if (meta.m_inactiveType && m_clzType.isMixin()) {
				continue;
			}

			if (!meta.m_useFullName
					|| (!meta.m_inactiveType && meta.m_usedInDecl)) {
				writeImport(meta.m_fullName);
			}
		}

		// if (!m_clzType.isMixin()) {
		// for (IJstType ineed : m_clzType.getInactiveImports()) {
		// String name = ineed.getName() + JSR_SUFFIX;
		// if (ineed.isOType()){
		// for (IJstType ioneed : ineed.getInactiveImports()) {
		// String iname = ioneed.getName() + JSR_SUFFIX;
		// if (!ioneed.isOType() && m_jsrTypeMgr.get(iname)==null) {
		// writeImport(iname);
		// }
		// }
		// }else
		// if (!ineed.isOType() && m_jsrTypeMgr.get(name)==null) {
		// writeImport(name);
		// }
		// }
		// }
		/*
		 * writeNewline(); getWriter().write("// Serializable for VJO");
		 * writeNewline();
		 */
		// itr = m_vjoSerializableTypeMgr.getMetaItr();
		// while (itr.hasNext()) {
		// TypeMeta meta = itr.next();
		// if (!meta.m_useFullName) {
		// writeImport(meta.m_fullName);
		// }
		// }

		for (IJsrGenListener listener : m_listeners) {
			listener.postImports(new ArrayList<String>(0), getWriter(),
					getStyle());
		}
	}

	private void writeFrameworkImports() {
		if (m_hasFunction) {
			writeImport(IClassR.JsFunc);
		}
		if (m_needsIValueBinding) {
			writeImport(IClassR.IValueBinding);
		}
		setupDefaultExtend(false);
		if (m_hasProp) {
			writeImport(IClassR.JsProp);
		}
		if (m_needsPropSetter) {
			writeImport(IClassR.IJsPropSetter);
		}
		if (!m_clzType.isInterface()
				|| hasNonInterfaceInnerTypes(m_clzType.getEmbededTypes())) {
			writeImport(IClassR.JsCmpMeta);
		}

		if (!m_skip_methods || m_needsTypeRef) {
			writeImport(IClassR.JsTypeRef);
		}

		// if (m_needsJsObject) {
		// writeImport(IClassR.JsVjoObject);
		// }
		// if (m_needsJsClass) {
		// writeImport(IClassR.JsVjoClass);
		// }
	}

	private void writeOtypeImports() {
		if (m_needsObjectLiteral) {
			if (m_needsIValueBinding) {
				writeImport(IClassR.IValueBinding);
				writeImport(IClassR.BV);
			}
			writeImport(IClassR.JsObjectLiteral);
		}
		if (m_needsFuncRef) {
			writeImport(IClassR.JsFuncRef);
		}
		if (m_oTypeNeedsJsObj) {
			writeImport(IClassR.JsObj);
			// writeImport(IClassR.JsVjoObject);
		}
		// if (m_needsJsClass) {
		// writeImport(IClassR.JsVjoClass);
		// }

		if (m_needsTypeRef) {
			writeImport(IClassR.JsTypeRef);
		}

		writeImport(IClassR.JsObjData);

		writeResourceSpecImports();
	}

	private void writeResourceSpecImports() {
		if (m_config.getJsrGenConfig().isGenResouceSpec()) {
			writeImport(IClassR.IComponentSpec);
			writeImport(IClassR.JsResource);
			writeImport(IClassR.IJsResourceRef);
		}
	}

	private boolean hasNonInterfaceInnerTypes(List<? extends IJstType> list) {
		// for (int i = 0; i < m_clzType.getEmbededTypes().size(); i++) {
		for (IJstType jst : list) {
			if (!jst.isInterface()
					|| hasNonInterfaceInnerTypes(jst.getEmbededTypes())) {
				return true;
			}
		}
		return false;
	}

	private void setupDefaultExtend(boolean noImport) {
		// if (!m_clzType.isInterface()) {
		// IJstType extendType = m_clzType.getExtend();
		// if (!hasNonDefaultExtend(m_clzType)) {
		if (!noImport) {
			if (m_needsJsEnum) {
				writeImport(IClassR.JsEnum);
			}
			if (m_needsJsObj) {
				writeImport(IClassR.JsObj);
			}
		}
		// m_isDefaultExtend = true;
		// }
		// }
	}

	private void writeJsrClassDefinition(String jsrName) {
		PrintWriter writer = getWriter();
		writer.append("public ");
		if (m_clzType.isEmbededType() && m_clzType.getModifiers().isStatic()) {
			writer.append("static ");
		}
		if (m_clzType.isInterface()) {
			writer.append("interface ");
		} else {
			if (m_clzType.getModifiers().isFinal()) {
				writer.append("final ");
			}
			if (m_clzType.getModifiers().isAbstract() || m_clzType.isMixin()) {
				writer.append("abstract ");
			}
			writer.append("class ");
		}

		writer.append(jsrName);
		if (!m_clzType.getParamNames().isEmpty()) {
			// writer.append(((JstType)m_clzType).getParamsDecoration());
			writer.append(getParamsDecoration(m_clzType));
		}

		if (m_clzType.isInterface()) {
			List<? extends IJstType> extendedTypes = m_clzType.getExtends();
			if (!extendedTypes.isEmpty()) {
				writer.append(" extends ");
				for (int i = 0; i < extendedTypes.size(); i++) {
					if (i > 0) {
						writer.append(COMMA).append(" ");
					}
					writer.append(getJsrNameForTypeDecl(extendedTypes.get(i)));
				}
			}
		} else if (!m_clzType.isMixin()) {
			if (hasNonDefaultExtend(m_clzType)) {
				writer.append(" extends ");
				writer.append(getJsrNameForTypeDecl(m_clzType.getExtend()));
			} else {
				if (m_clzType.isEnum()) {
					writer.append(" extends JsEnum");
				} else {
					writer.append(" extends ").append(JS_OBJ);
				}
			}
		}

		List<IJstType> interfaceTypes = new ArrayList<IJstType>();
		for (IJstType type : m_clzType.getSatisfies()) {
			interfaceTypes.add(type);
		}
		/*
		 * for (IJstTypeReference mixin : m_clzType.getMixins()) { for (IJstType
		 * sat : mixin.getReferencedType().getSatisfies()) { if
		 * (!interfaceTypes.contains(sat)) { interfaceTypes.add(sat); } } }
		 */
		if (!interfaceTypes.isEmpty()) {
			boolean addComma = false;
			StringBuilder buf = new StringBuilder();
			for (int i = 0; i < interfaceTypes.size(); i++) {
				IJstType itfType = interfaceTypes.get(i);
				if (m_config.getFilters().isSkipSatisfies(itfType)) {
					continue;
				}
				if (addComma) {
					buf.append(COMMA).append(" ");
				}
				buf.append(getJsrNameForTypeDecl(itfType));
				addComma = true;
			}
			if (buf.length() > 0) {
				writer.append(" implements ").append(buf.toString());
			}
		}
		for (IJsrGenListener listener : m_listeners) {
			listener.postInterfaces(writer, getStyle());
		}
	}

	private void writeOTypeJsrClassDefinition(String jsrName) {
		PrintWriter writer = getWriter();
		writer.append("public abstract class ").append(jsrName);
		// .append(jsrName).append(" extends ")
		// .append(IClassR.JsObjectLiteralSimpleName);

	}

	private void writeImport(final String fullName) {
		getWriter().append(IMPORT).append(fullName).append(SEMI_COLON);
		writeNewline();
	}

	private void writeJsrStaticDeclaration(final String jsrName) {
		writeNewlineAndIndent();
		if (!m_clzType.isInterface()) {
			getWriter().append("private static final ");
		}
		getWriter().append("JsObjData ").append(JSOBJDATA_VAR_NAME).append(
				" = ");
		indent();
		writeNewlineAndIndent();
		getWriter().append(getNewJsObjDataString(m_clzType, jsrName)).append(
				";");
		outdent();
	}

	private void writeJsrResourceSpec(IJstType type) {
		if (!m_config.getJsrGenConfig().isGenResouceSpec()) {
			return;
		}

		writeNewline();
		writeNewlineAndIndent();
		PrintWriter writer = getWriter();

		// backwards compatibility
		writeNewlineAndIndent();
		writer.append("public static class ResourceSpec {");
		indent();
		writeNewlineAndIndent();
		writer.append("public static IComponentSpec getInstance() {");// .append(SPACE);
		indent();
		writeNewlineAndIndent();
		writer.append("return S.getResourceSpec();").append(SPACE);
		outdent();
		writeNewlineAndIndent();
		writer.append("}");
		writeNewlineAndIndent();
		writer.append(
				"public static final JsResource RESOURCE = S.getJsResource()")
				.append(SEMI_COLON);
		writeNewlineAndIndent();
		writer
				.append(
						"public static final IJsResourceRef REF = S.getJsResourceRef()")
				.append(SEMI_COLON);
		outdent();
		writeNewlineAndIndent();
		indent();
		writer.append("}");
		writeNewline();
		outdent();
		writeNewlineAndIndent();

		writer.append("public static final IComponentSpec SPEC = ").append(
				JSOBJDATA_VAR_NAME).append(".getResourceSpec()");

		// if (m_enableScriptingJava) {
		// writeNewlineAndIndent();
		// writeIndent();
		// writer.append(".addClassDefinitionJsRef(").append(
		// "JsResource.viaJavaType(\"").append(type.getName()).append(
		// "\"))");
		// }

		Iterator<TypeMeta> itr = m_jsrTypeMgr.getMetaItr();
		while (itr.hasNext()) {
			TypeMeta meta = itr.next();
			if (meta.m_inactiveType && !meta.m_usedInDecl) {
				continue;
			}
			writeNewlineAndIndent();
			writeIndent();
			writer.append(".addDependentComponent(");
			if (meta.m_useFullName) {
				writer.append(meta.m_fullName);
			} else {
				writer.append(meta.m_simpleName);
			}
			writer.append(".ResourceSpec.getInstance())");
		}
		writer.append(SEMI_COLON);
	}

	private void writeJsObjProtectedConstructor(String jsrName) {
		writeNewline();
		writeNewlineAndIndent();
		PrintWriter writer = getWriter();
		writer.append("protected ").append(jsrName).append(
				"(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {");
		indent();
		writeNewlineAndIndent();
		writer.append("super(cmpMeta, isInstance, args);");
		outdent();
		writeNewlineAndIndent();
		writer.append("}");
	}

	private static final List<SimpleParam> EMPTY_LIST = new ArrayList<SimpleParam>(
			0);

	// private static final boolean DEBUG = false;

	private void writeConstructors(String jsrName) {
		if (m_constructors.isEmpty()) {
			// create default constructor
			writeConstructor(jsrName, EMPTY_LIST, false, false);
			return;
		}
		List<IJstMethod> writtenConstructors = new ArrayList<IJstMethod>();
		for (MethodMeta meta : m_constructors) {
			if (!writtenConstructors.contains(meta.m_method)) {
				for (List<SimpleParam> paramList : meta.m_argListPermutation) {
					writeConstructor(jsrName, paramList, false, false);
					if (needValueBinding(paramList)) {
						writeConstructor(jsrName, paramList, true, false);
					}

					if (needNonProxyMethod(paramList)) {
						writeConstructor(jsrName, paramList, false, true);
					}
				}
				writtenConstructors.add(meta.m_method);
			}
		}
		writtenConstructors = null;
	}

	private void writeConstructor(String jsrName, List<SimpleParam> paramList,
			boolean useValueBinding, boolean useNonProxyType) {
		writeNewline();
		writeNewlineAndIndent();
		PrintWriter writer = getWriter();
		writer.append("public ").append(jsrName).append("(");
		writeArgs(jsrName, paramList, useValueBinding, useNonProxyType, false,
				null);
		writer.append("){");
		indent();
		writeNewlineAndIndent();
		writer.append("super(").append(JSOBJDATA_VAR_NAME).append(
				".getJsCmpMeta(), true");

		boolean isVjoSerializableType = false;
		int i = 0;
		for (SimpleParam param : paramList) {
			if (!isVjoSerializableType) {
				isVjoSerializableType = m_config.getFilters()
						.isSerializableForVjo(param.m_type, true);
			}
			String pName = param.m_arg.getName();
			if (pName == null) {
				pName = DEFAULT_PARAM_PREFIX + i;
			}
			writer.append(", ").append(pName);
			i++;
		}
		writer.append(");");
		outdent();
		writeNewlineAndIndent();
		writer.append("}");
	}

	// private boolean checkForVjoSerializableType(IJstType type) {
	// boolean ret = false;
	// String forName = ISerializableForVjo.class.getName();
	// if (forName.startsWith("com.")) {
	// forName = "vjo." + forName.substring(4);
	// }
	//
	// if (m_config.getFilters().isJavaPrimitiveOrWrapper(type.getName())
	// || type.isEnum()) {
	// return false;
	// }
	//
	// if (type.isInterface()) {
	// // Check extends of Interface
	// if (type.getName().equals(forName)) {
	// return true;
	// }
	// } else {
	// if (type.getName().equals(forName)) {
	// return true;
	// }
	// // Check interfaces of the class
	// for (IJstType iType : type.getSatisfies()) {
	// if (iType.getName().equals(forName)) {
	// return true;
	// }
	// ret = checkForVjoSerializableType(iType);
	// if (ret)
	// return ret;
	// }
	// }
	// for (IJstType eType : type.getExtends()) {
	// ret = checkForVjoSerializableType(eType);
	// if (ret)
	// return ret;
	// }
	//
	// return false;
	// }

	private void writeProps() {

		PrintWriter writer = getWriter();
		if (m_clzType.isInterface() && !m_staticProperties.isEmpty()) {			
			writeNewline();
			writeNewlineAndIndent();
			writer.append("public static final class PROPS{");
			writeNewlineAndIndent();
		}

		for (IJstProperty p : m_staticProperties) {
			if (!p.isPublic()) {
				continue;
			}
			if (m_clzType.isInterface() && (p.isFinal() || p.isStatic())) {
				writeStaticFieldForInterface(p, writer);
			} else if ((m_clzType.isClass() || m_clzType.isEnum() )&& p.isFinal()) {
				writeNewline();
				writePropertyGetter(p, false);
			} else {
				writeNewline();
				writePropertyGetter(p, false);
				writeNewline();
				writePropertySetter(p, false, false, false);

				// For serializable VJO objects
				if (m_vjoSerializableTypeMgr.m_fullNameMap.containsKey(p
						.getType().getName())) {
					writeNewline();
					writePropertySetter(p, false, false, true);
				}

				writeNewline();
				writePropertySetter(p, true, false, false);
			}
		}

		if (m_clzType.isInterface()&& !m_staticProperties.isEmpty()) {
			writeIndent();
			writer.append("}");
		
		}

		if (!m_clzType.isInterface()) {
			List<IJstMethod> writtenMethods = new ArrayList<IJstMethod>();
			// VJO supports static methods in interface, but java can't support
			for (MethodMeta meta : m_staticMethods) {
				if (!writtenMethods.contains(meta.m_method)) {
					writeMethods(meta);
					writtenMethods.add(meta.m_method);
				}
			}
			writtenMethods = null;
		}

		for (IJstProperty p : m_clzType.getEnumValues()) {
			writeStaticVariable(p, true);
		}
	}

	private void writeProtos() {
		List<IJstMethod> writtenMethods = new ArrayList<IJstMethod>();
		boolean isI = m_clzType.isInterface();
		for (IJstProperty p : m_instanceProperties) {
			if (!p.isPublic()) {
				continue;
			}
			writeNewline();
			writePropertyGetter(p, isI);
			if (!p.isFinal()) {
				writeNewline();
				writePropertySetter(p, false, isI, false);
				// For serializable VJO objects
				if (m_vjoSerializableTypeMgr.m_fullNameMap.containsKey(p
						.getType().getName())) {
					writeNewline();
					writePropertySetter(p, false, isI, true);
				}

				writeNewline();
				writePropertySetter(p, true, isI, false);
			}
		}
		for (MethodMeta meta : m_instanceMethods) {
			if (!writtenMethods.contains(meta.m_method)) {
				writeMethods(meta);
				writtenMethods.add(meta.m_method);
			}
		}
		writtenMethods = null;
	}

	private void writePropertyGetter(IJstProperty p, boolean isItForInterface) {
		PrintWriter writer = getWriter();
		writeNewlineAndIndent();
		// String primitive = getTypeSimpleName(p.getType(), Type.Primitive);
		String wrapper = getTypeSimpleName(p.getType(),
				isQualified(p.getType()));
		// if (!isItForInterface) { //ENABLE LATER
		writer.append("public ");
		// }
		if (p.isStatic()) {
			writer.append("static ");
		}
		if (p.isFinal()) {
			writer.append("final ");
		}
		writer.append("JsProp");
		writer.append("<").append(wrapper).append(">");
		String name = p.getName().getName();
		writer.append(" ").append(name);
		writer.append("()");
		if (isItForInterface) {
			writer.append(SEMI_COLON);
		} else {
			writer.append("{");
			indent();
			writeNewlineAndIndent();
			writer.append("return getProp(");
			if (p.isStatic()) {
				writer.append(JSOBJDATA_VAR_NAME).append(", ");
			}
			if (p.getType() instanceof JstTypeWithArgs
					|| p.getType() instanceof IJstRefType) {

				writer.append(classTemplateCast(wrapper));
			} else {
				writer.append(wrapper).append(".class");
			}
			writer.append(", \"").append(name).append("\"");
			writer.append(");");
			outdent();
			writeNewlineAndIndent();
			writer.append("}");
		}
	}

	private void writePropertySetter(IJstProperty p, boolean useBinding,
			boolean isItForInterface, boolean isNonProxyType) {
		PrintWriter writer = getWriter();
		writeNewlineAndIndent();
		// if (!isItForInterface) { //ENABLE LATER
		writer.append("public ");
		// }
		if (p.isStatic()) {
			writer.append("static ");
		}

		String typeName = getTypeSimpleName(p.getType(), isQualified(p
				.getType()), Type.Primitive);

		if (useBinding) {
			typeName = typeBinding(p.getType());
		}
		String name = p.getName().getName();
		writer.append("IJsPropSetter ").append(name).append("(").append(
				typeName).append(" v)");
		if (isItForInterface) {
			writer.append(SEMI_COLON);
		} else {
			writer.append(" {");
			indent();
			writeNewlineAndIndent();
			writer.append("return ").append("setProp(");
			if (p.isStatic()) {
				writer.append(JSOBJDATA_VAR_NAME);
				writer.append(", ");
			}
			writer.append("\"").append(name).append("\", ");
			writer.append("v);");
			outdent();
			writeNewlineAndIndent();
			writer.append("}");
		}
	}

	private void writeStaticFieldForInterface(IJstProperty property,
			PrintWriter writer) {

		String propType = getWrapperType(property.getType());
		writeIndent();
		// if (!isInterface) { //ENABLE LATER
		writer.append("public static final ");
		// }
		writer.append("JsProp<").append(propType).append("> ").append(
				property.getName().getName()).append("(){");
		writeNewlineAndIndent();
		writeIndent();
		writeIndent();
		writer.append("return new JsProp<").append(propType).append(">(")
				.append(JSOBJDATA_VAR_NAME).append(".getStaticAnchor(), ")
				.append("\"").append(property.getName().getName()).append(
						"\");");
		writeNewlineAndIndent();
		writeIndent();
		writer.append("}");
		writeNewline();

		/**
		 * public static class PROPS{ public static final JsProp<String> msg(){
		 * return new JsProp<String>(S.getStaticAnchor(), "msg"); } }
		 */

		// writer.append(" = new JsProp<").append(propType).append(">(")
		// .append(JSOBJDATA_VAR_NAME).append(".getStaticAnchor(), ")
		// .append("\"").append(property.getName().getName()).append(
		// "\")");

//		writer.append(SEMI_COLON);
	}

	private void writeStaticVariable(IJstProperty property, boolean isInterface) {
		PrintWriter writer = getWriter();
		writeNewline();
		writeNewlineAndIndent();
		String propType = getWrapperType(property.getType());
		// if (!isInterface) { //ENABLE LATER
		writer.append("public static final ");
		// }
		writer.append("JsProp<").append(propType).append("> ").append(
				property.getName().getName());
		writer.append("(){ return getProp(").append(JSOBJDATA_VAR_NAME).append(", ");
		if (property.getType() instanceof JstTypeWithArgs
				|| property.getType() instanceof IJstRefType) {
			writer.append(classTemplateCast(propType)).append(", ");
		} else {
			writer.append(propType).append(".class, ");
		}
		writer.append("\"").append(property.getName().getName()).append("\")");

		writer.append(SEMI_COLON);
		writer.append("}");
	}

	private void writeMethods(MethodMeta meta) {
		IJstMethod method = meta.m_method;
		if (method instanceof ISynthesized) {
			return;
		}

		IJstType rtnType = method.getRtnType();
		IJstType oType = null;
		if (rtnType != null && rtnType.getModifiers().isPrivate()) {
			return;
		}
		boolean isStatic = method.isStatic();
		boolean isOType = method.isOType();
		if (isOType) {
			StringBuilder funcRef = new StringBuilder();
			JstMethod m = (JstMethod) ((method instanceof JstProxyMethod) ? ((JstProxyMethod) method)
					.getTargetMethod()
					: method);
			oType = (IJstType) m.getOType().getParentNode();
			funcRef.append("public ");
			if (isStatic) {
				funcRef.append("static ");
			}
			writeNewlineAndIndent();
			writeNewlineAndIndent();
			String jsrName = getTypeName(m.getOType(), true);
			funcRef.append(jsrName).append(" ").append(method.getName())
					.append(" = new ").append(jsrName).append("(").append(
							(isStatic) ? JSOBJDATA_VAR_NAME : "this").append(
							COMMA).append("\"").append(method.getName())
					.append("\");");
			getWriter().append(funcRef);
		}
		StringBuilder methodDeclBegin = new StringBuilder();
		// if (!m_clzType.isInterface()) { //ENABLE LATER
		methodDeclBegin.append("public ");
		// }
		if (method.isAbstract()) {
			methodDeclBegin.append("abstract ");
		}
		if (isStatic) {
			methodDeclBegin.append("static ");
		}
		if (method.isFinal()) {
			methodDeclBegin.append("final ");
		}
		if (!method.getParamNames().isEmpty()) {
			methodDeclBegin.append(method.getParamsDecoration()).append(" ");
		}
		String methodName = m_config.getFilters().decorateMethod(method);
		// boolean isDecoratedMethod = (methodName == method.getOriginalName());
		boolean isDecoratedMethod = (methodName
				.equals(method.getOriginalName()));
		String rtnTypeName = getTypeSimpleName(rtnType, isQualified(rtnType),
				Type.AddParams);
		boolean isTypeRef = method.getRtnType() instanceof IJstRefType;
		
		boolean addExtends = !"Void".equals(rtnTypeName)
				&& !m_config.getFilters().isJavaPrimitiveOrWrapper(rtnTypeName)
				&& !rtnTypeName.endsWith("[]");
		String rtnJsFunc = "JsFunc<" + (addExtends ? "? extends " : "")
				+ rtnTypeName + ">";
		// }

		methodDeclBegin.append(rtnJsFunc).append(" ").append(methodName)
				.append("(");

		String rtnTypeClzStr;
		if (rtnType instanceof JstTypeWithArgs
				|| rtnType instanceof JstParamType
				|| isTypeRef
				|| (rtnType instanceof JstArray && (((JstArray) rtnType)
						.getComponentType() instanceof JstTypeWithArgs || ((JstArray) rtnType)
						.getComponentType() instanceof JstParamType))) {
			rtnTypeClzStr = classTemplateCast(rtnTypeName);
		} else {
			rtnTypeClzStr = rtnTypeName + ".class";
		}
		for (List<SimpleParam> paramList : meta.m_argListPermutation) {
			writeMethod(method, paramList, methodDeclBegin.toString(),
					rtnTypeClzStr, isDecoratedMethod, false, false, oType);
			boolean needIV = needValueBinding(paramList);
			if (needIV) {
				writeMethod(method, paramList, methodDeclBegin.toString(),
						rtnTypeClzStr, isDecoratedMethod, true, false, oType);
			}
			if (needNonProxyMethod(paramList)) {
				writeMethod(method, paramList, methodDeclBegin.toString(),
						rtnTypeClzStr, isDecoratedMethod, false, true, oType);
				/**
				 * Note: We are not going to create IValueBinding method for
				 * NonProxy method(Ex: param is Employee instead of EmployeeJsr)
				 * because the permutations would become complicated and number
				 * of methods in JSR can very high. If user really need
				 * IValueBinding for the non-proxy type then they can use a
				 * utily method(would be created) that would take non-proxy type
				 * and return IValueBinding...something like following
				 * IValueBinding<AddressJsr> iv = SomeUtil.getIV(new Address());
				 */
			}
		}
	}

	private boolean needNonProxyMethod(List<SimpleParam> paramList) {
		for (SimpleParam param : paramList) {
			Class<?> javaTypeForSerialable = m_config.getFilters()
					.getJavaTypeForSerialable(param.m_type);

			String name;
			if (javaTypeForSerialable == null) {
				name = param.m_type.getName();
			} else {
				name = javaTypeForSerialable.getName();
			}
			if (m_vjoSerializableTypeMgr.m_fullNameMap.containsKey(name)) {
				return true;
			}
		}
		return false;
	}

	private void writeMethod(IJstMethod method, List<SimpleParam> paramList,
			String methodDeclBegin, String rtnTypeClzStr,
			boolean isDecoratedMethod, boolean useValueBinding,
			boolean useNonProxyParams, final IJstType oType) {

		writeNewline();
		writeNewlineAndIndent();
		PrintWriter writer = getWriter();
		if (method.getDoc() != null) {
			// TODO Add formatter here
			writer.append("/**").append(getNewline());
			writer.append(method.getDoc().getComment());
			writer.append(getNewline()).append("*/").append(getNewline());
			writeNewline();
		}
		writer.append(methodDeclBegin);

		writeArgs(method.getOriginalName(), paramList, useValueBinding,
				useNonProxyParams, true, oType);

		writer.append(")");
		if (method.isAbstract() || m_clzType.isInterface()) {
			writer.append(SEMI_COLON);
		} else {
			writer.append("{");
			indent();
			writeNewlineAndIndent();

			writer.append("return call(");
			boolean isS = method.isStatic();
			if (isS) {
				writer.append(JSOBJDATA_VAR_NAME);
				writer.append(", ");
			}
			
			/* 
			 * To help handle event parameter to be passed
			 * into js code by browser but not by jsr
			 * this code automatically add the nativeEvent 
			 * to the param list to let jsr to js know that 
			 * event should be passed in as first argument.
			 *   
			 */
			
			boolean firstArgEvent = false;
			if(method.getArgs().size()>0){
				firstArgEvent = checkFirstArgForEvent(method.getArgs().get(0));
			}
			
			IJstType rtnType = method.getRtnType();

			if (rtnType == null
					|| "void".equals(rtnType.getName())
					|| (rtnType instanceof JstRefType && ((JstRefType) rtnType)
							.getRefType() == Void.class)) {
				// do nothing
			} else {
				// if (isS) {
				// writer.append(",");
				// }
				writer.append(rtnTypeClzStr).append(", ");
			}
			writer.append("\"").append(method.getOriginalName()).append("\"");
			writer.append(")");

			if (paramList.size() > 0) {
				writer.append(".with(");
				
			
				for (int i = 0; i < paramList.size(); i++) {
					SimpleParam param = paramList.get(i);
					
					if(i==0 && firstArgEvent && method.getArgs().get(0) != param.getArg()){
						writer.append("org.ebayopensource.dsf.resource.html.event.handler.JsHandlerObjectEnum.nativeEvent ");
						if(paramList.size()>=1){
							writer.append(",");
						}
						//continue;
					}
					
					if (i > 0) {
						writer.append(", ");
					}
					String pName = param.m_arg.getName();
					if (pName == null) {
						pName = DEFAULT_PARAM_PREFIX + i;
					}

					String argName = (param.m_mappedName == null) ? pName
							: param.m_mappedName;

					boolean booleanPrimitive = isBooleanPrimitive(param.m_type);
					boolean booleanWrapper = isBooleanWrapper(param.m_type);
					boolean jsrArray = isJsrArray(param);
					boolean addedMethodAroundArg = false;

					// if value binding and boolean primitive then check for
					// null state which is not allowed.
					if (useValueBinding && booleanPrimitive) {

						writer.append("check");
						if (method.isStatic()) {
							writer.append("S");
						}
						writer.append("(");
						addedMethodAroundArg = true;
					}

					boolean requireWrap = booleanWrapper||jsrArray;
					if (requiresWrap(useValueBinding, requireWrap)) {
						writer.append("wrap");
						if (method.isStatic()) {
							writer.append("S");
						}
						writer.append("(");
						addedMethodAroundArg = true;
					}

					if (!jsrArray && paramList.size() == 1) {
						String pType = getTypeSimpleName(param.m_type,
								Type.Primitive);
						if (pType.endsWith("[]") && !"Object[]".equals(pType)) {
							// Cast the arrays
							if (!useValueBinding) {
								writer.append("(Object)");
							}
						}
					}
					writer.append(argName);

					// close argument method
					if (addedMethodAroundArg) {
						writer.append(")");
					}

				}

				writer.append(")");
			}
			writer.append(SEMI_COLON);
			outdent();
			writeNewlineAndIndent();
			writer.append("}");
		}
	}

	private boolean isJsrArray(SimpleParam param) {
		
		
		
		
		if(m_JsToJavaMapper.isExcludedFromImport(param.m_arg.getType())){
			return false;
		}
		
		if(param.m_arg!=null && param.m_arg.getType() instanceof JstArray ){
			
			
			
			JstArray  arry = (JstArray)param.m_arg.getType();
			IJstType componentType = arry.getComponentType();
			if(componentType.getExtend()==null || componentType instanceof JstRefType){
				return false;
			}
			String groupName = componentType.getPackage().getGroupName();
			if(groupName.equals("JsNativeLib")|| groupName.equals("JavaPrimitive")){
				return false;
			}
			
			return true;
		}
		return false;
	}

	private boolean requiresWrap(boolean useValueBinding, boolean booleanWrapper) {
		return !useValueBinding && booleanWrapper;
	}

	private boolean checkFirstArgForEvent(JstArg jstArg) {
		
		
//		JstType dsfEventType = JstCache.getInstance().getType("vjo.dsf.Event");
		JstType eventType = JstCache.getInstance().getType("Event");
		if(jstArg!=null && jstArg.getType().equals(eventType)){
			return true;
		}
		return false;
	}

	private boolean isBooleanWrapper(IJstType type) {
		IJstType bool = JstCache.getInstance().getType("Boolean");
		if (bool != null && bool.equals(type)) {
			return true;
		}
		bool = JstCache.getInstance().getType(
				org.ebayopensource.dsf.jsnative.global.Boolean.class.getName());
		if (bool != null && bool.equals(type)) {
			return true;
		}
		return false;
	}

	private boolean isBooleanPrimitive(IJstType type) {
		IJstType bool = JstCache.getInstance().getType("boolean");
		if (bool != null && bool.equals(type)) {
			return true;
		}
		return false;
	}

	private void writeArgs(String name, List<SimpleParam> paramList,
			boolean useValueBinding, boolean useNonProxyType, boolean newLine,
			final IJstType oType) {
		PrintWriter writer = getWriter();
		int argSize = paramList.size();
		int firstParam = 0;
		for (int i = 0; i < argSize; i++) {
			SimpleParam param = paramList.get(i);
			if (param.m_mappedName != null) {
				firstParam++;
				continue;
			}
			if (i > firstParam) {
				writer.append(", ");
			}

			String pType = getTypeSimpleName(param.m_type,
					isQualified(param.m_type), Type.Primitive, Type.AddParams);

			if (useValueBinding) {
				pType = typeBinding(param.m_type);
			}

			writer.append(pType);

			if (param.m_arg.isVariable()) {
				writer.append("...");
			}
			String pName = param.m_arg.getName();
			// If no name available then create one
			if (pName == null) {
				pName = DEFAULT_PARAM_PREFIX + i;
			}
			writer.append(" ").append(pName);
		}
		if (useValueBinding && argSize > 0) {
			int index = getOverloadedIndex(name, argSize);
			if (index > 0) {
				writer.append(", ");
				if (newLine) {
					writeNewlineAndIndent();
					writeIndent();
					writeIndent();
				}
				writer.append(IClassR.DPkg).append(".D").append(
						String.valueOf(index)).append("... notUsed");
			}
		}
	}

	private Type isQualified(IJstType type) {

		if (type == null) {
			return null;
		}

		Type qualified = Type.Qualified;

		if (type instanceof JstObjectLiteralType
				|| type instanceof JstFunctionRefType) {
			return qualified;
		}

		IJstNode parentNode = type.getParentNode();
		IJstType outerType = m_clzType.getOuterType();
		if (type.isEmbededType() && outerType != null
				&& !parentNode.equals(outerType)) {
			return qualified;
		} else if (parentNode != null && outerType == null
				&& !m_clzType.equals(parentNode)) {
			return qualified;
		}
		return null;
	}

	private String getTypeRefType(String pType) {
		return IClassR.JsTypeRefSimpleName + "<" + pType + ">";
	}

	private void writeOTypes(List<IJstOType> otypes) {
		for (IJstOType otype : otypes) {
			if (otype instanceof JstObjectLiteralType) {
				writeOlType((JstObjectLiteralType) otype);
			} else if (otype instanceof JstFunctionRefType) {
				writeOlType((JstFunctionRefType) otype);
			}
			writeNewline();
		}
	}

	private void writeOlType(JstFunctionRefType otype) {
		writeOlType(otype, otype.getMethodRef());

		for (IJstMethod ovMtd : otype.getMethodRef().getOverloaded()) {
			writeOlType(otype, ovMtd);
		}
	}

	private void writeOlType(JstFunctionRefType otype, IJstMethod mtdRef) {
		indent();
		writeNewlineAndIndent();
		PrintWriter writer = getWriter();

		String jsrName = getTypeSimpleName(otype, Type.Wrapper);
		writer.append("public static class ").append(jsrName).append(
				" extends ").append(IClassR.JsFuncRefSimpleName).append("<")
				.append(getWrapperType(mtdRef.getRtnType())).append(">")
				.append(" { ");
		indent();
		writeNewlineAndIndent();
		writer.append(SERIAL_ID);
		writeNewline();
		writeNewlineAndIndent();
		writeOLConstructor(otype);

		outdent();
		writeNewlineAndIndent();
		writer.append("}");
		writeNewlineAndIndent();
		outdent();

	}

	private void writeOLConstructor(JstFunctionRefType otype) {
		PrintWriter writer = getWriter();

		String otypeName = getTypeSimpleName(otype, Type.Wrapper);
		writer.append("public ").append(otypeName).append(FNREF_JSOBJDATA_ARGS)
				.append("{");
		indent();
		writeNewlineAndIndent();
		writer.append(SUPER_FNREF_CALL);
		outdent();
		writeNewlineAndIndent();
		writer.append("}");
		writeNewlineAndIndent();

		writer.append("public ").append(otypeName).append(FNREF_JSOBJ_ARGS)
				.append("{");
		indent();
		writeNewlineAndIndent();
		writer.append(SUPER_FNREF_CALL);
		outdent();
		writeNewlineAndIndent();
		writer.append("}");
		writeNewlineAndIndent();
	}

	private void writeOlType(JstObjectLiteralType otype) {
		String otypeName = getTypeSimpleName(otype, Type.Wrapper);
		indent();
		writeNewlineAndIndent();
		PrintWriter writer = getWriter();
		writer.append("public static class ").append(otypeName).append(
				" extends ").append(IClassR.JsObjectLiteralSimpleName).append(
				" { ");
		indent();
		writeNewlineAndIndent();
		writer.append(SERIAL_ID);
		writeNewlineAndIndent();
		writeOLConstructor(otype);

		if (otype.getProperties().size() > 0)
			writeNewlineAndIndent();
		for (IJstProperty prop : otype.getProperties()) {
			String name = prop.getName().getName();
			String camelName = name.substring(0, 1).toUpperCase()
					+ name.substring(1);
			// String type = getTypeName(prop.getType(), false);
			// String setMethod = "set"+camelName;
			// writeObjectLiteralTypeSetter(name,setMethod,type,false);
			// writeObjectLiteralTypeSetter(name,setMethod,type,true);
			writeOLTypeGetter(name, "get" + camelName, prop.getType(), prop
					.getType() instanceof IJstRefType);

		}
		outdent();
		writeNewlineAndIndent();
		writer.append("}");
		writeNewlineAndIndent();
		outdent();
	}

	private void writeOLConstructor(JstObjectLiteralType otype) {
		// PrintWriter writer = getWriter();
		// TODO do we need a default constructor?
		// writer.append("private
		// ").append(otype.getSimpleName()).append(JSR_SUFFIX).append("(){");
		// indent();
		// for (IJstProperty prop : otype.getProperties()) {
		// writeNewlineAndIndent();
		// ISimpleTerm term = prop.getValue();
		// writeOLTypePut(prop.getName().getName(),term.toString());
		// }
		// outdent();
		// writeNewlineAndIndent();
		// writer.append("}");
		// writeNewlineAndIndent();
		// writeOLConstructor(otype, false);
		// if (otype.getProperties().size() != 0) {
		// writeOLConstructor(otype, true);
		// }

		String olName = getTypeSimpleName(otype, Type.Wrapper);

		writeOLContructorNew(olName, otype.getConstructor(), false);
		if (otype.getProperties().size() != 0) {
			writeOLContructorNew(olName, otype.getConstructor(), true);
		}
		for (IJstMethod overloaded : otype.getConstructor().getOverloaded()) {
			writeOLContructorNew(olName, overloaded, false);
			if (overloaded.getArgs().size() != 0) {
				writeOLContructorNew(olName, overloaded, true);
			}
		}
	}

	private void writeOLContructorNew(String olName, IJstMethod constructor,
			boolean iValueBinding) {
		PrintWriter writer = getWriter();
		writeNewlineAndIndent();
		// writer.append("//-> ");
		// for (JstArg arg : constructor.getArgs()) {
		// writer.append(arg.getType().getName()).append(" ").append(arg.getName()).append(", ");
		// }
		// writeNewlineAndIndent();

		writer.append("private ").append(olName).append("(");
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (JstArg prop : constructor.getArgs()) {
			if (!first) {
				sb.append(COMMA).append(" ");
			}
			String type = getTypeSimpleName(prop.getType(), isQualified(prop
					.getType()), Type.Primitive);
			sb.append((iValueBinding) ? typeBinding(prop.getType()) : type)
					.append(" ").append(prop.getName());
			first = false;
		}
		writer.append(sb).append("){");
		indent();
		first = true;

		StringBuilder sb2 = new StringBuilder();
		for (JstArg prop : constructor.getArgs()) {
			if (!first) {
				sb2.append(COMMA);
			}
			first = false;
			String name = prop.getName();
			sb2.append(name);
			writeNewlineAndIndent();
			writeOLTypePut(name, name, prop.getType(), iValueBinding);
		}
		outdent();
		writeNewlineAndIndent();
		writer.append("}");
		writeNewlineAndIndent();
		writeOLCreateMethod(olName, sb.toString(), sb2.toString());

	}

	/*
	 * Above method replaces this private void
	 * writeOLConstructor(JstObjectLiteralType otype, boolean iValueBinding) {
	 * PrintWriter writer = getWriter();
	 * writer.append("private ").append(otype.getSimpleName()).append(
	 * JSR_SUFFIX).append("("); StringBuilder sb = new StringBuilder(); boolean
	 * first = true; for (IJstProperty prop : otype.getProperties()) { if
	 * (!first) { sb.append(COMMA).append(" "); } String type =
	 * getTypeName(prop.getType(), false); sb.append((iValueBinding) ?
	 * typeBinding(type) : type).append(" ") .append(prop.getName().getName());
	 * first = false; } writer.append(sb).append("){"); indent(); first = true;
	 * 
	 * StringBuilder sb2 = new StringBuilder(); for (IJstProperty prop :
	 * otype.getProperties()) { if (!first) { sb2.append(COMMA); } first =
	 * false; String name = prop.getName().getName(); sb2.append(name);
	 * writeNewlineAndIndent(); writeOLTypePut(name, name); } outdent();
	 * writeNewlineAndIndent(); writer.append("}"); writeNewlineAndIndent();
	 * writeOLCreateMethod(otype.getSimpleName() + JSR_SUFFIX, sb.toString(),
	 * sb2.toString()); }
	 */

	private void writeOLCreateMethod(final String name,
			final String paramsDecl, final String params) {
		PrintWriter writer = getWriter();
		writer.append("public static ").append(name).append(" obj(").append(
				paramsDecl).append(") {");
		indent();
		writeNewlineAndIndent();
		writer.append("return new ").append(name).append("(").append(params)
				.append(")").append(SEMI_COLON);
		outdent();
		writeNewlineAndIndent();
		writer.append("}");
		writeNewlineAndIndent();
	}

	// private void writeOLTypeSetter(final String propName,
	// final String methodName, final String type, boolean iValueBinding) {
	// PrintWriter writer = getWriter();
	// writer.append("public void ").append(methodName).append("(")
	// .append((iValueBinding)? typeBinding(type) : type)
	// .append(" ").append(propName).append(") {");
	// indent();
	// writeNewlineAndIndent();
	// writeOLTypePut(propName,propName);
	// outdent();
	// writeNewlineAndIndent();
	// writer.append("}");
	// writeNewlineAndIndent();
	// }
	//
	private void writeOLTypePut(final String propName, final String propVal,
			IJstType iJstType, boolean iValueBinding) {
		PrintWriter writer = getWriter();
		writer.append("put(\"").append(propName).append("\",");
		if (!m_JsToJavaMapper.isLiteral(iJstType) && !iValueBinding) {
			writer.append(propName).append(".getClass()").append(",");
		}

		writer.append(propVal).append(");");
	}

	private void writeOLTypeGetter(final String propName,
			final String methodName, final IJstType type, boolean isTypeRef) {
		PrintWriter writer = getWriter();
		writer.append("public ").append(typeBinding(type)).append(" ").append(
				methodName).append("() {");
		indent();
		writeNewlineAndIndent();
		String typeStr = getWrapperType(type);
		writer.append("return BV.bind(").append(
				(isTypeRef) ? classTemplateCast(typeStr) : typeStr + ".class")
				.append(", (").append(getQualifiedType(type)).append(")get(\"")
				.append(propName).append("\"));");
		outdent();
		writeNewlineAndIndent();
		writer.append("}");
		writeNewlineAndIndent();
	}

	private void writeTypeRef() {
		writeNewlineAndIndent();
		writeNewlineAndIndent();
		String typeRef = IClassR.JsTypeRefSimpleName + "<"
				+ m_clzType.getSimpleName() + JSR_SUFFIX + ">";
		getWriter().append("public static ").append(typeRef).append(
				" prototype = new ").append(typeRef).append("(S);");
	}

	private void processJsrTypeImports() {
		addJsrTypeImport(m_clzType.getImports(), false);
		// addJsrTypeInactiveImport(m_clzType.getInactiveImports());
		addJsrTypeImport(m_clzType.getExtends(), true);
		addJsrTypeImport(m_clzType.getSatisfies(), true);
		addJsrTypeImport(m_clzType.getExpects(), true);
		addJsrTypeImportFromParams(m_clzType.getParamTypes());

		// processDefaultGenericsImports(m_clzType.getExtends());
		// processDefaultGenericsImports(m_clzType.getSatisfies());
	}

	private void addJsrTypeImportFromParams(List<? extends IJstType> paramTypes) {
		for (IJstType itm : paramTypes) {
			IJstType iaimp = m_clzType.getInactiveImport(itm.getName());
			boolean added = false;
			if (iaimp == null) {
				iaimp = m_clzType.getInactiveImport(itm.getSimpleName());
			}
			if (iaimp != null) {
				addJsrTypeImport(iaimp, true, false);
				added = true;
			}
			if (!added) {
				// It is less likely it reaches here.
				IJstType imp = m_clzType.getImport(itm.getName());
				if (imp == null) {
					imp = m_clzType.getImport(itm.getSimpleName());
				}
				if (imp != null) {
					addJsrTypeImport(imp, true, true);
				}
			}
			if (itm instanceof JstParamType) {
				JstParamType itmParam = (JstParamType) itm;
				if (!itmParam.getBounds().isEmpty()) {
					addJsrTypeImportFromParams(itmParam.getBounds());
				}
			}
		}
	}

	// private void processDefaultGenericsImports(List<? extends IJstType>
	// types) {
	// for (IJstType type : types) {
	// // Default generic type
	// if (!(type instanceof JstTypeWithArgs)
	// && type.getParamTypes().size() > 0) {
	// m_needsJsObj = true;
	// return;
	// }
	// }
	// }

	private void processMixins(Set<IJstType> typeSet) {
		List<? extends IJstTypeReference> mixins = m_clzType.getMixinsRef();
		for (IJstTypeReference ref : mixins) {
			IJstType mixinType = ref.getReferencedType();
			addJsrTypeImport(mixinType, false);
			for (IJstType imp : mixinType.getImports()) {
				addJsrTypeImport(imp, false);
			}
			for (IJstType inactiveImp : mixinType.getInactiveImports()) {
				if (!(inactiveImp.isOType())) {
					addJsrTypeImport(inactiveImp, false);
				}
				m_inactiveNeedsFromMixin.put(inactiveImp.getSimpleName(),
						inactiveImp);
			}
		}
	}

	private void processInnersForJsrType() {
		if (!isSameGroup(m_clzType.getRootType(), m_clzType)) {
			return;
		}

		Comparator<IJstType> comparator = new Comparator<IJstType>() {
			public int compare(IJstType o1, IJstType o2) {
				return o1.getName().compareTo(o2.getName());
			}
		};
		SortedSet<IJstType> space = new TreeSet<IJstType>(comparator);
		loadLocalTypeSpace(m_clzType.getRootType(), space);
		loadLocalTypeSpace(m_clzType, space);

		// List<? extends IJstType> embeded = m_clzType.getEmbededTypes();

		if (space.isEmpty())
			return;

		IJstType top = space.first();
		for (IJstType type : space) {
			if (comparator.compare(top, type) > 0)
				continue;

			SortedSet<IJstType> subSet = space.subSet(top, type);
			int len = subSet.size();
			IJstType[] array = new IJstType[len];
			subSet.toArray(array);

			int i = len;
			IJstType test = null;
			for (; i > 0; i--) {
				test = array[i - 1];
				if (checkInnerByName(type.getName(), test.getName())) {// Inner
					// check
					break;
				}
			}

			if (i != 0) {// type is a static inner class of test
				JstType inner = unwrapJstType(type);
				JstType outer = unwrapJstType(test);
				if (inner != null && outer != null) {
					if (inner.getOuterType() == null) {
						inner.setOuterType(outer);
						outer.addInnerType(inner);
					}
				}
			} else {// type is not an inner class
				top = type;
			}

		}

		// embeded = m_clzType.getEmbededTypes();
	}

	private boolean isSameGroup(IJstType type1, IJstType type2) {
		JstPackage pkg1 = type1.getPackage();
		JstPackage pkg2 = type2.getPackage();
		if (pkg1 == null && pkg2 == null)
			return true;
		if (pkg1 == null && pkg2 != null)
			return false;
		if (pkg1 != null && pkg2 == null)
			return false;

		String grp1 = pkg1.getGroupName(); // KEEPME
		String grp2 = pkg2.getGroupName();
		if (grp1 == null && grp2 == null)
			return true;
		if (grp1 == null && grp2 != null)
			return false;
		if (grp1 != null && grp2 == null)
			return false;
		return grp1.equals(grp2); // KEEPME
	}

	private boolean checkInnerByName(String innerName, String outerName) {
		if (innerName.indexOf(outerName) == 0) {
			String remainder = innerName.substring(outerName.length());
			return remainder.indexOf('.') == 0;
		}
		return false;
	}

	private void loadLocalTypeSpace(IJstType type, Set<IJstType> space) {
		space.add(type);
		space.addAll(type.getImports());
		space.addAll(type.getExtends());
		space.addAll(type.getSatisfies());
		space.addAll(type.getExpects());
		List<? extends IJstType> embeds = type.getEmbededTypes();
		for (IJstType embed : embeds) {
			loadLocalTypeSpace(embed, space);
		}
	}

	private JstType unwrapJstType(IJstType type) {
		if (type instanceof JstType) {
			return (JstType) type;
		}
		if (type instanceof JstProxyType) {
			return unwrapJstType(((JstProxyType) type).getType());
		}
		return null;
	}

	private void collectTypeFromProperties(Set<IJstType> typeSet,
			List<IJstProperty> properties, boolean isOnlyCollectType,
			boolean isInterface) {
		// getWriter().append("/* PROPERTIES:");
		for (IJstProperty prop : properties) {
			// getWriter().append("\n>>" + prop.getName().getName());
			if (!prop.isPublic()
					|| prop instanceof ISynthesized
					|| (prop instanceof JstProxyProperty && ((JstProxyProperty) prop)
							.getTargetProperty() instanceof ISynthesized)) {
				continue;
			}
			m_hasProp = true;
			if (!prop.isFinal() && !isInterface) {
				m_needsPropSetter = true;
				if (!isMappedEventType(prop.getType())) {
					m_needsIValueBinding = true;
				}
				// else {
				// m_hasMappedEvent = true;
				// }
			}

			if (!isOnlyCollectType) {
				if (prop.isStatic()) {
					m_staticProperties.add(prop);
				} else {
					m_instanceProperties.add(prop);
				}
			}

			if (m_clzType.isOType()
					&& (m_clzType.getInactiveImport(prop.getType()
							.getSimpleName()) != null)) {
				addJsrTypeImport(m_clzType.getInactiveImport(prop.getType()
						.getSimpleName()), true);
			} else if (m_clzType.isOType() && prop.getType().isImpliedImport()) {
				addJsrTypeImport(prop.getType(), true, false);
			}

			collectType(prop.getType(), typeSet);
			// if (VJO_OBJECT.equals(prop.getType().getName())) {
			// m_oTypeNeedsJsObj = true;
			// }

		}
		// getWriter().append("\n*/\n");
	}

	private void collectTypeFromMethods(Set<IJstType> typeSet,
			List<? extends IJstMethod> methods, boolean isOnlyCollectType) {
		// include static, instance methods and overloaded constructors
		for (IJstMethod method : methods) {
			if (m_clzType.isMixin() || method instanceof ISynthesized) {
				continue;
			}
			collectTypeFromMethod(method, typeSet, isOnlyCollectType);
		}
	}

	private boolean isExists(List<MethodMeta> list, IJstMethod method) {
		for (MethodMeta meta : list) {
			if (isEqual(meta.m_method, method)) {
				return true;
			}
		}
		return false;
	}

	private void collectTypeFromMethod(IJstMethod jstMethod,
			Set<IJstType> typeSet, boolean isOnlyCollectType) {

		if (jstMethod instanceof ISynthesized) {
			return;
		}

		IJstMethod method = jstMethod;
		if (jstMethod instanceof JstProxyMethod) {
			method = ((JstProxyMethod) jstMethod).getTargetMethod();
		}
		List<MethodMeta> metaList = m_constructors;
		if (method == null) {
			return;
		}
		if (!method.isPublic()) {
			return;
		}
		JstType otype = null;
		if (method.isOType()) {
			otype = (JstType) ((JstMethod) method).getOType().getParentNode();
		}
		if (method.isDispatcher()) {
			// skipt vjo's dispatcher method for overloading methods and
			// constructors
			if (method.isConstructor()) {
				metaList = m_constructors;
			} else if (method.isStatic()) {
				metaList = m_staticMethods;
			} else {
				metaList = m_instanceMethods;
			}
			List<IJstMethod> olconstrs = method.getOverloaded();
			if (olconstrs != null) {
				boolean setRtnType = false;
				for (IJstMethod cons : olconstrs) {

					if (isExists(metaList, cons)) {

						if (method instanceof JstMethod) {
							if (!setRtnType) {
								JstMethod jstMethod2 = (JstMethod) method;
								jstMethod2.setRtnType(JstCache.getInstance()
										.getType("Object", false));
								setRtnType = true;
							}
						}

						continue;
					}

					List<JstArg> args = cons.getArgs();
					List<Stack<SimpleParam>> olParamsPermutation = collectArgTypesAndPermutation(
							0, args, typeSet, otype);
					if (cons.isPublic() && !isOnlyCollectType) {
						metaList.add(new MethodMeta(cons, olParamsPermutation));
					}
					if (!method.isConstructor()) {
						m_hasFunction = true;
						IJstType returnType = cons.getRtnType();
						if (returnType != null) {
							if (isPublic(returnType)) {
								collectType(returnType, typeSet);
							}
						}
					}
				}
				if (!isDispatcher(method) && !isExists(metaList, method)) {
					List<Stack<SimpleParam>> paramsPermutation = collectArgTypesAndPermutation(
							0, method.getArgs(), typeSet, otype);
					if (!isOnlyCollectType) {
						MethodMeta methodMeta = new MethodMeta(method,
								paramsPermutation);
						if (method.isPublic() && !method.isPrivate()
								&& !method.isProtected()) {
							metaList.add(methodMeta);
						}
					}
				}
			}
			return;
		}

		if (!method.isConstructor()) {
			m_hasFunction = true;

			IJstType returnType = method.getRtnType();

			if (returnType != null) {
				if (!isPublic(returnType)) {
					return;
				}

				if (m_clzType.isOType()
						&& m_clzType.getInactiveImport(returnType
								.getSimpleName()) != null) {
					addJsrTypeImport(m_clzType.getInactiveImport(returnType
							.getSimpleName()), false);
				} else if (otype != null
						&& otype.getInactiveImport(returnType.getSimpleName()) != null) {
					addJsrTypeImport(otype.getInactiveImport(returnType
							.getSimpleName()), false);
				} else {
					addAsInactiveImport(returnType);
				}

				collectType(returnType, typeSet);
			}
			if (method.isStatic()) {
				metaList = m_staticMethods;
			} else {
				metaList = m_instanceMethods;
			}
		}

		List<Stack<SimpleParam>> paramsPermutation = collectArgTypesAndPermutation(
				0, method.getArgs(), typeSet, otype);
		if (paramsPermutation == null) {
			return;
		}
		if (!isOnlyCollectType) {
			MethodMeta methodMeta = new MethodMeta(method, paramsPermutation);
			// if(!isExists(metaList,method)){
			metaList.add(methodMeta);
			// }

		}
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

	private void collectType(IJstType type, Set<IJstType> typeSet) {

		if (type instanceof IJstRefType) {
			m_needsTypeRef = true;
			typeSet.add(type);
			addJsrTypeImport(((IJstRefType) type).getReferencedNode(), true);
			return;
			// }
		} else if (type instanceof IJstOType) {
			IJstType otype = (IJstType) ((IJstOType) type).getParentNode();
			typeSet.add(otype);
			addJsrTypeImport(otype, true);
		} else if (type.isImpliedImport()) {
			addJsrTypeImport(type, true, true);

		} else {

			// If the import is in inactive-imports list
			addAsInactiveImport(type);
		}

		if (typeSet.contains(type)) {
			return;
		}
		if (type instanceof JstArray) {
			type = ((JstArray) type).getElementType();
		}
		if (!type.getModifiers().isPublic()) {
			return;
		}
		typeSet.add(type);

		if (type instanceof JstTypeWithArgs) {
			JstTypeWithArgs jstTypeWArgs = (JstTypeWithArgs) type;
			for (IJstType argType : jstTypeWArgs.getArgTypes()) {
				// System.out.println(argType.getName());

				collectType(argType, typeSet);

				if (argType instanceof JstParamType) {
					JstParamType wctype = (JstParamType) argType;
					for (IJstType boundedType : wctype.getBounds()) {
						collectType(boundedType, typeSet); // RECURSIVE
					}
				}

			}
		}

		String name = type.getName();
		// if (!m_needsJsEnum && VJO_ENUM.equals(name)) {
		// m_needsJsEnum = true;
		// }

		// if (!m_needsJsObj && VJO_OBJECT.equals(name)) {
		// m_needsJsObj = true;
		// }
		// if (!m_needsJsClass && VJO_CLASS.equals(name)) {
		// m_needsJsClass = true;
		// }

		if (m_config.getFilters().isJsr(name) && name != null
				&& !name.equals(m_clzType.getName())
				&& !m_config.getFilters().isJavaWrapper(type.getSimpleName())
		// && !(VJO_OBJECT.equals(name) || VJO_CLASS.equals(name) || VJO_ENUM
		// .equals(name))
		) {
			// Check if its native types
			Class<?> clz = JsNativeMeta.getClass(type.getSimpleName());
			if (clz != null && !isInCurrentTypeDependencies(type)) {
				String typeName = clz.getName();
				String simpleName = clz.getSimpleName();
				Alias alias = clz.getAnnotation(Alias.class);
				if (alias != null) {
					typeName = clz.getPackage().getName() + "." + alias.value();
					simpleName = alias.value();
				}

				String proxyName = getNativeProxyType(type);
				if (proxyName != null) {
					typeName = proxyName;
					simpleName = proxyName
							.substring(proxyName.lastIndexOf(".") + 1);
				}

				// If it's native type then use corresponding Java type in JSR
				String javaName = DataTypeHelper
						.getJavaTypeNameForNative(typeName);
				if (javaName != null) {
					typeName = javaName;
				}
				m_jsNativeTypeMgr.add(typeName, simpleName, true);
			}
		}

		// See if it requires serialization utils
		// if (null != m_config.getFilters().getJavaTypeForSerialable(type)) {
		// addSerializeUtil(type);
		// }
	}

	private boolean isInCurrentTypeDependencies(IJstType type) {
		if (m_jsrTypeMgr.get(type.getName() + JSR_SUFFIX) != null) {
			return true;
		}
		return false;
	}

	private void addAsInactiveImport(IJstType type) {
		if (type instanceof JstArray) {
			JstArray ary = (JstArray) type;
			type = ary.getElementType();
		}

		if (m_JsToJavaMapper.isMappedEventType(type)) {
			addJsrTypeImport(type, true, false, false, true);
			m_jsrTypeMgr.add(IClassR.JsHandlerObjectEnum,
					"JsHandlerObjectEnum", false, true, true);
			// m_jsrTypeMgr.add(getJsrNameForInner(type, false),
			// getJsrNameForInner(type, true), usedFullName, isInactive,
			// usedInDecl);
			return;
		}

		if (type.isImpliedImport() || m_JsToJavaMapper.isInactiveImport(type)) {
			addJsrTypeImport(type, !m_JsToJavaMapper.shouldImport(type), false);
			return;
		}

		while (type.getOuterType() != null) {
			type = type.getOuterType();
		}

		IJstType inactiveImport = m_clzType.getInactiveImport(type.getName());
		if (inactiveImport == null) {
			inactiveImport = m_clzType.getInactiveImport(type.getSimpleName());
		}
		if (inactiveImport != null) {
			addJsrTypeImport(inactiveImport, true, false);
		} else {
			addJsrTypeImportJavaAPI(type);
		}

	}

	private void addJsrTypeImportJavaAPI(IJstType inactiveImport) {
		// TODO Auto-generated method stub
		return;

		// if(inactiveImport.getPackage()!=null &&
		// inactiveImport.getPackage().getGroupName().equals(LibManager.JAVA_PRIMITIVE_LIB_NAME)){
		// return;
		// }
		//    	
		// m_jsrTypeMgr.add(inactiveImport.getName(),inactiveImport.getSimpleName(),
		// false, true);
		//    	
	}

	// private void addSerializeUtil(IJstType type) {
	// //Assuming that ArrayList's util would be ArrayListUtil
	// String utilName = type.getName() + SERIALIZE_UTIL_SUFFIX + JSR_SUFFIX;
	// String utilSimpleName = type.getSimpleName() + SERIALIZE_UTIL_SUFFIX +
	// JSR_SUFFIX;
	// m_jsrTypeMgr.add(utilName, utilSimpleName, false);
	// }

	// private void addJsrTypeImports(List<? extends IJstType> types, boolean
	// usedInDecl) {
	// if (types == null) {
	// return;
	// }
	// for (IJstType type : types) {
	// if(!m_JsToJavaMapper.isInactiveImport(type)){
	// addJsrTypeImport(type, usedInDecl);
	// }
	//			
	// }
	// }
	private void addJsrTypeImport(List<? extends IJstType> types,
			boolean usedInDecl) {
		if (types == null) {
			return;
		}
		for (IJstType type : types) {
			if (!m_config.getFilters().isSkipExtends(type)
					&& !m_config.getFilters().isSkipSatisfies(type)) {
				addJsrTypeImport(type, usedInDecl);
			}
		}
	}

	private void addJsrTypeImport(IJstType type, boolean usedInDecl) {
		// System.out.println("IMPORTING = "+ type.getName());
		boolean isInactive = false;
		if (m_JsToJavaMapper.isInactiveImport(type)) {
			isInactive = true;
		}
		if (m_JsToJavaMapper.shouldImport(type)) {
			isInactive = false;
		}

		addJsrTypeImport(type, isInactive, usedInDecl);
	}

	private void addJsrTypeImport(IJstType type, boolean isInactive,
			boolean usedInDecl) {
		String name = type.getAlias();
		if (isMappedEventType(type)) {
			// m_hasMappedEvent = true;
			return;
		}

		if (!shouldExcludeFromImport(type)) {
			boolean usedFullName;
			if (isInactive) {
				usedFullName = m_clzType.getInactiveImportsMap().containsKey(
						name);
			} else {
				usedFullName = m_clzType.getImportsMap().containsKey(name);
			}

			if (m_JsToJavaMapper.isInactiveImport(type)) {
				isInactive = true;
			}

			if (m_JsToJavaMapper.shouldImport(type)) {
				isInactive = false;
			}

			// m_jsrTypeMgr.add(name + JSR_SUFFIX, type.getSimpleName()
			// + JSR_SUFFIX, usedFullName);
			addJsrTypeImport(type, isInactive, usedFullName, false, false);
		}
	}

	private void addJsrTypeImport(IJstType type, boolean isInactive,
			boolean usedFullName, boolean isImportOnly, boolean usedInDecl) {
		m_jsrTypeMgr.add(getJsrNameForInner(type, false), getJsrNameForInner(
				type, true), usedFullName, isInactive, usedInDecl);
	}

	private String getJsrNameForInner(IJstType type, boolean needSimpleName) {

		// Type placement = Type.Wrapper;

		if (needSimpleName) {
			String javaTypeSimpleName = getTypeSimpleName(type, Type.Wrapper);
			// if(DEBUG){
			// System.out.println(javaTypeSimpleName);
			// }
			return javaTypeSimpleName;
			// return type.getSimpleName() + JSR_SUFFIX;
		} else {
			String javaTypeFullName = getTypeName(type, isQualified(type));
			// if(DEBUG){
			// System.out.println(javaTypeFullName);
			// }
			return javaTypeFullName;

			// String typeName = type.getName();
			// if(!typeName.contains(".")){
			// typeName = type.getAlias().substring(0,
			// type.getAlias().lastIndexOf('.'))+"." + type.getSimpleName();
			// }
			//        	
			// String nativeType = getNativeProxyType(typeName);
			// if(nativeType!=null){
			// return nativeType;
			// }
			//        	
			// String name = type.getSimpleName() + JSR_SUFFIX;
			// IJstType tmp = type;
			// while (tmp.getOuterType() != null) {
			// tmp = tmp.getOuterType();
			// name = tmp.getSimpleName() + JSR_SUFFIX + "." + name;
			// }
			// JstPackage pkg = tmp.getPackage();
			// if(pkg != null) {
			// name = pkg.getName() + "." + name;
			// }
			// return name;
		}
	}

	private void addJavaTypeImport(Set<IJstType> typeSet) {
		for (IJstType type : typeSet) {
			boolean usedFullName = m_clzType.getImportsMap().containsKey(
					type.getName());
			if (!isJsrType(type)) {
				if (type instanceof JstRefType) {
					// TODO FIX_LATER remove this tmp logic fot java.util.Date
					if (type.getName().startsWith("java.util")) {
						m_javaTypeMgr.add(type.getName(), type.getSimpleName(),
								usedFullName);
					} else {
						continue;
					}
				}
				if (!shouldExcludeFromImport(type)) {
					m_javaTypeMgr.add(type.getName(), type.getSimpleName(),
							usedFullName);
				} else {
					// System.out.println(type.getName());
				}
			}
			if (m_config.getFilters().isSerializableForVjo(type, true)) {
				Class<?> javaTypeForSerialable = m_config.getFilters()
						.getJavaTypeForSerialable(type);
				if (javaTypeForSerialable != null) {
					m_vjoSerializableTypeMgr.add(javaTypeForSerialable
							.getName(), javaTypeForSerialable.getSimpleName(),
							usedFullName);
				} else {
					m_vjoSerializableTypeMgr.add(type.getName(), type
							.getSimpleName(), usedFullName);
				}
			}
		}
	}

	private static final Stack<SimpleParam> EMPTY_STACK = new Stack<SimpleParam>();

	/**
	 * It recursively builds the permutation of param lists, where the size of
	 * the return list is the permutation size and each stack inside the return
	 * list represents one permutation.
	 * 
	 * The recursion starts with index = 0
	 * 
	 * During the analysis, it also collects data type into typeSet.
	 */
	@SuppressWarnings("unchecked")
	private List<Stack<SimpleParam>> collectArgTypesAndPermutation(int index,
			List<JstArg> args, Set<IJstType> typeSet, final JstType otype) {

		List<Stack<SimpleParam>> paramStackPermutation = new ArrayList<Stack<SimpleParam>>();
		if (index == args.size()) { // empty params
			paramStackPermutation.add(EMPTY_STACK);
			return paramStackPermutation;
		}

		JstArg arg = args.get(index);
		boolean isTypeRef = arg.getType() instanceof IJstRefType;
		List<? extends IJstType> argTypes = arg.getTypes();
		boolean needsIValueBinding = false;
		if (index == args.size() - 1) { // last param
			for (IJstType argType : argTypes) {
				if (!isPublic(argType)) {
					return null;
				}
				if (otype != null
						&& !m_clzType.isOType()
						&& otype.getInactiveImport(argType.getSimpleName()) != null) {
					addJsrTypeImport(otype.getInactiveImport(argType
							.getSimpleName()), false);
				} else {
					addAsInactiveImport(argType);
				}
				boolean isObjectType = m_JsToJavaMapper.isObject(argType);
				boolean isMappedEventType = isMappedEventType(argType);
				boolean isBrowserEventType = isBrowserEventType(argType);

				// if (isBrowserEventType || isMappedEventType) {
				// collectType(argType, typeSet);
				//                	 
				// }
				collectType(argType, typeSet);

				if (args.size() == 1 && !isObjectType
						&& m_JsToJavaMapper.supportsValueBinding(argType)) {
					m_needsIValueBinding = true;

				} else if (args.size() > 1
						&& m_JsToJavaMapper.supportsValueBinding(argType)) {
					m_needsIValueBinding = true;
				}

				// if(args.size()==1 && isObjectType ){
				// m_needsIValueBinding = false;
				// }

				Stack<SimpleParam> paramStack = new Stack<SimpleParam>();

				SimpleParam simpleParam = processSimpleParam(arg, isTypeRef,
						argType);
				String mappedEventName = getMappedEventName(argType
						.getSimpleName());
				if (mappedEventName != null) {

					/*
					 * foo(Event) foo(vjo.dsf.Event)
					 * 
					 * translate foo(EventJsr) foo() foo(IValueBinding<EventJsr>
					 */

					simpleParam.setMappedName(mappedEventName);
					Stack<SimpleParam> eventParamStack = createEventArgStack(
							arg, isTypeRef, argType);
					Stack<SimpleParam> eventParamStack2 = createEventArgStack(
							arg, isTypeRef, JstFactory.getInstance()
									.createJstType(IClassR.JsHandlerObjectEnum,
											true));
					paramStackPermutation.add(eventParamStack);
					paramStackPermutation.add(eventParamStack2);
					addJsrTypeImport(argType, true, true);

					// m_hasMappedEvent = true;
				}

				paramStack.push(simpleParam);

				paramStackPermutation.add(paramStack);
			}
		} else {
			List<Stack<SimpleParam>> tList = collectArgTypesAndPermutation(
					index + 1, args, typeSet, otype);
			if (tList == null) {
				return null;
			}
			int size = argTypes.size();

			boolean hasMappedEvent = false;

			for (int i = 0; i < size; i++) {
				IJstType argType = argTypes.get(i);

				if (!isObjectType(argType) && !isMappedEventType(argType)) {
					if (m_JsToJavaMapper.supportsValueBinding(argType)) {
						needsIValueBinding = true;
					}

					collectType(argType, typeSet);
				} else {
					collectType(argType, typeSet);
					hasMappedEvent = true;
					needsIValueBinding = false;
				}
				SimpleParam param = new SimpleParam(arg, argType, arg
						.isOptional(), isTypeRef);
				for (Stack<SimpleParam> paramStack : tList) {
					if (i != size - 1) {
						// clone stack for new permutations, reuse the existing
						// stack for the last argType
						paramStack = (Stack<SimpleParam>) paramStack.clone();
					}
					String mappedEventName = getMappedEventName(argType
							.getSimpleName());
					if (mappedEventName != null) {

						paramStackPermutation.add(paramStack);

						Stack<SimpleParam> eventParamStack = (Stack<SimpleParam>) paramStack
								.clone();
						Stack<SimpleParam> eventParamStack2 = (Stack<SimpleParam>) paramStack
								.clone();
						eventParamStack = createEventArgStack(arg, isTypeRef,
								argType, eventParamStack);

						eventParamStack2 = createEventArgStack(arg, isTypeRef,
								JstFactory.getInstance().createJstType(
										IClassR.JsHandlerObjectEnum, true),
								eventParamStack2);
						paramStackPermutation.add(eventParamStack);
						paramStackPermutation.add(eventParamStack2);
						addJsrTypeImport(argType, true, true);

						// paramStack.push(param);
						// paramStackPermutation.add(paramStack);

					} else {

						paramStack.push(param);
						paramStackPermutation.add(paramStack);
					}
				}
			}
			if (!m_needsIValueBinding) {
				m_needsIValueBinding = needsIValueBinding;
			}
			// m_hasMappedEvent = hasMappedEvent;

		}
		return paramStackPermutation;
	}

	private Stack<SimpleParam> createEventArgStack(JstArg arg,
			boolean isTypeRef, IJstType argType) {
		return createEventArgStack(arg, isTypeRef, argType,
				new Stack<SimpleParam>());
	}

	private Stack<SimpleParam> createEventArgStack(JstArg arg,
			boolean isTypeRef, IJstType argType,
			Stack<SimpleParam> eventParamStack) {
		SimpleParam paramWithOutMapping = processSimpleParam(arg, isTypeRef,
				argType);
		eventParamStack.push(paramWithOutMapping);
		return eventParamStack;
	}

	private SimpleParam processSimpleParam(JstArg arg, boolean isTypeRef,
			IJstType argType) {
		SimpleParam simpleParam = new SimpleParam(arg, argType, arg
				.isOptional(), isTypeRef);
		return simpleParam;
	}

	private boolean isBrowserEventType(IJstType argType) {
		return m_JsToJavaMapper.isBrowserEventType(argType);
	}

	private static boolean isDuplicate(List<List<SimpleParam>> existedLists,
			List<SimpleParam> theList) {
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

	private String getJsrNameForTypeDecl(IJstType type) {
		String name;
		String jsrNameForInner = getJsrNameForInner(type, false);
		TypeMeta meta = m_jsrTypeMgr.get(jsrNameForInner);
		if (meta == null) {
			throw new RuntimeException(" JSR name = " + jsrNameForInner
					+ " typename = " + type.getName() + " should not be in "
					+ m_clzType.getName());
		}
		if (meta.m_useFullName) {
			name = meta.m_fullName;
		} else {
			name = meta.m_simpleName;
		}
		if (type instanceof JstTypeWithArgs) {
			name += getArgsDecoration((JstTypeWithArgs) type);
		} else {
			name += getDefaultArgsDecoration(type.getParamTypes());
		}
		return name;
	}

	// private String getInnerDecoratedJsrName(IJstType outerType,
	// String innerName, boolean needFullPath) {
	// StringBuffer s = new StringBuffer();
	// if (innerName == null)
	// return "";
	//
	// if (innerName.indexOf(outerType.getName()) == 0) {
	// String tmp = innerName.substring(outerType.getName().length() + 1,
	// innerName.length());
	// if (needFullPath) {
	// s.append(outerType.getName()).append(JSR_SUFFIX).append(".");
	// }
	// s.append(tmp.replace(".", JSR_SUFFIX + "."));
	// s.append(JSR_SUFFIX);
	// }
	// return s.toString();
	// }

	private String getTypeName(IJstType type, boolean isNonProxy) {
		return getTypeName(m_clzType, type, isNonProxy);
	}

	@SuppressWarnings("unchecked")
	private String getTypeName(IJstType type, Type... typeMapping) {

		TypeMeta tm = m_jsrTypeMgr.get(type.getName());
		if (tm != null && !tm.m_useFullName) {
			m_JsToJavaMapper.getJavaTypeSimpleName(type, typeMapping);
		}
		return m_JsToJavaMapper.getJavaTypeFullName(type, typeMapping);

	}

	@SuppressWarnings("unchecked")
	private String getTypeSimpleName(IJstType type, Type... typeMapping) {

		// // USE FULL NAME WHEN THERE IS NO EXTENDED TYPE FROM JS OBJECT OR
		// VJO.OBJECT

		if (type != null) {
			// if( type.isClass() && type.getExtends().size()==0 && !(type
			// instanceof JstObjectLiteralType)){
			// return getTypeName(type, typeMapping);
			// }

			TypeMeta tm = m_jsrTypeMgr.get(type.getName());
			if (tm != null && tm.m_useFullName) {
				m_JsToJavaMapper.getJavaTypeFullName(type, typeMapping);
			}
		}

		return m_JsToJavaMapper.getJavaTypeSimpleName(type, typeMapping);
	}

	private String getTypeName(IJstType currentType, IJstType type,
			boolean isNonProxy) {
		return m_JsToJavaMapper.getJavaTypeFullName(type, Type.Wrapper);

		//    	
		//    	
		// if (currentType==null) {
		// currentType = m_clzType;
		// }
		// if (type == null) {
		// System.err.println("Type info not found in " +
		// currentType.getName());
		// return "Void";
		// }
		// String nm = type.getName();
		// String snm = type.getSimpleName();
		//
		// boolean isNativeType = false;
		// if (type instanceof IJstRefType) {
		// IJstType target = ((IJstRefType<IJstType>)type).getReferencedNode();
		// nm = target.getName();
		// snm = target.getSimpleName();
		// return getTypeRefType(snm + JSR_SUFFIX);
		// }
		//
		// if ("Object".equals(nm)) {
		// return NATIVE_OBJECT;
		// }
		//        
		// if ("void".equals(nm)) {
		// return "Void";
		// }
		// // if (VJO_OBJECT.equals(nm)) {
		// // return JS_OBJ;
		// // }
		// // if (VJO_CLASS.equals(nm)) {
		// // return JS_CLASS;
		// // }
		// // if (VJO_ENUM.equals(nm)) {
		// // return JS_ENUM;
		// // }
		// //Use simple name for primitive types.
		// if (m_config.getFilters().isJavaLang(nm) &&
		// m_config.getFilters().isJavaPrimitive(snm)) {
		// return snm;
		// }
		// String tmp = type.getName();
		// Class<?> javaTypeForSerialable =
		// m_config.getFilters().getJavaTypeForSerialable(type);
		// if (javaTypeForSerialable != null) {
		// tmp = javaTypeForSerialable.getName();
		// }
		// String suffix = ((isNonProxy &&
		// m_vjoSerializableTypeMgr.m_fullNameMap
		// .containsKey(tmp)) ? "" : JSR_SUFFIX);
		// if (type == currentType) {
		// return type.getSimpleName() + suffix;
		// }
		// if (type instanceof JstArray) {
		// return getTypeName(((JstArray) type).getComponentType(), false)
		// + "[]";
		// }
		// TypeMeta meta = null;
		// if (meta == null) {
		// boolean isInnerType = (currentType.getRootType() != currentType);
		// if (isJsrType(type)) {
		// boolean addToJavaTypes = true;
		// if (!m_config.getFilters().isJavaWrapper(snm)) {
		// // Check for native type first
		// Class<?> clz;
		// if (nm.equals(snm)) {
		// clz = JsNativeMeta.getClass(nm);
		// } else {
		// clz = DataTypeHelper.getNativeType(nm);
		// }
		//
		// if (clz != null) {
		// addToJavaTypes = false;
		// isNativeType = true;
		//					
		//						
		// String typeName = clz.getName();
		// Alias alias = clz.getAnnotation(Alias.class);
		// if(alias!=null)
		// typeName = clz.getPackage().getName() +"." + alias.value();
		// String proxyName = getNativeProxyType(typeName);
		// if (proxyName != null) {
		// typeName = proxyName;
		// }
		// meta = m_jsNativeTypeMgr.get(typeName);
		// if (meta == null && isInnerType) {
		// meta = m_rootTypeGenerator.m_jsNativeTypeMgr.get(nm
		// + suffix);
		// }
		// }
		// }
		// // Check to see if it's Serializable VJO type
		// if (isNonProxy) {
		// Class<?> clz = m_config.getFilters().getJavaTypeForSerialable(type);
		// if (clz != null) {
		// meta = m_vjoSerializableTypeMgr.get(clz.getName());
		// } else {
		// meta = m_vjoSerializableTypeMgr.get(nm);
		// }
		// if (meta == null && isInnerType) {
		// meta = m_rootTypeGenerator.m_vjoSerializableTypeMgr
		// .get(nm);
		// }
		// if (meta != null) {
		// addToJavaTypes = false;
		// }
		// }
		//
		// if (addToJavaTypes) {
		// meta = m_jsrTypeMgr.get(nm + suffix);
		// if (meta == null && isInnerType) {
		// meta = m_rootTypeGenerator.m_jsrTypeMgr
		// .get(nm + suffix);
		// }
		// }
		// } else {
		// if (meta == null) {
		// meta = m_javaTypeMgr.get(nm);
		// if (meta == null && isInnerType) {
		// meta = m_rootTypeGenerator.m_jsrTypeMgr
		// .get(nm + suffix);
		// }
		// }
		// }
		// }
		// String name = "";
		// if (meta != null) {
		// if (meta.m_useFullName) {
		// name = meta.m_fullName;
		// } else {
		// name = meta.m_simpleName;
		// }
		// } else {
		// if (type instanceof JstProxyType
		// && ((JstProxyType) type).getType() == currentType) {
		// name = currentType.getSimpleName() + suffix;
		// } else {
		// // Look up inner types
		// IJstType innType = searchInnerTypes(currentType, nm, name);
		// if (innType != null) {
		// name = getInnerDecoratedJsrName(currentType, innType
		// .getName(), false);
		//
		// } else if (m_clzType.isOType() && isInnerOType(currentType,nm)) {
		// name = snm + suffix;
		// } else {
		// int idx = nm.lastIndexOf(".");
		// int currentIdx = 0;
		// while (idx > -1) {
		// // Logic may have to change if data type is
		// // IJsSerializable
		// currentIdx = idx;
		// String rootName = nm.substring(0, currentIdx);
		// String clzName = (rootName.indexOf(".") != -1) ? rootName
		// .substring(rootName.lastIndexOf(".") + 1, rootName.length())
		// : rootName;
		//
		// IJstType clzType = currentType;
		// if (clzType.isEmbededType()) {
		// while (clzType.getOuterType()!=null) {
		// clzType = clzType.getOuterType();
		// }
		// }
		// // Add jsr sufficix. Container type is added as a
		// // dependency
		// if (clzType.getImport(clzName) != null
		// || clzType.getInactiveImport(clzName) != null
		// || m_inactiveNeedsFromMixin.get(clzName) != null) {
		// if (suffix.length() > 0) {
		// nm = clzName
		// + suffix
		// + "."
		// + nm.substring(currentIdx + 1).replace(
		// ".", suffix + ".") + suffix;
		// } else {
		// nm = clzName + "."
		// + nm.substring(currentIdx + 1);
		// }
		//
		// break;
		// }
		// idx = rootName.lastIndexOf(".");
		// }
		// name = nm;
		// }
		// }
		// }
		// if (type instanceof JstTypeWithArgs) {
		// name += getArgsDecoration((JstTypeWithArgs) type);
		// }
		// IJstType clzType = currentType;
		// if (clzType.isEmbededType()) {
		// while (clzType.getOuterType()!=null) {
		// clzType = clzType.getOuterType();
		// }
		// }
		// if (!isNativeType
		// && !isNonProxy
		// && !(type instanceof JstTypeWithArgs)
		// && !(meta !=null && meta.m_useFullName)
		// && (clzType.getInactiveImport(name) != null
		// || type.isImpliedImport()
		// || clzType.getInactiveImport(snm) != null
		// || m_inactiveNeedsFromMixin.get(name) != null
		// || m_inactiveNeedsFromMixin.get(snm) != null)) {
		// return snm + JSR_SUFFIX;
		// }
		// return name;
	}

	private boolean isInnerOType(final IJstType currentType, final String name) {
		if (currentType.isOType() && currentType.getOTypes().size() > 0) {
			for (IJstOType otype : currentType.getOTypes()) {
				if (otype instanceof IJstType) {
					IJstType type = (IJstType) otype;
					if (name.equals(type.getName())
							|| name.equals(type.getSimpleName())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// private IJstType searchInnerTypes(IJstType type, String nm,
	// String decoratedName) {
	// if (type.getEmbededTypes().size() > 0) {
	// for (IJstType itm : type.getEmbededTypes()) {
	// if (nm.equals(itm.getName())) {
	// return itm;
	// } else {
	// IJstType innType = searchInnerTypes(itm, nm, decoratedName);
	// if (innType != null) {
	// return innType;
	// }
	// }
	// }
	// }
	// return null;
	// }

	private boolean shouldExcludeFromImport(IJstType type) {
		if (type == null) {
			throw new NullPointerException(
					"Invalid import type, JstType cannot be null");
		}
		if (type.getName() == null) {
			throw new NullPointerException("JstType cannot have a null name");
		}

		if (m_JsToJavaMapper.isExcludedFromImport(type)) {
			return true;
		}

		if (m_importExclusionList.contains(type)) {
			return true;
		}
		if (!shouldAcceptImport(type.getName()) || skipJsrImport(type)
				|| isObjectType(type)) {
			m_importExclusionList.add(type);
			return true;
		}
		return false;
	}

	// private boolean isNativeType(IJstType t) {
	// String name = t.getName();
	// return (!DataTypeHelper.isJavaMappedToNative(name)
	// && DataTypeHelper.getNativeType(name) != null);
	// }

	private boolean shouldAcceptImport(String typeName) {
		if (m_customJsrProvider == null) {
			return true;
		}
		return m_customJsrProvider.shouldAcceptImport(typeName);
	}

	private boolean skipJsrImport(IJstType type) {
		return m_config.getFilters().isSkipImportJsr(type);
	}

	private boolean isJsrType(IJstType type) {
		return m_config.getFilters().isJsr(type.getName());
	}

	private static boolean isPublic(IJstType type) {
		if (type instanceof JstArray) {
			type = ((JstArray) type).getElementType();
		}
		if (type instanceof JstParamType) {
			return true;
		}
		return type.getModifiers().isPublic();
	}

	private boolean needValueBinding(List<SimpleParam> list) {
		return m_JsToJavaMapper.supportsValueBinding(list);

	}

	private String typeBinding(final IJstType typeName) {
		String typeName2 = getQualifiedType(typeName);
		boolean addExtends = !typeName2.endsWith("[]")
				&& !"Void".equals(typeName2)
				&& (m_hasGenericParams || !m_config.getFilters()
						.isJavaPrimitiveOrWrapper(typeName2));
		return "IValueBinding<" + (addExtends ? "? extends " : "") + typeName2
				+ ">";

		// return "IValueBinding<" + getWrapperType(typeName) + ">";
	}

	private String getNewJsObjDataString(IJstType jstType, String jsrName) {
		IJstType outer = jstType;
		if (outer.isEmbededType()) {// embedded type should reference top level
			// file name
			outer = jstType.getOuterType();
			while (outer.getOuterType() != null) {
				outer = outer.getOuterType();
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append("new JsObjData(\"").append(jstType.getName()).append("\", ")
				.append(jsrName).append(".class, \"").append(
						outer.getSimpleName()).append("\"");
		if (m_enableScriptingJava) {
			sb.append(", true");
		}
		sb.append(")");
		return sb.toString();
	}

	private boolean isObjectType(IJstType type) {
		// Note: If user did not specify the package then we assume it's
		// java.lang.Object
		String name = DataTypeHelper.getTypeName(type.getName());
		if ("java.lang.Object".equals(name)
				|| NATIVE_OBJECT.equals(name)
				|| org.ebayopensource.dsf.jsnative.global.Object.class.getName().equals(
						name)) {
			return true;
		}

		if (type instanceof JstRefType) {
			return ((JstRefType) type).getRefType() == Object.class;
		}
		return false;
	}

	private boolean isMappedEventType(IJstType type) {
		return m_JsToJavaMapper.isMappedEventType(type);

	}

	private String getMappedEventName(String typeName) {
		return (m_customJsrProvider == null) ? null : m_customJsrProvider
				.getMappedEvent(typeName);
	}

	private String getWrapperType(IJstType type) {
		return m_JsToJavaMapper.getJavaTypeSimpleName(type, Type.Wrapper,
				isQualified(type));
	}

	private String getQualifiedType(IJstType type) {
		// if(type.isClass() && type.getExtends().size()==0){
		// return m_JsToJavaMapper.getJavaTypeFullName(type, Type.AddParams);
		// }

		return m_JsToJavaMapper.getJavaTypeSimpleName(type, isQualified(type),
				Type.AddParams);
	}

	private String getParamsDecoration(IJstType type) {
		if (type.getParamNames().isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder("<");
		int i = 0;
		IJstType bType;
		for (JstParamType p : type.getParamTypes()) {
			if (i++ > 0) {
				sb.append(",");
			}
			sb.append(getTypeName(p, false));
			if (!p.getBounds().isEmpty()) {
				bType = p.getBounds().get(0);
				sb.append(" extends ").append(
						getTypeSimpleName(bType, Type.Qualified));
				// if (bType instanceof JstTypeWithArgs) {
				// sb.append(getParamsDecoration(bType));
				// }
			}
		}
		sb.append(">");

		return sb.toString();
	}

	private String getArgsDecoration(JstTypeWithArgs templatedType) {
		StringBuilder sb = new StringBuilder(OPEN_ANGLE_BRACKET);
		int i = 0;
		for (IJstType p : templatedType.getArgTypes()) {
			if (i++ > 0) {
				sb.append(COMMA);
			}
			if (p instanceof JstWildcardType) {
				sb.append(QUESTION_MARK);
				if (!JstWildcardType.DEFAULT_NAME.equals(p.getSimpleName())) {
					if (((JstWildcardType) p).isUpperBound()) {
						sb.append(" extends ").append(getTypeName(p, false));
					} else if (((JstWildcardType) p).isLowerBound()) {
						sb.append(" super ").append(getTypeName(p, false));
					}
				}
			} else {
				sb.append(getTypeName(p, false));
			}
		}
		sb.append(CLOSE_ANGLE_BRACKET);
		return sb.toString();
	}

	private String getDefaultArgsDecoration(List<JstParamType> params) {
		if (params.size() == 0)
			return "";

		StringBuilder sb = new StringBuilder(OPEN_ANGLE_BRACKET);
		for (int i = 0; i < params.size(); i++) {
			if (i > 0) {
				sb.append(COMMA);
			}
			sb.append(NATIVE_OBJECT);
		}
		sb.append(CLOSE_ANGLE_BRACKET);
		return sb.toString();
	}

	private static final Map<String, String> s_javaWrapperTypes = new HashMap<String, String>();
	static {
		s_javaWrapperTypes.put(boolean.class.getName(), Boolean.class
				.getSimpleName());
		s_javaWrapperTypes
				.put(byte.class.getName(), Byte.class.getSimpleName());
		s_javaWrapperTypes.put(short.class.getName(), Short.class
				.getSimpleName());
		s_javaWrapperTypes.put(int.class.getName(), Integer.class
				.getSimpleName());
		s_javaWrapperTypes
				.put(long.class.getName(), Long.class.getSimpleName());
		s_javaWrapperTypes.put(float.class.getName(), Float.class
				.getSimpleName());
		s_javaWrapperTypes.put(double.class.getName(), Double.class
				.getSimpleName());
		s_javaWrapperTypes.put(char.class.getName(), Character.class
				.getSimpleName());
	}

	private int getOverloadedIndex(String methodName, int argSize) {
		String key = methodName + "_" + argSize;
		Integer value = m_overloadedMap.get(key);
		int index = 0;
		if (value != null) {
			index = value.intValue() + 1;
		}
		m_overloadedMap.put(key, Integer.valueOf(index));
		return index;
	}

	public JsrGenerator writeNewlineAndIndent() {
		super.writeNewline().writeIndent();
		return this;
	}

	private void writePkg() {
		// http://quickbugstage.arch.ebay.com/show_bug.cgi?id=3014
		if (m_clzType.getPackage() != null
				&& m_clzType.getPackage().getName().length() > 0) {
			getWriter().append(PACKAGE)
					.append(m_clzType.getPackage().getName())
					.append(SEMI_COLON);
			writeNewline();
		}
	}

	private static class TypeMetaMgr {
		private Map<String, TypeMeta> m_fullNameMap = new LinkedHashMap<String, TypeMeta>();
		private Map<String, TypeMeta> m_simpleNameMap = new LinkedHashMap<String, TypeMeta>();
		private IJsrFilters m_jsrFilters = null;

		TypeMetaMgr() {
		}

		TypeMetaMgr(IJsrFilters jsrFilters) {
			m_jsrFilters = jsrFilters;
		}

		TypeMeta get(String fullName) {
			return m_fullNameMap.get(fullName);
		}

		void add(String fullName, String simpleName, boolean usedFullName) {
			add(fullName, simpleName, usedFullName, false, false);
		}

		void add(String fullName, String simpleName, boolean usedFullName,
				boolean isInactive, boolean usedInDecl) {
			if (!m_fullNameMap.containsKey(fullName)) {
				TypeMeta meta = new TypeMeta(fullName, simpleName,
						usedFullName, isInactive, usedInDecl);
				if (m_jsrFilters != null) {
					meta.m_fullNameInJava = m_jsrFilters.normalize(fullName);
				}
				add(meta);
			}
		}

		private void add(TypeMeta type) {

			if (m_simpleNameMap.get(type.m_simpleName) != null) {
				// simpleName conflict
				type.m_useFullName = true;
			} else {
				m_simpleNameMap.put(type.m_simpleName, type);
			}

			m_fullNameMap.put(type.m_fullName, type);
		}

		void addToSimpleNameMap(TypeMeta type) {
			m_simpleNameMap.put(type.m_simpleName, type);
		}

		Iterator<TypeMeta> getMetaItr() {
			return m_fullNameMap.values().iterator();
		}
	}

	private static class TypeMeta {
		String m_fullName;
		String m_simpleName;
		String m_fullNameInJava;
		boolean m_useFullName = false;
		boolean m_inactiveType = false;
		boolean m_usedInDecl = false;

		TypeMeta(String fullName, String simpleName, boolean usedFullName) {
			this(fullName, simpleName, usedFullName, false, false);
		}

		TypeMeta(String fullName, String simpleName, boolean usedFullName,
				boolean isInactiveType, boolean usedInDecl) {
			m_fullName = fullName;
			m_simpleName = simpleName;
			m_fullNameInJava = m_fullName;
			m_inactiveType = isInactiveType;
			if (usedFullName || simpleName.equals(fullName)) {
				m_useFullName = true;
			}
		}

		public String toString() {
			return m_fullName + "[" + m_fullNameInJava + "]";
		}
	}

	private static class MethodMeta {
		IJstMethod m_method;
		List<List<SimpleParam>> m_argListPermutation;

		@SuppressWarnings("unchecked")
		MethodMeta(final IJstMethod method,
				final List<Stack<SimpleParam>> params) {

			m_method = method;
			m_argListPermutation = new ArrayList<List<SimpleParam>>(params
					.size());
			for (Stack<SimpleParam> paramStack : params) {
				List<SimpleParam> argList = new ArrayList<SimpleParam>(
						paramStack.size());
				m_argListPermutation.add(argList);
				while (!paramStack.isEmpty()) {
					argList.add(paramStack.pop());
				}
			}
//			List<JstArg> args = method.getArgs();
//			int size = args.size();
//			int firstOptional = size;
//			for (int i = size - 1; i >= 0; i--) {
//				if (args.get(i).isOptional()) {
//					firstOptional = i;
//				} else {
//					break;
//				}
//			}
//			if (firstOptional < size) {
//				// temp list for deduping
//				List<List<SimpleParam>> newlyAddedPermutations = new ArrayList<List<SimpleParam>>();
//
//				// create permutations for optional params
//				int permutationSize = m_argListPermutation.size();
//				for (int i = 0; i < permutationSize; i++) {
//					List<SimpleParam> argList = m_argListPermutation.get(i);
//					for (int j = size - 1; j >= firstOptional; j--) {
//						// create new list by remove the last optional param
//						argList = (List<SimpleParam>) ((ArrayList<SimpleParam>) argList)
//								.clone();
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

	public int getInitialIndent() {
		return m_initialIndent;
	}

	public void setInitialIndent(int indent) {
		m_initialIndent = indent;
	}

	private String classTemplateCast(String typeName) {
		return "(Class<" + typeName + ">)null";
	}

	private boolean isEqual(IJstMethod source, IJstMethod target) {
		List<JstArg> sourceArgs = source.getArgs();
		List<JstArg> targetArgs = target.getArgs();
		int sourceArgsCount = sourceArgs.size();
		int targetArgsCount = targetArgs.size();
		if (!source.getOriginalName().equals(target.getOriginalName())) {
			return false;
		}
		if (sourceArgsCount == 0 && targetArgsCount == 0) {
			return true;
		} else if (sourceArgsCount == targetArgsCount) {
			for (int i = 0; i < sourceArgsCount; i++) {

				IJstType sourceType = sourceArgs.get(i).getType();
				IJstType targetType = targetArgs.get(i).getType();

				if (m_JsToJavaMapper.isMappedEventType(sourceType)
						&& m_JsToJavaMapper.isMappedEventType(targetType)) {
					return true;
				}

				if (!sourceType.equals(targetType)) {
					return false;
				}
			}

			return true;
		}
		return false;
	}

	// private static final Map<String, String> s_nativeToProxyMapping = new
	// HashMap<String, String>();
	// static {
	// s_nativeToProxyMapping.put(IClassR.NativeArrayName,
	// IClassR.ArrayProxyName);
	// s_nativeToProxyMapping.put(IClassR.ObjLiteralName,
	// IClassR.OlName);
	// }

	private String getNativeProxyType(IJstType nativeType) {

		return m_JsToJavaMapper.getJavaTypeFullName(nativeType, Type.Wrapper);

		// return s_nativeToProxyMapping.get(nativeType);
	}

	public IJsrTypeProvider getJsToJavaMapper() {
		return m_JsToJavaMapper;
	}

	public void setJsToJavaMapper(IJsrTypeProvider jsToJavaMapper) {
		m_JsToJavaMapper = jsToJavaMapper;
	}
}
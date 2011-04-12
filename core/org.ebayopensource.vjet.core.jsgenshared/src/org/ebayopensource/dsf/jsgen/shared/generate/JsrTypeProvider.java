/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.generate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.global.Object;
import org.ebayopensource.dsf.jst.IJstAnnotation;
import org.ebayopensource.dsf.jst.IJstRefType;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArray;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstFunctionRefType;
import org.ebayopensource.dsf.jst.declaration.JstObjectLiteralType;
import org.ebayopensource.dsf.jst.declaration.JstParamType;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstTypeWithArgs;
import org.ebayopensource.dsf.jst.declaration.JstWildcardType;
import org.ebayopensource.dsf.jst.util.DataTypeHelper;

public class JsrTypeProvider implements IJsrTypeProvider {

	private static final String JS_HANDLER_OBJECT_ENUM = "org.ebayopensource.dsf.resource.html.event.handler.JsHandlerObjectEnum";
	private static final String JSR = "Jsr";
	private static final String IDAPSVCCALLBACK = "org.ebayopensource.dsf.dap.svc.IDapSvcCallback";
//	private static final boolean DEBUG = false;
	private static final String EVENTPACKAGE = "org.ebayopensource.dsf.jsnative.events";
	private static final String OPEN_ANGLE_BRACKET = "<";
	private static final String COMMA = ",";
	private static final String QUESTION_MARK = "?";
	private static final String CLOSE_ANGLE_BRACKET = ">";
	private static final String[] LITERALS = {"boolean","byte","char","double", "int","float","long","short"};
	Map<String, Mapping> jsToJavaMapping = new LinkedHashMap<String, Mapping>();

	private boolean m_init;
	private Map<String, String> jsToJsrPackageMap = new LinkedHashMap<String, String>();

	private IJstType m_currentType;
	private boolean m_dot;
	private boolean m_enableTypeMapping = true;
	

	
	private void initTypeMapping() {
		jsToJavaMapping.put("org.ebayopensource.dsf.jsnative.global.Boolean",
				createMapping("Boolean", "Boolean"));
		jsToJavaMapping.put("org.ebayopensource.dsf.jsnative.global.Number",
				createMapping("Number"));
		jsToJavaMapping.put("org.ebayopensource.dsf.jsnative.global.Date",
				createMapping("Date","java.util.Date"));
		jsToJavaMapping.put("org.ebayopensource.dsf.jsnative.global.Object",
				createMapping("Object"));
		jsToJavaMapping.put("org.ebayopensource.dsf.jsnative.global.ObjLiteral",
				createMapping("Object"));
		jsToJavaMapping.put("org.ebayopensource.dsf.jsnative.global.String",
				createMapping("String"));
		
		jsToJavaMapping.put("vjo.dsf.Message", createMapping("vjo.dsf.MessageJsr"));
//		jsToJavaMapping.put("vjo.dsf.Event", createMapping(JS_HANDLER_OBJECT_ENUM));
		
		// TODO remove these and use vjo.EnumJsr instead kept for compatibility
		jsToJavaMapping.put("vjo.Enum", createMapping("JsEnum", "org.ebayopensource.dsf.aggregator.jsref.JsEnum"));
		jsToJavaMapping.put("Enum", createMapping("JsEnum", "org.ebayopensource.dsf.aggregator.jsref.JsEnum"));
		
		
//		// artificial types
//		jsToJavaMapping.put("vjo.java.lang.Util", createMapping("UtilJsr", "vjo.java.lang.UtilJsr"));
		
		// TODO problem with java 2 js not using jst type name
		jsToJavaMapping.put("org.ebayopensource.dsf.jsnative.HtmlElement", 
				createMapping("com.ebay.jsbrowser.jsr.HTMLElementJsr"));
		jsToJavaMapping.put("org.ebayopensource.dsf.jsnative.HtmlDocument", 
				createMapping("com.ebay.jsbrowser.jsr.HTMLDocumentJsr"));

		jsToJavaMapping.put("java.lang.Boolean", createMapping("Boolean",
				"Boolean"));
		jsToJavaMapping.put("java.lang.Number", createMapping("double"));
		jsToJavaMapping.put("java.util.Date", createMapping("Date", "java.util.Date"));
		jsToJavaMapping.put("java.lang.Object", createMapping("Object"));
		jsToJavaMapping.put("org.ebayopensource.dsf.dap.proxy.OL",
				createMapping("Object"));
		jsToJavaMapping.put("java.lang.String", createMapping("String"));

		// TEMP mapping
//		jsToJavaMapping.put(JS_HANDLER_OBJECT_ENUM,
//				createMapping(JS_HANDLER_OBJECT_ENUM));
		jsToJavaMapping.put(IDAPSVCCALLBACK, createMapping(IDAPSVCCALLBACK));

		jsToJavaMapping.put("Boolean", createMapping("Boolean", "Boolean"));
		jsToJavaMapping.put("boolean", createMapping("boolean", "Boolean"));
		jsToJavaMapping.put("byte", createMapping("byte", "Byte"));
		jsToJavaMapping.put("char", createMapping("char", "Character"));
		jsToJavaMapping.put("double", createMapping("double", "Double"));
		jsToJavaMapping.put("int", createMapping("int", "Integer"));
		jsToJavaMapping.put("float", createMapping("float", "Float"));
		jsToJavaMapping.put("long", createMapping("long", "Long"));
		jsToJavaMapping.put("short", createMapping("short", "Short"));
		// jsToJavaMapping.put("Integer", createMapping("int", "Integer" ));
		jsToJavaMapping.put("Date", createMapping("Date", "java.util.Date"));
		jsToJavaMapping.put("Number", createMapping("java.lang.Number"));
		jsToJavaMapping.put("Object", createMapping("Object"));
		 jsToJavaMapping.put("vjo.Object",createMapping("Object" ));
		jsToJavaMapping.put("ObjLiteral", createMapping("Object"));
		jsToJavaMapping.put("String", createMapping("String", "String"));
		jsToJavaMapping.put("void", createMapping("Void", "Void"));
		
		
	}

//	private Mapping createMapping(String primitiveType, String wrapperType, boolean isInactive) {
//		Mapping mapping = new Mapping();
//		mapping.primitiveType = primitiveType;
//		mapping.wrapperType = wrapperType;
//		mapping.isInactive = isInactive;
//		return mapping;
//	}

	
	private void initPackageMapping() {
		jsToJsrPackageMap.put("org.ebayopensource.dsf.jsnative.global",
				"com.ebay.jsnative.jsr");
		jsToJsrPackageMap.put("org.ebayopensource.dsf.jsnative.events",
				"com.ebay.jsbrowser.jsr");
		jsToJsrPackageMap
				.put("org.ebayopensource.dsf.jsnative", "com.ebay.jsbrowser.jsr");
		// jsToJsrPackageMap .put("vjo", "com.ebay.vjobootstrap.jsr" );
	}

	static class Mapping {
		String primitiveType;
		String wrapperType;
		public boolean isInactive = true;
	}

	private Mapping createMapping(String both) {
		return createMapping(both, both);
	}

	private Mapping createMapping(String primitiveType, String wrapperType) {
		Mapping mapping = new Mapping();
		mapping.primitiveType = primitiveType;
		mapping.wrapperType = wrapperType;
		return mapping;
	}

	@Override
	public String getJavaTypeFullName(IJstType type, Type ... t) {
		return getJavaFullName(type, true, t);

	}

	@Override
	public String getJavaTypeSimpleName(final IJstType type, Type ... t) {
		return getJavaFullName(type, false, t);

	}
	
	private String getJavaFullName(IJstType type, boolean isFull, Type... t) {
		boolean primitive = false;
		boolean qualified = false;
		boolean addParams = false;
		boolean ignoretypemap = false;
		
		for(Type atype: t){
			if(atype==null){
				continue;
			}
			if (atype == Type.Qualified) {
				qualified = true;
			}
			
			if (atype.equals(Type.Primitive)) {
				primitive = true;
			}
			if (atype.equals(Type.IgnoreTypeMap)) {
				ignoretypemap= true;
			}
			if (atype.equals(Type.AddParams)) {
				addParams = true;
			}
			
		}
		
		if(type==null || type.getAlias()==null){
			
				if(primitive){
					return "void";
				}
				return "Void";
		}
		
		
		
		return getJavaTypeName(type, isFull, qualified, primitive,ignoretypemap, addParams);
	}

	private String getJavaTypeName(final IJstType type, boolean isFull,
			boolean isQualified, boolean isPrimitive, boolean ignoretypemap, boolean addParams) {
		if (!m_init) {
			init();
		}
		String suffix = JSR;
		String typeName = type.getAlias();
		String simpleTypeName = type.getSimpleName();
		String arraySuffix = "";
		
		if(type instanceof JstWildcardType ){
			
			
			JstWildcardType wctype = (JstWildcardType)type;
			
			
			if(wctype.getType() instanceof JstParamType){
				return type.getName();
			}
		}
		
		
		if( type instanceof JstParamType){
			if(isFull){
				return typeName;
				
			}
			return simpleTypeName;
		}
	
		
		
		boolean isAry = false;
		if (type instanceof JstArray) {
			isAry = true;
			JstArray ary = (JstArray) type;

			for (int i = 0; i < ary.getDimensions(); i++) {
				arraySuffix = arraySuffix + "[]";
			}

//			suffix = suffix + arraySuffix;
			Type[] filters = new Type[2];
			if(isPrimitive)
				filters[0] = Type.Primitive;
			if(isQualified){
				filters[1] = Type.Qualified;
			}
			
			if(isFull){
				return getJavaTypeFullName(ary.getElementType(), filters) + arraySuffix;
			}else{
				return getJavaTypeSimpleName(ary.getElementType(), filters) + arraySuffix;
			}
		}
		Mapping mapping = jsToJavaMapping.get(typeName);
		String mappingType = determineType(isFull, isPrimitive, mapping);
		int typeNameIndexOfDot = typeName.lastIndexOf(".");
		if(!ignoretypemap){
			if (mappingType != null && !typeInNeeds(mapping)) {
				
				if(isPrimitive && simpleTypeName.equals("Boolean")){
					System.out.println("WARNING BOOLEAN USAGE IN " + m_currentType.getName());
				}
				
				if (isAry) {
					return mappingType + arraySuffix;
				}
				return mappingType;
			}
		
			if (isFull) {
				String pkg = "";
				if (typeNameIndexOfDot != -1) {
					pkg = typeName.substring(0, typeNameIndexOfDot);
				}
				if (jsToJsrPackageMap.get(pkg) != null) {
					// typeName = typeName.substring(typeNameIndexOfDot,
					// typeName.length());
					 simpleTypeName = simpleNameFromAlias(type);
					
					
					return jsToJsrPackageMap.get(pkg) + "." + simpleTypeName
							+ suffix;
				}
		
			} else {
				String pkg = "";
				if (typeNameIndexOfDot != -1) {
					pkg = typeName.substring(0, typeNameIndexOfDot);
				}
				if (jsToJsrPackageMap.get(pkg) != null) {
					// typeName = typeName.substring(typeNameIndexOfDot+1,
					// typeName.length());
					 simpleTypeName = simpleNameFromAlias(type);
						
					return simpleTypeName + suffix;
				}
		
			}
		}

		if (type instanceof JstParamType) {
			return type.getName();
		}

		if (type.isEmbededType()) {
			return determineEmbeddedJsrName(type, isFull, isQualified);
		}

		if (type instanceof JstObjectLiteralType) {
			return determineObjLiteralTypeName(type, isFull, isQualified);
		}

		if (type instanceof IJstRefType) {
			IJstRefType tempType = (IJstRefType) type;
			return determineTypeRefType(tempType, isAry, suffix, mappingType, typeNameIndexOfDot);
		
		}

		if (type instanceof JstFunctionRefType) {
			return determineFunctionRefType(type, isFull, isQualified);

		}
		
		if(addParams && type instanceof JstTypeWithArgs ){
			JstTypeWithArgs targs = (JstTypeWithArgs)type;
			return determineTypeWithArgs(targs, isFull, isQualified);
		}

		if(type instanceof JstType){
			JstType jstType = (JstType)type;
			if(type.isClass() && jstType.getExtends().size()==0){
				return type.getName();
			}
		}

		return defaultJsr(isFull, suffix, typeName, simpleTypeName,
				typeNameIndexOfDot);
	}

	private String determineTypeWithArgs(JstTypeWithArgs templatedType, boolean isFull,
			boolean isQualified) {
		
		
		
		 StringBuilder sb = new StringBuilder();
		
		 sb.append(getJavaTypeName(templatedType.getType(),isFull,isQualified,false,false,true))
		 	.append(OPEN_ANGLE_BRACKET);
		 
		 int i = 0;
	        for (IJstType p : templatedType.getArgTypes()) {
	            if (i++ > 0) {
	                sb.append(COMMA);
	            }
	            if (p instanceof JstWildcardType) {
	                sb.append(QUESTION_MARK);
	                if (!JstWildcardType.DEFAULT_NAME.equals(p.getSimpleName())) {
	                    if (((JstWildcardType) p).isUpperBound()) {
	                        sb.append(" extends ").append(getJavaTypeName(p,isFull,isQualified,false,false,true));
	                    } else if (((JstWildcardType) p).isLowerBound()) {
	                        sb.append(" super ").append(getJavaTypeName(p,isFull,isQualified,false,false,true));
	                    }
	                }
	            } else if(p instanceof JstTypeWithArgs) {
	            	JstTypeWithArgs p2 = (JstTypeWithArgs)p;
	                sb.append(determineTypeWithArgs(p2,isFull,isQualified));
	            }else{
	            	sb.append(getJavaTypeName(p,isFull,isQualified,false,false,true));
	            }
	        }
	        sb.append(CLOSE_ANGLE_BRACKET);
	        return sb.toString();
		
	}

	private String simpleNameFromAlias(final IJstType type) {
//		String simpleTypeName;
		if(type.getPackage()==null || type.getPackage().getName()==""){
			return type.getSimpleName();
		}
		IJstAnnotation annotation = type.getAnnotation("Alias");
		if(annotation!=null){
			return annotation.values().get(0).toExprText().replace("\"", "");
			
		}
		
		return type.getSimpleName();
		
//		simpleTypeName = type.getAlias().substring(type.getAlias().lastIndexOf(".")+1, type.getAlias().length());
//		return simpleTypeName;
	}

	private boolean typeInNeeds(Mapping mapping) {
		if(m_currentType!=null){
			for(IJstType t :m_currentType.getInactiveImports()){
				if(t.getName().equals(mapping.wrapperType)){
					return true;
				}
			}
		}
		return false;
	}

	private String determineTypeRefType(IJstRefType tempType, boolean isFull, String suffix, String typeName, int typeNameIndexOfDot) {
		
		String simpleTypeName = tempType.getReferencedNode().getSimpleName();

		return "JsTypeRef<"
				+ defaultJsr(isFull, suffix, typeName, simpleTypeName,
						typeNameIndexOfDot) + ">";
	}

	private String determineFunctionRefType(final IJstType type,
			boolean isFull, boolean isQualified) {
//		System.out.println("funct ref type");
		JstFunctionRefType tempType = (JstFunctionRefType) type;
		IJstType jstType = ((IJstType) tempType.getParentNode());
		String funcTypeName = tempType.getMethodRef().getName() + JSR;
		String containerTypeName = jstType.getSimpleName() + JSR;
		if (isFull) {
			return jstType.getPackage().getName() + "." + containerTypeName
					+ "." + funcTypeName;
		} else if (isQualified) {
			return containerTypeName + "." + funcTypeName;
		} else {
			return funcTypeName;
		}
	}

	private String determineObjLiteralTypeName(final IJstType type,
			boolean isFull, boolean isQualified) {
//		if (DEBUG) {
//			System.out.println("object literal type = " + type.getName());
//		}

		IJstType parent = (IJstType) type.getParentNode();
		if (isFull) {
			return parent.getPackage().getName() + parent.getSimpleName() + JSR
					+ "." + type.getSimpleName() + JSR;
		}
		if (isQualified) {
			return parent.getSimpleName() + JSR + "." + type.getSimpleName()
					+ JSR;
		}

		return type.getSimpleName() + JSR;

	}

	private String determineEmbeddedJsrName(IJstType type, boolean isFull, boolean isQualified) {
		StringBuilder stringBuilder = new StringBuilder();
		
		boolean nested =type.getOuterType().isEmbededType();
		determineEmbeddedJsrName(type, isFull, isQualified==false?nested:isQualified, stringBuilder);
		m_dot = false;

		return stringBuilder.toString();
	}

	private void determineEmbeddedJsrName(IJstType type, boolean isFull, boolean isQualified, StringBuilder b) {
		String suffix = "Jsr";

		if (type.getOuterType() != null) {
			boolean nested =type.getOuterType().isEmbededType();
			determineEmbeddedJsrName(type.getOuterType(), isFull, isQualified==false?nested:isQualified, b);
		
			if(isQualified || isFull){
				addDot(b);
				m_dot= false;
			}
			b.append(type.getSimpleName());
			b.append(suffix);
			

		} else if(isFull) {
			b.append(type.getPackage().getName());
			addDot(b);
			m_dot= false;
			b.append(type.getSimpleName());
			b.append(suffix);
		} else if (isQualified){
			b.append(type.getSimpleName());
			b.append(suffix);
			addDot(b);
		}

	}

	private void addDot(StringBuilder b) {
		if(!m_dot){
			b.append(".");
			m_dot = true;
		}
		
		
	}

	private String defaultJsr(boolean isFull, String suffix, String typeName,
			String simpleTypeName, int typeNameIndexOfDot) {
		if (!isFull && typeNameIndexOfDot != -1) {
			typeName = simpleTypeName;
		}

		return typeName + suffix;
	}

	private String determineType(boolean isFull, boolean isPrimitive,
			Mapping mapping) {
		if (mapping == null) {
			return null;
		}

		String mappingType = mapping.wrapperType;
		if (isPrimitive) {
			mappingType = mapping.primitiveType;
		}

		if (!isFull && mappingType.indexOf('.') != -1) {
			mappingType = mappingType.substring(
					mappingType.lastIndexOf('.') + 1, mappingType.length());
		}

		return mappingType;
	}

	private void init() {
		initTypeMapping();
		initPackageMapping();
	}

	/**
	 * Answers if the type should be excluded from imports
	 */

	@Override
	public boolean isExcludedFromImport(IJstType type) {

		
		
		String mappedTypeName = type.getAlias();
		if (mappedTypeName == null) {
			return false;
		}
		if (mappedTypeName.contains("String")) {
			return true;
		}
		return false;
	}
	
	
	@Override
	public boolean isInactiveImport(IJstType type) {
		if(!m_init){
			init();
			m_init = true;
		}
		
		
		
		Mapping mapping = jsToJavaMapping.get(type.getAlias());
		if(mapping!=null && mapping.isInactive ){
			return true;
		}
		
		
		String name = getPkg(type);
		
		
		if(jsToJsrPackageMap.containsKey(name)){
			return true;
		}
		
		return false;
	}

	private String getOriginalJavaPkg(IJstType type){
		if(type.getAlias()!=null  &&  type.getAlias().indexOf(".")!=-1){
			return type.getAlias().substring(0, type.getAlias().lastIndexOf("."));
		}
		return "";
	}
	
	
	private String getPkg(IJstType type) {
		String name ="";
		IJstAnnotation annotation = type.getAnnotation(Alias.class.getSimpleName());
		if(type.getPackage()!=null){
			name = type.getPackage().getName();
		}else if(annotation!=null && type.getAlias().indexOf(".")!=-1){
			name = getAliasTypeSimpleName(annotation, type);
		}
		return name;
	}

	private String getAliasTypeSimpleName(IJstAnnotation annotation, IJstType type) {
			return annotation.values().get(0).toExprText();
	}

	/**
	 * Answers if the type is only a java type there is no Js equalivalent Jsr
	 * generated will use this data type as a special case
	 */

	@Override
	public boolean isMappedEventType(IJstType type) {
		if(!m_enableTypeMapping){
			return false;
		}
		
//		if(type.getAlias().startsWith(EVENTPACKAGE)){
//			return true;
//		}
		
//		if(type.getAlias().equals("vjo.dsf.Event")){
//			return true;
//		}
		
//		if (type.getName().equals(JS_HANDLER_OBJECT_ENUM)) {
//			return true;
//		}
//		if (type.getName().equals(IDAPSVCCALLBACK)) {
//			return true;
//		}

		return false;
	}

	@Override
	public boolean supportsValueBinding(List<SimpleParam> list) {
		if (list.isEmpty()) {
			return false;
		}
		
		if(list.size()==1 && isObjectType(list.get(0).m_type)){
			return false;
		}
		
		for (SimpleParam param : list) {
			boolean hasmapping=false;
			
			if(param.m_mappedName!=null){
				hasmapping=true;
			}
			
			IJstType type = param.getType();
			if(!hasmapping&& supportValueBindingInternal(type)){
				return true;
			}
			
		}
		
		return false;
	}

	private boolean supportValueBindingInternal(IJstType type) {
		if(!m_init){
			init();
			m_init=true;
		}
		
		if(type instanceof JstArray){
			JstArray jstArray = (JstArray)type;
			type = jstArray.getElementType();
		}
		
		if(isMappedEventType(type)){
			return false;
		}
		if(type.getName().equals(JS_HANDLER_OBJECT_ENUM)){
			return false;
		}
		
		
		// Should we be adding IValueBinding<Object> 
//		if (isObjectType(type)) {
//			return false;
//	    }
//		
//		if(jsToJavaMapping.get(type.getName())!=null){
//			return true;
//		}
//		if(jsToJavaMapping.get(type.getAlias())!=null){
//			return true;
//		}
////		
////		if(isPrimitive(type)){
////			return false;
////		}
//		
		if (isMappedEventType(type)) {
			return false;
		}

		if(isJsObject(type)){
			return false;
		}
//		
//		
//		System.out.println("supports IValueBinding :" + type.getName());

		
		return true;
	}

//	private boolean isJavaPrimitive(IJstType type) {
//		if(type instanceof JstRefType){
//			return true;
//		}
//		
//		if(type.getPackage()!=null && type.getPackage().getGroupName().equals(LibManager.JAVA_PRIMITIVE_LIB_NAME)){
//			return true;
//		}
//		return false;
//	}

	private boolean isJsObject(IJstType type) {
		JstType object = JstCache.getInstance().getType("Object");
		if(object==null){
			object = JstCache.getInstance().getType(Object.class.getName());
		}
		
		if(type.equals(object)){
			return true;
		}
		
//		if(type.getExtend()!=null && type.getExtend().equals(object)){
//			return true;
//		}
		
		return false;
	}

//	private boolean isVjoObject(IJstType type) {
//		
//		
//		
//		if(type.getSimpleName().equals("Object")){
//			return false;
//		}
//		
//		if(type.getExtend()!=null){
//			return true;
//		}
//		
//		if(type.isInterface() || type.isMixin() || type.isEnum() || type.isOType()){
//			return true;
//		}
//		
//		return false;
//	}

	private boolean isObjectType(IJstType type) {
		// Note: If user did not specify the package then we assume it's
		// java.lang.Object
		String name = DataTypeHelper.getTypeName(type.getName());
		if ("java.lang.Object".equals(name)
				|| "Object".equals(name)
				|| org.ebayopensource.dsf.jsnative.global.Object.class.getName().equals(
						name)) {
			return true;
		}

		// if (type instanceof JstRefType) {
		// return ((JstRefType) type).getRefType() == Object.class;
		// }
		return false;
	}

	public void setCurrentType(IJstType currentType) {
		m_currentType = currentType;
	}

	@Override
	public boolean isBrowserEventType(IJstType argType) {
		String pkg = getPkg(argType);
		if(pkg.equals("")){
			pkg = getOriginalJavaPkg(argType);
		}
		
		if(pkg.equals(EVENTPACKAGE)){
			return true;
		}
		return false;
	}

	@Override
	public boolean supportsValueBinding(IJstType argType) {
		return supportValueBindingInternal(argType);
	}

	public boolean isEnableTypeMapping() {
		return m_enableTypeMapping;
	}

	public void setEnableTypeMapping(boolean enableTypeMapping) {
		m_enableTypeMapping = enableTypeMapping;
	}

	@Override
	public boolean isObject(IJstType type) {
		
		JstType object = JstCache.getInstance().getType("Object");
		if(object==null){
			object = JstCache.getInstance().getType(Object.class.getName());
		}
		
		if(type.equals(object)){
			return true;
		}
		
		
		return false;
	}

	@Override
	public boolean shouldImport(IJstType type) {
		if(type.getName().equals("vjo.dsf.Message")){
			return true;
		}
		return false;
	}

	@Override
	public boolean isLiteral(IJstType type) {
		for(String name:LITERALS){
			if(type.getSimpleName().equals(name)){
					return true;
			}
		}
		return false;
	}

}

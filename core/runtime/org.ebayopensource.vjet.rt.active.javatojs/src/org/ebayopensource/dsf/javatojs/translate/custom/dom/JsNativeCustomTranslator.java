/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.custom.dom;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.javatojs.trace.TranslateMsgId;
import org.ebayopensource.dsf.javatojs.translate.TranslateHelper;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomMethod;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomType;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.MetaDrivenCustomTranslator;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.VjoConstants;
import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.Constructor;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.Dynamic;
import org.ebayopensource.dsf.jsnative.anno.FactoryFunc;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.GlobalProperty;
import org.ebayopensource.dsf.jsnative.anno.IType;
import org.ebayopensource.dsf.jsnative.anno.JsArray;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.JsSupport;
import org.ebayopensource.dsf.jsnative.anno.JsVariantArray;
import org.ebayopensource.dsf.jsnative.anno.JsVersion;
import org.ebayopensource.dsf.jsnative.anno.JstExclude;
import org.ebayopensource.dsf.jsnative.anno.JstMultiReturn;
import org.ebayopensource.dsf.jsnative.anno.MType;
import org.ebayopensource.dsf.jsnative.anno.OverLoadFunc;
import org.ebayopensource.dsf.jsnative.anno.OverrideFunc;
import org.ebayopensource.dsf.jsnative.anno.OverrideProp;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.ebayopensource.dsf.jsnative.anno.Static;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstAnnotation;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.datatype.JstReservedTypes;
import org.ebayopensource.dsf.jst.declaration.JstAnnotation;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstArray;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstConstructor;
import org.ebayopensource.dsf.jst.declaration.JstDoc;
import org.ebayopensource.dsf.jst.declaration.JstGlobalProp;
import org.ebayopensource.dsf.jst.declaration.JstGlobalVar;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstMixedType;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstType.Category;
import org.ebayopensource.dsf.jst.declaration.JstTypeReference;
import org.ebayopensource.dsf.jst.declaration.SynthOlType;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.IExtendedModifier;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeLiteral;

public class JsNativeCustomTranslator extends MetaDrivenCustomTranslator {

	public JsNativeCustomTranslator() {
		super(new ADomMeta());
	}

	@Override
	public boolean processTypeBody(List bodyDeclaration, JstType jstType) {
		CustomType toType = getMetaProvider().getCustomType(jstType.getName());
		if (toType == null) {
			return false;
		}

		// Keep the fullname as alias and change the name to name
		// provided by custom type.
		jstType.setAlias(jstType.getName());
		jstType.setSimpleName(getTypeName(toType));
		jstType.setMetaType(isTypeMetaType(toType));
		jstType.getModifiers().setDynamic(isTypeDynamic(toType));
		jstType.setPackage(null);
		jstType.clearMethods();
		jstType.clearProperties();

		for (Object d : bodyDeclaration) {
			if (d instanceof MethodDeclaration) {
				MethodDeclaration astMtd = (MethodDeclaration) d;
				processMethod(astMtd, jstType, toType);
			}
		}
		addExtendsGlobal(jstType);
		addExtendsObject(jstType);
		fixOverLoaded(jstType);
		fixImports(jstType);
		fixCategory(jstType);
		return true;
	}

	private void fixCategory(JstType jstType) {
		jstType.setImpliedImport(true);

		if (jstType.getAnnotation(MType.class.getSimpleName()) != null) {
			jstType.setCategory(Category.MODULE);
			jstType.removeExtend(jstType.getExtend());
			if (!jstType.getSatisfies().isEmpty()) {
				jstType.removeSatisfy(jstType.getSatisfies().get(0));
			}

		} else if (jstType.getAnnotation(IType.class.getSimpleName()) != null) {

			if (jstType.getExtend() != null
					|| jstType.getExtend().getSimpleName().equals("Object")) {
				jstType.removeExtend(jstType.getExtend());
			}

		} else if (jstType.getAnnotation(IType.class.getSimpleName()) == null) {
			// Make sure we set the category to CLASS since
			// JsNative types are defined as java interfaces and translator
			// will set the category to INTERFACE by default.
			jstType.setCategory(Category.CLASS);
			jstType.getModifiers().setAbstract();

		}

	}

	private void fixOverLoaded(JstType jstType) {
		JstMethod constructor = jstType.getConstructor();
		if (constructor != null && constructor.isDispatcher()) {
			JstArg[] args = new JstArg[constructor.getArgs().size()];
			int i = 0;
			for (JstArg arg : constructor.getArgs()) {
				args[i] = new JstArg(arg.getType(), arg.getName(),
						arg.isVariable(), arg.isOptional(), arg.isFinal());
				i++;
			}
			JstConstructor ovldConst = new JstConstructor(
					constructor.getModifiers(), args);
			constructor.addOverloaded(ovldConst);
			ovldConst.setParent(constructor, false);
		}

		for (IJstMethod method : jstType.getMethods()) {
			if (method.isDispatcher() && method instanceof JstMethod) {
				JstArg[] args = new JstArg[method.getArgs().size()];
				int i = 0;
				for (JstArg arg : method.getArgs()) {
					args[i] = new JstArg(arg.getType(), arg.getName(),
							arg.isVariable(), arg.isOptional(), arg.isFinal());
					i++;
				}
				JstMethod mtd = new JstMethod(method.getName(),
						method.getModifiers(), method.getRtnType(), args);
				((JstMethod) method).addOverloaded(mtd);
				mtd.setParent(method, false);
			}
		}

	}

	private void addExtendsGlobal(JstType jstType) {
		// Window type needs to extend Global object but this cannot be
		// achived in jsnative java interfaces so we establish this
		// inheritence here.
		if ("Window".equals(jstType.getName())) {
			IJstType globalType = JstCache.getInstance().getType(
					org.ebayopensource.dsf.jsnative.global.Global.class
							.getName());
			if (globalType != null) {
				JstTypeReference jstTypeReference = new JstTypeReference(
						globalType);
				jstType.addExtend(jstTypeReference);
				jstType.addImport(jstTypeReference);
			}
		}
	}

	// All JS types need to extend Object but we cannot do that in
	// JS native interfaces since it will force the impl classes to
	// implement JS Object interfaces
	private void addExtendsObject(JstType jstType) {
		if (jstType.isMixin()) {
			return;
		}

		if ("Object".equals(jstType.getName())) {
			return;
		}

		if ("vjo.Object".equals(jstType.getName())) {
			return;
		}

		IJstType extend = jstType.getExtend();

		if (extend != null
				&& "java.lang.RuntimeException".equals(extend.getName())) {
			jstType.removeExtend(extend);
			extend = null;
		}
		if (extend != null) {
			return;
		}

		IJstType objectType = JstCache.getInstance().getType(
				org.ebayopensource.dsf.jsnative.global.Object.class.getName());
		if (objectType != null) {
			JstTypeReference jstTypeReference = new JstTypeReference(objectType);
			jstType.addExtend(jstTypeReference);
			jstType.addImport(jstTypeReference);
		}
	}

	@Override
	public JstIdentifier processIdentifier(Name astName, boolean hasSuper,
			boolean hasThis, final IExpr qualifierExpr,
			final JstIdentifier identifier, BaseJstNode jstNode) {

		if (astName.isSimpleName()) {
			// TODO
			return null;
		}

		QualifiedName qualifiedName = (QualifiedName) astName;
		Name qualifier = qualifiedName.getQualifier();
		if (isSupportedByAnnotation(qualifier.getFullyQualifiedName())) {
			JstIdentifier jstQualifier = new JstIdentifier(qualifier.toString());
			SimpleName name = qualifiedName.getName();
			JstIdentifier jstIdentifier = new JstIdentifier(name.toString());
			if (jstQualifier != null) {
				jstIdentifier.setQualifier(jstQualifier);
				jstIdentifier.setType(jstQualifier.getType());
			}
			return jstIdentifier;

		}

		return super.processIdentifier(qualifiedName, hasSuper, hasThis,
				qualifierExpr, identifier, jstNode);
	}

	private boolean isSupportedByAnnotation(String fullyQualifiedName) {
		if (fullyQualifiedName == null) {
			return false;
		}
		if (fullyQualifiedName.equals(BrowserType.class.getName())
				|| fullyQualifiedName.equals(BrowserType.class.getSimpleName())
				|| fullyQualifiedName.equals(JsVersion.class.getName())
				|| fullyQualifiedName.equals(JsVersion.class.getSimpleName())
				|| fullyQualifiedName.equals(DomLevel.class.getName())
				|| fullyQualifiedName.equals(DomLevel.class.getSimpleName())) {
			return true;
		}
		return false;
	}

	// Remove the package information from the imports
	private void fixImports(JstType jstType) {
		List<IJstType> imports = jstType.getImports();
		for (IJstType t : imports) {
			((JstType) t).setPackage(null);
		}
		jstType.clearImports();
		for (IJstType t : imports) {
			jstType.addImport(new JstTypeReference(t));
		}

	}

	private boolean isTypeDynamic(CustomType cType) {
		Class c = cType.getJavaType();
		Dynamic dynamic = (Dynamic) c.getAnnotation(Dynamic.class);
		if (dynamic != null) {
			return true;
		}
		return false;
	}

	private boolean isTypeMetaType(CustomType cType) {
		Class c = cType.getJavaType();
		JsMetatype metatype = (JsMetatype) c.getAnnotation(JsMetatype.class);
		if (metatype != null) {
			return true;
		}
		return false;
	}

	private String getTypeName(CustomType cType) {
		Class c = cType.getJavaType();
		Alias alias = (Alias) c.getAnnotation(Alias.class);
		if (alias != null) {
			return alias.value();
		}
		return TranslateHelper.getShortName(cType.getJstName());
	}

	private void processMethod(MethodDeclaration astMtd, JstType jstType,
			CustomType toType) {
		List<IExtendedModifier> extModifiers = astMtd.modifiers();
		for (IExtendedModifier em : extModifiers) {
			if (em.isAnnotation()) {
				Annotation ma = (Annotation) em;
				if (GlobalProperty.class.getName().equals(
						ma.getTypeName().toString())
						|| GlobalProperty.class.getSimpleName().equals(
								ma.getTypeName().toString())) {
					processGlobalProperty(astMtd, jstType, toType);
				} else if (Property.class.getName().equals(
						ma.getTypeName().toString())
						|| Property.class.getSimpleName().equals(
								ma.getTypeName().toString())
						|| OverrideProp.class.getName().equals(
								ma.getTypeName().toString())
						|| OverrideProp.class.getSimpleName().equals(
								ma.getTypeName().toString())) {
					processProperty(astMtd, jstType, toType);

				} else if (Function.class.getName().equals(
						ma.getTypeName().toString())
						|| Function.class.getSimpleName().equals(
								ma.getTypeName().toString())
						|| OverrideFunc.class.getName().equals(
								ma.getTypeName().toString())
						|| OverrideFunc.class.getSimpleName().equals(
								ma.getTypeName().toString())) {
					processFunction(astMtd, jstType, toType);
				} else if (Constructor.class.getName().equals(
						ma.getTypeName().toString())
						|| Constructor.class.getSimpleName().equals(
								ma.getTypeName().toString())) {
					processConstructor(astMtd, jstType, toType);
				} else if (OverLoadFunc.class.getName().equals(
						ma.getTypeName().toString())
						|| OverLoadFunc.class.getSimpleName().equals(
								ma.getTypeName().toString())) {
					processOverLoadFunc(astMtd, jstType, toType);
				}
			}
		}
	}

	private void processGlobalProperty(MethodDeclaration astMtd,
			JstType jstType, CustomType toType) {
		String mtdName = astMtd.getName().toString();
		// Must be a getter method with no arguments to be property
		if (!mtdName.startsWith("get") || !astMtd.parameters().isEmpty()) {
			return;
		}

		CustomMethod mtd = getCustomMethod(toType, astMtd);
		if (mtd == null) {
			getLogger().logError(
					TranslateMsgId.EXCLUDED_MTD,
					"Method '" + astMtd.getName().toString()
							+ "' not found in custom type "
							+ toType.getJstName(), this, astMtd, jstType);
			return;
		}

		// Return Type indicates property type
		Type rtnType = astMtd.getReturnType2();
		if (rtnType == null) {
			getLogger()
					.logError(
							TranslateMsgId.EXCLUDED_MTD,
							"Method '"
									+ astMtd.getName().toString()
									+ "' is annotated as Property but has no return type",
							this, astMtd, jstType);
			return;
		}
		IJstType propType = getDataTypeTranslator().processType(rtnType,
				jstType);

		IJstType arryType = setUpArray(propType, astMtd);

		if (arryType != null) {
			List<IJstType> types = new ArrayList<IJstType>();
			types.add(arryType);
			types.add(propType);
			propType = new JstMixedType(types);
		}

		if (propType == null) {
			getLogger().logError(
					TranslateMsgId.NULL_RESULT,
					"failed translation for property type "
							+ rtnType.toString(), this, astMtd, jstType);
			return;
		}
		boolean isStatic = isStatic(astMtd);
		JstProperty pty = new JstProperty(propType, normalizeName(mtd, astMtd),
				new JstModifiers().setPublic().setStatic(isStatic));

		JstGlobalProp prop = new JstGlobalProp(pty);
		JstGlobalVar var = new JstGlobalVar(prop);
		// jstType.addProperty(pty);
		jstType.addGlobalVar(var);

		processFieldJavadoc(astMtd, pty);
		for (Object m : astMtd.modifiers()) {
			if (m instanceof Annotation) {
				processAnnotation(pty, m);
			}
		}

	}

	private void processConstructor(MethodDeclaration astMtd, JstType jstType,
			CustomType toType) {
		CustomMethod mtd = getCustomMethod(toType, astMtd);
		if (mtd == null) {
			getLogger().logError(
					TranslateMsgId.EXCLUDED_MTD,
					"Method '" + astMtd.getName().toString()
							+ "' not supported base on meta info for type "
							+ jstType.getName(), this, astMtd, jstType);
			return;
		}
		// Parameters
		List<SingleVariableDeclaration> params = astMtd.parameters();
		JstArg[] argsArray = new JstArg[params.size()];
		int indx = 0;
		for (SingleVariableDeclaration sv : params) {
			String name = getNameTranslator().processVarName(
					sv.getName().toString());
			IJstType type = getDataTypeTranslator().processType(sv.getType(),
					jstType);
			argsArray[indx] = new JstArg(type, name, sv.isVarargs());
			indx++;
		}
		JstConstructor constr = new JstConstructor(
				new JstModifiers().setPublic(), argsArray);

		JstMethod currConstructor = jstType.getConstructor();
		if (currConstructor != null) {
			currConstructor.addOverloaded(constr);
			constr.setParent(currConstructor, false);
		} else {
			jstType.setConstructor(constr);
		}
		for (Object m : astMtd.modifiers()) {
			if (m instanceof Annotation) {
				processAnnotation(constr, m);
			}
		}
	}

	private void processFunction(MethodDeclaration astMtd, JstType jstType,
			CustomType toType) {
		CustomMethod mtd = getCustomMethod(toType, astMtd);
		if (mtd == null) {
			getLogger().logError(
					TranslateMsgId.EXCLUDED_MTD,
					"Method '" + astMtd.getName().toString()
							+ "' not supported base on meta info for type "
							+ jstType.getName(), this, astMtd, jstType);
			return;
		}
		String mtdName = normalizeName(mtd, astMtd);
		JstModifiers modifiers = new JstModifiers().setPublic().setStatic(
				isStatic(astMtd));

		if (jstType.getMethod(mtdName, modifiers.isStatic()) != null) {
			String msg = "Method '" + astMtd.getName().toString()
					+ "' already exists in type " + jstType.getName();
			// System.err.println(">>>>" + msg);
			getLogger().logError(
					TranslateMsgId.FAILED_TO_PARSE,
					"Method '" + astMtd.getName().toString()
							+ "' already exists in type " + jstType.getName(),
					this, astMtd, jstType);
			throw new RuntimeException(msg);
		}

		JstMethod jstMtd = new JstMethod(mtdName, modifiers);

		// Parameters
		List<SingleVariableDeclaration> params = astMtd.parameters();
		for (SingleVariableDeclaration sv : params) {
			String name = getNameTranslator().processVarName(sv.getName(),
					jstMtd.getBlock(true));
			IJstType type = getDataTypeTranslator().processType(sv.getType(),
					jstType);
			jstType.setPackage(null);
			jstMtd.getBlock(true).getVarTable().addVarType(name, type);
			jstMtd.addArg(new JstArg(type, name, sv.isVarargs()));
		}

		// Return Type
		Type rtnType = astMtd.getReturnType2();
		// TODO use JsMultiReturn

		if (rtnType != null) {
			IJstType type = getDataTypeTranslator().processType(rtnType,
					jstType);
			jstType.setPackage(null);
			jstMtd.setRtnType(type);
		}

		// function modifiers
		List<IExtendedModifier> extModifiers = astMtd.modifiers();
		for (IExtendedModifier em : extModifiers) {
			if (em.isAnnotation()) {
				Annotation ma = (Annotation) em;
				if (FactoryFunc.class.getSimpleName().equals(
						ma.getTypeName().toString())) {
					jstMtd.setTypeFactoryEnabled(true);
				}
				if (JstMultiReturn.class.getSimpleName().equals(
						ma.getTypeName().toString())) {
					if (ma instanceof SingleMemberAnnotation) {

						SingleMemberAnnotation sma = (SingleMemberAnnotation) em;
						List<IJstType> types = getTypesFromAryInAnno(sma);
						types.add(jstMtd.getRtnType());
						JstMixedType mixed = new JstMixedType(types);
						jstMtd.setRtnType(mixed);

					}
				}
			}
		}

		processMethodJavadoc(astMtd, jstMtd);
		jstType.addMethod(jstMtd);
		for (Object m : astMtd.modifiers()) {
			if (m instanceof Annotation) {
				processAnnotation(jstMtd, m);
			}
		}
		if (!isSupported(jstMtd)) {
			jstType.removeMethod(jstMtd.getName().getName(), jstMtd.isStatic());
		}
	}

	private List<IJstType> getTypesFromAryInAnno(SingleMemberAnnotation sma) {
		List<IJstType> types = new ArrayList<IJstType>();
		if (sma.getValue() instanceof ArrayInitializer) {
			System.out.println("test");
			ArrayInitializer ai = (ArrayInitializer) sma.getValue();
			for (Object oj : ai.expressions()) {

				if (oj instanceof TypeLiteral) {
					TypeLiteral tl = (TypeLiteral) oj;
					String fullName = tl.getType().toString();
					IJstType type = JstCache.getInstance().getType(
							"org.ebayopensource.dsf.jsnative." + fullName);
					if(type==null){
						type = JstCache.getInstance().getType("org.ebayopensource.dsf.jsnative.global." +fullName);
					}
					if(type==null){
						type = toJsNativeType(fullName);
					}
					if (type != null) {
						types.add(type);
					}
				}
			}

		}
		return types;
	}
	
	protected static IJstType toJsNativeType(String type){

		
		for(IJstType jsNativeType : VjoConstants.NativeTypes.getJstNativeTypes()){
			if(equals(jsNativeType, type)){
				return jsNativeType;
			}
		}
		
		if(equals(JstReservedTypes.JavaPrimitive.BOOLEAN, type)){
			return VjoConstants.NativeTypes.getPrimitiveBooleanJstType();
		}
		else if(equals(JstReservedTypes.JavaPrimitive.BYTE, type)){
			return VjoConstants.NativeTypes.getNumberJstType();
		}
		else if(equals(JstReservedTypes.JavaPrimitive.CHAR, type)){
			return VjoConstants.NativeTypes.getStringJstType();
		}
		else if(equals(JstReservedTypes.JavaPrimitive.DOUBLE, type)
				|| equals(JstReservedTypes.JavaPrimitive.FLOAT, type)){
			return VjoConstants.NativeTypes.getDoubleJstType();
		}
		else if(equals(JstReservedTypes.JavaPrimitive.INT, type)
				|| equals(JstReservedTypes.JavaPrimitive.SHORT, type)
				|| equals(JstReservedTypes.JavaPrimitive.LONG, type)){
			return VjoConstants.NativeTypes.getIntJstType();
		}
		else if(equals(JstReservedTypes.Other.VOID, type)){
			return JstReservedTypes.Other.VOID;
		}
//		else if(type instanceof JstFunctionRefType
//				|| "Function".equals(type.getSimpleName())){
//			return getFunctionNativeType();
//		}
//		else if(type instanceof JstArray
//				|| "Array".equals(type.getSimpleName())){
//			return VjoConstants.NativeTypes.getArrayJstType();
//		}
//		else if(type instanceof JstObjectLiteralType){
//			return getObjectNativeType();
//		}
		
		return null;
	}
	
	public static boolean equals(final IJstType one, final String two){
		if(one == null || two == null){
			return false;
		}
		if(one.equals(two)){
			return true;
		}
		
		String name1 = one.getName();
		String name2 = two;
		if(name1 != null){
			if (name1.equals(name2)) {
				return true;
			}
			return ("Object".equals(name1) || SynthOlType.TYPE_NAME.equals(name1)) &&
				("Object".equals(name2) || SynthOlType.TYPE_NAME.equals(name2));
		}
		return false;
	}
	

	private boolean isSupported(JstMethod jstMtd) {
		IJstAnnotation anno = jstMtd.getAnnotation("JstExclude");
		if (anno != null) {
			return false;
		}

		return true;
	}

	private void processAnnotation(BaseJstNode jstMtd, Object m) {
		if (DOMSupport.class.getSimpleName().equals(
				((Annotation) m).getTypeName().toString())) {
			getOtherTranslator().processAnnotation((Annotation) m, jstMtd);
		} else if (BrowserSupport.class.getSimpleName().equals(
				((Annotation) m).getTypeName().toString())) {
			getOtherTranslator().processAnnotation((Annotation) m, jstMtd);
		} else if (JsSupport.class.getSimpleName().equals(
				((Annotation) m).getTypeName().toString())) {
			getOtherTranslator().processAnnotation((Annotation) m, jstMtd);
		} else if (JstExclude.class.getSimpleName().equals(
				((Annotation) m).getTypeName().toString())) {
			getOtherTranslator().processAnnotation((Annotation) m, jstMtd);
		}
	}

	private void processOverLoadFunc(MethodDeclaration astMtd, JstType jstType,
			CustomType toType) {
		CustomMethod mtd = getCustomMethod(toType, astMtd);
		if (mtd == null) {
			getLogger().logError(
					TranslateMsgId.EXCLUDED_MTD,
					"Method '" + astMtd.getName().toString()
							+ "' not supported base on meta info for type "
							+ jstType.getName(), this, astMtd, jstType);
			return;
		}
		JstMethod jstMtd = new JstMethod(normalizeName(mtd, astMtd),
				new JstModifiers().setPublic().setStatic(isStatic(astMtd)));
		// Parameters
		List<SingleVariableDeclaration> params = astMtd.parameters();
		for (SingleVariableDeclaration sv : params) {
			String name = getNameTranslator().processVarName(sv.getName(),
					jstMtd.getBlock(true));
			IJstType type = getDataTypeTranslator().processType(sv.getType(),
					jstType);
			jstType.setPackage(null);
			jstMtd.getBlock(true).getVarTable().addVarType(name, type);
			jstMtd.addArg(new JstArg(type, name, sv.isVarargs()));
		}
		// Return Type
		Type rtnType = astMtd.getReturnType2();
		if (rtnType != null) {
			IJstType type = getDataTypeTranslator().processType(rtnType,
					jstType);
			jstType.setPackage(null);
			jstMtd.setRtnType(type);
		}
		// processMethodJavadoc(astMtd, jstMtd);

		JstMethod currMtd = (JstMethod) jstType.getMethod(jstMtd.getName()
				.getName(), jstMtd.isStatic());
		if (currMtd != null) {
			processMethodJavadoc(astMtd, currMtd);
			currMtd.addOverloaded(jstMtd);
			jstMtd.setParent(currMtd, false);
		} else {
			jstType.addMethod(jstMtd);
		}
		for (Object m : astMtd.modifiers()) {
			if (m instanceof Annotation) {
				// if (SupportedBy.class.getSimpleName().equals(
				// ((Annotation) m).getTypeName().toString())) {
				// getOtherTranslator().processAnnotation((Annotation) m,
				// jstMtd);
				// }
				processAnnotation(jstMtd, m);
				if (m instanceof SingleMemberAnnotation) {
					SingleMemberAnnotation sma = (SingleMemberAnnotation)m;
					if (JstMultiReturn.class.getSimpleName().equals(
							sma.getTypeName().toString())) {
						List<IJstType> types = getTypesFromAryInAnno(sma);
						types.add(jstMtd.getRtnType());
						JstMixedType mixed = new JstMixedType(types);
						jstMtd.setRtnType(mixed);

					}
				}
			}
		}

	}

	private String normalizeName(CustomMethod mtd, MethodDeclaration astMtd) {
		if ("_void".equals(mtd.getJstOrigName())) {
			return "void";
		}
		Annotation alias = getAliasAnnotation(astMtd);
		if (alias != null && alias instanceof SingleMemberAnnotation) {
			SingleMemberAnnotation smAnn = (SingleMemberAnnotation) alias;
			String value = smAnn.getValue().toString();
			if (value.startsWith("\"") && value.endsWith("\"")) {
				value = value.substring(1, value.length() - 1);
			}
			return value;
		}
		return mtd.getJstOrigName();
	}

	private Annotation getAliasAnnotation(MethodDeclaration astMtd) {
		List<IExtendedModifier> extModifiers = astMtd.modifiers();
		for (IExtendedModifier em : extModifiers) {
			if (em.isAnnotation()) {
				Annotation ma = (Annotation) em;
				if (Alias.class.getName().equals(ma.getTypeName().toString())
						|| Alias.class.getSimpleName().equals(
								ma.getTypeName().toString())) {
					return ma;
				}
			}
		}
		return null;
	}

	private void processProperty(MethodDeclaration astMtd, JstType jstType,
			CustomType toType) {
		String mtdName = astMtd.getName().toString();
		// Must be a getter method with no arguments to be property
		if (!mtdName.startsWith("get") || !astMtd.parameters().isEmpty()) {
			return;
		}

		CustomMethod mtd = getCustomMethod(toType, astMtd);
		if (mtd == null) {
			getLogger().logError(
					TranslateMsgId.EXCLUDED_MTD,
					"Method '" + astMtd.getName().toString()
							+ "' not found in custom type "
							+ toType.getJstName(), this, astMtd, jstType);
			return;
		}

		// Return Type indicates property type
		Type rtnType = astMtd.getReturnType2();
		if (rtnType == null) {
			getLogger()
					.logError(
							TranslateMsgId.EXCLUDED_MTD,
							"Method '"
									+ astMtd.getName().toString()
									+ "' is annotated as Property but has no return type",
							this, astMtd, jstType);
			return;
		}
		IJstType propType = getDataTypeTranslator().processType(rtnType,
				jstType);

		IJstType arrayType = setUpArray(propType, astMtd);

		if (arrayType != null) {
			List<IJstType> types = new ArrayList<IJstType>();
			types.add(arrayType);
			types.add(propType);
			propType = new JstMixedType(types);
		}

		if (propType == null) {
			getLogger().logError(
					TranslateMsgId.NULL_RESULT,
					"failed translation for property type "
							+ rtnType.toString(), this, astMtd, jstType);
			return;
		}
		boolean isStatic = isStatic(astMtd);
		JstProperty pty = new JstProperty(propType, normalizeName(mtd, astMtd),
				new JstModifiers().setPublic().setStatic(isStatic));
		jstType.addProperty(pty);
		processFieldJavadoc(astMtd, pty);
		for (Object m : astMtd.modifiers()) {
			if (m instanceof Annotation) {

				processAnnotation(pty, m);
			}
		}
	}

	private IJstType setUpArray(IJstType propType, MethodDeclaration astMtd) {
		IJstType type = getArrayType(astMtd, propType);
		if (type != null) {

			return new JstArray(type);
		}
		return null;
	}

	private void processMethodJavadoc(MethodDeclaration astMtd, JstMethod jstMtd) {
		Javadoc javadoc = astMtd.getJavadoc();
		if (javadoc == null) {
			return;
		}
		JstDoc jstDoc = new JstDoc(cleanUpDoc(javadoc.toString()));
		jstMtd.setDoc(jstDoc);
	}

	private void processFieldJavadoc(MethodDeclaration astMtd, JstProperty pty) {
		Javadoc javadoc = astMtd.getJavadoc();
		if (javadoc == null) {
			return;
		}
		String jsdoc = cleanUpDoc(javadoc.toString());
		JstDoc jstDoc = new JstDoc(jsdoc);
		pty.setDoc(jstDoc);
	}

	private String cleanUpDoc(String jsdoc) {
		jsdoc = jsdoc.trim();
		jsdoc = jsdoc.replaceAll("^/\\*\\*", "");
		jsdoc = jsdoc.replaceAll("^\\*", "");
		jsdoc = jsdoc.replaceAll("\\*/$", "");
		// commentSrc = commentSrc.replaceAll("^(\t)*(\\*)*", "");
		jsdoc = jsdoc.replaceAll("\\s+\\*", "\n");
		return jsdoc;
	}

	private boolean isStatic(MethodDeclaration astMtd) {
		List<IExtendedModifier> extModifiers = astMtd.modifiers();
		for (IExtendedModifier em : extModifiers) {
			if (em.isAnnotation()) {
				if (em instanceof MarkerAnnotation) {
					MarkerAnnotation ma = (MarkerAnnotation) em;
					if (Static.class.getName().equals(
							ma.getTypeName().toString())
							|| Static.class.getSimpleName().equals(
									ma.getTypeName().toString())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private IJstType getArrayType(MethodDeclaration astMtd, IJstType propType) {
		List<IExtendedModifier> extModifiers = astMtd.modifiers();
		for (IExtendedModifier em : extModifiers) {
			if (em.isAnnotation()) {
				if (em instanceof SingleMemberAnnotation) {

					SingleMemberAnnotation sma = (SingleMemberAnnotation) em;
					if (JsVariantArray.class.getSimpleName().equals(
							sma.getTypeName().toString())) {
						List<IJstType> types = getTypesFromAryInAnno(sma);
						return new JstMixedType(types);
					} else if (JsArray.class.getSimpleName().equals(
							sma.getTypeName().toString())) {

						JstAnnotation annotation = new JstAnnotation();
						annotation.setParent(propType);

						if (sma.getValue() instanceof TypeLiteral) {
							TypeLiteral tl = (TypeLiteral) sma.getValue();
							String fullName = tl.getType().toString();
							IJstType type = JstCache.getInstance().getType(
									"org.ebayopensource.dsf.jsnative."
											+ fullName);
							return type;
						}

						return null;
					}
				}
			}
		}
		return null;
	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.WildcardType;

import org.ebayopensource.dsf.javatojs.trace.TranslateMsgId;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstFactory;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstParamType;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstTypeWithArgs;
import org.ebayopensource.dsf.jst.declaration.JstWildcardType;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.util.DataTypeHelper;

public class DataTypeTranslator extends BaseTranslator {

	//
	// API
	//
	public JstType processImport(final ImportDeclaration importDec, final JstType ownerType){
		
		TranslateInfo tInfo = getCtx().getTranslateInfo(ownerType.getRootType());
		
		String typeName = null;
		String refName = null;
		if (importDec.isStatic()){
			QualifiedName qName = (QualifiedName)importDec.getName();
			if (importDec.isOnDemand()){
				refName = "*";
				typeName = qName.getFullyQualifiedName();
				tInfo.addImportedStaticRef(refName, typeName);
				
				typeName = getCtx().getConfig().getPackageMapping().mapTo(typeName);
				JstType jstType = JstFactory.getInstance().createJstType(typeName, false);
				tInfo.setType(jstType.getSimpleName(), jstType);
				return jstType;
			}
			else {
				refName = qName.getName().toString();
				typeName = qName.getQualifier().getFullyQualifiedName();	
				tInfo.addImportedStaticRef(refName, typeName);
			}
		}
		else if (importDec.isOnDemand()){
			tInfo.addImportedPkg(importDec.getName().getFullyQualifiedName());
		}
		else {
			typeName = importDec.getName().getFullyQualifiedName();
			tInfo.addImport(TranslateHelper.getShortName(typeName), typeName);
		}
		return null;
	}
	
	public IJstType processType(final Type astType, final BaseJstNode jstNode){
		boolean processed = false;
		if (astType == null){
			return null;
		}
		IJstType processType = null;
		if (astType.isArrayType()){
			return processArrayType((ArrayType)astType, jstNode);
		}
		else if (astType.isParameterizedType()){
			return processTypeWithArgs((ParameterizedType)astType, jstNode);
		}
		else if (astType.isQualifiedType()){
			return processQualifiedType((QualifiedType)astType, jstNode);
		}
		else if (astType.isWildcardType()){
			return processWildcardType((WildcardType)astType, jstNode);
		}
		else if (astType.isSimpleType()){
			return processName(((SimpleType)astType).getName(), jstNode);
		}
		
		JstType ownerType = jstNode.getOwnerType();
		String typeName = astType.toString();
		IJstType jstType = findJstType(typeName, jstNode);
		
		if (jstType == null){
			jstType = getJstType(astType, typeName, ownerType, true);
		}
		
		if(processed){
			return processType;
		}
		getCtx().getTranslateInfo(ownerType).setType(typeName, jstType);
		
		return jstType;
	}
	
	public IJstType processName(final Name name, final BaseJstNode jstNode){
		TranslateInfo tInfo = getCtx().getTranslateInfo(jstNode.getRootType());
		if (name instanceof QualifiedName){
			JstIdentifier identifier = getNameTranslator().processName(name, false, jstNode);
			IJstType jstType = TranslateHelper.Type.getType(identifier);
			if (jstType != null){
				tInfo.setType(name.toString(), jstType);
				return jstType;
			}
		}
		
		String typeName = name.getFullyQualifiedName();
		IJstType jstType = findJstType(typeName, jstNode);
		if (jstType == null){
			jstType = getJstType(name, typeName, jstNode.getOwnerType(), true);
		}
		
		TranslateHelper.Type.validateTypeReference(jstType, name, jstNode, this);

		tInfo.setType(typeName, jstType);

		return jstType;
	}
	
	public IJstType toJstType(String typeName, JstType ownerType){

		IJstType jstType = findJstType(typeName, ownerType);
		if (jstType != null){
			return jstType;
		}
		
		return createJstType(typeName, ownerType);
	}
	
	public IJstType findJstType(final String name, final BaseJstNode jstNode){
		
		if (name == null || jstNode == null){
			return null;
		}
		
		IJstType jstType = JstCache.getInstance().getRefType(name);
		if (jstType!= null){
			return jstType;
		}
		
		jstType = getCtx().getJsType(name);
		if (jstType != null){
			return jstType;
		}
		
		//jstType = JstCache.getInstance().getType(name);
		if (name.equals("Object") || name.equals("java.lang.Object")) {
			jstType = getNativeJsObject();
		}
		if (jstType != null){
			return jstType;
		}
		
		//The JSNative is not VJO, so do not use vjo.Object for JSNative translation
		//TODO: below logic should be removed? check with Choi.
		//if (!getCtx().getConfig().isJsNativeTranslationEnabled()) {		
//		if (!(getCtx().getConfig().getConfigInitializer() instanceof JsNativeConfigInitializer)){
//			if (name.equals("Object") || name.equals("java.lang.Object")) {
//				//return VjoBaseJstLib.getInstance().getObjectJst();
//				return getNativeJsObject();
//			}
//		}
		if (name.equals("vjo.Object")) {
			return JstCache.getInstance().getType("vjo.Object");
		}
		if (jstNode instanceof JstMethod){
			jstType = ((JstMethod)jstNode).getParamType(name);
			if (jstType != null){
				return jstType;
			}
		}
		
		JstType ownerType = jstNode.getOwnerType();
		jstType = getParamType(name, jstNode);
		if (jstType != null){
			return jstType;
		}

		String typeName = name;
		if (typeName.startsWith("java.lang.") && typeName.lastIndexOf(".")==9) {
			typeName = name.substring(10);
		}
		TranslateCtx ctx = getCtx();
		TranslateInfo info = ctx.getTranslateInfo(ownerType);
    	
		jstType = info.getType(typeName, true);
		if (jstType != null){
			if (!typeName.equals(jstType.getSimpleName()) && !typeName.equals(jstType.getName())){
				addImport(jstType, ownerType, jstType.getName());
			}
			return jstType;
		}
		
		if (typeName.equals(ownerType.getSimpleName())){
			return ownerType;
    	}
		
		JstType jstLocalType = info.getLocalType(typeName);
		if (jstLocalType != null) {
			return jstLocalType;
		}
		
		String jstTypeName;
		if (null == DataTypeHelper.getNativeType(typeName)) {
			jstTypeName = ctx.getConfig().getPackageMapping().mapTo(typeName);
		} else {
			jstTypeName = typeName;
		}
		jstType = JstCache.getInstance().getType(jstTypeName);
		if (jstType != null){
			return jstType;
		}
    	
    	jstType = info.getUnknownType(typeName);
		if (jstType != null){
			return jstType;
		}
		
    	jstType = TranslateHelper.Type.getEmbededType(ownerType.getRootType(), typeName);
		if (jstType != null){
			return jstType;
		}
		
		jstType = TranslateHelper.Type.getDependedType(ownerType, typeName);
		if (jstType != null){
			return jstType;
		}
    	
    	jstType = getFromImport(typeName, jstNode.getOwnerType());
		if (jstType != null){
			return jstType;
		}
		
		jstType = getFromSamePackage(typeName, jstNode);
		if (jstType != null){
			return jstType;
		}
		
		return null;
	}

	public static JstType getNativeJsObject() {
		return JstCache.getInstance().getType(org.ebayopensource.dsf.jsnative.global.Object.class.getName());
	}
	
	public void addImport(final IJstType jstType, final JstType ownerType, final String key){
		
		if (null == key) return;
		if (jstType == null || jstType == ownerType){
			return;
		}
		if (!(jstType instanceof JstType)){
			return;
		}
		
		if (jstType.getRootType() == ownerType){
			return;
		}

		if (DataTypeHelper.isPrimitiveType(jstType.getName())){
			return;
		}
		
		if (VjoTranslateHelper.isVjoNativeType(jstType.getName())){
			return;
		}
		
		if (getCtx().isJsType(jstType)){
			return;
		}
		
		JstType outerType = ownerType;
		IJstNode parentNode = outerType.getParentNode();
		while (parentNode != null){
			parentNode = outerType.getParentNode();
			if (parentNode != null 
					&& parentNode.getOwnerType() != null 
					&& parentNode.getOwnerType() instanceof JstType){
				outerType = (JstType)parentNode.getOwnerType();
			}
			else {
				break;
			}
			parentNode = outerType.getParentNode();
		}
		
		TranslateCtx ctx = getCtx();
		if (ctx.isExcluded(jstType)){
			return;
		}

		if (ctx.isMappedToJS(jstType) || ctx.isJavaOnly(jstType)){
			return;
		}
		
		if ((jstType.isEmbededType() && jstType.getOuterType() == ownerType) 
				|| (jstType.isSiblingType() && jstType.getOuterType() == ownerType)){ 
			return;
		}
		String importKey = key;
		IJstType importType = jstType.getRootType();
		if (importType != jstType){
			if (key != null && key.equals(jstType.getSimpleName())){
				importKey = importType.getSimpleName();
			}
			else {
				importKey = importType.getName();
			}
		}
		else if (importKey == null){
			importKey = importType.getName();
		}
		
//		ITranslateConfigInitializer initializer = ctx.getConfig().getConfigInitializer();
//		
//		if (!(initializer instanceof JsNativeConfigInitializer)) {
//			IJstLib browserTypesLib = LibManager.getInstance().getBrowserTypesLib();
//			if (null != browserTypesLib && null != browserTypesLib.getType(importType.getName(), true)) {
//				return;
//			}
//		}
		
		if (!(importType instanceof JstType)) {
			return;
		}
		if (getCtx().isJavaOnly(importType)){
			return;
		}
		outerType.addImport(importKey, (JstType)importType);
	}
	
	//
	// Private
	//
	private IJstType getFromImport(final String name, final JstType ownerType){
		JstType rootType = ownerType.getRootType();
		TranslateInfo tInfo = getCtx().getTranslateInfo(rootType);
		String fullName = tInfo.getImported(name);
		if (fullName == null){
			return null;
		}
		fullName = getCtx().getConfig().getPackageMapping().mapTo(fullName);
		IJstType jstType = JstCache.getInstance().getType(fullName);
		if (jstType == null || getCtx().isMappedToJS(jstType) || getCtx().isMappedToVJO(jstType)){
			return null;
		}
		addImport(jstType, ownerType, name);
		tInfo.setType(name, jstType);
		return jstType;
	}
	
	private IJstType getFromSamePackage(final String name, final IJstNode jstNode){
		IJstType rootType = jstNode.getRootType();
		if (rootType == null){
			return rootType;
		}
		StringBuffer sb = new StringBuffer();
		if (rootType.getPackage() != null){
			sb.append(rootType.getPackage().getName()).append(".");
		}
		sb.append(name);
		String fullName = sb.toString();
		fullName = getCtx().getConfig().getPackageMapping().mapTo(fullName);
		return JstCache.getInstance().getType(fullName);
	}
	
	private IJstType getJstType(final ASTNode node, final String typeName, final JstType ownerType, boolean tryCustomTranslation){
		
		if (tryCustomTranslation){
			TranslateInfo tInfo = getCtx().getTranslateInfo(ownerType.getRootType());
			boolean enableShortName = true;
			String fullName = tInfo.getImported(typeName);			
			if (fullName == null){
				enableShortName = DataTypeHelper.isInJavaLang(typeName); //false for non java.lang types
				fullName = TranslateHelper.Type.resolveImplicitImport(typeName, ownerType.getRootType());
				if (fullName != null){
					tInfo.addImport(typeName, fullName);
				}
			}
			if (fullName == null){
				fullName = typeName;
			}
			IJstType jstType = getCustomTranslator().processType(fullName, node, ownerType);
			if (jstType != null){
				if (enableShortName) {
					addImport(jstType, ownerType, jstType.getSimpleName());
				}
				else {
					addImport(jstType, ownerType, jstType.getName());
				}
				return jstType;
			}
		}
		JstType type = createJstType(typeName, ownerType);
		return type;
	}
	
	private JstType createJstType(final String typeName, final JstType ownerType){

		if (typeName == null){
			return null;
		}
		
		JstType type = null;
		TranslateInfo tInfo = getCtx().getTranslateInfo(ownerType.getRootType());
		
		if (typeName.indexOf(".") > 0){
			String fullName = getCtx().getConfig().getPackageMapping().mapTo(typeName);
			type = JstCache.getInstance().getType(fullName, true);	
		}
		else {
			String fullName = tInfo.getImported(typeName);
			if (fullName == null){
				fullName = TranslateHelper.Type.resolveImplicitImport(typeName, ownerType.getRootType());
				if (fullName != null){
					tInfo.addImport(typeName, fullName);
				}
			}
			if (fullName == null){
				type = JstFactory.getInstance().createJstType(typeName, false);
				tInfo.addUnknownType(typeName, (JstType)type);
			}
			else {
				// 
				String translatedFullName = getCtx().getConfig().mapToNative(fullName);
				if(translatedFullName==null){
					translatedFullName = getCtx().getConfig().getPackageMapping().mapTo(fullName);
					
				}
				
				type = JstCache.getInstance().getType(translatedFullName, true);
			}
		}
		
		return type;
	}
	
	private JstParamType getParamType(final String name, final BaseJstNode jstNode){
		JstType ownerType = jstNode.getOwnerType();
		JstParamType jstType = ownerType.getParamType(name);
		if (jstType != null){
			return jstType;
		}
		JstType outerType = ownerType.getOuterType();
		while (outerType != null){
			jstType = outerType.getParamType(name);
			if (jstType != null){
				return jstType;
			}
			outerType = outerType.getOuterType();
		}
		return null;
	}
	
	private JstType processArrayType(final ArrayType type, final BaseJstNode jstNode) {
		IJstType eleType = processType(type.getComponentType(), jstNode);
		if (eleType == null){
			getLogger().logError(TranslateMsgId.NULL_RESULT, "Fail to translate element type", this, type, jstNode.getOwnerType());
			return null;
		}

		return JstFactory.getInstance().createJstArrayType(eleType, true);
	}
	
	private IJstType processTypeWithArgs(final ParameterizedType astType, final BaseJstNode jstNode){

		Type type = astType.getType();
		IJstType jstType = processType(type, jstNode);
		if (jstType == null){
			getLogger().logError(TranslateMsgId.NULL_RESULT, "Fail to translate parameterized type", this, type, jstNode.getOwnerType());
			return null;
		}
		if (!(jstType instanceof JstType)){
			getLogger().logError(TranslateMsgId.INVALID_TYPE, "jstType not a JstType", this, type, jstNode.getOwnerType());
			return null;
		}
		
		List<Type> args = astType.typeArguments();
		JstTypeWithArgs jpType = new JstTypeWithArgs((JstType)jstType);
		for (Type arg: args){
			jpType.addArgType(processType(arg, jstNode));
		}
		return jpType;
	}
	
	private IJstType processQualifiedType(final QualifiedType type, final BaseJstNode jstNode) {
		// TODO:  need to decide if we are going to support qualified types
		TranslateInfo tInfo = getCtx().getTranslateInfo(jstNode.getOwnerType());
		if (tInfo.getMode().hasImplementation()){
			return processName(type.getName(), jstNode);
		}
		else {
			return processType(type.getQualifier(), jstNode);
		}
	}
	
	private IJstType processWildcardType(final WildcardType astType, final BaseJstNode jstNode){
		Type type = astType.getBound();
		IJstType jstType = processType(type, jstNode);
		return new JstWildcardType(jstType, astType.isUpperBound());
	}
}

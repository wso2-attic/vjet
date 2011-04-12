/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeParameter;

import org.ebayopensource.dsf.javatojs.trace.TranslateMsgId;
import org.ebayopensource.dsf.javatojs.translate.config.PackageMapping;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomInfo;
import org.ebayopensource.dsf.javatojs.translate.custom.anno.IAnnoProcessor;
import org.ebayopensource.dsf.javatojs.util.AstBindingHelper;
import org.ebayopensource.dsf.jsgen.shared.classref.IClassR;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstDoc;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstParamType;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstTypeReference;
import org.ebayopensource.dsf.jst.declaration.JstType.Category;
import org.ebayopensource.dsf.jst.stmt.BlockStmt;

public class TypeTranslator extends BaseTranslator {
	
	//
	// API
	//
	/**
	 * Process TypeDeclaration AST node into supplied JstType
	 * @param jstType JstType
	 * @param astType TypeDeclaration
	 */
	public void processType(final AbstractTypeDeclaration abstractType, final JstType type){		
		JstType jstType = type;
		TranslateCtx ctx = getCtx();
		TranslateInfo tInfo = getCtx().getTranslateInfo(jstType);
		
		if (!tInfo.getMode().hasImplementation()){
			
			if (TranslateHelper.Type.isEmbededType(abstractType)){
				String name = abstractType.getName().toString();
				if (tInfo.getEmbeddedTypeCustomInfo(name).isExcluded()){
					return;
				}
				tInfo.addEmbededType(name);
				ctx.getTranslateInfo(type).addEmbededType(name);
				if (!name.equals(type.getSimpleName())){
					jstType = type.getEmbededType(name);
					if (jstType == null){
						jstType = ctx.getProvider().getTypeTranslator()
							.processEmbededType(abstractType, type);
					}
				}
			}
			
			CustomInfo cInfo = null;
			for (IAnnoProcessor ap: ctx.getConfig().getAnnoProcessors()){
				cInfo = ap.process(abstractType, jstType);
				if (cInfo != null && cInfo != CustomInfo.NONE){
					ctx.updateCustomInfo(jstType, cInfo);
					cInfo.setJstNode(jstType);
				}
			}
			if (cInfo != null && (cInfo.isMappedToJS() || cInfo.isMappedToVJO())){
				IJstType jsType = getCustomTranslator().processType(jstType.getName(), abstractType, jstType);
				String typeName = jstType.getSimpleName();
				IJstType toType;
				for (TranslateInfo info: ctx.getAllTranslateInfos()){
					toType = info.getType(typeName, false);
					if (toType != null && toType != jsType){
						info.setClearTypeRefs(true);
						info.setType(typeName, jsType);
					}
				}
			}
		}
			
		if (!tInfo.getStatus().isDeclTranlationDone()){
			getCustomTranslator().initialize(jstType);
			processTypeDecl(abstractType, jstType);
		}
		
		if (tInfo.getMode().hasImplementation()){
			if (ctx.isExcluded(jstType) 
					|| ctx.isJavaOnly(jstType) 
					|| ctx.isJSProxy(jstType) 
//					|| ctx.isMappedToJS(jstType) TODO fix JsNative build
					|| ctx.isMappedToVJO(jstType)){
				return ;
			}
			reDoDecl(abstractType, jstType);
			processTypeImpl(abstractType, jstType);
			tInfo.getStatus().setImplTranlationDone();
		}
	}
	
	public JstType processEmbededType(final AbstractTypeDeclaration embeddedType, JstType parentType){
		TranslateCtx ctx = getCtx();
        TranslateInfo tInfo = ctx.getTranslateInfo(parentType);
        String shortName = embeddedType.getName().toString();
		JstType innerType = parentType.getEmbededType(shortName);	
		if (innerType == null){
			innerType = (JstType)tInfo.getType(shortName, false);
    		if (innerType == null){
    			innerType = JstCache.getInstance()
					.getType(parentType.getName() + "." + shortName, true);
    		}
    		else {
    			JstType cachedType = JstCache.getInstance().addType(innerType, true);
    			if (cachedType != innerType){
    				tInfo.setClearTypeRefs(true);
    				innerType = cachedType;
    			}
    		}
    		innerType.setParent(parentType);
			innerType.setSource(new JstSource(new AstBinding(embeddedType)));
			tInfo.setType(shortName, innerType);
		}
		for (IAnnoProcessor ap: ctx.getConfig().getAnnoProcessors()){
			CustomInfo cInfo = ap.process(embeddedType, parentType);
			if (cInfo != null && cInfo != CustomInfo.NONE){
				tInfo.addEmbeddedTypeCustomInfo(shortName, cInfo);
				ctx.updateCustomInfo(innerType, cInfo);
			}
		}

		parentType.addInnerType(innerType);
		innerType.setOuterType(parentType);

		boolean isStatic = TranslateHelper.isStatic(embeddedType.modifiers());
		if (embeddedType instanceof TypeDeclaration){
			isStatic = ((TypeDeclaration)embeddedType).isInterface();
		}
    	innerType.getModifiers().setStatic(isStatic);
    	
    	ctx.getTranslateInfo(innerType).addMode(tInfo.getMode());
    	
    	IJstType outer = innerType.getOuterType();
    	String key = innerType.getSimpleName();
    	while (outer instanceof JstType){
    		tInfo = ctx.getTranslateInfo((JstType)outer);
    		tInfo.setType(key, innerType);
    		key = outer.getSimpleName() + "." + key;
    		outer = outer.getOuterType();
    	}
    	
    	return innerType;
	}
	
	//
	// Package Protected
	//
	void processBody(final List bodyDeclaration, final JstType jstType){
		if (getCustomTranslator().processTypeBody(bodyDeclaration, jstType)) {
			return;
		}
		
		TranslateInfo tInfo = getCtx().getTranslateInfo(jstType);
		String name;
        for (Object d: bodyDeclaration){
        	if (d instanceof FieldDeclaration){
        		getFieldTranslator().processField((FieldDeclaration)d, jstType);
        	}
        	else if (d instanceof Initializer){
        		Initializer initializer = (Initializer)d;
        		BlockStmt jstBlock = getCtx().getTranslateInfo(jstType).getJstInitializer(initializer);
        		if (jstBlock != null){
	        		getOtherTranslator().processBlock(initializer.getBody(), jstBlock.getBody());
	        		boolean isStatic = Modifier.isStatic(initializer.getModifiers());
	        		//instead of adding the whole block
/*	        		for (IStmt o:jstBlock.getBody().getStmts()) {
	        			jstType.addInit(o, isStatic);
	        		}
*/	        		jstType.addInit(jstBlock, isStatic);
        		}
        		else {
        			getLogger().logError(TranslateMsgId.MISSING_DATA_IN_TRANSLATE_INFO, 
        					"initializer not found", this, (Initializer)d, jstType);
        		}
        	}
        	else if (d instanceof MethodDeclaration){
        		MethodDeclaration astMtd = (MethodDeclaration)d;
        		getMtdTranslator().processMethod(astMtd, jstType);
        	}
        	else if (d instanceof AbstractTypeDeclaration){
        		AbstractTypeDeclaration embeddedType = (AbstractTypeDeclaration)d;
        		name = embeddedType.getName().toString();
	        	if (TranslateHelper.Type.isExcluded(embeddedType, jstType, name, jstType)){
	        		continue;
	        	}
        		JstType type = (JstType)jstType.getEmbededType(name);
	        	if (type == null){
	        		if (tInfo.isEmbededType(name)){
	        			continue;	// Excluded
	        		}
	        		type = jstType; // Anonymous case
	        	}
	        	processType((AbstractTypeDeclaration)d, type);
        	}
        	else {
        		getLogger().logUnhandledNode(this, (ASTNode)d, jstType);
        	}
        }
	}
	
	//
	// Private
	//
	private void reDoDecl(final AbstractTypeDeclaration astType, final JstType jstType){
		TranslateInfo tInfo = getCtx().getTranslateInfo(jstType);
		if (tInfo.clearTypeRefs()){
			processTypeSigniture(astType, jstType);
//			tInfo.setClearTypeRefs(false);
		}
		for (IJstType extend: jstType.getExtends()){
			if (extend instanceof JstType){
				reDoDecl(AstBindingHelper.getAstType(extend), (JstType)extend);
			}
		}
	}
	
	private void processTypeDecl(final AbstractTypeDeclaration abstractType, final JstType jstType){
		
		if (jstType == null){
			return;
		}
		
		
		//	Javadoc
		processTypeJavadoc(abstractType, jstType);
		
		processTypeSigniture(abstractType, jstType);
		
		// Modifiers
		JstModifiers modifiers = jstType.getModifiers();
		modifiers.setDefault(); //to clear public access set by JstType
		for (Object m: abstractType.modifiers()){
			if (m instanceof Modifier){
				modifiers.merge(((Modifier)m).getKeyword().toFlagValue());
			}
			else if (m instanceof Annotation){
				getOtherTranslator().processAnnotation((Annotation) m, jstType);
				continue;
			}
			else {
				getLogger().logUnhandledNode(this, (ASTNode)m, jstType);
				continue;
			}
        	
        }
		if (jstType.isInterface()){
			modifiers.setPublic();
		}
		
        // Body 
        // - First Pass
        for (Object d: abstractType.bodyDeclarations()){
        	if (d instanceof AbstractTypeDeclaration){
        		processEmbededType((AbstractTypeDeclaration)d, jstType);
        	}
        }
	}
	
	private void processTypeJavadoc(AbstractTypeDeclaration abstractType, JstType jstType) {
		Javadoc javadoc = abstractType.getJavadoc();
		if (javadoc == null) {
			return;
		}
		JstDoc jstDoc = new JstDoc(javadoc.toString());
		jstType.setDoc(jstDoc);
		
	}

	private void processTypeSigniture(final AbstractTypeDeclaration abstractType, final JstType jstType){
		if (jstType == null){
			return;
		}
		jstType.clearParams();
		jstType.clearExtends();
		jstType.clearSatisfies();
		jstType.clearEnumValues();
		
		if (abstractType instanceof TypeDeclaration){
			List<TypeParameter> params = ((TypeDeclaration)abstractType).typeParameters();
			for (TypeParameter p: params){
				JstParamType pType = jstType.addParam(p.getName().toString());
				if (pType == null){
					continue;
				}
				for (Object b: p.typeBounds()){
					if (b instanceof Type){
						pType.addBound(getDataTypeTranslator().processType((Type)b, jstType));
					}
				}
			}
		}
		
		String typeName = abstractType.getName().toString();
		jstType.setSimpleName(typeName);
		TranslateCtx ctx = getCtx();
        TranslateInfo tInfo = ctx.getTranslateInfo(jstType);
        
        if (abstractType instanceof TypeDeclaration){
        	
        	TypeDeclaration astType = (TypeDeclaration)abstractType;
        	if (astType.isInterface()){
        		jstType.setCategory(Category.INTERFACE);
        	} else if ((astType.getModifiers() & Modifier.ABSTRACT) == Modifier.ABSTRACT) {
        		jstType.getModifiers().setAbstract();
        	} 
       
			// Extends
	        Type type = astType.getSuperclassType();
	        if (type != null){
	        	String typeFullName = jstType.getPackage().getName() + "." + type.toString();
	        	IJstType baseType = JstCache.getInstance().getType(typeFullName, false);
	        	if (baseType == null){
	        		baseType = getDataTypeTranslator().processType(type, jstType);
	        	}
	        	if (baseType != null){
		        	tInfo.setBaseType(baseType);
		        	JstTypeReference extendRef = new JstTypeReference(baseType);
		        	extendRef.setSource(new JstSource(new AstBinding(type)));
		        	jstType.addExtend(extendRef); 
	        	}
	        }

	        // Interfaces
	        processSuperInterfaces(astType.superInterfaceTypes(), jstType);

	        // add default vjo.Object extends if using NativeProxy
	        if(usingNativeProxy(jstType)){
	        	jstType.clearExtends();
	        	jstType.addExtend(JstCache.getInstance().getType("vjo.Object"));
	        }
	        
	        // Add default extend if none exists
	        if (canAddDefaultExtendForClass(jstType)) {
				jstType.addExtend(JstCache.getInstance().getType("vjo.Object"));
	        }
        }
        else if (abstractType instanceof EnumDeclaration){
        	
        	jstType.setCategory(Category.ENUM);
        	jstType.getModifiers().setFinal();        	
        	if (jstType.getParentNode() != null) {
        		jstType.getModifiers().setStatic(true);
        	}
        	EnumDeclaration enumType = (EnumDeclaration)abstractType;

        	// Interfaces
	        processSuperInterfaces(enumType.superInterfaceTypes(), jstType);
	        
	        // Enum Constants
	        FieldTranslator fieldTranslator = getCtx().getProvider().getFieldTranslator();
            for (Object d: enumType.enumConstants()){
            	fieldTranslator.processEnumConstsDecl((EnumConstantDeclaration)d, jstType);
            }
            
            //Add default extend if none exists
			if (jstType.getExtend() == null) {
				jstType.addExtend(JstCache.getInstance().getType("vjo.Enum"));
			}
        }
        else {
        	getLogger().logUnhandledNode(this, abstractType, jstType);
        }
	}

	private boolean usingNativeProxy(JstType jstType) {
		if(jstType.getExtend()!=null && jstType.getExtend().getName().equals(IClassR.NativeJsProxyName)){
			return true;
		}
		return false;
	}

	private boolean canAddDefaultExtendForClass(final JstType jstType) {
		if (!jstType.isClass()) {
			return false;
		}
		if (jstType.getExtend() == null) {
			return true;
		}
		
		String name = jstType.getExtend().getName();
		if (name .equals("Object") || name.equals("java.lang.Object")) {
			return true;
		}
		
		return false;
	}

	private void processTypeImpl(final AbstractTypeDeclaration abstractType, final JstType jstType){
		
        if (abstractType instanceof EnumDeclaration){

        	EnumDeclaration enumType = (EnumDeclaration)abstractType;
	        
	        // Enum Constants
        	FieldTranslator fieldTranslator = getCtx().getProvider().getFieldTranslator();
            for (Object d: enumType.enumConstants()){
            	fieldTranslator.processEnumConstsImpl((EnumConstantDeclaration)d, jstType);
            }
        }
        
        // Body 
        // - Second Pass
        processBody(abstractType.bodyDeclarations(), jstType);
	}
	
	private void processSuperInterfaces(final List superInterfaces, final JstType jstType){
		if (superInterfaces.isEmpty()) {
			return;
		}
		TranslateCtx ctx = getCtx();
		TranslateInfo tInfo = ctx.getTranslateInfo(jstType);
		DataTypeTranslator dataTypeTranslator = getDataTypeTranslator();
		AstBinding binding = AstBindingHelper.getAstSrcBinding(jstType);
		Type astType;
		IJstType interfaceType;
		String simpleName;
		String fullName;
		PackageMapping pkgMapping = ctx.getConfig().getPackageMapping();
		for (Iterator it= superInterfaces.iterator(); it.hasNext();) {
			astType = (Type)it.next();
			simpleName = astType.toString();
			interfaceType = dataTypeTranslator.processType(astType, jstType);
			if (binding != null && interfaceType != null){
				fullName = interfaceType.getName();
				binding.addInterfaceName(simpleName, pkgMapping.mapFrom(fullName));
			}
			if (ctx.isJavaOnly(interfaceType)
				|| ctx.isExcluded(interfaceType)){
				continue;
			}
			if (ctx.isJavaOnly(interfaceType)
        		|| ctx.isExcluded(interfaceType)){
        		tInfo.addInterfaceType(interfaceType);
        	}
        	else {
        		JstTypeReference jstTypeRef = new JstTypeReference(interfaceType);
				jstTypeRef.setSource(new JstSource(new AstBinding(astType)));
				if (jstType.isInterface()){
					jstType.addExtend(jstTypeRef);
				}
				else {
					jstType.addSatisfy(jstTypeRef);
				}
        	}
		}
	}
}

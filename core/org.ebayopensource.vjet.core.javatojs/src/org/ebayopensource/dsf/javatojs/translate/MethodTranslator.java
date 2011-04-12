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
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeParameter;

import org.ebayopensource.dsf.javatojs.trace.TranslateMsgId;
import org.ebayopensource.dsf.javatojs.translate.config.MethodKey;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomInfo;
import org.ebayopensource.dsf.javatojs.translate.custom.anno.IAnnoProcessor;
import org.ebayopensource.dsf.javatojs.translate.custom.jdk.JavaLangMeta;
import org.ebayopensource.dsf.javatojs.util.AstBindingHelper;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstArray;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstDoc;
import org.ebayopensource.dsf.jst.declaration.JstFactory;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstParamType;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.stmt.DispatchStmt;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.vjo.meta.VjoKeywords;

public class MethodTranslator extends BaseTranslator {
	
	private static final String AMBIGUIOUS_ARGS = "Arguments of overlaod methods has ambiguity in JS";
	
	//
	// API
	//
	/**
	 * Translate AST method to Vjo method
	 * @param astMtd MethodDeclaration
	 * @param jstType JstType
	 */
	public JstMethod processMethod(final MethodDeclaration astMtd, final JstType jstType){
		
		TranslateCtx ctx = getCtx();
		TranslateInfo tInfo =  getCtx().getTranslateInfo(jstType);	
		MethodKey mtdKey = MethodKey.genMethodKey(astMtd);
		String mtdName = astMtd.getName().toString();
		mtdName = getNameTranslator().processVarName(mtdName);
		
		CustomInfo cInfo = null;
		if (!tInfo.getMode().hasImplementation()){
			for (IAnnoProcessor ap: ctx.getConfig().getAnnoProcessors()){
				cInfo = ap.process(astMtd, jstType);
				if (cInfo != null && cInfo != CustomInfo.NONE){
					tInfo.addMethodCustomInfo(mtdKey, cInfo);
				}
			}
			if (!TranslateHelper.Method.includeMethodForDecl(astMtd, cInfo, jstType)){
				return null;
			}
		}
		else {
			if (!TranslateHelper.Method.includeMethodForImpl(astMtd, cInfo, jstType)){
				return null;
			}
		}
		cInfo = tInfo.getMethodCustomInfo(mtdKey);
		
		if (cInfo != null){
			if (!cInfo.isNone()){
				JstMethod jstMtd = tInfo.getRemovedMtds(mtdName).get(astMtd);
				if (jstMtd == null){
					jstMtd = new JstMethod(mtdName); 
					jstMtd.setParent(jstType);
					jstMtd.setSource(new JstSource(new AstBinding(astMtd)));
					processMethodDecl(astMtd, jstMtd, cInfo);
					tInfo.addRemovedMtd(astMtd, jstMtd);
				}
				return jstMtd;
			}
		}
		
		JstMethod jstMtd = null;

		if (!tInfo.getStatus().isDeclTranlationDone()){
			jstMtd = new JstMethod(mtdName); 
			jstMtd.setParent(jstType);
			jstMtd.setSource(new JstSource(new AstBinding(astMtd)));
			processMethodDecl(astMtd, jstMtd, cInfo);
		}
		
		if (cInfo != null && (cInfo.isJavaOnly() || cInfo.isJSProxy())){
			return jstMtd;
		}
		
		if (tInfo.getMode().hasImplementation()){
			jstMtd = (JstMethod)TranslateHelper.Method.getMethod(astMtd, jstType);
			if (jstMtd == null){
				if( !( getCtx().isJavaOnly(jstType) || (cInfo!=null && cInfo.isExcluded()))){
					getLogger().logError(TranslateMsgId.NULL_RESULT, "failed to translate method '" +
							astMtd.getName().toString()+"'", 
							this, astMtd, jstType);
				}
				return null;
			}
			// Validate
			IJstType rtnType = jstMtd.getRtnType();
			if (rtnType != null){
				TranslateHelper.Type.validateTypeReference(
					rtnType, astMtd.getReturnType2(), jstMtd, jstType, this);
			}
			for (JstArg jstArg: jstMtd.getArgs()){
				IJstType pType = jstArg.getType();
				if (pType != null){
					TranslateHelper.Type.validateTypeReference(
						pType, astMtd.getReturnType2(), jstMtd, jstType, this);
				}
			}
			// Re-process
			if (tInfo.clearTypeRefs()){
				processMethodSigniture(astMtd, jstMtd);
			}
			// Process impl
			processMethodImpl(astMtd, jstMtd);
		}

		return jstMtd;
	}
	
	//
	// Private
	//
	private void processMethodDecl(final MethodDeclaration astMtd, final JstMethod jstMtd, CustomInfo cInfo){
			
		JstType jstType = jstMtd.getOwnerType();
		TranslateInfo tInfo = getCtx().getTranslateInfo(jstType);
	
		String mtdName = astMtd.getName().toString();
		mtdName = getNameTranslator().processVarName(mtdName);
		boolean isStatic = TranslateHelper.isStatic(astMtd.modifiers());
		
		for (IJstProperty p: jstType.getProperties()){
			if (p.getName().getName().equals(mtdName)) {
				getLogger().logError(TranslateMsgId.DUPLICATE_NAME, "Method name is same as other field: " , this, astMtd,jstType);	
			}			
		}
		
		// Javadoc
		processMethodJavadoc(astMtd, jstMtd);
		
		// Signiture
		processMethodSigniture(astMtd, jstMtd);
		
		// Modifiers
		JstModifiers modifiers = jstMtd.getModifiers();
		for (Object m: astMtd.modifiers()){
			if (m instanceof Modifier){
				modifiers.merge(((Modifier)m).getKeyword().toFlagValue());
			}
			else if (m instanceof Annotation) {
				getOtherTranslator().processAnnotation((Annotation) m, jstMtd);
				continue;
			}
			else {
				getLogger().logUnhandledNode(this, (ASTNode)m, jstMtd);
				continue;
			}
		}
		if (jstType.isInterface()){
			modifiers.setPublic();
		}
		
		if (cInfo != null && !cInfo.isNone()){
			jstType.addMethod(jstMtd);
			return;
		}

		// Add to Type
		JstMethod firstOne = null;
		MethodKey mtdKey = null;
		if (astMtd.isConstructor()){
			mtdName = VjoKeywords.CONSTRUCTS;
			jstMtd.setName(mtdName);
			jstMtd.setIsConstructor(true);
			firstOne = jstType.getConstructor();
			if (tInfo.isOverloaded(mtdName, isStatic)){
				if (hasArgConflict(tInfo.getOverloaded(mtdName, isStatic),jstMtd)) {
					getLogger().logError(TranslateMsgId.INVALID_ARGS, AMBIGUIOUS_ARGS, this, astMtd,jstType);
				}				
				tInfo.addOverloaded(jstMtd);
				jstType.addMethod(jstMtd);
				addDispatching(jstMtd, jstType);
			}
			else if (firstOne != null){
				if (hasArgConflict(firstOne,jstMtd)) {
					getLogger().logError(TranslateMsgId.INVALID_ARGS, AMBIGUIOUS_ARGS, this, astMtd,jstType);
				}				
				jstType.setConstructor(null);
				tInfo.addOverloaded(firstOne);
				tInfo.addOverloaded(jstMtd);
				JstMethod dispatcher = addDispatching(firstOne, jstType);
				jstType.setConstructor(dispatcher);
				addDispatching(jstMtd, jstType);
				jstType.addMethod(firstOne);
				jstType.addMethod(jstMtd);
			}
			else {
				jstType.setConstructor(jstMtd);
			}
		}
		else {
			for (IJstMethod m: jstType.getMethods(isStatic)){
				if (!mtdName.equals(m.getName().getName())){
					continue;
				}
				mtdKey = MethodKey.genMethodKey(AstBindingHelper.getAstMethod(m));
				CustomInfo c = tInfo.getMethodCustomInfo(mtdKey);
				if ((m instanceof JstMethod) && c.isNone()){
					firstOne = (JstMethod)m;
					break;
				}
			}

			if (tInfo.isOverloaded(mtdName, TranslateHelper.isStatic(astMtd.modifiers()))){
				if (hasArgConflict(tInfo.getOverloaded(mtdName, isStatic),jstMtd)) {
					getLogger().logError(TranslateMsgId.INVALID_ARGS, AMBIGUIOUS_ARGS, this, astMtd,jstType);
				}
				tInfo.addOverloaded(jstMtd);
				jstType.addMethod(jstMtd);
				addDispatching(jstMtd, jstType);
			}
			else if (firstOne != null){
				if (hasArgConflict(firstOne,jstMtd)) {
					getLogger().logError(TranslateMsgId.INVALID_ARGS, AMBIGUIOUS_ARGS, this, astMtd,jstType);
				}
				jstType.removeMethod(mtdName, isStatic);
				tInfo.addOverloaded(firstOne);
				tInfo.addOverloaded(jstMtd);
				JstMethod dispatcher = addDispatching(firstOne, jstType);
				jstType.addMethod(dispatcher);
				addDispatching(jstMtd, jstType);
				jstType.addMethod(firstOne);
				jstType.addMethod(jstMtd);
			}
			else {
				jstType.addMethod(jstMtd);
			}
		}
	}
	
	private void processMethodJavadoc(MethodDeclaration astMtd, JstMethod jstMtd) {
		Javadoc javadoc = astMtd.getJavadoc();
		if (javadoc == null) {
			return;
		}
		JstDoc jstDoc = new JstDoc(javadoc.toString());
		jstMtd.setDoc(jstDoc);
	}

	private void processMethodSigniture(final MethodDeclaration astMtd, final JstMethod jstMtd){
		
		JstType jstType = jstMtd.getOwnerType();
//		String mtdName = astMtd.getName().toString();
//		mtdName = getNameTranslator().processVarName(mtdName);
		
		DataTypeTranslator typeTranslator = getDataTypeTranslator();
		TranslateInfo tInfo = getCtx().getTranslateInfo(jstType);
		
		List<TypeParameter> params = astMtd.typeParameters();
		for (TypeParameter p: params){
			JstParamType pType = jstMtd.addParam(p.getName().toString());
			if (pType == null){
				continue;
			}
			for (Object b: p.typeBounds()){
				if (b instanceof Type){
					pType.addBound(getDataTypeTranslator().processType((Type)b, jstMtd));
				}
			}
		}

		// Args
		SingleVariableDeclaration sv;
		String name;
		if (tInfo.getStatus().isDeclTranlationDone() && tInfo.clearTypeRefs()){
			jstMtd.removeArgs();
		}
		JstArg jstArg;
		for (Object p: astMtd.parameters()){
			if (p instanceof SingleVariableDeclaration){
				sv = (SingleVariableDeclaration)p;
				sv.getModifiers();
				name = getNameTranslator().processVarName(sv.getName(), jstMtd.getBlock(true));
			
				IJstType type = typeTranslator.processType(sv.getType(), jstMtd);
				if (sv.getExtraDimensions() > 0) {
					for (int i=0; i<sv.getExtraDimensions(); i++){
						type = JstFactory.getInstance().createJstArrayType(type, true);
					}
				}
				jstArg = new JstArg(type, name, sv.isVarargs(), false, (sv.getModifiers()==Modifier.FINAL));
				jstArg.setSource(new JstSource(new AstBinding(sv.getType())));
				if (sv.isVarargs()) {
					type = JstFactory.getInstance().createJstArrayType(type, false);
				}
				jstMtd.getBlock(true).getVarTable().addVarType(name, type);
				jstMtd.addArg(jstArg);
			}
			else {
				getLogger().logUnhandledNode(this, (ASTNode)p, jstMtd);
			}
		}
		
		// Return Type
		Type rtnType = astMtd.getReturnType2();
		if (rtnType != null){
			jstMtd.setRtnType(typeTranslator.processType(rtnType, jstMtd));
		}
	}
	
	private void processMethodImpl(final MethodDeclaration astMtd, final JstMethod jstMtd){
		
		// Body
		Block body = astMtd.getBody();
		
		if (body != null){
			// Check if any custom translator can translate this method body.
			if (!getCustomTranslator().processMethodBody(astMtd, jstMtd)) {
				getOtherTranslator().processBlock(body, jstMtd.getBlock(true));
			}
			//add dispatch statement if there's an overloading of super method
			checkAndProcessBaseOverload(astMtd, jstMtd);
		}
	}

	private void checkAndProcessBaseOverload(final MethodDeclaration astMtd, final JstMethod jstMtd) {
		JstType jstType = jstMtd.getOwnerType();
		IJstType extend = jstType.getExtend();
		boolean hasSuperOverload = false;
		String mtdName = jstMtd.getOriginalName();
		while (extend !=null && !JavaLangMeta.VJO_OBJECT.equals(extend.getName())) {
			if (hasSuperOverload) {
				break;
			}
			IJstMethod m = extend.getMethod(mtdName, false);
			boolean isOverride = false;
			
			if (m!=null) {
				List<JstArg> args1 = m.getArgs();
				List<JstArg> args2 = jstMtd.getArgs();
				if (args1.size() == args2.size()) {
					if (args1.size()==0) {
						isOverride = true;
					} else {
						for (int i=0; i<args1.size(); i++) {
							if (!args1.get(i).getType().getName().equals(
									args2.get(i).getType().getName())) {
								isOverride = false;
								break;
							}
							isOverride = true;
						}
					}
				}
			}
			hasSuperOverload = (m != null)
					&& !m.isAbstract()
					&& !jstMtd.isStatic()
					&& (!isOverride || m.getOverloaded().size()!=0);
			extend = extend.getExtend();
		}
		TranslateInfo tInfo = getCtx().getTranslateInfo(jstType);
		boolean isOverloaded = tInfo.isOverloaded(mtdName, TranslateHelper.isStatic(astMtd.modifiers()));
		boolean isConstructor = jstMtd.isConstructor();
		if (hasSuperOverload && !isConstructor && !isOverloaded) {
			jstType.removeMethod(jstMtd.getOriginalName(), false);
			JstMethod dispatcher = addDispatching(jstMtd, jstType);
			jstType.addMethod(dispatcher);
			jstType.addMethod(jstMtd);
			tInfo.addOverloaded(jstMtd);
		}
	}

	
	private JstMethod addDispatching(JstMethod jstMtd, JstType jstType){
		
		final MethodDeclaration astMtd = (MethodDeclaration)AstBindingHelper.getAstNode(jstMtd);
		if (astMtd == null){
			getLogger().logError(TranslateMsgId.NULL_INPUT, "astMtd is null for " + jstMtd.getName(), 
					this, AstBindingHelper.getAstNode(jstType), jstType);
			return jstMtd;
		}
		
		String mtdName = astMtd.getName().toString();
		mtdName = getNameTranslator().processVarName(mtdName);
		JstMethod dispatcher;
		if (astMtd.isConstructor()){
			mtdName = VjoKeywords.CONSTRUCTS;
			dispatcher = jstType.getConstructor();
		}
		else {
			dispatcher = (JstMethod)jstType.getMethod(mtdName, jstMtd.isStatic());
		}
		
		if (dispatcher == null){
			if (astMtd.isConstructor()) {
				dispatcher = new JstMethod(mtdName).setIsConstructor(true);
			}
			else {
				dispatcher = new JstMethod(mtdName, jstMtd.getRtnType());
				for(JstArg arg: jstMtd.getArgs()){
					dispatcher.addArg(arg);
				}
			}
			dispatcher.setParent(jstType);
		}
		dispatcher.addOverloaded(jstMtd);
		DispatchStmt dispatchStmt = null;
		JstBlock body = dispatcher.getBlock(true);
		if (dispatcher.getBlock(true).getStmts().isEmpty()){
			dispatchStmt = new DispatchStmt();
			body.addStmt(dispatchStmt);
		}
		else {
			for (IStmt s: body.getStmts()){
				if (s instanceof DispatchStmt){
					dispatchStmt = (DispatchStmt)s;
					break;
				}
			}	
		}
		
		if (dispatchStmt != null){	
			dispatchStmt.addMethod(jstMtd);
		}
		
		dispatcher.getModifiers().merge(astMtd.getModifiers());
		
		return dispatcher;
	}
	
	private boolean isPrimitiveNumeric(String type){
        if (type.equals("int") || type.equals("float") || type.equals("byte") || 
        	type.equals("short") || type.equals("double") ||type.equals("long")) {
        	return true;
        } else 
        	return false;
	}
	
	private boolean isMappedToStringInJs(String type){
        if (type.equals("char") || type.equals("String")) {
        	return true;
        } else 
        	return false;
	}
	
	private boolean hasArgConflict(Map<Integer,List<JstMethod>> jstMtds, JstMethod jstMtd) {
		if (TranslateCtx.ctx().isJSProxy(jstMtd.getRootType())) {
			return false;
		} 
		for(List<JstMethod> list: jstMtds.values()){
			for (IJstMethod mtd: list){
				if (hasArgConflict(mtd, jstMtd)){
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean hasArgConflict(IJstMethod jstMtd1, JstMethod jstMtd2) {
		if (TranslateCtx.ctx().isJSProxy(jstMtd2.getRootType())) {
			return false;
		} 
		List<JstArg> argsFirst = jstMtd1.getArgs();
		List<JstArg> args = jstMtd2.getArgs();
		if (argsFirst.size() != args.size()) {
			return false;
		}

		IJstType type1;
		IJstType type2;
		for (int i = 0; i < args.size();i++){
			if (argsFirst.get(i) == null || args.get(i) == null  || 
				argsFirst.get(i).getType() == null || args.get(i).getType() == null) {
				getLogger().logError(TranslateMsgId.NULL_RESULT, "arg or arg type is null", this, 
					AstBindingHelper.getAstNode(jstMtd2), jstMtd2);
				continue;
			}
			type1 = argsFirst.get(i).getType();
			type2 = args.get(i).getType();
			if  ((!isPrimitiveNumeric(type1.getName()) || !isPrimitiveNumeric(type2.getName()))
				&& (!(type1 instanceof JstArray) || !(type2 instanceof JstArray))
				&& (!isMappedToStringInJs(type1.getName()) || !isMappedToStringInJs(type2.getName()))) {
				return false;
			}
		}

		return true;
	}
}

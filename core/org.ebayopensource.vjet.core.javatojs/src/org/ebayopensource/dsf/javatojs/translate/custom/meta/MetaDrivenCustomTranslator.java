/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.custom.meta;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.Type;

import org.ebayopensource.dsf.javatojs.trace.TranslateMsgId;
import org.ebayopensource.dsf.javatojs.translate.BaseTranslator;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.TranslateHelper;
import org.ebayopensource.dsf.javatojs.translate.TranslateInfo;
import org.ebayopensource.dsf.javatojs.translate.config.MethodKey;
import org.ebayopensource.dsf.javatojs.translate.config.PackageMapping;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomAttr;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomInfo;
import org.ebayopensource.dsf.javatojs.translate.custom.ICustomTranslator;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArray;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.expr.PtyGetter;
import org.ebayopensource.dsf.jst.expr.TextExpr;
import org.ebayopensource.dsf.jst.stmt.PtySetter;
import org.ebayopensource.dsf.jst.stmt.TextStmt;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.util.DataTypeHelper;

/**
 * Default custom translator that operates based on meta data.
 */
public class MetaDrivenCustomTranslator extends BaseTranslator 
	implements ICustomTranslator {

	private ICustomMetaProvider m_metaProvider;
	private Set<IJstType> m_initializedTypes = new HashSet<IJstType>();
	
	//
	// Constructor
	//
	/**
	 * Constructor.
	 * @param metaProvider ICustomMetaProvider 
	 * @exception RuntimeException if metaProvider is null
	 */
	public MetaDrivenCustomTranslator(final ICustomMetaProvider metaProvider){
		if (metaProvider == null){
			throw new RuntimeException("metaProvider cannot be null");
		}
		m_metaProvider = metaProvider;
	}
	
	//
	// Satisfy ICustomTranslator
	//
	/**
	 * @see ICustomTranslator#initialize(JstType)
	 */
	public synchronized void initialize(final JstType jstType){
		
		if (jstType == null || m_initializedTypes.contains(jstType)){
			return;
		}
		
		CustomType cType =  getMetaProvider().getCustomType(jstType.getName());
		if (cType == null){
			return;
		}
		
		TranslateCtx ctx = getCtx();
		TranslateInfo tInfo =  ctx.getTranslateInfo(jstType);	
		CustomInfo cInfo;
		
		// Type-level
		CustomAttr cAttr = cType.getAttr();
		if (!cAttr.isNone()){
			ctx.setCustomAttr(jstType, cAttr);
		}
		
		if (cAttr.isJavaOnly() || cAttr.isJsProxy()){
			if (!jstType.getName().equals(cType.getJstName())){
				ctx.setNewName(jstType, cType.getJstName());
			}
		}	
		else if (cAttr.isMappedToJS()){
			IJstType type = getDataTypeTranslator().toJstType(cType.getJstName(), jstType);
			if (type != null){
				ctx.addJsType(type.getName(), type);
			}
		}

		// Method-level
		Map<String,Map<MethodKey,CustomMethod>> methods =  cType.getAllCustomMethods();
		for (Map<MethodKey,CustomMethod> map: methods.values()){
			for( CustomMethod cMtd: map.values()){
				cInfo = null;
				cAttr = cMtd.getAttr();
				if(!cAttr.isNone()){
					cInfo = new CustomInfo(cAttr);
				}
				if (!cMtd.getJavaName().equals(cMtd.getJstName())){
					if (cAttr.isJavaOnly() || cAttr.isJsProxy()){
						if (cInfo == null){
							cInfo = new CustomInfo();
						}
						cInfo.setName(cMtd.getJstName());
					}
				}
				if (cInfo != null){
					tInfo.addMethodCustomInfo(cMtd.getKey(), cInfo);
				}
			}	
		}
		
		// Field-level
		Collection<CustomField> fieldList =  cType.getAllCustomFields();		
		for (CustomField cFld: fieldList){
			cInfo = null;
			cAttr = cFld.getAttr();
			if(!cAttr.isNone()){
				cInfo = new CustomInfo(cAttr);
			}
			if (!cFld.getJavaName().equals(cFld.getJstName())){
				if (cAttr.isJavaOnly() || cAttr.isJsProxy()){
					if (cInfo == null){
						cInfo = new CustomInfo();
					}
					cInfo.setName(cFld.getJstName());
				}
			}
			if (cInfo != null){
				tInfo.addFieldCustomInfo(cFld.getJavaName(), cInfo);
			}
		}	
		
		m_initializedTypes.add(jstType);
	}
	
	/**
	 * @see ICustomTranslator#processType(String, ASTNode, BaseJstNode)
	 */
	public IJstType processType(
			final String javaTypeName, 
			final ASTNode astNode, 
			final BaseJstNode jstNode){
		
		CustomType cType =  getMetaProvider().getCustomType(javaTypeName);
		if (cType == null){
			return null;
		}
		
		TranslateCtx ctx = getCtx();
		
		JstType ownerType = jstNode.getOwnerType();
		
		// Get jstType based on meta
		IJstType jstType = null;
		CustomAttr cAttr = cType.getAttr();
		if (cAttr.isJavaOnly() || cAttr.isJsProxy()){
			jstType = getDataTypeTranslator().toJstType(javaTypeName, ownerType);
		}
		else if (cAttr.isMappedToJS() || cAttr.isMappedToVJO()){
			JstType tempType = JstCache.getInstance().getType(cType.getJstName(), true);
			if(tempType.getExtends().size()==0){
				tempType.addExtend(JstCache.getInstance().getType("vjo.Object"));
			}
			jstType = tempType;
			
		}
		else {
			jstType = getDataTypeTranslator().toJstType(cType.getJstName(), ownerType);
		}

		if (jstType == null){
			return null;
		}
		
		// Processing for the reference to jstType
		if (cAttr.isExcluded()){
			TranslateHelper.Type.validateTypeReference(jstType, astNode, jstNode, this);
		}
		else if (cAttr.isJsProxy() || cAttr.isMappedToVJO()){
			getDataTypeTranslator().addImport(jstType, ownerType, jstType.getName());
		}
		else if (cAttr.isMappedToJS()){
			ctx.addJsType(jstType.getName(), jstType);
		}
		
		ctx.getTranslateInfo(ownerType)
			.setType(TranslateHelper.getShortName(javaTypeName), jstType);
	
		// Init-processing for meta of jstType  
		if (jstType instanceof JstType){	
			initialize((JstType)jstType);
		}
		
		return jstType;
	}
	
	/**
	 * @see ICustomTranslator#processIdentifier(Name, boolean, boolean, IExpr, JstIdentifier, BaseJstNode)
	 */
	public JstIdentifier processIdentifier(
			final Name astName, 
			final boolean hasSuper, 
			final boolean hasThis, 
			final IExpr jstQualifier, 
			final JstIdentifier jstIdentifier, 
			final BaseJstNode jstNode){

		// Process simple name
		if (astName.isSimpleName()){
			return processSimpleName(
					(SimpleName)astName, 
					hasSuper, 
					hasThis, 
					jstQualifier, 
					jstIdentifier, 
					jstNode);
		}
		
		if (jstIdentifier.getQualifier() == null){
			return null;
		}
		
		QualifiedName qualifiedName = (QualifiedName)astName;
		
		// Process qualifier
		JstIdentifier cQualifier = processIdentifier(
					qualifiedName.getQualifier(), 
					hasSuper, 
					hasThis, 
					jstQualifier, 
					jstIdentifier.getQualifier(), 
					jstNode);

		if (cQualifier != null){
			jstIdentifier.setQualifier(cQualifier);
		}
		
		// Process qualified simple name
		return processSimpleName(
				qualifiedName.getName(), 
				false, 
				false, 
				jstIdentifier.getQualifier(), 
				jstIdentifier, 
				jstNode);
	}
	
	/**
	 * @see ICustomTranslator#processInstanceCreation(ClassInstanceCreation, IExpr, IJstType,List<IExpr>,BaseJstNode)
	 */
	public IExpr processInstanceCreation(
			final ClassInstanceCreation cic, 
			final IExpr jstQualifier, 
			final IJstType jstType,
			final List<IExpr> jstArgs,
			final BaseJstNode jstNode){
		
		if (jstType == null){
			return null;
		}
		
		// Get custom type
		CustomType cType =  getMetaProvider().getCustomType(jstType.getName());
		if (cType == null){
			return null;
		}
		
		// Delegate to processor if registered
		IExpr rtnExpr = delegate(cic, jstQualifier, cType, jstNode);
		if (rtnExpr != null){
			return rtnExpr;
		}
		
		// Get custom method for the constructor
		CustomMethod cMtd = getCustomMethod(
				cType, 
				jstType.getSimpleName(),
				jstArgs,
				jstType.getConstructor());
		if (cMtd == null ) {	
			return null;
		}
		
		// Processing based on custom method for the constructor
		String jstMtdName = cMtd.getJstName();
		JstIdentifier identifier = new JstIdentifier(jstMtdName);
		if (cMtd.getJstOwnerTypeName() != null) {
			JstType type = JstCache.getInstance().getType(cMtd.getJstOwnerTypeName(), true);
			JstIdentifier qualifier = new JstIdentifier(cMtd.getJstOwnerTypeName());
			qualifier.setJstBinding(type);
			qualifier.setType(type);
			identifier.setQualifier(qualifier);
			getDataTypeTranslator().addImport(type, jstNode.getRootType(), type.getName());
		}
		
		// Create method invocation accordingly
		MtdInvocationExpr mtdCall = new MtdInvocationExpr(identifier);
		mtdCall.setResultType(jstType);
		mtdCall.setArgs(jstArgs);
		
		return mtdCall;
	}
	
	/**
	 * @see ICustomTranslator#processMtdInvocation(ASTNode, JstIdentifier, IExpr, List, boolean, BaseJstNode)
	 */
	public IExpr processMtdInvocation(
			final ASTNode mtdInvocation,
			final boolean hasSuper,
			final IExpr jstQualifier, 
			final JstIdentifier jstIdentifier, 
			final List<IExpr> jstArgs, 
			final BaseJstNode jstNode){
		
		if (jstIdentifier == null){
			return null;
		}
		
		Name astName = null;
		if (mtdInvocation instanceof MethodInvocation){
			astName = ((MethodInvocation)mtdInvocation).getName();
		}
		else if (mtdInvocation instanceof SuperMethodInvocation){
			astName = ((SuperMethodInvocation)mtdInvocation).getName();
		}
		else {
			return null;
		}
		
		JstType ownerType = jstNode.getOwnerType();
		IJstMethod jstMtd = (IJstMethod)jstIdentifier.getJstBinding();
		
		IJstType varType = null;
		CustomType cType = null;
		CustomMethod cMtd = null;

		IExpr qualifierExpr = jstQualifier;
		if (qualifierExpr == null){
			qualifierExpr = jstIdentifier.getQualifier();
		}
		if (qualifierExpr != null){
			varType = TranslateHelper.Type.getType(qualifierExpr);
			if (varType == null){
				//If it is static method
				String name = qualifierExpr.toString();				
				varType = getCtx().getProvider().getDataTypeTranslator().findJstType(name, ownerType);
			}
		}
		else {
			if (jstMtd != null){
				varType = jstMtd.getRtnType();
			}
			jstIdentifier.setQualifier(null);
		}
		
		if (jstMtd != null){
			IJstType bindingType = jstMtd.getOwnerType();
			if (bindingType instanceof JstType){
				TranslateInfo tInfo = getCtx().getTranslateInfo((JstType)bindingType);
				MethodKey mtdKey = MethodKey.genMethodKey(jstMtd);
				CustomInfo cInfo = tInfo.getMethodCustomInfo(mtdKey);
				IJstType asType = cInfo.getAsType();
				if (asType != null){
					varType = asType;
					cType = getCustomType(asType);
				}
				if (cType != null){
					jstIdentifier.setName(cInfo.getAsName());
				}
			}
		}
		
		if (cType == null){
			cType = getCustomType(varType);
		}
		
		if (cType != null){
			cMtd = getCustomMethod(cType, jstIdentifier.getName(), jstArgs, jstMtd);
		}

		IExpr rtnExpr = delegate(astName, hasSuper, qualifierExpr, jstIdentifier, jstArgs, varType, cType, cMtd, jstNode);
		if (rtnExpr != null){
			return rtnExpr;
		}
		
		if (cType == null || cMtd == null){
			return null;
		}

		if (cMtd.getAttr().isExcluded()){
			if (jstMtd != null){
				TranslateHelper.Method.validateMethodReference(jstMtd, astName, jstNode, this);
			}
			else {
				getCtx().getLogger().logError(TranslateMsgId.EXCLUDED_MTD, 
						"'" + cMtd.getJavaName() + "' of type '"+cType.getJavaName()+"' is excluded.", 
						this, mtdInvocation, jstNode);
			}
			return null;
		}
		
		if (cMtd.getAttr().isMappedToJS() || cMtd.getAttr().isMappedToVJO()){
			MtdInvocationExpr mtdCall = new MtdInvocationExpr(cMtd.getJstName());
			if (jstArgs != null){
				for (IExpr e: jstArgs){
					mtdCall.addArg(e);
				}
			}
			
			if (cMtd.getJstOwnerTypeName() != null){
				mtdCall.setQualifyExpr(new JstIdentifier(cMtd.getJstOwnerTypeName()));
				if (cMtd.getAttr().isMappedToVJO()){
					JstType type = JstCache.getInstance().getType(cMtd.getJstOwnerTypeName(), true);
					type.addExtend(JstCache.getInstance().getType("vjo.Object"));
					getDataTypeTranslator().addImport(type, jstNode.getRootType(), type.getName());
					TranslateInfo tInfo = TranslateCtx.ctx().getTranslateInfo(ownerType);
					tInfo.addActiveImport(type);
				}
			}

			// TODO return type
			
			return mtdCall;
		}
		
		String methodOwnerType = cMtd.getJstOwnerTypeName();
		if (methodOwnerType != null && !methodOwnerType.equals(cType.getJstName())){
			IJstType toOwnerType = getDataTypeTranslator().toJstType(cMtd.getJstOwnerTypeName(), jstNode.getOwnerType());
			getDataTypeTranslator().addImport(toOwnerType, jstNode.getOwnerType(), toOwnerType.getSimpleName());
		}
		
		if (qualifierExpr != null 
				&& qualifierExpr.toExprText() != null
				&& qualifierExpr.toExprText().equals(TranslateHelper.getShortName(cType.getJavaName()))){
			qualifierExpr = new TextExpr(cType.getJstName());
		}
		if (cMtd.getAttr().isJavaOnly()){
			return qualifierExpr;
		}
		String clientMtdName = cMtd.getJstName();
		if (clientMtdName == null){
			return qualifierExpr;
		}
		jstIdentifier.setName(clientMtdName);
		if (cMtd.getRemoveQualifier()){
			jstIdentifier.setQualifier(null);
			return jstIdentifier;
		}
		if (methodOwnerType != null && !methodOwnerType.equals(cType.getJstName())){
			JstIdentifier qualifier = new JstIdentifier(methodOwnerType);
			qualifier.setJstBinding(JstCache.getInstance().getType(methodOwnerType));
			jstIdentifier.setQualifier(qualifier);
			
			MtdInvocationExpr mtdCall = new MtdInvocationExpr(jstIdentifier);
			if (isInstance(qualifierExpr)){
				mtdCall.addArg(qualifierExpr);
			}
			if (jstArgs != null){
				for (IExpr e: jstArgs){
					mtdCall.addArg(e);
				}
			}
//			else {
//				for (IExpr a: jstArgs){
//					mtdCall.addArg(a);
//				}
//			}
			IJstType jstType = getDataTypeTranslator().toJstType(cMtd.getJstOwnerTypeName(), jstNode.getOwnerType());
			IJstMethod jstMethod = jstType.getMethod(cMtd.getJstName());
			mtdCall.setResultType(jstMethod.getRtnType());
			return mtdCall;
		} else {
			//Add return type
			if (!cMtd.isProperty()) {
				MtdInvocationExpr mtdCall = new MtdInvocationExpr(jstIdentifier);
				mtdCall.setQualifyExpr(jstQualifier);
				
				String rtnTypeName = cMtd.getJstReturnTypeName();
				Class<?>[] argTypes = new Class[jstArgs.size()];
				boolean failed = false;
				if (jstArgs != null){
					argTypes = new Class[jstArgs.size()];
					int i = 0;
					for (IExpr e: jstArgs){
						mtdCall.addArg(e);
						if (rtnTypeName == null) {
							if (e.getResultType() == null) {
								argTypes[i] = null;
								failed = true;
								continue;
							}
							
							String forN = e.getResultType().getName();
							Class<?> pT = DataTypeHelper.getPrimitiveClass(forN);
							if (pT == null) {
								try {
									pT = Class.forName(forN);
								} catch (ClassNotFoundException e1) {
									failed = true;
								}
								if (pT != null) {
									argTypes[i] = pT;
								}
							} else {
								argTypes[i] = pT;
							}
							i++;
						}
					}
				}

				if (rtnTypeName == null && !failed && cType.getJavaType() != null) {
					//Use the return type from Java
					Method method = null; 
					try {
						String javaMethodName = cMtd.getJavaName();
						method = cType.getJavaType().getMethod(javaMethodName, argTypes);
					} catch (SecurityException e) {
					} catch (NoSuchMethodException e) {
					}
					if (method != null) {
						if(method.getReturnType().getSimpleName().contains("String")){
							rtnTypeName = method.getReturnType().getSimpleName();					
						}else{
							rtnTypeName = method.getReturnType().getName();					
						}
					}
				} 
				
				if (rtnTypeName != null) {
					mtdCall.setResultType(JstCache.getInstance().getType(rtnTypeName));
				}
				
				return mtdCall;
			}
		}
		if (!cMtd.isProperty()){
			return null;
		}
		
		if (jstQualifier == null) {
			qualifierExpr = null;
		}
		
		String mtdName = jstIdentifier.getName();
		if(mtdName.startsWith("get") || mtdName.startsWith("set")){
			mtdName = mtdName.substring(3,4).toLowerCase() + mtdName.substring(4, mtdName.length());
			jstIdentifier.setName(mtdName);
		}
		
		if (jstArgs != null && jstArgs.size() > 0){
			PtySetter setter = new PtySetter(jstIdentifier, qualifierExpr, processArgs(astName, jstArgs, jstNode));
			setter.setParent(jstNode);
			return setter;
		}
		else {
			PtyGetter getter = new PtyGetter(jstIdentifier, qualifierExpr);
			getter.setParent(jstNode);
			return getter;
		}
	}
	
	/**
	 * @see ICustomTranslator#processMethodBody(MethodDeclaration, JstMethod)
	 */
	public boolean processMethodBody(final MethodDeclaration astMtd, final JstMethod jstMtd) {
		if (astMtd == null || jstMtd == null || jstMtd.getOwnerType() == null){
			return false;
		}
		CustomType cType = null;
		CustomMethod cMtd = null;
		
		cType = getMetaProvider().getCustomType(jstMtd.getOwnerType().getName());
		if (cType == null){
			return false;
		}
		cMtd = getCustomMethod(cType, astMtd);
		if (cMtd == null){
			return false;
		}
		String delegateType = cMtd.getDelegateTypeName();
		if (delegateType == null) {
			return false;
		}

		// Replacing the method body with a call to delegated type
		JstBlock body = new JstBlock();
		StringBuilder sb = new StringBuilder();
		Type rtnType = astMtd.getReturnType2();
		boolean shouldReturn = false;
		if (rtnType != null) {
			if(!(rtnType instanceof PrimitiveType)){
				shouldReturn = true;
			} else if (((PrimitiveType) rtnType).getPrimitiveTypeCode() != PrimitiveType.VOID) {
				shouldReturn = true;
				
			}
		}
		if (shouldReturn){
			sb.append("return ");
		}
		sb.append(delegateType).append('.').append(cMtd.getJstName()).append('(');
		for (Iterator<?> it = astMtd.parameters().iterator(); it.hasNext(); ) {
			SingleVariableDeclaration v = (SingleVariableDeclaration) it.next();
			sb.append(v.getName().toString());
			if (it.hasNext()) {
				sb.append(',');
			}
		}
		sb.append(");");
		TextStmt text = new TextStmt(sb.toString());
		body.addStmt(text);
		jstMtd.setBlock(body);
		return true;
	}
	
	/**
	 * @see ICustomTranslator#processTypeBody(List, JstType)
	 */
	public boolean processTypeBody(final List<?> bodyDeclaration, final JstType jstType) {
		// Indicate we don't process this
		return false;
	}
	
	/**
	 * @see ICustomTranslator#getStaticTypeQualifier(IJstType, IJstNode)
	 */
	public JstIdentifier getStaticTypeQualifier(final IJstType jstType, final BaseJstNode jstNode) {
		if (jstType == null) return null;
		
		CustomType cusType =  getMetaProvider().getCustomType(jstType.getName());
		if (cusType != null && cusType.removeTypeQualifier()) {
			return null;
		}
		return null;
	}
	
	//
	// Protected
	//
	/**
	 * Answer the meta provider of this custom translator
	 * @return ICustomMetaProvider
	 */
	protected ICustomMetaProvider getMetaProvider(){
		return m_metaProvider;
	}
	
	/**
	 * Answer the custom type for the given jstType
	 * @param jstType IJstType
	 * @return CustomType
	 */
	protected CustomType getCustomType(final IJstType jstType){
		
		if (jstType == null){
			return null;
		}
		
		if (jstType instanceof JstArray){
			return getMetaProvider().getCustomType(Object.class.getName());
		}

		// Check this type
		CustomType cType = getMetaProvider().getCustomType(jstType.getName());
		if (cType != null){
			return cType;
		}
		
		// Check for mapped types.
		String mappedName = DataTypeHelper.getJavaTypeNameForNative(jstType.getName());
		if (mappedName != null) {
			cType = getMetaProvider().getCustomType(mappedName);
			if (cType != null){
				return cType;
			}
		}
		
		// Check base type
		if (jstType.getExtend() != null){
			cType = getCustomType(jstType.getExtend());
		}
		if (cType != null){
			return cType;
		}
		
		// Check interfaces
		for (IJstType itf: jstType.getSatisfies()){
			cType = getCustomType(itf.getExtend());
			if (cType != null){
				return cType;
			}
		};
		
		return null;
	}
	
	/**
	 * Answer the custom field with given AST name and JST identifier
	 * @param astName Name
	 * @param jstIdentifier JstIdentifier
	 * @param jstNode BaseJstNode
	 * @return CustomField
	 */
	protected CustomField getCustomField(
			final Name astName, 
			final JstIdentifier jstIdentifier, 
			final BaseJstNode jstNode){

		CustomType cType = null;
		
		if (jstIdentifier != null){
			PackageMapping pkgMapping = getCtx().getConfig().getPackageMapping();
			if (jstIdentifier.getJstBinding() != null){
				IJstType jstType = jstIdentifier.getJstBinding().getOwnerType();
				if (jstType != null){
					cType = getMetaProvider().getCustomType(
							pkgMapping.mapFrom(jstType.getName()));
				}
			}
			if (cType == null && jstIdentifier.getQualifier() != null){
				IJstType jstType = jstIdentifier.getQualifier().getType();
				if (jstType != null){
					cType = getMetaProvider().getCustomType(
							pkgMapping.mapFrom(jstType.getName()));
				}
				else if (jstIdentifier.getQualifier().getJstBinding() != null){
					jstType = jstIdentifier.getQualifier().getJstBinding().getOwnerType();
					if (jstType != null){
						cType = getMetaProvider().getCustomType(
							pkgMapping.mapFrom(jstType.getName()));
					}
				}
			}
		}
		
		if (cType != null){
			return cType.getCustomField(astName.toString());
		}
		
		return null;
	}
	
	/**
	 * Answer custom method with given custom type and AST method
	 * @param cType CustomType
	 * @param astMtd MethodDeclaration
	 * @return CustomMethod
	 */
	protected CustomMethod getCustomMethod(
			final CustomType cType,
			final MethodDeclaration astMtd){
		
		MethodKey mtdKey = MethodKey.genMethodKey(astMtd);

		Collection<CustomMethod> cMtds = cType.getCustomMethods(mtdKey.getName());
		if (cMtds == null || cMtds.isEmpty()){
			return null;
		}
		
		if (cMtds.size() == 1){
			CustomMethod cMtd = cMtds.iterator().next();
			if (!cMtd.isLookupBySignature()){
				return cMtd;
			}
		}
		
		for (CustomMethod cMtd: cMtds){
			if (mtdKey.equals(cMtd.getKey())){
				return cMtd;
			}
		}
		return null;
	}
	
	/**
	 * Answer custom method with given custom type and method name/arguments
	 * @param cType CustomType
	 * @param mtdName String
	 * @param jstArgs List<IExpr>
	 * @return CustomMethod
	 */
	protected CustomMethod getCustomMethod(
			final CustomType cType, 
			final String mtdName,
			final List<IExpr> jstArgs,
			final IJstMethod jstMtd){
		
		if (mtdName == null){
			return null;
		}

		Collection<CustomMethod> cMtds = cType.getCustomMethods(mtdName);
		if (cMtds == null || cMtds.isEmpty()){
			return null;
		}
		
		if (cMtds.size() == 1){
			CustomMethod cMtd = cMtds.iterator().next();
			if (!cMtd.isLookupBySignature()){
				return cMtd;
			}
		}
		
		MethodKey mtdKey = MethodKey.genMethodKey(jstMtd);
		if (mtdKey != null){
			for (CustomMethod cMtd: cMtds){
				if (mtdKey.equals(cMtd.getKey())){
					return cMtd;
				}
			}
			return null;
		}
		else {
			return getCustomMethod(mtdName, jstArgs, cMtds);
		}
	}
	
	/**
	 * Answer custom method with given method name/arguments
	 * from given custom methods
	 * Should be used only when IJstMethod cannot be found
	 * @param mtdName String
	 * @param jstArgs List<IExpr>
	 * @param cMtds Collection<CustomMethod>
	 * @return CustomMethod
	 */
	protected CustomMethod getCustomMethod(
			final String mtdName, 
			final List<IExpr> jstArgs, 
			final Collection<CustomMethod> cMtds){
		
		if (mtdName == null || jstArgs == null || cMtds == null){
			return null;
		}
		
		MethodKey mtdKey;
		IExpr arg;
		IJstType argType;
		String argTypeName;
		CustomMethod varArgMtd = null;
		int size;
		boolean found;

		for (CustomMethod cMtd: cMtds){
			mtdKey = cMtd.getKey();
			if (!mtdName.equals(mtdKey.getName())){
				continue;
			}
			size = mtdKey.getArgTypeNames().size();
			if (mtdKey.isVarArg()){
				size = size - 1;
				if (jstArgs.size() < size){
					continue;
				}
			}
			else {
				if (jstArgs.size() != size){
					continue;
				}
			}
			found = true;
			for (int i=0; i<size; i++){
				arg = jstArgs.get(i);
				if (arg == null){
					return null;
				}
				argType = arg.getResultType();
				if (argType == null){
					return null;
				}
				argTypeName = argType.getName();
				if (argTypeName == null){
					return null;
				}
				if (!argTypeName.equals(mtdKey.getArgTypeNames().get(i))){
					found = false;
					break;
				}
			}
			if (!found){
				continue;
			}
			for (int i=size; i<jstArgs.size(); i++){
				arg = jstArgs.get(i);
				if (arg == null){
					return null;
				}
				argType = arg.getResultType();
				if (argType == null){
					return null;
				}
				argTypeName = argType.getName();
				if (argTypeName == null){
					return null;
				}
				if (!argTypeName.equals(mtdKey.getArgTypeNames().get(size))){
					found = false;
					break;
				}
			}
			if (found){
				if (mtdKey.isVarArg()){
					varArgMtd = cMtd;
				}
				else {
					return cMtd;
				}
			}
		}
		
		if (varArgMtd != null){
			return varArgMtd;
		}
		
		return null;
	}
	
	/**
	 * Answer the privileged processor registered with given jstType 
	 * @param jstType IJstType 
	 * @return IPrivilegedProcessor
	 */
	protected IPrivilegedProcessor getTypeProcessor(final IJstType jstType){
		
		if (jstType == null){
			return null;
		}

		// Check this type
		IPrivilegedProcessor p = getMetaProvider().getPrivilegedTypeProcessor(jstType.getName());
		if (p != null){
			return p;
		}
		
		// Check base type
		for (IJstType extend: jstType.getExtends()){
			p = getTypeProcessor(extend);
			if (p != null){
				return p;
			}
		}
		
		// Check interfaces
		for (IJstType itf: jstType.getSatisfies()){
			p = getTypeProcessor(itf);
			if (p != null){
				return p;
			}
		};
		
		return null;
	}
	
	/**
	 * Answer the privileged processor registered with given jstType and method name
	 * @param jstType IJstType 
	 * @param mtdName String
	 * @return IPrivilegedProcessor
	 */
	protected IPrivilegedProcessor getMethodProcessor(final IJstType jstType, final String mtdName){
		
		if (jstType == null){
			return null;
		}

		// Check this type
		IPrivilegedProcessor p = getMetaProvider().getPrivilegedMethodProcessor(jstType.getName(), mtdName);
		if (p != null){
			return p;
		}
		
		// Check base type
		for (IJstType extend: jstType.getExtends()){
			p = getMethodProcessor(extend, mtdName);
			if (p != null){
				return p;
			}
		}
		
		// Check interfaces
		for (IJstType itf: jstType.getSatisfies()){
			p = getMethodProcessor(itf, mtdName);
			if (p != null){
				return p;
			}
		};
		
		return null;
	}
	
	/**
	 * Answer the privileged processor for given jstType and method name.
	 * It first looks for processor registered with given jstType and method name. 
	 * If not found, then it looks for processor registered with given jstType
	 * @param jstType IJstType 
	 * @param mtdName String
	 * @return IPrivilegedProcessor
	 */
	protected IPrivilegedProcessor getProcessor(final IJstType jstType, final String mtdName){
		IPrivilegedProcessor p = getMethodProcessor(jstType, mtdName);
		if (p != null){
			return p;
		}
		return getTypeProcessor(jstType);
	}
	
	protected IExpr delegate(
			final Name astName,
			final boolean isSuper,
			final IExpr jstQualifier, 
			final JstIdentifier jstIdentifier, 
			final List<IExpr> jstArgs, 
			final IJstType ownerType,
			final CustomType cType, 
			final CustomMethod cMtd,
			final BaseJstNode jstNode){
		
		IPrivilegedProcessor processor = getProcessor(ownerType, jstIdentifier.getName());
		if (processor != null){
			try {
				IExpr expr = processor.processMtdInvocation(
					astName, jstIdentifier, jstQualifier, jstArgs, isSuper, jstNode, cType, cMtd);
				if (expr != null){
					return expr;
				}
			}
			catch(Throwable t){
				getLogger().logError(
					TranslateMsgId.CUSTOM_PROCESSOR_EXCEPTION, 
					"unhandled exception in processMtdInvocation of " + processor.getClass().getName(), 
					this, astName, jstNode);
			}
		}
		
		return null;
	}
	
	protected IExpr delegate(
			final ClassInstanceCreation cic, 
			final IExpr jstQualifier, 
			final CustomType cType, 
			final BaseJstNode jstNode){
		
		IPrivilegedProcessor processor = getMetaProvider()
			.getPrivilegedConstructorProcessor(cType.getJavaName());
		if (processor != null){
			try {
				List<?> args = cic.arguments();
				List<IExpr> argExprList = new ArrayList<IExpr>(args.size());			
				for (Object a: args){
					if (a instanceof Expression){
						argExprList.add(getExprTranslator().processExpression((Expression)a, jstNode));
					}
					else {
						getLogger().logUnhandledNode(this, (ASTNode)a, jstNode);
					}
				}
				IExpr expr = processor.processInstanceCreation(cic, jstNode, argExprList, cType);
				if (expr != null){
					return expr;
				}
			}
			catch(Throwable t){
				getLogger().logError(
					TranslateMsgId.CUSTOM_PROCESSOR_EXCEPTION, 
					"unhandled exception in processInstanceCreation of " + processor.getClass().getName(), 
					this, cic, jstNode);
			}
		}
		
		return null;
	}
	
	protected JstIdentifier processSimpleName(
			final SimpleName astName, 
			boolean hasSuper, 
			boolean hasThis, 
			final IExpr jstQualifier, 
			final JstIdentifier jstIdentifier, 
			final BaseJstNode jstNode){
		
		CustomField cField = getCustomField(astName, jstIdentifier, jstNode);
		if (cField == null){
			return null;
		}
		
		CustomAttr cAttr = cField.getAttr();
		
		if (cAttr.isExcluded()){
			getLogger().logError(TranslateMsgId.EXCLUDED_FIELD, 
					"Unsupported field '" + cField.getJavaName() + "'", 
					this, astName, jstNode);
			return null;
		}
		
		// Field Name
		if (cField.getJstName() != null){
			jstIdentifier.setName(cField.getJstName());
			if (TranslateHelper.Expression.removeQualifier(jstIdentifier, jstNode)){
				jstIdentifier.setQualifier(null);
			}
		}

		// Field Type
		if (cField.getJstOwnerTypeName() != null){	
			jstIdentifier.setQualifier(new JstIdentifier(cField.getJstOwnerTypeName()));
			IJstType jstType = JstCache.getInstance().getType(cField.getJstOwnerTypeName(), true);
			if (jstType != null){
				if (cAttr.isMappedToJS()){
					getCtx().addJsType(jstType.getName(), jstType);
				}
				else if (cAttr.isMappedToVJO() || cAttr.isJsProxy()){
					getDataTypeTranslator().addImport(jstType, jstNode.getOwnerType(), jstType.getName());
				}
				jstIdentifier.setJstBinding(jstType);
			}
		}
		
		if (cField.getJstTypeName() != null){
			String fldTypeName = cField.getJstTypeName();
			IJstType fldType = getDataTypeTranslator().toJstType(fldTypeName, jstNode.getOwnerType());
			if (fldType != null){
				jstIdentifier.setType(fldType);
				if (cAttr.isMappedToJS()){
					getCtx().addJsType(fldType.getName(), fldType);
				}
				else if (cAttr.isMappedToVJO() || cAttr.isJsProxy()){
					getDataTypeTranslator().addImport(fldType, jstNode.getOwnerType(), fldType.getName());
				}
			}
		}
		
		return jstIdentifier;
	}
	
	protected boolean isInstance(final IExpr jstQualifier){
		if (jstQualifier == null || jstQualifier.toExprText() == null || jstQualifier.getResultType() == null){
			return false;
		}
		return !jstQualifier.toExprText().equals(jstQualifier.getResultType().getName());
	}
	
	protected String processArgs(
			final ASTNode astNode, 
			final List<IExpr> jstArgs, 
			final BaseJstNode jstNode){
		
		if (jstArgs == null || jstArgs.size() != 1){
			getLogger().logError(TranslateMsgId.INVALID_ARGS, "should be only one argument", this, astNode, jstNode);
			return null;
		}
		return jstArgs.get(0).toExprText();
	}
}

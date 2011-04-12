/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;

import org.ebayopensource.dsf.javatojs.trace.TranslateMsgId;
import org.ebayopensource.dsf.javatojs.translate.config.MethodKey;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomInfo;
import org.ebayopensource.dsf.javatojs.translate.custom.jdk.JavaLangMeta;
import org.ebayopensource.dsf.javatojs.translate.util.AutoBoxer;
import org.ebayopensource.dsf.javatojs.translate.util.AutoUnboxer;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstArray;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstFactory;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstParamType;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstTypeWithArgs;
import org.ebayopensource.dsf.jst.declaration.VarTable;
import org.ebayopensource.dsf.jst.expr.BoolExpr;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.expr.ObjCreationExpr;
import org.ebayopensource.dsf.jst.stmt.JstBlockInitializer;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.term.SimpleLiteral;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.util.DataTypeHelper;
import org.ebayopensource.dsf.jst.util.JstMethodHelper;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;
import org.ebayopensource.dsf.util.JavaSourceLocator;
import org.ebayopensource.vjo.meta.VjoKeywords;

public final class TranslateHelper {
	
	private static AutoBoxer s_autoBoxer = AutoBoxer.getInstance();
	private static AutoUnboxer s_autoUnboxer = AutoUnboxer.getInstance();
	
	public static boolean isObjectType(IJstType type){
		return type != null && isObjectType(type.getName());
	}

	public static boolean isObjectType(String typeName){
		return typeName != null && 
		(org.ebayopensource.dsf.jsnative.global.Object.class.getName().equals(typeName) ||
		 org.ebayopensource.dsf.jsnative.global.Object.class.getSimpleName().equals(typeName) ||
		 JavaLangMeta.VJO_OBJECT.equals(typeName));
	}

	public static class Factory {
		/**
		 * JstType creation helper that performs additional logic:
		 * 1. Attach the AST node as source of newly created JstType
		 * 2. Add newly created type to the type table of root type (except anonymous types)
		 * 3. For anonymous or local types, add mapping to translateInfo
		 * 4. For local types, also add info to varTable
		 * @param astNode ASTNode
		 * @param scopeNode IJstNode
		 * @param cacheIt boolean
		 * @return JstType
		 */
		public static JstType createJstType(final ASTNode astNode, final IJstNode scopeNode){
			
			// Create
			JstType jstType = JstFactory.getInstance().createJstType(false);
			
			// Attach source
			jstType.setSource(new JstSource(new AstBinding(astNode)));
			
			// Get root type
			JstType rootType = null;
			if (scopeNode == null){
				rootType = jstType;
			}
			else if(scopeNode.getRootType() instanceof JstType){
				rootType = (JstType)scopeNode.getRootType();
			}
	
			if (rootType == null){
				return jstType;
			}
			
			// Retrive symbol
			String symbol = null;
			if (astNode instanceof TypeDeclaration){
				symbol = ((TypeDeclaration)astNode).getName().toString();
			}
			else if (astNode instanceof TypeDeclarationStatement){
				symbol = ((TypeDeclarationStatement)astNode).getDeclaration().getName().toString();
			}
			
			// Add to translateInfo
			TranslateInfo tInfo = TranslateCtx.ctx().getTranslateInfo(rootType.getRootType());
			if (astNode instanceof AnonymousClassDeclaration){
				tInfo.addAnonymousType((AnonymousClassDeclaration)astNode, jstType);
			}
			else if (astNode instanceof TypeDeclarationStatement){
				tInfo.addLocalType((TypeDeclarationStatement)astNode, jstType);
				VarTable varTable = TranslateHelper.getVarTable(scopeNode);
				if (varTable != null){
					varTable.addLocalType(symbol, jstType);
				}
			}
			
			// Add to type lookup table
			if (symbol != null){
				tInfo.setType(symbol, jstType);
			}
			
			return jstType;
		}
	}
	
	public static class Type {
		
		public static boolean isEmbededType(AbstractTypeDeclaration astType){
			return astType.getParent() instanceof AbstractTypeDeclaration;
		}
		
		public static boolean isFullyQualifiedTypeName(final String name){
			return JavaSourceLocator.getInstance().getSourceUrl(name) != null;
		}
		
		public static String resolveEmbeddedType(final String dependentName, final JstType referencingType){
			int index = dependentName.indexOf(".");
			if (index < 0){
				return null;
			}
			String rootName = dependentName.substring(0, index);
			IJstType rootType = referencingType.getImport(rootName);
			if (rootType != null){
				return rootType.getName();
			}
			return TranslateCtx.ctx().getTranslateInfo(referencingType).getImported(rootName);
		}
		
		public static String resolveImplicitImport(final String dependentName, final JstType referencingType){
			
			if (dependentName == null || referencingType == null){
				return null;
			}
			
			JstType rootType = referencingType;
			while (rootType.getOuterType() != null){
				rootType = rootType.getOuterType();
			}
			
			// Try given name first
			String clsName = getOwnerClassName(dependentName);
			if (clsName != null){
				return clsName;
			}
			
			TranslateCtx ctx = TranslateCtx.ctx();
			
			// Try same package and imported packages of referencing type
			if (rootType.getPackage() != null){
				ctx.getTranslateInfo(rootType).addImportedPkg(
					ctx.getConfig().getPackageMapping().mapFrom(rootType.getPackage().getName()));
			}
			List<String> pkgs = ctx.getTranslateInfo(rootType).getImportedPkgs();
			
			for (String pkg: pkgs){
				if (pkg == null || pkg.length() == 0){
					continue;
				}
				clsName = getOwnerClassName(pkg + "." + dependentName);
				if (clsName != null){
					return clsName;
				}
			}
			
			// Type Java.lang
			if (DataTypeHelper.isInJavaLang(dependentName)){
				return "java.lang." + dependentName;
			}
			
			return clsName;
		}
		
		public static String getTypeFullName(final String simpleName, final JstType jstType){
			TranslateInfo tInfo = TranslateCtx.ctx().getTranslateInfo(jstType);
			String fullName = tInfo.getImported(simpleName);
			if (fullName != null){
				return fullName;
			}
			
			IJstType javaOnlyType = tInfo.getType(simpleName, true);
			if (javaOnlyType != null){
				return javaOnlyType.getName();
			}
			
			return null;
		}
		
		public static IJstType getType(final IExpr expr){
			if (expr == null){
				return null;
			}
			IJstType jstType = expr.getResultType();
			if (jstType != null){
				return jstType;
			}
			if (expr instanceof JstIdentifier){
				return getType((JstIdentifier)expr);
			}
			return expr.getResultType();
		}
		
		public static IJstType getType(final JstIdentifier identifier){
			if (identifier == null){
				return null;
			}
			
			if (identifier.getType() != null){
				return identifier.getType();
			}
			
			JstIdentifier qualifier = identifier.getQualifier();
			if (qualifier == null){
				return null;
			}
			
			JstType jstType = JstTypeHelper.getJstType(getType(identifier.getQualifier()));
			if (jstType == null){
				return null;
			}
			
			String innerFullName = jstType.getName() + 
				"." + identifier.getName();
			JstType inner = JstCache.getInstance().getType(innerFullName, false);
			if (inner != null){
			jstType.addInnerType(inner);
			}
			
			return inner;
		}
		
		public static IJstType getIdentifierType(final String name, final BaseJstNode node){
			IJstType jstType = getVarType(name, node);
			if (jstType != null){
				return jstType;
			}
			
			return TranslateCtx.ctx().getProvider().getDataTypeTranslator().findJstType(name, node.getOwnerType());
		}
		
		public static IJstType getLocalVarType(final String name, final IJstNode node){
			if (node == null){
				return null;
			}
			
			if (node instanceof JstType){
				return null;
			}
			
			IJstType varType = null;
			if (node instanceof JstBlock){
				JstBlock block = (JstBlock)node;
				
				varType = block.getVarTable().getVarType(name);
				if (varType != null){
					return varType;
				}
			}
			
			if (node.getParentNode() != null){
				return getLocalVarType(name, node.getParentNode());
			}
			
			return null;
		}
		
		public static IJstType getVarType(final String name, final IJstNode node){
			return getVarType(name, node, true);
		}
		
		public static IJstType getVarType(final String name, final IJstNode node, boolean deep){
			
			if (node == null){
				return null;
			}
			
			IJstType varType = null;
			if (node instanceof JstType){
				JstType jstType = (JstType)node;	
				varType = jstType.getVarTable().getVarType(name);
				if (varType != null){
					return varType;
				}
			}
			
			if (node instanceof JstBlock){
				JstBlock block = (JstBlock)node;
				
				varType = block.getVarTable().getVarType(name);
				if (varType != null){
					return varType;
				}
			}
			
			if (deep && node.getParentNode() != null){
				return getVarType(name, node.getParentNode(), deep);
			}
			
			return null;
		}
		
		public static IJstType getLocalType(final String name, final IJstNode node){
			if (node == null){
				return null;
			}
			
			IJstType varType = null;
			if (node instanceof JstBlock){
				JstBlock block = (JstBlock)node;
				
				varType = block.getVarTable().getLocalType(name);
				if (varType != null){
					return varType;
				}
			}
			
			return null;
		}
		
		public static boolean hasLocalType(final String name, final IJstNode node){
			if (node == null){
				return false;
			}

			if (node instanceof JstBlock){
				JstBlock block = (JstBlock)node;
				return block.getVarTable().hasLocalType(name);
			}
			
			return false;
		}
		
		public static JstType getEmbededType(final JstType jstType, final String shortName) {
			for (JstType type: jstType.getEmbededTypes()){
				if (shortName.equals(type.getSimpleName())){
					return type;
				}
				JstType embeded = getEmbededType(type, shortName);
				if (embeded != null){
					return embeded;
				}
			}
			
			return null;
		}
		
		public static IJstType getEmbededType(final IJstType type, final String shortName, boolean recursive){
			if (type == null){
				return null;
			}
			IJstType embededType = type.getEmbededType(shortName);
			if (embededType != null){
				return embededType;
			}
			for (IJstType e: type.getEmbededTypes()){
				embededType = getEmbededType(e, shortName, recursive);
				if (embededType != null){
					return embededType;
				}
			}
			return null;
		}
		
		/**
		 * Answer the type with given short name that the given type depends on
		 * @param jstType the type that been looked up
		 * @param shortName String the short name of the type that the given type depends on
		 * @return IJstType
		 */
		public static IJstType getDependedType(final JstType jstType, final String shortName) {
			IJstType depended = jstType.getEmbededType(shortName);
			if (depended != null) {
				return depended;
			}
			JstType outerType = jstType.getOuterType();
			if (outerType != null) {
				if (shortName.equals(outerType.getSimpleName())) {
					return outerType;
				}
				depended = getDependedType(outerType, shortName);
				if (depended != null) {
					return depended;
				}
			}
			Map<String,? extends IJstType> imports = jstType.getImportsMap();
			for (Map.Entry<String, ? extends IJstType> entry : imports.entrySet()) {				
				if (entry.getKey().equals(shortName)) {
					return entry.getValue();
				}
				IJstType imported = entry.getValue();
				depended = imported.getEmbededType(shortName);
				if (depended != null) {
					return depended;
				}
			}
			
			return null;
		}
		
		public static String getOwnerClassName(final String srcName){
			URL url = null;
			int index;
			String clsName = srcName;
			while (clsName != null){
				url = JavaSourceLocator.getInstance().getSourceUrl(clsName);
				if (url != null){
					return clsName;
				}
				index = clsName.lastIndexOf(".");
				if (index > 0){
					clsName = clsName.substring(0,index);
				}
				else {
					return null;
				}
			}
			return null;
		}
		
		public static boolean isExcluded(final IJstType jstType, 
				final ASTNode astNode, 
				final BaseJstNode scopeNode){
			
			if (scopeNode == null){
				return false;
			}
			
			TranslateCtx ctx = TranslateCtx.ctx();
			JstType rootType = scopeNode.getRootType();
			
			TranslationMode mode = ctx.getTranslateInfo(rootType).getMode();
			if (!mode.hasImplementation()){
				return false;
			}
			
			if (ctx.isJavaOnly(rootType) || ctx.isJSProxy(rootType) ){	
				return false;
			}
			
			IJstType baseType = getBase(scopeNode.getOwnerType());
			if (ctx.isExcluded(baseType)){
				return false;
			}
			return ctx.isExcluded(jstType);
		}
		
		public static boolean isExcluded( 
				final ASTNode astNode, 
				final JstType outerType,
				final String embeddedypeName,
				final BaseJstNode scopeNode){
			
			IJstType ownerType = scopeNode.getOwnerType();
			if (ownerType == null || !(ownerType instanceof JstType)){
				return false;
			}

			TranslateCtx ctx = TranslateCtx.ctx();
			
			TranslationMode mode = ctx.getTranslateInfo((JstType)ownerType).getMode();
			if (!mode.hasImplementation()){
				return false;
			}
			
			if (ctx.isJavaOnly(ownerType) || ctx.isJSProxy(ownerType)){
				return false;
			}
			
			IJstType baseType = getBase(ownerType);
			if (ctx.isExcluded(baseType)){
				return false;
			}
			
			CustomInfo cInfo = getCustomInfo(outerType, embeddedypeName, ctx);			
			return cInfo.isExcluded();
		}
		
		public static boolean isMapped(final IJstType jstType, final SimpleName astName){
			if (jstType == null || astName == null){
				return false;
			}
			return !astName.toString().equals(jstType.getSimpleName());
		}
		
		public static void validateTypeReference(final IJstType jstType, 
				final ASTNode astNode, 
				final BaseJstNode scopeNode, 
				final BaseTranslator translator){
			validateTypeReference(jstType, astNode, null, scopeNode, translator);
		}
		
		public static void validateTypeReference(final IJstType jstType, 
				final ASTNode astNode, 
				final BaseJstNode theNode, 
				final BaseJstNode scopeNode, 
				final BaseTranslator translator){
			
			if (scopeNode == null){
				return;
			}
			
			TranslateCtx ctx = TranslateCtx.ctx();

			BaseJstNode jstNode = (theNode != null) ? theNode : scopeNode;

			boolean removeParentNode = false;
			
			try {
				if (isExcluded(jstType, astNode, scopeNode)){
					if (jstNode.getParentNode() == null && jstNode != scopeNode){
						jstNode.setParent(scopeNode);
						removeParentNode = true;
					}
					String msgId = jstType.getOuterType() == null ? TranslateMsgId.EXCLUDED_TYPE : TranslateMsgId.EXCLUDED_EMBEDDED_TYPE;
					ctx.getLogger().logError(msgId, jstType.getName()
						+ " excluded", translator, astNode, jstNode);			
				}
			}
			finally{
				if (removeParentNode){
					jstNode.setParent(null);
				}
			}
		}
		
		public static void validateEmbeddedTypeReference( 
				final ASTNode astNode, 
				final JstType outerType,
				final String embeddedypeName,
				final BaseJstNode theNode, 
				final BaseJstNode scopeNode, 
				final BaseTranslator translator){
			
			IJstType ownerType = scopeNode.getOwnerType();
			if (ownerType == null || !(ownerType instanceof JstType)){
				return;
			}

			TranslateCtx ctx = TranslateCtx.ctx();		
			if(isExcluded(astNode, outerType, embeddedypeName, (JstType)ownerType)) {
				ctx.getLogger().logError(TranslateMsgId.EXCLUDED_EMBEDDED_TYPE, 
					"'" + embeddedypeName + "' of type '"+ownerType.getName()+"' or its base or outer is excluded", 
					translator, astNode, theNode);
			}
		}
		
		public static JstIdentifier createIdentifier(final IJstType jstType, final BaseJstNode scopeNode){
			
			String typeName = jstType.getName();
			if (null != DataTypeHelper.getNativeType(typeName) 
					|| DataTypeHelper.isJavaMappedToNative(typeName)
					|| org.ebayopensource.dsf.jsnative.global.String.class.getName().equals(typeName)) {//Temp code for String
					return new JstIdentifier(DataTypeHelper.getTypeName(jstType.getName()));
			}
			JstIdentifier jstIdentifier = new JstIdentifier(
				(scopeNode.getOwnerType() == jstType)?null:jstType.getSimpleName(), VjoTranslateHelper.getStaticTypeQualifier(jstType, scopeNode));
			jstIdentifier.setJstBinding(jstType);
			jstIdentifier.setType(jstType);
			
			return jstIdentifier;
		}
		
		private static CustomInfo getCustomInfo(final JstType jstType, final String embeddedName, final TranslateCtx ctx){
			TranslateInfo tInfo = ctx.getTranslateInfo(jstType);
			CustomInfo cInfo = tInfo.getEmbeddedTypeCustomInfo(embeddedName);	
			if (cInfo != CustomInfo.NONE){
				return cInfo;
			}
			
			IJstType baseType = getBase(jstType);
			while (baseType != null && baseType instanceof JstType){
				cInfo = getCustomInfo((JstType)baseType, embeddedName, ctx);
				if (cInfo != CustomInfo.NONE){
					return cInfo;
				}
				baseType = getBase(baseType);
			}
			
			IJstType outer = jstType.getOuterType();
			while (outer != null && outer instanceof JstType){
				cInfo = getCustomInfo((JstType)outer, embeddedName, ctx);
				if (cInfo != CustomInfo.NONE){
					return cInfo;
				}
				outer = outer.getOuterType();
			}
			
			return CustomInfo.NONE;
		}
	}
	
	public static class Method {
		
		public static IJstMethod getMethod(final MethodDeclaration astMtd, final JstType jstType){
			String mtdName = astMtd.getName().toString();
			mtdName = TranslateCtx.ctx().getProvider().getNameTranslator().processVarName(mtdName);
			boolean isStatic = TranslateHelper.isStatic(astMtd.modifiers());
			if (astMtd.isConstructor()){
				mtdName = VjoKeywords.CONSTRUCTS;
			}
			TranslateInfo tInfo = TranslateCtx.ctx().getTranslateInfo(jstType);
			if (!tInfo.isOverloaded(mtdName, isStatic(astMtd.modifiers()))){
				if (astMtd.isConstructor()){
					return jstType.getConstructor();
				}
				else {
					return jstType.getMethod(mtdName, isStatic);
				}
			}
			return tInfo.getOverloaded(astMtd);
		}
		
		public static IJstMethod getConstructor(final IJstType ownerType, final List<IExpr> args){
			JstType type = null;
			if (ownerType instanceof JstType){
				type = (JstType)ownerType;
			}
			else if (ownerType instanceof JstTypeWithArgs){
				IJstType aType = ((JstTypeWithArgs)ownerType).getType();
				if (aType instanceof JstType){
					type = (JstType)aType;
				}
			}
			if (type == null){
				return null;
			}
			
			List<IJstMethod> mtds = new ArrayList<IJstMethod>();
			if (type.getConstructor() instanceof JstMethod){
				mtds.add((JstMethod)type.getConstructor());
			}
			TranslateInfo tInfo = TranslateCtx.ctx().getTranslateInfo(type);
			Map<Integer,List<JstMethod>> map = tInfo.getOverloaded(VjoKeywords.CONSTRUCTS, false);
			if (map != null){
				Integer argCount = new Integer(args.size());
				List<JstMethod> list = map.get(argCount);
				if (list != null){
					mtds.addAll(list);
				}
			}
			
			Map<MethodDeclaration,JstMethod> removedMap = tInfo.getRemovedMtds(type.getSimpleName());
			for (Map.Entry<MethodDeclaration,JstMethod> entry: removedMap.entrySet()){
				mtds.add(entry.getValue());
			}
			
			if (mtds.size() == 1){
				return mtds.get(0);
			}
			
			return getMatchingMethod(type, mtds, args);
		}
		
		public static IJstMethod getMethod(final ASTNode astNode, final IJstType qualifierType, final String mtdName, final List<IExpr> args){

			List<IJstMethod> mtds = new ArrayList<IJstMethod>();

			collectMethods(astNode, qualifierType, mtdName, mtds);

			Collections.sort(mtds, JstMethodHelper.s_mtdComparator);

			return getMatchingMethod(qualifierType, mtds, args);
		}

		private static void collectMethods(final ASTNode astNode, IJstType jstType, String mtdName, List<IJstMethod> mtds){
			if (jstType == null){
				return;
			}
			IJstType type = jstType;
			if (type instanceof JstTypeWithArgs){
				type = ((JstTypeWithArgs)type).getType();
			}
			for (IJstMethod jstMtd: type.getMethods()){
				if (jstMtd.getName().getName().equals(mtdName)){
					mtds.add(jstMtd);
					for (IJstMethod overloaded: jstMtd.getOverloaded()){
						if (overloaded instanceof JstMethod){
							mtds.add((JstMethod)overloaded);
						}
					}
				}
			}
			for (IJstType extend: type.getExtends()){
				collectMethods(astNode, extend, mtdName, mtds);
			}
			if (!type.isInterface()){
				for (IJstType satisfy: type.getSatisfies()){
					collectMethods(astNode, satisfy, mtdName, mtds);
				}
			}
			
			if (type instanceof JstType){
				Collection<JstMethod> c = getRemovedMethods(astNode, (JstType)type);
				if (c != null){
					for (JstMethod m: c){
						mtds.add(m);
					}
				}
			}
		}
		
		public static IJstMethod getOwnerMethod(final IJstNode expr){
			if (expr == null){
				return null;
			}
			IJstNode parentNode = expr.getParentNode();
			while (parentNode != null){
				if (parentNode instanceof IJstMethod){
					return (IJstMethod)parentNode;
				}
				parentNode = parentNode.getParentNode();
			}
			return null;
		}
		
		public static boolean includeMethodForDecl(final MethodDeclaration astMtd, final CustomInfo fInfo, final JstType jstType){
			
			if (!TranslateHelper.isPrivate(astMtd.modifiers())){
				return true;
			}
			
			TranslateCtx ctx = TranslateCtx.ctx();
			if (ctx.isJavaOnly(jstType) || ctx.isJSProxy(jstType)){
				return false;
			}
			
			IJstType outer = jstType.getOuterType();
			while (outer != null){
				if (ctx.isJavaOnly(outer) || ctx.isJSProxy(outer)){
					return false;
				}
				outer = outer.getOuterType();
			}
			
			return true;
		}
		
		public static boolean includeMethodForImpl(final MethodDeclaration astMtd, final CustomInfo fInfo, final JstType jstType){
			
			if (fInfo != null && (fInfo.isExcluded() || fInfo.isMappedToJS() || fInfo.isMappedToVJO())){
				return false;
			}
			
			if (!TranslateHelper.isPrivate(astMtd.modifiers())){
				return true;
			}

			if (fInfo != null && fInfo.isJavaOnly()){
				return false;
			}
			
			TranslateCtx ctx = TranslateCtx.ctx();
			if (ctx.isJavaOnly(jstType) || ctx.isJSProxy(jstType)){
				return false;
			}
			
			IJstType outer = jstType.getOuterType();
			while (outer != null){
				if (ctx.isJavaOnly(outer) || ctx.isJSProxy(outer)){
					return false;
				}
				outer = outer.getOuterType();
			}
			
			return true;
		}
		
		public static void validateMethodReference( 
				final IJstMethod jstMtd, 
				final ASTNode astNode,
				final BaseJstNode scopeNode, 
				final BaseTranslator translator){
			
			if (jstMtd == null){
				return;
			}
			
			IJstType ownerType = jstMtd.getOwnerType();
			if (!(ownerType instanceof JstType)){
				return;
			}

			IJstType scopeType = scopeNode.getOwnerType();
			if (scopeType == null || !(scopeType instanceof JstType)){
				return;
			}
			
			TranslateCtx ctx = TranslateCtx.ctx();
			CustomInfo cInfo = getCustomInfo((JstType)ownerType, jstMtd, ctx, false);
			if(cInfo.isExcluded()) {
				StringBuffer sb = new StringBuffer();
				if (jstMtd.getName().getName().equals(ownerType.getSimpleName())){
					sb.append("Constructor of ");
				}
				else {
					sb.append("Method ").append(jstMtd.getName().getName()).append(" of ");
				}
				sb.append(ownerType.getName()).append(" is excluded");
				ctx.getLogger().logError(TranslateMsgId.EXCLUDED_MTD, sb.toString(),
//					"'" + jstMtd.getName().getName() + "' of type '"+scopeType.getName()+"' or from its base or outer", 
					translator, astNode, scopeNode);
			}
		}
		
		private static IJstMethod getMatchingMethod(final IJstType qualifierType, final List<IJstMethod> mtds, final List<IExpr> args){

			IJstMethod mtdHasVarArgs = null;
			IJstMethod mtdNeedBoxing = null;
			int indexOfFirstArgForBoxing = -1;
			IJstMethod mtd;
			int baseSize;
			ComparisonResult result;
			
			for (int index=0; index<mtds.size(); index++){
				mtd = mtds.get(index);
				if (mtd.isDispatcher()){
					continue;
				}
				baseSize = mtd.isVarArgs() ? mtd.getArgs().size() - 1 : mtd.getArgs().size();
				if (mtd.isVarArgs()){
					if (args.size() < baseSize){
						continue;
					}
				}
				else {
					if (args.size() != mtd.getArgs().size()){
						continue;
					}
				}

				result = new ComparisonResult();
				
				for (int i=0; i<baseSize; i++){
					if (!comapreArg(qualifierType, mtd, i, args.get(i), mtd.getArgs().get(i), result)){
						break;
					}
				}
				
				if (mtd.isVarArgs()){
					JstArg varArg = mtd.getArgs().get(baseSize);
					for (int i=baseSize; i<args.size(); i++){
						if (!comapreArg(qualifierType, mtd, i, args.get(i), varArg, result)){
							break;
						}
					}
				}
				
				if (result.m_found){
					if (!result.m_needAutoBoxing && !mtd.isVarArgs()){
						return mtd;
					}
					else if (mtd.isVarArgs()){
						mtdHasVarArgs = mtd;
					}
					else if (indexOfFirstArgForBoxing < 0 || result.m_mtdIndexOfFirstArgForBoxing < indexOfFirstArgForBoxing){
						indexOfFirstArgForBoxing = result.m_mtdIndexOfFirstArgForBoxing;
						mtdNeedBoxing = mtd;
					}
				}
			}
			if (mtdNeedBoxing != null){
				return mtdNeedBoxing;
			}
			if (mtdHasVarArgs != null){
				return mtdHasVarArgs;
			}
			return null;
		}
		
		private static boolean comapreArg(
				final IJstType jstType, 
				final IJstMethod mtd, 
				int index, 
				final IExpr actualArg, 
				final JstArg expectedArg,
				final ComparisonResult result){
			
			if (actualArg instanceof SimpleLiteral && ((SimpleLiteral)actualArg).getValue() == null){
				return true;
			}
			IJstType actualType = actualArg.getResultType();
			if (actualType != null) {
				IJstType tmp = getCurrentArgType(actualArg, actualType);
				if (tmp != null) {
					actualType = tmp;
				}
			}
			
			IJstType cType = expectedArg.getType();
			if (actualType == null || cType == null){
				result.m_found = false;
				return false;
			}
			//If the method arg type is 'Object' then it can accept any type of object 
			String cTypeName = DataTypeHelper.getTypeName(cType.getName());
			while (actualType instanceof JstArray && cType instanceof JstArray){
				actualType = ((JstArray)actualType).getComponentType();
				cType = ((JstArray)cType).getComponentType();
				cTypeName = DataTypeHelper.getTypeName(cType.getName());
			}
			if (TranslateHelper.isObjectType(cTypeName) && !DataTypeHelper.isPrimitiveType(actualType)) {
				return true;
			}
			if (JstTypeHelper.isTemplateType(cType,mtd)) {
				return true;
			}
			
			String typeName = DataTypeHelper.getTypeName(actualType.getName());
			if (cTypeName.equals(typeName)){
				return true;
			}
			if (JstTypeHelper.isTypeOf(actualType, cType)){
				return true;
			}
			if (cType instanceof JstParamType){
				if (jstType != null && jstType instanceof JstTypeWithArgs){
					cType = ((JstTypeWithArgs)jstType).getArgType();
				}
			}
			if (s_autoBoxer.needAutoBoxing(actualArg, cType)
				|| s_autoUnboxer.needAutoUnboxing(actualArg, cType)){
				result.m_needAutoBoxing = true;
				if (result.m_mtdIndexOfFirstArgForBoxing < 0){
					result.m_mtdIndexOfFirstArgForBoxing = index;
				}
				return true;
			}
			if (DataTypeHelper.canPromote(actualType, cType)){
				return true;
			}
			result.m_found = false;
			
			return false;
		}
		
		private static class ComparisonResult {
			boolean m_found = true;
			boolean m_needAutoBoxing = false;
			int m_mtdIndexOfFirstArgForBoxing = -1;
		}
		
		public static IJstType getCurrentArgType(IExpr expr, IJstType argType) {
			
			IJstType scopeType;
			if (expr instanceof MtdInvocationExpr) {
				MtdInvocationExpr mtdInvocationExpr = ((MtdInvocationExpr)expr);
				IJstType type = findTypeInMethodParams(mtdInvocationExpr, argType);
				if (type != null) {
					return type;
				}
				if (mtdInvocationExpr.getQualifyExpr() == null) return null;
				scopeType = mtdInvocationExpr.getQualifyExpr().getResultType();
			} else if (expr instanceof JstIdentifier) {
				JstIdentifier jstIdn = (JstIdentifier)expr;
				if (jstIdn.getQualifier() == null) {
					return null;
				}
				if (jstIdn.getQualifier() == null) return null;
				scopeType = jstIdn.getQualifier().getResultType();
			} else {
				return null;
			}
			
			//Check to see if the return type is generic param at type level
			if (scopeType instanceof JstTypeWithArgs) {
				JstTypeWithArgs scopeT = (JstTypeWithArgs)scopeType;
				for (int i = 0; i < scopeT.getType().getParamTypes().size(); i++) {
					IJstType itm = scopeT.getType().getParamTypes().get(i);
					if (itm != null && argType!=null && itm.getName().equals(argType.getName())) {
						return scopeT.getArgTypes().get(i);
					}
				}
			}
			return null;
		}

		private static IJstType findTypeInMethodParams(MtdInvocationExpr expr,
				IJstType argType) {
			
			JstIdentifier methodIdentifier = (JstIdentifier)expr.getMethodIdentifier();
			if (methodIdentifier.getJstBinding() instanceof JstMethod) {
				JstMethod jstBinding = (JstMethod)methodIdentifier.getJstBinding();
				if (jstBinding == null || jstBinding.getParamTypes().isEmpty()) {
					return null;
				}
				for (int i=0; i < jstBinding.getParamTypes().size(); i++) {
					if (argType.getName().equals(jstBinding.getParamTypes().get(i).getName())) {
						//Arg type is a generic type, now find the current type
						for (int j = 0; j < jstBinding.getArgs().size(); j++) {
							if (jstBinding.getArgs().get(j).getType().getName().equals(argType.getName())) {
								return expr.getArgs().get(j).getResultType();
							}
						}
						break;
					}
				}
			}
			return null;
		}

		private static Collection<JstMethod> getRemovedMethods(ASTNode astExpr, final BaseJstNode jstNode){
			Name astName;
			if (astExpr instanceof MethodInvocation){
				astName = ((MethodInvocation)astExpr).getName();
			}
			else if (astExpr instanceof SuperMethodInvocation){
				astName = ((SuperMethodInvocation)astExpr).getName();
			}
			else {
				return Collections.emptySet();
			}
			return TranslateCtx.ctx().getTranslateInfo(jstNode.getOwnerType()).getRemovedMtds(astName.toString()).values();
		}
		
		private static CustomInfo getCustomInfo(final JstType jstType, final IJstMethod jstMtd, final TranslateCtx ctx, boolean recusive){
			TranslateInfo tInfo = ctx.getTranslateInfo(jstType);
			CustomInfo cInfo = tInfo.getMethodCustomInfo(MethodKey.genMethodKey(jstMtd));
			if (cInfo != CustomInfo.NONE || !recusive){
				return cInfo;
			}
			
			IJstType baseType = getBase(jstType);
			while (baseType != null && baseType instanceof JstType){
				cInfo = getCustomInfo((JstType)baseType, jstMtd, ctx, recusive);
				if (cInfo != CustomInfo.NONE){
					return cInfo;
				}
				baseType = getBase(baseType);
			}
			
			IJstType outer = jstType.getOuterType();
			while (outer != null && outer instanceof JstType){
				cInfo = getCustomInfo((JstType)outer, jstMtd, ctx, recusive);
				if (cInfo != CustomInfo.NONE){
					return cInfo;
				}
				outer = outer.getOuterType();
			}
			
			return CustomInfo.NONE;
		}
	}
	
	public static class Property {
		
		public static IJstProperty getProperty(final IJstType type, String name){
			if (type == null || name == null){
				return null;
			}
			IJstProperty pty = type.getProperty(name, true, true);
			if (pty == null){
				pty = type.getProperty(name, false, true);
			}
			return pty;
		}
		
		public static boolean includeFieldForDecl(final FieldDeclaration astField, final CustomInfo fInfo, final JstType jstType){
			
			if (fInfo != null && fInfo.isExcluded()){
				return false;
			}
			
			if (!TranslateHelper.isPrivate(astField.modifiers())){
				return true;
			}
			
			TranslateCtx ctx = TranslateCtx.ctx();
			if (ctx.isJavaOnly(jstType) || ctx.isJSProxy(jstType)){
				return false;
			}
			
			IJstType outer = jstType.getOuterType();
			while (outer != null){
				if (ctx.isJavaOnly(outer) || ctx.isJSProxy(outer)){
					return false;
				}
				outer = outer.getOuterType();
			}
			
			return true;
		}
		
		public static boolean includeFieldForImpl(final FieldDeclaration astField, final CustomInfo fInfo, final JstType jstType){
			
			if (fInfo != null && (fInfo.isExcluded() || fInfo.isMappedToJS() || fInfo.isMappedToVJO())){
				return false;
			}
			
			if (!TranslateHelper.isPrivate(astField.modifiers())){
				return true;
			}

			if (fInfo != null && fInfo.isJavaOnly()){
				return false;
			}
			
			TranslateCtx ctx = TranslateCtx.ctx();
			if (ctx.isJavaOnly(jstType) || ctx.isJSProxy(jstType)){
				return false;
			}
			
			IJstType outer = jstType.getOuterType();
			while (outer != null){
				if (ctx.isJavaOnly(outer) || ctx.isJSProxy(outer)){
					return false;
				}
				outer = outer.getOuterType();
			}
			
			return true;
		}
		
		public static void validateFieldReference( 
				final Name astName, 
				final BaseJstNode theNode, 
				final BaseJstNode scopeNode, 
				final BaseTranslator translator){
			
			IJstType ownerType = scopeNode.getOwnerType();
			if (ownerType == null || !(ownerType instanceof JstType)){
				return;
			}
			
			TranslateCtx ctx = TranslateCtx.ctx();
			String fieldName = astName.toString();
			CustomInfo cInfo = getCustomInfo((JstType)ownerType, fieldName, ctx);			
			if(cInfo.isExcluded()) {
				ctx.getLogger().logError(TranslateMsgId.EXCLUDED_FIELD, 
					"'" + fieldName + "' of type '"+ownerType.getName()+"' or from its base or outer", 
					translator, astName, theNode);
			}
		}
		
		private static CustomInfo getCustomInfo(final JstType jstType, final String fieldName, final TranslateCtx ctx){
			TranslateInfo tInfo = ctx.getTranslateInfo(jstType);
			CustomInfo cInfo = tInfo.getFieldCustomInfo(fieldName);	
			if (cInfo != CustomInfo.NONE){
				return cInfo;
			}
			
			IJstType baseType = getBase(jstType);
			while (baseType != null && baseType instanceof JstType){
				cInfo = getCustomInfo((JstType)baseType, fieldName, ctx);
				if (cInfo != CustomInfo.NONE){
					return cInfo;
				}
				baseType = getBase(baseType);
			}
			
			IJstType outer = jstType.getOuterType();
			while (outer != null && outer instanceof JstType){
				cInfo = getCustomInfo((JstType)outer, fieldName, ctx);
				if (cInfo != CustomInfo.NONE){
					return cInfo;
				}
				outer = outer.getOuterType();
			}
			
			return CustomInfo.NONE;
		}
	}
	
	public static class Expression {
		
		public static boolean removeQualifier(IExpr identifier, IJstNode jstNode){
			if (identifier == null || jstNode == null || !(identifier instanceof JstIdentifier)){
				return false;
			}
			TranslateCtx ctx = TranslateCtx.ctx();
			JstIdentifier qualifier = ((JstIdentifier)identifier).getQualifier();
			if (qualifier != null){
				IJstNode binding = qualifier.getJstBinding();
				if (binding != null && binding instanceof JstType){
					JstType type = (JstType)binding;
					if (ctx.isJavaOnly(type)){
						return true;
					}
				}
			}
			
			IJstNode binding = ((JstIdentifier)identifier).getJstBinding();
			TranslateInfo tInfo = ctx.getTranslateInfo((JstType)jstNode.getOwnerType());
			if (binding == null || tInfo == null){
				return false;
			}
			if (binding instanceof JstMethod){
				JstMethod mtd = (JstMethod)binding;
				if (mtd != null){
					MethodKey mtdKey = MethodKey.genMethodKey(mtd);
					tInfo = ctx.getTranslateInfo(mtd.getOwnerType());
					CustomInfo cInfo = tInfo.getMethodCustomInfo(mtdKey);
					return (cInfo.isJavaOnly() || cInfo.isMappedToJS() || cInfo.isMappedToVJO());
				}
			}
			else if (binding instanceof JstProperty){
				JstProperty pty = (JstProperty)binding;
				if (pty != null && pty.getName() != null){
					tInfo = ctx.getTranslateInfo(pty.getOwnerType());
					CustomInfo cInfo = tInfo.getFieldCustomInfo(pty.getName().getName());
					return (cInfo.isJavaOnly() || cInfo.isMappedToJS() || cInfo.isMappedToVJO());
				}
			}

			return false;
		}
		
		public static ObjCreationExpr createObjCreationExpr(final String typeName, final IExpr...args){
			JstType type = JstCache.getInstance().getType(typeName);
			if (type != null) {
				return createObjCreationExpr(type, args);
			}
			return new ObjCreationExpr(new MtdInvocationExpr(new JstIdentifier(typeName), args));
		}
		
		/**
		 * This convenient method creates ObjCreationExpr with "short" name for identifier
		 */
		public static ObjCreationExpr createObjCreationExpr(JstType type, final IExpr...args){
			JstIdentifier identifier = new JstIdentifier(type.getSimpleName()).setType(type);
			return new ObjCreationExpr(new MtdInvocationExpr(identifier, args));
		}
	}
	
	public static AbstractTypeDeclaration getOwnerType(ASTNode astNode){
		if (astNode == null){
			return null;
		}
		if (astNode instanceof AbstractTypeDeclaration){
			return (AbstractTypeDeclaration)astNode;
		}
		ASTNode parent = astNode.getParent();
		while (parent != null){
			if (parent instanceof AbstractTypeDeclaration){
				return (AbstractTypeDeclaration)parent;
			}
			parent = parent.getParent();
		}
		return null;
	}
	
	public static boolean isStatic(final List<?> astModifiers){
		if (astModifiers != null){
			for (Object o: astModifiers){
				if (o instanceof Modifier && ((Modifier)o).isStatic()){
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isPublic(final List<?> astModifiers){
		if (astModifiers != null){
			for (Object o: astModifiers){
				if (o instanceof Modifier && ((Modifier)o).isPublic()){
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isPrivate(final List<?> astModifiers){
		if (astModifiers != null){
			for (Object o: astModifiers){
				if (o instanceof Modifier && ((Modifier)o).isPrivate()){
					return true;
				}
			}
		}
		return false;
	}
	
	public static String getTypeName(final String filePath){
		if (filePath == null){
			return null;
		}
		int index = filePath.lastIndexOf("\\");
		if (index < 0){
			index = filePath.lastIndexOf("/");
		}
		if (index < 0){
			return null;
		}
		String clsName = index < 0 ? filePath : filePath.substring(index+1);
		index = clsName.indexOf(".");
		if (index > 0){
			clsName = clsName.substring(0, index);
		}
		return clsName;
	}
	
	public static String getShortName(final String fullName){
		if (fullName == null){
			return null;
		}
		int index = fullName.lastIndexOf(".");
		return (index >= 0) ? fullName.substring(index+1) : fullName;
	}
	
	public static String getPkgName(final String fullName){
		if (fullName == null){
			return null;
		}
		int index = fullName.lastIndexOf(".");
		return (index >= 0) ? fullName.substring(0, index) : null;
	}
	
	public static VarTable getVarTable(final IJstNode jstNode){
		IJstNode node = jstNode;
		while (node != null) {
			if (node instanceof JstBlock){
				return ((JstBlock)node).getVarTable();
			}
			else if (node instanceof JstType){
				return ((JstType)node).getVarTable();
			}
			node = node.getParentNode();
		}
		
		return null;
	}
	
	public static JstMethod getOwnerMethod(final IJstNode node){
		IJstNode parent = node;
		while (parent != null){
			if (parent instanceof JstMethod){
				return (JstMethod)parent;
			}
			parent = parent.getParentNode();
		}
		return null;
	}
	
	public static JstBlock getOwnerBlock(final IJstNode node){
		if (node instanceof JstMethod){
			return ((JstMethod)node).getBlock();
		}
		IJstNode parent = node;
		while (parent != null){
			if (parent instanceof JstBlock){
				return (JstBlock)parent;
			}
			parent = parent.getParentNode();
		}
		return null;
	}
	
	public static JstBlockInitializer getOwnerBlockInitializer(final IJstNode node){
		IJstNode parent = node;
		while (parent != null){
			if (parent instanceof JstBlockInitializer){
				return (JstBlockInitializer)parent;
			}
			parent = parent.getParentNode();
		}
		return null;
	}
	
	public static boolean isInInstanceBlock(final IJstNode node){
		 JstMethod ownerMtd = getOwnerMethod(node);
		 if (ownerMtd != null && !ownerMtd.getModifiers().isStatic()){
			 return true;
		 }
		 JstBlockInitializer blockInitializer = getOwnerBlockInitializer(node);
		 if (blockInitializer != null && !blockInitializer.getModifiers().isStatic()){
			 return true;
		 }
		 if (node instanceof IJstProperty){
			 return !((IJstProperty)node).isStatic();
		 }
		 return false;
	}
	
	public static boolean isStaticType(final JstIdentifier identifier){
		 if (identifier == null || identifier.getJstBinding() == null){
			 return false;
		 }
		 IJstNode binding = identifier.getJstBinding();
		 if (binding instanceof IJstType){
			 return ((IJstType)binding).getModifiers().isStatic();
		 }
		 return false;
	}
	
	public static IJstType getStaticType(final JstIdentifier identifier){
		 if (identifier == null || identifier.getJstBinding() == null){
			 return null;
		 }
		 IJstNode binding = identifier.getJstBinding();
		 if (binding instanceof IJstType){
			 return (IJstType)binding;
		 }
		 return null;
	}
	
	public static boolean isStaticMember(final JstIdentifier identifier){
		 if (identifier == null || identifier.getJstBinding() == null){
			 return false;
		 }
		 IJstNode binding = identifier.getJstBinding();
		 if (binding instanceof IJstProperty){
			 return ((IJstProperty)binding).isStatic();
		 }
		 if (binding instanceof IJstMethod){
			 return ((IJstMethod)binding).isStatic();
		 }
		 return false;
	}
	
	public static IJstType getJstBindingOwnerType(final JstIdentifier identifier){
		 if (identifier == null || identifier.getJstBinding() == null){
			 return null;
		 }
		 return identifier.getJstBinding().getOwnerType();
	}
	
	public static IJstType getBase(final IJstType jstType){
		if (jstType == null){
			return null;
		}
		IJstType base = jstType.getExtend();
		if (base != null && !VjoTranslateHelper.isVjoObjectType(base)){
			return base;
		}
		if (jstType instanceof JstType){
			return TranslateCtx.ctx().getTranslateInfo((JstType)jstType).getBaseType();
		}
		return null;
	}
	
	public static int getLineNumber(final ASTNode node) {
		if (node != null && node.getRoot() instanceof CompilationUnit) {
			CompilationUnit cu = (CompilationUnit) node.getRoot();
			return cu.getLineNumber(node.getStartPosition());
		}
		return -1;
	}
	
	public static int getColumnNumber(final ASTNode node) {
		if (node != null && node.getRoot() instanceof CompilationUnit) {
			CompilationUnit cu = (CompilationUnit) node.getRoot();
			return cu.getColumnNumber(node.getStartPosition());
		}
		return -1;
	}
	
	public static String getResourceString(final ASTNode node) {
		if (node != null && node.getRoot() instanceof CompilationUnit) {
			CompilationUnit cu = (CompilationUnit) node.getRoot();
			IJavaElement javaElement = cu.getJavaElement();
			if (javaElement != null && javaElement.getResource() != null) {
				return javaElement.getResource().getFullPath().toString();
			}
		}
		return null;
	}
	
	public static boolean isNameAllCapitalized(String name) {
		char [] chars = name.toCharArray();
		for (char ch : chars) {
			if (Character.isLetter(ch) && !Character.isUpperCase(ch)) {
				return false;
			}
		}
		return true;
	}
	
	public static String getOperatorStringForJs(String operator) {
		if (operator != null) {
			if (operator.equals("==")) {
				return BoolExpr.Operator.IDENTICAL.toString();
			} else if (operator.equals("!=")) {
				return BoolExpr.Operator.NOT_IDENTICAL.toString();
			}
		}
		return operator;
	}
	
}

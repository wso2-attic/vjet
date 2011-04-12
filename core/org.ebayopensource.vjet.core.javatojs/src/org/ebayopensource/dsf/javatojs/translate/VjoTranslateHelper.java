/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.ebayopensource.dsf.javatojs.translate.config.MethodKey;
import org.ebayopensource.dsf.javatojs.translate.custom.jdk.JavaLangMeta;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstRefType;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstTypeWithArgs;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.stmt.JstBlockInitializer;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.util.DataTypeHelper;
import org.ebayopensource.vjo.meta.VjoConvention;

public class VjoTranslateHelper {
	
	private static Set<String> s_vjoNativeTypes = new HashSet<String>();
	static {
		s_vjoNativeTypes.add("vjo.Object");
		s_vjoNativeTypes.add("vjo.Enum");
		s_vjoNativeTypes.add("vjo.Class");
		s_vjoNativeTypes.add("document");
		s_vjoNativeTypes.add("window");
	}
	
	public static boolean isVjoObjectType(IJstType type){
		return type != null && JavaLangMeta.VJO_OBJECT.equals(type.getName());
	}

	public static boolean isVjoNativeType(String name){
		return s_vjoNativeTypes.contains(name);
	}
	
	public static boolean isVjoJdkType(String name){
		return name != null && name.startsWith("vjo.java.");
	}
	
	public static IExpr processTypeLiteral(IJstType type, BaseJstNode jstNode){
		JstIdentifier jstQualifier = null;
		if (!type.getModifiers().isStatic()) {
			jstQualifier = new JstIdentifier(VjoConvention.getType(type.getName()));
			jstQualifier.setJstBinding(type);
			jstQualifier.setType(type);
			
		} else {
			jstQualifier = getStaticMemberQualifier(type, jstNode);
		}
		
		//If qualifier is null, assume it's Object.class
		//TODO - is this assumption correct?
		if (jstQualifier == null) {
			jstQualifier = new JstIdentifier("vjo.Object");
			JstType objType = JstCache.getInstance().getType("vjo.Object");
			jstQualifier.setJstBinding(objType);
			jstQualifier.setType(objType);
		}
		return new FieldAccessExpr(
				new JstIdentifier(TranslateCtx.ctx().getConfig().getVjoConvention().getClassKeyword()), 
				jstQualifier);
	}
	
	public static JstIdentifier getStaticPtyOrMtdQualifier(JstIdentifier qualifier, BaseJstNode jstNode){
		if (qualifier == null){
			return null;
		}
		return getStaticMemberQualifier(TranslateHelper.getJstBindingOwnerType(qualifier), jstNode);
	}
	
	public static JstIdentifier getStaticTypeQualifier(JstIdentifier qualifier, BaseJstNode jstNode){
		if (qualifier == null){
			return null;
		}
		return getStaticTypeQualifier(TranslateHelper.getJstBindingOwnerType(qualifier), jstNode);
	}
	
	public static JstIdentifier getStaticMemberQualifier(IJstType qualifierType, BaseJstNode jstNode){
		JstIdentifier qualifier = null;
		qualifier = getStaticTypeQualifier(qualifierType, jstNode);
		if (qualifier == null){
			return null;
		}
		if (qualifierType == jstNode.getOwnerType() || qualifierType == null){
			return qualifier;
		}
		qualifier.setName(qualifier.getName() + "." + qualifierType.getSimpleName());
		return qualifier;
	}
	
	public static JstIdentifier getStaticTypeQualifier(
			final IJstType type, 
			final BaseJstNode jstNode){
		
		return getStaticTypeQualifier(type, jstNode, false);
	}
	
	public static JstIdentifier getStaticTypeQualifier(
			final IJstType type, 
			final BaseJstNode jstNode, 
			boolean enableForceQualify){
			
		TranslateCtx ctx = TranslateCtx.ctx();
		
		if (ctx.isJsType(type)){
			return null;
		}
		
		boolean forceFullyQualify = false;	
		if (enableForceQualify){
			IJstMethod jstMtd = TranslateHelper.Method.getOwnerMethod(jstNode);
			if (jstMtd != null && jstMtd.getOwnerType() instanceof JstType){
				TranslateInfo tInfo = ctx.getTranslateInfo((JstType)jstMtd.getOwnerType());
				MethodKey mtdKey = MethodKey.genMethodKey(jstMtd);
				if (tInfo.getMethodCustomInfo(mtdKey).isForceFullyQualify()){
					forceFullyQualify = true;
				}
			}
		}
		
		IJstType qualifierType = type;
		if (qualifierType instanceof JstTypeWithArgs){
			qualifierType = ((JstTypeWithArgs)qualifierType).getType();
		}
		if (jstNode == null || jstNode.getOwnerType() == null){
			return null;
		}
		IJstType ownerType = jstNode.getOwnerType();
		
		JstIdentifier jstQualifier =
			ctx.getProvider().getCustomTranslator().getStaticTypeQualifier(type, jstNode);
		
		if (jstQualifier != null) {
			String name = jstQualifier.getName();
			if (name == null || name.length() == 0) {
				return null;
			}
			return jstQualifier;
		}
		//External dependency of owner type
		if (qualifierType != null && 
				ownerType.hasImport(qualifierType.getName())) {
			if (qualifierType.getPackage() != null){
				jstQualifier = new JstIdentifier(qualifierType.getPackage().getName());
				jstQualifier.setJstBinding(qualifierType);
				jstQualifier.setType(qualifierType);
				return jstQualifier;
			}
		}
		
		//Use global JS Object if it's java.lang.Object
		if (qualifierType != null && 
				DataTypeHelper.getNativeType(qualifierType.getName()) != null) {
			return null;
		}
		if (qualifierType != null && 
				(/*(qualifierType.getSimpleName().equals(Object.class.getSimpleName()))
				||*/ qualifierType.getName().equals(JavaLangMeta.VJO_ENUM)
				|| qualifierType.getName().equals(JavaLangMeta.VJO_CLASS))){
			return new JstIdentifier(ctx.getConfig().getVjoConvention().getVjoNS());
		}
		
		// Reference self
		if (qualifierType == ownerType){
			jstQualifier = new JstIdentifier(getStaticNsRoot(jstNode, forceFullyQualify));
			jstQualifier.setJstBinding(ownerType);
			jstQualifier.setType(ownerType);
			return jstQualifier;
		}
		
		// No qualifier
		if (qualifierType == null){
			jstQualifier = new JstIdentifier(getStaticNsRoot(jstNode, forceFullyQualify));
			jstQualifier.setJstBinding(ownerType);
			jstQualifier.setType(ownerType);
			return jstQualifier;
		}
		
		// External dependency of owner type
		if (ownerType.hasImport(qualifierType.getSimpleName())){
			jstQualifier = new JstIdentifier(getStaticNsQualifier(qualifierType, forceFullyQualify, jstNode));
			jstQualifier.setJstBinding(qualifierType);
			jstQualifier.setType(qualifierType);
			return jstQualifier;
		}
		
		// External dependency of outer ancestor
		if (ownerType.getRootType().hasImport(qualifierType.getSimpleName())){
			jstQualifier = new JstIdentifier(getStaticNsQualifier(qualifierType, forceFullyQualify, jstNode));
			jstQualifier.setJstBinding(qualifierType);
			jstQualifier.setType(qualifierType);
			return jstQualifier;
		}
		
		// External dependency of outer ancestor
		if (ownerType.getRootType().hasImport(qualifierType.getName())){
			jstQualifier = new JstIdentifier(qualifierType.getPackage().getName());
			jstQualifier.setJstBinding(qualifierType);
			jstQualifier.setType(qualifierType);
			return jstQualifier;
		}
		
		// Sibling
		if (qualifierType.isSiblingType()){
			return getStaticTypeQualifier(qualifierType.getContainingType(), jstNode);
		}
		
		// Embeded
		Stack<IJstType> path = new Stack<IJstType>();
		boolean isDecendent = buildEmbededTypePath(ownerType, qualifierType, path);
		if (isDecendent){
			StringBuilder sb = new StringBuilder(getStaticNsRoot(jstNode, forceFullyQualify));
			sb.append(ctx.getConfig().getVjoConvention().getInnerStaticPrefix());
			for (IJstType inner: path){
				if (inner == path.lastElement()){
					break;
				}
				sb.append(".").append(inner.getSimpleName());
			}
			jstQualifier = new JstIdentifier(sb.toString());
			jstQualifier.setJstBinding(qualifierType);
			jstQualifier.setType(qualifierType);
			return jstQualifier;
		}

		// Outer
		path = new Stack<IJstType>();
		boolean isAncestor = buildEmbededTypePath(qualifierType, ownerType, path);
		if (isAncestor){
			StringBuilder sb = new StringBuilder(getStaticNsRoot(qualifierType, forceFullyQualify));
			sb.append(ctx.getConfig().getVjoConvention().getOuterStaticPrefix());
			for (IJstType inner: path){
				if (inner == path.lastElement()){
					break;
				}
				sb.append(".").append(inner.getSimpleName());
			}
			jstQualifier = new JstIdentifier(sb.toString());
			jstQualifier.setJstBinding(qualifierType);
			jstQualifier.setType(qualifierType);
			return jstQualifier;
		}

		// Relative
		IJstType rootType = ownerType.getRootType();
		path = new Stack<IJstType>();
		boolean isRelative = buildEmbededTypePath(rootType, qualifierType, path);
		if (isRelative){
			StringBuilder sb = new StringBuilder(getStaticNsRoot(qualifierType, forceFullyQualify));
			sb.append(ctx.getConfig().getVjoConvention().getOuterStaticPrefix());
			sb.append(".").append(rootType.getSimpleName());
			for (IJstType inner: path){
				if (inner == path.lastElement()){
					break;
				}
				sb.append(".").append(inner.getSimpleName());
			}
			jstQualifier = new JstIdentifier(sb.toString());
			jstQualifier.setJstBinding(qualifierType);
			jstQualifier.setType(qualifierType);
			return jstQualifier;
		}

		if (qualifierType.getOuterType() != null){
			jstQualifier = getStaticTypeQualifier(qualifierType.getOuterType(), jstNode);
			if (jstQualifier != null){
				StringBuilder sb = new StringBuilder(jstQualifier.getName());
				sb.append(".").append(qualifierType.getOuterType().getSimpleName());
				jstQualifier = new JstIdentifier(sb.toString());
				jstQualifier.setJstBinding(qualifierType);
				jstQualifier.setType(qualifierType);
				return jstQualifier;
			}
		}

		jstQualifier = new JstIdentifier(getStaticNsQualifier(qualifierType, forceFullyQualify, jstNode));
		jstQualifier.setJstBinding(qualifierType);
		jstQualifier.setType(qualifierType);
		
		return jstQualifier;
	}
	
	public static JstIdentifier getInstanceTypeQualifier(IExpr optionalExpr, IJstType qualifierType, IJstNode jstNode){
		if (jstNode == null || jstNode.getOwnerType() == null){
			return null;
		}
		IJstType ownerType = jstNode.getOwnerType();
		JstIdentifier jstQualifier;
		boolean isAnon = ownerType.isAnonymous();
		if (isAnon) {
			IJstNode node = ownerType.getParentNode();
			while (node!=null) {
				if (node instanceof IJstType) {
					ownerType = (IJstType)node;
					break;
				}
				node = node.getParentNode();
			}
		}
		
		// Embeded
		if (TranslateHelper.isInInstanceBlock(jstNode) && ownerType.getEmbededTypes().size() > 0){
			Stack<IJstType> path = new Stack<IJstType>();
			IJstType anchor = optionalExpr == null ? ownerType : optionalExpr.getResultType();
			boolean isEmbeded = buildEmbededTypePath(anchor, qualifierType, path);
			StringBuilder sb = new StringBuilder();
			VjoConvention vjoConvension = TranslateCtx.ctx().getConfig().getVjoConvention();
			if (optionalExpr == null || optionalExpr.toExprText().equals(VjoConvention.getThisPrefix())){
				if (isAnon) {
					sb.append(vjoConvension.getThisPrefix()).append(
							vjoConvension.getParentInstancePrefix());
				} else {
					sb.append(getInstanceNsRoot(jstNode));
				}
			}
			sb.append(vjoConvension.getInnerStaticPrefix());
			if (isEmbeded){
				for (IJstType inner: path){
					if (sb.length() > 0){
						sb.append(".");
					}
					sb.append(inner.getSimpleName());
				}
			}
			jstQualifier = new JstIdentifier(sb.toString());
			jstQualifier.setJstBinding(qualifierType);
			jstQualifier.setType(qualifierType);
			return jstQualifier;
		}
		
		//http://quickbugstage.arch.ebay.com/show_bug.cgi?id=4672
		if (!ownerType.hasImport(qualifierType.getSimpleName()) && ownerType.hasImport(qualifierType.getName())){
			return new JstIdentifier(qualifierType.getName());
		}
		
		return new JstIdentifier(qualifierType.getSimpleName());
	}
	
	public static JstIdentifier getInstanceMemberQualifier(IJstType qualifierType, IJstNode jstNode){
		if (jstNode == null || jstNode.getOwnerType() == null){
			return null;
		}
		IJstType ownerType = jstNode.getOwnerType();
		JstIdentifier jstQualifier;

		// Embeded
		String qualifer;
		if ((qualifer = getOuterQualifier(qualifierType, ownerType))!=null){
			StringBuilder sb = new StringBuilder(getInstanceNsRoot(jstNode));
			sb.append(qualifer);
			jstQualifier = new JstIdentifier(sb.toString());
			jstQualifier.setJstBinding(qualifierType);
			jstQualifier.setType(qualifierType);
			return jstQualifier;
		}
				
		return new JstIdentifier(qualifierType.getSimpleName());
	}
	
	//
	// Private
	//
	private static String getStaticNsRoot(IJstNode jstNode, boolean forceFullyQualify){
		if (forceFullyQualify){
			return jstNode.getOwnerType().getName();
		}
		else if (TranslateHelper.isInInstanceBlock(jstNode) || isStaticBlock(jstNode)){
			return TranslateCtx.ctx().getConfig().getVjoConvention().getNameWithStaticThis(jstNode.getOwnerType().getSimpleName());
		}
		else {
			return TranslateCtx.ctx().getConfig().getVjoConvention().getThisPrefix();
		}
	}
	
	private static String getStaticNsQualifier(IJstType jstType, boolean forceFullyQualify, final IJstNode jstNode){
		if (forceFullyQualify && !(jstType instanceof JstRefType)){
			return jstType.getOwnerType().getPackage().getName();
		}
		else {
			return TranslateCtx.ctx().getConfig().getVjoConvention().getShortHandNS();
		}
	}
	
	private static boolean isStaticBlock(IJstNode jstNode) {
		IJstNode parentNode = jstNode.getParentNode();
		if (parentNode == null) {
			return false;
		}
		if (parentNode instanceof JstBlockInitializer) {
			return ((JstBlockInitializer)parentNode).getModifiers().isStatic();
		}
		return false;
	}
	private static String getInstanceNsRoot(IJstNode jstNode){
		return TranslateCtx.ctx().getConfig().getVjoConvention().getThisPrefix();
	}
	
	private static String getOuterQualifier(final IJstType outerType, final IJstType type){
		IJstType embededType = type;
		if (embededType instanceof JstTypeWithArgs){
			embededType = ((JstTypeWithArgs)embededType).getType();
		}
		String prefix = TranslateCtx.ctx().getConfig().getVjoConvention().getOuterInstancePrefix();
		if (embededType.isAnonymous()){
			return TranslateCtx.ctx().getConfig().getVjoConvention().getParentInstancePrefix();
		}
		IJstType parent = embededType.getOuterType();
		IJstType extend;
		String qualifier = prefix;
		while (parent != null){
			if (parent == outerType){
				return qualifier;
			}
			extend = parent.getExtend();
			while (extend != null){
				if (outerType.getParamNames().isEmpty()){
					if (extend == outerType){
						return qualifier;
					}
				}
				else if (extend instanceof JstTypeWithArgs){
					if (((JstTypeWithArgs)extend).getType() == outerType){
						return qualifier;
					}
				}
				extend = extend.getExtend();
			}
			parent = parent.getOuterType();
			qualifier += prefix;
		}
		return null;
	}
	
	private static boolean buildEmbededTypePath(final IJstType outerType, final IJstType type, final Stack<IJstType> path){
		IJstType embededType = type;
		if (embededType instanceof JstTypeWithArgs){
			embededType = ((JstTypeWithArgs)embededType).getType();
		}
		for (IJstType inner: outerType.getEmbededTypes()){
			path.push(inner);
			if (inner == embededType){
				return true;
			}
			if (buildEmbededTypePath(inner, embededType, path)){
				return true;
			}
			path.pop();
		}
		return false;
	}
}

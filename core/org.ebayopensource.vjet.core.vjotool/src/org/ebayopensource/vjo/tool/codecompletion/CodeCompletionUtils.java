/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.ebayopensource.dsf.jsgen.shared.util.JstDisplayUtils;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ISynthesized;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstFuncType;
import org.ebayopensource.dsf.jst.declaration.JstInferredType;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstObjectLiteralType;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstProxyMethod;
import org.ebayopensource.dsf.jst.declaration.JstProxyProperty;
import org.ebayopensource.dsf.jst.reserved.JsCoreKeywords;
import org.ebayopensource.vjo.lib.LibManager;
import org.ebayopensource.vjo.lib.TsLibLoader;
import org.ebayopensource.vjo.meta.VjoKeywords;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcCTypeProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcTypeProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.keyword.CompletionConstants;
import org.ebayopensource.vjo.tool.codecompletion.advisor.keyword.VjoKeywordFactory;

public class CodeCompletionUtils {
	public final static String SEPERATE_TOKEN = "<<Line_Seperator>>";
	public final static String CURSOR_POSITION_TOKEN = "<<CURSOR_POSITION>>";

	public static boolean isNativeType(IJstType jstType) {
		if(jstType==null){
			return false;
		}
		JstPackage pkg = jstType.getPackage();
		if (pkg == null) {
			return true;
		}
		String groupName = pkg.getGroupName();
		return TsLibLoader.isDefaultLibName(groupName);
	}

	public static boolean isSameType(IJstType type1, IJstType type2) {
		if (type1 == null || type2 == null || type1.getName() == null
				|| type2.getName() == null) {
			return false;
		}
		String callingName = type1.getName();
		String calledName = type2.getName();
		return calledName.equals(callingName);
	}

	/**
	 * @param inner
	 * @param outer
	 * @return wether inner is outer's inner type
	 */
	public static boolean isInnerType(IJstType inner, IJstType outer) {
		if (inner == null) {
			return false;
		}
		IJstNode node = inner.getParentNode();
		while (node != null && (node instanceof IJstType)) {
			if (isSameType(outer, (IJstType) node)) {
				return true;
			}
			node = node.getParentNode();
		}
		return false;
	}

	public static boolean isSamePackage(IJstType type1, IJstType type2) {
		if (type1 == null || type2 == null || type1.getName() == null
				|| type2.getName() == null) {
			return false;
		}
		JstPackage pack1 = type1.getPackage();
		JstPackage pack2 = type2.getPackage();
		if (pack1 == null || pack2 == null || pack1.getName() == null
				|| pack2.getName() == null) {
			return false;
		}
		return pack1.getName().equals(pack2.getName());
	}

	public static boolean isSuperType(IJstType type, IJstType superType) {
		if (type == null || superType == null || type.getName() == null
				|| superType.getName() == null) {
			return false;
		}
		List<String> list = new ArrayList<String>();
		IJstType stype = type.getExtend();
		while (stype != null && stype.getName() != null) {
			list.add(stype.getName());
			stype = stype.getExtend();
		}
		return list.contains(superType.getName());
	}

	/**
	 * @param method
	 * @return xx(arg)
	 */
	public static String getMthodsStr(IJstMethod method) {
		StringBuffer buffer = new StringBuffer();
		String name = method.getName().getName();
		buffer.append(name).append(":");
		List<JstArg> args = method.getArgs();
		if (args != null) {
			Iterator<JstArg> it = args.iterator();
			while (it.hasNext()) {
				JstArg arg = it.next();
				String argType = arg.getType().getName();
				buffer.append(argType).append(",");
			}
		}
		return buffer.toString();
	}

	public static boolean hasReturnValue(IJstMethod method) {
		String rname = getReturnType(method);
		return !JsCoreKeywords.VOID.equals(rname);
	}

	public static String getReturnType(IJstMethod method) {
		IJstType ref = method.getRtnType();
		String rname = JsCoreKeywords.VOID;
		if (ref != null) {
			rname = ref.getName();
		}
		return rname;
	}

	/**
	 * @param method
	 * @return xx(arg) rtype - otype
	 */
	public static String getFullMethodString(IJstMethod method) {
		return getFullMethodString(method, method.getOwnerType());
	}
	
	public static String getFullMethodString(IJstMethod method, 
			final IJstType ownerType){
		return getFullMethodString(method, ownerType, false);
	}
	
	public static String getFullMethodString(IJstMethod method, 
			final IJstType ownerType, final boolean optional){
		return getFullMethodString(method.getName().getName(), method, ownerType, false);
	}
	
	public static String getFullMethodString(String name, IJstMethod method, 
			final IJstType ownerType, final boolean optional){
		return JstDisplayUtils.getFullMethodString(name, method, ownerType, optional);
	}

	public static String getJstArgsStringForReplace(IJstMethod method) {
		StringBuffer buffer = new StringBuffer();
		List<JstArg> args = method.getArgs();
		if (args != null && !args.isEmpty()) {
			int counter=0;
			Iterator<JstArg> it = args.iterator();
			while (it.hasNext()) {
				
				JstArg arg = it.next();
				if(arg.getType() instanceof JstObjectLiteralType || arg.getType().getName().equals("ObjLiteral")){
//					if(counter==1){
					// TODO get correct indent level here
						buffer.append("{"+CURSOR_POSITION_TOKEN+"}");
//					}else{
//						buffer.append("{\n\n}");
//					}
				// TODO replacement string could have function stub
				}else if(arg.getType().getName().equals("Function")&& !(arg.getType() instanceof JstFuncType)){
//					if(counter==1){
						// TODO get correct indent level here
						
						buffer.append("function(){"+CURSOR_POSITION_TOKEN+"}");
//					}else{
//						buffer.append("{\n\n}");
//					}
					}else{
					buffer.append(arg.getName());
				}
				buffer.append(", ");
				counter++;
			}
		}
		String result = buffer.toString();
		if (result.length() > 2) {
			result = result.substring(0, result.length() - 2);
		}
		return result;

	}


	public static String getMethodStringForOverrideProposal(IJstMethod method) {
		StringBuffer buffer = new StringBuffer();
		String name = method.getName().getName();
		String opString = "Override";
		if (method.getOwnerType().isInterface()) {
			opString = "Implement";
		}
		buffer.append(name);
		buffer.append("(");
		IJstType ref = method.getRtnType();
		String aname = JstDisplayUtils.getJstArgsString(method);
		if (aname.length() > 0) {
			buffer.append(aname);
		}
		buffer.append(")");
		if (ref != null) {
			String rname = ref.getName();
			buffer.append(" " + rname);
		}
		buffer.append(" - ");
		buffer.append(opString + " method in '");
		buffer.append(method.getOwnerType().getName());
		buffer.append("'");
		return buffer.toString();
	}

	/**
	 * calculate the function definition string which will be injected into js
	 * file during overriding the method
	 * 
	 * @param method
	 * @return
	 */
	public static String getReplaceStringForOverrideProposal(IJstMethod method, String indent) {
		StringBuffer buffer = new StringBuffer();
		String commStr = getJstCommentStringR(method);
		buffer.append("//>" + commStr);
		buffer.append(SEPERATE_TOKEN);
		String name = method.getName().getName();
		buffer.append(name);
		buffer.append(" : function(");
		String aname = getJstArgsStringR(method);
		if (aname.length() > 0) {
			buffer.append(aname);
		}
		buffer.append(")");
		IJstType rtype = method.getRtnType();
		String rExpr = getReturnExprStr(rtype, method);
		buffer.append("{" + SEPERATE_TOKEN + indent + CURSOR_POSITION_TOKEN
				+ rExpr + SEPERATE_TOKEN + "}");
		return buffer.toString();
	}

	private static String getReturnExprStr(IJstType rtype, IJstMethod method) {

		if (method.getOwnerType().isInterface()) {
			if (VjoCcCtx.isVoidType(rtype)) {
				return "";
			} else {
				return "return null;";
			}
		} else {
			if (VjoCcCtx.isVoidType(rtype)) {
				return JsCoreKeywords.THIS + "." + VjoKeywords.BASE + "."
						+ method.getName() + "("
						+ getJstArgsStringForReplace(method) + ");";
			} else {
				return "return " + JsCoreKeywords.THIS + "." + VjoKeywords.BASE
						+ "." + method.getName() + "("
						+ getJstArgsStringForReplace(method) + ");";
			}
		}
	}

	public static String getDefaultValueStr(IJstType type) {
		String typeName = type.getSimpleName();
		if ("boolean".equals(typeName)) {
			return "false";
		} else if ("int".equals(typeName) || "float".equals(typeName)
				|| "double".equals(typeName)) {
			return "0";
		} else {
			return null;
		}
	}

	public static String getJstCommentStringR(IJstMethod method) {

		StringBuffer buffer = new StringBuffer();
		buffer.append(getModifierStr(method.getModifiers()) + " ");
		String name = method.getName().getName();
		IJstType ref = method.getRtnType();
		if (ref != null) {
			String rname = ref.getName();
			buffer.append(rname + " ");
		}
		buffer.append(name);
		buffer.append("(");
		String aname = JstDisplayUtils.getJstArgsString(method);
		if (aname.length() > 0) {
			buffer.append(aname);
		}
		buffer.append(")");
		return buffer.toString();

	}

	public static String getModifierStr(JstModifiers modifies) {
		StringBuffer buffer = new StringBuffer();
		if (modifies.isPublic()) {
			buffer.append("public");
		}
		if (modifies.isProtected()) {
			buffer.append("protected");
		}
		if (modifies.isPrivate()) {
			buffer.append("private");
		}
		// if (modifies.isStatic()) {
		// buffer.append(" static");
		// }
		if (modifies.isFinal()) {
			buffer.append(" final");
		}
		return buffer.toString();
	}

	/**
	 * arg String, used in replace string
	 * 
	 * @param method
	 * @return
	 */
	public static String getJstArgsStringR(IJstMethod method) {
		StringBuffer buffer = new StringBuffer();
		method = getDispatchMethod(method);
		List<JstArg> args = method.getArgs();
		if (args != null && !args.isEmpty()) {
			Iterator<JstArg> it = args.iterator();
			while (it.hasNext()) {
				JstArg arg = it.next();
				buffer.append("" + arg.getName());
				buffer.append(", ");
			}
		}
		String result = buffer.toString();
		if (result.length() > 2) {
			result = result.substring(0, result.length() - 2);
		}
		return result;
	}

	public static IJstMethod getDispatchMethod(IJstMethod method) {
		IJstType type = method.getOwnerType();
		if (type == null || type.isInterface()) {
			return method;
		}
		IJstMethod m = type.getMethod(method.getName().getName());
		if (m != null) {
			return m;
		} else {
			return method;
		}
	}

	/**
	 * @param modifier
	 * @return
	 */
	public static String getConstructorString(String modifier, String indent) {
		if (modifier == null) {
			modifier = "public";
		}
		return "//>" + modifier + " constructs()" + SEPERATE_TOKEN
				+ "constructs : function(){" + SEPERATE_TOKEN + indent
				+ CURSOR_POSITION_TOKEN + SEPERATE_TOKEN + "}";
	}

	public static String getPropertyString(IJstProperty property) {
		final StringBuilder strBldr = new StringBuilder();
		final String name = property.getName().getName();
		final String rname = property.getType() == null ? "" : property.getType().getSimpleName();
		strBldr.append(name);
		strBldr.append(" ");
		strBldr.append(rname);
		strBldr.append(isOptionalProperty(property) ? " ?" : "");
		strBldr.append(" - ");
		strBldr.append(getOwnerTypeName(property));
		return strBldr.toString();
	}

	/**
	 * added by huzhou@ebay.com to display property's optional in object literal case
	 * @param property
	 * @return
	 */
	private static boolean isOptionalProperty(IJstProperty property) {
		if(property == null){
			return false;
		}
		final IJstType ownerType = property.getOwnerType();
		if(ownerType instanceof JstObjectLiteralType){
			return ((JstObjectLiteralType)ownerType).getOptionalFields().contains(property instanceof JstProxyProperty ? ((JstProxyProperty)property).getTargetProperty() : property);
		}
		return false;
	}

	// to consider the type is etype, when etype,
	public static String getOwnerTypeName(IJstProperty property) {
		IJstType type = property.getType();
		String rname = type == null ? "" : type.getName();
		IJstType otype = property.getOwnerType();
		if(otype!=null){
			if(otype instanceof JstObjectLiteralType){
				JstObjectLiteralType objtype = (JstObjectLiteralType)otype;
				
				if(objtype.getParentNode()!=null && objtype.getParentNode() instanceof IJstType){
					return ((IJstType)objtype.getParentNode()).getSimpleName() + "::" + objtype.getSimpleName();
				}else{
					return objtype.getName();
				}
				
			}else{
				return otype.getSimpleName();
			}
		}
		
		return rname;
	}

	public static String getTypeDispalyString(IJstType type) {
		JstPackage packge = type.getPackage();
		if (packge == null) {
			return type.getSimpleName();
		} else {
			return type.getSimpleName() + " - " + packge.getName();
		}
	}

	public static String getModifierStr(int modifer) {
		if ((modifer & JstModifiers.PUBLIC) != 0) {
			return "public";
		} else if ((modifer & JstModifiers.PROTECTED) != 0) {
			return "protected";
		} else if ((modifer & JstModifiers.PRIVATE) != 0) {
			return "private";
		} else if ((modifer & JstModifiers.STATIC) != 0) {
			return "static";
		} else if ((modifer & JstModifiers.FINAL) != 0) {
			return "final";
		} else {
			return "";
		}
	}

	public static String getCommentKeywordReplaceString(String name,
			String typeName, boolean inScriptUnit, String indent) {
		if (VjoKeywords.NEEDS.equals(name)) {
			return name + "(" + CURSOR_POSITION_TOKEN + ")";
		} else {
			return getKeywordReplaceString(name, typeName, inScriptUnit, indent);
		}
	}

	public static String getKeywordReplaceString(String name, String typeName,
			boolean inSciptUnit, String indent) {
		if (isTypeDeclare(name)) {
			if (typeName == null || typeName.trim().equals("")) {
				return name + "()";
			} else {
				return name + "('" + typeName + "')";
			}

		} else if (VjoKeywords.PROPS.equals(name)
				|| VjoKeywords.DEFS.equals(name)
				|| VjoKeywords.OPTIONS.equals(name)
				|| VjoKeywords.PROTOS.equals(name)
				|| VjoKeywords.GLOBALS.equals(name)) {
			return name + "({" + SEPERATE_TOKEN + indent + CURSOR_POSITION_TOKEN
					+ SEPERATE_TOKEN + "})";
		} else if (VjoKeywords.NEEDS.equals(name)
				|| VjoKeywords.INHERITS.equals(name)
				|| VjoKeywords.SATISFIES.equals(name)
				|| VjoKeywords.MIXIN.equals(name)
				|| VjoKeywords.EXPECTS.equals(name)
				|| VjoKeywords.VALUES.equals(name)) {
			return name + "('" + CURSOR_POSITION_TOKEN + "')";

		} else if (VjoKeywords.INITS.equals(name)) {
			return name + "(" + "function(){" + SEPERATE_TOKEN + indent
					+ CURSOR_POSITION_TOKEN + SEPERATE_TOKEN + "}" + ")";
		} else if (VjoKeywords.ENDTYPE.equals(name)) {
			return name + "()" + (inSciptUnit ? ";" : "");
		}
		return name;

	}

	public static String getKeywordReplaceString(IJstMethod method,
			String typeName, boolean inSciptUnit, String indent) {
		String name = method.getName().getName();
		if (isTypeDeclare(name)) {
			if (typeName == null || typeName.trim().equals("")) {
				return name + "()";
			} else {
				return name + "('" + typeName + "')";
			}

		} else if (VjoKeywords.PROPS.equals(name)
				|| VjoKeywords.DEFS.equals(name)
				|| VjoKeywords.OPTIONS.equals(name)
				|| VjoKeywords.PROTOS.equals(name)
				) {
			return name + "({" + SEPERATE_TOKEN + indent + CURSOR_POSITION_TOKEN
					+ SEPERATE_TOKEN + "})";
		} else if (VjoKeywords.NEEDS.equals(name)
				|| VjoKeywords.INHERITS.equals(name)
				|| VjoKeywords.SATISFIES.equals(name)
				|| VjoKeywords.MIXIN.equals(name)
				|| VjoKeywords.EXPECTS.equals(name)
				|| VjoKeywords.GLOBALS.equals(name)
				|| VjoKeywords.VALUES.equals(name)) {
			List<JstArg> args = method.getArgs();
			if (args.size() == 2) {
				JstArg arg0 = args.get(0);
				JstArg arg1 = args.get(1);
				if (isStringType(arg0.getType())
						&& isStringType(arg1.getType())) {
					return name + "('" + CURSOR_POSITION_TOKEN + "','')";
				}
			} else if (args.size() == 1) {
				JstArg arg = args.get(0);
				IJstType argType = arg.getType();
				if (isArrayType(argType)) {
					return name + "(['" + CURSOR_POSITION_TOKEN + "'])";
				}
			}
			return name + "('" + CURSOR_POSITION_TOKEN + "')";

		} else if (VjoKeywords.INITS.equals(name)) {
			return name + "(" + "function(){" + SEPERATE_TOKEN + indent
					+ CURSOR_POSITION_TOKEN + SEPERATE_TOKEN + "}" + ")";
		} else if (VjoKeywords.ENDTYPE.equals(name)) {
			return name + "()" + (inSciptUnit ? ";" : "");
		}
		return name;
	}

	/**
	 * VjoCcFunctionGenProposalAdvisor
	 * 
	 * @param identifier
	 * @param functionName
	 * @return //> public void xxx() xxx:function(){}
	 */
	public static String getFunctionString(int identifier, String functionName, String indent) {
		String sidentifier = CodeCompletionUtils.getModifierStr(identifier);
		String cString = "//>" + sidentifier + " void " + functionName + "() "
				+ SEPERATE_TOKEN + functionName + " : function(){"
				+ SEPERATE_TOKEN + indent + CURSOR_POSITION_TOKEN
				+ SEPERATE_TOKEN + "}";
		return cString;
	}
	
	public static String getFullFunctionWithoutOverloading(final IJstMethod function, String indent){
		return new StringBuilder()
			.append(function.getName().getName()).append(" : ")
			.append(getFullFunctionWithoutOverloadingOrNaming(function, indent)).toString();
	}
	
	public static String getFullFunctionWithoutOverloadingOrNaming(final IJstMethod function, String indent){
		return new StringBuilder()
			.append("function(").append(getFullFunctionParams(function)).append("){")
			.append(SEPERATE_TOKEN).append(indent).append(CURSOR_POSITION_TOKEN)
			.append(getReturnStmt(function, indent))
			.append(SEPERATE_TOKEN).append('}').toString();
	}

	private static String getReturnStmt(IJstMethod function, String indent) {
		final IJstType rtnType = function.getRtnType();
		if(rtnType == null
				|| rtnType.getName() == null
				|| "void".equals(rtnType.getName())){
			return "";
		}
		final StringBuilder rtnStmt = new StringBuilder();
		rtnStmt.append(SEPERATE_TOKEN).append(indent).append("return ");
		
		if(rtnType.equals(JstCache.getInstance().getType("boolean"))){
			rtnStmt.append("false;");
		}
		else{
			rtnStmt.append("null;");
		}
		return rtnStmt.toString();
	}
	
	private static StringBuilder getFullFunctionParams(IJstMethod function){
		final StringBuilder sb = new StringBuilder();
		for(final Iterator<JstArg> it = function.getArgs().iterator(); it.hasNext();){
			final JstArg param = it.next();
			sb.append(param.getName()).append(',').append(' ');
		}
		if(sb.length() > 0){
			sb.delete(sb.length() - 2, sb.length());
		}
		return sb;
	}

	/**
	 * VjoCcFunctionGenProposalAdvisor
	 * 
	 * @param identifier
	 * @param functionName
	 * @return //> public void xxx() xxx:function(){}
	 */
	public static String getMainFunctionString(String indent) {
		String cString = "//>public" + " void main" + "(String... args) "
				+ SEPERATE_TOKEN + "main" + " : function(args){"
				+ SEPERATE_TOKEN + indent + CURSOR_POSITION_TOKEN
				+ SEPERATE_TOKEN + "}";
		return cString;
	}

	/**
	 * VjoCcFunctionGenProposalAdvisor
	 * 
	 * @param identifier
	 * @param functionName
	 * @return //> public void xxx() xxx:vjo.NEEDS_IMPL
	 */
	public static String getInterfaceFunctionString(int identifier,
			String functionName) {
		String sidentifier = CodeCompletionUtils.getModifierStr(identifier);
		String cString = "//>" + sidentifier + " void " + functionName + "() "
				+ SEPERATE_TOKEN + functionName + " : vjo.NEEDS_IMPL";
		return cString;
	}

	public static String getPropertyProposalReplaceStr(boolean isGlobal,
			IJstProperty property, VjoCcCtx vjoCcCtx) {
		final String ptyName = property.getName().getName();
		String replaceString = ptyName;
		if (!(isGlobal
				|| vjoCcCtx.getPositionType() == VjoCcCtx.POSITION_AFTER_THIS
				|| vjoCcCtx.getPositionType() == VjoCcCtx.POSITION_AFTER_THISVJO || vjoCcCtx
				.getPositionType() == VjoCcCtx.POSITION_AFTER_THISVJOTYPE)) {
			if (vjoCcCtx.hasNoPrefix()) {
				if (property.isStatic()
						&& (property.getOwnerType() != vjoCcCtx.getActingType() || !vjoCcCtx
								.isInStatic())) {
					replaceString = CompletionConstants.THIS_VJO
							+ CompletionConstants.DOT
							+ property.getOwnerType().getSimpleName()
							+ CompletionConstants.DOT + replaceString;
				} else {
					replaceString = CompletionConstants.THIS
							+ CompletionConstants.DOT + replaceString;
				}
			}
			if(isPropertyNameQuoted(ptyName)){
				replaceString = new StringBuilder(ptyName.length() + 2).append('[').append(ptyName).append(']').toString();
			}
		} 
		return replaceString;
	}

	private static boolean isPropertyNameQuoted(final String ptyName) {
		if(ptyName == null){
			return false;
		}
		
		final int ptyNameLen = ptyName.length();
		return ptyNameLen > 0 && 
				((ptyName.indexOf('\'') == 0 && ptyName.lastIndexOf('\'') == ptyNameLen - 1)
						|| (ptyName.indexOf('"') == 0 && ptyName.indexOf('"') == ptyNameLen - 1));
	}

	public static String getOuterMethodProposalReplaceStr(IJstMethod method) {
		String replaceString = method.getName().getName() + "("
				+ getJstArgsStringForReplace(method) + ")";
		if (method.isStatic()) {

			replaceString = CompletionConstants.THIS_VJO
					+ CompletionConstants.DOT
					+ method.getOwnerType().getSimpleName()
					+ CompletionConstants.DOT + replaceString;
		} else {
			replaceString = CompletionConstants.THIS_VJO
					+ CompletionConstants.DOT
					+ VjoKeywordFactory.KWD_OUTER.getName()
					+ CompletionConstants.DOT + replaceString;
		}
		return replaceString;
	}

	public static String getOuterPropertyProposalReplaceStr(
			IJstProperty property) {
		String replaceString = property.getName().getName();
		if (property.isStatic()) {

			replaceString = CompletionConstants.THIS_VJO
					+ CompletionConstants.DOT
					+ property.getOwnerType().getSimpleName()
					+ CompletionConstants.DOT + replaceString;
		} else {
			replaceString = CompletionConstants.THIS_VJO
					+ CompletionConstants.DOT
					+ VjoKeywordFactory.KWD_OUTER.getName()
					+ CompletionConstants.DOT + replaceString;
		}
		return replaceString;
	}

	public static String getOuterPropertyProposalReplaceStr(IJstType type) {
		String replaceString = type.getSimpleName();
		IJstType parentType = (IJstType) type.getParentNode();
		if (type.getModifiers().isStatic()) {
			replaceString = CompletionConstants.THIS_VJO
					+ CompletionConstants.DOT + parentType.getSimpleName()
					+ CompletionConstants.DOT + replaceString;
		} else {
			replaceString = CompletionConstants.THIS_VJO
					+ CompletionConstants.DOT
					+ VjoKeywordFactory.KWD_OUTER.getName()
					+ CompletionConstants.DOT + replaceString;
		}
		return replaceString;
	}

	public static String getMethodProposalReplaceStr(boolean isGlobal,
		IJstMethod method, String name, VjoCcCtx vjoCcCtx) {
				
		name = JstDisplayUtils.renameInvoke(method, name);
		
		String replaceString = name + "("
				+ getJstArgsStringForReplace(method) + ")";
//		if (vjoCcCtx.isVjoMethod(method)) {
//			String temp = getKeywordReplaceString(method, vjoCcCtx
//					.isInSciptUnitArea() ? vjoCcCtx.getTypeName() : "",
//					vjoCcCtx.isInSciptUnitArea());
//			if (!name.equals(temp)) {
//				replaceString = temp;
//			}
//		} else 
			
			if (isGlobal
				|| vjoCcCtx.getPositionType() == VjoCcCtx.POSITION_AFTER_THIS
				|| vjoCcCtx.getPositionType() == VjoCcCtx.POSITION_AFTER_THISVJO
				|| vjoCcCtx.getPositionType() == VjoCcCtx.POSITION_AFTER_THISVJOTYPE
		// || vjoCcCtx.callFromDifferentType(method)
		) {
			// do nothing
		} else {
			if (vjoCcCtx.hasNoPrefix()) {
				IJstType mOwnwerType = method.getOwnerType();
				if (method.isStatic()
						&& (mOwnwerType != vjoCcCtx.getActingType() || !vjoCcCtx
								.isInStatic())) {
					String typeName = mOwnwerType.getSimpleName();
					if (method.getOwnerType().isMixin()) {
						typeName = vjoCcCtx.getActingType().getSimpleName();
					}
					replaceString = CompletionConstants.THIS_VJO
							+ CompletionConstants.DOT + typeName
							+ CompletionConstants.DOT + replaceString;
				} else {
					replaceString = CompletionConstants.THIS
							+ CompletionConstants.DOT + replaceString;
				}
			}
		}
		return replaceString;
	}

	public static void printProposal(List<IVjoCcProposalData> list) {
		 System.out.println("Begin proposal...");
		 Iterator<IVjoCcProposalData> it = list.iterator();
		 while (it.hasNext()) {
		 IVjoCcProposalData pdata = it.next();
		 Object data = pdata.getData();
		 String str = "";
		 if (data instanceof IJstType) {
		 str = ((IJstType) data).getName();
		 } else if (data instanceof IJstMethod) {
		 str = ((IJstMethod) data).getName().getName();
		 } else if (data instanceof IJstProperty) {
		 str = ((IJstProperty) data).getName().getName();
		 } else if (data instanceof JstPackage) {
		 JstPackage pck = (JstPackage) data;
		 str = pck.getName();
		 } else if (data instanceof String) {
		 str = (String) data;
		 }
		 System.out.println(pdata.getAdvisor() + ":" + str);
		 }
		 System.out.println("End proposal...");
	}

	public static int[] getGeneralFieldCallLevel(IJstType callingType,
			IJstType calledType) {
		if (CodeCompletionUtils.isSameType(callingType, calledType)
				|| CodeCompletionUtils.isInnerType(calledType, callingType)
				|| CodeCompletionUtils.isInnerType(callingType, calledType)) {
			return new int[] { JstModifiers.PUBLIC, JstModifiers.PROTECTED,
					JstModifiers.NONE, JstModifiers.PRIVATE };
		}
		List<Integer> list = new ArrayList<Integer>();
		list.add(JstModifiers.PUBLIC);
		if (CodeCompletionUtils.isSamePackage(callingType, calledType)) {
			list.add(JstModifiers.NONE);
			list.add(JstModifiers.PROTECTED);
		} else if (CodeCompletionUtils.isSuperType(callingType, calledType)) {
			list.add(JstModifiers.PROTECTED);
		}
		int[] is = new int[list.size()];
		int i = 0;
		Iterator<Integer> it = list.iterator();
		while (it.hasNext()) {
			is[i++] = it.next();
		}
		return is;
	}

	public static boolean levelCheck(JstModifiers modifies, int[] levels) {
		for (int level : levels) {
			switch (level) {
			case JstModifiers.PUBLIC:
				if (modifies.isPublic()) {
					return true;
				}
				break;
			case JstModifiers.NONE:
				if (!modifies.isPublic() && !modifies.isPrivate()
						&& !modifies.isProtected()) {
					return true;
				}
				break;
			case JstModifiers.PROTECTED:
				if (modifies.isProtected()) {
					return true;
				}
				break;
			case JstModifiers.PRIVATE:
				if (modifies.isPrivate()) {
					return true;
				}
				break;
			default:
				break;
			}
		}
		return false;
	}

	public static String getInnerTypeAsPropertyProposalReplaceStr(
			boolean isGlobal, IJstType jstType, VjoCcCtx vjoCcCtx) {
		String replaceString = jstType.getSimpleName();
		if (isGlobal
				|| vjoCcCtx.getPositionType() == VjoCcCtx.POSITION_AFTER_THIS
				|| vjoCcCtx.getPositionType() == VjoCcCtx.POSITION_AFTER_THISVJO
				|| vjoCcCtx.getPositionType() == VjoCcCtx.POSITION_AFTER_THISVJOTYPE
		// || vjoCcCtx.callFromDifferentType(property)
		) {
		} else {
			if (vjoCcCtx.hasNoPrefix()) {
				replaceString = CompletionConstants.THIS
						+ CompletionConstants.DOT + replaceString;
			}
		}
		return replaceString;
	}

	public static boolean isTypeType(String typeName) {
		return "vjo.ctype".equals(typeName) || "vjo.mtype".equals(typeName)
				|| "vjo.itype".equals(typeName) || "vjo.etype".equals(typeName)
				|| "vjo.otype".equals(typeName) || "vjo.ftype".equals(typeName);
	}

	public static boolean isTypeRefDeclare(String name) {
		return VjoKeywords.NEEDS.equals(name);

	}

	public static boolean isInterfaceRefDeclare(String name, String type) {
		return VjoKeywords.SATISFIES.equals(name)
				|| (VjoKeywords.INHERITS.equals(name) && VjoKeywords.ITYPE
						.equals(type));
	}

	public static boolean isMixinTypeRefDeclare(String name) {
		return VjoKeywords.MIXIN.equals(name);
	}

	public static boolean isCTypeRefDeclare(String name, String type) {
		return VjoKeywords.INHERITS.equals(name)
				&& !VjoKeywords.ITYPE.equals(type);
	}

	public static boolean isTypeDeclare(String key) {
		return VjoKeywords.CTYPE.equals(key) || VjoKeywords.ETYPE.equals(key)
				|| VjoKeywords.ITYPE.equals(key)
				|| VjoKeywords.MTYPE.equals(key)
				|| VjoKeywords.OTYPE.equals(key);
	}

	public static IJstMethod getMethod(IJstType type, String methodName) {
		IJstMethod method = null;
		if (VjoKeywords.CONSTRUCTS.equals(methodName)) {
			method = type.getConstructor();
		} else {
			method = type.getMethod(methodName);
		}
		return method;
	}

	/**
	 * The following keywords are resolved as special type, and need not to be
	 * translate into DLTK model. 1. vj$ in "this.vj$" 2. base in "this.base" 3.
	 * default constructor
	 * 
	 * @param clazz
	 * @return
	 * 
	 */
	public static boolean isSynthesizedElement(Object obj) {
		if (ISynthesized.class.isAssignableFrom(obj.getClass())) {
			return true;
		} else if (obj instanceof JstProxyProperty) {
			return isSynthesizedElement(((JstProxyProperty) obj)
					.getTargetProperty().getClass());
		} else if (obj instanceof JstProxyMethod) {
			return isSynthesizedElement(((JstProxyMethod) obj)
					.getTargetMethod().getClass());
		} else {
			return false;
		}

	}

	public static boolean isStringType(IJstType type) {
		if (type == null) {
			return false;
		}
		return "String".equals(type.getName())
				&& LibManager.JS_NATIVE_LIB_NAME.equals(type.getPackage()
						.getGroupName());
	}

	public static boolean isArrayType(IJstType type) {
		if (type == null) {
			return false;
		}
		return "Array".equals(type.getName())
				&& LibManager.JS_NATIVE_LIB_NAME.equals(type.getPackage()
						.getGroupName());

	}

	public static String getAliasOrTypeName(IJstType ownerType, IJstType type) {
		if (type instanceof JstInferredType) {
			type = ((JstInferredType) type).getType();
		}
		if (ownerType != null) {
			Iterator<?> map = ownerType.getImportsMap().entrySet().iterator();
			while (map.hasNext()) {
				Entry<String, ? extends IJstType> entry = (Entry<String, IJstType>) map
						.next();
				if (entry.getValue().equals(type)) {
					return entry.getKey();
				}
			}
		}
		return type.getSimpleName();
	}

	/**
	 * if the a1 is an id of type related advisor
	 * 
	 * @param a1
	 * @return
	 */
	public static boolean isTypeAdvisor(String a1) {
		return a1.equals(VjoCcTypeProposalAdvisor.ID)
				|| a1.equals(VjoCcCTypeProposalAdvisor.ID);
	}

	public static IJstType getOuterJstType(IJstType jstType) {
		IJstNode result = jstType;
		while (result.getParentNode() != null) {
			result = result.getParentNode();
			if (result instanceof IJstType) {
				jstType = (IJstType) result;
			}
		}
		return jstType;
	}

	public static String getJstArgsString(IJstMethod method) {
		return JstDisplayUtils.getJstArgsString(method);
	}
}

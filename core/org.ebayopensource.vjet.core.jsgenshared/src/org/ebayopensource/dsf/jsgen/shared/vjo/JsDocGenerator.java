/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.vjo;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jst.IInferred;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstRefType;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ISynthesized;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstArray;
import org.ebayopensource.dsf.jst.declaration.JstConstructor;
import org.ebayopensource.dsf.jst.declaration.JstFuncType;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstParamType;
import org.ebayopensource.dsf.jst.declaration.JstTypeWithArgs;
import org.ebayopensource.dsf.jst.declaration.JstVariantType;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.CastExpr;
import org.ebayopensource.dsf.jst.expr.JstInitializer;
import org.ebayopensource.dsf.jst.reserved.JsCoreKeywords;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.util.DataTypeHelper;
import org.ebayopensource.vjo.meta.VjoKeywords;

public class JsDocGenerator extends BaseGenerator {

	public static final String DOT = ".";
	public static final String TYPE_REF_PREFIX = "type::";
	private List<IJstMethod> m_methodsWritten = new ArrayList<IJstMethod>();
	public JsDocGenerator(GeneratorCtx ctx) {
		super(ctx);
	}
	
	public void writeJsDoc(final IJstType type) {
		
		JstModifiers modifiers = type.getModifiers();
		boolean hasParams = type.getParamTypes().size() > 0;
		getWriter().append(SPACE);
		
		startWriteJsDoc(false);

		// Access
		writeJsDocAccessScope(modifiers.getAccessScope());

		//Dynamic 
		if (modifiers.isDynamic()){
			getWriter().append(SPACE).append("dynamic");
		}
		
		//Final 
		if (modifiers.isFinal() && !type.isEnum()){
			getWriter().append(SPACE).append(JsCoreKeywords.EXT_FINAL);
		}
		
		if (modifiers.isAbstract()){
			getWriter().append(SPACE).append(JsCoreKeywords.EXT_ABSTRACT);
		}

		
		
		//param types
		if (hasParams && type.isEmbededType()) {
			//name
			writeJsDocName(type.getSimpleName());
			writeParamTypes(type);
		}
		
//		writeNewline();
//		if (type.isEmbededType()) {
//			writeIndent();
//		}
	}

	public void writeParamTypes(final IJstType type) {
		List<JstParamType> params = type.getParamTypes();
		int len = params.size();
		if (len>0){
			getWriter().append("<");
			for (int i=0; i<len; i++) {
				JstParamType ptype = params.get(i);
				getWriter().append(ptype.getSimpleName());
				writeParamBounds(ptype.getBounds());
				if (i<len-1) {
					getWriter().append(",");
				}
			}
			getWriter().append(">");
		}
	}
	
	private void writeParamBounds(List<IJstType> bounds) {
		if (bounds!=null && bounds.size()>0) {
			int len = bounds.size();
			getWriter().append(" extends ");
			for (int i=0; i<len; i++) {
				IJstType bound = bounds.get(i);
				getWriter().append(bound.getSimpleName());
				if (bound.getParamNames().size()>0) {
					boolean first = true;
					getWriter().append("<");
					for (String pName : bound.getParamNames()) {
						if (!first) {
							getWriter().append(COMMA);
						}
						getWriter().append(pName);
						first = false;
					}
					getWriter().append(">");
				}
				if (i<len-1) {
					getWriter().append(",");
				}
			}
		}
	}
	

	public void writeJsDoc(IJstMethod mtd, String access, String name) {
		startWriteJsDoc();
		
		// Final 
		if (mtd.isFinal()){
			getWriter().append(SPACE).append(JsCoreKeywords.EXT_FINAL);
		}
		
		// Access
		writeJsDocAccessScope(access);
		
		if (mtd.getModifiers().isAbstract()){
			getWriter().append(SPACE).append(JsCoreKeywords.EXT_ABSTRACT);
		}
		
		writeVjetDocRawMtd(mtd, name);

	}

	public void writeVjetDocRawMtd(IJstMethod mtd, String name) {
		IJstType root = getType(mtd);
		if (mtd.isOType() && mtd instanceof JstMethod) {		
			JstMethod meth = (JstMethod)mtd;
			getWriter().append(SPACE).append(meth.getOType().getName());
			return;
		}
		// Params
		appendParameters(mtd);
		
		
		// Return type
		IJstType rtnType = mtd.getRtnType();
		if (rtnType != null && !mtd.isConstructor()){
			getWriter().append(SPACE);
			//factory function
			if(mtd.isTypeFactoryEnabled()){
				getWriter().append("^");
			}
		
			
			writeJsDocReturnType(getName(rtnType,root));
			
			if (rtnType instanceof JstTypeWithArgs){
				appendArguments((JstTypeWithArgs)rtnType);
			}
		}
		
		getWriter().append(SPACE);
		if(mtd.isFuncArgMetaExtensionEnabled()){
			getWriter().append("^");
		}
		// name
		//writeJsDocName(name);
		getWriter().append(name);
		
		// parameters
		writeJsDocParamType(mtd,root);

		m_methodsWritten.add(mtd);
	}

	public void writeJsDoc(IJstMethod mtd, String access) {
		writeJsDoc(mtd, access, mtd.getName().getName());
	}
	
	public void writeJsDoc(IJstMethod mtd) {
		JstModifiers modifiers = mtd.getModifiers();
		String access = modifiers.getAccessScope();
		if(isHelperMethod(mtd)){
			if(!modifiers.isFinal() && (modifiers.isPublic() || modifiers.isProtected() )) {
			    access = JsCoreKeywords.EXT_PROTECTED;
			} else {
			    access = JsCoreKeywords.EXT_PRIVATE;
			}
		}
		writeJsDoc(mtd, access);
	}
	
	public void writeJsDoc(IJstProperty pty) {
		if (pty instanceof ISynthesized) {
			return;
		}
		getWriter().append(SPACE);
		startWriteJsDoc(false);
		IJstType root = getType(pty);
		
		writeJsDocAccessScope(pty.getModifiers().getAccessScope());
		if (pty.isFinal()){
			getWriter().append(SPACE).append(JsCoreKeywords.EXT_FINAL);
		}
		writeJsDocName(getName(pty.getType(),root));
		if (pty.getType() instanceof JstTypeWithArgs){
			appendArguments((JstTypeWithArgs)pty.getType());
			writeJsDocName(pty.getName().getName());
		}
		
	}
	
	public void writeJsDocForArg(IExpr expr) {
		if (expr instanceof CastExpr) {
			getWriter().append(VjoKeywords.ANNO_MULTI_START_NEXT).append(
					">").append(VjoKeywords.ANNO_MULTI_END);
		}
	}
	
	public void writeJsDoc(AssignExpr assign, boolean forceType) {
		IExpr expr = assign.getExpr();
		if (expr instanceof CastExpr) {
			CastExpr cast = (CastExpr)expr;
			getWriter().append(SPACE);
			startWriteJsDoc(false);
			getWriter().append("<");
			//if (forceType) {
				writeType(cast.getResultType());
			//}
		} else if (forceType) {
			getWriter().append(SPACE);
			startWriteJsDoc(false);
//			if (expr!=null && expr.getResultType() instanceof JstArray) {
//				writeType(expr.getResultType());
//			} else {
				writeType(assign.getLHS().getType());
//			}
		}
	}
	
	public void writeJsDoc(IExpr expr, boolean forceType) {
		if (expr == null) {
			return;
		}
		if (expr instanceof CastExpr) {
			CastExpr cast = (CastExpr)expr;
			getWriter().append(SPACE);
			startWriteJsDoc(false);
			getWriter().append("<");
			//if (forceType) {
				writeType(cast.getResultType());
			//}
		} else if (forceType) {
			getWriter().append(SPACE);
			startWriteJsDoc(false);
			if (expr.getResultType() instanceof JstArray) {
				writeType(expr.getResultType());
			} 
		}
	}
	
	private void writeType(IJstType type) {
		if (type!=null) {
//			getWriter().append(type.getSimpleName());
			String typeName = DataTypeHelper.getTypeName(type.getName()); //Use alias if available
			if (typeName.equals(type.getName())) {
				if (isInnerType(type)) {
					typeName = getSimpleNameForInner(type);
				}
				else {
					typeName = type.getSimpleName();
				}
			}
			getWriter().append(typeName);
		}
	}
	public void writeJsDoc(JstVars vars) {
		JstInitializer inits = vars.getInitializer();
		if (inits != null) {
			List<AssignExpr> assigns = inits.getAssignments();
			int size = assigns.size();
			if (size > 0) {
				AssignExpr assignExpr = assigns.get(size - 1);
				if (assignExpr.getExpr() instanceof CastExpr) {
					writeJsDoc(assignExpr,true);
					return;
				}
			}
		}

		//Only add this comment for non-Object and non-inferred type
		IJstType varType = vars.getType();
		if (varType != null 
			&& !(varType instanceof IInferred)
			&& !"Object".equals(varType.getSimpleName())) {
			getWriter().append(SPACE);
			startWriteJsDoc(false);
			writeType(varType);
		}
	}
	
	protected void writeJsDocName(String name) {
		getWriter().append(SPACE).append(name);
		
	}

	protected void startWriteJsDoc(){
		startWriteJsDoc(true);
	}
	
	private void startWriteJsDoc(boolean forward){
		if (forward) {
			writeNewline();
			writeIndent();
			getWriter().append(VjoKeywords.ANNO_START_NEXT);
		} else {
			getWriter().append(VjoKeywords.ANNO_START_PREVIOUS);
		}
	}
	
	protected void writeJsDocParamType(IJstMethod mtd, IJstType root){
		final List<JstArg> args = mtd.getArgs();
		getWriter().append('(');
		int index = 0;
		for (JstArg arg: args){
//			if(arg != null && arg.getTypes().size()==0){
//				continue;
//			}
			if (arg == null || arg.getType() == null){
				continue;
			}
			if (index  > 0) {
				getWriter().append(',');
			}
			if (arg.isFinal()) {
				getWriter().append("final").append(" ");
			}
			if (arg.getTypeRef() instanceof IJstRefType) {
				getWriter().append(TYPE_REF_PREFIX);
			}
			if(arg.getTypes().size()==1){
				getWriter().append(getName(arg.getType(),root));
			}else{
				// support {T1|T2|T3}
				getWriter().append('{');
				for(int i=0; i<arg.getTypes().size();i++){
					IJstType type = arg.getTypes().get(i);
					getWriter().append(getName(type,root));
					if(i!=arg.getTypes().size()-1){
						getWriter().append('|');
					}
				}
				getWriter().append('}');
			}
		
			if (arg.getType() instanceof JstTypeWithArgs){
				appendArguments((JstTypeWithArgs)arg.getType());
			}
			if (arg.isVariable()){
				getWriter().append("...");
			} else if (arg.isOptional()) {
				getWriter().append("?");
			}
			
			getWriter().append(SPACE);
			getWriter().append(arg.getName());
			index++;
		}
		getWriter().append(')');
	}
	
	protected void writeJsDocReturnType(final String type){
		
		
		if ("Void".equals(type)) {
			getWriter().append("void");
		}
		else {
			String name = type;
			boolean isA = false;
			if (type.endsWith("[]")) {
				name = name.substring(0, type.indexOf("[]"));
				isA = true;
			}
			String s = DataTypeHelper.getTypeName(name);
			getWriter().append(s + ((isA)?"[]":"") );
		}
	}
		
	protected void writeJsDocAccessScope(final String scope){
		if (scope != null && scope.length() > 0){
			getWriter().append(SPACE);
		}
		getWriter().append(scope);
	}

	//
	// Private
	//
	private void appendArguments(JstTypeWithArgs pType){
		if (!pType.getArgTypes().isEmpty()){
			getWriter().append(pType.getArgsDecoration());
		}
	}
	
	private void appendParameters(IJstMethod mtd){
		if (!mtd.getParamTypes().isEmpty()){
			getWriter().append(SPACE).append(((JstMethod)mtd).getParamsDecoration());
		}
	}
	
	private String getName(IJstType type, IJstType root) {
		String name = type.getSimpleName();
		
//		String nativeName = VjoGenHelper.getNativeTypeName(type);
//		if (nativeName != null) {
//			return nativeName;
//		}

		boolean isN = isNeed(type.getName(),root);
		if ((!"vjo.Object".equals(type.getName())) 
				&& (getCtx().getConfig().getFilters().isJavaPrimitiveOrWrapper(name) || isN)) {
			if (isN && type != null) {
				//Check to see if it needs to use full name
				IJstType imT = root.getImport(type.getName());
				if (imT == null) {
					imT = root.getInactiveImport(type.getName());
				}
				if (imT != null && type.getName().equals(imT.getAlias())) {
					name = type.getName();
				}
			}
			if (isInnerType(type)) {
				//Get name for inner type
				return getSimpleNameForInner(type);
			}
			if(!isN && type.getPackage()!=null && !type.getName().startsWith("java.lang")&& !type.getName().startsWith("org.ebayopensource.dsf.jsnative.global")){
				return type.getName();
			}
			return name;
		}
		if (isInnerType(type)) {
			//Get name for inner type
			return getSimpleNameForInner(type);
		}
		if ( type.getPackage() !=null) {
			String otype = getOTypeContainer(root, type.getPackage().getName());
			if (otype!=null){
				return otype + DOT + name;
			}
			if(type instanceof JstFuncType){
				getWriter().append("(");
				IJstMethod mtd = ((JstFuncType)type).getFunction();
				writeVjetDocRawMtd(mtd, mtd.getName().getName());
				getWriter().append(")");
				return "";
			}
			
		}
		return DataTypeHelper.getTypeName(type.getName());
	}
	
	private boolean isInnerType(IJstType type) {
		if (type != null && type.getOuterType() != null && type != type.getRootType()) {
			return true;
		}
		
		return false;
	}
	
	private String getSimpleNameForInner(IJstType type) {
		String s = type.getSimpleName();
		IJstType root = type.getRootType();
		IJstType tmp = type;
		
		while (!tmp.equals(root) && tmp.getOuterType() != null) {
			tmp = tmp.getOuterType();
			if (s.length() > 0) {
				s = DOT + s;
			}
			s = tmp.getSimpleName() + s;
		}
		return s;
	}

	private String getOTypeContainer(IJstType type, String name) {
		if(type==null || type.getInactiveImports()==null){
			return null;
		}
		for (IJstType need : type.getInactiveImports()) {
			if (name.equals(need.getName())) {
				return need.getSimpleName();
			}
		}
		return null;
	}
	private boolean isNeed(String name, IJstType root) {
		
		if (null == root)
			return false;
		
		List<? extends IJstType> list = root.getImports();

		if (compare(name, root)) {
			return true;
		}
		for (IJstType type : list) {
			if (compare(name, type)) {
				return true;
			}
		}
		list = root.getInactiveImports();
		for (IJstType type : list) {
			if (compare(name, type)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean compare(String name, IJstType type) {
		String typeName = type.getName();
		return (name.equals(typeName) || name.startsWith(typeName+DOT) || name.startsWith(typeName+"["));
	}
	
	private IJstType getType(IJstNode mtd) {
		IJstNode type = mtd;
		while (type.getParentNode()!=null) {
			type = type.getParentNode();
		}
		if (type instanceof IJstType) {
			return (IJstType)type;
		}
		return null;
	}
	
	private boolean isWritten(IJstMethod target){
		for(IJstMethod method: m_methodsWritten){
			if(isEqual(method,target)){
				return true;
			}
		}
		return false;
	}

	private boolean isEqual(IJstMethod source, IJstMethod target){
		List<JstArg> sourceParams = source.getArgs();
		List<JstArg> targetParams = target.getArgs();

		int sourceParamsCount = sourceParams.size();
		int targetParamsCount = targetParams.size();

		if(sourceParamsCount==targetParamsCount){
			for(int i=0;i<sourceParamsCount;i++){
				JstArg sourceArg = sourceParams.get(i);
				JstArg targetArg = targetParams.get(i);
				if(sourceArg==null && targetArg!=null){
					return false;
				}else if(sourceArg!=null && targetArg==null){
					return false;
				}else if(!(sourceArg.getType().equals(targetArg.getType()))){
					return false;
				}
			}
		}
		if(!source.getName().getName().equals(target.getName().getName())){
			return false;
		}else if(sourceParamsCount!=targetParamsCount){
			return false;
		}else if(source instanceof JstMethod && target instanceof JstConstructor){
			return false;
		}else if(source instanceof JstConstructor && target instanceof JstMethod){
			return false;
		}else if(!source.getModifiers().getAccessScope().equals(target.getModifiers().getAccessScope())){
			return false;
		}else if(source.getRtnType()!=null && target.getRtnType() != null && !source.getRtnType().equals(target.getRtnType())){
			return false;
		}
		return true;
	}

	private boolean isHelperMethod(IJstMethod mtd) {
		if(mtd.getName()==null || mtd.getOriginalName()==null){
			return false;
		}
		return !mtd.getName().getName().equals(mtd.getOriginalName());
	}
}
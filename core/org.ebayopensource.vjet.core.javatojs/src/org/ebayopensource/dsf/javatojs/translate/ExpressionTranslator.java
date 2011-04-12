/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import org.ebayopensource.dsf.javatojs.trace.TranslateMsgId;
import org.ebayopensource.dsf.javatojs.translate.config.MethodKey;
import org.ebayopensource.dsf.javatojs.translate.util.AutoBoxer;
import org.ebayopensource.dsf.jsnative.anno.JsNativeMeta;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstArray;
import org.ebayopensource.dsf.jst.declaration.JstFactory;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstParamType;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstTypeReference;
import org.ebayopensource.dsf.jst.declaration.JstTypeWithArgs;
import org.ebayopensource.dsf.jst.declaration.JstVar;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.declaration.VarTable;
import org.ebayopensource.dsf.jst.expr.ArithExpr;
import org.ebayopensource.dsf.jst.expr.ArrayAccessExpr;
import org.ebayopensource.dsf.jst.expr.ArrayCreationExpr;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.BoolExpr;
import org.ebayopensource.dsf.jst.expr.CastExpr;
import org.ebayopensource.dsf.jst.expr.ConditionalExpr;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.expr.InfixExpr;
import org.ebayopensource.dsf.jst.expr.JstArrayInitializer;
import org.ebayopensource.dsf.jst.expr.JstInitializer;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.expr.ObjCreationExpr;
import org.ebayopensource.dsf.jst.expr.ParenthesizedExpr;
import org.ebayopensource.dsf.jst.expr.PostfixExpr;
import org.ebayopensource.dsf.jst.expr.PrefixExpr;
import org.ebayopensource.dsf.jst.expr.PtyGetter;
import org.ebayopensource.dsf.jst.expr.BoolExpr.Operator;
import org.ebayopensource.dsf.jst.reserved.JsCoreKeywords;
import org.ebayopensource.dsf.jst.stmt.ExprStmt;
import org.ebayopensource.dsf.jst.stmt.JstStmt;
import org.ebayopensource.dsf.jst.stmt.PtySetter;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.term.JstLiteral;
import org.ebayopensource.dsf.jst.term.SimpleLiteral;
import org.ebayopensource.dsf.jst.token.IBoolExpr;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.ILHS;
import org.ebayopensource.dsf.jst.token.ISimpleTerm;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.util.DataTypeHelper;
import org.ebayopensource.vjo.VjoTypes;
import org.ebayopensource.vjo.meta.VjoConvention;
import org.ebayopensource.vjo.meta.VjoKeywords;

public class ExpressionTranslator extends BaseTranslator {
	
	//
	// API
	//
	public IExpr processExpression(final Expression astExpr, final BaseJstNode jstNode){

		if (astExpr == null){
			return null;
		}
		
		IExpr jstExpr = null;
		
		// Literals
		if (astExpr instanceof BooleanLiteral
				|| astExpr instanceof NumberLiteral
				|| astExpr instanceof StringLiteral
				|| astExpr instanceof CharacterLiteral
				|| astExpr instanceof NullLiteral){
			jstExpr = toJstLiteral(astExpr, jstNode, true);
		}
		else if (astExpr instanceof TypeLiteral){
			Type ndType = ((TypeLiteral)astExpr).getType();
			if (ndType.isArrayType()) {
				getLogger().logError(TranslateMsgId.EXCLUDED_TYPE, "Array type translation not supported", this, astExpr, jstNode);
				return null;
			}
			IJstType type = getDataTypeTranslator().processType(ndType, jstNode);
			
			jstExpr = VjoTranslateHelper.processTypeLiteral(type, jstNode);
		}
		// Names 
		else if (astExpr instanceof SimpleName
				|| astExpr instanceof QualifiedName){
			
			jstExpr = toJstIdentifier(astExpr, jstNode, true);
		}
		// Cast 
		else if (astExpr instanceof CastExpression){
			CastExpression cExpr = (CastExpression)astExpr;
			Expression expr = cExpr.getExpression();
			IJstType type = getDataTypeTranslator().processType(cExpr.getType(), jstNode);
			jstExpr = processExpression(expr, jstNode);
			if (DataTypeHelper.isNumericPrimitiveType(type) 
				|| DataTypeHelper.isCharPrimitiveType(type)){
				jstExpr = new MtdInvocationExpr(VjoTypes.CAST.getName().getName(), 
						jstExpr, 
						new JstIdentifier("'" + type.getName() + "'"))
					.setQualifyExpr(VjoTranslateHelper.getStaticMemberQualifier(VjoTypes.VJO_JAVA_LANG_UTIL, jstNode));
				
				jstNode.getOwnerType().addImport(VjoTypes.VJO_JAVA_LANG_UTIL);
			} 
			else {
				jstExpr = new CastExpr(jstExpr, type);
			}
			
		}
		// Field Access
		else if (astExpr instanceof FieldAccess
				|| astExpr instanceof SuperFieldAccess){
			
			jstExpr = toFieldAccess(astExpr, jstNode, true);
		}
		// Array Access
		else if (astExpr instanceof ArrayAccess){
			jstExpr = toArrayAccess((ArrayAccess)astExpr, jstNode);
		}
		// Initializer
		else if (astExpr instanceof ArrayInitializer){
			jstExpr = toJstArrayInitializer(astExpr, jstNode);
		}
		// Assignments
		else if (astExpr instanceof Assignment){
			jstExpr = toAssignExpr((Assignment)astExpr, jstNode);
		}
		// Arithmetic Expr
		else if (astExpr instanceof InfixExpression 
				|| astExpr instanceof PrefixExpression
				|| astExpr instanceof PostfixExpression
				|| astExpr instanceof ParenthesizedExpression){

			jstExpr = toArithmeticExpr(astExpr, jstNode, true);
		}
		// Conditional Expr
		else if (astExpr instanceof ConditionalExpression){

			jstExpr = toConditionalExpr((ConditionalExpression)astExpr, jstNode);
		}
		// Method Invocation
		else if (astExpr instanceof MethodInvocation
				|| astExpr instanceof SuperMethodInvocation){ 
			jstExpr = processMtdInvocation(astExpr, jstNode);
		}
		// Object Creation
		else if (astExpr instanceof ClassInstanceCreation){
			ClassInstanceCreation cic = (ClassInstanceCreation)astExpr;
			IJstType objType = getDataTypeTranslator().processType(cic.getType(), jstNode);
			IExpr jstQualifier = processExpression(cic.getExpression(), jstNode);
			List<?> args = cic.arguments();
			List<IExpr> jstArgs = null;
			if (!args.isEmpty()){
				jstArgs = new ArrayList<IExpr>();
				for (Object a: args){
					if (a instanceof Expression){
						jstArgs.add(getExprTranslator().processExpression((Expression)a, jstNode));
					}
					else {
						getLogger().logUnhandledNode(this, (ASTNode)a, jstNode);
					}
				}
			}
			IExpr objCreationExpr = getCustomTranslator()
				.processInstanceCreation(cic, jstQualifier, objType, jstArgs, jstNode);
			if (objCreationExpr != null){
				jstExpr = objCreationExpr;
			}
			else {
				String typeName = cic.getType().toString();
				if (objType == null && jstQualifier != null && jstQualifier.getOwnerType() != null){
					objType = TranslateHelper.Type
						.getEmbededType(jstQualifier.getResultType(), typeName, true);
				}
				jstExpr = toObjCreation(
						cic.getType(),
						objType, 
						jstQualifier, 
						typeName, 
						cic.getExpression(), 
						cic.arguments(), 
						cic.getAnonymousClassDeclaration(), 
						jstNode);
			}
		}
		else if (astExpr instanceof ArrayCreation){
			jstExpr = toArrayCreation((ArrayCreation)astExpr, jstNode);
		}
		else if (astExpr instanceof InstanceofExpression){
			//jstExpr = toBoolExpr(astExpr, jstNode);
			//convert instanceof to Type.instanceOf
			jstExpr = toInstanceOfMethodExpr((InstanceofExpression)astExpr, jstNode);
		}
		else if (astExpr instanceof ThisExpression){
			ThisExpression expr = (ThisExpression)astExpr;
			Name name = expr.getQualifier();
			IJstType ownerType = jstNode.getOwnerType();
			String t = JsCoreKeywords.THIS;
			
			if (name!=null) {
//				JstIdentifier ident = getNameTranslator().processName(name, jstNode);
				IJstType outer = ownerType;
				String prefix = t;
				VjoConvention convention = getCtx().getConfig().getVjoConvention();
				while (outer!=null) {
					String typeName = outer.getSimpleName();
					if (typeName!=null && typeName.equals(name.toString())) {
						ownerType = outer;
						t = prefix;
						break;
					}
					
					if (outer.isAnonymous()) {
						IJstNode n = outer.getParentNode();
						while (!(n instanceof IJstType) && n!=null) {
							n = n.getParentNode();
						}
						if (n!=null) {
							prefix += convention.getParentInstancePrefix();
							outer = (IJstType)n;
						} else {
							break;
						}
						
					} else {
						prefix += convention.getOuterInstancePrefix();
						outer=outer.getOuterType();
					}
				}
//				ident.setType(ownerType);
//				ident.setJstBinding(ownerType);
				
			} 
			JstIdentifier identifier = new JstIdentifier(t);
			identifier.setType(ownerType);
			identifier.setJstBinding(ownerType);
			return identifier;
		}
		else {
			getLogger().logUnhandledNode(this, astExpr, jstNode);
			return null;
		}
		
		if (jstExpr != null && jstExpr instanceof BaseJstNode){
			((BaseJstNode)jstExpr).setParent(jstNode);
		}
		
		return jstExpr;
	}

	//
	// Package protected
	//
	ISimpleTerm toSimpleTerm(final Expression astExpr, final BaseJstNode jstNode){
		
		assert astExpr != null : "astExpr cannot be null";
		
		ISimpleTerm jstLiteral = toJstLiteral(astExpr, jstNode, false);
		if (jstLiteral != null){
			return jstLiteral;
		}
		
		JstIdentifier jstIdentifier = toJstIdentifier(astExpr, jstNode, false);
		if (jstIdentifier != null){
			return jstIdentifier;
		}

		return null;
	}
	
	List<AssignExpr> toAssignExprs(final VariableDeclarationExpression astExpr, final JstStmt jstStmt){
		
		VariableDeclarationExpression vdExpr = (VariableDeclarationExpression)astExpr;
		IJstType type = getDataTypeTranslator().processType(vdExpr.getType(), jstStmt);
		if (type == null){
			getLogger().logError(TranslateMsgId.NULL_RESULT, "failed to translate", this, vdExpr.getType(), jstStmt);
		}
		List<AssignExpr> list = new ArrayList<AssignExpr>();
		for (Object o: vdExpr.fragments()){
			if (o instanceof VariableDeclarationFragment){
				VariableDeclarationFragment f = (VariableDeclarationFragment)o;
				String name = f.getName().toString();
				Expression e = f.getInitializer();
				list.add(new AssignExpr(new JstVar(type, name), getExprTranslator().processExpression(e, jstStmt)));
			}
			else {
				getLogger().logUnhandledNode(this, (ASTNode)o, jstStmt);
			}
		}
		
		return list;
	}
	
	AssignExpr toAssignExpr(final Assignment astExpr, final BaseJstNode jstNode){
		
		Assignment a = (Assignment)astExpr;
		Expression left = a.getLeftHandSide();
		Expression right = a.getRightHandSide();
		String op = a.getOperator().toString();
		return new AssignExpr(
				toLHS(left, jstNode),
				processExpression(right, jstNode),
				op);
	}
	
	ArithExpr toArithmeticExpr(final Expression astExpr, final BaseJstNode jstNode, boolean report){
		
		if (astExpr instanceof InfixExpression){
			InfixExpression infix = (InfixExpression)astExpr;
			Expression le = infix.getLeftOperand();
			Expression re = infix.getRightOperand();
			InfixExpr.Operator op = InfixExpr.Operator.toOperator(TranslateHelper.getOperatorStringForJs(infix.getOperator().toString()));
			IExpr rightExpress = processExpression(re, jstNode);
//			if (re instanceof PrefixExpression) {
//				rightExpress = new ParenthesizedExpr(rightExpress);
//			}			
			InfixExpr expr = new InfixExpr(
					processExpression(le, jstNode), 
					rightExpress, 
					op);
			List exOprds = infix.extendedOperands();
			if (exOprds != null){
				for (Object o: exOprds){
					if (o instanceof Expression){
						expr = new InfixExpr(
								expr, 
								processExpression((Expression)o, jstNode), 
								op);
					}
					else {
						getLogger().logUnhandledNode(this, (ASTNode)o, jstNode);
					}
				}
			}
			return expr;
		}
		else if (astExpr instanceof PrefixExpression){
			PrefixExpression prefix = (PrefixExpression)astExpr;
			Expression e = prefix.getOperand();
			return new PrefixExpr(
					processExpression(e, jstNode), 
					PrefixExpr.Operator.toOperator(prefix.getOperator().toString()));
		}
		else if (astExpr instanceof PostfixExpression){
			PostfixExpression postfix = (PostfixExpression)astExpr;
			Expression e = postfix.getOperand();
			return new PostfixExpr(
					processExpression(e, jstNode), 
					PostfixExpr.Operator.toOperator(postfix.getOperator().toString()));
		}
		else if (astExpr instanceof ParenthesizedExpression){
			ParenthesizedExpression parenthefix = (ParenthesizedExpression)astExpr;
			Expression e = parenthefix.getExpression();
			return new ParenthesizedExpr(processExpression(e, jstNode));
		}
		else if (report) {
			getLogger().logUnhandledNode(this, (ASTNode)astExpr, jstNode);
		}

		return null;
	}
	
	IBoolExpr toBoolExpr(final Expression astExpr, final BaseJstNode jstNode){
		Expression e = astExpr;
		IBoolExpr boolExpr;
		int parenthesisCount = 0;
		while (e instanceof ParenthesizedExpression){
			e = ((ParenthesizedExpression)e).getExpression();
			parenthesisCount++;
		}
		if (e instanceof InfixExpression){
			InfixExpression infix = (InfixExpression)e;
			IExpr left = getExprTranslator().processExpression(infix.getLeftOperand(), jstNode);
			IExpr right = getExprTranslator().processExpression(infix.getRightOperand(), jstNode);
			boolExpr = new BoolExpr(left, right, TranslateHelper.getOperatorStringForJs(infix.getOperator().toString()));
		}
		else if (e instanceof InstanceofExpression){
			getLogger().logWarning(TranslateMsgId.INCOMPLETE_NODE, 
				e.getClass().getSimpleName() + " translated to " + JsCoreKeywords.INSTANCEOF, this, e, jstNode);
			InstanceofExpression infix = (InstanceofExpression)e;
			IExpr left = getExprTranslator().processExpression(infix.getLeftOperand(), jstNode);
			IJstType rightType = getDataTypeTranslator().processType(infix.getRightOperand(), jstNode);
			if (rightType == null){
				return null;
			}
			IExpr right;
			while (rightType instanceof JstTypeWithArgs){
				rightType = ((JstTypeWithArgs)rightType).getType();
			}
			if (rightType instanceof JstArray){
				right = new JstIdentifier("");
			}else {
				right = TranslateHelper.Type.createIdentifier(rightType, jstNode);
			}
			boolExpr = new BoolExpr(left, right, BoolExpr.Operator.INSTANCE_OF);
		}
		else {
			boolExpr = new BoolExpr(getExprTranslator().processExpression(e, jstNode));
		}
		for (int i = 0; i<parenthesisCount; i++){
			boolExpr = new ParenthesizedExpr(boolExpr);
		}
		return boolExpr;
	}
	
	IExpr toInstanceOfMethodExpr(final InstanceofExpression astExpr, final BaseJstNode jstNode){
		
		IExpr left = getExprTranslator().processExpression(astExpr.getLeftOperand(), jstNode);
		IJstType rightType = getDataTypeTranslator().processType(astExpr.getRightOperand(), jstNode);
		if (rightType == null){
			return null;
		}
		IExpr right;
		while (rightType instanceof JstTypeWithArgs){
			rightType = ((JstTypeWithArgs)rightType).getType();
		}
		String rname = DataTypeHelper.getTypeName(rightType.getName());
		if (rightType instanceof JstArray){
			right = new JstIdentifier("");
		}else {
			if (rightType.getModifiers().isStatic()) {
				right = new JstIdentifier(rname);
			} else {
				right = new JstIdentifier(VjoConvention.getType(rname));
			}
			((JstIdentifier)right).setJstBinding(rightType);
		}
		
		if (JsNativeMeta.isJsNativeType(rname)) {
			//Native types would not use method for instanceof, it uses native stype (o intanceof Type)
			BoolExpr exp = new BoolExpr(left, right, Operator.INSTANCE_OF);
			return exp;
		} else {
			//Ex: Type.instanceOf(o)
			MtdInvocationExpr exp = new MtdInvocationExpr(VjoKeywords.IS_INSTANCE);
			exp.setQualifyExpr(right);
			exp.addArg(left);
			return exp;
		}
	}
	
	IStmt toStmt(final Expression astExpr, final BaseJstNode jstNode){
		
		if (astExpr == null){
			return null;
		}
		
		// Assignments
		if (astExpr instanceof Assignment){
			return toAssignExpr((Assignment)astExpr, jstNode);
		}
		
		// Arithmetic Expr
		final ArithExpr expr = toArithmeticExpr(astExpr, jstNode, false);
		if (expr != null){
			return expr;
		}
		
		// Method Invocation
		final IExpr mtdCall = processMtdInvocation(astExpr, jstNode);
		if (mtdCall == null){
			return null;
		}
		
		if (mtdCall instanceof IStmt){
			return (IStmt)mtdCall;
		}
		
		return new ExprStmt(mtdCall);
	}
	
	ObjCreationExpr toObjCreation(final Type astType, final IJstType objType, final IExpr jstExpr, String typeName, final Expression expr, 
			final List args, final AnonymousClassDeclaration anonymous, final BaseJstNode jstNode){
		
		if (objType == null){
			getLogger().logError(TranslateMsgId.NULL_RESULT, "failed to translate type", this, anonymous, jstNode);
			return null;
		}
		JstIdentifier identifier = null;
		//Handle native type mapping
		if (DataTypeHelper.isJavaMappedToNative(objType.getName())) {
			identifier = new JstIdentifier(DataTypeHelper.getTypeName(objType.getName()));
		} else if (getCtx().getConfig().getPackageMapping().mapTo(typeName).equals(objType.getName())){
			identifier = new JstIdentifier(objType.getName());
		}
		else if (jstExpr != null){
			identifier = VjoTranslateHelper.getInstanceTypeQualifier(jstExpr, objType, jstNode);
		}
		else if ((objType.getModifiers().isStatic() || objType.getOuterType() != null && objType.getOuterType().isInterface())
				|| jstNode.getOwnerType().hasImport(objType.getSimpleName())){
			IJstType type = objType;
			if (type instanceof JstTypeWithArgs){
				type = ((JstTypeWithArgs)type).getType();
			}
			JstIdentifier jstQualifier = VjoTranslateHelper.getStaticTypeQualifier(type, jstNode);
			if (jstQualifier == null) {
				identifier = new JstIdentifier(type.getSimpleName());
			}
			else {
				String name = jstQualifier.getName();
				if (type != jstNode.getOwnerType()){
					name += "." + type.getSimpleName();
				}
				identifier = new JstIdentifier(name);
			}
		}
		else {
			identifier = VjoTranslateHelper.getInstanceTypeQualifier(null, objType, jstNode);
		}

		List<IExpr> jstArgs = new ArrayList<IExpr>();
		for (Object a: args){
			if (a instanceof Expression){
				jstArgs.add(getExprTranslator().processExpression((Expression)a, jstNode));
			}
			else {
				getLogger().logUnhandledNode(this, (ASTNode)a, jstNode);
			}
		}
		
		IJstMethod constructor = TranslateHelper.Method.getConstructor(objType, jstArgs);
		TranslateHelper.Method.validateMethodReference(constructor, astType, jstNode, this);
		
		identifier.setJstBinding(TranslateHelper.Method.getConstructor(objType, jstArgs));

		MtdInvocationExpr mtdCall = new MtdInvocationExpr(identifier);
		if (!jstArgs.isEmpty()){
			mtdCall.setArgs(jstArgs);
		}
		mtdCall.setResultType(objType);

		ObjCreationExpr objCreationExpr;
		if (anonymous != null){
			JstType anonymousType = getCtx().getTranslateInfo(jstNode.getRootType()).getJstAnonymousType(anonymous);
			if (anonymousType == null){
				getLogger().logError(TranslateMsgId.MISSING_DATA_IN_TRANSLATE_INFO, 
					"anonymous type not found", this, anonymous, jstNode);
				return null;
			}
			objCreationExpr = new ObjCreationExpr(mtdCall, anonymousType);
			objCreationExpr.setParent(jstNode);
			if (!objType.isInterface()){
				JstTypeReference jstTypeRef = new JstTypeReference(objType);
				jstTypeRef.setSource(new JstSource(new AstBinding(astType)));
				anonymousType.addExtend(jstTypeRef);
			}
			getCtx().getTranslateInfo(anonymousType)
				.addMode(getCtx().getTranslateInfo(jstNode.getOwnerType()).getMode());
			getTypeTranslator().processBody(anonymous.bodyDeclarations(), anonymousType);
//			if (args != null && args.size() > 0 && anonymousType.getConstructor() == null){
//				JstMethod constructor = new JstMethod().setIsConstructor(true);
//				constructor.setName(VjoKeywords.CONSTRUCTS);
//				MtdInvocationExpr baseCall = new MtdInvocationExpr(getCtx().getConfig().getVjoConvention().getBasePrefix());
//				String arg;
//				for (int i=0; i<args.size(); i++){
//					arg = "arg" + String.valueOf(i);
//					JstType argT = null;
//					constructor.addArg(new JstArg(argT, arg, false));
//					baseCall.addArg(new JstIdentifier(arg));
//				}
//				constructor.addStmt(baseCall);
//				anonymousType.setConstructor(constructor);
//			}
			
		}
		else {
			objCreationExpr = new ObjCreationExpr(mtdCall);
			objCreationExpr.setParent(jstNode);
		}
		
		if (expr == null || expr instanceof ThisExpression){
			return objCreationExpr;
		}
		
		if (expr instanceof ClassInstanceCreation){
			objCreationExpr.setExpression(new ParenthesizedExpr(jstExpr));
		}
		else {
			objCreationExpr.setExpression(jstExpr);
		}
		
		return objCreationExpr;
	}
	
	JstVars toJstVars(Type astType, List fragments, BaseJstNode jstNode){
		IJstType jstType = getDataTypeTranslator().processType(astType, jstNode);
		if (jstType == null){
			getLogger().logError(TranslateMsgId.NULL_RESULT, "failed to translate", this, astType, jstNode);
		}
		IJstType varType = jstType;
		VarTable varTable = TranslateHelper.getVarTable(jstNode);
		JstInitializer jstInitializers = new JstInitializer();
		JstIdentifier identifier;
		for (Object o: fragments){
			if (o instanceof VariableDeclarationFragment){
				VariableDeclarationFragment f = (VariableDeclarationFragment)o;
				Expression e = f.getInitializer();
				String name = getNameTranslator().processVarName(f.getName(), jstNode);
				/*if (e instanceof ArrayInitializer && !(varType instanceof JstArray)){
					varType = JstFactory.getInstance().createJstArrayType(jstType, true);
				}*/

				//Note: This is for the case where source is like: Object a[][] = {{1, "a"}};
				// 	AST provides dimensions info as metadata when declared "Object a[][]" instead of "Object[][] a"
				if (f.getExtraDimensions() > 0) {
					int dims = f.getExtraDimensions();
					varType = jstType;
					while (dims > 0) {
						varType = JstFactory.getInstance().createJstArrayType(varType, true);
						dims--;
					}
				}
				varTable.addVarType(name, varType);
				identifier = new JstIdentifier(name);
				identifier.setType(varType);
				identifier.setJstBinding(varType);
				jstInitializers.addAssignment(identifier, getExprTranslator().processExpression(e, jstNode));
			}
//			else if (o instanceof Assignment){
//				jstVars.add(new JstInitializer(null, getExprTranslator().toAssignExpr((Assignment)o, jstNode)));
//			}
			else {
				getLogger().logUnhandledNode(this, (ASTNode)o, jstNode);
			}
		}
		return new JstVars(varType, jstInitializers);
	}
	
	//
	// Private
	//
	private JstIdentifier toJstIdentifier(final Expression astExpr, BaseJstNode jstNode, boolean report){
		JstIdentifier identifier = null;
		if (astExpr instanceof Name){
			identifier = getNameTranslator().processName((Name)astExpr, false, jstNode);
		}
		else if (report){
			getLogger().logUnhandledNode(this, (ASTNode)astExpr, jstNode);
		}

//		if (identifier != null){
//			JstIdentifier qualifier = identifier.getQualifier();
//			String newName = TranslateHelper.Expression.rename(qualifier, jstNode);
//			if (newName != null){
//				qualifier.setName(newName);
//			}
//			else if (TranslateHelper.Expression.removeQualifier(qualifier, jstNode)){
//				identifier.setQualifier(null);
//			}
//		}

		return identifier;
	}
	
	private FieldAccessExpr toFieldAccess(final Expression astExpr, BaseJstNode jstNode, boolean report){
		
		if (astExpr instanceof FieldAccess){
			FieldAccess f = (FieldAccess)astExpr;
			Expression expr = f.getExpression();
			if (expr == null || expr instanceof ThisExpression){
				JstIdentifier name = getNameTranslator().processName(f.getName(), false, true, null, false, jstNode);
				return new FieldAccessExpr(name);	
			}
			else {
				IExpr optionalExpr = processExpression(f.getExpression(), jstNode);
				JstIdentifier ident = getNameTranslator().processName(f.getName(), false, false, optionalExpr, false, jstNode);
				IJstNode binding = ident.getJstBinding();
				if (optionalExpr instanceof ObjCreationExpr && binding != null
						&& binding instanceof IJstProperty
						&& ((IJstProperty) binding).isStatic()) {
					//if static property, ignore object creation expression
					//does omitting construction change the behavior?
					return new FieldAccessExpr(ident, null);
				} else {
					return new FieldAccessExpr(ident, optionalExpr);
				}
					
			}
		}
		else if (astExpr instanceof SuperFieldAccess){
			SuperFieldAccess f = (SuperFieldAccess)astExpr;
			return new FieldAccessExpr(
				getNameTranslator().processName(f.getName(), true, false, null, false, jstNode.getOwnerType()));		
		}
		else if (report){
			getLogger().logUnhandledNode(this, (ASTNode)astExpr, jstNode);
		}

		return null;
	}
	
	private ArrayAccessExpr toArrayAccess(final ArrayAccess astExpr, BaseJstNode jstNode){
		return new ArrayAccessExpr(
				processExpression(astExpr.getArray(), jstNode), 
				processExpression(astExpr.getIndex(), jstNode));
	}
	
	private JstArrayInitializer toJstArrayInitializer(final Expression astExpr, BaseJstNode jstNode){
		JstArrayInitializer jai = new JstArrayInitializer();
		ArrayInitializer ai = (ArrayInitializer)astExpr;
		List astExprs = ai.expressions();
		if (astExprs != null){
			for (Object o: astExprs){
				if (o instanceof Expression){
					jai.add(processExpression((Expression)o, jstNode));
				}
				else {
					getLogger().logUnhandledNode(this, (ASTNode)o, jstNode);
				}
			}
		}
		return jai;
	}
	
	private ConditionalExpr toConditionalExpr(final ConditionalExpression astExpr, final BaseJstNode jstNode){

		IBoolExpr boolExpr = toBoolExpr(astExpr.getExpression(), jstNode);
		IExpr thenExpr = processExpression(astExpr.getThenExpression(), jstNode);
		IExpr elseExpr = processExpression(astExpr.getElseExpression(), jstNode);
		return new ConditionalExpr(boolExpr, thenExpr,elseExpr);
	}
	
	private IExpr processMtdInvocation(final Expression astExpr, final BaseJstNode jstNode){

		Name name = null;
		Expression optionalExpr = null;
		List args = null;
		boolean isSuper = false;
		
		if (astExpr instanceof MethodInvocation){
			MethodInvocation astMtdCall = (MethodInvocation)astExpr;
			name = astMtdCall.getName();
			optionalExpr = astMtdCall.getExpression();
			args = astMtdCall.arguments();			
		}
		else if (astExpr instanceof SuperMethodInvocation){
			SuperMethodInvocation astMtdCall = (SuperMethodInvocation)astExpr;
			name = astMtdCall.getName();
			args = astMtdCall.arguments();
			isSuper = true;
		}
		else if (astExpr instanceof ClassInstanceCreation){
			ClassInstanceCreation cic = (ClassInstanceCreation)astExpr;
			return processExpression(cic, jstNode);
		}
		else {
			getLogger().logUnhandledNode(this, (ASTNode)astExpr, jstNode);
			return null;
		}
		
		List<IExpr> jstArgs = new ArrayList<IExpr>();
		for (Object a: args){
			if (a instanceof Name){
				jstArgs.add(getNameTranslator().processName((Name)a, false, jstNode));
			}
			else if (a instanceof Expression){
				jstArgs.add(getExprTranslator().processExpression((Expression)a, jstNode));
			}
			else {
				getLogger().logUnhandledNode(this, (ASTNode)a, jstNode);
			}
		}
		
		JstIdentifier identifier = null;
		IExpr jstOptionalExpr = null;
		if (optionalExpr == null){
			identifier = getNameTranslator().processMtdName(name, jstArgs, isSuper, true, null, jstNode);
		}
		else if (optionalExpr instanceof ThisExpression){
			jstOptionalExpr = processExpression((ThisExpression)optionalExpr, jstNode);
			boolean isThis = jstOptionalExpr == null;
			identifier = getNameTranslator().processMtdName(name, jstArgs, isSuper, isThis, jstOptionalExpr, jstNode);
		}
		else {
			jstOptionalExpr = getExprTranslator().processExpression(optionalExpr, jstNode);
			if (jstOptionalExpr != null && jstOptionalExpr.getResultType() != null){
				identifier = getNameTranslator().processMtdName(name, jstArgs, isSuper, false, jstOptionalExpr, jstNode);
			}
			else {
				// Error case but still trying
				identifier = new JstIdentifier(name.toString());
			}
		}
		
		if (TranslateHelper.isStaticMember(identifier)){
			JstIdentifier jstQualifier = VjoTranslateHelper
				.getStaticPtyOrMtdQualifier(identifier, jstNode);
			boolean forceFullyQualify = false;
			boolean usedQualifierType = false;
			IJstMethod jstMtd = TranslateHelper.Method.getOwnerMethod(jstNode);
			if (jstMtd != null && jstMtd.getOwnerType() instanceof JstType){
				TranslateInfo tInfo = getCtx().getTranslateInfo((JstType)jstMtd.getOwnerType());
				MethodKey mtdKey = MethodKey.genMethodKey(jstMtd);
				if (tInfo.getMethodCustomInfo(mtdKey).isForceFullyQualify()){
					forceFullyQualify = true;
				}
			}
			if (forceFullyQualify){
				IJstNode binding = identifier.getJstBinding();
				if (binding != null && binding instanceof JstMethod){
					JstType qualifierType = ((JstMethod)binding).getOwnerType();
					if (!qualifierType.isEmbededType()){
						JstIdentifier q = new JstIdentifier(qualifierType.getName());
						q.setJstBinding(qualifierType);
						q.setType(qualifierType);
						identifier.setQualifier(q);	
						jstOptionalExpr = null;
						usedQualifierType = true;
					}
				}
			}
			if (!usedQualifierType && identifier.getQualifier() != null){
				identifier.setQualifier(jstQualifier);	
				jstOptionalExpr = null;
			}
		}
		
		IExpr mtdCall = null;
		IJstType calleeType = null;
		IJstMethod mtd = null;
		if (jstOptionalExpr != null){
			calleeType = jstOptionalExpr.getResultType();
			if (calleeType != null && !(calleeType instanceof JstParamType)){
				if (calleeType instanceof JstTypeWithArgs){
					calleeType = ((JstTypeWithArgs)calleeType).getType();
				}
				mtd = TranslateHelper.Method
					.getMethod(astExpr, ((BaseJstNode)calleeType).getOwnerType(), identifier.getName(), jstArgs);
				if (mtd != null){
					identifier.setType(mtd.getRtnType());
				} 
			}
		}
		
		if (calleeType == null){
			calleeType = jstNode.getOwnerType();
		}
		
		mtdCall = getCustomTranslator().processMtdInvocation(astExpr, isSuper, jstOptionalExpr, identifier, jstArgs, jstNode);
		if(mtdCall==null){
			MtdInvocationExpr methodCall = toMtdInvocationExpr(identifier, jstOptionalExpr, jstArgs, jstNode);
			if(mtd == null) mtd = (IJstMethod)methodCall.getMethod();
			if(mtd!=null) {
				List<JstArg> exceptedArgs = mtd.getArgs();
				int exceptedLen = exceptedArgs.size();
				int actualLen = jstArgs.size();
				int len = Math.min(exceptedLen, actualLen);
				
				boolean vararg = exceptedLen!=actualLen;
				
				AutoBoxer autoBoxer = AutoBoxer.getInstance();
				IJstType expectedType = null;
				for(int i=0;i<len;i++) {
					JstArg arg = exceptedArgs.get(i);
					IExpr expr = jstArgs.get(i);
					
					expectedType = arg.getType();
					if(autoBoxer.needAutoBoxing(expr, expectedType)) {
						IExpr boxed = autoBoxer.autoBoxing(expr, expectedType);
						methodCall.setArg(i, boxed);
					}
				}
				if(vararg && exceptedLen < actualLen) {
					for(int i=exceptedLen;i<actualLen;i++) {
						IExpr expr = jstArgs.get(i);
						if(autoBoxer.needAutoBoxing(expr, expectedType)) {
							IExpr boxed = autoBoxer.autoBoxing(expr, expectedType);
							methodCall.setArg(i, boxed);
						}
					}
				}
			}
			mtdCall = methodCall;
		}
		

		
		IExpr qualifier = null;
		if (mtdCall instanceof MtdInvocationExpr){
			MtdInvocationExpr mtdInvoke = (MtdInvocationExpr)mtdCall;
			IExpr mtdName = mtdInvoke.getMethodIdentifier();
			qualifier = mtdInvoke.getQualifyExpr();
			if (qualifier == null){
				if (mtdName instanceof JstIdentifier){
					qualifier = ((JstIdentifier)mtdName).getQualifier();
					if (TranslateHelper.Expression.removeQualifier(qualifier, mtdCall)){
						((JstIdentifier)mtdName).setQualifier(null);
					}
				}
			}
			else {
				if (TranslateHelper.Expression.removeQualifier(qualifier, mtdCall)){
					((MtdInvocationExpr)mtdCall).setQualifyExpr(null);
				}
			}
		}
		else if (mtdCall instanceof PtyGetter){
			PtyGetter ptyGetter = (PtyGetter)mtdCall;
			qualifier = ptyGetter.getQualifyExpr();
			if (qualifier == null){
				IExpr ptyName = ptyGetter.getPtyName();
				if (ptyName instanceof JstIdentifier){
					qualifier = ((JstIdentifier)ptyName).getQualifier();
					if (TranslateHelper.Expression.removeQualifier(qualifier, mtdCall)){
						((JstIdentifier)ptyName).setQualifier(null);
					}
				}
			}
			else {
				if (TranslateHelper.Expression.removeQualifier(qualifier, mtdCall)){
					ptyGetter.setQualifyExpr(null);
				}
			}
		}
		else if (mtdCall instanceof PtySetter){
			PtySetter ptySetter = (PtySetter)mtdCall;
			qualifier = ptySetter.getQualifyExpr();
			if (qualifier == null){
				IExpr ptyName = ptySetter.getPtyName();
				if (ptyName instanceof JstIdentifier){
					qualifier = ((JstIdentifier)ptyName).getQualifier();
					if (TranslateHelper.Expression.removeQualifier(qualifier, mtdCall)){
						((JstIdentifier)ptyName).setQualifier(null);
					}
				}
			}
			else {
				if (TranslateHelper.Expression.removeQualifier(qualifier, mtdCall)){
					ptySetter.setQualifyExpr(null);
				}
			}
		}
		
		return mtdCall;
	}
	
	private MtdInvocationExpr toMtdInvocationExpr(JstIdentifier identifier, IExpr qualifyExpr, List<IExpr> jstArgs, final BaseJstNode jstNode){

		IExpr q;
		if (qualifyExpr instanceof ObjCreationExpr) {
			q = new ParenthesizedExpr(qualifyExpr);
		} else {
			q = qualifyExpr;
		}
		MtdInvocationExpr mtdCall = new MtdInvocationExpr(identifier);
		for (IExpr a: jstArgs){
			mtdCall.addArg(a);
		}
		mtdCall.setQualifyExpr(q);
		mtdCall.setResultType(identifier.getResultType());
		mtdCall.setParent(jstNode);
		
		return mtdCall;
	}
	
	private IExpr toArrayCreation(final ArrayCreation astExpr, final BaseJstNode jstNode){
		
		IJstType type = getDataTypeTranslator().processType(astExpr.getType(), jstNode);
		if (type == null){
			getLogger().logError(TranslateMsgId.NULL_RESULT, "failed to translate type", this, astExpr, jstNode);
			return null;
		}
		
		JstIdentifier identifier = new JstIdentifier(type.getName());
		identifier.setType(type);
		ArrayCreationExpr ace = new ArrayCreationExpr(identifier);

		ArrayInitializer ai = astExpr.getInitializer(); 
		if (ai != null) {
			IExpr e =getExprTranslator().processExpression(ai, jstNode);
			if (e instanceof JstArrayInitializer) {
				if (type instanceof JstArray){
					((JstArrayInitializer)e).setType((JstArray)type);
				}
				return e;
			}
		}
		
		List dimensions = astExpr.dimensions();
//		if (dimensions.size() > 1) {
//			getLogger().logError(TranslateMsgId.UNSUPPORTED_CREATION, 
//					"Can not new multi-dimensional array", this, (ASTNode)astExpr, jstNode);
//			return ace;
//		}
		
		for (Object o: dimensions){
			if (o instanceof Expression){
				ace.addDimension(getExprTranslator().processExpression((Expression)o, jstNode));
			}
			else {
				getLogger().logUnhandledNode(this, (ASTNode)o, jstNode);
			}
		}
		return ace;
	}
	
	private ILHS toLHS(final Expression astExpr, final BaseJstNode jstNode){

		assert astExpr != null : "astExpr cannot be null";
		
		// Names & Accesses
		if (astExpr instanceof Name){	
			return toJstIdentifier(astExpr, jstNode, true);
		}
		else if (astExpr instanceof FieldAccess
				|| astExpr instanceof SuperFieldAccess){
			
			return toFieldAccess(astExpr, jstNode, true);
		}
		else if (astExpr instanceof ArrayAccess){
			return toArrayAccess((ArrayAccess)astExpr, jstNode);
		}
		else if (astExpr instanceof ParenthesizedExpression){
			ParenthesizedExpression expr = (ParenthesizedExpression)astExpr;
			IExpr lhs = processExpression(expr.getExpression(), jstNode);
			if (lhs instanceof ILHS){
				return (ILHS)lhs;
			}
			getLogger().logUnhandledNode(this, (ASTNode)astExpr, jstNode);
			return null;
		}
		else {
			getLogger().logUnhandledNode(this, (ASTNode)astExpr, jstNode);
		}
		
		return null;
	}
	
	private JstLiteral toJstLiteral(final Expression astExpr, BaseJstNode jstNode, boolean report){
		//To get type of variable
		String typeName = "";
		VarTable varTable = null;
		if (astExpr instanceof PrefixExpression) {
			PrefixExpression pexpr = (PrefixExpression)astExpr;
			JstLiteral tmp = toJstLiteral(pexpr.getOperand(), jstNode, report);
			if (tmp!=null) {
				return new SimpleLiteral(tmp.getResultType().getClass(), tmp
						.getResultType(), pexpr.getOperator().toString()
						+ tmp.toValueText());
			}
			return null;
		}
		LiteralTranslator translator = getLiteralTranslator();
		if (astExpr instanceof NumberLiteral){
			return translator.toJstLiteral((NumberLiteral)astExpr,jstNode);
		} 
		else if (astExpr instanceof CharacterLiteral){
			return translator.toJstLiteral((CharacterLiteral)astExpr);
		}
		else if (astExpr instanceof BooleanLiteral){
			return translator.toJstLiteral((BooleanLiteral)astExpr);
		}
		else if (astExpr instanceof StringLiteral){
			return translator.toJstLiteral((StringLiteral)astExpr);
		}
		else if (astExpr instanceof NullLiteral){
			return translator.toJstLiteral((NullLiteral)astExpr);
		} 
		else if (report){
			getLogger().logUnhandledNode(this, (ASTNode)astExpr, jstNode);
		}
		
		return null;
	}
}

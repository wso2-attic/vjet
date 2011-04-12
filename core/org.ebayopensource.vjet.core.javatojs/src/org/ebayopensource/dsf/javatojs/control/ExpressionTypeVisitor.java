/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.control;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.javatojs.anno.AIsOType;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.TranslateHelper;
import org.ebayopensource.dsf.javatojs.translate.VjoTranslateHelper;
import org.ebayopensource.dsf.javatojs.translate.util.AutoBoxer;
import org.ebayopensource.dsf.javatojs.translate.util.AutoUnboxer;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.expr.ArithExpr;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.InfixExpr;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.expr.ObjCreationExpr;
import org.ebayopensource.dsf.jst.expr.ParenthesizedExpr;
import org.ebayopensource.dsf.jst.expr.PostfixExpr;
import org.ebayopensource.dsf.jst.expr.PrefixExpr;
import org.ebayopensource.dsf.jst.expr.TextExpr;
import org.ebayopensource.dsf.jst.expr.AssignExpr.Operator;
import org.ebayopensource.dsf.jst.stmt.DispatchStmt;
import org.ebayopensource.dsf.jst.stmt.ExprStmt;
import org.ebayopensource.dsf.jst.stmt.RtnStmt;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.term.SimpleLiteral;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.ILHS;
import org.ebayopensource.dsf.jst.token.ISimpleTerm;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.traversal.JstVisitorAdapter;
import org.ebayopensource.dsf.jst.util.DataTypeHelper;
import org.ebayopensource.vjo.meta.VjoConvention;
import org.ebayopensource.vjo.meta.VjoKeywords;

public class ExpressionTypeVisitor extends JstVisitorAdapter {
	
	private final static String DIV_JS_FUNC = "parseInt";
	private final static String DIV_FLOAT_JS_FUNC = "parseFloat";
	
	private final static AutoBoxer s_autoBoxer = AutoBoxer.getInstance();
	private final static AutoUnboxer s_autoUnboxer = AutoUnboxer.getInstance();
	
	//
	// Satisfy IJstVisitor
	//
	public void preVisit(IJstNode node){
	}
	
	public boolean visit(IJstNode node){
			
		autoBoxingVisit(node);
		autoUnboxingVisit(node);
		
		prefixExprVisit(node);
		postfixExprVisit(node);
		
		//process int division
		intDivisionVisit(node);
		
		defaultConstructorVisit(node);
		
		otypeVisit(node);
		//process overloading with primitive numeric type
//		overloadedMtdVisit(node);
		
//		autoCastVisit(node);
		
		return true;
	}
	
	


	public void endVisit(IJstNode node){
	}
	
	public void postVisit(IJstNode node){
		
	}
	
	//
	// Private
	//
	private void otypeVisit(IJstNode node) {
		if (node.getParentNode()==null && node instanceof JstType) {
			JstType type = (JstType)node;
			List<? extends IJstType> types = type.getImports();
			for (IJstType imp : types) {
				if (imp.getAnnotation(AIsOType.class.getSimpleName())!=null) {
					type.removeImport(imp);
					type.addInactiveImport(imp);
				}
			}
			
		}
	}
	public void autoBoxingVisit(IJstNode node){
		if (node instanceof JstVars){
			JstVars jstVar = (JstVars)node;
			processJstVarsAutoboxing(jstVar);
		}else if (node instanceof AssignExpr){
			AssignExpr assignExpr = (AssignExpr)node;
			processAssignmentAutoboxing(assignExpr);
		}else if (node instanceof RtnStmt){
			RtnStmt rtnStmt = (RtnStmt)node;
			processRtnStmtAutoboxing(rtnStmt);
		}else if (node instanceof JstProperty){
			JstProperty jstProperty = (JstProperty)node;
			processJstPropertyAutoboxing(jstProperty);
		}else if (node instanceof MtdInvocationExpr){
			MtdInvocationExpr mtdExpr = (MtdInvocationExpr)node;
			processMethodAutoboxing(mtdExpr);
		}
	}
	
	private void processJstVarsAutoboxing(JstVars jstVar){	
		for (AssignExpr jstInitilizer: jstVar.getAssignments()){
			processAssignmentAutoboxing(jstInitilizer);
		}		
	}
	
	private void processAssignmentAutoboxing(AssignExpr assignExpr){				
		s_autoBoxer.autoBoxing(assignExpr, assignExpr.getResultType());
	}
	
	private void processRtnStmtAutoboxing(RtnStmt rtnStmt ){	
		IExpr expr = rtnStmt.getExpression();
		if (expr!= null ){
			IJstMethod jstMtd = TranslateHelper.Method.getOwnerMethod(expr);
			IJstType expectedType = jstMtd.getRtnType();
			IExpr newExpr = s_autoBoxer.autoBoxing(expr, expectedType);
			if (newExpr != expr){
				rtnStmt.setExpression(newExpr);
			}
		}	
	}
	
	private void processJstPropertyAutoboxing(JstProperty jstProperty){
		IJstType expectedType = jstProperty.getType();
		if (jstProperty.getValue() != null){
			ISimpleTerm value =jstProperty.getValue();
			if (value instanceof SimpleLiteral){
				SimpleLiteral expr = (SimpleLiteral)value;
				IExpr newExpr = s_autoBoxer.autoBoxing(expr,expectedType);
				if (newExpr != expr){
					boolean isStatic = jstProperty.isStatic();
					VjoConvention conv = TranslateCtx.ctx().getConfig().getVjoConvention();		
					String qualifier = conv.getThisPrefix();
					if (isStatic && null == DataTypeHelper.getNativeType(jstProperty.getOwnerType().getName())){
						qualifier = conv.getNameWithStaticThis(jstProperty.getOwnerType().getSimpleName());
					}						
					JstIdentifier jstIdentifier = new JstIdentifier(jstProperty.getName().toString());
					if (qualifier != null){
						jstIdentifier.setQualifier(new JstIdentifier(qualifier));
					}
					AssignExpr assignExpr = new AssignExpr(jstIdentifier,newExpr);
					jstProperty.setInitializer(assignExpr);
					jstProperty.setValue(null);	
					JstType ownerType = jstProperty.getOwnerType();
					ownerType.addInitWithoutChild(assignExpr, isStatic);
				}					
			}
		}
		else if (jstProperty.getInitializer() != null){
			IExpr expr = jstProperty.getInitializer();
			IExpr newExpr = s_autoBoxer.autoBoxing(expr, expectedType);
			if (newExpr != expr){
				jstProperty.setInitializer(newExpr);
			}
		}		
	}
	private void processMethodAutoboxing(MtdInvocationExpr mtdExpr){
		if (mtdExpr.getMethod() == null || !(mtdExpr.getMethod() instanceof JstMethod)){
			return;
		}
		
		JstMethod mtd = (JstMethod)mtdExpr.getMethod();
		if (mtd.isDispatcher()){
			return;
		}

		List<IExpr> list = mtdExpr.getArgs();
		List<JstArg> args = mtd.getArgs();
		if (args.size() == list.size()) {
			List<IExpr> tempList = new ArrayList<IExpr>();
			for (IExpr arg: list){
				tempList.add(arg);
			}
			IJstType expectedType = null;
			boolean boxed = false;
			for (int i = 0; i< tempList.size();i++){					
				expectedType = args.get(i).getType();
				IExpr newExpr = s_autoBoxer.autoBoxing(tempList.get(i),expectedType);
				if (newExpr != tempList.get(i)){
					tempList.set(i, newExpr);
					boxed = true;
				}	
			}
			if (boxed){
				mtdExpr.setArgs(tempList);
			}
		}	
	}
	
	
	private void autoUnboxingVisit(IJstNode node){
		if (node instanceof JstVars){
			JstVars jstVar = (JstVars)node;
			processJstVarAutoUnboxing(jstVar);
		} else if (node instanceof AssignExpr){
			AssignExpr assignExpr = (AssignExpr)node;
			processAssignmentAutoUnboxing(assignExpr);
		} else if (node instanceof RtnStmt){
			RtnStmt rtnStmt = (RtnStmt)node;
			processRtnStmtAutoUnboxing(rtnStmt);
		} else if (node instanceof JstProperty){
			JstProperty jstProperty = (JstProperty)node;
			processJstPropertyAutoUnboxing(jstProperty);
		} else if (node instanceof MtdInvocationExpr){
			MtdInvocationExpr mtdExpr = (MtdInvocationExpr)node;
			processMethodAutoUnboxing(mtdExpr);
		}
	}
	
	private void processJstVarAutoUnboxing(JstVars jstVar){	
		for (AssignExpr jstInitilizer: jstVar.getAssignments()){
			processAssignmentAutoUnboxing(jstInitilizer);	
		}		
	}
	
	private void processJstPropertyAutoUnboxing(JstProperty jstProperty){			
		IExpr expr = jstProperty.getInitializer();
		IJstType expectedType = jstProperty.getType();
		s_autoUnboxer.autoUnboxing(expr,expectedType);		
	}
	
	private void processAssignmentAutoUnboxing(AssignExpr assignExpr){
		IJstType expectedType = assignExpr.getResultType();
		s_autoUnboxer.autoUnboxing(assignExpr,expectedType);	
	}

	private void processRtnStmtAutoUnboxing(RtnStmt rtnStmt){
		IExpr expr = rtnStmt.getExpression();
		IJstMethod jstMtd = TranslateHelper.Method.getOwnerMethod(expr);
		if (expr!= null ){
			IJstType expectedType = jstMtd.getRtnType();
			IExpr newExpr = s_autoUnboxer.autoUnboxing(expr, expectedType);
			if (newExpr != expr){
				rtnStmt.setExpression(newExpr);
			}
		}	
	}
	
	private void processMethodAutoUnboxing(MtdInvocationExpr mtdExpr){
		if (mtdExpr.getMethod() == null 
				|| !(mtdExpr.getMethod() instanceof JstMethod)){
			return;
		}
		
		List<IExpr> list = mtdExpr.getArgs();
		JstMethod mtd = (JstMethod)mtdExpr.getMethod();	
		List<JstArg> args = mtd.getArgs();
		if (list.size() == args.size()){
			IJstType expectedType = null;		
			List<IExpr> tempList = new ArrayList<IExpr>();
			for (IExpr arg: list){
				tempList.add(arg);
			}
			boolean unBoxed = false;
			for (int i = 0; i< list.size();i++){
				expectedType = args.get(i).getType();
				IExpr newArg = s_autoUnboxer.autoUnboxing(tempList.get(i),expectedType);
				if (newArg != tempList.get(i)){
					tempList.set(i, newArg);
					unBoxed = true;
				}
			}
			if (unBoxed) {
		    	mtdExpr.setArgs(tempList);
			}	
		}		
	}

	private void defaultConstructorVisit(IJstNode node) {
		if (node instanceof IJstMethod) {
			IJstMethod mtd = (IJstMethod)node;
			IJstType extend = mtd.getOwnerType().getExtend();
			if (mtd.isConstructor() && extend != null
					&& !VjoKeywords.VJO_OBJECT.equals(extend.getName())
					&& !VjoKeywords.VJO_ENUM.equals(extend.getName())
					&& !mtd.getOwnerType().isAnonymous()) {
				processForDefaultConstructor(mtd.getBlock());
			}
		}
	}

	private void processForDefaultConstructor(JstBlock block) {
		if (block == null) {
			return;
		}
		String thisRef = "this.";
		String thisBase = thisRef + VjoKeywords.BASE;
		String constructsPre = thisRef + VjoKeywords.CONSTRUCTS;
		if (block.getStmts().size()>0) {
			IStmt stmt = block.getStmts().get(0);					
			if (!(stmt instanceof DispatchStmt)) {
				if ((stmt instanceof MtdInvocationExpr )) {
					String func =  ((MtdInvocationExpr) stmt).getMethodIdentifier().toExprText();
					if (!thisBase.equals(func) && !func.startsWith(constructsPre)) {
						block.addStmt(0, new ExprStmt(new TextExpr(thisBase+"()")));
					}
				} else {
					block.addStmt(0, new ExprStmt(new TextExpr(thisBase+"()")));
				}	
			}
		} else {
			block.addStmt(0, new ExprStmt(new TextExpr(thisBase+"()")));
		}
	}
	
	private boolean isIntType(String typeName){
		if (typeName.equals("int") || typeName.equals("long") || typeName.equals("short") ||
			    typeName.equals("byte")){
				return true;
			} else 
				return false;		
	}
	
	private boolean isFloatType(String typeName){
		if (typeName.equals("float") || typeName.equals("double")){
				return true;
			} else 
				return false;		
	}
	
	private IExpr parseInfixExpr(InfixExpr expr,IJstNode jstNode){
		List<IExpr> args = new ArrayList<IExpr>();
		if (jstNode instanceof MtdInvocationExpr){
			MtdInvocationExpr parent = (MtdInvocationExpr)jstNode;
			String exprTxt = parent.getMethodIdentifier().toExprText();
			if (DIV_JS_FUNC.equals(exprTxt) || DIV_FLOAT_JS_FUNC.equals(exprTxt))
				return expr;
		}
		IJstType jstType = expr.getResultType();
		IExpr e = expr;
		String name = null;
		MtdInvocationExpr mtdExpr = null;		
		if (jstType instanceof JstType){
			JstType rtnType = (JstType)expr.getResultType();
			InfixExpr.Operator op = expr.getOperator();
			IExpr lhs = parseExpr(expr.getLeft());
			IExpr rhs = parseExpr(expr.getRight());	
			if (!lhs.equals(expr.getLeft()) || !rhs.equals(expr.getRight())) {
				e =  new InfixExpr(lhs,rhs,op);
			} 
			if (jstNode instanceof AssignExpr) {
				AssignExpr assign = (AssignExpr)jstNode;
				ILHS lh = assign.getLHS();
				if (lh instanceof JstIdentifier) {
					IJstNode ltype = ((JstIdentifier)lh).getJstBinding();
					if (ltype instanceof JstType) {
						rtnType = (JstType)ltype;
					}
				}
			}
			if (rtnType != null && isIntType(rtnType.getSimpleName()) && op.equals(InfixExpr.Operator.DIVIDE)){
				name = DIV_JS_FUNC;
			} else if (rtnType != null && isFloatType(rtnType.getSimpleName()) && op.equals(InfixExpr.Operator.DIVIDE)){
				name = DIV_FLOAT_JS_FUNC;
			} else {
				return e;
			}
			if (name != null){
				JstIdentifier identifier = new JstIdentifier(name);
				mtdExpr = new MtdInvocationExpr(identifier);
				mtdExpr.setResultType(rtnType);		

				if (jstNode instanceof AssignExpr){
					mtdExpr.addArg(expr);
					AssignExpr assignExpr = (AssignExpr)jstNode;
					assignExpr.setExpr(mtdExpr);
					mtdExpr.setParent(jstNode);	
					
				} else if (jstNode instanceof RtnStmt){
					mtdExpr.addArg(expr);
					RtnStmt rtnStmt = (RtnStmt)jstNode;
					rtnStmt.setExpression(mtdExpr);
					mtdExpr.setParent(jstNode);	
				} else if (jstNode instanceof MtdInvocationExpr){
					MtdInvocationExpr methodExpr = (MtdInvocationExpr)jstNode;					
					List<IExpr> list = methodExpr.getArgs();
					for (IExpr arg:list){
						args.add(arg) ;
					}					
					for (int i = 0; i< args.size();i++){
						if (list.get(i).toExprText().equals(e.toExprText()))
							args.set(i, mtdExpr);
					}
					if (args.size() > 0)
						methodExpr.setArgs(args);
					mtdExpr.addArg(e);
				} else if (jstNode instanceof InfixExpr){
					InfixExpr parentInfixExpr = (InfixExpr)jstNode;
					mtdExpr.addArg(expr);
					if (mtdExpr.getArgs().contains(parentInfixExpr.getLeft())){
						parentInfixExpr.removeChild(expr);
						parentInfixExpr.setLeft(mtdExpr);
						parentInfixExpr.addChild(mtdExpr);
					}
					if (mtdExpr.getArgs().contains(parentInfixExpr.getRight())){
						parentInfixExpr.removeChild(expr);					
						parentInfixExpr.setRight(mtdExpr);
						parentInfixExpr.addChild(mtdExpr);
					}
				}	
			}
 			return mtdExpr;	
		} else
			return e;
	}
	private void parseAssignExpr(AssignExpr expr){
		Operator op = expr.getOprator();
		IJstType rtnType = expr.getResultType();
		if (op.equals(AssignExpr.Operator.DIVIDE_ASSIGN) && rtnType != null &&
			isIntType(rtnType.getSimpleName()) ){
			IExpr e = expr.getExpr();
			ILHS lhs = expr.getLHS();
			InfixExpr infixExpr = new InfixExpr((IExpr)lhs,e,InfixExpr.Operator.DIVIDE);				
			String name = DIV_JS_FUNC;
			MtdInvocationExpr mtdExpr = null;	
			JstIdentifier identifier = new JstIdentifier(name);
			mtdExpr = new MtdInvocationExpr(identifier);
			mtdExpr.setResultType(rtnType);
			mtdExpr.addArg(infixExpr);
			expr.setExpr(mtdExpr);
			expr.setOp(AssignExpr.Operator.ASSIGN);
		}				
	}
	private IExpr parseExpr(IExpr expr){
		IExpr e = expr;
		if (expr instanceof InfixExpr){
			InfixExpr infixExpr = (InfixExpr)expr;
			IJstNode parentNode = infixExpr.getParentNode();
			e = parseInfixExpr(infixExpr,parentNode);
			return e;
		} else if (expr instanceof ParenthesizedExpr) {
			ParenthesizedExpr parenthesizedExpr = (ParenthesizedExpr)expr;
			e = parenthesizedExpr.getExpression();
			if (e instanceof InfixExpr){
				InfixExpr infixExpr = (InfixExpr)e;
				IJstNode parentNode = infixExpr.getParentNode();
				e = parseInfixExpr(infixExpr,parentNode);
				return new ParenthesizedExpr(e);

			}
		}
		return expr;
	}
	private void processOverloadedArgs(MtdInvocationExpr node){
		if (node.getMethod() instanceof JstMethod && node.getMethod() != null){
			JstMethod jstMtd = (JstMethod)node.getMethod();
			if (jstMtd.isDispatcher()){
				List<IExpr> list = node.getArgs();
				List<IExpr> args = new ArrayList<IExpr>();
				for (IExpr arg:list){
					args.add(arg) ;
				}
				for (int i = 0;i< args.size();i++){
					if (args.get(i).getResultType() != null && args.get(i).getResultType().getSimpleName() != null &&
							DataTypeHelper.isNumericPrimitiveType(args.get(i).getResultType())) {					
						ObjCreationExpr expr = changeOverLoadedMtdArgs(args.get(i),node);
						if (expr != null){						
							args.set(i, expr);
						}
					}
				}
				if (args.size() > 0)
			    	node.setArgs(args);
			}			
		}			
	}
	
	private ObjCreationExpr changeOverLoadedMtdArgs(IExpr arg, MtdInvocationExpr jstNode){
		IJstType type = arg.getResultType();
		String fullName = getWrapperTypeName(type.getName(),true);
		
		JstType rtnType = JstCache.getInstance().getType(fullName, true);
		
		JstIdentifier jstQualifier = VjoTranslateHelper.getStaticTypeQualifier(rtnType,jstNode);
		String name = jstQualifier.getName();
		if (rtnType != jstNode.getOwnerType()){
			name += "." + rtnType.getSimpleName();
		}
		JstIdentifier identifier = new JstIdentifier(name);
		MtdInvocationExpr mtdCall = new MtdInvocationExpr(identifier);
		mtdCall.setResultType(rtnType);
		mtdCall.addArg(arg);
		ObjCreationExpr objCreationExpr = new ObjCreationExpr(mtdCall);
		return objCreationExpr;	
	}
	private String getWrapperTypeName(String str,boolean fullName){
		if (str.equals("int")){
			if (fullName)
				return "vjo.java.lang.Integer";
			else 
				return "Integer";
		} else if (str.equals("long")){
			if (fullName)
				return "vjo.java.lang.Long";
			else 
				return "Long";
		} else if (str.equals("short")){
			if (fullName)
				return "vjo.java.lang.Short";
			else 
				return "Short";
		} else if (str.equals("byte")){
			if (fullName)
				return "vjo.java.lang.Byte";
			else 
				return "Byte";
		} else if (str.equals("float")){
			if (fullName)
				return "vjo.java.lang.Float";
			else
				return "Float";
		} else if (str.equals("double")){
			if (fullName)
				return "vjo.java.lang.Double";
			else
				return "Double";
		}
		return null;		
	}
	
	private void intDivisionVisit(IJstNode node){
		if (node instanceof InfixExpr){
			InfixExpr infixExpr =(InfixExpr)node;
			IJstNode parentNode = node.getParentNode();
			parseInfixExpr(infixExpr,parentNode);
		} else if (node instanceof AssignExpr){
			AssignExpr expr =(AssignExpr)node;
			parseAssignExpr(expr);
		}		
	}
	
	//auto cast will be added for the following scenarios:
	//1.If result type is byte or short and operator is plus_assign, times_assign, minus_assign
	//2.If result type is int or float and operator is plus_assing,times_assign, minus_assign, plus, times, minus	
	private boolean needAutoCast(String typeName,Operator op){
		boolean autoCast = false;
//		if ((typeName.equals("int") || (typeName.equals("float"))) && (op.equals(AssignExpr.Operator.PLUS_ASSIGN) || 
//			op.equals(AssignExpr.Operator.TIMES_ASSIGN) || op.equals(AssignExpr.Operator.MINUS_ASSIGN) ||
//			op.equals(InfixExpr.Operator.PLUS) || op.equals(InfixExpr.Operator.TIMES) ||
//			op.equals(InfixExpr.Operator.MINUS))){
//			autoCast = true;
//		} else 
			if ((typeName.equals("byte") || typeName.equals("short")) && 
		        (op.equals(AssignExpr.Operator.PLUS_ASSIGN) || op.equals(AssignExpr.Operator.TIMES_ASSIGN)) ||
		         op.equals(AssignExpr.Operator.MINUS_ASSIGN))
			autoCast = true;
		return autoCast;
	}
	
	private void prefixExprVisit(IJstNode node){
		if (node instanceof PrefixExpr){
			((PrefixExpr)node).setIsFirstTerm(isFirst(node));
		}		
	}
	
	private void postfixExprVisit(IJstNode node){
		if (node instanceof PostfixExpr){
			((PostfixExpr)node).setIsLastTerm(isLast(node));
		}		
	}
	
	private boolean isFirst(IJstNode node){
		IJstNode parent = node.getParentNode();
		if (parent == null || !(parent instanceof ArithExpr)){
			return true;
		}
		if (parent instanceof InfixExpr){
			InfixExpr infixExpr = (InfixExpr)parent;
			if (infixExpr.getRight() == node){
				return false;
			}
			else if (infixExpr.getLeft() == node){
				return isLast(parent);
			}
		}
		else if (parent instanceof PrefixExpr){
			return false;
		}
		else if (parent instanceof PostfixExpr){
			return isFirst(parent);
		}
		return true;
	}
	
	private boolean isLast(IJstNode node){
		IJstNode parent = node.getParentNode();
		if (parent == null || !(parent instanceof ArithExpr)){
			return true;
		}
		if (parent instanceof InfixExpr){
			InfixExpr infixExpr = (InfixExpr)parent;
			if (infixExpr.getLeft() == node){
				return false;
			}
			else if (infixExpr.getRight() == node){
				return isLast(parent);
			}
		}
		else if (parent instanceof PostfixExpr){
			return false;
		}
		else if (parent instanceof PrefixExpr){
			return isLast(parent);
		}
		return true;
	}
}
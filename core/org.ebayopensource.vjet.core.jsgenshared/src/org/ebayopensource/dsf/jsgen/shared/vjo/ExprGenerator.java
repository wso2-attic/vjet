/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.vjo;

import java.io.PrintWriter;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.expr.ArrayAccessExpr;
import org.ebayopensource.dsf.jst.expr.ArrayCreationExpr;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.BoolExpr;
import org.ebayopensource.dsf.jst.expr.ConditionalExpr;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.expr.FuncExpr;
import org.ebayopensource.dsf.jst.expr.InfixExpr;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.expr.ObjCreationExpr;
import org.ebayopensource.dsf.jst.expr.PostfixExpr;
import org.ebayopensource.dsf.jst.expr.PrefixExpr;
import org.ebayopensource.dsf.jst.expr.PtyGetter;
import org.ebayopensource.dsf.jst.stmt.PtySetter;
import org.ebayopensource.dsf.jst.stmt.RtnStmt;
import org.ebayopensource.dsf.jst.term.ArrayLiteral;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.term.NV;
import org.ebayopensource.dsf.jst.term.ObjLiteral;
import org.ebayopensource.dsf.jst.term.SimpleLiteral;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.IStmt;

public class ExprGenerator extends BaseGenerator {
	
	
	//
	// Constructor
	//
	public ExprGenerator(final GeneratorCtx ctx){
		super(ctx);
	}
	
	//
	// API
	//
	public void writeExpr(final IExpr expr){
		if (expr == null){
			return;
		}
		writeComments(expr);
		
		if (expr instanceof ArrayAccessExpr){
			write((ArrayAccessExpr)expr);
		}
		else if (expr instanceof ArrayCreationExpr){
			write((ArrayCreationExpr)expr);
		}
//		else if (expr instanceof ArrayInitializer){
//			write((ArrayInitializer)expr);
//		}
		else if (expr instanceof ArrayLiteral){
			write((ArrayLiteral)expr);
		}
		else if (expr instanceof AssignExpr){
			write((AssignExpr)expr);
		}
		else if (expr instanceof JstIdentifier){
			getWriter().append(((JstIdentifier)expr).toSimpleTermText());
		}
//		else if (expr instanceof JstInitializer){
//			write((JstInitializer)expr);
//		}
//		else if (expr instanceof JstVars){
//			write((JstVars)expr);
//		}
		else if (expr instanceof BoolExpr){
			write((BoolExpr)expr);
		}
		else if (expr instanceof ConditionalExpr){
			write((ConditionalExpr)expr);
		}
		else if (expr instanceof FieldAccessExpr){
			getWriter().append(((FieldAccessExpr)expr).toLHSText());
		}
		else if (expr instanceof FuncExpr){
			write((FuncExpr)expr);
		}
		else if (expr instanceof InfixExpr){
			write((InfixExpr)expr);
		}
		else if (expr instanceof MtdInvocationExpr){
			write((MtdInvocationExpr)expr);
		}
		else if (expr instanceof ObjCreationExpr){
			write((ObjCreationExpr)expr);
		}
		else if (expr instanceof ObjLiteral){
			write((ObjLiteral)expr);
		}
//		else if (expr instanceof ParenthesizedExpr){
//			write((ParenthesizedExpr)expr);
//		}
		else if (expr instanceof PostfixExpr){
			write((PostfixExpr)expr);
		}
		else if (expr instanceof PrefixExpr){
			write((PrefixExpr)expr);
		}
		else if (expr instanceof PtyGetter){
			write((PtyGetter)expr);
		}
		else if (expr instanceof PtySetter){
			write((PtySetter)expr);
		}
		else if (expr instanceof SimpleLiteral){
			getWriter().append(((SimpleLiteral)expr).toSimpleTermText());
		}
		else {
			String text = expr.toExprText();
			if (text != null){
				getWriter().append(text);
			}
		}
	} 
	
	//
	// Private
	//
	private void write(final ArrayAccessExpr arrayAccessExpr){

		if (arrayAccessExpr == null){
			return;
		}
		
		PrintWriter writer = getWriter();

		writeExpr(arrayAccessExpr.getExpr());
		IExpr index = arrayAccessExpr.getIndex();
		if (index != null) {
			writer.append(OPEN_BRACKET);
			writeExpr(index);
			writer.append(CLOSE_BRACKET);
		}
	}
	
	private void write(ArrayCreationExpr arrayCreationExpr){
		getWriter().append(arrayCreationExpr.toExprText());
/*		getWriter().append("new Array");
		
		for (IExpr d: arrayCreationExpr.getDimensions()){
			if (d != null){
				getWriter().append(OPEN_PARENTHESIS).append(d.toExprText()).append(CLOSE_PARENTHESIS);
			}
		}
*/	}
	
	private void write(final AssignExpr assignExpr){
		if (assignExpr.getLHS() != null){
			getWriter().append(assignExpr.getLHS().toLHSText());
		}
		getWriter().append(assignExpr.getOprator().toString());
		writeExpr(assignExpr.getExpr());
	}
	
//	private void write(final JstInitializer astInitializer){
//		if (astInitializer == null){
//			return;
//		}
//		ILHS lhs = astInitializer.getLHS();
//		IExpr expr = astInitializer.getExpression();
//		getWriter().append(lhs.toLHSText());
//		if (expr != null){
//			if (lhs != null){
//				getWriter().append(EQUAL);
//			}
//			writeExpr(expr);
//		}
//	}
	
	private void write(final ArrayLiteral arrayLiteral){
		getWriter().append(arrayLiteral.toSimpleTermText());
	}
	
	private void write(final ObjLiteral objLiteral){
		getWriter().append("{");
		writeNameValues(objLiteral.getNVs(),false);
		getWriter().append("}");
	}
	
	private void write(final BoolExpr boolExpr){
		if (boolExpr == null){
			return;
		}
		IExpr left = boolExpr.getLeft();
		IExpr right = boolExpr.getRight();
		
//		if(left!=null && right!=null){
//			getWriter().append("(");
//		}
		writeExpr(left);
		if (boolExpr.getOperator() != null){
			getWriter().append(boolExpr.getOperator().toString());
		}
		writeExpr(right);
		
//		if(left!=null && right!=null){
//			getWriter().append(")");
//		}
	}
	
	private void write(final ConditionalExpr condExpr){
//		getWriter().append(OPEN_PARENTHESIS);
		writeExpr(condExpr.getCondition());
		getWriter().append(QUESTION_MARK);
		writeExpr(condExpr.getThenExpr());
		getWriter().append(COLON);
		writeExpr(condExpr.getElseExpr());
//		getWriter().append(CLOSE_PARENTHESIS);
	}
	
	private void write(final InfixExpr infixExpr){
//		getWriter().append(OPEN_PARENTHESIS);
		writeExpr(infixExpr.getLeft());
		getWriter().append(infixExpr.getOperator().toString());
		writeExpr(infixExpr.getRight());
//		getWriter().append(CLOSE_PARENTHESIS);
	}
	
	private void write(final MtdInvocationExpr mtdInvocation){
		IExpr q = mtdInvocation.getQualifyExpr();
		IExpr m = mtdInvocation.getMethodIdentifier();
		writeExpr(q);
		if (m instanceof JstIdentifier && ((JstIdentifier)m).getName() == null){
			return;
		}
		if (q != null && m != null){
			getWriter().append(DOT);
		}
		writeExpr(m);
		getWriter().append(OPEN_PARENTHESIS);
		List<IExpr> args = mtdInvocation.getArgs();
		for (int i=0; i<args.size(); i++){
			if (args.get(i) == null){
				continue;
			}
			if (i > 0){
				getWriter().append(COMMA);
			}
			getCtx().getProvider().getJsDocGenerator().writeJsDocForArg(
					args.get(i));
			writeExpr(args.get(i));
		}
		getWriter().append(CLOSE_PARENTHESIS);
	}
	
	private void write(final ObjCreationExpr expr){
		if (expr == null){
			return;
		}
		
		PrintWriter writer = getWriter();
		
		IJstType anonymousType = expr.getAnonymousType();
		if (anonymousType == null){
			writer.append("new ");
			IExpr oExpr = expr.getExpression();
			if (oExpr != null){
				writer.append(oExpr.toExprText()).append(".");
			}
			writeExpr(expr.getInvocationExpr());
			return;
		}
		else {
			VjoGenerator typeGenerator = getTypeGenerator();
			indent();
			if (!(expr.getParentNode() instanceof RtnStmt)) {
				writeNewline();
				writeIndent();
			}
			
			String name; 
			if (expr.getExpression()!=null) {
				name = expr.getExpression() + "."+ expr.getInvocationExpr().getMethodIdentifier();
			} else {
				name = expr.getInvocationExpr().getMethodIdentifier().toExprText();
			}
					
			List<IExpr> args = expr.getInvocationExpr().getArgs();
			IJstType previousType = typeGenerator.getCurrentType();
			typeGenerator.writeAnonymousType(anonymousType,name,args);
			typeGenerator.setCurrentType(previousType);
			
//			writer.append("(");
			
//			writer.append(")");

			outdent();
		}
	}
	
//	private void write(final ParenthesizedExpr parenthesizedExpr){
//		IExpr expr = parenthesizedExpr.getExpression();
//		if (expr == null) {
//			getWriter().append(OPEN_PARENTHESIS).append(CLOSE_PARENTHESIS);
//			return;
//		}
//		
//		if (expr instanceof JstIdentifier
//				|| (expr instanceof CastExpr && ((CastExpr)expr).getCastToType() instanceof JstIdentifier)) {
//			writeExpr(expr);
//			return;
//		} else {
//			getWriter().append(OPEN_PARENTHESIS);
//			writeExpr(expr);
//			getWriter().append(CLOSE_PARENTHESIS);
//		}
//	}
	
	private void write(final PostfixExpr postfixExpr){
		getWriter().append(postfixExpr.toExprText());
	}
	
	private void write(final PrefixExpr prefixExpr){
		getWriter().append(prefixExpr.toExprText());
	}
	
	private void write(final FuncExpr expr){
		write(expr, false);
	}
	
	private void write(final FuncExpr expr, boolean hasMore){
		if (expr == null){
			return;
		}
		JstMethod method = expr.getFunc();
		List<JstArg> args = method.getArgs();
		Object[] params = new Object[args.size()];
		for (int j = 0; j < params.length; j++) {
			params[j] = args.get(j).getName();
		}
		
		JsCoreGenerator coreGenerator = getJsCoreGenerator();
		coreGenerator.startWriteFunc(params);
		writeBlock(method.getBlock());
		coreGenerator.endWriteFunc(hasMore, method.getOwnerType().isMetaType());
	}
	
//	private void write(final JstVars jstVars){
//		
//		PrintWriter writer = getWriter();
//		
//		if (jstVars.getType() != null){
//			writer.append("var ");
//		}
//		
//		List<AssignExpr> initializers = jstVars.getInitializers().getInitializers();
//		AssignExpr assignExpr;
//		if (initializers != null){
//			for (int i=0; i<initializers.size(); i++){
//				if (i>0){
//					writer.append(",");
//				}
//				assignExpr = initializers.get(i);
//				writer.append(assignExpr.getLHS().toLHSText());
//				if (assignExpr.getExpr() != null){
//					writer.append(assignExpr.getOprator().toString());
//					writeExpr(assignExpr.getExpr());
//				}
//			}
//		}
//	}
	
	private void write(final PtyGetter getter){
		IExpr qualifier = getter.getQualifyExpr();
		JstIdentifier name = getter.getPtyName();
		getExprGenerator().writeExpr(qualifier);
		if (qualifier != null && name != null){
			getWriter().append(DOT);
		}
		if (name != null){
			getWriter().append(name.toSimpleTermText());
		}
	}
	
	private void write(final PtySetter setter){
		IExpr qualifier = setter.getQualifyExpr();
		JstIdentifier name = setter.getPtyName();
		String value = setter.getPtyValue();
		getExprGenerator().writeExpr(qualifier);
		if (qualifier != null && name != null){
			getWriter().append(DOT);
		}
		if (name != null){
			getWriter().append(name.toSimpleTermText());
		}
		getWriter().append(EQUAL);
		getWriter().append(value);
	}
	
	private void writeNameValues(final List<NV> nvs, boolean addNewLine) {
		NV nv;
		int size = nvs.size();
		for (int i=0; i<size; i++){
			nv = nvs.get(i);
			IExpr value = nv.getValue();
			if (value instanceof FuncExpr) {
				getWriter().append(nv.getName()).append(COLON);
				write((FuncExpr)value, i != size-1);
			}
			else {
				getJsCoreGenerator().writeNameValue(nv.getName(), nv.getValue().toExprText(), i != size-1, addNewLine);
			}
		}
	}
	private void writeNameValues(final List<NV> nvs){
		writeNameValues(nvs,true);
	}
	
	private void writeBlock(final JstBlock block){
		if (block == null || block.getStmts().isEmpty()){
			return;
		}
		for (IStmt s : block.getStmts()){
			getStmtGenerator().writeStmt(s);
		}
	}
}

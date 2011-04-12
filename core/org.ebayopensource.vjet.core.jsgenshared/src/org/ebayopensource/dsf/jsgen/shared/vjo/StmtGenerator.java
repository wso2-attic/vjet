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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstArray;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstParamType;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.expr.ArithExpr;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.BoolExpr;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.expr.ParenthesizedExpr;
import org.ebayopensource.dsf.jst.expr.TextExpr;
import org.ebayopensource.dsf.jst.stmt.BlockStmt;
import org.ebayopensource.dsf.jst.stmt.BreakStmt;
import org.ebayopensource.dsf.jst.stmt.CatchStmt;
import org.ebayopensource.dsf.jst.stmt.ContinueStmt;
import org.ebayopensource.dsf.jst.stmt.DispatchStmt;
import org.ebayopensource.dsf.jst.stmt.DoStmt;
import org.ebayopensource.dsf.jst.stmt.ExprStmt;
import org.ebayopensource.dsf.jst.stmt.ForInStmt;
import org.ebayopensource.dsf.jst.stmt.ForStmt;
import org.ebayopensource.dsf.jst.stmt.IfStmt;
import org.ebayopensource.dsf.jst.stmt.LabeledStmt;
import org.ebayopensource.dsf.jst.stmt.PtySetter;
import org.ebayopensource.dsf.jst.stmt.RtnStmt;
import org.ebayopensource.dsf.jst.stmt.SwitchStmt;
import org.ebayopensource.dsf.jst.stmt.ThisStmt;
import org.ebayopensource.dsf.jst.stmt.ThrowStmt;
import org.ebayopensource.dsf.jst.stmt.TryStmt;
import org.ebayopensource.dsf.jst.stmt.TypeDeclStmt;
import org.ebayopensource.dsf.jst.stmt.WhileStmt;
import org.ebayopensource.dsf.jst.stmt.WithStmt;
import org.ebayopensource.dsf.jst.stmt.SwitchStmt.CaseStmt;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IBoolExpr;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.IInitializer;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.util.DataTypeHelper;
import org.ebayopensource.dsf.jst.util.JstMethodHelper;
import org.ebayopensource.vjo.meta.VjoConvention;
import org.ebayopensource.vjo.meta.VjoKeywords;

public class StmtGenerator extends BaseGenerator {
	
	//
	// Constructor
	//
	public StmtGenerator(final GeneratorCtx ctx){
		super(ctx);
	}
	
	//
	// API
	//
	public void writeStmt(final IStmt stmt){
		if (stmt == null){
			return;
		}
		
		writeComments(stmt);
		
		if (stmt instanceof AssignExpr){
			write((AssignExpr)stmt);
		}
		else if (stmt instanceof ArithExpr){
			write((ArithExpr)stmt);
		}
		else if (stmt instanceof JstVars){
			write((JstVars)stmt);
		}
		else if (stmt instanceof BreakStmt){
			write((BreakStmt)stmt);
		}
		else if (stmt instanceof ContinueStmt){
			write((ContinueStmt)stmt);
		}
		else if (stmt instanceof RtnStmt){
			write((RtnStmt)stmt);
		}
		else if (stmt instanceof ForStmt){
			write((ForStmt)stmt);
		}
		else if (stmt instanceof WithStmt){
			write((WithStmt)stmt);
		}
		else if (stmt instanceof ForInStmt){
			write((ForInStmt)stmt);
		}
		else if (stmt instanceof DoStmt){
			write((DoStmt)stmt);
		}
		else if (stmt instanceof WhileStmt){
			write((WhileStmt)stmt);
		}
		else if (stmt instanceof IfStmt){
			writeNewline();
			writeIndent();
			write((IfStmt)stmt);
		}
		else if (stmt instanceof SwitchStmt){
			write((SwitchStmt)stmt);
		}
		else if (stmt instanceof LabeledStmt){
			write((LabeledStmt)stmt);
		}
		else if (stmt instanceof ThrowStmt){
			write((ThrowStmt)stmt);
		}
		else if (stmt instanceof TryStmt){
			write((TryStmt)stmt);
		}
		else if (stmt instanceof BlockStmt){
			write((BlockStmt)stmt);
		}
		else if (stmt instanceof ThisStmt){
			write((ThisStmt)stmt);
		}
		else if (stmt instanceof TypeDeclStmt){
			write((TypeDeclStmt)stmt);
		}
		else if (stmt instanceof MtdInvocationExpr){
			write((MtdInvocationExpr)stmt);
		}
		else if (stmt instanceof PtySetter){
			write((PtySetter)stmt);
		}
		else {
			String text = stmt.toStmtText();
			if (text != null){
				writeNewline();
				writeIndent();
				getWriter().append(text);
			}
		}
	} 
//	public void writeStmt(final IStmt stmt){
//		if (stmt == null){
//			return;
//		}
//		
//		if (stmt instanceof ForStmt){
//			write((ForStmt)stmt);
//		}
//		else if (stmt instanceof ForInStmt){
//			write((ForInStmt)stmt);
//		}
//		else if (stmt instanceof DoStmt){
//			write((DoStmt)stmt);
//		}
//		else if (stmt instanceof WhileStmt){
//			write((WhileStmt)stmt);
//		}
//		else if (stmt instanceof IfStmt){
//			writeNewline();
//			writeIndent();
//			write((IfStmt)stmt);
//		}
//		else if (stmt instanceof SwitchStmt){
//			write((SwitchStmt)stmt);
//		}
//		else if (stmt instanceof LabeledStmt){
//			write((LabeledStmt)stmt);
//		}
//		else if (stmt instanceof ThrowStmt){
//			write((ThrowStmt)stmt);
//		}
//		else if (stmt instanceof TryStmt){
//			write((TryStmt)stmt);
//		}
//		else if (stmt instanceof BlockStmt){
//			write((BlockStmt)stmt);
//		}
//		else if (stmt instanceof AssignExpr){
//			write((AssignExpr)stmt);
//		}
//		else if (stmt instanceof RtnStmt){
//			write((RtnStmt)stmt);
//		}
//		else if (stmt instanceof JstVars){
//			writeNewline();
//			writeIndent();
//			getExprGenerator().writeExpr((JstVars)stmt);
//			getWriter().append(";");
//		}
//		else {
//			String text = stmt.toStmtText();
//			if (text != null){
//				writeNewline();
//				writeIndent();
//				getWriter().append(text);
//			}
//		}
//	} 
	
	//
	// Private
	//
	private void write(final AssignExpr stmt){
		if (stmt == null){
			return;
		}
		
		PrintWriter writer = getWriter();
		writeNewline();
		writeIndent();
		if (stmt.getLHS() != null){
			writer.append(stmt.getLHS().toLHSText() + stmt.getOprator().toString());
		}
		getExprGenerator().writeExpr(stmt.getExpr());
		writer.append(";");
		getCtx().getProvider().getJsDocGenerator().writeJsDoc(stmt,false);
	}
	
	private void write(final ArithExpr arithExpr){
		writeNewline();
		writeIndent();
		getExprGenerator().writeExpr(arithExpr);
		getWriter().append(SEMI_COLON);
	}
	
	public void write(final JstVars jstVars){
		writeNewline();
		writeIndent();
		getFragmentGenerator().writeInitializer(jstVars);
		getWriter().append(SEMI_COLON);
		getCtx().getProvider().getJsDocGenerator().writeJsDoc(jstVars);
	}
	
	private void write(final BreakStmt breakStmt){
		JstIdentifier identifier = breakStmt.getIdentifier();
		writeNewline();
		writeIndent();
		getWriter().append("break");
		if (identifier != null){
			getWriter().append(SPACE).append(identifier.toSimpleTermText());
		}
		getWriter().append(SEMI_COLON);
	}
	
	private void write(final ContinueStmt continueStmt){
		JstIdentifier identifier = continueStmt.getIdentifier();
		writeNewline();
		writeIndent();
		getWriter().append("continue");
		if (identifier != null){
			getWriter().append(SPACE).append(identifier.toSimpleTermText());
		}
		getWriter().append(SEMI_COLON);
	}

	private void write(final RtnStmt stmt){
		PrintWriter writer = getWriter();
		writeNewline();
		writeIndent();
		writer.append("return");
		IExpr expr = stmt.getExpression();
		if (expr != null) {
			writer.append(SPACE);
			getExprGenerator().writeExpr(expr);
		}
		writer.append(SEMI_COLON);
		getCtx().getProvider().getJsDocGenerator().writeJsDoc(expr,false);
	}
	
	private void write(final ForStmt stmt){
		if (stmt == null){
			return;
		}
		
		PrintWriter writer = getWriter();
		
		IInitializer initializers = stmt.getInitializers();
		IBoolExpr condition = stmt.getCondition();
		List<IExpr> updaters = stmt.getUpdaters();
		JstBlock body = stmt.getBody();
		writeNewline();
		writeIndent();
		writer.append("for (");
		if (initializers != null){
			getFragmentGenerator().writeInitializer(initializers);
		}
		writer.append(";");
		if (condition!= null){
			writer.append(condition.toBoolExprText());
		}
		writer.append(";");
		for (int i=0; i<updaters.size(); i++){
			IExpr updater = updaters.get(i);
			getExprGenerator().writeExpr(updater);
			if (i < updaters.size() -1) {
				writer.append(",");
			}
		}
		writer.append(")");
		getBodyGenerator().writeBody(body);
	}
	
	private void write(final ForInStmt stmt){
		if (stmt == null){
			return;
		}
		
		PrintWriter writer = getWriter();
		
		writeNewline();
		writeIndent();
		writer.append(MessageFormat.format(ForInStmt.FOR_IN_STMT, stmt.getVar().toLHSText(), stmt.getExpr().toExprText()));
		getBodyGenerator().writeBody(stmt.getBody());
	}
	
	private void write(final WhileStmt stmt){
		if (stmt == null){
			return;
		}
		
		PrintWriter writer = getWriter();
		
		IBoolExpr condition = stmt.getCondition();
		JstBlock body = stmt.getBody();
		writeNewline();
		writeIndent();
		writer.append("while(");
		if (condition != null){
			writer.append(condition.toBoolExprText());
		}
		writer.append(")");
		getBodyGenerator().writeBody(body);
	}
	
	private void write(final DoStmt stmt){
		if (stmt == null){
			return;
		}
		
		PrintWriter writer = getWriter();
		
		IBoolExpr condition = stmt.getCondition();
		JstBlock body = stmt.getBody();
		writeNewline();
		writeIndent();
		writer.append("do");
		getBodyGenerator().writeBody(body);
		writer.append("while(");
		if (condition != null){
			writer.append(condition.toBoolExprText());
		}
		writer.append(");");
	}
	
	private void write(final IfStmt stmt){
		if (stmt == null){
			return;
		}
		
		if (stmt instanceof DispatchStmt){
			DispatchStmt dStmt =((DispatchStmt)stmt);
			IJstType extend = dStmt.getOwnerType().getExtend();

			constructDispatchStmt(dStmt,
					VjoConvention.getNameWithStaticThis(dStmt.getOwnerType()
							.getSimpleName()), VjoConvention.getThisPrefix(), extend!=null && !VjoKeywords.VJO_OBJECT.equals(extend.getName()));
					
		}
		
		IBoolExpr condition = stmt.getCondition();
		JstBlock thenBlock = stmt.getBody();
		
		if (condition == null || thenBlock == null){
			return;
		}
		
		JstBlock elseIfBlock = stmt.getElseIfBlock(false);
		JstBlock elseBlock = stmt.getElseBlock(false);
		
		PrintWriter writer = getWriter();
		
		writer.append("if(");
		writer.append(condition.toBoolExprText());
		writer.append(")");
		getBodyGenerator().writeBody(thenBlock);
		if (elseIfBlock != null && elseIfBlock.getStmts().size() > 0){
			for (IStmt eif: elseIfBlock.getStmts()){
				writer.append("else ");
				write((IfStmt)eif);
			}
		}
		if (elseBlock != null){
			writer.append("else ");
			getBodyGenerator().writeBody(elseBlock);
		}
	}
	
	private void write(final SwitchStmt stmt){
		if (stmt == null){
			return;
		}
		
		IExpr expr = stmt.getExpr();
		List<IStmt> stmts = stmt.getBody().getStmts();
		
		if (expr == null){
			return;
		}
		
		PrintWriter writer = getWriter();
		writeNewline();
		writeIndent();
		writer.append("switch(");
		getExprGenerator().writeExpr(expr);
		writer.append("){");
		if (stmts != null){
			indent();
			for (IStmt s: stmts){
				if (s == null){
					continue;
				}
				if (s instanceof CaseStmt){
					writeNewline();
					writeIndent();
					writer.append(s.toStmtText());
				}
				else if (s instanceof SwitchStmt){
					indent();
					write((SwitchStmt)s);
					outdent();
				}
				else if (s instanceof BlockStmt){
//					indent();
//					writeNewline();
//					writeIndent();
					writeStmt(s);
//					writer.append(s.toStmtText());
//					outdent();
				}
				else {
					indent();
					writeNewline();
					writeIndent();
					writer.append(s.toStmtText());
					outdent();
				}
			}
			outdent();
		}
		writeNewline();
		writeIndent();
		writer.append("}");
	}
	
	private void write(final LabeledStmt stmt){
		if (stmt == null){
			return;
		}
		
		PrintWriter writer = getWriter();
		writeNewline();
		writeIndent();
		writer.append(stmt.getLabel().toSimpleTermText()).append(":");
		writeStmt(stmt.getStmt());
	}
	
	private void write(final ThrowStmt stmt){
		if (stmt == null){
			return;
		}
		
		PrintWriter writer = getWriter();
		writeNewline();
		writeIndent();
		writer.append(stmt.toStmtText());
	}
	
	private void write(final TryStmt stmt){
		if (stmt == null){
			return;
		}
		
		PrintWriter writer = getWriter();
		writeNewline();
		writeIndent();
		writer.append("try ");
		getBodyGenerator().writeBody(stmt.getBody());
		if (stmt.getCatchBlock(false) != null){
			for (IStmt c: stmt.getCatchBlock(false).getStmts()){
				write((CatchStmt)c);
			}
		}
		JstBlock fBlock = stmt.getFinallyBlock(false);
		if (fBlock != null){
			writeNewline();
			writeIndent();
			writer.append("finally ");
			getBodyGenerator().writeBody(fBlock);
		}
	}
	
	private void write(final BlockStmt stmt){
		if (stmt == null){
			return;
		}
		
		writeNewline();
		writeIndent();
		getBodyGenerator().writeBody(stmt.getBody());
	}
	
	private void write(final CatchStmt stmt){
		if (stmt == null){
			return;
		}
		
		PrintWriter writer = getWriter();
		writeNewline();
		writeIndent();
		writer.append("catch(").append(stmt.getException().getName()).append(")");
		getBodyGenerator().writeBody(stmt.getBody());
	}
	
	private void write(final TypeDeclStmt stmt){
		if (stmt == null){
			return;
		}

		JstType localType = stmt.getType();
		if (localType != null){
			//localType.setLocalType(true);
			VjoGenerator typeGenerator = getTypeGenerator();
			writeNewline();
			writeIndent();
			
			IJstType previousType = typeGenerator.getCurrentType();
			typeGenerator.writeVjo(localType);
			typeGenerator.setCurrentType(previousType);
			
		}
	}
	
	private void write(final ThisStmt stmt){
		PrintWriter writer = getWriter();
		writeNewline();
		writeIndent();
		writer.append(stmt.toStmtText());
		/*List<IExpr> args = stmt.getArgs();
		writer.append(JsCoreKeywords.THIS)
			.append(".")
			.append(VjoKeywords.CONSTRUCTS)
			.append("(");
		if (args != null){
			for (int i=0; i<args.size(); i++){
				if (args.get(i) == null){
					continue;
				}
				if (i > 0){
					writer.append(",");
				}
				writer.append(args.get(i).toExprText());
			}
		}
		writer.append(");");*/
	}
	
	private void write(final PtySetter setter){
		writeNewline();
		writeIndent();
		getExprGenerator().writeExpr(setter);
		getWriter().append(SEMI_COLON);
	}
	
	private void write(final MtdInvocationExpr mtdInvocation){
		writeNewline();
		writeIndent();
		getExprGenerator().writeExpr(mtdInvocation);
		getWriter().append(SEMI_COLON);
	}

	private void write(final WithStmt stmt){
		if (stmt == null){
			return;
		}
		PrintWriter writer = getWriter();
		BoolExpr condition = stmt.getCondition();
		JstBlock body = stmt.getBody();
		writeNewline();
		writeIndent();
		writer.append("with (");
		if (condition!= null){
			writer.append(condition.toBoolExprText());
		}
		writer.append(")");
		getBodyGenerator().writeBody(body);
	}

	private void constructDispatchStmt(final DispatchStmt dispatchStmt,
			final String staticQualifier, final String instanceQualifier, boolean hasSuperClass) {
		Map<Integer,List<JstMethod>> mtds = dispatchStmt.getMethods();
		if (mtds.size() == 0){
			return;
		}
		BaseJstNode meth  = dispatchStmt.getParentNode();
		while (meth != null && !(meth instanceof IJstMethod)) {
			meth = meth.getParentNode();
		}
		boolean hasReturn = false;
		if (meth!=null && meth instanceof IJstMethod) {
			IJstType type = ((IJstMethod) meth).getRtnType();
			hasReturn = (type!=null && !"void".equalsIgnoreCase(type.getName()));
		}
		Integer key;
		List<JstMethod> list;
		List<JstArg> args;
		String mtdName = null;
		boolean isConstructor = false;
		boolean isStatic = false;
		//getBody().addStmt(new ExprStmt(new TextExpr("var a = arguments;")));
		// Loop through method groups by number of args
		for (Entry<Integer,List<JstMethod>> entry: mtds.entrySet()){
			key = entry.getKey();
			BoolExpr cond = new BoolExpr(
					new TextExpr("arguments.length"), 
					new TextExpr(key.toString()), 
							BoolExpr.Operator.IDENTICAL);
			list = entry.getValue();
			if (list.size() > 1){
				Collections.sort(list, JstMethodHelper.s_mtdComparator);
			}
			JstMethod first = list.get(0);
			isConstructor = first.isConstructor();
			isStatic = first.isStatic();
			mtdName = first.getOriginalName();
			// For cases with only one method in that group
			if (list.size() == 1 && (!hasSuperClass || isConstructor)){
				JstMethod jstMtd = list.get(0);
				IJstType rtnType = jstMtd.getRtnType();
				String qualifier = jstMtd.isStatic() ? staticQualifier : instanceQualifier;
				MtdInvocationExpr mtdInvocation = new MtdInvocationExpr(qualifier+"."+jstMtd.getName());
				args = jstMtd.getArgs();
//				mtdName = jstMtd.getOriginalName();
				for (int i=0; i<args.size(); i++){
					mtdInvocation.addArg(new TextExpr("arguments[" + i + "]"));
				}
				
				IStmt addReturn = new RtnStmt(mtdInvocation);				
				if (dispatchStmt.getCondition() == null){
					dispatchStmt.setCondition(cond);
					if (rtnType == null || "void".equalsIgnoreCase(rtnType.getName())) {
						dispatchStmt.getBody().addStmt(mtdInvocation);						
					} else {
						dispatchStmt.getBody().addStmt(addReturn);						
					}
				}
				else {
					IfStmt elseIf = new IfStmt();
					dispatchStmt.addChild(elseIf);
					elseIf.setCondition(cond);
					if (rtnType == null || "void".equalsIgnoreCase(rtnType.getName())) {
						elseIf.getBody().addStmt(mtdInvocation);						
					} else {
						elseIf.getBody().addStmt(addReturn);						
					}
					dispatchStmt.addElseStmt(elseIf);
				}
				if (mtdName!=null && !jstMtd.isStatic() && !jstMtd.isConstructor() && hasSuperClass) {
					addBaseDispatch(dispatchStmt, mtdName,hasReturn);

				}
			}
			// For cases with more than one methods in that group
			else {
				IfStmt innerIf = new IfStmt();
				dispatchStmt.getBody().addChild(innerIf);
				if (dispatchStmt.getCondition() == null){
					dispatchStmt.setCondition(cond);
					dispatchStmt.getBody().addStmt(innerIf);
				}
				else {
					IfStmt elseIf = new IfStmt();
					dispatchStmt.addChild(elseIf);
					elseIf.setCondition(cond);
					elseIf.getBody().addStmt(innerIf);
					dispatchStmt.addElseStmt(elseIf);
					innerIf.setParent(elseIf);
				}
				
				List<JstMethod> list1 = new ArrayList<JstMethod>();
				List<JstMethod> list2 = new ArrayList<JstMethod>();
				
				
				
				
				for (JstMethod jstMtd: list){
					if (hasParamArg(jstMtd)){
						list2.add(jstMtd);
					}
					else {
						list1.add(jstMtd);
					}
				}	
				
				processMethod(dispatchStmt, key, list1, innerIf, staticQualifier, instanceQualifier);
				processMethod(dispatchStmt, key, list2, innerIf, staticQualifier, instanceQualifier);
				
				if (mtdName!=null && !isStatic && !isConstructor && hasSuperClass) {
					addBaseDispatch(innerIf, mtdName,hasReturn);
					
				}
				
			}
		}
		if (mtdName!=null && !isStatic && !isConstructor && hasSuperClass) {
			addBaseDispatch(dispatchStmt, mtdName,hasReturn);
		}
	}

	private void addBaseDispatch(final IfStmt dispatchStmt, String mtdName, boolean hasReturn) {
		IfStmt defaultBlock = new IfStmt();
		String baseMeth = "this.base";
//		if (jstMtd.isConstructor()) {
//			defaultBlock.setCondition(new BoolExpr(new TextExpr(
//			"this.base")));
//		} else {
			baseMeth += "."+ mtdName;
			defaultBlock.setCondition(new BoolExpr(new TextExpr(
					"this.base"), new TextExpr(baseMeth),
					BoolExpr.Operator.CONDITIONAL_AND));
//		}
		String baseCall = baseMeth + ".apply(this,arguments)";
		if (hasReturn) {
			baseCall = "return " + baseCall;
		}
		defaultBlock.getBody().addStmt(
				new ExprStmt(new TextExpr(baseCall)));
		dispatchStmt.addElseStmt(defaultBlock);
	}
	
	//
	// Private
	//
	private boolean hasParamArg(JstMethod jstMtd){
		for (JstArg arg: jstMtd.getArgs()){
			if (arg.getType() instanceof JstParamType){
				return true;
			}
		}
		return false;
	}
	
	private void processMethod(final DispatchStmt dispatchStmt,
			final Integer numOfArgs, final List<JstMethod> list,
			final IfStmt innerIf, 
			final String staticQualifier, final String instanceQualifier){
		
		List<JstArg> args;
		BoolExpr cond;
		
		// Loop thru methods in that group
		for (JstMethod jstMtd: list){
			IJstType rtnType = jstMtd.getRtnType();
			String qualifier = jstMtd.isStatic() ? staticQualifier : instanceQualifier;
			MtdInvocationExpr mtdInvocation = new MtdInvocationExpr(qualifier+"."+jstMtd.getName());
			args = jstMtd.getArgs();
			BoolExpr innerCond = new BoolExpr(
					new TextExpr("arguments.length"), 
					new TextExpr(numOfArgs.toString()), 
							BoolExpr.Operator.EQUALS);
			// Loop thru each arg
			for (int i=0; i<args.size(); i++){
				if (args.get(i) == null || args.get(i).getType() == null){
					continue;
				}
//				StringBuffer sb = new StringBuffer("arguments[" + i + "]");
//				if (DataTypeHelper.isNumeric(args.get(i).getType())){
//					IJstType jstType = args.get(i).getType();							
//					if (jstType != null && jstType.getSimpleName() != null){
//						sb.append(".");
//						sb.append(jstType.getSimpleName());
//						sb.append("Value()");
//					}
//				}
//				mtdInvocation.addArg(new TextExpr(sb.toString()));				
				String argStr = "arguments[" + i + "]";
				mtdInvocation.addArg(new TextExpr(argStr));
				
				IExpr mtdExpr = createInstanceOfMethod(args, args.get(i).getType(), argStr);
				
				if (mtdExpr != null) {
					cond = new BoolExpr(mtdExpr);
				} else {
					cond = createCond(args.get(i).getType(), argStr, args.get(i).isVariable(), i);
				}
				if (i == 0){
					innerCond = cond;
				}
				else {
					IExpr e1 = innerCond;
					if (((BoolExpr)innerCond).getOperator() == BoolExpr.Operator.CONDITIONAL_OR){
						e1 = new ParenthesizedExpr(innerCond);
					}

					IExpr e2 = cond;
					if (cond.getOperator() == BoolExpr.Operator.CONDITIONAL_OR){
						e2 = new ParenthesizedExpr(cond);
					}
					
					innerCond = new BoolExpr(e1, e2, BoolExpr.Operator.CONDITIONAL_AND);
				}
			}
			
			IStmt addReturn = new RtnStmt(mtdInvocation);				
			if (innerIf.getCondition() == null){
				innerIf.setCondition(innerCond);
				if (rtnType == null || "void".equalsIgnoreCase(rtnType.getName())) {
					innerIf.getBody().addStmt(mtdInvocation);							
				} else {
					innerIf.getBody().addStmt(addReturn);							
				}
			}
			else {
				IfStmt elseIf = new IfStmt();
				dispatchStmt.addChild(elseIf);
				elseIf.setCondition(innerCond);
				if (rtnType == null || "void".equalsIgnoreCase(rtnType.getName())) {
					elseIf.getBody().addStmt(mtdInvocation);							
				} else {
					elseIf.getBody().addStmt(addReturn);							
				}
				innerIf.addElseStmt(elseIf);
			}
		}
	}

	private MtdInvocationExpr createInstanceOfMethod(List<JstArg> args, IJstType argType, String argExprText) {
		if (DataTypeHelper.isPrimitiveType(argType)
				|| null != DataTypeHelper.getNativeType(argType.getName())
				|| argType instanceof JstArray
				|| DataTypeHelper.isString(argType)
				|| argType instanceof JstParamType
				|| argType.getName().equals("boolean")
				|| !argType.isInterface()
				) {
			return null;
		} else {
			String typeName = DataTypeHelper.getTypeName(argType.getName());
			return new MtdInvocationExpr(new JstIdentifier(VjoKeywords.IS_INSTANCE, new JstIdentifier(typeName + ".clazz")), new TextExpr(argExprText));
		}
	}
	
	private BoolExpr createCond(IJstType type, String arg, boolean isVarArg, int index){
		BoolExpr cond = null;
		if (isVarArg || type instanceof JstArray){
			cond = new BoolExpr(
					new TextExpr(arg), 
					new TextExpr("Array"), 
					BoolExpr.Operator.INSTANCE_OF);	
		}
		else if (DataTypeHelper.isNumericPrimitiveType(type)
				|| DataTypeHelper.isCharPrimitiveType(type)){
			cond = new BoolExpr(new TextExpr("typeof " + arg),
		            new TextExpr("\"number\""),
		            BoolExpr.Operator.EQUALS);
		} 
		else if (type.getName().equals("boolean")) {
			cond = new BoolExpr(new TextExpr("typeof " + arg),
		            new TextExpr("\"boolean\""),
		            BoolExpr.Operator.EQUALS);							
		} 
		else if (DataTypeHelper.isString(type)) {
			BoolExpr left  = new BoolExpr(
					new TextExpr(arg), 
					new TextExpr("String"), 
							BoolExpr.Operator.INSTANCE_OF);	
			BoolExpr right = new BoolExpr(new TextExpr("typeof " + arg),
		            new TextExpr("\"string\""),
		            BoolExpr.Operator.EQUALS);	
			cond = new BoolExpr(left,right,BoolExpr.Operator.CONDITIONAL_OR);
		} 
		else if (type instanceof JstParamType){
			cond = new BoolExpr(
					new TextExpr(arg), 
					new TextExpr("Object"), 
							BoolExpr.Operator.INSTANCE_OF);
		}
		else {
			cond = new BoolExpr(
					new TextExpr(arg), 
					new TextExpr(DataTypeHelper.getTypeName(type.getName())), 
							BoolExpr.Operator.INSTANCE_OF);
		}
		return cond;
	}
	
	
}

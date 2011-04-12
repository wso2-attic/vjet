/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.validator;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticValidator;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.VjoMethodControlFlowTable;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPostAllChildrenListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPostChildListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPreChildListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationVisitorEvent;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.stmt.IfStmt;

public class VjoIfStmtValidator 
	extends VjoSemanticValidator 
	implements IVjoValidationPreChildListener,
		IVjoValidationPostChildListener,
		IVjoValidationPostAllChildrenListener {

	private static List<Class<? extends IJstNode>> s_targetTypes;

	static{
		s_targetTypes = new ArrayList<Class<? extends IJstNode>>();
		s_targetTypes.add(IfStmt.class);
	}
	
	@Override
	public List<Class<? extends IJstNode>> getTargetNodeTypes() {
		return s_targetTypes;
	}
	
	@Override
	public void onPostAllChildrenEvent(final IVjoValidationVisitorEvent event){
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();

		if(!(jstNode instanceof IfStmt)){
			return;
		}
		
		final IfStmt ifStmt = (IfStmt)jstNode;
		final JstMethod method  = lookUpMethod(ifStmt);
		//bugfix 5908
		if(method == null){
			return;
		}
		
		final VjoMethodControlFlowTable flowTable = ctx.getMethodControlFlowTable();
		if(ifStmt.getElseIfBlock(false) == null && ifStmt.getElseBlock(false) == null){
			flowTable.addBranch(method, null);
			flowTable.endBranch(method, null);
		}
		
	}
	
	@Override
	public void onPreChildEvent(final IVjoValidationVisitorEvent event){
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();
		final IJstNode child = event.getVisitChildNode();
		
		if(!(jstNode instanceof IfStmt)){
			return;
		}
		
		final IfStmt ifStmt = (IfStmt)jstNode;
		final JstMethod method  = lookUpMethod(ifStmt);
		//bugfix 5908
		if(method == null){
			return;
		}
		
		final VjoMethodControlFlowTable flowTable = ctx.getMethodControlFlowTable();
		if(child instanceof JstBlock){
			flowTable.addBranch(method, (JstBlock)child);
		}
	}
	
	@Override
	public void onPostChildEvent(final IVjoValidationVisitorEvent event){
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();
		final IJstNode child = event.getVisitChildNode();
		
		if(!(jstNode instanceof IfStmt)){
			return;
		}
		
		final IfStmt ifStmt = (IfStmt)jstNode;
		final JstMethod method  = lookUpMethod(ifStmt);
		//bugfix 5908
		if(method == null){
			return;
		}
		
		final VjoMethodControlFlowTable flowTable = ctx.getMethodControlFlowTable();
		if(child instanceof JstBlock){
			flowTable.endBranch(method, (JstBlock)child);
		}
	}
}

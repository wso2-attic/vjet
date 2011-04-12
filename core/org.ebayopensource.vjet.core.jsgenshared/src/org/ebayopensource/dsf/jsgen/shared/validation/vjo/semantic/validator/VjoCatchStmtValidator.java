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
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPreAllChildrenListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationVisitorEvent;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.stmt.CatchStmt;
import org.ebayopensource.dsf.jst.stmt.TryStmt;

public class VjoCatchStmtValidator extends VjoSemanticValidator 
	implements IVjoValidationPreAllChildrenListener,
		IVjoValidationPostAllChildrenListener{
	
	private static List<Class<? extends IJstNode>> s_targetTypes;

	static{
		s_targetTypes = new ArrayList<Class<? extends IJstNode>>();
		s_targetTypes.add(CatchStmt.class);
	}
	
	@Override
	public List<Class<? extends IJstNode>> getTargetNodeTypes() {
		return s_targetTypes;
	}
	
	@Override
	public void onPreAllChildrenEvent(final IVjoValidationVisitorEvent event){
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();

		if(!(jstNode instanceof CatchStmt)){
			return;
		}
		
		final CatchStmt catchStmt = (CatchStmt)jstNode;
		final JstMethod method  = lookUpMethod(catchStmt);
		//bugfix 5908
		if(method == null){
			return;
		}
		
		final VjoMethodControlFlowTable flowTable = ctx.getMethodControlFlowTable();
		flowTable.addBranch(method, catchStmt.getBody());
	}
	
	@Override
	public void onPostAllChildrenEvent(final IVjoValidationVisitorEvent event){
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();

		if(!(jstNode instanceof CatchStmt)){
			return;
		}
		
		final CatchStmt catchStmt = (CatchStmt)jstNode;
		final JstMethod method  = lookUpMethod(catchStmt);
		//bugfix 5908
		if(method == null){
			return;
		}
		
		final VjoMethodControlFlowTable flowTable = ctx.getMethodControlFlowTable();
		flowTable.endBranch(method, catchStmt.getBody());
		
		//bugfix by roy
		//if catch stmt is the last catch stmt in catch block and there's no following finally block
		//catch stmt should act as if stmt without else
		final IJstNode catchBlock = catchStmt.getParentNode();
		if(catchBlock != null && catchBlock.getChildren().indexOf(catchStmt) == catchBlock.getChildren().size() - 1){
			if(catchBlock.getParentNode() != null && catchBlock.getParentNode() instanceof TryStmt){
				final TryStmt tryStmt = (TryStmt)catchBlock.getParentNode();
				if(tryStmt.getFinallyBlock(false) == null){
					flowTable.addBranch(method, null);
					flowTable.endBranch(method, null);
				}
			}
		}
	}

}

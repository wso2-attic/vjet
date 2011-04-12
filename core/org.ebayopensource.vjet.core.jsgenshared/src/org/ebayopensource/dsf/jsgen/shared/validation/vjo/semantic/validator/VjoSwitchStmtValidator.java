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
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.VjoSemanticRuleRepo;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.BaseVjoSemanticRuleCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPostAllChildrenListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationVisitorEvent;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.stmt.BreakStmt;
import org.ebayopensource.dsf.jst.stmt.RtnStmt;
import org.ebayopensource.dsf.jst.stmt.SwitchStmt;
import org.ebayopensource.dsf.jst.stmt.ThrowStmt;
import org.ebayopensource.dsf.jst.stmt.SwitchStmt.CaseStmt;
import org.ebayopensource.dsf.jst.token.IStmt;

public class VjoSwitchStmtValidator 
	extends VjoSemanticValidator 
	implements IVjoValidationPostAllChildrenListener {

	private static List<Class<? extends IJstNode>> s_targetTypes;

	static{
		s_targetTypes = new ArrayList<Class<? extends IJstNode>>();
		s_targetTypes.add(SwitchStmt.class);
	}
	
	@Override
	public List<Class<? extends IJstNode>> getTargetNodeTypes() {
		return s_targetTypes;
	}

	@Override
	public void onPostAllChildrenEvent(final IVjoValidationVisitorEvent event){
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();
		
		if(!(jstNode instanceof SwitchStmt)){
			return;
		}
		
		final SwitchStmt switchStmt = (SwitchStmt)jstNode;
		if(switchStmt.getBody() == null){
			return;//no flow control validation needed
		}
		
		final JstMethod mtd = lookUpMethod(switchStmt);
		if(mtd == null){
			return;
		}
		
		verifySwitchFlowControl(ctx, switchStmt, mtd);
	}

	/**
	 * refactored by huzhou@ebay.com
	 * @param ctx
	 * @param switchStmt
	 * @param mtd
	 */
	private void verifySwitchFlowControl(final VjoValidationCtx ctx,
			final SwitchStmt switchStmt, 
			final JstMethod mtd) {
		final VjoMethodControlFlowTable mtdCtrlFlowTable = ctx.getMethodControlFlowTable();
		final List<IStmt> stmts = lookUpStmts(switchStmt);
		IStmt pseudoBlock = null;
		boolean hasDefault = false;//check default label
		boolean caseClosed = true;//flag indicating if prev case has been closed or not
	
		//validate flow
		for(IStmt stmt : stmts){
			//case/default opens a new branch if previous case was closed
			if(stmt instanceof CaseStmt){
				if(pseudoBlock == null){
					pseudoBlock = stmt;
				}
				
				final CaseStmt caseStmt = (CaseStmt)stmt;
				if(caseStmt.getExpr() == null){
					hasDefault = true;
				}

				if(caseClosed){
					mtdCtrlFlowTable.addBranch(mtd, pseudoBlock);
					caseClosed = false;
				}
				continue;
			}
			//when caseClosed and the next stmt isn't CaseStmt, this statement becomes unreachable
			else if(caseClosed){
				//report problem as unreachable statement
				final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(stmt, ctx.getGroupId(), new String[]{mtd.getName().getName(), stmt.toStmtText()});
				satisfyRule(ctx, VjoSemanticRuleRepo.getInstance().STMT_SHOULD_BE_REACHABLE, ruleCtx);
				continue;
			}
			//return stmt needs to be added to the open branch as ThrowStmt
			else if(stmt instanceof RtnStmt){
				mtdCtrlFlowTable.addStmt(mtd, (RtnStmt)stmt);
			}
			else if(stmt instanceof ThrowStmt){
				mtdCtrlFlowTable.addStmt(mtd, (ThrowStmt)stmt);
			}
			else if(stmt instanceof BreakStmt){
				//do nothing
			}
			else{
				continue;
			}
			
			//when stmt is one of {RtnStmt, ThrowStmt, BreakStmt} and open branch exists
			//we could close the branch and mark case as closed
			if(pseudoBlock != null){
				mtdCtrlFlowTable.endBranch(mtd, pseudoBlock);
				pseudoBlock = null;
				caseClosed = true;
			}
		}
		//TODO, this is wrong, switch with only case/default, but no break; return; throw;
		//should act as if there's no branching at all
		if(pseudoBlock != null){
			mtdCtrlFlowTable.endBranch(mtd, pseudoBlock);
		}
		else{
			//when switch is done, but no default, it means even if all cases have return
			//it's incomplete as there could be unmatched cases falling through
			//as if there's a branch without termination
			if(!hasDefault){
				mtdCtrlFlowTable.addBranch(mtd, null);
				mtdCtrlFlowTable.endBranch(mtd, null);
			}
		}
	}

	private List<IStmt> lookUpStmts(SwitchStmt switchStmt){
		List<IStmt> caseStmts = new ArrayList<IStmt>();
		
		for(IJstNode node : switchStmt.getBody().getChildren()){
			if(node instanceof IStmt){
				caseStmts.add((IStmt)node);
			}
		}
		
		return caseStmts;
	}
}

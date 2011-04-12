/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.stmt.RtnStmt;
import org.ebayopensource.dsf.jst.stmt.ThrowStmt;
import org.ebayopensource.dsf.jst.token.IStmt;


public class VjoMethodControlFlowTable {

	private Map<IJstMethod, CtrlFlowNode> m_mtd2CtrlFlowMap;
	private Map<JstBlock, Boolean> blockUnreachableValidatedMap;
	
	private static class CtrlFlowNode{
		public static final CtrlFlowNode DEAD = new CtrlFlowNode();
		
		private CtrlFlowNode m_parent;
		private List<CtrlFlowNode> m_children;
		private RtnStmt m_rtnStmt;
        private ThrowStmt m_throwStmt;
		private List<IJstNode> m_scopedVars;
		
		public CtrlFlowNode getParent(){
			return m_parent;
		}
		
		public List<CtrlFlowNode> getChildren(){
			if(m_children == null){
				return Collections.emptyList();
			}
			return m_children;
		}
		
		public RtnStmt getRtnStmt(){
			return m_rtnStmt;
		}
		
        public ThrowStmt getThrowStmt() {
            return m_throwStmt;
        }

		public List<IJstNode> getScopedVars(){
			if(m_scopedVars == null){
				return Collections.emptyList();
			}
			return Collections.unmodifiableList(m_scopedVars);
		}
		
		public void setParent(CtrlFlowNode parent){
			m_parent = parent;
		}
		
		public void appendChild(CtrlFlowNode child){
			if(m_children == null){
				m_children = new ArrayList<CtrlFlowNode>();
			}
			m_children.add(child);
		}
		
		public void removeChild(CtrlFlowNode child){
			if(m_children != null){
				m_children.remove(child);
			}
		}
		
		public void removeAllChildren(){
			if(m_children != null){
				m_children.clear();
			}
		}
		
		public void setRtnStmt(RtnStmt stmt){
			m_rtnStmt = stmt;
		}
		
        public void setThrowStmt(ThrowStmt stmt) {
            m_throwStmt = stmt;
        }

		public void addScopedVar(IJstNode scopedVar){
			if(m_scopedVars == null){
				m_scopedVars = new ArrayList<IJstNode>();
			}
			m_scopedVars.add(scopedVar);
		}
	}
	
    public List<IStmt> lookUpStmt(IJstMethod method) {
		return lookUpStmt(method, true);
	}
	
    public List<IStmt> lookUpStmt(IJstMethod method, boolean fromRoot) {
		if(m_mtd2CtrlFlowMap == null){
			return Collections.emptyList();
		}
		
		final CtrlFlowNode flowTree = m_mtd2CtrlFlowMap.get(method);
		if(flowTree == null){
			return Collections.emptyList();
		}
		
		CtrlFlowNode rootNode = flowTree;
		if(fromRoot){
			while(rootNode.getParent() != null){
				rootNode = rootNode.getParent();
			}
		}
		
		//traverse the tree to flatten the branches
        final List<IStmt> rtnStmts = new ArrayList<IStmt>();
		flatten(rootNode, rtnStmts);
		return rtnStmts;
	}
	
	public List<IJstNode> lookUpScopedVars(IJstMethod method){
		if(m_mtd2CtrlFlowMap == null){
			return Collections.emptyList();
		}
		
		final CtrlFlowNode flowTree = m_mtd2CtrlFlowMap.get(method);
		if(flowTree == null){
			return Collections.emptyList();
		}
		
		final List<IJstNode> scopedVars = new ArrayList<IJstNode>();
		CtrlFlowNode rootNode = flowTree;
		while(rootNode != null){
			scopedVars.addAll(rootNode.getScopedVars());
			rootNode = rootNode.getParent();
		}
		
		return scopedVars;
	}
	
    private boolean flatten(final CtrlFlowNode node, final List<IStmt> stmts) {
		if(node.getRtnStmt() != null){
			stmts.add(node.getRtnStmt());
			return true;
		}
        if (node.getThrowStmt() != null) {
            stmts.add(node.getThrowStmt());
            return true;
        }
		
		boolean allReturned = false;
		for(Iterator<CtrlFlowNode> it = node.getChildren().iterator(); it.hasNext();){
			allReturned = flatten(it.next(), stmts);
			if(!allReturned){
				break;
			}
		}
		
		if(!allReturned){
			stmts.add(null);
		}
		
		return allReturned;
	}
	
	public void addStmt(IJstMethod method, RtnStmt stmt){
		final CtrlFlowNode flowTree = getRtnFlow(method);
		flowTree.setRtnStmt(stmt);
	}
	
	public void addStmt(IJstMethod method, ThrowStmt stmt) {
        final CtrlFlowNode flowTree = getRtnFlow(method);
        flowTree.setThrowStmt(stmt);
    }

	public void addScopedVar(IJstMethod method, IJstNode symbol){
		final CtrlFlowNode flowTree = getRtnFlow(method);
		flowTree.addScopedVar(symbol);
	}

	private CtrlFlowNode getRtnFlow(IJstMethod method) {
		if(m_mtd2CtrlFlowMap == null){
			m_mtd2CtrlFlowMap = new HashMap<IJstMethod, CtrlFlowNode>();
		}

		CtrlFlowNode flowTree = m_mtd2CtrlFlowMap.get(method);
		if(flowTree == null){
			flowTree = new CtrlFlowNode();
			m_mtd2CtrlFlowMap.put(method, flowTree);
		}
		return flowTree;
	}
	
	public void addBranch(IJstMethod method, IJstNode block){
		final CtrlFlowNode flowTree = getRtnFlow(method);
		final CtrlFlowNode branchNode = new CtrlFlowNode();
		
		final IJstNode ifBranchNode = block != null ? block.getParentNode() : null;
		if(ifBranchNode != null && ifBranchNode.getParentNode() == method.getBlock()){
			flowTree.removeAllChildren();
		}
		
		flowTree.appendChild(branchNode);
		branchNode.setParent(flowTree);
		
		m_mtd2CtrlFlowMap.put(method, branchNode);
	}
	
//	public void resetBranch()
	
	public void endBranch(IJstMethod method, IJstNode block){
		final CtrlFlowNode branchNode = getRtnFlow(method);
		if(branchNode.getParent() != null){
			final CtrlFlowNode flowTree = branchNode.getParent();
			m_mtd2CtrlFlowMap.put(method, flowTree);
		}
	}
	
	public void setBlockUnreachableValidated(final JstBlock block, final boolean validated){
		if(blockUnreachableValidatedMap == null){
			blockUnreachableValidatedMap = new HashMap<JstBlock, Boolean>();
		}
		blockUnreachableValidatedMap.put(block, Boolean.valueOf(validated));
	}
	
	public boolean hasBlockUnreachableValidated(final JstBlock block){
		if(blockUnreachableValidatedMap == null){
			return false;
		}
		else{
			final Boolean validated = blockUnreachableValidatedMap.get(block);
			return validated == null ? false : validated.booleanValue();
		}
	}
}

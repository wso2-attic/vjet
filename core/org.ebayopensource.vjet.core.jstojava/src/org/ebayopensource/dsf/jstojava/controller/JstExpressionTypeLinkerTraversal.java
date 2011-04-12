/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.TopLevelJstBlock;
import org.ebayopensource.dsf.jst.expr.FuncExpr;
import org.ebayopensource.dsf.jst.traversal.IJstVisitor;

public class JstExpressionTypeLinkerTraversal<T extends IJstNode> {
	
	
	public static void accept(final IJstNode node, 
			final IJstVisitor visitor){
		accept(node, visitor, new Stack<List<IJstNode>>());
	}
	
	private static void accept(final IJstNode node, 
			final IJstVisitor visitor,
			final Stack<List<IJstNode>> deferredVisitNodesStack) {
		
		if (node == null || visitor == null) {
			return;
		}
		//check scope updates if needed
		final boolean pushNewScope = doesIntroNewScope(node);
		if(pushNewScope){
			deferredVisitNodesStack.push(new LinkedList<IJstNode>());
		}
		
		synchronized (node) { // prevent modification to the node during traversal
			// begin with the generic pre-visit
			visitor.preVisit(node);
			
			// visit children
			try {
				final boolean willVisitChildren = visitor.visit(node);
				final List<? extends IJstNode> children = node.getChildren();
			
				if (willVisitChildren && children != null) {
					for (int i = 0, len = children.size(); i < len; i++){
						final IJstNode child = children.get(i);
						if(!deferredVisitNodesStack.isEmpty()
								&& doesRequireDeferVisit(child)){
							deferredVisitNodesStack.peek().add(child);
							continue;
						}
						accept(child, visitor, deferredVisitNodesStack);
					}
				}
			}
			catch (Exception e) { 
				e.printStackTrace(); //KEEPME
			}
			finally{
				//visit deferred children
				if(pushNewScope && !deferredVisitNodesStack.isEmpty()){
					for(IJstNode deferredVisitNode: deferredVisitNodesStack.pop()){
						accept(deferredVisitNode, visitor, deferredVisitNodesStack);
					}
				}
				
				//end visit
				visitor.endVisit(node);
				// end with the generic post-visit
				visitor.postVisit(node);
			}
		}
	}

	private static boolean doesRequireDeferVisit(final IJstNode node) {
		return node instanceof JstMethod
				&& node.getParentNode() instanceof FuncExpr;
	}

	private static boolean doesIntroNewScope(final IJstNode node) {
		return node instanceof TopLevelJstBlock //floating js
			|| (node instanceof JstBlock && 
					(node.getParentNode() instanceof JstMethod /*normal method*/
							|| node.getParentNode() instanceof JstType) /*init block*/);
	}
}
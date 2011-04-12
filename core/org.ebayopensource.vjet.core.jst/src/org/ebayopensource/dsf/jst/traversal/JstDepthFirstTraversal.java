/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.traversal;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstNode;

public class JstDepthFirstTraversal<T extends IJstNode> {
	
	public static void accept(List<? extends IJstNode> nodes, final IJstVisitor visitor){
		if (nodes == null || visitor == null){
			return;
		}
		
		for (IJstNode node: nodes){
			accept(node, visitor);
		}
	}

	public static void accept(IJstNode node, final IJstVisitor visitor){
		if (node == null || visitor == null) {
			return;
		}
		
		synchronized (node) { // prevent modification to the node during traversal
		
			// begin with the generic pre-visit
			visitor.preVisit(node);
			
			boolean visitChildren = true;
	
			try {
				visitChildren = visitor.visit(node);
			}
			catch (Exception e) { 
				e.printStackTrace(); //KEEPME
			}
			
			List<? extends IJstNode> children = node.getChildren();
			
			if (visitChildren && children != null) {
				for (int i = 0; i < children.size(); i++) {
					IJstNode child = children.get(i);
					accept(child, visitor);
				}
			}
			
			try {
				visitor.endVisit(node);
			
				// end with the generic post-visit
				visitor.postVisit(node);
			}
			catch (Exception e) { 
				e.printStackTrace(); //KEEPME
			}
			
		}
	}
	
	public static void accept(List<? extends IJstNode> nodes, final List<? extends IJstVisitor> visitors){
		if (nodes == null || visitors == null || visitors.isEmpty()){
			return;
		}
		
		for (IJstNode node: nodes){
			accept(node, visitors);
		}
	}

	public static void accept(IJstNode node, final List<? extends IJstVisitor> visitors){
		
		if (node == null || visitors == null || visitors.isEmpty()) {
			return;
		}
		
		// Pre-visit this node
		for (IJstVisitor visitor: visitors){
			visitor.preVisit(node);
		}

		// - visit this node
		List<IJstVisitor> childrenVisitors = new ArrayList<IJstVisitor>();
		for (IJstVisitor visitor: visitors){
			if (visitor.visit(node)){
				childrenVisitors.add(visitor);
			}
		}
		
		// - end this node
		for (IJstVisitor visitor: visitors){
			visitor.endVisit(node);
		}
		
		// - visit children
		if (node.getChildren() != null && !childrenVisitors.isEmpty()) {
			for (int i = node.getChildren().size()-1; i>=0; i--) {
				IJstNode child = node.getChildren().get(i);
				accept(child, childrenVisitors);
			}
		}
		
		// Post-visit this node after normal visit of this node and it's children (recursive)
		for (IJstVisitor visitor: visitors){
			visitor.postVisit(node);
		}
	}
}
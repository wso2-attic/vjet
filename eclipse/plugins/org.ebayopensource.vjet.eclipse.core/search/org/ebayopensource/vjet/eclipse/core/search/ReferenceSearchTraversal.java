/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.vjet.eclipse.core.search;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.traversal.IJstVisitor;

/**
 * create this traversal class to iterate overloaded method/constructor. 
 * 
 * 
 * 
 */
public class ReferenceSearchTraversal {
	/**
	 * Note: including overloaded methods/constrctors
	 * 
	 * @param node
	 * @param visitor
	 */
	public static void accept(IJstNode node, final IJstVisitor visitor){
		if (node == null || visitor == null) 
			return;
		
		synchronized (node) { // prevent modification to the node during traversal
			boolean visitChildren = true;
			try {
				visitChildren = visitor.visit(node);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			//for children nodes
			List<? extends IJstNode> children = node.getChildren();
			if (visitChildren && children != null) {
				for (int i = 0; i < children.size(); i++) {
					IJstNode child = children.get(i);
					accept(child, visitor);
				}
			}
			
			//for overloaded method
	        if (node instanceof IJstMethod
	        		&& ((IJstMethod)node).isDispatcher()) {
	        	for (IJstMethod method : ((IJstMethod)node).getOverloaded()) {
	        		accept(method, visitor);
				}
	        	return;
	        }
		}
	}
	
}

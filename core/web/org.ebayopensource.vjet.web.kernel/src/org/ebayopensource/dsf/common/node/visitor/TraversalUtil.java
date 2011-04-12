/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.node.visitor;

import java.util.Iterator;

import org.ebayopensource.dsf.common.node.IDNodeList;
import org.ebayopensource.dsf.dom.DNode;

public class TraversalUtil {
//	// Avoid using iterators to do this.  While DSF does support child, facet,
//	// and child_facet iterators for conveinence, they are not the most efficient
//	// when doing bulk visitations...  If there are both children and facets, the
//	// children are processed first.
//	static void traverse(
//		final DNode node,
//		final IDNodeVisitor visitor)
//	{
//		if (node == null) { 
//			return;
//		}	
//		
//		// The most common case is having children
//		if (node.hasChildNodes()) {	
//			traverseChildrenOnly(node, visitor) ;
//			if (node.hasDsfFacets()) { // children and facets - children first
//				traverseFacetsOnly(node, visitor) ;
//			}
//			return ;
//		}
//		
//		// we only have the case where the node may have only facets
//		if (node.hasDsfFacets()) {
//			traverseFacetsOnly(node, visitor) ;
//		}
//	}

	// MrP - perf - we should find a way to loop directly over values without having
	// to create an iterator.
	static void traverseFacetsOnly(
		final DNode node,
		final IDNodeVisitor visitor)
	{
		if (!node.hasDsfFacets()) return ;
		Iterator<DNode> itr = node.getDsfFacets().iterator() ;
		while(itr.hasNext()) {
			itr.next().dsfAccept(visitor) ;
		}	
	}
	
	static void traverseChildrenOnly(
		final DNode node,
		final IDNodeVisitor visitor)
	{
		if (!node.hasChildNodes()) return ;
		final IDNodeList children = node.getDsfChildNodes() ;
		final int len = children.getLength();
		for(int i = 0; i < len; i++) {
			children.get(i).dsfAccept(visitor) ;
		}	
	}
}

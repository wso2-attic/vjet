/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom.util;

import org.w3c.dom.Node;

import org.ebayopensource.dsf.common.node.visitor.AbortDNodeTraversalException;
import org.ebayopensource.dsf.common.node.visitor.BreadthFirstDNodeTraversal;
import org.ebayopensource.dsf.common.node.visitor.DNodeVisitStatus;
import org.ebayopensource.dsf.common.node.visitor.DefaultDNodeVisitor;
import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.dom.support.DNamespace;

/**
 * Handle Namespace properly during node creation and node rendering.
 * 
 */
public class HtmlNSHelper {
	//this is used by ElementType.namespace(String, String)
	public static final String PARSER_NS_PREFIX = "urn:x-prefix:";	

	/**
	 * Noramlize namespace for the given node before rendering, 
	 * @param node
	 */
	
	public static void normalizeNSBeforeRender(DNode node) {
		normalizeNS(node, false);
	}
	
	/**
	 * Normalize NS for the given node after parsing or before rendering
	 * @param node - the node to be normalized in terms of namespace
	 * @param bHtmlBuilder - true, normalization request comes from HtmlBuilder
	 * 						false, normalization request comes from HtmlWriter  
	 */
	static void normalizeNS(DNode node, boolean bHtmlBuilder){
		if (!node.hasChildNodes()) {
			return;
		}		
		BreadthFirstDNodeTraversal breadthTraversal = new BreadthFirstDNodeTraversal() ;
		DefaultDNodeVisitor normalizer = null;
		if (bHtmlBuilder == true){
			normalizer = new BuilderNSNormalizer();			
		} else {
			normalizer = new RenderNSNormalizer();
		}
		normalizer.setStrategy(breadthTraversal); 
		node.dsfAccept(normalizer);		
	}	
	/**
	 * Check if the given namespace has prefix, but no namespaceURI.
	 * Apply for node created by htmlBuilder only.
	 * @param ns
	 * @return
	 */
	public static boolean isPrefixOnly(DNamespace ns){		
		return PrefixHelper.isPrefixOnly(ns);
	}
}
/**
 * Normalize namespace by walking through he DOM tree. 
 * 
 * HtmlSaxParser, scans a html string text, from top down.
 * HtmlBuilder, creates DNode object, from bottom up.
 * TopDown scanning and bottomUp creation means that 
 * a node maybe created with prefix but not correct Uri,
 * if the prefix is associated with a namespace at parent/grandparent node.   
 * Because the correct namespace is not visible.
 * For example:
 *  Html input string: <p:mn xmlns:p="uriP">mn_node<p:xyz id="a">unknown</p:xyz></p:mn>
 * 	The first created DElement  is xyz: <p:xyz xmlns:p="urn:x-prefix:p" id="a">unknown </p:xyz>
 * 		namespace is "urn:x-prefix:p",  but should be "uriP"
 * 	
 * So, for DHtmlDocument created by HtmlBuilder and has namespace, 
 * HtmlBuilder should normalize the namespace for all children nodes, before return the
 * DHtmlDocument object to the caller of HtmlBuilderHelper.parseHtmlFragment(..)
 * 
 * This class is created to normalize the namespace after DHtmlDoceument is created.
 * After HtmlNSHelper.normalizeNS(), 
 * DElement will have the correct Uri, if its parent declared one.
 * 
 * 	INPUT: <p:xyz xmlns:p="p" id="a">unknown </p:xyz>
 * 	OUTPUT:<p:xyz xmlns:p="p" id="a">unknown </p:xyz>
 * 
 * 	
 */
class BuilderNSNormalizer extends DefaultDNodeVisitor {	
	public DNodeVisitStatus visit(DNode node) throws AbortDNodeTraversalException {
		if (node.getNodeType() != Node.ELEMENT_NODE
				|| node.getParentNode() == null
				||node.getDsfNamespace()==null)
		{
			return DNodeVisitStatus.CONTINUE;
		}
		if (PrefixHelper.isPrefixOnly(node.getDsfNamespace())) {			
			DNamespace ns2 = ((DNode)node.getParentNode())
			.dsfLookupNamespaceURI(node.getPrefix());
			if (ns2 != null && node.getDsfNamespace() != ns2){
				node.setDsfNamespace(ns2);	
			} 
			//else, repair it to drop the prefix syntax??
		} 
		//Case 2: apply to any element inside a DOM tree
		//should not do this
//		if(node.getPrefix()==null){
//			DNamespace ns2 = ((DNode)node.getParentNode())
//			.dsfLookupPrefix(node.getNamespaceURI());
//			if (ns2 != null && node.getDsfNamespace() != ns2){
//				node.setDsfNamespace(ns2);
//				return DNodeVisitStatus.CONTINUE ;  
//			} 
//		}
		return DNodeVisitStatus.CONTINUE ;                       
	}	
}

class RenderNSNormalizer extends DefaultDNodeVisitor {	
	public DNodeVisitStatus visit(DNode node) throws AbortDNodeTraversalException {
		if (node.getNodeType() != Node.ELEMENT_NODE
				|| node.getParentNode() == null
				||node.getDsfNamespace()==null
				|| PrefixHelper.isPrefixOnly(node.getDsfNamespace()))
		{
			return DNodeVisitStatus.CONTINUE;
		}
		
		DNamespace ns2 = ((DNode)node.getParentNode())
		.dsfLookupPrefix(node.getNamespaceURI());
		if (ns2 != null && node.getDsfNamespace() != ns2){
//			NodeList list = ((DElement) node)
//			.getElementsByTagNameNS(ns2.getNamespaceKey(), "*");
//			for (int i = 0; i < list.getLength(); i ++){
//				((DElement)list.item(i)).setDsfNamespace(ns2);
//			}
			node.setDsfNamespace(ns2);
		} 
		return DNodeVisitStatus.CONTINUE ;                       
	}	
}


/**
 * Check if the given namespace contains only prefix,
 * based on the namespaceURI pattern.
 *
 */
class PrefixHelper{
	static boolean isPrefixOnly(DNamespace ns){	
		int idx = ns ==null? -1: 
			ns.getNamespaceKey().indexOf(HtmlNSHelper.PARSER_NS_PREFIX);
		if (idx != -1) {
			//xmlns:p="urn:x-prefix:p"
			return ns.getNamespaceKey()
			.substring(HtmlNSHelper.PARSER_NS_PREFIX.length())
				.equals(ns.getPrefix());
		}		
		//<p:xyz xmlns:p="p" id = a>unknown </p:xyz>	
		return false;			
	}
}

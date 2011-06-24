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
package org.ebayopensource.vjet.eclipse.internal.codeassist.select;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.vjet.eclipse.core.IVjoSourceModule;
import org.ebayopensource.vjet.eclipse.internal.codeassist.select.translator.IJstNodeTranslator;
import org.ebayopensource.vjet.eclipse.internal.codeassist.select.translator.JstToDLTKNodeTranslator;
import org.eclipse.dltk.mod.core.IModelElement;

/**
 * 
 * 
 */
public class JstNodeDLTKElementResolver {

	/**
	 * utility class, not need constructor
	 */
	private JstNodeDLTKElementResolver() {
	}

	/**
	 * Convert the given jst node to the corresponding DLTK model element.
	 * @param module 
	 * 
	 * @param node
	 * @return
	 */
	public static IModelElement[] convert(IVjoSourceModule module, IJstNode node) {
		IJstNodeTranslator nodeTranslator = JstToDLTKNodeTranslator
				.getNodeTranslator(node);
		if (nodeTranslator == null)
			return null;
//		System.out.println(nodeTranslator.getClass().getName());
		return nodeTranslator.convert(module, node);
	}
	/**
	 * Convert the given jst node to the corresponding DLTK model element.
	 * @param module 
	 * 
	 * @param node
	 * @return
	 */
	public static IModelElement[] convert(IJstNode node) {
		IJstNodeTranslator nodeTranslator = JstToDLTKNodeTranslator
		.getNodeTranslator(node);
		if (nodeTranslator == null)
			return null;
		return nodeTranslator.convert(null, node);
	}

	/**
	 * Lookup the binding of the given jst node, or null if cannot resolve.
	 * 
	 * @param jstNode
	 * @return
	 */
	public static IJstNode lookupBinding(IJstNode jstNode) {
		IJstNodeTranslator nodeTranslator = JstToDLTKNodeTranslator
				.getNodeTranslator(jstNode);
		if (nodeTranslator == null)
			return null;

		return nodeTranslator.lookupBinding(jstNode);
	}

	/**
	 * Lookup the binding of the given jst node, and then convert the binding to
	 * dltk model.
	 * 
	 * @param jstNode
	 * @return
	 */
	public static IModelElement[] lookupAndConvert(IJstNode jstNode) {
		return convert(null, lookupBinding(jstNode));
	}

}

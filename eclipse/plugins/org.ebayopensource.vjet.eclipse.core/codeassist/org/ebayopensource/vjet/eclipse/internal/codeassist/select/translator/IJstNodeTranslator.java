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
package org.ebayopensource.vjet.eclipse.internal.codeassist.select.translator;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.vjet.eclipse.core.IVjoSourceModule;
import org.eclipse.dltk.mod.core.IModelElement;

/**
 * jst node translator (resolved to dltk model elements)
 * 
 * 
 * 
 */
public interface IJstNodeTranslator {
	/**
	 * Convert the given jst node to the corresponding DLTK model element.
	 * 
	 * @param node
	 * @return
	 */
	public IModelElement[] convert(IJstNode node);
	/**
	 * Convert the given jst node to the corresponding DLTK model element.
	 * 
	 * @param node
	 * @param groupName
	 * @return
	 */
	public IModelElement[] convert(IVjoSourceModule module, IJstNode node);

	/**
	 * Lookup the binding of the given jst node, or null if cannot resolve.
	 * 
	 * @param jstNode
	 * @return
	 */
	public IJstNode lookupBinding(IJstNode jstNode);
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.search;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstProxyType;
import org.ebayopensource.dsf.jst.ts.JstQueryExecutor;
import org.ebayopensource.dsf.jstojava.translator.JstUtil;
import org.ebayopensource.dsf.ts.TypeSpace;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.eclipse.dltk.mod.core.IMember;
import org.eclipse.dltk.mod.core.ISourceRange;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.VjoSourceModule;

/**
 * Utility methods for search
 * 
 *  Ouyang
 * 
 */
final class SearchUtil {

	/**
	 * Get the Jst node need search from {@link SearchQueryParameters}, or null
	 * if cannot parse
	 * 
	 * @param parameters
	 * @param typeSpace
	 * @return {@link IJstNode}
	 */
	public static IJstNode getSearchedJstNode(SearchQueryParameters parameters,
			TypeSpace<IJstType, IJstNode> typeSpace) {
		if (parameters == null || typeSpace == null) {
			return null;
		}
		if (!(parameters.getElement() instanceof IMember)) {
			return null;
		}

		// get vjo source module
		IMember searchedElement = (IMember) parameters.getElement();
		if (!(searchedElement.getSourceModule() instanceof VjoSourceModule)) {
			return null;
		}
		VjoSourceModule module = (VjoSourceModule) searchedElement
				.getSourceModule();

		// get root jst type of the module
		TypeName rootTypeName = module.getTypeName();
		JstQueryExecutor queryExecutor = new JstQueryExecutor(typeSpace);
		IJstType rootType = queryExecutor.findType(rootTypeName);
		if (rootType == null) {
			return null;
		}

		// get offset
		ISourceRange sourceRange;
		try {
			sourceRange = searchedElement.getSourceRange();
		} catch (ModelException e) {
			VjetPlugin.error(e.getMessage(), e);
			return null;
		}
		int startOffset = sourceRange.getOffset();
		int endOffset = startOffset;

		return JstUtil.getLeafNode(rootType, startOffset, endOffset);
	}

	/**
	 * True is the property belongs to "Vj$Type" type which is the type of
	 * "this.vj$".
	 * 
	 * @param jstProperty
	 * @return boolean
	 */
	public static boolean isVjDollarProp(IJstProperty jstProperty) {
		if (jstProperty == null) {
			return false;
		}
		return "Vj$Type".equals(jstProperty.getOwnerType().getName());
	}

	/**
	 * Lookup the given IJstType, and find the target type if it is
	 * JstProxyType. Or return the given type if others.
	 * 
	 * @param type
	 * @return IJstType
	 */
	public static IJstType lookupJstType(IJstType type) {
		if (type == null) {
			return null;
		}
		if (type instanceof JstProxyType) {
			return lookupJstType(((JstProxyType) type).getType());
		}
		return type;
	}

}

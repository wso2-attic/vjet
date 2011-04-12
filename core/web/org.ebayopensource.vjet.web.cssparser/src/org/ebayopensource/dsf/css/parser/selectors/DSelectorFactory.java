/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*
 * SelectorFactoryImpl.java
 *
 * Modified from Open-Source CSS Parser (Steady State Software) under
 * GNU Lesser General Public License.
 * 
 */

package org.ebayopensource.dsf.css.parser.selectors;

import org.ebayopensource.dsf.css.sac.CssException;
import org.ebayopensource.dsf.css.sac.ICharacterDataSelector;
import org.ebayopensource.dsf.css.sac.ICondition;
import org.ebayopensource.dsf.css.sac.IConditionalSelector;
import org.ebayopensource.dsf.css.sac.IDescendantSelector;
import org.ebayopensource.dsf.css.sac.IElementSelector;
import org.ebayopensource.dsf.css.sac.IGeneralSiblingSelector;
import org.ebayopensource.dsf.css.sac.INegativeSelector;
import org.ebayopensource.dsf.css.sac.IProcessingInstructionSelector;
import org.ebayopensource.dsf.css.sac.ISelector;
import org.ebayopensource.dsf.css.sac.ISelectorFactory;
import org.ebayopensource.dsf.css.sac.ISiblingSelector;
import org.ebayopensource.dsf.css.sac.ISimpleSelector;

public class DSelectorFactory implements ISelectorFactory {

	public IConditionalSelector createConditionalSelector(
		final ISimpleSelector selector,
		final ICondition condition)
		throws CssException
	{
		return new DConditionalSelector(selector, condition);
	}

	public ISimpleSelector createAnyNodeSelector() throws CssException {
		throw new CssException(CssException.SAC_NOT_SUPPORTED_ERR);
	}

	public ISimpleSelector createRootNodeSelector() throws CssException {
		throw new CssException(CssException.SAC_NOT_SUPPORTED_ERR);
	}

	public INegativeSelector createNegativeSelector(final ISimpleSelector selector)
		throws CssException
	{
		throw new CssException(CssException.SAC_NOT_SUPPORTED_ERR);
	}

	public IElementSelector createElementSelector(
		final String namespaceURI,
		final String localName)
		throws CssException
	{
		if (namespaceURI != null) {
			throw new CssException(CssException.SAC_NOT_SUPPORTED_ERR);
		}
		return new DElementSelector(localName);
	}

	public ICharacterDataSelector createTextNodeSelector(final String data)
		throws CssException
	{
		throw new CssException(CssException.SAC_NOT_SUPPORTED_ERR);
	}

	public ICharacterDataSelector createCDataSectionSelector(final String data)
		throws CssException
	{
		throw new CssException(CssException.SAC_NOT_SUPPORTED_ERR);
	}

	public IProcessingInstructionSelector createProcessingInstructionSelector(
		final String target,
		final String data)
		throws CssException
	{
		throw new CssException(CssException.SAC_NOT_SUPPORTED_ERR);
	}

	public ICharacterDataSelector createCommentSelector(final String data)
		throws CssException {
		throw new CssException(CssException.SAC_NOT_SUPPORTED_ERR);
	}

	public IElementSelector createPseudoElementSelector(
		final String namespaceURI,
		final String pseudoName)
		throws CssException
	{
		if (namespaceURI != null) {
			throw new CssException(CssException.SAC_NOT_SUPPORTED_ERR);
		}
		return new DPseudoElementSelector(pseudoName);
		
	}

	public IDescendantSelector createDescendantSelector(
		final ISelector parent,
		final ISimpleSelector descendant)
		throws CssException 
	{
		return new DDescendantSelector(parent, descendant);
	}

	public IDescendantSelector createChildSelector(
		final ISelector parent,
		final ISimpleSelector child)
		throws CssException 
	{
		return new DChildSelector(parent, child);
	}
	
	public IGeneralSiblingSelector createGeneralSiblingSelector(
		final short nodeType,
		final ISelector child,
		final ISimpleSelector generalSibling)
		throws CssException
	{
		return new DGeneralSiblingSelector(nodeType, child, generalSibling);
	}

	public ISiblingSelector createDirectAdjacentSelector(
		final short nodeType,
		final ISelector child,
		final ISimpleSelector directAdjacent)
		throws CssException
	{
		return new DDirectAdjacentSelector(nodeType, child, directAdjacent);
	}
}

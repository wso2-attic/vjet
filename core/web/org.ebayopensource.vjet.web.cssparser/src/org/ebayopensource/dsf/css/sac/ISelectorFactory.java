/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.sac;
public interface ISelectorFactory {
	
	IConditionalSelector createConditionalSelector(
		ISimpleSelector simpleselector,
		ICondition condition)
			throws CssException;
	
	ISimpleSelector createAnyNodeSelector() throws CssException;
	
	ISimpleSelector createRootNodeSelector() throws CssException;
	
	INegativeSelector createNegativeSelector(ISimpleSelector simpleselector)
		throws CssException;
	
	IElementSelector createElementSelector(
		String string,
		String string_0_)
		throws CssException;
	
	ICharacterDataSelector createTextNodeSelector(String string)
		throws CssException;
	
	ICharacterDataSelector createCDataSectionSelector(String string)
		throws CssException;
	
	IProcessingInstructionSelector createProcessingInstructionSelector(
		String string,
		String string_1_)
		throws CssException;
	
	ICharacterDataSelector createCommentSelector(String string)
		throws CssException;
	
	IElementSelector createPseudoElementSelector(
		String string,
		String string_2_)
		throws CssException;
	
	IDescendantSelector createDescendantSelector(
		ISelector selector,
		ISimpleSelector simpleselector)
		throws CssException;
	
	IGeneralSiblingSelector createGeneralSiblingSelector(
		short i,
		ISelector selector,
		ISimpleSelector simpleselector)
		throws CssException;
	
	IDescendantSelector createChildSelector(
		ISelector selector,
		ISimpleSelector simpleselector)
		throws CssException;
	
	ISiblingSelector createDirectAdjacentSelector(
		short i,
		ISelector selector,
		ISimpleSelector simpleselector)
		throws CssException;
}

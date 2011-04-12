/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.sac;

public interface IConditionFactory {
	ICombinatorCondition createAndCondition(
		ICondition condition,
		ICondition condition_0_) throws CssException;
	
//	ICombinatorCondition createOrCondition(
//		ICondition condition,
//		ICondition condition_1_)
//		throws CssException;

//	INegativeCondition createNegativeCondition(ICondition condition)
//		throws CssException;

//	IPositionalCondition createPositionalCondition(
//		int i,
//		boolean bool,
//		boolean bool_2_)
//		throws CssException;

	/** 
	 * <ul>
	 * <li> E[attr] {color:red} 
	 * <li> E[attr = "foo"] {color:red}
	 * </ul>
	 */
	IAttributeCondition createAttributeCondition(
		String string,
		String string_3_,
		boolean bool,
		String string_4_) throws CssException;

	/** E F {color:red} */
	IAttributeCondition createIdCondition(String string)
		throws CssException;

	/** E:someFunction(...) {color:red} */
	ICondition createPseudoFunction(String function, String argValue) throws CssException ;
//	ILangCondition createLangCondition(String string)
//		throws CssException;

	/** E[attr ~= "foo"] {color:red} */
	IAttributeCondition createOneOfAttributeCondition(
		String string,
		String string_5_,
		boolean bool,
		String string_6_) throws CssException;
	
	/** E[attr *= "foo"] {color:red} */
	IAttributeCondition createContainsAtLeastOneAttributeCondition(
		String string,
		String string_5_,
		boolean bool,
		String string_6_) throws CssException;
	
	/** E[attr ^= "foo"] {color:red} */
	IAttributeCondition createStartsWithAttributeCondition(
		String string,
		String string_5_,
		boolean bool,
		String string_6_) throws CssException;
	
	/** E[attr $= "foo"] {color:red} */
	IAttributeCondition createEndsWithAttributeCondition(
		String string,
		String string_5_,
		boolean bool,
		String string_6_) throws CssException;
	
	/** E[foo |= "bar"] */
	IAttributeCondition createIsOrStartsWithAttributeCondition(
		String string,
		String string_7_,
		boolean bool,
		String string_8_) throws CssException;

	/** .E {color:red */
	IAttributeCondition createClassCondition(
		String string,
		String string_9_) throws CssException;

	/** E:pseudo {color:red} */
	IAttributeCondition createPseudoClassCondition(
		String string,
		String string_10_) throws CssException;

//	ICondition createOnlyChildCondition() throws CssException;

//	ICondition createOnlyTypeCondition() throws CssException;

//	IContentCondition createContentCondition(String string)
//		throws CssException;
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*
 * ConditionFactoryImpl.java
 *
 * Modified from Open-Source CSS Parser (Steady State Software) under
 * GNU Lesser General Public License.
 * 
 */

package org.ebayopensource.dsf.css.parser.selectors;

import org.ebayopensource.dsf.css.sac.CssException;
import org.ebayopensource.dsf.css.sac.IAttributeCondition;
import org.ebayopensource.dsf.css.sac.ICombinatorCondition;
import org.ebayopensource.dsf.css.sac.ICondition;
import org.ebayopensource.dsf.css.sac.IConditionFactory;

public class DConditionFactory implements IConditionFactory {
	/** E F {color:red} */
	public ICombinatorCondition createAndCondition(
		ICondition first,
		ICondition second) throws CssException 
	{
		return new DAndCondition(first, second);
	}

//	public ICombinatorCondition createOrCondition(
//		ICondition first,
//		ICondition second) throws CssException
//	{
//		throw new CssException(CssException.SAC_NOT_SUPPORTED_ERR);
//	}

//	public INegativeCondition createNegativeCondition(ICondition condition)
//		throws CssException
//	{
//		return new DNegativeCondition(condition);
//	}

//	public IPositionalCondition createPositionalCondition(
//		int position,
//		boolean typeNode,
//		boolean type) throws CssException
//	{
//		throw new CssException(CssException.SAC_NOT_SUPPORTED_ERR);
//	}

	/** 
	 * <ul>
	 * <li> E[attr] {color:red} 
	 * <li> E[attr = "foo"] {color:red}
	 * </ul>
	 */
	public IAttributeCondition createAttributeCondition(
		String localName,
		String namespaceURI,
		boolean specified,
		String value) throws CssException
	{
		//        if ((namespaceURI != null) || !specified) {
		//            throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
		//        } else {
		return new DAttributeCondition(localName, value);
		//        }
	}
	
	/** #E {color:red} */
	public IAttributeCondition createIdCondition(String value)
		throws CssException
	{
		return new DIdCondition(value);
	}

	/** E:func(...) {color:red} */
	public ICondition createPseudoFunction(
		final String function, final String argValue) throws CssException
	{
		final String lowercaseFunction = function.toLowerCase() ;
		if (lowercaseFunction.equals("lang")) {
			return new DLangCondition(argValue);
		}
		else if (lowercaseFunction.equals("not")) {
			return new DNotCondition(argValue) ;
		}
		else if (lowercaseFunction.equals("nth-child")) {
			return new DNthChildCondition(argValue) ;
		}
		else if (lowercaseFunction.equals("nth-last-child")) {
			return new DNthLastChildCondition(argValue) ;
		}
		else if (lowercaseFunction.equals("nth-of-type")) {
			return new DNthOfTypeCondition(argValue) ;
		}
		else if (lowercaseFunction.equals("nth-last-of-type")) {
			return new DNthLastOfTypeCondition(argValue) ;
		}
		else if (lowercaseFunction.startsWith("-moz")) {
			return new DMozPseudoCondition(function, argValue) ;
		}
		
		throw new RuntimeException("Can't create pseudo function: " + function) ;
	}

	/** E[attr ~= "foo"] {color:red} */
	public IAttributeCondition createOneOfAttributeCondition(
		String localName,
		String namespaceURI,
		boolean specified,
		String value) throws CssException
	{
		//        if ((namespaceURI != null) || !specified) {
		//            throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
		//        } else {
		return new DOneOfAttributeCondition(localName, value);
		//        }
	}

	/** E[attr ^= "foo"] {color:red} */
	public IAttributeCondition createStartsWithAttributeCondition(
		String localName,
		String namespaceURI,
		boolean specified,
		String value) throws CssException
	{
		return new DStartsWithAttributeCondition(localName, value);
	}

	/** E[attr $= "foo"] {color:red} */
	public IAttributeCondition createEndsWithAttributeCondition(
		String localName,
		String namespaceURI,
		boolean specified,
		String value) throws CssException
	{
		return new DEndsWithAttributeCondition(localName, value);
	}
	
	/** E[attr *= "foo"] {color:red} */
	public IAttributeCondition createContainsAtLeastOneAttributeCondition(
		String localName,
		String namespaceURI,
		boolean specified,
		String value) throws CssException
	{
		return new DContainsAtLeastOneAttributeCondition(localName, value);
	}
	
	/** E[foo |= "bar"] */
	public IAttributeCondition createIsOrStartsWithAttributeCondition(
		String localName,
		String namespaceURI,
		boolean specified,
		String value) throws CssException
	{
		//        if ((namespaceURI != null) || !specified) {
		//            throw new CSSException(CSSException.SAC_NOT_SUPPORTED_ERR);
		//        } else {
		return new DBeginHyphenAttributeCondition(localName, value);
		//        }
	}

	/** .E {color:red */
	public IAttributeCondition createClassCondition(
		String namespaceURI,
		String value)
		throws CssException
	{
		return new DClassCondition(value);
	}

	/** E:somePseudo {color:red} */
	public IAttributeCondition createPseudoClassCondition(
		String namespaceURI,
		String value)
		throws CssException
	{
		return new DPseudoClassCondition(value);
	}

//	public ICondition createOnlyChildCondition() throws CssException {
//		throw new CssException(CssException.SAC_NOT_SUPPORTED_ERR);
//	}
//
//	public ICondition createOnlyTypeCondition() throws CssException {
//		throw new CssException(CssException.SAC_NOT_SUPPORTED_ERR);
//	}

//	public IContentCondition createContentCondition(String data)
//		throws CssException
//	{
//		throw new CssException(CssException.SAC_NOT_SUPPORTED_ERR);
//	}
}

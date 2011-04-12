/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.sac;

public interface ICondition {
	short getConditionType();
	
	/** E F {color:red} */
	short SAC_AND_CONDITION = 0;
//	short SAC_OR_CONDITION = 1;
//	short SAC_NEGATIVE_CONDITION = 2;
//	short SAC_POSITIONAL_CONDITION = 3;
	
	/**
	 * <ul>
	 * <li> E[attr] {color:red}
	 * <li> E[attr = "foo"] {color:red}
	 * </ul>
	 */
	short SAC_ATTRIBUTE_CONDITION = 4;
	
	/** #id {color:red} */
	short SAC_ID_CONDITION = 5;
	
	/** E:lang(fr) */
	short SAC_LANG_CONDITION = 6;
	
	/** E[attr ~= "foo"] {color:red} */
	short SAC_ONE_OF_ATTRIBUTE_CONDITION = 7;
	
	/** E[foo |= "bar"] */
	short SAC_BEGIN_HYPHEN_ATTRIBUTE_CONDITION = 8;
	
	/** .E {color:red} */
	short SAC_CLASS_CONDITION = 9;
	
	/** E:pseudo {color:red} */
	short SAC_PSEUDO_CLASS_CONDITION = 10;  // see SAC_MOZ_PSEUDO....
	
//	short SAC_ONLY_CHILD_CONDITION = 11;
//	short SAC_ONLY_TYPE_CONDITION = 12;
//	short SAC_CONTENT_CONDITION = 13;
	
	/** E[attr ^= "foo"] {color:red} */
	short SAC_STARTS_WITH_ATTRIBUTE_CONDITION = 14;
	
	/** E[attr $= "foo"] {color:red}  */
	short SAC_ENDS_WITH_ATTRIBUTE_CONDITION = 15;
	
	/** E[attr *= "foo"] {color:red} */
	short SAC_CONTAINS_AT_LEAST_ONE_ATTRIBUTE_CONDITION = 16 ;
	
	/** E:not(simpleSelector) {color:red} */
	short SAC_NOT_CONDITION = 17 ;
	
	/** E:nth-child(n) */
	short SAC_NTH_CHILD_CONDITION = 18 ;
	
	/** E:nth-last-child(n) */
	short SAC_NTH_LAST_CHILD_CONDITION = 19 ;
	
	/** E:nth-of-type(n) {color:red} */
	short SAC_NTH_OF_TYPE_CONDITION = 20 ;
	
	/** E:nth-last-of-type(n) {color:red} */
	short SAC_NTH_LAST_OF_TYPE_CONDITION = 21 ;
	
	short SAC_MOZ_PSEUDO_CONDITION = 22 ;
}

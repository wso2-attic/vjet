/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.sac;

public interface ISelector extends Cloneable {
	
	short SAC_CONDITIONAL_SELECTOR = 0;
	
	short SAC_ANY_NODE_SELECTOR = 1;
	
	short SAC_ROOT_NODE_SELECTOR = 2;
	
	short SAC_NEGATIVE_SELECTOR = 3;
	
	short SAC_ELEMENT_NODE_SELECTOR = 4;
	
	short SAC_TEXT_NODE_SELECTOR = 5;
	
	short SAC_CDATA_SECTION_NODE_SELECTOR = 6;
	
	short SAC_PROCESSING_INSTRUCTION_NODE_SELECTOR = 7;
	
	short SAC_COMMENT_NODE_SELECTOR = 8;
	
	short SAC_PSEUDO_ELEMENT_SELECTOR = 9;
	
	short SAC_DESCENDANT_SELECTOR = 10;
	
	short SAC_CHILD_SELECTOR = 11;
	
	short SAC_DIRECT_ADJACENT_SELECTOR = 12;
	
	short SAC_GENERAL_SIBLING_SELECTOR = 13 ;
    
	short getSelectorType();
	
	Object clone() throws CloneNotSupportedException;
}

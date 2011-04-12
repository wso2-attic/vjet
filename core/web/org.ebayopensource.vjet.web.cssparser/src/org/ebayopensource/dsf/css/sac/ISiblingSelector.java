/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.sac;

/** E + F {color:red} */
public interface ISiblingSelector extends ISelector {
	short ANY_NODE = 201;
    
	short getNodeType();
    
	ISelector getSelector();
    
	ISimpleSelector getSiblingSelector();
}

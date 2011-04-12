/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.sac;

/**
 * <ul>
 * <li> #E {color:red}  
 * <li> .E {color:red} 
 * 	
 * <li> E:somePseudo {color:red}
 * 
 * <li> E[attr] {color:red}
 * <li> E[attr = "foo"] {color:red}
 * 
 * <li> E[foo |= "bar"]  {color:red}
 * <li> E[attr *= "foo"] {color:red}
 * <li> E[attr $= "foo"] {color:red}
 * <li> E[attr ^= "foo"] {color:red}
 * </ul>
 */
public interface IAttributeCondition extends ICondition {
	String getNamespaceURI();
	String getLocalName();
	boolean getSpecified();
	String getValue();
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.ovs;

/*
 * Represents an option in set of legal option for a an input field
 * For example in a HTML Select element
 * <Select name="Format">
 * 	<Option value="1">Online Auction</Option>
 * </Select>
 * 
 * The CaptionedValue models the option element and all its related info
 */ 

public interface ICaptionedValue<T> {
	
	/**
	 * Answer value.
	 * @return T.
	 */
	T getValue();
	
	/**
	 * Answer caption for the value
	 * @return String
	 */
	String getCaption();
}

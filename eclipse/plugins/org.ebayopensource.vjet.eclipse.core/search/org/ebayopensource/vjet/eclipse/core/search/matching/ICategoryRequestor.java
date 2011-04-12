/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.vjet.eclipse.core.search.matching;

/**
 *  Describe request category for search data in source.
 * 
 * 
 * 
 */
public interface ICategoryRequestor {
	String TEXT_CATEGORY = "text_category";
	String TYPE_CATEGORY = "type_category";

	public String getCategory();
}

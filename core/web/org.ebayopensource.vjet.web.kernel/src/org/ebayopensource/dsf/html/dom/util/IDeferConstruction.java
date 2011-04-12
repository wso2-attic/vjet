/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom.util;

/**
 * A Component implements this interface if it wants to defer the construction
 * of events or DOM to better analyze properties or data that may effect
 * construction.
 * 
 */
public interface IDeferConstruction {
	/**
	 * This method is the last chance that a component has to finish its
	 * construction before doc processing begins. This allows deferred
	 * construction to work with resources, such as JS events and instance-based
	 * CSS.
	 * 
	 */
	void finish();
}

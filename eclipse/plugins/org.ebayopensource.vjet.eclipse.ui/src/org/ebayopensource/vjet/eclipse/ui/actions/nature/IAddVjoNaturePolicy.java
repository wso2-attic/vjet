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
package org.ebayopensource.vjet.eclipse.ui.actions.nature;

import org.eclipse.core.resources.IProject;

/**
 * 
 *
 */
public interface IAddVjoNaturePolicy {
	/**
	 * whether accept this adding vjo nature request to the project
	 * 
	 * @param project  the project waiting for adding a vjo nature
	 * @return
	 */
	public boolean accept(IProject project);
	
	/**
	 * process the request for adding vjo nature
	 * 
	 * @param project
	 */
	public void addVjoNature(IProject project);
}

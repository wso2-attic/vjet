/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.util;

/**
 * Interface for asynchronous window tasks
 */
public interface IAsyncTask {
	
	/**
	 * Answer whether the asynchronous task is ready to provide jsCode to
	 * be executed as window task
	 * @return boolean
	 */
	boolean isReady();
	
	/**
	 * Answer the jsCode to be executed. This method should be called
	 * after isReady() answers true
	 * @return Object any supported type by <code>WindowTask</code>
	 */
	Object getJsCode();
}

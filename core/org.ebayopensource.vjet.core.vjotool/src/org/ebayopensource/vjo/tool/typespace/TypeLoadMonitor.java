/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.typespace;

/**
 * This interface define methods for monitor and control loading types to the type space.
 * 
 * 
 *
 */
public abstract class TypeLoadMonitor {

	private boolean isCanceled = false;
	
	/**
	 * Return true if the process of the loading is canceled.
	 * 
	 * @return true if the process of the loading is canceled.
	 */
	public boolean isCanceled() {
		return isCanceled;
	}

	/**
	 * If set true then process of the loading make as canceled.
	 * 
	 * @param isCancel flag for control process loading.
	 */
	public void setCanceled(boolean isCancel) {
		this.isCanceled = isCancel;
	}

	/**
	 * Reports preparation type list started
	 */
	public abstract void preparationTypeListStarted();
	
	/**
	 * Reports preparation type list finished
	 */
	public abstract void preparationTypeListFinished();
	
	/**
	 * Reports process loading types to type space started
	 * 
	 * @param typesCount count of the types
	 */
	public abstract void loadTypeListStarted(int typesCount);
	
	/**
	 * Reports process loading types to type space finished
	 */
	public abstract void loadTypeListFinished();
	
	/**
	 * Reports process loading type to type space started
	 * 
	 * @param group group of the type
	 * 
	 * @param file filename of the type
	 */
	public abstract void loadTypeStarted(String group, String file);
	
	/**
	 * Reports process percent to type space started
	 * 
	 * @param percent
	 */
	public abstract void loadTypeStarted(float percent);
	
	/**
	 * Reports process loading type to type space finished
	 */
	public abstract void loadTypeFinished();
		
		
	

}

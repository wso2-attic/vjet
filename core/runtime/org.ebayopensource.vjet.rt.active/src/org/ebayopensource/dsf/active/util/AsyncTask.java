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
 * Default implementation for <code>IAsyncTask</code>
 */
public class AsyncTask implements IAsyncTask {
	
	private Object m_jsCode;
	private boolean m_isReady = false;

	//
	// Satisfy IAsyncTask
	//
	/**
	 * @see IAsyncTask#isReady()
	 */
	public boolean isReady(){
		return m_isReady;
	}
	
	/**
	 * @see IAsyncTask#getJsCode()
	 */
	public Object getJsCode(){
		return m_jsCode;
	}
	
	//
	// API
	//
	/**
	 * Set jsCode and notify <code>WindowAsyncTask</code> to
	 * re-schedule the task for execution
	 * @param jsCode Object
	 */
	public synchronized void setJsCode(final Object jsCode){
		m_jsCode = jsCode;
		m_isReady = true;
		notifyAll();
	}
	
	/**
	 * Notify <code>WindowAsyncTask</code> to cancel the task
	 */
	public synchronized void cancel(){
		m_isReady = true;
		notifyAll();
	}
}

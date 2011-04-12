/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.util;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Scriptable;

/**
 * WindowAsyncTask extends <code>WindowTask</code> to support
 * asynchronous tasks.
 */
public class WindowAsyncTask extends WindowTask {
		
	private IAsyncTask m_asyncTask;
	
	//
	// Constructor
	//
	/**
	 * Constructor
	 * @param asyncTask IAsyncTask
	 * @param scope Scriptable
	 * @param ctx Context
	 * @param queue WindowTaskQueue
	 * @exception throws <code>DsfRuntimeException</code> if asyncTask is null
	 */
	public WindowAsyncTask(
			final IAsyncTask asyncTask,
			final Scriptable scope,
			final Context ctx,
			final WindowTaskManager mgr) {
		
		super(asyncTask, scope, ctx, mgr);
		
		if (asyncTask == null){
			throw new DsfRuntimeException("asyncTask cannot be null");
		}
		m_asyncTask = asyncTask;
		
		//new Locker(asyncTask, this).start();
	}
	
	//
	// Satisfy IAsyncTask
	//
	/**
	 * @see IAsyncTask#isReady()
	 */
	public boolean isReady(){
		return m_asyncTask.isReady();
	}
	
	/**
	 * @see IAsyncTask#getJsCode()
	 */
	public Object getJsCode(){
		return m_asyncTask.getJsCode();
	}

	//
	// Embedded
	//
//	private class Locker extends Thread {
//		
//		private WindowTask m_task;
//		private IAsyncTask m_asyncTask;
//		
//		private Locker(final IAsyncTask asyncTask, final WindowTask task){
//			m_asyncTask = asyncTask;
//			m_task = task;
//		}
//		
//		@Override
//		public void run(){
//			synchronized (m_asyncTask){
//				
//				try {
//					m_asyncTask.wait();
//				} catch (InterruptedException e) {
//					// NO OP
//				}
//				
//				if (m_asyncTask.getJsCode() != null){
//					m_task.schedule();
//				}
//				else {
//					m_task.doneExec();
//				}
//			}
//		}
//	}
}

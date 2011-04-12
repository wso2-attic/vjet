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
 * a controller for executing all ASYNC JS tasks 
 * (such as window timeout, interval task, and other callback tasks)
 */
public class WindowTaskExecutor implements Runnable {
	
	private final WindowTaskQueue m_queue;	
	private boolean m_shutdown = false;

	public WindowTaskExecutor(WindowTaskQueue queue) {
		m_queue = queue;
		Thread t = new Thread(this, this.getClass().getName());
		t.setDaemon(true);
		t.start();
	}

	public void run() {
		WindowTask task = null;
		while (!m_shutdown) {
			while ((task = m_queue.next()) != null) {
				if (task instanceof WindowAsyncTask){
					if (!((WindowAsyncTask)task).isReady()){
						continue;
					}
				}
				synchronized (this) {
					task.execute();
				}				
			}
			synchronized (m_queue) {
				if (m_queue.isReadyQueueEmpty()) {
					try {
						m_queue.wait();
					} catch (InterruptedException e) {
						//DO Nothing
					}
				}
			}
		}		
	}
	
	public void shutdown() {
		m_shutdown = true;
		synchronized (m_queue) {
			m_queue.notifyAll();
		}
	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Queuing all ASYNC tasks for sequential execution (in single thread) of
 * all ready-state tasks.
 */
public class WindowTaskQueue {
	
	private List<WindowTask> m_readyQueue = new ArrayList<WindowTask>();
	private Map<Integer, WindowTask> m_taskMap = new HashMap<Integer, WindowTask>();
	private int m_idIndex = 0;
	
	public synchronized WindowTask get(int id) {
		return m_taskMap.get(Integer.valueOf(id));
	}
	
	public synchronized WindowTask next() {
		if (m_readyQueue.isEmpty()) {
			return null;
		}
		return m_readyQueue.remove(0);
	}
	
	public synchronized void ready(WindowTask task) {
		if (!isCanceled(task)) {
			m_readyQueue.add(task);
			notifyAll();
		}
	}
	
	public synchronized void add(WindowTask task) {
		m_taskMap.put(Integer.valueOf(task.getId()), task);
	}
	
	public synchronized void cancel(int id) {
		WindowTask task = m_taskMap.remove(Integer.valueOf(id));
		if (task != null) {
			m_readyQueue.remove(task);	
		}
		notifyAll();
	}
	
	public synchronized boolean isCanceled(WindowTask task) {
		return !m_taskMap.containsKey(Integer.valueOf(task.getId()));
	}
	
	public synchronized int createId() {
		m_idIndex++;
		return m_idIndex;
	}
	
	public synchronized boolean isReadyQueueEmpty() {
		return m_readyQueue.isEmpty();
	}
	
	/**
	 * For unit test code to wait for all events, throw exception
	 * after timeout.
	 */
	public synchronized void waitForEmpty(long timeoutInMs) {		
		if (timeoutInMs < 0) {
			timeoutInMs = 0;
		}
		long start = System.currentTimeMillis();
		while (!m_taskMap.isEmpty()) {
			try {
				wait(timeoutInMs);
			} catch (InterruptedException e) {
				// DO nothing
			}
			if (timeoutInMs > 0) {
				if (System.currentTimeMillis() - start >= timeoutInMs) {
					break;
				}
			}
		}
		if (timeoutInMs > 0 && !m_taskMap.isEmpty()) {
			m_taskMap.clear();
			m_readyQueue.clear();
			throw new RuntimeException(
				"There are still some timer events in system after specified timeout: "
				+ timeoutInMs);
		}
	}

	public synchronized void cancelAll() {
		m_taskMap.clear();
		m_readyQueue.clear();
		notifyAll();
	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.util;

import java.util.HashMap;
import java.util.Map;

public class WindowTaskManager {

	private int m_idIndex = 0;
	private Map<Integer, WindowTask> m_taskMap = new HashMap<Integer, WindowTask>();
	
	public WindowTaskManager() {
	}

	public int createId() {
		m_idIndex++;
		return m_idIndex;
	}

	public WindowTask getTask(int id) {
		return m_taskMap.get(id);
	}

	public void add(WindowTask windowTask) {
		//System.out.println("add Task: " + windowTask.getId());
		m_taskMap.put(windowTask.getId(), windowTask);
	}

	public void cancel(int id) {
		m_taskMap.remove(id);
	}

	public void cancelAll() {
		m_taskMap.clear();
	}

	public boolean isCanceled(WindowTask windowTask) {
		return !m_taskMap.keySet().contains(windowTask.getId());
	}

}

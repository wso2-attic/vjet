/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.liveconnect;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class DLCFutureResult implements Future<String> {
	
	private String m_result = null;
	private boolean m_cancelled = false;
	private final String m_requestId;
	private final DLCFutureResultMgr m_mgr;
	
	DLCFutureResult(String requestId, DLCFutureResultMgr mgr) {
		m_requestId = requestId;
		m_mgr = mgr;
		m_mgr.addFuture(this);
	}

	public synchronized boolean cancel(boolean mayInterruptIfRunning) {
		m_cancelled = true;
		notifyAll();
		m_mgr.removeFuture(this);
		return true;
	}

	public String get() {
		return get(0, TimeUnit.MILLISECONDS);
	}

	public synchronized String get(long timeout, TimeUnit unit) {
		try {
			wait(unit.toMillis(timeout));
			return m_result;
		}
		catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		finally {
			m_mgr.removeFuture(this);
		}		
	}

	public boolean isCancelled() {
		return m_cancelled;
	}

	public boolean isDone() {
		return m_result != null;
	}
	
	synchronized void set(String result) {
		m_result = result;
		notifyAll();
	}
	
	String getRequestId() {
		return m_requestId;
	}
}

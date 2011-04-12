/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.liveconnect;

import java.util.HashMap;
import java.util.Map;

public class DLCFutureResultMgr {
	
	private Map<String, DLCFutureResult> m_futureResults =
		new HashMap<String, DLCFutureResult>();
	
	private int m_counter = 0;
	
	DLCFutureResult create() {
		return new DLCFutureResult(String.valueOf(getId()), this);
	}
	
	synchronized void setResult(String requestId, String result) {
		DLCFutureResult futureResult = m_futureResults.get(requestId);
		if (futureResult == null) {
			//log
			System.err.println("No result for " + requestId + " : " + result);
		}
		else {
			futureResult.set(result);
		}
	}
	
	synchronized void addFuture(DLCFutureResult future) {
		m_futureResults.put(future.getRequestId(), future);
	}
	
	synchronized void removeFuture(DLCFutureResult future) {
		m_futureResults.remove(future.getRequestId());
		notify();
	}
	
	/**
	 * wait until all expected responses are received
	 */
	synchronized void waitForAllDone() {
		while (!m_futureResults.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// DONOTHING
			}
		}
	}

	private synchronized int getId() {
		return m_counter++;
	}
}

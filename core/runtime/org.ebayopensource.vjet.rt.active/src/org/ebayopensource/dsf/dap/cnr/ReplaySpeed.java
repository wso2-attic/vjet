/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.cnr;

public enum ReplaySpeed {

	FAST(0, 0), NORMAL(1, 10000), SLOW(4, 40000);

	private int m_factor;
	private long m_maxWait;

	private ReplaySpeed(int factor, long maxWait) {
		m_factor = factor;
		m_maxWait = maxWait;
	}

	public int getFactor() {
		return m_factor;
	}

	public long getMaxWait() {
		return m_maxWait;
	}

}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.event.dispatch;

public interface IEventListenerHandle {
	void setProgress(int progress);
	int getProgress();
	void setAbort(boolean abort);
	boolean isAbort();
	
	public static class Default implements IEventListenerHandle {
		private int m_progress = 0;
		private boolean m_abort = false;
		
		public boolean isAbort() {
			return m_abort;
		}
		public void setAbort(boolean abort) {
			m_abort = abort;
		}
		public int getProgress() {
			return m_progress;
		}
		public void setProgress(int progress) {
			m_progress = progress;
		}
		
	}
}

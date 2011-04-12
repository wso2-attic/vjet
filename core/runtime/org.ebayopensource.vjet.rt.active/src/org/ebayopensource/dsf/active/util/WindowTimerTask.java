/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.util;

import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Scriptable;

/**
 * For window's timeout task
 */
public class WindowTimerTask extends WindowTask {
	
	private final int m_msecs;
	
	public WindowTimerTask (
		Object jsCode,
		int msecs,
		Scriptable scope,
		Context ctx,
		WindowTaskManager mgr) {
		super(jsCode, scope, ctx, mgr);
		m_msecs = msecs;
	}
	
//	@Override
//	public int schedule() {
//		new Timer(true).schedule(new JsTimerTask(), m_msecs);
//		return getId();
//	}
//	
//	private class JsTimerTask extends TimerTask {
//		public void run() {
//			m_mgr.ready(WindowTimerTask.this);
//		}
//	}	
}

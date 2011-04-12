/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.client;

import java.util.Timer;
import java.util.TimerTask;

import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Function;
import org.mozilla.mod.javascript.Scriptable;

public class ASetInterval extends ActiveObject {
	Timer timer;

	public ASetInterval(Object code, int msecs, Scriptable scope,
			Context ctx) {
		timer = new Timer();
		timer.schedule(new SetIntervalTask(code, scope, ctx), msecs, msecs);
	}

	class SetIntervalTask extends TimerTask {
		Object m_code;

		Scriptable m_scope;

		Context m_ctx;

		SetIntervalTask(Object code, Scriptable scope, Context ctx) {
			m_code = code;
			m_scope = scope;
			m_ctx = ctx;
		}

		public void run() {
			synchronized (ASetInterval.class) {
				m_ctx.setLanguageVersion(Context.VERSION_1_5);
				Context.enter();
				if (m_code instanceof Function) {
					Function f = (Function) m_code;
					f.call(m_ctx, m_scope, f.getParentScope().getParentScope(),
							new Object[0]);
				} else {
					ScriptExecutor.executeScript(m_code.toString(), m_scope, m_ctx);
				}
			}
		}
	}

	public Timer getTimer() {
		return timer;
	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.util;

import org.ebayopensource.dsf.active.client.ScriptExecutor;
import org.ebayopensource.dsf.dap.proxy.INativeJsFuncProxy;
import org.ebayopensource.dsf.dap.rt.DapCtx;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Function;
import org.mozilla.mod.javascript.Scriptable;

/**
 * For all ASYNC JS tasks
 */
public class WindowTask {
	private final int m_id;
	private final Object m_jsCode;
	private final Scriptable m_scope;
	private final Context m_ctx;
	protected final WindowTaskManager m_mgr;
	private final DapCtx m_savedCtx;
	
	public WindowTask(
		Object jsCode,
		Scriptable scope,
		Context ctx,
		WindowTaskManager mgr) {
		m_jsCode = jsCode;
		m_scope = scope;
		m_ctx = ctx;
		m_mgr = mgr;
		m_id = m_mgr.createId();
		m_savedCtx = DapCtx.ctx();
		m_mgr.add(this);
	}
	
	public int getId() {
		return m_id;
	}
	
//	public int schedule() {
//		m_mgr.ready(this);
//		return getId();
//	}
	
	private static final Object[] EMPTY_ARG = new Object[0];
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void execute() {
		if (m_mgr.isCanceled(this)) {
			return;
		}
		Context.enter();
		
		Object jsCode = m_jsCode;
		
		if (jsCode instanceof IAsyncTask){
			jsCode = ((IAsyncTask)m_jsCode).getJsCode();
		}
		
		if (jsCode != null){
			m_savedCtx.setSameCxtForCurrentThread();
			if (jsCode instanceof Function) {
				Function f = (Function) jsCode;
				f.call(m_ctx, m_scope, f.getParentScope(), EMPTY_ARG);
			} 
			else if (jsCode instanceof INativeJsFuncProxy) {
				Scriptable nativef = ((INativeJsFuncProxy)jsCode).getJsNative();
				Function f = (Function) nativef ;
				f.call(m_ctx, m_scope, f.getParentScope(), EMPTY_ARG);
			}
			else {
				ScriptExecutor.executeScript(jsCode.toString(), m_scope, m_ctx);
			}
		}
		doneExec();		
	}
	
	protected void doneExec() {
		m_mgr.cancel(getId());
	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.proxy;

import java.util.Stack;

import org.ebayopensource.dsf.common.context.BaseSubCtx;
import org.ebayopensource.dsf.common.context.ContextHelper;
import org.ebayopensource.dsf.common.context.DsfCtx;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Scriptable;

public final class ScriptEngineCtx extends BaseSubCtx {
	
	private Context m_scriptContext;
	private Scriptable m_scriptScope;
	private Stack<Object> m_stackScopes = new Stack<Object>();

	/**
	 * Gets a context associated with current thread
	 */
	public static ScriptEngineCtx ctx() {
		ScriptEngineCtx context = CtxAssociator.getCtx();
		if (context == null) {
			context = new ScriptEngineCtx();
			setCtx(context);
		}
		return context;
	}
	
	public Context getScriptContext(){
		return m_scriptContext;
	}
	
	public void setScriptContext(Context scriptContext){
		m_scriptContext = scriptContext;
	}
	
	public Scriptable getScope(){
		return m_scriptScope;
	}
	
	public void setScope(Scriptable scriptScope){
		m_scriptScope = scriptScope;
	}
	
	public void reset() {
		m_scriptContext = null;
		m_scriptScope = null;
		m_stackScopes = new Stack<Object>();
	}
	
	public Object peekScopeFormStack() {
		return m_stackScopes.peek();
	}
	
	void pushScopeToStack(Object scope) {
		m_stackScopes.push(scope);
	}
	
	void popScopeFromStack() {
		m_stackScopes.pop();
	}
	
	/**
	 * Sets the context to be associated with this thread.  The context
	 * can be null.  
	 */
	static void setCtx(final ScriptEngineCtx context) {
		CtxAssociator.setCtx(context) ;
	}
	
	//
	// Private
	//
	private static class CtxAssociator extends ContextHelper {
		private static final String CTX_NAME = ScriptEngineCtx.class.getSimpleName();
		protected static ScriptEngineCtx getCtx() {
			return (ScriptEngineCtx)getSubCtx(DsfCtx.ctx(), CTX_NAME);
		}
		
		protected static void setCtx(final ScriptEngineCtx ctx) {
			setSubCtx(DsfCtx.ctx(), CTX_NAME, ctx);
		}
	}
}

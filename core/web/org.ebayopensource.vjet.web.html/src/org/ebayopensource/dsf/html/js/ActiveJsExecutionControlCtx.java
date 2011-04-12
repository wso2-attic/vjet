/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import org.ebayopensource.dsf.common.context.BaseSubCtx;
import org.ebayopensource.dsf.common.context.ContextHelper;
import org.ebayopensource.dsf.common.context.DsfCtx;

/**
 * Controls JS execution at server side.
 */
public class ActiveJsExecutionControlCtx extends BaseSubCtx {
	
	public static final String ACTIVE_JS_SRC = "//ACTIVE_JS_SRC:";
	
	private static boolean s_inDebugMode = false;
	static {
		RuntimeMXBean rtMxBean = ManagementFactory.getRuntimeMXBean();
		
		for (String arg : rtMxBean.getInputArguments()) {
			if (arg.startsWith("-agentlib:jdwp")) {
				s_inDebugMode = true;
				break;
			}
		}
	}
	
	private boolean m_executeJavaScript = false;
	private boolean m_parseGeneratedContent = false;
	private boolean m_verbose = false;
	private boolean m_printJavaScriptException = false;
	private boolean m_debug = s_inDebugMode;
	private boolean m_useSharedDebugClient = true;
	
	public static ActiveJsExecutionControlCtx ctx() {
		ActiveJsExecutionControlCtx context = CtxAssociator.getCtx();
		if (context == null) {
			context = new ActiveJsExecutionControlCtx();
			setCtx(context);
		}
		return context;
	}
	
	public void reset() {
		m_executeJavaScript = false;
		m_parseGeneratedContent = false;
		m_verbose = false;
		m_printJavaScriptException = false;
	}
	
	public boolean needExecuteJavaScript() {
		return m_executeJavaScript;
	}

	public void setExecuteJavaScript(boolean execute) {
		m_executeJavaScript = execute;
	}

	public boolean needParseGeneratedContent() {
		return m_parseGeneratedContent;
	}

	public void setParseGeneratedContent(boolean parse) {
		m_parseGeneratedContent = parse;
	}
	
	public boolean needVerbose() {
		return m_verbose;
	}

	public void setVerbose(boolean verbose) {
		m_verbose = verbose;
	}
	
	public boolean needPrintJavaScriptException() {
		return m_printJavaScriptException;
	}

	public void setPrintJavaScriptException(boolean printJavaScriptException) {
		m_printJavaScriptException = printJavaScriptException;
	}
	
	public boolean needDebug() {
		return m_debug;
	}

	public void setDebug(boolean debug) {
		m_debug = debug;
	}
	
	public boolean useSharedDebugClient() {
		return m_useSharedDebugClient;
	}
	
	public void setUseSharedDebugClient(boolean useSharedDebugClient) {
		m_useSharedDebugClient = useSharedDebugClient;
	}
	
	static void setCtx(final ActiveJsExecutionControlCtx context) {
		CtxAssociator.setCtx(context) ;
	}
	
	private static class CtxAssociator extends ContextHelper {
		private static final String CTX_NAME = ActiveJsExecutionControlCtx.class.getSimpleName();
		protected static ActiveJsExecutionControlCtx getCtx() {
			return (ActiveJsExecutionControlCtx)getSubCtx(DsfCtx.ctx(), CTX_NAME);
		}
		
		protected static void setCtx(final ActiveJsExecutionControlCtx ctx) {
			setSubCtx(DsfCtx.ctx(), CTX_NAME, ctx);
		}
	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.js.dbgp;

import java.net.Socket;

import org.ebayopensource.dsf.html.js.ActiveJsExecutionControlCtx;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.ContextFactory;

public class JsDebuggerEnabler {
	
	public synchronized static void enable() {
		if (!ActiveJsExecutionControlCtx.ctx().needDebug()) {
			return;
		}		
		ContextFactory.getGlobal().addListener(new ContextListener());
	}
	
	private static class ContextListener implements ContextFactory.Listener {
		private DBGPDebugger debugger;
		
		public void contextCreated(Context cx) {
			if (this.debugger != null) {//It is a listener which has been binded to a debugger
				return;
			}
			String debuggerServiceIp = System.getProperty("VJETDebugHost");
			String debuggerServicePort = System.getProperty("VJETDebugPort");
			String debuggerServiceSessionID = System.getProperty("VJETDebugSessionID");
			if (debuggerServiceIp == null || debuggerServicePort == null || debuggerServiceSessionID == null)
				return;
			
			try {
				Socket socket = new Socket(debuggerServiceIp, Integer.parseInt(debuggerServicePort));
				debugger = new DBGPDebugger(socket, "filegoeshere", debuggerServiceSessionID, cx);
			} catch (Exception e) {
				throw new RuntimeException(e);
			} 
						
			cx.setDebugger(debugger, null);
			cx.setGeneratingDebug(true);
			cx.setOptimizationLevel(-1);
			debugger.start();
		}

		public void contextReleased(Context cx) {
			if (this.debugger != null && cx.getDebugger().equals(this.debugger))
				debugger.notifyEnd();
		}
	}
}

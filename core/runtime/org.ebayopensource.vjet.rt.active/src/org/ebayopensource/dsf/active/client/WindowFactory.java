/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.client;

import org.ebayopensource.dsf.active.util.ScriptingSessionClassLoader;
import org.ebayopensource.dsf.active.util.VJContextFactory;
import org.ebayopensource.dsf.active.util.WindowTaskManager;
import org.ebayopensource.dsf.dap.event.EventConverter;
import org.ebayopensource.dsf.dap.event.KeyEventConverter;
import org.ebayopensource.dsf.dap.event.MouseEventConverter;
import org.ebayopensource.dsf.dap.event.UIEventConverter;
import org.ebayopensource.dsf.dap.rt.DapCtx;
import org.ebayopensource.dsf.html.js.ActiveJsExecutionControlCtx;
import org.ebayopensource.dsf.jsdebugger.DebuggerAdapter;
import org.ebayopensource.dsf.jsdebugger.JsDebuggerEnabler;
import org.ebayopensource.dsf.jsnative.Window;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.events.Event;
import org.ebayopensource.dsf.jsnative.events.KeyboardEvent;
import org.ebayopensource.dsf.jsnative.events.MouseEvent;
import org.ebayopensource.dsf.jsnative.events.UIEvent;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.ContextFactory;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;

public class WindowFactory extends java.lang.Object {

	private WindowFactory() {
	}

	/** 
	 * Create a top level AWindow object.
	 * @return AWindow.
	 */
	public static Window createWindow() {
		return createWindow(null, BrowserType.IE_6P, new WindowTaskManager());
	}
	
	/**
	 * Create a top level AWindow object.
	 * @param type BrowserType
	 * @return AWindow
	 */
	public static Window createWindow(BrowserType type) {
		return createWindow(null, type == null ? BrowserType.IE_6P : type, new WindowTaskManager());
	}
	
	/**
	 * Create a child AWindow object.
	 * @param parent The parent AWindow object.
	 * @return AWindow
	 */
	public static Window createWindow(Window parent) {
		return createWindow(parent, parent == null ? BrowserType.IE_6P : 
				((AWindow) parent).getBrowserType(), new WindowTaskManager());
	}

	public static Window createWindow(BrowserType browserType,	WindowTaskManager taskMgr) {
		return createWindow(null, browserType == null ? BrowserType.IE_6P : browserType, taskMgr);
	}

	/** Create a child AWindow object.
	 * User must destroy the child windows before parent.
	 *
	 * @param parent The parent AWindow object.
	 * @return A child AWindow object.
	 */
	public static Window createWindow(Window parent, BrowserType type,	WindowTaskManager taskMgr) {
		Context cx = null;
		Scriptable scope = null;
		AWindow window = null;
		DebuggerAdapter dbg = null;
		try {
			// Create context.
			if (parent == null) {
				/**
				 * modify by xingzhu: init context first, then start the corresponding JsDebuggerEnabler
				 * 
				 */
				
				// Need to have our own factory to override some hasFeature(..) lookups
				if (!ContextFactory.hasExplicitGlobal()) {
					ContextFactory.initGlobal(new VJContextFactory()) ;
				}
				
				if (ActiveJsExecutionControlCtx.ctx().needExecuteJavaScript()) {
					/**
					 * if corresponding env variables are available, start dbgp debugger,
					 * else, start swing js debugger
					 */
					String debuggerServiceIp = System.getProperty("VJETDebugHost");
					String debuggerServicePort = System.getProperty("VJETDebugPort");
					String debuggerServiceSessionID = System.getProperty("VJETDebugSessionID");
					if (debuggerServiceIp == null || debuggerServicePort == null || debuggerServiceSessionID == null) {
						dbg = JsDebuggerEnabler.enable();
					}
					else
						org.ebayopensource.dsf.js.dbgp.JsDebuggerEnabler.enable();
				}	
			}
			
			cx = setupContext((AWindow)parent);
			
			// Create scope.
			scope = cx.initStandardObjects(null);
			// Define java classes of browser's window objects.
			ScriptableObject.defineClass(scope, AWindow.class);
			// Create scriptable java window object.
			window = (AWindow) cx.newObject(scope, "AWindow");
			window.setParentScope(scope);
			window.setPrototype(scope);
			if (dbg != null) {
				window.setDebugger(dbg);
			}
			scope = window;
			// Init window object with standard javascript objects.
			window.init((AWindow) parent, cx, scope, type, taskMgr);

			// Add this browser window to the child list of parent
			if (parent != null) {
				((AWindow) parent).addChildWindow(window);
			}
			if (DapCtx.ctx().isActiveMode()) {
				DapCtx.ctx().setWindow(window);
			}
			return window;
		} catch (Exception ex) {
			ex.printStackTrace();	// KEEPME
		}
		return (null);
	}

	public static void contextSwitch(AWindow window){		
		setupContext(null);
	}
	
	//
	// Private
	//
	private static Context setupContext(final AWindow parent){
		if (parent != null){
			return parent.getContext();
		}
		
		Context cx = ContextFactory.getGlobal().enterContext();
		if (DapCtx.ctx().isActiveMode()) {
			//add new ClassLoader for a given window/page
			cx.setApplicationClassLoader(new ScriptingSessionClassLoader());
			cx.registerConverter(MouseEvent.class, new MouseEventConverter());
			cx.registerConverter(KeyboardEvent.class, new KeyEventConverter());
			cx.registerConverter(UIEvent.class, new UIEventConverter());
			cx.registerConverter(Event.class, new EventConverter());
		}
		cx.setLanguageVersion(Context.VERSION_1_5);
		cx.setOptimizationLevel(-1); //using interpret mode
		
		return cx;
	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import org.ebayopensource.dsf.jsdebugger.JsDebuggerEnabler;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;

public class JSWindowFactory extends java.lang.Object {

	private JSWindowFactory() {
	}

	/** Create a toplevel JSWindow object.
	 * User must call destroy method of the returned object when it's done.
	 *
	 * @return a object of JSWindow class.
	 */
	public static JSWindow createJSWindow() {
		return (JSWindowFactory.createJSWindow(null));
	}

	/** Create a child JSWindow object.
	 * User must destroy the child windows before parent.
	 *
	 * @param parent The parent JSWindow object.
	 * @return A child JSWindow object.
	 */
	public static JSWindow createJSWindow(JSWindow parent) {
		Context cx = null;
		Scriptable scope = null;
		JSWindow window = null;
		try {
			// Create context.
			if (parent == null) {
				if (ActiveJsExecutionControlCtx.ctx().needExecuteJavaScript()) {
					JsDebuggerEnabler.enable();
				}
				cx = Context.enter();
				JSDebug.println(
					"Default JavaScript Version: " + cx.getLanguageVersion());
				cx.setLanguageVersion(Context.VERSION_1_1);
				JSDebug.println(
					"Set JavaScript Version: " + cx.getLanguageVersion());
			} else {
				cx = parent.getContext();
			}
			// Create scope.
			scope = cx.initStandardObjects(null);
			// Define java classes of browser's window objects.
			ScriptableObject.defineClass(scope, JSWindow.class);
			// Create scriptable java window object.
			window = (JSWindow) cx.newObject(scope, "JSWindow");
			window.setParentScope(scope);
			window.setPrototype(scope);
			scope = window;
			// Init window object with standard javascript objects.
			window.init(parent, cx, scope);

			// Add this browser window to the child list of parent
			if (parent != null) {
				parent.addChildWindow(window);
			}
			return (window);
		} catch (Exception ex) {
		}
		return (null);
	}

}

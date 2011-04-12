/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Scriptable;

public class JSHistory {

    /** Creates new JSHistory */
    private JSWindow window = null;
    private Context cx = null;
    private Scriptable scope = null;
    
    public JSHistory(JSWindow window) {
        this.window = window;
        this.cx     = window.getContext();
        this.scope  = window.getScope();
    }

    // Property: length, R/O -------------------------------------------------
    private int length = 0;
    public int getLength() {
        return (length);
    }

    // Functions ---------------------------------------------------------------
    public void back() {
        if (window.windowState == JSWindow.IN_SERVER)
            window.getJSListener().doAction(JSAction.HISTORY_BACK, "history", null, null);
        return;
    }

    public void forward() {
        if (window.windowState == JSWindow.IN_SERVER)
            window.getJSListener().doAction(JSAction.HISTORY_FORWARD, "history", null, null);
        return;
    }

    public void go(Object o) {
        return;
    }

}

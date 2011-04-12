/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import java.util.ArrayList;

import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;

public class JSFrames extends ScriptableObject {

    private JSWindow self = null;
    private Context cx = null;
    private Scriptable scope = null;
    private ArrayList frames = new ArrayList();
    private ArrayList hiddenWindows = null;
    
    private JSFrames() {
    }

    /** Creates new JSFrames */
    public JSFrames(JSWindow window) {
        this.self   = window;
        this.cx     = window.getContext();
        this.scope  = window.getScope();
        
        defineProperty("self", JSFrames.class, ScriptableObject.DONTENUM);
        defineProperty("top", JSFrames.class, ScriptableObject.DONTENUM);

    }

    public String getClassName() {
        return ("JSFrames");
    }

    public boolean has(String name, Scriptable start) {
        if (name.equals("length")) {
            return true;
        }
        
        int size = frames.size();
        for (int i = 0; i < size; i++) {
            JSWindow window = (JSWindow)frames.get(i);
            if (window.getName().equals("name"))
                return true;
        }
        return false;
    }

    public boolean has(int index, Scriptable start) {
        if (index >= 0 && index < frames.size())
            return true;
        else
            return false;
    }

    public Object get(String name, Scriptable start) {
        Object obj = super.get(name, start);
        if (obj != NOT_FOUND) {
            return obj;
        }
        
        if (name.equals("length")) {
            return new Integer(frames.size());
        }

        int size = frames.size();
        for (int i = 0; i < size; i++) {
            JSWindow window = (JSWindow)frames.get(i);
            if (window.getName().equals(name))
                return window;
        }
        
        return getHiddenWindow(name, true);
        //return (Scriptable.NOT_FOUND);
    }
    

    public Object get(int index, Scriptable start) {
        if (index < 0 || index >= frames.size())
            return Scriptable.NOT_FOUND;
            
        return (JSWindow)frames.get(index);
    }
    
    public Object getSelf()
    {
        return self;
    }
    
    public void setSelf(Object self)
    {
        if (self instanceof JSWindow)
            this.self = (JSWindow)self;
    }
    
    public Object getTop()
    {
        return self;
    }
    
    public void setTop(Object top)
    {
        if (self instanceof JSWindow)
            this.self = (JSWindow)top;
    }
    
    public void addChildWindow(JSWindow window) {
        frames.add(window);
    }
    
    public void removeChildWindow(JSWindow window) {
        frames.remove(window);
    }
    
    public int size() {
        return frames.size();
    }
    
    public JSWindow at(int index) {
        return (JSWindow)frames.get(index);
    }
    
    public JSWindow getHiddenWindow(String name, boolean create) {
        if (hiddenWindows == null) {
            if (create)
                hiddenWindows = new ArrayList();
            else
                return null;
        }
        for (int i = 0; i < hiddenWindows.size(); i++) {
            JSWindow hiddenWindow = (JSWindow)hiddenWindows.get(i);
            if (name.equals(hiddenWindow.getName().toString()))
                return hiddenWindow;
        }
        
        if (create)
        {
            JSWindow hiddenWindow = new JSWindow();
            try {
                hiddenWindow.init(self, cx, scope);
            }
            catch (Exception e) {}
            hiddenWindow.setName(name);
            hiddenWindows.add(hiddenWindow);
            return hiddenWindow;
        }
        
        return null;
    }
    
    public JSWindow getHiddenWindow() {
        if (hiddenWindows == null || hiddenWindows.size() != 1)
                return null;
        return (JSWindow)hiddenWindows.get(0);
    }
        
    public int numHiddenWindow() {
        return (hiddenWindows == null) ? 0 : hiddenWindows.size();
    }
    
    public void fixHiddenWindowHref(String urlBase, int startIndex) {
        if (hiddenWindows == null)
            return;
        for (int i = startIndex; i < hiddenWindows.size(); i++) {
            JSWindow hiddenWindow = (JSWindow)hiddenWindows.get(i);
            String href = hiddenWindow.getJSLocation().getReplacement();
            if (href != null && href.length() > 0) {
                href = URLUtil.getAbsoluteURL(href, urlBase);
                hiddenWindow.getJSLocation().replace(href);
            }
        }
    }
}

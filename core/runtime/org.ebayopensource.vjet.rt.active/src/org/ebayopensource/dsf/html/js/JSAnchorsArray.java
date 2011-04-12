/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import org.ebayopensource.dsf.html.dom.DA;
import org.ebayopensource.dsf.html.dom.DHtmlCollection;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Scriptable;

public class JSAnchorsArray implements Scriptable {

    private JSWindow m_window = null;
    private Context m_cx = null;
    private Scriptable m_scope = null;
	private Scriptable m_prototype = null;
	private Scriptable m_parent = null;    
    
    private JSAnchorsArray() {
    }

    /** Creates new JSAnchorsArray */
    public JSAnchorsArray(JSWindow window) {
        JSDebug.println("JSAnchorsArray:JSAnchorsArray()...");
        this.m_window = window;
        this.m_cx     = window.getContext();
        this.m_scope  = window.getScope();
    }

    public String getClassName() {
        JSDebug.println("JSAnchorsArray:getClassName(...)");
        return ("JSAnchorsArray");
    }

    /**
     * Defines the "length" property by returning true if name is equal to "length".
     * Defines no other properties, i.e., returns false for all other names.
     *
     * @param name the name of the property
     * @param start the object where lookup began
     */
    public boolean has(String name, Scriptable start) {
        JSDebug.println("JSAnchorsArray:has()... " + name);
        if (name.equals("length")) {
            return (true);
        }
        
        DHtmlCollection anchors = m_window.getHTMLDocument().getAnchors();
        if (anchors == null) {
            return (false);
        }
        
        DA anchor = null; 
        for (int i = 0; i < anchors.getLength(); i++) {
            anchor = (DA)anchors.item(i);
            if (name.equals(anchor.getHtmlName())) {
				return true;
            }
        }
        return false;
    }

    /**
     * Defines all numeric properties by returning true.
     *
     * @param index the index of the property
     * @param start the object where lookup began
     */
    public boolean has(int index, Scriptable start) {
        JSDebug.println("JSAnchorsArray:has()... " + index);
        DHtmlCollection anchors = m_window.getHTMLDocument().getAnchors();
        if (anchors == null) {
            return (false);
        }
        
        return (index >= 0 && index < anchors.getLength());
       
    }

    /**
     * Get the named property.
     * Handles the "length" property and returns NOT_FOUND for all other names.
     *
     * @param name the property name
     * @param start the object where the lookup began
     */
    public Object get(String name, Scriptable start) {
        JSDebug.println("JSAnchorsArray:get()... " + name);
        DHtmlCollection anchors = m_window.getHTMLDocument().getAnchors();
        if (name.equals("length")) {
            if (anchors != null) {
                return (new Integer(anchors.getLength()));
            }
            else {
                return (new Integer(0));
            }
        }
        
        if (anchors == null) {
            return (Scriptable.NOT_FOUND);
        }
        
        DA anchor = null; 
        for (int i = 0; i < anchors.getLength(); i++) {
            anchor = (DA)anchors.item(i);
            if (name.equals(anchor.getHtmlName())) {
				return (Context.toObject(anchor, m_scope));
            }
        }
        return (Scriptable.NOT_FOUND);
    }

    /**
     * Get the indexed property.
     * <p>
     * Look up the element in the DOM tree, and return an HTMLAnchorElement of it
     * if it exists. If it doesn't exist, return NOT_FOUND.<p>
     *
     * @param index the index of the integral property
     * @param start the object where the lookup began
     */
    public Object get(int index, Scriptable start) {
        JSDebug.println("JSAnchorsArray:get()... " + index);
        DHtmlCollection anchors = m_window.getHTMLDocument().getAnchors();
        if (anchors == null) {
            return (Scriptable.NOT_FOUND);
        }
        
        if (index < 0 || index >= anchors.getLength()) {
            return (Scriptable.NOT_FOUND);
        }
        return (Context.toObject(anchors.item(index), m_scope));
    }

    /**
     * Set a named property.
     *
     * We do nothing here, so all properties are effectively read-only.
     */
    public void put(String name, Scriptable start, Object value) {
    }

    /**
     * Set an indexed property.
     *
     * We do nothing here, so all properties are effectively read-only.
     */
    public void put(int index, Scriptable start, Object value) {
    }

    /**
     * Remove a named property.
     *
     * This method shouldn't even be called since we define all properties
     * as PERMANENT.
     */
    public void delete(String id) {
    }

    /**
     * Remove an indexed property.
     *
     * This method shouldn't even be called since we define all properties
     * as PERMANENT.
     */
    public void delete(int index) {
    }

    /**
     * Get prototype.
     */
    public Scriptable getPrototype() {
        JSDebug.println("JSAnchorsArray:getPrototype()...");
        return m_prototype;
    }

    /**
     * Set prototype.
     */
    public void setPrototype(Scriptable prototype) {
        JSDebug.println("JSAnchorsArray:setPrototype()...");
        this.m_prototype = prototype;
    }

    /**
     * Get parent.
     */
    public Scriptable getParentScope() {
        JSDebug.println("JSAnchorsArray:getParentScope()...");
        return m_parent;
    }

    /**
     * Set parent.
     */
    public void setParentScope(Scriptable parent) {
        JSDebug.println("JSAnchorsArray:setPrototype()...");
        this.m_parent = parent;
    }

    /**
     * Get properties.
     *
     * We return an empty array since we define all properties to be DONTENUM.
     */
    public Object[] getIds() {
        JSDebug.println("JSAnchorsArray:getIds()...");
        return new Object[0];
    }

    /**
     * Default value.
     *
     * Use the convenience method from Context that takes care of calling
     * toString, etc.
     */
    public Object getDefaultValue(Class typeHint) {
        JSDebug.println("JSAnchorsArray:getDefaultValue()...");
        return "[object JSAnchorsArray]";
    }

    /**
     * instanceof operator.
     *
     * We mimick the normal JavaScript instanceof semantics, returning
     * true if <code>this</code> appears in <code>value</code>'s prototype
     * chain.
     */
    public boolean hasInstance(Scriptable value) {
        JSDebug.println("JSAnchorsArray:hasInstance()...");
        Scriptable proto = value.getPrototype();
        while (proto != null) {
            if (proto.equals(this)) {
                return (true);
            }
        }
        return (false);
    }
}

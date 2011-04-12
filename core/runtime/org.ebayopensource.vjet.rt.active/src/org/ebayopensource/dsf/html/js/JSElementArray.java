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

public class JSElementArray extends ScriptableObject {

    private JSWindow window = null;
    private Context cx = null;
    private Scriptable scope = null;
    private ArrayList elements = new ArrayList();
    
    
    private JSElementArray() {
    }

    /** Creates new JSElementWraperArray */
    public JSElementArray(JSWindow window) {
        this.window   = window;
        this.cx       = window.getContext();
        this.scope    = window.getScope();
        
        String[] functions = {"tags", "valueOf"};
        
        defineFunctionProperties(functions,
            JSElementArray.class,
            ScriptableObject.DONTENUM);
        defineProperty("length", JSElementArray.class, ScriptableObject.DONTENUM);

    }

    public String getClassName() {
        return "JSElementWraperArray";
    }

    /**
     * Defines the "length" property by returning true if name is equal to "length".
     * Defines no other properties, i.e., returns false for all other names.
     *
     * @param name the name of the property
     * @param start the object where lookup began
     */
    public boolean has(String name, Scriptable start) {
        if (name.equals("length")) {
            return true;
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
        if (index >= 0 && index < elements.size()) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Get the indexed property.
     * <p>
     * Look up the element in the DOM tree, and return an Object of it
     * if it exists. If it doesn't exist, return NOT_FOUND.<p>
     *
     * @param index the index of the integral property
     * @param start the object where the lookup began
     */
    public Object get(int index, Scriptable start) {
        if (index >= 0 && index < elements.size())
            //return cx.toObject(elements.get(index), scope);
            return elements.get(index);
        return Scriptable.NOT_FOUND;
    }
    
    public int getLength() {
        return elements.size();
    }

    public void add(Object element) {
        elements.add(element);
    }
    
    public void insertAfter(Object element, Object ref) {
    	for (int i=0; i<elements.size(); i++){
    		if (elements.get(i) == ref){
    			elements.add(i+1,element);
    			return;
    		}
    	}
    }
    
    public void remove(Object element) {
        elements.remove(element);
    }
    
    public JSElementArray jsFunction_tags(String name) {
        return tags(name);
    }
    
    public JSElementArray tags(String name) {
        JSElementArray list = new JSElementArray(window);
        /**
        int size = elements.size();
        for (int i = 0; i < size; i++) {
            JSElementWraper wraper = (JSElementWraper)elements.get(i);
            if (wraper.getTagName().equalsIgnoreCase(name))
                list.add(wraper);
        }**/
        return list;
    }
    
    public Object valueOf(String type) {
        if (type.equals("boolean"))
            return Boolean.TRUE;
        else if (type.equals("string")) {
            return type;
        }
        else if (type.equals("object"))
            return this;
        else if (type.equals("number"))
            return "0";
            
        return null;
    }
}

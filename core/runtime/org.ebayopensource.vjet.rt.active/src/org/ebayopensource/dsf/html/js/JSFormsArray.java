/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import java.util.Vector;

import org.w3c.dom.NodeList;

import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.html.dom.DForm;
import org.ebayopensource.dsf.html.dom.DHtmlCollection;
import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.Scriptable;
import org.mozilla.mod.javascript.ScriptableObject;

public class JSFormsArray extends ScriptableObject {

    private JSWindow window = null;
    private Context cx = null;
    private Scriptable scope = null;
    private Vector forms = new Vector();
    private JSForm defaultForm = null; //for fake form
    
    private JSFormsArray() {
    }

    /** Creates new JSFormsArray */
    public JSFormsArray(JSWindow window) {
        this.window = window;
        this.cx     = window.getContext();
        this.scope  = window.getScope();
    }

    public String getClassName() {
        return ("JSFormsArray");
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
            return (true);
        }
        
        DHtmlCollection forms = window.getHTMLDocument().getForms();
        if (forms == null) {
            return false;
        }
        
		DForm form = null; 
        for (int i = 0; i < forms.getLength(); i++) {
            form = (DForm)forms.item(i);
            if (name.equals(form.getHtmlName())) {
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
        DHtmlCollection forms = window.getHTMLDocument().getForms();
        if (forms == null) {
            return (false);
        }

        if (index >= 0 && index < forms.getLength()) {
            return (true);
        }
        else {
            return (false);
        }
    }

    /**
     * Get the named property.
     * Handles the "length" property and returns NOT_FOUND for all other names.
     *
     * @param name the property name
     * @param start the object where the lookup began
     */
    public Object get(String name, Scriptable start) {
        if (window.windowState == JSWindow.IN_LOADING) {
            DHtmlCollection forms = window.getHTMLDocument().getForms();
            if (name.equals("length")) {
                if (forms != null)
                    return (new Integer(forms.getLength()));
                else
                    return (new Integer(0));
            }

            if (forms == null)
                return (Scriptable.NOT_FOUND);

			DForm form = null;
            for (int i = 0; i < forms.getLength(); i++) {
                form = (DForm)forms.item(i);
                if (name.equals(form.getHtmlName())) {
                	//return (Context.toObject(form, scope));
                	return new JSForm(window, form);
                }
            }
        }
        else {
            int numForms = forms.size();
            for (int i = 0; i < numForms; i++) {
                JSForm form = (JSForm)forms.get(i);
                if (form.name.equals(name))
                    return Context.toObject(form, scope);
            }
        }
        return Scriptable.NOT_FOUND;
    }

    /**
     * Get the indexed property.
     * <p>
     * Look up the element in the DOM tree, and return an DHtmlFormElement of it
     * if it exists. If it doesn't exist, return NOT_FOUND.<p>
     *
     * @param index the index of the integral property
     * @param start the object where the lookup began
     */
    public Object get(int index, Scriptable start) {
        if (window.windowState == JSWindow.IN_LOADING) {
            DHtmlCollection forms = window.getHTMLDocument().getForms();
       
            if (forms == null || index < 0 || index >= forms.getLength())
                return (Scriptable.NOT_FOUND);
            //return (Context.toObject((DHtmlFormElement)(forms.item(index)), scope));
            return new JSForm(window, (DForm)forms.item(index));
        }
        else {
            if (forms == null || index < 0 || index >= forms.size())
                return (Scriptable.NOT_FOUND);
            return (Context.toObject(forms.get(index), scope));
        }
    }

    public void updateForms() {
        DHtmlCollection domForms = window.getHTMLDocument().getForms();
        int numForms = 0;
        if (domForms == null || (numForms = domForms.getLength()) == 0)
            return;
        
        forms.removeAllElements();
        for (int i = 0; i < numForms; i++) {
            forms.add(
                new JSForm(window, (DForm)domForms.item(i)));
        }
    }
    
    public void addJSForm(DElement formElem) {
        forms.add(new JSForm(window, (DForm)formElem));
    }
    
    public void addDefaultJSForm(String formId) {
        defaultForm = new JSForm(window, formId);
    }
    
    public void addJSFormChildElem(DElement formChildElem) {
        String formId = formChildElem.getAttribute("form-id");
        JSForm form = getFormById(formId);
        if (form != null) {
            form.addJSFormChildElem(formChildElem);
        }
    }
    
    public JSForm getFormById(String formId) {
        int numForms = forms.size();
        for (int i = 0; i < numForms; i++) {
            JSForm form = (JSForm)forms.get(i);
            if (form.formId != null && form.formId.equals(formId))
                return form;
        }
        if (defaultForm != null && defaultForm.formId.equals(formId))
            return defaultForm;
        return null;
    }
    
    public int numForms() {
        return forms.size();
    }
    
    public JSForm getForm(int index) {
        return (JSForm)forms.get(index);
    }
    
    public Object getForm(String name) {
        int numForms = forms.size();
        for (int i = 0; i < numForms; i++) {
            JSForm form = (JSForm)forms.get(i);
            if (form.name.equals(name))
                return form;
        }
        if (window.windowState == JSWindow.IN_LOADING) {
            NodeList domForms = window.getHTMLDocument().getElementsByTagName(
            	HtmlTypeEnum.FORM);
            numForms = domForms.getLength();
            for (int i = 0; i < numForms; i++) {
                DForm domForm = 
                    (DForm)domForms.item(i);
                if (domForm.getHtmlName().equals(name))
                    return new JSForm(window, domForm);;
            }
        }
        return null;
    }
}

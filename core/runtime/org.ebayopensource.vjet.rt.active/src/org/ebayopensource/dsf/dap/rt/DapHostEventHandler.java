/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import org.ebayopensource.dsf.active.dom.html.AHtmlElement;
import org.ebayopensource.dsf.dap.event.DapEvent;
import org.ebayopensource.dsf.dap.event.listener.IDapEventListener;
import org.ebayopensource.dsf.dap.event.listener.IDapHostEventHandler;
import org.mozilla.mod.javascript.ScriptRuntime;
import org.mozilla.mod.javascript.ScriptableObject;

@SuppressWarnings("serial")
public final class DapHostEventHandler extends BaseScriptable 
	implements IDapHostEventHandler {
	
	private IDapEventListener m_listener;
	
	private static final String[] MTD_NAMES = {
		"process",
	};
	
	public DapHostEventHandler(){}
	
	@SuppressWarnings("unchecked")
	public boolean process(ScriptableObject src, String eventTypeName, ScriptableObject event) {		
		if (src instanceof AHtmlElement){
			m_listener.handleEvent((DapEvent)event);
		}
		return true;
	}


    public Object getDefaultValue(Class typeHint) {
    	if (typeHint == Boolean.TYPE || typeHint == ScriptRuntime.BooleanClass) {
    		return Boolean.TRUE;
    	}
    	return super.getDefaultValue(typeHint);
    }
	
	//
	// Protected
	//
	DapHostEventHandler(IDapEventListener listener){
		
		m_listener = listener;

		defineFunctionProperties(MTD_NAMES);
	}
}

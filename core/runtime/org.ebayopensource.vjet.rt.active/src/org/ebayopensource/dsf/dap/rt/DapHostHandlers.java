/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import java.util.ArrayList;
import java.util.List;

import org.mozilla.mod.javascript.Scriptable;

@SuppressWarnings("serial")
public final class DapHostHandlers<H> extends BaseScriptable {

    private List<H> m_handlers = new ArrayList<H>();
    
	public DapHostHandlers() {
    }

    public Object get(int index, Scriptable start) {
        if (index < 0 || index >= m_handlers.size())
            return Scriptable.NOT_FOUND;
            
        return m_handlers.get(index);
    }
    
    public int size() {
        return m_handlers.size();
    }
    
    //
    // Package protected
    //
    void add(H handler){
    	m_handlers.add(handler);
    }
}

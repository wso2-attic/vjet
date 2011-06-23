/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.client;

import org.ebayopensource.dsf.active.event.IBrowserBinding;
import org.ebayopensource.dsf.jsnative.History;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;


public class AHistory extends ActiveObject implements History {

	private static final long serialVersionUID = 1L;
	private IBrowserBinding m_browserBinding;

	public AHistory(BrowserType browserType) {
		populateScriptable(AHistory.class, browserType);
	}
	
	public void back() {
		if (m_browserBinding != null) {
			m_browserBinding.back();
		}
    }

    public void forward() {
		if (m_browserBinding != null) {
			m_browserBinding.forward();
		}
    }

    public void go(int o) {
		if (m_browserBinding != null) {
			m_browserBinding.go(o);
		}
    }
    public void go(String o) {
    	if (m_browserBinding != null) {
    		m_browserBinding.go(o);
    	}
    }

	public int getLength() {
		if (m_browserBinding != null) {
			return m_browserBinding.historyLength();
		}
		return 0;
	}

	public void setBrowserBinding(IBrowserBinding binding)	{
		m_browserBinding = binding;
	}



	@Override
	public void __pushState(Object numberOrUrl, Object notreq0, Object notreq1,
			Object notreq2, Object notreq3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void __replaceState(Object numberOrUrl, Object notreq0, Object notreq1,
			Object notreq2, Object notreq3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void __go(Object numberOrUrl, Object notreq0, Object notreq1,
			Object notreq2, Object notreq3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pushState(Object state, String title, String URL) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pushState(Object state, String title) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pushState(Object state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void replaceState(Object state, String title, String URL) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void replaceState(Object state, String title) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void replaceState(Object state) {
		// TODO Auto-generated method stub
		
	}
	
}

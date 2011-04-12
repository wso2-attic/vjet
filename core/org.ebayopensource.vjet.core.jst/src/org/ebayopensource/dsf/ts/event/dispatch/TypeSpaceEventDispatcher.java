/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.event.dispatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.jst.ts.TypeSpaceConfig;
import org.ebayopensource.dsf.ts.event.EventListenerStatus;
import org.ebayopensource.dsf.ts.event.ITypeSpaceEvent;
import org.ebayopensource.dsf.ts.event.ITypeSpaceEventListener;

public class TypeSpaceEventDispatcher {
	
	private List<ITypeSpaceEventListener> m_tsEventListeners = new ArrayList<ITypeSpaceEventListener>();
	private boolean m_synchronous_events = false;
	
	public synchronized void addListener(ITypeSpaceEventListener listener){
		if (listener != null && !m_tsEventListeners.contains(listener)){
			m_tsEventListeners.add(listener);
		}
	}
	
	public synchronized void removeListener(ITypeSpaceEventListener listener){
		m_tsEventListeners.remove(listener);
	}
	
	public void dispatch(final ITypeSpaceEvent event, final EventListenerStatus status){
		
		for (ITypeSpaceEventListener listener: getListeners(true)){
			if (event.isAppropriateListener(listener)){
				run(event, listener, status);
			}
		}
	}
	
	//
	// Private
	//
	private synchronized List<ITypeSpaceEventListener> getListeners(boolean readOnly){
		if (readOnly){
			return Collections.unmodifiableList(m_tsEventListeners);
		}
		return m_tsEventListeners;
	}
	
	// TODO - replace with real impl.
	private void run(final ITypeSpaceEvent event, final ITypeSpaceEventListener listener, 
					 final EventListenerStatus status){

		Runnable r = new Runnable(){
			public void run(){
				try {
					event.dispatch(listener, status);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		};
		if (m_synchronous_events) {
			r.run();
		}
		else {
			new Thread(r).start();
		}
	}

	public void setConfig(TypeSpaceConfig config) {
		m_synchronous_events = config.isSynchronousEvents();
	}
}

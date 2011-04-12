/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import org.w3c.dom.Node;

import org.ebayopensource.dsf.active.event.IDomEventBindingListener;
import org.ebayopensource.dsf.dap.util.DapDomHelper;
import org.ebayopensource.dsf.html.dom.DHtmlDocument;
import org.ebayopensource.dsf.html.dom.DImg;
import org.ebayopensource.dsf.html.dom.DScript;
import org.ebayopensource.dsf.liveconnect.IDLCDispatcher;
import org.ebayopensource.dsf.liveconnect.client.DLCClientHelper;
import org.ebayopensource.dsf.liveconnect.client.IDLCClient;
import org.ebayopensource.dsf.liveconnect.client.NativeEvent;

public class DapDomEventBindingListener implements IDomEventBindingListener {
	
	private final IDLCDispatcher m_dlcDispatcher;
	private final IDLCClient m_dlcClient;
	
	public DapDomEventBindingListener(IDLCDispatcher dlcDispatcher, IDLCClient dlcClient) {
		m_dlcDispatcher = dlcDispatcher;
		m_dlcClient = dlcClient;
	}
	
	public void eventBound(Node elem, String eventType) {
		NativeEvent event = NativeEvent.valueOf(eventType);
		if(event.equals(NativeEvent.load) && (elem instanceof DImg)){
			event = NativeEvent.imageload;
		}
		if(event.equals(NativeEvent.load) && (elem instanceof DScript)){
			event = NativeEvent.scriptLoad;
		}
		if(event.equals(NativeEvent.readystatechange) && (elem instanceof DScript)){
			event = NativeEvent.scriptReadyStateChange;
		}
		if (event == NativeEvent.change || event == NativeEvent.click) {
			return;
		}
		if ((event.equals(NativeEvent.readystatechange)
				|| event.equals(NativeEvent.load) || event
				.equals(NativeEvent.unload))
				&& (elem instanceof DHtmlDocument)) {
			return;
		}
		
		String path = DapDomHelper.getPath(elem);
		if(path==null) {
			return;
		}
		
		if (event.equals(NativeEvent.scroll) && (elem instanceof DHtmlDocument)) {
			path = "window";
		}

		String enabler = DLCClientHelper.dlcClientEventEnablerViaPath(path, event, m_dlcClient);
		if (enabler != null) {
			m_dlcDispatcher.send(enabler);
		}
	}

	public boolean isEventBinding(Node elem, String name) {
		NativeEvent event = NativeEvent.valueOf(name);
		return (event != null);
	}
}

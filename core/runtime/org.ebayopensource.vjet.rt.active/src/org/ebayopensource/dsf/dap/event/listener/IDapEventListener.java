/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.event.listener;

import org.ebayopensource.dsf.html.events.ISimpleJsEventHandler;
import org.ebayopensource.dsf.html.js.IJsFunc;
import org.ebayopensource.dsf.jsnative.events.EventListener;

/**
 * Interface for all DAP event listeners
 * 
 * 
 */
public interface IDapEventListener extends EventListener, IJsEventListenerProxy {
	
	/**
	 * Answer the adapted event handler for element with given id.
	 * @param elemId String 
	 * @param index int 
	 * @return ISimpleJsEventHandler
	 */
	ISimpleJsEventHandler getEventHandlerAdapter(String elemId, int index);
	
	/**
	 * Answer the adapted event handler with given js function.
	 * @param func IJsFunc 
	 * @return ISimpleJsEventHandler
	 */
	ISimpleJsEventHandler getEventHandlerAdapter(IJsFunc func);
	
	/**
	 * Answer the host event handler
	 * @return IDapHostEventHandler
	 */
	IDapHostEventHandler getHostEventHandler();
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.event.listener;

import java.util.Map;

import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.html.js.IJsFunc;

/**
 * Interface for DAP event listener proxies.
 * 
 * 
 */
public interface IJsEventListenerProxy {
	
	/**
	 * Answer a map of <code>IJsFunc</code> keyed by <code>EventType</code>
	 * @return Map<EventType,IJsFunc>
	 */
	Map<EventType,IJsFunc> getProxyEventHandlers();
}

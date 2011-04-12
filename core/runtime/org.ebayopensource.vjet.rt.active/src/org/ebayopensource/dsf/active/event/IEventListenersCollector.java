/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.event;

import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.active.dom.html.AJavaScriptHandlerHolder;
import org.ebayopensource.dsf.active.dom.html.AJavaScriptHandlerHolder.JAVASCRIPT_HANDLER_TYPE;

public interface IEventListenersCollector {
	public void removeListener(String type, Object listener, boolean useCapture);

	public Map<String, List<AJavaScriptHandlerHolder>> getListeners();

	public void addListener(String type, Object listener, boolean useCapture, boolean bind, JAVASCRIPT_HANDLER_TYPE handlerType);
}

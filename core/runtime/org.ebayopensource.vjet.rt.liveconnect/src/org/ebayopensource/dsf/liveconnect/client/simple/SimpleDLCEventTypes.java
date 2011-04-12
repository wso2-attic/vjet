/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.liveconnect.client.simple;

import org.ebayopensource.dsf.html.events.EventType;

public interface SimpleDLCEventTypes {

	String FOCUS = EventType.FOCUS.getName();
	String BLUR = EventType.BLUR.getName();
	String CHANGE = EventType.CHANGE.getName();
	String CLICK = EventType.CLICK.getName();
	String DBLCLICK = EventType.DBLCLICK.getName();
	String MOUSEOVER = EventType.MOUSEOVER.getName();
	String MOUSEOUT = EventType.MOUSEOUT.getName();
	String MOUSEDOWN = EventType.MOUSEDOWN.getName();
	String MOUSEUP = EventType.MOUSEUP.getName();
	String MOUSEMOVE = EventType.MOUSEMOVE.getName();
	String DRAG = "drag";
	String DROP = "drop";
	String LINK = "link";
	String KEYUP = EventType.KEYUP.getName();
	String KEYDOWN = EventType.KEYDOWN.getName();
	String KEYPRESS = EventType.KEYPRESS.getName();
	String RESIZE = EventType.RESIZE.getName();
	String LOAD = EventType.LOAD.getName();
	String READY_STATE_CHANGE = EventType.READYSTATECHANGE.getName();
	String SUBMIT = EventType.SUBMIT.getName();
	@Deprecated
	String SUBMIT_BUTTON_CLICK = "submitButtonClick";
	@Deprecated
	String LINK_CLICK = "linkClick";
	@Deprecated
	String LINK_DBLCLICK = "linkDblClick";
	@Deprecated
	String RADIO_CHANGE = "radioChange";
	@Deprecated
	String IMAGE_LOAD= "imageLoad";
	@Deprecated
	String SCRIPT_LOAD= "scriptLoad";
	@Deprecated
	String SCRIPT_READY_STATE_CHANGE= "scriptReadyStateChange";
}

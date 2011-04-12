/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.event;

import org.ebayopensource.dsf.active.dom.html.AHtmlHelper;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.jsnative.events.UIEvent;
import org.mozilla.mod.javascript.IJsJavaConverter;
import org.mozilla.mod.javascript.Scriptable;

public class EventConverter implements IJsJavaConverter {
	private KeyEventConverter m_keyEventConverter = new KeyEventConverter();
	private MouseEventConverter m_mouseEventConverter = new MouseEventConverter();
	private UIEventConverter m_uiEventConverter = new UIEventConverter();
	public UIEvent convert(Scriptable s) {
		String eventType = AHtmlHelper.getStringAttributeValue("type", s);
		if(eventType.equals(EventType.KEYPRESS.getName())||
				eventType.equals(EventType.KEYUP.getName())||
				eventType.equals(EventType.KEYDOWN.getName())){
			return m_keyEventConverter.convert(s);
		}else if(eventType.equals(EventType.MOUSEDOWN.getName())||
				eventType.equals(EventType.MOUSEMOVE.getName())||
				eventType.equals(EventType.MOUSEOUT.getName())||
				eventType.equals(EventType.MOUSEOVER.getName())||
				eventType.equals(EventType.CLICK.getName())||
				eventType.equals(EventType.DBLCLICK.getName())||
				eventType.equals(EventType.MOUSEUP.getName())){
			return m_mouseEventConverter.convert(s);
		}else{
			return m_uiEventConverter.convert(s);
		}
	}
}

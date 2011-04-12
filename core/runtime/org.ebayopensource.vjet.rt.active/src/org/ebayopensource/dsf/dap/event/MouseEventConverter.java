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
import org.ebayopensource.dsf.jsnative.events.EventTarget;
import org.ebayopensource.dsf.jsnative.events.MouseEvent;
import org.mozilla.mod.javascript.IJsJavaConverter;
import org.mozilla.mod.javascript.Scriptable;

public class MouseEventConverter implements IJsJavaConverter {

	public MouseEvent convert(Scriptable s) {
		AMouseEvent event = new AMouseEvent();

		EventTarget srcElement = (EventTarget) AHtmlHelper.getAttributeValue(
				"srcElement", s);
		if (srcElement != null) {
			event.setSrcElement(srcElement);
		}
		EventTarget target = (EventTarget) AHtmlHelper.getAttributeValue(
				"target", s);
		if (target != null) {
			event.setTarget(target);
		}
		EventTarget relatedTerget = (EventTarget) AHtmlHelper
				.getAttributeValue("relatedTerget", s);
		if (relatedTerget != null) {
			event.setRelatedTarget(relatedTerget);
		}
		event.setTimestamp(AHtmlHelper.getLongAttributeValue("timeStamp", s));
		event.setType(AHtmlHelper.getStringAttributeValue("type", s));
		event.setMetaKey(AHtmlHelper.getBooleanAttributeValue("metaKey", s));
		event.setCtrlKey(AHtmlHelper.getBooleanAttributeValue("ctrlKey", s));
		event.setShiftKey(AHtmlHelper.getBooleanAttributeValue("shiftKey", s));
		event.setAltKey(AHtmlHelper.getBooleanAttributeValue("altKey", s));
		event.setCancelable(AHtmlHelper.getBooleanAttributeValue("cancelable",
				s));
		event
				.setCanBubble(AHtmlHelper.getBooleanAttributeValue("canBubble",
						s));
		event.setDetail(AHtmlHelper.getIntAttributeValue("detail", s));
		event.setButton(AHtmlHelper.getIntAttributeValue("button", s));
		event.setClientX(AHtmlHelper.getIntAttributeValue("clientX", s));
		event.setClientY(AHtmlHelper.getIntAttributeValue("clientY", s));
		event.setPageX(AHtmlHelper.getIntAttributeValue("pageX", s));
		event.setPageY(AHtmlHelper.getIntAttributeValue("pageY", s));
		event.setScreenX(AHtmlHelper.getIntAttributeValue("screenX", s));
		event.setScreenY(AHtmlHelper.getIntAttributeValue("screenY", s));
		return event;
	}

}

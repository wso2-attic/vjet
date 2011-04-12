/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.events;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ebayopensource.dsf.html.ctx.HtmlCtx;
import org.ebayopensource.dsf.html.events.InlineEventHandlerContainer.EventsToHandlerPair;

public final class InlineEventHandlerConverter {

	private final static String FUNCTION_WRAPPER 
		= "(function()'{'{0}}').apply(event.src)";

	public static void convert() {
		InlineEventHandlerContainer container 
			= HtmlCtx.ctx().getInlineEventHandlerContainer();
		Map<DsfEventTarget, List<EventsToHandlerPair>> elemMap 
			= container.getElements();
		
		for (Entry<DsfEventTarget, 
			List<EventsToHandlerPair>> entry : elemMap.entrySet())
		{
			for (EventsToHandlerPair pair : entry.getValue()) {
				EventHandlerAttacher.add(
					entry.getKey().getElem(), 
					pair.getEventType(), 
					functionWrapper(pair.getHandler()));
			}
		}
	}

	private static String functionWrapper(String code) {
		return MessageFormat.format(FUNCTION_WRAPPER, code);
	}
}

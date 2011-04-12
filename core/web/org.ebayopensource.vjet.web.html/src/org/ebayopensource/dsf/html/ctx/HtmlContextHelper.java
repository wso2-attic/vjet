/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.ctx;

import org.ebayopensource.dsf.html.events.EventHandlerContainer;

/**
 * This class is used to insulate the DsfContext from exporting methods that
 * are generally reserved for use by the framework and/or trusted code/tools.
 * 
 * This approach requires a concrete subclass to be created and then that class
 * has access to the protected methods.  While this approach is not tamper-proof
 * it is better than directly exporting public methods...  where is my friend
 * concept in Java!
 */
public abstract class HtmlContextHelper {
	
//	protected static Object getSubCtx(final HtmlCtx context, final String name) {
//		return context.getSubCtx(name);
//	}
//	
//	protected HtmlCtx void setSubCtx(
//		final HtmlCtx context, final String name, final ISubCtx subCtx)
//	{
//		context.setSubCtx(name, subCtx);
//	}
	
// MrP - make protected	
	public static void setEventHandlerContainer(
		final HtmlCtx context, 
		final EventHandlerContainer eventHandlerContainer)
	{		
		context.setEventHandlerContainer(eventHandlerContainer);
	}
}


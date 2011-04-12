/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.context;

import org.ebayopensource.dsf.common.container.DsfNodeContainer;
import org.ebayopensource.dsf.common.phase.PhaseDriver;


/**
 * This class is used to insulate the DsfContext from exporting methods that
 * are generally reserved for use by the framework and/or trusted code/tools.
 * 
 * This approach requires a concrete subclass to be created and then that class
 * has access to the protected methods.  While this approach is not tamper-proof
 * it is better than directly exporting public methods...  where is my friend
 * concept in Java!
 */
public abstract class ContextHelper {

	protected static void setLifecycle(
		final DsfCtx context, final PhaseDriver lifecycle)
	{
		context.setLifecycle(lifecycle);
	}
	
	protected static void setContainer(
		final DsfCtx context, final DsfNodeContainer container)
	{
		context.setContainer(container);
	}	
	
	protected static void setComponentEventQueue(
		final DsfCtx context, final IDsfNodeEventQueue queue)
	{
		context.setComponentEventQueue(queue);
	}
	
	protected static Object getSubCtx(final DsfCtx context, final String name) {
		return context.getSubCtx(name);
	}
	
	protected static void setSubCtx(
		final DsfCtx context, final String name, final ISubCtx subCtx)
	{
		context.setSubCtx(name, subCtx);
	}
	
	protected static void removeSubCtx(
		final DsfCtx context, final String name)
	{
		context.removeSubCtx(name);
	}
}

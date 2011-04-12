/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.ctx;

import org.ebayopensource.dsf.common.context.BaseSubCtx;
import org.ebayopensource.dsf.common.context.ContextHelper;
import org.ebayopensource.dsf.common.context.DsfCtx;
import org.ebayopensource.dsf.html.events.EventHandlerContainer;
import org.ebayopensource.dsf.html.events.InlineEventHandlerContainer;

public final class HtmlCtx extends BaseSubCtx {
	private EventHandlerContainer m_eventHandlerContainer ;
	private boolean m_disableInlineHandler = false;
	private InlineEventHandlerContainer  m_inlineEventHandlerContainer ;
	
	//
	// ThreadLocal setup
	//
	public static HtmlCtx ctx() {
		return ctx(DsfCtx.ctx());
	}
	
	public static HtmlCtx ctx(final DsfCtx ctx) {
		HtmlCtx context = CtxAssociator.getCtx(ctx);
		if (context == null) {
			context = new HtmlCtx();
			setCtx(context);
		}
		return context;
	}
	
	/**
	 * Sets the context to be associated with this thread local.  The context
	 * can be null.  
	 */
	public static void setCtx(final HtmlCtx context) {
		CtxAssociator.setCtx(context) ;
	}

	//
	// Constructor(s)
	//		
	private HtmlCtx() {
		// empty on purpose
	}
	
	//
	// API
	//
	public EventHandlerContainer getEventHandlerContainer() {
		if(m_eventHandlerContainer==null) {
			m_eventHandlerContainer = new EventHandlerContainer();
		}
		return m_eventHandlerContainer;
	}
	
	void setEventHandlerContainer(final EventHandlerContainer eventHandlerContainer) {
		m_eventHandlerContainer = eventHandlerContainer;
	}
	
	public boolean isDisableInlineHandler() {
		return m_disableInlineHandler;
	}
	
	public void setDisableInlineHandler(boolean set) {
		m_disableInlineHandler = set;
	}
	
	public InlineEventHandlerContainer getInlineEventHandlerContainer() {
		if(m_inlineEventHandlerContainer==null) {
			m_inlineEventHandlerContainer = new InlineEventHandlerContainer();
		}
		return m_inlineEventHandlerContainer;
	}
	
	void setInlineEventHandlerContainer(final InlineEventHandlerContainer inlineEventHandlerContainer) {
		m_inlineEventHandlerContainer = inlineEventHandlerContainer;
	}
	
	public void reset() {
		m_eventHandlerContainer = null ;
		m_disableInlineHandler = false;
		m_inlineEventHandlerContainer = null ;
	}
	
	private static class CtxAssociator extends ContextHelper {
		private static final String CTX_NAME = HtmlCtx.class.getSimpleName();
		protected static HtmlCtx getCtx(final DsfCtx ctx) {
			return (HtmlCtx)getSubCtx(ctx, CTX_NAME);
		}
		
		protected static void setCtx(final HtmlCtx ctx) {
			setSubCtx(DsfCtx.ctx(), CTX_NAME, ctx);
		}
	}
}

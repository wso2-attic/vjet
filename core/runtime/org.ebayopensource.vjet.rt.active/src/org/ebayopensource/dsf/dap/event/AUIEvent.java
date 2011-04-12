/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.event;

import org.ebayopensource.dsf.dap.rt.DapCtx;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.jsnative.events.EventTarget;
import org.ebayopensource.dsf.jsnative.events.UIEvent;

public class AUIEvent extends DapEvent implements UIEvent {

	private static final long serialVersionUID = 1L;
	
	private int m_detail;

	public AUIEvent(EventTarget target, EventType eventType) {
		super(target, eventType);
		popScriptable();
	}

	public AUIEvent() {
		super();
		popScriptable();
	}
	
	public int getDetail() {
		return m_detail;
	}

	public void initUIEvent(String typeArg, boolean canBubbleArg, boolean cancelableArg, Object viewArg, int detailArg) {
		setCanBubble(canBubbleArg);
		setCancelable(cancelableArg);
		setView(viewArg);
		setDetail(detailArg);
	}
	
	public void setDetail(int m_detail) {
		this.m_detail = m_detail;
	}
	
	private void popScriptable(){
		populateScriptable(AUIEvent.class,DapCtx.ctx().getWindow().getBrowserType());
	}
}

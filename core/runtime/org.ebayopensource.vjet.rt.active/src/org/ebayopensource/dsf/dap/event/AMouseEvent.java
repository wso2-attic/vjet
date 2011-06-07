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
import org.ebayopensource.dsf.jsnative.events.MouseEvent;

public class AMouseEvent extends AUIEvent implements MouseEvent {

	private static final long serialVersionUID = 1L;

	private int m_screenX;
	private int m_screenY;
	private int m_clientX;
	private int m_clientY;
	private int m_offsetX;
	private int m_offsetY;
	private int m_x;
	private int m_y;
	private boolean m_ctrlKey;
	private boolean m_shiftKey;
	private boolean m_altKey;
	private boolean m_metaKey;
	private int m_button;
	private String m_type;
	private EventTarget m_relatedTarget;
	private int m_pageX;
	private int m_pageY;
	//
	// Constructor
	//
	public AMouseEvent(EventTarget source, EventType eventType) {
		super(source, eventType);
		m_type = eventType.getName();
		popScriptable();
	}

	public AMouseEvent() {
		super();
		popScriptable();
	}

	private void popScriptable(){
		populateScriptable(AMouseEvent.class,DapCtx.ctx().getWindow().getBrowserType());
	}

	public boolean getAltKey() {
		return m_altKey;
	}

	public int getButton() {
		return m_button;
	}

	public int getClientX() {
		return m_clientX;
	}

	public int getClientY() {
		return m_clientY;
	}
	
	public int getOffsetX() {
		return m_offsetX;
	}

	public int getOffsetY() {
		return m_offsetY;
	}

	public int getX() {
		return m_x;
	}

	public int getY() {
		return m_y;
	}

	public boolean getCtrlKey() {
		return m_ctrlKey;
	}

	public boolean getMetaKey() {
		return m_metaKey;
	}

	public int getScreenX() {
		return m_screenX;
	}

	public void setScreenX(int m_screenx) {
		m_screenX = m_screenx;
	}

	public int getScreenY() {
		return m_screenY;
	}

	public boolean getShiftKey() {
		return m_shiftKey;
	}

	public String getType() {
		return m_type;
	}

	public EventTarget getFromElement() {
		return m_relatedTarget;
	}

	public EventTarget getToElement() {
		return m_relatedTarget;
	}

	public EventTarget getRelatedTarget() {
		return m_relatedTarget;
	}

	public void initMouseEvent(String typeArg, 
            boolean canBubbleArg, 
            boolean cancelableArg, 
            Object viewArg, 
            int detailArg, 
            int screenXArg, 
            int screenYArg, 
            int clientXArg, 
            int clientYArg, 
            boolean ctrlKeyArg, 
            boolean altKeyArg, 
            boolean shiftKeyArg, 
            boolean metaKeyArg, 
            int buttonArg, 
            EventTarget relatedTargetArg){
		setType(typeArg);
		setCanBubble(canBubbleArg);
		setCancelable(cancelableArg);
		setView(viewArg);
		setDetail(detailArg);
		setScreenX(screenXArg);
		setScreenY(screenYArg);
		setClientX(clientXArg);
		setClientY(clientYArg);
		setCtrlKey(ctrlKeyArg);
		setAltKey(altKeyArg);
		setShiftKey(shiftKeyArg);
		setMetaKey(metaKeyArg);
		setButton(buttonArg);
		setRelatedTarget(relatedTargetArg);
	}

	public void setAltKey(boolean key) {
		m_altKey = key;
	}

	public void setButton(int button) {
		m_button = button;
	}

	public void setClientX(int clientX) {
		m_clientX = clientX;
	}

	public void setClientY(int clientY) {
		m_clientY = clientY;
	}
	
	public void setOffsetX(int offsetX) {
		m_offsetX = offsetX;
	}

	public void setOffsetY(int offsetY) {
		m_offsetY = offsetY;
	}
	
	public void setX(int x) {
		m_x = x;
	}

	public void setY(int y) {
		m_y = y;
	}

	public void setCtrlKey(boolean key) {
		m_ctrlKey = key;
	}

	public void setMetaKey(boolean key) {
		m_metaKey = key;
	}

	public void setScreenY(int screenY) {
		m_screenY = screenY;
	}

	public void setShiftKey(boolean key) {
		m_shiftKey = key;
	}

	public void setType(String type) {
		m_type = type;
	}

	public void setRelatedTarget(EventTarget target) {
		m_relatedTarget = target;
	}

	public int getPageX() {
		return m_pageX;
	}

	public int getPageY() {
		return m_pageY;
	}

	public void setPageX(int m_pagex) {
		m_pageX = m_pagex;
	}

	public void setPageY(int m_pagey) {
		m_pageY = m_pagey;
	}

	@Override
	public int getButtons() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getModifierState(String keyIdentifierArg) {
		// TODO Auto-generated method stub
		return false;
	}
}
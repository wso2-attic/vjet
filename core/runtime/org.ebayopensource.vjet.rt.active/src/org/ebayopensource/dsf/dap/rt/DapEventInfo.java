/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import org.ebayopensource.dsf.active.dom.html.AHtmlElement;

public class DapEventInfo{

	private int m_clientX;

	private int m_clientY;

	private int m_screenX;

	private int m_screenY;

	private boolean m_altKey;

	private boolean m_shiftKey;

	private boolean m_ctrlKey;

	private boolean m_cancelBubble;
	
	private short m_button;
	
	private boolean m_metaKey;
	
	private AHtmlElement m_relatedTarget;
	
	private int m_keyCode = -1;
	
	private String m_keyIdentifier = "";

	private int m_keyLocation = 0;

	private boolean m_modifierState;
	
	private boolean m_cancelable;
	
	private long m_timeStamp;
	
	private int m_detail;

	private int m_pageX;

	private int m_pageY;

	private int m_which = -1;

	private int m_charCode = -1;
	
	public DapEventInfo() {
	}

	public boolean isAltKey() {
		return m_altKey;
	}

	public void setAltKey(boolean key) {
		m_altKey = key;
	}

	public int getClientX() {
		return m_clientX;
	}

	public void setClientX(int m_clientx) {
		m_clientX = m_clientx;
	}

	public int getClientY() {
		return m_clientY;
	}

	public void setClientY(int m_clienty) {
		m_clientY = m_clienty;
	}

	public boolean isCtrlKey() {
		return m_ctrlKey;
	}

	public void setCtrlKey(boolean key) {
		m_ctrlKey = key;
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

	public void setScreenY(int m_screeny) {
		m_screenY = m_screeny;
	}

	public boolean isShiftKey() {
		return m_shiftKey;
	}

	public void setShiftKey(boolean key) {
		m_shiftKey = key;
	}

	public boolean isCancelBubble() {
		return m_cancelBubble;
	}

	public void setCancelBubble(boolean bubble) {
		m_cancelBubble = bubble;
	}

	public short getButton() {
		return m_button;
	}

	public void setButton(short m_button) {
		this.m_button = m_button;
	}

	public AHtmlElement getRelatedTarget() {
		return m_relatedTarget;
	}

	public void setRelatedTarget(AHtmlElement target) {
		m_relatedTarget = target;
	}

	public boolean isMetaKey() {
		return m_metaKey;
	}

	public void setMetaKey(boolean key) {
		m_metaKey = key;
	}

	public int getKeyCode() {
		return m_keyCode;
	}

	public void setKeyCode(int code) {
		m_keyCode = code;
	}

	public String getKeyIdentifier() {
		return m_keyIdentifier;
	}

	public void setKeyIdentifier(String identifier) {
		m_keyIdentifier = identifier;
	}

	public int getKeyLocation() {
		return m_keyLocation;
	}

	public void setKeyLocation(int location) {
		m_keyLocation = location;
	}

	public boolean isModifierState() {
		return m_modifierState;
	}

	public void setModifierState(boolean state) {
		m_modifierState = state;
	}

	public boolean isCancelable() {
		return m_cancelable;
	}

	public void setCancelable(boolean m_cancelable) {
		this.m_cancelable = m_cancelable;
	}
	
	public long getTimeStamp() {
		return m_timeStamp;
	}

	public void setTimeStamp(long stamp) {
		m_timeStamp = stamp;
	}

	public int getDetail() {
		return m_detail;
	}

	public void setDetail(int m_detail) {
		this.m_detail = m_detail;
	}

	public int getPageX() {
		return m_pageX;
	}

	public void setPageX(int m_pagex) {
		m_pageX = m_pagex;
	}

	public int getPageY() {
		return m_pageY;
	}

	public void setPageY(int m_pagey) {
		m_pageY = m_pagey;
	}

	public int getWhich() {
		return m_which;
	}

	public void setWhich(int m_which) {
		this.m_which = m_which;
	}

	public int getCharCode() {
		return m_charCode;
	}

	public void setCharCode(int code) {
		m_charCode = code;
	}

}

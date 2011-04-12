/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.liveconnect.client;

import java.util.ArrayList;
import java.util.List;


public class DLCEvent {
	
	private static final List<String> m_bodyEvents = new ArrayList<String>();
	private static final String BODY_PATH = "document.body";
	
	static {
		m_bodyEvents.add("load");
		m_bodyEvents.add("unload");
		m_bodyEvents.add("resize");
	}
	
	private String m_payload;
	
	private String m_type;
	private String m_srcPath;
	private String m_srcId;
	private String m_value;
	
	private Position m_position;
	private KeyInfo m_keyInfo;
	
	private boolean m_cancelBubble;
	private boolean m_cancelable = true;
	
	private short m_button = -1;
	private String m_relatedTarget;
	private String m_relatedTargetId;
	private boolean m_modifierState;
	private long m_timeStamp;
	private int m_detail;

	private int m_which = -1;
	
	private String m_srcProp;
	
	public DLCEvent(String payload) {
		m_payload = payload;
	}
	
	public DLCEvent shallowCopy(){
		
		DLCEvent copy = new DLCEvent(m_payload);

		copy.m_type = m_type;
		copy.m_srcPath = m_srcPath;
		copy.m_srcId = m_srcId;
		copy.m_value = m_value;
		
		copy.m_position = m_position;
		copy.m_keyInfo = m_keyInfo;
		
		copy.m_cancelBubble = m_cancelBubble;
		copy.m_cancelable = m_cancelable;
		
		copy.m_button = m_button;
		copy.m_relatedTarget = m_relatedTarget;
		copy.m_relatedTargetId = m_relatedTargetId;
		copy.m_modifierState = m_modifierState;

		copy.m_timeStamp = m_timeStamp;
		copy.m_detail = m_detail;
		copy.m_which = m_which;

		return copy;
	}

	//
	// API
	//
	public DLCEvent setType(String type){
		m_type = type;
		return this;
	}
	
	public String getType() {
		return m_type;
	}
	
	public String getPayload(){
		return m_payload;
	}
	
	/**
	 * Only for SimpleDLCClient to set the generated payload
	 * @param payload
	 */
	public void setGeneratedPayload(String payload){
		m_payload = payload;
	}
	
	public void setSrcPath(String srcPath){
		m_srcPath = srcPath;
	}
	
	public String getSrcPath(){
		if (m_srcPath != null){
			return m_srcPath;
		}
		
		if (m_bodyEvents.contains(m_type)){
			return BODY_PATH;
		}
		return null;
	}
	
	public void setSrcId(String srcId){
		m_srcId = srcId;
	}
	
	public String getSrcId(){
		return m_srcId;
	}
	
	public void setValue(String value){
		m_value = value;
	}
	
	public String getValue(){
		return m_value;
	}
	
	public void setPosition(Position position){
		m_position = position;
	}
	
	public Position getPosition(){
		return m_position;
	}
	
	public void setKeyInfo(KeyInfo keyInfo){
		m_keyInfo = keyInfo;
	}
	
	public KeyInfo getKeyInfo(){
		return m_keyInfo;
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

	public String getRelatedTarget() {
		return m_relatedTarget;
	}

	public void setRelatedTarget(String target) {
		m_relatedTarget = target;
	}

	public String getRelatedTargetId() {
		return m_relatedTargetId;
	}

	public void setRelatedTargetId(String targetId) {
		m_relatedTargetId = targetId;
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

	public int getWhich() {
		return m_which;
	}

	public void setWhich(int m_which) {
		this.m_which = m_which;
	}
	
	public static class Position {
		private int m_clientX = 0;
		private int m_clientY = 0;
		private int m_screenX = 0;
		private int m_screenY = 0;
		
		private int m_pageX = 0;
		private int m_pageY = 0;
		private int m_mouseX = 0;
		private int m_mouseY = 0;
		
		public int getClientX() {
			return m_clientX;
		}
		public void setClientX(int clientX) {
			m_clientX = clientX;
		}
		public int getClientY() {
			return m_clientY;
		}
		public void setClientY(int clientY) {
			m_clientY = clientY;
		}
		public int getScreenX() {
			return m_screenX;
		}
		public void setScreenX(int screenX) {
			m_screenX = screenX;
		}
		public int getScreenY() {
			return m_screenY;
		}
		public void setScreenY(int screenY) {
			m_screenY = screenY;
		}
		public int getPageX() {
			return m_pageX;
		}
		public void setPageX(int pageX) {
			m_pageX = pageX;
		}
		public int getPageY() {
			return m_pageY;
		}
		public void setPageY(int pageY) {
			m_pageY = pageY;
		}
		public int getMouseX() {
			return m_mouseX;
		}
		public void setMouseX(int mouseX) {
			m_mouseX = mouseX;
		}
		public int getMouseY() {
			return m_mouseY;
		}
		public void setMouseY(int mouseY) {
			m_mouseY = mouseY;
		}
	}
	
	public static class KeyInfo {
		private boolean m_altKey;
		private boolean m_shiftKey;
		private boolean m_ctrlKey;
		private boolean m_metaKey;
		private int m_charCode = 0;
		private int m_keyCode = 0;
		private int m_keyLocation = 0;
		private String m_keyIdentifier;
		
		public boolean isAltKey() {
			return m_altKey;
		}
		public void setAltKey(boolean altKey) {
			m_altKey = altKey;
		}
		public boolean isShiftKey() {
			return m_shiftKey;
		}
		public void setShiftKey(boolean shiftKey) {
			m_shiftKey = shiftKey;
		}
		public boolean isCtrlKey() {
			return m_ctrlKey;
		}
		public void setCtrlKey(boolean ctrlKey) {
			m_ctrlKey = ctrlKey;
		}
		public boolean isMetaKey() {
			return m_metaKey;
		}
		public void setMetaKey(boolean metaKey) {
			m_metaKey = metaKey;
		}
		public int getCharCode() {
			return m_charCode;
		}
		public void setCharCode(int charCode) {
			m_charCode = charCode;
		}
		public int getKeyCode() {
			return m_keyCode;
		}
		public void setKeyCode(int keyCode) {
			m_keyCode = keyCode;
		}
		public int getKeyLocation() {
			return m_keyLocation;
		}
		public void setKeyLocation(int keyLocation) {
			m_keyLocation = keyLocation;
		}
		public String getKeyIdentifier() {
			return m_keyIdentifier;
		}
		public void setKeyIdentifier(String keyIdentifier) {
			m_keyIdentifier = keyIdentifier;
		}
	}

	@Override
	public String toString(){
		return m_payload;
	}

	public String getSrcProp() {
		return m_srcProp;
	}

	public void setSrcProp(String srcProp) {
		this.m_srcProp = srcProp;
	}
}

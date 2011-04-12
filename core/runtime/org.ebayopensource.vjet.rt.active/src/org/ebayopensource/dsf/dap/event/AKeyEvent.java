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
import org.ebayopensource.dsf.jsnative.events.KeyboardEvent;

public class AKeyEvent extends AUIEvent implements KeyboardEvent {

	private static final long serialVersionUID = 1L;

	private int m_keyCode;
	
	private int m_which;
	
	private int m_charCode;
	
	private boolean m_ctrlKey;

	private boolean m_shiftKey;

	private boolean m_altKey;

	private boolean m_metaKey;

	private String m_keyIdentifier;

	private int m_keyLocation;

	private boolean m_modifierState;
	
	public AKeyEvent(EventTarget target, EventType eventType) {
		super(target, eventType);
		popScriptable();
	}

	public AKeyEvent() {
		super();
		popScriptable();
	}

	public boolean getAltKey() {
		return m_altKey;
	}

	public boolean getCtrlKey() {
		return m_ctrlKey;
	}

	public String getKeyIdentifier() {
		return m_keyIdentifier;
	}

	public int getKeyLocation() {
		return m_keyLocation;
	}

	public boolean getMetaKey() {
		return m_metaKey;
	}

	public boolean getModifierState(String keyIdentifierArg) {
		return m_modifierState;
	}

	public boolean getShiftKey() {
		return m_shiftKey;
	}

	public int getKeyCode() {
		return m_keyCode;
	}

	public int getWhich() {
		return m_which;
	}

	public void initKeyboardEvent(String typeArg, boolean canBubbleArg,
			boolean cancelableArg, Object viewArg,
			String keyIdentifierArg, int keyLocationArg, String modifiersListArg) {
		setType(typeArg);
		setCanBubble(canBubbleArg);
		setCanBubble(cancelableArg);
		setView(viewArg);
		setKeyIdentifier(keyIdentifierArg);
		setKeyLocation(keyLocationArg);
		// setModifierState(modifiersListArg);
	}

	public void initKeyboardEventNS(String namespaceURIArg, String typeArg,
			boolean canBubbleArg, boolean cancelableArg, Object viewArg,
			String keyIdentifierArg, int keyLocationArg, String modifiersListArg) {
		// TODO: namespaceURIArg
		setType(typeArg);
		setCanBubble(canBubbleArg);
		setCancelable(cancelableArg);
		setView(viewArg);
		setKeyIdentifier(keyIdentifierArg);
		setKeyLocation(keyLocationArg);
		// setModifierState(modifiersListArg);
	}

	public void setMetaKey(boolean key) {
		m_metaKey = key;
	}

	public void setKeyCode(int code) {
		m_keyCode = code;
	}
	
	public void setWhich(int code) {
		m_which = code;
	}

	public void setKeyIdentifier(String identifier) {
		m_keyIdentifier = identifier;
	}

	public void setKeyLocation(int location) {
		m_keyLocation = location;
	}

	public void setModifierState(boolean state) {
		m_modifierState = state;
	}

	private void popScriptable(){
		populateScriptable(AKeyEvent.class,DapCtx.ctx().getWindow().getBrowserType());
	}

	public void setCtrlKey(boolean key) {
		m_ctrlKey = key;
	}

	public void setShiftKey(boolean key) {
		m_shiftKey = key;
	}

	public void setAltKey(boolean key) {
		m_altKey = key;
	}

	public void setCharCode(int charCode) {
		m_charCode = charCode;
	}

	public int getCharCode() {
		return m_charCode;
	}
}
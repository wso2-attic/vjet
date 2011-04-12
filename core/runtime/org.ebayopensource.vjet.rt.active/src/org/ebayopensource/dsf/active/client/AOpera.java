/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.client;

import java.text.MessageFormat;

import org.ebayopensource.dsf.active.event.IBrowserBinding;
import org.ebayopensource.dsf.jsnative.Opera;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.global.Function;

public class AOpera extends ActiveObject implements Opera {

	private static final long serialVersionUID = -2031523986573037809L;
	
	private static final String SET_OVERRIDE_HISTORY_MODE = 
		"window.opera.setOverrideHistoryNavigationMode({0})";
	
	private static final String SET_PREFERENCE = 
		"window.opera.setPreference({0}, {1}, {2})";
	
	private static final String REMOVE_EVENT_LISTENER = 
		"window.opera.removeEventListener({0}, {1}, {2})";
	
	private static final String ADD_EVENT_LISTENER = 
		"window.opera.addEventListener({0}, {1}, {2})";
	
	private static final String DEFINE_MAGIC_FUNCTION = 
		"window.opera.defineMagicFunction({0}, {1})";
	
	private static final String DEFINE_MAGIC_VARIABLE = 
		"window.opera.defineMagicVariable({0}, {1}, {2})";
	
	private static final String GET_PREFERENCE = 
		"window.opera.getPreference({0}, {1})";
	
	private static final String GET_PREFERENCE_DEFAULT = 
		"window.opera.getPreferenceDefault({0}, {1})";
	
	private IBrowserBinding m_browserBinding;

	public AOpera(BrowserType browserType, IBrowserBinding bowserBinding) {
		m_browserBinding = bowserBinding;
		populateScriptable(AOpera.class, browserType);
	}

	public Object valueOf(String type) {
		if (type.equals("boolean")) {
			return Boolean.TRUE;
		} else if (type.equals("string")) {
			return getClass().getName();
		}
		return null;
	}

	@Override
	public void addEventListener(String type, Function handler, boolean phase) {
		if(m_browserBinding!=null){
			m_browserBinding.executeJs(MessageFormat.format(
					ADD_EVENT_LISTENER, type, handler, phase));
		}
	}

	@Override
	public void collect() {
		if(m_browserBinding!=null){
			m_browserBinding.executeJs("window.opera.collect");
		}
	}

	@Override
	public void defineMagicFunction(String funcName,
			Function replacementFunction) {
		if(m_browserBinding!=null){
			m_browserBinding.executeJs(MessageFormat.format(
					DEFINE_MAGIC_FUNCTION, funcName, replacementFunction));
		}
	}

	@Override
	public void defineMagicVariable(String VarName, Function getter,
			Function setter) {
		if(m_browserBinding!=null){
			m_browserBinding.executeJs(MessageFormat.format(
					DEFINE_MAGIC_VARIABLE, VarName, getter, setter));
		}
	}

	@Override
	public String buildNumber() {
		if(m_browserBinding!=null){
			return m_browserBinding.executeJs("window.opera.buildNumber()");
		}
		return "";
	}

	@Override
	public String getOverrideHistoryNavigationMode() {
		if(m_browserBinding!=null){
			return m_browserBinding.executeJs("window.opera.overrideHistoryNavigationMode()");
		}
		return "";
	}

	@Override
	public String getPreference(String section, String preference) {
		if(m_browserBinding!=null){
			return m_browserBinding.executeJs(MessageFormat.format(
					GET_PREFERENCE, section, preference));
		}
		return "";
	}

	@Override
	public String getPreferenceDefault(String section, String preference) {
		if(m_browserBinding!=null){
			return m_browserBinding.executeJs(MessageFormat.format(
					GET_PREFERENCE_DEFAULT, section, preference));
		}
		return "";
	}

	@Override
	public String version() {
		if(m_browserBinding!=null){
			return m_browserBinding.executeJs("window.opera.version()");
		}
		return "";
	}

	@Override
	public void removeEventListener(String type, Function handler, boolean phase) {
		if(m_browserBinding!=null){
			m_browserBinding.executeJs(MessageFormat.format(
					REMOVE_EVENT_LISTENER, type, handler, phase));
		}
	}

	@Override
	public void setOverrideHistoryNavigationMode(String mode) {
		if(m_browserBinding!=null){
			m_browserBinding.executeJs(MessageFormat.format(
					SET_OVERRIDE_HISTORY_MODE, mode));
		}
	}

	@Override
	public void setPreference(String section, String preference, String value) {
		if(m_browserBinding!=null){
			m_browserBinding.executeJs(MessageFormat.format(
					SET_PREFERENCE, section, preference, value));
		}
	}
}

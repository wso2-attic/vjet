/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.generate.custom;

import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.jsgen.shared.classref.IClassR;
import org.ebayopensource.dsf.jsgen.shared.generate.ICustomJsrProvider;

/**
 * This class provide meta information regarding JSR generation of DAP
 * handlers.
 */
public class DapJsrMeta implements ICustomJsrProvider {
	
	private static final String NATIVE_EVENT = IClassR.JsHandlerObjectEnum+".nativeEvent";
	
	private static DapJsrMeta s_instance = new DapJsrMeta();
	
	private String[] m_pkgNames;
	private Map<String, String> m_eventMap;
	
	private DapJsrMeta() {
		initPkgNames();
		initEventMap();
	}

	public static DapJsrMeta getInstance() {
		return s_instance;
	}
	
	public String getMappedEvent(String event) {
		return m_eventMap.get(event);
	}
	
	public boolean shouldAcceptImport(String importStr) {
		if (importStr == null) {
			return false;
		}
		for (String pkgName : m_pkgNames) {
			if (importStr.startsWith(pkgName)) {
				return false;
			}
		}
		return true;
	}
	
	private void initPkgNames() {
		m_pkgNames = new String[] {
			IClassR.ARenamePkg,
			IClassR.DapEventPkg,
			IClassR.IDapEventListenerPkg,
			IClassR.DapHandlerAdapterSimpleNamePkg
		};
	}
	
	private void initEventMap() {
		m_eventMap = new HashMap<String, String>();
		m_eventMap.put(IClassR.Event, NATIVE_EVENT);
		m_eventMap.put(IClassR.EventSimpleName, NATIVE_EVENT);
		m_eventMap.put(IClassR.DapEvent, NATIVE_EVENT);
		m_eventMap.put(IClassR.DapEventSimpleName, NATIVE_EVENT);
		m_eventMap.put(IClassR.MouseEvent, NATIVE_EVENT);
		m_eventMap.put(IClassR.MouseEventSimpleName, NATIVE_EVENT);
		m_eventMap.put(IClassR.KeyEvent, NATIVE_EVENT);
		m_eventMap.put(IClassR.KeyEventSimpleName, NATIVE_EVENT);
	}

}

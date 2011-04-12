/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.event.listener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.dap.event.DapEvent;
import org.ebayopensource.dsf.html.events.EventType;

/**
 * Please be aware thst this class is *** NOT THREAD SAFE ***
 */
public final class DapEventListenerHelper {

	private static Map<Class<? extends IDapEventListener>,List<EventType>> s_evtTypeMap = 
		new LinkedHashMap<Class<? extends IDapEventListener>,List<EventType>>(10);
	
	private static Map<Class<? extends IDapEventListener>,List<Class<? extends DapEvent>>> s_evtClsMap = 
		new LinkedHashMap<Class<? extends IDapEventListener>,List<Class<? extends DapEvent>>>(10);
	
	private static List<Class<? extends DapEvent>> m_allEvtClsList = new ArrayList<Class<? extends DapEvent>>();

	static {
		add(IBlurListener.class);
		add(IChangeListener.class);
		add(IClickListener.class);
		add(IDblClickListener.class);
		add(IFocusListener.class);
		add(IKeyListener.class);
		add(IKeyUpListener.class);
		add(IKeyDownListener.class);
		add(IKeyPressListener.class);
		add(ILoadListener.class);
		add(IMouseListener.class);
		add(IMouseUpListener.class);
		add(IMouseDownListener.class);
		add(IMouseMoveListener.class);
		add(IMouseOutListener.class);
		add(IMouseOverListener.class);
		add(IResetListener.class);
		add(IResizeListener.class);
		add(IScrollListener.class);
		add(ISelectListener.class);
		add(ISubmitListener.class);
		add(IUnloadListener.class);
	}
	
	public static Collection<Class<? extends IDapEventListener>> getAllEventListeners(){
		return s_evtTypeMap.keySet();
	}
	
	public static List<EventType> getSupportedEventTypes(Class<? extends IDapEventListener> listenerType){
		return s_evtTypeMap.get(listenerType);
	}
	
	public static List<Class<? extends DapEvent>> getSupportedEvents(){
		if (!m_allEvtClsList.isEmpty()){
			return m_allEvtClsList;
		}
		synchronized(DapEventListenerHelper.class){
			for (List<Class<? extends DapEvent>> events: s_evtClsMap.values()){
				for (Class<? extends DapEvent> cls: events){
					if (!m_allEvtClsList.contains(cls)){
						m_allEvtClsList.add(cls);
					}
				}
			}
			return m_allEvtClsList;
		}
	}
	
	//
	// Private
	//
	private static void add(Class<? extends IDapEventListener> listenerType){
		addEvents(listenerType);
		addEventTypes(listenerType);
	}
	
	private static void addEvents(Class<? extends IDapEventListener> listenerType){
		List<Class<? extends DapEvent>> list = s_evtClsMap.get(listenerType);
		if (list == null){
			list = new ArrayList<Class<? extends DapEvent>>();
			s_evtClsMap.put(listenerType, list);
		}
		Method[] mtds = listenerType.getDeclaredMethods();
		Class<?>[] paramTypes;
		Class<?> evtCls;
		for (Method mtd: mtds){
			paramTypes = mtd.getParameterTypes();
			if (paramTypes.length == 0){
				continue;
			}
			evtCls = paramTypes[0];
			if (DapEvent.class.isAssignableFrom(evtCls)) {
				list.add(evtCls.asSubclass(DapEvent.class));
			}
		}
	}
	
	private static void addEventTypes(Class<? extends IDapEventListener> listenerType){
		List<EventType> list = s_evtTypeMap.get(listenerType);
		if (list == null){
			list = new ArrayList<EventType>();
			s_evtTypeMap.put(listenerType, list);
		}
		Method[] mtds = listenerType.getDeclaredMethods();
		String mtdName;
		EventType evtType;
		for (Method mtd: mtds){
			mtdName = mtd.getName();
			evtType = EventType.get(mtdName.substring(2).toLowerCase());
			if (evtType == null){
				evtType = EventType.get(mtdName.toLowerCase());
			}
			if (evtType == null){
				throw new DsfRuntimeException("evtType nor found for: " 
					+ listenerType.getSimpleName() + "," + mtd.getName());
			}
			list.add(evtType);
		}
	}
}

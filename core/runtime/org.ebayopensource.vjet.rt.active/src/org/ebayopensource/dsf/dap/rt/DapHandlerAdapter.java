/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.dap.event.listener.IBlurListener;
import org.ebayopensource.dsf.dap.event.listener.IChangeListener;
import org.ebayopensource.dsf.dap.event.listener.IClickListener;
import org.ebayopensource.dsf.dap.event.listener.IDapEventListener;
import org.ebayopensource.dsf.dap.event.listener.IDblClickListener;
import org.ebayopensource.dsf.dap.event.listener.IFocusListener;
import org.ebayopensource.dsf.dap.event.listener.IJsEventListenerProxy;
import org.ebayopensource.dsf.dap.event.listener.IKeyDownListener;
import org.ebayopensource.dsf.dap.event.listener.IKeyPressListener;
import org.ebayopensource.dsf.dap.event.listener.IKeyUpListener;
import org.ebayopensource.dsf.dap.event.listener.ILoadListener;
import org.ebayopensource.dsf.dap.event.listener.IMouseDownListener;
import org.ebayopensource.dsf.dap.event.listener.IMouseMoveListener;
import org.ebayopensource.dsf.dap.event.listener.IMouseOutListener;
import org.ebayopensource.dsf.dap.event.listener.IMouseOverListener;
import org.ebayopensource.dsf.dap.event.listener.IMouseUpListener;
import org.ebayopensource.dsf.dap.event.listener.IResetListener;
import org.ebayopensource.dsf.dap.event.listener.IResizeListener;
import org.ebayopensource.dsf.dap.event.listener.IScrollListener;
import org.ebayopensource.dsf.dap.event.listener.ISelectListener;
import org.ebayopensource.dsf.dap.event.listener.ISubmitListener;
import org.ebayopensource.dsf.dap.event.listener.IUnloadListener;
import org.ebayopensource.dsf.dap.svc.IDapHostSvcCallback;
import org.ebayopensource.dsf.dap.svc.IDapSvcCallback;
import org.ebayopensource.dsf.dap.svc.IJsSvcCallbackProxy;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.html.events.ISimpleJsEventHandler;
import org.ebayopensource.dsf.html.js.IJsContentGenerator;
import org.ebayopensource.dsf.html.js.IJsFunc;
import org.ebayopensource.dsf.jsnative.events.Event;
import org.ebayopensource.dsf.jsnative.events.EventListener;
import org.ebayopensource.dsf.jsnative.events.KeyboardEvent;
import org.ebayopensource.dsf.jsnative.events.MouseEvent;

/**
 * Base for all DAP handlers. Implements <code>IDapEventListener</code> and 
 * <code>IDapSvcCallback</code>
 * 
 * 
 */
public abstract class DapHandlerAdapter extends JsBase 
	implements IDapEventListener, IDapSvcCallback{
	
	private static final String JSR = "Jsr";	
	private static final Map<EventType,IJsFunc> EMPTY_HANDLERS = new HashMap<EventType,IJsFunc>(0);
	private static final Map<String,IJsFunc> EMPTY_CALLBACKS = new HashMap<String,IJsFunc>(0);
	private static final Map<String,IJsFunc> EMPTY_ERROR_HANDLERS = new HashMap<String,IJsFunc>(0);

	private IJsContentGenerator m_jsContentGenerator =  null;
	private IDapHostSvcCallback m_hostSvcHandler = null;
	private JsProxyMeta m_proxyMeta = null;
	
	//
	// Constructor
	//
	/**
	 * Constructor
	 * @param args Object ...
	 */
	protected DapHandlerAdapter(Object ... args) {
		m_proxyMeta = new JsProxyMeta(getClass(), args);
	}
	
	//
	// Satisfy EventListener
	//
	/**
	 * @see EventListener#handleEvent(Event)
	 */
	public void handleEvent(Event evt){
		if (evt == null){
			return;
		}
		
		if (evt.getType()== null){
			return;
		}
		
		if (EventType.CHANGE.getName().equals(evt.getType())
				&& this instanceof IChangeListener) {
			((IChangeListener) this).onChange(evt);
		} else if (EventType.CLICK.getName().equals(evt.getType())
				&& this instanceof IClickListener) {
			((IClickListener) this).onClick((MouseEvent)evt);
		} else if (EventType.DBLCLICK.getName().equals(evt.getType())
				&& this instanceof IDblClickListener) {
			((IDblClickListener) this).onDblClick((MouseEvent)evt);
		} else if (EventType.BLUR.getName().equals(evt.getType())
				&& this instanceof IBlurListener) {
			((IBlurListener) this).onBlur(evt);
		} else if (EventType.FOCUS.getName().equals(evt.getType())
				&& this instanceof IFocusListener) {
			((IFocusListener) this).onFocus(evt);
		} else if (EventType.LOAD.getName().equals(evt.getType())
				&& this instanceof ILoadListener) {
			((ILoadListener) this).onLoad(evt);
		} else if (EventType.UNLOAD.getName().equals(evt.getType())
				&& this instanceof IUnloadListener) {
			((IUnloadListener) this).onUnload(evt);
		} else if (EventType.RESET.getName().equals(evt.getType())
				&& this instanceof IResetListener) {
			((IResetListener) this).onReset(evt);
		} else if (EventType.SUBMIT.getName().equals(evt.getType())
				&& this instanceof ISubmitListener) {
			((ISubmitListener) this).onSubmit(evt);
		} else if (EventType.RESIZE.getName().equals(evt.getType())
				&& this instanceof IResizeListener) {
			((IResizeListener) this).onResize(evt);
		} else if (EventType.SCROLL.getName().equals(evt.getType())
				&& this instanceof IScrollListener) {
			((IScrollListener) this).onScroll(evt);
		} else if (EventType.SELECT.getName().equals(evt.getType())
				&& this instanceof ISelectListener) {
			((ISelectListener) this).onSelect(evt);
		} else if (EventType.MOUSEDOWN.getName().equals(evt.getType())
				&& this instanceof IMouseDownListener) {
			((IMouseDownListener) this).onMouseDown((MouseEvent)evt);
		} else if (EventType.MOUSEUP.getName().equals(evt.getType())
				&& this instanceof IMouseUpListener) {
			((IMouseUpListener) this).onMouseUp((MouseEvent)evt);
		} else if (EventType.MOUSEMOVE.getName().equals(evt.getType())
				&& this instanceof IMouseMoveListener) {
			((IMouseMoveListener) this).onMouseMove((MouseEvent)evt);
		} else if (EventType.MOUSEOVER.getName().equals(evt.getType())
				&& this instanceof IMouseOverListener) {
			((IMouseOverListener) this).onMouseOver((MouseEvent)evt);
		} else if (EventType.MOUSEOUT.getName().equals(evt.getType())
				&& this instanceof IMouseOutListener) {
			((IMouseOutListener) this).onMouseOut((MouseEvent)evt);
		} else if (EventType.KEYUP.getName().equals(evt.getType())
				&& this instanceof IKeyUpListener) {
			((IKeyUpListener) this).onKeyUp((KeyboardEvent)evt);
		} else if (EventType.KEYDOWN.getName().equals(evt.getType())
				&& this instanceof IKeyDownListener) {
			((IKeyDownListener) this).onKeyDown((KeyboardEvent)evt);
		} else if (EventType.KEYPRESS.getName().equals(evt.getType())
				&& this instanceof IKeyPressListener) {
			((IKeyPressListener) this).onKeyPress((KeyboardEvent)evt);
		}
		return;
	}

	//
	// Satisfy IDapEventListener
	//
	/**
	 * @see IDapEventListener#getEventHandlerAdapter(String,int)
	 */
	public ISimpleJsEventHandler getEventHandlerAdapter(String elemId, int index){
		return new DapEventHandlerAdapter(elemId, index);
	}
	
	/**
	 * @see IDapEventListener#getEventHandlerAdapter(IJsFunc)
	 */
	public ISimpleJsEventHandler getEventHandlerAdapter(IJsFunc func){
		return new DapEventHandlerAdapter(func);
	}
	
	/**
	 * @see IDapEventListener#getHostEventHandler()
	 */
	public DapHostEventHandler getHostEventHandler(){
		return new DapHostEventHandler(this);
	}
	
	//
	// Satisfy IDapEventListener
	//
	public Map<EventType,IJsFunc> getProxyEventHandlers(){
		if (m_proxyMeta != null) {
			return m_proxyMeta.m_evtHandlers;
		}
		return EMPTY_HANDLERS;
	}
	
	//
	// Satisfy IDapSvcCallback
	//
	public IJsContentGenerator getSvcCallbackAdapter(String svcId, int index){
		if (m_jsContentGenerator == null){
			m_jsContentGenerator = new DapSvcHandlerAdapter(svcId, index);
		}
		return m_jsContentGenerator;
	}
	
	public IDapHostSvcCallback getHostSvcCallback(){
		if (m_hostSvcHandler == null){
			m_hostSvcHandler = new DapHostSvcHandler(this);
		}
		return m_hostSvcHandler;
	}

	public Map<String,IJsFunc> getProxySvcCallbacks(){
		if (m_proxyMeta != null) {
			return m_proxyMeta.m_svcCallbacks;
		}
		return EMPTY_CALLBACKS;
	}
	
	public Map<String,IJsFunc> getProxySvcErrorHandlers(){
		if (m_proxyMeta != null) {
			return m_proxyMeta.m_svcErrorHandlers;
		}
		return EMPTY_ERROR_HANDLERS;
	}
	
	//
	// Private
	//
	private static class JsProxyMeta {
		private Object m_proxyInstance = null;
		private Map<EventType,IJsFunc> m_evtHandlers = EMPTY_HANDLERS;
		private Map<String,IJsFunc> m_svcCallbacks = EMPTY_CALLBACKS;
		private Map<String,IJsFunc> m_svcErrorHandlers = EMPTY_ERROR_HANDLERS;
		
		JsProxyMeta(Class<?> clz, Object... args) {
			Constructor<?> cnstr = JsProxyConstructorCache.get(clz, args);
			if (cnstr != null) {
				try {
					m_proxyInstance = cnstr.newInstance(args);
				} catch (Exception e) {
					throw new DsfRuntimeException(e);
				} 
				if (m_proxyInstance instanceof IJsEventListenerProxy){
					m_evtHandlers = ((IJsEventListenerProxy)m_proxyInstance)
						.getProxyEventHandlers();
				}
				if (m_proxyInstance instanceof IJsSvcCallbackProxy){
					m_svcCallbacks = ((IJsSvcCallbackProxy)m_proxyInstance)
						.getProxySvcCallbacks();
				}
				if (m_proxyInstance instanceof IJsSvcCallbackProxy){
					m_svcErrorHandlers = ((IJsSvcCallbackProxy)m_proxyInstance)
						.getProxySvcErrorHandlers();
				}
			}
		}
	}
	
	private static class JsProxyConstructorCache {
		private static Map<Class<?>, ConstructorMeta[]> s_constructors
			= new HashMap<Class<?>, ConstructorMeta[]>();
		
		synchronized static Constructor<?> get(Class<?> clz, Object[] args) {
			ConstructorMeta[] cnstrMetas = s_constructors.get(clz);
			if (cnstrMetas == null) {
				if (s_constructors.containsKey(clz)) {
					return null;
				}
				String jsrName = clz.getName() + JSR;
				try {
					Class<?> jsrClz = Class.forName(jsrName);
					Constructor<?>[] cnstrs = jsrClz.getConstructors();
					cnstrMetas = new ConstructorMeta[cnstrs.length];
					for (int i = 0; i < cnstrs.length; i++) {
						cnstrMetas[i] = new ConstructorMeta
							(cnstrs[i], cnstrs[i].getParameterTypes());
					}
					s_constructors.put(clz, cnstrMetas);			
				} 
				catch (ClassNotFoundException e) {
					s_constructors.put(clz, null);
					return null;
				}
			}
			if (cnstrMetas.length == 1) {
				return cnstrMetas[0].m_cnstr;
			}
			for (ConstructorMeta cnstrMeta : cnstrMetas) {
				if (cnstrMeta.isApplicable(args)) {
					return cnstrMeta.m_cnstr;
				}
			}
			return null;
		}
	}
	
	private static class ConstructorMeta {
		Constructor<?> m_cnstr;
		Class<?>[] m_paramTypes;
		
		ConstructorMeta(Constructor<?> cnstr, Class<?>[] paramTypes) {
			m_cnstr = cnstr;
			m_paramTypes = paramTypes;
			for (int i = 0; i < m_paramTypes.length; i++) {
				if (m_paramTypes[i].isPrimitive()) {
					m_paramTypes[i] = s_primitiveToWrapperMapping.get(m_paramTypes[i]);
				}
			}
		}
		
		boolean isApplicable(Object[] args) {
			if (m_paramTypes.length != args.length) {
				return false;
			}
			for (int i = 0; i < m_paramTypes.length; i++) {
				if (args[i] != null && !m_paramTypes[i].isInstance(args[i])) {
					return false;
				}
			}
			return true;
		}
		
		private static Map<Class<?>, Class<?>> s_primitiveToWrapperMapping 
			= new HashMap<Class<?>, Class<?>>();
		static {
			s_primitiveToWrapperMapping.put(Boolean.TYPE, Boolean.class);
			s_primitiveToWrapperMapping.put(Character.TYPE, Character.class);
			s_primitiveToWrapperMapping.put(Byte.TYPE, Byte.class);
			s_primitiveToWrapperMapping.put(Short.TYPE, Short.class);
			s_primitiveToWrapperMapping.put(Integer.TYPE, Integer.class);
			s_primitiveToWrapperMapping.put(Long.TYPE, Long.class);
			s_primitiveToWrapperMapping.put(Float.TYPE, Float.class);
			s_primitiveToWrapperMapping.put(Double.TYPE, Double.class);
		}
	}
}
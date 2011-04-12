/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.active.client.ANavigator;
import org.ebayopensource.dsf.active.client.APlugin;
import org.ebayopensource.dsf.active.client.AWindow;
import org.ebayopensource.dsf.active.dom.html.AHtmlAnchor;
import org.ebayopensource.dsf.active.dom.html.AHtmlButton;
import org.ebayopensource.dsf.active.dom.html.AHtmlDocument;
import org.ebayopensource.dsf.active.dom.html.AHtmlElement;
import org.ebayopensource.dsf.active.dom.html.AHtmlFactory;
import org.ebayopensource.dsf.active.dom.html.AHtmlHelper;
import org.ebayopensource.dsf.active.event.IBrowserBinding;
import org.ebayopensource.dsf.active.event.IDomChangeListener;
import org.ebayopensource.dsf.active.event.IDomEventBindingListener;
import org.ebayopensource.dsf.dap.event.DapEvent;
import org.ebayopensource.dsf.html.dom.DObject;
import org.ebayopensource.dsf.jsnative.HtmlButton;
import org.ebayopensource.dsf.liveconnect.IDLCDispatcher;
import org.ebayopensource.dsf.liveconnect.client.DLCEvent;
import org.ebayopensource.dsf.liveconnect.client.simple.SimpleDLCEventTypes;

/**
 * <p>DapBrowserEngine reacts to each data received from DapBrowserBridge. 
 * It is designed to be one instance per html document. </p>
 * <p>During construction DapBrowserEmulator creates an instance of DapWindowManager 
 * for the given html.</p>
 * <p>Upon receiving data from browser bridge, depending on event type, DapBrowserEmulator 
 * may sends msg back and return right way (ex. link click event), or update dom then 
 * delegate to DapEventDispatcher for event processing (ex. change event).</p>
 */
public class DapBrowserEngine {
	
	private static DapEventCreator s_eventGenerator = DapEventCreator.getInstance();
	private static DapEventDispatcher s_dapEventDispatcher = DapEventDispatcher.getInstance();
	
	private DapWindowManager m_windowManager;
	
	private IDLCDispatcher m_dlcDispatcher;

	//
	// Constructor
	//
	/**
	 * Constructor
	 * It will setup context and instantiate new window manager for given html.
	 * All given listeners and bindings will be passed along to the window manager.
	 * @param html String
	 * @param browserBinding IBrowserBinding
	 * @param dlcDispatcher IDLCDispatcher
	 * @param domChangeListeners List<IDomChangeListener>
	 * @param domEventBindingListener IDomEventBindingListener
	 */
	public DapBrowserEngine(
			final String html,
			final IBrowserBinding browserBinding,
			final IDLCDispatcher dlcDispatcher,
			List<IDomChangeListener> domChangeListeners,
			final IDomEventBindingListener domEventBindingListener) {
		
		m_dlcDispatcher = dlcDispatcher;
		if (domChangeListeners == null) {
			domChangeListeners = new ArrayList<IDomChangeListener>(1);
		}
		domChangeListeners.add(new DapDomChangeListener(m_dlcDispatcher));
		
		// Reset window
		AWindow window = DapCtx.window();
		if (window != null) {
			window.finialize();
		}

		DapCtx.ctx().setEventDispatcher(s_dapEventDispatcher);

		// Instantiate window manager
		m_windowManager = new DapWindowManager(
			html,
			domChangeListeners,
			DapEventEngine.getInstance(),
			domEventBindingListener,
			browserBinding);
		
		// TEMP: Register object types TODO: get from registry
		registerObjectType("flash_proxy", DapFlashProxy.class);
		
		// TEMP: Register plug TODO: get from registry
		window = DapCtx.ctx().getWindow();
		APlugin flashPlugin = new APlugin(window.getBrowserType());
    	flashPlugin.setName("Shockwave Flash");
    	flashPlugin.setDescription("Shockwave Flash 10.0 r12");
    	((ANavigator)window.getNavigator()).addPlugin(flashPlugin);
	}
	
	//
	// API
	//
	public AWindow getWindow(){
		return m_windowManager.getWindow();
	}
	
	public IDLCDispatcher getDispatcher(){
		return m_dlcDispatcher;
	}
	
	/**
	 * Process received data from client, which can real or simulated browser
	 * @param data String
	 */
	public void onReceive(final DLCEvent event) {
		
		String eventType = event.getType();
		DapEvent dapEvent =  getDapEvent(event);
		if (SimpleDLCEventTypes.SUBMIT.equals(eventType)) {
			dapEvent = getDapEvent(event);
			if(s_dapEventDispatcher.dispatchEvent(dapEvent)){
				m_dlcDispatcher.send(event.getSrcPath() + ".submit()");
			}
			return;
		}
		else if (SimpleDLCEventTypes.CLICK.equals(dapEvent.getType())
				|| SimpleDLCEventTypes.DBLCLICK.equals(dapEvent.getType())) {
			if (dispatchAnchorEvent(dapEvent, event)) {
				return;
			}else if (dispatchSubmitBtnEvent(dapEvent, event)) {
				return;
			}
		}	
		dapEvent = getDapEvent(event);
		m_windowManager.updateDDom(dapEvent, event);
		s_dapEventDispatcher.dispatchEvent(dapEvent);
	}
	
	public void shutdown() {
		m_windowManager.close();
	}
	
	// TEMP: TODO move to registry 
	public static void registerObjectType(String objId, Class<?> objType){
		
		if (objId == null){
			throw new RuntimeException("objId is null ");
		}
		
		if (objType == null){
			throw new RuntimeException("objType is null ");
		}

		Class<?> dObjClz = DObject.class;
		Class<?>[] args = new Class[] {AHtmlDocument.class, dObjClz};
		try {
			AHtmlFactory.setObjectConstructor(objId, objType.getDeclaredConstructor(args));
		}
		catch(Exception e){
			throw new RuntimeException("Failed to find constructor for " + objType.getName());
		}
	}

	//
	// Private
	//
	private static Map<String,String> s_eventMap = new HashMap<String,String>();
	static {
		s_eventMap.put(SimpleDLCEventTypes.LINK_CLICK, SimpleDLCEventTypes.CLICK);
		s_eventMap.put(SimpleDLCEventTypes.LINK_DBLCLICK, SimpleDLCEventTypes.DBLCLICK);
		s_eventMap.put(SimpleDLCEventTypes.SUBMIT_BUTTON_CLICK, SimpleDLCEventTypes.CLICK);
		s_eventMap.put(SimpleDLCEventTypes.IMAGE_LOAD, SimpleDLCEventTypes.LOAD);
		s_eventMap.put(SimpleDLCEventTypes.SCRIPT_LOAD, SimpleDLCEventTypes.LOAD);
		s_eventMap.put(SimpleDLCEventTypes.RADIO_CHANGE, SimpleDLCEventTypes.CLICK);
		s_eventMap.put(SimpleDLCEventTypes.SCRIPT_READY_STATE_CHANGE, SimpleDLCEventTypes.READY_STATE_CHANGE);
	}
	
	private DapEvent getDapEvent(final DLCEvent event){
		String toType = s_eventMap.get(event.getType());
		if (toType == null){
			return s_eventGenerator.createEvent(event);
		}
		
		DLCEvent toEvent = event.shallowCopy()
			.setType(toType);
		
		return s_eventGenerator.createEvent(toEvent);
	}

	private boolean dispatchAnchorEvent(final DapEvent dapEvent, final DLCEvent event){
		AHtmlElement elem = (AHtmlElement) dapEvent.getTarget();
		if (elem instanceof AHtmlAnchor) {
			if (s_dapEventDispatcher.dispatchEvent(dapEvent)) {
				m_dlcDispatcher.send("document.location.href="
						+ event.getSrcPath() + ".href");
			}
			return true;
		}
		return false;
	}

	private boolean dispatchSubmitBtnEvent(final DapEvent dapEvent, final DLCEvent event){
		AHtmlElement elem = (AHtmlElement) dapEvent.getTarget();
		if (elem instanceof AHtmlButton) {
			if(s_dapEventDispatcher.dispatchEvent(dapEvent)){
				if(HtmlButton.TYPE_SUBMIT.equals(((AHtmlButton)elem).getType())){
					DLCEvent submit = event.shallowCopy()
						.setType(SimpleDLCEventTypes.SUBMIT);
					DapEvent sbmtDapEvent = s_eventGenerator.createEvent(submit);
					if(s_dapEventDispatcher.dispatchEvent(sbmtDapEvent)){
						String formPath = AHtmlHelper.getFormPath(sbmtDapEvent.getTarget());
						if (formPath != null){
							String elemRef = submit.getSrcPath();
							m_dlcDispatcher.send("DLC_onSubmitButtonClick(" + elemRef + ")");
							m_dlcDispatcher.send(formPath + ".submit()");
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	public void executeTask(String msg) {
		int id = Integer.parseInt(msg.substring(DapTaskMsgHandler.NAMESPACE.length() + 2));
		//System.out.println("executeTask: " + id);
		m_windowManager.executeTask(id);
	}

}

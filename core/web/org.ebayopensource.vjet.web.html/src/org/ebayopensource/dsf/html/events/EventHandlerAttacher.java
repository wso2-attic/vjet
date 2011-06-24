/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.events;

import org.ebayopensource.dsf.common.context.DsfCtx;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.html.ctx.HtmlCtx;
import org.ebayopensource.dsf.html.dom.BaseHtmlElement;
import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;
import org.ebayopensource.dsf.html.dom.util.InvisibleContainer;
import org.ebayopensource.dsf.html.js.IJsFunc;
import org.ebayopensource.dsf.html.js.JsFunctionEventHandlerAdaptor;
/**
 * 
 * This class allows attaching event handler on a target html element for a 
 * specified event type.  All the html elements along with corresponding event 
 * type and event handlers are registered to the DSF event handler container 
 * {@link EventHandlerContainer}, this acts as a event dispatcher.  Whenever 
 * an event is triggered, it is captured by the EventHandlerContainer and it 
 * dispatch it to the corresponding event handler based on the event and 
 * html element.
 * 
 * This also provides functions to remove the already registered event 
 * handlers from the DSF EventHandlerContainer. 
 *  
 * Most of the VJO's jsr methods return IJsFunc. This class helps developer
 * to use this IJsFunc as an event handler when writing components which refers 
 * java script through jsr files.
 * 
 * for ex.,
 * 
 * Below code creates a html buttons and add onClick event handler to it.  
 * Java script event handler method "yes" is refered from a jsr file 
 * MyButtonJsr.java which returns IJsFunc object. 
 * 
 * <code>
 * DInput buttonYes = new DInput();
 * DInput buttonNo = new DInput();
 * DInput buttonYesNo = new DInput();
		   
 * buttonNo.setHtmlName("Nname");	
 * buttonYesNo.setHtmlId("YNId");  
 * buttonYes.setAttribute("type", "submit");
 * buttonNo.setAttribute("type", "submit");
 * buttonYesNo.setAttribute("type", "submit");	  	
 * EventHandlerAttacher.add(buttonYes ,
 * 							EventType.CLICK, AjaxSampleEx1Jsr.yes());
 * EventHandlerAttacher.add(buttonNo.getHtmlName(),
 * 							EventType.CLICK, MyButtonJsr.yes());
 * EventHandlerAttacher.add(buttonYesNo.getHtmlId(),
 * 							EventType.CLICK, MyButtonJsr.yes());
 * </code>		
 * 
 * 
 * Add methods in this class are identical to add methods in {@ link DElement}. 
 *
 */
public class EventHandlerAttacher {
	
	/**
	 * Add event handler to the html element for the given event type and 
	 * register this with the {@link EventHandlerContainer} the DSF event 
	 * handler container. 
	 * 
	 * @param element, html element to add event and handler
	 * @param eventType, type of event html element to respond 
	 * @param handler, event handler to perform the action for the event.
	 */
	public static void add(
		final DElement element,
		final IDomEventType eventType,
		final ISimpleJsEventHandler handler)
	{
		assertValidParams(element, handler);
		if (element instanceof BaseHtmlElement) {
			BaseHtmlElement elem = (BaseHtmlElement)element ;
			if (elem.getHtmlId().equals("")) {
				elem.setHtmlId(DsfCtx.ctx().ids().nextHtmlId());
			}			
		}
		else {
			if (element.getAttribute("id").equals("")) {
				element.setAttribute("id", DsfCtx.ctx().ids().nextHtmlId()) ;
			}
		}
//		if (element.getHtmlId().equals("")) {
//			element.setHtmlId(DsfCtx.ctx().ids().nextHtmlId());
//		}
		//use by id always
		//getEventHandlerContainer().add(element.getHtmlId(), eventType, handler);
		getEventHandlerContainer().add(element, eventType, handler);
	}
	
	/**
	 * Add event handler to the html element for the given event type.  
	 * 
	 * This helps developer using jsr files for java script reference which
	 * methods normally return IJsFunc.  
	 * 
	 * @param element html element to add event and handler
	 * @param eventType event type to be added to html element
	 * @param jsText event handler as Javascript text
	 */
	public static void add(
		final DElement element, final IDomEventType eventType, final String jsText)
	{
		add(element, eventType, viaText(jsText));
	}

	/**
	 * Add event handler to the html element for the given event type.  
	 * 
	 * This supports using jsr files for Javascript reference 
	 * methods which normally return IJsFunc.  
	 * 
	 * @param element html element to add event and handler
	 * @param eventType event type to be added to html element
	 * @param jsFunc event handler as a javascript function
	 */
	public static void add(
		final DElement element,final IDomEventType eventType, final IJsFunc jsFunc)
	{
		add(element, eventType, new JsFunctionEventHandlerAdaptor(jsFunc));
	}

	public static void add(
		final String elementId,
		final IDomEventType eventType,
		final ISimpleJsEventHandler handler)
	{
		assertValidParams(elementId, handler);
		getEventHandlerContainer().add(elementId, eventType, handler);
	}

	public static void add(
		final String elementId, 
		final IDomEventType eventType,
		final String jsText)
	{
		add(elementId, eventType, viaText(jsText));
	}

	public static void add(
		final String element, 
		final IDomEventType eventType,
		final IJsFunc jsFunc)
	{
		add(element, eventType, new JsFunctionEventHandlerAdaptor(jsFunc));
	}
	
	/**
	 * Add event handler to the html element for the given event type.  Html 
	 * element is represented by {@link IDomType}object and handler as a 
	 * {@link ISimpleJsEventHandler} object. 
	 * 
	 * Developer at the component level does not have access to html body 
	 * element.  This method comes in handy for those needs to refer html 
	 * body element and attach event handler.
	 *  
	 * @param element {@link IDomType} object representing html element 
	 * to add event and handler
	 * @param eventType event type to be added to html element
	 * @param handler event handler {@link ISimpleJsEventHandler} object 
	 * to perform the 
	 * action for the event type.
	 */
	public static void add(
		final IDomType element,
		final IDomEventType eventType,
		final ISimpleJsEventHandler handler)
	{
		assertValidParams(element, handler);
		getEventHandlerContainer().add(element, eventType, handler);
	}

	public static void add(
		final IDomType element,
		final IDomEventType eventType, 
		final String jsText)
	{
		add(element, eventType, viaText(jsText));
	}

	public static void add(
		final IDomType element,
		final IDomEventType eventType, 
		final IJsFunc jsFunc)
	{
		add(element, eventType,  new JsFunctionEventHandlerAdaptor(jsFunc));
	}
//	/**
//	 * Add event listener to the HTML body element.  
//	 * @param listener {@link IDapEventListener} listener object
//	 */
//	public static void addToBody(final IDapEventListener listener) {
//		getDapEventListenerRegistry().addBodyListener(listener);
//	}

//	/**
//	 * Add event listner to the html element for the given event type and add 
//	 * the listener along with element and event type to the 
//	 * {@link DapEventListenerRegistry}
//	 * 
//	 * @param elem the html element 
//	 * @param eventType the type of event
//	 * @param listener  event listner
//	 */
//	public static void add(final BaseHtmlElement elem, 
//			final EventType eventType,
//			final IDapEventListener listener) {
//		getDapEventListenerRegistry().addListener(elem,eventType,listener);
//	}

	/**
	 * Removes the specified event handler associated with the element from the
	 * EventHandlerContainer.  Assert if the passed element or handler is null 
	 * or element is not of type InvisibleContainer.
	 * 
	 * @param element html element from which handler to be removed
	 * @param handler event handler to be removed
	 * @return <tt>true</tt> if handler is removed successfully otherwise <tt>false</tt>
	 */
	public static boolean remove(
		final String element, final ISimpleJsEventHandler handler)
	{
		assertValidParams(element, handler);
		return getEventHandlerContainer().removeHandler(element, handler);
	}

	public static boolean remove(
		final DElement element,final ISimpleJsEventHandler handler)
	{
		assertValidParams(element, handler);
		return getEventHandlerContainer().removeHandler(element, handler);
	}

	public static boolean remove(
		final IDomType element,final ISimpleJsEventHandler handler)
	{
		assertValidParams(element, handler);
		return getEventHandlerContainer().removeHandler(element, handler);
	}

	/**
	 * Determine if html element has any event handlers associated with it.
	 * 
	 * @param element html element to check for event handler
	 * @return <tt>true</tt> if html element has at least one event handler
	 */
	public static boolean hasEventHandlers(final DElement elem) {
		return getEventHandlerContainer().getNumOfHandlers(elem) > 0 ? true: false;
	}

	//
	// Private
	//
	/**
	 * Name of the Java Script event handler adapter.
	 */
//	private static final Class<?> Clz = getClz();

	/**
	 * Java Method object point to "viaText" method of Java Script event 
	 * handler adapter.
	 */
//	private static final Method Mthd = getMthd(Clz);

	/**
	 * Loads Java Script event handler adapter into memory and return.  
	 * @return Java Script event handler adapter class. 
	 */
//	private static Class<?> getClz() {
//		try {
//			Class<?> clz = Class.forName(
//				"org.ebayopensource.dsf.resource.html.event.handler.JsEventHandlerAdapter");
//			return clz;
//		} 
//		catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

	/**
	 * Get java Method "viaText" from the given class.
	 * @param clz java class to look into for the method.
	 * @return method from the given java class
	 */
//	private static Method getMthd(Class<?> clz) {
//		try {
//			Method method = clz.getDeclaredMethod("viaText", String.class);
//			return method;
//		}
//		catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

	/**
	 * Call viaText method of Java Script event handler adapter.
	 * @param jsText java script text to pass to viaText method 
	 * of Java Script event handler adapter
	 * @return
	 */
	private static ISimpleJsEventHandler viaText(final String jsText) {
		try {
			// MrPEvents - yikes, what a hack - need test to make sure we always
			// can find the class/method
			Object[] viaTextArgs = { jsText };
//			return (ISimpleJsEventHandler) Mthd.invoke(Clz, viaTextArgs);
			return new ISimpleJsEventHandler() {
				
				@Override
				public String asJsHandler() {
					return jsText;
				}
				
				@Override
				public String asJsDefinition() {
					return jsText;
				}
			};
		} 
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get event handler container from the DSF context.
	 * @return {@link EventHandlerContainer}
	 */
	private static EventHandlerContainer getEventHandlerContainer() {
		return HtmlCtx.ctx().getEventHandlerContainer();
	}

	/**
	 * Assert if handler is null or element is either null or not instance of 
	 * InvisibleContainer.
	 * @param element element to check for null or InvisibleContainer
	 * @param handler handler to check for null
	 */
	private static void assertValidParams(
		final Object element, final ISimpleJsEventHandler handler)
	{
		assertValidElement(element);
		assertValidHandler(handler);
	}

	/**
	 * Assert if passed handler is null.
	 * @param handler handler to check.
	 */
	private static void assertValidHandler(final ISimpleJsEventHandler handler) {
		if (handler == null) {
			chuck("handler is null");
		}
	}

	/**
	 * Assert if element is null or not instance of InvisibleContainer.
	 * @param element
	 */
	private static void assertValidElement(final Object element) {
		if (element == null) {
			chuck("element is null");
		}
		if (element instanceof InvisibleContainer) {
			chuck("Can not attach events to invisible container");
		}
	}

	/**
	 * Helper method to throw DSF runtime exception constructed with the 
	 * given string value.
	 * 
	 * @param string
	 */
	private static void chuck(String string) {
		throw new DsfRuntimeException(string);
	}

//	/**
//	 * Get {@link DapEventListenerRegistry} object from the DAP context.
//	 * @return the {@link DapEventListenerRegistry}
//	 */
//	private static DapEventListenerRegistry getDapEventListenerRegistry() {
//		return DapCtx.ctx().getEventListenerRegistry();
//	}

	/**
	 * Add event handler to html body on load event.
	 * @param handler event handler
	 */
	public static void addToBodyLoad(final ISimpleJsEventHandler handler) {
		addToBody(EventType.LOAD,handler);
	}

	public static void addToBodyLoad(final String jsText) {
		addToBody(EventType.LOAD,jsText);
	}

	/**
	 * Add event handler to html body unload event.
	 * @param handler the handler object
	 */
	public static void addToBodyUnload(final ISimpleJsEventHandler handler) {
		addToBody(EventType.UNLOAD,handler);
	}

	public static void addToBodyUnload(final String jsText) {
		addToBody(EventType.UNLOAD,jsText);
	}

	public static void addToBodyLoad(final IJsFunc jsFunc) {
		addToBody(EventType.LOAD,jsFunc);
	}

	/**
	 * Add event handler to html body unload event.
	 * @param handler the handler object
	 */
	public static void addToBodyUnload(final IJsFunc jsFunc) {
		addToBody(EventType.UNLOAD,jsFunc);
	}

	public static void addToBody(final IDomEventType eventType,
			final ISimpleJsEventHandler handler) {
		getEventHandlerContainer().add(HtmlTypeEnum.BODY, eventType, handler);
	}

	public static void addToBody(final IDomEventType eventType, final String jsText) {
		add(HtmlTypeEnum.BODY, eventType, viaText(jsText));
	}

	public static void addToBody(final IDomEventType eventType, final IJsFunc jsFunc) {
		add(HtmlTypeEnum.BODY, eventType, jsFunc);
	}
}

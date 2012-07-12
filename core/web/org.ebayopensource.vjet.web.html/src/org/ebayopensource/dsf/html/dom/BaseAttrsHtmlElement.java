/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom;

import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.html.events.ISimpleJsEventHandler;
import org.ebayopensource.dsf.html.js.IJsFunc;

//import org.ebayopensource.dsf.common.context.DsfCtx;
//import org.ebayopensource.dsf.dap.event.listener.IDapEventListener;
//import org.ebayopensource.dsf.dap.rt.DapCtx;
//import org.ebayopensource.dsf.html.events.EventHandlerAttacher;
//import org.ebayopensource.dsf.html.events.EventHandlerContainer;
//import org.ebayopensource.dsf.html.events.EventType;
//import org.ebayopensource.dsf.html.events.ISimpleJsEventHandler;
//import org.ebayopensource.dsf.html.js.IJsFunc;
//import org.ebayopensource.dsf.common.Z;

/** 
 * @Deprecated - Use BaseHtmlElement instead.
 * <br>This is an abstract interface that implements an HTML element that has
 * "attrs" attributes as defined by the w3c spec:
 * <code>
 * http://www.w3.org/TR/html401/sgml/dtd.html#attrs
 * </code>.
 * This means that it has core attributes, i18n attributes and events
 * attributes.
 */
@Deprecated
public abstract class BaseAttrsHtmlElement extends BaseCoreHtmlElement {
	//
	// Constructor(s)
	//
	BaseAttrsHtmlElement(final HtmlTypeEnum type) {
		this(null, type);
	}
	BaseAttrsHtmlElement(final DHtmlDocument doc,final HtmlTypeEnum type){
		super(doc, type);
	}
	
//	// Some funkery...
	@Override
	public BaseAttrsHtmlElement add(
		final EventType eventType, 
		final ISimpleJsEventHandler handler)
	{
		super.add(eventType, handler);
		return this ;
	}
	
	@Override
	public BaseAttrsHtmlElement add(
		final EventType eventType, 
		final IJsFunc func)
	{
		super.add(eventType, func);
		return this ;
	}
	
	@Override
	public BaseAttrsHtmlElement add(
		final EventType eventType, 
		final String jsText)
	{
		super.add(eventType, jsText);
		return this ;
	}
	
//	public boolean hasEventHandlers() {
//		return getEventHandlerContainer().getNumOfHandlers(this) > 0 ? true : false;
//	}
//	
//	public boolean removeEventHandler(final ISimpleJsEventHandler handler) {
//		return getEventHandlerContainer().removeHandler(this, handler);
//	}
//	
//	private EventHandlerContainer getEventHandlerContainer() {
//		return DsfCtx.ctx().getEventHandlerContainer() ;
//	}
//	
//	@Override
//	public BaseAttrsHtmlElement add(final IDomActiveListener listener){
//		super.add(listener);
//		return this ;
//	}
//	
//	@Override
//	public BaseAttrsHtmlElement add(
//		final EventType eventType, final IDomActiveListener listener)
//	{
//		super.add(eventType, listener);
//		return this ;
//	}
//	
//	@Override
//	public BaseAttrsHtmlElement removeListener(
//		final EventType eventType, final IDomActiveListener listener)
//	{
//		super.removeListener(eventType, listener);
//		return this ;
//	}
	
//	//
//	// Spec API
//	//
//	public String getHtmlLang() {
//		return getHtmlAttribute(EHtmlAttr.lang);
//	}
//
	@Override
	public BaseAttrsHtmlElement setHtmlLang(final String lang) {
		super.setHtmlLang(lang);
		return this ;
	}
//
//	public String getHtmlDir() {
//		return getHtmlAttribute(EHtmlAttr.dir);
//	}

	@Override
	public BaseAttrsHtmlElement setHtmlDir(final String dir) {
		super.setHtmlDir(dir);
		return this ;
	}
//
//	//
//	// HTML 5.0 API - Attributes
//	//
//	
//	// contentEditable
//	public boolean getHtmlContentEditable() {
//		return hasHtmlAttribute(EHtmlAttr.contentEditable);
//	}
//	public BaseAttrsHtmlElement setHtmlContentEditable(final String editable) {
//		setHtmlAttribute(EHtmlAttr.contentEditable, editable);
//		return this ;
//	}
//	public BaseAttrsHtmlElement setHtmlContentEditable(final boolean editable) {
//		setHtmlAttribute(EHtmlAttr.contentEditable, editable);
//		return this ;
//	}
//	
//	// contextMenu
//	public String getHtmlContextMenu() {
//		return getHtmlAttribute(EHtmlAttr.contextMenu);
//	}
//	public BaseAttrsHtmlElement setHtmlContextMenu(final String contextMenu) {
//		setHtmlAttribute(EHtmlAttr.contextMenu, contextMenu);
//		return this ;
//	}
//	
//	// draggable
//	public String getHtmlDraggable() {
//		return getHtmlAttribute(EHtmlAttr.draggable);
//	}
//	public BaseAttrsHtmlElement setHtmlDraggable(final String draggable) {  // true, false, auto
//		setHtmlAttribute(EHtmlAttr.draggable, draggable);
//		return this ;
//	}
//	public BaseAttrsHtmlElement setHtmlDraggable(final boolean draggable) {  // true, false
//		setHtmlAttribute(EHtmlAttr.draggable, draggable);
//		return this ;
//	}
//	
//	// irrelevant
//	public boolean getHtmlIrrelevant() {
//		return hasHtmlAttribute(EHtmlAttr.irrelevant);
//	}
//	public BaseAttrsHtmlElement setHtmlIrrelevant(final String irrelevant) {
//		setHtmlAttribute(EHtmlAttr.irrelevant, irrelevant);
//		return this ;
//	}
//	public BaseAttrsHtmlElement setHtmlIrrelevant(final boolean irrelevant) {
//		setHtmlAttribute(EHtmlAttr.irrelevant, irrelevant);
//		return this ;
//	}
//	
//	// contextMenu
//	public String getHtmlRef() {
//		return getHtmlAttribute(EHtmlAttr.ref);
//	}
//	public BaseAttrsHtmlElement setHtmlRef(final String ref) {
//		setHtmlAttribute(EHtmlAttr.ref, ref);
//		return this ;
//	}
//	
//	// registrationMark
//	public String getHtmlRegistrationMark() {
//		return getHtmlAttribute(EHtmlAttr.registrationMark);
//	}
//	public BaseAttrsHtmlElement setHtmlRegistrationMark(final String registrationMark) {
//		setHtmlAttribute(EHtmlAttr.registrationMark, registrationMark);
//		return this ;
//	}
//	
//	// template
//	public String getHtmlTemplate() {
//		return getHtmlAttribute(EHtmlAttr.template);
//	}
//	public BaseAttrsHtmlElement setHtmlTemplate(final String template) {
//		setHtmlAttribute(EHtmlAttr.template, template);
//		return this ;
//	}
//	
//	//
//	// HTML 5.0 API - Events
//	//
//	// onabort
//	public String getHtmlOnAbort() {
//		return getHtmlAttribute(EHtmlAttr.onabort);		
//	}
//	public BaseAttrsHtmlElement setHtmlOnAbort(final String script) {
//		setInlineEvent(EHtmlAttr.onabort,script,EventType.ONABORT);
//		return this ;
//	}
//	
//	// onbeforeonload
//	public String getHtmlOnBeforeOnload() {
//		return getHtmlAttribute(EHtmlAttr.onbeforeonload);		
//	}
//	public BaseAttrsHtmlElement setHtmlOnBeforeOnload(final String script) {
//		setInlineEvent(EHtmlAttr.onbeforeonload,script,EventType.ONBEFOREONLOAD);
//		return this ;
//	}
//	
//	// onbeforeunload
//	public String getHtmlOnBeforeUnload() {
//		return getHtmlAttribute(EHtmlAttr.onbeforeunload);		
//	}
//	public BaseAttrsHtmlElement setHtmlOnBeforeUnload(final String script) {
//		setInlineEvent(EHtmlAttr.onbeforeunload,script,EventType.ONBEFOREUNLOAD);
//		return this ;
//	}
//	
//	// oncontextmenu
//	public String getHtmlOnContextMenu() {
//		return getHtmlAttribute(EHtmlAttr.oncontextmenu);		
//	}
//	public BaseAttrsHtmlElement setHtmlOnContextMenu(final String script) {
//		setInlineEvent(EHtmlAttr.oncontextmenu,script,EventType.ONCONTEXTMENU);
//		return this ;
//	}
//	
//	// ondrag
//	public String getHtmlOnDrag() {
//		return getHtmlAttribute(EHtmlAttr.ondrag);		
//	}
//	public BaseAttrsHtmlElement setHtmlOnDrag(final String script) {
//		setInlineEvent(EHtmlAttr.ondrag,script,EventType.ONDRAG);
//		return this ;
//	}
//	
//	// ondragend
//	public String getHtmlOnDragEnd() {
//		return getHtmlAttribute(EHtmlAttr.ondragend);		
//	}
//	public BaseAttrsHtmlElement setHtmlOnDragEnd(final String script) {
//		setInlineEvent(EHtmlAttr.ondragend,script,EventType.ONDRAGEND);
//		return this ;
//	}
//	
//	// ondragenter
//	public String getHtmlOnDragEnter() {
//		return getHtmlAttribute(EHtmlAttr.ondragenter);		
//	}
//	public BaseAttrsHtmlElement setHtmlOnDragEnter(final String script) {
//		setInlineEvent(EHtmlAttr.ondragenter,script,EventType.ONDRAGENTER);
//		return this ;
//	}
//	
//	// ondragleave
//	public String getHtmlOnDragLeave() {
//		return getHtmlAttribute(EHtmlAttr.ondragleave);		
//	}
//	public BaseAttrsHtmlElement setHtmlOnDragLeave(final String script) {
//		setInlineEvent(EHtmlAttr.ondragleave,script,EventType.ONDRAGLEAVE);
//		return this ;
//	}
//	
//	// ondragover
//	public String getHtmlOnDragOver() {
//		return getHtmlAttribute(EHtmlAttr.ondragover);		
//	}
//	public BaseAttrsHtmlElement setHtmlOnDragOver(final String script) {
//		setInlineEvent(EHtmlAttr.ondragover,script,EventType.ONDRAGOVER);
//		return this ;
//	}
//	
//	// ondragstart
//	public String getHtmlOnDragStart() {
//		return getHtmlAttribute(EHtmlAttr.ondragstart);		
//	}
//	public BaseAttrsHtmlElement setHtmlOnDragStart(final String script) {
//		setInlineEvent(EHtmlAttr.ondragstart,script,EventType.ONDRAGSTART);
//		return this ;
//	}
//	
//	// ondrag
//	public String getHtmlOnDragDrop() {
//		return getHtmlAttribute(EHtmlAttr.ondragdrop);		
//	}
//	public BaseAttrsHtmlElement setHtmlOnDragDrop(final String script) {
//		setInlineEvent(EHtmlAttr.ondragdrop,script,EventType.ONDRAGDROP);
//		return this ;
//	}
//	
//	// onerror
//	public String getHtmlOnError() {
//		return getHtmlAttribute(EHtmlAttr.onerror);		
//	}
//	public BaseAttrsHtmlElement setHtmlOnError(final String script) {
//		setInlineEvent(EHtmlAttr.onerror,script,EventType.ONERROR);
//		return this ;
//	}
//	
//	// onmessage
//	public String getHtmlOnMessage() {
//		return getHtmlAttribute(EHtmlAttr.onmessage);		
//	}
//	public BaseAttrsHtmlElement setHtmlOnMessage(final String script) {
//		setInlineEvent(EHtmlAttr.onmessage,script,EventType.ONMESSAGE);
//		return this ;
//	}
//	
//	// onmousewheel
//	public String getHtmlOnMouseWheel() {
//		return getHtmlAttribute(EHtmlAttr.onmousewheel);		
//	}
//	public BaseAttrsHtmlElement setHtmlOnMouseWheel(final String script) {
//		setInlineEvent(EHtmlAttr.onmousewheel,script,EventType.ONMOUSEWHEEL);
//		return this ;
//	}
//	
//	// onresize
//	public String getHtmlOnResize() {
//		return getHtmlAttribute(EHtmlAttr.onresize);		
//	}
//	public BaseAttrsHtmlElement setHtmlOnResize(final String script) {
//		setInlineEvent(EHtmlAttr.onresize,script,EventType.ONRESIZE);
//		return this ;
//	}
//	
//	// onscroll
//	public String getHtmlOnScroll() {
//		return getHtmlAttribute(EHtmlAttr.onscroll);		
//	}
//	public BaseAttrsHtmlElement setHtmlOnScroll(final String script) {
//		setInlineEvent(EHtmlAttr.onscroll,script,EventType.ONSCROLL);
//		return this ;
//	}
//	
//	// onunload
//	public String getHtmlOnUnload() {
//		return getHtmlAttribute(EHtmlAttr.onunload);		
//	}
//	public BaseAttrsHtmlElement setHtmlOnUnload(final String script) {
//		setInlineEvent(EHtmlAttr.onunload,script,EventType.ONUNLOAD);
//		return this ;
//	}
//	
//	//
//	// Event Handler Content Attributes
//	//
//	
////	public String getHtmlOnChange() {
////		return getDomAttribute("onchange");
////	}
////	public void setHtmlOnChange(String s) {
////		setAttribute("onchange", s);
//////		return this;
////	}
//
//	public String getHtmlOnClick() {
//		return getHtmlAttribute(EHtmlAttr.onclick);
//	}
//
	@Override
	public BaseAttrsHtmlElement setHtmlOnClick(final String script) {
		super.setInlineEvent(EHtmlAttr.onclick,script,EventType.CLICK);
		return this ;
	}
//
//	public String getHtmlOnDoubleClick() {
//		return getHtmlAttribute(EHtmlAttr.ondblclick);
//	}
	/**
	 * @deprecated - use BaseHtmlElement.setHtmlOnDblClick()
	 * @see setHtmlDblClick()
	 */
	@Override
	public BaseAttrsHtmlElement setHtmlOnDoubleClick(final String script) {
		super.setInlineEvent(EHtmlAttr.ondblclick,script,EventType.DBLCLICK);
		return this ;
	}
//
//	public String getHtmlOnMouseDown(){
//		return getHtmlAttribute(EHtmlAttr.onmousedown);		
//	}
//	
	@Override
	public BaseAttrsHtmlElement setHtmlOnMouseDown(final String script){
		super.setInlineEvent(EHtmlAttr.onmousedown,script,EventType.MOUSEDOWN);
		return this ;
	}
//	
//	public String getHtmlOnMouseUp() {
//		return getHtmlAttribute(EHtmlAttr.onmouseup);		
//	}
//	
	@Override
	public BaseAttrsHtmlElement setHtmlOnMouseUp(final String script) {
		super.setInlineEvent(EHtmlAttr.onmouseup,script,EventType.MOUSEUP);
		return this ;
	}
//	
//	public String getHtmlOnMouseOver() {
//		return getHtmlAttribute(EHtmlAttr.onmouseover);		
//	}

	@Override
	public BaseAttrsHtmlElement setHtmlOnMouseOver(final String script) {
		super.setInlineEvent(EHtmlAttr.onmouseover,script,EventType.MOUSEOVER);
		return this ;
	}
//
//	public String getHtmlOnMouseMove() {
//		return getHtmlAttribute(EHtmlAttr.onmousemove);
//	}
//	
	@Override
	public BaseAttrsHtmlElement setHtmlOnMouseMove(final String script) {
		super.setInlineEvent(EHtmlAttr.onmousemove,script,EventType.MOUSEMOVE);
		return this ;
	}
//
//	public String getHtmlOnMouseOut() {
//		return getHtmlAttribute(EHtmlAttr.onmouseout);
//	}

	@Override
	public BaseAttrsHtmlElement setHtmlOnMouseOut(final String script) {
		super.setInlineEvent(EHtmlAttr.onmouseout,script,EventType.MOUSEOUT);
		return this ;
	}
//
//	public String getHtmlOnKeyPress() {
//		return getHtmlAttribute(EHtmlAttr.onkeypress);
//	}
//	
	@Override
	public BaseAttrsHtmlElement setHtmlOnKeyPress(final String script) {
		super.setInlineEvent(EHtmlAttr.onkeypress,script,EventType.KEYPRESS);
		return this ;
	}
//
//	public String getHtmlOnKeyDown() {
//		return getHtmlAttribute(EHtmlAttr.onkeydown);
//	}
	@Override
	public BaseAttrsHtmlElement setHtmlOnKeyDown(final String script) {
		super.setInlineEvent(EHtmlAttr.onkeydown,script,EventType.KEYDOWN);
		return this ;
	}
//
//	public String getHtmlOnKeyUp() {
//		return getHtmlAttribute(EHtmlAttr.onkeyup);
//	}
	
	@Override
    public BaseAttrsHtmlElement setHtmlOnKeyUp(String script) {
		setInlineEvent(EHtmlAttr.onkeyup,script,EventType.KEYUP);
    	return this ;
	}
//	
//	@Override
//	public String toString() {
//		return super.toString() +
//		Z.fmt(EHtmlAttr.dir.getAttributeName(), getHtmlDir()) +
//		Z.fmt(EHtmlAttr.lang.getAttributeName(), getHtmlLang());
//	}
//
//	void setInlineEvent(EHtmlAttr attr, String script, EventType eventType){
//		if(DapCtx.ctx().isActiveMode()){
//			DapCtx.ctx().getInlineEventHandlerContainer().add(this, eventType, script);
//		}else{
//			setHtmlAttribute(attr, script);
//		}
//	}

}

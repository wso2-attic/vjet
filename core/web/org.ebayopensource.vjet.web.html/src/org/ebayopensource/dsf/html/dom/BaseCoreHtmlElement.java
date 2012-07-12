/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom;

import org.ebayopensource.dsf.css.CssClassConstant;
import org.ebayopensource.dsf.css.CssIdConstant;
import org.ebayopensource.dsf.css.dom.ICssStyleDeclaration;

//import org.ebayopensource.dsf.common.DsfVerifierConfig;
//import org.ebayopensource.dsf.common.statistics.DarwinStatisticsCtxHelper;
//import org.ebayopensource.dsf.css.CssClassConstant;
//import org.ebayopensource.dsf.css.CssIdConstant;
//import org.ebayopensource.dsf.css.dom.ICssStyleDeclaration;
//import org.ebayopensource.dsf.css.dom.impl.DCssStyleDeclaration;
//import org.ebayopensource.dsf.common.Z;

/** 
 * @Deprecated - Use BaseHtmlElement instead.
 * This is an abstract interface that implments an HTML elment that has
 * core attributes as defined by the w3c spec:
 * <code>
 * http://www.w3.org/TR/html401/sgml/dtd.html#coreattrs
 * </code>.
 *
 */
@Deprecated
public abstract class BaseCoreHtmlElement extends BaseHtmlElement {
	BaseCoreHtmlElement(final DHtmlDocument doc,final HtmlTypeEnum type){
		super(doc, type);
	}
//	public String getHtmlId() {
//		return getHtmlAttribute(EHtmlAttr.id);
//	}
//
	/**
	 * Use BaseHtmlElement.setHtmlId
	 */
	@Override
	public BaseCoreHtmlElement setHtmlId(String id) {
		super.setHtmlId(id);
		return this ;
	}
	
	/**
	 * Use BaseHtmlElement.setHtmlId
	 */
	@Override
	public BaseCoreHtmlElement setHtmlId(CssIdConstant id) {
		super.setHtmlId(id) ;
		return this ;
	}
//
//	public String getHtmlTitle() {
//		return getHtmlAttribute(EHtmlAttr.title);
//	}
//
	@Override
	public BaseCoreHtmlElement setHtmlTitle(final String title) {
		super.setHtmlTitle(title);
		return this ;
	}
//
//	/*
//	 * returns class or many classes, space delimeted
//	 */
//	public String getHtmlClassName() {
//		return getHtmlAttribute(EHtmlAttr._class);
//	}
//	
	/*
	 * set class name, overwrite current class(es)
	 */
	@Override
	public BaseCoreHtmlElement setHtmlClassName(final String className) {
		super.setHtmlClassName(className);
		return this ;
	}
	
	@Override
	public BaseCoreHtmlElement setHtmlClassName(final CssClassConstant ccc) {
		return setHtmlClassName(ccc.getName()) ;
	}

	/**
	 * Adds a class to the end, does not overwrite, and the classes are space
	 * delimited.
	 */
	@Override
	public BaseCoreHtmlElement addHtmlClassName(final String className) {
		super.addHtmlClassName(className);
		return this ;
	}
	
	@Override
	public BaseCoreHtmlElement addHtmlClassName(final CssClassConstant ccc) {
		super.addHtmlClassName(ccc);
		return this ;
	}
	
//	/** get the inline style.
//	 * This is not a cheap operation, it creates several objects.
//	 * TODO: revist string/object usage.
//	 * @return DCssStyleDeclarationImpl - if not set, null will be returned. 
//	 */
//	public ICssStyleDeclaration getHtmlStyle() {
//		final String styleString = getHtmlAttribute(EHtmlAttr.style);
//		if (styleString == null || "".equals(styleString)) {
//			return null;
//		}
//		final ICssStyleDeclaration style = new DCssStyleDeclaration(null);
//		style.setCssText("{" + styleString + "}");
//		return style;
//	}
//
//	/** get the inline style.
//	 * This is not a cheap operation, it creates several objects.
//	 * TODO: revist string/object usage.
//	 * @return String - if not set, null will be returned. 
//	 */
//	public String getHtmlStyleAsString() {
//		return getHtmlAttribute(EHtmlAttr.style);
//	}
//	/** Set the style.
//	 * This will create several objects during the call.
//	 * @param style
//	 * TODO: revist string/object usage.
//	 * @return
//	 */
	@Override
	public BaseCoreHtmlElement setHtmlStyleAsString(final String styleString) {
		super.setHtmlStyleAsString(styleString);
		return this;
	}
	/** Set the style.
	 * This will make a copy of the contents, so further changes to the
	 * style object will not be reflected.
	 * @param style
	 * TODO: revist string/object usage.
	 * @return
	 */
	@Override
	public BaseCoreHtmlElement setHtmlStyle(final ICssStyleDeclaration style) {
		super.setHtmlStyle(style);
		return this;
	}
//	private final void setStyleInternal(final String style) {
//		setHtmlAttribute(EHtmlAttr.style, style);
//	}
//
//
////	/**
////	 * Inline script routines; allow component to designate inline and
////	 * external scripting includes to be built out with the element.
////	 * 
////	 * @param script The script to associate with this HTML element
////	 */
////	@Deprecated
////	private List<DScript> m_inlineScripts;
////	@Deprecated
////	public void addInlineScript(final DScript script) {
////		if (m_inlineScripts == null) {
////			m_inlineScripts = new ArrayList<DScript>(3);
////		}
////		if (!m_inlineScripts.contains(script)) {
////			m_inlineScripts.add(script);
////		}
////	}
////	@Deprecated
////	public Iterator getInlineScripts() {
////		if (m_inlineScripts == null) {
////			return (new ArrayList()).iterator();
////		}
////		return m_inlineScripts.iterator();
////	}
//	@Override
//	public String toString() {
//		return super.toString() +
//		Z.fmt(EHtmlAttr._class.getAttributeName(), getHtmlClassName()) + 
//		Z.fmt(EHtmlAttr.id.getAttributeName(), getHtmlId()) +
//		Z.fmt(EHtmlAttr.style.getAttributeName(), getHtmlStyleAsString()) +		
//		Z.fmt(EHtmlAttr.title.getAttributeName(), getHtmlTitle()) ;		
//	}
}

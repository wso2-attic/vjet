/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import java.io.StringWriter;
import java.text.MessageFormat;

import org.w3c.dom.Node;

import org.ebayopensource.dsf.active.event.IDomChangeListener;
import org.ebayopensource.dsf.css.dom.ICssStyleDeclaration;
import org.ebayopensource.dsf.css.dom.ICssValue;
import org.ebayopensource.dsf.dap.util.DapDomHelper;
import org.ebayopensource.dsf.html.HtmlWriterHelper;
import org.ebayopensource.dsf.html.dom.BaseAttrsHtmlElement;
import org.ebayopensource.dsf.html.dom.BaseCoreHtmlElement;
import org.ebayopensource.dsf.html.dom.BaseHtmlElement;
import org.ebayopensource.dsf.html.dom.DBody;
import org.ebayopensource.dsf.html.dom.DHead;
import org.ebayopensource.dsf.html.dom.DHtml;
import org.ebayopensource.dsf.html.dom.DOption;
import org.ebayopensource.dsf.html.dom.DSelect;
import org.ebayopensource.dsf.html.dom.ECssAttr;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.liveconnect.IDLCDispatcher;
import org.ebayopensource.dsf.common.xml.IIndenter;

public final class DapDomChangeListener implements IDomChangeListener {
	private static final String EMPTY = "";
	private static final String SINGLE_QUOTE = "'";
	private static final String DOUBLE_QUOTE = "\"";
		
	private static final String SET_VALUE = "{0}.value=\"{1}\";";

	private static final String UPDATE_ELEMENT = "DLC_updateElement({0},{1});";
	private static final String APPEND_CHILD = "DLC_appendHtml({0},{1},{2});";
	private static final String SET_ATTRIBUTE = "{0}.{1}={2};";
	private static final String SET_STYLE= "{0}.style.{1}={2};";
	private static final String INSERT_NEXT = "DLC_insertHtml({0},{1},{2});";
	private static final String REMOVE_ELEMENT = "DLC_removeElement({0});";
	private static final String NEW_OPTION = "{0}.options[{1}]=new Option(\"{2}\",\"{3}\");";
	
//	private static final String SET_ATTRIBUTE = "DLC_setAttribute({0},{1});";
	private static final String SET_WIDTH = "DLC_setWidth({0},\"{1}\");";
	private static final String SET_HEIGHT = "DLC_setHeight({0},\"{1}\");";
	private static final String SET_CLASS_NAME = "DLC_setClassName({0},\"{1}\");";
	private static final String SET_CLASS_NAME_BY_ID = "DLC_setClassName(\"{0}\",\"{1}\");";
	
	private final IDLCDispatcher m_dlcDispatcher;
	
	public DapDomChangeListener(final IDLCDispatcher dlcDispatcher){
		m_dlcDispatcher = dlcDispatcher;
	}
	
	public void onValueChange(final BaseHtmlElement elem, final String value){
		final String path = DapDomHelper.getPath(elem);
		if (path == null) {
			return;
		}
		m_dlcDispatcher.send(MessageFormat.format(SET_VALUE, path, value != null ? value : EMPTY));
	}
	
	public void onAttrChange(final BaseHtmlElement elem, final EHtmlAttr attr, final boolean value){
		onAttrChange(elem, attr, String.valueOf(value), true);
	}
	
	public void onAttrChange(final BaseHtmlElement elem, final EHtmlAttr attr, final int value){
		onAttrChange(elem, attr, String.valueOf(value), true);
	}
	
	public void onAttrChange(final BaseHtmlElement elem, final EHtmlAttr attr, final double value){
		onAttrChange(elem, attr, String.valueOf(value), true);
	}

	public void onAttrChange(final BaseHtmlElement elem, final EHtmlAttr attr, final String value){
		onAttrChange(elem, attr, value, false);
	}
	
	private void onAttrChange
		(final BaseHtmlElement elem,
		 final EHtmlAttr attr,
		 final String value,
		 final boolean isBoolean) {
		
		String strValue = value != null ? value : EMPTY;
		if (!isBoolean) {
			strValue = "\"" + value + "\"";
		}
		String path = null;
		if(attr.equals(EHtmlAttr.id)){
			 path = DapDomHelper.getPath(elem,false);
		}else{
			path = DapDomHelper.getPath(elem);
		}
		if (path == null) {
			return;
		}
		m_dlcDispatcher.send(MessageFormat.format(SET_ATTRIBUTE, path, attr.name(), strValue));
	}
	
	public void onStyleChange(final BaseHtmlElement elem, final String name, final String value){
		final String path = DapDomHelper.getPath(elem);
		if (path == null) {
			return;
		}
		m_dlcDispatcher.send(MessageFormat.format(SET_STYLE, path, name, value));
	}
	
	
	public void onElementChange(final BaseHtmlElement elem){
		final String path = DapDomHelper.getPath(elem);
		if (path == null) {
			return;
		}
		final String html = getHtml(elem);
		m_dlcDispatcher.send(MessageFormat.format(UPDATE_ELEMENT, path, html));
	}
	
	public void onAppendChild(final Node child) {
		final BaseHtmlElement parent = (BaseHtmlElement)child.getParentNode();
		if (parent instanceof DHtml&&
			!(child instanceof DHead || child instanceof DBody)) {
			return;
		}
		final String parentRef = DapDomHelper.getPath(parent);
		if (parentRef == null) {
			return;
		}
		if (child instanceof DOption) {
			DOption op = (DOption)child;
			m_dlcDispatcher.send(MessageFormat.format(NEW_OPTION, parentRef, getIndex(op), op.getHtmlText(), op.getHtmlValue()));
			return;
		}
		final String html = getHtml(child);
		m_dlcDispatcher.send(MessageFormat.format(APPEND_CHILD, parentRef, html, DapDomHelper.getIndex(parent, child)));
		syncPaddingInfo(child);
	}
		
	public void onInsert(final Node newChild, final Node nodeRef, final boolean insertBefore){
		if (nodeRef.getParentNode() instanceof DHtml &&
			!(newChild instanceof DHead || newChild instanceof DBody)) {
			return;
		}
		final String siblingRef = DapDomHelper.getPath(nodeRef);
		if (siblingRef == null) {
			return;
		}
		final String html = getHtml(newChild);
		m_dlcDispatcher.send(MessageFormat.format(INSERT_NEXT, siblingRef, html, insertBefore));
	}
	
	public void onRemove(final Node node){
		if (node.getParentNode() instanceof DHtml && 
			!(node instanceof DHead || node instanceof DBody)) {
			return;
		}
		final String nodeRef = DapDomHelper.getPath(node);
		if (nodeRef == null) {
			return;
		}
		m_dlcDispatcher.send(MessageFormat.format(REMOVE_ELEMENT, nodeRef));
	}
	
	public void onWidthChange(BaseHtmlElement node, int width){
		final String path = DapDomHelper.getPath(node);
		m_dlcDispatcher.send(path == null ? null : MessageFormat.format(SET_WIDTH, path, width));
	}
	
	public void onHeightChange(BaseHtmlElement node, int height){
		final String path = DapDomHelper.getPath(node);
		m_dlcDispatcher.send(path == null ? null : MessageFormat.format(SET_HEIGHT, path, height));
	}
	
	public void onClassNameChange(BaseHtmlElement elem, String className){
		if (elem instanceof BaseAttrsHtmlElement){
			String id = ((BaseAttrsHtmlElement)elem).getHtmlId();
			if (id != null){
				m_dlcDispatcher.send(MessageFormat.format(SET_CLASS_NAME_BY_ID, id, className));
				return;
			}
		}
		
		final String path = DapDomHelper.getPath(elem);
		m_dlcDispatcher.send(path == null ? null : MessageFormat.format(SET_CLASS_NAME, path, className));
	}

	public String getAttributeValue(BaseHtmlElement elem, EHtmlAttr attr) {
		return m_dlcDispatcher.request(DapDomHelper.getPath(elem)+"."+attr.getAttributeName(),0);
	}

	//
	// Private
	//
	private String getHtml(Node node){
		StringWriter writer = new StringWriter(1000);
		HtmlWriterHelper.write(node, writer, IIndenter.COMPACT);
		String html = writer.toString();
		int singleIdx = html.indexOf("'");
		int doubleIdx = html.indexOf("\"");
		
		if (doubleIdx > singleIdx){
			html = SINGLE_QUOTE + html.replaceAll("'","\'") + SINGLE_QUOTE;
		}
		else {
			html = DOUBLE_QUOTE + html.replaceAll("\"","\'") + DOUBLE_QUOTE;
		}
		
		return html;
	}
	
	private static int getIndex(DOption op) {
		DSelect select = null;
		Node parent = op.getParentNode();
		while (parent != null) {
			if (parent instanceof DSelect) {
				select = (DSelect)parent;
				break;
			}
		}
		if (select != null) {
			for (int i = 0; i < select.getLength(); i++) {
				if (select.item(i) == op) {
					return i;
				}
			}			
		}
		
		return 0;
	}
	
	private void syncPaddingInfo(final Node child){
		//DLC_appendHTML(document.body,<div style='paddingLeft:1px;'></div>")
		//When we add  <div style='paddingLeft:1px;'></div> via (DLCClient.js) DLC_appendHTML, 
		//div is loosing all padding related data.
		//To fix this, explicitly we are applying padding data,
		//Bug# http://quickbugstage.arch.ebay.com/show_bug.cgi?id=3585
		final String ref = DapDomHelper.getPath(child);
		if (ref == null) {
			return;
		}
		if(child instanceof BaseCoreHtmlElement){
			ICssStyleDeclaration style = ((BaseCoreHtmlElement)child).getHtmlStyle();
			if(style!=null){
				syncStyleProp(ref,ECssAttr.padding.toString(),style.getPropertyCSSValue(ECssAttr.padding.toString()));
				syncStyleProp(ref,ECssAttr.paddingLeft.toString(),style.getPropertyCSSValue(ECssAttr.paddingLeft.toString()));
				syncStyleProp(ref,ECssAttr.paddingRight.toString(),style.getPropertyCSSValue(ECssAttr.paddingRight.toString()));
				syncStyleProp(ref,ECssAttr.paddingTop.toString(),style.getPropertyCSSValue(ECssAttr.paddingTop.toString()));
				syncStyleProp(ref,ECssAttr.paddingBottom.toString(),style.getPropertyCSSValue(ECssAttr.paddingBottom.toString()));
			}
		}
	}

	private void syncStyleProp(final String ref, String name, ICssValue  val){
		if(!"".equals(ref) && !"".equals(name) && val !=null){
			StringBuilder code = new StringBuilder(ref);
			code.append(".style.");
			code.append(name);
			code.append("=\"");
			code.append(val);
			code.append("\"");
			m_dlcDispatcher.send(code.toString());
		}
	}

}

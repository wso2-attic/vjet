/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;


import org.w3c.dom.DOMException;

import org.ebayopensource.dsf.active.event.IBrowserBinding;
import org.ebayopensource.dsf.html.dom.DSelect;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;
import org.ebayopensource.dsf.html.dom.IDFormControl;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.jsnative.HtmlCollection;
import org.ebayopensource.dsf.jsnative.HtmlElement;
import org.ebayopensource.dsf.jsnative.HtmlForm;
import org.ebayopensource.dsf.jsnative.HtmlOptGroup;
import org.ebayopensource.dsf.jsnative.HtmlOption;
import org.ebayopensource.dsf.jsnative.HtmlOptionsCollection;
import org.ebayopensource.dsf.jsnative.HtmlSelect;
import org.ebayopensource.dsf.jsnative.Node;
import org.ebayopensource.dsf.jsnative.NodeList;
import org.ebayopensource.dsf.jsnative.Option;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.mozilla.mod.javascript.Scriptable;

public class AHtmlSelect extends AHtmlElement implements HtmlSelect, IDFormControl {

	private static final long serialVersionUID = 1L;
	private static final String FOCUS_JS_METHOD = "focus()";
	private static final String SELECT_ONE = "select-one";
	private static final String SELECT_MULTIPLE = "select-multiple";
	
	private AHtmlOptionsCollection m_options;
	
	protected AHtmlSelect(AHtmlDocument doc,DSelect node) {
		super(doc, node);
		populateScriptable(AHtmlSelect.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}
	
    public ANode cloneNode( boolean deep ) {
    	AHtmlSelect clonedNode = (AHtmlSelect)super.cloneNode( deep );
        clonedNode.m_options = null;
        return clonedNode;
    }

	public void blur() {
		dispatchEvent(EventType.BLUR.getName(), this);

	}

	public void focus() {
		AHtmlDocument doc = getOwnerDocument();
		if(doc!=null){
			IBrowserBinding browserBinding = doc.getBrowserBinding();
			if(browserBinding!=null){
				browserBinding.executeDomMethod(getDSelect(), FOCUS_JS_METHOD);
			}
		}
	}

	public boolean getDisabled() {
		return AHtmlHelper.booleanValueOf(EHtmlAttr.disabled,getHtmlAttribute(EHtmlAttr.disabled));
	}

	public HtmlForm getForm() {
		return super.getFormInternal();
	}

	public int getLength() {
		return getDSelect().getHtmlLength();
	}

	public boolean getMultiple() {
		return AHtmlHelper.booleanValueOf(EHtmlAttr.multiple,getHtmlAttribute(EHtmlAttr.multiple));
	}

	public String getName() {
		return getDSelect().getHtmlName();
	}

	public HtmlOptionsCollection getOptions() {
		if (m_options == null) {
			m_options = new AHtmlOptionsCollection(this, AHtmlCollection.OPTION);
		}
		return m_options;
	}

	public int getSelectedIndex() {
		if(!getDSelect().getHtmlMultiple()){
			//settingup default values
			int idx = getDSelect().getHtmlSelectedIndex();
			if(idx==-1 && getSize()!=-1){
				idx = 0;
				Node opt = getOptions().item(idx);
				if(opt != null){
					if (opt instanceof HtmlOption) {
						((HtmlOption)opt).setSelected(true);
					} else if (opt instanceof Option) {
						((Option)opt).setSelected(true);
					}
				}
			}
			return idx;
		}
		return 0;
	}

	public int getSize() {
		return getDSelect().getHtmlSize();
	}

	public int getTabIndex() {
		return getDSelect().getHtmlTabIndex();
	}

	public String getType() {
		String type = getDSelect().getHtmlType();
		if(type==""||type==null){
			type = getMultiple()?SELECT_MULTIPLE:SELECT_ONE;
		}
		return type;
	}

	public String getValue() {
		if(!getDSelect().getHtmlMultiple()){
			return getDSelect().getHtmlValue();
		}
		return "";
	}

	public void remove(Object option) {
		if (option instanceof HtmlOption) {
			removeOption((HtmlOption)option);
		} else if (option instanceof Option) {
			removeOption((Option)option);
		} else if (option instanceof Double) {
			int index = ((Double)option).intValue();
			removeByIndex(index);
		} else if (option instanceof Integer) {
			int index = ((Integer)option).intValue();
			removeByIndex(index);
		} 
	}
	
	public void removeByIndex(int index) {
		final NodeList options = getElementsByTagName(HtmlTypeEnum.OPTION.getName());
		final Node removed = options.item(index);
		if (removed != null) {
			removeChild(removed);
		}
	}
	
	public void removeOption(HtmlOption option) {
		removeChild((Node) option);
	}
	
	public void removeOption(Option option) {
		removeChild((Node) option);
	}

	public void setDisabled(boolean disabled) {
		setHtmlAttribute(EHtmlAttr.disabled, disabled);
		onAttrChange(EHtmlAttr.disabled, disabled);
	}

	public void setMultiple(boolean multiple) {
		setHtmlAttribute(EHtmlAttr.multiple, multiple);
		onAttrChange(EHtmlAttr.multiple, multiple);
	}

	public void setName(String name) {
		getDSelect().setHtmlName(name);
		onAttrChange(EHtmlAttr.name, name);
	}

	public void setSelectedIndex(int selectedIndex) {
		if(!getDSelect().getHtmlMultiple()){
			getDSelect().setHtmlSelectedIndex(selectedIndex);
		}
		// TODO: EHtmlAttr.selectedindex not defined
		//onAttrChange(EHtmlAttr.selectedIndex,  String.valueOf(selectedIndex));
	}

	public void setSize(int size) {
		getDSelect().setHtmlSize(size);
		onAttrChange(EHtmlAttr.size, String.valueOf(size));
	}

	public void setTabIndex(int tabIndex) {
		getDSelect().setHtmlTabIndex(tabIndex);
		onAttrChange(EHtmlAttr.tabindex, String.valueOf(tabIndex));
	}

	public void setValue(String value) {
		if(!getDSelect().getHtmlMultiple()){
			getDSelect().setHtmlValue(value);
			onValueChange(value);
		}
	}

	public void add(HtmlElement element, HtmlElement before) {
		if (element instanceof HtmlOption || element instanceof Option || 
				element instanceof HtmlOptGroup) {
			if (before == null) {
				appendChild(element);
			} else {
				if (this != before.getParentNode()) {
					throw new ADOMException(new DOMException(
							DOMException.NOT_FOUND_ERR, 
							"before is not a descendant of the SELECT element"));
				}
				insertBefore(element, before);
			}
		}
	}
	
	@Override
	public Object get(int index, Scriptable start) {
		Object obj = null;
		if ((obj = findOptionObject(index)) != null)
			return obj;
		obj = super.get(index, start);
		return obj;
	}

	private Object findOptionObject(int index) {
		return getOptions().item(index);
	}
	
	@Override
	public Object get(String name, Scriptable start) {
		Object obj = null;
		if ((obj = findOptionObject(name)) != null)
			return obj;
		obj = super.get(name, start);
		return obj;
	}
	

	// Since property name is 'onblur', Rhino invokes this method.
	public Object getOnblur() {
		return getOnBlur();
	}
	
	// Since property name is 'onfocus', Rhino invokes this method.
	public Object getOnfocus() {
		return getOnFocus();
	}
	
	// For Rhino
	public void setOnblur(Object functionRef) {
		setOnBlur(functionRef);
	}
	
	// For Rhino
	public void setOnfocus(Object functionRef) {
		setOnFocus(functionRef);
	}
	
	// Since property name is 'onkeydown', Rhino invokes this method.
	public Object getOnkeydown() {
		return getOnKeyDown();
	}
	
	// For Rhino
	public void setOnkeydown(Object functionRef) {
		setOnKeyDown(functionRef);
	}
	
	// Since property name is 'onkeypress', Rhino invokes this method.
	public Object getOnkeypress() {
		return getOnKeyPress();
	}
	
	// For Rhino
	public void setOnkeypress(Object functionRef) {
		setOnKeyPress(functionRef);
	}
	
	// Since property name is 'onkeyup', Rhino invokes this method.
	public Object getOnkeyup() {
		return getOnKeyUp();
	}
	
	// For Rhino
	public void setOnkeyup(Object functionRef) {
		setOnKeyUp(functionRef);
	}
	
	// Since property name is 'onresize', Rhino invokes this method.
	public Object getOnresize() {
		return getOnResize();
	}
	
	// For Rhino
	public void setOnresize(Object functionRef) {
		setOnResize(functionRef);
	}
	
	// Since property name is 'onclick', Rhino invokes this method.
	public Object getOnclick() {
		return getOnClick();
	}
	
	// For Rhino
	public void setOnclick(Object functionRef) {
		setOnClick(functionRef);
	}
	
	// Since property name is 'ondblclick', Rhino invokes this method.
	public Object getOndblclick() {
		return getOnDblClick();
	}
	
	// For Rhino
	public void setOndblclick(Object functionRef) {
		setOnDblClick(functionRef);
	}
	
	// Since property name is 'onmousedown', Rhino invokes this method.
	public Object getOnmousedown() {
		return getOnMouseDown();
	}
	
	// For Rhino
	public void setOnmousedown(Object functionRef) {
		setOnMouseDown(functionRef);
	}
	
	// Since property name is 'onmousemove', Rhino invokes this method.
	public Object getOnmousemove() {
		return getOnMouseMove();
	}
	
	// For Rhino
	public void setOnmousemove(Object functionRef) {
		setOnMouseMove(functionRef);
	}
	
	// Since property name is 'onmouseout', Rhino invokes this method.
	public Object getOnmouseout() {
		return getOnMouseOut();
	}
	
	// For Rhino
	public void setOnmouseout(Object functionRef) {
		setOnMouseOut(functionRef);
	}
	
	// Since property name is 'onmouseover', Rhino invokes this method.
	public Object getOnmouseover() {
		return getOnMouseOver();
	}
	
	// For Rhino
	public void setOnmouseover(Object functionRef) {
		setOnMouseOver(functionRef);
	}
	
	// Since property name is 'onmouseup', Rhino invokes this method.
	public Object getOnmouseup() {
		return getOnMouseUp();
	}
	
	// For Rhino
	public void setOnmouseup(Object functionRef) {
		setOnMouseUp(functionRef);
	}
	
	private Object findOptionObject(String name) {
		HtmlCollection options = getOptions();
		for (int i = 0; i < options.getLength(); i++) {
			Node area = options.item(i);
			if (area.getNodeName().equalsIgnoreCase(name)) {
				return area;
			}
		}
		return null;
	}
	
	private DSelect getDSelect() {
		return (DSelect) getDNode();
	}
	
}
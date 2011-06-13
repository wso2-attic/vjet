/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DOption;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlForm;
import org.ebayopensource.dsf.jsnative.Option;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AOption extends AHtmlElement implements Option {
	
	private static final long serialVersionUID = 1L;

	public AOption(AHtmlDocument doc, DOption option) {
		super(doc, option);
		populateScriptable(AHtmlOption.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}
	
	public boolean getDefaultSelected() {
		return AHtmlHelper.booleanValueOf(EHtmlAttr.defaultSelected,getHtmlAttribute(EHtmlAttr.defaultSelected));
	}

	public boolean getDisabled() {
		return AHtmlHelper.booleanValueOf(EHtmlAttr.disabled,getHtmlAttribute(EHtmlAttr.disabled));
	}

	public HtmlForm getForm() {
		return super.getFormInternal();
	}

	public int getIndex() {
		return getDOption().getHtmlIndex();
	}

	public String getLabel() {
		return getDOption().getHtmlLabel();
	}

	public boolean getSelected() {
		return AHtmlHelper.booleanValueOf(EHtmlAttr.selected,getHtmlAttribute(EHtmlAttr.selected));
	}

	public String getText() {
		return getDOption().getHtmlText();
	}

	public String getValue() {
		return getDOption().getHtmlValue();
	}

	public void setDefaultSelected(boolean defaultSelected) {
		setHtmlAttribute(EHtmlAttr.defaultSelected, defaultSelected);
		onAttrChange(EHtmlAttr.defaultSelected, defaultSelected);
	}

	public void setDisabled(boolean disabled) {
		getDOption().setAttribute(EHtmlAttr.disabled, String.valueOf(disabled));
		onAttrChange(EHtmlAttr.disabled, disabled);
	}

	public void setIndex(int index) {
		getDOption().setHtmlIndex(index);
		// TODO EHtmlAttr.index doesn't exists
//		onAttrChange(EHtmlAttr.index, index);
	}

	public void setLabel(String label) {
		getDOption().setHtmlLabel(label);
		onAttrChange(EHtmlAttr.label, label);
	}
	
	public void setSelected(boolean selected) {
		getDOption().setAttribute(EHtmlAttr.selected, String.valueOf(selected));
		onAttrChange(EHtmlAttr.selected, selected);
	}
	
	public void setText(String text) {
		getDOption().setHtmlText(text);
	}

	public void setValue(String value) {
		getDOption().setHtmlValue(value);
		onValueChange(value);
	}

	public void Option() {

	}

	public void Option(String text) {
		setText(text);
	}

	public void Option(String text, Object value) {
		setText(text);
		// TODO convert this correctly
		setValue((String)value);
	}

	public void Option(String text, Object value, boolean defaultSelected) {
		setText(text);
		setValue((String)value);
		setDefaultSelected(defaultSelected);
	}

	public void Option(String text, Object value, boolean defaultSelected,
			boolean selected) {
		setText(text);
		setValue((String)value);
		setDefaultSelected(defaultSelected);
		setSelected(selected);
	}
	
	private DOption getDOption() {
		return (DOption) getDNode();
	}

	@Override
	public void Option(String text, Object value, int defaultSelected,
			int selected) {
		setText(text);
		setValue((String)value);
		setDefaultSelected(defaultSelected!=0);
		setSelected(selected!=0);
	}

}

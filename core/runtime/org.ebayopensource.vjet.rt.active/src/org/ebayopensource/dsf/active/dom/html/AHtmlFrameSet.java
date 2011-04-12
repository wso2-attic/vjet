/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.html.dom.DFrameSet;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlFrameSet;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlFrameSet extends AHtmlElement implements HtmlFrameSet {

	private static final long serialVersionUID = 1L;
	
	protected AHtmlFrameSet(AHtmlDocument doc, DFrameSet head) {
		super(doc, head);
		populateScriptable(AHtmlFrameSet.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	/**
	 * Retruns the number of columns of frames in the frameset.
	 * @return The comma-separated list of pixels and/or percentages.
	 * 
	 */
	public String getCols() {
		return getDFrameSet().getHtmlCols();
	}

	/**
	 * Returns the number of rows of frames in the frameset
	 * @return The comma-separated list of pixels and/or percentages.
	 */
	public String getRows() {
		return getDFrameSet().getHtmlRows();
	}

	public void setCols(String cols) {
		getDFrameSet().setHtmlCols(cols);
		onAttrChange(EHtmlAttr.cols, cols);
	}

	public void setRows(String rows) {
		getDFrameSet().setHtmlRows(rows);
		onAttrChange(EHtmlAttr.rows, rows);
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
	
	// Since property name is 'onkeyup', Rhino invokes this method.
	public Object getOnload() {
		return getOnLoad();
	}
	
	// For Rhino
	public void setOnload(Object functionRef) {
		setOnLoad(functionRef);
	}
	
	// Since property name is 'onunload', Rhino invokes this method.
	public Object getOnunload() {
		return getOnLoad();
	}
	
	// For Rhino
	public void setOnunload(Object functionRef) {
		setOnLoad(functionRef);
	}
	
	private DFrameSet getDFrameSet() {
		return (DFrameSet) getDNode();
	}

}

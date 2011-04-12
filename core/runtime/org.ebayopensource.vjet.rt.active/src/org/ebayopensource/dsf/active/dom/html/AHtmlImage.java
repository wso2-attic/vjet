/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import org.ebayopensource.dsf.active.event.IBrowserBinding;
import org.ebayopensource.dsf.html.dom.BaseHtmlElement;
import org.ebayopensource.dsf.html.dom.DImg;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.HtmlImage;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlImage extends AHtmlElement implements HtmlImage {

	private static final long serialVersionUID = 1L;
	
	protected AHtmlImage(AHtmlDocument doc, DImg image) {
		super(doc, image);
		populateScriptable(AHtmlImage.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	public String getAlign() {
		return getDImage().getHtmlAlign();
	}

	public String getAlt() {
		return getDImage().getHtmlAlt();
	}

	public String getBorder() {
		return getDImage().getHtmlBorder();
	}

	public int getHeight() {
		try {
			return Integer.valueOf(getDImage().getHtmlHeight());	
		} catch (Exception e) {
			return 0;
		}
		
	}

	public int getHspace() {
		int retVal = 0;
		try {
			String val = getDImage().getHtmlHspace();
			retVal = Integer.valueOf(val);
		} catch (Exception e) {
			// ignore exception
		}
		return retVal;
	}

	public boolean getIsMap() {
		return AHtmlHelper.booleanValueOf(EHtmlAttr.ismap,getHtmlAttribute(EHtmlAttr.ismap));
	}

	public String getLongDesc() {
		return getDImage().getHtmlLongDesc();
	}

	public String getLowsrc() {
		return getDImage().getHtmlLowSrc();
	}

	public String getName() {
		return getDImage().getHtmlName();
	}

	public String getSrc() {
		return getDImage().getHtmlSrc();
	}

	public String getUseMap() {
		return getDImage().getHtmlUseMap();
	}

	public int getVspace() {
		int retVal = 0;
		try {
			retVal = Integer.valueOf(getDImage().getHtmlVspace());
		} catch (Exception e) {
			// ignore exception
		}
		return retVal;
	}

	public int getWidth() {
		try {
			return Integer.valueOf(getDImage().getHtmlWidth());
		} catch (NumberFormatException e) {
			// TODO: handle exception
			return 0;
		}
		
	}

	public void setAlign(String align) {
		getDImage().setHtmlAlign(align);
		onAttrChange(EHtmlAttr.align, align);
	}

	public void setAlt(String alt) {
		getDImage().setHtmlAlt(alt);
		onAttrChange(EHtmlAttr.alt, alt);
	}

	public void setBorder(String border) {
		getDImage().setHtmlBorder(border);
		onAttrChange(EHtmlAttr.border, border);
	}

	public void setHeight(int height) {
		getDImage().setHtmlHeight(height);
		onAttrChange(EHtmlAttr.height, String.valueOf(height));
	}

	public void setHspace(int hspace) {
		getDImage().setHtmlHspace(hspace);
		onAttrChange(EHtmlAttr.hspace, String.valueOf(hspace));
	}

	public void setIsMap(boolean isMap) {
		setHtmlAttribute(EHtmlAttr.ismap, isMap);
		onAttrChange(EHtmlAttr.ismap, isMap);
	}

	public void setLongDesc(String longDesc) {
		getDImage().setHtmlLongDesc(longDesc);
		onAttrChange(EHtmlAttr.longdesc, longDesc);
	}

	public void setLowsrc(String lowSrc) {
		getDImage().setHtmlLowSrc(lowSrc);
		onAttrChange(EHtmlAttr.lowsrc, lowSrc);
	}

	public void setName(String name) {
		getDImage().setHtmlName(name);
		onAttrChange(EHtmlAttr.name, name);
	}

	public void setSrc(String src) {
		getDImage().setHtmlSrc(src);
		onAttrChange(EHtmlAttr.src, src);
	}

	public void setUseMap(String useMap) {
		getDImage().setHtmlUseMap(useMap);
		onAttrChange(EHtmlAttr.usemap, useMap);
	}

	public void setVspace(int vspace) {
		getDImage().setHtmlVspace(vspace);
		onAttrChange(EHtmlAttr.vspace, String.valueOf(vspace));
	}

	public void setWidth(int width) {
		getDImage().setHtmlWidth(width);
		onAttrChange(EHtmlAttr.width, String.valueOf(width));
	}

	private DImg getDImage() {
		return (DImg) getDNode();
	}

	public int getClientHeight() {
		IBrowserBinding bb = getBrowserBinding();
		if(bb!=null){
			try{
				return Integer.valueOf(bb.getDomAttributeValue(getElement(), EHtmlAttr.clientHeight));
			}catch(Exception e) {
				//return 0 in case of exception
			}
		}
		return 0;
	}

	public int getClientWidth() {
		IBrowserBinding bb = getBrowserBinding();
		if(bb!=null){
			try{
				return Integer.valueOf(bb.getDomAttributeValue(getElement(), EHtmlAttr.clientWidth));
			}catch(Exception e) {
				//return 0 in case of exception
			}
		}
		return 0;
	}
	
	// Since property name is 'onabort', Rhino invokes this method.
	public Object getOnabort() {
		return getOnAbort();
	}
	
	// For Rhino
	public void setOnabort(Object functionRef) {
		setOnAbort(functionRef);
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
	
	private IBrowserBinding getBrowserBinding(){
		AHtmlDocument doc = getOwnerDocument();
		if(doc!=null){
			return  doc.getBrowserBinding();
		}
		return null;
	}

	private BaseHtmlElement getElement() {
		return (BaseHtmlElement) getDNode();
	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import org.ebayopensource.dsf.html.dom.DImg;

public class JSImage {
    
    private DImg image = null;
    private String complete = "true";
    
    /** Creates new JSImage */
    public JSImage() {
        image = null;
    }
    
    public JSImage(DImg image) {
        this.image = image;
    }
    
    public void setWidth(java.lang.String p1) {
        image.setHtmlWidth(p1);
    }    
     
    public void setVspace(java.lang.String p1) {
        image.setHtmlVspace(p1);
    }
    
    public void setSrc(java.lang.String p1) {
        image.setHtmlSrc(p1);
    }
    
    public void setName(java.lang.String p1) {
        image.setHtmlName(p1);
    }
    
    public void setLowsrc(java.lang.String p1) {
        image.setHtmlLowSrc(p1);
    }
    
    public void setHspace(java.lang.String p1) {
        image.setHtmlHspace(p1);
    }
    
    public void setHeight(java.lang.String p1) {
        image.setHtmlHeight(p1);
    }
    
    public void setComplete(java.lang.String p1) {
        complete = p1;
    }
    
    public void setBorder(java.lang.String p1) {
        image.setHtmlBorder(p1);
    }
    
    public java.lang.String getWidth() {
        return (image.getHtmlWidth());
    }
    
    public java.lang.String getVspace() {
        return (image.getHtmlVspace());
    }
    
    public java.lang.String getSrc() {
        return (image.getHtmlSrc());
    }
    
    public java.lang.String getName() {
        return (image.getHtmlName());
    }
    
    public java.lang.String getLowsrc() {
        return (image.getHtmlLowSrc());
    }
    
    public java.lang.String getHspace() {
        return (image.getHtmlHspace());
    }
    
    public java.lang.String getHeight() {
        return (image.getHtmlHeight());
    }
    
    public java.lang.String getComplete() {
        return (complete);
    }

    public java.lang.String getBorder() {
        return (image.getHtmlBorder());
    }
    
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.dom.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.DOMException;

import org.ebayopensource.dsf.css.sac.ISacMediaList;
import org.ebayopensource.dsf.dom.stylesheets.IMediaList;

/**
 * @see org.w3c.dom.stylesheets.MediaList
 */
public class DCssMediaList 
	implements IMediaList, Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private List<String> m_media = new ArrayList<String>();

    public DCssMediaList(ISacMediaList mediaList) {
        for (int i = 0; i < mediaList.getLength(); i++) {
            m_media.add(mediaList.item(i));
        }
    }

    public String getMediaText() {
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < m_media.size(); i++) {
            sb.append(m_media.get(i));
            if (i < m_media.size() - 1) {
                sb.append( ", " );
            }
        }
        return sb.toString();
    }

    public void setMediaText(String mediaText) throws DOMException {
    }

    public int getLength() {
        return m_media.size();
    }

    public String item(int index) {
        return (index < m_media.size()) ? m_media.get(index) : null;
    }

    public void deleteMedium(String oldMedium) throws DOMException {
        for (int i = 0; i < m_media.size(); i++) {
            String str = m_media.get(i);
            if (str.equalsIgnoreCase(oldMedium)) {
                m_media.remove(i);
                return;
            }
        }
        throw new DCssException(
            DOMException.NOT_FOUND_ERR,
            DCssException.NOT_FOUND);
    }

    public void appendMedium(String newMedium) throws DOMException {
    	//bug 173, 11-14-2007
    	for (int i = 0; i < m_media.size(); i++) {
            String str = m_media.get(i);
            if (str.equalsIgnoreCase(newMedium)) {
                m_media.remove(i);                
            }
        }
        m_media.add(newMedium);
    }

	/** 
	 * DO NOT CHANGE THIS.  Unfortunately the def code relies on toString()
	 * for values.  Will need to fix this.
	 */
	@Override
    public String toString() {
        return getMediaText();
    }
    
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}

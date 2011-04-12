/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*
 * SACMediaListImpl.java
 *
 * Modified from Open-Source CSS Parser (Steady State Software) under
 * GNU Lesser General Public License.
 * 
 */

package org.ebayopensource.dsf.css.parser;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.css.sac.ISacMediaList;

public class DSacMediaList implements ISacMediaList {

    private List<String> m_selectors = new ArrayList<String>();

    public int getLength() {
        return m_selectors.size();
    }

    public String item(int index) {
        return m_selectors.get(index);
    }

    public void add(String s) {
        m_selectors.add(s);
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        int len = getLength();
        for (int i = 0; i < len; i++) {
            sb.append(item(i));
            if (i < len - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}

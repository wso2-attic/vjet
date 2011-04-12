/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: OptionGroup.java, Jan 25, 2010, 10:38:46 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jstojava.cml.vjetv.parser;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Class/Interface description
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class OptionGroup implements Serializable {
    private static final long serialVersionUID = 1L;

    /** hold the options */
    private Map optionMap = new HashMap();

    /** the name of the selected option */
    private String selected;

    /** specified whether this group is required */
    private boolean required;

    /**
     * Add the specified <code>Option</code> to this group.
     * 
     * @param option
     *            the option to add to this group
     * @return this option group with the option added
     */
    public OptionGroup addOption(Option option) {
        // key - option name
        // value - the option
        optionMap.put(option.getKey(), option);

        return this;
    }

    /**
     * @return the names of the options in this group as a
     *         <code>Collection</code>
     */
    public Collection getNames() {
        // the key set is the collection of names
        return optionMap.keySet();
    }

    /**
     * @return the options in this group as a <code>Collection</code>
     */
    public Collection getOptions() {
        // the values are the collection of options
        return optionMap.values();
    }

    /**
     * Set the selected option of this group to <code>name</code>.
     * 
     * @param option
     *            the option that is selected
     * @throws AlreadySelectedException
     *             if an option from this group has already been selected.
     */
    public void setSelected(Option option) throws AlreadySelectedException {
        // if no option has already been selected or the
        // same option is being reselected then set the
        // selected member variable
        if (selected == null || selected.equals(option.getOpt())) {
            selected = option.getOpt();
        } else {
            throw new AlreadySelectedException(this, option);
        }
    }

    /**
     * @return the selected option name
     */
    public String getSelected() {
        return selected;
    }

    /**
     * @param required
     *            specifies if this group is required
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * Returns whether this option group is required.
     * 
     * @return whether this option group is required
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Returns the stringified version of this OptionGroup.
     * 
     * @return the stringified representation of this group
     */
    public String toString() {
        StringBuffer buff = new StringBuffer();

        Iterator iter = getOptions().iterator();

        buff.append("[");

        while (iter.hasNext()) {
            Option option = (Option) iter.next();

            if (option.getOpt() != null) {
                buff.append("-");
                buff.append(option.getOpt());
            } else {
                buff.append("--");
                buff.append(option.getLongOpt());
            }

            buff.append(" ");
            buff.append(option.getDescription());

            if (iter.hasNext()) {
                buff.append(", ");
            }
        }

        buff.append("]");

        return buff.toString();
    }
}

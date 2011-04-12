/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: MissingOptionException.java, Jan 25, 2010, 10:36:16 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jstojava.cml.vjetv.parser;

import java.util.Iterator;
import java.util.List;

/**
 * Class/Interface description
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class MissingOptionException extends ParseException {
    /** The list of missing options */
    private List missingOptions;

    /**
     * Construct a new <code>MissingSelectedException</code> with the
     * specified detail message.
     * 
     * @param message
     *            the detail message
     */
    public MissingOptionException(String message) {
        super(message);
    }

    /**
     * Constructs a new <code>MissingSelectedException</code> with the
     * specified list of missing options.
     * 
     * @param missingOptions
     *            the list of missing options
     * @since 1.2
     */
    public MissingOptionException(List missingOptions) {
        this(createMessage(missingOptions));
        this.missingOptions = missingOptions;
    }

    /**
     * Return the list of options (as strings) missing in the command line
     * parsed.
     * 
     * @return the missing options
     * @since 1.2
     */
    public List getMissingOptions() {
        return missingOptions;
    }

    /**
     * Build the exception message from the specified list of options.
     * 
     * @param missingOptions
     * @since 1.2
     */
    private static String createMessage(List missingOptions) {
        StringBuffer buff = new StringBuffer("Missing required option");
        buff.append(missingOptions.size() == 1 ? "" : "s");
        buff.append(": ");

        Iterator it = missingOptions.iterator();
        while (it.hasNext()) {
            buff.append(it.next());
            if (it.hasNext()) {
                buff.append(", ");
            }
        }

        return buff.toString();
    }
}

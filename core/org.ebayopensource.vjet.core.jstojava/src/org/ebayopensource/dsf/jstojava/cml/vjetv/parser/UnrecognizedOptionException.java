/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: UnrecognizedOptionException.java, Jan 25, 2010, 10:45:33 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jstojava.cml.vjetv.parser;

/**
 * Class/Interface description
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class UnrecognizedOptionException extends ParseException {
    /** The unrecognized option */
    private String option;

    /**
     * Construct a new <code>UnrecognizedArgumentException</code> with the
     * specified detail message.
     * 
     * @param message
     *            the detail message
     */
    public UnrecognizedOptionException(String message) {
        super(message);
    }

    /**
     * Construct a new <code>UnrecognizedArgumentException</code> with the
     * specified option and detail message.
     * 
     * @param message
     *            the detail message
     * @param option
     *            the unrecognized option
     * @since 1.2
     */
    public UnrecognizedOptionException(String message, String option) {
        this(message);
        this.option = option;
    }

    /**
     * Returns the unrecognized option.
     * 
     * @return the related option
     * @since 1.2
     */
    public String getOption() {
        return option;
    }

}

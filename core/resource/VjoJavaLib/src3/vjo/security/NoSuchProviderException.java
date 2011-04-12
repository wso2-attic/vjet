package vjo.security;

/*
 * @(#)src/classes/sov/java/security/NoSuchProviderException.java, security, asdev, 20070119 1.12
 * ===========================================================================
 * Licensed Materials - Property of IBM
 * "Restricted Materials of IBM"
 *
 * IBM SDK, Java(tm) 2 Technology Edition, v5.0
 * (C) Copyright IBM Corp. 1998, 2005. All Rights Reserved
 * ===========================================================================
 */

/*
 * ===========================================================================
 (C) Copyright Sun Microsystems Inc, 1992, 2004. All rights reserved.
 * ===========================================================================
 */



/*
 * ===========================================================================
 * Change activity:
 *
 * Reason  Date   Origin   Description
 * ------  ----   ------   ---------------------------------------------------- 
 * 76914   141004 eldergil Merge in Sun's 1.5.0 security changes
 *
 * ===========================================================================
 */

import vjo.lang.* ;

/**
 * This exception is thrown when a particular security provider is
 * requested but is not available in the environment.
 *
 * @version 1.17 01/12/03
 * @author Benjamin Renaud 
 */

public class NoSuchProviderException extends GeneralSecurityException {

    private static final long serialVersionUID = 8488111756688534474L;

    /**
     * Constructs a NoSuchProviderException with no detail message. A
     * detail message is a String that describes this particular
     * exception.
     */
    public NoSuchProviderException() {
        super();
    }

    /**
     * Constructs a NoSuchProviderException with the specified detail
     * message. A detail message is a String that describes this
     * particular exception.  
     *
     * @param msg the detail message.  
     */
    public NoSuchProviderException(String msg) {
        super(msg);
    }
}

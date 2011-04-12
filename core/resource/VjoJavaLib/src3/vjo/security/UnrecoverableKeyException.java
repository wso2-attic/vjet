package vjo.security;

/*
 * @(#)src/classes/sov/java/security/UnrecoverableKeyException.java, security, asdev, 20070119 1.11
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

import vjo.lang.* ;

/**
 * This exception is thrown if a key in the keystore cannot be recovered.
 *
 * @version 1.10, 12/19/03
 *
 * @since 1.2
 */

public class UnrecoverableKeyException extends GeneralSecurityException {

    private static final long serialVersionUID = 7275063078190151277L;

    /**
     * Constructs an UnrecoverableKeyException with no detail message.
     */
    public UnrecoverableKeyException() {
        super();
    }

    /**
     * Constructs an UnrecoverableKeyException with the specified detail
     * message, which provides more information about why this exception
     * has been thrown.
     *
     * @param msg the detail message.
     */
   public UnrecoverableKeyException(String msg) {
       super(msg);
    }
}


package vjo.security;

/*
 * @(#)src/classes/sov/java/security/KeyException.java, security, asdev, 20070119 1.12
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
 *===========================================================================
 * Change activity:
 *
 * Reason  Date   Origin   Description
 * ------  ------ -------- --------------------------------------------------- 
 * 76914   131004 eldergil Merge in Sun's 1.5.0 security changes
 *                        
 *===========================================================================
 */

import vjo.lang.* ;

/**
 * This is the basic key exception.
 *
 * @see Key
 * @see InvalidKeyException
 * @see KeyManagementException
 *
 * @version 1.15 01/12/03
 * @author Benjamin Renaud
 */

public class KeyException extends GeneralSecurityException {

    private static final long serialVersionUID = -7483676942812432108L;

    /**
     * Constructs a KeyException with no detail message. A detail
     * message is a String that describes this particular exception.
     */
    public KeyException() {
        super();
    }

    /**
     * Constructs a KeyException with the specified detail message.
     * A detail message is a String that describes this particular
     * exception.
     *
     * @param msg the detail message.  
     */
    public KeyException(String msg) {
        super(msg);
    }

    /**
     * Creates a <code>KeyException</code> with the specified
     * detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A <tt>null</tt> value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public KeyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a <code>KeyException</code> with the specified cause
     * and a detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).
     *
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A <tt>null</tt> value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public KeyException(Throwable cause) {
        super(cause);
    }
}


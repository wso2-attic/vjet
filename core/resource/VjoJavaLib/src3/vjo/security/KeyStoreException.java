package vjo.security;

/*
 * @(#)src/classes/sov/java/security/KeyStoreException.java, security, asdev, 20070119 1.12
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
 * This is the generic KeyStore exception. 
 * 
 * @author Jan Luehe
 *
 * @version 1.12, 12/19/03
 *
 * @since 1.2
 */

public class KeyStoreException extends GeneralSecurityException {

    private static final long serialVersionUID = -1119353179322377262L;

    /** 
     * Constructs a KeyStoreException with no detail message.  (A
     * detail message is a String that describes this particular
     * exception.)  
     */
    public KeyStoreException() {
        super();
    }

    /** 
     * Constructs a KeyStoreException with the specified detail
     * message.  (A detail message is a String that describes this
     * particular exception.)
     *
     * @param msg the detail message.  
     */
   public KeyStoreException(String msg) {
       super(msg);
    }

    /**
     * Creates a <code>KeyStoreException</code> with the specified
     * detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval
     *        by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A <tt>null</tt> value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public KeyStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a <code>KeyStoreException</code> with the specified cause
     * and a detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).
     *
     * @param cause the cause (which is saved for later retrieval by the
     *        {@link #getCause()} method).  (A <tt>null</tt> value is permitted,
     *        and indicates that the cause is nonexistent or unknown.)
     * @since 1.5
     */
    public KeyStoreException(Throwable cause) {
        super(cause);
    }
}


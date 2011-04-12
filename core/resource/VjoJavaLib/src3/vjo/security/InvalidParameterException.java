package vjo.security;

/*
 * @(#)src/classes/sov/java/security/InvalidParameterException.java, security, asdev, 20070119 1.12
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

import java.lang.IllegalArgumentException;

import vjo.lang.* ;

/**
 * This exception, designed for use by the JCA/JCE engine classes, 
 * is thrown when an invalid parameter is passed 
 * to a method.
 *
 * @author Benjamin Renaud
 * @version 1.17, 01/12/03
 */

public class InvalidParameterException extends IllegalArgumentException {

    private static final long serialVersionUID = -857968536935667808L;

    /**
     * Constructs an InvalidParameterException with no detail message.
     * A detail message is a String that describes this particular
     * exception.
     */
    public InvalidParameterException() {
        super();
    }

    /**
     * Constructs an InvalidParameterException with the specified
     * detail message.  A detail message is a String that describes
     * this particular exception.
     *
     * @param msg the detail message.  
     */
    public InvalidParameterException(String msg) {
        super(msg);
    }
}

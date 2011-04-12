package vjo.net;

/*
 * @(#)src/classes/sov/java/net/UnknownHostException.java, net, asdev, 20070119 1.10
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

import java.io.IOException; 

import vjo.lang.* ;

/**
 * Thrown to indicate that the IP address of a host could not be determined.
 *
 * @author  Jonathan Payne 
 * @version 1.10, 09/21/98
 * @since   JDK1.0
 */
public 
class UnknownHostException extends IOException {
    /**
     * Constructs a new <code>UnknownHostException</code> with the 
     * specified detail message. 
     *
     * @param   host   the detail message.
     */
    public UnknownHostException(String host) {
	super(host);
    }

    /**
     * Constructs a new <code>UnknownHostException</code> with no detail 
     * message. 
     */
    public UnknownHostException() {
    }
}


package vjo.net;

/*
 * @(#)src/classes/sov/java/net/ProtocolException.java, net, asdev, 20070119 1.9
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
 * Thrown to indicate that there is an error in the underlying 
 * protocol, such as a TCP error. 
 *
 * @author  Chris Warth
 * @version 1.11, 09/21/98
 * @since   JDK1.0
 */
public 
class ProtocolException extends IOException { 
    /**
     * Constructs a new <code>ProtocolException</code> with the 
     * specified detail message. 
     *
     * @param   host   the detail message.
     */
    public ProtocolException(String host) {
	super(host);
    }
    
    /**
     * Constructs a new <code>ProtocolException</code> with no detail message.
     */
    public ProtocolException() {
    }
}


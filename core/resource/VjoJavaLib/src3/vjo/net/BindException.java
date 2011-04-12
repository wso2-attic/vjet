package vjo.net;

import vjo.lang.* ;

/*
 * @(#)src/classes/sov/java/net/BindException.java, net, asdev, 20070119 1.9
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






 



/**
 * Signals that an error occurred while attempting to bind a
 * socket to a local address and port.  Typically, the port is
 * in use, or the requested local address could not be assigned.
 *
 * @since   JDK1.1
 */

public class BindException extends SocketException {

    /**
     * Constructs a new BindException with the specified detail 
     * message as to why the bind error occurred.
     * A detail message is a String that gives a specific 
     * description of this error.
     * @param msg the detail message
     */
    public BindException(String msg) {
	super(msg);
    }

    /**
     * Construct a new BindException with no detailed message.
     */
    public BindException() {}
}


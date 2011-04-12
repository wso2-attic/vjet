package vjo.net;

/*
 * @(#)src/classes/sov/java/net/PortUnreachableException.java, net, asdev, 20070119 1.6
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
 * Signals that an ICMP Port Unreachable message has been
 * received on a connected datagram.
 *
 * @since   1.4
 */

public class PortUnreachableException extends SocketException {

    /**
     * Constructs a new <code>PortUnreachableException</code> with a 
     * detail message.
     * @param msg the detail message
     */
    public PortUnreachableException(String msg) {
	super(msg);
    }

    /**
     * Construct a new <code>PortUnreachableException</code> with no 
     * detailed message.
     */
    public PortUnreachableException() {}
}


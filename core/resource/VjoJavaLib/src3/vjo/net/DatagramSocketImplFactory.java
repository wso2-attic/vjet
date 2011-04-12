package vjo.net;

/*
 * @(#)src/classes/sov/java/net/DatagramSocketImplFactory.java, net, asdev, 20070119 1.10
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
 * Change activity:
 *
 * Reason  Date   Origin   Description
 * ------  ----   ------   ----------------------------------------------------
 * 003683  051199 hdrl     Sun 1.3 rollup
 * 056111  301002 stalleyj Merge 1.4.1 changes 
 * ===========================================================================
 */

import vjo.lang.* ;

/**
 * This interface defines a factory for datagram socket implementations. It
 * is used by the classes <code>DatagramSocket</code> to create actual socket
 * implementations.
 *
 * @author  Yingxian Wang
 * @version %I %E
 * @see     java.net.DatagramSocket
 * @since   1.3
 */
public
interface DatagramSocketImplFactory {
    /**
     * Creates a new <code>DatagramSocketImpl</code> instance.
     *
     * @return  a new instance of <code>DatagramSocketImpl</code>.
     * @see     java.net.DatagramSocketImpl
     */
    DatagramSocketImpl createDatagramSocketImpl();
}

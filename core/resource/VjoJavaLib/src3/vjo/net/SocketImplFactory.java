package vjo.net;

/*
 * @(#)src/classes/sov/java/net/SocketImplFactory.java, net, asdev, 20070119 1.10
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
 * This interface defines a factory for socket implementations. It
 * is used by the classes <code>Socket</code> and
 * <code>ServerSocket</code> to create actual socket
 * implementations.
 *
 * @author  Arthur van Hoff
 * @version 1.15, 02/02/00
 * @see     java.net.Socket
 * @see     java.net.ServerSocket
 * @since   JDK1.0
 */
public
interface SocketImplFactory {
    /**
     * Creates a new <code>SocketImpl</code> instance.
     *
     * @return  a new instance of <code>SocketImpl</code>.
     * @see     java.net.SocketImpl
     */
    SocketImpl createSocketImpl();
}


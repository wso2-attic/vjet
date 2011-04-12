package vjo.net;

/*
 * @(#)src/classes/sov/java/net/InetAddressImpl.java, net, asdev, 20070119 1.7
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
 * ===========================================================================
 * 
 * Change activity:
 *
 * Reason  Date    Origin   Description
 * ------  ------  ------   --------------------------------------------------
 * 099499  230106  riclau   Change lookupAllHostAddr() to return InetAddress[]
 *
 * ===========================================================================
 */

import java.io.IOException;

import vjo.lang.* ;

/*
 * Package private interface to "implementation" used by 
 * {@link InetAddress}.
 * <p> 
 * See {@link java.net.Inet4AddressImp} and 
 * {@link java.net.Inet6AddressImp}.
 *
 * @since 1.4
 */
interface InetAddressImpl {

    String getLocalHostName() throws UnknownHostException;
    InetAddress[]
        lookupAllHostAddr(String hostname) throws UnknownHostException; /*ibm@99499*/
    String getHostByAddr(byte[] addr) throws UnknownHostException;

    InetAddress anyLocalAddress();
    InetAddress loopbackAddress();
    boolean isReachable(InetAddress addr, int timeout, NetworkInterface netif,
			int ttl) throws IOException;
}


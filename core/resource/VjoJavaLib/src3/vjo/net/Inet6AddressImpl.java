package vjo.net;

/*
 * @(#)src/classes/sov/java/net/Inet6AddressImpl.java, net, asdev, 20070119 1.7
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

import java.util.Enumeration ;

import vjo.lang.* ;

/*
 * Package private implementation of InetAddressImpl for dual
 * IPv4/IPv6 stack.
 * <p>
 * If InetAddress.preferIPv6Address is true then anyLocalAddress(),
 * loopbackAddress(), and localHost() will return IPv6 addresses, 
 * otherwise IPv4 addresses.
 *
 * @since 1.4
 */

class Inet6AddressImpl implements InetAddressImpl {
    public native String getLocalHostName() throws UnknownHostException;
    public native InetAddress[]
        lookupAllHostAddr(String hostname) throws UnknownHostException; /*ibm@99499*/
    public native String getHostByAddr(byte[] addr) throws UnknownHostException;
    private native boolean isReachable0(byte[] addr, int scope, int timeout, byte[] inf, int ttl, int if_scope) throws IOException;

    public boolean isReachable(InetAddress addr, int timeout, NetworkInterface netif, int ttl) throws IOException {
	byte[] ifaddr = null;
	int scope = -1;
	int netif_scope = -1;
	if (netif != null) {
	    /*
	     * Let's make sure we bind to an address of the proper family.
	     * Which means same family as addr because at this point it could
	     * be either an IPv6 address or an IPv4 address (case of a dual
	     * stack system).
	     */
	    Enumeration it = netif.getInetAddresses();
	    InetAddress inetaddr = null;
	    while (it.hasMoreElements()) {
		inetaddr = (InetAddress) it.nextElement();
		if (inetaddr.getClass().isInstance(addr)) {
		    ifaddr = inetaddr.getAddress();
		    if (inetaddr instanceof Inet6Address) {
			netif_scope = ((Inet6Address) inetaddr).getScopeId();
		    }
		    break;
		}
	    }
	    if (ifaddr == null) {
		// Interface doesn't support the address family of 
		// the destination
		return false;
	    }
	}
	if (addr instanceof Inet6Address)
	    scope = ((Inet6Address) addr).getScopeId();
	return isReachable0(addr.getAddress(), scope, timeout, ifaddr, ttl, netif_scope);
    }

    public synchronized InetAddress anyLocalAddress() {
        if (anyLocalAddress == null) {
            if (InetAddress.preferIPv6Address) {
                anyLocalAddress = new Inet6Address();
                anyLocalAddress.hostName = "::";
            } else {
                anyLocalAddress = (new Inet4AddressImpl()).anyLocalAddress();
            }
        }
        return anyLocalAddress;
    }

    public synchronized InetAddress loopbackAddress() {
        if (loopbackAddress == null) {
             if (InetAddress.preferIPv6Address) {
                 byte[] loopback = 
			{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                         0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01};
                 loopbackAddress = new Inet6Address("localhost", loopback);
             } else {
                loopbackAddress = (new Inet4AddressImpl()).loopbackAddress();
             }
        }
        return loopbackAddress;
    }

    private InetAddress      anyLocalAddress;
    private InetAddress      loopbackAddress;
}



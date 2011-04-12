package vjo.net;

/*
 * @(#)src/classes/sov/java/net/Inet4AddressImpl.java, net, asdev, 20070119 1.7
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
 * Package private implementation of InetAddressImpl for IPv4.
 *
 * @since 1.4
 */
class Inet4AddressImpl implements InetAddressImpl {
    public native String getLocalHostName() throws UnknownHostException;
    public native InetAddress[]
        lookupAllHostAddr(String hostname) throws UnknownHostException; /*ibm@99499*/
    public native String getHostByAddr(byte[] addr) throws UnknownHostException;
    private native boolean isReachable0(byte[] addr, int timeout, byte[] ifaddr, int ttl) throws IOException;

    public synchronized InetAddress anyLocalAddress() {
        if (anyLocalAddress == null) {
            anyLocalAddress = new Inet4Address(); // {0x00,0x00,0x00,0x00}
            anyLocalAddress.hostName = "0.0.0.0";
        }
        return anyLocalAddress;
    }

    public synchronized InetAddress loopbackAddress() {
        if (loopbackAddress == null) {
            byte[] loopback = {0x7f,0x00,0x00,0x01};
            loopbackAddress = new Inet4Address("localhost", loopback);
        }
        return loopbackAddress;
    }

  public boolean isReachable(InetAddress addr, int timeout, NetworkInterface netif, int ttl) throws IOException {
      byte[] ifaddr = null;
      if (netif != null) {
	  /*
	   * Let's make sure we use an address of the proper family
	   */
	  Enumeration it = netif.getInetAddresses();
	  InetAddress inetaddr = null;
	  while (!(inetaddr instanceof Inet4Address) &&
		 it.hasMoreElements())
	      inetaddr = (InetAddress) it.nextElement();
	  if (inetaddr instanceof Inet4Address)
	      ifaddr = inetaddr.getAddress();
      }
      return isReachable0(addr.getAddress(), timeout, ifaddr, ttl);
  }
    private InetAddress      anyLocalAddress;
    private InetAddress      loopbackAddress;
}




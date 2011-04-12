package vjo.net;

/*
 * @(#)src/classes/sov/java/net/SocketUtil.java, net, andev, 20070106 1.3
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
 * Change activity:
 *
 * Reason  Date    Origin   Description
 * ------ ------   -------- ----------------------------------------------------
 * 087365 18052005 mpartrid Module created. 
 *
 * ===========================================================================
 * Module Information:
 *
 * DESCRIPTION: Class containing Socket utility methods.
 * ===========================================================================
 */
 
import vjo.lang.* ;

class SocketUtil
{
    public SocketUtil()
    {
    }

    // Dummy SocketUtil methods, for Windows implementation.

    static private long getThreadTag()
    {
        return 0;
    }

    static private boolean isBlocked(long tag)
    {
        return false;
    }

    static private boolean interrupt(long tag)
    {
        return false;
    }

}


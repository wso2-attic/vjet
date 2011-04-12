package vjo.java.sun.text.normalizer;

/*
 * @(#)ICUData.java	1.3 05/11/17
 *
 * Portions Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/*
 *******************************************************************************
 * (C) Copyright IBM Corp. 1996-2005 - All Rights Reserved                     *
 *                                                                             *
 * The original version of this source code and documentation is copyrighted   *
 * and owned by IBM, These materials are provided under terms of a License     *
 * Agreement between IBM and Sun. This technology is protected by multiple     *
 * US and International patents. This notice and attribution to IBM may not    *
 * to removed.                                                                 *
 *******************************************************************************
 */

import java.util.MissingResourceException;

import vjo.java.lang.* ;
import vjo.java.lang.System ;

import vjo.java.io.InputStream;
import vjo.java.javaconversion.ClassUtil;

import vjo.java.security.AccessController;
import vjo.java.security.PrivilegedAction;

//import java.net.URL;

/**
 * Provides access to ICU data files as InputStreams.  Implements security checking.
 */
public final class ICUData {

    private static InputStream getStream(final Class root, final String resourceName, boolean required) {
        InputStream i = null;
        
        if (System.getSecurityManager() != null) {
            i = (InputStream)AccessController.doPrivileged(new PrivilegedAction() {
                    public Object run() {
                        return root.getResourceAsStream(resourceName);
                    }
                });
        } else {
//MrP - need utility
//            i = root.getResourceAsStream(resourceName);
            i = ClassUtil.getResourceAsStream(root, resourceName);
        }

        if (i == null && required) {
            throw new MissingResourceException("could not locate data", root.getPackage().getName(), resourceName);
        }
        return i;
    }

    /*
     * Convenience override that calls getStream(ICUData.class, resourceName, false);
     */
    public static InputStream getStream(String resourceName) {
        return getStream(ICUData.class, resourceName, false);
    }
        
    /*
     * Convenience method that calls getStream(ICUData.class, resourceName, true).
     */
    public static InputStream getRequiredStream(String resourceName) {
        return getStream(ICUData.class, resourceName, true);
    }
}



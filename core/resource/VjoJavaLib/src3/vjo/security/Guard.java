package vjo.security;

/*
 * @(#)src/classes/sov/java/security/Guard.java, security, asdev, 20070119 1.11
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
 * <p> This interface represents a guard, which is an object that is used
 * to protect access to another object.
 *
 * <p>This interface contains a single method, <code>checkGuard</code>,
 * with a single <code>object</code> argument. <code>checkGuard</code> is
 * invoked (by the GuardedObject <code>getObject</code> method)
 * to determine whether or not to allow access to the object.
 *
 * @see GuardedObject
 *
 * @version 1.10 01/12/03
 * @author Roland Schemers
 * @author Li Gong
 */

public interface Guard {

    /**
     * Determines whether or not to allow access to the guarded object
     * <code>object</code>. Returns silently if access is allowed.
     * Otherwise, throws a SecurityException.
     *
     * @param object the object being protected by the guard.
     *
     * @exception SecurityException if access is denied.
     *
     */
    void checkGuard(Object object) throws SecurityException;
}


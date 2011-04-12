package vjo.java.lang.reflect;

import vjo.java.lang.* ;

/*
 * @(#)src/classes/sov/java/lang/reflect/GenericDeclaration.java, reflect, asdev, 20070119 1.3
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
 * @(#)GenericDeclaration.java	1.3 04/04/20
 *
 */


/**
 * A common interface for all entities that declare type variables.
 *
 * @since 1.5
 */
public interface GenericDeclaration {
    /**
     * Returns an array of <tt>TypeVariable</tt> objects that
     * represent the type variables declared by the generic
     * declaration represented by this <tt>GenericDeclaration</tt>
     * object, in declaration order.  Returns an array of length 0 if
     * the underlying generic declaration declares no type variables.
     *
     * @return an array of <tt>TypeVariable</tt> objects that represent
     *     the type variables declared by this generic declaration
     * @throws GenericSignatureFormatError if the generic
     *     signature of this generic declaration does not conform to
     *     the format specified in the Java Virtual Machine Specification,
     *     3rd edition
     */
    public TypeVariable<?>[] getTypeParameters();
}


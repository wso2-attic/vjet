package vjo.java.lang.reflect;

import vjo.java.lang.* ;

/*
 * @(#)src/classes/sov/java/lang/reflect/WildcardType.java, reflect, asdev, 20070119 1.3
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
 * @(#)WildcardType.java	1.3 04/01/12
 *
 */


/**
 * WildcardType represents a wildcard type expression, such as
 * <tt>?</tt>, <tt>? extends Number</tt>, or <tt>? super Integer</tt>.
 *
 * @since 1.5
 */
										// MrP - must be java.lang
public interface WildcardType extends java.lang.reflect.Type {
    /**
     * Returns an array of <tt>Type</tt> objects representing the  upper
     * bound(s) of this type variable.  Note that if no upper bound is
     * explicitly declared, the upper bound is <tt>Object</tt>.
     *
     * <p>For each upper bound B :
     * <ul>
     *  <li>if B is a parameterized type or a type variable, it is created,
     *  (see {@link java.lang.reflect.ParameterizedType ParameterizedType} 
     *  for the details of the creation process for parameterized types).
     *  <li>Otherwise, B is resolved. 
     * </ul>
     *
     * @return an array of Types representing the upper bound(s) of this 
     *     type variable
     * @throws TypeNotPresentException if any of the
     *     bounds refers to a non-existent type declaration
     * @throws MalformedParameterizedTypeException if any of the 
     *     bounds refer to a parameterized type that cannot be instantiated 
     *     for any reason
     */
	//MrP - must be java.lang
	java.lang.reflect.Type[] getUpperBounds();

    /**
     * Returns an array of <tt>Type</tt> objects representing the 
     * lower bound(s) of this type variable.  Note that if no lower bound is
     * explicitly declared, the lower bound is the type of <tt>null</tt>.
     * In this case, a zero length array is returned.
     * 
     * <p>For each lower bound B :
     * <ul>
     *   <li>if B is a parameterized type or a type variable, it is created,
     *  (see {@link java.lang.reflect.ParameterizedType ParameterizedType} 
     *  for the details of the creation process for parameterized types).
     *   <li>Otherwise, B is resolved.
     * </ul>
     *
     * @return an array of Types representing the lower bound(s) of this 
     *     type variable
     * @throws TypeNotPresentException if any of the
     *     bounds refers to a non-existent type declaration
     * @throws MalformedParameterizedTypeException if any of the 
     *     bounds refer to a parameterized type that cannot be instantiated 
     *     for any reason
     */
	//MrP - must be java.lang
	java.lang.reflect.Type[] getLowerBounds(); 
    // one or many? Up to language spec; currently only one, but this API
    // allows for generalization. 
}



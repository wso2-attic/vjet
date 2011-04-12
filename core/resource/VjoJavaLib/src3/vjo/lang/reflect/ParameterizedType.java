package vjo.java.lang.reflect;

import vjo.java.lang.* ;

/*
 * @(#)src/classes/sov/java/lang/reflect/ParameterizedType.java, reflect, asdev, 20070119 1.3
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
 * @(#)ParameterizedType.java	1.4 04/02/06
 *
 */



/**
 * ParameterizedType represents a parameterized type such as
 * Collection&lt;String&gt;.
 *
 * <p>A parameterized type is created the first time it is needed by a
 * reflective method, as specified in this package. When a
 * parameterized type p is created, the generic type declaration that
 * p instantiates is resolved, and all type arguments of p are created
 * recursively. See {@link java.lang.reflect.TypeVariable
 * TypeVariable} for details on the creation process for type
 * variables. Repeated creation of a parameterized type has no effect.
 *
 * <p>Instances of classes that implement this interface must implement
 * an equals() method that equates any two instances that share the
 * same generic type declaration and have equal type parameters.
 *
 * @since 1.5
 */
                                           //MrP - must be java.lang
public interface ParameterizedType extends java.lang.reflect.Type {
    /**
     * Returns an array of <tt>Type</tt> objects representing the actual type
     * arguments to this type.
     * 
     * <p>Note that in some cases, the returned array be empty. This can occur
     * if this type represents a non-parameterized type nested within
     * a parameterized type.
     *
     * @return an array of <tt>Type</tt> objects representing the actual type
     *     arguments to this type
     * @throws <tt>TypeNotPresentException</tt> if any of the
     *     actual type arguments refers to a non-existent type declaration
     * @throws <tt>MalformedParameterizedTypeException</tt> if any of the 
     *     actual type parameters refer to a parameterized type that cannot
     *     be instantiated for any reason
     * @since 1.5
     */
	//MrP - must be java.lang
	java.lang.reflect.Type[] getActualTypeArguments();

    /**
     * Returns the <tt>Type</tt> object representing the class or interface
     * that declared this type.
     *
     * @return the <tt>Type</tt> object representing the class or interface
     *     that declared this type
     * @since 1.5
     */
	//MrP - must be java.lang
	java.lang.reflect.Type getRawType();

    /**
     * Returns a <tt>Type</tt> object representing the type that this type
     * is a member of.  For example, if this type is {@code O<T>.I<S>},
     * return a representation of {@code O<T>}.
     *
     * <p>If this type is a top-level type, <tt>null</tt> is returned.
     *
     * @return a <tt>Type</tt> object representing the type that 
     *     this type is a member of. If this type is a top-level type, 
     *     <tt>null</tt> is returned
     * @throws <tt>TypeNotPresentException</tt> if the owner type
     *     refers to a non-existent type declaration
     * @throws <tt>MalformedParameterizedTypeException</tt> if the owner type
     *     refers to a parameterized type that cannot be instantiated 
     *     for any reason
     * @since 1.5
     */
	//MrP - must be java.lang
	java.lang.reflect.Type getOwnerType();
}



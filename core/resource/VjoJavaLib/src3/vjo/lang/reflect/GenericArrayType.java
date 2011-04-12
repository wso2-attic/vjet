package vjo.java.lang.reflect;

import vjo.java.lang.* ;

/*
 * @(#)src/classes/sov/java/lang/reflect/GenericArrayType.java, reflect, asdev, 20070119 1.3
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
 * @(#)GenericArrayType.java	1.3 04/01/12
 *
 */

/**
 * <tt>GenericArrayType</tt> represents an array type whose component
 * type is either a parameterized type or a type variable.
 * @since 1.5
 */
											//MrP - must be java.lang
public interface GenericArrayType extends java.lang.reflect.Type {
    /**
     * Returns a <tt>Type</tt> object representing the component type
     * of this array. This method creates the component type of the
     * array.  See the declaration of {@link
     * java.lang.reflect.ParameterizedType ParameterizedType} for the
     * semantics of the creation process for parameterized types and
     * see {@link java.lang.reflect.TypeVariable TypeVariable} for the
     * creation process for type variables.
     *
     * @return  a <tt>Type</tt> object representing the component type
     *     of this array
     * @throws TypeNotPresentException if the underlying array type's
     *     component type refers to a non-existent type declaration
     * @throws MalformedParameterizedTypeException if  the
     *     underlying array type's component type refers to a
     *     parameterized type that cannot be instantiated for any reason
     */
	//MrP - must be java.lang
	java.lang.reflect.Type getGenericComponentType();
}



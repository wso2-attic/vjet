package vjo.java.lang.reflect;

/*
 * @(#)src/classes/sov/java/lang/reflect/ReflectAccess.java, reflect, asdev, 20070119 1.8
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
 * Reason Date   Origin   Description
 * ------ ----   ------   ----------------------------------------------------
 * 071341 050404 nichanir Change slot in Field, Method, Constructor to long for J9
 * ===========================================================================
 */

import vjo.java.lang.* ;

import sun.reflect.MethodAccessor;
import sun.reflect.ConstructorAccessor;

/** Package-private class implementing the
    sun.reflect.LangReflectAccess interface, allowing the java.lang
    package to instantiate objects in this package. */

class ReflectAccess implements sun.reflect.LangReflectAccess {
    public Field newField(Class declaringClass,
                          String name,
                          Class type,
                          int modifiers,
                          long slot,                    /*ibm@71341*/
                          String signature,
                          byte[] annotations)
    {
        return new Field(declaringClass,
                         name,
                         type,
                         modifiers,
                         slot,
                         signature,
                         annotations);
    }

    public Method newMethod(Class declaringClass,
                            String name,
                            Class[] parameterTypes,
                            Class returnType,
                            Class[] checkedExceptions,
                            int modifiers,
                            long slot,                   /*ibm@71341*/
                            String signature,
                            byte[] annotations,
                            byte[] parameterAnnotations,
                            byte[] annotationDefault)
    {
        return new Method(declaringClass,
                          name,
                          parameterTypes,
                          returnType,
                          checkedExceptions,
                          modifiers,
                          slot,
                          signature,
                          annotations,
                          parameterAnnotations,
                          annotationDefault);
    }

    public <T> Constructor<T> newConstructor(Class<T> declaringClass,
					     Class[] parameterTypes,
					     Class[] checkedExceptions,
					     int modifiers,
                                             long slot,                   /*ibm@71341*/
                                             String signature,
                                             byte[] annotations,
                                             byte[] parameterAnnotations)
    {
        return new Constructor<T>(declaringClass,
				  parameterTypes,
				  checkedExceptions,
				  modifiers,
				  slot,
                                  signature,
                                  annotations,
                                  parameterAnnotations);
    }

    public MethodAccessor getMethodAccessor(Method m) {
        return m.getMethodAccessor();
    }

    public void setMethodAccessor(Method m, MethodAccessor accessor) {
        m.setMethodAccessor(accessor);
    }

    public ConstructorAccessor getConstructorAccessor(Constructor c) {
        return c.getConstructorAccessor();
    }

    public void setConstructorAccessor(Constructor c,
                                       ConstructorAccessor accessor)
    {
        c.setConstructorAccessor(accessor);
    }

    public long getConstructorSlot(Constructor c) {       /*ibm@71341*/
        return c.getSlot();
    }

    public String getConstructorSignature(Constructor c) {
        return c.getSignature();
    }

    public byte[] getConstructorAnnotations(Constructor c) {
        return c.getRawAnnotations();
    }

    public byte[] getConstructorParameterAnnotations(Constructor c) {
        return c.getRawParameterAnnotations();
    }

    //
    // Copying routines, needed to quickly fabricate new Field,
    // Method, and Constructor objects from templates
    //
    public Method      copyMethod(Method arg) {
        return arg.copy();
    }

    public Field       copyField(Field arg) {
        return arg.copy();
    }

    public <T> Constructor<T> copyConstructor(Constructor<T> arg) {
        return arg.copy();
    }
}


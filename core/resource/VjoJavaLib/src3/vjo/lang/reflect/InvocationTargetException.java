package vjo.java.lang.reflect;

import vjo.java.lang.* ;

/*
 * @(#)src/classes/sov/java/lang/reflect/InvocationTargetException.java, reflect, asdev, 20070119 1.11
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


/**
 * InvocationTargetException is a checked exception that wraps
 * an exception thrown by an invoked method or constructor.
 *
 * <p>As of release 1.4, this exception has been retrofitted to conform to
 * the general purpose exception-chaining mechanism.  The "target exception"
 * that is provided at construction time and accessed via the
 * {@link #getTargetException()} method is now known as the <i>cause</i>,
 * and may be accessed via the {@link Throwable#getCause()} method,
 * as well as the aforementioned "legacy method."
 *
 * @see Method
 * @see Constructor
 */
public class InvocationTargetException extends Exception {
    /**
     * Use serialVersionUID from JDK 1.1.X for interoperability
     */
    private static final long serialVersionUID = 4085088731926701167L;

     /**
     * This field holds the target if the 
     * InvocationTargetException(Throwable target) constructor was
     * used to instantiate the object
     * 
     * @serial 
     * 
     */
    private Throwable target;

    /**
     * Constructs an <code>InvocationTargetException</code> with 
     * <code>null</code> as the target exception.
     */
    protected InvocationTargetException() {
	super((Throwable)null);  // Disallow initCause
    }

    /**
     * Constructs a InvocationTargetException with a target exception.
     * 
     * @param target the target exception
     */
    public InvocationTargetException(Throwable target) {
	super((Throwable)null);  // Disallow initCause
        this.target = target;
    }

    /**
     * Constructs a InvocationTargetException with a target exception
     * and a detail message.
     *
     * @param target the target exception
     * @param s      the detail message
     */
    public InvocationTargetException(Throwable target, String s) {
	super(s, null);  // Disallow initCause
        this.target = target;
    }

    /**
     * Get the thrown target exception.
     *
     * <p>This method predates the general-purpose exception chaining facility.
     * The {@link Throwable#getCause()} method is now the preferred means of
     * obtaining this information.
     *
     * @return the thrown target exception (cause of this exception).
     */
    public Throwable getTargetException() {
	return target;
    }

    /**
     * Returns the the cause of this exception (the thrown target exception,
     * which may be <tt>null</tt>).
     *
     * @return  the cause of this exception.
     * @since   1.4
     */
    public Throwable getCause() {
        return target;
    }
}


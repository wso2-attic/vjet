package vjo.java.text;

import vjo.java.lang.* ;
/*
 * @(#)src/classes/sov/java/text/ParsePosition.java, i18n, asdev, 20070119 1.10
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
 * <code>ParsePosition</code> is a simple class used by <code>Format</code>
 * and its subclasses to keep track of the current position during parsing.
 * The <code>parseObject</code> method in the various <code>Format</code>
 * classes requires a <code>ParsePosition</code> object as an argument.
 *
 * <p>
 * By design, as you parse through a string with different formats,
 * you can use the same <code>ParsePosition</code>, since the index parameter
 * records the current position.
 *
 * @version     1.14 07/24/98
 * @author      Mark Davis
 * @see         java.text.Format
 */

public class ParsePosition {

    /**
     * Input: the place you start parsing.
     * <br>Output: position where the parse stopped.
     * This is designed to be used serially,
     * with each call setting index up for the next one.
     */
    int index = 0;
    int errorIndex = -1;

    /**
     * Retrieve the current parse position.  On input to a parse method, this
     * is the index of the character at which parsing will begin; on output, it
     * is the index of the character following the last character parsed.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Set the current parse position.
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Create a new ParsePosition with the given initial index.
     */
    public ParsePosition(int index) {
        this.index = index;
    }
    /**
     * Set the index at which a parse error occurred.  Formatters
     * should set this before returning an error code from their
     * parseObject method.  The default value is -1 if this is not set.
     */
    public void setErrorIndex(int ei)
    {
        errorIndex = ei;
    }

    /**
     * Retrieve the index at which an error occurred, or -1 if the
     * error index has not been set.
     */
    public int getErrorIndex()
    {
        return errorIndex;
    }
    /**
     * Overrides equals
     */
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (!(obj instanceof ParsePosition))
            return false;
        ParsePosition other = (ParsePosition) obj;
        return (index == other.index && errorIndex == other.errorIndex);
    }

    /**
     * Returns a hash code for this ParsePosition.
     * @return a hash code value for this object
     */
    public int hashCode() {
        return (errorIndex << 16) | index;
    }

    /**
     * Return a string representation of this ParsePosition.
     * @return  a string representation of this object
     */
    public String toString() {
        return getClass().getName() +
            "[index=" + index +
            ",errorIndex=" + errorIndex + ']';
    }
}

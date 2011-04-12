package vjo.java.text;

import vjo.java.lang.* ;

/*
 * @(#)src/classes/sov/java/text/EntryPair.java, i18n, asdev, 20070119 1.10
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
 * This is used for building contracting character tables.  entryName
 * is the contracting character name and value is its collation
 * order.
 */
final class EntryPair
{
    public String entryName;
    public int value;
    public boolean fwd;

    public EntryPair(String name, int value) {
        this(name, value, true);
    }
    public EntryPair(String name, int value, boolean fwd) {
        this.entryName = name;
        this.value = value;
        this.fwd = fwd;
    }
}

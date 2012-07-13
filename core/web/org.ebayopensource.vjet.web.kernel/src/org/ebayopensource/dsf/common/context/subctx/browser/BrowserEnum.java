/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.context.subctx.browser;

import java.util.ListIterator;

import org.ebayopensource.dsf.common.enums.BaseEnum;

public class BrowserEnum extends BaseEnum{

     /**
     * 
     */
    private static final long serialVersionUID = 3622264992962311926L;

    public static final BrowserEnum MSIE = //key used in db
         new BrowserEnum("MSIE", 1);
    
     public static final BrowserEnum INTERNET_EXPLORER  =
         new BrowserEnum("INTERNET EXPLORER", 2);
    
     public static final BrowserEnum MOZILLA    =   //key used in db
         new BrowserEnum("MOZILLA", 3);
    
     public static final BrowserEnum NETSCAPE   =   //key used in db
         new BrowserEnum("NETSCAPE", 4);
    
     public static final BrowserEnum OPERA  =
         new BrowserEnum("OPERA", 5);
     
    public static final BrowserEnum WEBTV   =
        new BrowserEnum("WEBTV", 6);
    public static final BrowserEnum AOL =
        new BrowserEnum("AOL", 7);
    public static final BrowserEnum SAFARI  =
        new BrowserEnum("SAFARI", 8);
  // Added for Bugfix  BUGDB00342972 to suppport MSNTV2
  public static final BrowserEnum MSNTV =
        new BrowserEnum("MSNTV", 9); 

    public static final BrowserEnum UNKNOWN =
        new BrowserEnum("Unknown", -1);
     
 
    // Add new instances above this line
    
    //-----------------------------------------------------------------//
    // Template code follows....do not modify other than to replace    //
    // enumeration class name with the name of this class.             //
    //-----------------------------------------------------------------//   
    private BrowserEnum(String name, int intValue) {
        super(intValue, name);
    }

    // ------- Type specific interfaces -------------------------------//
    /** Get the enumeration instance for a given value or null */
    public static BrowserEnum get(int key) {
        return (BrowserEnum) getEnum(BrowserEnum.class, key);
    }

    /** Get the enumeration instance for a given value or return the
     *  elseEnum default.
     */
    public static BrowserEnum getElseReturn(int key, OsEnum elseEnum) {
        return (BrowserEnum) getElseReturnEnum(BrowserEnum.class, key, elseEnum);
    }

    /** Return an bidirectional iterator that traverses the enumeration
     *  instances in the order they were defined.
     */
    public static ListIterator iterator() {
        return getIterator(BrowserEnum.class);
    }
}


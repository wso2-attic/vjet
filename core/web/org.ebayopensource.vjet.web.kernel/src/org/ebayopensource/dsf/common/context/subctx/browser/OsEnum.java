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

public final class OsEnum extends BaseEnum {

    /**
     * 
     */
    private static final long serialVersionUID = 1012867993311783087L;

    public static final OsEnum WIN16 =
        new OsEnum("WIN16", 1);

    public static final OsEnum WIN31    =
        new OsEnum("WINDOWS 3.1", 2);
        
    public static final OsEnum WIN95    =
        new OsEnum("WIN95", 3);

    public static final OsEnum WIN98    =   //key used in db
        new OsEnum("WIN98", 4);

    public static final OsEnum WIN_ME   =   //key used in db
        new OsEnum("WINDOWS ME", 5);

    public static final OsEnum WINDOWS98    =
        new OsEnum("WINDOWS 98", 6);
    public static final OsEnum WINDOWS95    =
        new OsEnum("WINDOWS 95", 7);
    public static final OsEnum WIN_XP   =
        new OsEnum("WINDOWS NT 5.1", 8);
    public static final OsEnum WIN_2003_SERVER  =
        new OsEnum("WINDOWS NT 5.2", 9);
    public static final OsEnum WIN_XP_NEW   =   //key used in db
        new OsEnum("WINDOWS XP", 10);
    public static final OsEnum WIN2K    =
        new OsEnum("WINDOWS NT 5.0", 11);
    public static final OsEnum WIN2K_NEW    =   //key used in db
        new OsEnum("WINDOWS 2000", 12);
    public static final OsEnum WIN_NT4  =
        new OsEnum("WINNT4", 13);
    public static final OsEnum WIN_NT4_NEW  =   //key used in db
        new OsEnum("NT 4", 14);
    public static final OsEnum Mac_OS_X =
        new OsEnum("MACOSX", 15);
    public static final OsEnum Mac_OS_X_NEW =   //key used in db
        new OsEnum("MAC OS X", 16);

    public static final OsEnum WIN  =
        new OsEnum("WIN", 17);

    public static final OsEnum MAC  =
        new OsEnum("MAC", 18);
        
    public static final OsEnum WINDOWS  =
        new OsEnum("WINDOWS", 20);

    public static final OsEnum LINUX    =
        new OsEnum("LINUX", 21);

    public static final OsEnum SUNOS    =
        new OsEnum("SUNOS", 22);

    public static final OsEnum HP_UX    =
        new OsEnum("HP-UX", 23);
        
    public static final OsEnum MACINTOSH    =
        new OsEnum("MACINTOSH", 24);
    public static final OsEnum OPEN_BSD =
        new OsEnum("OPENBSD", 25);
    public static final OsEnum WINNT    =
        new OsEnum("WINNT", 26);
    
    public static final OsEnum MAC_POWERPC  =
        new OsEnum("MAC_POWERPC", 27);

        
    public static final OsEnum UNKNOWN  =
        new OsEnum("Unknown", -1);
        
    // Add new instances above this line

    //-----------------------------------------------------------------//
    // Template code follows....do not modify other than to replace    //
    // enumeration class name with the name of this class.             //
    //-----------------------------------------------------------------//   
    private OsEnum(String name, int intValue) {
        super(intValue, name);
    } 
    // ------- Type specific interfaces -------------------------------//
    /** Get the enumeration instance for a given value or null */
    public static OsEnum get(int key) {
        return (OsEnum)getEnum(OsEnum.class, key);
    }   
    /** Get the enumeration instance for a given value or return the
     *  elseEnum default.
     */
    public static OsEnum getElseReturn(int key, 
    OsEnum elseEnum) {  
        return (OsEnum)getElseReturnEnum(OsEnum.class, key, elseEnum);
    }   
    /** Return an bidirectional iterator that traverses the enumeration
     *  instances in the order they were defined.
     */
    public static ListIterator iterator() {
        return getIterator(OsEnum.class);
    }                         
}

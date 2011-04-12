/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: PatternOptionBuilder.java, Jan 25, 2010, 10:43:14 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jstojava.cml.vjetv.parser;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Date;

/**
 * Class/Interface description
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class PatternOptionBuilder {
    /** String class */
    public static final Class STRING_VALUE = String.class;

    /** Object class */
    public static final Class OBJECT_VALUE = Object.class;

    /** Number class */
    public static final Class NUMBER_VALUE = Number.class;

    /** Date class */
    public static final Class DATE_VALUE = Date.class;

    /** Class class */
    public static final Class CLASS_VALUE = Class.class;

    // / can we do this one??
    // is meant to check that the file exists, else it errors.
    // ie) it's for reading not writing.

    /** FileInputStream class */
    public static final Class EXISTING_FILE_VALUE = FileInputStream.class;

    /** File class */
    public static final Class FILE_VALUE = File.class;

    /** File array class */
    public static final Class FILES_VALUE = File[].class;

    /** URL class */
    public static final Class URL_VALUE = URL.class;

    /**
     * Retrieve the class that <code>ch</code> represents.
     * 
     * @param ch
     *            the specified character
     * @return The class that <code>ch</code> represents
     */
    public static Object getValueClass(char ch) {
        switch (ch) {
        case '@':
            return PatternOptionBuilder.OBJECT_VALUE;
        case ':':
            return PatternOptionBuilder.STRING_VALUE;
        case '%':
            return PatternOptionBuilder.NUMBER_VALUE;
        case '+':
            return PatternOptionBuilder.CLASS_VALUE;
        case '#':
            return PatternOptionBuilder.DATE_VALUE;
        case '<':
            return PatternOptionBuilder.EXISTING_FILE_VALUE;
        case '>':
            return PatternOptionBuilder.FILE_VALUE;
        case '*':
            return PatternOptionBuilder.FILES_VALUE;
        case '/':
            return PatternOptionBuilder.URL_VALUE;
        }

        return null;
    }

    /**
     * Returns whether <code>ch</code> is a value code, i.e. whether it
     * represents a class in a pattern.
     * 
     * @param ch
     *            the specified character
     * @return true if <code>ch</code> is a value code, otherwise false.
     */
    public static boolean isValueCode(char ch) {
        return ch == '@' || ch == ':' || ch == '%' || ch == '+' || ch == '#'
                || ch == '<' || ch == '>' || ch == '*' || ch == '/'
                || ch == '!';
    }

    /**
     * Returns the {@link Options} instance represented by <code>pattern</code>.
     * 
     * @param pattern
     *            the pattern string
     * @return The {@link Options} instance
     */
    public static Options parsePattern(String pattern) {
        char opt = ' ';
        boolean required = false;
        Object type = null;

        Options options = new Options();

        for (int i = 0; i < pattern.length(); i++) {
            char ch = pattern.charAt(i);

            // a value code comes after an option and specifies
            // details about it
            if (!isValueCode(ch)) {
                if (opt != ' ') {
                    OptionBuilder.hasArg(type != null);
                    OptionBuilder.isRequired(required);
                    OptionBuilder.withType(type);

                    // we have a previous one to deal with
                    options.addOption(OptionBuilder.create(opt));
                    required = false;
                    type = null;
                    opt = ' ';
                }

                opt = ch;
            } else if (ch == '!') {
                required = true;
            } else {
                type = getValueClass(ch);
            }
        }

        if (opt != ' ') {
            OptionBuilder.hasArg(type != null);
            OptionBuilder.isRequired(required);
            OptionBuilder.withType(type);

            // we have a final one to deal with
            options.addOption(OptionBuilder.create(opt));
        }

        return options;
    }
}

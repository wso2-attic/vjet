/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: ParserHelper.java,v 1.5 2008/09/17 03:09:54 patrick Exp $
 *
 * Copyright (c) 2006-2007 Wipro Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Wipro 
 * Technologies.
 */
package org.ebayopensource.dsf.jstojava.cml.vjetv.util;

import org.ebayopensource.dsf.jstojava.cml.vjetv.parser.Options;
import org.ebayopensource.dsf.jstojava.cml.vjetv.parser.VjetVFormatter;

/**
 * This class is used to support utility function to commad line parser.
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class ParserHelper {

    /**
     * Export type of txt
     */
    public static final String TXT = "txt";

    /**
     * Export type of xml
     */
    public static final String XML = "xml";

    /**
     * Export type of html
     */
    public static final String HTML = "html";

    /**
     * Export type of pdf
     */
    public static final String PDF = "pdf";

    /**
     * Short single-character name of the option.
     */
    private static final int SHORT_OPTION = 0;

    /**
     * longOpt Long multi-character name of the option
     */
    private static final int LONG_OPTION = 1;

    /**
     * description Self-documenting description
     */
    private static final int OPTION_DESC = 2;

    /**
     * User age message
     */
    private static final String USAGE_HIT = "Please input the right options. \n";

    /**
     * create command line options for each function.
     * 
     * @param options
     *            String[][] first dimension is the option, and second dimension
     *            is the detail of option. For example, String[][] options = new
     *            String[][] { new String[] { "a", "aba", "description" }, new
     *            String[] { "b", "bbb", "description" }.
     * @return Options
     */
    public static Options createSubOptions(String[][] options) {
        return createSubOptions(options, true);
    }

    /**
     * create command line options for each function.
     * 
     * @param options
     *            String[][] first dimension is the option, and second dimension
     *            is the detail of option. For example, String[][] options = new
     *            String[][] { new String[] { "a", "aba", "description" }, new
     *            String[] { "b", "bbb", "description" }.
     * @param hasArg
     *            Has argument behind options
     * @return Options
     */
    public static Options createSubOptions(String[][] options, boolean hasArg) {
        Options opts = new Options();
        for (int i = 0; i < options.length; i++) {
            opts.addOption(options[i][SHORT_OPTION], options[i][LONG_OPTION],
                    hasArg, options[i][OPTION_DESC]);
        }
        return opts;
    }

    /**
     * print options help for end user if end user enter wrong command line.
     * 
     * @param options
     *            target options to be print.
     */
    public static void printOptionsHelp(Options options, String message) {
        VjetVFormatter formatter = new VjetVFormatter();
        if (message == null) {
            message = USAGE_HIT;
        }
        formatter.printHelp(message, options, true);
    }

    /**
     * Check the given string is null or "";
     * 
     * @param str
     * @return True is that the given string is null or "", false otherwise.
     */
    public static boolean isEmpty(String str) {
        return str == null || str.equals(ParserHelper.STRING_EMPTY);
    }

    /**
     * String represents the empty string "".
     */
    public static final String STRING_EMPTY = "";

    /**
     * exit with problem.
     */
    public static final int ERROR_EXIT = 1;

    /**
     * Exit current system
     */
    public static void exitSystem() {
        System.exit(ParserHelper.ERROR_EXIT);
    }

    /**
     * Exit current system
     * 
     * @param message
     *            {@link String}
     */
    public static void exitSystem(String message) {
        System.out.println(message);
        System.exit(ParserHelper.ERROR_EXIT);
    }

}

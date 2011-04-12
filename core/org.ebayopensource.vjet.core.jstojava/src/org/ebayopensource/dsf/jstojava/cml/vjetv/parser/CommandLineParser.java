/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: CommandLineParser.java, Jan 25, 2010, 10:32:09 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jstojava.cml.vjetv.parser;

/**
 * Class/Interface description
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public interface CommandLineParser {
    /**
     * Parse the arguments according to the specified options.
     * 
     * @param options
     *            the specified Options
     * @param arguments
     *            the command line arguments
     * @return the list of atomic option and value tokens
     * 
     * @throws ParseException
     *             if there are any problems encountered while parsing the
     *             command line tokens.
     */
    CommandLine parse(Options options, String[] arguments)
            throws ParseException;

    /**
     * Parse the arguments according to the specified options.
     * 
     * @param options
     *            the specified Options
     * @param arguments
     *            the command line arguments
     * @param stopAtNonOption
     *            specifies whether to continue parsing the arguments if a non
     *            option is encountered.
     * 
     * @return the list of atomic option and value tokens
     * @throws ParseException
     *             if there are any problems encountered while parsing the
     *             command line tokens.
     */
    CommandLine parse(Options options, String[] arguments,
            boolean stopAtNonOption) throws ParseException;
}

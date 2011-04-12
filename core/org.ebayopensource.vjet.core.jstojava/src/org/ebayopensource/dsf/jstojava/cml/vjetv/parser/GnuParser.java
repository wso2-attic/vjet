/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: GnuParser.java, Jan 25, 2010, 10:33:19 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jstojava.cml.vjetv.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Class/Interface description
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class GnuParser {
    /**
     * This flatten method does so using the following rules:
     * <ol>
     * <li>If an {@link Option} exists for the first character of the
     * <code>arguments</code> entry <b>AND</b> an {@link Option} does not
     * exist for the whole <code>argument</code> then add the first character
     * as an option to the processed tokens list e.g. "-D" and add the rest of
     * the entry to the also.</li>
     * <li>Otherwise just add the token to the processed tokens list.</li>
     * </ol>
     * 
     * @param options
     *            The Options to parse the arguments by.
     * @param arguments
     *            The arguments that have to be flattened.
     * @param stopAtNonOption
     *            specifies whether to stop flattening when a non option has
     *            been encountered
     * @return a String array of the flattened arguments
     */
    protected String[] flatten(Options options, String[] arguments,
            boolean stopAtNonOption) {
        List tokens = new ArrayList();

        boolean eatTheRest = false;

        for (int i = 0; i < arguments.length; i++) {
            String arg = arguments[i];

            if ("--".equals(arg)) {
                eatTheRest = true;
                tokens.add("--");
            } else if ("-".equals(arg)) {
                tokens.add("-");
            } else if (arg.startsWith("-")) {
                String opt = Util.stripLeadingHyphens(arg);

                if (options.hasOption(opt)) {
                    tokens.add(arg);
                } else {
                    if (opt.indexOf('=') != -1
                            && options.hasOption(opt.substring(0, opt
                                    .indexOf('=')))) {
                        // the format is --foo=value or -foo=value
                        tokens.add(arg.substring(0, arg.indexOf('='))); // --foo
                        tokens.add(arg.substring(arg.indexOf('=') + 1)); // value
                    } else if (options.hasOption(arg.substring(0, 2))) {
                        // the format is a special properties option
                        // (-Dproperty=value)
                        tokens.add(arg.substring(0, 2)); // -D
                        tokens.add(arg.substring(2)); // property=value
                    } else {
                        eatTheRest = stopAtNonOption;
                        tokens.add(arg);
                    }
                }
            } else {
                tokens.add(arg);
            }

            if (eatTheRest) {
                for (i++; i < arguments.length; i++) {
                    tokens.add(arguments[i]);
                }
            }
        }

        return (String[]) tokens.toArray(new String[tokens.size()]);
    }
}

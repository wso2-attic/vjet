/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: VjetVFormatter.java, Jan 19, 2010, 9:32:16 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jstojava.cml.vjetv.parser;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Class/Interface description
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class VjetVFormatter extends HelpFormatter {

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.cli.HelpFormatter#printUsage(java.io.PrintWriter,
     *      int, java.lang.String, org.apache.commons.cli.Options)
     */
    @Override
    public void printUsage(PrintWriter pw, int width, String app,
            Options options) {
        // initialise the string buffer
        StringBuffer buff = new StringBuffer(defaultSyntaxPrefix).append(app)
                .append(" ");

        // create a list for processed option groups
        final Collection processedGroups = new ArrayList();

        // temp variable
        Option option;

        List optList = new ArrayList(options.getOptions());
        // iterate over the options
        for (Iterator i = optList.iterator(); i.hasNext();) {
            // get the next Option
            option = (Option) i.next();

            // check if the option is part of an OptionGroup
            OptionGroup group = options.getOptionGroup(option);

            // if the option is part of a group
            if (group != null) {
                // and if the group has not already been processed
                if (!processedGroups.contains(group)) {
                    // add the group to the processed list
                    processedGroups.add(group);

                    // add the usage clause
                    appendOptionGroup(buff, group);
                }

                // otherwise the option was displayed in the group
                // previously so ignore it.
            }

            // if the Option is not part of an OptionGroup
            else {
                appendOption(buff, option, option.isRequired());
            }

            if (i.hasNext()) {
                buff.append(" ");
            }
        }

        // call printWrapped
        printWrapped(pw, width, buff.toString().indexOf(' ') + 1, buff
                .toString());
    }

    /**
     * Appends the usage clause for an OptionGroup to a StringBuffer. The clause
     * is wrapped in square brackets if the group is required. The display of
     * the options is handled by appendOption
     * 
     * @param buff
     *            the StringBuffer to append to
     * @param group
     *            the group to append
     * @see #appendOption(StringBuffer,Option,boolean)
     */
    private void appendOptionGroup(final StringBuffer buff,
            final OptionGroup group) {
        if (!group.isRequired()) {
            buff.append("[");
        }

        List optList = new ArrayList(group.getOptions());
        // for each option in the OptionGroup
        for (Iterator i = optList.iterator(); i.hasNext();) {
            // whether the option is required or not is handled at group level
            appendOption(buff, (Option) i.next(), true);

            if (i.hasNext()) {
                buff.append(" | ");
            }
        }

        if (!group.isRequired()) {
            buff.append("]");
        }
    }

    /**
     * Appends the usage clause for an Option to a StringBuffer.
     * 
     * @param buff
     *            the StringBuffer to append to
     * @param option
     *            the Option to append
     * @param required
     *            whether the Option is required or not
     */
    private static void appendOption(final StringBuffer buff,
            final Option option, final boolean required) {
        if (!required) {
            buff.append("[");
        }

        if (option.getOpt() != null) {
            buff.append("-").append(option.getOpt());
        } else {
            buff.append("--").append(option.getLongOpt());
        }

        // if the Option has a value
        if (option.hasArg() && option.hasArgName()) {
            buff.append(" <").append(option.getArgName()).append(">");
        }

        // if the Option is not a required option
        if (!required) {
            buff.append("]");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.cli.HelpFormatter#renderOptions(java.lang.StringBuffer,
     *      int, org.apache.commons.cli.Options, int, int)
     */
    @Override
    protected StringBuffer renderOptions(StringBuffer sb, int width,
            Options options, int leftPad, int descPad) {
        final String lpad = createPadding(leftPad);
        final String dpad = createPadding(descPad);

        // first create list containing only <lpad>-a,--aaa where
        // -a is opt and --aaa is long opt; in parallel look for
        // the longest opt string this list will be then used to
        // sort options ascending
        int max = 0;
        StringBuffer optBuf;
        List prefixList = new ArrayList();

        List optList = new ArrayList(options.getOptions());

        for (Iterator i = optList.iterator(); i.hasNext();) {
            Option option = (Option) i.next();
            optBuf = new StringBuffer(8);

            if (option.getOpt() == null) {
                optBuf.append(lpad).append("   " + defaultLongOptPrefix)
                        .append(option.getLongOpt());
            } else {
                optBuf.append(lpad).append(defaultOptPrefix).append(
                        option.getOpt());

                if (option.hasLongOpt()) {
                    optBuf.append(',').append(defaultLongOptPrefix).append(
                            option.getLongOpt());
                }
            }

            if (option.hasArg()) {
                if (option.hasArgName()) {
                    optBuf.append(" <").append(option.getArgName()).append(">");
                } else {
                    optBuf.append(' ');
                }
            }

            prefixList.add(optBuf);
            max = (optBuf.length() > max) ? optBuf.length() : max;
        }

        int x = 0;

        for (Iterator i = optList.iterator(); i.hasNext();) {
            Option option = (Option) i.next();
            optBuf = new StringBuffer(prefixList.get(x++).toString());

            if (optBuf.length() < max) {
                optBuf.append(createPadding(max - optBuf.length()));
            }

            optBuf.append(dpad);

            int nextLineTabStop = max + descPad;

            if (option.getDescription() != null) {
                optBuf.append(option.getDescription());
            }

            renderWrappedText(sb, width, nextLineTabStop, optBuf.toString());

            if (i.hasNext()) {
                sb.append(defaultNewLine);
            }
        }

        return sb;
    }
}

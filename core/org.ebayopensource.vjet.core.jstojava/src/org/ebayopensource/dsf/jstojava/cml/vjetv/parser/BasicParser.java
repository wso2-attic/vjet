/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: BasicParser.java, Jan 25, 2010, 10:30:40 PM, liama. Exp$
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
public class BasicParser extends Parser {
    /**
     * <p>
     * A simple implementation of {@link Parser}'s abstract
     * {@link Parser#flatten(Options, String[], boolean) flatten} method.
     * </p>
     * 
     * <p>
     * <b>Note:</b> <code>options</code> and <code>stopAtNonOption</code>
     * are not used in this <code>flatten</code> method.
     * </p>
     * 
     * @param options
     *            The command line {@link Options}
     * @param arguments
     *            The command line arguments to be parsed
     * @param stopAtNonOption
     *            Specifies whether to stop flattening when an non option is
     *            found.
     * @return The <code>arguments</code> String array.
     */
    @Override
    protected String[] flatten(Options options, String[] arguments,
            boolean stopAtNonOption) {
        // just echo the arguments
        return arguments;
    }
}

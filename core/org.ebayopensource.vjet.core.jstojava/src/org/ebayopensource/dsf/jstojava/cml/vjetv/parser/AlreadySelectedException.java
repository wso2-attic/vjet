/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.cml.vjetv.parser;

/**
 * Class/Interface description
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class AlreadySelectedException extends ParseException {
    /** The option group selected. */
    private OptionGroup group;

    /** The option that triggered the exception. */
    private Option option;

    /**
     * Construct a new <code>AlreadySelectedException</code> with the
     * specified detail message.
     * 
     * @param message
     *            the detail message
     */
    public AlreadySelectedException(String message) {
        super(message);
    }

    /**
     * Construct a new <code>AlreadySelectedException</code> for the specified
     * option group.
     * 
     * @param group
     *            the option group already selected
     * @param option
     *            the option that triggered the exception
     * @since 1.2
     */
    public AlreadySelectedException(OptionGroup group, Option option) {
        this("The option '" + option.getKey()
                + "' was specified but an option from this group "
                + "has already been selected: '" + group.getSelected() + "'");
        this.group = group;
        this.option = option;
    }

    /**
     * Returns the option group where another option has been selected.
     * 
     * @return the related option group
     * @since 1.2
     */
    public OptionGroup getOptionGroup() {
        return group;
    }

    /**
     * Returns the option that was added to the group and triggered the
     * exception.
     * 
     * @return the related option
     * @since 1.2
     */
    public Option getOption() {
        return option;
    }
}

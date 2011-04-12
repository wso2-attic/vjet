/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: OptionValidator.java, Jan 25, 2010, 10:41:14 PM, liama. Exp$
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
public class OptionValidator {
    /**
     * Validates whether <code>opt</code> is a permissable Option shortOpt.
     * The rules that specify if the <code>opt</code> is valid are:
     * 
     * <ul>
     * <li><code>opt</code> is not NULL</li>
     * <li>a single character <code>opt</code> that is either ' '(special
     * case), '?', '@' or a letter</li>
     * <li>a multi character <code>opt</code> that only contains letters.</li>
     * </ul>
     * 
     * @param opt
     *            The option string to validate
     * @throws IllegalArgumentException
     *             if the Option is not valid.
     */
    static void validateOption(String opt) throws IllegalArgumentException {
        // check that opt is not NULL
        if (opt == null) {
            return;
        }

        // handle the single character opt
        else if (opt.length() == 1) {
            char ch = opt.charAt(0);

            if (!isValidOpt(ch)) {
                throw new IllegalArgumentException("illegal option value '"
                        + ch + "'");
            }
        }

        // handle the multi character opt
        else {
            char[] chars = opt.toCharArray();

            for (int i = 0; i < chars.length; i++) {
                if (!isValidChar(chars[i])) {
                    throw new IllegalArgumentException(
                            "opt contains illegal character value '" + chars[i]
                                    + "'");
                }
            }
        }
    }

    /**
     * Returns whether the specified character is a valid Option.
     * 
     * @param c
     *            the option to validate
     * @return true if <code>c</code> is a letter, ' ', '?' or '@', otherwise
     *         false.
     */
    private static boolean isValidOpt(char c) {
        return isValidChar(c) || c == ' ' || c == '?' || c == '@';
    }

    /**
     * Returns whether the specified character is a valid character.
     * 
     * @param c
     *            the character to validate
     * @return true if <code>c</code> is a letter.
     */
    private static boolean isValidChar(char c) {
        return Character.isJavaIdentifierPart(c);
    }
}

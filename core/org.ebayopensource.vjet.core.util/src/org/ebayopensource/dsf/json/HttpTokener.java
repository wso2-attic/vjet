/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.json;

import java.text.ParseException;

/**
 * The HTTPTokener extends the JSONTokener to provide additional methods
 * for the parsing of HTTP headers.
 * .org
 * @version 0.1
 */
public class HttpTokener extends JsonTokener {

    /**
     * Construct an XMLTokener from a string.
     * @param s A source string.
     */
    public HttpTokener(String s) {
        super(s);
    }


    /**
     * Get the next token or string. This is used in parsing HTTP headers.
     * @throws ParseException
     * @return A String.
     */
    public String nextToken() throws ParseException {
        char c;
        char q;
        StringBuffer sb = new StringBuffer();
        do {
            c = next();
        } while (Character.isWhitespace(c));
        if (c == '"' || c == '\'') {
            q = c;
            while (true) {
                c = next();
                if (c < ' ') {
                    throw syntaxError("Unterminated string.");
                }
                if (c == q) {
                    return sb.toString();
                }
                sb.append(c);
            }
        } 
        while (true) {
            if (c == 0 || Character.isWhitespace(c)) {
                return sb.toString();
            }
            sb.append(c);
            c = next();
        }
    }
}

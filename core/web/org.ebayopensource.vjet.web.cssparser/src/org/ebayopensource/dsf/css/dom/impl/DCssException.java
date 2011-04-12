/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*
 * DOMExceptionImpl.java
 *
 * Modified from Open-Source CSS Parser (Steady State Software) under
 * GNU Lesser General Public License.
 * 
 */

package org.ebayopensource.dsf.css.dom.impl;

import java.util.Locale;
import java.util.ResourceBundle;

import org.w3c.dom.DOMException;

public class DCssException extends DOMException {

	private static final long serialVersionUID = 1L;
	public static final int SYNTAX_ERROR = 0;
    public static final int ARRAY_OUT_OF_BOUNDS = 1;
    public static final int READ_ONLY_STYLE_SHEET = 2;
    public static final int EXPECTING_UNKNOWN_RULE = 3;
    public static final int EXPECTING_STYLE_RULE = 4;
    public static final int EXPECTING_CHARSET_RULE = 5;
    public static final int EXPECTING_IMPORT_RULE = 6;
    public static final int EXPECTING_MEDIA_RULE = 7;
    public static final int EXPECTING_FONT_FACE_RULE = 8;
    public static final int EXPECTING_PAGE_RULE = 9;
    public static final int FLOAT_ERROR = 10;
    public static final int STRING_ERROR = 11;
    public static final int COUNTER_ERROR = 12;
    public static final int RECT_ERROR = 13;
    public static final int RGBCOLOR_ERROR = 14;
    public static final int CHARSET_NOT_FIRST = 15;
    public static final int CHARSET_NOT_UNIQUE = 16;
    public static final int IMPORT_NOT_FIRST = 17;
    public static final int NOT_FOUND = 18;
    public static final int NOT_IMPLEMENTED = 19;
    public static final int RGBACOLOR_ERROR = 20;
    public static final int HSLCOLOR_ERROR = 21;
    public static final int HSLACOLOR_ERROR = 22;

    private static ResourceBundle _exceptionResource =
        ResourceBundle.getBundle(
            "org.ebayopensource.dsf.css.parser.ExceptionResource",
            Locale.getDefault());

    public DCssException(short code, int messageKey) {
        super(code, _exceptionResource.getString(keyString(messageKey)));
    }

    public DCssException(int code, int messageKey) {
        super((short) code, _exceptionResource.getString(keyString(messageKey)));
    }

    public DCssException(short code, int messageKey, String info) {
        super(code, info == null 
        	? _exceptionResource.getString(keyString(messageKey)) 
        	: _exceptionResource.getString(keyString(messageKey)) + "\n" + info);
    }

    private static String keyString(int key) {
        return "s" + String.valueOf(key);
    }
}

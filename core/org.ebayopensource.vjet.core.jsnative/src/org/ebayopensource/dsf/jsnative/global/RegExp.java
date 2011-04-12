/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative.global;

import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.Constructor;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsSupport;
import org.ebayopensource.dsf.jsnative.anno.JsVersion;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.ebayopensource.dsf.jsnative.anno.Static;


/**
 * 
 * Represents JavaScript RegExp native object
 *
 */
@JsSupport( {JsVersion.MOZILLA_ONE_DOT_TWO, JsVersion.JSCRIPT_THREE_DOT_ZERO})
public interface RegExp extends Object {
	
	
	@Constructor void RegExp();
	
	@Constructor void RegExp(String pattern);
	
	@Constructor void RegExp(String pattern, String flags);
	
	/**
	 * String corresponding to the 1st expression of a match.
	 */
	@Property @Static String get$1();
	/**
	 * String corresponding to the 2nd expression of a match.
	 */
	@Property @Static String get$2();
	/**
	 * String corresponding to the 3rd expression of a match.
	 */
	@Property @Static String get$3();
	/**
	 * String corresponding to the 4th expression of a match.
	 */
	@Property @Static String get$4();
	/**
	 * String corresponding to the 5th expression of a match.
	 */
	@Property @Static String get$5();
	/**
	 * String corresponding to the 6th expression of a match.
	 */
	@Property @Static String get$6();
	/**
	 * String corresponding to the 7th expression of a match.
	 */
	@Property @Static String get$7();
	/**
	 * String corresponding to the 8th expression of a match.
	 */
	@Property @Static String get$8();
	/**
	 * String corresponding to the 9th expression of a match.
	 */
	@Property @Static String get$9();
	/**
	 * String corresponding to the 10th expression of a match.
	 */
	@Property @Static String get$10();
	
	/**
	 * Whether or not the "g" flag is used with the regular expression. 
	 */
	@Property boolean getGlobal();
	
	/**
	 * Whether or not the "i" flag is used with the regular expression. 
	 */
	@Property boolean getIgnoreCase();
	
	/**
	 * Character position of the start of the first match.
	 */
	@BrowserSupport({BrowserType.IE_6P})
	@Property Number getIndex();
	
	/**
	 * String containing the most recently found regular expression match.
	 */
	@BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P})
	@Property @Static String getInput();
	
	/**
	 * A read/write integer property that specifies the index at which to start the next match. 
	 */
	@Property Number getLastIndex();
	
	/**
	 * String containing the last characters of the last regular expression match.
	 */
	@BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P})
	@Property @Static String getLastMatch();
	
	/**
	 * String containing the most recent parenthesized match.
	 */
	@BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P})
	@Property @Static String getLastParen();
	
	/**
	 * String containing the characters before the most recently found regular expression match.
	 */
	@BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P})
	@Property @Static String getLeftContext();
	
	/**
	 * Whether or not the "m" (multiline) flag is used with the regular expression.
	 */
	@BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P})
	@Property boolean getMultiline();
	
	/**
	 * String containing the characters following the most recently 
	 * found regular expression match.
	 */
	@BrowserSupport({BrowserType.IE_6P, BrowserType.FIREFOX_1P})
	@Property @Static String getRightContext();
	
	/**
	 * A read-only property that contains the text of the pattern, 
	 * excluding the forward slashes. 
	 */
	@Property String getSource();
	
	/**
	 * Creates a new regular expression by compiling the specified 
	 * regular expression with the specified flags.
	 */
	@Function RegExp compile(Object expr, Object flags);
	
	/**
	 * Executes the search for a match in a specified string. 
	 * Returns a result array. 
	 */
	@Function Array exec(String str);
	
	/**
	 * Executes the search for a match between a regular expression 
	 * and a specified string.
	 */
	@Function boolean test(String str);	
}

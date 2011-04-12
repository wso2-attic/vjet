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
import org.ebayopensource.dsf.jsnative.anno.OverLoadFunc;
import org.ebayopensource.dsf.jsnative.anno.Property;


/**
 * 
 * Represents JavaScript Array native object
 *
 */
@JsSupport( {JsVersion.MOZILLA_ONE_DOT_ONE, 
		JsVersion.JSCRIPT_TWO_DOT_ZERO})
public interface Array extends Object {
	
	@Constructor void Array();
	
	@Constructor void Array(Number size);
	
	@Constructor void Array(Object ...elements);
	
	/**
	 * Zero-based index number 
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.OPERA_7P})
	@Property Number getIndex();
	
	/**
	 * String in a RegExp match.
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P, BrowserType.OPERA_7P})
	@Property Number getInput();
	
	/**
	 * The number of elements in an array. 
	 */
	@Property Number getLength();
	
	/**
	 * Returns a new array comprised of this array 
	 * joined with other array(s) and/or value(s). 
	 */
	@Function Array concat(Array ...arrays);
	
	/**
	 * Returns true if every element in an array meets the specified criteria.
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P})
	@OverLoadFunc boolean every(org.ebayopensource.dsf.jsnative.global.Function callback) ; 
	/**
	 * Returns true if every element in an array meets the specified criteria.
	 */
	@OverLoadFunc boolean every(org.ebayopensource.dsf.jsnative.global.Function callback, Object thisObject);
	
	/**
	 * Creates a new array with all elements that meet the specified criteria.
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P})
	@OverLoadFunc Array filter(org.ebayopensource.dsf.jsnative.global.Function callback);
	/**
	 * Creates a new array with all elements that meet the specified criteria.
	 */
	@OverLoadFunc Array filter(org.ebayopensource.dsf.jsnative.global.Function callback, Object thisObject);
	
	/**
	 * Executes the specified function once for each element in an array.
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P})
	@OverLoadFunc Array forEach(org.ebayopensource.dsf.jsnative.global.Function callback);
	/**
	 * Executes the specified function once for each element in an array.
	 */
	@OverLoadFunc Array forEach(org.ebayopensource.dsf.jsnative.global.Function callback, Object thisObject);
	
	/**
	 * Returns the first index number at which the specified element 
	 * can be found in the array. 
	 * Returns -1 if the element is not present. 
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P})
	@OverLoadFunc Number indexOf(Object searchElement);
	/**
	 * Returns the first index number at which the specified element 
	 * can be found in the array. 
	 * Returns -1 if the element is not present. 
	 */
	@OverLoadFunc Number indexOf(Object searchElement, int fromIndex);
	
	/**
	 * Returns the first index number at which the specified element 
	 * can be found in the array. 
	 * Returns -1 if the element is not present. 
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P})
	@OverLoadFunc Number indexOf(Object searchElement, Number fromIndex);
	
	/**
	 * Joins all elements of an array into a string.
	 */
	@OverLoadFunc String join();
	
	/**
	 * Joins all elements of an array into a string.
	 * @param separator specifies the separator to be used
	 */
	@OverLoadFunc String join(String separator);
	
	/**
	 * Searches an array backwards starting from fromIndex and 
	 * returns the last index number at which the specified element 
	 * can be found in the array. Returns -1 if the element is not present. 
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P})
	@OverLoadFunc Number lastIndexOf(Object searchElement);
	
	/**
	 * Searches an array backwards starting from fromIndex and 
	 * returns the last index number at which the specified element 
	 * can be found in the array. Returns -1 if the element is not present. 
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P})
	@OverLoadFunc Number lastIndexOf(Object searchElement, Number fromIndex);
	
	/**
	 * Creates a new array with the results of 
	 * calling a provided function on every element in this array.
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P})
	@OverLoadFunc Array map(org.ebayopensource.dsf.jsnative.global.Function callback);
	
	/**
	 * Creates a new array with the results of 
	 * calling a provided function on every element in this array.
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P})
	@OverLoadFunc Array map(org.ebayopensource.dsf.jsnative.global.Function callback, Object thisObject);
	
	/**
	 * Removes the last element from an array and 
	 * returns that element. This method changes the length of the array. 
	 */
	@Function Object pop();
	
	/**
	 * Adds one or more elements to the end of an array and returns 
	 * the new length of the array. This method changes the length of the array. 
	 */
	@Function Number push(Object ...elements);
	
	/**
	 * reduce() method applies a function simultaneously against two values of the array 
	 * (from left-to-right) as to reduce it to a single value.
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P})
	@OverLoadFunc Object reduce(org.ebayopensource.dsf.jsnative.global.Function callback);
	/**
	 * reduce() method applies a function simultaneously against two values of the array 
	 * (from left-to-right) as to reduce it to a single value.
	 */
	@OverLoadFunc Object reduce(org.ebayopensource.dsf.jsnative.global.Function callback, Object thisObject);
	
	/**
	 * reduceRight() method applies a function simultaneously against two values of the array 
	 * (from right-to-left) as to reduce it to a single value.
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P})
	@OverLoadFunc Object reduceRight(org.ebayopensource.dsf.jsnative.global.Function callback);
	/**
	 * reduceRight() method applies a function simultaneously against two values of the array 
	 * (from right-to-left) as to reduce it to a single value.
	 */
	@OverLoadFunc Object reduceRight(org.ebayopensource.dsf.jsnative.global.Function callback, Object thisObject);
	
	/**
	 * Transposes the elements of an array: the first 
	 * array element becomes the last and the last becomes the first. 
	 */
	@Function Array reverse();
	
	/**
	 * Removes the first element from an array and returns that element. 
	 * This method changes the length of the array. 
	 */
	@Function Object shift();
	
	/**
	 * Returns a new array. 
	 */
	@Function Array slice();
	
	/**
	 * Extracts a section of an array and returns a new array.
	 * Selects all elements from the start position and to the end of the array  
	 * @param begin An integer that specifies where to start the selection
	 */
	@OverLoadFunc Array slice(Number begin);
	
	/**
	 * Extracts a section of an array and returns a new array.
	 * @param begin An integer that specifies where to start the selection
	 * @param end An integer that specifies where to end the selection.
	 */
	@OverLoadFunc Array slice(Number begin, Number end);
	
	/**
	 * Returns true if some element in the array passes 
	 * the test implemented by the provided function.
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P})
	@OverLoadFunc boolean some(org.ebayopensource.dsf.jsnative.global.Function searchElement);
	
	/**
	 * Returns true if some element in the array passes 
	 * the test implemented by the provided function.
	 */
	@BrowserSupport({BrowserType.FIREFOX_1P})
	@OverLoadFunc boolean some(org.ebayopensource.dsf.jsnative.global.Function searchElement, Object thisObject);
	
	/**
	 * Sorts the elements of an array.
	 */
	@OverLoadFunc Array sort();
	
	/**
	 * Sorts the elements of an array.
	 * @param compareFunction specifies the sort order.
	 */
	@OverLoadFunc Array sort(org.ebayopensource.dsf.jsnative.global.Function compareFunction);
	
	/**
	 * Returns the array with the specified elements inserted or removed.
	 */
	@OverLoadFunc Array splice(Number index);
	/**
	 * Returns the array with the specified elements inserted or removed.
	 */
	@OverLoadFunc Array splice(Number index, Number howMany);
	
	/**
	 * Returns the array with the specified elements inserted or removed.
	 */
	@OverLoadFunc Array splice(Number index, Number howMany, Object ...elements);
	
	/**
	 * Adds one or more elements to the beginning of an array and 
	 * returns the new length of the array. 
	 */
	@Function Number unshift(Object ...elements);
	
	/**
	 * Answer the combined value of this array
	 */
	@Function Object valueOf();	
}

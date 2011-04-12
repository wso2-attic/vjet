/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.Property;

/**
 * The <code>CharacterData</code> interface extends <code>Node</code> with a
 * set of attributes and methods for accessing character data in the DOM. For
 * clarity this set is defined here rather than on each object that uses these
 * attributes and methods.
 * <p>
 * No DOM objects correspond directly to <code>CharacterData</code>, though
 * <code>Text</code> and others do inherit the interface from it. All offsets
 * in this interface start from 0.
 * <br>
 * See http://www.w3.org/TR/DOM-Level-2-Core/core.html#ID-FF21A306
 */
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface CharacterData extends Node {

	/**
	 * The character data of the node that implements this interface. The DOM 
	 * implementation may not put arbitrary limits on the amount of data 
	 * that may be stored in a <code>CharacterData</code> node. However, 
	 * implementation limits may mean that the entirety of a node's data may 
	 * not fit into a single <code>DOMString</code>. In such cases, the user 
	 * may call <code>substringData</code> to retrieve the data in 
	 * appropriately sized pieces.
	 * @exception DOMException
	 *   DOMSTRING_SIZE_ERR: Raised when it would return more characters than 
	 *   fit in a <code>DOMString</code> variable on the implementation 
	 *   platform.
	 */
	@Property String getData();

	/**
	 * The character data of the node that implements this interface. The DOM 
	 * implementation may not put arbitrary limits on the amount of data 
	 * that may be stored in a <code>CharacterData</code> node. However, 
	 * implementation limits may mean that the entirety of a node's data may 
	 * not fit into a single <code>DOMString</code>. In such cases, the user 
	 * may call <code>substringData</code> to retrieve the data in 
	 * appropriately sized pieces.
	 * @exception DOMException
	 *   NO_MODIFICATION_ALLOWED_ERR: Raised when the node is readonly.
	 */
	@Property void setData(String data);

	/**
	 * The number of 16-bit units that are available through <code>data</code> 
	 * and the <code>substringData</code> method below. This may have the 
	 * value zero, i.e., <code>CharacterData</code> nodes may be empty.
	 */
	@Property int getLength();

	/**
	 * Extracts a range of data from the node.
	 * @param offset Start offset of substring to extract.
	 * @param count The number of 16-bit units to extract.
	 * @return The specified substring. If the sum of <code>offset</code> and 
	 *   <code>count</code> exceeds the <code>length</code>, then all 16-bit 
	 *   units to the end of the data are returned.
	 * @exception DOMException
	 *   INDEX_SIZE_ERR: Raised if the specified <code>offset</code> is 
	 *   negative or greater than the number of 16-bit units in 
	 *   <code>data</code>, or if the specified <code>count</code> is 
	 *   negative.
	 *   <br>DOMSTRING_SIZE_ERR: Raised if the specified range of text does 
	 *   not fit into a <code>DOMString</code>.
	 */
	@Function String substringData(int offset, int count);

	/**
	 * Append the string to the end of the character data of the node. Upon 
	 * success, <code>data</code> provides access to the concatenation of 
	 * <code>data</code> and the <code>DOMString</code> specified.
	 * @param arg The <code>DOMString</code> to append.
	 * @exception DOMException
	 *   NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
	 */
	@Function void appendData(String arg);

	/**
	 * Insert a string at the specified 16-bit unit offset.
	 * @param offset The character offset at which to insert.
	 * @param arg The <code>DOMString</code> to insert.
	 * @exception DOMException
	 *   INDEX_SIZE_ERR: Raised if the specified <code>offset</code> is 
	 *   negative or greater than the number of 16-bit units in 
	 *   <code>data</code>.
	 *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
	 */
	@Function void insertData(int offset, String arg);

	/**
	 * Remove a range of 16-bit units from the node. Upon success, 
	 * <code>data</code> and <code>length</code> reflect the change.
	 * @param offset The offset from which to start removing.
	 * @param count The number of 16-bit units to delete. If the sum of 
	 *   <code>offset</code> and <code>count</code> exceeds 
	 *   <code>length</code> then all 16-bit units from <code>offset</code> 
	 *   to the end of the data are deleted.
	 * @exception DOMException
	 *   INDEX_SIZE_ERR: Raised if the specified <code>offset</code> is 
	 *   negative or greater than the number of 16-bit units in 
	 *   <code>data</code>, or if the specified <code>count</code> is 
	 *   negative.
	 *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
	 */
	@Function void deleteData(int offset, int count);

	/**
	 * Replace the characters starting at the specified 16-bit unit offset 
	 * with the specified string.
	 * @param offset The offset from which to start replacing.
	 * @param count The number of 16-bit units to replace. If the sum of 
	 *   <code>offset</code> and <code>count</code> exceeds 
	 *   <code>length</code>, then all 16-bit units to the end of the data 
	 *   are replaced; (i.e., the effect is the same as a <code>remove</code>
	 *    method call with the same range, followed by an <code>append</code>
	 *    method invocation).
	 * @param arg The <code>DOMString</code> with which the range must be 
	 *   replaced.
	 * @exception DOMException
	 *   INDEX_SIZE_ERR: Raised if the specified <code>offset</code> is 
	 *   negative or greater than the number of 16-bit units in 
	 *   <code>data</code>, or if the specified <code>count</code> is 
	 *   negative.
	 *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
	 */
	@Function void replaceData(int offset, int count, String arg);

}

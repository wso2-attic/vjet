/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import org.w3c.dom.CharacterData;
import org.w3c.dom.DOMException;

/**
 * The <code>CharacterData</code> interface extends Node with a set of 
 * attributes and methods for accessing character data in the DOM. For 
 * clarity this set is defined here rather than on each object that uses 
 * these attributes and methods. No DOM objects correspond directly to 
 * <code>CharacterData</code>, though <code>Text</code> and others do 
 * inherit the interface from it. All <code>offsets</code> in this interface 
 * start from <code>0</code>.
 * <p>As explained in the <code>DOMString</code> interface, text strings in 
 * the DOM are represented in UTF-16, i.e. as a sequence of 16-bit units. In 
 * the following, the term 16-bit units is used whenever necessary to 
 * indicate that indexing on CharacterData is done in 16-bit units.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 */
public abstract class DCharacterData extends DNode implements CharacterData {
	//
	// Constructor(s)
	//
	DCharacterData(final DDocument document, final String data) {
		super(document);
		m_nodeValue = data;
	}

	//
	// Satisfy ICharacterData
	//
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
	public String getData() throws DOMException {
		return m_nodeValue;
	}

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
	public void setData(final String data) throws DOMException {
		m_nodeValue = data;
	}

	/**
	 * The number of 16-bit units that are available through <code>data</code> 
	 * and the <code>substringData</code> method below. This may have the 
	 * value zero, i.e., <code>CharacterData</code> nodes may be empty.
	 */
	@Override
	public int getLength() {
		return m_nodeValue == null ? 0 : m_nodeValue.length();
	}

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
	public String substringData(
		final int offset, final int count) throws DOMException
	{
		return m_nodeValue.substring(offset, offset + count);
	}

	/**
	 * Append the string to the end of the character data of the node. Upon 
	 * success, <code>data</code> provides access to the concatenation of 
	 * <code>data</code> and the <code>DOMString</code> specified.
	 * @param arg The <code>DOMString</code> to append.
	 * @exception DOMException
	 *   NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
	 */
	public void appendData(final String data) throws DOMException {
		if (m_nodeValue == null) {
			m_nodeValue = data;
			return;
		}

		m_nodeValue += data;
	}

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
	public void insertData(
		final int offset, final String middle) throws DOMException
	{
		final String first = m_nodeValue.substring(0, offset);
		final String last = m_nodeValue.substring(offset);
		m_nodeValue = first + middle + last;
	}

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
	public void deleteData(
		final int offset, final int count) throws DOMException
	{
		final String first = m_nodeValue.substring(0, offset);
		final String last = m_nodeValue.substring(offset + count);
		m_nodeValue = first + last;
	}

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
	public void replaceData(
		final int offset, final int count, final String middle) throws DOMException
	{
		if (offset < 0 || offset > m_nodeValue.length()) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR, "offset of " +
				offset + " is invalid");
		}
		if (count < 0) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR, "count of " +
				count + " is invalid, must not be negative");
		}
//		if (offset >= m_nodeValue.length()) {
//			// this is an append
//			m_nodeValue += middle;
//			return ;
//		}
		final String first = m_nodeValue.substring(0, offset);
		final int endIndex = offset + count;
		final String last= endIndex >= m_nodeValue.length()
			? "" : m_nodeValue.substring(endIndex);
		m_nodeValue = first + middle + last;
	}
	
	@Override
	public DCharacterData jif(final String jif) { 
		super.jif(jif) ;
		return this ;
	}
}

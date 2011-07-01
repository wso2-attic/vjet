/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.search;

import org.ebayopensource.dsf.jst.IJstNode;
import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.mod.core.search.SearchPattern;

/**
 * This class contains information about match element : match object, its
 * position and flags.
 * 
 * 
 * 
 */
public class VjoMatch {

	/**
	 * A constant expressing that offset and length of this match are specified
	 * in lines
	 */
	public static final int UNIT_LINE = 1;

	/**
	 * A constant expressing that offset and length of this match are specified
	 * in characters
	 */
	public static final int UNIT_CHARACTER = 2;

	private static final int IS_FILTERED = 1 << 2;

	private Object fElement;
	private int fOffset;
	private int fLength;
	private int fFlags;
	private int fAccuracy;
	private int fMatchRule;
	private boolean fIsWriteAccess;
	private boolean fIsReadAccess;
	private boolean fIsScriptdoc;
	
	private boolean fIsImport;
	private boolean fIsPublic;
	private boolean fIsStatic;
	
	
	private IJstNode jstNode;
	
	

	public IJstNode getJstNode() {
		return jstNode;
	}

	/**
	 * put original JstNode.
	 * @param jstNode
	 */
	public void setJstNode(IJstNode jstNode) {
		this.jstNode = jstNode;
	}

	/**
	 * Constructs a new Match object.
	 * 
	 * @param element
	 *            the element that contains the match
	 * @param unit
	 *            the unit offset and length are based on
	 * @param offset
	 *            the offset the match starts at
	 * @param length
	 *            the length of the match
	 */
	public VjoMatch(Object element, int unit, int offset, int length) {
		Assert.isTrue(unit == UNIT_CHARACTER || unit == UNIT_LINE);
		fElement = element;
		fOffset = offset;
		fLength = length;
		fFlags = unit;
	}

	/**
	 * Constructs a new Match object. The offset and length will be based on
	 * characters.
	 * 
	 * @param element
	 *            the element that contains the match
	 * @param offset
	 *            the offset the match starts at
	 * @param length
	 *            the length of the match
	 */
	public VjoMatch(Object element, int offset, int length) {
		this(element, UNIT_CHARACTER, offset, length);
	}

	/**
	 * Returns the offset of this match.
	 * 
	 * @return the offset
	 */
	public int getOffset() {
		return fOffset;
	}

	/**
	 * Sets the offset of this match.
	 * 
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(int offset) {
		fOffset = offset;
	}

	/**
	 * Returns the length of this match.
	 * 
	 * @return the length
	 */
	public int getLength() {
		return fLength;
	}

	/**
	 * Sets the length.
	 * 
	 * @param length
	 *            the length to set
	 */
	public void setLength(int length) {
		fLength = length;
	}

	/**
	 * Returns the element that contains this match. The element is used to
	 * group the match.
	 * 
	 * @return the element that contains this match
	 */
	public Object getElement() {
		return fElement;
	}

	/**
	 * Returns whether match length and offset are expressed in lines or
	 * characters.
	 * 
	 * @return either UNIT_LINE or UNIT_CHARACTER;
	 */
	public int getBaseUnit() {
		if ((fFlags & UNIT_LINE) != 0)
			return UNIT_LINE;
		return UNIT_CHARACTER;
	}

	/**
	 * Marks this match as filtered or not.
	 * 
	 * @param value
	 *            <code>true</code> if the match is filtered; otherwise
	 *            <code>false</code>
	 * 
	 * @since 3.1
	 */
	public void setFiltered(boolean value) {
		if (value) {
			fFlags |= IS_FILTERED;
		} else {
			fFlags &= (~IS_FILTERED);
		}
	}

	/**
	 * Returns whether this match is filtered or not.
	 * 
	 * @return <code>true<code> if the match is filtered;
	 *  otherwise <code>false</code>
	 *  
	 * @since 3.1
	 */
	public boolean isFiltered() {
		return (fFlags & IS_FILTERED) != 0;
	}

	

	/**
	 * Create instance of this class with parameters.
	 * 
	 * @param element match element.
	 * @param matchRule match rule. See more to {@link SearchPattern}
	 * @param offset element offset in source
	 * @param length element length in source
	 * @param accuracy accuracy 
	 * @param isReadAccess indicate read access either not
	 * @param isWriteAccess indicate write access either not.
	 * @param isJavadoc indicate javadoc either not. 
	 */
	public VjoMatch(Object element, int matchRule, int offset, int length,
			int accuracy, boolean isReadAccess, boolean isWriteAccess,
			boolean isJavadoc) {
		this(element, offset, length);
		fAccuracy = accuracy;
		fMatchRule = matchRule;
		fIsWriteAccess = isWriteAccess;
		fIsReadAccess = isReadAccess;
		fIsScriptdoc = isJavadoc;
	}

	/**
	 * Returns the value of the accuracy field.
	 * 
	 * @return the value of the accuracy field.
	 */
	public int getAccuracy() {
		return fAccuracy;
	}

	/**
	 * Returns true if this match has write access
	 * 
	 * @return true if this match has write access
	 */
	public boolean isWriteAccess() {
		return fIsWriteAccess;
	}

	/**
	 * Returns true if this match has read access
	 * 
	 * @return true if this match has read access
	 */
	public boolean isReadAccess() {
		return fIsReadAccess;
	}

	/**
	 * Returns true if this match is script document
	 * 
	 * @return true if this match is script document
	 */
	public boolean isScriptdoc() {
		return fIsScriptdoc;
	}

	/**
	 * Returns the value of the match rule field.
	 * 
	 * @return the value of the match rule field.
	 */
	public int getMatchRule() {
		return fMatchRule;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fAccuracy;
		result = prime * result + ((fElement == null) ? 0 : fElement.hashCode());
		result = prime * result + fFlags;
		result = prime * result + (fIsReadAccess ? 1231 : 1237);
		result = prime * result + (fIsScriptdoc ? 1231 : 1237);
		result = prime * result + (fIsWriteAccess ? 1231 : 1237);
		result = prime * result + (fIsImport ? 1231 : 1237);
		result = prime * result + fLength;
		result = prime * result + fMatchRule;
		result = prime * result + fOffset;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final VjoMatch other = (VjoMatch) obj;
		if (fAccuracy != other.fAccuracy)
			return false;
		if (fElement == null) {
			if (other.fElement != null)
				return false;
		} else if (!fElement.equals(other.fElement))
			return false;
		if (fFlags != other.fFlags)
			return false;
		if (fIsReadAccess != other.fIsReadAccess)
			return false;
		if (fIsScriptdoc != other.fIsScriptdoc)
			return false;
		if (fIsWriteAccess != other.fIsWriteAccess)
			return false;
		if (fLength != other.fLength)
			return false;
		if (fMatchRule != other.fMatchRule)
			return false;
		if (fOffset != other.fOffset)
			return false;
		if (fIsImport != other.fIsImport)
			return false;
		return true;
	}

	public boolean isIsImport() {
		return fIsImport;
	}

	public void setIsImport(boolean isImport) {
		fIsImport = isImport;
	}

	public void setIsScriptdoc(boolean isScriptdoc) {
		fIsScriptdoc = isScriptdoc;
	}

	public boolean isPublic() {
		return fIsPublic;
	}

	public void setIsPublic(boolean isPublic) {
		fIsPublic = isPublic;
	}

	public boolean isStatic() {
		return fIsStatic;
	}

	public void setIsStatic(boolean isStatic) {
		fIsStatic = isStatic;
	}
	
	
	

}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.ovs;


/**
 * IOptionValues manages an ordered map of ICaptionedValues 
 * and OtherInfos. ICaptionedValue is key and OtherInfo is value.
 */
public interface IOptionValues {
	
	/**
	 * Answer an list copy of internal ordered captionedValues.
	 * If no captionedValue exists, return immutable Collections.emptyList()  
	 * @return List<ICaptionedValue<T>>
	 */
//	List<ICaptionedValue> getCaptionedValues();
	
	/**
	 * Answer otherInfo associated with passed-in captionedValue.
	 * @param cv ICaptionedValue<T>
	 * @return OtherInfo
	 */
//	OtherInfo getOtherInfo(ICaptionedValue cv);
	
	/**
	 * Answers whether the internal map keySet contains passed-in captionedValue 
	 * @param cv ICaptionedValue<T>
	 * @return boolean
	 */
//	boolean contains(ICaptionedValue cv);
	
	/**
	 * Answers whether the internal map valueSet contains passed-in otherInfo 
	 * @param otherInfo OtherInfo
	 * @return boolean
	 */
	boolean contains(OtherInfo otherInfo);
	
	/**
	 * Answer the entry whose key equals the passed in captionedValue.
	 * Return null if not found.
	 * @param cv ICaptionedValue<T>
	 * @return Map.Entry<CaptionedValue<T>,OtherInfo>
	 */
//	Map.Entry<ICaptionedValue,OtherInfo> getEntry(ICaptionedValue cv);
	
	/**
	 * Remove the entry whose key equals the passed in captionedValue.
	 * Return the entry that is removed. If nothing removed, returns null.
	 * @param cv ICaptionedValue<T>
	 * @return Map.Entry<CaptionedValue<T>,OtherInfo>
	 */
//	Map.Entry<ICaptionedValue,OtherInfo> removeEntry(ICaptionedValue cv);
	
	/**
	 * Answer the size of the internal map
	 * @return int
	 */
	int size();
}

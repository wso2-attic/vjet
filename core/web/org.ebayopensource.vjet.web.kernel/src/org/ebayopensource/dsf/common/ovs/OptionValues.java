/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.ovs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.common.exceptions.DsfExceptionHelper;

/**
 * OptionValues maintains an ordered map of CaptionedValue<T> 
 * and OtherInfo instances. CaptionedValue<T> is key and OtherInfo is value.
 * Please be aware that:
 * 
 * 1. Ordering is based on the order captionedValues are added to the map.
 * 
 * 2. Adding same value/caption pair will get DsfRuntimeException. 
 *    Comparison is based on equal semantic of CaptionedValue<T>
 * 
 * 3. Adding same value with different caption is okay but not recommended. 
 * 
 * 4. OtherInfo can be set together with associated captionedValue or afterwards.
 * 
 * 5. getCaptionedValues() will return an ordered list of all captionedValues. 
 *    If none exists, it'll return immutable Collections.emptyList().
 * 
 * 6. OtherInfo can be null. It's used only by application.
 * 
 * 
 */
public final class OptionValues<T> implements IOptionValues {

	private static final String CAPTION_VALUE_IS_NULL = "cv is null";
	private static final String CAPTION_VALUES_IS_NULL = "cvs is null";
	private static final String NOT_FOUND = " is not found in the map";
	
	private Map<CaptionedValue<T>,OtherInfo> m_map = 
		new LinkedHashMap<CaptionedValue<T>,OtherInfo>();
	
	//
	// Satisfying IOptionValues
	//
	/**
	 * Answer an list copy of internal ordered captionedValues.
	 * If no captionedValue exists, return immutable Collections.emptyList()  
	 * @return List<ICaptionedValue>
	 */
	public List<ICaptionedValue> getGenericCaptionedValues(){
	
		if (getInternalMap().size() > 0){
			return new ArrayList<ICaptionedValue>(getInternalMap().keySet());
		}
		else {
			return Collections.emptyList();
		}
	}
	
	/**
	 * Answer otherInfo associated with passed-in captionedValue.
	 * @param cv ICaptionedValue
	 * @return OtherInfo
	 */
	public OtherInfo getOtherInfo(final CaptionedValue<T> cv){
		final Map.Entry<CaptionedValue<T>,OtherInfo> entry = getEntry(cv);
		if (entry == null){
			return null;
		}
	
		return entry.getValue();
	}
	
	/**
	 * Answers whether the internal map keySet contains passed-in captionedValue 
	 * @param cv ICaptionedValue
	 * @return boolean
	 */
	public boolean contains(final CaptionedValue<T> cv){
		if (cv == null){
			return false;
		}
		
		return getInternalMap().containsKey(cv);
	}
	
	/**
	 * Answers whether the internal map valueSet contains passed-in otherInfo 
	 * @param otherInfo OtherInfo
	 * @return boolean
	 */
	public boolean contains(final OtherInfo otherInfo){
		if (otherInfo == null){
			return false;
		}
		return getInternalMap().containsValue(otherInfo);
	}
	
	/**
	 * Answer the entry whose key equals the passed in captionedValue.
	 * Return null if not found.
	 * @param cv ICaptionedValue
	 * @return Map.Entry<CaptionedValue<T>,OtherInfo>
	 */
	public Map.Entry<CaptionedValue<T>,OtherInfo> getEntry(CaptionedValue<T> cv){
		if (cv == null){
			return null;
		}

		for (Map.Entry<CaptionedValue<T>,OtherInfo> entry : getInternalMap().entrySet()){
			final ICaptionedValue key = entry.getKey();
			if (key.equals(cv)){
				return entry;
			}
		}
	
		return null;
	}
	
	/**
	 * Remove the entry whose key equals the passed in captionedValue.
	 * Return the entry that is removed. If nothing removed, returns null.
	 * @param cv ICaptionedValue
	 * @return Map.Entry<CaptionedValue<T>,OtherInfo>
	 */
	public Map.Entry<CaptionedValue<T>,OtherInfo> removeEntry(
		final CaptionedValue<T> cv)
	{
		if (cv == null){
			return null;
		}
		
		final Map.Entry<CaptionedValue<T>,OtherInfo> entry = getEntry(cv);
		if (entry == null){
			return null;
		}
		
		final OtherInfo value = getInternalMap().remove(entry.getKey());
		
		return (value == null) ? null : entry;
	}
	
	//
	// API
	//
	/**
	 * Add captionedValue to the internal map. 
	 * If captionedValue already exists in map, do nothing and return false. 
	 * @param cv CaptionedValue<T>
	 * @return boolean true if cv is added to the map.
	 * @exception DsfRuntimeException if cv is null 
	 */
	public boolean addCaptionedValue(final CaptionedValue<T> cv){
		
		if (cv == null){
			DsfExceptionHelper.chuck(CAPTION_VALUE_IS_NULL);
		}
			
		if (contains(cv)){
			return false;
		}
	
		m_map.put(cv, null);
		return true;
	}

	/**
	 * Add captionedValue and otherInfo to the internal map.
	 * If entry with captionedValue already exists, update otherInfo only.
	 * @param cv CaptionedValue<T>
	 * @param otherInfo OtherInfo
	 * @exception DsfRuntimeException if cv is null
	 */
	public void add(final CaptionedValue<T> cv, final OtherInfo otherInfo){
			
		if (cv == null){
			DsfExceptionHelper.chuck(CAPTION_VALUE_IS_NULL);
		}
		
		if (contains(cv)){
			setOtherInfo(cv, otherInfo);
		}
		else {
			m_map.put(cv, otherInfo);	
		}
	}
	
	/**
	 * Associate otherInfo with passed-in captionedValue.
	 * If captionedValue doesn't exist in the map, throws DsfRuntimeException
	 * @param cv CaptionedValue<T>
	 * @param otherInfo OtherInfo
	 * @exception DsfRuntimeException if cv is null or not found in the map
	 */
	public void setOtherInfo(
		final CaptionedValue<T> cv, final OtherInfo otherInfo){
	
		if (cv == null){
			DsfExceptionHelper.chuck(CAPTION_VALUE_IS_NULL);
		}
		
		if (!contains(cv)){
			DsfExceptionHelper.chuck(cv + NOT_FOUND);
		}
		else {
			getEntry(cv).setValue(otherInfo);
		}
	}
	
	/**
	 * Create a new map of entries with keys from passed-in list, replacing the
	 * original map. Please note that otherInfos associated with the original 
	 * captionedValues will be lost. 
	 * In case of exception, the original map stays intact.
	 * @param List<CaptionedValue<T>> list of captionedValues 
	 * @exception DsfRuntimeException if list is null
	 */
	public void setCaptionedValues(final List<CaptionedValue<T>> cvs){
		
		if (cvs == null){
			DsfExceptionHelper.chuck(CAPTION_VALUES_IS_NULL);
		}
		
		final LinkedHashMap<CaptionedValue<T>,OtherInfo> map = 
			new LinkedHashMap<CaptionedValue<T>,OtherInfo>(cvs.size());
			
		for (CaptionedValue<T> obj : cvs){
			if (obj == null){
				continue;
			}
			map.put(obj, null);
		}
		
		setInternalMap(map);
	}
	
	/**
	 * Answer an list copy of internal ordered captionedValues.
	 * If no captionedValue exists, return immutable Collections.emptyList()  
	 * @return List<CaptionedValue<T>>
	 */
	public List<CaptionedValue<T>> getCaptionedValues(){
		if (getInternalMap().size() > 0){
			return new ArrayList<CaptionedValue<T>>(getInternalMap().keySet());
		}

		return Collections.emptyList();
	}
	
	/**
	 * Answer whether the internal map keySet contains captionedValue with 
	 * passed in value. 
	 * @param value T 
	 * @return boolean
	 */
	public boolean contains(final T value){
		return getEntry(value) != null;
	}
	
	/**
	 * Return the map entry that its captionedValue has passed-in value.
	 * If there are more than one exist, return the first one found.
	 * Return null if none found.
	 * @param value T
	 * @return Map.Entry<CaptionedValue<T>,OtherInfo>
	 */
	public Map.Entry<CaptionedValue<T>,OtherInfo> getEntry(final T value){
		for (Map.Entry<CaptionedValue<T>,OtherInfo> entry : m_map.entrySet()){
			final CaptionedValue<T>cv = entry.getKey(); 
			if (cv.getValue().equals(value)){
				return entry;
			}
		}
	
		return null;
	}
	
	/**
	 * Remove the entry whose key has passed-in value.
	 * If there are more than one, remove all of them.
	 * @param value T
	 * @return int the number of entries removed
	 */
	public int removeEntry(final T value){
		int counter = 0;
		
		Map.Entry entry = getEntry(value);
		while (entry != null){
			if (m_map.remove(entry.getKey()) != null){
				counter++;
			}
			entry = getEntry(value);
		}
		
		return counter;
	}
	
	/**
	 * Answer the size of the internal map
	 * @return int
	 */
	public int size(){
		return m_map.size();
	}
	
	//
	// Private
	//
	private Map<CaptionedValue<T>,OtherInfo> getInternalMap(){
		return m_map;
	}
	
	private void setInternalMap(
		final LinkedHashMap<CaptionedValue<T>, OtherInfo> map)
	{
		if (map == null){
			DsfExceptionHelper.chuck("map is null");
		}
		m_map.clear();
		m_map = map;
	}
}


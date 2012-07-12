package org.ebayopensource.dsf.common.enums;

import java.io.Serializable;
import java.util.Iterator;


/**
 * This is a base interface for all EnumHolder implementation.
 */
public interface EnumHolder extends Serializable {
	
	/**
	 * Return the iterator over all constants inside the holder.
	 */
	Iterator iterator();
	
	/**
	 * Return the number of constants inside the holder.
	 */
	int size();
	
	/**
	 * Clear all the constants from the holder.
	 */	
	void clear();

}

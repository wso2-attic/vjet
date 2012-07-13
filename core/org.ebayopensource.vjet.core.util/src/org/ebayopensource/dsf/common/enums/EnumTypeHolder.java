package org.ebayopensource.dsf.common.enums;

import java.util.Iterator;
import java.util.List;

/**
 * This final class is an implementation of EnumHolder.
 * It is immutable. It allows to simulate the Enum holder behavior
 * for holding complete list of constants of given type without
 * really put individual constants in.
 */
public final class EnumTypeHolder implements EnumHolder {
	
	private Class m_type;
	
	/**
	 * Construct an EnumHolder for a given Enum type.
	 */
	public EnumTypeHolder(Class type) {
		if (!BaseEnum.class.isAssignableFrom(type)) {
			throw new IllegalArgumentException
				("EnumTypeHolder: "+ type + " is not the right type");
		}
		m_type = type;
	}

	/**
	 * Return the List over all constants declared by
	 * an Enum type.
	 */	
	public List getList() {
		return EnumManager.getList(m_type);	
	}	
	
	/**
	 * Return the iterator over all constants declared by
	 * an Enum type.
	 */	
	public Iterator iterator() {
		return EnumManager.getIterator(m_type);	
	}
	
	/**
	 * Return the number of constants inside the holder.
	 */
	public int size() {
		return EnumManager.size(m_type);
	}

	/**
	 * Clear method is disabled.
	 */		
	public void clear() {
		//do nothing
	}

	private static final long serialVersionUID = 179010868799572416L;

}

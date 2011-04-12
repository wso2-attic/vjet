/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.binding;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;

public final class PropertyHelper {
	// keyed by Class.  Needs WeakHashMap since we don't want to prevent a
	// class from being unloaded.
	private static final WeakHashMap<Object, Object> s_descriptors = new WeakHashMap<Object, Object>();
	private static final Object empty[] = new Object[0];
	
	public static Object get(final Object target, final String name) {
		if (name == null) {
			throw new NullPointerException();
		}

		final PropertyDescriptor pd = getPropertyDescriptor(
			target.getClass(), name);
			
		if (pd == null) {
			return null ;
		}
		
		try {
			final Method readMethod = pd.getReadMethod();
			if (readMethod != null) {
				return readMethod.invoke(target, empty);
			} 
			throw new IllegalArgumentException(name);
			
		}
		catch(IllegalAccessException e) {
			chuck(e.getMessage());
		}
		catch(InvocationTargetException e) {
			chuck(e.getTargetException().getMessage());
		}

		return null;
	}
	
	public static Object put(
		final Object target, final String name, final Object value)
	{
		if (name == null) {
			throw new NullPointerException();
		}
				
		final PropertyDescriptor pd 
			= getPropertyDescriptor(target.getClass(), name);
		if (pd == null) {
			return null ;
		}
		try {
			Object result = null;
			final Method readMethod = pd.getReadMethod();
			if (readMethod != null) {
				result = readMethod.invoke(target, empty);
			}
			final Method writeMethod = pd.getWriteMethod();
			if (writeMethod != null) {
				writeMethod.invoke(target, new Object[] {value});
			}
			else {
				throw new IllegalArgumentException();
			}
			return result;
		}
		catch(IllegalAccessException e) {
			chuck(e.getMessage());
		}
		catch(InvocationTargetException e) {
			chuck(e.getTargetException().getMessage());
		}

		return null ;
	}

	//
	// Private
	//
	private static void chuck(final String message) {
		throw new DsfRuntimeException(message) ;
	}
	
	private static PropertyDescriptor getPropertyDescriptor(
		final Class clz, final String name)
	{
		final PropertyDescriptor pd[] = getPropertyDescriptors(clz);
		for (int i = 0; i < pd.length; i++) {
			if (name.equals(pd[i].getName())) {
				return (pd[i]);
			}
		}
		return null;
	}
		
	private static PropertyDescriptor[] getPropertyDescriptors(
		final Class clz)
	{
		synchronized (s_descriptors) {
			PropertyDescriptor pd[] =
				(PropertyDescriptor[]) s_descriptors.get(clz);
			if (pd == null) {
				try {
					pd = Introspector.getBeanInfo(clz).
						getPropertyDescriptors();
				}
				catch (IntrospectionException e) {
					chuck(e.getMessage());
				}
				s_descriptors.put(clz, pd);
			}
			return pd;
		}
	}
}

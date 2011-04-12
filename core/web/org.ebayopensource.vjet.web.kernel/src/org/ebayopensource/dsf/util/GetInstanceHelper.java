/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.util;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;

/**
 * Helper class to invoke static getInstance() method from a class
 */
public abstract class GetInstanceHelper {
	private static Class[] TYPES = new Class[]{};
	private static Object[] ARGS = new Object[]{};
	
	public static Object get(Class<?> clz) {
		try {
			return clz.getDeclaredMethod("getInstance", TYPES)
				.invoke(null, ARGS);
		}
		catch (Exception e) {
			throw new DsfRuntimeException(e);
		}
	}
}

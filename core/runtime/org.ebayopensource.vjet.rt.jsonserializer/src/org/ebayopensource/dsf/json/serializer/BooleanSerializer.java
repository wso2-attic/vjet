/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.json.serializer;


public class BooleanSerializer extends AbstractSerializer {
	private static Class[] s_serializableClasses =
		new Class[] { boolean.class, Boolean.class };

	private static Class[] s_JSONClasses =
		new Class[] { Boolean.class, String.class };

	public Class[] getSerializableClasses() {
		return s_serializableClasses;
	}
	public Class[] getJSONClasses() {
		return s_JSONClasses;
	}

	public ObjectMatch tryUnmarshall(
		SerializerState state,
		Class clazz,
		Object jso)
		throws SerializationException {
		return ObjectMatch.OKAY;
	}

	public Object unmarshall(SerializerState state, Class clazz, Object jso)
		throws SerializationException {
			
		Object result = jso;
		if (jso instanceof String) {
			try {
				result = Boolean.valueOf((String) jso);
			} catch (Exception e) {
				throw new SerializationException(
					"Cannot convert " + jso + " to Boolean");
			}
		}
		if (clazz == boolean.class) {
			//return new Boolean(((Boolean) jso).booleanValue());
			//TODO
			result = (Boolean) jso;
		} 
		return result;
	}

	public Object marshall(SerializerState state, Object o)
		throws SerializationException {
		return o;
	}

}

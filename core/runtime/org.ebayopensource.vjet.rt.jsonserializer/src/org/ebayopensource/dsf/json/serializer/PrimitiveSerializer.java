/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.json.serializer;



public class PrimitiveSerializer extends AbstractSerializer {
	private static Class[] s_serializableClasses =
		new Class[] {
			int.class,
			byte.class,
			short.class,
			long.class,
			float.class,
			double.class };

	private static Class[] s_JSONClasses =
		new Class[] {
			Integer.class,
			Byte.class,
			Short.class,
			Long.class,
			Float.class,
			Double.class,
			String.class };

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
		try {
			toPrimitive(clazz, jso);
		} catch (NumberFormatException e) {
			throw new SerializationException("not a primitive");
		}
		return ObjectMatch.OKAY;
	}

	public Object toPrimitive(Class clazz, Object jso)
		throws NumberFormatException {
		if (int.class.equals(clazz)) {
			if (jso instanceof String) {
				return new Integer((String) jso);
			 } else {
				return new Integer(((Number) jso).intValue());
			}
		} else if (long.class.equals(clazz)) {
			if (jso instanceof String) {
				return new Long((String) jso);
			 } else {
				return new Long(((Number) jso).longValue());
			}
		} else if (short.class.equals(clazz)) {
			if (jso instanceof String) {
				return new Short((String) jso);
			 } else {
				return new Short(((Number) jso).shortValue());
			}
		} else if (byte.class.equals(clazz)) {
			if (jso instanceof String) {
				return new Byte((String) jso);
			 } else {
				return new Byte(((Number) jso).byteValue());
			}
		} else if (float.class.equals(clazz)) {
			if (jso instanceof String) {
				return new Float((String) jso);
			 } else {
				return new Float(((Number) jso).floatValue());
			}
		} else if (double.class.equals(clazz)) {
			if (jso instanceof String) {
				return new Double((String) jso);
			 } else {
				return new Double(((Number) jso).doubleValue());
			}
		}
		return null;
	}

	public Object unmarshall(SerializerState state, Class clazz, Object jso)
		throws SerializationException {
		try {
			return toPrimitive(clazz, jso);
		} catch (NumberFormatException nfe) {
			throw new SerializationException(
				"cannot convert object " + jso + " to type " + clazz.getName());
		}
	}

	public Object marshall(SerializerState state, Object o)
		throws SerializationException {
		return o;
	}

}

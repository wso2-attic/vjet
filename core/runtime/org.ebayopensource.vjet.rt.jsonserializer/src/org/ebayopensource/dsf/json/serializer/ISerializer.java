/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.json.serializer;

/**
 * Interface to be implemented by custom serializer objects that convert
 * to and from Java objects and JSON objects.
 */
public interface ISerializer {
	public void setOwner(JsonRpcSerializer ser);

	public Class<?>[] getSerializableClasses();
	public Class<?>[] getJSONClasses();

	public boolean canSerialize(Class<?> clazz, Class<?> jsonClazz);

	public ObjectMatch tryUnmarshall(
		SerializerState state,
		Class<?> clazz,
		Object json)
		throws SerializationException;

	public Object unmarshall(SerializerState state, Class<?> clazz, Object json)
		throws SerializationException;
	

	public Object marshall(SerializerState state, Object o)
		throws SerializationException;
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.json.serializer;

import org.ebayopensource.dsf.services.ServiceEngineError;

/**
 * Thrown by Serializer objects when they are unable to Unmarshall the JSON
 * objects into Java objects.
 */

public class SerializationException extends Exception {
	private ServiceEngineError m_error;
	public SerializationException(String msg) {
		super(msg);
	}
	public SerializationException(String msg, ServiceEngineError error) {
		super(msg);
		m_error = error;
	}
	public ServiceEngineError getError() {
		return m_error;
	}
	public void setError(ServiceEngineError error) {
		this.m_error = error;
	}
}

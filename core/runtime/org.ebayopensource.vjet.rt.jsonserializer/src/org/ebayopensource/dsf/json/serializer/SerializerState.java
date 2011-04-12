/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.json.serializer;

import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.services.IRequestValidator;

/**
 * This class is used by Serializers to hold state during marshalling and
 * unmarshalling.
 */

public class SerializerState {
	
	private Map<Class, Object> m_stateMap = new HashMap<Class, Object>();
	private IRequestValidator m_validator;
	
	public Object get(Class clazz)
		throws InstantiationException, IllegalAccessException {
		
		Object obj = m_stateMap.get(clazz);
		if (obj != null){
			return obj;
		}
		obj = clazz.newInstance();
		m_stateMap.put(clazz, obj);
		return obj;
	}

	public IRequestValidator getValidator() {
		return m_validator;
	}

	public void setValidator(IRequestValidator validator) {
		this.m_validator = validator;
	}

}

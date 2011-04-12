/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.service.serializer;

import org.ebayopensource.dsf.services.IRequestValidator;

public interface ISerializer {
	Object deserialize(String aContent, Class aClass, IRequestValidator validator) throws Exception;
	String serialize(Object aSource) throws Exception;

}

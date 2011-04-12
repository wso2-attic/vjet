/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface IJstSerializer {

	/**
	 * Serialize given JST types to given output stream
	 * @param jstTypes List<IJstType>
	 * @param os OutputStream
	 */
	void serialize(List<IJstType> jstTypes, OutputStream os);
	
	/**
	 * De-serialize given input stream into JST types
	 * @param is InputStream
	 * @return List<IJstType>
	 */
	List<IJstType> deserialize(InputStream is);
}

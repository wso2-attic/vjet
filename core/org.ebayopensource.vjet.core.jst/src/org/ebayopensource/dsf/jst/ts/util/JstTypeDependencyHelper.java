/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.ts.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstType;

public class JstTypeDependencyHelper {

	public static Map<String,IJstType> toMap(final List<IJstType> types){
		if (types == null || types.isEmpty()){
			return Collections.emptyMap();
		}
		Map<String,IJstType> map = new LinkedHashMap<String,IJstType>();
		for (IJstType t: types){
			map.put(t.getName(), t);
		}
		
		return map;
	}
}

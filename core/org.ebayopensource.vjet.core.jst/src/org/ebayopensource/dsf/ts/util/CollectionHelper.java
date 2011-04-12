/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionHelper {

	public static <T> void add(final List<T> toList, final List<T> fromList){
		
		if (toList == null || fromList == null){
			return;
		}
		
		for (T t: fromList){
			if (!toList.contains(t)){
				toList.add(t);
			}
		}
	}

	public static <T> List<T> merge(final List<T> aList, final List<T> bList){
		
		if (aList == null && bList == null){
			return Collections.emptyList();
		}
		
		if (aList != null && bList == null){
			return Collections.unmodifiableList(aList);
		}
		
		if (aList == null && bList != null){
			return Collections.unmodifiableList(bList);
		}
		
		ArrayList<T> list = new ArrayList<T>();
		list.addAll(aList);
		for (T t: bList){
			if (!list.contains(t)){
				list.add(t);
			}
		}

		return list;
	}
}

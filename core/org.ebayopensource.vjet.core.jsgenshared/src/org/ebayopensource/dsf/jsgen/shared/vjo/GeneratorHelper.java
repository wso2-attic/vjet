/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.vjo;

import java.util.Arrays;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;

public class GeneratorHelper {
	private static final List<String> s_skip_satisfies = Arrays.asList(
			"org.ebayopensource.dsf.serializers.ISerializableForVjo"
			);
	private static final List<String> s_skip_extends = Arrays.asList(
			"org.ebayopensource.dsf.aggregator.serializable.BaseVjoSerializable"
			);

	//TODO - Find the correct place for this kind of logic
	public static boolean isSkipSatisfies(IJstType jstType) {
		return (s_skip_satisfies.contains(jstType.getName()));
	}

	//TODO - Find the correct place for this kind of logic
	public static boolean isSkipInherits(IJstType jstType) {
		return (s_skip_extends.contains(jstType.getName()));
	}
}

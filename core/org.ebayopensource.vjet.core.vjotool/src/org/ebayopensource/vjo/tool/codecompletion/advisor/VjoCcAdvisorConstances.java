/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor;

import java.util.ArrayList;
import java.util.List;

/**
 * Constances used by advisor during advising
 * 
 *
 */
public class VjoCcAdvisorConstances {
	private static final List<String> UNRESOLVED_COMPLETIONS = new ArrayList<String>();
	/**
	 * The virtual type list which is not enabled to referenced by others
	 */
	public static final List<String> UNEXIST_TYPES = new ArrayList<String>();
	/**
	 * the jst type constuctor name
	 */
	public static final String CONSTRUCTOR = "constructs";

	/**
	 * jst native type prefix
	 */
	public static final String NATIVE_PREFIX = "org.ebayopensource.dsf.jsnative";
	/**
	 * jst native global type prefix
	 */
	public static final String GLOBAL_PREFIX = "org.ebayopensource.dsf.jsnative.global";

	static {
		UNRESOLVED_COMPLETIONS.add("type");
		UNRESOLVED_COMPLETIONS.add("atype");
		UNRESOLVED_COMPLETIONS.add("makeFinal");
		UNEXIST_TYPES.add("Global");// TODO it should come from a external class
	}
	
}

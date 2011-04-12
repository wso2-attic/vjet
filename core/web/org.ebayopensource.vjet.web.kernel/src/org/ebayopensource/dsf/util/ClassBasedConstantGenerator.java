/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.util;

import java.lang.reflect.Field;

import org.ebayopensource.dsf.common.exceptions.DsfExceptionHelper;

public class ClassBasedConstantGenerator {
	private static final String TAB = "    ";
	private static final String NEWLINE = System.getProperty("line.separator");
	protected static final String SPACE = " ";
	protected static final String EQUAL = "=";
	protected static final String SEMI_COLON = ";";
	protected static final String QUOTE = "\"";
	protected static final String TYPE = "String";
	
	public static void generate (Class clz) throws IllegalArgumentException, IllegalAccessException {
		if (!clz.isInterface()) {
			DsfExceptionHelper.chuck("class should be an interface");
		}
		StringBuilder builder = new StringBuilder();
		for (Field field : clz.getFields()) {
			if (field.getType() == String.class) {
				builder.append(TAB)
				.append(TYPE).append(SPACE)
				.append(field.getName())
				.append(SPACE).append(EQUAL).append(SPACE)
				.append(QUOTE).append(field.get(null)).append(QUOTE)
				.append(SEMI_COLON).append(NEWLINE);
			}
		}
		System.out.println(builder.toString());
	}
}

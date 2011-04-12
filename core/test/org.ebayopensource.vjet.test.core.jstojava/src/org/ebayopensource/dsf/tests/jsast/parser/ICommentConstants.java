/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.tests.jsast.parser;

/**
 * Constants for testing comment.
 * 
 *
 */
public interface ICommentConstants {
	public static final String FOLDER = "data";
	
	public static final String ACCESS = "public"; //==JstModifiers.S_PACKAGE
	public static final String PACKAGE_ACCESS = ""; //==JstModifiers.S_PACKAGE
	
	public static final String PREFIX_METHOD = "foo";
	public static final String PREFIX_OPTIONAL_ARG = "o";
	public static final String PREFIX_ARG = "arg";
	public static final String VARIABLE_ARG = "vargs";
	
	//TODO convert to EnumSet
	public static final char CONCRETE = 'c';
	public static final char ABSTRACT = 'a';
	public static final char INTERFACE = 'i';
	//public static final ENUM = 'e';
	//public static final MIXIN = 'm';
}

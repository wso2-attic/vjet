/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.error;

import org.ebayopensource.af.common.error.RegExErrorFilter;

public final class ErrorFilters {
	
	public static final RegExErrorFilter ALL = 
		new RegExErrorFilter(RegExErrorFilter.MATCH_ALL);
		
	public static final RegExErrorFilter CONVERSION = new RegExErrorFilter(
		ErrorSourceEnum.CONVERSION.getName()
		+RegExErrorFilter.MATCH_DOT_ANY);
			
	public static final RegExErrorFilter RULE = new RegExErrorFilter(
		ErrorSourceEnum.RULE.getName()
		+RegExErrorFilter.MATCH_DOT_ANY);
			
	public static final RegExErrorFilter CONSTRAINT = new RegExErrorFilter(
		ErrorSourceEnum.CONSTRAINT.getName()
		+RegExErrorFilter.MATCH_DOT_ANY);
			
	public static final RegExErrorFilter ACTION = new RegExErrorFilter(
		ErrorSourceEnum.ACTION.getName()
		+RegExErrorFilter.MATCH_DOT_ANY);
			
	public static final RegExErrorFilter VALIDATOR = new RegExErrorFilter(
		ErrorSourceEnum.VALIDATOR.getName()
		+RegExErrorFilter.MATCH_DOT_ANY);

	public static final RegExErrorFilter CLIENT_FIELD_SET =new RegExErrorFilter(
		ErrorSourceEnum.CLIENT_FIELD_SET.getName()
		+RegExErrorFilter.MATCH_DOT_ANY);
						
	public static final RegExErrorFilter OTHER = new RegExErrorFilter(
		ErrorSourceEnum.OTHER.getName()
		+RegExErrorFilter.MATCH_DOT_ANY);
}

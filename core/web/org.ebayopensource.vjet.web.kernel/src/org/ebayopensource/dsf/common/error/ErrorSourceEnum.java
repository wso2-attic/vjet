/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.error;

import java.io.Serializable;
import java.util.List;

import org.ebayopensource.dsf.common.enums.BaseEnum;

//
// Enum Classes
//	
public final class ErrorSourceEnum extends BaseEnum implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	public static final ErrorSourceEnum ACTION =
		new ErrorSourceEnum("Action", 1);
	
	// Field is a type of Validator.  Validators can have general errors.
	public static final ErrorSourceEnum VALIDATOR =
		new ErrorSourceEnum("Validator", 2);
		
	public static final ErrorSourceEnum RULE =
		new ErrorSourceEnum("Rule", 3);
		
	public static final ErrorSourceEnum CONSTRAINT =
		new ErrorSourceEnum("Constraint", 4);
		
	public static final ErrorSourceEnum CONVERSION =
		new ErrorSourceEnum("Conversion", 5);

	public static final ErrorSourceEnum CLIENT_FIELD_SET =
		new ErrorSourceEnum("ClientFieldSet", 6);
				
	public static final ErrorSourceEnum OTHER =
		new ErrorSourceEnum("Other", 100);
					
	private ErrorSourceEnum(String name, int intValue) {
		super(intValue, name);
	}

	public static List getList() {
		return BaseEnum.getList(ErrorSourceEnum.class) ;
	}
	
	/** Get the enumeration instance for a given value or null
	 *  
	 * @param key
	 * @return ErrorSourceEnum
	 */
	public static ErrorSourceEnum get(int key) {
		return (ErrorSourceEnum) BaseEnum.getEnum(ErrorSourceEnum.class, key);
	}

	/** Get the enumeration instance for a given value or return the
	 *  elseEnum default.
	 * 
	 *  @param  key
	 *  @param  elseEnum
	 *  @return ErrorSourceEnum
	 */
	public static ErrorSourceEnum getElseReturn(int key, ErrorSourceEnum elseEnum){
		return (ErrorSourceEnum)BaseEnum.getElseReturnEnum(
		ErrorSourceEnum.class,
			key,
			elseEnum);
	}
}


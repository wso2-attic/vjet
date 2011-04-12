/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.trace;

public interface TranslateMsgId {
	
	String GENERAL_ERROR = "General";
	
	String CUSTOM_PROCESSOR_EXCEPTION = "CustomProcessorException";
	
	String STACK_ERROR = "StackError";
	
	String EXCLUDED_TYPE = "ExcludedType";
	String EXCLUDED_FIELD = "ExcludedField";
	String EXCLUDED_MTD = "ExcludedMethod";
	String EXCLUDED_EMBEDDED_TYPE = "ExcludedEmbeddedType";
	
	String NULL_INPUT = "NullInput";
	String INVALID_PATH = "InvalidPath";
	String SRC_NOT_FOUND = "SrcNotFound";
	String SRC_NAME_IS_NULL = "SrcNameIsNull";
	String CLASS_NOT_FOUND = "ClassNotFound";
	String FAILED_TO_PARSE = "FailedToParse";
	
	String MISSING_BINDING = "MissingBinding";
	String MISSING_DATA_IN_TRANSLATE_INFO = "MissingDataInTranslateInfo";
	
	String UNHANDLED_NODE = "UnhandledNode";
	String UNHANDLED_TYPE = "UnhandledType";
	
	String UNSUPPORTED_NODE = "UnsupportedNode";
	String UNSUPPORTED_CREATION = "UnsupportedCreation";
	
	String INCOMPLETE_NODE = "IncompleteNode";
	
	String NULL_RESULT = "NullResult";
	String INVALID_TYPE = "InvalidType";
	
	String INVALID_ARGS = "InvalidArgs";
	String DUPLICATE_NAME = "Duplicate method or filed name";
}

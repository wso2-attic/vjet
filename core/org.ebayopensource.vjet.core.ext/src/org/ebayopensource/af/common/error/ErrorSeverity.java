/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.af.common.error;

// Java imports
import java.util.ListIterator;

import org.ebayopensource.dsf.common.enums.BaseEnum;
import org.ebayopensource.dsf.logger.LogLevel;
/**
 * Enumeration of the severity levels for errors.
*/
public class ErrorSeverity extends BaseEnum {
	private static final long serialVersionUID = -6223423843265184039L;
	/*
	 * Enum Definitions
	 */
	/**
	 *  Debugging level. Could be used for debugging messages.
	 */
	public static final ErrorSeverity DEBUG = new ErrorSeverity("DEBUG", LogLevel.DEBUG);
	/**
	 * Informational level. Could be used for information messages.
	 */
	public static final ErrorSeverity INFO = new ErrorSeverity("INFO", LogLevel.INFO);
	/**
	 * Warning level. Could be used for recoverable errors.
	 */
	public static final ErrorSeverity WARNING = new ErrorSeverity("WARNING", LogLevel.WARN);
	/**
	 * Error level. Could be used for non-recoverable errors.
	 */
	public static final ErrorSeverity ERROR = new ErrorSeverity("ERROR", LogLevel.ERROR);
	/**
	 * Fatal level. Could be used for severe errors most likely indicating
	 * unexpected sub-system failure.
	 */
	public static final ErrorSeverity FATAL = new ErrorSeverity("FATAL", LogLevel.FATAL);
	private final LogLevel m_logLevel;
	// Add new instances above this line
	/**
	 * Compare this enum to another.  Return -1 is this Enum is less, 1 if this
	 * Enum is greater and 0 if they are equal.
	 */
	public final int compareTo(ErrorSeverity o) {
		if (this.getValue() < o.getValue()) {
			return -1;
		}
		if (this.getValue() > o.getValue()) {
			return 1;
		}
		return 0; // equals
	}

	//-----------------------------------------------------------------//
	// Template code follows....do not modify other than to replace    //
	// enumeration class name with the name of this class.             //
	//-----------------------------------------------------------------//   
	private ErrorSeverity(String name, LogLevel logLevel) {
		super(logLevel.getEbayLevelValue(), name);
		m_logLevel = logLevel;
	}
	public LogLevel getLogLevel(){
		return m_logLevel;
	}
	// ------- Type specific interfaces -------------------------------//
	/** Get the enumeration instance for a given value or null */
	public static ErrorSeverity get(int key) {
		return (ErrorSeverity) getEnum(ErrorSeverity.class, key);
	}
	/** Get the enumeration instance for a given value or return the
	 *  elseEnum default.
	 */
	public static ErrorSeverity getElseReturn(int key, ErrorSeverity elseEnum) {
		return (ErrorSeverity) getElseReturnEnum(ErrorSeverity.class, key, elseEnum);
	}
	/** Return an bidirectional iterator that traverses the enumeration
	 *  instances in the order they were defined.
	 */
	public static ListIterator iterator() {
		return getIterator(ErrorSeverity.class);
	}
}

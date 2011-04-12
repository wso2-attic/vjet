/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.report;

import org.ebayopensource.af.common.error.ErrorList;
import org.ebayopensource.dsf.jstojava.report.ErrorReportPolicy.ReportLevel;

public interface ErrorReporter {
	
	/**
     * report a warning or error based on the ReportLevel
     * @param level ReportLevel
     * @param message message string
     * @param resource a string representation of file or class
     * @param line int line number within resource
     * @param column int column number
     */
	void report(ReportLevel level, String message, String resource, int line, int column);
	
    /**
     * report a warning
     * @param message message string
     * @param resource a string representation of file or class
     * @param line int line number within resource
     * @param column int column number
     */
	void warning(String message, String resource, int line, int column);

    /**
     * report a warning
     * @param message message string
     * @param resource a string representation of file or class
     * @param start int start offset location
     * @param end int end offset location
     * @param line int line number within resource
     * @param column int column number
     */
	void warning(String message, String resource, int start, int end, int line, int column);
	
	 /**
     * report an error
     * @param message message string
     * @param resource a string representation of file or class
     * @param line int line number within resource
     * @param column int column number
     */
    void error(String message, String resource, int line, int column);
    
    
    /**
     * report an error
     * @param message message string
     * @param resource a string representation of file or class
     * @param start int start offset location
     * @param end int end offset location
     * @param line int line number within resource
     * @param column int column number
     */
    void error(String message, String resource, int start, int end,int line, int column);
    
    /**
     * Returns errors as list of ErrorObject 
     * @return a ErrorList
     */
    ErrorList getErrors();
    
    /**
     * Returns warnings as list of ErrorObject 
     * @return a ErrorList
     */
    ErrorList getWarnings();
    
    void reportErrors();
    
    void reportWarnings();
    
    void reportAll();
    
    boolean hasErrors();
    
    boolean hasWarnings();
    
    /**
     * Enable/Disable error reporting
     * @param value true to enable error reporting, false to disable
     */
    void setReportErrors(boolean value);
    
    /**
     * Enable/Disable warning reporting
     * @param value true to enable warning reporting, flase to disable
     */
    void setReportWarnings(boolean value);

}

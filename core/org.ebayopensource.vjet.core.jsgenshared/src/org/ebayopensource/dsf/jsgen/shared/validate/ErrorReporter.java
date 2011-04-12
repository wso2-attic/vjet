/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validate;

import org.ebayopensource.af.common.error.ErrorList;
import org.ebayopensource.dsf.jsgen.shared.validate.ErrorReportPolicy.ReportLevel;

public interface ErrorReporter {
	
	/**
     * report a warning or error based on the ReportLevel
     * @param level ReportLevel
     * @param message message string
     * @param resource a string representation of file or class
     * @param startOffset int start number within resource
     * @param endOffset int end number
     */
	void report(ReportLevel level, String message, String resource, int startOffset, int endOffset);
	
    /**
     * report a warning
     * @param message message string
     * @param resource a string representation of file or class
     * @param startOffset int start number within resource
     * @param endOffset int end number
     */
	void warning(String message, String resource, int startOffset, int endOffset);

	 /**
     * report an error
     * @param message message string
     * @param resource a string representation of file or class
     * @param startOffset int start number within resource
     * @param endOffset int end number
     */
    void error(String message, String resource, int startOffset, int endOffset);
    
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

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst;

/**
 * API for accessing an instance of a problem a VJO type
 * 
 * 
 *
 */


public interface IScriptProblem {

	
	/**
	 * Answer back the original arguments recorded into the problem.
	 * 
	 * @return the original arguments recorded into the problem
	 */
	String[] getArguments();

	/**
	 * Returns the problem id
	 * 
	 * @return the problem id
	 */
	JstProblemId getID();

	/**
	 * Answer a localized, human-readable message string which describes the
	 * problem.
	 * 
	 * @return a localized, human-readable message string which describes the
	 *         problem
	 */
	String getMessage();

	/**
	 * Answer the file name in which the problem was found.
	 * 
	 * @return the file name in which the problem was found
	 */
	char[] getOriginatingFileName();

	/**
	 * Answer the end position of the problem (inclusive), or -1 if unknown.
	 * 
	 * @return the end position of the problem (inclusive), or -1 if unknown
	 */
	int getSourceEnd();

//	/**
//	 * Answer the line number in source where the problem begins.
//	 * 
//	 * @return the line number in source where the problem begins
//	 */
	int getSourceLineNumber();

	/**
	 * Answer the start position of the problem (inclusive), or -1 if unknown.
	 * 
	 * @return the start position of the problem (inclusive), or -1 if unknown
	 */
	int getSourceStart();


	/**
	 * Checks the severity to see if the problem is Error/Warning .
	 * 
	 * @return ProblemTypeEnum
	 */
	ProblemSeverity type();

	int getColumn();

	Class getOriginatingClass();




}

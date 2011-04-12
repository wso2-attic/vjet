/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.jstvalidator;

import org.ebayopensource.dsf.jst.IScriptProblem;
import org.ebayopensource.dsf.jst.JstProblemId;
import org.ebayopensource.dsf.jst.ProblemSeverity;

public class DefaultJstProblem implements IScriptProblem {

	private String[] m_arguments;
	private JstProblemId m_id;
	private String m_message;
	private char[] m_fileName;
	private int m_sourceStart;
	private int m_sourceEnd;
	private int m_lineNumber;
	private ProblemSeverity m_type;
	private int m_col;

	public DefaultJstProblem(String[] arguments, JstProblemId id, String message, char[] fileName, int sourceStart, int sourceEnd, int lineNumber, int col, ProblemSeverity type){
		m_arguments = arguments;
		m_id = id;
		m_message = message;
		m_fileName = fileName;
		m_sourceStart = sourceStart;
		m_sourceEnd = sourceEnd;
		m_lineNumber = lineNumber;
		m_col = col;
		m_type = type;
	}
	

	public String[] getArguments() {
		return m_arguments;
	}

	public JstProblemId getID() {
		return m_id;
	}

	public String getMessage() {
		return m_message;
	}

	public char[] getOriginatingFileName() {
		return m_fileName;
	}

	public int getSourceEnd() {
		return m_sourceEnd;
	}

	public int getSourceStart() {
		return m_sourceStart;
	}

	public ProblemSeverity type() {
		return m_type;
	}


	public int getSourceLineNumber() {
		return m_lineNumber;
	}


	public int getColumn() {
		return m_col;
	}


	public Class<?> getOriginatingClass() {
		return this.getClass();
	}
	
	@Override
	public String toString(){
		return String.format("PROBLEM ID: %s, DETAILS: %s \n in file: %s line: %s", 
				getID() != null ? getID().toString() : "unknown", 
				getMessage(), 
				String.valueOf(getOriginatingFileName()), 
				String.valueOf(getSourceLineNumber()));
	}
}

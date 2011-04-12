/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
//package com.ebay.dsf.jst;
//
//public class DefaultJstProblem implements IJstProblem {
//
//	
//	private String m_message;
//	private char[] m_fileName;
//	private int m_sourceStart;
//	private int m_sourceEnd;
//	private ProblemTypeEnum m_type;
//
//
//	public DefaultJstProblem(String message, char[] fileName, int sourceStart, int sourceEnd){
//		m_message = message;
//		m_fileName = fileName;
//		m_sourceStart = sourceStart;
//		m_sourceEnd = sourceEnd;
//		
//	}
//	public DefaultJstProblem(ProblemTypeEnum type, String message, char[] fileName, int sourceStart, int sourceEnd){
//		m_type = type;
//		m_message = message;
//		m_fileName = fileName;
//		m_sourceStart = sourceStart;
//		m_sourceEnd = sourceEnd;
//		
//	}
//	
//	public String[] getArguments() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public int getID() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	public String getMessage() {
//		return m_message;
//	}
//
//	public char[] getOriginatingFileName() {
//		return m_fileName;
//	}
//
//	public int getSourceEnd() {
//		return m_sourceEnd;
//	}
//
//
//	public int getSourceStart() {
//		return m_sourceStart;
//	}
//
//	public boolean isError() {
//		return m_type.equals(ProblemTypeEnum.error);
//	}
//
//	public boolean isWarning() {
//		return m_type.equals(ProblemTypeEnum.warning);
//	}
//
//
//
//
//
//}

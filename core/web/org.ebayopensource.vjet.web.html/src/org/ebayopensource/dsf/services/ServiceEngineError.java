/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.services;


/**
 * 
 */
public class ServiceEngineError implements IError{

	private String m_errorId;
	private String m_severity;
	private String m_message;
	
	public ServiceEngineError() {
			super();
	}
		
	/**
	 * 
	 */
	public ServiceEngineError(final String anErrorId) {
		super();
		m_errorId = anErrorId;
	}
	
	public ServiceEngineError(final String anErrorId, final String aMessage) {
		this(anErrorId);
		m_message = aMessage;
	}
	
	public ServiceEngineError(final String anErrorId, final String aMessage,
			String aSeverity) {
		this(anErrorId, aMessage);
		m_severity = aSeverity;
	}
	

	/**
	 * @return
	 */
	public String getErrorId() {
		return m_errorId;
	}

	/**
	 * @param aString
	 */
	public void setErrorId(final String aString) {
		m_errorId = aString;
	}
	
	/**
	 * @return
	 */
	public String getMessage() {
		return m_message;
	}

	/**
	 * @param aString
	 */
	public void setMessage(final String aString) {
		m_message = aString;
	}

	public String getSeverity() {
		return m_severity;
	}

	public void setSeverity(final String severity) {
		this.m_severity = severity;
	}
	
	//
	// Override(s) from Object
	//
	@Override
	public String toString() {
		return "ServiceEngineError ErrorId: "
			+ m_errorId
			+ "; ErrorSeverity: "
			+ m_severity
			+ "; ErrorMessage: "
			+ m_message;
	}
}

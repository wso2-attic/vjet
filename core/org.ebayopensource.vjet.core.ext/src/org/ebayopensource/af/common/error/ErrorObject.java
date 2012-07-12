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
import java.io.Serializable;


/**
 * Aggregates information about an individual error.
 * Contains four fields:
 * <ul>
 * <li> Id</li>
 * <li> Severity</li>
 * <li> Parameters (strings likely to be incorporated
 * into the error message)</li>
 * <li> Correlations (for example,information about UI element
 * the error is related to)</li>
 * </ul>
 *  Lopatin
 * @see ErrorList
 * @see ErrorId
 * @see ErrorSeverity
 * @see ErrorArgsInterface
*/

public	class 		ErrorObject 
		implements	Serializable {
	
	/* 
	 * The members protection is set to the "package" level
	 * so that ErrorList methods can optimize access to them
	 */ 
	ErrorId 			m_id;
	ErrorSeverity 		m_severity;
	ErrorArgsInterface 	m_parameters;
	ErrorArgsInterface 	m_correlations;

	// Constructors

	/**
	 * Constructor
	 * @param id error id
	 * @param severity error severity
	 */
	public ErrorObject(ErrorId id, ErrorSeverity severity) {
		setId(id);
		setSeverity(severity);
		m_parameters = null;
		m_correlations = null;
	}
	
	/**
	 * Constructor
	 * @param id error id
	 * @param severity error severity
	 * @param parameters error parameters
	 */
	public ErrorObject(ErrorId id, 
						ErrorSeverity severity,
						ErrorArgsInterface parameters) {
		setId(id);
		setSeverity(severity);
		m_parameters = parameters;
		m_correlations = null;
	}
	
	/**
	 * Constructor
	 * @param id error id
	 * @param severity error severity
	 * @param parameters error parameters
	 * @param correlations error correlations
	 * @throws NullPointerException if the supplied id is <b>null</b>
	 * @throws NullPointerException if the supplied severity is <b>null</b>
	 */
	public ErrorObject(ErrorId id, 
						ErrorSeverity severity, 
						ErrorArgsInterface parameters, 
						ErrorArgsInterface correlations) { 
		setId(id);
		setSeverity(severity);
		m_parameters = parameters;
		m_correlations = correlations;
	}
	

	// Setters and getters

	/**
	 * Set error id
	 * @param id error id
	 * @throws NullPointerException if the supplied id is <b>null</b>
	 */
	public void setId(ErrorId id) {
		if (id == null) {
			throw new NullPointerException
			("The supplied Id must not be null");
		}
		m_id = id;
	}
	
	/**
	 * Get error id
	 * @return error id
	 */
	public ErrorId	getId(){
		return m_id;
	}

	/**
	 * Set error severity
	 * @param severity error severity
	 * @throws NullPointerException if the supplied severity is <b>null</b>
	 */
	public void setSeverity(ErrorSeverity severity) {
		if (severity == null) {
			throw new NullPointerException
			("The supplied severity must not be null");
		}
		m_severity = severity;
	}
	
	/**
	 * Get error severity
	 * @return error severity
	 */
	public ErrorSeverity getSeverity() {
		return m_severity;
	}

	/**
	 * Set error parameters
	 * @param parameters error parameters
	 */
	public void setParameters(ErrorArgsInterface parameters) {
		m_parameters = parameters;
	}
	
	/**
	 * Get error parameters
	 * @return error parameters
	 */
	public ErrorArgsInterface getParameters() {
		return m_parameters;
	}

	/**
	 * Set error correlations
	 * @param correlations error correlations
	 */
	public void setCorrelations(ErrorArgsInterface correlations) {
		m_correlations = correlations;
	}
	
	/**
	 * Get error correlations
	 * @return error correlations
	 */
	public ErrorArgsInterface getCorrelations() {
		return m_correlations;
	}

	
	// Equality checks
	
	/**
	 * Check whether this error object equals to the other error object.
	 * The objects are equal if all their respective fields are equal.
	 * @param thatErrorObject another error object
	 * @return <b>true</b> if the objects are equal, <b>false</b> otherwise
	 */ 
	public boolean equals(ErrorObject thatErrorObject) {
		if (m_id != thatErrorObject.m_id) {
			if ((m_id == null) 
				|| (thatErrorObject.m_id == null)) 
			{
				return false;
			}
			if (!m_id.equals(thatErrorObject.m_id)) {
				return false;
			}
		}
		if (m_severity != thatErrorObject.m_severity) {
			if ((m_severity == null) 
				|| (thatErrorObject.m_severity == null)) 
			{
				return false;
			}
			if (!m_severity.equals(thatErrorObject.m_severity)) {
				return false;
			}
		}
		if (m_parameters != thatErrorObject.m_parameters){
			if ((m_parameters == null) 
				|| (thatErrorObject.m_parameters == null)) 
			{
				return false;
			}
			if (!m_parameters.equals(thatErrorObject.m_parameters)) {
				return false;
			}
		}
		if (m_correlations != thatErrorObject.m_correlations){
			if ((m_correlations == null)
				|| (thatErrorObject.m_correlations == null))
			{
				return false;
			}
			if (!m_correlations.equals(thatErrorObject.m_correlations)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Overrides Object.equals()
	 * @param object another object
	 * @return <b>true</b> if the objects are equal, <b>false</b> otherwise
	 */ 
	public boolean equals(Object object) {
		if (object instanceof ErrorObject) {
			return equals((ErrorObject) object);
		}
		return false;
	}
	
	/**
	 * Overrides Object.hashCode()
	 * @return hash code
	 */ 
	public int hashCode() {
		int hash = 0;

		/*
		 * We assume that id and severity
		 * are non-null.
		 * If they are null, then the class invariant is violated and 
		 * the NullPointerException will be rightfully thrown.
		 */
		hash ^= m_id.hashCode();
		hash ^= m_severity.hashCode();
		
		if(m_parameters != null) {
			hash ^= m_parameters.hashCode();
		}
		if(m_correlations != null) {
			hash ^= m_correlations.hashCode();
		}
		
		return hash;
	}
	

	// Boolean checks

	/**
	 * Is this error severity == INFO?
	 * @return <b>true</b> if the severity is INFO, <b>false</b> otherwise
	 */
	public boolean isInfo() {
		return m_severity.equals(ErrorSeverity.INFO);
	}
	
	/**
	 * Is this error severity == WARNING?
	 * @return <b>true</b> if the severity is WARNING, <b>false</b> otherwise
	 */
	public boolean isWarning() {
		return m_severity.equals(ErrorSeverity.WARNING);
	}
	
	/**
	 * Is this error severity == ERROR?
	 * @return <b>true</b> if the severity is ERROR, <b>false</b> otherwise
	 */
	public boolean isError() {
		return m_severity.equals(ErrorSeverity.ERROR);
	}
	
	/**
	 * Is this error severity == FATAL?
	 * @return <b>true</b> if the severity is FATAL, <b>false</b> otherwise
	 */
	public boolean isFatal() {
		return m_severity.equals(ErrorSeverity.FATAL);
	}
	
	/**
	 * Get String representation
	 * @return "{" "Id :" ErrorId.toString() etc. "}"
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{ ");
		sb.append("Id : ");
		sb.append(getId().toString());
		sb.append(", Severity : ");
		sb.append(getSeverity().toString());

		ErrorArgsInterface parameters = getParameters();
		sb.append(", Parameters : ");
		if(parameters == null) {
			sb.append("{}");
		} else {
			sb.append(parameters.toString());
		}

		ErrorArgsInterface correlations = getCorrelations();
		sb.append(", Correlations : ");
		if(correlations == null) {
			sb.append("{}");
		} else {
			sb.append(correlations.toString());
		}
		
		sb.append(" }");
		
		return sb.toString();
	}

	private static final long serialVersionUID = -7180485712868396374L;
}

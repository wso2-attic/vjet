/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.kernel.stage;

import org.ebayopensource.af.common.error.ErrorFilter;
import org.ebayopensource.af.common.error.ErrorId;
import org.ebayopensource.af.common.error.ErrorList;
import org.ebayopensource.af.common.error.ErrorObject;
import org.ebayopensource.af.common.error.ErrorSeverity;

/**
 * Base impl of IStage<T>
 * @param <T>
 */
public abstract class BaseStage<T> implements IStage<T> {

	private T m_id;
	private IStageTransition<T> m_entryTz;
	private IStageTransition<T> m_exitTz;
	private ErrorList m_directErrors;
	
	//
	// Constructor
	//
	protected BaseStage(final T id){
		if (id == null){
			throw new RuntimeException("id cannot be null");
		}
		m_id = id;
	}
	
	BaseStage(){};
	
	//
	// Satisfy IWork
	//
	public T getId(){
		return m_id;
	}
	
	public void setEntryTransition(final IStageTransition<T> tz){
		m_entryTz = tz;
	}
	
	public IStageTransition<T> getEntryTransition(){
		return m_entryTz;
	}
	
	public void setExitTransition(final IStageTransition<T> tz){
		m_exitTz = tz;
	}
	
	public IStageTransition<T> getExitTransition(){
		return m_exitTz;
	}
	
	public void addError(final ErrorId errorId) {
		getDirectErrors().add(new ErrorObject(errorId, ErrorSeverity.ERROR)) ;
	}
	
	public void addError(final ErrorObject error) {
		getDirectErrors().add(error) ;
	}
	
	public void addErrors(final ErrorList errorList) {
		getDirectErrors().add(errorList) ;
	}
	
	//
	// Satisfy IDirectErrorsContainer
	//
	public boolean hasDirectErrors() {
		if (m_directErrors == null || m_directErrors.isEmpty()) {
			return false ;
		}
		return m_directErrors.hasAnyErrors();
	}
	
	public boolean hasDirectErrors(final ErrorFilter errorFilter) {
		if (m_directErrors == null || m_directErrors.isEmpty()) {
			return false ;
		}
		return m_directErrors.hasAnyErrors(errorFilter) ;
	}	
	
	public ErrorList getDirectErrors() {
		if (m_directErrors == null) {
			m_directErrors = new ErrorList(0) ;
		}
		return m_directErrors ;
	}
	
	public ErrorList getDirectErrors(final ErrorFilter errorFilter) {
		if (m_directErrors == null) {
			m_directErrors = new ErrorList(0) ;
		}
		return m_directErrors.getAllErrors(errorFilter) ;
	}
	
	public void clearDirectErrors() {
		if (m_directErrors != null){
			m_directErrors.clearAllErrors();
		}
	}
	
	public void clearDirectErrors(final ErrorFilter errorFilter) {
		if (m_directErrors != null){
			m_directErrors.clearAllErrors(errorFilter);
		}
	}
	
	//
	// Satisfy IAggregateErrorContainer
	//
	public boolean hasAnyErrors() {
		return hasDirectErrors();
	}
	
	public boolean hasAnyErrors(final ErrorFilter errorFilter) {
		return hasDirectErrors(errorFilter);
	}

	public ErrorList getAllErrors() {
		return getDirectErrors() ;
	}
		
	public ErrorList getAllErrors(final ErrorFilter errorFilter) {
		return getDirectErrors(errorFilter) ;			
	}
	
	// Override Object
	@Override
	public String toString(){
		return "Id=" + getId();
	}
}

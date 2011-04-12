/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.kernel.stage;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.af.common.error.ErrorFilter;
import org.ebayopensource.af.common.error.ErrorList;
import org.ebayopensource.af.common.error.ErrorObject;
import org.ebayopensource.af.common.error.IAggregateErrorContainer;
import org.ebayopensource.af.common.error.IDirectErrorsContainer;
import org.ebayopensource.kernel.stage.DefaultStageManager.AllErrorFilter;

public class StageDriver<T> implements IDirectErrorsContainer, IAggregateErrorContainer {
	
	private static final long serialVersionUID = 1L;
	
	private final IStageManager<T> m_mgr; 
	private ErrorList m_directErrors;
	private final List<IStageListener> m_listeners = new ArrayList<IStageListener>();
	
	//
	// Constructor
	//
	public StageDriver(final StageGraph<T> graph){
		if (graph == null){
			throw new RuntimeException("graph cannot be null");
		}
		m_mgr = new DefaultStageManager<T>(graph);
	}
	
	public StageDriver(final IStageManager<T> mgr){
		if (mgr == null){
			throw new RuntimeException("mgr cannot be null");
		}
		m_mgr = mgr;
	}
	
	//
	// API
	//
	public IStageManager<T> getManager(){
		return m_mgr;
	}
	
	public void execute(){
		
		try {
			IStage<T> stage = m_mgr.start();
			
			while (stage != null){

				beforeDoWork(stage);
				
				// Do work
				stage.doWork();
		
				afterDoWork(stage);
				
				// Next
				stage = m_mgr.next(stage);
			}
		}
		catch (AbortStageProcessingException e){
			// Short-circuit
		}
	}
	
	public void addError(final ErrorObject error){
		
		if (error == null){
			return;
		}
	
		if (m_directErrors == null){
			m_directErrors = new ErrorList();
		}
	
		m_directErrors.add(error);
	}
	
	public void addErrors(final ErrorList errors){
		
		if (errors == null || errors.isEmpty()){
			return;
		}
	
		if (m_directErrors == null){
			m_directErrors = new ErrorList();
		}
	
		m_directErrors.add(errors);
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
		if (m_directErrors == null || m_directErrors.isEmpty()){
			return;
		}
		m_directErrors.clearAllErrors();
	}
	
	public void clearDirectErrors(final ErrorFilter errorFilter) {
		if (m_directErrors == null || m_directErrors.isEmpty()){
			return;
		}
		m_directErrors.clearAllErrors(errorFilter);
	}

	//
	// Satisfy IAggregateErrorContainer
	//
	public boolean hasAnyErrors() {
		return hasAnyErrors(new AllErrorFilter());
	}
	
	public boolean hasAnyErrors(final ErrorFilter errorFilter) {
		
		if (hasDirectErrors(errorFilter)){
			return true ;
		}
		return m_mgr.hasAnyErrors(errorFilter);
	}

	public ErrorList getAllErrors() {
		return getAllErrors(new AllErrorFilter()) ;
	}
		
	public ErrorList getAllErrors(final ErrorFilter errorFilter) {
		
		final ErrorList answer = new ErrorList();
		
		answer.add(getDirectErrors(errorFilter)) ;
		answer.add(m_mgr.getAllErrors(errorFilter)) ;
		
		return answer ;			
	}
	
	public void addListener(final IStageListener listener) {
		if (listener == null) {
			return;
		}
		
		if (m_listeners.contains(listener)) {
			return;
		}
		
		m_listeners.add(listener);
	}
	
	public boolean removeListener(final IStageListener listener) {
		return m_listeners.remove(listener);
	}
	
	//
	// Protected
	//
	protected void beforeDoWork(final IStage stage) {
		// Notify the "beforeStage" for interested listeners (ascending)
		for (IStageListener listener : m_listeners) {
			try {
				if (listener.isApplicable(stage)) {
					StagePreExecutionEvent event = new StagePreExecutionEvent(stage);
					event.processListener(listener);
				}
			} catch(Exception e){
				// TODO: Do we need a strategy to tell us what to do?
				throw new RuntimeException(
						"Exception in listener:" + listener.getClass().getName(), e);
			}
		}
	}
	
	protected void afterDoWork(final IStage stage) {		
		// Notify the "afterStage" for interested listeners (descending)
		for (int i = m_listeners.size() - 1; i >= 0; i--) {
			IStageListener listener = m_listeners.get(i);
			try {
				if (listener.isApplicable(stage)) {
					StagePostExecutionEvent event = new StagePostExecutionEvent(stage);
					event.processListener(listener);
				}
			} catch(Exception e){
				// TODO: Do we need a strategy to tell us what to do?
				throw new RuntimeException(
						"Exception in listener:" + listener.getClass().getName(), e);
			}
		}
	}
}

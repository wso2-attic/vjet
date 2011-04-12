/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.kernel.stage;

import java.util.HashSet;
import java.util.Set;

import org.ebayopensource.af.common.error.ErrorFilter;
import org.ebayopensource.af.common.error.ErrorList;
import org.ebayopensource.af.common.error.ErrorObject;

public class DefaultStageManager<T> implements IStageManager<T> {

	private static final long serialVersionUID = 1L;
	private StageGraph<T> m_graph = null;
	private IStageProvider<T> m_provider;
	
	private boolean m_started = false;
	private ErrorList m_directErrors;
	
	private IStage<T> m_current;
	
	//
	// Constructor
	//
	public DefaultStageManager (){
		this(new StageGraph<T>());
	}
	
	public DefaultStageManager(final StageGraph<T> graph){
		if (graph == null){
			throw new RuntimeException("stage graph cannot be null");
		}
		m_graph = graph;
	}
	
	//
	// Satisfy IStageManager
	//
	public IStage<T> start(){
		if (m_started){
			throw new RuntimeException("Driver already started.");
		}
		if (m_graph == null){
			throw new RuntimeException("stage graph is null");
		}
		return start(m_graph.getFirst());
	}
	
	public IStage<T> start(final T id){
		if (m_started){
			throw new RuntimeException("Driver already started.");
		}
		if (id == null){
			throw new RuntimeException("id cannot be null");
		}
		final IStage<T> stage = getStage(id);
		if (stage == null){
			throw new RuntimeException("Stage is not found for:" + id);
		}
		m_started = true;
		m_current = processEntryTransition(stage);
		return m_current;
	}
	
	public IStage<T> next(final IStage<T> currentStage){
		
		if (currentStage == null){
			throw new RuntimeException("currentStage is null");
		}
		
		T nextId = null;

		// Get nextId
		IStageTransition<T> tz = currentStage.getExitTransition();
		if (tz != null && tz.getNext() != null){
			nextId = tz.getNext();
		}
		else {
			nextId = m_graph.getNext(currentStage.getId());
		}
		
		return next(nextId);
	}
	
	public IStage<T> getCurrent(){
		return m_current;
	}
	
	public DefaultStageManager<T> add(final T stageId){
		getGraph().add(stageId);
		return this;
	}
	
	public DefaultStageManager<T> add(final IStage<T> stage){
		getGraph().add(stage);
		return this;
	}
	
	public StageGraph<T> getGraph(){
		return m_graph;
	}
	
	public IStage<T> getStage(final T id){

		if (id == null){
			return null;
		}
		
		IStage<T> stage = null;
		if (m_graph != null){
			stage = m_graph.get(id);
		}
		
		if (stage == null && m_provider != null){
			stage = m_provider.get(id);
		}

		return stage;
	}
	
	//
	// API
	//
	public void setProvider(final IStageProvider<T> provider){
		m_provider = provider;
	}
	
	public IStageProvider<T> getProvider(){
		return m_provider;
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
		return hasAnyErrors(new AllErrorFilter());
	}
	
	public boolean hasAnyErrors(final ErrorFilter errorFilter) {
		
		if (hasDirectErrors(errorFilter)){
			return true ;
		}
		
		if (m_provider != null) {
			for (IStage<T> stage: m_provider.getAll().values()){
				if (stage.hasDirectErrors(errorFilter)){
					return true;
				}
			}
		}
		
		for (IStage<T> stage: m_graph.getAll().values()){
			if (stage.hasDirectErrors(errorFilter)){
				return true;
			}
		}
		
		return false;
	}
	static class AllErrorFilter implements ErrorFilter {
		public boolean matches(ErrorObject errorObject) {
			return true;
		}
	}
	public ErrorList getAllErrors() {
		return getAllErrors(new AllErrorFilter()) ;
	}
		
	public ErrorList getAllErrors(final ErrorFilter errorFilter) {
		
		final ErrorList answer = new ErrorList();
		
		answer.add(getDirectErrors(errorFilter)) ;
		
		if (m_provider != null) {
			for (IStage<T> stage: m_provider.getAll().values()){
				answer.add(stage.getDirectErrors(errorFilter)) ;
			}
		}
		
		for (IStage<T> stage: m_graph.getAll().values()){
			answer.add(stage.getDirectErrors(errorFilter)) ;
		}

		return answer ;			
	}
	
	//
	// Protected
	//
	protected IStage<T> next(final T nextId){
		
		// Check for terminal
		if (isTerminal(nextId)){
			return null;
		}
		
		// Get instance
		final IStage<T> next = getStage(nextId);
		
		m_current = processEntryTransition(next);
		
		return m_current;
	}
	
	protected boolean isTerminal(final T stageId){
		return stageId == StageId.TERMINAL;
	}
	
	//
	// Private
	//
	private IStage<T> processEntryTransition(final IStage<T> stage){
		
		if (stage == null){
			return null;
		}
		
		T nextId;
		IStage<T> next = stage;
		IStageTransition<T> entryTz;
		
		final Set<T> visitedStages = new HashSet<T>(2);

		while (next != null){
			entryTz = next.getEntryTransition();
			if (entryTz == null){
				break;
			}
			nextId = entryTz.getNext();
			if (nextId == null){
				break;
			}
			if (visitedStages.contains(nextId)){
				throw new RuntimeException("Cycle detected:" + nextId);
			}
			visitedStages.add(nextId);
			next = getStage(nextId);	
		}
		
		return next;
	}
}

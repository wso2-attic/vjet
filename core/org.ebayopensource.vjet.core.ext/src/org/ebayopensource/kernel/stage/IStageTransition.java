/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.kernel.stage;

/**
 * Interface for stage transition that answers what's next.
 *
 * @param <T> Type of stageId
 */
public interface IStageTransition<T> {
	
	/**
	 * Answer the id of next stage. It can be null if it doesn't
	 * have an answer.
	 * @return T
	 */
	T getNext();
	
	//
	// Default Impl
	//
	public static class Default<T> implements IStageTransition<T> {
		
		private IStage<T> m_current;
		private T m_next;
		
		//
		// Constructor
		//
		public Default(){
		}
		
		public Default(T next){
			m_next = next;
		}
		
		public Default(final IStage<T> current){
			m_current = current;
		}
		
		public Default(final IStage<T> current, T next){
			m_current = current;
			m_next = next;
		}
		
		//
		// Satisfy IStageTransition
		//
		public T getNext(){
			return m_next;
		}
		
		//
		// API
		//
		public void setNext(final T next){
			m_next = next;
		}
		
		public IStage<T> getCurrent(){
			return m_current;
		}
	}
}

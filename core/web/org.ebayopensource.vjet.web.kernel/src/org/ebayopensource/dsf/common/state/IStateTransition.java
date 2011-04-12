/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.state;

import org.ebayopensource.kernel.stage.IStageTransition;

public interface IStateTransition extends IStageTransition<StateId>{

	StateId getNext();
	
	//
	// Default Impl
	//
	public static class Default extends IStageTransition.Default<StateId> 
		implements IStateTransition {
		
		//
		// Constructor
		//
		public Default(){
		}
		
		public Default(StateId next){
			super(next);
		}
		
		public Default(final IState current){
			super(current);
		}
		
		public Default(final IState current, StateId next){
			super(current, next);
		}
		
		//
		// Satisfy IStateTransition
		//
		public StateId getNext(){
			return super.getNext();
		}
		
		//
		// API
		//
		public void setNext(final StateId next){
			super.setNext(next);
		}
		
		public IState getCurrent(){
			return (IState)super.getCurrent();
		}
	}
}

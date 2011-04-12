/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.phase;

import org.ebayopensource.kernel.stage.IStageTransition;

public interface IPhaseTransition extends IStageTransition<PhaseId>{

	PhaseId getNext();
	
	//
	// Default Impl
	//
	public static class Default extends IStageTransition.Default<PhaseId> 
		implements IPhaseTransition {
		
		//
		// Constructor
		//
		public Default(){
		}
		
		public Default(PhaseId next){
			super(next);
		}
		
		public Default(final IPhase current, PhaseId next){
			super(current, next);
		}
		
		//
		// Satisfy IStateTransition
		//
		public PhaseId getNext(){
			return (PhaseId)super.getNext();
		}
		
		//
		// API
		//
		public void setNext(final PhaseId next){
			super.setNext(next);
		}
		
		public IPhase getCurrent(){
			return (IPhase)super.getCurrent();
		}
	}
}

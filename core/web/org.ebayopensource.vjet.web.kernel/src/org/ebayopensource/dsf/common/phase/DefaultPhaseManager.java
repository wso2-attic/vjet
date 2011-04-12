/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.phase;

import org.ebayopensource.kernel.stage.DefaultStageManager;

public class DefaultPhaseManager extends DefaultStageManager<PhaseId>
	implements IPhaseManager {

	private static final long serialVersionUID = 1L;

	//
	// Constructors
	//
	public DefaultPhaseManager (){
		super();
	}
	
	public DefaultPhaseManager (final PhaseGraph graph){
		super(graph);
	}
	
	//
	// Satisfy IPhaseManager
	//
	@Override
	public IPhase start(){
		return (IPhase)super.start();
	}
	
	public IPhase next(final IPhase currentPhase){
		return (IPhase)super.next(currentPhase);
	}
	
	public DefaultPhaseManager add(final PhaseId phaseId){
		getGraph().add(phaseId);
		return this;
	}
	
	public DefaultPhaseManager add(final IPhase phase){
		getGraph().add(phase);
		return this;
	}
	
	//
	// Protected
	//
	@Override
	protected boolean isTerminal(final PhaseId phaseId){
		return phaseId == PhaseId.TERMINAL;
	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.state;

import org.ebayopensource.kernel.stage.DefaultStageManager;

public class DefaultStateManager 
	extends DefaultStageManager<StateId> implements IStateManager
{
	private static final long serialVersionUID = 1L;

	//
	// Constructors
	//
	public DefaultStateManager(final IStateProvider stateProvider){
		super();
		setProvider(stateProvider);
	}
	
	//
	// Satisfy IStateManager
	//
	@Override
	public IStateProvider getProvider(){
		return (IStateProvider)super.getProvider();
	}
	
	@Override
	public IState start(){
		return (IState)super.start();
	}
	
	public IState next(final IState currentState){
		return (IState)super.next(currentState);
	}
	
	//
	// Protected
	//
	@Override
	protected boolean isTerminal(final StateId stateId){
		return stateId == StateId.TERMINAL;
	}
}

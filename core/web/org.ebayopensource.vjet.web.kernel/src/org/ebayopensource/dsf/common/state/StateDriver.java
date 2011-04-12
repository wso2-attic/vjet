/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.state;

import org.ebayopensource.dsf.common.context.DsfCtx;
import org.ebayopensource.dsf.common.trace.TraceStageListener;
import org.ebayopensource.kernel.stage.AbortStageProcessingException;
import org.ebayopensource.kernel.stage.StageDriver;

public class StateDriver extends StageDriver<StateId> {

	private static final long serialVersionUID = 1L;
	private DsfCtx m_dsfCtx;
	
	//
	// Constructor
	//
	public StateDriver(final IStateManager mgr){
		super(mgr);
		m_dsfCtx = DsfCtx.ctx();
		addListener(new TraceStageListener());
	}
	
	//
	// API
	//
	@Override
	public void execute(){
		try {
			IState state = getManager().start();
			
			while (state != null){		
				// Set directon
				m_dsfCtx.setDirection(state.getDirection());
				
				beforeDoWork(state);
				
				// Do work
				state.doWork();
				
				afterDoWork(state);
			
				// Next
				state = getManager().next(state);
			}
		}
		catch (AbortStageProcessingException e){
			// TODO
		}
	}
	
	@Override
	public IStateManager getManager(){
		return (IStateManager)super.getManager();
	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.kernel.stage;


public class StagePostExecutionEvent extends BaseStageEvent/*DsfEvent*/<IStage, IStageListener> {
	
	private static final long serialVersionUID = 1L;

	//
	// Constructor(s)
	//
	public StagePostExecutionEvent(final IStage stage) {
		super(stage, IStageListener.class);
	}

	//
	// Satisfy abstract requirement from DsfEvent
	//
	@Override
	public void processListener(final IStageListener listener)
//		throws AbortDsfEventProcessingException
	{
		listener.afterStage(this);
	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.phase;

import org.ebayopensource.dsf.common.trace.TraceStageListener;
import org.ebayopensource.kernel.stage.StageDriver;

public class PhaseDriver extends StageDriver<PhaseId>{

	private static final long serialVersionUID = 1L;

	//
	// Constructor
	//
	public PhaseDriver(){
		this(new DefaultPhaseManager());
	}
	
	public PhaseDriver(final PhaseGraph graph){
		this(new DefaultPhaseManager(graph));
	}
	
	public PhaseDriver(final IPhaseManager mgr){
		super(mgr);
		addListener(new TraceStageListener());
	}
	
	//
	// API
	//
	@Override
	public IPhaseManager getManager(){
		return (IPhaseManager)super.getManager();
	}
}

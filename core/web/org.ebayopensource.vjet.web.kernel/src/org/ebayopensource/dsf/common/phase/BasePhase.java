/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.phase;

import org.ebayopensource.kernel.stage.BaseStage;

public abstract class BasePhase extends BaseStage<PhaseId> 
	implements IPhase {

	//
	// Constructor
	//
	protected BasePhase(final PhaseId id){
		super(id);
	}
}

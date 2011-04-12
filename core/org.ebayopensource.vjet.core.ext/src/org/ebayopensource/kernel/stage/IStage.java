/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.kernel.stage;

import org.ebayopensource.af.common.error.IAggregateErrorContainer;
import org.ebayopensource.af.common.error.IDirectErrorsContainer;

/**
 * IStage is an interface for stages that have mainly a piece of work 
 * that can have pre- and post- conditions and associated transitions.
 * @param <T> Type of stageId
 */
public interface IStage<T> 
	extends IDirectErrorsContainer, IAggregateErrorContainer
{	
	/**
	 * Id of the stage.
	 * @return T
	 */
	T getId();
	
	/**
	 * Answer the entry transition of the stage.
	 * @return IStageTransition<T>
	 */
	IStageTransition<T> getEntryTransition();
	
	/**
	 * Do the work of this stage.
	 */
	void doWork();
	
	/**
	 * Answer the exit transition of the stage.
	 * @return IStageTransition<T>
	 */
	IStageTransition<T> getExitTransition();
}

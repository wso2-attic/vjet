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
 * Interface for stage managers that are capable to answer 
 * what's the first stage to start with, what should be the next stage,
 * and always be able to answer what's the current stage.
 *
 * @param <T> Type of stageId
 */
public interface IStageManager<T> 
	extends IDirectErrorsContainer, IAggregateErrorContainer
{	
	/**
	 * Start the stage processing and answer the first stage to begin with.
	 * @return IStage<T>
	 */
	IStage<T> start();
	
	/**
	 * Start the stage processing with the stage of given id.
	 * @param id IStage<T> The id of stage to start with
	 * @return IStage<T>
	 */
	IStage<T> start(T id);
	
	/**
	 * Answer the next stage after given stage.
	 * @param currentStage
	 * @return IStage<T>
	 */
	IStage<T> next(final IStage<T> currentStage);
	
	/**
	 * Answer the current stage. If called before next() being called,
	 * it should answer the stage that is doing the work right now
	 * or has just done the work. If called after next() being called,
	 * it should answer the stage that is doing the work right now
	 * or is going to do the work.
	 * @return IStage<T>
	 */
	IStage<T> getCurrent();
	IStage<T> getStage(final T id);
}

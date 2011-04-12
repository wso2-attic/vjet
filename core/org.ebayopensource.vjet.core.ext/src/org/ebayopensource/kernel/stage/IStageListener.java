/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.kernel.stage;



public interface IStageListener extends IStageEventListener/*IDsfEventListener*/ {
	/**
	 * Answer whether this listener is interested in events for the specified
	 * stage.  stage will never be null.  If interested in all events just
	 * return true.
	 */
	boolean isApplicable(IStage stage);

	/**
	 * Called before stage's doWork.  StageEvent will never be null.
	 */
	void beforeStage(StagePreExecutionEvent event);

	/**
	 * Called after stage's doWork.  StageEvent will never be null.
	 */	
	void afterStage(StagePostExecutionEvent event);

}
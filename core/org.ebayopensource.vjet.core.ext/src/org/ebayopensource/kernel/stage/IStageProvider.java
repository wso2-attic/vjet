/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.kernel.stage;

import java.util.Map;


public interface IStageProvider<T> {
	
	/**
	 * Add given stage to the internal registry.
	 * @param stage IStage
	 */
	IStageProvider<T> add(final IStage<T> stage);
	
	/**
	 * Answer the stage instance with given id.
	 * @param stageId StageId
	 * @return IStage
	 */
	IStage<T> get(T stageId);
	
	/**
	 * Return all registered stages
	 * @return Map<T,IStage<T>>
	 */
	Map<T,IStage<T>> getAll();
}

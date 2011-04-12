/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.kernel.stage;

public class StageListenerAdapter implements IStageListener {

	public boolean isApplicable(final IStage stage) {
		return true;
	}

	public void beforeStage(final StagePreExecutionEvent event) {	
		// empty on purpose
	}

	public void afterStage(final StagePostExecutionEvent event) {
		// empty on purpose
	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.typespace;

/**
 * Empty type load monitor implementation.
 * 
 * 
 *
 */
class EmptyTypeLoadMonitor extends TypeLoadMonitor {

	@Override
	public void loadTypeFinished() {
		// nothing

	}

	@Override
	public void loadTypeListFinished() {
		// nothing

	}

	@Override
	public void loadTypeListStarted(int typesCount) {
		// nothing

	}
	@Override
	public void loadTypeStarted(float percent) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void loadTypeStarted(String group, String file) {
		

	}

	@Override
	public void preparationTypeListFinished() {
		// nothing

	}

	@Override
	public void preparationTypeListStarted() {
		// nothing
	}

}

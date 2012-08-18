/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.artifact;


import org.ebayopensource.vjet.testframework.sandbox.ISandbox;


/**
 * @author ddodd
 *
 * IArtifactManager
 * 
 * Manages each test artifact before and after tests are run.
 * 
 */
public interface IArtifactManager {
	
	void init(IArtifactDef artifactDef, ISandbox sandBox);
	
	/**
	 * Setup the artifact in the sandbox environment
	 * @return Return whether setup was performed.  Only artifacts that were setup will 
	 * be torn down.
	 */
	boolean setUp();
	
	void tearDown();

}

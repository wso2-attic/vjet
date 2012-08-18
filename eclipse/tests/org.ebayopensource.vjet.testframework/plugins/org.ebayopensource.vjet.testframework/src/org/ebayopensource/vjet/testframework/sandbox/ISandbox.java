/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.sandbox;

import java.io.File;

/**
 * 
 * @author ddodd
 * 
 * The Sandbox is a place where test files and artifacts are placed 
 * to run tests.
 *
 */
public interface ISandbox {

	public abstract void tearDown();

	public abstract void setUp();

	public abstract File getSandBoxDir();

}
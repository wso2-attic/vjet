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
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.ebayopensource.vjet.testframework.fixture.IFixtureDef;
import org.ebayopensource.vjet.testframework.util.TestUtils;



/**
 * 
 * @author ddodd
 *
 * An implementation of {@link ISandbox}
 * 
 */
public class Sandbox implements ISandbox {
	
	Object m_testSource;
	
	File m_sandBoxDir;
	
	public Sandbox(Object testSource) {
		m_testSource = testSource;
		
		m_sandBoxDir = TestUtils.getSandboxDir(m_testSource);
	}

	/* (non-Javadoc)
	 * @see com.ebay.tools.testframework.sandbox.ISandbox#tearDown()
	 */
	public void tearDown() {
		try {
			FileUtils.deleteDirectory(m_sandBoxDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.ebay.tools.testframework.sandbox.ISandbox#setUp()
	 */
	public void setUp() {
		TestUtils.makeSandboxDir(m_sandBoxDir);
		m_sandBoxDir = TestUtils.makeSandboxDir(m_testSource);
	}

	/* (non-Javadoc)
	 * @see com.ebay.tools.testframework.sandbox.ISandbox#getSandBoxDir()
	 */
	public File getSandBoxDir() {
		return m_sandBoxDir;
	}
	

}

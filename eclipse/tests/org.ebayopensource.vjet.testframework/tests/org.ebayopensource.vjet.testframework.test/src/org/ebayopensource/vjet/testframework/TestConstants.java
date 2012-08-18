/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework;

public interface TestConstants {

	//fixtures.xml file names used for testing
	public static final String VALID_FIXTURE_FILENAME = "fixtures_test.xml";	
	public static final String INVALID_FIXTURE_FILENAME = "fixtures_invalid.xml";	
	
	//Valid Fixtures
	public static final String VALID_ZIP_PROJECT_FIXTURE_ID = "com.ebay.tools.v4.repositoryeditor.ui.test.fixtures"; 
	public static final String VALID_EMBEDDED_PROJECT_FIXTURE_ID = "org.ebayopensource.vjet.testframework.fixture3";
	public static final String VALID_ENV_BUILDCONFIG_FIXTURE_ID = "org.ebayopensource.vjet.testframework.fixture4";
	public static final String VALID_BUNDLE_BUILDCONFIG_FIXTURE_ID = "org.ebayopensource.vjet.testframework.fixture5";
	
	//Invalid Fixtures
	public static final String ZIP_PROJECT_NO_NAME_FIXTURE_ID = "com.ebay.tools.v4.repositoryeditor.ui.test.fixtures.artifactnoprojectname";
}

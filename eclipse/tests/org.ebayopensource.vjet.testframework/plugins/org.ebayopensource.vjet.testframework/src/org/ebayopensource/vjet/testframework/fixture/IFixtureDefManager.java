/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.fixture;

import java.util.Collection;

/**
 * @author ddodd
 *
 * The fixture definition manager is used to return fixture definitions and is 
 * used by the FixtureManager.
 */
public interface IFixtureDefManager {

	/**
	 * Return a fixture definition based on a fixture id.
	 * @param fixtureId
	 * @return
	 */
	public IFixtureDef getFixtureDef(String fixtureId);
	
	/**
	 * Returns a list of all the fixture definitions.
	 * @return
	 */
	public Collection<IFixtureDef> getFixtureDefs();
	
}

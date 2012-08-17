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
import java.util.LinkedHashMap;
import java.util.Map;

import org.ebayopensource.vjet.testframework.sandbox.ISandbox;


/**
 * @author   ddodd
 * 
 * An implementation of {@link IFixtureDefManager}
 */
public class FixtureDefManager implements IFixtureDefManager {

	ISandbox m_sandbox;
	
	Map<String, IFixtureDef> m_fixtureDefs = new LinkedHashMap<String, IFixtureDef>();
	
	public FixtureDefManager() {
	}

	public IFixtureDef getFixtureDef(String fixtureId) {
		return m_fixtureDefs.get(fixtureId);
	}
	
	public Collection<IFixtureDef> getFixtureDefs() {
		return m_fixtureDefs.values();
	}
	
	public void addFixtureDef(IFixtureDef fixtureDef) {
		m_fixtureDefs.put(fixtureDef.getFixtureId(), fixtureDef);
	}

}

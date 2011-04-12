/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.ts;

import java.util.Properties;

/**
 * Config that can be either loaded from external file via standard Java property mechanizm,
 * or set programmatically via accessors.
 */
public class TypeSpaceConfig extends Properties {
	private static final long serialVersionUID = 8094232256203265465L;
	private static final String PROPERTY_PREFIX = "org.ebayopensource.dsf.jst.ts.";
	
	public enum ConfigProperty {
		SynchronousEvents,
		PersistTypeSpace,
	};
	
	public void setSynchronousEvents(boolean f) {
		setProperty(ConfigProperty.SynchronousEvents, String.valueOf(f));
	}
	
	public boolean isSynchronousEvents() {
		return getBooleanProperty(ConfigProperty.SynchronousEvents, false);
	}
	
	public void setPersistTypeSpace(boolean value) {
		setProperty(ConfigProperty.PersistTypeSpace, String.valueOf(value));
	}

	public boolean shouldPersistTypeSpace() {
		return getBooleanProperty(ConfigProperty.PersistTypeSpace, false);
	}
	public void setProperty(ConfigProperty p, String val) {
		setProperty(PROPERTY_PREFIX+p.name(), val);
	}

	public boolean getBooleanProperty(ConfigProperty p, boolean dflt) {
		try {
			return Boolean.parseBoolean(getProperty(PROPERTY_PREFIX+p.name(), String.valueOf(dflt)));
		}
		catch (Exception x){
			// ignore
		}
		return dflt;
	}
}

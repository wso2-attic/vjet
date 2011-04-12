/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.sac.helpers;

import org.ebayopensource.dsf.css.sac.IParser;

public class DParserFactory
{
	public IParser makeParser()
	throws ClassNotFoundException, IllegalAccessException,
		   InstantiationException, NullPointerException,
		   ClassCastException {
	String string = System.getProperty("org.w3c.css.sac.parser");
	if (string == null)
		throw new NullPointerException("No value for sac.parser property");
	return (IParser) Class.forName(string).newInstance();
	}
}

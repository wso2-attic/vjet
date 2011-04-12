/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.custom;

import org.ebayopensource.dsf.javatojs.anno.AExclude;
import org.ebayopensource.dsf.javatojs.anno.AJavaOnly;
import org.ebayopensource.dsf.javatojs.anno.AMappedToJS;
import org.ebayopensource.dsf.javatojs.anno.AMappedToVJO;


public class OverloadedConstructors {
	
	@AExclude
	public OverloadedConstructors() {

	}
	
	@AJavaOnly
	public OverloadedConstructors(String arg) {

	}

	@AMappedToJS(name = "x")
	public OverloadedConstructors(Boolean v) {

	}
	
	@AMappedToVJO(name = "a.b.c.y")
	public OverloadedConstructors(Integer arg) {

	}

	public void create() {
		OverloadedConstructors test = new OverloadedConstructors();
		test = new OverloadedConstructors("booyah");
		test = new OverloadedConstructors(Boolean.TRUE);
		test = new OverloadedConstructors(10);
	}
}

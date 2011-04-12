/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.cli.data;

import org.ebayopensource.dsf.javatojs.tests.cli.data.subdir.Foo;
import org.ebayopensource.dsf.javatojs.tests.cli.data2.Doo;

public class Boo {
	
	public void dependGoo(){
		Goo go = new Goo();
	}
	
	public void dependFoo(){
		Foo go = new Foo();
	}
	
	public void dependDoo(){
		Doo go = new Doo();
	} 
}

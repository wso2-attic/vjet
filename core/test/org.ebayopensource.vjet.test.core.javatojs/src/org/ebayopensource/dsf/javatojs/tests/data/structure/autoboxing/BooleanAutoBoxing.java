/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure.autoboxing;

public class BooleanAutoBoxing {
	boolean b1 = true;
	boolean b2 = false;
	boolean b3 = false;
	Boolean aB = true;
	Boolean bB  = b1 && aB && b2 || aB && b3;
	Boolean cB  = b1 && !aB && b2 && aB && b3;
	Boolean dB  = !aB;
	Boolean eB  = !aB && b1;
	Boolean hB  = (!aB);
	Boolean iB = (aB = b3) && b3;
	Boolean jB = b2 && (aB = b3) && b3;
}

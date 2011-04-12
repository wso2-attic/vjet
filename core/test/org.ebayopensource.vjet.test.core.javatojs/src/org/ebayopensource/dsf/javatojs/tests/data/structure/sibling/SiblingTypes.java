/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure.sibling;

public class SiblingTypes {
	int x=0;
}
class AA {
	int x=0;
	public int getX(){
		x++;
		return x;
	}
}
class BB {
	int y=0;
	public int getY(){
		y++;
		return y;
	}
}

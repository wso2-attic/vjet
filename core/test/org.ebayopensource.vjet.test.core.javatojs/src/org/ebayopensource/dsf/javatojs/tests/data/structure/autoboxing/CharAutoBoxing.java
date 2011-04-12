/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure.autoboxing;

public class CharAutoBoxing {
	Character aC = 2;
	char ac = aC;
	Integer bI  = 1 + aC + 2 + aC + 3;
	Integer cI  = 1 + ++aC + 2 + aC-- + 3;
	Character dI  = ++aC;
	Integer eI  = ++aC + 1;
	Character fI  = aC--;
	Integer gI  = aC-- + 1;
	Character hI  = (++aC);
	Integer iI = (aC = 3) + 4;
	Integer jI = 2 + (aC = 3) + 4;
	
	int ai = 2;
	int bi  = 1 + aC + 2 + aC + 3;
	int ci  = 1 + ++aC + 2 + aC-- + 3;
	int di  = ++aC;
	int ei  = ++aC + 1;
	int fi  = aC--;
	int gi  = aC-- + 1;
	int hi  = (++aC);
	int ii = (aC = 3) + 4;
	int ji = 2 + (aC = 3) + 4;	
}

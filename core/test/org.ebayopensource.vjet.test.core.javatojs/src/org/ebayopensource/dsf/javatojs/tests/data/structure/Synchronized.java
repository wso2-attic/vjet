/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure;

import org.ebayopensource.dsf.javatojs.tests.data.Person;
import org.ebayopensource.dsf.javatojs.tests.data.Person.Status;

public class Synchronized {
	
	private static int s_total;

	public synchronized int updateCount(int x){
		
		int y = x * 10;
		
		while (s_total < 10000){
		
			synchronized (Synchronized.class){
				int z = y + 100;
				s_total += z;
			}
			
			synchronized (Synchronized.class){
				Status enumConst = Person.Status.SINGLE;
				int z = y + 100;
				s_total += z;
			}
			
			int z;	// You can still define z but it has to be initialized. Do dup-name should be fine.
			
			if (y > 0){
				z = y;
			}
		}
		
		return s_total;
	}
}

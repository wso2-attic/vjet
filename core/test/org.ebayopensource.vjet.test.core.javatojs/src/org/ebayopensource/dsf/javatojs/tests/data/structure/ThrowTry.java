/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure;

import org.ebayopensource.dsf.javatojs.tests.Logger;



public class ThrowTry {
	
	private static Logger s_Logger = Logger.getInstance(ThrowTry.class);
	private static int s_count = 0;

	public void test(){
		try {
			throw new NumberFormatException("test");
		}
		catch(NumberFormatException eDsf){
			eDsf.printStackTrace();
		}
		catch(Exception e){
			s_Logger.log(e);
		}
		finally {
			s_count++;
		}
	}
}

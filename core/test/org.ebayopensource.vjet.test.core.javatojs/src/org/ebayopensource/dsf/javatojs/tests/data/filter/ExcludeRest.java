/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.filter;


import org.ebayopensource.dsf.javatojs.anno.AExclude;
import org.ebayopensource.dsf.javatojs.tests.data.multipass.G;

public class ExcludeRest {
	
	@AExclude
	private G m_a;
	
	@AExclude
	public G getCtx(){
		return m_a;
	}

	@AExclude
	public static enum EnumA {
		A(new G()),
		B(new G()),
		C(new G());
		private G m_g;
		EnumA(G g){
			m_g = g;
		}	
	}
	
	public static enum EnumB {
		@AExclude
		A(new G()), 
		
		@AExclude
		B(new G()), 
		
		@AExclude
		C(new G());
		
		@AExclude
		private G m_g;
		
		@AExclude
		EnumB(G g){
			m_g = g;
		}
	}
}

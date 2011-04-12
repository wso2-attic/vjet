/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure;

import org.ebayopensource.dsf.javatojs.tests.data.IHandler;

public interface Interface extends IHandler {
	
	public String PREFIX = "m_";
	String POSTFIX = "Js";
	
	public boolean handle2();
	
	int total();
	
	abstract class Base implements Interface {
		public boolean handle2(){
			return false;
		}
	}
	
	class Default extends Base implements Interface {
		public boolean handle2(){
			return super.handle2();
		}
		public boolean handle(boolean debug){
			return false;
		}
		public int total(){
			return 0;
		}
	}
}

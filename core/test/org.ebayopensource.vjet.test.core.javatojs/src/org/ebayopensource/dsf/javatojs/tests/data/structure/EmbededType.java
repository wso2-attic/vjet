/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure;

import org.ebayopensource.dsf.javatojs.tests.data.BaseHandler;
import org.ebayopensource.dsf.javatojs.tests.data.IHandler;

public class EmbededType {
	
	private void useDefaultType(){

		IHandler handler = new org.ebayopensource.dsf.javatojs.tests.data.DefaultHandler(true, false){
			public boolean handle(boolean debug){	
				if (debug){
					log();
				}
				return !super.handle(debug);
			}
		};
		boolean success = handler.handle(true);
	}
	
	protected void useBaseType(){

		IHandler handler = new BaseHandler(true, false){
			public boolean handle(boolean debug){	
				if (debug){
					log();
				}
				return true;
			}
			protected void log(){
				super.log();
			}
		};
		boolean success = ((BaseHandler)handler).handle(true);
	}
	
	void useInterface(){

		IHandler handler = new IHandler(){
			public boolean handle(boolean debug){	
				return !debug;
			}
		};
		boolean success = handler.handle(true);
	}
	
	public void testImport(){
		DefaultHandler h1 = new DefaultHandler();
		IHandler h2 = new org.ebayopensource.dsf.javatojs.tests.data.DefaultHandler(true, false);
	}
}

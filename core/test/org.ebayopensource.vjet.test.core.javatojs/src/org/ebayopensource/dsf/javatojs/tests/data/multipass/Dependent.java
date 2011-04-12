/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.multipass;

import org.ebayopensource.dsf.javatojs.tests.data.A;
import org.ebayopensource.dsf.javatojs.tests.data.B;
import org.ebayopensource.dsf.javatojs.tests.data.C;

public class Dependent extends Base {

	private A a;
	
	public String foo(B b){
		C c = new C();
		while (true){
			String value = createB().getWindow().getDocument().getCookie();
			value = a.getWindow().getDocument().getCookie();
			value = b.getWindow().getDocument().getCookie();
			
			if (value != null){
				String F = "F";
			}
			
			if (F.getList().size() > 0){
				String cookie = a.getWindow().getDocument().getCookie();
				cookie = c.getWindow().getDocument().getCookie();
				cookie = D.createA().getWindow().getDocument().getCookie();
				cookie = new E().createC().getWindow().getDocument().getCookie();
				return cookie.toString();
			}
		}
	}
	
	public String getValue(){
		return window()
			.getDocument()
			.getForms()
			.item(0)
			.getNodeValue();
	}
	
	public E getE(){
		return new E();
	}
}

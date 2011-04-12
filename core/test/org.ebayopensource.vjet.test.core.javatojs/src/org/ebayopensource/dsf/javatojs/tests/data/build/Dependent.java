/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.build;

import org.ebayopensource.dsf.dap.rt.DapCtx;
import org.ebayopensource.dsf.javatojs.anno.AExclude;
import org.ebayopensource.dsf.javatojs.tests.data.build.subdir.subdir2.Base;
import org.ebayopensource.dsf.javatojs.tests.data.build.subdir.subdir2.D;
import org.ebayopensource.dsf.javatojs.tests.data.build.subdir.subdir2.E;
import org.ebayopensource.dsf.javatojs.tests.data.build.subdir.subdir2.F;
import org.ebayopensource.dsf.javatojs.tests.data.build.subdir.subdir2.subdir3.A;
import org.ebayopensource.dsf.javatojs.tests.data.build.subdir.subdir2.subdir3.B;
import org.ebayopensource.dsf.javatojs.tests.data.build.subdir.subdir2.subdir3.C;

public class Dependent extends Base {

	private A a;
	
	@AExclude
	private DapCtx m_ctx = DapCtx.ctx();
	
	public void foo(B b){
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
				cookie.toString();
			}
		}
	}
	
	@AExclude
	public String getValue(){
		return DapCtx.ctx()
			.getWindow()
			.getDocument()
			.getForms()
			.item(0)
			.getNodeValue();
	}
	
	public E getE(){
		return new E();
	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.runtime.tests.functional;

import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.html.dom.DDiv;
import org.ebayopensource.dsf.html.dom.DScript;
import org.ebayopensource.dsf.common.FileUtils;
import org.ebayopensource.vjo.VjBootstrapJsr;

public class ProtosWithPropsNeedsTest {
	public static DNode test(){
		DDiv container = new DDiv();
		String text = "You should see alert(\"false,true\")";
		container.add(text);
		String js = FileUtils.getResourceString(VjBootstrapJsr.class, "VjBootstrap_3.js");
		js += FileUtils.getResourceString(ProtosWithPropsNeedsTest.class, "ProtosWithPropsNeedsTest.txt");
		container.add(new DScript(js)); 
		return container;
	}
}

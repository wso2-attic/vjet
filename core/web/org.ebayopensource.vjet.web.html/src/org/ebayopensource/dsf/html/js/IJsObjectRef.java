/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

import org.ebayopensource.dsf.javatojs.anno.AExclude;

@AExclude
public interface IJsObjectRef extends IJsContentGenerator,IHaveJsParams,IJsVariableBinding{

	//String getReservedName();
	String getClassName();
	String getInstantiationJs();
	String getInstanceId();
	String setInstanceId(String compId);
	String getInstancePropertySetters();
	String generate(boolean withRegistry);
	boolean isHandler();
	void setIsHandler(boolean value);
	boolean isGenned();
}

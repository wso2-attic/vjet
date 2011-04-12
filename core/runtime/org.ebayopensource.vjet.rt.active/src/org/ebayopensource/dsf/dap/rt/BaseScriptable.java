/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import org.mozilla.mod.javascript.ScriptableObject;

public abstract class BaseScriptable extends ScriptableObject {

	public BaseScriptable(){
	}
	
	public void jsConstructor() {
	}
	
	public String getClassName(){
		return getClass().getSimpleName();
	}
	
	protected void defineProperties(final String[] propertyNames) {
		for (int i = 0; i < propertyNames.length; i++) {
			defineProperty(propertyNames[i], getClass(), ScriptableObject.DONTENUM);
		}
	}
	
	protected void defineFunctionProperties(final String[] methodNames) {
		defineFunctionProperties(methodNames,getClass(),ScriptableObject.DONTENUM);
	}
}

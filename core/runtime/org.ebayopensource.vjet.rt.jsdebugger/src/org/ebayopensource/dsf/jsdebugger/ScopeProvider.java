/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsdebugger;

import org.mozilla.mod.javascript.Scriptable;

/**
 * Interface to provide a scope object for script evaluation to the debugger.
 */
public interface ScopeProvider {

	/**
	 * Returns the scope object to be used for script evaluation.
	 */
	Scriptable getScope();
}
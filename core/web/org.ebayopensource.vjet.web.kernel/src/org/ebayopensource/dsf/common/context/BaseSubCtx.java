/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.context;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;

public abstract class BaseSubCtx implements ISubCtx {
	public ISubCtx cloneCtx() {
		try {
			return (ISubCtx) clone();
		} catch (CloneNotSupportedException e) {
			throw new DsfRuntimeException(e);
		}
	}

}

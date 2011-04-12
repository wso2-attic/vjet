/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.event;

import org.w3c.dom.Node;

public interface IDomEventBindingListener {
	void eventBound(final Node elem, final String eventType);

	boolean isEventBinding(final Node elem, final String name);
}

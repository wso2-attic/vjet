/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.event.property;

import org.ebayopensource.dsf.ts.event.ISourceEventListener;



public interface IPropertyEventListener extends ISourceEventListener {
	void onPropertyAdded(AddPropertyEvent event);
	void onPropertyRenamed(RenamePropertyEvent event);
	void onPropertyRemoved(RemovePropertyEvent event);
}

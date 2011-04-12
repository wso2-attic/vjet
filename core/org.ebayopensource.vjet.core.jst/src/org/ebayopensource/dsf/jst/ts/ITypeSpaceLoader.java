/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.ts;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.ts.event.EventListenerStatus;
import org.ebayopensource.dsf.ts.event.ISourceEventCallback;
import org.ebayopensource.dsf.ts.event.group.AddGroupEvent;

public interface ITypeSpaceLoader {

	/**
	 * Load all the jst types in the list of groups into TypeSpace
	 * @param groupList
	 * @return EventListenerStatus
	 */	
	public EventListenerStatus<IJstType> loadJstTypesIntoTS(List<AddGroupEvent> groupList, ISourceEventCallback<IJstType> callback);
	
}

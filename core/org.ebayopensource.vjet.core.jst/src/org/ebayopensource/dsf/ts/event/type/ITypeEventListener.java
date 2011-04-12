/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.event.type;

import org.ebayopensource.dsf.ts.event.EventListenerStatus;
import org.ebayopensource.dsf.ts.event.ISourceEventCallback;
import org.ebayopensource.dsf.ts.event.ISourceEventListener;
import org.ebayopensource.dsf.ts.event.dispatch.IEventListenerHandle;

public interface ITypeEventListener<T> extends ISourceEventListener {
	EventListenerStatus<T> onTypeAdded(AddTypeEvent<T> event, IEventListenerHandle handle, ISourceEventCallback<T> callBack);
	EventListenerStatus<T> onTypeRenamed(RenameTypeEvent event, IEventListenerHandle handle, ISourceEventCallback<T> callBack);
	EventListenerStatus<T> onTypeModified(ModifyTypeEvent event, IEventListenerHandle handle, ISourceEventCallback<T> callBack);
	EventListenerStatus<T> onTypeRemoved(RemoveTypeEvent event, IEventListenerHandle handle, ISourceEventCallback<T> callBack);
}

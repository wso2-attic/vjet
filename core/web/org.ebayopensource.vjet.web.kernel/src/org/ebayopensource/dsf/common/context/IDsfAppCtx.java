/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.context;


/**
 * Concrete implementations should contain all the meaningful data and behavior
 * abstractions that need to be centrally accessed by a given usecases components
 * and other application functionality.
 * 
 * The DsfContext has set/get IDsfAppCtx methods.  The concrete implementation
 * should use a static method called ctx() that will use the DsfCtx as
 * a thread local mule to actually hold onto the the app context instance and then just
 * does the cast.
 */
public interface IDsfAppCtx {
	
	IInputParamValueProvider getInputDataProvider();
	
	void setApp(final IDsfApp app);
	IDsfApp getApp();
}

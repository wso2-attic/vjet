/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.service.client;

import java.util.Map;

import org.ebayopensource.dsf.html.js.IJsContentGenerator;
import org.ebayopensource.dsf.service.IServiceSpec;
import org.ebayopensource.dsf.service.ServiceConfig;

public interface IClientServiceHandlerRegistry {

	void registerSvcReqHandler(final IServiceSpec svcSpec);
	void registerSvcReqHandler(final IServiceSpec svcSpec, final String opName);
	void registerSvcRespHandler(final String svcId, final IJsContentGenerator func);
	void registerSvcRespHandler(final String svcId, final IJsContentGenerator callback, final IJsContentGenerator errorHandler);
	
	Map<String,ServiceConfig> getServiceConfigs();
	ServiceConfig getServiceConfig(String svcName);
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.liveconnect.client;

public interface IDLCClient {
	
	String DLC_TOKEN = "dlc";
	
	byte[] getClientJs();
	
	String getDlcEventHandler(NativeEvent event);
	
	DLCEvent parse(String payload);
	
	String getPayload(DLCEvent event);
	
	String getSessionId(String payload);
	
	String getReqId(String payload);
}

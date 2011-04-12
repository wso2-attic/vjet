/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.liveconnect;

import java.nio.channels.SocketChannel;

/**
 * Listener interface for DLCServer
 */
public interface IDLCReceiver {
	void received(SocketChannel channel, String message);
	void connected(SocketChannel channel);
	void closed(SocketChannel channel);
	DLCHttpResponse get(SocketChannel channel, DLCHttpRequest request);
}

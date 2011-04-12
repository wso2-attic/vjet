/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import org.ebayopensource.dsf.liveconnect.IDLCDispatcher;

public interface IDLCMsgHandler {

	public void onLoad(String msg, DapSession session, IDLCDispatcher dispatcher);
	public void handle(String msg, DapSession session, IDLCDispatcher dispatcher);
	public void onUnload(String msg, DapSession session, IDLCDispatcher dispatcher);

	public String getNameSpace();
}

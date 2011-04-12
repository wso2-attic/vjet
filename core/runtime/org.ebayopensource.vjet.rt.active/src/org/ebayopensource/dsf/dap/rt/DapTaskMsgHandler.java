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

public class DapTaskMsgHandler implements IDLCMsgHandler {

	public static final String NAMESPACE = "task";
	
	@Override
	public String getNameSpace() {
		return NAMESPACE;
	}

	@Override
	public void handle(String msg, DapSession session, IDLCDispatcher dispatcher) {
		//the order of the below two statement could not be changed
		session.getCaptureReplay().executed(msg);
		session.getCurrentView().getEngine().executeTask(msg);
	}

	@Override
	public void onLoad(String msg, DapSession session, IDLCDispatcher dispatcher) {
		//NOOP
	}

	@Override
	public void onUnload(String msg, DapSession session,
			IDLCDispatcher dispatcher) {
		//NOOP
	}

}

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
import org.ebayopensource.dsf.liveconnect.client.DLCEvent;
import org.ebayopensource.dsf.liveconnect.client.IDLCClient;

public class DapEventMsgHandler implements IDLCMsgHandler {

	private static DapEventMsgHandler s_instance = new DapEventMsgHandler();

	private DapEventMsgHandler() {
	}

	public static DapEventMsgHandler getInstance() {
		return s_instance;
	}

	private IDLCClient getDlcClient() {
		return DapCtx.ctx().getDapConfig().getDlcClient();
	}

	@Override
	public void handle(String msg, DapSession session, IDLCDispatcher dispatcher) {
		DLCEvent event = getDlcClient().parse(msg);

		DapCaptureReplay captureReplay = session.getCaptureReplay();
		if (captureReplay != null && captureReplay.received(event)){
			return;
		}

		session.onReceive(event);
	}

	@Override
	public void onLoad(String msg, DapSession session, IDLCDispatcher dispatcher) {
	}

	@Override
	public void onUnload(String msg, DapSession session, IDLCDispatcher dispatcher) {
	}

	@Override
	public String getNameSpace() {
		return "";
	}

}

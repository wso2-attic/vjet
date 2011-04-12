/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.cnr;

import org.ebayopensource.dsf.dap.rt.DapCtx;
import org.ebayopensource.dsf.dap.rt.DapSession;
import org.ebayopensource.dsf.dap.rt.IDapConsoleHandler;

public class DapConsoleReplayHandler implements IDapConsoleHandler {

	public static final String REPLAY = "REPLAY";
	public static final String FAST_REPLAY = "FAST_REPLAY";
	public static final String SLOW_REPLAY = "SLOW_REPLAY";
	
	private static final String[] INPUTS = {REPLAY, FAST_REPLAY, SLOW_REPLAY};
	
	//
	// Singleton
	//
	private static DapConsoleReplayHandler s_instance = new DapConsoleReplayHandler();
	private DapConsoleReplayHandler(){}
	public static DapConsoleReplayHandler getInstance(){
		return s_instance;
	}
	
	//
	// Satisfy IDapConsoleHandler
	//
	public String[] getSupportedInputs(){
		return INPUTS;
	}
	
	public void handle(String input){
		String cmd = input.toUpperCase();
		if (cmd.startsWith(REPLAY)) {
			replay(ReplaySpeed.NORMAL);
		}
		else if (cmd.startsWith(FAST_REPLAY)) {
			replay(ReplaySpeed.FAST);
		}
		else if (cmd.startsWith(SLOW_REPLAY)) {
			replay(ReplaySpeed.SLOW);
		}
	}
	
	private void replay(ReplaySpeed speed){
		DapSession session = DapCtx.ctx().getSession();
		if (session != null){
			session.startReplay();
			session.getCaptureReplay().replayCapture(speed);
		}
		else {
			System.out.println("There is no active session");
		}
	}
}

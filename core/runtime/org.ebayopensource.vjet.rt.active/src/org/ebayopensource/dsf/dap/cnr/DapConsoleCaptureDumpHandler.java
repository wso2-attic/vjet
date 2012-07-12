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
import org.ebayopensource.dsf.dap.rt.IDapConsoleHandler;
import org.ebayopensource.dsf.common.xml.IIndenter;

public class DapConsoleCaptureDumpHandler implements IDapConsoleHandler {

	public static final String DUMP_CAPTURE = "DUMP_CAPTURE";
	
	private static final String[] INPUTS = {DUMP_CAPTURE};
	
	//
	// Singleton
	//
	private static DapConsoleCaptureDumpHandler s_instance = new DapConsoleCaptureDumpHandler();
	private DapConsoleCaptureDumpHandler(){}
	public static DapConsoleCaptureDumpHandler getInstance(){
		return s_instance;
	}
	
	//
	// Satisfy IDapConsoleHandler
	//
	public String[] getSupportedInputs(){
		return INPUTS;
	}
	
	public void handle(String input){
		if (DUMP_CAPTURE.equalsIgnoreCase(input)) {
			dumpEventCapture();
		}
	}
	
	public void dumpEventCapture() {

		DapCaptureData capturedData = DapCtx.ctx().getSession().getCaptureReplay().getCapturedData();
		if (capturedData == null) {
			System.err.println("capture is not enabled");
			return;
		}
		
		new DapCaptureXmlSerializer(new IIndenter.Pretty())
			.serialize(capturedData, System.out);
		
//		for (EventCapture capture: capturedData.getAllEventCaptures()){
//			System.err.println(capture.getEventMessage());
//			Iterator<IActionInfo> actionsItr = capture.getActions();
//			while (actionsItr.hasNext()) {
//				IActionInfo info = actionsItr.next();
//				if (info instanceof DlcSend) {
//					DlcSend sendActionInfo = (DlcSend)info;
//					System.err.println("  SEND -> " + sendActionInfo.getMessage());
//				}
//				else if (info instanceof DlcRnR) {
//					DlcRnR rnrActionInfo = (DlcRnR)info;
//					System.err.println("  REQUEST -> " + rnrActionInfo.getRequest());
//					System.err.println("    RESPONSE -> " + rnrActionInfo.getResponse());
//				}
//				else {
//					System.err.println("  " + info);
//				}
//			}
//		}
	}
}

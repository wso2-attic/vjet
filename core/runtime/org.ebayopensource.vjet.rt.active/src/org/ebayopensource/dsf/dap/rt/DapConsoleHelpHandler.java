/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

public class DapConsoleHelpHandler implements IDapConsoleHandler {

	public static final String HELP = "HELP";
	
	private static final String[] INPUTS = {HELP};

	//
	// Satisfy IDapConsoleHandler
	//
	public String[] getSupportedInputs(){
		return INPUTS;
	}
	
	public void handle(String input){
		if (HELP.equalsIgnoreCase(input)) {
			printHelpMenu();
		}
	}
	
	//
	// Private
	//
	private void printHelpMenu(){
		
		DapConfig config = DapCtx.ctx().getDapConfig();
		StringBuilder sb;
		for (IDapConsoleHandler h: config.getConsoleHandlers()){
			if (h.getSupportedInputs() == null){
				continue;
			}
			sb = new StringBuilder();
			for (String s: h.getSupportedInputs()){
				if (sb.length() > 0){
					sb.append("|");
				}
				sb.append(s);
			}
			sb.append(" (handled by ").append(h.getClass().getName());
			System.out.println(sb.toString());
		}
	}
}

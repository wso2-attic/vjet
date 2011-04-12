/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class StreamGobbler extends Thread {
	InputStream is;
	String type;

	private String output = "";

	StreamGobbler(InputStream is, String type) {
		this.is = is;
		this.type = type;
	}

	public void run() {

		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		StringBuffer buffer = new StringBuffer();
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
//				System.out.println(type + "> " + line);
				buffer.append(line);
			}
			is.close();
			isr.close();
			br.close();
			output = buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getOutput() {
		return output;
	}
	
	public String getType() {
		return type;
	}
}
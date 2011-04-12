/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests;

import java.io.IOException;

public class ReadProcStream {
	private String output = "";
	private String errors = "";
	
	public String getErrors() {
		return errors;
	}

	public String getOutput() {
		return output;
	}

	public void readStreams(Process p) throws InterruptedException, IOException {
		// any error message?
		StreamGobbler errorStreamConsumer = new StreamGobbler(p
				.getErrorStream(), "ERROR");

		// any output?
		StreamGobbler outputStreamConsumer = new StreamGobbler(p
				.getInputStream(), "OUTPUT");

		// kick them off
		errorStreamConsumer.start();
		outputStreamConsumer.start();

		// any error
		p.waitFor();

		output = outputStreamConsumer.getOutput();
		errors = errorStreamConsumer.getOutput();
	}
}

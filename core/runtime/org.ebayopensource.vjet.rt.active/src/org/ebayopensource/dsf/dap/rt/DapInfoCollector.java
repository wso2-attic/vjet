/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.ebayopensource.dsf.liveconnect.IDLCDispatcherInfoCollector.VerboseInfoCollector;

public class DapInfoCollector extends VerboseInfoCollector {

	public DapInfoCollector(PrintStream verboseStream) {
		super(verboseStream);
	}
	
	public void log(String msg){
		if (m_verboseStream != null){
			m_verboseStream.append("\n").append(msg);
		}
	}
	
	public void log(String msg, Throwable t){
		if (m_verboseStream != null){
			StringWriter sw = new StringWriter();
			t.printStackTrace(new PrintWriter(sw));
			m_verboseStream.append("\n").append(msg).append(":\n").append(sw.toString());
		}
	}
}

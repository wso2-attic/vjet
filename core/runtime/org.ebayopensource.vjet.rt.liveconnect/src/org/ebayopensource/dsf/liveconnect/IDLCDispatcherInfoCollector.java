/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.liveconnect;

import java.io.PrintStream;

/**
 * API for collecting message/request sent from server to browser
 * via DLC.
 */
public interface IDLCDispatcherInfoCollector {
	void send(String data);
	void request(String data);
	void response(String data);
	
	public static class VerboseInfoCollector 
		implements IDLCDispatcherInfoCollector {
		
		protected final PrintStream m_verboseStream;
		
		public VerboseInfoCollector(PrintStream verboseStream) {
			m_verboseStream = verboseStream;
		}

		public void request(String data) {
			if (m_verboseStream != null) {
				m_verboseStream.println("OUT (REQ) -> " + data);
			}
		}

		public void response(String data) {
			if (m_verboseStream != null) {
				m_verboseStream.println("IN (RESP) -> " + data);
			}
		}

		public void send(String data) {
			if (m_verboseStream != null) {
				m_verboseStream.println("OUT -> " + data);
			}
		}		
	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.contentassist;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;

public class LogListener implements ILogListener {
	File m_log;
	public LogListener() {
		try {
			IPath path = Platform.getLogFileLocation();
			File file = path.toFile();
			String canonicalPath = file.getCanonicalPath();
			String newFileName = canonicalPath.substring(0, canonicalPath.lastIndexOf(".")) + "_VJETTests.log";
			m_log = new File(newFileName);
		}
		catch (Exception e) {
			m_log = null;
		}
	}

	public void logging(IStatus status, String plugin) {
		if (m_log == null)
			return;
			
		try {	
			FileWriter writer = new FileWriter(m_log, true);
			BufferedWriter bufWriter = new BufferedWriter(writer);
	
			bufWriter.write(status.getMessage());
			bufWriter.newLine();
			bufWriter.flush();
			writer.close();
			bufWriter.close();
		}
		catch (Exception e) {
			
		}

	}

}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.prebuild;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * 
 * Used by the prebuild processor to prepare a file for parsing.
 *  
 * 
 *
 */
public class FileUtil {
	/**
	 * Reads a file and sends it to a buffer for parsing.
	 * 
	 * @param fileName file to parse
	 * @param characterEncoding 
	 * @return a string buffer
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String readFile(String fileName, String characterEncoding)
	throws UnsupportedEncodingException, FileNotFoundException, IOException {
	FileInputStream fis;
	InputStreamReader isr;
	StringBuffer sb = new StringBuffer(75000);
	char[] buf = new char[4096];
	int numRead;
	fis = new FileInputStream(fileName);
	isr = new InputStreamReader(fis, characterEncoding);
	do {
		numRead = isr.read(buf, 0, buf.length);
		if (numRead > 0) {
			sb.append(buf, 0, numRead);
		}
	} while (numRead >= 0);
	isr.close();
	fis.close();
	return sb.toString();
}
}

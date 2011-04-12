/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.loader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

public class VjFetcher {
	
	private static boolean s_verbose = false;

	public String fetch(final VjUrl vjUrl) {
		
		if (vjUrl == null){
			throw new RuntimeException("url is null");
		}
		
		if(s_verbose){
			System.out.println("Fetching: " + vjUrl.getExternalForm()); //KEEPME
		}

		try {	
			final URL url = new URL(vjUrl.getExternalForm());

			Reader r = new InputStreamReader(new FileInputStream(url.getPath()));// TODO handle http etc
			
			char[] buffer = new char[512];
			int cursor = 0;
			for (;;) {
				int n = r.read(buffer, cursor, buffer.length - cursor);
				if (n < 0) { break; }
				cursor += n;
				if (cursor == buffer.length) {
					char[] tmp = new char[buffer.length * 2];
					System.arraycopy(buffer, 0, tmp, 0, cursor);
					buffer = tmp;
				}
			}
			String source = new String(buffer, 0, cursor);
			if(s_verbose){
				System.out.println("... source = \n" + source); //KEEPME
			}
			return source;
		}
		catch(IOException e){
			e.printStackTrace();
			throw new RuntimeException("Fail to read from input stream.", e);
		}
	}
}
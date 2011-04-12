/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.js.dbgp;

import java.io.File;

/**
 * 
 * 
 *  Ouyang
 *
 */
public final class PathUtil {

	public static String normalize(String filePath) {
		if (!filePath.startsWith("file:")
				&& !filePath.startsWith("jar:")
				&& !filePath.startsWith("dbgp:")) {
			File sourceFile = new File(filePath);
			filePath = sourceFile.toURI().toASCIIString();
		}
	
		if(filePath.contains("#")){
			filePath = filePath.substring(0,filePath.indexOf("#"));
		}
		return filePath;
	}

}

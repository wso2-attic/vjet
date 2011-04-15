/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.ts.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JstSrcFileCollector {
	
	private File m_srcRoot;

	public List<File> getJsSrcFiles(String srcFolder) {
		
		return getJsSrcFiles(new File(srcFolder));
	}
	
	public List<File> getJsSrcFiles(File srcFolder) {
		
		if(m_srcRoot==null && srcFolder.isDirectory()){
			m_srcRoot = srcFolder;
		}
		
		ArrayList<File> fileList = new ArrayList<File>();
				
		if (srcFolder.exists() && srcFolder.isDirectory()) {
			File[] files = srcFolder.listFiles();
			
			for (int i = 0; i < files.length; i++) {
				
				File file = files[i];
				
				if (file.isFile()) {
					
					String fileName = file.getName();
					if (fileName.matches(".*\\.js") || fileName.matches(".*\\.vjo")) {
						fileList.add(file);
					}
				}
				else if (file.isDirectory()) {
					
		
					if(file.getAbsolutePath().substring((int) m_srcRoot.getAbsolutePath().length()).contains(".")){
						continue;
					}
					
					
					fileList.addAll(getJsSrcFiles(file));
				}
			}
		}		
		
		return fileList;
	}
	
	public static void main(String[] args) {
		
		JstSrcFileCollector coll = new JstSrcFileCollector();
		
		List<File> list = coll.getJsSrcFiles(args[0]);
		
		for (File f : list) {
			System.out.println(f.getPath());
		}
		
	}

}

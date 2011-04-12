/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;

import org.ebayopensource.dsf.util.JavaSourceLocator;

/**
 * Set of static helper methods for reading in files
 */
public class FileUtils extends Console {
	

	public static String getPath(Class clz, String project, String hack) {
		
		// look in directory we are running
		final File dir1 = new File(".");
		String currentDir = null ;
		try {
			currentDir = dir1.getCanonicalPath();
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage()) ;
		}
		
		if (project != null){
			int last = currentDir.lastIndexOf("\\");
			currentDir = currentDir.substring(0, last+1) + project;
		}

		final Package pkg = clz.getPackage();
		final String packageName = pkg.getName();
//		out("package: " + packageName) ;
		
//		out("separator: " + File.separator) ;
		
		String packagePath = packageName.replace('.', File.separatorChar) ;
		String path ;
		if (hack == null) {
			path = currentDir + File.separator + packagePath ;
		}
		else {
			path = currentDir + File.separator + hack + File.separator + packagePath ;
		}
		
		path = path.replace(File.separatorChar, '/') ;
		
//		System.out.println("path: " + path ) ;
		return path ;
	}
	
	public static String getPackageNameFromFileDir(String filePath, String project, String hack) {
		// look in directory we are running		
		String currentDir = null ;
		try {
			// get current directory path
			currentDir = new File(".").getCanonicalPath();
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage()) ;
		}
		
		if (project != null){
			int last = currentDir.lastIndexOf("\\");
			currentDir = currentDir.substring(0, last+1) + project;
		}
		// add hack directory ie., "src" to the end of it
		if (hack != null) {
			currentDir = currentDir + File.separator + hack;
		}
		
		currentDir = currentDir.replace(File.separatorChar, '/') ;	
		currentDir = currentDir.replace(File.separatorChar, '\\') ;	
		String packageName = filePath.substring(currentDir.length()+1);
		packageName = packageName.replace('/', '.');
		packageName = packageName.replace('\\', '.');
		return packageName ;
	}
	
	public static String getPath(String pkgName, String project, String hack) {
		
		
		final String packageName = pkgName;//pkg.getName();
		String packagePath = packageName.replace('.', '/') ;
		
		final JavaSourceLocator sourceLocator = JavaSourceLocator.getInstance();
		URLClassLoader classLoader = sourceLocator.getClassLoader();
		URL[] urls = classLoader.getURLs();
		
		for(URL url : urls){
			if(url.getProtocol().equalsIgnoreCase("jar")){
				
			}else if(url.getProtocol().equalsIgnoreCase("file")){
				
			}
		}
		
		// look in directory we are running
		final File dir1 = new File(".");
		String currentDir = null ;
		try {
			currentDir = dir1.getCanonicalPath();
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage()) ;
		}
		
		if (project != null){
			int last = currentDir.lastIndexOf("\\");
			currentDir = currentDir.substring(0, last+1) + project;
		}


		String path ;
		if (hack == null) {
			path = currentDir + File.separator + packagePath ;
		}
		else {
			path = currentDir + File.separator + hack + File.separator + packagePath ;
		}
		
		path = path.replace(File.separatorChar, '/') ;
		
//		System.out.println("path: " + path ) ;
		return path ;
	}
	
	public static String getStringFromFile(String filePath) {
		try {
			FileInputStream fis = new FileInputStream(filePath) ;
			BufferedInputStream bis = new BufferedInputStream(fis) ;
			byte[] buf = new byte[99999] ;
			int actualBytesRead = bis.read(buf, 0, buf.length) ;
			bis.close() ;
			fis.close() ;
			return new String(buf, 0, actualBytesRead) ;
		}
		catch(Exception e) { 
			throw new RuntimeException(e.getMessage()) ;
		}		
	}
}

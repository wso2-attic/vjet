/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.loader;

import java.io.IOException;
import java.net.URL;

import org.ebayopensource.dsf.common.resource.ResourceUtil;

public class VjResolver {
	
	private static final String SCHEME_TYPE = "type";
	private static final String SCHEME_FILE = "file";
	private static final String SCHEME_HTTP = "http";
	
	private static boolean s_verbose = false;
	
	public VjUrl resolve(final String identifier, final VjUrl parentUrl){
		
		if (identifier == null){
			throw new RuntimeException("identifier cannot be null");
		}
		
		if(s_verbose){
			System.out.println("Resolving: " + identifier); //KEEPME
		}
		
		final VjUrl vjUrl = toVjUrl(identifier, parentUrl);
		if (vjUrl == null){
			return null;
		}
		
		if (SCHEME_TYPE.equals(vjUrl.getScheme())){
			resolveType(vjUrl);
		}
		else if (SCHEME_FILE.equals(vjUrl.getScheme())){
			resolveFile(vjUrl);
		}
		
		if(s_verbose){
			System.out.println("... url = " + vjUrl.getExternalForm()); //KEEPME
		}
		
		return vjUrl;
	}

	protected void resolveType(final VjUrl vjUrl){
		
		String path = vjUrl.getPath();
		if (path.endsWith(".js")){
			path = path.substring(0, path.length()-3);
		}
		path = path.replace(".", "/") + ".js";
		if(s_verbose){
			System.out.println("... path = " + path); //KEEPME
		}
		vjUrl.setPath(path);
		
		URL sourceUrl = null;
//		String clzName = null;
		do {
//			try {
//				//load from source if available
//				sourceUrl = JavaSourceLocator.getInstance().getSourceUrl(uri, ".js");
//				clzName = uri;
//			}
//			catch (Throwable e) {
//				//Do nothing //KEEPME
////				e.printStackTrace();
//			}
//			if (sourceUrl == null) {
				int index = path.lastIndexOf("/");
				String resourceName = path;
				String relDir = "";
				if (index > 0) {
					relDir = path.substring(0, index);
					resourceName = path.substring(index + 1);
				}
				try {
					if(s_verbose){
						System.out.println("... relDir = " + relDir); //KEEPME
						System.out.println("... resourceName = " + resourceName); //KEEPME
					}
					sourceUrl = ResourceUtil.getResource(relDir, resourceName);
					if (sourceUrl != null){
						vjUrl.setScheme(SCHEME_FILE)
							.setRelativePath(relDir)
							.setResourceName(resourceName);
					}
//					clzName = uri;
//					System.out.println(sourceUrl);
				} catch (IOException e) {
					//Do nothing
//					e.printStackTrace();
				}
//			}
		} 
		while(sourceUrl==null && (path=getContainer(path))!=null);
	
		if (sourceUrl != null){
			if (vjUrl.getScheme() != null){
				vjUrl.setScheme(SCHEME_TYPE);
			}
			vjUrl.setExternalForm(sourceUrl.toExternalForm());
		}
	}
	
	protected void resolveFile(final VjUrl vjUrl){

		String path = vjUrl.getPath();
		if (path == null){
			return;
		}
		if (!path.endsWith(".js")){
			path += ".js";
			vjUrl.setPath(path);
		}
		int index = path.lastIndexOf("/");
		String resourceName = path;
		String relDir = "";
		if (index > 0) {
			relDir = path.substring(0, index);
			resourceName = path.substring(index + 1);
		}
		
		if(s_verbose){
			System.out.println("... relDir = " + relDir); //KEEPME
			System.out.println("... resourceName = " + resourceName); //KEEPME
		}
		
		try {
			URL resourceUrl = ResourceUtil.getResource(relDir, resourceName);
			if (resourceUrl != null){
				vjUrl.setRelativePath(relDir)
					.setResourceName(resourceName)
					.setExternalForm(resourceUrl.toExternalForm());
			}
		} 
		catch (IOException e) {
			// Not found //KEEPME
		}
	}
	
	//
	// Private
	//
	private VjUrl toVjUrl(final String identifier, final VjUrl parentUrl){
		
		final VjUrl vjUrl = new VjUrl();
		
		final String scheme = extractScheme(identifier);
		if(s_verbose){
			System.out.println("... scheme = " + scheme); //KEEPME
		}
		vjUrl.setScheme(scheme);
		
		int index = identifier.indexOf(":");
		final String remaining = (index < 0) ? identifier : identifier.substring(index+1);
		
		if(s_verbose){
			System.out.println("... remaining = " + remaining); //KEEPME
		}
		
		if (SCHEME_TYPE.equals(scheme)){
			vjUrl.setPath(remaining);
		}
		else if (SCHEME_FILE.equals(scheme)){
			String path = remaining;
			if (path.startsWith("///")){
				path = identifier.substring(3);
			}
			else if (path.startsWith("/")){
				path = identifier.substring(1);
			}
			else if (path.startsWith("./")){
				String parentPath = getParentPath(parentUrl);
				if (parentPath == null){
					return null;
				}
				path = parentPath + path.substring(1);
			}
			else if (path.startsWith("../")){
				String parentPath = getParentPath(parentUrl);
				if (parentPath == null){
					return null;
				}
				index = parentPath.lastIndexOf("/");
				if (index > 0){
					path = parentPath.substring(0,index) + path.substring(2);
				}
				else {
					path = path.substring(3);
				}
			}
			vjUrl.setPath(path);
		}
		else {
			// TODO
		}
		
		if(s_verbose){
			System.out.println("... path = " + vjUrl.getPath()); //KEEPME
		}

		return vjUrl;
	}
	
	private String extractScheme(final String identifier){
		if (identifier.startsWith(SCHEME_FILE)){
			return SCHEME_FILE;
		}
		else if (identifier.startsWith(SCHEME_HTTP)){
			return SCHEME_HTTP;
		}
		else if (identifier.startsWith(SCHEME_TYPE)){
			return SCHEME_TYPE;
		}
		
		if (!identifier.contains(":")){
			if (identifier.startsWith("./") || identifier.startsWith("..")){
				return SCHEME_FILE;
			}
			else {
				return SCHEME_TYPE;
			}
		}
		else {
			throw new RuntimeException("Non-supported scheme: " + identifier);
		}
	}
	
	private String getParentPath(final VjUrl parentUrl){
		if(s_verbose){
			System.out.println("... parentUrl = " + (parentUrl == null ? null : parentUrl.getExternalForm())); //KEEPME
		}
		if (parentUrl == null || parentUrl.getPath() == null){
			return null;
		}
		String parentPath = parentUrl.getPath();
		int index = parentPath.lastIndexOf("/");
		if (index > 0){
			parentPath = parentPath.substring(0, index);
		}
		
		return parentPath;
	}
	
	/**
	 * Returns the root class. Need to find the root class for nested
	 * types.
	 * @param identifier
	 * @return
	 */
	private static String getContainer(String identifier) {//resolve nested classes
		String[]parts =identifier.split("\\.");
		int len = parts.length;
		//return null if class is lowercase
		if (len <= 1 || parts[len - 2].toLowerCase().equals(parts[len - 2])) {
			return null;
		}
		String clz = parts[0];
		for (int i=1;i<len-1;i++) {
			clz += "." + parts[i];
		}
		return clz;
	}
}
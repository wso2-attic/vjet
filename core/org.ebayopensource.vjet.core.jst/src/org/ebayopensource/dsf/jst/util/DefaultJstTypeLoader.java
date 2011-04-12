/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
//package org.ebayopensource.dsf.jst.util;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Enumeration;
//import java.util.List;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipFile;
//
//import org.ebayopensource.dsf.jst.ts.IJstTypeLoader;
//import org.ebayopensource.dsf.jst.ts.util.JstSrcFileCollector;
//import org.ebayopensource.dsf.ts.event.group.AddGroupEvent;
//
//@Deprecated
//public class DefaultJstTypeLoader implements IJstTypeLoader {
//	
//	public DefaultJstTypeLoader() {
//		
//	}
//	
//	public List<SourceType> loadJstTypes(List<AddGroupEvent> groupList) {
//		
//		List<SourceType> typeList = new ArrayList<SourceType>();
//		
//		for (AddGroupEvent group : groupList) {
//			String groupName = group.getGroupName();
//			String groupPath = group.getGroupPath();
//			String srcPath = group.getSourcePath();
//			
//			File groupFolderOrFile = getGroupSrcFolder(groupPath, srcPath);
//			
//			typeList.addAll(loadJstTypesFromGroup(groupName, groupFolderOrFile));
//		}
//		
//		return typeList;
//		
//	}
//	
//	private List<SourceType> loadJstTypesFromGroup(String groupName, File groupFolderOrFile) {
//		
//		ArrayList<SourceType> srcTypeList = new ArrayList<SourceType>();
//		
//		if (groupFolderOrFile != null) {
//			
//			if (groupFolderOrFile.isDirectory()) {
//				return loadJstTypesFromProject(groupName, groupFolderOrFile);
//			}
//			else {
//				return loadJstTypesFromLibrary(groupName, groupFolderOrFile);
//			}
//		}
//		
//		return srcTypeList; // return empty list
//		
//	}
//	
//	private File getGroupSrcFolder(String groupPath, String srcPath) {
//		
//		if (groupPath != null) {
//			File groupFile = new File(groupPath);
//			
//			if (groupFile.exists()) {
//				if (!groupFile.isDirectory() || srcPath == null) {
//					return groupFile;
//				}
//				else {
//					File srcFolder = new File(groupFile, srcPath);
//					return srcFolder;
//				}
//			}
//		}
//		else if (srcPath != null) {
//			File srcFolder = new File(srcPath);
//			
//			if (srcFolder.exists() && srcFolder.isDirectory()) {
//				return srcFolder;
//			}			
//		}
//		
//		return null;
//		
//	}
//	
//	protected List<SourceType> loadJstTypesFromProject(String groupName, File srcFolder) {
//		
//		JstSrcFileCollector fileColl = new JstSrcFileCollector();
//		ArrayList<SourceType> srcTypeList = new ArrayList<SourceType>();
//		
//		if (srcFolder != null) {
//			
//			List<File> list = fileColl.getJsSrcFiles(srcFolder);
//			
//			for (File file : list) {
//								
//				try {			
//					srcTypeList.add(createType(groupName, srcFolder.getPath(), file));
//				}
//				catch (IOException e) {
//					
//				}								
//			}			
//		}
//		
//		return srcTypeList;
//		
//	}
//	
//	protected List<SourceType> loadJstTypesFromLibrary(String groupName, File libFile) {
//		List<SourceType> typeList = new ArrayList<SourceType>();
//		
//		String libFileName = libFile.getName().toLowerCase();
//
//		if (libFileName.endsWith(".zip") || libFileName.endsWith(".jar")) {
//			
//			try {				
//				ZipFile jarFile = new ZipFile(libFile);
//				Enumeration<? extends ZipEntry> enumeration = jarFile.entries();
//				
//				while (enumeration.hasMoreElements()) {
//	
//					ZipEntry elem = enumeration.nextElement();
//	
//					if (!elem.isDirectory()) {
//						typeList.add(createType(groupName, jarFile, elem));
//					}
//				}					
//			}
//			catch (IOException e) {
//				
//			}				
//		}		
//		
//		return typeList;
//	}
//	
//	protected SourceType createType(String groupName, String srcPath, File file) 
//		throws IOException 
//		{
//		String fileName = file.getPath();
//		
//		// replace / and \ with .
//		fileName = fileName.replace("\\", ".");
//		fileName = fileName.replace("/", ".");
//		
//		String srcFolder = srcPath;
//		srcFolder = srcFolder.replace("\\", ".");
//		srcFolder = srcFolder.replace("/", ".");
//		
//		int start = fileName.lastIndexOf(srcFolder);
//		start += srcFolder.length() + 1; // skip the trailing .
//		
//		int end = fileName.lastIndexOf(".js"); // remove .js from type name
//		
//		String typeName = fileName.substring(start, end);
//		
//		StringBuilder builder = new StringBuilder();
//		
//	
//		BufferedReader bufReader = new BufferedReader(new FileReader(file));
//	
//		String line = null;					
//	
//		while ((line = bufReader.readLine()) != null) {
//			builder.append(line);
//		}
//		
//		SourceType srcType = new SourceType(groupName, typeName, builder.toString());
//		
//		return srcType;
//		
//	}
//	
//	protected SourceType createType(String groupName, ZipFile jarFile, ZipEntry elem) throws IOException {
//		
//		String typeName = elem.getName();
//		int end = typeName.lastIndexOf(".js"); // remove .js from type name
//		
//		typeName = typeName.substring(0, end);		
//		typeName = typeName.replace("\\", ".");
//		typeName = typeName.replace("/", ".");
//		
//		InputStream stream = jarFile.getInputStream(elem);
//		byte[] bs = new byte[stream.available()];
//		stream.read(bs);
//		stream.close();
//		
//		String source = new String(bs);
//		
//		SourceType srcType = new SourceType(groupName, typeName, source);
//		
//		return srcType;
//		
//	}
//	
//}

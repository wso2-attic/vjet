/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.ts;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.dltk.mod.core.DLTKCore;

import org.ebayopensource.dsf.jsgroup.bootstrap.JsLibBootstrapLoader;
import org.ebayopensource.dsf.jst.ts.IJstTypeLoader;
import org.ebayopensource.dsf.jst.ts.util.JstSrcFileCollector;
import org.ebayopensource.dsf.ts.event.group.AddGroupEvent;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;

public class VjoJstTypeLoader implements IJstTypeLoader {

	protected SourceType createType(String groupName,
			String actualGroupFolderName, String srcPath, File file)
			throws IOException, URISyntaxException {

		String typeName = getTypeName(srcPath, file);

		FileInputStream stream;
		stream = new FileInputStream(file);
		byte[] bs = new byte[stream.available()];
		stream.read(bs);
		stream.close();

		String absolutePath = file.getAbsolutePath();
		absolutePath = absolutePath.replace("\\", "/");
		URI fileURI = null;
		if (absolutePath.contains(groupName)) {
			fileURI = new URI(absolutePath.substring(absolutePath
					.indexOf(groupName)));
		} else if (absolutePath.contains(actualGroupFolderName)) {
			fileURI = new URI(absolutePath.substring(
					absolutePath.indexOf(actualGroupFolderName)).replace(
					actualGroupFolderName, groupName));

		} else {
			fileURI = new URI(absolutePath);
		}

		TypeSpaceMgr.getInstance().getTypeToFileMap().put(
				groupName + "#" + typeName, fileURI);

		SourceType srcType = new SourceType(groupName, typeName,
				new String(bs), file);

		return srcType;
	}

	public List<SourceType> loadJstTypes(List<AddGroupEvent> groupList) {

		List<SourceType> typeList = new ArrayList<SourceType>();

		for (AddGroupEvent group : groupList) {
			String groupName = group.getGroupName();
			String groupPath = group.getGroupPath();
			String actualGroupFolderName = groupPath.substring(groupPath
					.lastIndexOf("\\") + 1);
			List<String> srcPathList = group.getSourcePathList();

			File groupFolderOrFile = getGroupSrcFolder(groupPath, null);
			List<String> bootStrapPath = group.getBootStrapList();
			if (bootStrapPath != null && bootStrapPath.size() != 0) {
				String bootstrapJS = getBootStrapJs(groupFolderOrFile, bootStrapPath);
                JsLibBootstrapLoader.load(bootstrapJS, groupName);
			}
			
			
			
			if (srcPathList == null || srcPathList.size() == 0) {
				typeList.addAll(loadJstTypesFromGroup(groupName,
						actualGroupFolderName, groupFolderOrFile));
			} else {
				for (String srcPath : srcPathList) {
					File srcFolder = getGroupSrcFolder(groupPath, srcPath);
					typeList.addAll(loadJstTypesFromGroup(groupName,
							actualGroupFolderName, srcFolder));
				}
			}
		}

		return typeList;

	}
	
	private String getBootStrapJs(File groupFolder, List<String> bootStrapPath) {
		StringBuilder bootStrapJs = new StringBuilder();
		for(String root: bootStrapPath){
			try {
				bootStrapJs.append(createType("bootstrap", "", root + "bootstrap.js", new File(groupFolder, root + File.separatorChar + "bootstrap.js")).getSource());
			} catch (IOException e) {
				// do nothing
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return bootStrapJs.toString();
	}

	private File getGroupSrcFolder(String groupPath, String srcPath) {

		if (groupPath != null) {
			File groupFile = new File(groupPath);

			if (groupFile.exists()) {
				if (!groupFile.isDirectory() || srcPath == null) {
					return groupFile;
				} else {
					File srcFolder = new File(groupFile, srcPath);
					return srcFolder;
				}
			}
		} else if (srcPath != null) {
			File srcFolder = new File(srcPath);

			if (srcFolder.exists() && srcFolder.isDirectory()) {
				return srcFolder;
			}
		}

		return null;

	}

	private List<SourceType> loadJstTypesFromGroup(String groupName,
			String actualGroupFolderName, File groupFolderOrFile) {

		ArrayList<SourceType> srcTypeList = new ArrayList<SourceType>();

		if (groupFolderOrFile != null) {

			if (groupFolderOrFile.isDirectory()) {
				return loadJstTypesFromProject(groupName,
						actualGroupFolderName, groupFolderOrFile);
			} else if (!TypeSpaceMgr.getInstance().existGroup(groupName)) {
				return loadJstTypesFromLibrary(groupName, groupFolderOrFile);
			}
		}

		return srcTypeList; // return empty list

	}

	private String getTypeName(String srcPath, File file) {
		String fileName = file.getPath();

		// replace / and \ with .
		fileName = fileName.replace("\\", ".");
		fileName = fileName.replace("/", ".");

		String srcFolder = srcPath;
		srcFolder = srcFolder.replace("\\", ".");
		srcFolder = srcFolder.replace("/", ".");

		int start = fileName.lastIndexOf(srcFolder);
		start += srcFolder.length() + 1; // skip the trailing .

		int end = fileName.lastIndexOf(".js"); // remove .js from type name

		String typeName = fileName.substring(start, end);
		return typeName;
	}

	protected List<SourceType> loadJstTypesFromProject(String groupName,
			String actualGroupFolderName, File srcFolder) {

		JstSrcFileCollector fileColl = new JstSrcFileCollector();
		ArrayList<SourceType> srcTypeList = new ArrayList<SourceType>();

		if (srcFolder != null) {

			List<File> list = fileColl.getJsSrcFiles(srcFolder);

			for (File file : list) {

				try {
					if (isVjoFile(file)) {
						srcTypeList.add(createType(groupName,
								actualGroupFolderName, srcFolder.getPath(),
								file));
					}

				} catch (IOException e) {

				} catch (URISyntaxException e) {
					DLTKCore.error(e.toString(), e);
				}
			}
		}

		return srcTypeList;

	}

	private boolean isVjoFile(File file) {
		if (file.exists()) {
			return file.getName().endsWith(VjetPlugin.VJO_SUBFIX);
		}
		return false;
	}

	protected List<SourceType> loadJstTypesFromLibrary(String groupName,
			File libFile) {
		List<SourceType> typeList = new ArrayList<SourceType>();

		String libFileName = libFile.getName().toLowerCase();

		if (CodeassistUtils.isBinaryPath(libFileName)) {
			ZipFile jarFile = null;
			try {
				jarFile = new ZipFile(libFile);
				Enumeration<? extends ZipEntry> enumeration = jarFile.entries();

				while (enumeration.hasMoreElements()) {

					ZipEntry elem = enumeration.nextElement();

					if (!elem.isDirectory()) {
						SourceType sourceType = createType(groupName, jarFile,
								elem);
						if (sourceType != null) {
							typeList.add(sourceType);
						}
					}
				}
			} catch (IOException e) {

			}finally{
				try {
					jarFile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}

		return typeList;
	}

	protected SourceType createType(String groupName, ZipFile jarFile,
			ZipEntry elem) throws IOException {

		String typeName = elem.getName();
		if (!CodeassistUtils.isVjetFileName(typeName)) {
			return null;
		}
		int end = typeName.lastIndexOf(VjetPlugin.VJO_SUBFIX); // remove .js





		typeName = typeName.substring(0, end);
		typeName = typeName.replace("\\", ".");
		typeName = typeName.replace("/", ".");

		InputStream stream = jarFile.getInputStream(elem);
		byte[] bs = new byte[stream.available()];
		stream.read(bs);
		stream.close();

		String source = new String(bs);

		File f = null;
		f = new File(jarFile.getName() + "!"
				+ elem.getName());
		
		SourceType srcType = new SourceType(groupName, typeName, source, f);

		return srcType;

	}

}

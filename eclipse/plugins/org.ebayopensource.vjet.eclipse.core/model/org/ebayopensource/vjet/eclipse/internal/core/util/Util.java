/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.generate.CodeStyle;
import org.ebayopensource.dsf.jsgen.shared.vjo.GeneratorCtx;
import org.ebayopensource.dsf.jsgen.shared.vjo.VjoGenerator;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.ts.group.IGroup;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjo.tool.codecompletion.StringUtils;
import org.ebayopensource.vjo.tool.typespace.SourceTypeName;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.mod.ast.Modifiers;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.mod.internal.core.NativeVjoSourceModule;
import org.eclipse.dltk.mod.internal.core.ScriptProject;

public class Util {
	private static final String METAFILE_NAME = "manifest.mf";

	private Util() {
	}

	public static int getModifiers(JstModifiers jstModifiers) {
		int modifiers = 0;
		if (jstModifiers.isPublic()) {
			modifiers |= Modifiers.AccPublic;
		} else if (jstModifiers.isProtected()) {
			modifiers |= Modifiers.AccProtected;
		} else if (jstModifiers.isPrivate()) {
			modifiers |= Modifiers.AccPrivate;
		} else {
			// default
			modifiers |= Modifiers.AccDefault;
		}

		if (jstModifiers.isAbstract()) {
			modifiers |= Modifiers.AccAbstract;
		}
		if (jstModifiers.isFinal()) {
			modifiers |= Modifiers.AccFinal;
		}
		if (jstModifiers.isStatic()) {
			modifiers |= Modifiers.AccStatic;
		}

		return modifiers;
	}

	public static IJstType toJstType(IType type) {
		
		if(type.getParent() instanceof NativeVjoSourceModule){
			return ((NativeVjoSourceModule)type.getParent()).getJstType();
		}
		
		IFile file = (IFile) type.getResource();
		String name = type.getElementName();
		String groupName = type.getScriptProject().getElementName();

		if (file != null) {
			name = CodeassistUtils.getClassName(file);
		} else {
			groupName = JstTypeSpaceMgr.JS_NATIVE_GRP;
		}

		return TypeSpaceMgr.findType(groupName, name);
	}

	public static IType toIType(IJstType type) {
		ScriptProject project = CodeassistUtils.getScriptProject(type
				.getPackage().getGroupName());
		if (project == null) {
			return null;
		}
		return CodeassistUtils
				.findType((ScriptProject) project, type.getName());
	}

	private static void createJsNativeFile(SourceTypeName tname) {
		GeneratorCtx m_generatorCtx = new GeneratorCtx(CodeStyle.PRETTY);
		IJstType type = CodeassistUtils.findNativeJstType(tname.typeName());
		if (type == null) {
			return;
		}
		VjoGenerator writer = m_generatorCtx.getProvider().getTypeGenerator();
		try {
//			cacheText(name, type.toString());
			 cacheText(tname , writer.writeVjo(type).getGeneratedText());
		} catch (Exception e) {
			DLTKCore.error(e.toString(), e);
		}
	}
	
	public static File getCacheDir() {
		IPath path = DLTKCore.getDefault().getStateLocation();
		File file = path.toFile();
		if (!file.exists()) {
			file.mkdir();
		}
		return file;
	}

	/**
	 * The place to cache NativeType text info
	 * @return
	 */
	public static File getNativeTypeCacheDir(String groupName) {
		File file = getCacheDir();
		File nDirectory = new File(file, groupName);
		if (!nDirectory.exists()) {
			nDirectory.mkdir();
		}
		initDirStructure(nDirectory, groupName);
		return nDirectory;
	}
	
	public static boolean isNativeCacheDir(IPath fPath) {
		if (fPath == null) {
			return false;
		}
		IPath path = EnvironmentPathUtils.getLocalPath(fPath);
		if (path == null) {
			return false;
		}
		File file = path.toFile();
		if (file.exists()) {
			return file.getParentFile().equals(getCacheDir());
		} else {
			return false;
		}
	}

	private static void initDirStructure(File dir, String groupName) {
		List<String> metaInfo = getMetaInfo(dir);
		boolean updateJsFile = true;
		TypeSpaceMgr tsm = TypeSpaceMgr.getInstance();
		IGroup<IJstType> group = tsm.getController().getJstTypeSpaceMgr().getTypeSpace().getGroups().get(groupName);
		if (group == null) {
			return;
		}
		
		Iterator<IJstType> it = group.getEntities().values().iterator();
		while (it.hasNext()) {
			IJstType jstType = (IJstType) it.next();
			if (!isOuterType(jstType)) {
				break;
			}
			JstPackage packag = jstType.getPackage();
			String packageName = "";
			if (packag != null && !StringUtils.isBlankOrEmpty(packag.getName())) {
				packageName = packag.getName();
			}
			File file = getPackDir(dir, packageName);
			if (file.exists() && !updateJsFile) {
				continue;
			} else {
				createJsNativeFile(file, jstType);
			}
		}
	}
	
	private static void createJsNativeFile(File file, IJstType jstType) {
		GeneratorCtx m_generatorCtx = new GeneratorCtx(CodeStyle.PRETTY);
		VjoGenerator writer = m_generatorCtx.getProvider().getTypeGenerator();
		try {
//			cacheText(name, type.toString());
			File jsFile = new File(file, jstType.getSimpleName() + ".js");
			if (jsFile.exists()) {
				return;
			} else {
				writeText(jsFile , writer.writeVjo(jstType).getGeneratedText());
			}
		} catch (Exception e) {
			DLTKCore.error(e.toString(), e);
		}
	}
	
	private static void writeText(File jsFile, String generatedText) {
		FileOutputStream fo = null;
		try {
			fo = new FileOutputStream(jsFile);
			fo.write(generatedText.getBytes());
		} catch (FileNotFoundException e) {
			DLTKCore.error(e.toString(), e);
		} catch (IOException e) {
			DLTKCore.error(e.toString(), e);
		} finally {
			try {
				fo.close();
			} catch (IOException e) {
				DLTKCore.error(e.toString(), e);
			}
		}
	}
	
	private static File getPackDir(File dir, String packageName) {
		String path = packageName.replace(".", File.separator);
		File file = new File(dir, path);
		if (file.exists()) {
			return file;
		} else {
			file.mkdirs();
			return file;
		}
	}


	public static boolean isOuterType(IJstType jstType) {
		return jstType.getParentNode() == null;
	}

	private static List<String> getMetaInfo(File dir) {
		File metaFile = new File(dir, METAFILE_NAME);
		if (!metaFile.exists()) {
			try {
				metaFile.createNewFile();
				return new ArrayList<String>();
			} catch (IOException e) {
				return new ArrayList<String>();
			}
		}
		List<String> metaInfo = loadMetaFile(metaFile);
		return metaInfo;
	}

	private static List<String> loadMetaFile(File metaFile) {
		List<String> list = new ArrayList<String>();
		FileReader fr;
		try {
			fr = new FileReader(metaFile);
			BufferedReader reader = new BufferedReader(fr);
			String s  = reader.readLine();
			while (s != null) {
				list.add(s);
				s = reader.readLine();
			}
			return list;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<String>();
		
	}

	private static void cacheText(SourceTypeName tname, String generatedText) {
		File file = getNativeTypeCacheDir(tname.groupName());
		if (!file.exists()) {
			return;
		}
		File jsFile = new File(file, tname.typeName() + ".js");
		FileOutputStream fo = null;
		try {
			fo = new FileOutputStream(jsFile);
			fo.write(generatedText.getBytes());
		} catch (FileNotFoundException e) {
			DLTKCore.error(e.toString(), e);
		} catch (IOException e) {
			DLTKCore.error(e.toString(), e);
		} finally {
			try {
				fo.close();
			} catch (IOException e) {
				DLTKCore.error(e.toString(), e);
			}
		}
	}

	public static File getNativeTypeCacheFile(SourceTypeName tname) {

		File file = Util.getNativeTypeCacheDir(tname.groupName());
		if (!file.exists()) {
			return null;
		}
		File jsFile = new File(file, tname.typeName() + ".js");
		if (!jsFile.exists()) {
			createJsNativeFile(tname);
		}
		return jsFile;
	}
	
	
	/**
	 * Sets or unsets the given resource as read-only in the file system.
	 * It's a no-op if the file system does not support the read-only attribute.
	 * 
	 * @param resource The resource to set as read-only
	 * @param readOnly <code>true</code> to set it to read-only, 
	 *		<code>false</code> to unset
	 */
	public static void setReadOnly(IResource resource, boolean readOnly) {
		if (isReadOnlySupported()) {
			ResourceAttributes resourceAttributes = resource.getResourceAttributes();
			if (resourceAttributes == null) return; // not supported on this platform for this resource
			resourceAttributes.setReadOnly(readOnly);
			try {
				resource.setResourceAttributes(resourceAttributes);
			} catch (CoreException e) {
				// ignore
			}
		}
	}
	
	
	/**
	 * Returns whether the local file system supports accessing and modifying
	 * the given attribute.
	 */
	protected static boolean isAttributeSupported(int attribute) {
		return (EFS.getLocalFileSystem().attributes() & attribute) != 0;
	}
	
	
	
	/**
	 * Returns whether the given resource is read-only or not.
	 * @param resource
	 * @return <code>true</code> if the resource is read-only, <code>false</code> if it is not or
	 * 	if the file system does not support the read-only attribute.
	 */
	public static boolean isReadOnly(IResource resource) {
		if (isReadOnlySupported()) {
			ResourceAttributes resourceAttributes = resource.getResourceAttributes();
			if (resourceAttributes == null) return false; // not supported on this platform for this resource
			return resourceAttributes.isReadOnly();
		}
		return false;
	}

	/**
	 * Returns whether the local file system supports accessing and modifying
	 * the read only flag.
	 */
	public static boolean isReadOnlySupported() {
		return isAttributeSupported(EFS.ATTRIBUTE_READ_ONLY);
	}

	
}

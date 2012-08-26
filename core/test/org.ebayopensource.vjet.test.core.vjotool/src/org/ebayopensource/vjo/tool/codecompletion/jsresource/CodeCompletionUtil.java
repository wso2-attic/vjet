/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.jsresource;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstParseController;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.jstojava.controller.JstParseController;
import org.ebayopensource.dsf.jstojava.loader.DefaultJstTypeLoader;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.ts.ITypeSpace;
import org.ebayopensource.dsf.ts.event.EventListenerStatus;
import org.ebayopensource.dsf.ts.event.ISourceEventCallback;
import org.ebayopensource.dsf.ts.event.group.AddGroupEvent;
import org.ebayopensource.dsf.ts.event.type.AddTypeEvent;
import org.ebayopensource.dsf.ts.event.type.ModifyTypeEvent;
import org.ebayopensource.dsf.ts.event.type.RemoveTypeEvent;
import org.ebayopensource.dsf.ts.event.type.RenameTypeEvent;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.dsf.util.JavaSourceLocator;
import org.ebayopensource.vjo.lib.IResourceResolver;
import org.ebayopensource.vjo.lib.LibManager;
import org.ebayopensource.vjo.lib.ResourceHelper;
import org.ebayopensource.vjo.lib.TsLibLoader;
import org.eclipse.core.runtime.FileLocator;

public class CodeCompletionUtil {
	public static Boolean fullyLoaded = true;
	public static String ARTIFACT_FOLDER = "artifact";
	public static final String GROUP_NAME = "test";
	private static IJstParseController controller;

	public static IJstParseController getJstParseController() {
		if (controller == null) {
			loadJsToTypeSpace();
		}
		return controller;
	}
	
	public static void setFolder(String folder){
		System.out.println("updating ARTIFACT_FOLDER=" + ARTIFACT_FOLDER);
		ARTIFACT_FOLDER = folder;
	}
	
	private static void promoteGlobals(JstTypeSpaceMgr jstTypeSpaceMgr) {
		jstTypeSpaceMgr.getTypeSpace().addAllGlobalTypeMembers(new TypeName(LibManager.JS_NATIVE_LIB_NAME,"Global"));
		jstTypeSpaceMgr.getTypeSpace().addToGlobalMemberSymbolMap("JQueryx", "$", "vjo.dsf.jqueryx.Jq.$");
		jstTypeSpaceMgr.getTypeSpace().addToGlobalTypeSymbolMap("JQueryx", "jQuery", "vjo.dsf.jqueryx.Jq");
		jstTypeSpaceMgr.getTypeSpace().addToGlobalTypeSymbolMap(LibManager.JS_NATIVE_LIB_NAME, "vjo", "vjo");
		jstTypeSpaceMgr.getTypeSpace().addToGlobalMemberSymbolMap(LibManager.JS_NATIVE_LIB_NAME, "alert", "Window.alert");
	}

	/**
	 * Load Typespace from current test project, all the js file placed at the
	 * ARTIFACT_FOLDER
	 * 
	 * @return
	 */
	public static JstTypeSpaceMgr loadJsToTypeSpace() {
		JstTypeSpaceMgr ts = getInitailTypeSpace();
		System.out.println("ARTIFACT_FOLDER=" + ARTIFACT_FOLDER);
		addAllToTypeSpace(ARTIFACT_FOLDER,ts);
		return ts;
	}
	
	/**
	 * Initialize the typespace with basic libs and global types
	 * @return
	 */
	public static JstTypeSpaceMgr getInitailTypeSpace() {
		JstTypeSpaceMgr ts = null;
		try {
			IResourceResolver jstLibResolver = org.ebayopensource.vjet.test.util.JstLibResolver
					.getInstance()
					.setSdkEnvironment(
							new org.ebayopensource.vjet.test.util.VJetSdkEnvironment(
									new String[0], "DefaultSdk"));

			LibManager.getInstance().setResourceResolver(jstLibResolver);
			
			controller = new JstParseController(new VjoParser());
			ts = new JstTypeSpaceMgr(controller, new DefaultJstTypeLoader());
			ts.initialize();
			TsLibLoader.loadDefaultLibs(ts);
			promoteGlobals(ts);
		} catch (Exception e) {
			fail();
		}
		return ts;
	}
	
	/**
	 *  Add the provided js file to the type space. Only the provided js file 
	 *  will be added to type space. The Js file should be the fully qualified 
	 *  name, like 
	 *  For js file ParentType.js: 
	 *  	- jsName : artifact.parent.ParentType  
	 *  
	 *  The js file should be provided without 
	 *  suffix. like:
	 *  1. If adding foo.js.txt, present in artifact/parent folder,
	 *  	- jsName : artifact.parent.foo
	 *  	- suffix : .js.txt
	 *  
	 *  2. If adding foo.txt.js
	 *   	- jsName : artifact.parent.foo
	 *  	- suffix : .txt.js
	 *  
	 *  3. If adding foo.js
	 *   	- jsName : artifact.parent.foo
	 *  	- suffix : .js
	 *  
	 * @param jsName	JS file to be added to type space (without suffix)
	 * @param suffix	suffix for js file (like ".js")
	 * @param ts		Typespace instance to be used for loading type
	 */
	public static void addJsToTypeSpace(String jsName, String suffix, JstTypeSpaceMgr ts){
		try {
			URL u = JavaSourceLocator.getInstance().getSourceUrl(CodeCompletionUtil.class);
			String fileLoc = getBasePath(u);
			fileLoc = fileLoc + ARTIFACT_FOLDER + "/" + jsName.replace(".", "/") + suffix;
			File file = new File(fileLoc);
			ts.processEvent(new AddTypeEvent(GROUP_NAME, file.getName(), 
					new String(readIntoChars(file))), new ISourceEventCallback<IJstType>() {
				public void onComplete(EventListenerStatus<IJstType> status) {
					synchronized (fullyLoaded) {
						fullyLoaded.notify();
					}
				}

				public void onProgress(float percent) {
					System.out.println("Percentage of completion " + percent);
				}
			});

			try {
				synchronized (fullyLoaded) {
					fullyLoaded.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			fail();
		}
	}
	
	public static void ModifyJsInTypeSpace(String jsName, String suffix, JstTypeSpaceMgr ts){
		try {
			URL u = JavaSourceLocator.getInstance().getSourceUrl(CodeCompletionUtil.class);
			String fileLoc = getBasePath(u);
			fileLoc = fileLoc + ARTIFACT_FOLDER + "/" + jsName.replace(".", "/") + suffix;
			File file = new File(fileLoc);
			ts.processEvent(new ModifyTypeEvent(GROUP_NAME, file.getName(), 
					new String(readIntoChars(file))), new ISourceEventCallback<IJstType>() {
				public void onComplete(EventListenerStatus<IJstType> status) {
					synchronized (fullyLoaded) {
						fullyLoaded.notify();
					}
				}

				public void onProgress(float percent) {
					System.out.println("Percentage of completion " + percent);
				}
			});

			try {
				synchronized (fullyLoaded) {
					fullyLoaded.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			fail();
		}
	}
	
	public static void removeJsFromTypeSpace(String jsName, JstTypeSpaceMgr ts){
		try {
			ts.processEvent(new RemoveTypeEvent(GROUP_NAME, jsName), 
					new ISourceEventCallback<IJstType>() {
				public void onComplete(EventListenerStatus<IJstType> status) {
					synchronized (fullyLoaded) {
						fullyLoaded.notify();
					}
				}

				public void onProgress(float percent) {
					System.out.println("Percentage of completion " + percent);
				}
			});

			try {
				synchronized (fullyLoaded) {
					fullyLoaded.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			fail();
		}
	}
	
	public static void renameJsInTypeSpace(TypeName old, String newName, JstTypeSpaceMgr ts){
		try {
			ts.processEvent(new RenameTypeEvent(old, newName), 
					new ISourceEventCallback<IJstType>() {
				public void onComplete(EventListenerStatus<IJstType> status) {
					synchronized (fullyLoaded) {
						fullyLoaded.notify();
					}
				}

				public void onProgress(float percent) {
					System.out.println("Percentage of completion " + percent);
				}
			});

			try {
				synchronized (fullyLoaded) {
					fullyLoaded.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			fail();
		}
	}
	
	/**
	 * Add all the available js files from the root folder. All the js files 
	 * present in this root folder and subfolders will be added to type space
	 * 
	 * @param root 	Root folder to be added to type space
	 * @param ts	Type Space
	 */
	public static void addAllToTypeSpace(String root, final JstTypeSpaceMgr ts){
		try {
			String grpPath = "";
			String srcPath = "";
			String jarPath = "";
			URL u = JavaSourceLocator.getInstance().getSourceUrl(
					CodeCompletionUtil.class.getName(), ".class");
			if (u == null){
				u = JavaSourceLocator.getInstance().getSourceUrl(CodeCompletionUtil.class.getName());
			}
			if(u.getProtocol().contains("bundleresource")){
				u = FileLocator.resolve(u);
			}
			
			if(u.getProtocol().equalsIgnoreCase("jar")){
				String path = u.getFile();
				if (path.startsWith("file:/")){
					path = path.substring("file:/".length());
				}
				jarPath = path.substring(0, path.indexOf(".jar")+".jar".length());
				grpPath = jarPath;
				srcPath = jarPath;
			} else {
				File baseDirDir = new File(getBasePath(u));
				File artifactDir = new File(baseDirDir,  ARTIFACT_FOLDER + "/");
				if(!artifactDir.exists()){
					artifactDir = new File(baseDirDir,"src");			
				}
				jarPath = artifactDir.getAbsolutePath();
				grpPath = artifactDir.getParent();
				srcPath = artifactDir.getName();
			}
			
	//		System.out.println("LAX : u : " + u + " u1 = " + u);
			
//			String fileLoc = u.getFile();
//			if( u.getFile().indexOf("src/")!=-1){
//				fileLoc = u.getFile().substring(0, u.getFile().indexOf("src/"));
//			}
		//	System.out.println("LAX : fileLoc : " + fileLoc);
		//	File file = new File(fileLoc + ARTIFACT_FOLDER + "/");
			System.out.println("groupPath loading to ts : " + grpPath);
			
			ts.processEvent(new AddGroupEvent(GROUP_NAME, grpPath, "artifact",
					null, getDefaultLibList()), new ISourceEventCallback<IJstType>() {
				public void onComplete(EventListenerStatus<IJstType> status) {
					synchronized (fullyLoaded) {
						fullyLoaded.notify();
						
						ts.getTypeSpace().addToGlobalTypeSymbolMap(
								LibManager.VJO_SELF_DESCRIBED, "vjo", "vjo");
						ts.getTypeSpace().addToGlobalMemberSymbolMap(
								JstTypeSpaceMgr.JS_BROWSER_GRP, "alert", "Window.alert");
						
					}
				}

				public void onProgress(float percent) {
					System.out.println("Percentage of completion " + percent);
				}
			});

			try {
				synchronized (fullyLoaded) {
					fullyLoaded.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getLocalizedMessage());
		}
	}

	private static List<String> getDefaultLibList() {
		List<String> list = new ArrayList<String>();
		String[] defaultLibs = TsLibLoader.getDefaultLibNames();
		for (int i = 0; i < defaultLibs.length; i++) {
			list.add(defaultLibs[i]);
		}
		return list;
	}

	/**
	 * @param pkg
	 * @param js
	 * @return
	 * @deprecated
	 */
	public static JstTypeSpaceMgr loadJsToTypeSpace(String pkg, String js) {
		JstTypeSpaceMgr ts = null;
		try {
			IJstParseController controller = new JstParseController(
					new VjoParser());
			ts = new JstTypeSpaceMgr(controller, new DefaultJstTypeLoader());
			ts.initialize();
			ts.loadLibrary(ResourceHelper.getInstance().getJsNativeSerializedStream(),
					JstTypeSpaceMgr.JS_NATIVE_GRP);

			URL url = CodeCompletionUtil.class.getClassLoader().getResource(
					pkg + js);
			String path = url.getFile();

			int end = path.indexOf(pkg);
			String groupFullPath = path.substring(0, end - 1);
			int lastSlashIdx = groupFullPath.lastIndexOf("/");
			String groupPath = groupFullPath.substring(0, lastSlashIdx + 1);
			String srcPath = groupFullPath.substring(lastSlashIdx + 1);
			ts.processEvent(
					new AddGroupEvent("test", groupPath, srcPath, null, getDefaultLibList()),
					new ISourceEventCallback<IJstType>() {
						public void onComplete(
								EventListenerStatus<IJstType> status) {
							synchronized (fullyLoaded) {
								fullyLoaded.notify();
							}
						}

						public void onProgress(float percent) {
							System.out.println("Percentage of completion "
									+ percent);
						}
					});

			try {
				synchronized (fullyLoaded) {
					fullyLoaded.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			fail();
		}
		return ts;
	}

	public static void main(String[] args) {
		String pkg = "parent/";
		String js = "ParentType.js";
		// JstTypeSpaceMgr ts = loadJsToTypeSpace(pkg, js);
		JstTypeSpaceMgr ts = loadJsToTypeSpace();
		js = js.substring(0, js.lastIndexOf(".js"));
		TypeName typeName = new TypeName("test", pkg.replace("/", ".") + js);
		IJstType type = ts.getQueryExecutor().findType(typeName);
		assertNotNull(type);
		printTypes(ts);
	}

	private static void printTypes(JstTypeSpaceMgr ts) {
		ITypeSpace<IJstType, IJstNode> tsds = ts.getTypeSpace();
		Map<TypeName, IJstType> types = tsds.getTypes();
		System.out.println(" Total types = " + types.size());
		for (TypeName type : types.keySet()) {
			System.out.println(type + ":");
			System.out.println("\ttype=" + types.get(type).getName());
			System.out.println("\talias=" + types.get(type).getAlias());
		}
	}
	
	public static char[] readIntoChars(File file) throws IOException, FileNotFoundException {
	      final FileInputStream fis = new FileInputStream(file);
	      try {
	         BufferedInputStream bis = new BufferedInputStream(fis, 32000);
	         char[] chars = new char[(int) file.length()];
	         int data;

	         int index = 0;
	         while ((data = bis.read()) > -1) {
	            chars[index] = (char) data;
	            index++;
	         }
	         bis.close();
	         return chars;
	      } finally {
	         fis.close();
	      }
	   }
	
	private static String getBasePath(final URL url){
		final String path = url.getFile();
		int index = path.indexOf("src/");
		if (index < 0){
			index = path.indexOf("org/");
		}
		return path.substring(0, index);
	}
}

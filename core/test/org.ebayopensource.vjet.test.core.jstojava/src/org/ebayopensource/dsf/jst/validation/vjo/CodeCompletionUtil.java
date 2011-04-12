/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.validation.vjo;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.net.URL;
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
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.lib.ResourceHelper;
import org.ebayopensource.vjo.lib.TsLibLoader;

public class CodeCompletionUtil {
	public static Boolean fullyLoaded = true;
//	public static final String ARTIFACT_FOLDER = "testFiles";
	public static final String GROUP_NAME = "test";

	/**
	 * Load Typespace from current test project, all the js file placed at the
	 * ARTIFACT_FOLDER
	 * 
	 * @return
	 */
	public static JstTypeSpaceMgr loadJsToTypeSpace(String testFodlerName) {
		JstTypeSpaceMgr ts = null;
		try {
			IJstParseController controller = new JstParseController(
					new VjoParser());
			ts = new JstTypeSpaceMgr(controller, new DefaultJstTypeLoader());
			ts.initialize();
			TsLibLoader.loadDefaultLibs(ts);
			String name = CodeCompletionUtil.class.getName().replace(".", "/")
					+ ".class";
			URL url = CodeCompletionUtil.class.getClassLoader().getResource(
					name);
			String path = url.getFile();
			System.out.println("Path:" + path);
			int lastSlashIdx = 0;
			String srcPath = testFodlerName;
			String groupPath = null;
			if (path.contains("/bin/")) {
                lastSlashIdx = path.lastIndexOf("/bin/");
                groupPath = path.substring(0, lastSlashIdx + 1);
            } else if(path.contains("/BuildOutput/build50")){ 
                lastSlashIdx = path.lastIndexOf("/BuildOutput/build50");
                groupPath = path.substring(0, lastSlashIdx + 1) + "v4darwin/DSFJsToJavaTests/";
            } else {
                lastSlashIdx = path.lastIndexOf("/" + name);
                groupPath = path.substring(0, lastSlashIdx + 1);
                srcPath = "";
            } 
			System.out.println("groupPath:" + groupPath);
			System.out.println("SrcPath :" + srcPath);
			ts.processEvent(new AddGroupEvent(GROUP_NAME, groupPath,
			        srcPath, null),
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
    /**
     * Load Typespace from current test project, all the js file placed at the
     * ARTIFACT_FOLDER
     * 
     * @return
     */
    public static void loadSingleJsToTypeSpace(JstTypeSpaceMgr ts, String jsProjectName, String packageName, String fileName) {
        try {
            packageName = packageName.replace("/", ".");
            packageName = packageName.replace('.', File.separatorChar);
            String name = CodeCompletionUtil.class.getName().replace(".", "/")
                    + ".class";
            URL url = CodeCompletionUtil.class.getClassLoader().getResource(
                    name);
            String path = url.getFile();
            System.out.println("Path:" + path);
            int lastSlashIdx = 0;
            String srcPath = jsProjectName;
            String groupPath = null;
            if (path.contains("/bin/")) {
                lastSlashIdx = path.lastIndexOf("/bin/");
                groupPath = path.substring(0, lastSlashIdx + 1);
            } else if(path.contains("/BuildOutput/build50")){ 
                lastSlashIdx = path.lastIndexOf("/BuildOutput/build50");
                groupPath = path.substring(0, lastSlashIdx + 1) + "v4darwin/DSFJsToJavaTests/";
            } else {
                lastSlashIdx = path.lastIndexOf("/" + name);
                groupPath = path.substring(0, lastSlashIdx + 1);
                srcPath = "";
            } 
            srcPath = jsProjectName+File.separator+packageName;
            System.out.println("groupPath:" + groupPath);
            System.out.println("SrcPath :" + srcPath);
            ts.processEvent(new AddGroupEvent(GROUP_NAME, groupPath,
                    srcPath, null),
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
    }

    
    public static void main(String[] args) {
//        String pkg = "access.scope.";
//        String js = "FatherDefaultUser1.js";
//        // JstTypeSpaceMgr ts = loadJsToTypeSpace(pkg, js);
//        File file = new File("C:\\Eric\\views\\LiangMa_VJet_LIGER1\\v4darwin\\DSFJsToJavaTests\\testFiles\\access\\scope\\FatherDefaultUser1.js");
//        JstTypeSpaceMgr ts = VjoValidationTesterHelper.getTs(VjoValidationBaseTester.SINGLE_FOLDER);
//        loadSingleJsToTypeSpace(ts, "testFiles", "","");
//        js = js.substring(0, js.lastIndexOf(".js"));
//        TypeName typeName = new TypeName("test", pkg.replace("/", ".") + js);
//        IJstType type = ts.getQueryExecutor().findType(typeName);
//        assertNotNull(type);
//        printTypes(ts);
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
					new AddGroupEvent("test", groupPath, srcPath, null),
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

	public static void main2(String[] args) {
		String pkg = "syntax/";
		String js = "Person.js";
		// JstTypeSpaceMgr ts = loadJsToTypeSpace(pkg, js);
		JstTypeSpaceMgr ts = loadJsToTypeSpace("testFiles");
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
}

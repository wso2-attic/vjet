/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.jstojava.mixer.TypeExtensionRegistry;
import org.ebayopensource.dsf.jstojava.resolver.FunctionMetaRegistry;
import org.ebayopensource.dsf.jstojava.resolver.TypeResolverRegistry;
import org.ebayopensource.dsf.ts.event.EventListenerStatus;
import org.ebayopensource.dsf.ts.event.ISourceEventCallback;
import org.ebayopensource.dsf.ts.event.dispatch.IEventListenerHandle;
import org.ebayopensource.dsf.ts.event.group.AddGroupDependencyEvent;
import org.ebayopensource.dsf.ts.event.group.AddGroupEvent;
import org.ebayopensource.dsf.ts.event.group.BatchGroupLoadingEvent;
import org.ebayopensource.dsf.ts.event.group.IGroupEventListener;
import org.ebayopensource.dsf.ts.event.group.RemoveGroupDependencyEvent;
import org.ebayopensource.dsf.ts.event.group.RemoveGroupEvent;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.PiggyBackClassPathUtil;
import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.ebayopensource.vjet.eclipse.core.parser.VjoParserToJstAndIType;
import org.ebayopensource.vjet.eclipse.core.ts.TypeSpaceTracer;
import org.ebayopensource.vjo.lib.TsLibLoader;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.mod.core.IBuildpathEntry;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.ScriptProject;

public class TypeSpaceBuilder {

	public TypeSpaceBuilder() {
	}

	public void buildProject(ScriptProject project, Map args,
			IProgressMonitor monitor, List<String> groupDepends, IBuildpathEntry bootstrapPath) {
		
		// add group event
		String name = project.getProject().getName();
		File groupPath = project.getProject().getLocation().toFile();
		List<String> srcPaths = PiggyBackClassPathUtil
		.getProjectSrcPath_DLTK(project);
		// TODO support library zip entries
		List<String> classPaths = new ArrayList<String>();
		
		List<String> bootstrapDirs = new ArrayList<String>();
		if(bootstrapPath!=null){
			String portableString = "";
			portableString = getRelativeProjectPath(project, bootstrapPath);
			bootstrapDirs.add(portableString);
		}
		
		
		VjoParserToJstAndIType.getJstParseController().getJstTypeSpaceMgr()
		.processEvent(new AddGroupEvent(name, groupPath.getAbsolutePath(), srcPaths,
				classPaths, groupDepends, bootstrapDirs));
		
		
		if(VjetPlugin.TRACE_TYPESPACE){
			System.out.println("project: " + project);
			System.out.println("args: " + args);
			System.out.println("monitor: " + monitor);
			System.out.println("project buildpath: " + groupDepends);
			System.out.println("bootstrapPath: " + bootstrapPath);
		
			
		}
		
	}
	
	
	private String getRelativeProjectPath(IScriptProject project, IBuildpathEntry bootstrap) {
		String name = project.getElementName();
		String bootstrapPath = bootstrap.getPath().toPortableString();
		if (bootstrapPath.lastIndexOf(name) != -1) {
			if (bootstrapPath.equals(name)) {
				bootstrapPath = "";
			} else {
				bootstrapPath = bootstrapPath.substring(bootstrapPath
						.indexOf(name)
						+ name.length());

			}
		}
		return bootstrapPath;
	}
	
	
	public static IBuildpathEntry getBootstrapDir(IScriptProject project) throws ModelException {
		IBuildpathEntry[] entries = project.getRawBuildpath();
		for (IBuildpathEntry entry : entries) {
			if(entry.getEntryKind() == IBuildpathEntry.BPE_BOOTSTRAP){
				return entry;
			}
		}
		return null;
	}
	
	public static IBuildpathEntry[] getSerFileGroupDepends(IScriptProject project,
			List<String> list) throws ModelException {
		IBuildpathEntry[] entries = project.getRawBuildpath();
		for (IBuildpathEntry entry : entries) {
			// process only project entries
			if (entry.getEntryKind() == IBuildpathEntry.BPE_PROJECT) {
				// add first segment of the project entry - project name.
				list.add(entry.getPath().segment(0));
				// add transitive project dependencies
				
				getSerFileGroupDepends(CodeassistUtils.getScriptProject(entry.getPath().segment(0)), list);
				
				
			} else if (entry.getEntryKind() == IBuildpathEntry.BPE_LIBRARY){
				String id = entry.getPath().segment(entry.getPath().segmentCount()-1);
				list.add(id);
				
			} else if (entry.getEntryKind() == IBuildpathEntry.BPE_CONTAINER) {
				String id = entry.getPath().segment(0);
				
				if (org.ebayopensource.vjet.eclipse.core.VjetPlugin.VJETTL_ID.equals(id)) {
					//add built in libs
					list.add(entry.getPath().lastSegment());
				
				}
				
				
				if (org.ebayopensource.vjet.eclipse.core.VjetPlugin.VJOLIB_ID.equals(id)) {
					//add built in libs
					String[] defaultLibs = TsLibLoader.getVjoGroups();
					for (int i = 0; i < defaultLibs.length; i++) {
						list.add(defaultLibs[i]);
					}
				}
				if (org.ebayopensource.vjet.eclipse.core.VjetPlugin.BROWSERSDK_ID.equals(id)) {
					//add built in libs
					String[] defaultLibs = TsLibLoader.getBrowserGroups();
					for (int i = 0; i < defaultLibs.length; i++) {
						list.add(defaultLibs[i]);
					}
				}
				if (org.ebayopensource.vjet.eclipse.core.VjetPlugin.JSNATIVESDK_ID.equals(id)) {
					//add built in libs
					String[] defaultLibs = TsLibLoader.getJsNativeGroups();
					for (int i = 0; i < defaultLibs.length; i++) {
						list.add(defaultLibs[i]);
					}
				}
				if (org.ebayopensource.vjet.eclipse.core.VjetPlugin.SDK_CONTAINER .equals(id)) {
					//add built in libs
					String[] defaultLibs = TsLibLoader.getDefaultLibNames();
					for (int i = 0; i < defaultLibs.length; i++) {
						list.add(defaultLibs[i]);
					}
				}
			}
		}
		return entries;
	}
	
	public static void addGroupTraceEventListeners(JstTypeSpaceMgr jstTypeSpace){
		jstTypeSpace.registerSourceEventListener(new IGroupEventListener<IJstType>() {

			@Override
			public EventListenerStatus<IJstType> onBatchGroupLoaded(
					BatchGroupLoadingEvent event,
					IEventListenerHandle handle,
					ISourceEventCallback callBack) {
				System.out.println("on batch group add dependendency");
				TypeSpaceTracer.onBatchLoaded(event);
				return null;
			}

			@Override
			public EventListenerStatus onGroupAddDependency(
					AddGroupDependencyEvent event,
					IEventListenerHandle handle,
					ISourceEventCallback callBack) {
				// TODO Auto-generated method stub
				System.out.println("onGroupAddDependency for group:" + event.getGroupName());
				System.out.println("\tgroup path: " + event.getGroupPath());
				System.out.println("\tdependency list: " + event.getDependencyList());
				return null;
			}

			@Override
			public EventListenerStatus onGroupAdded(AddGroupEvent event,
					IEventListenerHandle handle,
					ISourceEventCallback callBack) {
				// TODO Auto-generated method stub
				System.out.println("on group added");
				System.out.println("bootstrap paths:" + event.getBootStrapList());
				
				System.out.println(event);
				return null;
			}

			@Override
			public EventListenerStatus onGroupRemoveDependency(
					RemoveGroupDependencyEvent event,
					IEventListenerHandle handle,
					ISourceEventCallback callBack) {
				System.out.println("on group remove dependency");
				System.out.println(event);
				return null;
			}

			@Override
			public void onGroupRemoved(RemoveGroupEvent event) {
				// TODO Auto-generated method stub
				System.out.println("on group removed");
				System.out.println(event);
			}
			
		});
	}

	public static void addGroupEventListeners(JstTypeSpaceMgr jstTypeSpaceMgr) {
		jstTypeSpaceMgr.registerSourceEventListener(new IGroupEventListener<JstType>(){

			@Override
			public EventListenerStatus<JstType> onBatchGroupLoaded(
					BatchGroupLoadingEvent event, IEventListenerHandle handle,
					ISourceEventCallback<JstType> callBack) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public EventListenerStatus<JstType> onGroupAddDependency(
					AddGroupDependencyEvent event, IEventListenerHandle handle,
					ISourceEventCallback<JstType> callBack) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public EventListenerStatus<JstType> onGroupAdded(
					AddGroupEvent event, IEventListenerHandle handle,
					ISourceEventCallback<JstType> callBack) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public EventListenerStatus<JstType> onGroupRemoveDependency(
					RemoveGroupDependencyEvent event,
					IEventListenerHandle handle,
					ISourceEventCallback<JstType> callBack) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void onGroupRemoved(RemoveGroupEvent event) {
				// remove the bootstrap entries for the group
				String groupId = event.getGroupName();
				TypeResolverRegistry.getInstance().clear(groupId);
				TypeExtensionRegistry.getInstance().clear(groupId);
				FunctionMetaRegistry.getInstance().clear(groupId);
			}
			
		});
		
		
	}

	
	
}

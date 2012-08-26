/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.test.core.ecma.ts.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

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
import org.ebayopensource.dsf.ts.event.dispatch.IEventListenerHandle;
import org.ebayopensource.dsf.ts.event.group.AddGroupDependencyEvent;
import org.ebayopensource.dsf.ts.event.group.AddGroupEvent;
import org.ebayopensource.dsf.ts.event.group.BatchGroupLoadingEvent;
import org.ebayopensource.dsf.ts.event.group.IGroupEventListener;
import org.ebayopensource.dsf.ts.event.group.RemoveGroupDependencyEvent;
import org.ebayopensource.dsf.ts.event.group.RemoveGroupEvent;
import org.ebayopensource.dsf.ts.event.type.AddTypeEvent;
import org.ebayopensource.dsf.ts.event.type.ITypeEventListener;
import org.ebayopensource.dsf.ts.event.type.ModifyTypeEvent;
import org.ebayopensource.dsf.ts.event.type.RemoveTypeEvent;
import org.ebayopensource.dsf.ts.event.type.RenameTypeEvent;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjet.test.util.JstLibResolver;
import org.ebayopensource.vjet.test.util.VJetSdkEnvironment;
import org.ebayopensource.vjo.lib.IResourceResolver;
import org.ebayopensource.vjo.lib.LibManager;
import org.ebayopensource.vjo.lib.TsLibLoader;
import org.eclipse.core.runtime.FileLocator;
import org.junit.Test;

public class JsLangTypeSpaceTests {

	@Test
	public void testJsLangLoad() throws Exception {

		try {

			IResourceResolver jstLibResolver = JstLibResolver
					.getInstance()
					.setSdkEnvironment(
							new VJetSdkEnvironment(new String[0], "DefaultSdk"));

			LibManager.getInstance().setResourceResolver(jstLibResolver);
			IJstParseController controller = new JstParseController(
					new VjoParser());

			JstTypeSpaceMgr ts = new JstTypeSpaceMgr(controller,
					new DefaultJstTypeLoader());
			// addTraceEvents(ts);
			ts.initialize();
			TsLibLoader.loadDefaultLibs(ts);
			// printTypes(ts);

			URL url = this.getClass().getClassLoader()
					.getResource("dsf/jslang/feature/tests/EcmaArrayTests.js");
			if(url.getProtocol().contains("bundleresource")){
				url = FileLocator.resolve(url);
			}
			String path = url.getFile();
			
			System.out.println("path = " + path);

			int end = path.indexOf("dsf/jslang/feature/tests/");
			String groupFullPath = path.substring(0, end - 1);
			System.out.println("groupFullPath = " + groupFullPath);
			int lastSlashIdx = groupFullPath.lastIndexOf("/");
			String groupPath = groupFullPath.substring(0, lastSlashIdx + 1);
			String srcPath = groupFullPath.substring(lastSlashIdx + 1);
			System.out.println("srcPath = " + srcPath);
			ts.processEvent(new AddGroupEvent(
					"org.ebayopensource.vjet.test.core.jstojava", groupPath,
					srcPath, null));

			// TypeName typeName = new TypeName(JstTypeSpaceMgr.JS_NATIVE_GRP,
			// "Array");
			// IJstType type = ts.getQueryExecutor().findType(typeName);
			// assertNotNull(type);

			TypeName typeName = new TypeName(
					"org.ebayopensource.vjet.test.core.jstojava",
					"dsf.jslang.feature.tests.EcmaArrayTests");
			IJstType type = ts.getQueryExecutor().findType(typeName);
			printTypes(ts);
			assertNotNull(type);
		} catch (Exception e) {
			fail();
		}

	}

	private void addTraceEvents(JstTypeSpaceMgr ts) {

		ts.registerSourceEventListener(new ITypeEventListener<IJstType>() {

			@Override
			public EventListenerStatus<IJstType> onTypeAdded(
					AddTypeEvent<IJstType> event, IEventListenerHandle handle,
					ISourceEventCallback<IJstType> callBack) {

				System.out.println("onTypeAdded add dependendency"
						+ event.getTypeName().typeName());
				return null;
			}

			@Override
			public EventListenerStatus<IJstType> onTypeModified(
					ModifyTypeEvent event, IEventListenerHandle handle,
					ISourceEventCallback<IJstType> callBack) {
				System.out.println("onTypeModified add dependendency"
						+ event.getTypeName().typeName());
				return null;
			}

			@Override
			public EventListenerStatus<IJstType> onTypeRemoved(
					RemoveTypeEvent event, IEventListenerHandle handle,
					ISourceEventCallback<IJstType> callBack) {
				System.out.println("onTypeRemoved add dependendency"
						+ event.getTypeName().typeName());
				return null;
			}

			@Override
			public EventListenerStatus<IJstType> onTypeRenamed(
					RenameTypeEvent event, IEventListenerHandle handle,
					ISourceEventCallback<IJstType> callBack) {
				System.out.println("onTypeRenamed add dependendency"
						+ event.getTypeName().typeName());
				return null;
			}

		});

		ts.registerSourceEventListener(new IGroupEventListener<IJstType>() {

			@Override
			public EventListenerStatus<IJstType> onBatchGroupLoaded(
					BatchGroupLoadingEvent event, IEventListenerHandle handle,
					ISourceEventCallback callBack) {
				System.out.println("on batch group add dependendency");
				return null;
			}

			@Override
			public EventListenerStatus onGroupAddDependency(
					AddGroupDependencyEvent event, IEventListenerHandle handle,
					ISourceEventCallback callBack) {
				// TODO Auto-generated method stub
				System.out.println("onGroupAddDependency for group:"
						+ event.getGroupName());
				System.out.println("\tgroup path: " + event.getGroupPath());
				System.out.println("\tdependency list: "
						+ event.getDependencyList());
				return null;
			}

			@Override
			public EventListenerStatus onGroupAdded(AddGroupEvent event,
					IEventListenerHandle handle, ISourceEventCallback callBack) {
				// TODO Auto-generated method stub
				System.out.println("on group added");
				System.out.println("bootstrap paths:"
						+ event.getBootStrapList());
				System.out.println(event);
				return null;
			}

			@Override
			public EventListenerStatus onGroupRemoveDependency(
					RemoveGroupDependencyEvent event,
					IEventListenerHandle handle, ISourceEventCallback callBack) {
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

	private void printTypes(JstTypeSpaceMgr ts) {
		ITypeSpace<IJstType, IJstNode> tsds = ts.getTypeSpace();
		Map<TypeName, IJstType> types = tsds.getTypes();
		for (TypeName type : types.keySet()) {
			System.out.println(type + ":");
			// System.out.print("pkg= " + types.get(type).getPackage());
			System.out.println("\ttype=" + types.get(type).getName());
			System.out.println("\talias=" + types.get(type).getAlias());
		}
	}

}

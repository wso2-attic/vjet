/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.tests.ts;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.TestCase;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstParser;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IScriptProblem;
import org.ebayopensource.dsf.jst.IScriptUnit;
import org.ebayopensource.dsf.jst.IWritableScriptUnit;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.tests.ts.data.A;
import org.ebayopensource.dsf.jst.tests.ts.data.B;
import org.ebayopensource.dsf.jst.tests.ts.data.C;
import org.ebayopensource.dsf.jst.tests.ts.data.D;
import org.ebayopensource.dsf.jst.tests.ts.data.IA;
import org.ebayopensource.dsf.jst.tests.ts.data.x.Ax;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.jst.ts.util.JstTypeDependencyCollector;
import org.ebayopensource.dsf.jst.ts.util.JstTypeDependencyHelper;
import org.ebayopensource.dsf.ts.TypeSpace;
import org.ebayopensource.dsf.ts.event.group.AddGroupEvent;
import org.ebayopensource.dsf.ts.event.group.BatchGroupLoadingEvent;
import org.ebayopensource.dsf.ts.event.type.AddTypeEvent;
import org.ebayopensource.dsf.ts.event.type.RemoveTypeEvent;
import org.ebayopensource.dsf.ts.graph.DependencyGraph;
import org.ebayopensource.dsf.ts.group.Group;
import org.ebayopensource.dsf.ts.group.Project;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.lib.IResourceResolver;
import org.ebayopensource.vjo.lib.LibManager;
import org.ebayopensource.vjo.lib.ResourceHelper;
import org.ebayopensource.vjo.lib.TsLibLoader;

public class BaseTest extends TestCase {
	
	
	
	public final String PROJ_1 = "Proj1";
	public final String PROJ_2 = "Proj2";
	
	public final TypeName TYPE_IA = new TypeName(PROJ_1, "org.ebayopensource.dsf.jst.tests.ts.data.IA");
	public final TypeName TYPE_A = new TypeName(PROJ_1, "org.ebayopensource.dsf.jst.tests.ts.data.A");
	public final TypeName TYPE_B = new TypeName(PROJ_1, "org.ebayopensource.dsf.jst.tests.ts.data.B");
	public final TypeName TYPE_C = new TypeName(PROJ_1, "org.ebayopensource.dsf.jst.tests.ts.data.C");
	public final TypeName TYPE_D = new TypeName(PROJ_1, "org.ebayopensource.dsf.jst.tests.ts.data.D");
	public final TypeName TYPE_Ax = new TypeName(PROJ_2, "org.ebayopensource.dsf.jst.tests.ts.data.x.Ax");
	
	
	public final TypeName TYPE_A1 = new TypeName(PROJ_1, "org.ebayopensource.dsf.jst.tests.ts.data.A1");
	
	public JstTypeDependencyCollector s_typeBuilder = new JstTypeDependencyCollector();
	
	static String s_outputFolder;
	
	static{
		
		try {
			s_outputFolder = File.createTempFile("test", ".test").getParent();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected List<Class<?>> getClassList1(){
		
		List<Class<?>> classes = new ArrayList<Class<?>>();
		classes.add(IA.class);
		classes.add(A.class);
		classes.add(B.class);
		classes.add(C.class);
		classes.add(D.class);
		
		return classes;
	}
	
	protected List<Class<?>> getClassList2(){
		
		List<Class<?>> classes = new ArrayList<Class<?>>();
		classes.add(Ax.class);
		
		return classes;
	}
	
	protected DependencyGraph<IJstType> createGraph(List<Class<?>> classes){
		List<IJstType> jstTypes = getJstTypes(classes);
		
		return new DependencyGraph<IJstType>(JstTypeDependencyHelper.toMap(jstTypes), s_typeBuilder);
	}
	
	protected List<IJstType> getJstTypes(List<Class<?>> classes){
		
//		List<JstType> jstTypes = controller.targetedTranslation(classes);
//		List<IJstType> list = new ArrayList<IJstType>();
//		for (IJstType t: jstTypes){
//			list.add(t);
//		}
		Map<String,IJstType> jstTypes = TestDataHelper.getJstTypes();
		List<IJstType> list = new ArrayList<IJstType>();
		for (Class<?> c: classes){
			list.add(jstTypes.get(c.getName()));
		}
		return list;
	}
	
	protected void doAssertions(TypeSpace<IJstType,IJstNode> ts){
		
		Group<IJstType> group1 = ts.getGroup(PROJ_1);
		Group<IJstType> group2 = ts.getGroup(PROJ_2);
		assertEquals(6, ts.getTypes().size());
		//TODO - Following needs to be updated after VjoJavaLib ser file is fixed
		assertEquals(2, group1.getGraph().getUnresolvedNodes().size()); // Object is implicitly added
		assertEquals(2, group2.getGraph().getUnresolvedNodes().size());
		
		List<IJstType> list = ts.getAllDependents(TYPE_A);
		for (IJstType t: list){
			System.out.println(t.getName());
		}
		assertEquals(6, ts.getTypes().size());
		assertEquals(TYPE_A.typeName(), ts.getType(TYPE_A).getName());
		assertEquals(4, ts.getDirectDependents(TYPE_A).size());
		assertEquals(2, ts.getDirectDependents(TYPE_B).size());
		assertEquals(2, ts.getDirectDependents(TYPE_C).size());
		assertEquals(0, ts.getDirectDependents(TYPE_D).size());
		assertEquals(0, ts.getDirectDependents(TYPE_Ax).size());
		
		assertEquals(2, ts.getIndirectDependents(TYPE_A).size());
		assertEquals(3, ts.getIndirectDependents(TYPE_B).size());
		assertEquals(3, ts.getIndirectDependents(TYPE_C).size());
		assertEquals(0, ts.getIndirectDependents(TYPE_D).size());
		assertEquals(0, ts.getIndirectDependents(TYPE_Ax).size());
		
		assertEquals(4, ts.getAllDependents(TYPE_A).size());
		assertEquals(4, ts.getAllDependents(TYPE_B).size());
		assertEquals(4, ts.getAllDependents(TYPE_C).size());
		assertEquals(0, ts.getAllDependents(TYPE_Ax).size());
		
		assertEquals(3, ts.getDirectDependencies(TYPE_A).size());
		assertEquals(1, ts.getDirectDependencies(TYPE_B).size());
		assertEquals(2, ts.getDirectDependencies(TYPE_C).size());

		//TODO - Following needs to be updated after VjoJavaLib ser file is fixed
		assertEquals(5, ts.getDirectDependencies(TYPE_D).size());
		assertEquals(1, ts.getDirectDependencies(TYPE_Ax).size());
		
		assertEquals(1, ts.getIndirectDependencies(TYPE_A).size());
		assertEquals(3, ts.getIndirectDependencies(TYPE_B).size());
		assertEquals(3, ts.getIndirectDependencies(TYPE_C).size());
		assertEquals(4, ts.getIndirectDependencies(TYPE_D).size());
		assertEquals(4, ts.getIndirectDependencies(TYPE_Ax).size());
		
		assertEquals(4, ts.getAllDependencies(TYPE_A).size());
		assertEquals(4, ts.getAllDependencies(TYPE_B).size());
		assertEquals(4, ts.getAllDependencies(TYPE_C).size());
		assertEquals(6, ts.getAllDependencies(TYPE_D).size());
		assertEquals(5, ts.getAllDependencies(TYPE_Ax).size());
	}
	
	protected TypeSpace<IJstType,IJstNode> createTypeSpace(){
		
		JstCache.getInstance().clear();
		
		TypeSpace<IJstType,IJstNode> ts = new TypeSpace<IJstType,IJstNode>();
		ts.setMethodSymbolTableManager(new TestSymbolTableManager(true));
		ts.setPropertySymbolTableManager(new TestSymbolTableManager(false));
		
		return ts;
	}
	
	protected TypeSpace<IJstType,IJstNode> loadTypeSpace(){
		
		TypeSpace<IJstType,IJstNode> ts = createTypeSpace();
		
		Group<IJstType> group1 = new Project<IJstType>(PROJ_1, s_typeBuilder);
		Group<IJstType> group2 = new Project<IJstType>(PROJ_2, s_typeBuilder);
		ts.addGroup(group1).addGroup(group2);
		
		group1.addEntities(JstTypeDependencyHelper.toMap(getJstTypes(getClassList1())));
		group2.addEntities(JstTypeDependencyHelper.toMap(getJstTypes(getClassList2())));
		
		return ts;
	}
	
	protected JstTypeSpaceMgr getTypeSpaceManager(){

		final JstTypeSpaceMgr tsMgr = new JstTypeSpaceMgr(null, null);
		JstCache.getInstance().clear();
		
		tsMgr.getConfig().setSynchronousEvents(true);
		
		tsMgr.initialize();
				
		IResourceResolver jstLibResolver = JstLibResolver.getInstance()
				.setSdkEnvironment(new VJetSdkEnvironment(new String[0], "DefaultSdk"));

		LibManager.getInstance().setResourceResolver(jstLibResolver);
		
		TsLibLoader.loadDefaultLibs(tsMgr);
	
		BatchGroupLoadingEvent batchEvent = new BatchGroupLoadingEvent();
		
		batchEvent.addGroupEvent(new AddGroupEvent(PROJ_1, null));
		List<String> classpath = new ArrayList<String>();
		classpath.add(PROJ_1);
		batchEvent.addGroupEvent(new AddGroupEvent(PROJ_2, null, (List)null, classpath));
		
		tsMgr.processEvent(batchEvent);
		
		Map<String,IJstType> jstTypes = JstTypeDependencyHelper.toMap(getJstTypes(getClassList1()));
		for (Entry<String,IJstType> entry: jstTypes.entrySet()){
			((JstType)entry.getValue()).getPackage().setGroupName(PROJ_1);
		}
		for (Entry<String,IJstType> entry: jstTypes.entrySet()){
			tsMgr.processEvent(new AddTypeEvent<IJstType>(new TypeName(PROJ_1, entry.getValue().getName()), entry.getValue()));
		}
		
		for (Entry<String,IJstType> entry: jstTypes.entrySet()){
			
			TypeName typeName = new TypeName(PROJ_1, entry.getKey());
			tsMgr.processEvent(new RemoveTypeEvent(typeName));
		}
		
		for (Entry<String,IJstType> entry: jstTypes.entrySet()){
			Object userMap = new LinkedHashMap();
			String typeName = entry.getKey();
			tsMgr.processEvent(new AddTypeEvent(new TypeName(PROJ_1, entry.getKey()), entry.getValue(), userMap));
			Object userMap1 = tsMgr.getTypeSpace().getUserObject(new TypeName(PROJ_1, typeName));
			assertEquals(userMap, userMap1);
		}
		
		jstTypes = JstTypeDependencyHelper.toMap(getJstTypes(getClassList2()));
		for (Entry<String,IJstType> entry: jstTypes.entrySet()){
			((JstType)entry.getValue()).getPackage().setGroupName(PROJ_2);
		}
		for (Entry<String,IJstType> entry: jstTypes.entrySet()){
			tsMgr.processEvent(new AddTypeEvent(new TypeName(PROJ_2, entry.getValue().getName()), entry.getValue()));
		}

		return tsMgr;
	}
	
	protected static InputStream getJsNativeSerializedStreamFromSource() {
//		Class anchorClass = ResourceHelper.getJsNativeAnchorClass();
		String jsNativeFile = ResourceHelper.getInstance().getJsBrowserFileName();
		return getJsNativeSerializedStream(s_outputFolder, jsNativeFile);
	}
	
	protected static InputStream getJsNativeGlobalSerializedStreamFromSource() {
//		Class anchorClass = ResourceHelper.getJsNativeAnchorClass();
		String jsNativeFile = ResourceHelper.getInstance().getJsNativeGlobalObjectsFileName();
		return getJsNativeSerializedStream(s_outputFolder, jsNativeFile);
	}
	
	private static InputStream getJsNativeSerializedStream(String outputFolder, String jsNativeFile) {
		File file = null;
		file = new File(outputFolder +"/"+jsNativeFile);

		if (file == null) {
			throw new RuntimeException("Could not load JsNative resource - " + 
					file);
		}
		try {
			URL url = file.toURL();
			return url.openStream();
		} catch (IOException e) {
			throw new RuntimeException("Could not load JsNative resource - " + 
					file, e);
		}
	}

	IJstParser getParser(){
		return new IJstParser(){
			public IWritableScriptUnit parse(String groupName, String fileName, String source){
				
				
				final JstType type = JstCache.getInstance().getType(fileName);
				if (type == null ){
					return null;
				}
				if (type.getPackage() == null){
					type.setPackage(new JstPackage());
				}
				type.getPackage().setGroupName(groupName);
				
				return new IWritableScriptUnit(){

					public IJstNode getNode(int startOffset) {
						// TODO Auto-generated method stub
						return null;
					}

					public List<IScriptProblem> getProblems() {
						// TODO Auto-generated method stub
						return null;
					}

					public JstBlock getSyntaxRoot() {
						// TODO Auto-generated method stub
						return null;
					}
					
					public List<JstBlock> getJstBlockList() {
						return new ArrayList<JstBlock>();
					}

					public IJstType getType() {
			
						return type;
					}

					@Override
					public void setType(IJstType type) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void setSyntaxRoot(JstBlock block) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void setJstBlockList(List<JstBlock> blocks) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void setProblems(List<IScriptProblem> probs) {
						// TODO Auto-generated method stub
						
					}
					
				};
				
				
			}

			@Override
			public IScriptUnit postParse(IScriptUnit unit) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public IScriptUnit preParse(String groupName, String fileName,
					String source) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public IScriptUnit parse(String groupName, File file) {
				return null;
			}

		};
	}
	
	synchronized void waitABit(){
		try {
			wait(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

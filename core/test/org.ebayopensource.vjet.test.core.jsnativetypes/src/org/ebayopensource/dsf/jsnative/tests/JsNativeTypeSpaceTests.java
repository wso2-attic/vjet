/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative.tests;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.dap.proxy.Ol;
import org.ebayopensource.dsf.javatojs.util.SupportedBrowser;
import org.ebayopensource.dsf.javatojs.util.SupportedDomLevel;
import org.ebayopensource.dsf.javatojs.util.SupportedJsVersion;
import org.ebayopensource.dsf.jsnative.HtmlElement;
import org.ebayopensource.dsf.jsnative.Window;
import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.BrowserSupport;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.JsNativeMeta;
import org.ebayopensource.dsf.jsnative.anno.JsSupport;
import org.ebayopensource.dsf.jsnative.anno.JsVersion;
import org.ebayopensource.dsf.jst.IJstAnnotation;
import org.ebayopensource.dsf.jst.IJstDoc;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstParseController;
import org.ebayopensource.dsf.jst.IJstParser;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IScriptProblem;
import org.ebayopensource.dsf.jst.IScriptUnit;
import org.ebayopensource.dsf.jst.IWritableScriptUnit;
import org.ebayopensource.dsf.jst.datatype.JstReservedTypes;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstRefType;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.ts.IJstTypeLoader;
import org.ebayopensource.dsf.jst.ts.JstQueryExecutor;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.jstojava.controller.JstParseController;
import org.ebayopensource.dsf.jstojava.loader.DefaultJstTypeLoader;
import org.ebayopensource.dsf.ts.ITypeSpace;
import org.ebayopensource.dsf.ts.group.IGroup;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.lib.IResourceResolver;
import org.ebayopensource.vjo.lib.LibManager;
import org.ebayopensource.vjo.lib.TsLibLoader;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import org.ebayopensource.dsf.common.Z;

//@ModuleInfo(value="JsNativeResource",subModuleId="JsNativeResource")
public class JsNativeTypeSpaceTests {

	@Before
	public void setUp() throws Exception {
		LibManager.getInstance().clear();
	}

	@Test
	public void testJavaPrimitiveTypes() {
		
		IResourceResolver jstLibResolver = JstLibResolver.getInstance()
				.setSdkEnvironment(new VJetSdkEnvironment(new String[0], "DefaultSdk"));

		LibManager.getInstance().setResourceResolver(jstLibResolver);
		
		JstTypeSpaceMgr mgr = new JstTypeSpaceMgr(getController(), getLoader()).initialize();
		TsLibLoader.loadJavaPrimitiveLib(mgr);
		JstQueryExecutor qe = mgr.getQueryExecutor();
		
		// Test Java primitive types are available in type space.
		for (JstRefType type : JstReservedTypes.JavaPrimitive.ALL) {
			IJstType jstType = qe.findType(new TypeName(
					JstTypeSpaceMgr.JAVA_PRIMITIVE_GRP,type.getName()));
			Assert.assertNotNull(jstType);
			//dump((JstType) jstType);
		}
	}

	@Test
	public void testJsNativeTypeSpace() {
		
		IResourceResolver jstLibResolver = JstLibResolver.getInstance()
				.setSdkEnvironment(new VJetSdkEnvironment(new String[0], "DefaultSdk"));

		LibManager.getInstance().setResourceResolver(jstLibResolver);
		
		JstTypeSpaceMgr mgr = new JstTypeSpaceMgr(getController(), getLoader()).initialize();
		TsLibLoader.loadJsNativeGlobalLib(mgr);
		TsLibLoader.loadBrowserTypesLib(mgr);
		JstQueryExecutor qe = mgr.getQueryExecutor();
		
		// Test Browser JS native types are available in type space.
		IJstType jstWindow = qe.findType(new TypeName(JstTypeSpaceMgr.JS_BROWSER_GRP,"Window"));
		Assert.assertNotNull(jstWindow);
		assertTrue(jstWindow.getModifiers().isDynamic());
		//dump((JstType) jstWindow);
		Assert.assertEquals("Window", jstWindow.getName());
		assertJavaDoc(jstWindow.getDoc());
		IJstProperty propDocument = jstWindow.getProperty("document", false, true);
		Assert.assertNotNull(propDocument);
		assertJavaDoc(propDocument.getDoc());
		IJstProperty propInnerWidth = jstWindow.getProperty("innerWidth", false, true);
		Assert.assertNotNull(propInnerWidth);
		assertSupportedByAnnotation(Window.class, "innerWidth", propInnerWidth, true);
		
		// test navigator.plugins
		
		IJstType navigator = qe.findType(new TypeName(JstTypeSpaceMgr.JS_BROWSER_GRP,"Navigator"));
		Assert.assertNotNull(navigator);
		IJstProperty property = navigator.getProperty("plugins");
		assertNotNull(property);
		
		// todo fix this
//		assertEquals("Plugin[]", property.getType().getName());
	
		
		// Check IE event property
		IJstProperty propEvent = jstWindow.getProperty("event", false, true);
		Assert.assertNotNull(propEvent);
		IJstType jstEvent = qe.findType(
			new TypeName(JstTypeSpaceMgr.JS_BROWSER_GRP,propEvent.getType().getName()));
		Assert.assertNotNull(jstEvent);
		
		
		// Check document
		IJstType jstDocument = qe.findType(
			new TypeName(JstTypeSpaceMgr.JS_BROWSER_GRP,propDocument.getType().getName()));
		Assert.assertNotNull(jstDocument);
		//dump((JstType) jstDocument);
		Assert.assertEquals("HTMLDocument", jstDocument.getName());
		assertTrue(jstDocument.isMetaType());
		assertNotNull(jstDocument.getMethod("createElement"));
		assertTrue(jstDocument.getMethod("createElement").isTypeFactoryEnabled());
		
		// Check document.body
		IJstProperty propBody = jstDocument.getProperty("body", false, true);
		Assert.assertNotNull(propBody);
		assertJavaDoc(propBody.getDoc());
		IJstType jstBody = qe.findType(
			new TypeName(JstTypeSpaceMgr.JS_BROWSER_GRP,propBody.getType().getName()));
		Assert.assertNotNull(jstBody);
		Assert.assertEquals("HTMLBodyElement", jstBody.getName());
		
		// Check HTMLBodyElement extends HTMLElement
		IJstType jstElement = jstBody.getExtend();
		Assert.assertNotNull(jstElement);
		//dump((JstType) jstElement);
		Assert.assertEquals("HTMLElement", jstElement.getName());
		IJstMethod mtd = jstElement.getMethod("hasAttributes", false, true);
		Assert.assertNotNull(mtd);
		assertJavaDoc(mtd.getDoc());
		
		
		assertSupportedByAnnotation(HtmlElement.class, "hasAttributes", mtd, false);
		
		ITypeSpace<IJstType,IJstNode> ts = mgr.getTypeSpace();
		IGroup<IJstType> jsnative = ts.getGroup(JstTypeSpaceMgr.JS_NATIVE_GRP);
		IGroup<IJstType> jsbrowser = ts.getGroup(JstTypeSpaceMgr.JS_BROWSER_GRP);
		Assert.assertNotNull(jsnative);
		Assert.assertNotNull(jsbrowser);
		
		Map<String,IJstType> members = new LinkedHashMap<String, IJstType>();
		members.putAll(jsnative.getEntities());
		members.putAll(jsbrowser.getEntities());
		
		Assert.assertNotNull(members);
		
		for (Class c : JsNativeMeta.getAllClasses()) {
			String typeName;
			Alias alias = (Alias) c.getAnnotation(Alias.class);
			if (alias != null) {
				typeName = alias.value();
			} else {
				typeName = c.getSimpleName();
			}
			IJstType jst = members.get(typeName);
			Assert.assertNotNull("Can not find type " + typeName, jst);
			System.out.println("Found JST type for " + typeName);
			// Make sure extended JstType is the same instance as the one we find in the 
			// type space.
			assertInheratence(qe, jst);
			// Make sure satisfied JstTypes are the same instance as the one we find in the 
			// type space.
			assertImplments(qe, jst);
		}
		
		assertJsNativeFunction(qe);
		
		// Now check for Ol (ObjLiteral) type
		String typeName;
		Alias alias = Ol.class.getAnnotation(Alias.class);
		if (alias != null) {
			typeName = alias.value();
		} else {
			typeName = Ol.class.getSimpleName();
		}
		IJstType jst = members.get(typeName);
		Assert.assertNotNull("Can not find type " + typeName, jst);
		System.out.println("Found JST type for " + typeName);
		
		//verify ser of Object, DOMException, ActiveXObject's constructors
		IJstType activeXObject = qe.findType(new TypeName(JstTypeSpaceMgr.JS_BROWSER_GRP, "ActiveXObject"));
		Assert.assertNotNull(activeXObject);
		Assert.assertNotNull("ActiveXObject has no constructor", activeXObject.getConstructor());
		
		IJstType eventException = qe.findType(new TypeName(JstTypeSpaceMgr.JS_BROWSER_GRP, "EventException"));
		Assert.assertNotNull(eventException);
		Assert.assertNotNull("EventException has no constructor", eventException.getConstructor());
	}
	
	private static String[] s_jsNativeGlobals = new String[] {
		org.ebayopensource.dsf.jsnative.global.Array.class.getSimpleName(),
		org.ebayopensource.dsf.jsnative.global.Boolean.class.getSimpleName(),
		org.ebayopensource.dsf.jsnative.global.Date.class.getSimpleName(),
		org.ebayopensource.dsf.jsnative.global.Error.class.getSimpleName(),
		org.ebayopensource.dsf.jsnative.global.Number.class.getSimpleName(),
		org.ebayopensource.dsf.jsnative.global.RegExp.class.getSimpleName(),
		org.ebayopensource.dsf.jsnative.global.String.class.getSimpleName()
	};

	private void assertJsNativeFunction(JstQueryExecutor qe) {
		IJstType jstFunc = qe.findType(new TypeName(JstTypeSpaceMgr.JS_NATIVE_GRP,"Function"));
		Assert.assertNotNull(jstFunc);
		for (String name : s_jsNativeGlobals) {
			IJstType jstType = qe.findType(new TypeName(JstTypeSpaceMgr.JS_NATIVE_GRP,name));
			Assert.assertNotNull(jstType);
			// assert global jsnative object has all the Function properties
			for (IJstProperty prop : jstFunc.getStaticProperties()) {
				IJstProperty p = jstType.getProperty(prop.getName().getName(), prop.isStatic());
				Assert.assertNotNull("Did not find property "+prop.getName().getName()+
						" in "+jstType.getName(), p);
			}
			// assert global jsnative object has all the Function methods
			for (IJstMethod method : jstFunc.getStaticMethods()) {
				IJstMethod m = jstType.getMethod(method.getName().getName(), method.isStatic());
				Assert.assertNotNull("Did not find method "+method.getName().getName()+
						" in "+jstType.getName(), m);
			}
		}
		
	}

	private void assertImplments(JstQueryExecutor qe, IJstType jst) {
		List<IJstType> satisfies = (List<IJstType>) jst.getSatisfies();
		if (satisfies.isEmpty()) {
			return;
		}
		for (IJstType jstType : satisfies) {
			IJstType actual = qe.findType(
					new TypeName(JstTypeSpaceMgr.JS_NATIVE_GRP,jstType.getName()));
			if(actual==null){
				actual = qe.findType(
						new TypeName(JstTypeSpaceMgr.JS_BROWSER_GRP,jstType.getName()));
			}
			Assert.assertEquals("assertImplments> failed for type " + jstType.getName(), jstType, actual);
			assertImplments(qe, jstType);
		}
	}

	private void assertInheratence(JstQueryExecutor qe, IJstType jst) {
		JstType extend = (JstType) jst.getExtend();
		if (extend == null) {
			return;
		}
		if (extend.getSimpleName().startsWith("RuntimeException")) {
			return;
		}
		IJstType actual = qe.findType(
				new TypeName(JstTypeSpaceMgr.JS_NATIVE_GRP,extend.getName()));
	
		if(actual==null){
			actual = qe.findType(
					new TypeName(JstTypeSpaceMgr.JS_BROWSER_GRP,extend.getName()));
		}
		
		Assert.assertEquals("assertInheratence> failed for type " + jst.getName(), extend, actual);
		assertInheratence(qe, extend);
	}

	private void assertJavaDoc(IJstDoc doc) {
		Assert.assertNotNull(doc);
		String comment = doc.getComment();
		Assert.assertNotNull(comment);
//		Assert.assertTrue(comment.startsWith("/**") && comment.endsWith("*/\n"));
	}

	private void assertSupportedByAnnotation(
			Class klass, String mtdName, IJstNode jstNode, boolean isProperty) {
		BrowserSupport supportedBy = getSupportedBy(klass, mtdName, isProperty);
		IJstAnnotation annot = jstNode.getAnnotation(BrowserSupport.class.getSimpleName());
		if (supportedBy != null) {
		
			Assert.assertNotNull(annot);
			Assert.assertEquals(BrowserSupport.class.getSimpleName(), annot.getName().toString());
			
			// Check browser types
			for (BrowserType browserType : supportedBy.value()) {
				Assert.assertTrue(SupportedBrowser.isSupportedBy(annot, browserType));
			}
			
			if (SupportedBrowser.isFF1Plus(annot)) {
				Assert.assertTrue(SupportedBrowser.isFF2Plus(annot));
				Assert.assertTrue(SupportedBrowser.isFF3Plus(annot));
			}
			if (SupportedBrowser.isIE6Plus(annot)) {
				Assert.assertTrue(SupportedBrowser.isIE7Plus(annot));
				Assert.assertTrue(SupportedBrowser.isIE8Plus(annot));
			}
		}
		
		DOMSupport supportedByDOM = getSupportedByDOM(klass, mtdName, isProperty);
		if (supportedByDOM != null) {
			
			annot = jstNode.getAnnotation(DOMSupport.class.getSimpleName());
		
			Assert.assertNotNull(annot);
			Assert.assertEquals(DOMSupport.class.getSimpleName(), annot.getName().toString());
			
			DomLevel domLevel = supportedByDOM.value();
			if (domLevel != null) {
				if (SupportedDomLevel.isLevelZero(annot)) {
					Assert.assertEquals(DomLevel.ZERO, domLevel);
				}
				if (SupportedDomLevel.isLevelOne(annot)) {
					Assert.assertEquals(DomLevel.ONE, domLevel);
				}
				if (SupportedDomLevel.isLevelTwo(annot)) {
					Assert.assertEquals(DomLevel.TWO, domLevel);
				}
				if (SupportedDomLevel.isLevelThree(annot)) {
					Assert.assertEquals(DomLevel.THREE, domLevel);
				}
			}
		
		}
		
		JsSupport supportedByJS = getSupportedByJS(klass, mtdName, isProperty);
		if (supportedByJS != null) {
		
		annot = jstNode.getAnnotation(JsSupport.class.getSimpleName());
	
			// Check JavaScript version
			for (JsVersion jsVersion : supportedByJS.value()) {
				if (jsVersion != JsVersion.NONE && jsVersion!= JsVersion.UNDEFINED) {
					Assert.assertTrue(jsVersion + " not supported by " + annot.toString(),
							SupportedJsVersion.isSupportedBy(annot, jsVersion));
				}
			}
			
			
			
			if (SupportedJsVersion.mozillaOneDotFour(annot)) {
				Assert.assertFalse(SupportedJsVersion.mozillaOneDotZero(annot));
				Assert.assertTrue(SupportedJsVersion.mozillaOneDotOne(annot));
				Assert.assertTrue(SupportedJsVersion.mozillaOneDotTwo(annot));
				Assert.assertTrue(SupportedJsVersion.mozillaOneDotThree(annot));
			}
			
			if (!SupportedJsVersion.mozillaOneDotThree(annot)) {
				Assert.assertFalse(SupportedJsVersion.mozillaOneDotFour(annot));
				Assert.assertFalse(SupportedJsVersion.mozillaOneDotFive(annot));
				Assert.assertFalse(SupportedJsVersion.mozillaOneDotSix(annot));
				Assert.assertFalse(SupportedJsVersion.mozillaOneDotSeven(annot));
				Assert.assertFalse(SupportedJsVersion.mozillaOneDotEight(annot));
			}
		
		}
	}

	private BrowserSupport getSupportedBy(Class klass, String mtdName, boolean isProperty) {
		if (klass == null || mtdName == null) {
			return null;
		}
		Method m;
		try {
			m = klass.getMethod(isProperty ? normalizePropertyName(mtdName) : mtdName);
		} catch (Exception e) {
			e.printStackTrace();	//KEEPME
			return null;
		}
		return m.getAnnotation(BrowserSupport.class);
	}
	private DOMSupport getSupportedByDOM(Class klass, String mtdName, boolean isProperty) {
		if (klass == null || mtdName == null) {
			return null;
		}
		Method m;
		try {
			m = klass.getMethod(isProperty ? normalizePropertyName(mtdName) : mtdName);
		} catch (Exception e) {
			e.printStackTrace();	//KEEPME
			return null;
		}
		return m.getAnnotation(DOMSupport.class);
	}
	private JsSupport getSupportedByJS(Class klass, String mtdName, boolean isProperty) {
		if (klass == null || mtdName == null) {
			return null;
		}
		Method m;
		try {
			m = klass.getMethod(isProperty ? normalizePropertyName(mtdName) : mtdName);
		} catch (Exception e) {
			e.printStackTrace();	//KEEPME
			return null;
		}
		return m.getAnnotation(JsSupport.class);
	}

	private String normalizePropertyName(String mtdName) {
		if (Character.isUpperCase(mtdName.charAt(0))) {
			return "get"+mtdName;
		}
		return "get" +  mtdName.substring(0, 1).toUpperCase() + mtdName.substring(1);
	}

	private void dump(JstType jstType) {
		Z z = new Z();
		z.format(">> JstType");
		z.format("name", jstType.getName());
		z.format("extends", jstType.getExtends());
		if (jstType.getDoc() != null) {
			z.format("javadoc", jstType.getDoc().getComment());
		}
		if (!jstType.getAnnotations().isEmpty()) {
			z.format("annotations", jstType.getAnnotations());
		}
		if (jstType.getDoc() != null) {
			z.format("javadoc", jstType.getDoc().getComment());
		}
		z.format("instance props:");
		for (IJstProperty p : jstType.getAllPossibleProperties(false, true)) {
			if (p.getDoc() != null) {
				z.format("\tprops-javadoc", p.getDoc().getComment());
			}
			if (!p.getAnnotations().isEmpty()) {
				z.format("\tprops-annotations", p.getAnnotations());
			}
			z.append("\t"+p.getType().getName());
			z.append(" "+ p.getName());
			z.format(";");
		}
		
		z.format("instance methods:");
		for (IJstMethod m : jstType.getMethods(false, true)) {
			if (m.getDoc() != null) {
				z.format("\tmtd-javadoc", m.getDoc().getComment());
			}
			if (!m.getAnnotations().isEmpty()) {
				z.format("\tmtd-annotations", m.getAnnotations());
			}
			z.append("\t");
			if (m.getRtnType() != null) {
				z.append(m.getRtnType().getName());
				z.append(" ");
			}
			z.append(m.getName());
			z.append("(");
			Iterator iter = m.getArgs().iterator();
			while(iter.hasNext()) {
				JstArg arg = (JstArg) iter.next();
				z.append(arg.getType().getName());
				z.append(" "+arg.getName());
				if (iter.hasNext()) {
					z.append(", ");
				}
			}
			z.format(");");
		}

		System.out.println(z);
	}

	@After
	public void tearDown() throws Exception {
		LibManager.getInstance().clear();
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
				IWritableScriptUnit unit = new IWritableScriptUnit(){

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
						// TODO Auto-generated method stub
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
				
				return unit;
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
	
	IJstParseController getController() {
		return new JstParseController(getParser());
	}
	
	IJstTypeLoader getLoader() {
		return new DefaultJstTypeLoader();
	}
}

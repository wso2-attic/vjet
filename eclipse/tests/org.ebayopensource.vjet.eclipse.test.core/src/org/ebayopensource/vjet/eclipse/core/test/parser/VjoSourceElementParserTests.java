/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.ast.Modifiers;
import org.eclipse.dltk.mod.core.IField;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IPackageDeclaration;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.IJSInitializer;
import org.eclipse.dltk.mod.internal.core.VjoSourceModule;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstProxyMethod;
import org.ebayopensource.dsf.jst.declaration.JstSynthesizedMethod;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;
import org.ebayopensource.vjet.eclipse.core.IImportContainer;
import org.ebayopensource.vjet.eclipse.core.IJSField;
import org.ebayopensource.vjet.eclipse.core.IJSMethod;
import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.IJSType;
import org.ebayopensource.vjo.tool.codecompletion.CodeCompletionUtils;

public class VjoSourceElementParserTests extends AbstractVjoModelTests {

	// @Before
	public void setUp() {
		IProject project = getWorkspaceRoot().getProject(getTestProjectName());
		if (!project.exists()) {
			super.setUpSuite();
			// waitRefreshFinished();
		}

	}

	// @Test
	public void testClass() throws ModelException {
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/testClass.js"));
		// source module itself
		assertNotNull("No source module", module);
		IModelElement[] children = module.getChildren();
		// children general
		assertNotNull("No children", children);
		assertEquals("Wrong size", 3, children.length);
		// package
		IPackageDeclaration[] packages = module.getPackageDeclarations();
		assertEquals("Wrong number of packages", 1, packages.length);
		assertEquals("Wrong package name", "test", packages[0].getElementName());
		// imports
		IImportContainer importContainer = module.getImportContainer();
		assertNotNull("No import container", importContainer);
		IModelElement[] imports = importContainer.getChildren();
		// assertEquals("Wrong number of imports", 3, imports.length);
		// String[] impNames = { "vjo.dsf.Error", "vjo.dsf.test.*",
		// "someLib" };
		// boolean[] isLib = { false, false, true };
		// for (int i = 0; i < impNames.length; i++) {
		// assertEquals("Wrong child type", IImportDeclaration.ELEMENT_TYPE,
		// imports[i].getElementType());
		// assertEquals("Wrong import name or order", impNames[i], imports[i]
		// .getElementName());
		// assertEquals("Wrong import type", isLib[i],
		// ((IImportDeclaration) imports[i]).isLibrary());
		// }
		// types
		IType[] types = module.getTypes();
		assertEquals("Wrong number of types", 1, types.length);
		IJSType type = (IJSType) types[0];
		assertEquals("Wrong type name", "testClass", type.getElementName());
		assertTrue("Is not class", type.isClass());
		assertFalse("Is not class", type.isInterface());
		int flags = type.getFlags();
		assertEquals("Wrong type mofifiers", Modifiers.AccPublic, flags);
		// methods
		IMethod[] methods = type.getMethods();
		assertEquals("Wrong number of method", 6, methods.length);
		// registerSvcHdl
		IJSMethod method = processMethod(methods, "registerSvcHdl", "void",
				Modifiers.AccPublic);
		String[] paramNames = method.getParameters();
		assertTrue("Wrong parameters' names", Arrays.equals(new String[] {
				"svcId", "handler" }, paramNames));
		// getSvcHdl
		method = processMethod(methods, "getSvcHdl", "void",
				Modifiers.AccDefault);
		paramNames = method.getParameters();
		assertTrue("Wrong parameters' names", Arrays.equals(
				new String[] { "svcId" }, paramNames));
		// constructor
		// method = processMethod(methods, "constructs", /*
		// type.getElementName(), */
		// "void", Modifiers.AccDefault);
		// assertTrue("Constructor expected", method.isConstructor());
		// handleRequest
		method = processMethod(methods, "handleRequest", "void",
				Modifiers.AccDefault);
		paramNames = method.getParameters();
		assertTrue("Wrong parameters' names", Arrays.equals(
				new String[] { "message" }, paramNames));
		// bar
		method = processMethod(methods, "bar", "void", Modifiers.AccDefault
				| Modifiers.AccStatic);
		// foo
		method = processMethod(methods, "foo", "String", Modifiers.AccPublic
				| Modifiers.AccStatic);
		paramNames = method.getParameters();
		String[] paramTypes = method.getParameterTypes();
		assertTrue("Numbers of names and types are not equal",
				paramNames.length == paramTypes.length);
		assertTrue("Wrong parameters' names", Arrays.equals(
				new String[] { "name" }, paramNames));
		assertTrue("Wrong parameters' types", Arrays.equals(
				new String[] { "String" }, paramTypes));
	}

	// @Test
	public void testImplementor() throws ModelException {
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/testImplements.js"));
		// source module itself
		assertNotNull("No source module", module);
		IModelElement[] children = module.getChildren();
		// children general
		assertNotNull("No children", children);
		assertEquals("Wrong size", 3, children.length);
		// package
		IPackageDeclaration[] packages = module.getPackageDeclarations();
		assertEquals("Wrong number of packages", 1, packages.length);
		assertEquals("Wrong package name", "test", packages[0].getElementName());

		// types
		IType[] types = module.getTypes();
		assertEquals("Wrong number of types", 1, types.length);
		IJSType type = (IJSType) types[0];
		assertEquals("Wrong type name", "testImplements", type.getElementName());
		assertTrue("Is not class", type.isClass());
		assertFalse("Is not class", type.isInterface());
		int flags = type.getFlags();
		assertEquals("Wrong type modifiers", Modifiers.AccPublic, flags);
		String[] superinterfaces = type.getSuperInterfaceNames();
		assertNotNull("Superinterfaces are not specified", superinterfaces);
		assertEquals("Wrong number of superinterfaces", 1,
				superinterfaces.length);
		assertEquals("Wrong superinterface",
				"org.ebayopensource.dsf.resource.html.event.handler.IJsService",
				superinterfaces[0]);

		// methods
		IMethod[] methods = type.getMethods();
		assertEquals("Wrong number of method", 1, methods.length);
		// constructor
		IJSMethod method = processMethod(methods, "constructs", /*
																 * type.getElementName(),
																 */
		"void", Modifiers.AccDefault);
		assertTrue("Constructor expected", method.isConstructor());
	}

	// @Test
	public void testExtends() throws ModelException {
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/testExtends.js"));
		// source module itself
		assertNotNull("No source module", module);
		IModelElement[] children = module.getChildren();
		// children general
		assertNotNull("No children", children);
		assertEquals("Wrong size", 3, children.length);
		// package
		IPackageDeclaration[] packages = module.getPackageDeclarations();
		assertEquals("Wrong number of packages", 1, packages.length);
		assertEquals("Wrong package name", "test", packages[0].getElementName());

		// types
		IType[] types = module.getTypes();
		assertEquals("Wrong number of types", 1, types.length);
		IJSType type = (IJSType) types[0];
		assertEquals("Wrong type name", "testExtends", type.getElementName());
		assertTrue("Is not class", type.isClass());
		assertFalse("Is not class", type.isInterface());
		int flags = type.getFlags();
		assertEquals("Wrong type modifiers", Modifiers.AccPublic, flags);
		String[] superclasses = type.getSuperClasses();
		assertNotNull("Superinterfaces are not specified", superclasses);
		assertEquals("Wrong number of superinterfaces", 1, superclasses.length);
		assertEquals("Wrong superinterface", "vjo.darwin.app.MySuperClass",
				superclasses[0]);

		// methods
		IMethod[] methods = type.getMethods();
		assertEquals("Wrong number of method", 1, methods.length);
		// constructor
		// IJSMethod method = processMethod(methods, "constructs", /*
		// ctype.getElementName(), */
		// "void", Modifiers.AccDefault);
		// assertTrue("Constructor expected", method.isConstructor());
	}

	// @Test
	public void testFinal() throws ModelException {
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/testFinal.js"));
		// source module itself
		assertNotNull("No source module", module);
		IModelElement[] children = module.getChildren();
		// children general
		assertNotNull("No children", children);
		assertEquals("Wrong size", 2, children.length);
		// package
		IPackageDeclaration[] packages = module.getPackageDeclarations();
		assertEquals("Wrong number of packages", 1, packages.length);
		assertEquals("Wrong package name", "test", packages[0].getElementName());

		// types
		IType[] types = module.getTypes();
		assertEquals("Wrong number of types", 1, types.length);
		IJSType type = (IJSType) types[0];
		assertEquals("Wrong type name", "testFinal", type.getElementName());
		assertTrue("Is not class", type.isClass());
		assertFalse("Is not class", type.isInterface());
		// is final
		int flags = type.getFlags();
		assertEquals("Wrong type modifiers", Modifiers.AccPublic
				| Modifiers.AccFinal, flags);
	}

	// @Test
	public void testInitializer() throws ModelException {
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src",
				new Path("test/testInitializer.js"));
		// source module itself
		assertNotNull("No source module", module);
		IModelElement[] children = module.getChildren();
		// children general
		assertNotNull("No children", children);
		assertEquals("Wrong size", 2, children.length);
		// package
		IPackageDeclaration[] packages = module.getPackageDeclarations();
		assertEquals("Wrong number of packages", 1, packages.length);
		assertEquals("Wrong package name", "test", packages[0].getElementName());

		// types
		IType[] types = module.getTypes();
		assertEquals("Wrong number of types", 1, types.length);
		IJSType type = (IJSType) types[0];

		assertEquals("Wrong type name", "testInitializer", type
				.getElementName());
		assertTrue("Is not class", type.isClass());
		assertFalse("Is not class", type.isInterface());
		// initializer
		children = type.getChildren();
		assertEquals("Wrong number of type's children", 1, children.length);
		assertEquals("Is not initializer", IJSInitializer.ELEMENT_TYPE,
				children[0].getElementType());
		assertEquals("Is not static", Modifiers.AccStatic,
				((IJSInitializer) children[0]).getFlags());
	}

	// @Test
	public void testFields() throws ModelException {
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/testFields.js"));
		// source module itself
		assertNotNull("No source module", module);
		IModelElement[] children = module.getChildren();
		// children general
		assertNotNull("No children", children);
		assertEquals("Wrong size", 2, children.length);
		// package
		IPackageDeclaration[] packages = module.getPackageDeclarations();
		assertEquals("Wrong number of packages", 1, packages.length);
		assertEquals("Wrong package name", "test", packages[0].getElementName());

		// types
		IType[] types = module.getTypes();
		assertEquals("Wrong number of types", 1, types.length);
		IJSType type = (IJSType) types[0];
		assertEquals("Wrong type name", "testFields", type.getElementName());
		assertTrue("Is not class", type.isClass());
		assertFalse("Is not class", type.isInterface());
		// fields
		IField[] fields = type.getFields();
		assertEquals("Wrong number of fieds", 3, fields.length);
		IJSField field = processField(fields, "x", "int",
				Modifiers.AccDefault | Modifiers.AccStatic);
		assertEquals("Wrong constant value", "5", field.getConstant());
		field = processField(fields, "y", "String", Modifiers.AccPublic
				| Modifiers.AccStatic | Modifiers.AccFinal);
		assertEquals("Wrong constant value", "Test value", field.getConstant());
		field = processField(fields, "z", "String", Modifiers.AccProtected);
		assertNull("Wrong constant value", field.getConstant());
	}

	public void testInnerTypes() throws ModelException {
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/testInnerType.js"));
		// source module itself
		assertNotNull("No source module", module);
		IModelElement[] children = module.getChildren();
		// children general
		assertNotNull("No children", children);
		assertEquals("Wrong size", 3, children.length);
		// type
		IJstType type = ((VjoSourceModule) module).getJstType();
		assertNotNull("Type is null", type);
		// inner types
		List<? extends IJstType> innerTypes = type.getEmbededTypes();
		assertNotNull("No inner types", innerTypes);
		assertEquals("Wrong number of inner types", 2, innerTypes.size());
		assertEquals("Wrong inner type", "test.testInnerType.innerM",
				innerTypes.get(0).getName());
		assertEquals("Wrong inner type", "test.testInnerType.innerM2",
				innerTypes.get(1).getName());
	}

	public void testInnerTypeNameRange() throws ModelException {
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path(
						"test/testInnerTypeNameRange.js"));
		// source module itself
		assertNotNull("No source module", module);
		IModelElement[] children = module.getChildren();
		// children general
		assertNotNull("No children", children);
		assertEquals("Wrong size", 3, children.length);
		// type
		IJstType type = ((VjoSourceModule) module).getJstType();
		assertNotNull("Type is null", type);
		// inner types
		List<? extends IJstType> innerTypes = type.getEmbededTypes();
		assertNotNull("No inner types", innerTypes);
		assertEquals("Wrong number of inner types", 2, innerTypes.size());
		IJstType firstInnerType = innerTypes.get(0);
		JstSource source = firstInnerType.getSource();

		assertEquals("Wrong inner type start offset", 103, source
				.getStartOffSet());
		assertEquals("Wrong inner type name length", 5, source.getLength());

	}

	public void testMain() throws ModelException {
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/MainTest.js"));
		// source module itself
		assertNotNull("No source module", module);
		IModelElement[] children = module.getChildren();
		assertEquals("Wrong number of children", 3, children.length);
		// type
		IJstType type = ((VjoSourceModule) module).getJstType();
		assertNotNull("Type is null", type);
		// methods
		List methods = type.getMethods();
		assertNotNull("No methods", methods);

		int methodSize = methods.size();
		for (Object method : methods) {
			if (method instanceof JstSynthesizedMethod) {
				methodSize--;
			}

		}
		assertEquals("Wrong number of methods", 1, methodSize);
	}

	// @Test
	public void testOverloadMethods() throws ModelException {
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path(
						"test/TestOverloadMethods.js"));
		// source module itself
		assertNotNull("No source module", module);
		IModelElement[] children = module.getChildren();
		// children general
		assertNotNull("No children", children);
		assertEquals("Wrong size", 3, children.length);

		// types
		IType[] types = module.getTypes();
		assertEquals("Wrong number of types", 1, types.length);

		// BEGIN: We get the JstType from the ISourceModule, and then use
		// JstTypeHelper.getSignatureMethods(type) to get overloaded methods but
		// exclude dispatched method.
		IJstType type = ((VjoSourceModule) module).getJstType();
		Collection<? extends IJstMethod> methodsMap = JstTypeHelper
				.getSignatureMethods(type);
		// END.

		int size = methodsMap.size();
		for (IJstMethod jstMethod : methodsMap) {
			if (CodeCompletionUtils.isSynthesizedElement(jstMethod)) {
				size--;
			}
		}
		// If we don't use JstTypeHelper.getSignatureMethods(type), it will
		// return 3 methods.
		assertEquals("Wrong type name", 2, size);
	}

	// @Test
	public void testBug6055() throws ModelException {
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/TestTiger.js"));
		// source module itself
		assertNotNull("No source module", module);
		IModelElement[] children = module.getChildren();

		// types
		IType[] types = module.getTypes();
		assertEquals("Wrong number of types", 1, types.length);

		// BEGIN: We get the JstType from the ISourceModule, and then use
		// JstTypeHelper.getSignatureConstructors(type) to get constructor.

		IJstType type = ((VjoSourceModule) module).getJstType();
		Collection<? extends IJstMethod> constructorList = JstTypeHelper
				.getSignatureConstructors(type);
		// END.

		// If the JstTypeHelper.getSignatureConstructors(type) is corrected,
		// will uncomment following line.
		assertEquals("Wrong constructort number", 1, constructorList.size());
	}

	/**
	 * @Test
	 * //@Description("Bug4136: It tests same type but in different packages
	 *                        should be displayed in outline view")
	 * @throws ModelException
	 */
	public void testBug4136() throws ModelException {
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/TestImps.js"));
		// source module itself
		assertNotNull("No source module", module);
		IType[] types = module.getTypes();
		assertEquals("Wrong number of types", 1, types.length);
		// type
		IJstType type = ((VjoSourceModule) module).getJstType();
		assertNotNull("Type is null", type);

		List<? extends IJstType> importsList = type.getImports();
		assertNotNull("No imports", importsList);
		assertEquals("Wrong number of import", 4, importsList.size());

		String ctypeName = "org.ebayopensource.vjo.CType";
		boolean isExistingInNeeds = false;
		for (Object needObject : importsList) {
			JstType need = (JstType) needObject;
			String typeName = need.getName();
			if (ctypeName.endsWith(typeName)) {
				isExistingInNeeds = true;
			}
		}
		assertEquals("'org.ebayopensource.vjo.CType' is not in the Needs area", true,
				isExistingInNeeds);
	}

	/**
	 * @Test
	 * //@Description("Bug4749: the highlight area always is shown wrong when we
	 *                        click the method in the outline. Now it is no
	 *                        behavior if the method we click in the outline
	 *                        view is not in current editor, only handle the
	 *                        method in current .js file. ")
	 * @throws ModelException
	 */
	public void testBug4749() throws ModelException {
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getTestProjectName(), "src", new Path("test/Drager.js"));
		// source module itself
		assertNotNull("No source module", module);
		IModelElement[] children = module.getChildren();
		// children general
		assertNotNull("No children", children);
		assertEquals("Wrong size", 3, children.length);
		// type
		IJstType type = ((VjoSourceModule) module).getJstType();
		assertNotNull("Type is null", type);
		// inner types
		List<? extends IJstMethod> methods = type.getMethods();
		assertEquals("Wrong method number", 3, methods.size());
		List<IJstMethod> jstMethods = new ArrayList<IJstMethod>();
		List<IJstMethod> jstProxyMethods = new ArrayList<IJstMethod>();

		for (IJstMethod method : methods) {
			if (method instanceof JstMethod) {
				jstMethods.add(method);
			}
			if (method instanceof JstProxyMethod) {
				jstProxyMethods.add(method);
			}
		}
		// There is a real method in 
		assertEquals(jstMethods.size(), 1);
		assertEquals(jstProxyMethods.size(), 2);

		for (Object method : jstProxyMethods) {
			String methodName = ((JstProxyMethod) method).getOriginalName();
			assertTrue(
					"There are 2 methods ('Drag','Drop') that do not beloing to this type",
					"drag".equals(methodName) || "drop".equals(methodName));
		}

	}
}

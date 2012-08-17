/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.parser;

import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.ISourceRange;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.ImportContainer;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.IJSMethod;
import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.test.FixtureUtils;
import org.ebayopensource.vjet.testframework.fixture.FixtureManager;

public class VjoNativeOffsetTests extends AbstractSearchModelTests {

	public void setUp() throws Exception {
		super.setUp();
		// TypeSpaceMgr mgr = TypeSpaceMgr.getInstance();
		// Collection<IJstType> types = mgr.getAllTypes();
		// for(IJstType type : types) {
		// if(type.getName() == null)
		// continue;
		// RemoveTypeEvent removeEvent = new RemoveTypeEvent(new
		// TypeName(getProjectName(), type.getName()));
		// mgr.processEvent(removeEvent);
		// }
	}

	public void testOnNativeSource() throws ModelException {
		String js = "search/NativeTypeA.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));

			IType[] types = module.getTypes();
			assertEquals("Wrong number of types", 1, types.length);
			IJstType nativeJstType = getNativeJstType("Array");
			IJstMethod element = nativeJstType.getMethod("reverse");
			
			assertNotSame("native type source is null", null, element.getSource());
			if (element.getSource()!=null){
				int offset = element.getSource().getStartOffSet();
				assertNotSame("Invalid offset, the actrual value is -1", -1, offset);
			}
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testOnNativeMethodOffset() throws ModelException {
		String js = "search/NativeTypeA.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));

			IType[] types = module.getTypes();
			assertEquals("Wrong number of types", 1, types.length);
			// IJSType type = (IJSType) types[0];

			// VjoMatch[] matches=null;
			IType nativeType = getNativeType("Array");

			IJSMethod element = findMethodByName(nativeType.getMethods(),
					"reverse");
			ISourceRange range = element.getSourceRange();
			int offset = range.getOffset();
			assertNotSame("Invalid offset, the actrual value is -1", -1, offset);
			// assertTrue(offset != -1);

			// nativeType.getSourceModule().getChildren();
		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	public void testOnNativeNeedOffset() throws ModelException {
		String js = "search/NativeTypeA.js";
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this, js);
		try {
			IJSSourceModule module = (IJSSourceModule) getSourceModule(
					getProjectName(), "src", new Path(js));

			IType[] types = module.getTypes();
			assertEquals("Wrong number of types", 1, types.length);
			// IJSType type = (IJSType) types[0];

			// VjoMatch[] matches=null;
			IType nativeType = getNativeType("Array");

			IModelElement[] elements = nativeType.getSourceModule()
					.getChildren();
			for (IModelElement element : elements) {
				if (element instanceof ImportContainer) {
					ISourceRange range = ((ImportContainer) element)
							.getSourceRange();
					int offset = range.getOffset();
					assertNotSame("Invalid offset, the actrual value is -1",
							-1, offset);
				}
			}

		} finally {
			FixtureUtils.tearDownFixture(m_fixtureManager);
		}
	}

	private IJstType getNativeJstType(String typeName) {
		IJstType arrayJstType = CodeassistUtils.findNativeJstType(typeName);
		assertNotNull(arrayJstType);

		return arrayJstType;
	}

	private IType getNativeType(String typeName) {
		IJstType arrayJstType = CodeassistUtils.findNativeJstType(typeName);
		assertNotNull(arrayJstType);

		IType arrayType = CodeassistUtils.findNativeSourceType(arrayJstType);
		assertNotNull(arrayType);
		return arrayType;
	}

}

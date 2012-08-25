/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;
import org.ebayopensource.dsf.jstojava.parser.SyntaxTreeFactory2;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.junit.Before;
import org.junit.Test;




//@Category({P1, FAST, UNIT})
//@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")
public class MixinTranslatorTest extends BaseTest{

	IJstType mtype;
	TranslateCtx ctx;
	IJstType mtype2;
	TranslateCtx ctx2;
	
	@Before
	public void prepareTest() {
		CompilationUnitDeclaration ast = prepareAst("mTypeTranslatorTestFile.txt",
				null);
		ctx = new TranslateCtx();
		mtype = SyntaxTreeFactory2.createJST(ast, ctx);
		assertNotNull(mtype);
		
		// add to cache
		
		JstCache.getInstance().addType((JstType) mtype);
		
		
		CompilationUnitDeclaration ast2 = prepareAst("mTypeCTranslatorTestFile.txt",
				null);
		ctx2 = new TranslateCtx();
		mtype2 = SyntaxTreeFactory2.createJST(ast2, ctx2);
		assertNotNull(mtype2);
		
		// add to cache
		JstCache.getInstance().addType((JstType) mtype2);
	}
	
	@Test //@Category({P1, FAST, UNIT})
	//@Description("Test Ctype mixin with mtype. check the package and type information.")
	public void testMixin() {
		CompilationUnitDeclaration ast = prepareAst("mixinTranslatorTestFile.js.txt",
				null);
//		ITypeManager typeManager1 = ctx.getTypeManager();
		IJstType jstType = SyntaxTreeFactory2.createJST(ast, ctx);
		assertNotNull(jstType);
//		ITypeManager typeManager2 = ctx.getTypeManager();
		
//		assertTrue(typeManager1.equals(typeManager2));
		
		assertEquals("com.ebay.vjet", jstType.getPackage().getName());
		assertEquals("A", jstType.getSimpleName());
		
		List<? extends IJstTypeReference> mixins = jstType.getMixinsRef();
		assertNotNull(mixins);
		assertEquals(2, mixins.size());
		
		IJstType mixin = mixins.get(0).getReferencedType();
		assertTrue(mixin.isMixin());
		IJstType mixin2 = mixins.get(1).getReferencedType();
		assertTrue(mixin2.isMixin());
		
		assertEquals("com.ebay.vjet.fld", mixin.getPackage().getName());
		assertEquals("B", mixin.getSimpleName());
		assertEquals(3, mixin.getMethods().size());
		assertEquals(4, JstTypeHelper.getDeclaredProperties(mixin.getProperties()).size());
	}
}

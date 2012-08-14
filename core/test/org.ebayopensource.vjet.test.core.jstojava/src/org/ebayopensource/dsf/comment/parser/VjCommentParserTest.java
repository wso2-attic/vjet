/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.comment.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import junit.framework.TestCase;

import org.ebayopensource.dsf.jst.meta.JsType;
import org.ebayopensource.dsf.jst.meta.JsTypingMeta;
import org.ebayopensource.dsf.jst.meta.IJsCommentMeta.DIRECTION;
import org.ebayopensource.dsf.jst.meta.ArgType;
import org.ebayopensource.dsf.jst.meta.ArgType.WildCardType;
import org.ebayopensource.dsf.jstojava.parser.comments.JsAttributed;
import org.ebayopensource.dsf.jstojava.parser.comments.JsCommentMeta;
import org.ebayopensource.dsf.jstojava.parser.comments.JsFuncArgAttributedType;
import org.ebayopensource.dsf.jstojava.parser.comments.JsFuncScopeAttributedType;
import org.ebayopensource.dsf.jstojava.parser.comments.JsFuncType;
import org.ebayopensource.dsf.jstojava.parser.comments.JsMixinType;
import org.ebayopensource.dsf.jstojava.parser.comments.JsParam;
import org.ebayopensource.dsf.jstojava.parser.comments.JsVariantType;
import org.ebayopensource.dsf.jstojava.parser.comments.ParseException;
import org.ebayopensource.dsf.jstojava.parser.comments.VjComment;
import org.ebayopensource.dsf.jstojava.parser.comments.VjCommentUtil;
import org.junit.Test;

public class VjCommentParserTest {
	
	@Test
	public void testType() throws ParseException {
		JsCommentMeta meta = VjComment.parse("//>A");
		TestCase.assertFalse(((JsType)meta.getTyping()).isTypeRef());
		TestCase.assertEquals("A", meta.getTyping().getType());
		
		meta = VjComment.parse("/*>A*/");
		TestCase.assertEquals("A", meta.getTyping().getType());
	}
	
	@Test
	public void testTypeRef() throws ParseException {
		JsCommentMeta meta = VjComment.parse("//>type::A");
		TestCase.assertTrue(((JsType)meta.getTyping()).isTypeRef());
		TestCase.assertEquals("A", meta.getTyping().getType());
		
		meta = VjComment.parse("//>Type::A"); //Old format with Type::
		TestCase.assertTrue(((JsType)meta.getTyping()).isTypeRef());
		TestCase.assertEquals("A", meta.getTyping().getType());
		
		meta = VjComment.parse("/*<type::A*/");
		TestCase.assertTrue(((JsType)meta.getTyping()).isTypeRef());
		TestCase.assertEquals("A", meta.getTyping().getType());
	}
	
	@Test
	public void testAttributedType() throws ParseException {
		JsCommentMeta meta = VjComment.parse("//>A:a");
		TestCase.assertTrue(meta.getTyping() instanceof JsAttributed);
		JsAttributed attributed = (JsAttributed) meta.getTyping();
		TestCase.assertEquals("A", attributed.getAttributor().getType());
		TestCase.assertEquals("a", attributed.getName());
		TestCase.assertTrue(attributed.isInstance());
		
		meta = VjComment.parse("//>A::b");
		attributed = (JsAttributed) meta.getTyping();
		TestCase.assertEquals("A", attributed.getAttributor().getType());
		TestCase.assertEquals("b", attributed.getName());
		TestCase.assertFalse(attributed.isInstance());
		
		meta = VjComment.parse("//>A::");
		attributed = (JsAttributed) meta.getTyping();
		TestCase.assertEquals("A", attributed.getAttributor().getType());
		TestCase.assertEquals("$missing$", attributed.getName());
		TestCase.assertFalse(attributed.isInstance());
		
		meta = VjComment.parse("//>A:");
		attributed = (JsAttributed) meta.getTyping();
		TestCase.assertEquals("A", attributed.getAttributor().getType());
		TestCase.assertEquals("$missing$", attributed.getName());
		TestCase.assertTrue(attributed.isInstance());
		
		meta = VjComment.parse("//>::");
		attributed = (JsAttributed) meta.getTyping();
		TestCase.assertTrue(attributed.isAttributedFromGlobal());
		TestCase.assertEquals("$missing$", attributed.getName());
		TestCase.assertFalse(attributed.isInstance());
		
		meta = VjComment.parse("//>::b");
		attributed = (JsAttributed) meta.getTyping();
		TestCase.assertTrue(attributed.isAttributedFromGlobal());
		TestCase.assertEquals("b", attributed.getName());
		TestCase.assertEquals(null, attributed.getAttributor());
		
		meta = VjComment.parse("//>::b fn(A::x, B:z)");
		JsFuncType typing = (JsFuncType)meta.getTyping();
		attributed = (JsAttributed)typing.getReturnType();
		TestCase.assertTrue(attributed.isAttributedFromGlobal());
		TestCase.assertEquals("b", attributed.getName());
		
		List<JsParam> params = typing.getParams();
		attributed = (JsAttributed)params.get(0).getTypes().get(0);
		TestCase.assertEquals("A", attributed.getAttributor().getType());
		TestCase.assertEquals("x", attributed.getName());
		TestCase.assertFalse(attributed.isInstance());
		
		attributed = (JsAttributed)params.get(1).getTypes().get(0);
		TestCase.assertEquals("B", attributed.getAttributor().getType());
		TestCase.assertEquals("z", attributed.getName());
		TestCase.assertTrue(attributed.isInstance());
	}
	
	@Test
	public void testVariantType() throws ParseException {
		JsCommentMeta meta = VjComment.parse("//>{A|B}");
		TestCase.assertTrue(meta.getTyping() instanceof JsVariantType);
		List<JsTypingMeta> types = ((JsVariantType)meta.getTyping()).getTypes();
		TestCase.assertEquals("A", types.get(0).getType());
		TestCase.assertEquals("B", types.get(1).getType());
	}
	
	@Test
	public void testDirection() throws ParseException {
		JsCommentMeta meta = VjComment.parse("//>A");
		TestCase.assertEquals(DIRECTION.FORWARD, meta.getDirection());

		meta = VjComment.parse("//<A");
		TestCase.assertEquals(DIRECTION.BACK, meta.getDirection());
		
		meta = VjComment.parse("/*<A */");
		TestCase.assertEquals(DIRECTION.BACK, meta.getDirection());
		
		meta = VjComment.parse("/*>A */");
		TestCase.assertEquals(DIRECTION.FORWARD, meta.getDirection());
	}
	
	@Test
	public void testCast() throws ParseException {
		JsCommentMeta meta = VjComment.parse("//>>A");
		TestCase.assertEquals(DIRECTION.FORWARD, meta.getDirection());
		TestCase.assertTrue(meta.isCast());

		meta = VjComment.parse("//<<A");
		TestCase.assertEquals(DIRECTION.BACK, meta.getDirection());
		TestCase.assertTrue(meta.isCast());
	}
	
	@Test
	public void testHTMLTag() throws ParseException {
		assertFalse(VjCommentUtil.isVjetComment("//<p>"));
		assertFalse(VjCommentUtil.isVjetComment("// <debug>"));
		assertTrue(VjCommentUtil.isVjetComment("//< Class<T>"));
		assertTrue(VjCommentUtil.isVjetComment("//> Class<T>"));
	}
	
	
	@Test
	public void testOptionalReturn() throws ParseException {
		JsCommentMeta meta = VjComment.parse("//>public String? zot()");
		assertEquals("String", meta.getTyping().getType());
		assertTrue(meta.getTyping().isOptional());
		
	}
	
	@Test
	public void testOptionalReturnCallBack() throws Exception {
		JsCommentMeta meta = VjComment.parse("//>public (int? fnreturn(int))? foo((int? fnparam(String)) x)");
		JsTypingMeta typing = meta.getTyping();
		TestCase.assertTrue(typing instanceof JsFuncType);
		assertTrue(typing.isOptional());
		JsTypingMeta retTyping = ((JsFuncType)typing).getReturnType();
		TestCase.assertTrue(retTyping instanceof JsFuncType);
		JsFuncType funcType = (JsFuncType)retTyping;
		TestCase.assertEquals("int", funcType.getReturnType().getType());
		// support for function return values which have optional return
		TestCase.assertTrue(funcType.getReturnType().isOptional());
		
		TestCase.assertEquals("int", funcType.getParams().get(0).getType());

		JsTypingMeta paramTyping = ((JsFuncType)typing).getParams().get(0).getTypes().get(0);
		TestCase.assertTrue(paramTyping instanceof JsFuncType);
		funcType = (JsFuncType)paramTyping;
		// support for function params/ callbacks which have optional return
		TestCase.assertTrue(funcType.getReturnType().isOptional());
		TestCase.assertEquals("int", funcType.getReturnType().getType());
		TestCase.assertEquals("String", funcType.getParams().get(0).getType());	
	}
	
	
	
	@Test
	public void testModifiers() throws ParseException {
		JsCommentMeta meta = VjComment.parse("//>public A");
		TestCase.assertTrue(meta.getModifiers().isPublic());

		meta = VjComment.parse("//<protected A");
		TestCase.assertTrue(meta.getModifiers().isProtected());
		
		meta = VjComment.parse("//<private A");
		TestCase.assertTrue(meta.getModifiers().isPrivate());
		
		meta = VjComment.parse("//<A");
		TestCase.assertTrue(meta.getModifiers().isNone());
		TestCase.assertTrue(meta.getModifiers().isInternal());
		
		meta = VjComment.parse("//<public static final A");
		TestCase.assertTrue(meta.getModifiers().isPublic());
		TestCase.assertTrue(meta.getModifiers().isStatic());
		TestCase.assertTrue(meta.getModifiers().isFinal());
		
		meta = VjComment.parse("//<dynamic A");
		TestCase.assertTrue(meta.getModifiers().isDynamic());
	}
	
	@Test
	public void testFunction() throws ParseException {
		JsCommentMeta meta = VjComment.parse("//>public X foo(int x, A y)");
		TestCase.assertTrue(meta.getModifiers().isPublic());
		TestCase.assertTrue(meta.isMethod());
		TestCase.assertEquals("foo", meta.getName());
		TestCase.assertEquals("X", meta.getTyping().getType());
		
		List<JsParam> params = ((JsFuncType)meta.getTyping()).getParams();
		JsParam param0 = params.get(0);
		TestCase.assertEquals("x", param0.getName());
		TestCase.assertEquals("int", param0.getType());
		
		JsParam param1 = params.get(1);
		TestCase.assertEquals("y", param1.getName());
		TestCase.assertEquals("A", param1.getType());
		TestCase.assertFalse(param1.isOptional());
	}
	
	@Test
	public void testFunctionWithOptionalParams() throws ParseException {
		JsCommentMeta meta = VjComment.parse("//>public void foo(int x, A? y, B? z, {C|D}? u)");
	
		List<JsParam> params = ((JsFuncType)meta.getTyping()).getParams();

		TestCase.assertFalse(params.get(0).isOptional());
		
		JsParam param1 = params.get(1);
		TestCase.assertEquals("y", param1.getName());
		TestCase.assertEquals("A", param1.getType());
		TestCase.assertTrue(param1.isOptional());
		
		JsParam param2 = params.get(2);
		TestCase.assertEquals("z", param2.getName());
		TestCase.assertEquals("B", param2.getType());
		TestCase.assertTrue(param2.isOptional());
		
		JsParam param3 = params.get(3);
		TestCase.assertEquals("u", param3.getName());
		TestCase.assertTrue(param3.isOptional());
		for (JsTypingMeta paramType : param3.getTypes()) {
			TestCase.assertTrue(paramType.isOptional());
		}
	}
	
	@Test
	public void testFunctionWithVariableParam() throws ParseException {
		JsCommentMeta meta = VjComment.parse("//>public void foo(int x, A... y)");
	
		List<JsParam> params = ((JsFuncType)meta.getTyping()).getParams();
		TestCase.assertFalse(params.get(0).isVariable());
		
		JsParam param1 = params.get(1);
		TestCase.assertEquals("y", param1.getName());
		TestCase.assertEquals("A", param1.getType());
		TestCase.assertTrue(param1.isVariable());
	}
	
	@Test
	public void testFunctionWithGenericParamType() throws ParseException {
		JsCommentMeta meta = VjComment.parse("//>public void test(Collection<? super AbstractList> x)");
		JsParam param = ((JsFuncType)meta.getTyping()).getParams().get(0);
		TestCase.assertEquals("x", param.getName());
		TestCase.assertEquals("Collection", param.getType());
		JsType type = (JsType)param.getTypes().get(0);
		ArgType argType = type.getArgs().get(0);
		TestCase.assertEquals(WildCardType.SUPER, argType.getWildCardType());
		TestCase.assertEquals("AbstractList", argType.getFamily().getType());
		
		meta = VjComment.parse("//>public void test(Collection<? extends ArrayList> x)");
		param = ((JsFuncType)meta.getTyping()).getParams().get(0);
		TestCase.assertEquals("x", param.getName());
		TestCase.assertEquals("Collection", param.getType());
		type = (JsType)param.getTypes().get(0);
		argType = type.getArgs().get(0);
		TestCase.assertEquals(WildCardType.EXTENDS, argType.getWildCardType());
		TestCase.assertEquals("ArrayList", argType.getFamily().getType());
	}
	
	@Test
	public void testFunctionWithMultiTypeParam() throws ParseException {
		JsCommentMeta meta = VjComment.parse("//>public void foo(final {Node|String} x, {String|boolean}? y)");
	
		List<JsParam> params = ((JsFuncType)meta.getTyping()).getParams();
		JsParam param = params.get(0);
		TestCase.assertFalse(param.isOptional());
		TestCase.assertTrue(param.isFinal());
		List<JsTypingMeta> types = param.getTypes();
		TestCase.assertEquals("Node", types.get(0).getType());
		TestCase.assertEquals("String", types.get(1).getType());
		
		param = params.get(1);
		types = param.getTypes();
		TestCase.assertEquals("String", types.get(0).getType());
		TestCase.assertEquals("boolean", types.get(1).getType());
		TestCase.assertTrue(param.isOptional());
		TestCase.assertFalse(param.isFinal());
	}
	
	@Test
	public void testOptionalParamOrdering() throws ParseException {
		VjComment.parse("//>public X foo(int x, A? y, B... z)");

		try {
			VjComment.parse("//>public X foo(int x, A? y, B z)");
			TestCase.fail("should get ParseException");
		}
		catch (ParseException pe) {}
		
		try {
			VjComment.parse("//>public X foo(int x, A... y, B... z)");
			TestCase.fail("should get ParseException");
		}
		catch (ParseException pe) {}
	}
	
	@Test
	public void testFunctionAsTyping() throws ParseException {
		JsCommentMeta meta = VjComment.parse("//>public (int fn(int)) foo((int fn(String)) x)");
		JsTypingMeta typing = meta.getTyping();
		TestCase.assertTrue(typing instanceof JsFuncType);
		JsTypingMeta retTyping = ((JsFuncType)typing).getReturnType();
		TestCase.assertTrue(retTyping instanceof JsFuncType);
		JsFuncType funcType = (JsFuncType)retTyping;
		TestCase.assertEquals("int", funcType.getReturnType().getType());
		TestCase.assertFalse(funcType.isOptional());
		TestCase.assertEquals("int", funcType.getParams().get(0).getType());
		
		JsTypingMeta paramTyping = ((JsFuncType)typing).getParams().get(0).getTypes().get(0);
		TestCase.assertTrue(paramTyping instanceof JsFuncType);
		funcType = (JsFuncType)paramTyping;
		TestCase.assertEquals("int", funcType.getReturnType().getType());
		TestCase.assertEquals("String", funcType.getParams().get(0).getType());		
	}
	
	@Test
	public void testFunctionWithCaret() throws ParseException {
		JsCommentMeta meta = VjComment.parse("//>public ^String foo(String, Function)");
		JsTypingMeta typing = meta.getTyping();
		TestCase.assertTrue(typing instanceof JsFuncType);
		TestCase.assertEquals("String", ((JsFuncType)typing).getReturnType().getType());
		TestCase.assertTrue(((JsFuncType)typing).isTypeFactoryEnabled());
		TestCase.assertFalse(((JsFuncType)typing).isFuncArgMetaExtensionEnabled());
		
		meta = VjComment.parse("//>public String ^foo(String, Function)");
		typing = meta.getTyping();
		TestCase.assertTrue(typing instanceof JsFuncType);
		TestCase.assertEquals("String", ((JsFuncType)typing).getReturnType().getType());
		TestCase.assertFalse(((JsFuncType)typing).isTypeFactoryEnabled());
		TestCase.assertTrue(((JsFuncType)typing).isFuncArgMetaExtensionEnabled());
		
		meta = VjComment.parse("//>public ^String ^foo(String, Function)");
		typing = meta.getTyping();
		TestCase.assertTrue(typing instanceof JsFuncType);
		TestCase.assertEquals("String", ((JsFuncType)typing).getReturnType().getType());
		TestCase.assertTrue(((JsFuncType)typing).isTypeFactoryEnabled());
		TestCase.assertTrue(((JsFuncType)typing).isFuncArgMetaExtensionEnabled());
	}
	
	@Test
	public void testFunctionReturnScopeAttributed() throws ParseException {
		JsCommentMeta meta = VjComment.parse("//>public this foo(String, Function)");
		JsTypingMeta typing = meta.getTyping();
		TestCase.assertTrue(typing instanceof JsFuncType);
		TestCase.assertTrue(((JsFuncType)typing).getReturnType() instanceof JsFuncScopeAttributedType);
	}
	
	@Test
	public void testFunctionReturnArgAttributed() throws ParseException {
		JsCommentMeta meta = VjComment.parse("//>public %2 foo(String, Object)");
		JsTypingMeta typing = meta.getTyping();
		TestCase.assertTrue(typing instanceof JsFuncType);
		TestCase.assertTrue(((JsFuncType)typing).getReturnType() instanceof JsFuncArgAttributedType);
		TestCase.assertEquals(2, ((JsFuncArgAttributedType)((JsFuncType)typing).getReturnType()).getArgIndex());
	}
	
	@Test
	public void testFunctionReturnCombined() throws ParseException {
		JsCommentMeta meta = VjComment.parse("//>public [this + %2] foo(String, Object)");
		JsTypingMeta typing = meta.getTyping();
		TestCase.assertTrue(typing instanceof JsFuncType);
		TestCase.assertTrue(((JsFuncType)typing).getReturnType() instanceof JsMixinType);
		JsMixinType combType = (JsMixinType)((JsFuncType)typing).getReturnType();
		TestCase.assertTrue(combType.getTypes().get(0) instanceof JsFuncScopeAttributedType);
		TestCase.assertTrue(combType.getTypes().get(1) instanceof JsFuncArgAttributedType);
	}
	
	
	@Test
	public void testFunctionArgumentCombined() throws ParseException {
		JsCommentMeta meta = VjComment.parse("//>public [this + %2] foo([Number+String], Object)");
		JsTypingMeta typing = meta.getTyping();
		TestCase.assertTrue(typing instanceof JsFuncType);
		TestCase.assertTrue(((JsFuncType)typing).getReturnType() instanceof JsMixinType);
		JsMixinType combType = (JsMixinType)((JsFuncType)typing).getReturnType();
		TestCase.assertTrue(combType.getTypes().get(0) instanceof JsFuncScopeAttributedType);
		TestCase.assertTrue(combType.getTypes().get(1) instanceof JsFuncArgAttributedType);
	}
	
	@Test
	public void testArrayTyping() throws ParseException {
		JsCommentMeta meta = VjComment.parse("//>public (int fn(int))[] foo(A:b[][], {int[]|A::x[]})");
		JsFuncType func = (JsFuncType)meta.getTyping();
		JsTypingMeta retTyping = func.getReturnType();
		TestCase.assertTrue(retTyping instanceof JsFuncType);
		TestCase.assertEquals(1, retTyping.getDimension());
		
		List<JsParam> params = func.getParams();
		JsAttributed attributed = (JsAttributed)params.get(0).getTypes().get(0);
		TestCase.assertEquals("A", attributed.getAttributor().getType());
		TestCase.assertEquals("b", attributed.getName());
		TestCase.assertTrue(attributed.isInstance());
		TestCase.assertEquals(2, attributed.getDimension());
		
		attributed = (JsAttributed)params.get(1).getTypes().get(1);
		TestCase.assertEquals("A", attributed.getAttributor().getType());
		TestCase.assertEquals("x", attributed.getName());
		TestCase.assertFalse(attributed.isInstance());
		TestCase.assertEquals(1, attributed.getDimension());
	}
	
	@Test
	public void testMixingTypes() throws ParseException {
		JsCommentMeta meta = VjComment.parse("//>public (int fn(int))[] foo(A:b[][], {int[]|A::x[]})");
		JsFuncType func = (JsFuncType)meta.getTyping();
		JsTypingMeta retTyping = func.getReturnType();
		TestCase.assertTrue(retTyping instanceof JsFuncType);
		TestCase.assertEquals(1, retTyping.getDimension());
		
		List<JsParam> params = func.getParams();
		JsAttributed attributed = (JsAttributed)params.get(0).getTypes().get(0);
		TestCase.assertEquals("A", attributed.getAttributor().getType());
		TestCase.assertEquals("b", attributed.getName());
		TestCase.assertTrue(attributed.isInstance());
		TestCase.assertEquals(2, attributed.getDimension());
		
		attributed = (JsAttributed)params.get(1).getTypes().get(1);
		TestCase.assertEquals("A", attributed.getAttributor().getType());
		TestCase.assertEquals("x", attributed.getName());
		TestCase.assertFalse(attributed.isInstance());
		TestCase.assertEquals(1, attributed.getDimension());
	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.completion;




import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjo.tool.codecompletion.comment.VjoCcCommentUtil;
import org.junit.Test;


/**
 *
 */
//@Category({P1,FAST,UNIT})
public class CommentCompletionTest extends BaseTest {

	@Test
	public void testInType() {
		String fileName = "typeComment.js.txt";
		IJstType jstType = getJstTypeFromVjoParser(fileName,
				"org.ebayopensource.dsf.jstojava.translator.robust.completion.typeComment", 
				"//<p");
		assertNotNull(jstType);
		JstCommentCompletion completion = getJstCommentCompletion(jstType);
		assertNotNull(completion);
		assertTrue(VjoCcCommentUtil.getScope(completion) == ScopeIds.TYPE);
		assertEquals("//<p", completion.getCommentBeforeCursor());
	}
	
	@Test
	public void testInMethod() {
		String fileName = "methodComment.js.txt";
		IJstType jstType = getJstTypeFromVjoParser(fileName,
				"org.ebayopensource.dsf.jstojava.translator.robust.completion.methodComment", 
				"//> pri");
		assertNotNull(jstType);
		JstCommentCompletion completion = getJstCommentCompletion(jstType);
		assertNotNull(completion);
		assertTrue(VjoCcCommentUtil.getScope(completion) == ScopeIds.METHOD);
		assertEquals("//> pri", completion.getCommentBeforeCursor());
		
		jstType = getJstTypeFromVjoParser(fileName,
				"org.ebayopensource.dsf.jstojava.translator.robust.completion.methodComment", 
				"//> public St");
		assertNotNull(jstType);
		completion = getJstCommentCompletion(jstType);
		assertNotNull(completion);
		assertTrue(VjoCcCommentUtil.getScope(completion) == ScopeIds.METHOD);
		assertEquals("//> public St", completion.getCommentBeforeCursor());
	}
	
	@Test
	public void testInProperty() {
		String fileName = "propComment.js.txt";
		IJstType jstType = getJstTypeFromVjoParser(fileName,
				"org.ebayopensource.dsf.jstojava.translator.robust.completion.typeComment", 
				"//< S");
		assertNotNull(jstType);
		JstCommentCompletion completion = getJstCommentCompletion(jstType);
		assertNotNull(completion);
		assertTrue(VjoCcCommentUtil.getScope(completion) == ScopeIds.PROPERTY);
		assertEquals("//< S", completion.getCommentBeforeCursor());
		
		jstType = getJstTypeFromVjoParser(fileName,
				"org.ebayopensource.dsf.jstojava.translator.robust.completion.typeComment", 
				"//> i");
		assertNotNull(jstType);
		completion = getJstCommentCompletion(jstType);
		assertNotNull(completion);
		assertTrue(VjoCcCommentUtil.getScope(completion) == ScopeIds.PROPERTY);
		assertEquals("//> i", completion.getCommentBeforeCursor());
	}
	
	@Test
	public void testInVars() {
		String fileName = "methodComment.js.txt";
		IJstType jstType = getJstTypeFromVjoParser(fileName,
				"org.ebayopensource.dsf.jstojava.translator.robust.completion.methodComment", 
				"//<A");
		assertNotNull(jstType);
		JstCommentCompletion completion = getJstCommentCompletion(jstType);
		assertNotNull(completion);
		assertTrue(VjoCcCommentUtil.getScope(completion) == ScopeIds.VAR);
		assertEquals("//<A", completion.getCommentBeforeCursor());
		
		jstType = getJstTypeFromVjoParser(fileName,
				"org.ebayopensource.dsf.jstojava.translator.robust.completion.methodComment", 
				"//>i");
		assertNotNull(jstType);
		completion = getJstCommentCompletion(jstType);
		assertNotNull(completion);
		assertTrue(VjoCcCommentUtil.getScope(completion) == ScopeIds.VAR);
		assertEquals("//>i", completion.getCommentBeforeCursor());
	}
	
}
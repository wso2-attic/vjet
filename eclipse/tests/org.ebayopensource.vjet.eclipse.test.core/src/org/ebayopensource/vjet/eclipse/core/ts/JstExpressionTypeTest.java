/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.ts;

import java.io.File;
import java.net.URL;
import java.util.List;

import junit.framework.TestCase;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.ts.event.group.AddGroupEvent;
import org.ebayopensource.dsf.ts.event.group.RemoveGroupEvent;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;

public class JstExpressionTypeTest extends TestCase {

	private static final String ASSIGMENT_EXPRESSION = "vjo.dsf.AssigmentExpression";

	private static final String PROJECT = "project";

	private TypeSpaceMgr spaceMgr = TypeSpaceMgr.getInstance();

	@Override
	protected void setUp() throws Exception {
		insertGroup();
	}

	public void testDefect1890() {
		assertTrue(spaceMgr.existType(PROJECT, "vjo.dsf.Test"));
	}

	private void insertGroup() {
		URL url = this.getClass().getResource("A.js");
		File file = new File(url.getFile());
		File project = new File(file.getParentFile(), PROJECT);
		AddGroupEvent event;
		event = new AddGroupEvent(PROJECT, project.getAbsolutePath());
		spaceMgr.processEvent(event);
	}

	public void testNewLocalVarAssigment() {
		
		IJstType type = spaceMgr.findType(PROJECT, ASSIGMENT_EXPRESSION);
		assertNotNull(type);
		
		IJstMethod method = type.getMethod("gh");
		assertNotNull(method);
		
		JstBlock block = method.getBlock();
		assertNotNull(block);
		
		List<BaseJstNode> children = block.getChildren();
		assertNotNull(children);
		assertTrue(children.size()>1);
				
		assertTrue(children.get(1) instanceof AssignExpr);
		AssignExpr assignExpr = (AssignExpr) children.get(1);
		assertTrue(assignExpr.getLHS() instanceof FieldAccessExpr);
		FieldAccessExpr fieldAccessExpr = (FieldAccessExpr) assignExpr.getLHS();
		assertTrue(fieldAccessExpr.getExpr() instanceof JstIdentifier);
		JstIdentifier identifier = (JstIdentifier) fieldAccessExpr.getExpr() ;
		assertNotNull(identifier.getType());
		assertEquals(identifier.getType().getName(), "String");
				
		
	}

	@Override
	protected void tearDown() throws Exception {
		removeGroup();
	}

	private void removeGroup() {
		RemoveGroupEvent event = new RemoveGroupEvent(PROJECT, PROJECT);
		spaceMgr.processEvent(event);
	}

}

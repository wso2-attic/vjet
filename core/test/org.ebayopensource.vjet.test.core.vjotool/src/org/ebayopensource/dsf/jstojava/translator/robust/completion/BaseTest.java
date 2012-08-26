/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust.completion;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstParseController;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IScriptUnit;
import org.ebayopensource.dsf.jstojava.parser.SyntaxTreeFactory2;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.mod.wst.jsdt.internal.compiler.batch.CompilationUnit;
import org.eclipse.mod.wst.jsdt.internal.compiler.env.ICompilationUnit;

public abstract class BaseTest {

	private TranslateCtx m_translateCtx;

	CompilationUnitDeclaration prepareAst(String filename, String input) {
		// get absolute file path
		if (input == null && filename != null) {
			URL resource = getClass().getResource(filename);
			filename = resource.getPath();
		}
		CompilationUnitDeclaration ast = SyntaxTreeFactory2.createAST(null,
				input != null ? input.toCharArray() : null, filename, null);
		return ast;
	}

	protected static IJstMethod getMethodByName(
			List<? extends IJstMethod> methods, String name) {
		for (IJstMethod method : methods) {
			if (name.equals(method.getName().getName())) {
				return method;
			}
		}
		return null;
	}

	protected static IJstProperty getPropertyByName(
			Collection<IJstProperty> properties, String name) {
		for (IJstProperty property : properties) {
			if (name.equals(property.getName().getName())) {
				return property;
			}
		}
		return null;
	}

	protected String getFileContent(String filename, String input) {
		URL resource = null;
		if (input == null && filename != null) {
			resource = getClass().getResource(filename);
			if(resource.getProtocol().contains("bundleresource")){
				try {
					resource = FileLocator.resolve(resource);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			filename = resource.getPath();
		}
		File file = new File(filename);
		if (!file.getAbsolutePath().contains(".jar"))
			assertTrue("Following file not found : " + file, file.exists());
		ICompilationUnit sourceUnit = new CompilationUnit(input != null ? input
				.toCharArray() : null, filename, null);
		String contents = "";
		try {
			contents = new String(sourceUnit.getContents());
		} catch (Exception ex){
			contents = VjoParser.getContent(resource);
		}
		return contents;
	}

	protected int lastPositionInFile(String token, String fileName) {
		String content = getFileContent(fileName, null);
		assertNotNull(content);
		int position = content.lastIndexOf(token);
		assertTrue(position > -1);
		return position + token.length();
	}

	protected IJstType getJstTypeFromVjoParser(String fileName,
			String fullName, String token) {
		VjoParser vjoParser = new VjoParser();
		int position = lastPositionInFile(token, fileName);
		String content = getFileContent(fileName, null);
		m_translateCtx = new TranslateCtx();
		m_translateCtx.setCompletionPos(position);

		IScriptUnit unit = vjoParser.parse("Test", fullName, content,
				m_translateCtx);
		assertNotNull(unit);
		IJstType jstType = unit.getType();
		assertNotNull(jstType);
		return jstType;
	}

	protected IJstType getJstTypeFromVjoParser(String fileName,
			String fullName, int position) {
		IScriptUnit unit = getScriptUnitFromVjoParser(fileName, fullName,
				position);
		assertNotNull(unit);
		IJstType jstType = unit.getType();
		assertNotNull(jstType);
		return jstType;
	}

	protected IScriptUnit getScriptUnitFromVjoParser(String fileName,
			String fullName, String token) {
		IScriptUnit unit = getScriptUnitFromVjoParser(fileName, fullName, token);
		assertNotNull(unit);
		return unit;
	}

	protected IScriptUnit getScriptUnitFromVjoParser(String fileName,
			String fullName, int position) {
		VjoParser vjoParser = new VjoParser();
		String content = getFileContent(fileName, null);
		m_translateCtx = new TranslateCtx();
		m_translateCtx.setCompletionPos(position);

		IScriptUnit unit = vjoParser.parse("Test", fullName, content,
				m_translateCtx);
		assertNotNull(unit);
		return unit;
	}

	protected IJstType getJstTypeSyntaxTreeFactory(String fileName, String token) {
		CompilationUnitDeclaration ast = prepareAst(fileName, null);
		TranslateCtx tranCtx = new TranslateCtx();
		int position = lastPositionInFile(token, fileName);
		tranCtx.setCompletionPos(position);
		IJstType jstType = SyntaxTreeFactory2
				.createJST(ast, new TranslateCtx());
		assertNotNull(jstType);
		return jstType;
	}

	protected JstCompletion getJstCompletion(IJstType type) {
		List<JstCompletion> completions = m_translateCtx.getJstErrors();
		if (completions != null && !completions.isEmpty()) {
			return completions.get(0);
		} else {
			return null;
		}
	}

	protected void resovleJstType(IJstType jstType) {
		IJstParseController controller = CodeCompletionUtil
				.getJstParseController();
		controller.resolve(jstType);
	}
	
	protected JstCommentCompletion getJstCommentCompletion(IJstType jstType) {
		List<JstCompletion> completions = m_translateCtx.getJstErrors();
		if (completions == null) {
			return null;
		}
		for (JstCompletion c : completions) {
			if (c instanceof JstCommentCompletion) {
				return (JstCommentCompletion) c;
			}
		}
		return null;	
	}

}
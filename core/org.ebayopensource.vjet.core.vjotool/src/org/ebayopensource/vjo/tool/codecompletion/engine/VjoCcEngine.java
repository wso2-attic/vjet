/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.engine;

import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jsgen.shared.validation.common.ScopeId;
import org.ebayopensource.dsf.jst.IJstParseController;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IScriptUnit;
import org.ebayopensource.dsf.jst.IWritableScriptUnit;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.ResolutionResult;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.term.ObjLiteral;
import org.ebayopensource.dsf.jstojava.controller.JstParseController;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.IJstCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCommentCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletionOnMemberAccess;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstFieldOrMethodCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstKeywordCompletion;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcEngine;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcAdvisorManager;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;

public class VjoCcEngine implements IVjoCcEngine {

	private IJstParseController m_jstParseController;
	private VjoCcCtx m_ctx;

	public VjoCcEngine(IJstParseController jstParseController) {
		this.m_jstParseController = jstParseController;
	}

	public List<IVjoCcProposalData> complete(IJstType type, int position) {
		// calculate VjoCcContext
		// VjoCcVisitor vistor = new VjoCcVisitor(type, position);
		// VjoCcCtx ctx = vistor.visit();
		//
		// // get VjoCcHandler
		// IVjoCcHandler handler = new VjoCcHandler();
		// handler.handle(ctx);
		//
		// // return result from CjoCcContext
		// return ctx.getReporter().getProposalData();
		return Collections.emptyList();
	}

	public List<IVjoCcProposalData> complete(String groupName, String typeName,
			String content, int position) {
		if (m_jstParseController == null) {
			return Collections.emptyList();
		}

		// create VjoCcCtx
		m_ctx = genCcContext(groupName, typeName, content, position);
		
		if (m_ctx == null) {
			return Collections.emptyList();
		}

		VjoCcAdvisorManager advisorManager = new VjoCcAdvisorManager();
		advisorManager.advise(m_ctx);
		return m_ctx.getProposals();

	}

	private String preProcessContent(String content, int position) {
		if (position >= content.length()) {
			return content;
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(content.substring(0, position));
		content = content.substring(position);
		int index = Math.min(content.indexOf("\n"), content.indexOf("\r"));
		if (index != -1) {
			buffer.append(content.substring(0, index)).append(";").append(
					content.substring(index));
		}
		return buffer.toString();
	}

	public VjoCcCtx getContext() {
		return m_ctx;
	}

	/**
	 * generate VjoCcCtx
	 * 
	 * @param groupName
	 * @param typeName
	 * @param content
	 * @param position
	 * @return
	 */
	public VjoCcCtx genCcContext(String groupName, String typeName,
			String content, int position) {
		// resolve JstType from String content
		TranslateCtx translateCtx = new TranslateCtx();
		translateCtx.setCompletionPos(position);
		IWritableScriptUnit scriptUnit = parseJstContent(groupName, typeName, content,
				position, translateCtx);
		IJstType jstType = scriptUnit.getType();
		if (jstType == null) {
			return null;
		}
		IJstCompletion jstCompletion = getJstCompletion(translateCtx, position);
		if (jstCompletion instanceof JstCompletionOnMemberAccess) {
			// JstCompletionOnMethodAccess is useless for CC, so try recalculate
			// it
			content = preProcessContent(content, position);
			IWritableScriptUnit scriptUnit1 = parseJstContent(groupName, typeName,
					content, position, translateCtx);
			IJstType jstType1 = scriptUnit.getType();
			if (jstType1 != null) {
				IJstCompletion jstCompletion1 = getJstCompletion(translateCtx,
						position);
				if (jstCompletion1 != null
						&& !(jstCompletion1 instanceof JstCompletionOnMemberAccess)) {
					jstType = jstType1;
					scriptUnit = scriptUnit1;
					jstCompletion = jstCompletion1;
				}
			}
		}
		if(m_jstParseController instanceof JstParseController){
			((JstParseController)m_jstParseController).resolve(groupName,scriptUnit);
		}
		
		TypeName typeNameT = new TypeName(groupName,getTypeName(groupName, typeName));
		
		if((!scriptUnit.getType().equals(jstType)) &&jstCompletion instanceof JstCompletion){
			JstCompletion c = (JstCompletion)jstCompletion;
			
			if(c.getRealParent() instanceof IJstType){
				
				c.setRealParent(scriptUnit.getType());
			}
			c.setParent(scriptUnit.getType());
			jstType = scriptUnit.getType();
			typeName = scriptUnit.getType().getName();
			typeNameT = new TypeName(groupName, typeName);
			
		}
		List<JstBlock> blocks = scriptUnit.getJstBlockList();
		if (blocks != null && !blocks.isEmpty()) {
			for (JstBlock block : blocks) {
				m_jstParseController.resolve(jstType, block); // this is new
																// API
			}
		}
		// create VjoCcCtx
		VjoCcCtx ctx = new VjoCcCtx(m_jstParseController.getJstTypeSpaceMgr(), typeNameT);
		
		if (jstCompletion == null) {
			ctx.setActingType(jstType);
		} else {
			ctx.setCompletion(jstCompletion);
		}
		ctx.setOffset(position);
		ctx.setScriptUnit(scriptUnit);
		ctx.setContent(content);
		return ctx;
	}

	/**
	 * Calculate the correct type name;
	 * 
	 * @param typeName
	 * @return
	 */
	private String getTypeName(String groupName, String typeName) {
		String sourceFolder = "\\" + groupName + "\\" + "src" + "\\";
		if (typeName.startsWith(sourceFolder)) {
			typeName = typeName.substring(sourceFolder.length());
		}
		if (typeName.endsWith(".js")) {
			typeName = typeName.substring(0, typeName.length() - 3);
		}
		return typeName.replace("\\", ".");
	}

	private IWritableScriptUnit parseJstContent(String groupName, String typeName,
			String content, int position, TranslateCtx ctx) {
		VjoParser vjoParser = new VjoParser();

		IWritableScriptUnit scriptUnit = vjoParser.parse(groupName, typeName, content, ctx);
		return scriptUnit;
	}

	private JstCompletion getJstCompletion(TranslateCtx ctx, int position) {

		List<JstCompletion> jstErrors = ctx.getJstErrors();
		
		List<JstCompletion> jstBlockCompletions = ctx.getBlockCompletions();
		
	
		int size = jstErrors.size();
		if(size==0 && jstBlockCompletions.size()>0){
			for (JstCompletion c : jstBlockCompletions) {
				JstSource source = c.getSource();
				if (source != null) {
					return c;
				}
			}
		}else if (size > 0) {
			for (JstCompletion c : jstErrors) {
				if (c instanceof JstKeywordCompletion
						&& c.getScopeStack().size() > 0) {
					continue;
				}
				JstSource source = c.getSource();
				if (source != null) {
					return c;
				}
			}
		}
		return null;
	}

	@Override
	public List<IVjoCcProposalData> completeComment(String groupName,
			String typeName, String content, int position,
			String commentString, int commentOffset) {
		// create VjoCcCtx
		m_ctx = genCcContext(groupName, typeName, content, position);
		// Analyze comment info
		if (!(m_ctx.getCompletion() instanceof JstCommentCompletion)) {
			JstCommentCompletion commentCompletion = new JstCommentCompletion(
					(JstType) m_ctx.getActingType(), commentString,
					commentOffset);
			if (m_ctx.getCompletion() instanceof JstFieldOrMethodCompletion) {// the comment is "//>public f<cursor>static" before function declaration
				commentCompletion.pushScope(ScopeIds.METHOD);
			} else if(m_ctx.getCompletion() == null || m_ctx.getCompletion() instanceof JstKeywordCompletion) {
				commentCompletion.pushScope(ScopeIds.TYPE);
			}
			else {// the comment is "//<Type::"
				commentCompletion.pushScope(ScopeIds.VAR);
			}
			m_ctx.setCompletion(commentCompletion);

		}
		VjoCcAdvisorManager advisorManager = new VjoCcAdvisorManager();
		advisorManager.advise(m_ctx);
		return m_ctx.getProposals();
	}

}

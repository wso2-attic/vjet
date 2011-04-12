/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.vjo;

import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.generate.SourceGenerator;
import org.ebayopensource.dsf.jst.IJstNode;


public abstract class BaseGenerator extends SourceGenerator {
	
	private GeneratorCtx m_ctx;
		
	//
	// Constructor
	//
	public BaseGenerator(final GeneratorCtx ctx){
		super(ctx.getWriter(), ctx.getIndenter(), ctx.getStyle());
		m_ctx = ctx;
	}
	
	public String getGeneratedText(){
		return m_ctx.getStringWriter().toString();
	}
	
	//
	// Protected
	//
	protected GeneratorCtx getCtx() {
		return m_ctx;
	}
	
	protected JsCoreGenerator getJsCoreGenerator(){
		return getCtx().getProvider().getJsCoreGenerator();
	}
	
	protected JsClientGenerator getJsClientGenerator(){
		return getCtx().getProvider().getJsClientGenerator();
	}
	
	protected VjoGenerator getTypeGenerator(){
		return getCtx().getProvider().getTypeGenerator();
	}
	
	protected MtdGenerator getMtdGenerator(){
		return getCtx().getProvider().getMtdGenerator();
	}
	
	protected BodyGenerator getBodyGenerator(){
		return getCtx().getProvider().getBodyGenerator();
	}
	
	protected StmtGenerator getStmtGenerator(){
		return getCtx().getProvider().getStmtGenerator();
	}
	
	protected ExprGenerator getExprGenerator(){
		return getCtx().getProvider().getExprGenerator();
	}
	
	protected FragmentGenerator getFragmentGenerator(){
		return getCtx().getProvider().getFragmentGenerator();
	}
	
	protected void writeComments(IJstNode node) {
		if (node!=null && node.getComments()!=null) {
			List<String> comments = node.getComments();
			for (String comment : comments) {
				writeNewline();
				getWriter().append(comment);
			}
		}
	}
}

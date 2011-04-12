/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.validator;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationVisitorEvent;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.declaration.JstConstructor;
import org.ebayopensource.dsf.jst.declaration.JstMethod;

public class VjoJstConstructorValidator 
	extends VjoJstMethodValidator {

private static List<Class<? extends IJstNode>> s_targetTypes;
	
	static{
		s_targetTypes = new ArrayList<Class<? extends IJstNode>>();
		s_targetTypes.add(JstConstructor.class);
	}

	private static final String[] VJO_KEYWORDS = {"vj$", "base", "_getBase"};
	private static final String[] VJO_KEYWORDS_4_ETYPE = {"vj$", "base", "_getBase", "name", "ordinal", "values"};

	@Override
	public List<Class<? extends IJstNode>> getTargetNodeTypes() {
		return s_targetTypes;
	}

	@Override
	public void onPreAllChildrenEvent(final IVjoValidationVisitorEvent event){
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();
		if(!(jstNode instanceof JstConstructor)){
			return;
		}
		
		final JstMethod jstMethod = (JstMethod)jstNode;
		if (jstMethod.getBlock() == null) {
			return; //no validation for undefined function
		}
		
		validateBefore(ctx, jstMethod);
	}
	
	@Override
	public void onPostAllChildrenEvent(final IVjoValidationVisitorEvent event){
		final VjoValidationCtx ctx = event.getValidationCtx();
		final IJstNode jstNode = event.getVisitNode();
		if(!(jstNode instanceof JstConstructor)){
			return;
		}
		
		final JstMethod jstMethod = (JstMethod)jstNode;
		if (jstMethod.getBlock() == null) {
			return; //no validation for undefined function
		}
		
		validateAfter(jstMethod, ctx);
	}
	
	@Override
	protected String[] getVjoKeywords(final IJstMethod jstMethod) {
		return jstMethod.getOwnerType() != null && jstMethod.getOwnerType().isEnum() ? VJO_KEYWORDS_4_ETYPE : VJO_KEYWORDS;
	}
}

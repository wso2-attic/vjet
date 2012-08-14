/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.vjet.eclipse.internal.codeassist.select.translator;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.meta.JsCommentMetaNode;
import org.ebayopensource.vjet.eclipse.internal.codeassist.select.JstNodeDLTKElementResolver;

/**
 * New translator for JstSynthesizedProperty type, like sysout/syserr
 * 
 * 
 * 
 */
public class JsCommentMetaNodeTranslator extends DefaultNodeTranslator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.internal.codeassist.select.translator.DefaultNodeTranslator#lookupBinding(org.ebayopensource.dsf.jst.IJstNode)
	 */
	@Override
	public IJstNode lookupBinding(IJstNode jstNode) {
		JsCommentMetaNode jsCommentMetaNode = (JsCommentMetaNode) jstNode;
		return JstNodeDLTKElementResolver.lookupBinding(jsCommentMetaNode.getResultType());
	}

}

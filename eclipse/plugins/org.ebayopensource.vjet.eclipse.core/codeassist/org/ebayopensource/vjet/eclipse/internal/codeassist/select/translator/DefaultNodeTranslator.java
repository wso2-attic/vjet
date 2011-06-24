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
import org.ebayopensource.vjet.eclipse.core.IVjoSourceModule;
import org.eclipse.dltk.mod.core.IModelElement;

/**
 * list common methods for sub classes
 * 
 * 
 * 
 */
public abstract class DefaultNodeTranslator implements IJstNodeTranslator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.internal.codeassist.select.translator.IJstNodeTranslator#convert(org.ebayopensource.dsf.jst.IJstNode)
	 */
	@Override
	public IModelElement[] convert(IJstNode node) {
		return convert(null, node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.internal.codeassist.select.translator.IJstNodeTranslator#resolveBinding(org.ebayopensource.dsf.jst.IJstNode)
	 */
	@Override
	public IJstNode lookupBinding(IJstNode jstNode) {
		// default implementation
		return jstNode;
	}
	
	
	/* 
	 * Ignore IVjoSourceModule by default
	 * @see org.ebayopensource.vjet.eclipse.internal.codeassist.select.translator.IJstNodeTranslator#convert(org.ebayopensource.vjet.eclipse.core.IVjoSourceModule, org.ebayopensource.dsf.jst.IJstNode)
	 */
	public IModelElement[] convert(IVjoSourceModule module, IJstNode node) {
		return null;
	}

}

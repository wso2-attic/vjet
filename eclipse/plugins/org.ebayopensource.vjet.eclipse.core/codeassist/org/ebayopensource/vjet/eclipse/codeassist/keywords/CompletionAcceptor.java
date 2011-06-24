/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.keywords;

import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;

/**
 * The abstract class for all completion acceptors. Accept or decline keyword
 * completion for current completion position in source module.
 * 
 * 
 * 
 */
public abstract class CompletionAcceptor {

	protected ISourceModule sourceModule;

	/**
	 * If returns true then completion show.
	 * 
	 * @param position
	 *            completion position
	 * @param completion
	 *            {@link JstCompletion} object.
	 * @return flag for accept or decline completion.
	 */
	abstract boolean accept(int position, JstCompletion completion);

	public ISourceModule getSourceModule() {
		return sourceModule;
	}

	public void setSourceModule(ISourceModule sourceModule) {
		this.sourceModule = sourceModule;
	}

}

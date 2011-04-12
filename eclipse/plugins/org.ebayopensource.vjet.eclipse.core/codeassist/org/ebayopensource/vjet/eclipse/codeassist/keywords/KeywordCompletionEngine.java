/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.keywords;

/**
 * This interface define method for keyword completion engine. 
 * 
 * 
 *
 */
public interface KeywordCompletionEngine {

	/**
	 * Gets completion result.
	 * 
	 * @return completion result.
	 */
	public abstract VjoKeywordCompletionResult getCompletionResult();

}
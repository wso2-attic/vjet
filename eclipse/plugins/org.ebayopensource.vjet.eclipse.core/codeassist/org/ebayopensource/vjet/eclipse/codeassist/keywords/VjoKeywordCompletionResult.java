/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.keywords;

import java.util.List;

public class VjoKeywordCompletionResult {
	
	private List<IVjoCompletionData> keywords;
	private int startPosition;
	
	public VjoKeywordCompletionResult(List<IVjoCompletionData> keywords, int startPosition) {
		this.keywords = keywords;
		this.startPosition = startPosition;
	}
	
	public List<IVjoCompletionData> getKeywords(){
		return keywords;
	}
	
	public int getStartPosition(){
		return startPosition;
	}
	
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.sac;

public interface IDocumentHandler {
	void startDocument(InputSource inputsource) throws CssException;
    
	void endDocument(InputSource inputsource) throws CssException;
    
	void comment(String string) throws CssException;
    
	void ignorableAtRule(String string) throws CssException;
    
	void namespaceDeclaration(String string, String string_0_)
		throws CssException;
    
	void importStyle(String string, ISacMediaList sacmedialist, 
		String string_1_) throws CssException;
    
	void startMedia(ISacMediaList sacmedialist) throws CssException;
    
	void endMedia(ISacMediaList sacmedialist) throws CssException;
    
	void startPage(String string, String string_2_) throws CssException;
    
	void endPage(String string, String string_3_) throws CssException;
    
	void startFontFace() throws CssException;
    
	void endFontFace() throws CssException;
    
	void startSelector(ISelectorList selectorlist) throws CssException;
    
	void endSelector(ISelectorList selectorlist) throws CssException;
    
	void property(String string, ILexicalUnit lexicalunit, boolean bool)
		throws CssException;
}

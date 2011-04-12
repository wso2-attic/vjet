/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.css.sac;

import java.io.IOException;
import java.util.Locale;

public interface IParser {
	void setLocale(Locale locale) throws CssException;
    
	void setDocumentHandler(IDocumentHandler documenthandler);
    
	void setSelectorFactory(ISelectorFactory selectorfactory);
    
	void setConditionFactory(IConditionFactory conditionfactory);
    
	void setErrorHandler(IErrorHandler errorhandler);
    
	void parseStyleSheet(InputSource inputsource)
		throws CssException, IOException;
    
	void parseStyleSheet(String string) throws CssException, IOException;
    
	void parseStyleDeclaration(InputSource inputsource)
		throws CssException, IOException;
    
	void parseRule(InputSource inputsource) throws CssException, IOException;
    
	String getParserVersion();
    
	ISelectorList parseSelectors(InputSource inputsource)
		throws CssException, IOException;
    
	ILexicalUnit parsePropertyValue(InputSource inputsource)
		throws CssException, IOException;
    
	boolean parsePriority(InputSource inputsource)
		throws CssException, IOException;
}
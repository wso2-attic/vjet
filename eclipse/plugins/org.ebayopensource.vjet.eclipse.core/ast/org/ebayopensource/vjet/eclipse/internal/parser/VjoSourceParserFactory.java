/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.parser;

import org.eclipse.dltk.mod.ast.parser.ISourceParser;
import org.eclipse.dltk.mod.ast.parser.ISourceParserFactory;

public class VjoSourceParserFactory implements ISourceParserFactory {

	public ISourceParser createSourceParser() {
		return new VjoSourceParser();
	}

}

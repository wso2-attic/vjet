/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.parse;

import org.ebayopensource.dsf.javatojs.translate.TranslationMode;
import org.ebayopensource.dsf.jst.declaration.JstType;

public class TypeDnDVisitor extends TypeDependencyVisitor {
	
	//
	// Constructors
	//
	public TypeDnDVisitor(final JstType type) {
		super(type, new TranslationMode().addDependency().addDeclaration());
	}
}

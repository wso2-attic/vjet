/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.custom.jdk;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import org.ebayopensource.dsf.javatojs.translate.custom.meta.MetaDrivenCustomTranslator;
import org.ebayopensource.dsf.jst.declaration.JstMethod;

public class JavaLangCustomTranslator extends MetaDrivenCustomTranslator {
	
	//
	// Constructor
	//
	/**
	 * Constructor.
	 * @param customTypeMetaProvider ICustomTypeMetaProvider required
	 */
	public JavaLangCustomTranslator(){
		super(JavaLangMeta.getInstance());
	}
	
	@Override
	public boolean processMethodBody(MethodDeclaration astMtd, JstMethod jstMtd) {
		return false;
	}
}

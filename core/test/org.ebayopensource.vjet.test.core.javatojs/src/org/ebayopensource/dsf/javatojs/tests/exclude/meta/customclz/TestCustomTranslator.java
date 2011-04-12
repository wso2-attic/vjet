/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.exclude.meta.customclz;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import org.ebayopensource.dsf.javatojs.translate.custom.meta.MetaDrivenCustomTranslator;
import org.ebayopensource.dsf.jst.declaration.JstMethod;

public class TestCustomTranslator extends MetaDrivenCustomTranslator{
	
	/**
	 * Constructor.
	 * @param customTypeMetaProvider ICustomTypeMetaProvider required
	 */
	public TestCustomTranslator(){
		super(TestMetaProvider.getInstance());
	}
	
	@Override
	public boolean processMethodBody(MethodDeclaration astMtd, JstMethod jstMtd) {
		return false;
	}
}

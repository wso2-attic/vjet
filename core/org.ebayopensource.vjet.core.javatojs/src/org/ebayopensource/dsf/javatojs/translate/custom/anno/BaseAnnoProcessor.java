/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.custom.anno;

import org.eclipse.jdt.core.dom.ASTNode;

import org.ebayopensource.dsf.javatojs.translate.custom.CustomInfo;
import org.ebayopensource.dsf.jst.declaration.JstType;

public abstract class BaseAnnoProcessor implements IAnnoProcessor {
	
	public CustomInfo process(ASTNode astNode, JstType jstType){
		return null;
	}
}

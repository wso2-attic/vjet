/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.astjst;

import org.ebayopensource.vjo.tool.astjst.TypeConstants.TypeRef;
import org.ebayopensource.vjo.tool.astjst.TypeConstants.Types;


public class RefInfo {
	public TypeRef relationShip;
	public String referenceTypeName;
	public Types referenceType;
	
	public RefInfo(TypeRef relationShip, 
			String referenceTypeName, Types referenceType) {
		this.relationShip = relationShip;
		this.referenceTypeName = referenceTypeName;
		this.referenceType = referenceType;
	}
	
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst;

import java.util.List;
import java.util.jar.Manifest;

import org.ebayopensource.dsf.jst.declaration.JstType;

public interface IJstLib {
	String getName();
	Manifest getManifest();
	JstType getType(String typeName, boolean recursive);
	IJstLib getLib(String libName, boolean recursive);
	
	List<JstType> getAllTypes(boolean recursive);
	List<IJstLib> getAllLibs(boolean recursive);
}

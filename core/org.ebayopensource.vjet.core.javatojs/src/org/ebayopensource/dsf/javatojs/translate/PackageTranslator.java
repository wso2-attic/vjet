/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate;

import org.ebayopensource.dsf.javatojs.translate.config.PackageMapping;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstFactory;
import org.ebayopensource.dsf.jst.declaration.JstPackage;

public class PackageTranslator extends BaseTranslator {
	
	private JstCache m_repository = JstCache.getInstance();
	private JstFactory m_factory = JstFactory.getInstance();
	
	//
	// API
	//
	public JstPackage getPackage(final String fullName) {
		
		if (fullName == null){
			return null;
		}
		
		PackageMapping pkgMapping = getCtx().getConfig().getPackageMapping();
		
		String toName = pkgMapping.mapTo(fullName);
		if (toName == null){
			toName = fullName;
		}
		JstPackage pkg = m_repository.getPackage(toName);
		if (pkg == null){
			pkg = m_factory.createPackage(toName);
			m_repository.addPackage(pkg);
		}

		return pkg;
	}
}

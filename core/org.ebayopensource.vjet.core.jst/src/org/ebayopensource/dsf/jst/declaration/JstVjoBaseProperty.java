/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.term.JstIdentifier;

public class JstVjoBaseProperty extends JstSynthesizedProperty {
	
	private static final long serialVersionUID = 1L;
	private IJstType m_ownerType;

	public JstVjoBaseProperty(final String name, final IJstType ownerType) {
		super(null, name, (JstIdentifier)null, new JstModifiers());
		m_ownerType = ownerType;
	}

	/**
	 * @see IJstProperty#getType()
	 */
	public IJstType getType() {
		return m_ownerType.getExtend();
	}
}

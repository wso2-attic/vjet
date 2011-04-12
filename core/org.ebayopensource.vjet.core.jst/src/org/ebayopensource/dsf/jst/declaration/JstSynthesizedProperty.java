/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ISynthesized;
import org.ebayopensource.dsf.jst.term.JstIdentifier;

public class JstSynthesizedProperty extends JstProperty implements ISynthesized{
	private static final long serialVersionUID = 1L;
	
	public JstSynthesizedProperty(final IJstType type, final String name, JstIdentifier identifier, JstModifiers modifier) {
		super(type, name, identifier, (modifier==null)?new JstModifiers():modifier);
	}
	
}

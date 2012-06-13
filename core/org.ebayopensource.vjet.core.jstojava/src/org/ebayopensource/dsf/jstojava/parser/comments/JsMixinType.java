/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.parser.comments;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jst.meta.JsTypingMeta;

/**
 * representing a mix-in type from multiple types
 */
public class JsMixinType extends JsTypingMeta {
	
	private List<JsTypingMeta> m_types = new ArrayList<JsTypingMeta>(2);
	
	public JsMixinType() {
	}
	
	public JsMixinType add(JsTypingMeta type) {
		if (getTypingToken() == null) {
			setTypingToken(type.getTypingToken());
		}
		m_types.add(type);
		return this;
	}
	
	public List<JsTypingMeta> getTypes() {
		return m_types;
	}

	@Override
	public String getType() {
		return m_types.size() == 0 ? null : m_types.get(0).getType();
	}
}

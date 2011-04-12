/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.generate;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;

public class SimpleParam {

	final JstArg m_arg;
	final IJstType m_type;
	final boolean m_isOptional;
	// commented out as it is not used by anything.
	// boolean m_isTypeRef = false;
	String m_mappedName;

	SimpleParam(JstArg arg, IJstType type, boolean isOptional, boolean isTypeRef) {
		m_arg = arg;
		m_type = type;
		m_isOptional = isOptional;
		// m_isTypeRef = isTypeRef;
		// TODO FIX_LATER (use the old logic for lookup which is incorrect)
//		m_mappedName = getMappedEventName(type.getSimpleName());
	}
	
	public void setMappedName(String mappedName){
		m_mappedName = mappedName;
	}

	@Override
	public String toString() {
		return m_type.getName() + (m_isOptional ? "?" : "") + " "
				+ m_arg.getName();
	}

	public JstArg getArg() {
		return m_arg;
	}

	public IJstType getType() {
		return m_type;
	}

	public boolean isOptional() {
		return m_isOptional;
	}

	public String getMappedName() {
		return m_mappedName;
	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug;

import org.eclipse.dltk.mod.debug.core.model.ArrayScriptType;
import org.eclipse.dltk.mod.debug.core.model.AtomicScriptType;
import org.eclipse.dltk.mod.debug.core.model.ComplexScriptType;
import org.eclipse.dltk.mod.debug.core.model.IScriptType;
import org.eclipse.dltk.mod.debug.core.model.IScriptTypeFactory;
import org.eclipse.dltk.mod.debug.core.model.IScriptValue;
import org.eclipse.dltk.mod.debug.core.model.StringScriptType;

public class VjetTypeFactory implements IScriptTypeFactory{
	private static final String[] atomicTypes = { "number", "boolean", "date" };

	public VjetTypeFactory() {

	}

	public IScriptType buildType(String type) {
		for (int i = 0; i < atomicTypes.length; ++i) {
			if (atomicTypes[i].equals(type)) {
				return new AtomicScriptType(type);
			}
		}

		if ("javaarray".equals(type) || "array".equals(type)) {
			return new ArrayScriptType();
		}

		if ("string".equals(type)) {
			return new StringScriptType("string");
		}
		return new ComplexScriptType(type) {
			public String formatValue(IScriptValue value) {
				StringBuffer sb = new StringBuffer();
				sb.append(value.getRawValue());
				String id = value.getInstanceId();
				if (id != null) {
					sb.append(" (id = " + id + ")"); // TODO add constant
					// //$NON-NLS-1$
					// //$NON-NLS-2$
	}

				return sb.toString();
			}
		};
	}
}

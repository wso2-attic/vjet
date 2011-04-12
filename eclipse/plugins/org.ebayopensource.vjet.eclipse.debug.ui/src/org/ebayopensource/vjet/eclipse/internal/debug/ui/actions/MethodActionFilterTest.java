/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.ui.actions;

import org.eclipse.dltk.mod.core.Flags;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.ui.actions.IActionFilterTester;

public class MethodActionFilterTest implements IActionFilterTester {
	public static final String FILTER_ID="org.eclipse.vjet.ui.method";

	public boolean test(Object target, String name, String value) {
//		if (target instanceof IModelElement) {
//			final IDLTKLanguageToolkit toolkit = DLTKLanguageManager
//					.getLanguageToolkit((IModelElement) target);
//			if (toolkit != null) {
//				return toolkit.getNatureId().equals(value);
//			}
//		}
//		return false;
		

		try {
			if (name.equals(FILTER_ID)) { //$NON-NLS-1$
				if (target instanceof IMethod) {
					int flags=((IMethod)target).getFlags();
					int typeFlags=((IMethod)target).getDeclaringType().getFlags();
					return !Flags.isAbstract(flags)&&!Flags.isInterface(typeFlags);
				
				}
			}
		} catch (ModelException e) {
			e.printStackTrace();
		}
		return false;
	
	}

}

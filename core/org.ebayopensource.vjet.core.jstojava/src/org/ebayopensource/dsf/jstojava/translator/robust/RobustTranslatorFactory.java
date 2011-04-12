/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust;

import java.lang.reflect.InvocationTargetException;

import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;

class RobustTranslatorFactory {
	
	static IRobustTranslator createTranslator(TranslateCtx ctx, Class trans_class) {

		IRobustTranslator robustTranslator = null;

		try {
			if (trans_class != null) {
				robustTranslator = (IRobustTranslator) trans_class.getConstructor(TranslateCtx.class).newInstance(ctx);
			}
		} catch (InstantiationException ie) {
			// ignore this exception
		} catch (IllegalAccessException iae) {
			// ignore this exception
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return robustTranslator;

	}
	
}

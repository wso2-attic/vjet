/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.util;

import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.ContextFactory;

public class VJContextFactory extends ContextFactory {
	@Override
	protected boolean hasFeature(Context cx, int featureIndex) {
		if (featureIndex == Context.FEATURE_ENHANCED_JAVA_ACCESS) {
			return true ;
		}
		return super.hasFeature(cx, featureIndex);
	}
}

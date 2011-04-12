/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstParamType;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;

public class EndTypeRobustTranslator extends BaseRobustTranslator {

	
	public EndTypeRobustTranslator(TranslateCtx ctx) {
		super(ctx);
	}
	
//	@Override
//	protected Map<String, Class> getTranslatorMap() {
//		return Collections.EMPTY_MAP;
//	}

	public boolean transform() {

		// remove endType("") element from the stack
		current = astElements.pop();

		lookupEmptyCompletion();

		super.transform();
		
		//Revisit type definition and update param bounds if any...
		if (jst.getInactiveImports().size() > 0) {
			for (JstParamType itm : jst.getParamTypes()) {
				for (IJstType bound : itm.getBounds()) {
					String bnm = bound.getName();
					IJstType newType = TranslateHelper.findType(m_ctx, bnm);
					if (!bound.equals(newType)) {
						itm.updateBound(bnm, newType);
					}
				}
			}
		}
		if (jst.getExtend() == null) {
			if (jst.isEnum()) {
				jst.addExtend(JstCache.getInstance().getType("vjo.Enum"));
			} else if (jst.isClass()) {
				jst.addExtend(JstCache.getInstance().getType("vjo.Object"));
			}
		}

		return true;
	}

}

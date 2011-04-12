/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust;

import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.jstojava.translator.ISectionTranslatorProvider;
import org.ebayopensource.vjo.meta.VjoKeywords;

public class VjoSectionTranlationProvider implements ISectionTranslatorProvider {

	private static Map<String, Class<? extends IRobustTranslator>> T = new HashMap<String, Class<? extends IRobustTranslator>>();

	static {

		T.put(VjoKeywords.VJO, VjoRobustTranslator.class);
		// VJO_SUB_TRANSLATORS_MAP.put(VjoKeywords.LTYPE,
		// LTypeRobustTranslator.class);
		// VJO_SUB_TRANSLATORS_MAP.put(VjoKeywords.ATYPE,
		// ATypeRobustTranslator.class);
		T.put(VjoKeywords.TYPE, TypeRobustTranslator.class);
		T.put(VjoKeywords.ITYPE, ITypeRobustTranslator.class);
		T.put(VjoKeywords.CTYPE, TypeRobustTranslator.class);
		T.put(VjoKeywords.MTYPE, MTypeRobustTranslator.class);
		T.put(VjoKeywords.OTYPE, OTypeRobustTranslator.class);
		T.put(VjoKeywords.ETYPE, ETypeRobustTranslator.class);
		T.put(VjoKeywords.FTYPE, FTypeRobustTranslator.class);
				
		T.put(VjoKeywords.NEEDS, NeedsRobustTranslator.class);

		T.put(VjoKeywords.PROPS, PropsRobustTranslator.class);
		T.put(VjoKeywords.PROTOS, ProtosRobustTranslator.class);
		T.put(VjoKeywords.DEFS, DefsRobustTranslator.class);

		T.put(VjoKeywords.INITS, InitsRobustTranslator.class);
		T.put(VjoKeywords.GLOBALS, GlobalsRobustTranslator.class);
		T.put(VjoKeywords.OPTIONS, OptionsRobustTranslator.class);
		T.put(VjoKeywords.INHERITS, InheritsRobustTranslator.class);
		T.put(VjoKeywords.SATISFIES, SatisfiesRobustTranslator.class);
		T.put(VjoKeywords.EXPECTS, ExpectsRobustTranslator.class);
		T.put(VjoKeywords.NEEDS, NeedsRobustTranslator.class);
		// T.put(VjoKeywords.NEEDSLIB,NeedsLibRobustTranslator.class);
		T.put(VjoKeywords.MIXIN, MixinRobustTranslator.class);
		T.put(VjoKeywords.ENDTYPE, EndTypeRobustTranslator.class);
		T.put(VjoKeywords.VALUES, ValuesRobustTranslator.class);
		T.put(VjoKeywords.MAKE_FINAL,FinalRobustTranslator.class);
		
		
		

	}

	@Override
	public Class<? extends IRobustTranslator> getTranslator(String token) {

		Class<? extends IRobustTranslator> class1 = T.get(token);
		if (class1 == null) {
			// TODO should we have default translator for uknown sections
			System.err.println("missing translator for section "
					+ token);
			return null;
		}

		return class1;
	}

	@Override
	public String[] getSections() {
		return T.keySet().toArray(new String[T.keySet().size()]);
	}

}

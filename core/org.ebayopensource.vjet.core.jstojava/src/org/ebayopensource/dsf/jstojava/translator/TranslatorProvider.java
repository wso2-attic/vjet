/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator;

import java.util.HashMap;
import java.util.Map;

public class TranslatorProvider {

	private Map<Class<?>, BaseTranslator> m_translators = 
		new HashMap<Class<?>, BaseTranslator>();

	public TranslatorProvider(final TranslateCtx ctx) {
		assert ctx != null : "ctx cannot be null";
		m_translators.put(TypeTranslator.class, new TypeTranslator(ctx));
		m_translators.put(ProtosTranslator.class, new ProtosTranslator(ctx));
		m_translators.put(PropsTranslator.class, new PropsTranslator(ctx));
		m_translators.put(GlobalsTranslator.class, new GlobalsTranslator(ctx));
		m_translators.put(ValuesTranslator.class, new ValuesTranslator(ctx));
		m_translators.put(InitsTranslator.class, new InitsTranslator(ctx));
		m_translators.put(DefsTranslator.class, new DefsTranslator(ctx));
		m_translators.put(OptionsTranslator.class, new OptionsTranslator(ctx));
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseTranslator> T getTranslator(final Class<T> srcType) {
		assert srcType != null : "srcType cannot be null";
		return (T)m_translators.get(srcType);
	}

	public TypeTranslator getTypeTranslator() {
		return getTranslator(TypeTranslator.class);
	}
	
	public ProtosTranslator getProtosTranslator() {
		return getTranslator(ProtosTranslator.class);
	}

	public PropsTranslator getPropsTranslator() {
		return getTranslator(PropsTranslator.class);
	}
	
	public GlobalsTranslator getGlobalsTranslator() {
		return getTranslator(GlobalsTranslator.class);
	}
	
	public DefsTranslator getDefsTranslator() {
		return getTranslator(DefsTranslator.class);
	}
	
	public ValuesTranslator getValuesTranslator() {
		return getTranslator(ValuesTranslator.class);
	}

	public InitsTranslator getInitsTranslator() {
		return getTranslator(InitsTranslator.class);
	}
	
	public OptionsTranslator getOptionsTranslator() {
		return getTranslator(OptionsTranslator.class);
	}
}

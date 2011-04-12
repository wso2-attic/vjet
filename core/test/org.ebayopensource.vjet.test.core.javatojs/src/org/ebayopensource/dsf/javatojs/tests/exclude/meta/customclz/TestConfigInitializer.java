/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.exclude.meta.customclz;

import org.ebayopensource.dsf.javatojs.control.DefaultTranslationInitializer;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.config.TranslateConfig;

public class TestConfigInitializer extends DefaultTranslationInitializer {

	public void initialize() {
		// Invoke Super mathod in order to retain standard JDK translators
		super.initialize();

		TranslateCtx ctx = TranslateCtx.ctx();
		TranslateConfig config = ctx.getConfig();
		// Add Custom Translator
		config.addCustomTranslator(new TestCustomTranslator());
	}

}

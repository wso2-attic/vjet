/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.control;

import org.ebayopensource.dsf.dap.rt.DapCtx;
import org.ebayopensource.dsf.dap.rt.DapHandlerAdapter;
import org.ebayopensource.dsf.dap.rt.DapServiceEngine;
import org.ebayopensource.dsf.dap.rt.JsBase;
import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.config.DefaultJstLibProvider;
import org.ebayopensource.dsf.javatojs.translate.config.TranslateConfig;
import org.ebayopensource.dsf.javatojs.translate.custom.anno.AnnoDrivenCustomTranslator;
import org.ebayopensource.dsf.javatojs.translate.custom.anno.DefaultAnnoProcessor;
import org.ebayopensource.dsf.javatojs.translate.custom.dap.DapCustomTranslator;
import org.ebayopensource.dsf.javatojs.translate.custom.dom.ADomCustomTranslator;
import org.ebayopensource.dsf.javatojs.translate.custom.jdk.JavaLangCustomTranslator;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.MetaDrivenCustomTranslator;
import org.ebayopensource.dsf.javatojs.translate.policy.ITranslationPolicy;
import org.ebayopensource.dsf.javatojs.translate.policy.Pkg;

public class DefaultTranslationInitializer extends BaseTranslationInitializer
	implements ITranslationInitializer{

	//
	// Satisfy ITranslateConfigInitializer
	//
	public void initialize() {
		
		TranslateCtx ctx = TranslateCtx.ctx();	
		TranslateConfig config = ctx.getConfig();
		
		// Lib provider
		config.setJstLibProvider(new DefaultJstLibProvider());
		
		// Annotation processors
		DefaultAnnoProcessor annoProcessor = new DefaultAnnoProcessor();
		config.addAnnoProcessor(annoProcessor);
		
		// Custom translators
		config.addCustomTranslator(new MetaDrivenCustomTranslator(annoProcessor.getMetaProvider()))
			.addCustomTranslator(new AnnoDrivenCustomTranslator())
			.addCustomTranslator(new JavaLangCustomTranslator())
			.addCustomTranslator(new ADomCustomTranslator())
			.addCustomTranslator(new DapCustomTranslator());

		// Translation Policy
		ITranslationPolicy policy = config.getPolicy();
		
		// - External
		policy.addExcludePackage(new Pkg("org.mozilla.*"));
		policy.addExcludePackage(new Pkg("sun.*"));
		policy.addExcludePackage(new Pkg("com.ibm.*"));
		
//		policy.addExcludePackage(new Pkg("com.ebay.internal.org.*"));
//		policy.addExcludePackage(new Pkg("org.mozilla.mod.javascript", true));
		
		// - Kernel
		policy.addExcludePackage(new Pkg("org.ebayopensource.dsf.common.*"));
		
		// - DSF
		// (A)
		policy.addExcludePackage(new Pkg("org.ebayopensource.dsf.active.*"));
		policy.addExcludePackage(new Pkg("com.ebay.dsf.aggregator.*")
			.addExemptedClass("com.ebay.dsf.aggregator.serializable.BaseVjoSerializable"));
		
		// (C)
		policy.addExcludePackage(new Pkg("org.ebayopensource.dsf.common.*")
			.addExemptedPkg("org.ebayopensource.dsf.common.converter"));
		policy.addExcludePackage(new Pkg("org.ebayopensource.dsf.css.*"));
		
		// (D)
		policy.addExcludePackage(new Pkg("org.ebayopensource.dsf.dap"));
		policy.addExcludePackage(new Pkg("org.ebayopensource.dsf.dap.cnr.*"));
		policy.addExcludePackage(new Pkg("org.ebayopensource.dsf.dap.svc.*")
			.addExemptedClass("org.ebayopensource.dsf.dap.svc.IDapSvcCallback"));
		policy.addExcludePackage(new Pkg("org.ebayopensource.dsf.dap.util.*"));
		policy.addExcludePackage(new Pkg(DapCtx.class, true)
			.addExemptedClass(DapCtx.class)
			.addExemptedClass(JsBase.class)
			.addExemptedClass(DapHandlerAdapter.class)
			.addExemptedClass(DapServiceEngine.class));
		policy.addExcludePackage(new Pkg("org.ebayopensource.dsf.dom.*"));
		
		// (H)
		policy.addExcludePackage(new Pkg("org.ebayopensource.dsf.html.*")
			.addExemptedClass(HtmlTypeEnum.class)
			.addExemptedClass(EventType.class));
		
		// (J)
		policy.addExcludePackage(new Pkg("org.ebayopensource.dsf.javatocss.*"));
		policy.addExcludePackage(new Pkg("org.ebayopensource.dsf.javatojs.trace.*"));
		policy.addExcludePackage(new Pkg("org.ebayopensource.dsf.jsdebugger.*"));
		policy.addExcludePackage(new Pkg("org.ebayopensource.dsf.jsruntime.*"));
	
		// (R)
		policy.addExcludePackage(new Pkg("com.ebay.dsf.resource.*")
			.addExemptedClass("com.ebay.dsf.resource.html.event.handler"));
		
		// (S)
		policy.addExcludePackage(new Pkg("com.ebay.dsf.serializers.*")
			.addExemptedClass("com.ebay.dsf.serializers.ISerializableForVjo"));
		policy.addExcludePackage(new Pkg("org.ebayopensource.dsf.service.*")
			.addExemptedClass("org.ebayopensource.dsf.service.DefaultServiceSpec"));
		policy.addExcludePackage(new Pkg("com.ebay.dsf.spec.*"));

		// (U)
		policy.addExcludePackage(new Pkg("org.ebayopensource.dsf.util.*"));
		
		// - JsNative
		policy.addExcludePackage(new Pkg(org.ebayopensource.dsf.jsnative.anno.BrowserType.class.getPackage().toString()+".*"));
		
		// - JunitNexGen
		policy.addExcludePackage(new Pkg("com.ebay.junitnexgen.*"));
		
//		policy.addExcludeClass("java.util.RandomAccess");
//		
//		policy.addExcludeClass("vjo.java.lang.ErrorBak");
//		policy.addExcludeClass("vjo.java.lang.ExceptionBak");
//		policy.addExcludeClass("vjo.java.lang.ThrowableBak");

//		policy.addExcludeClass("Scriptable");
//		policy.addExcludeClass(org.ebayopensource.dsf.jsnative.anno.BrowserType.class.getName());
//		policy.addExcludeClass(org.ebayopensource.dsf.jsnative.anno.DomLevel.class.getName());
//		policy.addExcludeClass(org.ebayopensource.dsf.jsnative.anno.JsVersion.class.getName());
//		policy.addExcludeClass(org.ebayopensource.dsf.jsnative.anno.Function.class.getName());
//		policy.addExcludeClass(org.ebayopensource.dsf.jsnative.anno.JsNative.class.getName());
//		policy.addExcludeClass(org.ebayopensource.dsf.jsnative.anno.Property.class.getName());
//		policy.addExcludeClass(org.ebayopensource.dsf.jsnative.anno.Constructor.class.getName());
//		policy.addExcludeClass(org.ebayopensource.dsf.jsnative.anno.SupportedBy.class.getName());
	}
}
/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: AllVjoVjLibTester.java, Jul 17, 2009, 1:30:59 AM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jst.validation.vjo.vjLib;

import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.vjo.lib.LibManager;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Class/Interface description
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */

@RunWith(Suite.class)
@SuiteClasses({
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.client.ActiveX.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.client.Browser.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.common.IDedupComparable.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.common.IJsHandler.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.common.IJsReqHandler.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.common.IJsRespHandler.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.common.IJsServiceHandler.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.cookie.VjCookieJar.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.document.Element.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.document.Form.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.document.Frame.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.document.Image.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.document.Positioning.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.document.Select.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.document.Shim.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.document.Text.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.docx.ElementUIx.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.docx.Elementx.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.docx.Formx.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.docx.Framex.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.docx.Imagex.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.docx.Positioning.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.docx.Selectx.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.docx.Shim.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.docx.Textx.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.flash.FlashHelper.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.flash.Version.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.service.DedupServiceHandler.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.service.DefaultDedupComparable.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.jstrace.JSTraceLogConfig.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.jstrace.PlainFormatter.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.jstrace.RemoteFormatter.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.jstrace.Trace.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.jstrace.TraceLogger.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.jstrace.Wrap.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.logging.ConsoleHandler.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.logging.DefaultConfig.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.logging.Formatter.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.logging.Handler.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.logging.Level.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.logging.Logger.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.logging.LogManager.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.logging.LogNode.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.logging.LogRecord.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.logging.MessageHandler.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.logging.RemoteFormatter.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.logging.RemoteHandler.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.logging.RootLogger.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.logging.SimpleFormatter.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.reflection.Reflection.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.xml.XmlHelper.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.Ajax.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.AlphaNumeric.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.Array.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.Bit.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.Css.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.CssLoader.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.Currency.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.Date.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.Form.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.Frame.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.Html.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.JsLoader.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.Object.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.Popup.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.Timer.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.UriBuilder.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.utils.URL.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.window.utils.VjWindow.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.dsf.window.utils.VjWindowUtils.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.example.BaseComp.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.example.CompInstanceShortened.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.example.CompStaticShortened.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.example.DomUtilities.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.example.Person.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.example.Utilities.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.example.VjComp.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.example.VjCompInnerFunction.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.example.VjOldFunctions.class,
    org.ebayopensource.dsf.jst.validation.vjo.vjLib.example.VjStatic.class
})   
public class AllVjoVjLibTester {
    @BeforeClass
    public static void beforeClass(){
//    	JstCache.getInstance().clear();
//    	LibManager.getInstance().clear();
    }
}

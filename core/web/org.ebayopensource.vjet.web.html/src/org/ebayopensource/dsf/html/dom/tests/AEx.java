/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom.tests;

import org.ebayopensource.dsf.html.HtmlWriterHelper;
import org.ebayopensource.dsf.html.dom.DHtmlDocument;
import org.ebayopensource.dsf.html.dom.DHtmlDocumentBuilder;
import org.ebayopensource.dsf.common.FileUtils;

import org.ebayopensource.dsf.common.xml.IIndenter;

public class AEx {
	public static void main(String[] args) {
		String xml = FileUtils.getResourceString(AEx.class, "A.xml") ;
		DHtmlDocument doc = DHtmlDocumentBuilder.getDocument(xml) ;
		String s = HtmlWriterHelper.asString(doc, new IIndenter.Pretty()) ;
		System.out.println(s) ;
	}
}

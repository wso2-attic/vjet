/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom.util;

import org.ebayopensource.dsf.common.xml.IXmlStreamWriter;



public class XmlWriter extends BaseDomWriter {

	public XmlWriter(final IXmlStreamWriter writer) {
		super(writer) ;
	}
	
	public XmlWriter(final IXmlStreamWriter writer, DomWriterCtx ctx) {
		super(writer, ctx) ;
	}
	
//	@Override
//	public IElementInfo getElementInfo(DElement element) {
//		return null;
//	}
	
//	@Override
//	public void endElement(DElement node) {
//		// TODO Auto-generated method stub
//		
//	}

}

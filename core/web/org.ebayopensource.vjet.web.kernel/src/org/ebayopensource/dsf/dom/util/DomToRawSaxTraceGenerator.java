/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom.util;

import org.ebayopensource.dsf.common.trace.IDsfInstrumenter;
import org.ebayopensource.dsf.common.trace.TraceCtx;
import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.html.dom.util.IRawSaxHandler;
import org.ebayopensource.dsf.common.xml.IXmlStreamWriter;

/**
 * Dom2RawSaxTraceGenerator
 * This alternate version of Dom2RawSaxGenerator is hooked in to the render when
 * instrumenters have been registered with the Dsf context. This allows minimal
 * performance impact when instrumenting code.
 *
 */
public class DomToRawSaxTraceGenerator extends DomToRawSaxGenerator {

	private final IDsfInstrumenter m_instumenter 
		= TraceCtx.ctx().getInstrumenter() ;
	
	public DomToRawSaxTraceGenerator(final IRawSaxHandler rawSaxHandler) {
		super(rawSaxHandler);
		debugChildIntercepter = true;
	}
	
	protected void genEventsForElementInternal(final DElement element, final IXmlStreamWriter writer) {
		m_instumenter.runStartInstrumenters(element, writer);
		super.genEventsForElementInternal(element, writer);
		m_instumenter.runEndInstrumenters(element, writer);		
	}
}

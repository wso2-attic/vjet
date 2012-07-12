/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace;

	import org.w3c.dom.Node;

import org.ebayopensource.dsf.common.xml.IXmlStreamWriter;
import org.ebayopensource.dsf.dom.DAttr;
import org.ebayopensource.dsf.dom.DElement;


	/**
 * Defines common interface for adding, detecting and running
 * instrumenters that can insert tracing data into nodes during
 * the render stage.
 *
 */
public interface IDsfInstrumenter {

	/**
	 * Add component instrumenter.
	 * @param handler IDsfInstrumenter
	 * @return IDsfInstrumenter
	 */
	boolean addInstrumenter(IInstrumentDElement handler);
	
	boolean hasInstrumenter(Class clz);
	
	/**
	 * Reset component instrumenters. Removes all components instrumenters.
	 * @param handler IDsfInstrumenter
	 * @return IDsfInstrumenter
	 */
	void resetInstrumenter();
	
	/**
	 * Run all starting instrumentation handlers. Should be called at the end
	 * of instrumenting for a node.
	 * @param node DElement
	 */
	void runStartInstrumenters(DElement component, IXmlStreamWriter writer);
	
	/**
	 * Run all ending instrumentation handlers. Should be called at the end
	 * of instrumenting for a node.
	 * @param node DElement
	*/
	void runEndInstrumenters(DElement component, IXmlStreamWriter writer);
	
	/**
	 * Run all appendChild instrumentation handlers. Should be called when
	 * appending a node to the Dom tree.
	 * @param node DElement
	*/
	void runAppendInstrumenters(Node parent, Node component);
	
	/**
	 * Run all starting instrumentation handlers. Should be called at the end
	 * of instrumenting for a node.
	 * @param node DElement
	 */
	void runStartSelfRenderInstrumenters(DElement component, IXmlStreamWriter writer);
	
	/**
	 * Run all ending instrumentation handlers. Should be called at the end
	 * of instrumenting for a node.
	 * @param node DElement
	*/
	void runEndSelfRenderInstrumenters(DElement component, IXmlStreamWriter writerO);
	
	/**
	 * Run all attribute instrumentation handlers. Should be called at the end
	 * of attribute value change for an element.
	 */
	void runAttributeInstrumenters(DElement component, DAttr attr, String value);
}

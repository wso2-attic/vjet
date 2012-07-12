/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.ebayopensource.dsf.dom.DDocument;
import org.ebayopensource.dsf.dom.DElement;
import org.ebayopensource.dsf.html.IConditionalUsage.Condition;
import org.ebayopensource.dsf.html.dom.util.INodeEmitter;
import org.ebayopensource.dsf.html.dom.util.IRawSaxHandler;
import org.ebayopensource.dsf.html.dom.util.ISelfRender;
import org.ebayopensource.dsf.common.xml.IXmlStreamWriter;

/**
 * This special element is to support device-specific conditional comment
 * which wrappers arround other HTML element.
 * 
 * Passed-in element (rootElement) will be wrappered inside conditional
 * comment block(s).
 * 
 * Any direct DOM children added to this ConditionalElement are "unconditional"
 * elements. (Note, rootElement is not a direct DOM child of ConditionalElement)
 * 
 * All child elements under the "root conditional element" - rootElement are
 * also "conditional.
 * 
 * If there is no "unconditional" child, all conditional sub-tree will in inside
 * single conditional comment block, for example,
 * <code>
 * 	 <!--[if lt IE 8]>
 * 	 <div class="ie-only">
 * 		<h2>conditional header</h2>
 * 		<a>conditional-link</a>
 *   </div>
 *   <![endif]-->
 * </code>
 * 
 * If there are both conditional and unconditional childrens, two
 * conditional comment blocks will ensure the properly parenting with/without
 * condition. For example,
 * <code>
 * 	 <!--[if lt IE 8]>
 *   <div class="ie-only">
 *      <h2>conditional header</h2>
 *      <a>conditional-link</a>
 *      <![endif]-->
 *      <h4>unconditional header</h4>
 *      <h4>unconditional header</h4>
 *    <!--[if lt IE 8]>
 *    </div>
 *    <![endif]-->
 * </code>
 * 
 * If the unconditional children are under a conditional multi-depth sub-tree,
 * one can directly use StartConditionalComment and EndConditionalComment to
 * form the DOM tree effeciently. For example,
 * <code>
 * 	 <div>
 *      <h1>test conditional structure</h1>
 *      <!--[if lt IE 8]>
 *      <table border="1">
 *         <tr>
 *            <td>
 *               <![endif]-->
 *               <h2>unconditional header</h2>
 *               <a>unconditional-link</a>
 *               <!--[if lt IE 8]>
 *            </td>
 *         </tr>
 *      </table>
 *      <![endif]-->
 *   </div>
 * </code>
 */
public class ConditionalElement extends DElement implements ISelfRender {

	private static final long serialVersionUID = 1L;
	private final Condition m_condition;
	private final DElement m_rootElement;
		
	public ConditionalElement(
		final Condition condition, final DElement rootElement)
	{
		super((DDocument)null, "dummy");
		m_condition = condition;
		m_rootElement = rootElement;
	}
	
	public Condition getCondition() {
		return m_condition;
	}
	
	public static StartConditionalComment startCondition(
		final Condition condition)
	{
		return new StartConditionalComment(condition);
	}
	
	public static EndConditionalComment endCondition(
		final Condition condition)
	{
		return new EndConditionalComment(condition);
	}

	//
	// Satisfy ISelfRender
	//
	public boolean render(
		final IRawSaxHandler rawSaxHandler,
		final IXmlStreamWriter xmlStreamWriter,
		final INodeEmitter nodeEmitter)
	{
		boolean hasUnconditionalChild = hasChildNodes();
		
		xmlStreamWriter.writeRaw(IXmlStreamWriter.COMMENT_START);
		xmlStreamWriter.writeRaw(m_condition.getBegin());

		if (!hasUnconditionalChild) {
			nodeEmitter.genEvents(m_rootElement, xmlStreamWriter);
		}
		else {
			//for conditional children
			rawSaxHandler.startElement(m_rootElement);
			renderChildren(rawSaxHandler, xmlStreamWriter, nodeEmitter, m_rootElement);
			xmlStreamWriter.writeRaw(m_condition.getEnd());
			xmlStreamWriter.writeRaw(IXmlStreamWriter.COMMENT_END) ;
			
			//for unconditional children
			renderChildren(rawSaxHandler, xmlStreamWriter, nodeEmitter, this);
	
			xmlStreamWriter.writeRaw(IXmlStreamWriter.COMMENT_START);
			xmlStreamWriter.writeRaw(m_condition.getBegin());
			rawSaxHandler.endElement(m_rootElement);
		}
		xmlStreamWriter.writeRaw(m_condition.getEnd());
		xmlStreamWriter.writeRaw(IXmlStreamWriter.COMMENT_END) ;
		return true;
	}
	
	private static void renderChildren(
		final IRawSaxHandler rawSaxHandler,
		final IXmlStreamWriter xmlStreamWriter,
		final INodeEmitter nodeEmitter,
		final DElement element)
	{
		if (! element.hasChildNodes()) return ;
		final NodeList kids = element.getChildNodes();
		final int len = kids.getLength() ;
		for (int i = 0; i < len; i++) {
			final Node childNode = kids.item(i);
			nodeEmitter.genEvents(childNode, xmlStreamWriter);
		}
	}
	
	public static class StartConditionalComment 
		extends DElement implements ISelfRender
	{
		private static final long serialVersionUID = 1L;
		private final Condition m_condition;
		
		public StartConditionalComment(final Condition condition) {
			super((DDocument)null, "dummy");
			m_condition = condition;
		}
		// Satisfy ISelfRender
		public boolean render(
			final IRawSaxHandler rawSaxHandler,
			final IXmlStreamWriter xmlStreamWriter,
			final INodeEmitter nodeEmitter)
		{
			xmlStreamWriter.writeRaw(IXmlStreamWriter.COMMENT_START);
			xmlStreamWriter.writeRaw(m_condition.getBegin());
			return true;
		}
	}
	
	public static class EndConditionalComment extends DElement implements ISelfRender {

		private static final long serialVersionUID = 1L;
		private final Condition m_condition;
		
		public EndConditionalComment(Condition condition) {
			super((DDocument)null, "dummy");
			m_condition = condition;
		}
		
		public boolean render(
			final IRawSaxHandler rawSaxHandler,
			final IXmlStreamWriter xmlStreamWriter,
			final INodeEmitter nodeEmitter)
		{
			xmlStreamWriter.writeRaw(m_condition.getEnd());
			xmlStreamWriter.writeRaw(IXmlStreamWriter.COMMENT_END) ;
			return true;
		}
	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;

import org.ebayopensource.dsf.html.dom.util.INodeEmitter;
import org.ebayopensource.dsf.html.dom.util.IRawSaxHandler;
import org.ebayopensource.dsf.html.dom.util.ISelfRender;

import org.ebayopensource.dsf.common.Z;
import org.ebayopensource.dsf.common.xml.IXmlStreamWriter;

/**
 * The <code>ProcessingInstruction</code> interface represents a "processing 
 * instruction", used in XML as a way to keep processor-specific information 
 * in the text of the document.
 * <p> No lexical check is done on the content of a processing instruction and 
 * it is therefore possible to have the character sequence 
 * <code>"?&gt;"</code> in the content, which is illegal a processing 
 * instruction per section 2.6 of [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>]. The 
 * presence of this character sequence must generate a fatal error during 
 * serialization. 
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 *
 * PI's are not part of the documents character data, but MUST be passed through
 * to the application.  The target names "XML" and "xml" (lowercase) are reserved
 * by the spec.  Parameter entity references must NOT be recognized within
 * processing instructions.
 */
public class DProcessingInstruction
	extends DNode
	implements ProcessingInstruction, ISelfRender
{
	private static final long serialVersionUID = 3644273682180788756L;
	private String m_target ;
	
	//
	// Constructor(s)
	//
	public DProcessingInstruction(final DDocument document, final String target){
		this(document, target, null) ;
	}
	
	public DProcessingInstruction(
		final DDocument document, final String target, final String data)
	{
		super(document);
		m_target = target ;
		m_nodeValue = data ;
	}
	
	//
	// Satisfy ISelfRender
	//
	@Override
	public boolean render(
		final IRawSaxHandler rawSaxHandler,
		final IXmlStreamWriter xmlStreamWriter, 
		final INodeEmitter nodeEmitter)
	{
		xmlStreamWriter.writeRaw("<?") ;
		xmlStreamWriter.writeRaw(getTarget()) ;
		xmlStreamWriter.writeRaw(" ") ;
		String data = getData() == null ? "" : getData() ;
		xmlStreamWriter.writeRaw(data) ;
		xmlStreamWriter.writeRaw("?>") ;
		return true; // don't try to process children
	}
	//
	// API
	//
	@Override
	public Node appendChild(final Node newChild) throws DOMException {
		throw new DOMException(
			DOMException.VALIDATION_ERR, 
			"ProcessingInstruction does not support having children");
	}
	
	//
	// API
	//
    /**
     * The target of this processing instruction. XML defines this as being 
     * the first token following the markup that begins the processing 
     * instruction.
     */
    public String getTarget() {
    	return m_target ;
    }

    /**
     * The content of this processing instruction. This is from the first non 
     * white space character after the target to the character immediately 
     * preceding the <code>?&gt;</code>.
     */
    public String getData() {
    	return m_nodeValue ;
    }
    
    /**
     * The content of this processing instruction. This is from the first non 
     * white space character after the target to the character immediately 
     * preceding the <code>?&gt;</code>.
     * @exception IDOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised when the node is readonly.
     */
    public DProcessingInstruction setDsfData(final String data) throws DOMException {
    	setData(data) ;
    	return this ;
    }
    public void setData(final String data) throws DOMException {
    	m_nodeValue = data ;
    }

	@Override
	public final short getNodeType() {
		return Node.PROCESSING_INSTRUCTION_NODE;
	}
	
	/**
	 * This attribute returns the text content of this node and its 
	 * descendants. When it is defined to be <code>null</code>, setting it 
	 * has no effect. On setting, any possible children this node may have 
	 * are removed and, if it the new string is not empty or 
	 * <code>null</code>, replaced by a single <code>Text</code> node 
	 * containing the string this attribute is set to. 
	 * <br> On getting, no serialization is performed, the returned string 
	 * does not contain any markup. No whitespace normalization is performed 
	 * and the returned string does not contain the white spaces in element 
	 * content (see the attribute 
	 * <code>Text.isElementContentWhitespace</code>). Similarly, on setting, 
	 * no parsing is performed either, the input string is taken as pure 
	 * textual content. 
	 * <br>The string returned is made of the text content of this node 
	 * depending on its type, as defined below: 
	 * <table border='1' cellpadding='3'>
	 * <tr>
	 * <th>Node type</th>
	 * <th>Content</th>
	 * </tr>
	 * <tr>
	 * <td valign='top' rowspan='1' colspan='1'>
	 * ELEMENT_NODE, ATTRIBUTE_NODE, ENTITY_NODE, ENTITY_REFERENCE_NODE, 
	 * DOCUMENT_FRAGMENT_NODE</td>
	 * <td valign='top' rowspan='1' colspan='1'>concatenation of the <code>textContent</code> 
	 * attribute value of every child node, excluding COMMENT_NODE and 
	 * PROCESSING_INSTRUCTION_NODE nodes. This is the empty string if the 
	 * node has no children.</td>
	 * </tr>
	 * <tr>
	 * <td valign='top' rowspan='1' colspan='1'>TEXT_NODE, CDATA_SECTION_NODE, COMMENT_NODE, 
	 * PROCESSING_INSTRUCTION_NODE</td>
	 * <td valign='top' rowspan='1' colspan='1'><code>nodeValue</code></td>
	 * </tr>
	 * <tr>
	 * <td valign='top' rowspan='1' colspan='1'>DOCUMENT_NODE, 
	 * DOCUMENT_TYPE_NODE, NOTATION_NODE</td>
	 * <td valign='top' rowspan='1' colspan='1'><em>null</em></td>
	 * </tr>
	 * </table>
	 * @exception DOMException
	 *   DOMSTRING_SIZE_ERR: Raised when it would return more characters than 
	 *   fit in a <code>DOMString</code> variable on the implementation 
	 *   platform.
	 *
	 * @since DOM Level 3
	 */
	@Override
	public String getTextContent() throws DOMException {
		return getNodeValue() ;
	}
	
	@Override
	public DProcessingInstruction jif(final String jif) { 
		super.jif(jif) ;
		return this ;
	}
	
    //
    // Override(s) from Object
    //
	@Override
    public String toString() {
    	Z z = new Z() ;
    	z.format("target", m_target) ;
    	z.format("data", m_nodeValue) ;    	
    	return z.toString() ;
    }
}

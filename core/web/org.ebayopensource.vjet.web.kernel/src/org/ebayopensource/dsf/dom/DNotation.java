/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.Notation;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;

/**
 * This interface represents a notation declared in the DTD. A notation either 
 * declares, by name, the format of an unparsed entity (see <a href='http://www.w3.org/TR/2004/REC-xml-20040204#Notations'>section 4.7</a> of the XML 1.0 specification [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>]), or is 
 * used for formal declaration of processing instruction targets (see <a href='http://www.w3.org/TR/2004/REC-xml-20040204#sec-pi'>section 2.6</a> of the XML 1.0 specification [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>]). The 
 * <code>nodeName</code> attribute inherited from <code>Node</code> is set 
 * to the declared name of the notation.
 * <p>The DOM Core does not support editing <code>Notation</code> nodes; they 
 * are therefore readonly.
 * <p>A <code>Notation</code> node does not have any parent.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 */
public class DNotation extends DNode implements Notation {
	private static final long serialVersionUID = 5984087719387769295L;
	
	private String m_publicId ;
	private String m_systemId ;
	
	//
	// Constructor(s)
	//
	public DNotation(final DDocument doc, final String name) {
		super(doc, name);
	}	
	
	//
	// Framework
	//
	@Override
	public Node appendChild(final Node newChild) throws DOMException {
		throw new DOMException(
			DOMException.VALIDATION_ERR, 
			"Notation does not support having children");
	}
	
	@Override
	public final short getNodeType() {
		return Node.NOTATION_NODE;
	}
	
	//
	// Satisfy INotation
	//
    /**
	 * The public identifier of this notation. If the public identifier was 
	 * not specified, this is <code>null</code>.
	 */
	public String getPublicId() {
		return m_publicId;
	}

	/**
	 * The system identifier of this notation. If the system identifier was 
	 * not specified, this is <code>null</code>. This may be an absolute URI 
	 * or not.
	 */
	public String getSystemId() {
		return m_systemId;
	}

	//
	// API
	//
	public DNotation setDsfPublicId(final String id) {
		setPublicId(id) ;
		return this ;
	}
	public void setPublicId(final String id) {
		m_publicId = id;
	}

	public DNotation setDsfSystemId(final String id) {
		setSystemId(id) ;
		return this ;
	}
	public void setSystemId(final String id) {
		m_systemId = id;
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
		return null ;
	}
	
	@Override
	public DNotation jif(final String jif) { 
		super.jif(jif) ;
		return this ;
	}
	
	//
	// Override(s) from Object
	//
    @Override
    public String toString() {
    	final StringWriter out = new StringWriter(100) ;
    	write(out) ;
    	return out.toString();
    }
    
    //
    // Private
    //
    public void write(final Writer out) {
		String entityNodeName = this.getNodeName();
		String entityPublicId = this.getPublicId() ;
		String entitySystemId = this.getSystemId() ;
		boolean entityHasPublic = false ;
		
		try {
			out.write("<!NOTATION ");
	
			if (entityNodeName != null) {
				out.write(entityNodeName);
			}
			
			if (entityPublicId != null) {
				out.write(" PUBLIC \"");
				out.write(entityPublicId);
				out.write("\"");
				entityHasPublic = true;
			}
			
			if (entitySystemId != null) {
				if (!entityHasPublic) {
					out.write(" SYSTEM");
				}
				out.write(" \"");
				out.write(entitySystemId);
				out.write("\"");
			}
			
			out.write(">");
		}
		catch(IOException e) {
			throw new DsfRuntimeException(e) ;
		}
    }
}


/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import org.w3c.dom.CDATASection;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;


/**
 * CDATA sections are used to escape blocks of text containing characters that 
 * would otherwise be regarded as markup. The only delimiter that is 
 * recognized in a CDATA section is the "]]&gt;" string that ends the CDATA 
 * section. CDATA sections cannot be nested. Their primary purpose is for 
 * including material such as XML fragments, without needing to escape all 
 * the delimiters.
 * <p>The <code>CharacterData.data</code> attribute holds the text that is 
 * contained by the CDATA section. Note that this <em>may</em> contain characters that need to be escaped outside of CDATA sections and 
 * that, depending on the character encoding ("charset") chosen for 
 * serialization, it may be impossible to write out some characters as part 
 * of a CDATA section.
 * <p>The <code>CDATASection</code> interface inherits from the 
 * <code>CharacterData</code> interface through the <code>Text</code> 
 * interface. Adjacent <code>CDATASection</code> nodes are not merged by use 
 * of the <code>normalize</code> method of the <code>Node</code> interface.
 * <p> No lexical check is done on the content of a CDATA section and it is 
 * therefore possible to have the character sequence <code>"]]&gt;"</code> 
 * in the content, which is illegal in a CDATA section per section 2.7 of [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>]. The 
 * presence of this character sequence must generate a fatal error during 
 * serialization or the cdata section must be splitted before the 
 * serialization (see also the parameter <code>"split-cdata-sections"</code> 
 * in the <code>DOMConfiguration</code> interface). 
 * <p ><b>Note:</b> Because no markup is recognized within a 
 * <code>CDATASection</code>, character numeric references cannot be used as 
 * an escape mechanism when serializing. Therefore, action needs to be taken 
 * when serializing a <code>CDATASection</code> with a character encoding 
 * where some of the contained characters cannot be represented. Failure to 
 * do so would not produce well-formed XML.
 * <p ><b>Note:</b> One potential solution in the serialization process is to 
 * end the CDATA section before the character, output the character using a 
 * character reference or entity reference, and open a new CDATA section for 
 * any further characters in the text node. Note, however, that some code 
 * conversion libraries at the time of writing do not return an error or 
 * exception when a character is missing from the encoding, making the task 
 * of ensuring that data is not corrupted on serialization more difficult.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 */

public class DCDATASection extends DText implements CDATASection {
	private static final long serialVersionUID = 6737293443738770450L;

	//
	// Constructor(s)
	//
	public DCDATASection(final String data) {
		this(null, data) ;
	}
	
	DCDATASection(final DDocument document, final String data) {
		super(document, data) ;
	}
	
	//
	// Framework
	//
	@Override
	public Node appendChild(final Node newChild) throws DOMException {
		throw new DOMException(
			DOMException.VALIDATION_ERR, 
			"CDATASection does not support having children");
	}
	
	@Override
	public final short getNodeType() {
		return Node.CDATA_SECTION_NODE;
	}
	
	//
	// More DOM 2.0/3.0
	//
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
	public DCDATASection jif(final String jif) { 
		super.jif(jif) ;
		return this ;
	}
}

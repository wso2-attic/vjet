/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.TypeInfo;

import org.ebayopensource.dsf.common.DsfVerifierConfig;
import org.ebayopensource.dsf.common.binding.IValueBinding;
import org.ebayopensource.dsf.common.binding.SimpleValueBinding;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.trace.TraceCtx;
import org.ebayopensource.dsf.dom.support.DsfDomLevelNotSupportedException;
import org.ebayopensource.dsf.dom.support.XmlVerifier;
import org.ebayopensource.dsf.common.Z;

public class DAttr extends DNode implements Attr, IValueBinding<String> {

	private static final long serialVersionUID = 1L;
	
	private DElement m_ownerElement = null;
	private boolean m_isId = false;
	private IValueBinding<Object> m_valueBinding = null;
// MrPperf - need some index -> char[] for faster render
	private int m_nameAsCharsIndex = -1 ;
	private transient TraceCtx m_traceCtx ;

	//
	// Constructor(s)
	//
	public DAttr(final DDocument document, final String name) {
		this(document, name, null);
	}

	public DAttr(final DDocument document, final String name, final String value) {
		super(document, name);
		if (DsfVerifierConfig.getInstance().isVerifyNaming()) {
			if (XmlVerifier.isXMLName(name, false) == false) {
				throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "bad Attribute tag name: " + name) ;

			}
		}
		m_nodeValue = value;
	}

	public DAttr(final String name, final String value) {
		this(null, name, value);
	}

	//
	// Other API
	//
	protected void setOwnerElement(final DElement ownerElement) {
		m_ownerElement = ownerElement;
	}
	
	void setNameAsCharIndex(final int index) {
		m_nameAsCharsIndex = index ;
	}
	
	int getNameAsCharIndex() {
		return m_nameAsCharsIndex ;
	}

	//
	// Satisfy IValueBinding<String>
	//
	public Class<String> getValueType() { 
		return String.class ;
	}
	
	/**
	 * On retrieval, the value of the attribute is returned as a string. 
	 * Character and general entity references are replaced with their 
	 * values. See also the method <code>getAttribute</code> on the 
	 * <code>Element</code> interface.
	 * <br>On setting, this creates a <code>Text</code> node with the unparsed 
	 * contents of the string, i.e. any characters that an XML processor 
	 * would recognize as markup are instead treated as literal text. See 
	 * also the method <code>Element.setAttribute()</code>.
	 * <br> Some specialized implementations, such as some [<a href='http://www.w3.org/TR/2003/REC-SVG11-20030114/'>SVG 1.1</a>] 
	 * implementations, may do normalization automatically, even after 
	 * mutation; in such case, the value on retrieval may differ from the 
	 * value on setting. 
	 */
	public String getValue() {
		String value = m_nodeValue;
		if (m_valueBinding != null) {
			final Object oValue = m_valueBinding.getValue();
			value = (oValue == null) ? null : oValue.toString() ;
		}
		// In the DOM world null means empty String
		return (value == null) ? "" : value ;
	}

	/**
	 * On retrieval, the value of the attribute is returned as a string. 
	 * Character and general entity references are replaced with their 
	 * values. See also the method <code>getAttribute</code> on the 
	 * <code>Element</code> interface.
	 * <br>On setting, this creates a <code>Text</code> node with the unparsed 
	 * contents of the string, i.e. any characters that an XML processor 
	 * would recognize as markup are instead treated as literal text. See 
	 * also the method <code>Element.setAttribute()</code>.
	 * <br> Some specialized implementations, such as some [<a href='http://www.w3.org/TR/2003/REC-SVG11-20030114/'>SVG 1.1</a>] 
	 * implementations, may do normalization automatically, even after 
	 * mutation; in such case, the value on retrieval may differ from the 
	 * value on setting. 
	 * @exception DOMException
	 *   NO_MODIFICATION_ALLOWED_ERR: Raised when the node is readonly.
	 */
	public void setValue(final String value) throws DOMException {
		setNodeValue(value);
		if (m_valueBinding != null) {
			m_valueBinding.setValue(value);
		}
	}
	
	//
	// Satisfy Attr
	//
	/**
	 * Returns the name of this attribute. 
	 * <br>The attribute name will never be null.
	 * <br>The attribute name is readonly and set at construction time.
	 */
	public String getName() {
		return getNodeName();
	}

	/**
	 *  <code>True</code> if this attribute was explicitly given a value in 
	 * the instance document, <code>false</code> otherwise. If the 
	 * application changed the value of this attribute node (even if it ends 
	 * up having the same value as the default value) then it is set to 
	 * <code>true</code>. The implementation may handle attributes with 
	 * default values from other schemas similarly but applications should 
	 * use <code>Document.normalizeDocument()</code> to guarantee this 
	 * information is up-to-date. 
	 */
	public boolean getSpecified() {
		// TODO: because we do not have a schema/DTD, this should always
		// be true.  When we have schema/DTD capability to supply defaults,
		// then this will need to get fixed.
		return true;
	}

	@Override
	public void setNodeValue(final String nodeValue) throws DOMException {
		
		if (isId() && getOwnerDocument() != null) {
			final String oldValue = getValue();
			if (oldValue != null) {
				getDsfOwnerDocument().removeIdentifier(oldValue);
			}
			super.setNodeValue(nodeValue);
			if (nodeValue != null) {
				getDsfOwnerDocument().putIdentifier(nodeValue, m_ownerElement);
			}
		} 
		else {
			super.setNodeValue(nodeValue);
		}
		
		if (getTraceCtx().haveInstrumenter()) {
			m_traceCtx.getInstrumenter().runAttributeInstrumenters(m_ownerElement, this, nodeValue);
		}
	}
	
	/**
	 * Set the attribute value after removing the instrumentation
	 * This is called only by the attribute instrumenter
	 * @param oldValue
	 */
	public void setOldValue(final String oldValue) {
		m_nodeValue = oldValue;
	}
	

	/**
	 * The <code>Element</code> node this attribute is attached to or 
	 * <code>null</code> if this attribute is not in use.
	 * @since DOM Level 2
	 */
	public Element getOwnerElement() {
		return m_ownerElement;
	}

	/**
	 *  The type information associated with this attribute. While the type 
	 * information contained in this attribute is guarantee to be correct 
	 * after loading the document or invoking 
	 * <code>Document.normalizeDocument()</code>, <code>schemaTypeInfo</code>
	 *  may not be reliable if the node was moved. 
	 * @since DOM Level 3
	 */
	public TypeInfo getSchemaTypeInfo() {
		throw new DsfDomLevelNotSupportedException(3);
	}

	/**
	 *  Returns whether this attribute is known to be of type ID (i.e. to 
	 * contain an identifier for its owner element) or not. When it is and 
	 * its value is unique, the <code>ownerElement</code> of this attribute 
	 * can be retrieved using the method <code>Document.getElementById</code>
	 * . The implementation could use several ways to determine if an 
	 * attribute node is known to contain an identifier: 
	 * <ul>
	 * <li> If validation 
	 * occurred using an XML Schema [<a href='http://www.w3.org/TR/2001/REC-xmlschema-1-20010502/'>XML Schema Part 1</a>]
	 *  while loading the document or while invoking 
	 * <code>Document.normalizeDocument()</code>, the post-schema-validation 
	 * infoset contributions (PSVI contributions) values are used to 
	 * determine if this attribute is a schema-determined ID attribute using 
	 * the <a href='http://www.w3.org/TR/2003/REC-xptr-framework-20030325/#term-sdi'>
	 * schema-determined ID</a> definition in [<a href='http://www.w3.org/TR/2003/REC-xptr-framework-20030325/'>XPointer</a>]
	 * . 
	 * </li>
	 * <li> If validation occurred using a DTD while loading the document or 
	 * while invoking <code>Document.normalizeDocument()</code>, the infoset <b>[type definition]</b> value is used to determine if this attribute is a DTD-determined ID 
	 * attribute using the <a href='http://www.w3.org/TR/2003/REC-xptr-framework-20030325/#term-ddi'>
	 * DTD-determined ID</a> definition in [<a href='http://www.w3.org/TR/2003/REC-xptr-framework-20030325/'>XPointer</a>]
	 * . 
	 * </li>
	 * <li> from the use of the methods <code>Element.setIdAttribute()</code>, 
	 * <code>Element.setIdAttributeNS()</code>, or 
	 * <code>Element.setIdAttributeNode()</code>, i.e. it is an 
	 * user-determined ID attribute; 
	 * <p ><b>Note:</b>  XPointer framework (see section 3.2 in [<a href='http://www.w3.org/TR/2003/REC-xptr-framework-20030325/'>XPointer</a>]
	 * ) consider the DOM user-determined ID attribute as being part of the 
	 * XPointer externally-determined ID definition. 
	 * </li>
	 * <li> using mechanisms that 
	 * are outside the scope of this specification, it is then an 
	 * externally-determined ID attribute. This includes using schema 
	 * languages different from XML schema and DTD. 
	 * </li>
	 * </ul>
	 * <br> If validation occurred while invoking 
	 * <code>Document.normalizeDocument()</code>, all user-determined ID 
	 * attributes are reset and all attribute nodes ID information are then 
	 * reevaluated in accordance to the schema used. As a consequence, if 
	 * the <code>Attr.schemaTypeInfo</code> attribute contains an ID type, 
	 * <code>isId</code> will always return true. 
	 * @since DOM Level 3
	 */
	public boolean isId() {
		return m_isId;
	}

	@Override
	/**
	 * The w3c Node constant name for Attr - value is (2).
	 * The value is readonly and is a constant for all Attr instances.
	 * @return org.w3c.dom.Node.ATTRIBUTE_NODE (2)
	 */
	public final short getNodeType() {
		return Node.ATTRIBUTE_NODE;
	}

	void setId(final boolean isId) {
		m_isId = isId;
	}

	public IValueBinding<Object> getValueBinding() {
		return m_valueBinding;
	}

	public IValueBinding<Object> setValueBinding(
		final IValueBinding<Object> binding)
	{
		final IValueBinding<Object> pre = m_valueBinding;
		m_valueBinding = binding;
		return pre;
	}
	
	public void setObjectValue(final Object value) {
		if (value instanceof String) {
			setNodeValue((String)value);
			if (m_valueBinding != null) {
				m_valueBinding.setValue(value);
			}
		} else {
			if (m_valueBinding == null) {
				m_valueBinding = new SimpleValueBinding<Object>(Object.class);
			}
			m_valueBinding.setValue(value);
		}
	}
	
	public Object getObjectValue() {
		if (m_valueBinding == null) {
			return getNodeValue();
		}
		return m_valueBinding.getValue();
	}
	
	@Override
	final DNode setParent(final DNode parent) {
		throw new DsfRuntimeException("attribute '" + getName() + "' cannot have a parent");
	}
	
	private TraceCtx getTraceCtx() {
		if (m_traceCtx == null) {
			m_traceCtx = TraceCtx.ctx();
		}
		return m_traceCtx ;
	}
	
	@Override
	public DAttr jif(final String jif) { 
		super.jif(jif) ;
		return this ;
	}
	
    //
    // Override(s) from Object
    //
	public static void main(String[] args) {
		DElement root = new DElement("root") ;
		root.setAttribute("a", "alpha") ;
		
	}
    public String toString() {
    	Z z = new Z() ;
    	
    	z.format("owning Element", 
    		m_ownerElement == null ? null : m_ownerElement.getTagName()) ;
    	z.format("isId", isId()) ;
    	z.format("has binding", m_valueBinding != null) ;
    	z.append(super.toString()) ;

    	return z.toString() ;
    }
}

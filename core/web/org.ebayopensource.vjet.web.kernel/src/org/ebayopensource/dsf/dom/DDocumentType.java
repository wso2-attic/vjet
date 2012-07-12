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
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Notation;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.Z;

/**
 * Each <code>Document</code> has a <code>doctype</code> attribute whose value 
 * is either <code>null</code> or a <code>DocumentType</code> object. The 
 * <code>DocumentType</code> interface in the DOM Core provides an interface 
 * to the list of entities that are defined for the document, and little 
 * else because the effect of namespaces and the various XML schema efforts 
 * on DTD representation are not clearly understood as of this writing.
 * <p>DOM Level 3 doesn't support editing <code>DocumentType</code> nodes. 
 * <code>DocumentType</code> nodes are read-only.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 */
public class DDocumentType extends DNode implements DocumentType {
	private static final long serialVersionUID = -4825617330411078512L;
	private final String m_publicId ;
	private final String m_systemId ;

	private String m_internalSubset ;
	
	private DNamedNodeMap m_entities=new DNamedNodeMap(2,Node.ENTITY_NODE);
	private DNamedNodeMap m_notations=new DNamedNodeMap(2,Node.NOTATION_NODE);
	
	//
	// Constructor(s)
	//
	/**
	 * This will create the <code>DocType</code> with the specified element 
	 * name and a reference to an external DTD.
	 */
	public DDocumentType(
		final String qualifiedName, 
		final String publicId, 
		final String systemId)
	{
		this(null, qualifiedName, publicId, systemId);
	}
	
	public DDocumentType(
		final DDocument ownerDocument,
		final String qualifiedName,
		final String publicId,
		final String systemId)
	{
		super(ownerDocument, qualifiedName);
		m_publicId = publicId ;
		m_systemId = systemId;
	}

	//
	// Framework
	//
	@Override
	public final short getNodeType() {
		return Node.DOCUMENT_TYPE_NODE;
	}
	
	@Override
	public Node appendChild(final Node newChild) throws DOMException {
		if (newChild != null) {
			final Class<?> clz = newChild.getClass();
			if (Entity.class.isAssignableFrom(clz)) {
				getEntities().setNamedItem((Entity)newChild);
				return newChild ;
			}
			else if (Notation.class.isAssignableFrom(clz)) {
				getNotations().setNamedItem((Notation)newChild);
				return newChild ;
			}
		}
		throw new DOMException(
			DOMException.VALIDATION_ERR, 
			"DocumentType does not support having children");
	}
	
	//
	// Helper(s)
	//
	
	/**
	 * @Deprecated
	 * Use DDocumentType.write(final Writer out)
	 */
	@Deprecated
	public String asString() {
		StringWriter out = new StringWriter() ;
		write(out);
		
		return out.toString();
	}

	public void write(final Writer out) {
		final String publicId = getPublicId();
		final String systemId = getSystemId();
		final String internalSubset = getInternalSubset();
		
		boolean hasPublic = false;

		try {
			out.write("<!DOCTYPE ");
			out.write(super.getNodeName());  // TODO: is this the right calls???
			
			if (publicId != null) {
				out.write(" PUBLIC \"");
				out.write(publicId);
				out.write("\"");
				hasPublic = true;
			}
			
			if (systemId != null) {
				if (!hasPublic) {
					out.write(" SYSTEM");
				}
				out.write(" \"");
				out.write(systemId);
				out.write("\"");
			}
			
			/*
			 * We now have 3 other pieces to worry about:
			 * 1. internal subset
			 * 2. Entity definitions
			 * 3. Notation definitions
			 * 
			 * If we have any of these we need to emit the '[' and ']'
			 */
			boolean extraOutput = false ;
			if ( ((internalSubset != null) && (!internalSubset.equals(""))
				|| getEntities().getLength() > 0
				|| getNotations().getLength() > 0))
			{
				extraOutput = true ;
			}
			
			if (!extraOutput) {
				out.write(">");
				return ;
			}
			
			out.write(" [");
			if ( (internalSubset != null) && (!internalSubset.equals("")) ) {
				out.write(Z.NL);
				out.write(getInternalSubset());
			}
		
			// Handle notations first since they may influence other generations
			// This is not scientific, just a best guess on processors...
			int len = getNotations().getLength() ;
			for(int i = 0; i < len; i++) {
				final Object obj = getNotations().item(i) ;	
				((DNotation)obj).write(out) ;
			}		
	
			// Output the Entities
			len = getEntities().getLength() ;
			for(int i = 0; i < len; i++) {
				final Object obj = getEntities().item(i) ;	
				if (obj instanceof DEntity) {
					((DEntity)obj).write(out) ;
				}
				else {
					((DEntityInternal)obj).write(out) ;
				}
			}
			
			out.write("]");
			out.write(">");
		}
		catch(IOException e) {
			throw new DsfRuntimeException(e) ;
		}
	}
	
	public DEntity createEntity(final String name) {
		DEntity entity = new DEntity(getDsfOwnerDocument(), name) ;
		getDsfEntities().add(entity) ;
		return entity ;
	}
	
	public DEntity createEntity(final String name, final String value) {
		DEntity entity = new DEntity(getDsfOwnerDocument(), name, value) ;
		getDsfEntities().add(entity) ;
		return entity ;
	}
	
	public DNotation createNotation(final String name) {
		DNotation notation = new DNotation(getDsfOwnerDocument(), name) ;
		getDsfNotations().add(notation) ;
		return notation ;
	}
	
	//
	// Satisfy DocumentType
	//
    /**
     * The name of DTD; i.e., the name immediately following the 
     * <code>DOCTYPE</code> keyword.
     */
    public String getName() {
    	return getNodeName() ;
    }

    /**
     * A <code>NamedNodeMap</code> containing the general entities, both 
     * external and internal, declared in the DTD. Parameter entities are 
     * not contained. Duplicates are discarded. For example in: 
     * <pre>&lt;!DOCTYPE 
     * ex SYSTEM "ex.dtd" [ &lt;!ENTITY foo "foo"&gt; &lt;!ENTITY bar 
     * "bar"&gt; &lt;!ENTITY bar "bar2"&gt; &lt;!ENTITY % baz "baz"&gt; 
     * ]&gt; &lt;ex/&gt;</pre>
     *  the interface provides access to <code>foo</code> 
     * and the first declaration of <code>bar</code> but not the second 
     * declaration of <code>bar</code> or <code>baz</code>. Every node in 
     * this map also implements the <code>Entity</code> interface.
     * <br>The DOM Level 2 does not support editing entities, therefore 
     * <code>entities</code> cannot be altered in any way.
     */
    public NamedNodeMap getEntities() {
    	return m_entities ;
    }
    public DNamedNodeMap getDsfEntities() {
    	return (DNamedNodeMap)getEntities() ;
    }

    /**
     * A <code>NamedNodeMap</code> containing the notations declared in the 
     * DTD. Duplicates are discarded. Every node in this map also implements 
     * the <code>Notation</code> interface.
     * <br>The DOM Level 2 does not support editing notations, therefore 
     * <code>notations</code> cannot be altered in any way.
     */
    public NamedNodeMap getNotations() {
    	return m_notations ;
    }
    public DNamedNodeMap getDsfNotations() {
    	return (DNamedNodeMap)getNotations() ;
    }

    /**
     * The public identifier of the external subset.
     * @since DOM Level 2
     */
    public String getPublicId() {
    	return m_publicId ;
    }

    /**
     * The system identifier of the external subset. This may be an absolute 
     * URI or not.
     * @since DOM Level 2
     */
    public String getSystemId() {
    	return m_systemId ;
    }

    /**
     * The internal subset as a string, or <code>null</code> if there is none. 
     * This is does not contain the delimiting square brackets.
     * <p ><b>Note:</b> The actual content returned depends on how much 
     * information is available to the implementation. This may vary 
     * depending on various parameters, including the XML processor used to 
     * build the document.
     * @since DOM Level 2
     */
    public String getInternalSubset() {
    	return m_internalSubset ;
    }
    
    //
    // NON-DOM
    //
    /**
     * NON-DOM. <p>
     *
     * Set the internalSubset given as a string.
     */
    public void setInternalSubset(final String internalSubset) {
        m_internalSubset = internalSubset;
    }
    
//    /**
//     * NON-DOM: Access the collection of ElementDefinitions.
//     * @see ElementDefinitionImpl
//     */
//    public NamedNodeMap getElements() {
//    	return m_elements;
//    }
	/**
	 * NON-DOM
	 * set the ownerDocument of this node and its children
	 */
	@Override
	void setDsfOwnerDocument(DDocument doc) {
		super.setDsfOwnerDocument(doc);
//		m_entities.setOwnerDocument(doc);
//		m_notations.setOwnerDocument(doc);
//		m_elements.setOwnerDocument(doc);
	}
    
    //
    // DOM 3
    //
    /*
     * Set Node text content
     * @since DOM Level 3
     */
    @Override
    public void setTextContent(final String textContent) throws DOMException {
        // no-op
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
	public DDocumentType jif(final String jif) { 
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
	
	public boolean isEqualNode(Node arg) {        
        if (!super.isEqualNode(arg)) {
            return false;
        }        
       
        DDocumentType argDocType = (DDocumentType) arg;

        //test if the following string attributes are equal: publicId, 
        //systemId, internalSubset.
        if (!isEqualString(getPublicId(), argDocType.getPublicId())           
            || !isEqualString(getSystemId(), getSystemId())           
            || !isEqualString(getInternalSubset(), argDocType.getInternalSubset()))
        {
            return false;
        }
        
        //test if NamedNodeMaps entities is equal
        if (!isSameNamedNodeMap(this.getEntities(), argDocType.getEntities())){
        	return false;
        }

        //test if NamedNodeMaps notations is equal
        if (!isSameNamedNodeMap(this.getNotations(), argDocType.getNotations())){
        	return false;
        }
        return true;
    }

	private boolean isSameNamedNodeMap(NamedNodeMap thisObj, NamedNodeMap argObj){
		if ((thisObj == null && argObj != null) || (thisObj != null && argObj == null)){
			return false;
		}
		if (thisObj != null && argObj != null){
			if (thisObj.getLength() != argObj.getLength()){
				return false;
			}
			 for (int index = 0; thisObj.item(index) != null; index++) {
                Node entNode1 = thisObj.item(index);
                Node entNode2 =
                    argObj.getNamedItem(entNode1.getNodeName());

                if (!entNode1.isEqualNode(entNode2))
                    return false;
            }
		}
		return true;
	}

}

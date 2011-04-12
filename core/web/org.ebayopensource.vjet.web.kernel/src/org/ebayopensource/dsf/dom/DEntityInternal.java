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
import org.w3c.dom.Entity;
import org.w3c.dom.Node;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
/**
MrP - This is for internal entity references.  It will implement Entity, but
does not have systemId, publicId or notation name.  The format will be
<!ENTITY [%] name replacementText>
*/

/**
 * This interface represents a known entity, either parsed or unparsed, in an 
 * XML document. Note that this models the entity itself <em>not</em> the entity declaration.
 * <p>The <code>nodeName</code> attribute that is inherited from 
 * <code>Node</code> contains the name of the entity.
 * <p>An XML processor may choose to completely expand entities before the 
 * structure model is passed to the DOM; in this case there will be no 
 * <code>EntityReference</code> nodes in the document tree.
 * <p>XML does not mandate that a non-validating XML processor read and 
 * process entity declarations made in the external subset or declared in 
 * parameter entities. This means that parsed entities declared in the 
 * external subset need not be expanded by some classes of applications, and 
 * that the replacement text of the entity may not be available. When the <a href='http://www.w3.org/TR/2004/REC-xml-20040204#intern-replacement'>
 * replacement text</a> is available, the corresponding <code>Entity</code> node's child list 
 * represents the structure of that replacement value. Otherwise, the child 
 * list is empty.
 * <p>DOM Level 3 does not support editing <code>Entity</code> nodes; if a 
 * user wants to make changes to the contents of an <code>Entity</code>, 
 * every related <code>EntityReference</code> node has to be replaced in the 
 * structure model by a clone of the <code>Entity</code>'s contents, and 
 * then the desired changes must be made to each of those clones instead. 
 * <code>Entity</code> nodes and all their descendants are readonly.
 * <p>An <code>Entity</code> node does not have any parent.
 * <p ><b>Note:</b> If the entity contains an unbound namespace prefix, the 
 * <code>namespaceURI</code> of the corresponding node in the 
 * <code>Entity</code> node subtree is <code>null</code>. The same is true 
 * for <code>EntityReference</code> nodes that refer to this entity, when 
 * they are created using the <code>createEntityReference</code> method of 
 * the <code>Document</code> interface.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 */

/*
Entity Declarations

Entities must be declared before they can be used. They may be declared in the DTD, 
if your XML parser processes the DTD (also known as the external subset), or the 
internal subset. Note: if the same entity is declared more than once, only the 
first declaration applies and the internal subset is processed before the external 
subset.
<p>
All entities are declared with the "ENTITY" declaration. The exact format of the 
declaration distinguishes between internal, external, and parameter entities.
<p>
Declaring Internal Entities
<p>
An internal entity declaration has the following form:

<!ENTITY entityname "replacement text">

You can use either double or single quotes to delimit the replacement text. The 
declaration of yoyo, mentioned earlier, would be:

<!ENTITY yoyo 'Yoyodyne Industries, Inc.'>

Declaring External Entities

External entity declarations come in two forms. If the external entity contains 
XML text, the declaration has the following form:

<!ENTITY entityname [PUBLIC "public-identifier"]
	SYSTEM "system-identifier">

The system identifier must point to an instance of a resource via a URI, most 
commonly a simple filename. The public identifier, if supplied, may be used by 
an XML system to generate an alternate URI (this provides a handy level of 
indirection on systems that support public identifiers).

An external entity that incorporates chap1.xml into your document might be 
declared like this:

<!ENTITY chap1 SYSTEM "chap1.xml">

Despite the growing trend to store everything in XML, there are some legacy 
systems that still store data in non-XML formats. Graphics are sometimes stored 
in odd formats like PNG and GIF, for example ;-).

External entities that refer to these files must declare that data they contain 
is not XML. They accomplish this by indicating the format of the external entity 
in a notation:

<!ENTITY entityname [PUBLIC "public-identifier"]
	SYSTEM "system-identifier" notation>

See the section called Entity Attributes for more detail. An external entity 
that refers to the GIF image pic01.gif might be declared like this:

<!ENTITY mypicture SYSTEM "pic01.gif" GIF>

Declaring Parameter Entities

Parameter entity declarations are identified by a % preceding the entity name:

<!ENTITY % pentityname1 "replacement text">
<!ENTITY % pentityname2 SYSTEM "URI">

Note the space following the % in the declaration. Parameter entities can be 
either internal or external, but they cannot refer to non-XML data (you can't 
have a parameter entity with a notation).
Entity Attributes

External entities can be further classified as either "parsed" or "unparsed". 
Entities which refer to external files that contain XML are called 
"parsed entities;" entities which refer to other types of data, identified by a 
notation, are "unparsed."

The parser inserts the replacement text of a parsed entity into the document 
wherever a reference to that entity occurs. It is an error to insert an entity 
reference to an unparsed entity directly into the flow of an XML document. 
Unparsed entities can only be used as attribute values on elements with ENTITY 
attributes.

Unparsed entities are used most frequently on XML elements that incorporate 
graphics into a document. Consider the following brief document:

<!DOCTYPE doc [
<!ELEMENT doc (para|graphic)+>
<!ELEMENT para (#PCDATA)>
<!ELEMENT graphic EMPTY>
<!ATTLIST graphic
        image ENTITY #REQUIRED
        alt   CDATA  #IMPLIED
>

<!NOTATION GIF SYSTEM "CompuServe Graphics Interchange Format 87a">
<!ENTITY mypicture SYSTEM "normphoto.gif" GIF>
<!ENTITY norm "Norman Walsh">
]>
<doc>
<para>The following element incorporates the image declared as
	"mypicture":</para>
<graphic image="mypicture" alt="A picture of &norm"/>
</doc>

You could also declare the image attribute as CDATA and simply type the filename, 
but the use of an entity offers a useful level of indirection.
Entities in Attribute Values

There is a somewhat subtle distinction between entity attributes and entity 
references in attribute values. An "ordinary" (CDATA) attribute contains text. 
You can put internal entity references in that text, just as you can in any other 
content. An ENTITY attribute can only contain the name of an external, unparsed 
entity. In particular, note that it contains the name of the entity, not a 
reference to the entity.
Entity Expansion

Section 4.4 and Appendix D of the XML Recommendation describe all the details of 
entity expansion. The key points are:

    * Character references are expanded immediately. They behave exactly as if 
      you had typed the literal character.
    * Entity references in the replacement text of other entities are not expanded 
      until the entity being declared is referenced. In other words, this is legal 
      in the internal subset:

      <!ENTITY foobar "&f;bar">
      <!ENTITY f      "foo">

      because the entity reference "&f;" isn't expanded until "&foobar;" is expanded.
      
    * Parsed entities are recognized in the body of your document, where unparsed 
      entities are forbidden. Unparsed entities are allowed in entity attributes, 
      where parsed entities are forbidden.
      
    * Although you can put references to internal entities in attribute values, 
      it is illegal to refer to an external entity in an attribute value.
 */
public class DEntityInternal extends DNode implements Entity { //, ISelfRender {
	private static final long serialVersionUID = 4066512827364288302L;
	
	private boolean m_parameterEntity = false ;
	private String m_replacementText ;
	private String m_xmlEncoding;
    private String m_inputEncoding;
	private String m_xmlVersion;
	
	//
	// Constructor(s)
	//
	public DEntityInternal(final DDocument doc, final String name) {
		super(doc, name);
	}
	
	public DEntityInternal(
		final DDocument doc, final String name, final String replacementText)
	{
		this(doc, name);
		setReplacementText(replacementText) ;
	}	
	
	//
	// Framework
	//
	@Override
	public final short getNodeType() {
		return Node.ENTITY_NODE;
	}
	
	//
	// Satisfy Entity.  Note that this is generally a no-op since internal
	// entities DONT have systemid, publicid, notationname.
	//
    /**
     * The public identifier associated with the entity if specified, and 
     * <code>null</code> otherwise.
     */
    public String getPublicId() {
    	return null ;
    }

    /**
     * The system identifier associated with the entity if specified, and 
     * <code>null</code> otherwise. This may be an absolute URI or not.
     */
    public String getSystemId() {
    	return null ;
    }

    /**
     * For unparsed entities, the name of the notation for the entity. For 
     * parsed entities, this is <code>null</code>.
     */
    public String getNotationName() {
    	return null ;
    }

    /**
     * An attribute specifying the encoding used for this entity at the time 
     * of parsing, when it is an external parsed entity. This is 
     * <code>null</code> if it an entity from the internal subset or if it 
     * is not known.
     * @since DOM Level 3
     */
    public String getInputEncoding() {
    	return m_inputEncoding;
    }

    /**
     * An attribute specifying, as part of the text declaration, the encoding 
     * of this entity, when it is an external parsed entity. This is 
     * <code>null</code> otherwise.
     * @since DOM Level 3
     */
    public String getXmlEncoding() {
    	return m_xmlEncoding;
    }

    /**
     * An attribute specifying, as part of the text declaration, the version 
     * number of this entity, when it is an external parsed entity. This is 
     * <code>null</code> otherwise.
     * @since DOM Level 3
     */
    public String getXmlVersion() {
    	return m_xmlVersion;
    }

    //
    // DSF API
    //
    public String getReplacementText() {
    	return m_replacementText ;
    }
    
    public void setReplacementText(final String text) {
    	if (text == null || text.trim().equals("")) {
    		throw new DsfRuntimeException("Replacement text must not be null or empty") ;
    	}
    	m_replacementText = text ;
    }
    
	/**
	 * 
	 * @param xmlVersion
	 * @throws DOMException
	 */
	public void setXmlVersion(final String xmlVersion) throws DOMException {			
		if(xmlVersion != null && (DDocument.XML_VERSIONS.indexOf(xmlVersion) != -1)){							
			m_xmlVersion = xmlVersion;			
		} else {				
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "unknown xml version " + xmlVersion);
		}		
	}
	
	/**
	 * 
	 * @param value
	 */
	public void setXmlEncoding(final String value) {     
		if (value != null && DDocument.ENCODEINGS.indexOf(value.toLowerCase()) != -1) {
			m_xmlEncoding = value;
		} else {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "unsupported XmlEncoding " + value);
		}
    } 
	
	public void setInputEncoding(final String value) {
		if (value != null && DDocument.ENCODEINGS.indexOf(value.toLowerCase()) != -1) {
			m_inputEncoding = value;
		} else {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "unsupported InputEncoding " + value);
		}	
	}
	
	public boolean isParameterEntity() {
		return m_parameterEntity ;
	}
	
	public void setParameterEntry(final boolean value) {
		m_parameterEntity = value ;
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
		try {
	    	out.write("<!ENTITY ");
			if (this.isParameterEntity()) {
				out.write("% ") ;
			}
				
			out.write(getNodeName());
			out.write(" ") ;
			out.write(escapeText(m_replacementText)) ;
			
			out.write(">");
		}
		catch(IOException e) {
			throw new DsfRuntimeException(e) ;
		}
    }
    
    private String escapeText(final String text) {
// MrP - TODO: need to handle if text has single quotes
    	return "'" + text + "'" ;
    }
}


/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dom.support;

import org.ebayopensource.dsf.dom.DErrUtil;


/**
 * Namespace name verification based on W3c	spec.
 * If the given name doesn't match with W3C spec, 
 * throws DOMException
 */
public class NSNameVerifier {
	
	/** 
	 * Verify the given prefix against the namespaceUr.
	 * It is invoked during element construction time and namespace modification time,
	 * if DsfVerifierConfig.getInstance().isVerifyNaming() is true.	 
	 * @param prefix the prefix to be verified. Must not be null.
	 * @param namespaceUri the namespaceUri to be verified. 
	 * @throws  DOMException.NAMESPACE_ERR if the prefix and/or namespaceUri is/are not valid.
	 */	
	public static void verifyNSPrefix(final String prefix, final String namespaceUri){
		if ( XmlVerifier.isXMLName(prefix, false) == false) {
			DErrUtil.elementCharError(null, prefix, null);
		}		
		if ( prefix.indexOf(DNamespace.NS_NAME_CHAR) != -1){
			DErrUtil.elementNSError(null, prefix, null);
		}
		if (namespaceUri == null) {
			DErrUtil.elementNSError(null, prefix, "null");
		}
		if ((prefix.equals(DNamespace.XML_PREFIX) && !namespaceUri.equals(DNamespace.XML_URI))
        		|| (namespaceUri.equals(DNamespace.XML_URI) && !prefix.equals(DNamespace.XML_PREFIX)) ) 
        {
        	DErrUtil.elementNSError(null, prefix, namespaceUri);
        } else if (prefix.equals(DNamespace.XMLNS_PREFIX) && !namespaceUri.equals(DNamespace.XMLNS_URI)
            || (!prefix.equals(DNamespace.XMLNS_PREFIX) && namespaceUri.equals(DNamespace.XMLNS_URI))) 
        {
        	DErrUtil.elementNSError(null, prefix, namespaceUri);
        }		
	}
		
	/** 
	 * Verify the given localName against the namespaceUr.
	 * It is invoked during element construction time and namespace modification time,
	 * if DsfVerifierConfig.getInstance().isVerifyNaming() is true.	 
	 * @param localName the localName to be verified. Must not be null.
	 * @param namespaceUri the namespaceUri to be verified. 
	 * @throws  DOMException.NAMESPACE_ERR if the localName and/or namespaceUri is/are not valid.
	 */	
	public static void verifyNSLocalName(final String localName, final String namespaceUri) {		
		if (localName.indexOf(DNamespace.NS_NAME_CHAR) != -1) {	        	 
        	DErrUtil.elementNSError(localName, null, null);
        }
		if (namespaceUri != null){				 
			if (localName.equals(DNamespace.XMLNS_PREFIX) 
					&& (namespaceUri == null 
							|| !namespaceUri.equals(DNamespace.XMLNS_URI))
			        || (namespaceUri!=null 
			        		&& namespaceUri.equals(DNamespace.XMLNS_URI) 
			        		&& !localName.equals(DNamespace.XMLNS_PREFIX))) 
			{		    	
				DErrUtil.elementNSError(localName, null, namespaceUri);
			}
		}
	}	
	
	/** 
	 * Verify the given namespaceUriO.
	 * It is invoked during element construction time and namespace modification time,
	 * if DsfVerifierConfig.getInstance().isVerifyNaming() is true.		
	 * @param namespaceUri the namespaceUri to be verified. 
	 * @throws  DOMException.NAMESPACE_ERR if the localName and/or namespaceUri is/are not valid.
	 */	
	public static void verifyNSUriName(final String namespaceUri) {		
		if (namespaceUri == null){				 
			DErrUtil.elementNSError(null, null, "null");
		}
	}	
}

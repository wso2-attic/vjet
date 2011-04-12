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

/**
 * Helper class for namespace related error handle.
 * This class will throw DOMException with different codes. 
 *
 */
public class DErrUtil {
	
	private static final String[]  ELEMENT_ERR = {"",
		"INDEX_SIZE_ERR", //1
		 "DOMSTRING_SIZE_ERR", //2
		 "HIERARCHY_REQUEST_ERR",//          = 3;
		 "WRONG_DOCUMENT_ERR",//            = 4;
		 "Invalid character in element, ",//          = 5;
		 "NO_DATA_ALLOWED_ERR",	//            = 6;
		 "Read only,",//    = 7;
		 "NOT_FOUND_ERR",//                  = 8;
		 "NOT_SUPPORTED_ERR",//              = 9;
		 "INUSE_ATTRIBUTE_ERR",// Introduced in DOM Level 2:
		 "INVALID_STATE_ERR", //              = 11; Introduced in DOM Level 2:
		 "SYNTAX_ERR", //                   = 12; Introduced in DOM Level 2:
		 "Invalid modification in element, ", //       = 13; Introduced in DOM Level 2:
		 "Namespace error in element ", //               = 14; Introduced in DOM Level 2:
		 "INVALID_ACCESS_ERR",//             = 15; Introduced in DOM Level 3:
		 "VALIDATION_ERR", //                 = 16; Introduced in DOM Level 3:
		 "TYPE_MISMATCH_ERR", //              = 17;
	};
	
	private static final String[] NAME_TYPE = {"tag name: ", "prefix: ", "namespaceURI: "};
	
	
	
	public static void elementNSError(String typeName, String prefix, String nsUri){
		elementError(typeName, prefix, nsUri, DOMException.NAMESPACE_ERR);
	}
	public static void elementCharError(String typeName, String prefix, String nsUri){
		elementError(typeName, prefix, nsUri, DOMException.INVALID_CHARACTER_ERR);
	}
	
	public static void elementSetterError_prefix(String prev, String newer){
		StringBuffer tmp = new StringBuffer(50);
		tmp.append(ELEMENT_ERR[DOMException.INVALID_MODIFICATION_ERR]);
		tmp.append(NAME_TYPE[1]);
		if(prev != null){
			tmp.append(prev).append(",");
		}
		if (newer != null){
			tmp.append(newer).append(",");
		}		
		throw new DOMException(DOMException.INVALID_MODIFICATION_ERR, tmp.toString());
	}	
	
	public static void elementError(String typeName, String prefix, String nsUri, short domException){
		StringBuffer tmp = new StringBuffer(50);
		tmp.append(ELEMENT_ERR[domException]);
		if(typeName != null){
			tmp.append(NAME_TYPE[0]).append(typeName).append(",");
		}
		if (prefix != null){
			tmp.append(NAME_TYPE[1]).append(prefix).append(",");
		}
		if (nsUri != null){
			tmp.append(NAME_TYPE[2]).append(nsUri).append(",");
		}
		throw new DOMException(domException, tmp.toString());
	}

}

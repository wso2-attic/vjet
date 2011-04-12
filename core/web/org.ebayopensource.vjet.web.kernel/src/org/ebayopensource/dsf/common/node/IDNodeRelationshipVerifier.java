/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.node;

import java.io.Serializable;

import org.ebayopensource.dsf.dom.DNode;

/**
 * DNodeRelationshipVerifier's are used to enforce constraints associated with:
 * <li> A child that may wish to constrain what parents it accepts
 * <li> A parent that may wish to constrain what children it accepts
 * <li> A parent that may with to constrain what facets it accepts
 * <li> A parent that may with to constrain what facets it attributes
 */
public interface IDNodeRelationshipVerifier extends Serializable, Cloneable {
	/**
	 * This is the default Status that should be returned from the acceptableXXX(...)
	 * methods when the arguments are acceptable.  This is not a requirement
	 * but is more efficient than creating a new Status every time.
	 */
	Status STATUS_OK = new DNodeRelationshipVerifierStatus(true, null) ;
	
	Object clone() throws CloneNotSupportedException;
	
	/**
	 * When a child is added to a parent this method is invoked if there
	 * is a DNodeRelationshipVerifier associated with the parent.  If 
	 * the Status that comes back has isOK() returning false, then this child
	 * is blocked from being added as a child and a DOMException with code=
	 *  HIERARCHY_REQUEST_ERR is thrown with the message from the Status else the add is allowed.
	 * @param parent The parent this child is being added to
	 * @param child The child about to be added
	 * @return A Status indicating if the add should be allowed or not
	 */
	Status acceptableAsChild(DNode parent, DNode child);
	
	/**
	 * When a parent is being set to a child this method is invoked if there
	 * is a DNodeRelationshipVerifier associated with the child.  If 
	 * the Status that comes back has isOK() returning false, then this parent
	 * is blocked from being set as the parent and a DOMException with code=
	 *  HIERARCHY_REQUEST_ERR is thrown with the message from the Status else the set parent is allowed.
	 * @param child The child whose parent is about to be set
	 * @param parent The parent about to set on the child
	 * @return A Status indicating if the parenting should be allowed or not
	 */
	Status acceptableAsParent(DNode child, DNode parent);
	
	/**
	 * When a facet is added to a parent this method is invoked if there
	 * is a DNodeRelationshipVerifier associated with the parent.  If 
	 * the Status that comes back has isOK() returning false, then this facet
	 * is blocked from being added as a facet and a DOMException with code=
	 *  HIERARCHY_REQUEST_ERR is thrown with the message from the Status else the add is allowed.
	 * @param parent The parent the facet is being added to
	 * @param facetName The name of the facet being added to the parent
	 * @return A Status indicating if the facet add should be allowed
	 */
	Status acceptableAsFacet(
		DNode parent, String facetName, DNode facet);
	
	/**
	 * When an attribute is added to a parent this method is invoked if there
	 * is a DNodeRelationshipVerifier associated with the parent.  If 
	 * the Status that comes back has isOK() returning false, then this attribute
	 * is blocked from being added as a child and a DOMException with code=
	 *  HIERARCHY_REQUEST_ERRis thrown with the message from the Status else the add is allowed.
	 * @param parent The parent the attribute is being added to
	 * @param attributeName The name of the attribute being added
	 * @param attribute The attribute being added
	 * @return A Status indicationg if the attribute add should be allowed
	 */
	Status acceptableAsAttribute(
		DNode parent, String attributeName, Object attribute) ;
				
//	/**
//	 * Verify the name of the newNode related to naming conflicts,
//	 * such as nameUnique within parent scope.
//	 * It is called during appendNode operation,
//	 * if DsfVerifierConfig.getInstance().isVerifyNaming() is true.
//	 * @param newNode
//	 * @param parent
//	 * @throws DsfInvalidNameException if the newNode name is not valid for the parent node
//	 * @return
//	 */
//	TODO go to schema
//	Status verifyNewNodeName(final DNode parent, final DNode newNode); 
//		
//	/**
//	 * When an element is added to a document, this method is invoked.
//	 * if enforceUniqueElementId() returns true,
//	 *  adding element w/ duplicate id to a document will throw DOMException.VALIDATION_ERR
//	 * if enforceUniqueElementId() returns false,
//	 * adding element w/ duplicate id to a document will replace the previous element
//	 * @return
//	 */
//	//TODO go to schema
//	public boolean enforceUniqueElementId();
	
	/**
	 * Interface that defines the result of the acceptableXXX(...) methods
	 * execution.  The interface implies immutability and if implementations
	 * only implement this interface they will be immutable.
	 */
	public static interface Status extends Serializable, Cloneable {
		boolean isOk();
		String getErrorMessage();
	}	
}

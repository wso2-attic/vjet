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
import java.util.Iterator;

import org.w3c.dom.Node;

import org.ebayopensource.dsf.common.event.AbortDsfEventProcessingException;
import org.ebayopensource.dsf.common.event.DsfEvent;
import org.ebayopensource.dsf.common.event.IDsfEventStrategy;
import org.ebayopensource.dsf.common.naming.IDsfName;
import org.ebayopensource.dsf.common.naming.IDsfNamingFamily;
import org.ebayopensource.dsf.common.node.visitor.IDNodeVisitor;
import org.ebayopensource.dsf.dom.DNode;

public interface IDsfNode extends Node, Serializable, Cloneable {
	DNode add(DNode newChild) ;
	
	DNodeId getNodeId();
	
	DNode setDsfNamingFamily(IDsfNamingFamily family);
	/**
	 * Don't declare getParent() to avoid BeanIntrospector taking it as
	 * child property. By naming it parent(), JXPath will not incorrectly
	 * traverse back from child to parent.
	 * 
	 * @return DsfComponent  Return the parent of this instance.  The parent
	 * can be null.
	 */
	DNode getDsfParentNode();
	
	/**
	 * Sets the parent for this instance and updates the parents child list.  If 
	 * the current parent is null and the passed in parent is null, the operation
	 * is a no-op.
	 * <br><br>
	 * The parent/child relationship is subjected to the following constraints:
	 * <li> No cycle must directly or indirectly be formed
	 * <li> If the parent and/or child has a RelationshipManager, they are consulted
	 * as to whether or not the parenting can take place.
	 * <li> If Scope/Local Names are in effect the parenting must not violate the
	 * unique local name for any given Scope and the parenting chains set of
	 * Scope names is also unique.
	 * <br><br>
	 * If a previous parent does exist and the passed in parent is null, the
	 * new parent is set and this instance is now parented by that passed in
	 * parent.
	 * <br><br>
	 * If a previous parent does exist and the passed in parent is not null,
	 * the old parent will have this component removed from its child list
	 * and the new parent will add this to its child list.  This instances new
	 * parent will now be the passed in parent. 
	 * @param parent The parent to set as the parent of this instance
	 * @return Returns this instance 
	 */
//	IDsfComponent setDsfParent(IDsfComponent parent);

	/** 
	 * Answer the List of children for this instance.  The List is a smart and active
	 * List and thus modifications to it are live changes.  
	 * component.getDsfChildren() == component.getDsfChildren() will always be
	 * true.
	 * @return List
	 */
	IDNodeList /* IChildNodes */ getDsfChildNodes();
	/**
	 * Answers the number of children.
	 * @return Answers the number of children.  
	 */
	int getLength();
	
	/**
	 * Add the child to the children List.
	 * @param child Child to be added to the children list for this component
	 * @return Answers this instance, not the child that was added.  The child
	 * must not:
	 * <li>Be null
	 * <li>Violate any of the Scope or Naming restrictions
	 * <li>Violate any parent/child verifiers on the parent and/or child
	 * <li>Be the same instance as this instance (can't parent to self)
	 * <li>Cause a direct or indirect circular parent/child dependency
	 * If the child was previously parented it is deparented from its current
	 * parent and this instance becomes its parent.
	 */	
//	void appendChild(DNode child);
//	/**
//	 * Answers the index of the specified child in the child List.  If the child
//	 * is not in the List returns -1.
//	 * @param child The child we want the index position of from the child List
//	 * @return Answers the index of the specified child in the child List.  If 
//	 * the child is not in the List returns -1.
//	 */
//	int dsfIndexOfChild(DNode child);
	/**
	 * Answers the child at the specified index.
	 * @param index position of the child in the children List we want returned
	 * @return Answers the child at the specified index of the children List.
	 * If the index is negative or greater than number of children - 1 an 
	 * IndexOutOfBounds exception is thrown.
	 */
//	DNode getDsfChild(int index);
//	boolean hasChildWithLocalName(String localname);

	/** This will return the facets map.  If there was no facet map,
	 * this function will create a facet map; i.e.  add, remove and has
	 * methods should be preferred.
	 * @return Map
	 */
	IFacetsMap getDsfFacets();

	boolean hasDsfFacets() ;
//	/**
//	 * Returns the Facet for the passed in name.  If no
//	 * Facets have been set or no Facet has been associated with this name or
//	 * the name is null, null is returned.
//	 * @param String 
//	 * @return DsfComponent  Returns the Facet for the passed in name.
//	 */
//	DNode getDsfFacet(String name);
	
//	DNode setDsfFacet(String name, DNode facet);
	
//	/**
//	 * Answers whether or not a Facet has been associated with the passed in name
//	 * @param name Name of the Facet we are checking for
//	 * @return Answer true if a Facet has been associated with the passed in name
//	 * else answers false.  If the name is null, false is always returned.
//	 */
//	boolean hasDsfFacet(String name);
	
//	/**
//	 * Removes the Facet associated with the passed in name.  Facets can not be
//	 * associated with a null name.  Thus if a null name is passed in, we return
//	 * null.
//	 * @param name The name of the Facet to be removed
//	 * @return Answer the Facet removed that matched the passed in name.  If
//	 * no Facets exist or the name is not associated with any Facet, null is
//	 * returned.
//	 */
//	DNode dsfRemoveFacet(String name) ;

	// Attributes
	/**
	 * Answers a smart/live/sequenced Map of attributes for this instance.
	 * @return Answers a smart/live/sequenced Map of attributes for this instance.
	 * <br>comp.getDsfAttributes() == comp.getDsfAttributes() will always be true.
	 * <br>The Map is internally sequenced so keys/values are in the order the
	 * were added.
	 * <br>The attributes will never be null and can be empty.
	 */
	IAttributeMap getDsfAttributes();
//	Object getDsfAttribute(String key) ;
//	DNode setDsfAttribute(String name, Object value) ;
//	/**
//	 * Answers if an attribute has been associated with the passed in key.
//	 * @param key The key used to check if an attributes has been associated with it.
//	 * @return Answer true if an attributes has been associated with the passed
//	 * in key, else answers false.
//	 */	
//	boolean hasDsfAttribute(String key) ;
	
//	/**
//	 * Removes the Attribute associated with the passed in name.  Attributes 
//	 * cannot be associated with a null name.  Thus if a null name is passed in,
//	 * we return null.
//	 * @param name The name of the Attribute to be removed
//	 * @return Answer the Attribute removed that matched the passed in name.  If
//	 * no Attributes exist or the name is not associated with any Attribute, null is
//	 * returned.
//	 */	
//	void dsfRemoveAttribute(String name) ;

//	// These two methods provide different iteration order for 
//	// facets and children
//	IDNodeIterator dsfFacetsAndChildrenItr();
//	IDNodeIterator dsfChildrenAndFacetsItr();
	
	// events
	/**
	 * Removes the listener from the set of listeners for this component.  Answers
	 * true if the listener was previously added else answers false.
	 * @param listener Listener to remove from the set of listeners for this component
	 * @return Answers true if the passed in listener was removed.
	 */
//	boolean dsfRemoveEventListener(IDsfEventListener listener);
//	DNode addDsfListener(IDsfEventListener listener);
	boolean hasDsfListeners() ;
	IDsfEventListeners getDsfEventListeners() ;
	
	DNode dsfBroadcast(DsfEvent<?, ?> event) throws AbortDsfEventProcessingException;
	DNode dsfBroadcast(DsfEvent<?, ?> event, IDsfEventStrategy strategy) 
		throws AbortDsfEventProcessingException;
	
//	// binding
//	/**
//	 * returns existing binding for the property, a runtime exception can be 
//	 * thrown if the property is an non-bindable intrinsic property
//	 */
//	IDsfBinding setDsfValueBinding(String propertyName, IDsfBinding binding);
//	
//	// value tracking by ValueManager
//	IValueManager getDsfPropertyValueManager(String propertyName);
//	
//	// attach data source
//	IValueDataSource dsfAttachDataSource(String propertyName, IValueDataSource delegator)
//		throws IllegalDataSourceDelegatorException;
		
	// traversal
	DNode dsfAccept(IDNodeVisitor visitor);
	IDsfStrategies getDsfStrategies() ;
	boolean hasDsfStrategies() ;
	
//	IDNodeHandlingStrategy setDsfStrategy(
//		PhaseId phaseId, IDNodeHandlingStrategy strategy);
//	/**
//	 * Removes the strategy associated with the passed in PhaseId.  If there was
//	 * a strategy associated, it is returned, else null is returned.
//	 * @param phaseId The PhaseId to identify which (if any) strategy to remove.
//	 * @return The strategy associated with the passed in PhaseId
//	 */
//	IDNodeHandlingStrategy dsfRemoveStrategy(PhaseId phaseId);
//	IDNodeHandlingStrategy getDsfStrategy(PhaseId phaseId);

	//clonable
	/**
	 * All DSF Components support cloning.  Cloning is an essential abstraction
	 * that must be supported by all V4 provided Components and any specialization
	 * based on them as well.
	 */
	Object clone() throws CloneNotSupportedException;
	
	/**
	 * Same as clone() but will throw DsfRuntimeException if not cloneable
	 */
	Object cloned() ;
	
	// namespace
//	Namespace getDsfNamespace();
//	Namespace setDsfNamespace(Namespace namespace);
//	Namespace getDsfDefaultNamespace();
//	IDsfComponent dsfDeclareNamespace(Namespace namespace);
	
	// DSF naming
	IDsfName getDsfName();
	/**
	 * Answers whether this instance has a DsfName or not.
	 * @return Answers whether this instance has a DsfName or not.
	 */
	boolean hasDsfName();

	/** This indicates whether local names should be exported to the closest
	 * scoped component.  If the current component has a scope name,
	 * then this has no effect.
	 * @return
	 */
	boolean isDsfExportingLocalNames();
	/** indicates whether local names should be exported.
	 * @param shouldExport
	 */
	DNode setDsfExportingLocalNames(final boolean exportingLocalNames);

	IDsfNamingFamily getDsfNamingFamily();
	
	// DSF component relationship checker
	IDNodeRelationshipVerifier getDsfRelationshipVerifier();
	DNode setDsfRelationshipVerifier(IDNodeRelationshipVerifier v) ;
	
	// Misc
	DNode dsfDiscard() ;

	Iterator<DNode> getChildNodesIterator();
	Iterator<DNode> getChildrenAndFacetsItr();
	Iterator<DNode> getFacetsAndChildrenItr();
}

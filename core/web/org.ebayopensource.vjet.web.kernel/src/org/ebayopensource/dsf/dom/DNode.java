/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/

package org.ebayopensource.dsf.dom;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import javax.xml.xpath.XPathExpression;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

import org.ebayopensource.dsf.common.DsfVerifierConfig;
import org.ebayopensource.dsf.common.binding.IValueBinding;
import org.ebayopensource.dsf.common.binding.SimpleValueBinding;
import org.ebayopensource.dsf.common.context.DsfCtx;
import org.ebayopensource.dsf.common.event.AbortDsfEventProcessingException;
import org.ebayopensource.dsf.common.event.DsfEvent;
import org.ebayopensource.dsf.common.event.DsfPhaseEvent;
import org.ebayopensource.dsf.common.event.IDsfEventListener;
import org.ebayopensource.dsf.common.event.IDsfEventStrategy;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.naming.IDsfName;
import org.ebayopensource.dsf.common.naming.IDsfNamingFamily;
import org.ebayopensource.dsf.common.naming.NameChecker;
import org.ebayopensource.dsf.common.node.DNodeId;
import org.ebayopensource.dsf.common.node.DNodeName;
import org.ebayopensource.dsf.common.node.IAttributeMap;
import org.ebayopensource.dsf.common.node.IDNodeList;
import org.ebayopensource.dsf.common.node.IDNodeRelationshipVerifier;
import org.ebayopensource.dsf.common.node.IDsfEventListeners;
import org.ebayopensource.dsf.common.node.IDsfNode;
import org.ebayopensource.dsf.common.node.IDsfStrategies;
import org.ebayopensource.dsf.common.node.IFacetsMap;
import org.ebayopensource.dsf.common.node.Initializer;
import org.ebayopensource.dsf.common.node.visitor.AbortDNodeTraversalException;
import org.ebayopensource.dsf.common.node.visitor.DNodeVisitStatus;
import org.ebayopensource.dsf.common.node.visitor.DefaultDNodeHandlingStrategy;
import org.ebayopensource.dsf.common.node.visitor.DefaultDNodeVisitor;
import org.ebayopensource.dsf.common.node.visitor.DepthFirstDNodeTraversal;
import org.ebayopensource.dsf.common.node.visitor.IDNodeHandlingStrategy;
import org.ebayopensource.dsf.common.node.visitor.IDNodeVisitor;
import org.ebayopensource.dsf.common.phase.PhaseDriver;
import org.ebayopensource.dsf.common.phase.PhaseId;
import org.ebayopensource.dsf.common.trace.TraceCtx;
import org.ebayopensource.dsf.dom.support.DNamespace;
import org.ebayopensource.dsf.dom.support.DsfDomLevelNotSupportedException;
import org.ebayopensource.dsf.dom.support.DsfDomNotSupportedRuntimeException;
import org.ebayopensource.dsf.dom.support.Jif;
import org.ebayopensource.kernel.stage.IStage;
import org.ebayopensource.dsf.common.Z;

public abstract class DNode
	implements 
	/* DOM */ Node, NodeList,
	/* DSF */ IDsfNode /*, IPropertyHolder */
{
	protected String m_nodeName;
	protected String m_nodeValue;
	private DNode m_parentNode;
	protected String m_prefix;
	protected String m_localName;	
	protected DNamespace m_dsfNamespace;


// MrPperf - we need to make this lazy creation -- Note that many nodes
// don't have any children.  Also DAttr is a DNode, this means that every DAttr
// we create also get a useless child list!!!
	protected DNodeList m_childNodes ;
	protected transient DDocument m_ownerDocument;
	protected FacetsMap m_facets ;
	
	private transient TraceCtx m_traceCtx ;
	private DNodeId m_nodeId ;
	private boolean m_exportingLocalNames = true;
	private IDNodeRelationshipVerifier m_dsfRelationshipVerifier ;
	//private IDsfNamingVerifier m_dsfNamingVerifier; 
	//naming verifier doesn't need to be set at node level. 
	
	
	// Keep these uninitialized to keep down on Object overhead
	private IDsfNamingFamily m_dsfNamingFamily ;
	private IDsfName m_dsfName ;
	protected transient Map<String, Object> m_userData ;
	private transient IDsfStrategies m_strategies ;
	protected transient IDsfEventListeners m_listeners ;
	private transient PropertyDescriptorMap m_propertyDescriptors ;
	private transient Map<String, Field> m_valueBindingMap ;
	
	private static WeakHashMap<Class<?>, Map<String, Field>> s_valueBindings
		= new WeakHashMap<Class<?>, Map<String, Field>>();

	private static WeakHashMap<Class<?>, PropertyDescriptorMap> s_descriptors 
		= new WeakHashMap<Class<?>, PropertyDescriptorMap>();
	
	//11-20-2008,  HtmlWriter throws stack overflow errors when dom tree goes beyond 4800 nodes depth
	//should be schema,
	//private static final int DOM_MAX_DEPTH = 2500;  
	//private static final int DOM_MAX_NODES = 25000;
	
	//
	// Constructor(s)
	//
	DNode() {
		// so subtypes can create without a nodeName
		this(null, null);
	}

	DNode(DDocument document) {
		this(document, null);
	}

	DNode(DDocument document, String nodeName) {
		// Must do checks BEFORE we assign any members since the check could
		// throw and we don't want assignments done even though the check
		// failed.  If the check fails you don't get an instance back but keep
		// this first so we don't do any expensive work
		DsfCtx.ctx().getContainer().checkNodeInstantiation(this);
		
		m_nodeName = nodeName;
		m_ownerDocument = document;
	}

	//
	// Satisfy Node
	//
	/**
	 * The name of this node, depending on its type; see the table above.
	 */
	public String getNodeName() {
		return m_nodeName;
	}

	/**
	 * The value of this node, depending on its type; see the table above. 
	 * When it is defined to be <code>null</code>, setting it has no effect, 
	 * including if the node is read-only.
	 * @exception DOMException
	 *   DOMSTRING_SIZE_ERR: Raised when it would return more characters than 
	 *   fit in a <code>DOMString</code> variable on the implementation 
	 *   platform.
	 */
	public String getNodeValue() throws DOMException {
		return m_nodeValue;
	}

	/**
	 * The value of this node, depending on its type; see the table above. 
	 * When it is defined to be <code>null</code>, setting it has no effect, 
	 * including if the node is read-only.
	 * @exception DOMException
	 *   NO_MODIFICATION_ALLOWED_ERR: Raised when the node is readonly and if 
	 *   it is not defined to be <code>null</code>.
	 */
	public void setNodeValue(final String nodeValue) throws DOMException {
		m_nodeValue = nodeValue;
	}

	/**
	 * A code representing the type of the underlying object, as defined above.
	 */
	public abstract short getNodeType();

	/**
	 * The parent of this node. All nodes, except <code>Attr</code>, 
	 * <code>Document</code>, <code>DocumentFragment</code>, 
	 * <code>Entity</code>, and <code>Notation</code> may have a parent. 
	 * However, if a node has just been created and not yet added to the 
	 * tree, or if it has been removed from the tree, this is 
	 * <code>null</code>. 
	 */
	public Node getParentNode() {
		return m_parentNode;
	}

	/**
	 * A <code>NodeList</code> that contains all children of this node. If 
	 * there are no children, this is a <code>NodeList</code> containing no 
	 * nodes.
	 */
	public NodeList getChildNodes() {
		if (m_childNodes == null) {
			m_childNodes = createChildNodes() ;
		}
		return m_childNodes;
	}
	
	public NodeList getChildNodes(final int initialSize) {
		if (m_childNodes == null) {
			if (initialSize < 0) {
				chuck("The initialSize must not be negative") ;
			}
			createChildNodes(this, initialSize);
		}
		
		return m_childNodes ;		
	}
	
	public static final Iterator<DNode> EMPTY_ITERATOR = new Iterator<DNode>() {
		public boolean hasNext() {
			return false;
		}
		public DNode next() {
			/* public class Test {
			 *		public static void main(String[] args) {
			 * 			final List<String> l = new ArrayList<String>();
			 *			l.iterator().next();
			 *		}
			 * }
			 * causes:
			 *
			 * Exception in thread "main" java.util.NoSuchElementException
			 * at java.util.AbstractList$Itr.next(AbstractList.java:442)
			 * at org.ebayopensource.dsf.dom.Test.main(Test.java:9)
			 */
			throw new DsfRuntimeException("no elements left");
		}
		public void remove() {
			throw new DsfRuntimeException("not implemented");
		}
	};

	public Iterator<DNode> getChildNodesIterator() {
		if (hasChildNodes()) {
			return m_childNodes.iterator();
		}
		return EMPTY_ITERATOR;
	}

	public Iterator<DNode> getChildrenAndFacetsItr() {
		final Iterator<DNode> itr = getItrIfFacetOrChildIsEmpty();
		if (itr != null) {
			return itr;
		}
		// there is both facets and children
		final List<DNode> combined =
			new ArrayList<DNode>(m_facets.size()+m_childNodes.size());
		combined.addAll(m_childNodes);
		combined.addAll(m_facets.values());
		return combined.iterator();
	}
	public Iterator<DNode> getFacetsAndChildrenItr() {
		final Iterator<DNode> itr = getItrIfFacetOrChildIsEmpty();
		if (itr != null) {
			return itr;
		}
// MrP - perf: very inefficient if nodes have both facets and children
		// there is both facets and children
		final List<DNode> combined =
			new ArrayList<DNode>(m_facets.size()+m_childNodes.size());
		combined.addAll(m_facets.values());
		combined.addAll(m_childNodes);
		return combined.iterator();		
	}
	private Iterator<DNode> getItrIfFacetOrChildIsEmpty() {
		if (!hasDsfFacets()) {
// MrPperf - handle m_owner.m_childNodes being null
			if (hasChildNodes()) {
				return m_childNodes.iterator();
			}
			return EMPTY_ITERATOR;
		}

// MrPperf - handle m_owner.m_childNodes being null
		if (!hasChildNodes()) {
			return m_facets.values().iterator();
		}
		return null;
	}
	
	/**
	 * The first child of this node. If there is no such node, this returns 
	 * <code>null</code>.
	 */
	public Node getFirstChild() {
// MrPperf - check for null children
		if (m_childNodes == null) {
			return null ;
		}
		
		if (m_childNodes.isEmpty()) {
			return null;
		}

		return m_childNodes.get(0);
	}

	/**
	 * The last child of this node. If there is no such node, this returns 
	 * <code>null</code>.
	 */
	public Node getLastChild() {
// MrPperf - handle null childNodes
		if (m_childNodes == null) {
			return null ;
		}
		
		if (m_childNodes.isEmpty()) {
			return null;
		}

		return m_childNodes.get(m_childNodes.getLength() - 1);
	}

	/**
	 * The node immediately preceding this node. If there is no such node, 
	 * this returns <code>null</code>.
	 */
	public Node getPreviousSibling() {
		if (m_parentNode == null) {
			return null;
		}
// MrPperf - handle null parentNode childNodes
		if (m_parentNode.m_childNodes == null) {
			return null ;
		}
		
		final int index = m_parentNode.m_childNodes.indexOf(this);
		if (index <= 0) {
			return null;
		}
		final DNode node = m_parentNode.m_childNodes.get(index - 1);
		return node;
	}

	/**
	 * The node immediately following this node. If there is no such node, 
	 * this returns <code>null</code>.
	 */
	public Node getNextSibling() {
		if (m_parentNode == null) {
			return null;
		}
// MrPperf - handle null parentNodes childNodes
		if (m_parentNode.m_childNodes == null) {
			return null ;
		}
		
		final int index = m_parentNode.m_childNodes.indexOf(this);
		if (index < 0 || index+1 >= m_parentNode.m_childNodes.size()) {
			return null;
		}
		final DNode node = m_parentNode.m_childNodes.get(index + 1);
		return node;
	}

	/**
	 * A <code>NamedNodeMap</code> containing the attributes of this node (if 
	 * it is an <code>Element</code>) or <code>null</code> otherwise.
	 */
	public NamedNodeMap getAttributes() {
		return null;
	}

	/**
	 * The <code>Document</code> object associated with this node. This is 
	 * also the <code>Document</code> object used to create new nodes. When 
	 * this node is a <code>Document</code> or a <code>DocumentType</code> 
	 * which is not used with any <code>Document</code> yet, this is 
	 * <code>null</code>.
	 * 
	 * @since DOM Level 2
	 */
	public Document getOwnerDocument() {
		return getDsfOwnerDocument();
	}
	public DDocument getDsfOwnerDocument() {
		return m_ownerDocument;		
	}

	/**
	 * Inserts the node <code>newChild</code> before the existing child node 
	 * <code>refChild</code>. If <code>refChild</code> is <code>null</code>, 
	 * insert <code>newChild</code> at the end of the list of children.
	 * <br>If <code>newChild</code> is a <code>DocumentFragment</code> object, 
	 * all of its children are inserted, in the same order, before 
	 * <code>refChild</code>. If the <code>newChild</code> is already in the 
	 * tree, it is first removed.
	 * <p ><b>Note:</b>  Inserting a node before itself is implementation 
	 * dependent. 
	 * @param newChild The node to insert.
	 * @param refChild The reference node, i.e., the node before which the 
	 *   new node must be inserted.
	 * @return The node being inserted.
	 * @exception DOMException
	 *   HIERARCHY_REQUEST_ERR: Raised if this node is of a type that does not 
	 *   allow children of the type of the <code>newChild</code> node, or if 
	 *   the node to insert is one of this node's ancestors or this node 
	 *   itself, or if this node is of type <code>Document</code> and the 
	 *   DOM application attempts to insert a second 
	 *   <code>DocumentType</code> or <code>Element</code> node.
	 *   <br>WRONG_DOCUMENT_ERR: Raised if <code>newChild</code> was created 
	 *   from a different document than the one that created this node.
	 *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly or 
	 *   if the parent of the node being inserted is readonly.
	 *   <br>NOT_FOUND_ERR: Raised if <code>refChild</code> is not a child of 
	 *   this node.
	 *   <br>NOT_SUPPORTED_ERR: if this node is of type <code>Document</code>, 
	 *   this exception might be raised if the DOM implementation doesn't 
	 *   support the insertion of a <code>DocumentType</code> or 
	 *   <code>Element</code> node.
	 * 
	 * @since DOM Level 3
	 */
	public Node insertBefore(
		final Node newChild, final Node refChild) throws DOMException
	{
		if (newChild instanceof DocumentFragment) {
			final DDocumentFragment fragment = (DDocumentFragment)newChild;
			while (fragment.getLength() > 0) {
// MrPperf - handle m_childNodes being null
				final DNode node = (fragment.m_childNodes == null) 
					? null : fragment.m_childNodes.get(0);
				insertBeforeInternal(node, refChild);
			}
		} 
		else {
			insertBeforeInternal(newChild, refChild);
		}
		
		return newChild;
	}
	
	private void insertBeforeInternal(final Node newChild, final Node refChild)
		throws DOMException
	{
		if (refChild == null) {
			// in general appendChild is simpler, faster and less error prone.
			appendChild(newChild);
		}
		
		if (newChild == refChild) {
			return ; // nothing to do
		}
		
		if (newChild.getParentNode() == this) {
// MrPperf - handle m_childNodes being null
			final int alreadyInIndex = (m_childNodes == null)
				? -1 : m_childNodes.indexOf(newChild);
			if (alreadyInIndex >= 0) {
				// optimzied remove, should not need to check or de-parent
				m_childNodes.remove(alreadyInIndex);
			} else {
				throw new DOMException(DOMException.VALIDATION_ERR,
					"child has this parent, but cannot find it as a child");
			}
		} 
		else {
			// optimized - to only check for truly new children
			checkChildForAdd(newChild);
			if (newChild.getParentNode() != null) {
				newChild.getParentNode().removeChild(newChild);
			}
		}
		
// MrPperf -- force childNodes creation at this point
		getChildNodes() ;
		if (refChild == null) {
			m_childNodes.privateAdd((DNode) newChild);
		} else {
			final int index = m_childNodes.indexOf(refChild);
			if (index < 0) {
				throw new DOMException(DOMException.NOT_FOUND_ERR,
					"reference node not found");
			}
			m_childNodes.add(index, (DNode) newChild);
		}
		postChildAdd((DNode) newChild);
	}
	

	/**
	 * Replaces the child node <code>oldChild</code> with <code>newChild</code>
	 *  in the list of children, and returns the <code>oldChild</code> node.
	 * <br>If <code>newChild</code> is a <code>DocumentFragment</code> object, 
	 * <code>oldChild</code> is replaced by all of the 
	 * <code>DocumentFragment</code> children, which are inserted in the 
	 * same order. If the <code>newChild</code> is already in the tree, it 
	 * is first removed.
	 * <p ><b>Note:</b>  Replacing a node with itself is implementation 
	 * dependent. 
	 * @param newChild The new node to put in the child list.
	 * @param oldChild The node being replaced in the list.
	 * @return The node replaced.
	 * @exception DOMException
	 *   HIERARCHY_REQUEST_ERR: Raised if this node is of a type that does not 
	 *   allow children of the type of the <code>newChild</code> node, or if 
	 *   the node to put in is one of this node's ancestors or this node 
	 *   itself, or if this node is of type <code>Document</code> and the 
	 *   result of the replacement operation would add a second 
	 *   <code>DocumentType</code> or <code>Element</code> on the 
	 *   <code>Document</code> node.
	 *   <br>WRONG_DOCUMENT_ERR: Raised if <code>newChild</code> was created 
	 *   from a different document than the one that created this node.
	 *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node or the parent of 
	 *   the new node is readonly.
	 *   <br>NOT_FOUND_ERR: Raised if <code>oldChild</code> is not a child of 
	 *   this node.
	 *   <br>NOT_SUPPORTED_ERR: if this node is of type <code>Document</code>, 
	 *   this exception might be raised if the DOM implementation doesn't 
	 *   support the replacement of the <code>DocumentType</code> child or 
	 *   <code>Element</code> child.
	 * 
	 * @since DOM Level 3
	 */
	public Node replaceChild(
		final Node newChild, final Node oldChild) throws DOMException
	{
		if (newChild == null) {
			throw new DOMException(DOMException.VALIDATION_ERR,
				"new child cannot be null");			
		}
		
// MrPperf - handle null m_childNodes
		final int index = (m_childNodes == null)
			? -1 : m_childNodes.indexOf(oldChild);
		if (index < 0) {
			throw new DOMException(DOMException.NOT_FOUND_ERR,
				"reference node not found");
		}
		
		removeChild(oldChild);
		// we want to make sure to get the checking from append/insert.
// MrPperf - need to materialize children
		getChildNodes() ;
		
		if (index >= m_childNodes.size()) {
			appendChild(newChild);
		} 
		else {
			insertBefore(newChild, m_childNodes.get(index));
		}
		
		return oldChild;
	}
	/**
	 * Dsf version of replaceChild, 
	 * detach oldChild from its ownerdocument. after oldChild is replaced by newChild.	
	 * @param newChild
	 * @param oldChild
	 * @return
	 * @see replaceChild API
	 * @throws DOMException
	 */
	public Node dsfReplaceChild(
			final DNode newChild, final DNode oldChild) throws DOMException
		{
		DNode result = (DNode)replaceChild(newChild, oldChild);
		result.dsfDetachFromOwnerDocument();
		return result;
	}
	/**
	 * Removes the child node indicated by <code>oldChild</code> from the list 
	 * of children, and returns it.
	 * @param oldChild The node being removed.
	 * @return The node removed.
	 * @exception DOMException
	 *   NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
	 *   <br>NOT_FOUND_ERR: Raised if <code>oldChild</code> is not a child of 
	 *   this node.
	 *   <br>NOT_SUPPORTED_ERR: if this node is of type <code>Document</code>, 
	 *   this exception might be raised if the DOM implementation doesn't 
	 *   support the removal of the <code>DocumentType</code> child or the 
	 *   <code>Element</code> child.
	 * 
	 * @since DOM Level 3
	 */
	public Node removeChild(final Node oldChild) throws DOMException {
		if (oldChild == null) {
			throw new DOMException(DOMException.NOT_FOUND_ERR,
				"null node is not a child of this node");
		}
		final DNode childx = (DNode) oldChild;
		if (childx.m_parentNode != this) {
			throw new DOMException(DOMException.NOT_FOUND_ERR,
				"node is not a child of this node");
		}
// MrPperf - at this point we force childNodes being there
		getChildNodes() ;
		m_childNodes.privateRemove(childx);		
		childx.m_parentNode = null;	
		if (m_ownerDocument != null) {
			m_ownerDocument.removeIdentifiedElement((DNode) oldChild);
		}
		return childx;
	}
	
	/**
	 * Dsf version of removeChild, 
	 * detach oldChild from its ownerdocument. after oldChild is removed from the DOM
	 * @param oldChild
	 * @return
	 * @see removeChild API
	 * @throws DOMException
	 */
	public Node dsfRemoveChild(final DNode oldChild) throws DOMException {
		removeChild(oldChild);
		oldChild.dsfDetachFromOwnerDocument();	
		return oldChild;
	}

	/**
	 * Adds the node <code>newChild</code> to the end of the list of children 
	 * of this node. If the <code>newChild</code> is already in the tree, it 
	 * is first removed.  The newChild must not be null and if it has an
	 * owning document, it must be the same (==) document as the parent of 
	 * this node else an exception is thrown.
	 * @param newChild The node to add.If it is a 
	 *   <code>DocumentFragment</code> object, the entire contents of the 
	 *   document fragment are moved into the child list of this node
	 * @return The node added.
	 * @exception DOMException
	 *   HIERARCHY_REQUEST_ERR: Raised if this node is of a type that does not 
	 *   allow children of the type of the <code>newChild</code> node, or if 
	 *   the node to append is one of this node's ancestors or this node 
	 *   itself, or if this node is of type <code>Document</code> and the 
	 *   DOM application attempts to append a second 
	 *   <code>DocumentType</code> or <code>Element</code> node.
	 *   <br>WRONG_DOCUMENT_ERR: Raised if <code>newChild</code> was created 
	 *   from a different document than the one that created this node.
	 *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly or 
	 *   if the previous parent of the node being inserted is readonly.
	 *   <br>NOT_SUPPORTED_ERR: if the <code>newChild</code> node is a child 
	 *   of the <code>Document</code> node, this exception might be raised 
	 *   if the DOM implementation doesn't support the removal of the 
	 *   <code>DocumentType</code> child or <code>Element</code> child.
	 *
	 * @since DOM Level 3
	 */
	public Node appendChild(final Node newChild) throws DOMException {
		if (newChild == null) {
			throw new DOMException(DOMException.VALIDATION_ERR,"Child is null");
		}
		
		// If we are instrumenting code for, then run the append-time instrumenters 
		// that are registered.
		if(getTraceCtx().haveInstrumenter()) {
			m_traceCtx.getInstrumenter().runAppendInstrumenters(this, newChild);
		}
		
		if (newChild instanceof DocumentFragment) {
			final DDocumentFragment fragment = (DDocumentFragment)newChild;
			// This loop on getLength() > 0 works because the appendChildNonDocFragment(...)
			// will actually de-parent the child from the fragment.
			while (fragment.getLength() > 0) {
				final DNode node = fragment.getDsfChildNodes().get(0);		
				appendChildNonDocFragment(node);
			}
		} 
		else {
			appendChildNonDocFragment(newChild);
		}
		
		return newChild;
	}
	
	/**
	 * Shorthand of appendChild(Node) but takes a DNode arg.
	 * Returns "this" DNode vs. the added child - this is nice for
	 * cascade style programming. 
	 * <code>
	 * node.add(anotherNode).addRaw("&nbsp;") ;
	 * vs.
	 * node.add(anotherNode);
	 * node.addRaw("&nbsp;");
	 * @param newChild node to be appended.  Throws DOMException if value is null.
	 * @return this
	 * @throws DOMException
	 */
	public DNode add(final DNode newChild) throws DOMException {
		appendChild(newChild) ;
		return this ;
	}
	
	/**
	 * Shorthand for add(new DText(value))
	 * <br><code>
	 * ex: node.add("Address")
	 * </code>
	 * @param value to be added as a DText node.  Throws DOMException if value is null.
	 * @return this
	 * @throws DOMException
	 */
	public DNode add(final String value) throws DOMException {
		final DText text = new DText(value) ;
		return add(text) ;
	}
	
	/**
	 * Shorthand for add(new DRawString(value))
	 * <br>
	 * The value will be emitted as is without any escaping.
	 * <br>
	 * ex: node.addRaw("&npbsp;")
	 * @param  value to be added without any escaping. Throws DOMException if value is null.
	 * @return this
	 * @throws DOMException
	 */
	public DNode addRaw(final String value) throws DOMException {
		final DRawString raw = new DRawString(value) ;
		return add(raw) ;
	}
	
	protected Node appendChildNonDocFragment(
		final Node newChild) throws DOMException
	{
		checkChildForAdd(newChild);
		final DNode child = (DNode)newChild ;
		eraseParent(child, (DNode)newChild.getParentNode());
// MrPPerf - need to force creation of child nodes
		
		// MUST USE privateAdd(DNode) else you get stack overflow because the
		// DNodeList::add(DNode) will call back to its owning DNode's add(DNode) !
		((DNodeList)getChildNodes()).privateAdd(child);
		postChildAdd(child);
		return newChild;
	}

	/**
	 * Returns whether this node has any children.
	 * @return Returns <code>true</code> if this node has any children, 
	 *   <code>false</code> otherwise.
	 */
	public boolean hasChildNodes() {
// MrPperf - check if children exist first
		return (m_childNodes == null) ? false : m_childNodes.size() > 0;
	}

	/**
	 * Returns a duplicate of this node, i.e., serves as a generic copy 
	 * constructor for nodes. The duplicate node has no parent (
	 * <code>parentNode</code> is <code>null</code>) and no user data. User 
	 * data associated to the imported node is not carried over. However, if 
	 * any <code>UserDataHandlers</code> has been specified along with the 
	 * associated data these handlers will be called with the appropriate 
	 * parameters before this method returns.
	 * <br>Cloning an <code>Element</code> copies all attributes and their 
	 * values, including those generated by the XML processor to represent 
	 * defaulted attributes, but this method does not copy any children it 
	 * contains unless it is a deep clone. This includes text contained in 
	 * an the <code>Element</code> since the text is contained in a child 
	 * <code>Text</code> node. Cloning an <code>Attr</code> directly, as 
	 * opposed to be cloned as part of an <code>Element</code> cloning 
	 * operation, returns a specified attribute (<code>specified</code> is 
	 * <code>true</code>). Cloning an <code>Attr</code> always clones its 
	 * children, since they represent its value, no matter whether this is a 
	 * deep clone or not. Cloning an <code>EntityReference</code> 
	 * automatically constructs its subtree if a corresponding 
	 * <code>Entity</code> is available, no matter whether this is a deep 
	 * clone or not. Cloning any other type of node simply returns a copy of 
	 * this node.
	 * <br>Note that cloning an immutable subtree results in a mutable copy, 
	 * but the children of an <code>EntityReference</code> clone are readonly
	 * . In addition, clones of unspecified <code>Attr</code> nodes are 
	 * specified. And, cloning <code>Document</code>, 
	 * <code>DocumentType</code>, <code>Entity</code>, and 
	 * <code>Notation</code> nodes is implementation dependent.
	 * @param deep If <code>true</code>, recursively clone the subtree under 
	 *   the specified node; if <code>false</code>, clone only the node 
	 *   itself (and its attributes, if it is an <code>Element</code>).
	 * @return The duplicate node.
	 */
	public Node cloneNode(final boolean deep) {
		try {
			return cloneInternal(deep);
		} 
		catch (CloneNotSupportedException e) {
			throw new DsfRuntimeException("unable to clone "+e.getMessage(),e);
		}
	}

	/**
	 *  Puts all <code>Text</code> nodes in the full depth of the sub-tree 
	 * underneath this <code>Node</code>, including attribute nodes, into a 
	 * "normal" form where only structure (e.g., elements, comments, 
	 * processing instructions, CDATA sections, and entity references) 
	 * separates <code>Text</code> nodes, i.e., there are neither adjacent 
	 * <code>Text</code> nodes nor empty <code>Text</code> nodes. This can 
	 * be used to ensure that the DOM view of a document is the same as if 
	 * it were saved and re-loaded, and is useful when operations (such as 
	 * XPointer [<a href='http://www.w3.org/TR/2003/REC-xptr-framework-20030325/'>XPointer</a>]
	 *  lookups) that depend on a particular document tree structure are to 
	 * be used. If the parameter "normalize-characters" of the 
	 * <code>DOMConfiguration</code> object attached to the 
	 * <code>Node.ownerDocument</code> is <code>true</code>, this method 
	 * will also fully normalize the characters of the <code>Text</code> 
	 * nodes. 
	 * <p ><b>Note:</b> In cases where the document contains 
	 * <code>CDATASections</code>, the normalize operation alone may not be 
	 * sufficient, since XPointers do not differentiate between 
	 * <code>Text</code> nodes and <code>CDATASection</code> nodes.
	 * 
	 * @since DOM Level 3
	 */
	public void normalize() {
		throw new DsfDomLevelNotSupportedException(3);
	}

	/**
	 *  Tests whether the DOM implementation implements a specific feature and 
	 * that feature is supported by this node, as specified in . 
	 * @param feature  The name of the feature to test. 
	 * @param version  This is the version number of the feature to test. 
	 * @return Returns <code>true</code> if the specified feature is 
	 *   supported on this node, <code>false</code> otherwise.
	 *
	 * @since DOM Level 2
	 */
	public boolean isSupported(final String feature, final String version) {
		throw new DsfDomNotSupportedRuntimeException(
			"isSupported(feature, version)");
	}

	/**
	 * The namespace URI of this node, or <code>null</code> if it is 
	 * unspecified (see ).
	 * <br>This is not a computed value that is the result of a namespace 
	 * lookup based on an examination of the namespace declarations in 
	 * scope. It is merely the namespace URI given at creation time.
	 * <br>For nodes of any type other than <code>ELEMENT_NODE</code> and 
	 * <code>ATTRIBUTE_NODE</code> and nodes created with a DOM Level 1 
	 * method, such as <code>Document.createElement()</code>, this is always 
	 * <code>null</code>.
	 * <p ><b>Note:</b> Per the <em>Namespaces in XML</em> Specification [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
	 *  an attribute does not inherit its namespace from the element it is 
	 * attached to. If an attribute is not explicitly given a namespace, it 
	 * simply has no namespace.
	 *
	 * @since DOM Level 2
	 */
	public String getNamespaceURI() {		
		return m_dsfNamespace!=null?m_dsfNamespace.getNamespaceKey():null; //supported for DElement onlyo
	}

	/**
	 * The namespace prefix of this node, or <code>null</code> if it is 
	 * unspecified. When it is defined to be <code>null</code>, setting it 
	 * has no effect, including if the node is read-only.
	 * <br>Note that setting this attribute, when permitted, changes the 
	 * <code>nodeName</code> attribute, which holds the qualified name, as 
	 * well as the <code>tagName</code> and <code>name</code> attributes of 
	 * the <code>Element</code> and <code>Attr</code> interfaces, when 
	 * applicable.
	 * <br>Setting the prefix to <code>null</code> makes it unspecified, 
	 * setting it to an empty string is implementation dependent.
	 * <br>Note also that changing the prefix of an attribute that is known to 
	 * have a default value, does not make a new attribute with the default 
	 * value and the original prefix appear, since the 
	 * <code>namespaceURI</code> and <code>localName</code> do not change.
	 * <br>For nodes of any type other than <code>ELEMENT_NODE</code> 
	 * and <code>ATTRIBUTE_NODE</code> and nodes created with a DOM Level 1 
	 * method, such as <code>createElement</code> from the 
	 * <code>Document</code> interface, this is always <code>null</code>.
	 *
	 * http://java.sun.com/j2se/1.4.2/docs/api/org/w3c/dom/Node.html#getPrefix()
	 * @since DOM Level 2
	 */
	public String getPrefix() {
		//return m_prefix; support namespace at DElement level
		return null;		
	}

	/**
	 * The namespace prefix of this node, or <code>null</code> if it is 
	 * unspecified. When it is defined to be <code>null</code>, setting it 
	 * has no effect, including if the node is read-only.
	 * <br>Note that setting this attribute, when permitted, changes the 
	 * <code>nodeName</code> attribute, which holds the qualified name, as 
	 * well as the <code>tagName</code> and <code>name</code> attributes of 
	 * the <code>Element</code> and <code>Attr</code> interfaces, when 
	 * applicable.
	 * <br>Setting the prefix to <code>null</code> makes it unspecified, 
	 * setting it to an empty string is implementation dependent.
	 * <br>Note also that changing the prefix of an attribute that is known to 
	 * have a default value, does not make a new attribute with the default 
	 * value and the original prefix appear, since the 
	 * <code>namespaceURI</code> and <code>localName</code> do not change.
	 * <br>For nodes of any type other than <code>ELEMENT_NODE</code> and 
	 * <code>ATTRIBUTE_NODE</code> and nodes created with a DOM Level 1 
	 * method, such as <code>createElement</code> from the 
	 * <code>Document</code> interface, this is always <code>null</code>.
	 * @exception DOMException
	 *   INVALID_CHARACTER_ERR: Raised if the specified prefix contains an 
	 *   illegal character according to the XML version in use specified in 
	 *   the <code>Document.xmlVersion</code> attribute.
	 *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
	 *   <br>NAMESPACE_ERR: Raised if the specified <code>prefix</code> is 
	 *   malformed per the Namespaces in XML specification, if the 
	 *   <code>namespaceURI</code> of this node is <code>null</code>, if the 
	 *   specified prefix is "xml" and the <code>namespaceURI</code> of this 
	 *   node is different from "<a href='http://www.w3.org/XML/1998/namespace'>
	 *   http://www.w3.org/XML/1998/namespace</a>", if this node is an attribute and the specified prefix is "xmlns" and 
	 *   the <code>namespaceURI</code> of this node is different from "<a href='http://www.w3.org/2000/xmlns/'>http://www.w3.org/2000/xmlns/</a>", or if this node is an attribute and the <code>qualifiedName</code> of 
	 *   this node is "xmlns" [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
	 *   .
	 * http://java.sun.com/j2se/1.4.2/docs/api/org/w3c/dom/Node.html#setPrefix(java.lang.String)
	 * @since DOM Level 2
	 */
	public void setPrefix(final String prefix) throws DOMException {		
		throw new DOMException(DOMException.NAMESPACE_ERR, "Error in namespace, namespaceURI = null.");
	}

	/**
	 * Returns the local part of the qualified name of this node.
	 * <br>For nodes of any type other than <code>ELEMENT_NODE</code>
	 * and <code>ATTRIBUTE_NODE</code> and nodes created with a DOM Level 1 
	 * method, such as <code>Document.createElement()</code>, this is always 
	 * <code>null</code>.
	 *
	 * @since DOM Level 2
	 */
	public String getLocalName() {
		//return m_localName;  //supported at DElement level
		return null;
	}

	/**
	 * Returns whether this node (if it is an element) has any attributes.
	 * @return Returns <code>true</code> if this node has any attributes, 
	 *   <code>false</code> otherwise.
	 *
	 * @since DOM Level 2
	 */
	public boolean hasAttributes() {
		return false; //!m_attributes.isEmpty();
	}

	/**
	 * The absolute base URI of this node or <code>null</code> if the 
	 * implementation wasn't able to obtain an absolute URI. This value is 
	 * computed as described in . However, when the <code>Document</code> 
	 * supports the feature 'HTML' [<a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>DOM Level 2 HTML</a>]
	 * , the base URI is computed using first the value of the href 
	 * attribute of the HTML BASE element if any, and the value of the 
	 * <code>documentURI</code> attribute from the <code>Document</code> 
	 * interface otherwise.
	 *
	 * @since DOM Level 3
	 */
	public String getBaseURI() {
		//throw new DsfDomLevelNotSupportedException(3);
		return null;
	}
	
	 /**
     * 
     * @return DNamespace of the absolute base URI 
     * @see getBaseURI()
     */
	public DNamespace getDsfBaseURI() {
		//throw new DsfDomLevelNotSupportedException(3);
		return null;
	}

//	// DocumentPosition
//	/**
//	 * The two nodes are disconnected. Order between disconnected nodes is 
//	 * always implementation-specific.
//	 */
//	public static final short DOCUMENT_POSITION_DISCONNECTED = 0x01;
//
//	/**
//	 * The second node precedes the reference node.
//	 */
//	public static final short DOCUMENT_POSITION_PRECEDING = 0x02;
//
//	/**
//	 * The node follows the reference node.
//	 */
//	public static final short DOCUMENT_POSITION_FOLLOWING = 0x04;
//
//	/**
//	 * The node contains the reference node. A node which contains is always 
//	 * preceding, too.
//	 */
//	public static final short DOCUMENT_POSITION_CONTAINS = 0x08;
//
//	/**
//	 * The node is contained by the reference node. A node which is contained 
//	 * is always following, too.
//	 */
//	public static final short DOCUMENT_POSITION_CONTAINED_BY = 0x10;
//
//	/**
//	 * The determination of preceding versus following is 
//	 * implementation-specific.
//	 */
//	public static final short DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 0x20;

	/**
	 * Compares the reference node, i.e. the node on which this method is 
	 * being called, with a node, i.e. the one passed as a parameter, with 
	 * regard to their position in the document and according to the 
	 * document order.
	 * @param other The node to compare against the reference node.
	 * @return Returns how the node is positioned relatively to the reference 
	 *   node.
	 * @exception DOMException
	 *   NOT_SUPPORTED_ERR: when the compared nodes are from different DOM 
	 *   implementations that do not coordinate to return consistent 
	 *   implementation-specific results.
	 *
	 * @since DOM Level 3
	 */
	public short compareDocumentPosition(final Node other) throws DOMException {
		throw new DsfDomLevelNotSupportedException(3);
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
	public String getTextContent() throws DOMException {
		if (!hasChildNodes()) return "" ;
		
		// Answer the concatenation of the textContent attribute value of every 
		// child node, excluding COMMENT_NODE and PROCESSING_INSTRUCTION_NODE nodes.
		String answer = "" ;
		final IDNodeList kids = getDsfChildNodes() ;
		final int len = kids.getLength() ;
		for(int i = 0; i < len; i++) {
			Node node = kids.item(i) ;
			if (node instanceof DComment || node instanceof DProcessingInstruction) {
				// skip per spec...
			}
			else {
				answer += node.getTextContent() ;
			}
		}
		return answer ;
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
	 *   NO_MODIFICATION_ALLOWED_ERR: Raised when the node is readonly.
	 *
	 * @since DOM Level 3
	 */
	public void setTextContent(final String textContent) throws DOMException {
		if (textContent == null) {
			return;
		}

// MrPperf - handle m_childNodes being null
		if (m_childNodes != null) {
			m_childNodes.clear();
		}
		
		//if (textContent == "") {
		if (textContent.length()==0) {
			return;
		}

		add(textContent);
	}

	/**
	 * Returns whether this node is the same node as the given one.
	 * <br>This method provides a way to determine whether two 
	 * <code>Node</code> references returned by the implementation reference 
	 * the same object. When two <code>Node</code> references are references 
	 * to the same object, even if through a proxy, the references may be 
	 * used completely interchangeably, such that all attributes have the 
	 * same values and calling the same DOM method on either reference 
	 * always has exactly the same effect.
	 * @param other The node to test against.
	 * @return Returns <code>true</code> if the nodes are the same, 
	 *   <code>false</code> otherwise.
	 *
	 * @since DOM Level 3
	 */
	public boolean isSameNode(final Node other) {
		return this==other;
	}

	/**
	 * Look up the prefix associated to the given namespace URI, starting from 
	 * this node. The default namespace declarations are ignored by this 
	 * method.
	 * <br>See  for details on the algorithm used by this method.
	 * @param namespaceURI The namespace URI to look for.
	 * @return Returns an associated namespace prefix if found or 
	 *   <code>null</code> if none is found. If more than one prefix are 
	 *   associated to the namespace prefix, the returned namespace prefix 
	 *   is implementation dependent.
	 *
	 * @since DOM Level 3
	 */
	public String lookupPrefix(final String namespaceURI){ 
		DNamespace ns = dsfLookupPrefix(namespaceURI);		
		return ns == null ? null : ns.getPrefix();
	}
	
	/**
	 * lookup prefix in DNamespace reference based on given namespaceURI
	 * @param namespaceURI
	 * @return
	 * @see lookupPrefix(final String namespaceURI)
	 */
	public DNamespace dsfLookupPrefix(final String namespaceURI){ 
        // REVISIT: When Namespaces 1.1 comes out this may not be true
        // Prefix can't be bound to null namespace
        if (namespaceURI == null) {
            return null;
        }

        short type = getNodeType();

        switch (type) {
	        case Node.ELEMENT_NODE: {
                getNamespaceURI(); // to flip out children 
                return getNamespaceByNamespaceURI(namespaceURI, (DElement)this);
            }
	        case Node.DOCUMENT_NODE:{
                DElement rootEle = (DElement)((DDocument)this).getDocumentElement();
                return rootEle !=null ? rootEle.dsfLookupPrefix(namespaceURI) : null;
            }
	
	        case Node.ENTITY_NODE :
	        case Node.NOTATION_NODE:
	        case Node.DOCUMENT_FRAGMENT_NODE:
	        case Node.DOCUMENT_TYPE_NODE:
	            // type is unknown
	            return null;
	        case Node.ATTRIBUTE_NODE:{
	            if (m_parentNode.getNodeType() == Node.ELEMENT_NODE) {
	                return m_parentNode.dsfLookupPrefix(namespaceURI);
	
	            }
	            return null;
	        }
	        default:{   
	            DNode ancestor = getElementAncestor(this);
	            if (ancestor != null) {
	                return ancestor.dsfLookupPrefix(namespaceURI);
	            }
	            return null;
	        }
        }
    }

	
	private DNamespace getNamespaceByNamespaceURI(String namespaceURI, DElement el){
        //String namespace = getNamespaceURI();
        // REVISIT: if no prefix is available is it null or empty string, or 
        //          could be both?
        //String prefix = getPrefix();

        if (m_dsfNamespace!=null && m_dsfNamespace.getNamespaceKey().equals(namespaceURI)) {
            if (m_prefix != null) {
                String foundNamespace =  el.lookupNamespaceURI(m_prefix);
                if (foundNamespace !=null && foundNamespace.equals(namespaceURI)) {
                    return m_dsfNamespace;
                }
            }
        }
       
        DNode ancestor = (DNode)getElementAncestor(this);

        if (ancestor != null) {
            return ancestor.getNamespaceByNamespaceURI(namespaceURI, el);
        }
        return null;
    }
	/**
	 *  This method checks if the specified <code>namespaceURI</code> is the 
	 * default namespace or not. 
	 * @param namespaceURI The namespace URI to look for.
	 * @return Returns <code>true</code> if the specified 
	 *   <code>namespaceURI</code> is the default namespace, 
	 *   <code>false</code> otherwise.
	 * 
	 * @since DOM Level 3
	 */
	public boolean isDefaultNamespace(final String namespaceURI) {
		if (namespaceURI==null){
			return false;
		}
		DNamespace ns = dsfLookupPrefix(namespaceURI);
		return ns==null?(namespaceURI==null):isDsfDefaultNamespace(ns);
	}
	
	/**
	 * check if the specified namespace is the default namespace or not.
	 * @param namespace
	 * @return <code>true</code> if the specified 
	 *   <code>namespaceURI</code> is the default namespace, 
	 *   <code>false</code> otherwise.
	 */
	public boolean isDsfDefaultNamespace(final DNamespace namespace){
		 // REVISIT: remove casts when DOM L3 becomes REC.
		short type = getNodeType();
		switch (type) {
			case Node.ELEMENT_NODE: {             
				return namespace==getNamespaceFromAncestor((DElement)this);
			}
	        case Node.DOCUMENT_NODE:{        
	        	DElement rootEle = (DElement)((DDocument)this).getDocumentElement();
	        	return rootEle !=null ? rootEle.isDsfDefaultNamespace(namespace) : false;
	        }
	        case Node.ENTITY_NODE :
	        case Node.NOTATION_NODE:
	        case Node.DOCUMENT_FRAGMENT_NODE:
	        case Node.DOCUMENT_TYPE_NODE:
	            // type is unknown
	            return false;
	        case Node.ATTRIBUTE_NODE:{             
	        	return false;
	        }
	        default:{   
	        	DNode ancestor = getElementAncestor(this);
	        	if (ancestor != null) {
	        		return ancestor.isDsfDefaultNamespace(namespace);
	        	}
	        	return false;
	        }
        }
	}	

	/**
	 * Look up the namespace URI associated to the given prefix, starting from 
	 * this node.
	 * <br>See  for details on the algorithm used by this method.
	 * @param prefix The prefix to look for. If this parameter is 
	 *   <code>null</code>, the method will return the default namespace URI 
	 *   if any.
	 * @return Returns the associated namespace URI or <code>null</code> if 
	 *   none is found.
	 *
	 * @since DOM Level 3
	 */
	public String lookupNamespaceURI(final String specifiedPrefix) {
		DNamespace ns = dsfLookupNamespaceURI(specifiedPrefix);
		return ns==null?null:ns.getNamespaceKey();
	}
	
	/**
	 * Look up the namespace associated to the given prefix, starting from 
	 * this node.
	 * @param specifiedPrefix
	 * @return
	 */
	public DNamespace dsfLookupNamespaceURI(final String specifiedPrefix) {
		short type = getNodeType();
		switch (type) {
			case Node.ELEMENT_NODE : {                  
				String namespace = getNamespaceURI();                
				if (namespace !=null) {                    
					if (specifiedPrefix== null && m_prefix==specifiedPrefix) {
						// looking for default namespace
						return m_dsfNamespace;
						//return getDefaultNamespaceURIFromAncestor((DElement)this);
					} else if (m_prefix != null && m_prefix.equals(specifiedPrefix)) {
						// non default namespace
						return m_dsfNamespace;
					}
				} 
				//Attr not support ns
				DNode ancestor = getElementAncestor(this);
				if (ancestor != null) {
					return ancestor.dsfLookupNamespaceURI(specifiedPrefix);
				}				
				return null;
            }
			case Node.DOCUMENT_NODE : {   
				((DDocument)this).dsfLookupNamespaceURI(specifiedPrefix);				
				
			}
			case Node.ENTITY_NODE :
			case Node.NOTATION_NODE:
			case Node.DOCUMENT_FRAGMENT_NODE:
			case Node.DOCUMENT_TYPE_NODE:
				//type is unknown
				return null;
			case Node.ATTRIBUTE_NODE:{
				return null;
			}
			default:{ 
				DNode ancestor = getElementAncestor(this);
				if (ancestor != null) {
					return ancestor.dsfLookupNamespaceURI(specifiedPrefix);
				}
				return null;
			}
		}
	}
	
	protected DNamespace getNamespaceFromAncestor(DElement ele){
		DNode ancestor = getElementAncestor(ele);
		DNamespace ns = null;
        while (ancestor != null) {
        	ns = ancestor.getDsfNamespace();
        	if (ns != null){
        		return ns;
        	}
        	ancestor = getElementAncestor(ancestor);
        } 
        if (ele.getOwnerDocument() != null) {
        	return ((DDocument)ele.getOwnerDocument()).getDsfDocumentURI();
        }
        return null;
	}
	
	protected String getDefaultNSURIFromAncestor(DElement ele){
		DNamespace ns = getNamespaceFromAncestor(ele);
		return ns !=null ? ns.getNamespaceKey() :null;
	}
	
	DNode getElementAncestor(DNode currentNode){
        DNode parent = (DNode)currentNode.getParentNode();
        if (parent != null) {
            short type = parent.getNodeType();
            if (type == Node.ELEMENT_NODE || type == Node.DOCUMENT_NODE) {
                return parent;
            }           
            return getElementAncestor(parent);
        }        
        return null;
    }

	
	/**
	 * Tests whether two nodes are equal.
	 * <br>This method tests for equality of nodes, not sameness (i.e., 
	 * whether the two nodes are references to the same object) which can be 
	 * tested with <code>Node.isSameNode()</code>. All nodes that are the 
	 * same will also be equal, though the reverse may not be true.
	 * <br>Two nodes are equal if and only if the following conditions are 
	 * satisfied: 
	 * <ul>
	 * <li>The two nodes are of the same type.
	 * </li>
	 * <li>The following string 
	 * attributes are equal: <code>nodeName</code>, <code>localName</code>, 
	 * <code>namespaceURI</code>, <code>prefix</code>, <code>nodeValue</code>
	 * . This is: they are both <code>null</code>, or they have the same 
	 * length and are character for character identical.
	 * </li>
	 * <li>The 
	 * <code>attributes</code> <code>NamedNodeMaps</code> are equal. This 
	 * is: they are both <code>null</code>, or they have the same length and 
	 * for each node that exists in one map there is a node that exists in 
	 * the other map and is equal, although not necessarily at the same 
	 * index.
	 * </li>
	 * <li>The <code>childNodes</code> <code>NodeLists</code> are equal. 
	 * This is: they are both <code>null</code>, or they have the same 
	 * length and contain equal nodes at the same index. Note that 
	 * normalization can affect equality; to avoid this, nodes should be 
	 * normalized before being compared.
	 * </li>
	 * </ul> 
	 * <br>For two <code>DocumentType</code> nodes to be equal, the following 
	 * conditions must also be satisfied: 
	 * <ul>
	 * <li>The following string attributes 
	 * are equal: <code>publicId</code>, <code>systemId</code>, 
	 * <code>internalSubset</code>.
	 * </li>
	 * <li>The <code>entities</code> 
	 * <code>NamedNodeMaps</code> are equal.
	 * </li>
	 * <li>The <code>notations</code> 
	 * <code>NamedNodeMaps</code> are equal.
	 * </li>
	 * </ul> 
	 * <br>On the other hand, the following do not affect equality: the 
	 * <code>ownerDocument</code>, <code>baseURI</code>, and 
	 * <code>parentNode</code> attributes, the <code>specified</code> 
	 * attribute for <code>Attr</code> nodes, the <code>schemaTypeInfo</code>
	 *  attribute for <code>Attr</code> and <code>Element</code> nodes, the 
	 * <code>Text.isElementContentWhitespace</code> attribute for 
	 * <code>Text</code> nodes, as well as any user data or event listeners 
	 * registered on the nodes. 
	 * <p ><b>Note:</b>  As a general rule, anything not mentioned in the 
	 * description above is not significant in consideration of equality 
	 * checking. Note that future versions of this specification may take 
	 * into account more attributes and implementations conform to this 
	 * specification are expected to be updated accordingly. 
	 * @param arg The node to compare equality with.
	 * @return Returns <code>true</code> if the nodes are equal, 
	 *   <code>false</code> otherwise.
	 *
	 * @since DOM Level 3
	 */
	public boolean isEqualNode(final Node arg) {
		 if (arg == this) {
            return true;
        }
        if (arg.getNodeType() != getNodeType()) {
            return false;
        }
        // in theory nodeName can't be null but better be careful
        // who knows what other implementations may be doing?...
        if(!isEqualString(getNodeName(),arg.getNodeName()) 
        		|| !isEqualString(getLocalName(), arg.getLocalName())
        		|| !isEqualString(getNamespaceURI(), arg.getNamespaceURI())
        		|| !isEqualString(getPrefix(), arg.getPrefix())
        		|| !isEqualString(getNodeValue(), arg.getNodeValue()))
        {
        	return false;
        }
        
        return isEqualChildrenNodes(arg);
      }

	/**
	 *  This method returns a specialized object which implements the 
	 * specialized APIs of the specified feature and version, as specified 
	 * in . The specialized object may also be obtained by using 
	 * binding-specific casting methods but is not necessarily expected to, 
	 * as discussed in . This method also allow the implementation to 
	 * provide specialized objects which do not support the <code>Node</code>
	 *  interface. 
	 * @param feature  The name of the feature requested. Note that any plus 
	 *   sign "+" prepended to the name of the feature will be ignored since 
	 *   it is not significant in the context of this method. 
	 * @param version  This is the version number of the feature to test. 
	 * @return  Returns an object which implements the specialized APIs of 
	 *   the specified feature and version, if any, or <code>null</code> if 
	 *   there is no object which implements interfaces associated with that 
	 *   feature. If the <code>DOMObject</code> returned by this method 
	 *   implements the <code>Node</code> interface, it must delegate to the 
	 *   primary core <code>Node</code> and not return results inconsistent 
	 *   with the primary core <code>Node</code> such as attributes, 
	 *   childNodes, etc.
	 *
	 * @since DOM Level 3
	 */
	public Object getFeature(final String feature, final String version) {
		throw new DsfDomLevelNotSupportedException(3);
	}

	/**
	 * Associate an object to a key on this node. The object can later be 
	 * retrieved from this node by calling <code>getUserData</code> with the 
	 * same key.
	 * @param key The key to associate the object to.
	 * @param data The object to associate to the given key, or 
	 *   <code>null</code> to remove any existing association to that key.
	 * @param handler The handler to associate to that key, or 
	 *   <code>null</code>.
	 * @return Returns the <code>DOMUserData</code> previously associated to 
	 *   the given key on this node, or <code>null</code> if there was none.
	 *
	 * @since DOM Level 3
	 */
	public Object setUserData(
		final String key, final Object data, final UserDataHandler handler)
	{
		if (m_userData == null) {
			m_userData = new LinkedHashMap<String, Object>(3) ;
		}
		//TODO store handles- coredocumentImpl
		return m_userData.put(key, data) ;
	}

	public boolean hasUserData() {
		return (m_userData == null) ? false : m_userData.size() > 0 ;
	}
	/**
	 * Retrieves the object associated to a key on a this node. The object 
	 * must first have been set to this node by calling 
	 * <code>setUserData</code> with the same key.
	 * @param key The key the object is associated to.
	 * @return Returns the <code>DOMUserData</code> associated to the given 
	 *   key on this node, or <code>null</code> if there was none.
	 *
	 * @since DOM Level 3
	 */
	public Object getUserData(final String key) {
		if (m_userData == null) {
			return null ;
		}
		return m_userData.get(key) ;
	}

	/**
	 * Answer the first node who's node who has an attr named attrName with 
	 * the passed in attrValue.  The order of the search is undefined and thus 
	 * the "first" located element may change over time so don't rely on it.
	 * There is no attempt to optimize the search so try to limit how much of
	 * the graph that needs to be processed.  
	 */
	public <T extends DNode>T dsfFindByAttrNameValue(final String attrName, final String attrValue) {
		final FindByAttrNameValueVisitor visitor 
			= new FindByAttrNameValueVisitor(attrName, attrValue) ;
		final DepthFirstDNodeTraversal traversal = new DepthFirstDNodeTraversal() ;
		visitor.setStrategy(traversal) ;
		this.dsfAccept(visitor) ;
		return (T)visitor.getMatch();
	}
	
	private static class FindByAttrNameValueVisitor extends DefaultDNodeVisitor {
		private DNode m_nodeMatch = null ;
		private String m_attrName ;
		private String m_attrValue ;
		
		FindByAttrNameValueVisitor(final String attrName, final String attrValue) {
			m_attrName = attrName ;
			m_attrValue = attrValue ;
		}
		public DNodeVisitStatus visit(DNode node) throws AbortDNodeTraversalException {
			if (node.hasAttributes()) {
				DAttr attr = node.getDsfAttributes().getAttr(m_attrName) ;
				if (attr != null) {
					if (attr.getValue().equals(m_attrValue)) {
						m_nodeMatch = node ;
					}
				}
				return DNodeVisitStatus.STOP_SUBTREE_TRAVERSAL ;
			}
			return DNodeVisitStatus.CONTINUE ;			
		}
		public DNode getMatch() {
			return m_nodeMatch ;
		}
	}
	
	public DNode jif(final String jif) { 
		Jif.jif(this, jif, true, "Dsf") ; 
		return this ;
	}
	
	/**
	 * Answers the passed in element.  Since it's a generic, the result type of the
	 * nav(...) is type of what's passed in.
	 */
	public <T extends DNode> T nav(T element) {
		return (T)element ;
	}
	
	public DNode nav(String nav) {
		nav = nav.trim() ;
		if (nav.startsWith(".")) {
			return getKid(nav);
		}
		else if (nav.startsWith("<")){
			return getPrev(nav) ;
		}
		else {
			throw new DsfRuntimeException("Unknown nav: " + nav) ;
		}
	}

	private DNode getPrev(String nav) {
		if ("<".equals(nav)) {
			return this ;
		}
		else { // expecting "<#" where # is a number
			String num = nav.substring(1) ;
			int moveBy = 0 ;
			try {
				moveBy = Integer.parseInt(num) ;
				int len = parentChainLength(this) ;
				if (moveBy > len) {
					throw new DsfRuntimeException(
						"There are less parents (" + len 
							+ ") than the nav requested: " + moveBy) ;
				}
				DNode answer = this ;
				for(int i = 0; i < moveBy; i++) {
					answer = answer.getDsfParentNode() ;
				}
				return answer ;
			}
			catch(NumberFormatException e) {
				throw new DsfRuntimeException(
					"expected nav format: nav# where # is a positive integer") ;
			}
		}
	}
	
	private int parentChainLength(Node node) {
		if (node.getParentNode() == null) return 0 ;
		return 1 + parentChainLength(node.getParentNode()) ;
	}
	
	private DNode getKid(String nav) {
		if (".".equals(nav)) {
			int len = getLength() ;
			if (len == 0) {
				throw new DsfRuntimeException("No child to nav to") ;
			}
			return getDsfChildNodes().get(0) ;
		}
		else { // expecting "<#" where # is a number
			String num = nav.substring(1) ;
			int moveBy = 0 ;
			try {
				moveBy = Integer.parseInt(num) ;
				int len = getLength() ;
				if (moveBy > len) {
					throw new DsfRuntimeException(
						"There are less children (" + len 
							+ ") than the nav requested: " + moveBy) ;
				}
				return getDsfChildNodes().get(moveBy - 1) ;
			}
			catch(NumberFormatException e) {
				throw new DsfRuntimeException(
						"expected nav format: nav# where # is a positive integer") ;
			}
		}
	}
	
	public <T extends DNode> T nav(String nav, Class<T> clz) {
		nav = nav.trim() ;
		if (nav.startsWith(".")) {
			if (".".equals(nav)) {
				int len = getLength() ;
				for(int i = 0; i < len; i++) {
					Node child = getChildNodes().item(i) ;
					if (clz.isAssignableFrom(child.getClass())) {
						return (T)child ;
					}
				}
				throw new DsfRuntimeException("Did find nav for: " + clz.getName()) ;
			}
			else { // expecting ".#" where # is a number
				String num = nav.substring(1) ;
				int moveBy = -1 ;
				try {
					moveBy = Integer.parseInt(num) ;
					return (T)getChildNodes().item(moveBy) ;
				}
				catch(NumberFormatException e) {
					throw new DsfRuntimeException(
							"expected nav format: nav# where # is a positive integer") ;
				}
			}
		}
		else if (nav.startsWith("<")) {
			return (T)getPrev(nav) ;
		}
		else {
			throw new DsfRuntimeException("Unknown nav: " + nav) ;
		}
	}
	
	//
	// Override(s) from Object
	//
	@Override
	public String toString() {
		Z z = new Z();
		
		z.format(
			"node parent", 
			m_parentNode == null ? null : m_parentNode.getNodeName());
		z.format("node name", m_nodeName);
		z.format("node value", m_nodeValue);
		z.format("node type", getNodeType());
//		z.format("node baseURI", getBaseURI()) ;
		
		z.format("node id", m_nodeId) ;
		z.format("node prefix", m_prefix);
		z.format("node dsf name", m_dsfName) ;
		z.format("node exports local names", m_exportingLocalNames);
		z.format("node naming family", m_dsfNamingFamily) ;
		if (m_dsfNamespace !=null){
			z.format("node namespace", m_dsfNamespace.toString());
		}
		if (m_facets == null) {
			z.format("node facet names", "{ }") ;
		}
		else {
			z.format("node facet names", m_facets.keySet()) ;
		}
		if (m_strategies != null) {
			z.format("node phase ids with strategy", m_strategies.keySet()) ;
		}
		if (m_listeners == null) {
			z.format("node listeners", 0) ;
		}
		else {
			z.format("node listeners", m_listeners.size()) ;
		}
// MrPperf - handle m_childNodes being null
		z.format("node children", getLength()) ;

		return z.toString();
	}

	/**
	 * same as cloneInternal(true)
	 * @see cloneInternal(boolean)
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return cloneInternal(true);
	}
	
	public DNode cloneInternal(boolean deep) throws CloneNotSupportedException {
		final DNode copy = abstractsClone() ; //super.clone();
		DsfCtx.ctx().getContainer().checkNodeInstantiation(copy);

		copy.m_ownerDocument = null;
		copy.m_parentNode = null;
//		copy.m_childrenAdapter = null; // it will get lazily created.
		if (copy.m_facets != null) {
			cloneFacets(copy);
		}
		if (deep) {
// MrPperf - don't create children unless orig has them
			if (m_childNodes != null) {
				copy.m_childNodes = createChildNodes(this, m_childNodes.size()) ; //new DNodeList(this, m_childNodes.size());
				for (DNode kid:m_childNodes) {
					final DNode kidCopy = (DNode)kid.clone();
			// TODO: MrP - should this be m_childNodes.privateAdd(DNode)
					copy.add(kidCopy);
					//copy.m_childNodes.privateAdd(kidCopy);
				}
			}
		} else {
// MrPperf - we don't auto-create the children any more but need to null it
// out from the basic clone operation
//			copy.m_childNodes = new DNodeList(this, 3);	
			copy.m_childNodes = null ;
		}
//		copy.m_attributes = new AttributeMap(this, this, m_attributes.size());
////		for (Map.Entry<String, IValueManager> entry:m_attributes.entrySet()) {
//		for (Map.Entry<String, DAttr> entry : m_attributes.entrySet()) {
////			final IValueManager<Object> valueMgr = new ManagedValue<Object>(Object.class);
////			valueMgr.setValue(((IValueManager<?>)entry.getValue()).getValue());
//			final DAttr attr = entry.getValue();
//			final IValueManager vm = attr.getValueManager();
//			if (vm == null) {
//				copy.m_attributes.put(attr.getNodeName(), attr.getNodeValue());
//			} else {
//				final Object value = vm.getValue();
//				copy.m_attributes.put(entry.getKey(), value);
//			}
//		}
		return copy;
	}

	/**
	 * Answers the number of children for this instance.
	 * @return the number of children for this instance
	 */
	public int getLength() {
// MrPperf - check if children exist first
		if (m_childNodes == null) {
			return 0 ;
		}
		return m_childNodes.size();
	}

	/**
	 * Answer the node at the specified index.
	 * @return the node at the specified index.  If no children then answers null.
	 * @throws IndexOutOfBoundsException if index < 0 || index >= getLength()
	 */
	public Node item(int index) {
// MrPperf - handle null childNodes but maintain error handling...
		if (index < 0 || index >= getLength()) {
			throw new IndexOutOfBoundsException() ;
		}
		if (m_childNodes == null) {
			return null ;
		}
		
		return m_childNodes.get(index) ;
	}

	protected void checkChildForAdd(final Node child) {
		// Need to null check here first since we don't want the name checkers
		// to NPE on a null child.
		if (child == null) {
			throw new DOMException(DOMException.VALIDATION_ERR,"Child is null");
		}
		
/* Here are our parent/child rules related to documents.  Assume
 * p = parent and child = c.
 
 1. p.doc == null && c.doc == null // ok
 2. p.doc == c.doc // ok
 3. p.doc != null and c.doc == null // ok
 4. all other combinations are failure
		 */
		final Document childsDoc = child.getOwnerDocument();
		if (m_ownerDocument == null && childsDoc != null) {
			//TODO log this error
		}
		else if (childsDoc != null && childsDoc != m_ownerDocument) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, 
				"node belongs to a different document");	
		}
		
		final DNode dsfChild = (DNode) child;
		
// MrPperf - don't check for cycles if we're not verifying relationships
		final boolean verifyRelationship 
			= DsfVerifierConfig.getInstance().isVerifyRelationship() ;
		
		if (verifyRelationship) {
			checkChildForCyclesAndLimits(dsfChild);
		}
		//used for component
		//ComponentDsfNamingTests
		if (DsfVerifierConfig.getInstance().isVerifyNaming()) {
			// go to schema
			NameChecker.assertNamesUnique(this, dsfChild);
			NameChecker.assertChildrenNamesUnique(this, dsfChild);
		}
		
		if (verifyRelationship) {
			assertParentChildRelationship(this, dsfChild);
		}
	}
	
	protected void checkChildForCyclesAndLimits(final DNode child) {
		int depth = 0;
		int nodeCounts = 0;
		if (child.hasChildNodes()){
			NodeCounter sz = new NodeCounter();
			sz.countDepth(child);
			depth = sz.m_depth;
			nodeCounts = sz.m_nodeCounts;
		}
		for (DNode ancestor=this;ancestor!=null;ancestor=ancestor.m_parentNode){
			if (ancestor == child) {
				throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
					"this node cannot be an ancestor of itself");
			}
			nodeCounts += ancestor.hasChildNodes()?ancestor.getChildNodes().getLength() : 0;
			depth += ancestor.hasChildNodes()? 1 : 0;
		}
		
//		if (getMaxDepth() < depth){
//			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Exceed maximum depth limit " + getMaxDepth() + ", " + depth);
//		}	
//		if (getMaxSize() < nodeCounts){
//			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Exceed maximum nodes limit " + DOM_MAX_NODES + ", " + nodeCounts);
//		}	
		//System.out.println("depth = " + depth);
		//System.out.println("nodeCounts = " + nodeCounts);
	}

	protected void postChildAdd(final DNode nodex) {
		nodex.m_parentNode = this;
		if (nodex.m_ownerDocument == null && m_ownerDocument != null) {
			//nodex.m_ownerDocument = m_ownerDocument;
			nodex.setDsfOwnerDocument(m_ownerDocument);			
		} 
		else {
			if (m_ownerDocument == null && nodex.m_ownerDocument != null) {
				//TODO log this error
			}
			else if (nodex.m_ownerDocument != m_ownerDocument) {
				throw new DOMException(DOMException.WRONG_DOCUMENT_ERR,
					"node belongs to a different document");
			}
		}
	}

	public boolean hasDsfFacets() {
		return (m_facets == null) ? false : m_facets.size() > 0 ;
	}
	
	public IFacetsMap getDsfFacets() {
		if (m_facets == null) {
			m_facets = createFacetsMap(this, 3);
		}

		return m_facets;
	}

	private FacetsMap createFacetsMap(final DNode owner, final int initialSize) {
		return new FacetsMap(owner, initialSize);
	}
	
	private void cloneFacets(final DNode newParent)
		throws CloneNotSupportedException
	{
// MrPperf - won't this NPE if facets haven't been materialized???	
		if (m_facets == null) {
			return ;
		}
		
		newParent.m_facets = createFacetsMap(newParent, m_facets.size());

		for (Map.Entry<String, DNode> entry : m_facets.entrySet()) {
			final String name = entry.getKey();
			final DNode copy = (DNode) entry.getValue().clone();
			newParent.getDsfFacets().put(name, copy);
		}
	}

	/**
	 * A utility that will "break up" most of the Node graph.
	 * This is useful for aiding in garbage collection since the dense graph
	 * is essentially nulled out a key node points.
	 */
	public DNode dsfDiscard() {
		// propagate discard() to all facets
		if (m_facets != null) {
			for (DNode facet : m_facets.values()) {
				facet.dsfDiscard();
			}
			m_facets.clear();
		}
		m_facets = null;
// MrPperf - check if childNodes is null
		if (m_childNodes != null) {
			for (DNode child:m_childNodes) {
				child.dsfDiscard();
				child.m_parentNode = null;
			}
			m_childNodes.privateClear() ;
		}
		m_childNodes = null;

		return abstractsDsfDiscard() ;
	}

	public Object cloned() {
		try {
			return clone();
		} 
		catch (CloneNotSupportedException e) {
			throw new DsfRuntimeException(e.getMessage());
		}
	}

	public DNode getDsfParentNode() {
		return m_parentNode;
	}
	/**
	 * Answer the parent going back "n" parent levels from "this" object.
	 * A
	 * +---> B
	 *       +---> C
	 *             +---> D
	 * 
	 * D.getParentNode() == C
	 * D.getParentNode(0) == C     // go back 0 parent (same as getParentNode())
	 * D.getParentNode(1) == B     // go back 1 parent
	 * D.getParentNode(2) == A	   // go back 2 parents
	 * D.getParentNode(3) == null  // go back 3 parents
	 * D.getParentNode(4)  -- exception
	 * D.getParentNode(-2) -- exception
	 */
	public DNode getDsfParentNode(final int back) {
		if (back < 1) {
			throw new DsfRuntimeException("back must be a positive integer") ;
		}
		DNode answer = this ;
		for (int i = 1; i <= back; i++) {
			answer = answer.getDsfParentNode() ;
			if (answer == null && (i < back)) {
				throw new DsfRuntimeException(
					"Could not go back " + back + " parents, max would be: " + i) ;
			}
		}
		return answer ;
	}
	
	public XPathResult dsfXpath(final String xpathExpression) {
		return new XPathResult(this, xpathExpression) ;
	}
	public XPathResult dsfXpath(final XPathExpression xpathExpression) {
		return new XPathResult(this, xpathExpression) ;
	}
		
	void deparent() {
		setParent(null) ;
	}
	
	DNode setParent(final DNode parent) {
		if (parent == this) {
			chuck("Attempted to parent to self.");
		}

		final DNode oldParent = m_parentNode;

		if (oldParent != null) {
			eraseParent(this, oldParent);
		}
		m_parentNode = parent;

		if (m_parentNode == null) {
			return oldParent;
		}

		DNode component = m_parentNode.getDsfParentNode();
		while (component != null) {
			if (component == m_parentNode) {
				m_parentNode = null; //break the circular parenting
				chuck("Circular parent/child relationship formed.");
			}
			component = component.getDsfParentNode();
		}

		return oldParent;
	}

	private void eraseParent(final DNode child, final DNode parent) {
		if (parent == null) {
			return;
		}

// MrPperf - ok as is since parent IS from the child
		parent.getDsfChildNodes().remove(child);
		
// MrPperf - smart check to avoid creating facets if not needed
		if (parent.hasDsfFacets() == false) {
			return ;
		}
		
// MrPperf - see if we can get around using the iterator
		final Map<?,?> facets = parent.getDsfFacets();
		final Iterator<?> entries = facets.entrySet().iterator();
		while (entries.hasNext()) {
			final Map.Entry<?,?> entry = (Map.Entry<?,?>) entries.next();
			if (entry.getValue() == child) {
				entries.remove();
				return;
			}
		}
	}

	public boolean hasDsfChildWithLocalName(final String localName) {
		if (localName == null) {
			return false;
		}
// MrPperf - handle null childNodes
		if (m_childNodes == null) {
			return false ;
		}
		
		for (final DNode kid : m_childNodes) {
			if (kid.hasDsfName()) {
				final String kidsLocalName = kid.getDsfName().getLocalName();
				if (kidsLocalName != null && kidsLocalName.equals(localName)) {
					return true;
				}
			}
		}
		return false;
	}

	/** null will be returned for all types except in DElement.
	 */
	public IAttributeMap getDsfAttributes() {
		return null;
	}

// MrPperf - we force m_childNodes to exist before we create the adapter
// this is not optimal but once you get the children (wrapper or not) we
// are commmitted to backing store being there...
//	class ChildrenAdapter implements IChildNodes {
//		public List<DNode> subList(int fromIndex, int toIndex) {
//			throw new DsfRuntimeException("Not implemented") ;
//		}
//		public Object[] toArray() {
//			throw new DsfRuntimeException("Not implemented") ;
//		}
//		
//	    /**
//	     * Returns an array containing all of the elements in this list in proper
//	     * sequence; the runtime type of the returned array is that of the
//	     * specified array.  Obeys the general contract of the
//	     * <tt>Collection.toArray(Object[])</tt> method.
//	     *
//	     * @param a the array into which the elements of this list are to
//	     *		be stored, if it is big enough; otherwise, a new array of the
//	     * 		same runtime type is allocated for this purpose.
//	     * @return  an array containing the elements of this list.
//	     * 
//	     * @throws ArrayStoreException if the runtime type of the specified array
//	     * 		  is not a supertype of the runtime type of every element in
//	     * 		  this list.
//	     * @throws NullPointerException if the specified array is <tt>null</tt>.
//	     */
//	    public <DNode> DNode[] toArray(DNode[] a) {
//	    	throw new DsfRuntimeException("Not implemented") ;
//	    }
//	    
//		// 
//		// satify Iterable<DNode>
//		//
//		public Iterator<DNode> iterator() {
//			return m_childNodes.iterator() ;
//		}
//		
//		//
//		// Satisfy IChildNodes
//		//
//		public int indexOf(final DNode node) {
//			return m_childNodes.indexOf(node) ;
//		}
//		
//		public void append(final DNode child) {
//			DNode.this.add(child);
//		}
//
//		public void clear() {
//			while (m_childNodes.size() > 0) {
//				final DNode child = m_childNodes.get(m_childNodes.size()-1);
//				removeChild(child);
//			}
////			m_childNodes.clear();
//		}
//
//		public DNode get(final int index) {
//			return m_childNodes.get(index);
//		}
//
//		public void insertBefore(final DNode child, final DNode reference) {
//			DNode.this.insertBefore(child, reference);
//		}
//
//		public boolean isEmpty() {
//			return m_childNodes.size() == 0;
//		}
//
//		public void remove(final DNode child) {
//			DNode.this.removeChild(child);
//		}
//
//		public void replace(final DNode child, final DNode reference) {
//			DNode.this.replaceChild(child, reference);
//		}
//
//		public int size() {
//			return m_childNodes.size();
//		}
//	}
//
//	transient ChildrenAdapter m_childrenAdapter = null;

	public IDNodeList getDsfChildNodes() {
// MrPperf - force m_childNodes to be created here since adapter is a wrapper of it
		getChildNodes() ;
		return m_childNodes ;
	}
	
	public IDNodeList getDsfChildNodes(final int initialSize) {
		getChildNodes(initialSize) ;
		return m_childNodes ;
	}

	/**
	 * NON-DOM
	 * set the ownerDocument of this node and its children
	 */
	void setDsfOwnerDocument(final DDocument doc) {
		if (m_ownerDocument == doc) {
			return;
		}
		//DDocument origDoc = null;
		if (m_ownerDocument != null) {
			m_ownerDocument.removeIdentifiedElement(this);	
			//origDoc = m_ownerDocument;
		}
		m_ownerDocument = doc;
		if (m_childNodes != null) {
			final int len = m_childNodes.getLength() ;
			for (int i = 0; i < len; i++) {		
				// *** RECURSION ***
				m_childNodes.item(i).setDsfOwnerDocument(doc);
			}
		}
	}
	
	/**
	 * NON-DOM
	 * it can only be called by a top node in the tree
	 */
	public void dsfDetachFromOwnerDocument() {
		if (m_parentNode != null) {
			throw new RuntimeException("This node still has a parent");
		}
		
		setDsfOwnerDocument(null);		
	}

    // ****************************************************
    //	ALL THE STATE AND METHODS FROM DSF ABSTRACT NODE   
    //	ALL THE STATE AND METHODS FROM DSF ABSTRACT NODE
    //	ALL THE STATE AND METHODS FROM DSF ABSTRACT NODE
    //	ALL THE STATE AND METHODS FROM DSF ABSTRACT NODE
    //	ALL THE STATE AND METHODS FROM DSF ABSTRACT NODE
    //	ALL THE STATE AND METHODS FROM DSF ABSTRACT NODE
    // ****************************************************

	//
	// API
	//
	public DNodeId getNodeId() {
		if (m_nodeId == null) {
			m_nodeId = new DNodeId() ;
		}
		return m_nodeId ;
	}
	protected void setNodeId(final DNodeId id) {
		m_nodeId = id ;
	}
	/** this is need because an object derived from this class is embedded in
	 * DOM2 derived components and it can't return the true this.  It must
	 * return the this of the outer piece.
	 * @return
	 */
//	protected abstract IDsfComponent getThis();
	
	// TODO: what do we do about the ID for a clone???
	/**
	 * Listeners are nulled
	 * DsfName is nulled
	 */
	private DNode abstractsClone() throws CloneNotSupportedException {
		final DNode copy = (DNode)super.clone() ;

		copy.m_dsfName = null;
		copy.m_listeners = null;
		
		final IDsfName copiesDsfName = copy.getDsfName() ;
		if (copiesDsfName.getLocalName() != null) {
			copiesDsfName.setLocalName(m_dsfName.getLocalName());
		}
		if (copiesDsfName.getScopeName() != null) {
			copiesDsfName.setScopeName(m_dsfName.getScopeName());
		}
		if (m_dsfRelationshipVerifier != null) {
			copy.m_dsfRelationshipVerifier =
				(IDNodeRelationshipVerifier)m_dsfRelationshipVerifier.clone();
		}
		
		cloneStrategies(copy);

		copy.m_propertyDescriptors = m_propertyDescriptors ;
		copy.m_valueBindingMap = m_valueBindingMap;
		
		return copy;
	}
	
	protected void cloneStrategies(final DNode copy) //AbstractDsfNode copy)
		throws CloneNotSupportedException
	{
		if (m_strategies == null) {
			return ;
		}
		copy.m_strategies = new DsfStrategies(2);
		if (m_strategies.size() <= 0) {
			return ;
		}

		for (Map.Entry<PhaseId,IDNodeHandlingStrategy> entry : m_strategies.entrySet()) {
			final PhaseId phaseId = entry.getKey();
			final IDNodeHandlingStrategy strategy = (IDNodeHandlingStrategy)
				entry.getValue().clone();
			copy.getDsfStrategies().put(phaseId, strategy);
		}
	}

//	protected abstract boolean hasDsfChildWithLocalName(final String localName);
	private DNode abstractsDsfDiscard() {
		// propagate discard() to all facets
//		if (m_facets != null) {
//			for (IDsfComponent facet : m_facets.values()) {
//				facet.dsfDiscard() ;
//			}
//			m_facets.clear() ;
//		}
		if (m_strategies != null) {
			m_strategies.clear() ;
		}
		
		if (m_listeners != null) {
			m_listeners.clear() ;
		}

		m_facets = null;
		m_strategies = null;
		m_listeners = null;
		m_dsfName = null;
		m_dsfRelationshipVerifier = null ;
		m_propertyDescriptors = null ;
		m_valueBindingMap = null ;
		//m_dsfNamingVerifier = null;
		return this;
	}
	
	/**
	 * Broadcasts the event to any registered IDsfEventListner's.
	 * The listeners are broadcast to in the order they were maintained.
	 * @param event the DsfNode event to broadcast.  Must not be null.
	 * @return this instance
	 * @see DsfPhaseEvent
	 * @see AbortDsfEventProcessingException
	 */
	@SuppressWarnings("unchecked")
	public DNode dsfBroadcast(final DsfEvent event)
		throws AbortDsfEventProcessingException
	{
		if (event == null) {
			chuck("ComponentEvent must not be null");
		}
		
		if (m_listeners == null) {
			return this;
		}

		for (IDsfEventListener listener : m_listeners) {
			if (event.isAppropriateListener(listener)) {
				event.dispatch(listener);
			}
		}
		return this;
	}
	
	/**
	 * Broadcasts the event through a strategy pattern
	 * The listeners are broadcast to in the order they were maintained.
	 * @param event the DsfNode event to broadcast.  Must not be null.
	 * @param strategy the strategy used to handle the event. Must not be null.
	 * @return this instance
	 * @see DsfPhaseEvent
	 * @see AbortDsfEventProcessingException
	 */
	@SuppressWarnings("unchecked")
	public DNode dsfBroadcast(
		final DsfEvent event, final IDsfEventStrategy strategy)
		throws AbortDsfEventProcessingException
	{
		if (event == null) {
			chuck("ComponentEvent must not be null");
		}
		if (strategy == null) {
			chuck("Broadcast event strategy must not be null");
		}

		// let the propagator decide what to do
		strategy.handle(this, event) ;
		
		return this ;
	}
	
//	private static class EventListenerAssociator extends EventHelper {
//		protected static boolean isAppropriateListener(
//			final DsfEvent event, final IDsfEventListener listener)
//		{
//			return EventHelper.isAppropriateListener(event, listener);
//		}
//	
//		protected static void processListener(
//			final DsfEvent event, final IDsfEventListener listener)
//				throws AbortDsfEventProcessingException
//		{
//			EventHelper.processListener(event, listener);
//		}
//	}

	public boolean hasDsfStrategies() {
		if (m_strategies == null) {
			return false ;
		}
		return m_strategies.size() > 0 ;
	}
	
	/**
	 * get Dsf stragteties
	 */
	public IDsfStrategies getDsfStrategies() {
		if (m_strategies == null) {
			m_strategies = new DsfStrategies(2) ;
		}
		return m_strategies ;
	}
	
//	/**
//	 * Answer the IDNodeHandlingStrategy for the passed in phaseId, if none answers null.
//	 * @return  the IDNodeHandlingStrategy for the passed in phaseId.
//	 * @param phaseId to get the strategy
//	 */
//	public IDNodeHandlingStrategy getDsfStrategy(final PhaseId phaseId) {
//		if (m_strategies == null) {
//			return null ;
//		}
//		
//		return m_strategies.get(phaseId);
//	}
	
//	/**
//	 * Associate the strategy with the passed in phaseId.
//	 * Answers the previous strategy associated with the passed in phaseId.
//	 * 
//	 * @param phaseId PhaseId to associate the passed in strategy.  Must not be null.
//	 * @param strategy Strategy to be used for the passed in phaseId.  Must not be null.
//	 * @return the previous strategy associated with the passed in phaseId, else null
//	 * @see PhaseId
//	 * @see IDNodeHandlingStrategy
//	 */
//	public IDNodeHandlingStrategy setDsfStrategy(
//		final PhaseId phaseId, final IDNodeHandlingStrategy strategy)
//	{
//		if (phaseId == null) chuck("phaseId must not be null.") ;
//		if (strategy == null) chuck("strategy must not be null.") ;
//		
//		if (m_strategies == null) {
//			m_strategies = new DsfStrategies(2); // unlikely we will have more than 2
//		}
//		
//		return m_strategies.put(phaseId, strategy);
//	}
	
//	/**
//	 * Removes the strategy associated with the passed in PhaseId.  If there was
//	 * a strategy associated, it is returned, else null is returned.
//	 * @param phaseId The PhaseId to identify which (if any) strategy to remove.
//	 * @return The strategy associated with the passed in PhaseId
//	 */
//	public IDNodeHandlingStrategy dsfRemoveStrategy(final PhaseId phaseId) {
//		if (m_strategies == null) {
//			return null ;
//		}	
//		return m_strategies.remove(phaseId);
//	}
	
	/**
	 * Answers the IDsfName for this instance
	 * <br>
	 * getDsfName() == getDsfName() is always true and getDsfName() will never be null.
	 */
	public IDsfName getDsfName() {
		if (m_dsfName == null) {
			m_dsfName = new DNodeName(this);
		}
		return m_dsfName;
	}
	
	/**
	 * Answers whether this instance has a DsfName or not.
	 * <br>
	 * Once getDsfName() has been called, this instance has a DSF name.
	 * @return whether this instance has a DsfName or not
	 */
	public boolean hasDsfName() {
		return m_dsfName != null ;
	}
	
	/**
	 * Answer whether this instance is exporting its' local names
	 * @return whether this instance is exporting its' local names
	 * @see DNode#setDsfExportingLocalNames(boolean)
	 */
	public boolean isDsfExportingLocalNames() {
		return m_exportingLocalNames;
	}
	
	/**
	 * Sets whether or not this instance will export its' local names
	 * @return this instance
	 * @param shouldExport whether or not to export local names
	 */
	public DNode setDsfExportingLocalNames(final boolean shouldExport) {
		if (!m_exportingLocalNames && shouldExport) {
			// We need to check as good as an add.
			NameChecker.assertChildrenNamesUnique(this, this);
		}
		m_exportingLocalNames = shouldExport;
		return this;
	}
	
	/**
	 * Answers the naming family for this instance.
	 * @return The naming family for this instance.  Will never be null.
	 */
	public IDsfNamingFamily getDsfNamingFamily() {
		if (m_dsfNamingFamily == null) {
			m_dsfNamingFamily = DsfCtx.ctx().getContainer().getDsfNamingFamily();
//TODO: do we really mean HtmlIdNamingFamily as the default for DNode (DOM) types?
//			m_dsfNamingFamily = HtmlIdNamingFamily.getInstance();
		}
		return m_dsfNamingFamily;
	}
	
	/**
	 * Set the naming family for this instance.
	 * @return this instance
	 * @param namingFamily The naming family for this node.  Can be null.
	 * @see IDsfNamingFamily
	 */
	public DNode setDsfNamingFamily(final IDsfNamingFamily namingFamily){
		m_dsfNamingFamily = namingFamily;
		return this;
	}

	/**
	 * Set the relationship verifier for this instance
	 * <br>
	 * The verifier can be used to assert a newly added ttribute, child, facet or parent.
	 * @return this instance
	 * @param relationshipVerifier relationship verifier for this instance.
	 * @see DNode#getDsfRelationshipVerifier()
	 */
	public DNode setDsfRelationshipVerifier(
		final IDNodeRelationshipVerifier relationshipVerifier)
	{
//		if (relationshipVerifier == null) {
//			chuck("Relationship verifier must not be null") ;
//		}
		m_dsfRelationshipVerifier = relationshipVerifier ;
		return this;
	}
	
	/**
	 * Answer the relationship verifier for this instance
	 * <br>
	 * If a relationship verifer had not be set previously the verifier is set
	 * from DsfCtx.ctx().getContainer().getNodeRelationshipVerifier().
	 * @return the relationship verifier for this instance
	 * @see DNode#setDsfRelationshipVerifier(IDNodeRelationshipVerifier)
	 */
	public IDNodeRelationshipVerifier getDsfRelationshipVerifier() {
		if (m_dsfRelationshipVerifier == null) { // can happen from a dsfDiscard()
			m_dsfRelationshipVerifier 
				= DsfCtx.ctx().getContainer().getNodeRelationshipVerifier();
		}
		return m_dsfRelationshipVerifier ;
	}

	/**
	 * Answer the ComponentHandlingStrategy that should be employed for the
	 */
	protected IDNodeHandlingStrategy getStrategy(
		final IDNodeVisitor visitor, final IStage<PhaseId> phase)
	{
		if (visitor == null) {
			// this check was added after it was discovered that
			// visitor.getStrategy() might be called
			chuck("null visitor not allowed");
		}
		IDNodeHandlingStrategy strategy = null;
		
		if (hasDsfStrategies()) { // MrP - perf - don't force create if not needed
			if (phase != null) {
				strategy = getDsfStrategies().get(phase.getId());
			}
			if (strategy == null) {
				strategy = getDsfStrategies().get(PhaseId.ANY_PHASE);
			}
		}
		
		if (strategy == null) {
			strategy = visitor.getStrategy();
		}
		
		if (strategy == null) {
			strategy = DefaultDNodeHandlingStrategy.getInstance();
		}
		
		return strategy;
	}
	
	/**
	 * Removes the listener from the set of listeners for getThis() component.  
	 * Answers true if the listener was previously added else answers false.
	 * @param listener Listener to remove from the set of listeners for getThis() component
	 * @return Answers true if the passed in listener was removed.
	 */
//	public boolean dsfRemoveEventListener(final IDsfEventListener listener) {
//		if (m_listeners == null) {
//			return false;
//		}
//		return m_listeners.remove(listener);
//	}
	
	/**
	 * get dsf event listeners
	 */
	public IDsfEventListeners getDsfEventListeners() {
		if (m_listeners == null) {
			// unlikely we will have too many listeners
			m_listeners = new DsfEventListeners(2);
		} 
		return m_listeners ;
	}
	
	/**
	 * Adds the listener to current ordered set of listeners.
	 * If the listener was already in the list the operation is a no-op.
	 * @see org.ebayopensource.dsf.common.event.IDsfEventListenr
	 * @param listener to be added.  Must not be null.
	 * @return this instance
	 */
//	public DNode addDsfListener(final IDsfEventListener listener) {
//		if (listener == null) {
//			chuck("Listener must not be null") ;
//		}
//		
//		if (m_listeners == null) {
//			// unlikely we will have too many listeners
//			m_listeners = new ArrayList<IDsfEventListener>(2);
//		} 
//		else if (m_listeners.contains(listener)){
//			return this;  // we already have it.
//		}
//		
//		m_listeners.add(listener);
//		return this ;
//	}
	
	/**
	 * Answers if "this" node has any listeners registered.  It does not answer
	 * if this node parents and/or children may have any listeners.
	 * @return
	 */
	public boolean hasDsfListeners() {
		return (m_listeners == null) ? false : m_listeners.size() > 0 ;
	}
	
//	protected boolean hasDsfListeners(
//		final Class<? extends IDsfEventListener> clz)
//	{
//		if (clz == null) {
//			throw new NullPointerException(
//				"Expected IDsfListener type class");
//		}
//		if (m_listeners == null) {
//			return false ;
//		}
//		
//		for (IDsfEventListener listener : m_listeners) {
//			if (clz.isInstance(listener)) {
//				return true ;
//			}
//		}
//		return false ;		
//	}
	
//	protected boolean removeDsfListeners(
//		final Class<? extends IDsfEventListener> clz)
//	{
//		if (m_listeners == null) {
//			return false ;
//		}
//		// We gather up what should be removed in one pass and remove it
//		// in pass 2.  This is so we don't muck with the backing store during
//		// the iteration.
//		final IDsfEventListener[] listeners = getDsfListeners(clz) ;
//		if (listeners.length == 0) {
//			return false ;
//		}
//		m_listeners.removeAll(Arrays.asList(listeners)) ;
//		return true ;	
//	}
	
//	protected IDsfEventListener[] getDsfListeners(
//		final Class<? extends IDsfEventListener> clz)
//	{
//		if (clz == null) {
//			throw new NullPointerException(
//				"Expected IDsfListener type class");
//		}
//		if (m_listeners == null) {
//			return ((IDsfEventListener[]) 
//				java.lang.reflect.Array.newInstance(clz, 0));
//		}
//
//		final List<IDsfEventListener> results = new ArrayList<IDsfEventListener>();
//		for (IDsfEventListener listener : m_listeners) {
//			if (clz.isInstance(listener)) {
//				results.add(listener);
//			}
//		}
//	
//		return ((IDsfEventListener[])results.toArray((Object []) 
//			java.lang.reflect.Array.newInstance(clz, results.size())));
//	}
	
	/**
	 * This double dispatch approach provides the control point for component
	 * to have customized behavior.
	 * @see IDNodeVisitor
	 */
	public DNode dsfAccept(final IDNodeVisitor visitor) {
		final PhaseDriver cycle = DsfCtx.ctx().getPhaseDriver();
		if (cycle != null){
			getStrategy(visitor, cycle.getManager().getCurrent()).handle(this, visitor);
		}
		else {
			getStrategy(visitor, null).handle(this, visitor);
		}
		
		return this;
	}
	
	protected static void chuck(final String message) {
		throw new DsfRuntimeException(message) ;
	}
	static {
		Initializer.init();
	}

	public PropertyDescriptor getPropertyDescriptor(final String propertyName) {
		final PropertyDescriptorMap pds = getPropertyDescriptors();
		return pds.get(propertyName);
	}

	private DNodeList createChildNodes() {
		return createChildNodes(this, 3) ;
	}
	
	private DNodeList createChildNodes(final DNode me, final int initialSize) {
		return new DNodeList(me, initialSize);
	}
	
	private PropertyDescriptorMap getPropertyDescriptors() {
		if (m_propertyDescriptors != null) {
			return m_propertyDescriptors ;
		}
		
		synchronized (s_descriptors) {
			if (m_propertyDescriptors != null) {
				return m_propertyDescriptors ;
			}
			PropertyDescriptorMap map = s_descriptors.get(this.getClass());
			if (map == null) {
				final Class<?> type = this.getClass();

				try {
					PropertyDescriptor[] pds = 
						Introspector.getBeanInfo(type, Object.class).
							getPropertyDescriptors();
					map = new PropertyDescriptorMap();
					for (PropertyDescriptor pd : pds) {
						if (!ignoreAsNativeProperty(pd)) {
							map.put(pd.getName(), pd);
						}
					}
				} catch (IntrospectionException e) {
					chuck(e.getMessage());
				}

				s_descriptors.put(this.getClass(), map);
			}
			m_propertyDescriptors = map ;
			return map;
		}
	}
	
	private boolean ignoreAsNativeProperty(final PropertyDescriptor pd) {
		final Method readMethod = pd.getReadMethod();
		if (readMethod != null) {
			final Annotation ignoreRead =
				readMethod.getAnnotation(IgnoreAsNativeProperty.class);
			if (ignoreRead != null) {
				return true;
			}
		}
		
		final Method writeMethod = pd.getWriteMethod();
		if (writeMethod != null) {
			final Annotation ignoreWrite =
				writeMethod.getAnnotation(IgnoreAsNativeProperty.class);
			if (ignoreWrite != null) {
				return true;
			}
		}
		
		return false;
	}

	public IValueBinding<?> getIntrinsicPropertyValueBinding(
		final String propertyName)
	{
		final Map<String, Field> fields = getIntrinsicValueBindingFields();
		final Field field = fields.get(propertyName.toLowerCase());
		
		if (field == null) {
			return null;
		}
		
		try {
			IValueBinding<?> binding = (IValueBinding<?>)field.get(this);
			if (binding == null) {
				// Handle case where we need to create the ValueBinding
				binding = new SimpleValueBinding<Object>(Object.class);
				field.set(this, binding);
			}
			
			return binding;
		}
		catch (Exception e) {
			throw new DsfRuntimeException(e);
		}
	} 
	 
	public DNode setDsfIntrinsicPropertyValueBinding( 
		final String propertyName, final IValueBinding<?> binding)
	{	
		final Map<String, Field> fields = getIntrinsicValueBindingFields();
		final Field field = fields.get(propertyName.toLowerCase());
		
		if (field == null) {
			chuck("The intrinsic property " + propertyName + " is not bindable.");
		}
		try {
			field.set(this, binding);
		} 
		catch (Exception e) {
			throw new DsfRuntimeException(e);
		}
		return this ;
	}
	
	private Map<String, Field> getIntrinsicValueBindingFields() {
		if (m_valueBindingMap != null) {
			return m_valueBindingMap;
		}
		
		synchronized (s_valueBindings) {
			Map<String, Field> valueBindingsPerClz =
				s_valueBindings.get(this.getClass());
			
			if (valueBindingsPerClz == null) {
				valueBindingsPerClz = new HashMap<String, Field>();
				try {
					Class<?> clz = this.getClass();
					while (clz != null && clz != DNode.class) {
						collectValueBindingFields(clz, valueBindingsPerClz);
						clz = clz.getSuperclass();
					}
				} 
				catch (Exception e) {
					chuck(e.getMessage());
				}
				s_valueBindings.put(this.getClass(), valueBindingsPerClz);
			}
			
			m_valueBindingMap = valueBindingsPerClz ;
			
			return valueBindingsPerClz;
		}
	}
	
	private void collectValueBindingFields(
		final Class<?> theClz, final Map<String, Field> valueBindingsPerClz)
	{
		final Field[] fields = theClz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			field.setAccessible(true);
			if (IValueBinding.class.isAssignableFrom(field.getType())) {
				String propertyName = field.getName();
				if (propertyName.startsWith("m_")) {
					propertyName = propertyName.substring(2);
				}
				valueBindingsPerClz.put
					(propertyName.toLowerCase(), field);
			}
		}
	}

	/*
	 * These operations can be expensive, so generally it is not invoked for
	 * L&P and Deployment cases.  The DsfVerifierConfig controls whether this
	 * method is invoked or not per environment.
	 */
	protected static void assertParentChildRelationship(
		final DNode parent, final DNode child)
	{
		IDNodeRelationshipVerifier.Status status = 
			parent.getDsfRelationshipVerifier().acceptableAsChild(parent, child);
		assertRelationship(status);
			
		status = child.getDsfRelationshipVerifier().acceptableAsParent(child, parent);		
		assertRelationship(status);				
	}
	
	/**
	 * Asserts that the added attribute of name attributeName is valid for the specified parent.
	 * @param parent
	 * @param attributeName
	 * @param attribute
	 * @see org.ebayopensource.dsf.common.DsfVerifierConfig
	 */
	void assertAttributeRelationship(
		final DNode /* IPropertyHolder */ parent, 
		final String attributeName,
		final Object attribute)
	{
		if (!DsfVerifierConfig.getInstance().isVerifyRelationship()) {
			return ;
		}
		
		final DNode parentNode = parent;
		final IDNodeRelationshipVerifier.Status status = 
			parentNode.getDsfRelationshipVerifier().acceptableAsAttribute(
				parentNode, attributeName, attribute);
				
		assertRelationship(status);				
	}
	
	private TraceCtx getTraceCtx() {
		if (m_traceCtx == null) {
			m_traceCtx = TraceCtx.ctx();
		}
		return m_traceCtx ;
	}
	
	protected static void assertRelationship(
		final IDNodeRelationshipVerifier.Status status)
	{
		if (!status.isOk()) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, status.getErrorMessage());
		}
	}
	
	private static class PropertyDescriptorMap 
		extends HashMap<String, PropertyDescriptor>
	{
		private static final long serialVersionUID = 1L;		
	}

	public Object readResolve() {
	    DsfCtx.ctx().getContainer().checkNodeInstantiation(this);
	    return this;
	}
	
    protected boolean isEqualString(String a, String b){
    	if (a == null && b == null) return true;
    	if (a != null && b!= null){
    		return a.equals(b);
    	}
    	return false;
    }
    
    /**
     * they are both null, or they have the same length and contain equal nodes at the same index. 
     * Note that normalization can affect equality; to avoid this, 
     * nodes should be normalized before being compared. 
     * @param node
     * @return boolean
     */
    protected boolean isEqualChildrenNodes(Node node){    	
    	 Node child1 = getFirstChild();
         Node child2 = node.getFirstChild();
         while (child1 != null && child2 != null) {
             if (!child1.isEqualNode(child2)) {
                 return false;
             }
             child1 = child1.getNextSibling();
             child2 = child2.getNextSibling();
         }
         if (child1 != child2) {
             return false;
         }
         return isEqualAttrs(node);
    }
    
    protected boolean isEqualAttrs(Node node){
    	return true;
    }
    
    /**
     * Call user data handlers when a node is deleted (finalized)
     * @param n The node this operation applies to.
     * @param c The copy node or null.
     * @param operation The operation - import, clone, or delete.
     */
    public void callUserDataHandlers(Node n, Node c, short operation) {    	
    	if (m_userData == null || m_userData.isEmpty()) {
    		return;
    	}
       
 //   	for(Entry<String, Object> e : m_userData.entrySet()){
    		//TODO enhance to introduce UserDataRecord
//            String key = (String) keys.nextElement();
//            UserDataRecord r = (UserDataRecord) userData.get(key);
//            if (r.fHandler != null) {
//                r.fHandler.handle(operation, key, r.fData, n, c);
//            }
//        }
    }  
    
    /**
     * return the namespace associated with this node.
     * 
     * @return
     */
    public DNamespace getDsfNamespace(){
    	return null;
    }
    
    /**
     * add namespace declaration to this node.
     * @param namespace
     * @return
     */
    public DNode setDsfNamespace(DNamespace namespace){
    	final Class<?> clz = this.getClass();
    	final String msg ;
    	if (DDocument.class.isAssignableFrom(clz)) {
    		msg = "Use getDsfNamespaceDeclarations() to manage document namespaces" ;
    	}
    	else {
    		msg = clz.getSimpleName() + " doesn't allow adding a namespace" ;
    	}
    	throw new DOMException(DOMException.NOT_SUPPORTED_ERR, msg);
    }
    
    DNode getRootElement(){
    	DNode rootN=this;
    	for (;rootN != null 
    	&& rootN.getNodeType()== Node.ELEMENT_NODE 
    	&& rootN.m_parentNode!=null
    	&& rootN.m_parentNode.getNodeType()==Node.ELEMENT_NODE;
    	rootN=rootN.m_parentNode);
    	
    	return rootN;    	 
    }
    
    /**
     * return true if this document is not null and doucment xml_verion 1.1. 
     * otherwise return false;
     * @return
     */
	protected boolean isDsfXml11Version(){
		return m_ownerDocument != null ? m_ownerDocument.isDsfXml11Version() : false;
	}	
	
//	private int getMaxDepth(){
//		int appSet = getDsfRelationshipVerifier().getAllowedDepth();
//		return (appSet == 0  || appSet > DOM_MAX_DEPTH) ? DOM_MAX_DEPTH : appSet;
//	}
//	private int getMaxSize(){
//		int appSet = getDsfRelationshipVerifier().getAllowedChildrenCounts();
//		return (appSet == 0  || appSet > DOM_MAX_NODES) ? DOM_MAX_NODES : appSet;
//	}
	
	/**
	 * helper class to to count the depth and chidlren-node-number of a given node
	 */
	private static class NodeCounter{		
		int m_nodeCounts;
		int m_depth;
		void countDepth(Node node){
			if (!node.hasChildNodes()){
				return;
			}
			m_depth ++;
			m_nodeCounts += node.getChildNodes().getLength();
			for (int i = 0; i < node.getChildNodes().getLength(); i ++){
				countDepth(node.getChildNodes().item(i));
			}				
		}	
	}
}

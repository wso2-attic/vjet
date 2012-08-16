/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.jst.declaration.JstAnnotation;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class BaseJstNode implements IJstNode {
	
	private static final long serialVersionUID = 1L;
	
	private BaseJstNode m_parent;
	private List<BaseJstNode> m_children;
	private JstSource m_source;
	private List<IJstAnnotation> m_annotations;
	private List<String> m_comments;
	
	//
	// Constructor
	//
	public BaseJstNode(){
		this(null);
	}

	public BaseJstNode(final BaseJstNode parent){
		setParent(parent);
	}
	
	//
	// Satisfy IJstNode
	//
	public BaseJstNode getRootNode(){
		if (m_parent == null){
			return this;
		}
		BaseJstNode root = m_parent;
		while (root.getParentNode() != null){
			root = root.getParentNode();
		}
		return root;
	}
	public BaseJstNode getParentNode(){
		return m_parent;
	}
	public JstType getOwnerType(){
		if (this instanceof JstType){
			return (JstType)this;
		}
		else if (m_parent != null){
			return m_parent.getOwnerType();
		}
		return null;
	}
	public JstType getRootType(){
		JstType parentType = getOwnerType();
		JstType rootType = parentType;
		while (parentType != null && parentType.getParentNode() != null){
			rootType = parentType.getParentNode().getOwnerType();
			parentType = parentType.getParentNode().getOwnerType();
		}
		return rootType;
	}
	public List<BaseJstNode> getChildren(){
		return getChildren(true);
	}
	
	public JstSource getSource() {
		return m_source;
	}
	
	//
	// API
	//
	public void setParent(final IJstNode parent){
		setParent(parent, true);
	}
	
	public void setParent(final IJstNode parent, boolean includeAsChild) {
		if (m_parent == parent){
			return;
		}
		if (m_parent != null){
			m_parent.removeChild(this);
		}
		if (parent instanceof BaseJstNode){
			if (parent != null) {
				if (parent instanceof BaseJstNode){
					if (((BaseJstNode)parent).isAncestor(this)){
						// TODO:
						return;
					}
				}
				if (includeAsChild) {
					((BaseJstNode)parent).addChild(this);
				}
			}
			m_parent = ((BaseJstNode)parent);
		}
		
	}
	
	public void addChild(IJstNode child){
		if (child == null || getChildren(true).contains(child)){
			return;
		}
		if (isAncestor(child)){
			// TODO:
			return;
		}
		if (child instanceof BaseJstNode){
			synchronized (this){
				initChildren();
				m_children.add((BaseJstNode)child);
			}
			((BaseJstNode)child).setParent(this);
		}
	}
	
	
	
	public void removeChild(IJstNode child){
		if (child == null || !getChildren(true).contains(child)){
			return;
		}
		synchronized (this){
			initChildren();
			m_children.remove(child);
		}
		if (child instanceof BaseJstNode){
			((BaseJstNode)child).setParent(null);
		}
	}
	
	public void removeChildren(Collection<? extends IJstNode> c) {
		synchronized(this){
			initChildren();
			for (IJstNode t: c) {
				m_children.remove(t);
			}
		}
	}
	
	/**
	 * Remove all children from the type
	 */
	public void clearChildren() {
		synchronized (this) {
			m_children = null;
		}
	}
	
	/**
	 * Add annotation to this node
	 * @param annot IJstAnnotation
	 */
	public void addAnnotation(JstAnnotation annot) {
		if (annot == null) {
			return;
		}
		synchronized (this) {
			if (m_annotations == null) {
				m_annotations = new ArrayList<IJstAnnotation>();
			}
			if (!m_annotations.contains(annot)) {
				m_annotations.add(annot);
				addChild(annot);
			}
		}
	}
	
	/**
	 * Get annotations for this node
	 */
	public List<IJstAnnotation> getAnnotations() {
		if (m_annotations == null || m_annotations.isEmpty()) {
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(m_annotations);
	}
	
	/**
	 * Get annotations for this node
	 */
	public void setAnnotations(List<IJstAnnotation> annotations) {
		m_annotations = annotations;
	}
	
	/**
	 * Returns annotation for given name
	 * @param name
	 */
	public IJstAnnotation getAnnotation(String name) {
		if (m_annotations == null || m_annotations.isEmpty()) {
			return null;
		}
		synchronized (this) {
			for (IJstAnnotation anno : m_annotations) {
				if (anno.getName().toString().equals(name)) {
					return anno;
				}
			}
		}
		return null;
	}

	/**
	 * clear annotations
	 */
	public void clearAnnotations() {
		synchronized (this) {
			if(m_annotations==null){
				return;
			}
			
			for (IJstAnnotation annot: m_annotations){
				removeChild((JstAnnotation) annot);
			}
			m_annotations = null;
		}
	}

	public void setSource(JstSource source) {
		m_source = source;
	}
	
	/**
	 * getComments
	 */
	public List<String> getComments() {
		return m_comments;
	}
	
	/**
	 * setComments
	 */
	public void setComments(List<String> comments) {
		m_comments = comments;
	}
	
	//
	// Private
	//
	private synchronized List<BaseJstNode> getChildren(boolean readOnly){
		initChildren();
		if (readOnly){
			return Collections.unmodifiableList(m_children);
		}
		else {
			return m_children;
		}
	}
	
	private synchronized void initChildren(){
		if(m_children==null){
			m_children = new ArrayList<BaseJstNode>();
		}
		
	}
	
	private boolean isAncestor(IJstNode node){
		IJstNode parent = getParentNode();
		while (parent != null && parent != parent.getParentNode()){
			if (parent == node){
				return true;
			}
			parent = parent.getParentNode();
		}
		return false;
	}
	
	public static String prettyPrintNode(IJstNode n) {
		if (n == null)
			return "[null]";
		String p = (n.getParentNode() != null)? prettyPrintNode(n.getParentNode()) : "";
		return p+"/["+n.getClass().getSimpleName()+":"+n.toString().replaceAll("\\s+", " ")+"]";
	}
	
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
}

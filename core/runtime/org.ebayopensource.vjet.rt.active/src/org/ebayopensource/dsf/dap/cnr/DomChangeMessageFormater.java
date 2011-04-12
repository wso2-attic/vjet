/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.cnr;

import org.w3c.dom.Node;

import org.ebayopensource.dsf.dap.cnr.DapCaptureData.IDomChange;
import org.ebayopensource.dsf.dap.util.DapDomHelper;
import org.ebayopensource.dsf.html.dom.BaseHtmlElement;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;

public class DomChangeMessageFormater {
	
	private static final String ATTR_CLASS = "class";
	private static final String ATTR_WIDTH = "width";
	private static final String ATTR_HEIGHT = "height";

	public static IDomChange onAppendChild(Node child) {
		final BaseHtmlElement parent = (BaseHtmlElement)child.getParentNode();
		final String parentRef = DapDomHelper.getPath(parent);
		return new NodeAppend()
			.setParentPath(parentRef)
			.setNodeHtml(DapDomHelper.getHtml(child));
	}

	public static IDomChange onInsert(Node newNode, Node refNode, boolean insertBefore) {
		final String siblingRef = DapDomHelper.getPath(refNode);
		return new NodeInsert()
			.setRefPath(siblingRef)
			.setNodeHtml(DapDomHelper.getHtml(newNode))
			.setInsertBefore(insertBefore);
	}
	
	public static IDomChange onRemove(Node node) {
		final String path = DapDomHelper.getPath(node);
		if (path == null){
			return null;
		}
		return new NodeRemove()
			.setPath(path)
			.setNodeHtml(DapDomHelper.getHtml(node));
	}

	public static IDomChange onElementChange(BaseHtmlElement elem) {
		final String path = DapDomHelper.getPath(elem);
		if (path == null){
			return null;
		}
		return new NodeUpdate()
			.setPath(path)
			.setNodeHtml(DapDomHelper.getHtml(elem));
	}

	public static IDomChange onValueChange(BaseHtmlElement elem, String value) {
		final String path = DapDomHelper.getPath(elem);
		if (path == null){
			return null;
		}
		return new NodeValueUpdate()
			.setPath(path)
			.setValue(value);
	}

	public static IDomChange onAttrChange(BaseHtmlElement elem, EHtmlAttr attr, String value) {
		final String path = DapDomHelper.getPath(elem);
		if (path == null){
			return null;
		}
		return new NodeAttrUpdate()
			.setPath(path)
			.setName(attr.name())
			.setValue(value);
	}

	public static IDomChange onClassNameChange(BaseHtmlElement elem, String className) {
		final String path = DapDomHelper.getPath(elem);
		if (path == null){
			return null;
		}
		return new NodeAttrUpdate()
			.setPath(path)
			.setName(ATTR_CLASS)
			.setValue(className);
	}

	public static IDomChange onHeightChange(BaseHtmlElement node, int height) {
		final String path = DapDomHelper.getPath(node);
		if (path == null){
			return null;
		}
		return new NodeAttrUpdate()
			.setPath(path)
			.setName(ATTR_HEIGHT)
			.setValue(String.valueOf(height));
	}

	public static IDomChange onWidthChange(BaseHtmlElement node, int width) {
		final String path = DapDomHelper.getPath(node);
		if (path == null){
			return null;
		}
		return new NodeAttrUpdate()
			.setPath(path)
			.setName(ATTR_WIDTH)
			.setValue(String.valueOf(width));
	}
	
	public static IDomChange onStyleChange(BaseHtmlElement elem, String name, String value) {
		final String path = DapDomHelper.getPath(elem);
		if (path == null){
			return null;
		}
		return new NodeAttrUpdate()
			.setPath(path)
			.setName(name)
			.setValue(value);
	}

	public static class NodeAppend implements IDomChange {

		private static final long serialVersionUID = 1L;
		private String m_parentPath;
		private String m_nodeHtml;

		public NodeAppend setParentPath(String parentPath){
			m_parentPath = parentPath;
			return this;
		}
		
		public NodeAppend setNodeHtml(String nodeHtml){
			m_nodeHtml = nodeHtml;
			return this;
		}
		
		@Override
		public String toString(){
			StringBuffer sb = new StringBuffer("on");
			sb.append("AppendChild").append(":");
			if (m_parentPath != null){
				sb.append(m_parentPath);
			}
			sb.append("[").append(m_nodeHtml).append("]");
			return sb.toString();
		}
	}
	
	public static class NodeInsert implements IDomChange {

		private static final long serialVersionUID = 1L;
		private String m_refPath;
		private String m_nodeHtml;
		private boolean m_insertBefore;
		
		
		public NodeInsert setRefPath(String refPath){
			m_refPath = refPath;
			return this;
		}
		
		public NodeInsert setNodeHtml(String nodeHtml){
			m_nodeHtml = nodeHtml;
			return this;
		}
		
		public NodeInsert setInsertBefore(boolean insertBefore){
			m_insertBefore = insertBefore;
			return this;
		}
		
		@Override
		public String toString(){
			StringBuffer sb = new StringBuffer("on");
			sb.append("Insert").append(":");
			if (m_refPath != null){
				sb.append(m_refPath);
			}
			sb.append("[").append(m_nodeHtml).append("]")
				.append("[").append(m_insertBefore).append("]");
			return sb.toString();
		}
	}
	
	public static class NodeRemove implements IDomChange {

		private static final long serialVersionUID = 1L;
		private String m_path;
		private String m_nodeHtml;
		
		public NodeRemove setPath(String path){
			m_path = path;
			return this;
		}
		
		public NodeRemove setNodeHtml(String nodeHtml){
			m_nodeHtml = nodeHtml;
			return this;
		}
		
		@Override
		public String toString(){
			StringBuffer sb = new StringBuffer("on");
			sb.append("Remove").append(":");
			if (m_path != null){
				sb.append(m_path);
			}
			sb.append("[").append(m_nodeHtml).append("]");
			return sb.toString();
		}
	}
	
	public static class NodeUpdate implements IDomChange {

		private static final long serialVersionUID = 1L;
		private String m_path;
		private String m_nodeHtml;
		
		public NodeUpdate setPath(String path){
			m_path = path;
			return this;
		}
		
		public NodeUpdate setNodeHtml(String nodeHtml){
			m_nodeHtml = nodeHtml;
			return this;
		}
		
		@Override
		public String toString(){
			StringBuffer sb = new StringBuffer("on");
			sb.append("ElementChange").append(":");
			if (m_path != null){
				sb.append(m_path);
			}
			sb.append("[").append(m_nodeHtml).append("]");
			return sb.toString();
		}
	}
	
	public static class NodeValueUpdate implements IDomChange {

		private static final long serialVersionUID = 1L;
		private String m_path;
		private String m_value;
		
		public NodeValueUpdate setPath(String path){
			m_path = path;
			return this;
		}
		
		public NodeValueUpdate setValue(String value){
			m_value = value;
			return this;
		}
		
		@Override
		public String toString(){
			StringBuffer sb = new StringBuffer("on");
			sb.append("ValueChange").append(":");
			if (m_path != null){
				sb.append(m_path);
			}
			if (m_value != null){
				sb.append("[").append(m_value).append("]");
			}
			return sb.toString();
		}
	}
	
	public static class NodeAttrUpdate implements IDomChange {

		private static final long serialVersionUID = 1L;
		private String m_path;
		private String m_name;
		private String m_value;
		public NodeAttrUpdate setPath(String path){
			m_path = path;
			return this;
		}
		
		public NodeAttrUpdate setName(String name){
			m_name = name;
			return this;
		}
		
		public NodeAttrUpdate setValue(String value){
			m_value = value;
			return this;
		}
		
		@Override
		public String toString(){

			StringBuffer sb = new StringBuffer("on");
			sb.append("AttrChange").append(":");
			if (m_path != null){
				sb.append(m_path);
			}
			if (m_name != null){
				sb.append("[").append(m_name).append("]");
			}
			if (m_value != null){
				sb.append("[").append(m_value).append("]");
			}
			return sb.toString();
		}
	}
}

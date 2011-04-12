/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsdebugger.gui;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

import org.ebayopensource.dsf.jsdi.ISession;
import org.ebayopensource.dsf.jsdi.IValue;
import org.ebayopensource.dsf.jsdi.IVariable;

public class VariableDataModel implements TreeTableModel {

	/**
	 * Tree column names.
	 */
	private static final String[] cNames = { " Name", " Value" };

	/**
	 * Tree column types.
	 */
	private static final Class[] cTypes = { TreeTableModel.class, String.class };

	/**
	 * The root node.
	 */
	private IVariable m_root;
	
	private ISession m_session;

	/**
	 * Creates a new VariableModel.
	 */
	public VariableDataModel() {
	}

	/**
	 * Creates a new VariableModel.
	 */
	public VariableDataModel(IVariable root, ISession session) {
		m_root = root;
		m_session = session;
	}

	// TreeTableModel

	/**
	 * Returns the root node of the tree.
	 */
	public Object getRoot() {
		return m_root;
	}

	/**
	 * Returns the number of children of the given node.
	 */
	public int getChildCount(Object nodeObj) {
		IVariable node = (IVariable) nodeObj;
		return node.getValue().getMemberCount(m_session);
	}

	/**
	 * Returns a child of the given node.
	 */
	public Object getChild(Object nodeObj, int i) {
		IVariable node = (IVariable) nodeObj;
		return node.getValue().getMember(i, m_session);
	}

	/**
	 * Returns whether the given node is a leaf node.
	 */
	public boolean isLeaf(Object nodeObj) {
		IVariable node = (IVariable) nodeObj;
		IValue value = node.getValue();
		return value.getMemberCount(m_session) == 0;
	}

	/**
	 * Returns the index of a node under its parent.
	 */
	public int getIndexOfChild(Object parentObj, Object childObj) {
		IVariable parent = (IVariable) parentObj;
		IVariable child = (IVariable) childObj;
		IVariable[] children = parent.getValue().getMembers(m_session);
		for (int i = 0; i != children.length; i++) {
			if (children[i] == child) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns whether the given cell is editable.
	 */
	public boolean isCellEditable(Object node, int column) {
		return column == 0;
	}

	/**
	 * Sets the value at the given cell.
	 */
	public void setValueAt(Object value, Object node, int column) {
	}

	/**
	 * Adds a TreeModelListener to this tree.
	 */
	public void addTreeModelListener(TreeModelListener l) {
	}

	/**
	 * Removes a TreeModelListener from this tree.
	 */
	public void removeTreeModelListener(TreeModelListener l) {
	}

	public void valueForPathChanged(TreePath path, Object newValue) {
	}

	// TreeTableNode

	/**
	 * Returns the number of columns.
	 */
	public int getColumnCount() {
		return cNames.length;
	}

	/**
	 * Returns the name of the given column.
	 */
	public String getColumnName(int column) {
		return cNames[column];
	}

	/**
	 * Returns the type of value stored in the given column.
	 */
	public Class getColumnClass(int column) {
		return cTypes[column];
	}

	/**
	 * Returns the value at the given cell.
	 */
	public Object getValueAt(Object nodeObj, int column) {
		IVariable node = (IVariable)nodeObj;
		switch (column) {
		case 0: // Name
			return node.getName();
		case 1: // Value
			String result;
			try {
				result = node.getValue().getObjectValueAsString(m_session);
			} catch (RuntimeException exc) {
				result = exc.getMessage();
				if (result == null) {
					exc.printStackTrace();
					return null;
				}
			}
			StringBuffer buf = new StringBuffer();
			int len = result.length();
			for (int i = 0; i < len; i++) {
				char ch = result.charAt(i);
				if (Character.isISOControl(ch)) {
					ch = ' ';
				}
				buf.append(ch);
			}
			return buf.toString();
		}
		return null;
	}
}

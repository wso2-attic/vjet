/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsdebugger.gui;

import javax.swing.tree.TreeModel;

public interface TreeTableModel extends TreeModel {
	/**
	 * Returns the number ofs availible column.
	 */
	public int getColumnCount();

	/**
	 * Returns the name for column number <code>column</code>.
	 */
	public String getColumnName(int column);

	/**
	 * Returns the type for column number <code>column</code>.
	 */
	public Class getColumnClass(int column);

	/**
	 * Returns the value to be displayed for node <code>node</code>, at
	 * column number <code>column</code>.
	 */
	public Object getValueAt(Object node, int column);

	/**
	 * Indicates whether the the value for node <code>node</code>, at column
	 * number <code>column</code> is editable.
	 */
	public boolean isCellEditable(Object node, int column);

	/**
	 * Sets the value for node <code>node</code>, at column number
	 * <code>column</code>.
	 */
	public void setValueAt(Object aValue, Object node, int column);
}

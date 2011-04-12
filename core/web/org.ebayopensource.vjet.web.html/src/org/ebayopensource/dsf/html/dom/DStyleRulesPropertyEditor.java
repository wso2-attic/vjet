/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.dom;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyEditorSupport;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.ebayopensource.dsf.css.dom.ICssRuleList;
import org.ebayopensource.dsf.css.dom.ICssStyleSheet;
import org.ebayopensource.dsf.css.dom.impl.DCssStyleRule;
import org.ebayopensource.dsf.css.parser.DCssBuilder;
import org.ebayopensource.dsf.css.sac.CssException;

public class DStyleRulesPropertyEditor extends PropertyEditorSupport {

	TextField m_textField;
	JPanel m_panel;
	DefaultTableModel m_tableModel;
	DCssBuilder m_cssParser = new DCssBuilder();
	
	private void initEditor() {
		m_tableModel = new DefaultTableModel(
			new Object[] {"Selectors", "Style Rule Declaration"}, 2);
		JTable table = new JTable(m_tableModel);	
		m_panel = new JPanel();
		m_panel.setLayout(new BorderLayout());
		m_panel.add(new JScrollPane(table));
		m_textField = new TextField(20);
		m_textField.addActionListener(			
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					TextField source = (TextField)e.getSource();
					String value = source.getText();
					String cssText = updateStyle(value);
					source.setText(cssText);
					setValue(cssText);				
				}
			}
		);
		m_panel.add(m_textField, BorderLayout.SOUTH);
	}
	
	public Component getCustomEditor() {
		if (m_textField == null) {
			initEditor();
		}
		m_textField.setText((String)super.getValue());
		return m_panel;
	}

	public boolean supportsCustomEditor() {
		return true;
	}
	
	public boolean isPaintable() {
		return true;
	}
	
	public void setValue(Object value) {
		super.setValue(value);	
	}
	
	public Object getValue() {
		if (m_textField != null) {
			return m_textField.getText();
		}
		return super.getValue();
	}
	
	public void paintValue(
		final java.awt.Graphics gfx, final java.awt.Rectangle box)
	{
		gfx.drawChars("Edit".toCharArray(), 0, 4, box.x + 5, box.y + box.height - 5);
	}
	
	private String updateStyle(final String text) {
		if (text == null || text.length() == 0) {
			m_tableModel.setRowCount(0);
			return "";
		}
		try {
			ICssStyleSheet cssStyleSheet = m_cssParser.parseStyleSheet(text);
			ICssRuleList rules = cssStyleSheet.getCssRules();
			m_tableModel.setRowCount(rules.getLength());
			for (int i = 0; i < rules.getLength(); i++) {
				DCssStyleRule rule = (DCssStyleRule)rules.item(i);
				String selector = rule.getSelectorText();
				String ruleDecl = rule.getStyle().getCssText();
				m_tableModel.setValueAt(selector, i, 0);
				m_tableModel.setValueAt(ruleDecl, i, 1);
			}
			String css = cssStyleSheet.toString();
			int endIndex = css.lastIndexOf("}");
			if (endIndex != -1) {
				css = css.substring(0, endIndex + 1);
			}
			return css;
		} catch (IOException e) {
			JOptionPane.showMessageDialog
				(null, e.getMessage(), "alert", JOptionPane.ERROR_MESSAGE); 
		} catch (CssException e) {
			JOptionPane.showMessageDialog
				(null, e.getMessage(), "alert", JOptionPane.ERROR_MESSAGE); 
		}
		return "";
	}
}

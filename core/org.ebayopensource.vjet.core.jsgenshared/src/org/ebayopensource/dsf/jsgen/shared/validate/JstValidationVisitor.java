/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.traversal.IJstVisitor;
import org.ebayopensource.dsf.common.resource.ResourceUtil;

public class JstValidationVisitor implements IJstVisitor {

	private static final String NO_PKG_TYPE_NAME = "NoPkgTypeName";
	private Properties m_messages;
	private ErrorReporter m_errorReporter;

	public JstValidationVisitor(ErrorReporter errorReporter) {

		if (m_messages == null) {
			m_messages = new Properties();
			try {	
				String file = ResourceUtil.getResource(JstValidationVisitor.class, "messages.properties").getFile();
				m_messages.load(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		m_errorReporter = errorReporter;
	}

	public void endVisit(IJstNode node) {
		// TODO Auto-generated method stub

	}

	public void postVisit(IJstNode node) {
		// TODO Auto-generated method stub

	}

	public void preVisit(IJstNode node) {
		// TODO Auto-generated method stub

	}

	public boolean visit(IJstNode node) {
		if (node instanceof IJstType) {
			return visitType((IJstType) node);
		}

		return false;
	}

	private boolean visitType(IJstType node) {
		if (node.getName() == "") {
			// TODO quick fix note to add package and type based on file
			m_errorReporter.error(m_messages.getProperty(NO_PKG_TYPE_NAME), node
					.getSource().toString(), node.getSource().getStartOffSet(),
					node.getSource().getEndOffSet());
		}

		if (node.isClass()) {
			validateCType(node);
		}

		return true;
	}

	private void validateCType(IJstType node) {

	}

}

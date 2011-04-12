/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validate;


import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.traversal.JstDepthFirstTraversal;

public class VjoValidator {

	private ErrorReporter m_errorReporter;

	public VjoValidator validate(IJstType type) {
		if(m_errorReporter==null){
			m_errorReporter = new DefaultErrorReporter();
		}
		
		JstDepthFirstTraversal.accept(type, new JstValidationVisitor(m_errorReporter));
		return this;
	}


	public ErrorReporter getErrorReporter() {
		return m_errorReporter;
	}
	public VjoValidator setErrorReporter(ErrorReporter errorReporter) {
		m_errorReporter = errorReporter;
		return this;
	}

}

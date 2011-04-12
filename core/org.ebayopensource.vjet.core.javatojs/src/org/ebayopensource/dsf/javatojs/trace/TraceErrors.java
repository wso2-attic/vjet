/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.trace;

import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.common.trace.ITraceData;

public class TraceErrors implements ITraceData {

	private List<TranslateError> m_errors;
	
	public TraceErrors(List<TranslateError> errors){
		m_errors = errors;
	}
	
	public void setErrors(List<TranslateError> errors){
		m_errors = errors;
	}
	
	public synchronized List<TranslateError> getErrors(){
		if (m_errors == null){
			return Collections.emptyList();
		}
		else {
			return Collections.unmodifiableList(m_errors);
		}
	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst;

import org.ebayopensource.dsf.common.Id;

/**
 * 
 * ProblemId represents an open set of ids that represent categories 
 * of problems that can be reported to a user to a client.
 * 
 * 
 *
 */
public class ProblemId extends Id {

	private static final long serialVersionUID = 9144917614265963426L;
	private JstProblemCategoryId m_classification;

	public ProblemId(JstProblemCategoryId classification ){
		super();
		m_classification = classification;
	}
	
}

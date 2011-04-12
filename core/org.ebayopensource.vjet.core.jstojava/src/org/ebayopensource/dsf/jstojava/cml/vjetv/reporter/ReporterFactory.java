/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: ReporterFactory.java, Dec 24, 2009, 10:43:48 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.dsf.jstojava.cml.vjetv.reporter;

import org.ebayopensource.dsf.jstojava.cml.vjetv.reporter.impl.BuildReporter;
import org.ebayopensource.dsf.jstojava.cml.vjetv.reporter.impl.VjetVReporter;

/**
 * Class/Interface description
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class ReporterFactory {

	private static VjetVReporter m_vReporter = null;

	private static VjetVReporter m_buildReporter = null;

	/**
	 * Get vjet validation reporter
	 * 
	 * @return {@link IHeadLessReporter}
	 */
	private synchronized static IHeadLessReporter getBuildVReporter() {
		if (m_buildReporter != null) {
			return m_buildReporter;
		} else {
			return new BuildReporter();
		}
	}

	/**
	 * Get vjet validation reporter
	 * 
	 * @return {@link IHeadLessReporter}
	 */
	private synchronized static IHeadLessReporter getVjetVReporter() {
		if (m_vReporter != null) {
			return m_vReporter;
		} else {
			return new VjetVReporter();
		}
	}

	/**
	 * Get reporter with verbose flag
	 * 
	 * @param isVerbose
	 *            boolean
	 * @return {@link IHeadLessReporter}
	 */
	public synchronized static IHeadLessReporter getReporter(boolean isVerbose) {
		if (isVerbose) {
			return getVjetVReporter();
		} else {
			return getBuildVReporter();
		}
	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.report;

public class DefaultErrorReportPolicy implements ErrorReportPolicy {
	
	ReportLevel m_unsupportedImport = ReportLevel.WARNING;
	ReportLevel m_unsupportedModifier = ReportLevel.WARNING;
	ReportLevel m_unsupportedDataType = ReportLevel.ERROR;
	ReportLevel m_unsupportedType = ReportLevel.ERROR;
	
	public ReportLevel getUnsupportedImportLevel() {
		return m_unsupportedImport;
	}
	public ReportLevel getUnsupportedModifierLevel() {
		return m_unsupportedModifier;
	}
	public ReportLevel getUnsupportedDataTypeLevel() {
		return m_unsupportedDataType;
	}
	public ReportLevel getUnsupportedTypeLevel() {
		return m_unsupportedType;
	}
}

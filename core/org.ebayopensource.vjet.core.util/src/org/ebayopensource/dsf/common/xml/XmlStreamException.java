/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.xml;

public class XmlStreamException extends RuntimeException {
	public XmlStreamException(final Exception e) {
		super(e.getMessage(), e);
	}
//	public XmlStreamException(final String message, final Throwable cause) {
//		super(message, cause);
//	}

}

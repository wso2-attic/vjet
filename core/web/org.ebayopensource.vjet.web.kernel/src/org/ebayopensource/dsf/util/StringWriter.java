/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.util;

import com.ebay.kernel.util.RopeBuffer;

/** This is an interface that only write a string.
 */
public interface StringWriter {
	StringWriter write(final String string);

	public static class RopeBufferWriter
		extends RopeBuffer
		implements StringWriter
	{
		public StringWriter write(final String string) {
			append(string);
			return this;
		}		
	}
}

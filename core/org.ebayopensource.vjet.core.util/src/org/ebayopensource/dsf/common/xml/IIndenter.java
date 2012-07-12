/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.xml;

import java.io.IOException;
import java.io.Writer;

public interface IIndenter {

	/** does appropriate thing to indent for a start tag.
	 * @throws IOException
	 */
	public void indent(final Writer writer, final int level) throws IOException;

	public static final IIndenter COMPACT = new IIndenter() {
		public void indent(final Writer writer, final int level) {
			// do nothing
		}
	};

	public static class LineBreak implements IIndenter {
		private int numWrites = 0;
		public void indent(final Writer writer, final int level)
			throws IOException
		{
			if (numWrites++ > 0) {
				writer.write('\n');
			}
		}
	};
	public static class Pretty implements IIndenter {
		private int numWrites = 0;
		public void indent(final Writer writer, final int level)
			throws IOException
		{
			if (numWrites++ > 0) {
				writer.write('\n');
			}
			for (int i = 0; i < level; i++) {
				writer.write(' ');
			}
		}
	};
}

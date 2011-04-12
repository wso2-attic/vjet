/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.schemas;

import org.ebayopensource.dsf.html.dom.HtmlTypeEnum;

public interface IContentModel {
	/** This asks a generic question about where an element with the given
	 * tag is allowed.  Please note that this does not take into account
	 * the order.
	 * @param type
	 * @return
	 */
	public boolean isElementAllowed(final HtmlTypeEnum type);

	/** This indicates whether or not no content is allowed; i.e. empty.
	 * @return
	 */
	public boolean isEmpty();

	public static final IContentModel EMPTY = new IContentModel() {
		public boolean isElementAllowed(final HtmlTypeEnum type) {
			return false;
		}
		public boolean isEmpty() {
			return true;
		}
	};
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationRuntimeException;

public interface IVjoValidationPostChildListener extends IVjoValidationListener{

	void onPostChildEvent(final IVjoValidationVisitorEvent event) 
		throws VjoValidationRuntimeException;
}
/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.node;

import java.util.Map;

import org.ebayopensource.dsf.common.node.visitor.IDNodeHandlingStrategy;
import org.ebayopensource.dsf.common.phase.PhaseId;

public interface IDsfStrategies 
	extends Map<PhaseId, IDNodeHandlingStrategy>, Iterable<IDNodeHandlingStrategy>
{
	// empty on purpose
}

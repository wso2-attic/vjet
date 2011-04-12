/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.event;

import org.ebayopensource.dsf.dom.DNode;

/**
 * Implementors can be passed to the DNode::dsfBroadcast(DNode, IDsfEventStrategy)
 * method to define the event delivery strategy.  The initial passed in node
 * is the node dsfBroadcast(...) was called on.
 * 
 * The contract does not define how the event should be delivered but the 
 * normal approach is to:
 * if (event.isAppropriateListener(listener) {
 *     // any other checks...
 *     event.processListener(listener) ;
 * }
 */
public interface IDsfEventStrategy {
	void handle(DNode current, DsfEvent<DNode, IDsfEventListener> event) 
		throws AbortDsfEventProcessingException ;
	
	IDsfEventStrategy Current = new CurrentEventStrategy() ;
	IDsfEventStrategy CurrentToRoot = new CurrentToRootEventStrategy() ;
	IDsfEventStrategy PostOrder = new PostOrderEventStrategy() ;
	IDsfEventStrategy PreOrder = new PreOrderEventStrategy()  ;
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.context;

import org.ebayopensource.dsf.common.event.DsfPhaseEvent;
import org.ebayopensource.dsf.common.phase.PhaseId;

public interface IDsfNodeEventQueue {	
	/**
	 * Add the passed in event to the queue.  If the event is null a
	 * DsfRuntimeException is thrown.
	 * 
	 * If the framework has a current phase being processed, that phases'
	 * PhaseId is then set as the orginating phase id of that event.  The
	 * current phase can be obtained via 
	 * DsfContext.getContext().getLifecycle().getCurrentPhase()
	 * <p>
	 * The originating phase id is set using a protected method from
	 * org.ebayopensource.dsf.event.EventHelper.  An internal class subclasses from
	 * EventHelper and thus has access to the protected method that allows
	 * the events originating PhaseId to be set.
	 */
	void enqueueEvent(DsfPhaseEvent event) ;

	/**
	 * Answer if the passed in event was removed from the queue.  If the event
	 * passed in is null the answer is always false.  
	 */
	boolean dequeueEvent(DsfPhaseEvent event) ;
	
	/**
	 * Delivers all events applicable to this phase.  If the passed in PhaseId
	 * is null a DsfRuntimeException is thrown.
	 * <p>
	 * It is important to have an implementation that can repeatedly try to
	 * exhaust all the events for this PhaseId.  This means a simple loop is
	 * not sufficient since events can be added during listener execution.
	 * 
	 * @param phaseId
	 */
	void deliverEvents(PhaseId phaseId) ;
}

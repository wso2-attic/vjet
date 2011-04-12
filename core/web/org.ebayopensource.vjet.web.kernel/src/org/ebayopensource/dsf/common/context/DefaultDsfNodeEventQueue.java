/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.context;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.common.event.AbortDsfEventProcessingException;
import org.ebayopensource.dsf.common.event.DsfPhaseEvent;
import org.ebayopensource.dsf.common.event.EventHelper;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.phase.PhaseId;
import org.ebayopensource.kernel.stage.IStage;

/**
 * Queues the node event and delivers them in the queuing order
 * when they are applicable for a specific phase.
 * 
 * A runtime instance of this class can be accessed via the DsfContext's
 * getEventQueue() method.
 */
public final class DefaultDsfNodeEventQueue implements IDsfNodeEventQueue {
	private List<DsfPhaseEvent> m_nodeEvents = new ArrayList<DsfPhaseEvent>();
	private DsfCtx m_context ;

	//
	// Constructor(s)
	//	
	public DefaultDsfNodeEventQueue() {
		this(DsfCtx.ctx()) ;
	}
	
	public DefaultDsfNodeEventQueue(final DsfCtx context) {
		if (context == null) {
			chuck("Context must not be null") ;
		}
		m_context = context ;
	}

	//
	// Satisfy IComponentEventQueue
	//
	public void enqueueEvent(final DsfPhaseEvent event) {
		if (event == null) {
			chuck("Event being enqueued must not be null") ;
		}
		
		final IStage<PhaseId> currentPhase = 
			m_context.getPhaseDriver().getManager().getCurrent();
		
		if (currentPhase != null) {
			EventAssociator.setOriginatingPhaseId
				(event, currentPhase.getId());
		}
				
		m_nodeEvents.add(event);
	}
	
	public boolean dequeueEvent(final DsfPhaseEvent event) {
		return m_nodeEvents.remove(event);
	}
	
	/**
	 * Delivers all events applicable to this phase.
	 * 
	 * @param phaseId
	 */
	public void deliverEvents(final PhaseId phaseId) {
		if (phaseId == null) {
			chuck("PhaseId must not be null") ;
		}
		//Should not use iterator, as new event can be added into the queue by
		//some of the event listeners.
		//To prevent infinite loop, we put the up-limit as 100 or 10 times the
		//current size.
		int maxLooping = 10 * m_nodeEvents.size();
		maxLooping = (maxLooping < 100) ? 100 : maxLooping;
		
		int index = 0;
		while (index < m_nodeEvents.size()) {
			maxLooping--;
			
			if (maxLooping < 0) {
				chuck("Possible infinite event loop detected.");
			}
			
			DsfPhaseEvent event = m_nodeEvents.get(index);

			if (event.shouldFire(phaseId)) {
				try {
//					deliverEvent(event);
					event.getSource().dsfBroadcast(event);
				}
				catch (AbortDsfEventProcessingException e) {
					//expected
				}		
			}
			
			if (event.shouldDelete(phaseId)) {
				m_nodeEvents.remove(index);
			}
			else {
				index++;						
			}
		}
	}

	//
	// Framework
	//
	void clear() {
		m_nodeEvents.clear();
	}
	
//	protected void deliverEvent(final DsfNodeEvent event) 
//		throws AbortDsfEventProcessingException
//	{
//		if (event == null) {
//			chuck("Event to be delivered must not be null") ;
//		}
//		event.geDsfNode().dsfBroadcast(event);
//	}
	
	//
	// Private
	//
	private void chuck(final String message) {
		throw new DsfRuntimeException(message) ;
	}
	
	//
	// Helper class(es)
	//
	private static class EventAssociator extends EventHelper {
		protected static void setOriginatingPhaseId
			(final DsfPhaseEvent event, final PhaseId phaseId)
		{
			EventHelper.setOriginatingPhaseId(event, phaseId);
		}
	}
}

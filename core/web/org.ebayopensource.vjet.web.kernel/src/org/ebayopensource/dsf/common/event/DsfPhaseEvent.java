/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.event;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.phase.PhaseId;
import org.ebayopensource.dsf.dom.DNode;

public abstract class DsfPhaseEvent<L extends IDsfEventListener>
	extends DsfEvent<DNode, L>
{	
	private PhaseId m_phaseId = PhaseId.ANY_PHASE;
	private PhaseId m_origPhaseId ;
	
	//
	// Constructor(s)
	//
	protected DsfPhaseEvent(final DNode node, final Class<L> listenerType) {
		super(node, listenerType);
	}

	//
	// API
	//
	/**
	 * This overide method is simply to avoid Eclipse IDE bug.
	 * Without this override, getSource() call outside this class
	 * will be treated as if it returns Object, not DNode.
	 */
	@Override
	public DNode getSource() {
		return super.getSource();
	}
	
	public PhaseId getPhaseId() {
		return m_phaseId;
	}

	public void setPhaseId(final PhaseId phaseId) {
		if (phaseId == null) {
			throw new DsfRuntimeException("PhaseId must not be null") ;
		}
		m_phaseId = phaseId;
	}

	public boolean shouldFire(final PhaseId phaseId) {
		if (m_phaseId == PhaseId.ANY_PHASE || m_phaseId == phaseId) {
			return true;
		}
		return false;
	}
	
	public boolean shouldDelete(final PhaseId phaseId) {
		if (m_phaseId == PhaseId.ANY_PHASE || m_phaseId == phaseId) {
			return true;
		}
		return false;
	}

	public PhaseId getOriginatingPhaseId() {
		return m_origPhaseId;
	}
	
	protected void setOriginatingPhaseId(final PhaseId phaseId) {
		m_origPhaseId = phaseId;
	}
}

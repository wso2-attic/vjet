/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic;

import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstProperty;


public class VjoPropertyStatesTable {

	private Map<IJstProperty, VjoPropertyState> m_ptyStateMap;
	
	public void reference(final IJstProperty pty){
		if(m_ptyStateMap == null){
			m_ptyStateMap = new HashMap<IJstProperty, VjoPropertyState>();
		}
		
		final VjoPropertyState state = m_ptyStateMap.get(pty);
		if(state == null
				|| VjoPropertyState.DECLARED.equals(state)){
			m_ptyStateMap.put(pty, VjoPropertyState.REFERENCED);
		}
		else if(VjoPropertyState.ASSIGNED.equals(state)){
			m_ptyStateMap.put(pty, VjoPropertyState.REFERENCED_AND_ASSIGNED);
		}
	}
	
	public void assign(final IJstProperty pty){
		if(m_ptyStateMap == null){
			m_ptyStateMap = new HashMap<IJstProperty, VjoPropertyState>();
		}
		
		final VjoPropertyState state = m_ptyStateMap.get(pty);
		if(state == null || VjoPropertyState.DECLARED.equals(state)){
			m_ptyStateMap.put(pty, VjoPropertyState.ASSIGNED);
		}
		else if(VjoPropertyState.REFERENCED.equals(state)){
			m_ptyStateMap.put(pty, VjoPropertyState.REFERENCED_AND_ASSIGNED);
		}
	}
	
	public boolean hasReferences(final IJstProperty pty){
		if(m_ptyStateMap != null){
			final VjoPropertyState state = m_ptyStateMap.get(pty);
			if(state != null){
				return VjoPropertyState.REFERENCED.equals(state)
					|| VjoPropertyState.REFERENCED_AND_ASSIGNED.equals(state);
			}
		}
		
		return false;
	}
	
	public boolean hasAssigned(final IJstProperty pty){
		if(m_ptyStateMap != null){
			final VjoPropertyState state = m_ptyStateMap.get(pty);
			if(state != null){
				return VjoPropertyState.ASSIGNED.equals(state)
				|| VjoPropertyState.REFERENCED_AND_ASSIGNED.equals(state);
			}
		}
		
		return false;
	}
	
	public static enum VjoPropertyState{
		DECLARED,
		REFERENCED,
		ASSIGNED,
		REFERENCED_AND_ASSIGNED
	}
}

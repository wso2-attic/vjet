/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.kernel.stage;

import java.util.EventObject;

public abstract class BaseStageEvent<SourceType, ListenerType extends IStageEventListener> 
	extends EventObject
{
	private final Class<ListenerType> m_listenerType;
	
	//
	// Constructor(s)
	//
	protected BaseStageEvent(final SourceType source, final Class<ListenerType> clz) {
		super(source);
		m_listenerType = clz;
	}
	
	@SuppressWarnings("unchecked")	// We can trust the <S> so its ok
	@Override
	public SourceType getSource() {
		return (SourceType)super.getSource() ;
	}
	
	//
	// Framework
	//
	public boolean isAppropriateListener(final IStageEventListener listener) {
		// default impl says if your the right type of lister your ok
		return m_listenerType.isInstance(listener);
	}
	
	public abstract void processListener(final ListenerType listener)
		/*throws AbortDsfEventProcessingException*/;
}

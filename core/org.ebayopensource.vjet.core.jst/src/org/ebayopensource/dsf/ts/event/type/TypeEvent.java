/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.event.type;

import org.ebayopensource.dsf.ts.event.ISourceEvent;
import org.ebayopensource.dsf.ts.event.ISourceEventListener;
import org.ebayopensource.dsf.ts.event.dispatch.IEventListenerHandle;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.dsf.common.Z;

public abstract class TypeEvent implements ISourceEvent<IEventListenerHandle> {
	
	private TypeName m_typeName;
	
	//
	// Constructors
	//
	TypeEvent(TypeName typeName){
		m_typeName = typeName;
	}

	//
	// Satisfy ISourceEvent
	//
	public boolean isAppropriateListener(ISourceEventListener listener){
		return listener instanceof ITypeEventListener;
	}

	//
	// API
	//
	public TypeName getTypeName(){
		return m_typeName;
	}
	
	@Override
	public String toString(){
		Z z = new Z();
		z.format("eventType", getClass().getSimpleName());
		z.format("m_typeName", m_typeName);
		return z.toString();
	}
}

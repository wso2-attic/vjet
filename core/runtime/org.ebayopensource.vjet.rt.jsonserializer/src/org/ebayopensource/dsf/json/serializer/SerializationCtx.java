/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.json.serializer;

import org.ebayopensource.dsf.common.context.BaseSubCtx;
import org.ebayopensource.dsf.common.context.ContextHelper;
import org.ebayopensource.dsf.common.context.DsfCtx;

/**
 * Provide thread-based control on JSON serialization:
 * (1) force BeanSerializer to emit classhints
 */
public class SerializationCtx extends BaseSubCtx {

	public SerializationCtx() {}

	public static SerializationCtx setCtx(final SerializationCtx ctx) {
		CtxAssociator.setCtx(ctx);
		return ctx;
	}

	public static SerializationCtx ctx() {
		SerializationCtx ctx = CtxAssociator.getCtx();
		return (ctx != null) ? ctx : setCtx(new SerializationCtx());
	}
		
	private boolean m_forceClassHintsInBeanMashalling = false;;

	public boolean isForceClassHintsInBeanMashalling() {
		return m_forceClassHintsInBeanMashalling;
	}
	
	public void setForceClassHintsInBeanMashalling(boolean set) {
		m_forceClassHintsInBeanMashalling = set;
	}
	
	private static class CtxAssociator extends ContextHelper {		
		private static final String CTX_NAME = SerializationCtx.class.getSimpleName();

		protected static SerializationCtx getCtx() {
			return (SerializationCtx)getSubCtx(DsfCtx.ctx(), CTX_NAME);
		}
		protected static void setCtx(final SerializationCtx ctx) {
			setSubCtx(DsfCtx.ctx(),CTX_NAME,ctx);
		}
	}
}

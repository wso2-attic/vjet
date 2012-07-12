/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.trace;

import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.common.context.BaseSubCtx;
import org.ebayopensource.dsf.common.context.ContextHelper;
import org.ebayopensource.dsf.common.context.DsfCtx;
import org.ebayopensource.dsf.common.xml.IXmlStreamWriter;
import org.ebayopensource.dsf.dom.DNode;


/*
 * DataModelHelper is designed as a singleton class. Any TLS information for 
 * current thread is stored in DataModelCtx.
 */
public class DataModelCtx extends BaseSubCtx {
	private static final String CTX_NAME = DataModelCtx.class.getSimpleName();
	public static final String SpyDataModelId="spydatamodelid";
	
	private IXmlStreamWriter m_writer;
	private HashMap<DNode,Object> m_componentModelMap= new HashMap<DNode,Object>();
                     //map of <component,datamodel>, for spyglass integration
	private DataModelCtx() {
	}

	static public DataModelCtx getCtx() {
		return DataModelCtxBuilder.getCtx();
	}

	static public void setCtx(DataModelCtx ctx) {
		DataModelCtxBuilder.setCtx(ctx);
	}
	
	public static String getCtxName() {
		return CTX_NAME;
	}


	public IXmlStreamWriter getWriter() {
		return m_writer;
	}

	public void setWriter(IXmlStreamWriter writer) {
		this.m_writer = writer;
	}

	/**
	 * limit to 1 model for each component
	 * @component the v4 component
	 * @model v4 application should not pass in model proxy for this parameter.
	 *        instead it should be the model object itself.
	 */
	public void addComponentAndModel(DNode component, Object model) {
		if (component!=null && model!=null)
			m_componentModelMap.put(component,model);
	}

	/**
	 * 
	 * @return the map of <v4-Component,v4-Model> registed by 
	 *         DataModelHelper.tracedataModel(component,model)
	 */
	public Map<DNode,Object>  getComponentModelMap() {
		return m_componentModelMap;
	}

	/*
	 * DataModelCtx factory class.
	 * The DataModelCtx is stored in DsfContext as a sub context for current thread.
	 */
	protected static class DataModelCtxBuilder extends ContextHelper {
		protected static DataModelCtx getCtx() {
			DataModelCtx ctx = (DataModelCtx)getSubCtx(DsfCtx.ctx(), DataModelCtx.getCtxName() );
			if (ctx==null) {
				ctx = new DataModelCtx();
				setCtx(ctx);
			}
			return ctx;
		}
		
		protected static void setCtx(final DataModelCtx ctx) {
			setSubCtx(DsfCtx.ctx(), DataModelCtx.getCtxName(), ctx);
		}
	}	
}

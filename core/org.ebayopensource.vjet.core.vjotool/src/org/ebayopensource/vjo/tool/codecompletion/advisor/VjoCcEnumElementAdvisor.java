/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor;

import java.util.Iterator;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;

/**
 * Advise enum element from etype values
 * this.vj$.E.e1, e1 is a value defined in etype (E1)
 * 
 * Need attributes:
 * 1. ctx.calledType
 * 2. ctx.actingToken
 * 
 *
 */
public class VjoCcEnumElementAdvisor extends AbstractVjoCcAdvisor implements
		IVjoCcAdvisor {
	public static final String ID = VjoCcEnumElementAdvisor.class.getName();

	public void advise(VjoCcCtx ctx) {
		IJstType calledType = ctx.getCalledType();
		if (!calledType.isEnum()) {
			return;
		}
		List<IJstProperty> properties = calledType.getEnumValues();
		Iterator<IJstProperty> it1 = properties.iterator();
		while (it1.hasNext()) {
			IJstProperty property = it1.next();
			if (property.getName().getName().startsWith(ctx.getActingToken())) {
				appendData(ctx, property);
			}
		}
	}

}

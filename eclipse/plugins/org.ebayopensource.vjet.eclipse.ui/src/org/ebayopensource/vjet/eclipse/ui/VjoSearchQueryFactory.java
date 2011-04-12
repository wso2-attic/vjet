/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.ui;

import org.eclipse.dltk.mod.ui.search.ISearchQueryFactory;
import org.eclipse.dltk.mod.ui.search.QuerySpecification;
import org.eclipse.search.ui.ISearchQuery;

/**
 * This class create {@link VjoSearchQuery} object for specified {@link QuerySpecification} object.
 * 
 * 
 *
 */
public class VjoSearchQueryFactory implements ISearchQueryFactory{

	
	public ISearchQuery createQuery(QuerySpecification specification){
		VjoSearchQuery query = new VjoSearchQuery(specification);
		//DLTKSearchQuery query = new DLTKSearchQuery(specification);
		return query;		
	}
}


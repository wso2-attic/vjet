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

import org.ebayopensource.dsf.jst.IJstMethod;


public class VjoMethodReferencesTable {

	private Map<IJstMethod, Integer> m_mtdRefMap;
	
	public void reference(final IJstMethod mtd){
		if(m_mtdRefMap == null){
			m_mtdRefMap = new HashMap<IJstMethod, Integer>();
		}
		
		final Integer count = m_mtdRefMap.get(mtd);
		if(count == null){
			m_mtdRefMap.put(mtd, Integer.valueOf(1));
		}
		else{
			m_mtdRefMap.put(mtd, Integer.valueOf(count.intValue() + 1));
		}
	}
	
	public boolean hasReferences(final IJstMethod mtd){
		return getReferencesCount(mtd) > 0;
	}
	
	public int getReferencesCount(final IJstMethod mtd){
		if(m_mtdRefMap == null){
			return 0;
		}
		final Integer count = m_mtdRefMap.get(mtd);
		if(count == null){
			return 0;
		}
		else{
			return count.intValue();
		}
	}
}

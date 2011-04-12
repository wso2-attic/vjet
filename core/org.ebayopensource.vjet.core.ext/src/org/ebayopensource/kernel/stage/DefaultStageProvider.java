/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.kernel.stage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DefaultStageProvider<T> implements IStageProvider<T> {
	
	private Map<T,IStage<T>> m_stages = new HashMap<T,IStage<T>>();
	
	//
	// Satisfy IStageProvider
	//
	/**
	 * Add given stage to the internal registry.
	 * @param stage IStage
	 * @exception DarwinRuntimeException if stage or stage id is null
	 */
	public DefaultStageProvider<T> add(final IStage<T> stage){
		if (stage == null){
			throw new RuntimeException("stage is null");
		}
		
		if (stage.getId() == null){
			throw new RuntimeException("stage id is null");
		}
		
		if (m_stages.containsKey(stage.getId())){
			throw new RuntimeException(
				"there is already stage registered with this id:" + stage.getId());
		}
		
		m_stages.put(stage.getId(), stage);
		return this;
	}
	
	/**
	 * Answer the stage with given id.
	 * @param stageId stageId
	 * @return IStage
	 * @exception DarwinRuntimeException if stageId is null.
	 */
	public IStage<T> get(final T stageId){
		
		if (stageId == null){
			throw new RuntimeException("stageId is null");
		}
	
		return m_stages.get(stageId);
	}
	
	public Map<T,IStage<T>> getAll(){
		return Collections.unmodifiableMap(m_stages);
	}
	
	public boolean remove(final T stageId) {
		return m_stages.remove(stageId) != null ;
//		if (stageId == null || !m_stages.keySet().contains(stageId)){
//			return false;
//		}
//
//		m_stages.remove(stageId);
//		return true;
	}
	
	public void clear(){
		m_stages.clear();
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("StageIds: ");
		for (T id: m_stages.keySet()){
			sb.append(id).append(", ");
		}
		
		return sb.toString();
	}
}

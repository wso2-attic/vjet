/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.kernel.stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Please be aware that this class is NOT R/W THREAD SAFE
 *
 * @param <T>
 */
public class StageGraph<T>{
	
	private List<Path<T>> m_paths = new ArrayList<Path<T>>(5);
	
	private DefaultStageProvider<T> m_provider = new DefaultStageProvider<T>();
	
	//
	// API
	//
	public Path<T> newPath(){
		Path<T> path = new Path<T>(this);
		add(path);
		return path;
	}
	
	public Path<T> getPath(final T stageId){
		if (stageId == null || m_paths.isEmpty()){
			return null;
		}
		for (Path<T> path: m_paths){
			if (path.contains(stageId)){
				return path;
			}
		}
		return null;
	}
	
	public Path<T> getCurrentPath(){
		if (m_paths.isEmpty()){
			return newPath();
		}
		return m_paths.get(m_paths.size()-1);
	}
	
	public void add(final T stageId){
		getCurrentPath().add(stageId);
	}
	
	public void add(final IStage<T> stage){
		getCurrentPath().add(stage);
	}
	
	public boolean contains(final T stageId){
		if (stageId == null || m_paths.isEmpty()){
			return false;
		}
		for (Path<T> path: m_paths){
			if (path.contains(stageId)){
				return true;
			}
		}
		return false;
	}
	
	public T getFirst(){
		if (m_paths.isEmpty()){
			return null;
		}
		Path<T> path = m_paths.get(0);
		if (path != null){
			return path.getHead();
		}
		return null;
	}
	
	public T getNext(final T stageId){
		if (stageId == null){
			return null;
		}
		
		final Path<T> path = getPath(stageId);
		if (path == null){
			return null;
		}
	
		return path.getNext(stageId);
	}
	
	public IStage<T> get(final T id){
		if (id == null){
			throw new RuntimeException("id is null");
		}
		return m_provider.get(id);
	}
	
	public Map<T,IStage<T>> getAll(){
		return m_provider.getAll();
	}
	
	public List<Path<T>> getAllPaths(){
		if (m_paths == null || m_paths.isEmpty()){
			return Collections.emptyList();
		}
		else {
			return Collections.unmodifiableList(m_paths);
		}
	}
	
	public boolean isEmpty(){
		if (m_paths.isEmpty()){
			return true;
		}
		for (Path<T> path: m_paths){
			if (!path.isEmpty()){
				return false;
			}
		}
		return true;
	}
	
	public void clearAll(){
		m_paths.clear();
		m_provider.clear();
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		for (Path<T> path: m_paths){
			sb.append(path);	
			if (path.getLinkTo() != null){
				sb.append(" => ");
				sb.append(path.getLinkTo());	
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	//
	// Protected
	//
	protected void register(final IStage<T> stage){
		m_provider.add(stage);
	}
	
	protected void add(Path<T> path){
		if (path == null){
			throw new RuntimeException("path is null");
		}
		m_paths.add(path);
	}

	//
	// Helpers
	//
	public static class Hook<T> {
		private final T m_fromAddr;
		private List<T> m_toAddrs;
		
		//
		// Constructor
		//
		Hook(final T fromAddr){
			m_fromAddr = fromAddr;
		}
		
		//
		// API
		//
		public T getFromAddr(){
			return m_fromAddr;
		}
		
		public List<T> getToAddrs(){
			if (m_toAddrs == null){
				return Collections.emptyList();
			}
			return Collections.unmodifiableList(m_toAddrs);
		}
		
		//
		// Package protected
		//
		void addToAddr(final T toAddr){
			if (m_toAddrs == null){
				m_toAddrs = new ArrayList<T>();
			}
			m_toAddrs.add(toAddr);
		}
	}
	
	public static class Path<T> {
		private StageGraph<T> m_graph;
		private List<T> m_stageIds = new ArrayList<T>(5);
		private T m_linkTo;
		
		//
		// Constructor
		//
		protected Path(final StageGraph<T> graph){
			if (graph == null){
				throw new RuntimeException("graph is null");
			}
			m_graph = graph;
		}
		
		//
		// API
		//
		public boolean isEmpty(){
			return m_stageIds.isEmpty();
		}
		
		public boolean contains(final T stageId){
			return m_stageIds.contains(stageId);
		}
		
		public int indexOf(final T stageId){
			return m_stageIds.indexOf(stageId);
		}
		
		public int size(){
			return m_stageIds.size();
		}
		
		public T getHead(){
			if (m_stageIds.isEmpty()){
				return null;
			}
			return m_stageIds.get(0);
		}
		
		public T getNext(final T stageId){
			int index = indexOf(stageId);
			if (index < 0 || index == m_stageIds.size() - 1){
				return null;
			}
			return m_stageIds.get(++index);
		}
		
		public T getTail(){
			if (m_stageIds.isEmpty()){
				return null;
			}
			return m_stageIds.get(m_stageIds.size() - 1);
		}
		
		public T getLinkTo(){
			return m_linkTo;
		}
		
		public Path<T> add(final T stageId){
			if (stageId == null){
				throw new RuntimeException("stageId is null");
			}
			if (m_graph.contains(stageId)){
				throw new RuntimeException("Already exists: " + stageId);
			}
			m_stageIds.add(stageId);
			return this;
		}
		
		public Path<T> add(final IStage<T> stage){
			if (stage == null){
				throw new RuntimeException("stage is null");
			}
			m_graph.register(stage);
			return add(stage.getId());
		}
		
		public Path<T> add(final int index, final IStage<T> stage){
			if (stage == null){
				throw new RuntimeException("stage is null");
			}
			m_graph.register(stage);
			return add(index, stage.getId());
		}

		public Path<T> add(final int index, final T stageId) {
			if (stageId == null){
				throw new RuntimeException("stageId is null");
			}
			if (m_graph.contains(stageId)){
				throw new RuntimeException("Already exists: " + stageId);
			}
			m_stageIds.add(index, stageId);
			return this;
		}

		public Path<T> insert(final T refStageId, final T newStageId) {
			if (refStageId == null){
				throw new RuntimeException("refStageId is null");
			}
			int index = m_stageIds.indexOf(refStageId);
			if (index < 0){
				throw new RuntimeException("refStageId not found: " + refStageId);
			}
			return add(index, newStageId);
		}
		
		public Path<T> insert(final T refStageId, final IStage<T> newStage){
			if (refStageId == null){
				throw new RuntimeException("refStageId is null");
			}
			int index = m_stageIds.indexOf(refStageId);
			if (index < 0){
				throw new RuntimeException("refStageId not found: " + refStageId);
			}
			return add(index, newStage);
		}

		public void linkTo(final T next){
			if (next == null){
				throw new RuntimeException("toAddr is null");
			}
			m_linkTo = next;
		}
		
		public void linkTo(final Path<T> path){
			if (path == null){
				throw new RuntimeException("path is null");
			}
			linkTo(path.getHead());
		}
		
		public List<T> getAll(){
			if (m_stageIds == null || m_stageIds.isEmpty()){
				return Collections.emptyList();
			}
			return Collections.unmodifiableList(m_stageIds);
		}
		
		@Override
		public String toString(){
			StringBuilder sb = new StringBuilder();
			T head = getHead();
			sb.append(head);	
			T stageId = getNext(head);
			while (stageId != null){
				sb.append(" -> ");
				sb.append(stageId);	
				stageId = getNext(stageId);
			}
			return sb.toString();
		}
		
		//
		// Protected
		//
		protected StageGraph<T> getGraph(){
			return m_graph;
		}
	}
}

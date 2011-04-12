/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPostAllChildrenListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPostChildListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPreAllChildrenListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationPreChildListener;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationVisitorEvent;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.IVjoValidationVisitorEventDispatcher;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.visitor.VjoValidationVisitorState;
import org.ebayopensource.dsf.jst.IJstNode;

/**
 * 
 * 
 * <p>
 * 	VjoSemanticValidatorRepo serves as the registration center and visitor event dispatcher for all validators
 * </p>
 */
public class VjoSemanticValidatorRepo implements IVjoValidationVisitorEventDispatcher{

	private static final VjoSemanticValidatorRepo s_instance = new VjoSemanticValidatorRepo();
	
	public static VjoSemanticValidatorRepo getInstance() {
		return s_instance;
	}

	static{
		s_instance.registerListener(new VjoFieldAccessExprValidator());
		s_instance.registerListener(new VjoMtdInvocationExprValidator());
		s_instance.registerListener(new VjoAssignmentExprValidator());
		s_instance.registerListener(new VjoCastExprValidator());
		s_instance.registerListener(new VjoJstVarsValidator());
		s_instance.registerListener(new VjoBoolExprValidator());
		s_instance.registerListener(new VjoParenthesizedExprValidator());
		s_instance.registerListener(new VjoInfixExprValidator());
		s_instance.registerListener(new VjoPostfixExprValidator());
		s_instance.registerListener(new VjoPrefixExprValidator());
		s_instance.registerListener(new VjoConditionalExprValidator());
		s_instance.registerListener(new VjoSwitchStmtValidator());
		s_instance.registerListener(new VjoCaseStmtValidator());
		s_instance.registerListener(new VjoThrowStmtValidator());
		s_instance.registerListener(new VjoRtnStmtValidator());
		s_instance.registerListener(new VjoObjLiteralValidator());
		s_instance.registerListener(new VjoWithStmtValidator());
		s_instance.registerListener(new VjoFuncExprValidator());
		s_instance.registerListener(new VjoJstTypeValidator());
		s_instance.registerListener(new VjoJstMethodValidator());
		s_instance.registerListener(new VjoJstConstructorValidator());
		s_instance.registerListener(new VjoJstPropertyValidator());
		s_instance.registerListener(new VjoJstBlockValidator());
		s_instance.registerListener(new VjoJstArrayInitializerValidator());
		s_instance.registerListener(new VjoObjCreationExprValidator());
		s_instance.registerListener(new VjoArrayAccessExprValidator());
		s_instance.registerListener(new VjoArrayLiteralValidator());
		s_instance.registerListener(new VjoIfStmtValidator());
		s_instance.registerListener(new VjoForInStmtValidator());
		s_instance.registerListener(new VjoJstIdentifierValidator());
		s_instance.registerListener(new VjoBreakStmtValidator());
		s_instance.registerListener(new VjoContinueStmtValidator());
		s_instance.registerListener(new VjoLabeledStmtValidator());
		s_instance.registerListener(new VjoCatchStmtValidator());
	}

	private Map<Class<? extends IJstNode>, Map<VjoValidationVisitorState, List<IVjoValidationListener>>> m_nodeType2ListenerMap = 
		new HashMap<Class<? extends IJstNode>, Map<VjoValidationVisitorState, List<IVjoValidationListener>>>(64);

	private Map<IJstNode, Map<VjoValidationVisitorState, List<IVjoValidationListener>>> m_node2PreListenerMap = 
		new HashMap<IJstNode, Map<VjoValidationVisitorState, List<IVjoValidationListener>>>(2);
	
	private Map<IJstNode, Map<VjoValidationVisitorState, List<IVjoValidationListener>>> m_node2PostListenerMap = 
		new HashMap<IJstNode, Map<VjoValidationVisitorState, List<IVjoValidationListener>>>(2);
	
//	@SuppressWarnings("unused")
//	private Map<String, List<String>> m_groupBrowserFilterMap = new HashMap<String, List<String>>(100);
	
	public void registerListener(IVjoValidationListener listener){
		if(listener == null){
			return;
		}
		if(listener instanceof IVjoValidationPreAllChildrenListener){
			registerPreAllChildrenListener(listener);
		}
		if(listener instanceof IVjoValidationPreChildListener){
			registerPreChildListener(listener);
		}
		if(listener instanceof IVjoValidationPostChildListener){
			registerPostChildListener(listener);
		}
		if(listener instanceof IVjoValidationPostAllChildrenListener){
			registerPostAllChildrenListener(listener);
		}
	}
	
	private Map<Class<? extends IJstNode>, Boolean> m_deactivationMap = new HashMap<Class<? extends IJstNode>, Boolean>();
	
	public void deactivateListener(final Class<? extends IJstNode> targetType){
		m_deactivationMap.put(targetType, Boolean.TRUE);
	}
	
	public void activateListener(final Class<? extends IJstNode> targetType){
		m_deactivationMap.put(targetType, Boolean.FALSE);
	}
	
	public void deactivateListeners(){
		if(m_nodeType2ListenerMap != null){
			for(Class<? extends IJstNode> targetType: m_nodeType2ListenerMap.keySet()){
				m_deactivationMap.put(targetType, Boolean.TRUE);
			}
		}
	}
	
	public void activateListeners(){
		if(m_nodeType2ListenerMap != null){
			for(Class<? extends IJstNode> targetType: m_nodeType2ListenerMap.keySet()){
				m_deactivationMap.put(targetType, Boolean.FALSE);
			}
		}
	}
	
	public void registerListener(IJstNode node, IVjoValidationListener listener){
		if(listener == null){
			return;
		}
		if(listener instanceof IVjoValidationPreAllChildrenListener){
			registerPreAllChildrenListener(node, listener);
		}
		if(listener instanceof IVjoValidationPreChildListener){
			registerPreChildListener(node, listener);
		}
		if(listener instanceof IVjoValidationPostChildListener){
			registerPostChildListener(node, listener);
		}
		if(listener instanceof IVjoValidationPostAllChildrenListener){
			registerPostAllChildrenListener(node, listener);
		}
	}
	
	
	public void appendListener(IJstNode node, IVjoValidationListener listener){
		if(listener == null){
			return;
		}
		if(listener instanceof IVjoValidationPreAllChildrenListener){
			appendPreAllChildrenListener(node, listener);
		}
		if(listener instanceof IVjoValidationPreChildListener){
			appendPreChildListener(node, listener);
		}
		if(listener instanceof IVjoValidationPostChildListener){
			appendPostChildListener(node, listener);
		}
		if(listener instanceof IVjoValidationPostAllChildrenListener){
			appendPostAllChildrenListener(node, listener);
		}
	}
	
	public void registerPreAllChildrenListener(IVjoValidationListener listener){
		if(listener == null || !(listener instanceof IVjoValidationPreAllChildrenListener)){
			return;
		}
		
		for(Class<? extends IJstNode> nodeType :  listener.getTargetNodeTypes()){
			Map<VjoValidationVisitorState, List<IVjoValidationListener>> listenerMap = m_nodeType2ListenerMap.get(nodeType);
			if(listenerMap == null){
				listenerMap = new HashMap<VjoValidationVisitorState, List<IVjoValidationListener>>();
				m_nodeType2ListenerMap.put(nodeType, listenerMap);
			}
			List<IVjoValidationListener> listeners = listenerMap.get(VjoValidationVisitorState.BEFORE_ALL_CHILDREN);
			if(listeners == null){
				listeners = new ArrayList<IVjoValidationListener>();
				listenerMap.put(VjoValidationVisitorState.BEFORE_ALL_CHILDREN, listeners);
			}
			listeners.add(listener);
		}
	}
	
	public void registerPreAllChildrenListener(IJstNode node, IVjoValidationListener listener){
		if(listener == null || !(listener instanceof IVjoValidationPreAllChildrenListener)){
			return;
		}
		
		Map<VjoValidationVisitorState, List<IVjoValidationListener>> listenerMap = m_node2PreListenerMap.get(node);
		if(listenerMap == null){
			listenerMap = new HashMap<VjoValidationVisitorState, List<IVjoValidationListener>>();
			m_node2PreListenerMap.put(node, listenerMap);
		}
		List<IVjoValidationListener> listeners = listenerMap.get(VjoValidationVisitorState.BEFORE_ALL_CHILDREN);
		if(listeners == null){
			listeners = new ArrayList<IVjoValidationListener>();
			listenerMap.put(VjoValidationVisitorState.BEFORE_ALL_CHILDREN, listeners);
		}
		listeners.add(listener);
	}
	
	public void appendPreAllChildrenListener(IJstNode node, IVjoValidationListener listener){
		if(listener == null || !(listener instanceof IVjoValidationPreAllChildrenListener)){
			return;
		}
		
		Map<VjoValidationVisitorState, List<IVjoValidationListener>> listenerMap = m_node2PostListenerMap.get(node);
		if(listenerMap == null){
			listenerMap = new HashMap<VjoValidationVisitorState, List<IVjoValidationListener>>();
			m_node2PostListenerMap.put(node, listenerMap);
		}
		List<IVjoValidationListener> listeners = listenerMap.get(VjoValidationVisitorState.BEFORE_ALL_CHILDREN);
		if(listeners == null){
			listeners = new ArrayList<IVjoValidationListener>();
			listenerMap.put(VjoValidationVisitorState.BEFORE_ALL_CHILDREN, listeners);
		}
		listeners.add(listener);
	}
	
	public void registerPreChildListener(IVjoValidationListener listener){
		if(listener == null || !(listener instanceof IVjoValidationPreChildListener)){
			return;
		}
		
		for(Class<? extends IJstNode> nodeType :  listener.getTargetNodeTypes()){
			Map<VjoValidationVisitorState, List<IVjoValidationListener>> listenerMap = m_nodeType2ListenerMap.get(nodeType);
			if(listenerMap == null){
				listenerMap = new HashMap<VjoValidationVisitorState, List<IVjoValidationListener>>();
				m_nodeType2ListenerMap.put(nodeType, listenerMap);
			}
			List<IVjoValidationListener> listeners = listenerMap.get(VjoValidationVisitorState.BEFORE_CHILD);
			if(listeners == null){
				listeners = new ArrayList<IVjoValidationListener>();
				listenerMap.put(VjoValidationVisitorState.BEFORE_CHILD, listeners);
			}
			listeners.add(listener);
		}
	}
	
	public void registerPreChildListener(IJstNode node, IVjoValidationListener listener){
		if(listener == null || !(listener instanceof IVjoValidationPreChildListener)){
			return;
		}
		
		Map<VjoValidationVisitorState, List<IVjoValidationListener>> listenerMap = m_node2PreListenerMap.get(node);
		if(listenerMap == null){
			listenerMap = new HashMap<VjoValidationVisitorState, List<IVjoValidationListener>>();
			m_node2PreListenerMap.put(node, listenerMap);
		}
		List<IVjoValidationListener> listeners = listenerMap.get(VjoValidationVisitorState.BEFORE_CHILD);
		if(listeners == null){
			listeners = new ArrayList<IVjoValidationListener>();
			listenerMap.put(VjoValidationVisitorState.BEFORE_CHILD, listeners);
		}
		listeners.add(listener);
	}

	public void appendPreChildListener(IJstNode node, IVjoValidationListener listener){
		if(listener == null || !(listener instanceof IVjoValidationPreChildListener)){
			return;
		}
		
		Map<VjoValidationVisitorState, List<IVjoValidationListener>> listenerMap = m_node2PostListenerMap.get(node);
		if(listenerMap == null){
			listenerMap = new HashMap<VjoValidationVisitorState, List<IVjoValidationListener>>();
			m_node2PostListenerMap.put(node, listenerMap);
		}
		List<IVjoValidationListener> listeners = listenerMap.get(VjoValidationVisitorState.BEFORE_CHILD);
		if(listeners == null){
			listeners = new ArrayList<IVjoValidationListener>();
			listenerMap.put(VjoValidationVisitorState.BEFORE_CHILD, listeners);
		}
		listeners.add(listener);
	}
	
	public void registerPostChildListener(IVjoValidationListener listener){
		if(listener == null || !(listener instanceof IVjoValidationPostChildListener)){
			return;
		}
		
		for(Class<? extends IJstNode> nodeType :  listener.getTargetNodeTypes()){
			Map<VjoValidationVisitorState, List<IVjoValidationListener>> listenerMap = m_nodeType2ListenerMap.get(nodeType);
			if(listenerMap == null){
				listenerMap = new HashMap<VjoValidationVisitorState, List<IVjoValidationListener>>();
				m_nodeType2ListenerMap.put(nodeType, listenerMap);
			}
			List<IVjoValidationListener> listeners = listenerMap.get(VjoValidationVisitorState.AFTER_CHILD);
			if(listeners == null){
				listeners = new ArrayList<IVjoValidationListener>();
				listenerMap.put(VjoValidationVisitorState.AFTER_CHILD, listeners);
			}
			listeners.add(listener);
		}
	}
	
	public void registerPostChildListener(IJstNode node, IVjoValidationListener listener){
		if(listener == null || !(listener instanceof IVjoValidationPostChildListener)){
			return;
		}
		
		Map<VjoValidationVisitorState, List<IVjoValidationListener>> listenerMap = m_node2PreListenerMap.get(node);
		if(listenerMap == null){
			listenerMap = new HashMap<VjoValidationVisitorState, List<IVjoValidationListener>>();
			m_node2PreListenerMap.put(node, listenerMap);
		}
		List<IVjoValidationListener> listeners = listenerMap.get(VjoValidationVisitorState.AFTER_CHILD);
		if(listeners == null){
			listeners = new ArrayList<IVjoValidationListener>();
			listenerMap.put(VjoValidationVisitorState.AFTER_CHILD, listeners);
		}
		listeners.add(listener);
	}

	public void appendPostChildListener(IJstNode node, IVjoValidationListener listener){
		if(listener == null || !(listener instanceof IVjoValidationPostChildListener)){
			return;
		}
		
		Map<VjoValidationVisitorState, List<IVjoValidationListener>> listenerMap = m_node2PostListenerMap.get(node);
		if(listenerMap == null){
			listenerMap = new HashMap<VjoValidationVisitorState, List<IVjoValidationListener>>();
			m_node2PostListenerMap.put(node, listenerMap);
		}
		List<IVjoValidationListener> listeners = listenerMap.get(VjoValidationVisitorState.AFTER_CHILD);
		if(listeners == null){
			listeners = new ArrayList<IVjoValidationListener>();
			listenerMap.put(VjoValidationVisitorState.AFTER_CHILD, listeners);
		}
		listeners.add(listener);
	}
	
	public void registerPostAllChildrenListener(IVjoValidationListener listener){
		if(listener == null || !(listener instanceof IVjoValidationPostAllChildrenListener)){
			return;
		}
		
		for(Class<? extends IJstNode> nodeType :  listener.getTargetNodeTypes()){
			Map<VjoValidationVisitorState, List<IVjoValidationListener>> listenerMap = m_nodeType2ListenerMap.get(nodeType);
			if(listenerMap == null){
				listenerMap = new HashMap<VjoValidationVisitorState, List<IVjoValidationListener>>();
				m_nodeType2ListenerMap.put(nodeType, listenerMap);
			}
			List<IVjoValidationListener> listeners = listenerMap.get(VjoValidationVisitorState.AFTER_ALL_CHILDREN);
			if(listeners == null){
				listeners = new ArrayList<IVjoValidationListener>();
				listenerMap.put(VjoValidationVisitorState.AFTER_ALL_CHILDREN, listeners);
			}
			listeners.add(listener);
		}
	}
	
	public void removePostAllChildrenListener(IVjoValidationListener listener){
		if(listener == null || !(listener instanceof IVjoValidationPostAllChildrenListener)){
			return;
		}
		
		for(Class<? extends IJstNode> nodeType :  listener.getTargetNodeTypes()){
			Map<VjoValidationVisitorState, List<IVjoValidationListener>> listenerMap = m_nodeType2ListenerMap.get(nodeType);
			if(listenerMap != null){
				final List<IVjoValidationListener> listeners = listenerMap.get(VjoValidationVisitorState.AFTER_ALL_CHILDREN);
				if(listeners != null){
					listeners.remove(listener);
				}
			}
		}
	}
	

	public void removePreAllChildrenListener(IVjoValidationListener listener){
		if(listener == null || !(listener instanceof IVjoValidationPreAllChildrenListener)){
			return;
		}
		
		for(Class<? extends IJstNode> nodeType :  listener.getTargetNodeTypes()){
			Map<VjoValidationVisitorState, List<IVjoValidationListener>> listenerMap = m_nodeType2ListenerMap.get(nodeType);
			if(listenerMap != null){
				final List<IVjoValidationListener> listeners = listenerMap.get(VjoValidationVisitorState.BEFORE_ALL_CHILDREN);
				if(listeners != null){
					listeners.remove(listener);
				}
			}
		}
	}
	
	public void removePostChildListener(IVjoValidationListener listener){
		if(listener == null || !(listener instanceof IVjoValidationPostChildListener)){
			return;
		}
		
		for(Class<? extends IJstNode> nodeType :  listener.getTargetNodeTypes()){
			Map<VjoValidationVisitorState, List<IVjoValidationListener>> listenerMap = m_nodeType2ListenerMap.get(nodeType);
			if(listenerMap != null){
				final List<IVjoValidationListener> listeners = listenerMap.get(VjoValidationVisitorState.AFTER_CHILD);
				if(listeners != null){
					listeners.remove(listener);
				}
			}
		}
	}
	
	public void removePreChildListener(IVjoValidationListener listener){
		if(listener == null || !(listener instanceof IVjoValidationPreChildListener)){
			return;
		}

		for(Class<? extends IJstNode> nodeType :  listener.getTargetNodeTypes()){
			Map<VjoValidationVisitorState, List<IVjoValidationListener>> listenerMap = m_nodeType2ListenerMap.get(nodeType);
			if(listenerMap != null){
				final List<IVjoValidationListener> listeners = listenerMap.get(VjoValidationVisitorState.BEFORE_CHILD);
				if(listeners != null){
					listeners.remove(listener);
				}
			}
		}
	}
	
	public void registerPostAllChildrenListener(IJstNode node, IVjoValidationListener listener){
		if(listener == null || !(listener instanceof IVjoValidationPostAllChildrenListener)){
			return;
		}
		
		Map<VjoValidationVisitorState, List<IVjoValidationListener>> listenerMap = m_node2PreListenerMap.get(node);
		if(listenerMap == null){
			listenerMap = new HashMap<VjoValidationVisitorState, List<IVjoValidationListener>>();
			m_node2PreListenerMap.put(node, listenerMap);
		}
		List<IVjoValidationListener> listeners = listenerMap.get(VjoValidationVisitorState.AFTER_ALL_CHILDREN);
		if(listeners == null){
			listeners = new ArrayList<IVjoValidationListener>();
			listenerMap.put(VjoValidationVisitorState.AFTER_ALL_CHILDREN, listeners);
		}
		listeners.add(listener);
	}

	public void appendPostAllChildrenListener(IJstNode node, IVjoValidationListener listener){
		if(listener == null || !(listener instanceof IVjoValidationPostAllChildrenListener)){
			return;
		}
		
		Map<VjoValidationVisitorState, List<IVjoValidationListener>> listenerMap = m_node2PostListenerMap.get(node);
		if(listenerMap == null){
			listenerMap = new HashMap<VjoValidationVisitorState, List<IVjoValidationListener>>();
			m_node2PostListenerMap.put(node, listenerMap);
		}
		List<IVjoValidationListener> listeners = listenerMap.get(VjoValidationVisitorState.AFTER_ALL_CHILDREN);
		if(listeners == null){
			listeners = new ArrayList<IVjoValidationListener>();
			listenerMap.put(VjoValidationVisitorState.AFTER_ALL_CHILDREN, listeners);
		}
		listeners.add(listener);
	}
	
	public List<String> getSupportedBrowserList(){
		return null;
	}
	
	public List<String> getBrowserFiltersByGroup(String groupId){
		return null;
	}
	
	public boolean hasBrowserFiltersUponGroup(String groupId){
		return false;
	}
	
	public void updateBrowserFilters(String groupId, List<String> filters){
		
	}

	/**
	 * <p>
	 * 	Validator Repository is the dispatcher for validation visitor events
	 * 	as repo is aware of all the validators, and when they should be notified
	 * 
	 *  repo will dispatch the event in the following order:
	 *  
	 *  pre node specific listeners
	 *  	
	 *  node type based listeners
	 *  
	 *  post node specific listeners
	 * </p>
	 */
	public void dispatch(IVjoValidationVisitorEvent event) {
		if(event != null){
			final IJstNode node = event.getVisitNode();
			if(node != null){
				//pre node specific listeners to notify 1st
				Map<VjoValidationVisitorState, List<IVjoValidationListener>> listenerMap = m_node2PreListenerMap.get(node);
				if(listenerMap != null){
					List<IVjoValidationListener> listeners = listenerMap.get(event.getVisitState());
					if(listeners != null){
						for(IVjoValidationListener listener : listeners){
							listener.onEvent(event);
						}
						listeners.clear();
					}
				}
				
				//node type general listeners to notify 2nd
				listenerMap = m_nodeType2ListenerMap.get(node.getClass());
				final Boolean deactiviation = m_deactivationMap.get(node.getClass());
				if(deactiviation == null || !deactiviation.booleanValue()){
					if(listenerMap != null){
						List<IVjoValidationListener> listners = listenerMap.get(event.getVisitState());
						if(listners != null){
							for(IVjoValidationListener listener : listners){
								listener.onEvent(event);
							}
						}
					}
				}
				
				//post node specific listeners to notify last
				listenerMap = m_node2PostListenerMap.get(node);
				if(listenerMap != null){
					List<IVjoValidationListener> listeners = listenerMap.get(event.getVisitState());
					if(listeners != null){
						for(IVjoValidationListener listener : listeners){
							listener.onEvent(event);
						}
						listeners.clear();
					}
				}
			}
		}
	}
}

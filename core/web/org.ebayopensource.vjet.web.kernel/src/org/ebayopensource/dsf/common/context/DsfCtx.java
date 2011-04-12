/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.context;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.common.container.DsfNodeContainer;
import org.ebayopensource.dsf.common.exceptions.DsfExceptionHelper;
import org.ebayopensource.dsf.common.phase.PhaseDriver;
import org.ebayopensource.dsf.dom.DNode;
import org.ebayopensource.dsf.dom.util.DeferConstructionCollector;

/**
 * The DsfContext is the junction box for Dsf-based Applications.  Much of the
 * DsfComponent contracts/implementations are given access to the DsfContext via
 * a formal argument.  A single instance of this context is made available via
 * a Java thread local variable.
 * 
 * It is expected that most of the changing Dsf-state artifacts that are of
 * general usage to Components and Application code are accessed through this
 * object.
 * 
 * The context provides access to:
 * <li>DsfContainer - component creation/lookup
 * <li>Application - application specific object
 * <li>PhaseDriver - Phase registration/management
 * <li>Event Queue -
 * <li>Root of the DsfComponent tree
 * 
 * Often Applications concrete implementation will provide a static method that
 * returns the type specific instance that was placed in the context.  Since the
 * context is accessible via thread local means, there is no need for the
 * concrete Application implementation to use thread local itself.  This 
 * approach works well and also cuts down on the amount of code needed to access
 * the application instance.
 * 
 * public class MyApp implements IApplication {
 *     public static MyApp getApp() {
 *         // assumes the V3 command created the MyApp instance and set it to
 *         // the DsfContext
 *         return (MyApp)DsfContext.getContext().getApplication() ;
 *     }
 * }
 * 
 * MyApp app ;
 * app = (MyApp)DsfContext.getContext().getApplication() ;  // a bit too long...
 *    vs.
 * app = MyApp.getApp() ;  // like this better...
 */
public class DsfCtx {
	
	private static ThreadLocalContext s_context = new ThreadLocalContext();
	
	private IDsfAppCtx m_appCtx ;
	private DNode m_rootNode;
	private IConverterRegistry m_converterRegistry;
	private IDsfNodeEventQueue m_nodeEventQueue ;
	private DsfNodeContainer m_container ;	
	private PhaseDriver m_lifecycle ;
//	private UserPreferences m_userPrefs = UserPreferences.defaultUserPrefs;
	private boolean m_isSecureRequest = false;
	private Direction m_direction = Direction.UNDEFINED;
	private Map<String, ISubCtx> m_subContextHolder ;
	private IdGenerator m_idGenerator = new IdGenerator() ;
//	private EventHandlerContainer m_eventHandlerContainer ;
	private DeferConstructionCollector m_deferConstructionCollector =
		new DeferConstructionCollector();
	
	/**
	 * Interface for any subCtx that need to be inherited by child thread.
	 */
	public interface IInheritable {
		/**
		 * Answer a copy of this subCtx. Depending on the 
		 * semantics of each subCtx, it can be a sharrow copy or must be a deep
		 * copy.
		 * @return ISubCtx copy of this subCtx.
		 */
		ISubCtx inherit();
	}

	/**
	 * Answer the thread local associated DsfContext instance.  Initially a
	 * DsfContext instance is associated.  The setContext(...) can set the
	 * associated value to null.
	 */
	public static DsfCtx ctx() {
		return s_context.get();
	}
	
	/**
	 * Sets the context to be associated with this thread local.  The context
	 * can be null.  
	 */
	public static void setCtx(final DsfCtx context) {
		s_context.set(context) ;
	}
	
	/**
	 * Create an new context if it is not existed or reset the existing one.
	 */
	public static DsfCtx createCtx() {
		DsfCtx context = ctx();
		if (context == null) {
			context = new DsfCtx();
			s_context.set(context);
		}
		else {
			context.reset();
		}
		return context;
	}

	//
	// Constructor(s)
	//		
	private DsfCtx() {
		// empty on purpose
	}
	
	public DsfCtx(final DNode componentRoot) {
		s_context.set(this);
		m_rootNode = componentRoot;
	}

	public DsfCtx(final IDsfAppCtx appCtx) {
		s_context.set(this) ;
		m_appCtx = appCtx ;
	}
	
	//
	// API
	//	
//	public EventHandlerContainer getEventHandlerContainer() {
//		if(m_eventHandlerContainer==null) {
//			m_eventHandlerContainer = new EventHandlerContainer();
//		}
//		return m_eventHandlerContainer;
//	}
	
	public IdGenerator ids() {
		return m_idGenerator ;
	}
	
//	public DsfCtx setUserPrefs(final UserPreferences userPrefs){
//		m_userPrefs = userPrefs;
//		return this ;
//	}
//	
//	public UserPreferences getUserPrefs(){
//		return m_userPrefs;
//	}
	
	public boolean isSecureRequest() {
		return m_isSecureRequest;
	}
	
	public DsfCtx setRequestSecure(final boolean isSecure) {
		m_isSecureRequest = isSecure;
		return this ;
	}
	
	/**
	 * Sets the following to null:
	 * <li>Component root
	 * <li>Application
	 * <li>Client service engine
	 * <p>
	 * The following also occurs:
	 * <li>The converter registry is re-looked up
	 * <li>The lifecycle is reset with the DefaultLifecycle implementation
	 * <li>The component event queue is cleared
	 */
	public DsfCtx reset() {
		m_rootNode = null;
		m_appCtx = null;
		m_converterRegistry = null;		
		m_nodeEventQueue = null;		
		m_lifecycle = null;	
		m_container = null;
		m_direction = Direction.UNDEFINED;
//		m_userPrefs = UserPreferences.defaultUserPrefs ;
		m_isSecureRequest = false;
		if(m_subContextHolder!=null){
			resetSubCtx(m_subContextHolder.values());
		}
		m_subContextHolder = null;
		m_idGenerator.resetAllIds();
//		m_eventHandlerContainer = null ;
		m_deferConstructionCollector.reset();
		
		return this ;
	}
	
	private void resetSubCtx(Collection<ISubCtx> values) {
		for(ISubCtx ctx : values){
			if(ctx instanceof IResettableSubCtx){
				((IResettableSubCtx)ctx).reset();
			}
		}
	}
	
	/**
	 * Set the DNode root node for this context.  The root may be null.
	 * @deprecated use setRootNode(DNode) instead
	 */
	public DsfCtx setCompRoot(final DNode rootNode) {
		return setRootNode(rootNode) ;
	}
	/**
	 * Set the DNode root node for this context.  The root may be null.
	 */
	public DsfCtx setRootNode(final DNode rootNode) {
		m_rootNode = rootNode ;
		return this ;
	}
	
	/**
	 * Answer the DNode root for this context.  The root may be null.
	 */
	public DNode getRootNode() {
		return m_rootNode ;
	}
	/**
	 * @deprecated Use getRootNode() instead
	 * @return 
	 */
	public DNode getCompRoot() {
		return getRootNode();
	}
	
	/**
	 * Answer the IDsfLifecycle for this context.  The lifecycle will never
	 * be null.  The default value was set at construction time is an instance
	 * of DefaultLifecycle with an empty phase factory
	 * @deprecated e497
	 */
	public PhaseDriver getLifecycle() {
		return getPhaseDriver() ;
	}
	
	/**
	 * Answer the IDsfLifecycle for this context.  The lifecycle will never
	 * be null.  The default value was set at construction time is an instance
	 * of DefaultLifecycle with an empty phase factory
	 */
	public PhaseDriver getPhaseDriver() {
		if (m_lifecycle == null) {
			m_lifecycle = new PhaseDriver();
		}
		return m_lifecycle;
	}
	
	/**
	 * Answers the DsfContainer for this context.  The container will never
	 * be null.
	 */
	public DsfNodeContainer getContainer() {
		if (m_container == null) {
			m_container = new DsfNodeContainer();
			m_container.addNodeInstantiationValidator(
					getDeferConstructionCollector());
		}
		return m_container;
	}	

	/**
	 * Answer the current IDsfAppCtx instance associated with this context
	 * if none answers null.  
	 */	
	public IDsfAppCtx getAppCtx() {
		return m_appCtx;
	}
	
	/**
	 * Sets the current IDsfAppCtx instance to be associated with this
	 * context.  The appCtx can be null.
	 */
	public DsfCtx setAppCtx(final IDsfAppCtx appCtx) {
		m_appCtx = appCtx ;
		return this ;
	}

	/**
	 * Set not-null converter registry for this context. If the passed in 
	 * registry is null a DsfRuntimeException is thrown.
	 * <p> 
	 * The default registry was set at construction time and it was obtained
	 * from ConverterRegistryFactory.getRegistry().
	 */
	public DsfCtx setConverterRegistry(final IConverterRegistry converterRegistry) {
		if (converterRegistry == null){
			throw new RuntimeException("converterRegistry is null");
		}
		m_converterRegistry = converterRegistry;
		return this ;
	}
	
	/**
	 * Answer the converter registry for this context. Never return null.
	 */
	public IConverterRegistry getConverterRegistry() {
		return m_converterRegistry;
	}	
	
	/**
	 * Answers the IDsfNodeEventQueue for this instance.  The queue will never
	 * be null.
	 */
	public IDsfNodeEventQueue getNodeEventQueue() {
		if (m_nodeEventQueue == null) {
			m_nodeEventQueue = new DefaultDsfNodeEventQueue(this);
		}
		return m_nodeEventQueue;
	}

	public Direction getDirection() {
		return m_direction;
	}
	
	//
	// Framework
	//

	void setComponentEventQueue(final IDsfNodeEventQueue queue) {
		m_nodeEventQueue = queue ;
	}
	
	void setLifecycle(PhaseDriver lifecycle) {
		m_lifecycle = lifecycle;
	}
		
	public DsfCtx setContainer(final DsfNodeContainer container) {
		m_container = container;
		return this ;
	}	
	
	public DsfCtx setDirection(final Direction direction) {
		m_direction = direction;
		return this ;
	}
		
	ISubCtx getSubCtx(final String name) {
		return getSubContextHolder().get(name);
	}
	
	void setSubCtx(final String name, final ISubCtx ctx) {
		if (ctx == null) {
			removeSubCtx(name);
			return;
		}
		getSubContextHolder().put(name, ctx);
	}
	
	void removeSubCtx(final String name) {
		getSubContextHolder().remove(name);
	}
	
	private Map<String, ISubCtx> getSubContextHolder() {
		if (m_subContextHolder == null) {
			m_subContextHolder = new HashMap<String, ISubCtx>(5);
		}
		return m_subContextHolder;
	}

//	void setEventHandlerContainer(final EventHandlerContainer eventHandlerContainer) {
//		m_eventHandlerContainer = eventHandlerContainer;
//	}
	
	public DeferConstructionCollector getDeferConstructionCollector() {
		return m_deferConstructionCollector;
	}
	
	void setDeferConstructionCollector(DeferConstructionCollector collector) {
		m_deferConstructionCollector = collector;
	}

	/**
	 * Create and return new DsfCtx for child thread that run child
	 * modules in parallel.
	 * BE VERY CAREFUL when sharing data with child ctx
	 * @return DsfCtx
	 */
	public DsfCtx createChildCtx(){
		
		DsfCtx childContext = new DsfCtx();

		childContext.m_isSecureRequest = m_isSecureRequest;
		ISubCtx subCtx;
		if (m_subContextHolder != null) {
			childContext.m_subContextHolder = new HashMap<String, ISubCtx>(m_subContextHolder.size());
			for (String key: m_subContextHolder.keySet()) {
				subCtx = getSubCtx(key);
				if (subCtx instanceof IInheritable){
					childContext.m_subContextHolder.put(key, ((IInheritable)subCtx).inherit());
				}
			}
		}
												
		return childContext;
	}
	
	//
	// Helper class(es)
	//
	private static class ThreadLocalContext extends InheritableThreadLocal<DsfCtx> {
		protected DsfCtx initialValue() {
			return new DsfCtx();
		}
	
		protected DsfCtx childValue(final DsfCtx parentContext) {
			DsfCtx childContext = new DsfCtx();

			childContext.m_container = parentContext.m_container;
			childContext.m_rootNode = parentContext.m_rootNode;
			childContext.m_nodeEventQueue = 
				parentContext.m_nodeEventQueue;			
						
			childContext.m_converterRegistry =
				parentContext.m_converterRegistry;
			
//			childContext.m_userPrefs = parentContext.m_userPrefs;
			childContext.m_isSecureRequest = parentContext.m_isSecureRequest;
			childContext.m_appCtx = parentContext.m_appCtx;
			childContext.m_lifecycle = parentContext.m_lifecycle;
			childContext.m_direction = parentContext.m_direction;
			Map<String, ISubCtx> parentSubContextHolder = parentContext.m_subContextHolder;
			if (parentSubContextHolder != null) {
				childContext.m_subContextHolder = new HashMap<String, ISubCtx>(parentSubContextHolder.size());
				for (Map.Entry<String, ISubCtx> entry: parentSubContextHolder.entrySet()) {
					ISubCtx value = entry.getValue();
					
					if (value == null) {
						DsfExceptionHelper.chuck("SubContext " + entry.getKey() + " should not be null");
					} else if (value instanceof IInheritable) {
						childContext.m_subContextHolder.put(entry.getKey(), value.cloneCtx());
					}
				}
			}
//			childContext.m_eventHandlerContainer = parentContext.m_eventHandlerContainer;
													
			return childContext;
		}
	}
}
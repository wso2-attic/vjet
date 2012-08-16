package org.ebayopensource.dsf.common.initialization;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.logger.LogLevel;
import org.ebayopensource.dsf.logger.Logger;




/** This class is a base implementation.  It keeps track of the state for
 * the user.  The user needs to implement the following methods:
 * - initialize()
 * - shutdown()
 * 
 */
public abstract class BaseInitializationManager implements InitializationManagerInterface {
	
	private static Logger s_logger = Logger.getInstance(BaseInitializationManager.class);

	private final ModuleInterface[] m_dependentModules;
	
	private InitializationState m_state = InitializationState.PRISTINE;
	
	private final InitializationHelper m_initializationHelper = new InitializationHelper();
	
	private long m_initTime = -1;

	/** constructor.
	 * Takes in the dependent modules.
	 */
	public BaseInitializationManager(final ModuleInterface [] dependentModules){
		m_dependentModules = dependentModules;
	}

	/** This returns the state of the initialization manager.
	 */
	public InitializationState getState() {
		return m_state;
	}

	protected final InitializationHelper getInitializationHelper() {
		return m_initializationHelper;
	}

	/** This is used to recurse and calucate all depenent modules. 
	 * @return - an array of modules (implementing ModuleInterface )
	 */
	public ModuleInterface [] getDependentModules() {
		return m_dependentModules;
	}

	/**
	 * Returns the name of component to be displayed in startup logs
	 */
	public String getComponentName() {
		return getClass().getName();
	}

	/** This is called during initialization.  It keeps track of the state.
	 * It internally calls doInitialize().  Its primary purpose is to track the
	 * state.
	 */
	public final synchronized void doInitialize(final InitializationContext context)
		throws InitializationException {
		
		if ( context == null ) {
			throw new NullPointerException();
		}
		
		if ( m_state == InitializationState.INITAILIZED) {
			context.out("initializing '" + getComponentName() +
					"' } already DONE ");
			return ;  // if we are already initied, skip
		}

		boolean isSuccess = false;
		
		try {			
			context.out("initializing '" + getComponentName() + "' {");
			long startTime = System.currentTimeMillis();
			
			initialize(context);
			
			long endTime = System.currentTimeMillis();
			
			m_initTime = endTime - startTime;
			
			String msg = "initializing '" + getComponentName() +
				"' } DONE - time " + m_initTime + "ms";
			
			context.out(msg);
			
			//TODO review whether to remove
			if (m_initTime > 1000) {
				s_logger.log(LogLevel.WARN, "ALERT-Mod-Init slow!!! "+msg);				
			}
			
			if(hasCharacteristic(context))
				m_state = InitializationState.INITIALIZED_CHARACTERISTIC;
			else
				m_state = InitializationState.INITAILIZED;
			
			isSuccess = true;
			
		} finally {
			if ( !isSuccess ) {
//				RuntimeContext.setErrorModule(getComponentName());
				m_state = InitializationState.INITAILIZED_FAILED;
				context.out("initializing FAILED '" + getComponentName() + "' }");
			}
		}
	}
	
	private boolean hasCharacteristic(InitializationContext context) {
		Initializable.CharacteristicEnum characteristic = context.getCharacteristic();
		
		return (characteristic != null && 
				characteristic != Initializable.CharacteristicEnum.NONE);
	}

	/** The can override this method to do some custom handling.  The user
	 * should not need to do this.  This will get called during initialization
	 * by doInitialize().
	 */
	protected void initialize(final InitializationContext context) {
		m_initializationHelper.initialize(context);
	}

	/** This is called during shutdown.  It keeps track of the state.
	 * It internally calls doShutdown().  Its primary purpose is to track the
	 * state.
	 */
	public final synchronized void doShutdown(
			final InitializationContext context) {
		if ( context == null ) {
			throw new NullPointerException();
		}
		try {
			context.out("shutdown '" + getComponentName() + "' {");
			shutdown(context);
			context.out("shutdown '" + getComponentName() + "' }DONE");
			m_state = InitializationState.SHUTDOWN;
		} finally {
			if ( m_state != InitializationState.SHUTDOWN ) {
				m_state = InitializationState.SHUTDOWN_FAILED;
			}
		}
	}

	/** The can override this method to do some custom handling.  The user
	 * should not need to do this.  This will get called during shutdown
	 * by doShutdown().
	 */
	protected void shutdown(final InitializationContext context) {
		m_initializationHelper.shutdown(context);
	}
	
	public boolean isNotifyUponInitDone(){
		return m_initializationHelper.isNotifyUponInitDone();
	}
	
	
	/** This method can be overridden by the child if they need to do some
	 * cleanup and/or check on any parallel initialization after all of the 
	 * Initialization Managers have been called and initialization is effectively done
	 */
	public void notifyInitDone(){
		m_initializationHelper.notifyInitDone();
	}
	
	
	/**
	 * 	
	 * @param characteristic enum specifying the characteristic
	 * @return array of initializables satisfying the specified characteristic. 
	 * returns all initializables if characteristic none or null 
	 */
	public Initializable[] getInitializablesWithCharacteristic(
		final Initializable.CharacteristicEnum characteristic) {
		
		List<Initializable> filteredInits = new ArrayList<Initializable>();
		
		Initializable[] inits = m_initializationHelper.getInitializables();
		
		// return all initializables if no characteristic
		if(characteristic == null || characteristic.equals(Initializable.CharacteristicEnum.NONE)){
			return inits;			   	
		}
		
		// filter based on the characteristic
		for(Initializable init : inits) {
			if(init.getCharacteristics().contains(characteristic)) {
				filteredInits.add(init);
			}
		}
		return (Initializable[])filteredInits.toArray(new Initializable[0]);					
	}
	
	
	/**
	 * Is this manager lazy initializable? By default all modules are lazy initable
	 * sub classes should override and specify false if they cannot be lazy inited
	 * @return 
	 */
	public boolean isLazyInitable() {
		return true;
	}

}

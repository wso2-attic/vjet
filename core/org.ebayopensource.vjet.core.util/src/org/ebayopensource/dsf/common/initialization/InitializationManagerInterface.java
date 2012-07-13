package org.ebayopensource.dsf.common.initialization;

/** The object responsible for initializing a module.  Its 
 * responsibilities are to initialize and shutdown the module when 
 * called upon and to specify which other modules it depends on 
 * for initialization.
 *
 * @author kquacken
 */
public interface InitializationManagerInterface {

	/** This is called to initialize the module. */
	void doInitialize(final InitializationContext context)
		throws InitializationException;

	/** This is called to shutdown the module. */
	void doShutdown(final InitializationContext context);

	/** This returns a set of modules that must be initialized before this
	 * module.
	 * 
	 * @return - an array of classes that follow the Module convention
	 */
	ModuleInterface [] getDependentModules();

	/** This returns the state of the initialization manager.
	 * 
	 * @return InitializationState
	 */
	InitializationState getState();
	
	/** This method is called after initialization is completed calling
	 * all of the InitializationManagers.  This allows cleanup and/or checking
	 * if parallel initialization was successful.
	 */
	void notifyInitDone();
	
	/** This method indicates whether an InitializationManager should be called
	 * after all of the InitializationManagers have been called, i.e. after
	 * initialization is done.
	 */
	boolean isNotifyUponInitDone();
	
	/**
	 * 	
	 * @param characteristic enum specifying the characteristic
	 * @return array of initializables satisfying the specified characteristic 
	 */
	Initializable[] getInitializablesWithCharacteristic(
			final Initializable.CharacteristicEnum characteristic);
			
	boolean isLazyInitable();
}

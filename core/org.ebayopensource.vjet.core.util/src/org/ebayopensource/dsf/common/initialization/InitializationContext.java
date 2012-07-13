package org.ebayopensource.dsf.common.initialization;

/**  This is the context that will be passed to all initialization managers
 * during initialization.
 *
 */
public interface InitializationContext {

	/** This is intended to be used for tracing/debugging of the
	 * initialization.  It can be used to tell what is currently
	 * initializing.  The implementor should terminate the line.  The caller
	 * need not.
	 */
	void out(final String message);

	public interface Work extends Runnable {
	}

	/** Depending on the implementation, this work might be done synchronously
	 * or asynchronously.  It is a hint to do work in parallel if possible.
	 * Also, note that you are saying my module can detect if the work is 
	 * not work and handle it gracefully.  This would happen when the
	 * implementation is paralle and your initialization manager finishes
	 * before this paralle work is done.  Your init manager does not wait
	 * for this work to be done before it says that it is done.  It is assumed
	 * that your module will handle this gracefully.
	 * 
	 * @param work
	 */
	void doWork(final Work work);
	
	
	/** Depending on the implementation, this work might be done synchronously
		 * or asynchronously.  It is a hint to do work in parallel if possible.
		 * Also, note that you are saying my module can detect if the work is 
		 * not work and handle it gracefully.  This would happen when the
		 * implementation is paralle and your initialization manager finishes
		 * before this paralle work is done.  Your init manager does not wait
		 * for this work to be done before it says that it is done.  It is assumed
		 * that your module will handle this gracefully.
		 * 
		 * @param work
		 * @param tag  a tag for the assigned work. Used in log output
		 */
	void doWork(final Work work, final String tag);
	
	/**
	 * sets the characteristic to be used in the initialization
	 * like REGISTER_COMMANDS used by lazy init
	 * @param characteristic 
	 */
	void setCharacteristic(final Initializable.CharacteristicEnum characteristic);
	
	/**
	 * the characteristic on this initialization context. 
	 * To be used by the initialization framework to make initialization decisions
	 * @return
	 * @see setCharacteristic
	 */
	Initializable.CharacteristicEnum getCharacteristic();
}

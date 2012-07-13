package org.ebayopensource.dsf.common.initialization;


/** This is a base implementatation that calls System.out.println for the out()
 * method.  It is recommended that folks extends this.  So that if we add
 * any method to InitializationContext that they will pickup default behavior
 * without any refactoring.
 *
 */
public class BaseInitializationContext implements InitializationContext {
	private Initializable.CharacteristicEnum m_characteristic = null;
	
	/**
	 * To add this static variable and its getter/setter in this class is a workaround without
	 * changing many other source files or worry about if InitializationContext
	 * has many implementors. The ideal solution is to upgade the interface
	 * to have a signature isVerbose().
	 * 
	 * the flag s_verbose is to allow batch jobs to set "false" by specifying at the command line
	 * "-verbose n" so we can have less logging on the stdout to save disk space per the Ops.
	 * 
	 * Default is true. i.e. non-batch jobs always get true. so no impact
	 * on App Server/non-batch apps virtyally.
	 */ 
	private static boolean s_verbose = true;
	
	/* currently it is called by BatchInitializer only */
	public static void setVerbose(boolean isVerbose) {
		s_verbose = isVerbose;
	}
	public static boolean isVerbose() {
		return s_verbose;
	}
	
	
	public void out(final String message) {
		System.out.println(message); //KEEPME
	}

	public void doWork(final Work work) {
		work.run();
	}
	
	public void doWork(final Work work, final String tag) {
		work.run();
	}
	
	public void setCharacteristic(final Initializable.CharacteristicEnum characteristic) {
		m_characteristic = characteristic;
	}
	
	public Initializable.CharacteristicEnum getCharacteristic() {
		return m_characteristic;
	}
	
}

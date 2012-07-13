package org.ebayopensource.dsf.common.initialization;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author kquacken
 */
public abstract class BaseInitializable implements Initializable {

	public static final String PROP_INIT_TIME = "InitTime";
	public static final String PROP_CLASSNAME = "ClassName";
	private static int s_InitNumber=0;
	private static int s_ShutdownNumber=0;

	private InitializationState m_state = InitializationState.PRISTINE;
	private long m_initTime = -1;
	private boolean m_notifyUponInitDone = false;
	private boolean m_registrationIsDone = false;
	private Map<CharacteristicEnum, Object> m_characteristicInfoMap = new HashMap<CharacteristicEnum, Object>();
	

	/** This returns the state of the Initializable.
	 */
	public InitializationState getState() {
		return m_state;
	}

	/** This is called during initialization.  It keeps track of the state.
	 * It internally calls doInitialize().
	 */
	public final synchronized void doInitialize(final InitializationContext context)
		throws InitializationException
	{
		if ( context == null ) {
			throw new NullPointerException();
		}
		if ( m_state == InitializationState.INITAILIZED) {
			return ;  // if we are already initied, skip
		}
		
		try {
			int num = s_InitNumber++;
			
			/* for Batch Jobs, if "-verbose -n" is specified, then 
			 * only "num:" is printed out.
			 */
			if (BaseInitializationContext.isVerbose()) {
			    context.out(num + "---" + getClass().getName() +
				      "--- initializing {");
			} else {
			    context.out(num + ":");
			}
			final long startTime = System.currentTimeMillis();
			initialize(context);
			final long endTime = System.currentTimeMillis();
			m_initTime = endTime - startTime;
			
			/*Batch jobs' ouput reduction when "-verbose n" is specified */
			if (BaseInitializationContext.isVerbose()) {
			    context.out(num + "---" + getClass().getName() +
				    "--- initializing } DONE - time " + m_initTime + "ms");
			} else {
				context.out(num + ":DONE");
			}
			
			m_state = InitializationState.INITAILIZED;
//			Map<String, String> prop = new HashMap<String, String>(2);
			
//			prop.put(PROP_CLASSNAME, getClass().getName());
//			prop.put(PROP_INIT_TIME, ""+m_initTime);
			
		} finally {
			if ( m_state != InitializationState.INITAILIZED ) {
				m_state = InitializationState.INITAILIZED_FAILED;
				context.out("initializing FAILED '" +
					getClass().getName() + "' }");
			}
		}
		if (!m_registrationIsDone) {
			m_registrationIsDone = true;
		}
	}

	/** The user should define this.  This will get called during 
	 * initialization.
	 */
	protected abstract void initialize(final InitializationContext context);

	/** This is called during shutdown.  It keeps track of the state.
	 * It internally calls doShutdown().
	 */
	public final synchronized void doShutdown(final InitializationContext context) {
		if ( context == null ) {
			throw new NullPointerException();
		}
		if ( m_state == InitializationState.SHUTDOWN ||
				m_state == InitializationState.PRISTINE) {
			return ;  // if we are already shutdown or pristine, skip
		}
		try {
			int num = s_ShutdownNumber++;
			
			/* batch job output reduction when "-verbose n" is specified */ 
			if (BaseInitializationContext.isVerbose()) {	
			    context.out(num + " shutdown '" + getClass().getName() + "' {");
			} else {
				context.out(num + ": down...");
			}
			shutdown(context);
			if (BaseInitializationContext.isVerbose()) {
			    context.out(num + " shutdown '" + getClass().getName() + "' } DONE");
			} else {
				context.out(num + ": down DONE");
			}
			m_state = InitializationState.SHUTDOWN;
		} finally {
			if ( m_state != InitializationState.SHUTDOWN ) {
				m_state = InitializationState.SHUTDOWN_FAILED;
				context.out("shutdown FAILED'" + getClass().getName() + "' }");
			}
		}
	}

	/** The user should define this.  This will get called during 
	 * shutdown.
	 */
	protected abstract void shutdown(final InitializationContext context);
	
	public boolean isNotifyUponInitDone(){
		return m_notifyUponInitDone;
	}
	
	protected void setNofifyUponInitDone(boolean notifyUponInitDone){
		m_notifyUponInitDone = notifyUponInitDone;
	}
	

	public void notifyInitDone(){
		// up to the subclass to do something
	}
	
	
	
	/**
	 * sets the info object against the characteristic
	 * @param characteristic 
	 * @param info
	 */
	public void setCharacteristicInfo(final CharacteristicEnum characteristic,
									  final Object info){
			m_characteristicInfoMap.put(characteristic, info);
	}
	
	/**
	 * 
	 * @param characteristic
	 * @return Info object associated with the characteristic or null if the 
	 * map contains no mapping for this characteristic
	 */
	public Object getCharacteristicInfo(final CharacteristicEnum characteristic) {
			return m_characteristicInfoMap.get(characteristic);
	}	
		
		
	/**
	 * by default no characteristics. We could alternately populate CharacteristicEnum.NONE
	 */
	public Set<?> getCharacteristics() {
		return new HashSet<CharacteristicEnum>();
	}
	
	/**
	 * This comparator assumes that the collection has PROP_INIT_TIME. It sorts
	 * in descending order
	 */
	public static class InitTimeDescComparator implements Comparator<Object>
	{
		public int compare(Object obj1, Object obj2) {
			if (obj1 == obj2) {
				return 0;
			}

			Map<String, String> map1 = (Map<String, String>)obj1;
			Map<String, String> map2 = (Map<String, String>)obj2;
		
			long time1 = Long.parseLong(map1.get(PROP_INIT_TIME));
			long time2 = Long.parseLong(map2.get(PROP_INIT_TIME));
			if (time1 < time2) {
				return 1;
			} else if (time1 > time2) {
				return -1;
			} else {
				return 0;
			}
		}

		public boolean equals(Object obj) {
			return obj == this;
		}
	}

}

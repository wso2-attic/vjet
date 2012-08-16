package org.ebayopensource.dsf.common.initialization;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


/** This is a helper class that one can use to keep track of several
 * things that need to be initialized in a module's init manager.
 *
 * @author kquacken
 */
public class InitializationHelper {

	private final List<Initializable> m_initializables = new ArrayList<Initializable>();
	private int m_highestInitIndex = 0;
	private InitializationState m_state = InitializationState.PRISTINE;
	private List<Initializable> m_notifyUponInitDoneList;

	/** This will add the initializable to the list of objects that will
	 * get call during initialize and shutdown.  It will not detect duplicates.
	 */
	public synchronized void add( final Initializable initializable) {
		m_initializables.add(initializable);
	}

	/** This returns the state of the InitializationHelper.
	 */
	public InitializationState getState() {
		return m_state;
	}

	
	/** This will call initialize on all the initializables that have
	 * been added.
	 */
	public synchronized void initialize(final InitializationContext context) {
		if ( context == null ) {
			throw new NullPointerException();
		}
		
		if ( m_state == InitializationState.INITAILIZED) {
			return ;  // if we are already initied, skip
		}
		
		boolean isSuccess = false;
		
		try {
			int initIndex=0;
			
			Initializable.CharacteristicEnum characteristic = context.getCharacteristic();
			
			boolean initWithCharacteristic = hasCharacteristic(context);
			
			for (Initializable o : m_initializables) {
				if(initWithCharacteristic &&
				   !o.getCharacteristics().contains(characteristic)) {
				   	continue;
				}
				
				initIndex++;
				
				if ( initIndex > m_highestInitIndex ) {
					m_highestInitIndex = initIndex;
				}
				
				o.doInitialize(context);
				
				if (o.isNotifyUponInitDone()){
					addToNotifyUponInitDoneList(o);
				}
			}
			if(initWithCharacteristic)
				m_state = InitializationState.INITIALIZED_CHARACTERISTIC;
			else
				m_state = InitializationState.INITAILIZED;
			
			isSuccess = true;
			
		} finally {
			if ( !isSuccess ) {
				m_state = InitializationState.INITAILIZED_FAILED;
			}
		}
	}
	
	private synchronized void addToNotifyUponInitDoneList(Initializable initializable){
		if (m_notifyUponInitDoneList == null){
			m_notifyUponInitDoneList = new ArrayList<Initializable>();
		}
		
		m_notifyUponInitDoneList.add(initializable);
	}
	
	private boolean hasCharacteristic(final InitializationContext context) {
		Initializable.CharacteristicEnum characteristic = context.getCharacteristic();
		
		return (characteristic != null && 
				characteristic != Initializable.CharacteristicEnum.NONE);
	}

	/** This will call shutdown on everything that has been initialized.
	 */
	public synchronized void shutdown(final InitializationContext context) {
		if ( context == null ) {
			throw new NullPointerException();
		}
		try {
			List<RuntimeException> exceptions = new ArrayList<RuntimeException>();
			
			final ListIterator<Initializable> iter =
				m_initializables.listIterator(m_highestInitIndex);
			
			while (iter.hasPrevious()) {
				final Initializable o = (Initializable)iter.previous();
				try {
					o.doShutdown(context);
				} catch ( RuntimeException e ) {
					exceptions.add(e);
				}
				m_highestInitIndex--;
			}
			if ( exceptions.size() > 0 ) {
				throw new ShutdownRuntimeException( exceptions );
			}
			m_state = InitializationState.SHUTDOWN;
			
		} finally {
			if ( m_state != InitializationState.SHUTDOWN ) {
				m_state = InitializationState.SHUTDOWN_FAILED;
			}
		}
	}
	
	public synchronized void notifyInitDone(){
		if (m_notifyUponInitDoneList != null){
			
			// go through the list and tell them that init is done
			for (Initializable o : m_notifyUponInitDoneList){
				o.notifyInitDone();
			}
		}
	}
	
	public synchronized boolean isNotifyUponInitDone(){
		if (m_notifyUponInitDoneList == null) {
			return false;
		}
		
		return true;
	}
				
	protected Initializable[] getInitializables() {
		return (Initializable[]) m_initializables.toArray(new Initializable[0] );
	}
	
}

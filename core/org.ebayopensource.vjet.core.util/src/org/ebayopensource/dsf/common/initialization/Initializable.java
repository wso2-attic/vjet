package org.ebayopensource.dsf.common.initialization;

import java.util.ListIterator;
import java.util.Set;

import org.ebayopensource.dsf.common.enums.BaseEnum;


/**
 * @author kquacken
 */
public interface Initializable {

	class CharacteristicEnum extends BaseEnum {
		/**
		* Characteristic enums
		*/

		public static final CharacteristicEnum NONE	=
			new CharacteristicEnum("None", 0);

		public static final CharacteristicEnum REGISTER_COMMANDS	=
			new CharacteristicEnum("RegisterCommands", 1);

		public static final CharacteristicEnum NO_LAZY_INIT	=
			new CharacteristicEnum("NoLazyInit", 2);
		
		public static final CharacteristicEnum FORCE_LAZY_INIT	=
			new CharacteristicEnum("ForceLazyInit", 3);
        
		// Add new instances above this line


		//-----------------------------------------------------------------//
		// Template code follows....do not modify other than to replace    //
		// enumeration class name with the name of this class.             //
		//-----------------------------------------------------------------//      
		private CharacteristicEnum(String name, int intValue) {
			super(intValue, name);
		}   
		// ------- Type specific interfaces -------------------------------//
		/** Get the enumeration instance for a given value or null */
		public static CharacteristicEnum get(int key) {
			return (CharacteristicEnum)getEnum(CharacteristicEnum.class, key);
		}   
		/** Get the enumeration instance for a given value or return the
		 *  elseEnum default.
		 */
		public static CharacteristicEnum getElseReturn(int key, CharacteristicEnum elseEnum) {  
			return (CharacteristicEnum)getElseReturnEnum(CharacteristicEnum.class, key, elseEnum);
		}   
		/** Return an bidirectional iterator that traverses the enumeration
		 *  instances in the order they were defined.
		 */
		public static ListIterator iterator() {
			return getIterator(CharacteristicEnum.class);
		}                  
	}
	
	/** This should be called to initialize the initializable.
	 */
	void doInitialize(InitializationContext context)
		throws InitializationException;

	/** This should be called to shutdown the initializable.
	 */
	void doShutdown(InitializationContext context);

	/** This returns the state of the Initializable.
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
	 * @param characteristic 
	 * @param info
	 */
	void setCharacteristicInfo(CharacteristicEnum characteristic, Object info);
	
	/**
	 * 
	 * @param characteristic
	 * @return
	 */
	Object getCharacteristicInfo(CharacteristicEnum characteristic);
	
	/**
	 * 
	 * @return the characteristics supported by this initializable
	 * 
	 */
	Set<?> getCharacteristics(); 
	

}

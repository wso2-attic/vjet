package org.ebayopensource.dsf.common.initialization;

import org.ebayopensource.dsf.common.enums.BaseEnum;


/**
 * @author kquacken
 */
public class InitializationState extends BaseEnum {

	private InitializationState(int id, String name) {
		super(id, name);
	}

	public static final InitializationState PRISTINE =
		new InitializationState(0, "pristine");
	public static final InitializationState INITAILIZED = 
		new InitializationState(1, "Initialized");
	public static final InitializationState INITIALIZED_CHARACTERISTIC = 
			new InitializationState(2, "InitializedCharacteristic");
	public static final InitializationState INITAILIZED_FAILED = 
		new InitializationState(3, "InitializedWithError");
	public static final InitializationState SHUTDOWN = 
		new InitializationState(4, "shutdown");
	public static final InitializationState SHUTDOWN_FAILED = 
		new InitializationState(5, "shutdownWithError");

}

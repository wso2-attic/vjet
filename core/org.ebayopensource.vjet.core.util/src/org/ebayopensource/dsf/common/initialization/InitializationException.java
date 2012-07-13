package org.ebayopensource.dsf.common.initialization;

import org.ebayopensource.dsf.common.exceptions.BaseRuntimeException;

/**
 * Exception thrown if problems ocurr while an Initializable class
 * attempts to initialize itself
 * 
 * TODO: make this a checked exception???
 * 
 */

public class   InitializationException
	   extends BaseRuntimeException
{
	/**
	 * Constructor for InitializationException
	 */
	public InitializationException(Throwable th) {
		super("", th);
	}
	
	public InitializationException(String msg) {
		super(msg);
	}

	public InitializationException(String msg, Throwable th) {
		super(msg, th);
	}


}

package org.ebayopensource.dsf.common.exceptions;

import java.util.List;

public interface GenericException {
	public String getMessage() ;
	
	// get the nested exception
	public Throwable getCause();

	// get the Error data
	public ErrorData getErrorData();

	// gets the Error stack by going through the chain.
	// returns a Collection of ErrorData objects.
	public List getErrorDataStack();
	
}


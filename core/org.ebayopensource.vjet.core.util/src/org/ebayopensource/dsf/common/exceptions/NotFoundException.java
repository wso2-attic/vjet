package org.ebayopensource.dsf.common.exceptions;

/**
 * 
 * This exception class is defined for use across different projects.
 * 
 */

public class NotFoundException 
       extends BaseException
{

	public NotFoundException (String message) {
		super(message);
	}	

	public NotFoundException (String message, Throwable t) {
		super(message, t);
	}
	

}


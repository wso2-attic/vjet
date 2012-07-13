package org.ebayopensource.dsf.common.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;


/**
 * Implements the behavior of EbayException, providing
 * exception chaining and serialization.
 */
public class BaseException 
       extends    Exception
       implements GenericException, CommonException.SuperPrintStackTrace 
{
	// Holds common delegate object that does all the work.  The delegate
	// is common between BaseException and BaseRuntimeException
	private CommonException m_common ;
	
	//
	// Constructor(s)
	//

	
	
	public BaseException(String stringMessage) {
		this(stringMessage, (Throwable)null) ;	
	}
	
	/**
	 * The transformation of stringMessage with messageArgs happens
	 * at constructor time.  That transformation is equivalent to the
	 * caller invoking MessageFormat.format(stringMessage, messageArgs).
	 * This makes sure that the:
	 * 1. fomatting timelines matches the exceptions construction
	 * 2. does not pin the messageArgs to the lifetime of the exception instance
	 * 3. avoids complications with Serialization of potentially problematic messageArgs 
	 *    contents.  
	 * 
	 * @param stringMessage
	 * @param messageArgs
	 */
	public BaseException(String stringMessage, Object[] messageArgs) {
		this(stringMessage, messageArgs, null) ;	
	}
	
	public BaseException(String stringMessage, Throwable cause) {
		m_common = new CommonException(this, stringMessage, cause) ;	
	}
	
	/**
	 * The transformation of stringMessage with messageArgs happens
	 * at constructor time.  That transformation is equivalent to the
	 * caller invoking MessageFormat.format(stringMessage, messageArgs).
	 * This makes sure that the:
	 * 1. fomatting timelines matches the exceptions construction
	 * 2. does not pin the messageArgs to the lifetime of the exception instance
	 * 3. avoids complications with Serialization of potentially problematic messageArgs 
	 *    contents.  
	 * 
	 * @param stringMessage
	 * @param messageArgs
	 * @param cause
	 */
	public BaseException(String stringMessage, Object[] messageArgs, Throwable cause) {
		String s = MessageFormat.format(stringMessage, messageArgs) ;
		m_common = new CommonException(this, s, cause) ;	
	}
	
	//
	// API
	//
	public ErrorData getErrorData() {
		return m_common.getErrorData() ;
	}

	public void setErrorData(ErrorData errorData) {
		m_common.setErrorData(errorData) ;
	}

	public Throwable getCause() {
		return m_common.getCause() ;
	}

	public void setCause(Throwable cause) {
		m_common.setCause(cause) ;
	}


	public List getErrorDataStack() {
		return m_common.getErrorDataStack() ;
	}

	public String getMessage() {
		return m_common.getMessage() ;
	}

	/**
	* Avoid calling this method.  Use Logger API to log
	* all stack traces.
	*/
	public void printStackTrace() {
		m_common.printStackTrace() ;
	}

	/**
	 * Avoid calling this method.  Use Logger API to log
	 * all stack traces.
	 */
	public void printStackTrace(PrintStream out) {
		m_common.printStackTrace(out) ;
	}
	
	/**
	 * Avoid calling this method.  Use Logger API to log
	 * all stack traces.
	 */
	public void printStackTrace(PrintWriter out) {
		m_common.printStackTrace(out) ;
	}

	/**
	 * Package internal method for recursion.
	 */
	void printStackTrace(PrintStream out, Set visited) {
		m_common.printStackTrace(out, visited) ;
	}

	/**
	 * Package internal method for recursion.
	 */
	void printStackTrace( PrintWriter out, Set visited ) {
		m_common.printStackTrace(out, visited) ;
	}

	// 
	// Satisfy CommonException.SuperPrintStackTrace
	//
	/**
	 * This conveinence method is needed since the CommonException
	 * wrapper cannot invoke another objects super.xxx().
	 * Only the exception framework should call this method
	 */
	public void superPrintStackTrace(PrintStream out) {
		super.printStackTrace(out) ;
	}
}


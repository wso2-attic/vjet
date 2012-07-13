package org.ebayopensource.dsf.common.exceptions;


/**
 * This class provides the common implementation code for BaseException
 * and BaseRuntimeException.  Since those classes don't have a common
 * parent for us to abstract from, we created this class.
 *
 * The various state, behavior and Serialization are all done here
 * and simply forwarded to by those classes.
 *
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public final class CommonException implements Serializable {
	private SuperPrintStackTrace m_actualEbayException;

	// Error data encapsulates severity, message, origin, etc.
	private ErrorData m_errorData = null;
	private String m_stringMessage = null;

	// Nested exception, if any
	private Throwable m_cause = null;

	// stack trace used across RMI. No need for this in JDK 1.4
	private String m_foreignStackTrace = null;
	private String m_cachedStackTrace = null;

	//
	// Constructor(s)
	//
	
	public CommonException(SuperPrintStackTrace actualException, String textMessage) {
		this(actualException, textMessage, null);
	}

	public CommonException(SuperPrintStackTrace actualException, String stringMessage, Throwable cause) {
		m_actualEbayException = actualException;
		m_stringMessage = stringMessage;
		m_cause = cause;
	}

	//
	// API
	//
	public ErrorData getErrorData() {
		if (null == m_errorData) {
			m_errorData = new ErrorData(getOrigin(), m_stringMessage );
		}

		return m_errorData;
	}

	public void setErrorData(ErrorData errorData) {
		m_errorData = errorData;
	}

	public Throwable getCause() {
		return m_cause;
	}

	public void setCause(Throwable cause) {
		m_cause = cause;
	}


	/**
	 * Used by the logger to output the chain
	 */
	public List getErrorDataStack() {
		ArrayList errorStack = new ArrayList();
		Throwable cause = (Throwable) m_actualEbayException; // MrP -- this;

		while (cause != null) {
			if (cause instanceof GenericException) {
				// push ErrorData object to the stack
				errorStack.add(((GenericException) cause).getErrorData());
				cause = ((GenericException) cause).getCause();
			} else {
				break;
			}
		}

		return errorStack;
	}

	/**
	 * This ends up being tricky since we only used to support a Message object
	 * up till e373, but now support a String message.  You can't have both
	 * based on the constructor so it is clear which "message" information
	 * we are using.
	 *
	 * @return
	 */
	public String getMessage() {
		final String text = getMessageData();

		if (m_cause == null) {
			return text;
		}

		String causesMessage = m_cause.getMessage();

		return (causesMessage == null) ? text : (text + "\n" + causesMessage);
	}

	/**
	* Avoid calling this method.  Use Logger API to log
	* all stack traces.
	*/
	public void printStackTrace() {
		printStackTrace(System.err);
	}

	/**
	 * Avoid calling this method.  Use Logger API to log
	 * all stack traces.
	 */
	public void printStackTrace(PrintStream out) {
		out.println(getCachedStackTrace(null));
	}

	/**
	 * Avoid calling this method.  Use Logger API to log
	 * all stack traces.
	 */
	public void printStackTrace(PrintWriter out) {
		out.println(getCachedStackTrace(null));
	}

	/**
	 * Package internal method for recursion.
	 */
	public void printStackTrace(PrintStream out, Set visited) {
		out.println(getCachedStackTrace(visited));
	}

	/**
	 * Package internal method for recursion.
	 */
	public void printStackTrace(PrintWriter out, Set visited) {
		out.println(getCachedStackTrace(visited));
	}

	//
	// Private
	//

	/**
	 * Get our formatted message data
	 */
	private String getMessageData() {


		if (m_stringMessage != null) {
			return m_stringMessage;
		}

		return null;
	}

	/**
	 * This provides an optimization by caching the result of the
	 * first call to this.printStackTrace() for later use.
	 * This is lousy, but it's much faster.
	 */
	private String getCachedStackTrace(Set visited) {
		if (null != m_cachedStackTrace) {
			return m_cachedStackTrace;
		}

		String trace = null;

		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		PrintStream pout = new PrintStream(buf);

		// record stack trace of nested exception
		if ((null == m_foreignStackTrace) && (null != m_cause)) {
			// Set up list of visited exceptions to avoid loops 
			if (visited == null) {
				visited = new HashSet();
			}

			visited.add(this);

			if (!visited.contains(m_cause)) {
				if (m_cause instanceof BaseException) {
					((BaseException) m_cause).printStackTrace(pout, visited);
				} else if (m_cause instanceof BaseRuntimeException) {
					((BaseRuntimeException) m_cause).printStackTrace(pout, visited);
				} else {
					// run chain to print out errors
					ExceptionUtilities.printRecursiveStackTrace(m_cause, pout, visited);
				}
			}
		}

		// record local stack trace
		// MrP -- super.printStackTrace( pout );
		m_actualEbayException.superPrintStackTrace(pout);

		pout.flush();

		trace = buf.toString();

		try {
			pout.close();
			pout = null;

			buf.close();
			buf = null;
		} catch (IOException e) {
			// empty on purpose
		} // TODO - NOPMD - EmptyCatchBlock - This line created by PMD fixer.

		// record foreign stack trace
		if (null != m_foreignStackTrace) {
			trace = m_foreignStackTrace + trace;
		}

		// populate the cache
		m_cachedStackTrace = trace;

		// help the GC
		m_foreignStackTrace = null;
		trace = null;

		return m_cachedStackTrace;
	}

	private String getOrigin() {
		// todo
		return "unknown";
	}

	/**
	 * Customize Serializable behavior.
	 * Do not change this function signature!  The signature
	 * must be maintained as is for Serialization to work.
	 */
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(getErrorData());

		//a_String object (Foreign stack trace)
		if (null == m_foreignStackTrace) {
			out.writeBoolean(false);
		} else {
			out.writeBoolean(true);
			out.writeObject(m_foreignStackTrace);
		}

		//a_String object (Cached stack trace)
		String cached = getCachedStackTrace(null);

		if (null == cached) {
			out.writeBoolean(false);
		} else {
			out.writeBoolean(true);
			out.writeObject(cached);
		}

		//a_Throwable object
		if (null == m_cause) {
			out.writeBoolean(false);
		} else {
			out.writeBoolean(true);
			out.writeObject(m_cause);
		}
	}

	/**
	 * Serialization behavior is to save the nested
	 * stack trace, nested exceptions, and ErrorData.
	 * This is probably overkill as there's much redundancy
	 * across these objects.
	 *
	 * Customize Serializable behavior.
	 * Do not change this function signature!  The signature
	 * must be maintained as is for Serialization to work.
	 */
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		m_errorData = (ErrorData) in.readObject();

		//a_Read in objects if they were not null when originally serialized
		//a_Message object

		//a_String object (Foreign stack trace)
		if (in.readBoolean()) {
			m_foreignStackTrace = (String) in.readObject();
		} else {
			m_foreignStackTrace = null;
		}

		//a_String object (Cached stack trace)
		if (in.readBoolean()) {
			m_cachedStackTrace = (String) in.readObject();
		} else {
			m_cachedStackTrace = null;
		}

		//a_Throwable object
		if (in.readBoolean()) {
			m_cause = (Throwable) in.readObject();
		} else {
			m_cause = null;
		}
	}

	//----------------------------------------------
	// Helper interface to allow us to call back to the
	// actual eBay exception and super its printStackTrace(PrintStream)
	//----------------------------------------------
	public static interface SuperPrintStackTrace {
		void superPrintStackTrace(PrintStream out);
	}

	private static final long serialVersionUID = 2466318575044764734L;
}

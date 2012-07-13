package org.ebayopensource.dsf.common.exceptions;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import org.ebayopensource.dsf.util.JdkUtil;


/**
 * Utility methods for manipulating exceptions.
 */
public final class ExceptionUtilities {
	private ExceptionUtilities() {
		// prevent instantiation
	}

	/**
	 * Recursively follow a chain of exceptions.
	 * For each one,
	 * <ul>
	 *   <li>look for a public instance method that
	 *       returns a Throwable.
	 *   <li>invoke the method to get that throwable
	 *   <li>if not null, then recurse.
	 *   <li>if no nested exception exist, then add this one
	 *       to the trace
	 * </ul>
	 * @param t the root exception
	 * @param ps a PrintStream to print the stack trace on
	 * @param visited a set of throwables already visited.  This guards
	 * against entering an infinite loop by stopping the recursion if we loop
	 */
	public static void printRecursiveStackTrace(Throwable t, PrintStream ps, Set visited) {
		if (visited == null) {
			visited = new HashSet();
		}

		visited.add(t); // remember us

		Throwable chained_t = getChainedException(t, visited);

		if (chained_t != null) {
			if (chained_t instanceof BaseException) {
				((BaseException) chained_t).printStackTrace(ps, visited);
			} else if (chained_t instanceof BaseRuntimeException) {
				((BaseRuntimeException) chained_t).printStackTrace(ps, visited);
			} else {
				// recursively call ourselves on chained_t first
				printRecursiveStackTrace(chained_t, ps, visited);
			}
		}

		// finally, add the argument 
		t.printStackTrace(ps);
	}

	/**
	 * Find a chained exception through reflection
	 * @param t root exception
	 * @param visited set of ones we've seen before
	 * @return a chained exception that is not in the visited set or null
	 */
	private static Throwable getChainedException(Throwable t, Set visited) {
		Throwable result = null;

		try {
			// First get the public member methods for t's class
			Method[] meths = t.getClass().getMethods();

			// Look for no-argument methods that return a Throwable (or subclass)
			for (int i = 0; i < meths.length; i++) {
				Method m = meths[i];

				if (m.getParameterTypes().length > 0) {
					continue;
				}

				Class c = m.getReturnType();

				if (!JdkUtil.forceInit(Throwable.class).isAssignableFrom(c)) {
					continue;
				}

				// don't use fillInStackTrace, it doesn't do what we want
				if (m.getName().equals("fillInStackTrace")) {
					continue;
				}

				// try invoking the method to get the result
				Throwable r = null;

				try {
					r = (Throwable) m.invoke(t, null);
				} catch (Exception e) {
					// don't care about this
				} // TODO - NOPMD - EmptyCatchBlock - This line created by PMD fixer.

				if (r != null) {
					// Check to see if r is in visited set, if so let
					// loop iterate to try another					
					if (!visited.contains(r)) {
						result = r;

						break; // exit loop
					}
				}
			}
		} catch (Exception e) {
			// don't care about this		
		} // TODO - NOPMD - EmptyCatchBlock - This line created by PMD fixer.

		return result;
	}
}

package vjo.security;

import java.lang.NullPointerException;

import vjo.lang.* ;
import vjo.lang.System; 

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 1998, 2006  All Rights Reserved
 */

/**
 * Checks access to system resources. Supports marking of code
 * as priveleged. Makes context snapshots to allow checking from
 * other contexts.
 *
 * @author		OTI
 * @version		initial
 */

public final class AccessController {
	static {
		// Initialize vm-internal caches
		initializeInternal();
	}

//MrP - No natives in VjO
//private static native void initializeInternal();
private static void initializeInternal() { }

/**
 * Prevents this class from being instantiated.
 */
private AccessController() {
}

/**
 * Returns an array of ProtectionDomain from the classes on the stack,
 * from the specified depth up to the first privileged frame, or the
 * end of the stack if there is not a privileged frame. The array
 * may be larger than required, but must be null terminated.
 *
 * The first element of the result is the AccessControlContext,
 * which may be null, either from the privileged frame, or
 * from the current Thread if there is not a privileged frame.
 *
 * A privileged frame is any frame running one of the following methods:
 *
 * <code><ul>
 * <li>java/security/AccessController.doPrivileged(Ljava/security/PrivilegedAction;)Ljava/lang/Object;</li>
 * <li>java/security/AccessController.doPrivileged(Ljava/security/PrivilegedExceptionAction;)Ljava/lang/Object;</li>
 * <li>java/security/AccessController.doPrivileged(Ljava/security/PrivilegedAction;Ljava/security/AccessControlContext;)Ljava/lang/Object;</li>
 * <li>java/security/AccessController.doPrivileged(Ljava/security/PrivilegedExceptionAction;Ljava/security/AccessControlContext;)Ljava/lang/Object;</li>
 * </ul></code>
 *
 * @param depth The stack depth at which to start. Depth 0 is the current
 * frame (the caller of this native).
 *
 * @return an Object[] where the first element is AccessControlContext,
 * and the other elements are ProtectionsDomain.
 */

private static native Object[] getProtectionDomains(int depth);

/**
 * Checks whether the running program is allowed to
 * access the resource being guarded by the given
 * Permission argument.
 *
 * @param		perm					the permission to check
 * @exception	AccessControlException	if access is not allowed.
 */
public static void checkPermission(Permission perm) throws AccessControlException {
	if (perm == null) throw new NullPointerException();
	if ((AccessControlContext.debugSetting() & AccessControlContext.DEBUG_ACCESS_STACK) != 0) {
		new Exception("Stack trace").printStackTrace();
	}
	Object[] domains = getProtectionDomains(1);
	AccessControlContext acc = (AccessControlContext)domains[0];
	ProtectionDomain[] pDomains = null;
	if (acc != null && acc.domainCombiner != null) {
		if ((AccessControlContext.debugSetting() & AccessControlContext.DEBUG_ACCESS) != 0) {
			AccessControlContext.debugPrintAccess();
			System.err.println("AccessController invoking the Combiner");
		}
		pDomains = acc.domainCombiner.combine(
			toArrayOfProtectionDomains(domains, null),
			acc.domainsArray);
	} else {
		pDomains = toArrayOfProtectionDomains(domains, acc);
	}
	if ((AccessControlContext.debugSetting() & AccessControlContext.DEBUG_ACCESS_DOMAIN) != 0) {
		AccessControlContext.debugPrintAccess();
		if (pDomains.length == 0) {
			System.err.println("domain (context is null)");
		}
	}
	for (int i = 0, length = pDomains.length; i < length; i++) {
		if (!pDomains[i].implies(perm)) {
			if ((AccessControlContext.debugSetting() & AccessControlContext.DEBUG_ACCESS) != 0) {
				AccessControlContext.debugPrintAccess();
				System.err.println("access denied " + perm);
			}
			if ((AccessControlContext.debugSetting() & AccessControlContext.DEBUG_ACCESS_FAILURE) != 0) {
				new Exception("Stack trace").printStackTrace();
				System.err.println("domain that failed " + pDomains[i]);
			}
			//[MSG "K002c", "Access denied {0}"]
			throw new AccessControlException(com.ibm.oti.util.Msg.getString("K002c", perm), perm);
		}
	}
	if ((AccessControlContext.debugSetting() & AccessControlContext.DEBUG_ACCESS) != 0) {
		AccessControlContext.debugPrintAccess();
		System.err.println("access allowed " + perm);
	}
}

/*
 * Used to keep the context live during doPrivileged().
 *
 * @see 		#doPrivileged(PrivilegedAction, AccessControlContext)
 */
private static void keepalive(AccessControlContext context) {
}

/**
 * Answers the access controller context of the current thread,
 * including the inherited ones. It basically retrieves all the
 * protection domains from the calling stack and creates an
 * <code>AccessControlContext</code> with them.
 *
 * @return an AccessControlContext which captures the current state
 *
 * @see 		AccessControlContext
 */
public static AccessControlContext getContext() {
	Object[] domains = getProtectionDomains(1);
	AccessControlContext acc = (AccessControlContext)domains[0];

	ProtectionDomain[] pDomains = null;
	if (acc != null && acc.domainCombiner != null) {
		pDomains = acc.domainCombiner.combine(
			toArrayOfProtectionDomains(domains, null),
			acc.domainsArray);
		AccessControlContext result = new AccessControlContext(pDomains, false);
		result.domainCombiner = acc.domainCombiner;
		return result;
	}
	return new AccessControlContext(toArrayOfProtectionDomains(domains, acc), false);
}

private static ProtectionDomain[] toArrayOfProtectionDomains(Object[] domains, AccessControlContext acc) {
	int len = 0, size = domains.length - 1;
	int extra = acc == null ? 0 : acc.domainsArray.length;
	ProtectionDomain[] answer = new ProtectionDomain[size + extra];
	for (int i = 1; i <= size; i++) {
		boolean found = false;
		if ((answer[len] = (ProtectionDomain)domains[i]) == null)
			break;
		if (acc != null) {
			for (int j=0; j<acc.domainsArray.length; j++) {
				if (answer[len] == acc.domainsArray[j]) {
					found = true;
					break;
				}
			}
		}
		if (!found) len++;
	}
	if (len == 0 && acc != null) return acc.domainsArray;
	else
	if (len < size) {
		ProtectionDomain[] copy = new ProtectionDomain[len + extra];
		System.arraycopy(answer, 0, copy, 0, len);
		answer = copy;
	}
	if (acc != null)
		System.arraycopy(acc.domainsArray, 0, answer, len, acc.domainsArray.length);
	return answer;
}

/**
 * Performs the privileged action specified by <code>action</code>.
 * <p>
 * When permission checks are made, if the permission has been granted by all
 * frames below and including the one representing the call to this method,
 * then the permission is granted. In otherwords, the check stops here.
 *
 * Any unchecked exception generated by this method will propagate up the chain.
 *
 * @param action The PrivilegedAction to performed
 *
 * @return the result of the PrivilegedAction
 *
 * @see 		#doPrivileged(PrivilegedAction)
 */
public static <T> T doPrivileged(PrivilegedAction<T> action) {
	return action.run();
}

/**
 * Performs the privileged action specified by <code>action</code>.
 * <p>
 * When permission checks are made, if the permission has been granted by all
 * frames below and including the one representing the call to this method,
 * then the permission is granted iff it is granted by the AccessControlContext
 * <code>context</code>. In otherwords, no more checking of the current stack
 * is performed. Instead, the passed in context is checked.
 *
 * Any unchecked exception generated by this method will propagate up the chain.
 *
 * @param action The PrivilegedAction to performed
 * @param context The AccessControlContext to check
 *
 * @return the result of the PrivilegedAction
 *
 * @see 		#doPrivileged(PrivilegedAction)
 */
public static <T> T doPrivileged(PrivilegedAction<T> action, AccessControlContext context) {
	T result = action.run();
	keepalive(context);
	return result;
}

/**
 * Performs the privileged action specified by <code>action</code>.
 * <p>
 * When permission checks are made, if the permission has been granted by all
 * frames below and including the one representing the call to this method,
 * then the permission is granted. In otherwords, the check stops here.
 *
 * Any unchecked exception generated by this method will propagate up the chain.
 * However, checked exceptions will be caught an re-thrown as PrivilegedActionExceptions
 *
 * @param action The PrivilegedExceptionAction to performed
 *
 * @return the result of the PrivilegedExceptionAction
 *
 * @throws PrivilegedActionException when a checked exception occurs when performing the action
 *
 * @see 		#doPrivileged(PrivilegedAction)
 */
public static <T> T doPrivileged(PrivilegedExceptionAction<T> action)
	throws PrivilegedActionException
{
	try {
		return action.run();
	} catch (RuntimeException ex) {
		throw ex;
	} catch (Exception ex) {
		throw new PrivilegedActionException(ex);
	}
}

/**
 * Performs the privileged action specified by <code>action</code>.
 * <p>
 * When permission checks are made, if the permission has been granted by all
 * frames below and including the one representing the call to this method,
 * then the permission is granted iff it is granted by the AccessControlContext
 * <code>context</code>. In otherwords, no more checking of the current stack
 * is performed. Instead, the passed in context is checked.
 *
 * Any unchecked exception generated by this method will propagate up the chain.
 * However, checked exceptions will be caught an re-thrown as PrivilegedActionExceptions
 *
 * @param action The PrivilegedExceptionAction to performed
 * @param context The AccessControlContext to check
 *
 * @return the result of the PrivilegedExceptionAction
 *
 * @throws PrivilegedActionException when a checked exception occurs when performing the action
 *
 * @see 		#doPrivileged(PrivilegedAction)
 */
public static <T> T doPrivileged (PrivilegedExceptionAction<T> action, AccessControlContext context)
	throws PrivilegedActionException
{
	try {
		T result = action.run();
		keepalive(context);
		return result;
	} catch (RuntimeException ex) {
		throw ex;
	} catch (Exception ex) {
		throw new PrivilegedActionException(ex);
	}
}

}

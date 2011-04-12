package vjo.security;

import java.lang.NullPointerException;

import vjo.lang.* ;
import vjo.lang.SecurityManager;
import vjo.lang.System; 

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 1998, 2006  All Rights Reserved
 */

/**
 * An AccessControlContext encapsulates the information which is needed
 * by class AccessController to detect if a Permission would be granted
 * at a particular point in a programs execution.
 *
 * @author		OTI
 * @version		initial
 */

public final class AccessControlContext {
	static int debugSetting = -1;
	DomainCombiner domainCombiner;
	ProtectionDomain[] domainsArray;

	private static final SecurityPermission createAccessControlContext =
		new SecurityPermission("createAccessControlContext");
	private static final SecurityPermission getDomainCombiner =
		new SecurityPermission("getDomainCombiner");

	static final int DEBUG_ACCESS = 1;
	static final int DEBUG_ACCESS_STACK = 2;
	static final int DEBUG_ACCESS_DOMAIN = 4;
	static final int DEBUG_ACCESS_FAILURE = 8;
	static final int DEBUG_ACCESS_THREAD = 0x10;
	static final int DEBUG_ALL = 0xff;

static int debugSetting() {
	if (debugSetting != -1) return debugSetting;
	debugSetting = 0;
	boolean access = false;
	String value = (String)AccessController.doPrivileged(new PrivilegedAction() {
		public Object run() {
			return System.getProperty("java.security.debug");
		}});
	if (value == null) return debugSetting;
	int start = 0;
	int length = value.length();
	while (start < length) {
		int index = value.indexOf(',', start);
		if (index == -1) index = length;
		String keyword = value.substring(start, index);
		if (keyword.equals("all")) {
			debugSetting  = DEBUG_ALL;
			return debugSetting;
		} else if (keyword.startsWith("access")) {
			debugSetting |= DEBUG_ACCESS;
			if ((start + 6) < length && value.charAt(start + 6) == ':') {
				index = start + 6;
				access = true;
			}
		} else if (access && keyword.equals("stack")) {
			debugSetting |= DEBUG_ACCESS_STACK;
		} else if (access && keyword.equals("domain")) {
			debugSetting |= DEBUG_ACCESS_DOMAIN;
		} else if (access && keyword.equals("failure")) {
			debugSetting |= DEBUG_ACCESS_FAILURE;
		} else if (access && keyword.equals("thread")) {
			debugSetting |= DEBUG_ACCESS_THREAD;
		} else {
			access = false;
		}
		start = index + 1;
	}
	return debugSetting;
}

static void debugPrintAccess() {
	System.err.print("access: ");
	if ((debugSetting() & DEBUG_ACCESS_THREAD) == DEBUG_ACCESS_THREAD) {
		System.err.print("(" + Thread.currentThread() + ")");
	}
}

/**
 * Constructs a new instance of this class given an array of
 * protection domains.
 *
 * @author		OTI
 * @version		initial
 */
public AccessControlContext(ProtectionDomain[] context) {
	int length = context.length;
	int domainIndex = 0;
	this.domainsArray = new ProtectionDomain[length];
	next : for (int i = 0; i < length; i++) {
		ProtectionDomain current = context[i];
		for (int j = 0; j < i; j++)
			if (current == this.domainsArray[j]) continue next;
		this.domainsArray[domainIndex++] = current;
	}
	if (domainIndex != length) {
		ProtectionDomain[] copy = new ProtectionDomain[domainIndex];
		System.arraycopy(this.domainsArray, 0, copy, 0, domainIndex);
		this.domainsArray = copy;
	}
}

AccessControlContext(ProtectionDomain[] context, boolean ignored) {
	domainsArray = context;
}

/**
 * Constructs a new instance of this class given a context
 * and a DomainCombiner
 */
public AccessControlContext(AccessControlContext acc, DomainCombiner combiner) {
	SecurityManager security = System.getSecurityManager();
	if (security != null)
		security.checkPermission(createAccessControlContext);
	this.domainsArray = acc.domainsArray;
	this.domainCombiner = combiner;
}

/**
 * Checks if the permission <code>perm</code> is allowed in this context.
 * All ProtectionDomains must grant the permission for it to be granted.
 *
 * @param		perm java.security.Permission
 *					the permission to check
 * @exception	java.security.AccessControlException
 *					thrown when perm is not granted.
 */
public void checkPermission(Permission perm) throws AccessControlException {
	if (perm == null) throw new NullPointerException();
	if ((debugSetting() & DEBUG_ACCESS_DOMAIN) != 0) {
		debugPrintAccess();
		if (domainsArray.length == 0) {
			System.err.println("domain (context is null)");
		} else {
			for (int i=0; i<domainsArray.length; i++) {
				System.err.println("domain " + i + " " + domainsArray[i]);
			}
		}
	}
	int i = domainsArray.length;
	while (--i>=0 && domainsArray[i].implies(perm)) ;
	if (i >= 0) {
		if ((debugSetting() & DEBUG_ACCESS) != 0) {
			debugPrintAccess();
			System.err.println("access denied " + perm);
		}
		if ((debugSetting() & DEBUG_ACCESS_FAILURE) != 0) {
			new Exception("Stack trace").printStackTrace();
			System.err.println("domain that failed " + domainsArray[i]);
		}
		// K002c = Access denied {0}
		throw new AccessControlException(com.ibm.oti.util.Msg.getString("K002c", perm), perm);
	}
	if ((debugSetting() & DEBUG_ACCESS) != 0) {
		debugPrintAccess();
		System.err.println("access allowed " + perm);
	}
}

/**
 * Compares the argument to the receiver, and answers true
 * if they represent the <em>same</em> object using a class
 * specific comparison. In this case, they must both be
 * AccessControlContexts and contain the same protection domains.
 *
 * @author		OTI
 * @version		initial
 *
 * @param		o		the object to compare with this object
 * @return		<code>true</code>
 *					if the object is the same as this object
 *				<code>false</code>
 *					if it is different from this object
 * @see			#hashCode
 */
public boolean equals(Object o) {
	if (this == o) return true;
	if (o == null || this.getClass() != o.getClass()) return false;
	AccessControlContext otherContext = (AccessControlContext) o;
	ProtectionDomain[] otherDomains = otherContext.domainsArray;
	int length = domainsArray.length;
	if (length != otherDomains.length) return false;

	next : for (int i = 0; i < length; i++) {
		ProtectionDomain current = domainsArray[i];
		for (int j = 0; j < length; j++)
			if (current == otherDomains[j]) continue next;
		return false;
	}
	return true;
}

/**
 * Answers an integer hash code for the receiver. Any two
 * objects which answer <code>true</code> when passed to
 * <code>equals</code> must answer the same value for this
 * method.
 *
 * @author		OTI
 * @version		initial
 *
 * @return		the receiver's hash
 *
 * @see			#equals
 */
public int hashCode() {
	int result=0;
	int i = domainsArray.length;
	while (--i>=0)
		result ^= domainsArray[i].hashCode();
	return result;
}

/**
 * Answers the DomainCombiner for the receiver.
 *
 * @author		OTI
 * @version		initial
 */
public DomainCombiner getDomainCombiner() {
	SecurityManager security = System.getSecurityManager();
	if (security != null)
		security.checkPermission(getDomainCombiner);
	return domainCombiner;
}
}


package org.eclipse.dltk.mod.dbgp;

public class DbgpSessionIdGenerator {
	public static String generate() {
		return "dbgp_" + System.currentTimeMillis(); //$NON-NLS-1$
	}
}

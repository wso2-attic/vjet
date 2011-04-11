package org.eclipse.dltk.mod.dbgp.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.dltk.mod.dbgp.internal.messages"; //$NON-NLS-1$
	public static String DbgpRawPacket_cantReadPacketBody;
	public static String DbgpRawPacket_invalidCharInPacketSize;
	public static String DbgpRawPacket_noTerminationByte;
	public static String DbgpRawPacket_zeroPacketSize;
	public static String DbgpWorkingThread_threadAlreadyStarted;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

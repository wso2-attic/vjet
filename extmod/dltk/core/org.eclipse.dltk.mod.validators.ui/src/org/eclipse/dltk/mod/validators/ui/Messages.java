package org.eclipse.dltk.mod.validators.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.dltk.mod.validators.ui.messages"; //$NON-NLS-1$
	public static String AbstractValidateSelectionWithConsole_dltkValidatorOutput;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

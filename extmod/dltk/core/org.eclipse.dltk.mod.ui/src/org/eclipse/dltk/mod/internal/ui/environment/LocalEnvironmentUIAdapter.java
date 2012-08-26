package org.eclipse.dltk.mod.internal.ui.environment;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.dltk.mod.core.internal.environment.LocalEnvironment;
import org.eclipse.dltk.mod.ui.environment.IEnvironmentUI;

public class LocalEnvironmentUIAdapter implements IAdapterFactory {
	private final static Class[] ADAPTABLES = new Class[] { IEnvironmentUI.class };

	public LocalEnvironmentUIAdapter() {
	}

	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adaptableObject instanceof LocalEnvironment
				&& adapterType == IEnvironmentUI.class) {
			return new LocalEnvironmentUI();
		}
		return null;
	}

	public Class[] getAdapterList() {
		return ADAPTABLES;
	}
}

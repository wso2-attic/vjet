package org.eclipse.dltk.mod.internal.launching.execution;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.dltk.mod.core.environment.IExecutionEnvironment;
import org.eclipse.dltk.mod.core.internal.environment.LocalEnvironment;

public class LocalExecEnvironmentAdapter implements IAdapterFactory {
	public static final Class[] ADAPTER_LIST = { IExecutionEnvironment.class };
	private IExecutionEnvironment localEnvironment = new LocalExecEnvironment();
	
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adapterType == IExecutionEnvironment.class && 
				adaptableObject instanceof LocalEnvironment) {
			return localEnvironment;
		}
		return null;
	}

	public Class[] getAdapterList() {
		return ADAPTER_LIST; 
	}

}

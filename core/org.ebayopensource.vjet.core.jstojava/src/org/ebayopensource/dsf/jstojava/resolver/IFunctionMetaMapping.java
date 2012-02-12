package org.ebayopensource.dsf.jstojava.resolver;

import java.util.Set;

public interface IFunctionMetaMapping {

	public abstract String getGroupId();

	public abstract boolean hasMetaExtension(String targetFunc);

	public abstract IMetaExtension getExtension(String targetFunc, String key);

	public abstract IMetaExtension getExtentedArgBinding(String targetFunc,
			String key);

	public abstract Set<String> getSupportedTargetFuncs();

}
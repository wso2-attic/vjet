package org.ebayopensource.dsf.jstojava.resolver;

import java.util.Set;

public interface IFunctionMetaMapping {

	String[] getGroupIds();

	boolean hasMetaExtension(String targetFunc);

	IMetaExtension getExtension(String targetFunc, String key);

	IMetaExtension getExtentedArgBinding(String targetFunc,
			String key);

	Set<String> getSupportedTargetFuncs();

	boolean isFirstArgumentType(String targetFunc);

}
/**
 * 
 */
package org.ebayopensource.vjet.eclipse.core.typeconstruct;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.jstojava.resolver.ITypeResolver;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

/**
 * A registry of {@link ITypeResolver}.
 * <p>
 * This is not available as singleton but can be used by creating
 * <code>new</code> instance every time. This helps avoiding problems due to
 * dynamic nature of plug-ins (e.g. if a plug-in contributing extension goes
 * away).
 * 
 * @author paragraval
 */
public class FunctionReturnTypeResolverExtensionRegistry {

	public static final String EXTENSION_POINT_ID = "org.ebayopensource.vjet.eclipse.core.functionreturntype"; //$NON-NLS-1$

	private static final String TAG_RESOLVER = "resolver"; //$NON-NLS-1$

	private Map<String, FunctionReturnTypeResolverExtension> resolversMap;
	private boolean isInitialized = false;

	/**
	 * Default constructor
	 */
	public FunctionReturnTypeResolverExtensionRegistry() {
		resolversMap = new HashMap<String, FunctionReturnTypeResolverExtension>();
	}

	/**
	 * Returns a collection of extensions registered as a function return type
	 * resolver resolver.
	 * 
	 * @return collection of extensions registered as a function return type
	 *         resolver
	 */
	public Collection<FunctionReturnTypeResolverExtension> getResolverExtensions() {
		if (isInitialized) {
			return resolversMap.values();
		}

		// initialize the extensions
		IExtensionPoint typeConstructResolverExtPoint = Platform
				.getExtensionRegistry().getExtensionPoint(EXTENSION_POINT_ID);

		if (typeConstructResolverExtPoint == null) {
			return resolversMap.values();
		}

		IExtension[] functionReturnTypeResolverExtensions = typeConstructResolverExtPoint
				.getExtensions();

		for (IExtension functionReturnTypeResolverExtension : functionReturnTypeResolverExtensions) {

			IConfigurationElement[] configElements = functionReturnTypeResolverExtension
					.getConfigurationElements();

			for (IConfigurationElement element : configElements) {

				if (element == null) {
					continue;
				}
				String elementName = element.getName();
				if (elementName.equals(TAG_RESOLVER)) {
					readFunctionReturnTypeResolverExtension(element);
				}

			}

		}

		return resolversMap.values();
	}

	private void readFunctionReturnTypeResolverExtension(
			IConfigurationElement element) {
		FunctionReturnTypeResolverExtension extension = new FunctionReturnTypeResolverExtension(
				element);
		resolversMap.put(extension.getKey(), extension);

	}

	/**
	 * Returns reference to {@link FunctionReturnTypeResolverExtension} based on
	 * given key, if available, otherwise returns <code>null</code>.
	 * 
	 * @param key
	 *            a key to which the resolver is registered
	 * @return reference to {@link FunctionReturnTypeResolverExtension} based on
	 *         given key, if available, otherwise returns <code>null</code>.
	 */
	public FunctionReturnTypeResolverExtension getResolverExtension(String key) {
		// To make sure that the registry is initialized
		getResolverExtensions();
		return resolversMap.get(key);
	}

	/**
	 * For the given <code>key</code>, finds the corresponding
	 * {@link FunctionReturnTypeResolverExtension} and if available, creates and
	 * returns the reference to {@link ITypeResolver}.
	 * <p>
	 * Returns <code>null</code>, if no extension found based on given key.
	 * 
	 * @param key
	 *            a key to which the resolver is registered
	 * @return reference to {@link ITypeResolver} based on given key,
	 *         if available, otherwise returns <code>null</code>.
	 * @throws CoreException
	 */
	public ITypeResolver getResolver(String key)
			throws CoreException {
		FunctionReturnTypeResolverExtension extension = getResolverExtension(key);
		if (extension != null) {
			return extension.createResolver();
		} else {
			return null;
		}
	}
}

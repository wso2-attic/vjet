/**
 * 
 */
package org.ebayopensource.vjet.eclipse.core.typeconstruct;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.jstojava.resolver.IOTypeResolver;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

/**
 * A registry of {@link IOTypeResolver}.
 * <p>
 * This is not available as singleton but can be used by creating
 * <code>new</code> instance every time. This helps avoiding problems due to
 * dynamic nature of plug-ins (e.g. if a plug-in contributing extension goes
 * away).
 * 
 * @author paragraval
 */
public class OTypeResolverExtensionRegistry {

	public static final String EXTENSION_POINT_ID = "org.ebayopensource.vjet.eclipse.core.otypedef"; //$NON-NLS-1$

	private static final String TAG_RESOLVER = "resolver"; //$NON-NLS-1$

	private Map<String, OTypeResolverExtension> resolversMap;
	private boolean isInitialized = false;

	/**
	 * Default constructor
	 */
	public OTypeResolverExtensionRegistry() {
		resolversMap = new HashMap<String, OTypeResolverExtension>();
	}

	/**
	 * Returns a collection of extensions registered as a function return type
	 * resolver resolver.
	 * 
	 * @return collection of extensions registered as a function return type
	 *         resolver
	 */
	public Collection<OTypeResolverExtension> getResolverExtensions() {
		if (isInitialized) {
			return resolversMap.values();
		}

		// initialize the extensions
		IExtensionPoint otypeDefResolverExtPoint = Platform
				.getExtensionRegistry().getExtensionPoint(EXTENSION_POINT_ID);

		if (otypeDefResolverExtPoint == null) {
			return resolversMap.values();
		}

		IExtension[] otypeDefResolverExtensions = otypeDefResolverExtPoint
				.getExtensions();

		for (IExtension otypeDefResolverExtension : otypeDefResolverExtensions) {

			IConfigurationElement[] configElements = otypeDefResolverExtension
					.getConfigurationElements();

			for (IConfigurationElement element : configElements) {

				if (element == null) {
					continue;
				}
				String elementName = element.getName();
				if (elementName.equals(TAG_RESOLVER)) {
					readOTypeDefResolverExtension(element);
				}

			}

		}

		return resolversMap.values();
	}

	private void readOTypeDefResolverExtension(
			IConfigurationElement element) {
		OTypeResolverExtension extension = new OTypeResolverExtension(
				element);
		resolversMap.put(extension.getKey(), extension);

	}

	/**
	 * Returns reference to {@link OTypeResolverExtension} based on
	 * given key, if available, otherwise returns <code>null</code>.
	 * 
	 * @param key
	 *            a key to which the resolver is registered
	 * @return reference to {@link OTypeResolverExtension} based on
	 *         given key, if available, otherwise returns <code>null</code>.
	 */
	public OTypeResolverExtension getResolverExtension(String key) {
		// To make sure that the registry is initialized
		getResolverExtensions();
		return resolversMap.get(key);
	}

	/**
	 * For the given <code>key</code>, finds the corresponding
	 * {@link OTypeResolverExtension} and if available, creates and
	 * returns the reference to {@link IOTypeResolver}.
	 * <p>
	 * Returns <code>null</code>, if no extension found based on given key.
	 * 
	 * @param key
	 *            a key to which the resolver is registered
	 * @return reference to {@link IOTypeResolver} based on given key,
	 *         if available, otherwise returns <code>null</code>.
	 * @throws CoreException
	 */
	public IOTypeResolver getResolver(String key)
			throws CoreException {
		OTypeResolverExtension extension = getResolverExtension(key);
		if (extension != null) {
			return extension.createResolver();
		} else {
			return null;
		}
	}
}

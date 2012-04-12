/**
 * 
 */
package org.ebayopensource.vjet.eclipse.core.typeconstruct;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.jstojava.resolver.IThisObjScopeResolver;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

/**
 * A registry of {@link IThisObjScopeResolver}.
 * <p>
 * This is not available as singleton but can be used by creating
 * <code>new</code> instance every time. This helps avoiding problems due to
 * dynamic nature of plug-ins (e.g. if a plug-in contributing extension goes
 * away).
 * 
 * @author paragraval
 */
public class ThisScopeResolverExtensionRegistry {

	public static final String EXTENSION_POINT_ID = "org.ebayopensource.vjet.eclipse.core.thisresolver"; //$NON-NLS-1$

	private static final String TAG_RESOLVER = "resolver"; //$NON-NLS-1$

	private Map<String, ThisScopeResolverExtension> resolversMap;
	private boolean isInitialized = false;

	/**
	 * Default constructor
	 */
	public ThisScopeResolverExtensionRegistry() {
		resolversMap = new HashMap<String, ThisScopeResolverExtension>();
	}

	/**
	 * Returns a collection of extensions registered as a 'this' scope resolver
	 * resolver.
	 * 
	 * @return collection of extensions registered as a 'this' scope resolver
	 *         resolver
	 */
	public Collection<ThisScopeResolverExtension> getResolverExtensions() {
		if (isInitialized) {
			return resolversMap.values();
		}

		// initialize the extensions
		IExtensionPoint thisScopeResolverExtPoint = Platform
				.getExtensionRegistry().getExtensionPoint(EXTENSION_POINT_ID);

		if (thisScopeResolverExtPoint == null) {
			return resolversMap.values();
		}

		IExtension[] thisScopeResolverExtensions = thisScopeResolverExtPoint
				.getExtensions();

		for (IExtension thisScopeResolverExtension : thisScopeResolverExtensions) {

			IConfigurationElement[] configElements = thisScopeResolverExtension
					.getConfigurationElements();

			for (IConfigurationElement element : configElements) {

				if (element == null) {
					continue;
				}
				String elementName = element.getName();
				if (elementName.equals(TAG_RESOLVER)) {
					readThisScopeResolverExtension(element);
				}

			}

		}

		return resolversMap.values();
	}

	private void readThisScopeResolverExtension(IConfigurationElement element) {
		ThisScopeResolverExtension extension = new ThisScopeResolverExtension(
				element);
		resolversMap.put(extension.getKey(), extension);

	}

	/**
	 * Returns reference to {@link ThisScopeResolverExtension} based on given
	 * key, if available, otherwise returns <code>null</code>.
	 * 
	 * @param key
	 *            a key to which the resolver is registered
	 * @return reference to {@link ThisScopeResolverExtension} based on given
	 *         key, if available, otherwise returns <code>null</code>.
	 */
	public ThisScopeResolverExtension getResolverExtension(String key) {
		// To make sure that the registry is initialized
		getResolverExtensions();
		return resolversMap.get(key);
	}

	/**
	 * For the given <code>key</code>, finds the corresponding
	 * {@link ThisScopeResolverExtension} and if available, creates and returns
	 * the reference to {@link IThisObjScopeResolver}.
	 * <p>
	 * Returns <code>null</code>, if no extension found based on given key.
	 * 
	 * @param key
	 *            a key to which the resolver is registered
	 * @return reference to {@link IThisObjScopeResolver} based on given key, if
	 *         available, otherwise returns <code>null</code>.
	 * @throws CoreException
	 */
	public IThisObjScopeResolver getResolver(String key) throws CoreException {
		ThisScopeResolverExtension extension = getResolverExtension(key);
		if (extension != null) {
			return extension.createResolver();
		} else {
			return null;
		}
	}
}

/**
 * 
 */
package org.ebayopensource.vjet.eclipse.core.typeconstruct;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.jstojava.resolver.ITypeConstructorResolver;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

/**
 * A registry of {@link ITypeConstructorResolver}.
 * <p>
 * This is not available as singleton but can be used by creating
 * <code>new</code> instance every time. This helps avoiding problems due to
 * dynamic nature of plug-ins (e.g. if a plug-in contributing extension goes
 * away).
 * 
 * @author paragraval
 */
public class TypeConstructResolverRegistry {

	public static final String EXTENSION_POINT_ID = "org.ebayopensource.vjet.eclipse.core.typeconstruct"; //$NON-NLS-1$

	private static final String TAG_RESOLVER = "resolver"; //$NON-NLS-1$

	private Map<String, ITypeConstructorResolver> resolversMap;
	private boolean isInitialized = false;

	/**
	 * Default constructor
	 */
	public TypeConstructResolverRegistry() {
		resolversMap = new HashMap<String, ITypeConstructorResolver>();
	}

	/**
	 * Returns a collection of extensions registered as a type constructor
	 * resolver.
	 * 
	 * @return collection of extensions registered as a type constructor
	 *         resolver
	 */
	public Collection<ITypeConstructorResolver> getResolvers() {
		if (isInitialized) {
			return resolversMap.values();
		}
		// initialize the extensions

		IExtensionPoint typeConstructResolverExtPoint = Platform.getExtensionRegistry()
				.getExtensionPoint(EXTENSION_POINT_ID);

		if (typeConstructResolverExtPoint == null) {
			return resolversMap.values();
		}
		
		
		return resolversMap.values();
	}

	/**
	 * Returns reference to {@link ITypeConstructorResolver} based on given key,
	 * if available, otherwise returns <code>null</code>.
	 * 
	 * @param key
	 *            a key to which the resolver is registered
	 * @return reference to {@link ITypeConstructorResolver} based on given key,
	 *         if available, otherwise returns <code>null</code>.
	 */
	public ITypeConstructorResolver getResolver(String key) {
		// To make sure that the registry is initialized
		getResolvers();
		return resolversMap.get(key);
	}
}

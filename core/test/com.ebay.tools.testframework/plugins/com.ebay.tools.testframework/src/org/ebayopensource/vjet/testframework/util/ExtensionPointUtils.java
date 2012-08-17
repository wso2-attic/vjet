/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;


/**
 * 
 * @author ddodd
 * 
 * Set of utilities to help with consuming extension points
 *
 */
public class ExtensionPointUtils {
	private static final IExtension[] NONE = new IExtension[0];


	public static List<IConfigurationElement> getConfigurationElements(
			IExtension[] extensions, String configElementName) {
		if (extensions == null) {
			return null;
		}
		List<IConfigurationElement> result = new ArrayList<IConfigurationElement>();
		for (IExtension extension : extensions) {
			IConfigurationElement[] ces = extension.getConfigurationElements();
			if (null == ces) {
				// skipping
			} else if (null == configElementName) {
				result.addAll(Arrays.asList(ces));
			} else {
				for (IConfigurationElement element : ces) {
					String name = element.getName();
					if ((null != name) && configElementName.equals(name)) {
						result.add(element);
					}
				}
			}
		}

		return result;
	}

	/**
	 * @param extensionPointID
	 *            ID of the extension point
	 * @param configElementName
	 *            Name of the configuration elements to get; null to get all of
	 *            them
	 */
	public static List<IConfigurationElement> getConfigurationElements(
			String extensionPointID, String configElementName) {
		IExtension[] extensions = getExtensions(extensionPointID);

		return getConfigurationElements(extensions, configElementName);
	}

	public static IExtension[] getExtensions(String extensionPointID) {
		if (null == extensionPointID) {
			return NONE;
		}
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		if (registry == null) {
			return NONE;
		}
		
		IExtensionPoint ep = registry.getExtensionPoint(extensionPointID);
		if (null == ep) {
			return NONE;
		}
		return ep.getExtensions();
	}

	public static class LazyElement<T> {
		public final IConfigurationElement element;
		public final String attributeName;
		private T result;
		private Throwable thrown;

		public LazyElement(IConfigurationElement element, String attributeName) {
			this.element = element;
			this.attributeName = attributeName;
		}

		/**
		 * Get instance specified by class attribute, saving any exception
		 * thrown.
		 * 
		 * @return T or null if exception thrown
		 */
		@SuppressWarnings("unchecked")
		public T getItem() {
			if ((null == result) && (null == thrown)) {
				try {
					return result = (T) element
							.createExecutableExtension(attributeName);
				} catch (CoreException e) {
					this.thrown = e;
				} catch (ClassCastException e) {
					this.thrown = e;
				}
			}
			return result;
		}

		/**
		 * 
		 * @return Throwable thrown from {@link #getItem()}, if any.
		 */
		public Throwable thrown() {
			return thrown;
		}
	}
}

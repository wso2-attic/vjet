/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.validation.model;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.mod.core.DLTKCore;

public class ValidatorDefinitionFactory {

	public static final String VALIDATORS_EXTENSION_POINT = VjetPlugin.PLUGIN_ID
			+ ".validators";

	public static final String VALIDATOR_ELEMENT = "validator";
	
	private static List<ValidatorDefinition> validatorDefinitions;

	/**
	 * Returns all contributed {@link ValidatorDefinition}.
	 */
	public synchronized static List<ValidatorDefinition> getValidatorDefinitions() {
		if (validatorDefinitions != null) {
			return validatorDefinitions;
		}
		validatorDefinitions =
				new ArrayList<ValidatorDefinition>();
		for (IExtension extension : Platform.getExtensionRegistry()
				.getExtensionPoint(VALIDATORS_EXTENSION_POINT)
						.getExtensions()) {
			for (IConfigurationElement element : extension
					.getConfigurationElements()) {
				if (VALIDATOR_ELEMENT.equals(element.getName())) {
					try {
						ValidatorDefinition validatorDefinition =
									new ValidatorDefinition(element);
						validatorDefinitions.add(validatorDefinition);
					}
					catch (CoreException e) {
						DLTKCore.error(e.toString(), e);
					}
				}
			}
		}
		return validatorDefinitions;
	}

	/**
	 * Returns a specific {@link ValidatorDefinition} or null if the requested
	 * one can't be found.
	 * @param validatorId the id of the desired {@link ValidatorDefinition}
	 */
	public static ValidatorDefinition getValidatorDefinition(String validatorId) {
		for (ValidatorDefinition validator : getValidatorDefinitions()) {
			if (validator.getID().equals(validatorId)) {
				return validator;
			}
		}
		return null;
	}

}

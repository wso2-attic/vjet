/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.validation.model;

import org.ebayopensource.vjet.eclipse.core.validation.AbstractValidator;
import org.ebayopensource.vjet.eclipse.core.validation.IValidator;
import org.ebayopensource.vjet.eclipse.core.validation.PersistablePreferenceObjectSupport;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

public class ValidatorDefinition extends PersistablePreferenceObjectSupport{

	private static final String CLASS_ATTRIBUTE = "class";

	private static final String DESCRIPTION_ATTRIBUTE = "description";

	private static final String ENABLED_BY_DEFAULT_ATTRIBUTE = "enabledByDefault";

	private static final String ENABLEMENT_PREFIX = "validator.enable.";

	private static final String ICON_ATTRIBUTE = "icon";

	private static final String ID_ATTRIBUTE = "id";

	private static final String NAME_ATTRIBUTE = "name";

	private static final String ORDER_ATTRIBUTE = "order";

	private String description;

	private String iconUri;

	private String id;

	private String name;

	private String namespaceUri;
	
	private int order;

	private IValidator validator;

	public ValidatorDefinition(IConfigurationElement element)
			throws CoreException {
		init(element);
	}

	public String getDescription() {
		return description;
	}

	public String getIconUri() {
		return iconUri;
	}

	public String getID() {
		return id;
	}

	public int getOrder() {
		return order;
	}

	public String getName() {
		return name;
	}

	public String getNamespaceUri() {
		return namespaceUri;
	}

	@Override
	protected String getPreferenceId() {
		return ENABLEMENT_PREFIX + id;
	}

	public IValidator getValidator() {
		return validator;
	}

	private void init(IConfigurationElement element) throws CoreException {
		Object executable = element.createExecutableExtension(CLASS_ATTRIBUTE);
		if (executable instanceof IValidator) {
			validator = (IValidator) executable;
		}
		id = element.getContributor().getName() + "."
				+ element.getAttribute(ID_ATTRIBUTE);
		name = element.getAttribute(NAME_ATTRIBUTE);
		description = element.getAttribute(DESCRIPTION_ATTRIBUTE);
		iconUri = element.getAttribute(ICON_ATTRIBUTE);
		//String orderString = element.getAttribute(ORDER_ATTRIBUTE);
		initValidator();
	}

	private void initValidator() {
		if (validator instanceof AbstractValidator) {
			((AbstractValidator) validator).setValidatorId(id);
		}
	}

	@Override
	public String toString() {
		return id + " (" + validator.getClass().getName() + ")";
	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.control;

import java.util.List;


/**
 * Contract for Java to JS translation initializers.
 * <p>
 * There can be one top-level initializer for each translation process.
 * The top-level initializer can specify other initializers that it
 * depends on</p>
 * <p>
 * When initialization starts, translation controller will first collect
 * all initializers based on dependency chain starting from top-level
 * initializer. Then all initializers will be invoked one by one based on 
 * their locations in the dependency chains. Please note:
 * <li>Same type of initializers will be dedupped.</li>
 * <li>The deeper is it in the chains, the earlier will it be executed.</li>
 * <li>Exception should be thrown if any cyclic dependency is detected.</li>
 * </p>
 */
public interface ITranslationInitializer {
	
	/**
	 * Answer a list of other initializers that should be invoked
	 * as part of translation initialization
	 * @return List<ITranslationInitializer>
	 */
	List<ITranslationInitializer> getDependents();
	
	/**
	 * Perform initialization for the translation.
	 */
	void initialize();
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/

package org.ebayopensource.dsf.jstojava.cml.vjetv.core;

import org.ebayopensource.dsf.jstojava.cml.vjetv.model.IHeadLessLauncherResult;
import org.ebayopensource.dsf.jstojava.cml.vjetv.model.IHeadlessLauncherConfigure;
import org.ebayopensource.dsf.jstojava.cml.vjetv.reporter.IHeadLessReporter;

/**
 * Class/Interface description
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public interface IHeadLessLauncher {

    /**
     * Headless model launch entry
     * 
     * @param conf
     *            {@link IHeadlessLauncherConfigure}
     * @param reporter
     *            {@link IHeadLessReporter}
     * @return {@link IHandLessLauncherResult}
     * @throws Exception 
     */
    public IHeadLessLauncherResult launch(IHeadlessLauncherConfigure conf,
            IHeadLessReporter reporter) throws Exception;

    /**
     * Headless model launch entry
     * 
     * @param conf
     *            {@link IHeadlessLauncherConfigure}
     * @return {@link IHandLessLauncherResult}
     */
    public IHeadLessLauncherResult launch(IHeadlessLauncherConfigure conf);

}

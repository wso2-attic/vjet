/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.mod.internal.core;

import org.eclipse.dltk.mod.core.IModelElement;

public interface IJSModelElementInfo {

	public void addChild(IModelElement child);

	public IModelElement[] getChildren();

	public void removeChild(IModelElement child);

	public void setChildren(IModelElement[] children);
}

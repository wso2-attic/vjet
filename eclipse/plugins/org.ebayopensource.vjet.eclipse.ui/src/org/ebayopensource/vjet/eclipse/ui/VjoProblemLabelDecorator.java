/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.vjet.eclipse.ui;

import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.dltk.mod.ui.ProblemsLabelDecorator;
import org.eclipse.dltk.mod.ui.viewsupport.ImageDescriptorRegistry;
import org.eclipse.dltk.mod.ui.viewsupport.ImageImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

/**
 * 
 * 
 */
public class VjoProblemLabelDecorator extends ProblemsLabelDecorator {
	private ImageDescriptorRegistry m_registry;
	private boolean m_useNewRegistry;

	public VjoProblemLabelDecorator() {
		m_useNewRegistry = true;
	}

	@Override
	public Image decorateImage(Image image, Object obj) {
		int adornmentFlags = computeAdornmentFlags(obj);
		if (adornmentFlags != 0) {
			ImageDescriptor baseImage = new ImageImageDescriptor(image);
			Rectangle bounds = image.getBounds();
			return getRegistry().get(
					new VjoElementImageDescriptor(baseImage, adornmentFlags,
							new Point(bounds.width, bounds.height)));
		}
		return image;
	}

	private ImageDescriptorRegistry getRegistry() {
		if (m_registry == null) {
			m_registry = m_useNewRegistry ? new ImageDescriptorRegistry()
					: DLTKUIPlugin.getImageDescriptorRegistry();
		}
		return m_registry;
	}
}

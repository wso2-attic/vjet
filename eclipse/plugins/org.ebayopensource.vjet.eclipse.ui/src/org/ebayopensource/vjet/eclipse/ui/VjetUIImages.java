/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.ui;

import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.ui.PluginImagesHelper;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public class VjetUIImages {
	public static final String IMAGE_BINARY_EDITOR_TITLE = "IMAGE_BINARY_EDITOR_TITLE";
	public static final String IMAGE_OUTOFPATH_EDITOR_TITLE = "IMAGE_OUTOFPATH_EDITOR_TITLE";

	// add by patrick
	public static final String IMG_METHOD_GLOBAL = "method_global_obj.gif";
	public static final String IMG_FIELD_GLOBAL = "field_global_obj.gif";
	
	public static final ImageDescriptor DESC_METHOD_GLOBAL;
	public static final ImageDescriptor DESC_FIELD_GLOBAL;
	// end add
	private static final PluginImagesHelper helper = new PluginImagesHelper(
			VjetUIPlugin.getDefault().getBundle(), new Path("/icons/full/"));

	static {
		helper.createManaged(PluginImagesHelper.T_OBJ, "classf_obj.gif",
				IMAGE_BINARY_EDITOR_TITLE);
		helper.createManaged(PluginImagesHelper.T_OBJ, "sourceEditorOut.GIF",
				IMAGE_OUTOFPATH_EDITOR_TITLE);
		// add by patrick
		DESC_METHOD_GLOBAL = helper.createManaged(PluginImagesHelper.T_OBJ,
				IMG_METHOD_GLOBAL, IMG_METHOD_GLOBAL);
		DESC_FIELD_GLOBAL = helper.createManaged(PluginImagesHelper.T_OBJ,
				IMG_FIELD_GLOBAL, IMG_FIELD_GLOBAL);
		// end add
	}

	public static Image getImage(String key) {
		return helper.get(key);
	}

	public static ImageDescriptor getImageDescriptor(String key) {
		return helper.getDescriptor(key);
	}

	public static void put(String key, Image image) {
		helper.getImageRegistry().put(key, image);
	}

	public static void put(String key, ImageDescriptor imageDescriptor) {
		helper.getImageRegistry().put(key, imageDescriptor);
	}
}

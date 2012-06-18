/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.typespace.efs;

import java.net.URI;
import java.net.URISyntaxException;

import org.ebayopensource.vjet.eclipse.typespace.efs.internal.Activator;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileSystem;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

/**
 * A test file system that keeps everything in memory.
 */
public class TypeSpaceFileSystem extends FileSystem {
	public static final String SCEME_TYPESPACE = "typespace";

//	/**
//	 * Converts a path to a URI in the memory file system.
//	 * @param path
//	 * @return
//	 */
//	public static URI toURI(IPath path) {
//		try {
//			return new URI(TypeSpaceFileSystem.SCEME_TYPESPACE, null, path.setDevice(null).toPortableString(), null);
//		} catch (URISyntaxException e) {
//		    Activator
//            .getDefault()
//            .getLog()
//            .log((IStatus) new Status(Status.ERROR, Activator.PLUGIN_ID,
//                            "could not create store for " + path.toString(), e));
//		
//		}
//	}

	public TypeSpaceFileSystem() {
		super();
	}

	public IFileStore getStore(URI uri) {
		try{
			return new TypeSpaceFileStore(new TSURI(uri));
		}catch (Exception e) {
		    Activator
            .getDefault()
            .getLog()
            .log((IStatus) new Status(Status.ERROR, Activator.PLUGIN_ID,
                            "could not create store for " + uri.toString(), e));
		    return EFS.getNullFileSystem().getStore(uri);
		}
		}

	public boolean isCaseSensitive() {
		return true;
	}
	
	@Override
	public boolean canDelete() {
		return false;
	}
	
	@Override
	public boolean canWrite() {
		return false;
	}
	
}

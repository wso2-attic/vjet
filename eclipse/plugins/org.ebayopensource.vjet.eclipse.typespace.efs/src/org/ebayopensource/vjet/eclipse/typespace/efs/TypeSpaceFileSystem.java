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

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipException;

import org.ebayopensource.vjet.eclipse.typespace.efs.internal.Activator;
import org.ebayopensource.vjet.eclipse.typespace.efs.internal.GroupItem;
import org.ebayopensource.vjet.eclipse.typespace.efs.internal.GroupRootItem;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileSystem;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * A test file system that keeps everything in memory.
 */
public class TypeSpaceFileSystem extends FileSystem {
	public static final String SCEME_TYPESPACE = "typespace";
	private static Map<URI, GroupItem> tsItemCache = new HashMap<URI, GroupItem>();
	private Map<URI, TypeSpaceFileStore> tstypeURICache = new HashMap<URI, TypeSpaceFileStore>();

	// /**
	// * Converts a path to a URI in the memory file system.
	// * @param path
	// * @return
	// */
	// public static URI toURI(IPath path) {
	// try {
	// return new URI(TypeSpaceFileSystem.SCEME_TYPESPACE, null,
	// path.setDevice(null).toPortableString(), null);
	// } catch (URISyntaxException e) {
	// Activator
	// .getDefault()
	// .getLog()
	// .log((IStatus) new Status(Status.ERROR, Activator.PLUGIN_ID,
	// "could not create store for " + path.toString(), e));
	//
	// }
	// }

	public TypeSpaceFileSystem() {
		super();
	}

	public static GroupItem getItem(URI uri) throws ZipException, IOException,
			CoreException {
		if (tsItemCache .containsKey(uri)) {
			return (GroupItem) tsItemCache.get(uri);
		} else {
			String groupName = uri.getHost();
			GroupRootItem item = new GroupRootItem(groupName);
			tsItemCache.put(uri, item);
			return item;
		}
	}

	public IFileStore getStore(URI uri) {
		try {
			System.out.println("looking for store:" + uri);
			if (tstypeURICache.containsKey(uri)) {
				return (TypeSpaceFileStore) tstypeURICache.get(uri);
			} else {
				TypeSpaceFileStore store = new TypeSpaceFileStore("root", null,uri);
				tstypeURICache.put(uri, store);
				return store;
			}
		} catch (Exception e) {
			Activator
					.getDefault()
					.getLog()
					.log((IStatus) new Status(Status.ERROR,
							Activator.PLUGIN_ID, "could not create store for "
									+ uri.toString(), e));
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

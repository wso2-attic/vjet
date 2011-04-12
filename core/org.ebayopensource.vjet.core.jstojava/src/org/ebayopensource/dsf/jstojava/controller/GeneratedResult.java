/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GeneratedResult {

	private Map<String, List<File>> genFiles;

	public void addFile(File source, File genedFile) {

		String key = source.getAbsolutePath();
		key = key.replace('\\', '/');
		List<File> files = getGenFiles().get(key);
		if (files == null) {
			files = new ArrayList<File>(3);
		}
		
		files.add(genedFile);
		getGenFiles().put(key, files);

	}

	public List<File> getGenFilesForSource(String source) {
		source = source.replace('\\', '/');
		List<File> list = getGenFiles().get(source);
		if(list==null){
			return null;
		}
		return Collections.unmodifiableList(list);
	}

	public Set<String> getAllSourceFiles() {
		return Collections.unmodifiableSet(getGenFiles().keySet());
	}

	public List<File> getAllGeneratedFiles() {
		List<File> all = new ArrayList<File>();
		for (List<File> l : getGenFiles().values()) {
			all.addAll(l);
		}
		return Collections.unmodifiableList(all);
	}

	private Map<String, List<File>> getGenFiles() {
		if (genFiles == null) {
			genFiles = new LinkedHashMap<String, List<File>>();
		}
		return genFiles;
	}

}

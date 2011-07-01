/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.ts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.ebayopensource.dsf.jsgen.shared.generate.CodeStyle;
import org.ebayopensource.dsf.jsgen.shared.vjo.GeneratorCtx;
import org.ebayopensource.dsf.jsgen.shared.vjo.VjoGenerator;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.mod.core.DLTKCore;

/**
 * Job to generate all JsNative type's js infomation.
 * 
 * 
 * 
 */
public class JstNativeTypeGenJob extends WorkspaceJob {

	private static final String TYPE_SPACE_LOADING = "Gen JstNativeType Info";

	public JstNativeTypeGenJob() {
		super(TYPE_SPACE_LOADING);
	}

	@Override
	public IStatus runInWorkspace(IProgressMonitor monitor)
			throws CoreException {
		GeneratorCtx m_generatorCtx = new GeneratorCtx(CodeStyle.PRETTY);
		Iterator<String> s = TypeSpaceMgr.NATIVE_GLOBAL_OBJECTS.iterator();
		while (s.hasNext()) {
			String name = s.next();
			IJstType type = CodeassistUtils.findNativeJstType(name);
			VjoGenerator writer = m_generatorCtx.getProvider()
					.getTypeGenerator();
			try {
				cacheText(name, type.toString());
//				cacheText(writer.writeVjo(type).getGeneratedText());
			} catch (Exception e) {
				DLTKCore.error(e.toString(), e);
			}
		}

		return Status.OK_STATUS;

	}

	private void cacheText(String name, String generatedText) {
		File file = getNativeTypeCacheFile();
		if (!file.exists()) {
			return;
		}
		File jsFile = new File (file, name + ".js");
		FileOutputStream fo = null;
		try {
			fo = new FileOutputStream(jsFile);
			fo.write(generatedText.getBytes());
		} catch (FileNotFoundException e) {
			DLTKCore.error(e.toString(), e);
		} catch (IOException e) {
			DLTKCore.error(e.toString(), e);
		} finally {
			try {
				fo.close();
			} catch (IOException e) {
				DLTKCore.error(e.toString(), e);
			}
		}
	}
	
	private File getNativeTypeCacheFile() {
		IPath path = DLTKCore.getDefault().getStateLocation();
		File file = path.toFile();
		if (!file.exists()) {
			file.mkdir();
		}
		File nDirectory = new File(file, "native");
		if (!nDirectory.exists()) {
			nDirectory.mkdir();
		}
		return nDirectory;
	}

}

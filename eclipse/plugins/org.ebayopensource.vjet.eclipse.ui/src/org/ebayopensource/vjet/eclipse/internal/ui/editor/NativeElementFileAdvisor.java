///*******************************************************************************
// * Copyright (c) 2005-2011 eBay Inc.
// * All rights reserved. This program and the accompanying materials
// * are made available under the terms of the Eclipse Public License v1.0
// * which accompanies this distribution, and is available at
// * http://www.eclipse.org/legal/epl-v10.html
// *
// *******************************************************************************/
//package org.ebayopensource.vjet.eclipse.internal.ui.editor;
//
//import java.io.File;
//
//import org.ebayopensource.dsf.jst.FileBinding;
//import org.ebayopensource.dsf.jst.IJstType;
//import org.ebayopensource.dsf.jst.JstSource.IBinding;
//import org.ebayopensource.vjet.eclipse.internal.core.util.Util;
//import org.ebayopensource.vjo.tool.typespace.SourceTypeName;
//import org.eclipse.dltk.mod.core.IModelElement;
//import org.eclipse.dltk.mod.internal.core.SourceModule;
//import org.eclipse.dltk.mod.internal.core.VjoSourceModule;
//import org.eclipse.dltk.mod.internal.ui.editor.IModelElementFileAdivsor;
//
//public class NativeElementFileAdvisor implements IModelElementFileAdivsor {
//
//	public File getFile(IModelElement element) {
//		if (element instanceof VjoSourceModule){
//			VjoSourceModule sourceModule = (VjoSourceModule)element;
//			IJstType jType =  sourceModule.getJstType();
//			if ((jType != null) && (jType.getSource() != null) && (jType.getSource().getBinding() != null)) {
//				IBinding binding = jType.getSource().getBinding();
//				if (binding instanceof FileBinding) {
//					File file = ((FileBinding)binding).getFile();
//					return file;
//				}
//				
//			}
//			
//			SourceTypeName tname = sourceModule.getTypeName();
//			return getNativeFile(tname);
//		}
//		return null;
//	}
//	
//	private File getNativeFile(SourceTypeName tname) {
//		
//		File file = Util.getNativeTypeCacheFile(tname);
//		return file;
//	}
//	
//
//
//	public String getSupportedType() {
//		return SourceModule.class.getName();
//	}
//
//
//}

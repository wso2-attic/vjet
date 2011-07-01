/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.mod.internal.core;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjo.tool.typespace.SourceTypeName;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IProjectFragment;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;

public class VjoSourceHelper {

	public static void getAllSourceModulesFromJst(final List<ISourceModule> selectedSourceModules, 
			final List<IJstType> jstTypes,
			final ScriptProject scriptProject){
		for(IJstType type: jstTypes){
			final IType found = CodeassistUtils.findType(scriptProject, type);
			if(found != null && found instanceof VjoSourceType){
				final ISourceModule sourceModule = ((VjoSourceType)found).getSourceModule();
				if(sourceModule instanceof VjoSourceModule){
					selectedSourceModules.add(sourceModule);
				}
			}
		}
	}
	
	public static void getAllSourceModulesFromProject(
			List<ISourceModule> selectedSourceModules, ScriptProject sp) {
		try {
			IModelElement[] frags = sp.getChildren();
			getChildrenFromFragment(selectedSourceModules, frags, sp);
		} catch (ModelException e) {
			e.printStackTrace();
		}
	}
	
	public static void getChildrenFromFragment(List<ISourceModule> resourceList,
			IModelElement[] element, ScriptProject sProject)
			throws ModelException {
		IResource resource = null;
		for (IModelElement pf : element) {
			IModelElement[] elements = ((ModelElement) pf).getChildren();
			for (IModelElement modelElement : elements) {
				IModelElement[] files = ((ModelElement) modelElement)
						.getChildren();
				for (IModelElement file : files) {
					if (file.getElementType() == IProjectFragment.SOURCE_MODULE) {
						resource = file.getResource();
						if (resource != null && resource.getLocation().toOSString().endsWith(
								".js")) {
							addSourceModules(resourceList, resource, sProject);
						}
					}
				}
			}
		}
	}
	
	public static void addSourceModules(List<ISourceModule> selectedSourceModules,
			IResource selectionElement, ScriptProject sProject) {
		ISourceModule module = null;
		if (!selectedSourceModules.contains(selectionElement)) {
			module = getModuleFromResource(selectionElement, sProject);
			if (module != null
					&& module.getElementType() == IModelElement.SOURCE_MODULE) {
				selectedSourceModules.add(module);
			}
		}
	}
	
	public static ISourceModule getModuleFromResource(IResource selectionElement,
			ScriptProject sProject) {
		SourceTypeName typeName = null;
		ISourceModule module = null;
		IType type = null;
		typeName = getFileQulifieName(selectionElement);
		if (typeName != null) {
			type = CodeassistUtils.findType(sProject, typeName.typeName());
			if (type != null) {
				module = type.getSourceModule();
			}
		}
		return module;
	}
	
	public static SourceTypeName getFileQulifieName(IResource resource) {
		if (resource instanceof IFile
				&& resource.getLocation().toOSString().endsWith(".js")) {
			return CodeassistUtils.getTypeName(resource);
		}
		return null;
	}
}

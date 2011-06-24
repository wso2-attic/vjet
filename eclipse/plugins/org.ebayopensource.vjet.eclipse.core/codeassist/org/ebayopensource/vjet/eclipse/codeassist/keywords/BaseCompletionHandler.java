/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.keywords;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.vjet.eclipse.core.ClassFileConstants;
import org.ebayopensource.vjet.eclipse.core.IImportDeclaration;
import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.IJSType;
import org.ebayopensource.vjet.eclipse.internal.core.util.Util;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.CompletionProposal;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IScriptFolder;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.DefaultWorkingCopyOwner;
import org.eclipse.dltk.mod.internal.core.ModelElementRequestor;
import org.eclipse.dltk.mod.internal.core.NameLookup;
import org.eclipse.dltk.mod.internal.core.ScriptProject;

/**
 * Base class for all  package/type completions
 * 
 * 
 *
 */
public class BaseCompletionHandler implements ICompletionHandler {

	public Class getCompletionClass() {
		return null;
	}

	/**
	 * populate {@link list} with package/type completion proposals
	 * 
	 * @param sourceModule - current {@link ISourceModule}
	 * @param position - completion position
	 * @param completion - {@link JstCompletion}
	 * @param list - {@link List} of completion proposals
	 */
	public void complete(ISourceModule sourceModule, int position,
			JstCompletion completion, List<CompletionProposal> list) {
//		completePackages(sourceModule, position, completion, list);
//		completeTypes(sourceModule, position, completion, list);

		String wordBefore = completion.getToken();
		if (wordBefore.length() == 0) {
			return;
		}
		if (!(sourceModule instanceof IModelElement)) {
			// should not happen
			return;
		}
		NameLookup lookup = getNameLookup((IModelElement) sourceModule);
		if (lookup == null) {
			return;
		}
		IScriptFolder[] paths = lookup.findScriptFolders(wordBefore, true);

		if (paths != null) {
			String[] packageProposals = createPackageProposals(paths);
			if (packageProposals.length > 0) {
				addProposals(list, packageProposals, position,
						CompletionProposal.PACKAGE_REF,
						getRelevance(CompletionProposal.PACKAGE_REF), wordBefore);
			}
		}
		
		String currentPath = "";
		int lastDotPosition = wordBefore.lastIndexOf('.');
		if (lastDotPosition != -1) {
			currentPath = wordBefore.substring(0, lastDotPosition);
		} else {
			// get current path from type
			// if (sourceModule instanceof JSSourceModule) {
			// String folderPath = ((JSSourceModule) sourceModule).getParent()
			// .getResource().getProjectRelativePath().toString();
			// currentPath = folderPath.replace('/', '.');
			// }
		}
		
		IScriptFolder[] currentPackage = lookup.findScriptFolders(currentPath,
				false);
		ModelElementRequestor modelRequestor = new ModelElementRequestor();

		if (currentPackage != null && currentPackage.length > 0) {
			lookup.seekTypes(wordBefore
					.substring(wordBefore.lastIndexOf('.') + 1),
					currentPackage[0], true, NameLookup.ACCEPT_ALL,
					modelRequestor);

			// Add also types that were already imported
			if (lastDotPosition == -1) {
				searchBetweenImported(sourceModule, lookup, modelRequestor,
						wordBefore);
			}

			if (modelRequestor.getTypes() != null
					&& modelRequestor.getTypes().length > 0) {
				List<IType> typeProposals = createTypeProposals(
						getType(sourceModule), modelRequestor);
				addProposals(list, wordBefore, typeProposals,
						CompletionProposal.TYPE_REF,
						getRelevance(CompletionProposal.TYPE_REF), position);
			}
		}

		
	}
	
	protected void completeTypes(ISourceModule sourceModule, int position,
			JstCompletion completion, List<CompletionProposal> list) {
		NameLookup lookup = getNameLookup((IModelElement) sourceModule);
		if (lookup == null) {
			return;
		}
		
		String wordBefore = completion.getToken();
		if (wordBefore.length() == 0) {
			return;
		}
		String currentPath = "";
		int lastDotPosition = wordBefore.lastIndexOf('.');
		if (lastDotPosition != -1) {
			currentPath = wordBefore.substring(0, lastDotPosition);
		} else {
			// get current path from type
			// if (sourceModule instanceof JSSourceModule) {
			// String folderPath = ((JSSourceModule) sourceModule).getParent()
			// .getResource().getProjectRelativePath().toString();
			// currentPath = folderPath.replace('/', '.');
			// }
		}
		IScriptFolder[] currentPackage = lookup.findScriptFolders(currentPath,
				false);
		ModelElementRequestor modelRequestor = new ModelElementRequestor();

		if (currentPackage != null && currentPackage.length > 0) {
			lookup.seekTypes(wordBefore
					.substring(wordBefore.lastIndexOf('.') + 1),
					currentPackage[0], true, NameLookup.ACCEPT_ALL,
					modelRequestor);

			// Add also types that were already imported
			if (lastDotPosition == -1) {
				searchBetweenImported(sourceModule, lookup, modelRequestor,
						wordBefore);
			}

			if (modelRequestor.getTypes() != null
					&& modelRequestor.getTypes().length > 0) {
				List<IType> typeProposals = createTypeProposals(
						getType(sourceModule), modelRequestor);
				addProposals(list, wordBefore, typeProposals,
						CompletionProposal.TYPE_REF,
						getRelevance(CompletionProposal.TYPE_REF), position);
			}
		}
	}
	
	public void completePackages(ISourceModule sourceModule, int position,
			JstCompletion completion, List<CompletionProposal> list) {
		String wordBefore = completion.getToken();
		if (wordBefore.length() == 0) {
			return;
		}
		if (!(sourceModule instanceof IModelElement)) {
			// should not happen
			return;
		}
		NameLookup lookup = getNameLookup((IModelElement) sourceModule);
		if (lookup == null) {
			return;
		}
		IScriptFolder[] paths = lookup.findScriptFolders(wordBefore, true);

		if (paths != null) {
			String[] packageProposals = createPackageProposals(paths);
			if (packageProposals.length > 0) {
				addProposals(list, packageProposals, position,
						CompletionProposal.PACKAGE_REF,
						getRelevance(CompletionProposal.PACKAGE_REF), wordBefore);
			}
		}
		
		
	}

	/**
	 * Adds type completion proposals from imports
	 * 
	 * @param sm - current {@link ISourceModule}
	 * @param lookup - {@link NameLookup}
	 * @param modelRequestor - {@link ModelElementRequestor}
	 * @param wordBefore - word before cursor position 
	 */
	private void searchBetweenImported(ISourceModule sm, NameLookup lookup,
			ModelElementRequestor modelRequestor, String wordBefore) {
		IJSSourceModule sourceModule = (IJSSourceModule) sm;
		try {
			IModelElement[] imports = sourceModule.getImportContainer()
					.getChildren();
			IType[] alreadyAddedTypes = modelRequestor.getTypes();
			List<IType> alreadyAdded = new ArrayList<IType>(
					alreadyAddedTypes.length);
			for (IType type : alreadyAddedTypes) {
				alreadyAdded.add(type);
			}
			for (IModelElement modelElement : imports) {
				IImportDeclaration importDeclaration = (IImportDeclaration) modelElement;
				String fullName = importDeclaration.getElementName();
				int dotIndex = fullName.lastIndexOf('.');
				String lastSegment = fullName.substring(dotIndex + 1);
				IType type = null;
				if ("*".equals(lastSegment)) {
					IScriptFolder[] folders = lookup.findScriptFolders(fullName
							.substring(0, dotIndex), false);
					if (folders != null && folders.length > 0) {
						IModelElement[] children = folders[0].getChildren();
						for (IModelElement modelElement2 : children) {
							if (modelElement2.getElementType() == IModelElement.SOURCE_MODULE) {
								IType[] types = ((IJSSourceModule) modelElement2)
										.getTypes();
								if (types != null && types.length > 0) {
									checkAndAdd(types[0], wordBefore, alreadyAdded,
											modelRequestor);
								}
							}
						}
					}
				} else if (lastSegment.startsWith(wordBefore)) {
					type = lookup.findType(importDeclaration.getElementName(),
							false, NameLookup.ACCEPT_ALL);
					checkAndAdd(type, wordBefore, alreadyAdded, modelRequestor);
				}
			}
		} catch (ModelException e) {
		}
	}

	private static void checkAndAdd(IType type, String prefix,
			Collection<IType> alreadyAdded, ModelElementRequestor modelRequestor) {
		if (type != null) {
			if (type.getElementName().startsWith(prefix)) {
				if (!alreadyAdded.contains(type)) {
					alreadyAdded.add(type);
					modelRequestor.acceptType(type);
				}
			}
		}
	}

	private void addProposals(List<CompletionProposal> keywords,
			String[] array, int position, int type, int relevance, String wordBefore) {
		for (String name : array) {
			CompletionProposal data = CompletionProposal.create(type, position);
			data.setName(name.toCharArray());
			data.setCompletion(name.toCharArray());
			data.setRelevance(relevance);
			data.setReplaceRange(position - wordBefore.length(), position);

			keywords.add(data);
		}

	}

	private void addProposals(List<CompletionProposal> keywords,
			IType[] proposals, int position, int type, int relevance) {
		for (IType itype : proposals) {

			final String typeName;
			typeName = itype.getFullyQualifiedName().replace('/', '.');
			CompletionProposal data = CompletionProposal.create(type, position);
			char[] shortName = itype.getElementName().toCharArray();
			data.setName(typeName.toCharArray());
			data.setCompletion(shortName);
			data.setRelevance(relevance);
			setFlags(itype, data);

			keywords.add(data);
		}

	}

	private void setFlags(IType itype, CompletionProposal data) {

		if (itype instanceof IJSType) {

			try {

				IJSType jsType = (IJSType) itype;

				if (jsType.isInterface()) {
					data.setFlags(ClassFileConstants.AccInterface);
				}

				if (jsType.isEnum()) {
					data.setFlags(ClassFileConstants.AccEnum);
				}

				IJstType jstType = Util.toJstType(jsType);
				if(jstType!=null && jstType.isMixin()) {
					data.setFlags(ClassFileConstants.AccModule);
				}
			} catch (ModelException e) {
				DLTKCore.error(e.toString(), e);
			}
		}
	}

	private void addProposals(List<CompletionProposal> keywords,
			String wordBefore, List<IType> proposals, int type, int relevance,
			int position) {
		List<IType> filteredPropodals = new ArrayList<IType>();
		for (IType name : proposals) {
			final String typeName = name.getFullyQualifiedName().replace('/',
					'.');
			// if (name.getFullyQualifiedName() == null
			// || !typeName.startsWith(wordBefore)) {
			// continue;
			// }

			filteredPropodals.add(name);
		}
		addProposals(keywords, filteredPropodals
				.toArray(new IType[filteredPropodals.size()]), position, type,
				relevance);
	}

	private NameLookup getNameLookup(IModelElement modelElement) {
		IScriptProject project = (IScriptProject) modelElement
				.getAncestor(IModelElement.SCRIPT_PROJECT);
		NameLookup lookup = null;
		try {
			lookup = ((ScriptProject) project)
					.newNameLookup(DefaultWorkingCopyOwner.PRIMARY);
		} catch (ModelException e) {
			DLTKCore.error(e.toString(), e);
		}
		return lookup;
	}

	/**
	 * @param paths - array of {@link IScriptFolder}
	 * @return String array of package names
	 */
	protected String[] createPackageProposals(IScriptFolder[] paths) {
		List<String> packageProposals = new LinkedList<String>();
		for (IScriptFolder path : paths) {
			packageProposals.add(path.getElementName().replace('/', '.'));
		}

		return packageProposals.toArray(new String[packageProposals.size()]);
	}

	/**
	 * @param currentType
	 * @param modelRequestor
	 * @return {@link List} of accessible types
	 */
	protected List<IType> createTypeProposals(IType currentType,
			ModelElementRequestor modelRequestor) {
		if (currentType != null && modelRequestor.getTypes() != null) {
			List<IType> proposals = new ArrayList<IType>(modelRequestor
					.getTypes().length);

			for (IType type : modelRequestor.getTypes()) {
				if (checkType(type, currentType)) {
					proposals.add(type);
				}
			}

			return proposals;
		}

		return null;
	}

	protected int getRelevance(int completionType) {
		switch (completionType) {
		case CompletionProposal.PACKAGE_REF:
			return 10;
		case CompletionProposal.TYPE_REF:
			return 200;
		default:
			return 1;
		}
	}

	private IType getType(ISourceModule sourceModule) {
		try {
			IType[] types = ((IJSSourceModule) sourceModule).getTypes();
			if (types.length > 0) {
				return types[0];
			}
		} catch (ModelException e) {
			DLTKCore.error(e.toString(), e);
		} catch (IndexOutOfBoundsException i) {
			DLTKCore.error(i.toString(), i);
		}
		return null;
	}

	protected boolean checkType(IType type, IType currentType) {
		if (type.equals(currentType)) {
			return false;
		}
		return true;
	}
}

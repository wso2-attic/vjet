/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.refactoring.rename;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.mod.core.DLTKContentTypeManager;
import org.eclipse.dltk.mod.core.DLTKLanguageManager;
import org.eclipse.dltk.mod.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IScriptFolder;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.internal.corext.refactoring.Checks;
import org.eclipse.dltk.mod.internal.corext.refactoring.RefactoringAvailabilityTester;
import org.eclipse.dltk.mod.internal.corext.refactoring.RefactoringCoreMessages;
import org.eclipse.dltk.mod.internal.corext.refactoring.ScriptRefactoringArguments;
import org.eclipse.dltk.mod.internal.corext.refactoring.ScriptRefactoringDescriptor;
import org.eclipse.dltk.mod.internal.corext.refactoring.ScriptRefactoringDescriptorComment;
import org.eclipse.dltk.mod.internal.corext.refactoring.changes.DynamicValidationStateChange;
import org.eclipse.dltk.mod.internal.corext.refactoring.changes.RenameResourceChange;
import org.eclipse.dltk.mod.internal.corext.refactoring.changes.RenameSourceModuleChange;
import org.eclipse.dltk.mod.internal.corext.refactoring.code.ScriptableRefactoring;
import org.eclipse.dltk.mod.internal.corext.refactoring.participants.RenameTypeArguments;
import org.eclipse.dltk.mod.internal.corext.refactoring.participants.ScriptProcessors;
import org.eclipse.dltk.mod.internal.corext.refactoring.rename.RenameModifications;
import org.eclipse.dltk.mod.internal.corext.refactoring.rename.RenameResourceProcessor;
import org.eclipse.dltk.mod.internal.corext.refactoring.rename.RenamingNameSuggestor;
import org.eclipse.dltk.mod.internal.corext.refactoring.rename.ScriptRenameProcessor;
import org.eclipse.dltk.mod.internal.corext.refactoring.tagging.IQualifiedNameUpdating;
import org.eclipse.dltk.mod.internal.corext.refactoring.tagging.IReferenceUpdating;
import org.eclipse.dltk.mod.internal.corext.refactoring.tagging.ISimilarDeclarationUpdating;
import org.eclipse.dltk.mod.internal.corext.refactoring.tagging.ITextUpdating;
import org.eclipse.dltk.mod.internal.corext.refactoring.util.ResourceUtil;
import org.eclipse.dltk.mod.internal.corext.refactoring.vjet.IVjoRefactorings;
import org.eclipse.dltk.mod.internal.corext.util.Messages;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.IResourceMapper;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RefactoringArguments;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;

public class RenameVjoSourceModuleProcessor extends ScriptRenameProcessor implements IReferenceUpdating, ITextUpdating, IQualifiedNameUpdating,
		ISimilarDeclarationUpdating, IResourceMapper {
	
	private RenameVjoTypeProcessor fRenameTypeProcessor=null;
	private boolean fWillRenameType= false;
	
	private static final String ID_RENAME_COMPILATION_UNIT = "org.eclipse.dltk.mod.ui.rename.sourcemodule"; //$NON-NLS-1$
	private static final String ATTRIBUTE_PATH = "path"; //$NON-NLS-1$
	private static final String ATTRIBUTE_NAME = "name"; //$NON-NLS-1$

	private ISourceModule fCu;

	public static final String IDENTIFIER = "org.eclipse.dltk.mod.ui.renameSourceModulerocessor"; //$NON-NLS-1$

	/**
	 * Creates a new rename compilation unit processor.
	 * 
	 * @param unit
	 *            the compilation unit, or <code>null</code> if invoked by
	 *            scripting
	 * @throws CoreException
	 */
	public RenameVjoSourceModuleProcessor(ISourceModule unit) throws CoreException {
		fCu = unit;
		if (fCu != null) {
			computeRenameTypeRefactoring();
			setNewElementName(fCu.getElementName());
		}
	}

	public String getIdentifier() {
		return IDENTIFIER;
	}

	public boolean isApplicable() {
		return RefactoringAvailabilityTester.isRenameAvailable(fCu);
	}

	public String getProcessorName() {
		return RefactoringCoreMessages.RenameSourceModuleRefactoring_name;
	}

	protected String[] getAffectedProjectNatures() throws CoreException {
		return ScriptProcessors.computeAffectedNatures(fCu);
	}

	public Object[] getElements() {
		return new Object[] { fCu };
	}

	protected RenameModifications computeRenameModifications() {
		RenameModifications result = new RenameModifications();
		result.rename(fCu, new RenameArguments(getNewElementName(), getUpdateReferences()));
//		if (DLTKCore.DEBUG) {
//			System.err.println("TODO: Add type renaming here if required..."); //$NON-NLS-1$
//		}
		
		
		
		if (fRenameTypeProcessor != null) {
			String newTypeName= removeFileNameExtension(getNewElementName());
			RenameTypeArguments arguments= new RenameTypeArguments(newTypeName, getUpdateReferences(), getUpdateSimilarDeclarations(), getSimilarElements());
			result.rename(fRenameTypeProcessor.getType(), arguments, getUpdateSimilarDeclarations() 
				? new RenameVjoTypeProcessor.ParticipantDescriptorFilter()
				: null);
		}
		return result;
	}

	protected IFile[] getChangedFiles() throws CoreException {
		IFile file = ResourceUtil.getFile(fCu);
		if (file != null) {
			return new IFile[] { file };
		}
		return new IFile[0];
	}

	// ---- IRenameProcessor -------------------------------------

	public String getCurrentElementName() {
		return getSimpleCUName();
	}

	public String getCurrentElementQualifier() {
		IScriptFolder pack = (IScriptFolder) fCu.getParent();
		return pack.getElementName();
	}

	public RefactoringStatus checkNewElementName(String newName) throws CoreException {		
		Assert.isNotNull(newName, "new name"); //$NON-NLS-1$
		String typeName= removeFileNameExtension(newName);
		RefactoringStatus result= Checks.checkSourceModuleName(newName);
		if (fWillRenameType)
			result.merge(fRenameTypeProcessor.checkNewElementName(typeName));
		
		
		if (Checks.isAlreadyNamed(fCu, newName))
			result.addFatalError(RefactoringCoreMessages.RenameSourceModuleRefactoring_same_name);	 
		return result;
	}

	public void setNewElementName(String newName) {
		super.setNewElementName(newName);
		if (fWillRenameType)
			fRenameTypeProcessor.setNewElementName(removeFileNameExtension(newName));
		
	}

	public Object getNewElement() {
		IModelElement parent = fCu.getParent();
		if (parent.getElementType() != IModelElement.SCRIPT_FOLDER)
			return fCu; // ??
		IScriptFolder pack = (IScriptFolder) parent;
		IDLTKLanguageToolkit tk = null;
		tk = DLTKLanguageManager.getLanguageToolkit(pack);
		if (tk != null && !DLTKContentTypeManager.isValidFileNameForContentType(tk, getNewElementName())) {
			return fCu; // ??
		}
		return pack.getSourceModule(getNewElementName());
	}

	// ---- ITextUpdating ---------------------------------------------

	public boolean canEnableTextUpdating() {
		if (fRenameTypeProcessor == null)
			return false;
		
		return fRenameTypeProcessor.canEnableUpdateReferences();
	}

	public boolean getUpdateTextualMatches() {
		if (fRenameTypeProcessor == null)
			return false;
		return fRenameTypeProcessor.getUpdateTextualMatches();
	}

	public void setUpdateTextualMatches(boolean update) {
		if (fRenameTypeProcessor != null)
			fRenameTypeProcessor.setUpdateTextualMatches(update);
	}

//	// ---- IReferenceUpdating -----------------------------------
//
//	public boolean canEnableUpdateReferences() {
//		return false;
//	}
//
//	public void setUpdateReferences(boolean update) {
//	}
//
//	public boolean getUpdateReferences() {
//		return false;
//	}
//
//	// ---- IQualifiedNameUpdating -------------------------------
//
//	public boolean canEnableQualifiedNameUpdating() {
//		return false;
//	}
//
//	public boolean getUpdateQualifiedNames() {
//		return false;
//	}
//
//	public void setUpdateQualifiedNames(boolean update) {
//	}
//
//	public String getFilePatterns() {
//		return null;
//	}
//
//	public void setFilePatterns(String patterns) {
//	}
//
//	// ---- ISimilarDeclarationUpdating ------------------------------
//
//	public boolean canEnableSimilarDeclarationUpdating() {
//		return false;
//	}
//
//	public void setUpdateSimilarDeclarations(boolean update) {
//		return;
//	}
//
//	public boolean getUpdateSimilarDeclarations() {
//		return false;
//	}
//
//	public int getMatchStrategy() {
//		return RenamingNameSuggestor.STRATEGY_EXACT; // method should not be
//		// called in this case
//		// anyway ...
//	}
//
//	public void setMatchStrategy(int selectedStrategy) {
//		return;
//	}
//
//	public IModelElement[] getSimilarElements() {
//		return null;
//	}
//
//	public IResource getRefactoredResource(IResource element) {
//		return element;
//	}
//
//	public IModelElement getRefactoredScriptElement(IModelElement element) {
//		return element;
//	}
	
	//---- IReferenceUpdating -----------------------------------

	public boolean canEnableUpdateReferences() {
		if (fRenameTypeProcessor == null)
			return false;
		return fRenameTypeProcessor.canEnableUpdateReferences();
	}

	public void setUpdateReferences(boolean update) {
		if (fRenameTypeProcessor != null)
			fRenameTypeProcessor.setUpdateReferences(update);
	}

	public boolean getUpdateReferences(){
		if (fRenameTypeProcessor == null)
			return false;
		return fRenameTypeProcessor.getUpdateReferences();		
	}
	
	//---- IQualifiedNameUpdating -------------------------------

	public boolean canEnableQualifiedNameUpdating() {
		if (fRenameTypeProcessor == null)
			return false;
		return fRenameTypeProcessor.canEnableQualifiedNameUpdating();
	}
	
	public boolean getUpdateQualifiedNames() {
		if (fRenameTypeProcessor == null)
			return false;
		return fRenameTypeProcessor.getUpdateQualifiedNames();
	}
	
	public void setUpdateQualifiedNames(boolean update) {
		if (fRenameTypeProcessor == null)
			return;
		fRenameTypeProcessor.setUpdateQualifiedNames(update);
	}
	
	public String getFilePatterns() {
		if (fRenameTypeProcessor == null)
			return null;
		return fRenameTypeProcessor.getFilePatterns();
	}
	
	public void setFilePatterns(String patterns) {
		if (fRenameTypeProcessor == null)
			return;
		fRenameTypeProcessor.setFilePatterns(patterns);
	}
	
	// ---- ISimilarDeclarationUpdating ------------------------------

	public boolean canEnableSimilarDeclarationUpdating() {
		if (fRenameTypeProcessor == null)
			return false;
		else
			return fRenameTypeProcessor.canEnableSimilarDeclarationUpdating();
	}

	public void setUpdateSimilarDeclarations(boolean update) {
		if (fRenameTypeProcessor == null)
			return;
		fRenameTypeProcessor.setUpdateSimilarDeclarations(update);
	}

	public boolean getUpdateSimilarDeclarations() {
		if (fRenameTypeProcessor == null)
			return false;
		return fRenameTypeProcessor.getUpdateSimilarDeclarations();
	}

	public int getMatchStrategy() {
		if (fRenameTypeProcessor == null)
			return RenamingNameSuggestor.STRATEGY_EXACT; // method should not be called in this case anyway ...
		return fRenameTypeProcessor.getMatchStrategy();
	}

	public void setMatchStrategy(int selectedStrategy) {
		if (fRenameTypeProcessor == null)
			return;
		fRenameTypeProcessor.setMatchStrategy(selectedStrategy);
	}

	public IModelElement[] getSimilarElements() {
		if (fRenameTypeProcessor == null)
			return null;
		return fRenameTypeProcessor.getSimilarElements();
	}

	public IResource getRefactoredResource(IResource element) {
		if (fRenameTypeProcessor == null)
			return element;
		return fRenameTypeProcessor.getRefactoredResource(element);
	}
	
	public IModelElement getRefactoredModelElement(IModelElement element) {
		if (fRenameTypeProcessor == null)
			return element;
		return fRenameTypeProcessor.getRefactoredModelElement(element);
	}

	// --- preconditions ----------------------------------

	public RefactoringStatus checkInitialConditions(IProgressMonitor pm) throws CoreException {

		// we purposely do not check activation of the renameTypeRefactoring
		// here.
//		return new RefactoringStatus();
		
		if (fRenameTypeProcessor != null && ! fCu.isStructureKnown()){
			//means the cu has syntax error, so abort to compute its rename type changes.
			fRenameTypeProcessor= null;
			fWillRenameType= false;
			return new RefactoringStatus();
		}
		
		//for a test case what it's needed, see bug 24248 
		//(the type might be gone from the editor by now)
		if (fWillRenameType && fRenameTypeProcessor != null && ! fRenameTypeProcessor.getType().exists()){
			fRenameTypeProcessor= null;
			fWillRenameType= false;
			return new RefactoringStatus();
		}
		 
		// we purposely do not check activation of the renameTypeRefactoring here. 
		return new RefactoringStatus();
	}

	protected RefactoringStatus doCheckFinalConditions(IProgressMonitor pm, CheckConditionsContext context) throws CoreException {
//		try {
//			return Checks.checkSourceModuleNewName(fCu, getNewElementName());
//		} finally {
//			pm.done();
//		}
		
		try{
			if (fWillRenameType && (!fCu.isStructureKnown())){
				RefactoringStatus result1= new RefactoringStatus();
				
				RefactoringStatus result2= new RefactoringStatus();
				result2.merge(Checks.checkSourceModuleNewName(fCu, getNewElementName()));
				if (result2.hasFatalError())
					result1.addError(Messages.format(RefactoringCoreMessages.RenameSourceModuleRefactoring_not_parsed_1, fCu.getElementName())); 
				else 
					result1.addError(Messages.format(RefactoringCoreMessages.RenameSourceModuleRefactoring_not_parsed, fCu.getElementName())); 
				result1.merge(result2);		
				
				return result1;
			}	
		
			if (fWillRenameType) {
				return fRenameTypeProcessor.checkFinalConditions(pm, context);
			} else {
				return Checks.checkSourceModuleNewName(fCu, getNewElementName());
			}
		} finally{
			pm.done();
		}
	}

	protected void computeRenameTypeRefactoring() throws CoreException {
//		if (getSimpleCUName().indexOf(".") != -1) { //$NON-NLS-1$			
//			return;
//		}
		
		
		if (getSimpleCUName().indexOf(".") != -1) { //$NON-NLS-1$
			fRenameTypeProcessor= null;
			fWillRenameType= false;
			return;
		}
		IType type= getTypeWithTheSameName();
		if (type != null) {
			fRenameTypeProcessor= new RenameVjoTypeProcessor(type);
		} else {
			fRenameTypeProcessor= null;
		}
		fWillRenameType= fRenameTypeProcessor != null && fCu.isStructureKnown();
		
		
	}
	
	private IType getTypeWithTheSameName() {
		try {
			IType[] topLevelTypes= fCu.getTypes();
			String name= getSimpleCUName();
			for (int i = 0; i < topLevelTypes.length; i++) {
				if (name.equals(topLevelTypes[i].getElementName()))
					return topLevelTypes[i];
			}
			return null; 
		} catch (CoreException e) {
			return null;
		}
	}
	
	

	private String getSimpleCUName() {
		return removeFileNameExtension(fCu.getElementName());
	}

	/**
	 * Removes the extension (whatever comes after the last '.') from the given
	 * file name.
	 */
	private static String removeFileNameExtension(String fileName) {
		if (fileName.lastIndexOf(".") == -1) //$NON-NLS-1$
			return fileName;
		return fileName.substring(0, fileName.lastIndexOf(".")); //$NON-NLS-1$
	}

	public Change createChange(IProgressMonitor pm) throws CoreException {
		// renaming the file is taken care of in renameTypeRefactoring
		
		if (fWillRenameType)
			return fRenameTypeProcessor.createChange(pm);
		fRenameTypeProcessor= null;
		
		
		final String newName = getNewElementName();
		final IResource resource = ResourceUtil.getResource(fCu);
		if (resource != null && resource.isLinked()) {
			final Map arguments = new HashMap();
			final IProject project = resource.getProject();
			final String name = project.getName();
			final String description = Messages.format(RefactoringCoreMessages.RenameSourceModuleChange_descriptor_description_short, resource.getName());
			final String header = Messages.format(RefactoringCoreMessages.RenameSourceModuleChange_descriptor_description, new String[] {
					resource.getFullPath().toString(), newName });
			final String comment = new ScriptRefactoringDescriptorComment(this, header).asString();
			final ScriptRefactoringDescriptor descriptor = new ScriptRefactoringDescriptor(RenameResourceProcessor.ID_RENAME_RESOURCE, name, description,
					comment, arguments, (RefactoringDescriptor.STRUCTURAL_CHANGE | RefactoringDescriptor.MULTI_CHANGE | RefactoringDescriptor.BREAKING_CHANGE));
			arguments.put(ScriptRefactoringDescriptor.ATTRIBUTE_INPUT, ScriptRefactoringDescriptor.resourceToHandle(name, resource));
			arguments.put(ScriptRefactoringDescriptor.ATTRIBUTE_NAME, newName);
			return new DynamicValidationStateChange(new RenameResourceChange(descriptor, resource, newName, comment));
		}
		String label = null;
		if (fCu != null) {
			final IScriptFolder fragment = (IScriptFolder) fCu.getParent();
			if (!fragment.isRootFolder())
				label = fragment.getElementName() + "." + fCu.getElementName(); //$NON-NLS-1$
			else
				label = fCu.getElementName();
		}
		final Map arguments = new HashMap();
		final String name = fCu.getScriptProject().getElementName();
		final String description = Messages.format(RefactoringCoreMessages.RenameSourceModuleChange_descriptor_description_short, fCu.getElementName());
		final String header = Messages.format(RefactoringCoreMessages.RenameSourceModuleChange_descriptor_description, new String[] { label, newName });
		final String comment = new ScriptRefactoringDescriptorComment(this, header).asString();
		final ScriptRefactoringDescriptor descriptor = new ScriptRefactoringDescriptor(ID_RENAME_COMPILATION_UNIT, name,
				description, comment, arguments, ScriptRefactoringDescriptor.ARCHIVE_IMPORTABLE | ScriptRefactoringDescriptor.ARCHIVE_REFACTORABLE
						| RefactoringDescriptor.STRUCTURAL_CHANGE | RefactoringDescriptor.MULTI_CHANGE);
		arguments.put(ScriptRefactoringDescriptor.ATTRIBUTE_INPUT, descriptor.elementToHandle(fCu));
		arguments.put(ScriptRefactoringDescriptor.ATTRIBUTE_NAME, newName);
		return new DynamicValidationStateChange(new RenameSourceModuleChange(descriptor, fCu, newName, comment));
	}

	/**
	 * {@inheritDoc}
	 */
	public Change postCreateChange(Change[] participantChanges, IProgressMonitor pm) throws CoreException {
		if (fWillRenameType)
			return fRenameTypeProcessor.postCreateChange(participantChanges, pm);
		return super.postCreateChange(participantChanges, pm);
	}

	public RefactoringStatus initialize(RefactoringArguments arguments) {
//		if (arguments instanceof ScriptRefactoringArguments) {
//			final ScriptRefactoringArguments generic = (ScriptRefactoringArguments) arguments;
//			final String path = generic.getAttribute(ATTRIBUTE_PATH);
//			if (path != null) {
//				final IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(path));
//				if (resource == null || !resource.exists())
//					return ScriptableRefactoring.createInputFatalStatus(resource, getRefactoring().getName(), ID_RENAME_COMPILATION_UNIT);
//				else {
//					fCu = (ISourceModule) DLTKCore.create(resource);
//					try {
//						computeRenameTypeRefactoring();
//					} catch (CoreException exception) {
//						DLTKUIPlugin.log(exception);
//					}
//				}
//			} else
//				return RefactoringStatus.createFatalErrorStatus(Messages.format(RefactoringCoreMessages.InitializableRefactoring_argument_not_exist,
//						ATTRIBUTE_PATH));
//			final String name = generic.getAttribute(ATTRIBUTE_NAME);
//			if (name != null && !"".equals(name)) //$NON-NLS-1$
//				setNewElementName(name);
//			else
//				setNewElementName(fCu.getElementName());
////				return RefactoringStatus.createFatalErrorStatus(Messages.format(RefactoringCoreMessages.InitializableRefactoring_argument_not_exist,
////						ATTRIBUTE_NAME));
//		} else
//			return RefactoringStatus.createFatalErrorStatus(RefactoringCoreMessages.InitializableRefactoring_inacceptable_arguments);
//		return new RefactoringStatus();
		
		if (!(arguments instanceof ScriptRefactoringArguments)) {
			return RefactoringStatus.createFatalErrorStatus(RefactoringCoreMessages.InitializableRefactoring_inacceptable_arguments);
		}
		
		final ScriptRefactoringArguments extended= (ScriptRefactoringArguments) arguments;
		final String handle= extended.getAttribute(ScriptRefactoringDescriptor.ATTRIBUTE_INPUT);
		if (handle == null) {
			return RefactoringStatus.createFatalErrorStatus(Messages.format(RefactoringCoreMessages.InitializableRefactoring_argument_not_exist, ScriptRefactoringDescriptor.ATTRIBUTE_INPUT));
		}
			
		final IModelElement element= ScriptRefactoringDescriptor.handleToElement(extended.getProject(), handle, false);
		if (element == null || !element.exists() || element.getElementType() != IModelElement.SOURCE_MODULE)
			return ScriptableRefactoring.createInputFatalStatus(element, getRefactoring().getName(), IVjoRefactorings.RENAME_SOURCE_MODULE);
		
		fCu= (ISourceModule) element;
		String name= extended.getAttribute(ScriptRefactoringDescriptor.ATTRIBUTE_NAME);
		if (name == null || name.length() == 0){
			name = fCu.getElementName();
//			return RefactoringStatus.createFatalErrorStatus(Messages.format(RefactoringCoreMessages.InitializableRefactoring_argument_not_exist, JDTRefactoringDescriptor.ATTRIBUTE_NAME));
		}

		try {
			computeRenameTypeRefactoring();
			setNewElementName(name);
		} catch (CoreException exception) {
			DLTKUIPlugin.log(exception);
			return ScriptableRefactoring.createInputFatalStatus(element, getRefactoring().getName(), IVjoRefactorings.RENAME_SOURCE_MODULE);
		}
		return new RefactoringStatus();	
		
	}
	
	/**
	 * @return the RenameTypeProcessor or <code>null</code> if no type will be renamed
	 */
	public RenameVjoTypeProcessor getRenameTypeProcessor() {
		return fRenameTypeProcessor;
	}

	public boolean isWillRenameType() {
		// TODO Auto-generated method stub
		return fWillRenameType;
	}

}

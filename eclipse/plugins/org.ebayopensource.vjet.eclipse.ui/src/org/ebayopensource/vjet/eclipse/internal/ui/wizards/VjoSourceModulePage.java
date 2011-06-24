/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.wizards;

import static org.ebayopensource.vjet.eclipse.internal.ui.wizards.VjetWizardMessages.ClassCreationWizard_modifiers;
import static org.ebayopensource.vjet.eclipse.internal.ui.wizards.VjetWizardMessages.ClassCreationWizard_satisfier_selection;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.IVjoSemanticRule;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.VjoSemanticRuleRepo;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.ClassBetterStartWithCapitalLetterRuleCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.InvalidIdentifierNameRuleCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.InvalidIdentifierNameWithKeywordRuleCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.TypeNameShouldNotBeEmptyRuleCtx;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.internal.ui.dialogs.VjoOpenTypeSelectionDialog;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.DLTKLanguageManager;
import org.eclipse.dltk.mod.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IProjectFragment;
import org.eclipse.dltk.mod.core.IScriptFolder;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.ScriptModelUtil;
import org.eclipse.dltk.mod.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.mod.core.search.IDLTKSearchScope;
import org.eclipse.dltk.mod.internal.core.ExternalScriptFolder;
import org.eclipse.dltk.mod.internal.ui.DLTKUIMessages;
import org.eclipse.dltk.mod.internal.ui.dialogs.StatusInfo;
import org.eclipse.dltk.mod.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.mod.internal.ui.wizards.dialogfields.DialogField;
import org.eclipse.dltk.mod.internal.ui.wizards.dialogfields.IDialogFieldListener;
import org.eclipse.dltk.mod.internal.ui.wizards.dialogfields.IListAdapter;
import org.eclipse.dltk.mod.internal.ui.wizards.dialogfields.IStringButtonAdapter;
import org.eclipse.dltk.mod.internal.ui.wizards.dialogfields.LayoutUtil;
import org.eclipse.dltk.mod.internal.ui.wizards.dialogfields.ListDialogField;
import org.eclipse.dltk.mod.internal.ui.wizards.dialogfields.SelectionButtonDialogField;
import org.eclipse.dltk.mod.internal.ui.wizards.dialogfields.SelectionButtonDialogFieldGroup;
import org.eclipse.dltk.mod.internal.ui.wizards.dialogfields.StringButtonDialogField;
import org.eclipse.dltk.mod.internal.ui.wizards.dialogfields.StringDialogField;
import org.eclipse.dltk.mod.ui.DLTKUILanguageManager;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.dltk.mod.ui.IDLTKUILanguageToolkit;
import org.eclipse.dltk.mod.ui.ModelElementLabelProvider;
import org.eclipse.dltk.mod.ui.wizards.Messages;
import org.eclipse.dltk.mod.ui.wizards.NewContainerWizardPage;
import org.eclipse.jdt.internal.corext.refactoring.StubTypeContext;
import org.eclipse.jdt.internal.ui.JavaPluginImages;
import org.eclipse.jdt.internal.ui.dialogs.TableTextCellEditor;
import org.eclipse.jdt.internal.ui.dialogs.TextFieldNavigationHandler;
import org.eclipse.jdt.internal.ui.refactoring.contentassist.CompletionContextRequestor;
import org.eclipse.jdt.internal.ui.refactoring.contentassist.ControlContentAssistHelper;
import org.eclipse.jdt.internal.ui.refactoring.contentassist.JavaTypeCompletionProcessor;
import org.eclipse.jface.contentassist.SubjectControlContentAssistant;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contentassist.ContentAssistHandler;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.dialogs.PreferencesUtil;

/**
 * This class is base class for all source module wizard pages.
 * 
 * 
 * 
 */
public abstract class VjoSourceModulePage extends NewContainerWizardPage {
	private static final String PACKAGE = "NewPackageWizardPage.package"; //$NON-NLS-1$
	protected static final String FILE = "NewSourceModulePage.file"; //$NON-NLS-1$

	// add by kevin, for comment support
	private SelectionButtonDialogField fAddCommentButton;
	private boolean fUseAddCommentButtonValue;

	protected IStatus sourceMoudleStatus;

	protected IScriptFolder currentScriptFolder;

	protected StringDialogField fileDialogField;

	final char DOT = '.';

	public boolean addSuperInterface(IJstType superInterface) {
		return fSuperInterfacesDialogField.addElement(new InterfaceWrapper(
				superInterface));
	}

	protected void createSuperInterfacesControls(Composite composite,
			int nColumns) {

		final String INTERFACE = "interface"; //$NON-NLS-1$
		fSuperInterfacesDialogField.doFillIntoGrid(composite, nColumns);
		final TableViewer tableViewer = fSuperInterfacesDialogField
				.getTableViewer();
		tableViewer.setColumnProperties(new String[] { INTERFACE });

		TableTextCellEditor cellEditor = new TableTextCellEditor(tableViewer, 0) {
			protected void doSetFocus() {
				if (text != null) {
					text.setFocus();
					text.setSelection(text.getText().length());
					checkSelection();
					checkDeleteable();
					checkSelectable();
				}
			}
		};
		JavaTypeCompletionProcessor superInterfaceCompletionProcessor = new JavaTypeCompletionProcessor(
				false, false, true);
		superInterfaceCompletionProcessor
				.setCompletionContextRequestor(new CompletionContextRequestor() {
					public StubTypeContext getStubTypeContext() {
						return /* getSuperInterfacesStubTypeContext() */null;
					}
				});
		SubjectControlContentAssistant contentAssistant = ControlContentAssistHelper
				.createJavaContentAssistant(superInterfaceCompletionProcessor);
		Text cellEditorText = cellEditor.getText();
		ContentAssistHandler.createHandlerForText(cellEditorText,
				contentAssistant);
		TextFieldNavigationHandler.install(cellEditorText);
		cellEditor.setContentAssistant(contentAssistant);

		tableViewer.setCellEditors(new CellEditor[] { cellEditor });
		// tableViewer.setCellModifier(new ICellModifier() {
		// public void modify(Object element, String property, Object value) {
		// if (element instanceof Item)
		// element = ((Item) element).getData();
		//
		// ((InterfaceWrapper) element).itype = (String) value;
		// fSuperInterfacesDialogField.elementChanged(element);
		// }
		//
		// public Object getValue(Object element, String property) {
		// return ((InterfaceWrapper) element).itype;
		// }
		//
		// public boolean canModify(Object element, String property) {
		// return true;
		// }
		// });
		tableViewer.getTable().addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.keyCode == SWT.F2 && event.stateMask == 0) {
					ISelection selection = tableViewer.getSelection();
					if (!(selection instanceof IStructuredSelection))
						return;
					IStructuredSelection structuredSelection = (IStructuredSelection) selection;
					tableViewer.editElement(structuredSelection
							.getFirstElement(), 0);
				}
			}
		});
		GridData gd = (GridData) fSuperInterfacesDialogField.getListControl(
				null).getLayoutData();
		gd.grabExcessVerticalSpace = false;
		gd.widthHint = getMaxFieldWidth();

	}

	// ////////////super interface////////////////////
	private ListDialogField fSuperInterfacesDialogField;

	public ListDialogField getSuperInterfacesDialogField() {
		return fSuperInterfacesDialogField;
	}

	// ////////////super interface////////////////////

	// /////////////////////////
	// The status of the last validation
	private IStatus packageStatus;

	private StringButtonDialogField packageDialogField;
	// private VjoPackageCompletionProcessor fCurrPackageCompletionProcessor;
	// script folder corresponding to the input type (can be null)
	// private IScriptFolder currRoot;

	private IScriptFolder currPackageRoot;

	private class PackageFieldAdapter implements IStringButtonAdapter,
			IDialogFieldListener {
		public void changeControlPressed(DialogField field) {
			packageChangeControlPressed(field);
		}

		public void dialogFieldChanged(DialogField field) {
			packageDialogFieldChanged(field);
		}
	}

	private void packageChangeControlPressed(DialogField field) {
		// TODO FIXME
		IScriptFolder root = choosePackage();
		if (root != null) {
			setScriptFolderRoot(root, true);
		}
	}

	public void setScriptFolderRoot(IScriptFolder root, boolean canBeModified) {
		currPackageRoot = root;
		String str = (root == null) ? "" : root.getPath().removeFirstSegments(getProjectFragment().getPath().segmentCount()).makeRelative().toString(); //$NON-NLS-1$
		str = str.replace("/", ".");
		packageDialogField.setText(str);
		packageDialogField.setEnabled(canBeModified);
	}

	public IScriptFolder getCurrPackageRoot() {
		return currPackageRoot;
	}

	protected IScriptFolder choosePackage() {

		IProjectFragment froot = getProjectFragment();
		IModelElement[] packages = null;
		try {
			if (froot != null && froot.exists()) {
				packages = froot.getChildren();
			}
		} catch (ModelException e) {

		}
		if (packages == null) {
			packages = new IModelElement[0];
		}
		ILabelProvider labelProvider = new ModelElementLabelProvider(
				ModelElementLabelProvider.SHOW_DEFAULT);
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				getShell(), labelProvider);
		dialog.setIgnoreCase(false);
		dialog
				.setTitle(NewWizardMessages.NewTypeWizardPage_ChoosePackageDialog_title);
		dialog
				.setMessage(NewWizardMessages.NewTypeWizardPage_ChoosePackageDialog_description);
		// dialog.setEmptyListMessage("todo--------------------");
		dialog.setElements(packages);
		dialog.setHelpAvailable(false);

		IScriptFolder pack = currPackageRoot;
		if (pack != null) {
			dialog.setInitialSelections(new Object[] { pack });
		}

		if (dialog.open() == Window.OK) {
			return (IScriptFolder) dialog.getFirstResult();
		}
		return null;

	}

	private void packageDialogFieldChanged(DialogField field) {
		if (field == packageDialogField) {
			packageChanged();
			handleFieldChanged(PACKAGE);
		}
		// tell all others
		// updateStatus(packageStatus);
	}

	private IStatus validatePackageName(String name) {

		// if (isEmptyName(name) != null) {
		// return new StatusInfo(IStatus.ERROR,
		// VjetWizardMessages.convention_package_nullName);
		// }
		// int length;
		// if ((length = name.length()) == 0) {
		// return new StatusInfo(IStatus.ERROR,
		// VjetWizardMessages.convention_package_emptyName);
		// }
		if (VjoNameValidator.startOrEndWithDot(name) != null) {
			return VjoNameValidator.startOrEndWithDot(name);
		}

		if (name != null && name.length() > 0) {
			// if (Character.isDigit(name.charAt(0))
			// || VjoNameValidator.isContainInvalidChar(name)
			// || VjoNameValidator.isContainBlank(name)
			// || VjoNameValidator.isInKeywords(name)) {
			if (containInvalidCharInPackageName(name)) {
				Object[] objects = new Object[] { "\'" + name + "\'" };
				String m = VjetWizardMessages.ClassCreationWizard_invalid_package_name;
				return new StatusInfo(IStatus.ERROR, MessageFormat.format(m,
						objects));
			}
		}

		// if (CharOperation.isWhitespace(name.charAt(0))
		// || CharOperation.isWhitespace(name.charAt(name.length() - 1))) {
		// return new StatusInfo(IStatus.ERROR,
		// VjetWizardMessages.convention_package_nameWithBlanks);
		// }
		if (VjoNameValidator.consecutiveDotsName(name) != null) {
			return VjoNameValidator.consecutiveDotsName(name);
		}

		return new StatusInfo();

		// return JavaConventions.validatePackageName(name,
		// JavaCore.VERSION_1_3,
		// JavaCore.VERSION_1_3);
	}

	/**
	 * @param name
	 * @return
	 */
	private static String containInvalidCharOrKeyword(String name) {

		String message = null;
		// Use the validation common util to validate the name whether
		// the name contain the invalid char. Begin.
		IVjoSemanticRule<InvalidIdentifierNameRuleCtx> invalidIdentifierNameRule = VjoSemanticRuleRepo
				.getInstance().INVALID_IDENTIFIER;
		VjoSemanticProblem vjoSemanticProblem = invalidIdentifierNameRule
				.fire(new InvalidIdentifierNameRuleCtx(name));

		if (vjoSemanticProblem != null && vjoSemanticProblem.getID() != null) {
			message = vjoSemanticProblem.getID().getName();
		}
		// Use the validation common util to validate the name whether
		// the name contain the invalid char. End.

		// Use the validation common util to validate whether the name
		// contain the keyword. Begin.
		IVjoSemanticRule<InvalidIdentifierNameWithKeywordRuleCtx> invalidIdentifierNameWithKeywordRule = VjoSemanticRuleRepo
				.getInstance().INVALID_IDENTIFIER_WITH_KEYWORD;
		vjoSemanticProblem = invalidIdentifierNameWithKeywordRule
				.fire(new InvalidIdentifierNameWithKeywordRuleCtx(name, false));
		if (vjoSemanticProblem != null && vjoSemanticProblem.getID() != null) {
			message = vjoSemanticProblem.getID().getName();
		}
		// Use the validation common util to validate whether the name
		// contain the keyword. End.

		return message;
	}

	/**
	 * @param name
	 * @return
	 */
	public static String isEmptyName(String name) {

		String message = null;
		// Use the validation common util to validate the name whether
		// the name is empty. Begin.
		IVjoSemanticRule<TypeNameShouldNotBeEmptyRuleCtx> isNameEmptyRule = VjoSemanticRuleRepo
				.getInstance().TYPE_NAME_SHOULD_NOT_BE_EMPTY;
		VjoSemanticProblem vjoSemanticProblem = isNameEmptyRule
				.fire(new TypeNameShouldNotBeEmptyRuleCtx(name));

		if (vjoSemanticProblem != null && vjoSemanticProblem.getID() != null) {
			message = vjoSemanticProblem.getID().getName();
		}
		// Use the validation common util to validate the name whether
		// the name is empty. End.

		return message;
	}

	/**
	 * @param name
	 * @return
	 */
	private static String isLowerCaseFirstChar(String name) {

		String message = null;
		// Use the validation common util to validate the name whether
		// the name is lower case. Begin.
		IVjoSemanticRule<ClassBetterStartWithCapitalLetterRuleCtx> isLowerCaseFirstCharRule = VjoSemanticRuleRepo
				.getInstance().CLASS_BETTER_START_WITH_NONE_CAPITAL_LETTER;
		VjoSemanticProblem vjoSemanticProblem = isLowerCaseFirstCharRule
				.fire(new ClassBetterStartWithCapitalLetterRuleCtx(name));

		if (vjoSemanticProblem != null && vjoSemanticProblem.getID() != null) {
			message = vjoSemanticProblem.getID().getName();
		}
		// Use the validation common util to validate the name whether
		// the name is lower case. End.

		return message;
	}

	/**
	 * @param name
	 * @return
	 */
	public static boolean containInvalidCharInPackageName(String name) {
		String[] nameFields = name.split("\\.");
		for (String nameField : nameFields) {
			String message = containInvalidCharOrKeyword(nameField);
			if (message != null) {
				return true;
			}
		}
		return false;
	}

	protected IStatus packageChanged() {

		fCurrPackageCompletionProcessor
				.setPackageFragmentRoot(getProjectFragment());

		StatusInfo status = new StatusInfo();
		String packName = packageDialogField.getText();

		if (packName.length() > 0) {
			IStatus val = validatePackageName(packName);
			if (val.getSeverity() == IStatus.ERROR) {
				status
						.setError(MessageFormat
								.format(
										VjetWizardMessages.NewPackageWizardPage_error_InvalidPackageName,
										val.getMessage()));
				return status;
			} else if (val.getSeverity() == IStatus.WARNING) {
				status
						.setWarning(MessageFormat
								.format(
										VjetWizardMessages.NewPackageWizardPage_warning_DiscouragedPackageName,
										val.getMessage()));
			}
		} else {
			status
					.setWarning(MessageFormat
							.format(
									VjetWizardMessages.NewPackageWizardPage_warning_DiscouragedPackageName,
									""));
			return status;
		}

		// Add by Oliver. 2009-10-31. Sometimes the project fragment is not
		// initialized or input wrong project name, it is null.
		if (getProjectFragment() != null) {
			currPackageRoot = getProjectFragment().getScriptFolder(packName);// .getPackageFragment(packName);
			StatusInfo statusFile = fileChanged();
			if (statusFile.getSeverity() == IStatus.ERROR) {
				return statusFile;
			}
		}
		return status;
	}

	private String[] createPackagesProposals(String input) {
		ArrayList proposals = new ArrayList();
		String prefix = input;
		try {
			IModelElement[] packageFragments = getProjectFragment()
					.getChildren();
			for (int i = 0; i < packageFragments.length; i++) {
				IScriptFolder pack = (IScriptFolder) packageFragments[i];
				String packName = pack.getElementName();
				if (packName.length() == 0 || !packName.startsWith(prefix))
					continue;
				String proposal = packName;
				proposals.add(proposal);
			}
		} catch (ModelException e) {
			// fPackageFragmentRoot is not a proper root -> no proposals
		}
		return (String[]) proposals.toArray(new String[proposals.size()]);
	}

	// IContentProposalProvider packageProposalProvider = new
	// IContentProposalProvider() {
	// public IContentProposal[] getProposals(String contents, int position) {
	// if (!packageAdapter.getControl().isFocusControl())
	// return new IContentProposal[0];
	//
	// String[] results = createPackagesProposals(contents);
	// IContentProposal[] proposals = new IContentProposal[results.length];
	// for (int i = 0; i < results.length; i++) {
	// final String proposal = results[i];
	// proposals[i] = new IContentProposal() {
	// public String getContent() {
	// return proposal;
	// }
	//
	// public String getLabel() {
	// return proposal;
	// }
	//
	// public String getDescription() {
	// return null;
	// }
	//
	// public int getCursorPosition() {
	// return proposal.length();
	// }
	// };
	// }
	//
	// return proposals;
	// }
	// };
	ContentProposalAdapter packageAdapter;
	private VjoPackageCompletionProcessor fCurrPackageCompletionProcessor;

	protected void createPackageControls(Composite parent, int nColumns) {
		packageDialogField.doFillIntoGrid(parent, nColumns);
		Text text = packageDialogField.getTextControl(null);
		LayoutUtil.setWidthHint(text, getMaxFieldWidth());
		LayoutUtil.setHorizontalGrabbing(text);
		ControlContentAssistHelper.createTextContentAssistant(text,
				fCurrPackageCompletionProcessor);
		// packageAdapter= new ContentProposalAdapter(
		// text,
		// new TextContentAdapter(),
		// packageProposalProvider,
		// null,
		// null);
		// packageAdapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
		// packageAdapter.setLabelProvider(new JavaUILabelProvider());
	}

	// /////////////////////////

	private final int PUBLIC_INDEX = 0, DEFAULT_INDEX = 1, PRIVATE_INDEX = 2,
			PROTECTED_INDEX = 3;
	private final int ABSTRACT_INDEX = 0, FINAL_INDEX = 1, STATIC_INDEX = 2;

	private SelectionButtonDialogFieldGroup fAccMdfButtons;

	private SelectionButtonDialogFieldGroup fOtherMdfButtons;

	private StatusInfo fileChanged() {
		StatusInfo status = new StatusInfo();

		String emptyTypeName = isEmptyName(getFileText());
		if (emptyTypeName != null) {
			status
					.setError(VjetWizardMessages.VjoSourceModulePage_pathCannotBeEmpty);
		} else {
			String name = getFileText();
			if (name != null && name.length() > 0) {
				//modify by patrick - reorder the name check
				//TODO: change the check algorithm - check all constriants, then order by serverity(1.Error, 2.Warning), show the first. 
				String invalidCharOrKeywordMessage = containInvalidCharOrKeyword(name);
				// if (Character.isDigit(name.charAt(0))
				// || VjoNameValidator.isContainInvalidChar(name)
				// || VjoNameValidator.isContainBlank(name)
				// || VjoNameValidator.isInKeywords(name)) {
				if (invalidCharOrKeywordMessage != null) {
					Object[] objects = new Object[] { name };
					String m = VjetWizardMessages.ClassCreationWizard_invalid_file_name;
					return new StatusInfo(IStatus.ERROR, MessageFormat.format(
							m, objects));
				}
				
				if (currPackageRoot != null) {
					IFolder folder = (IFolder) currPackageRoot.getResource();
					
					String errorMessage = getErrroMessageOfTypeIsInWorkSpace(
							currPackageRoot, getFileName());
					if (errorMessage != null) {
						status.setError(errorMessage);
						return status;
					}
					/**
					 * The old logic that judge the file exist under case sensitive
					 * or not.
					 */
					// IFile file = folder.getFile(getFileName());
					// File localFile = new File(folder.getFile(getFileName())
					// .getLocationURI());
					// if (localFile.exists())
					// status
					// .setError(Messages.NewSourceModulePage_typeAlreadyExists);
				}

				IStatus startWithLowerCaseWarning = getLowerCaseFirstCharWarning(name);
				if (startWithLowerCaseWarning != null) {
					return (StatusInfo) startWithLowerCaseWarning;
				}
				//end modify
			}

		}

		return status;
	}

	/**
	 * If the type name begins with lower case, will return warning information.
	 * 
	 * @param name
	 * @return
	 */
	public static IStatus getLowerCaseFirstCharWarning(String name) {
		String startWithLowerCaseMessage = isLowerCaseFirstChar(name);
		if (startWithLowerCaseMessage != null) {
			String m = VjetWizardMessages.Convention_type_uppercaseName;
			return new StatusInfo(IStatus.WARNING, m);
		}
		return null;
	}

	/**
	 * Judge the type name of case sensitive or not.
	 * 
	 * @param currPackageRoot
	 * @param name
	 * @return
	 */
	private String getErrroMessageOfTypeIsInWorkSpace(
			IScriptFolder currPackageRoot, String name) {
		try {
			IModelElement[] allModelElements = currPackageRoot.getChildren();
			for (IModelElement modelElement : allModelElements) {
				String currentElementName = modelElement.getElementName();

				if ((name).equals(currentElementName)) {
					// If the file exist with the matched name.
					return Messages.NewSourceModulePage_fileAlreadyExists;
				} else if ((name).equalsIgnoreCase(currentElementName)) {
					// If the file exist with the case sensitive name.
					return Messages.NewSourceModulePage_typeAlreadyExists;
				}

			}
		} catch (ModelException e) {
		}
		return null;
	}

	/**
	 * The wizard owning this page is responsible for calling this method with
	 * the current selection. The selection is used to initialize the fields of
	 * the wizard page.
	 * 
	 * @param selection
	 *            used to initialize the fields
	 */
	public void init(IStructuredSelection selection) {
		IModelElement element = getInitialScriptElement(selection);

		initContainerPage(element);
		// TODO
		initPackagePage(element);
		// setScriptFolderRoot(root, canBeModified)
		updateStatus(new IStatus[] { containerStatus, fileChanged() });
	}

	protected void initPackagePage(IModelElement elem) {
		IScriptFolder initRoot = null;
		if (elem != null) {
			initRoot = (IScriptFolder) elem
					.getAncestor(IModelElement.SCRIPT_FOLDER);

			if (initRoot instanceof ExternalScriptFolder)
				initRoot = null;
			// TODO: I think this piece of code is a mess, please fix it
			try {
				if (initRoot == null) {
					IProjectFragment fragment = ScriptModelUtil
							.getProjectFragment(elem);
					if (fragment != null
							&& fragment.getKind() == IProjectFragment.K_SOURCE
							&& !fragment.isExternal())
						initRoot = fragment.getScriptFolder(""); //$NON-NLS-1$

					if (initRoot == null) {
						IScriptProject project = elem.getScriptProject();
						if (project != null) {
							initRoot = null;
							if (project.exists()) {
								IProjectFragment[] roots = project
										.getProjectFragments();
								for (int i = 0; i < roots.length; i++) {
									if (roots[i].getKind() == IProjectFragment.K_SOURCE) {
										initRoot = roots[i].getScriptFolder(""); //$NON-NLS-1$
										break;
									}
								}
							}
							if (initRoot == null) {
								initRoot = project.getProjectFragment(
										project.getResource()).getScriptFolder(
										""); //$NON-NLS-1$
							}
						}
					}
				}
			} catch (ModelException e) {
				DLTKUIPlugin.log(e);
			}
		}
		setScriptFolderRoot(initRoot, true);
		handleFieldChanged(CONTAINER);

	}

	protected void createFileControls(Composite parent, int nColumns) {
		fileDialogField.doFillIntoGrid(parent, nColumns - 1);
		Text text = fileDialogField.getTextControl(null);
		LayoutUtil.setWidthHint(text, getMaxFieldWidth());
		LayoutUtil.setHorizontalGrabbing(text);
		DialogField.createEmptySpace(parent);
		// Add modifier section. Oliver 2009-04-01
		createModifierControls(parent, nColumns);

	}

	/**
	 * Add the modifier to wizard page.
	 * 
	 * @param composite
	 * @param nColumns
	 */
	protected void createModifierControls(Composite composite, int nColumns) {
		LayoutUtil.setHorizontalSpan(fAccMdfButtons.getLabelControl(composite),
				1);

		Control control = fAccMdfButtons.getSelectionButtonsGroup(composite);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.horizontalSpan = nColumns - 2;
		control.setLayoutData(gd);

		// Modify by Oliver.2009-05-04. Filter the other modifier area.
		if (!("etype".equals(getPageType()) || "itype".equals(getPageType()) || "mtype"
				.equals(getPageType()))) {

			DialogField.createEmptySpace(composite);
			DialogField.createEmptySpace(composite);

			control = fOtherMdfButtons.getSelectionButtonsGroup(composite);
			gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
			gd.horizontalSpan = nColumns - 2;
			control.setLayoutData(gd);
		}

		DialogField.createEmptySpace(composite);
	}

	/**
	 * @return
	 */
	public String getModifiers() {
		StringBuffer mdf = new StringBuffer();
		if (fAccMdfButtons.isSelected(PUBLIC_INDEX)) {
			mdf.append("public");
		} else if (fAccMdfButtons.isSelected(DEFAULT_INDEX)) {
			mdf.append("default");
		} else if (fAccMdfButtons.isSelected(PRIVATE_INDEX)) {
			mdf.append("private");
		} else if (fAccMdfButtons.isSelected(PROTECTED_INDEX)) {
			mdf.append("protected");
		}

		if (fOtherMdfButtons.isSelected(ABSTRACT_INDEX)) {
			mdf.append(",abstract");
		}
		if (fOtherMdfButtons.isSelected(FINAL_INDEX)) {
			mdf.append(",final");
		}
		if (fOtherMdfButtons.isSelected(STATIC_INDEX)) {
			mdf.append(",static");
		}

		return mdf.toString();
	}

	public static class InterfaceWrapper {
		public IJstType itype;

		public InterfaceWrapper(IJstType interfaceName) {
			this.itype = interfaceName;
		}

		public IJstType getSourceType() {
			return itype;
		}

		public int hashCode() {
			return itype.hashCode();
		}

		public boolean equals(Object obj) {
			return obj != null
					&& getClass().equals(obj.getClass())
					&& ((InterfaceWrapper) obj).itype.getName().equals(
							itype.getName());
		}

		@Override
		public String toString() {
			return itype.getName();
			// IFile file = (IFile) itype.getResource();
			// return CodeassistUtils.getClassName(file);
			// return itype;
		}
	}

	private static class InterfacesListLabelProvider extends LabelProvider {
		private Image fInterfaceImage;

		public InterfacesListLabelProvider() {
			fInterfaceImage = JavaPluginImages
					.get(JavaPluginImages.IMG_OBJS_INTERFACE);
		}

		public String getText(Object element) {
			return ((InterfaceWrapper) element).toString();
		}

		public Image getImage(Object element) {
			return fInterfaceImage;
		}
	}

	protected IDLTKSearchScope getSearchScope() {
		return null;
	}

	protected IDLTKUILanguageToolkit getUILanguageToolkit() {
		return DLTKUILanguageManager.getLanguageToolkit(VjoNature.NATURE_ID);
	}

	protected String getSatisfierDialogTitle() {
		return ClassCreationWizard_satisfier_selection;
	}

	protected String getOpenTypeDialogMessage() {
		return DLTKUIMessages.OpenTypeAction_dialogMessage;
	}

	/**
	 * Gets the parent class by active shell and returns the name of choosen
	 * class.
	 * 
	 * @return the name of super class.
	 */
	protected void chooseSuperInterfaces() {

		String interfaceName = null;

		Shell parent = DLTKUIPlugin.getActiveWorkbenchShell();
		VjoOpenTypeSelectionDialog dialog = new VjoOpenTypeSelectionDialog(
				parent, true, PlatformUI.getWorkbench().getProgressService(),
				getSearchScope(), IDLTKSearchConstants.TYPE, this
						.getUILanguageToolkit());

		dialog.setTitle(getSatisfierDialogTitle());
		dialog.setMessage(getOpenTypeDialogMessage());

		// Add by Oliver, 2009-04-17.
		// // Fix bug--http://quickbugstage.arch.ebay.com/show_bug.cgi?id=
		dialog.setShownTypeFlag(VjoOpenTypeSelectionDialog.SHOWINTERFACEONLY);

		int result = dialog.open();
		if (result != IDialogConstants.OK_ID)
			return;

		Object[] types = dialog.getResult();
		if (types != null && types.length > 0) {
			IType type = null;
			for (int i = 0; i < types.length; i++) {
				type = (IType) types[i];
				// IFile file = (IFile) type.getResource();
				// interfaceName = CodeassistUtils.getClassName(file);
				addSuperInterface(org.ebayopensource.vjet.eclipse.internal.core.util.Util
						.toJstType(type));
			}
		}

		// return interfaceName;
	}

	public VjoSourceModulePage() {
		super("wizardPage"); //$NON-NLS-1$
		setTitle(getPageTitle());
		setDescription(getPageDescription());

		// /////////////////////////////////

		String[] addButtons = new String[] {
				VjetWizardMessages.NewTypeWizardPage_interfaces_add,
				/* 1 */null,
				VjetWizardMessages.NewTypeWizardPage_interfaces_remove };
		fSuperInterfacesDialogField = new ListDialogField(new IListAdapter() {

			public void customButtonPressed(ListDialogField field, int index) {
				if (field == fSuperInterfacesDialogField) {
					chooseSuperInterfaces();
					List interfaces = fSuperInterfacesDialogField.getElements();
					if (!interfaces.isEmpty()) {
						Object element = interfaces.get(interfaces.size() - 1);
						fSuperInterfacesDialogField.editElement(element);
					}
				}
			}

			public void doubleClicked(ListDialogField field) {
				// TODO Auto-generated method stub

			}

			public void selectionChanged(ListDialogField field) {
				// TODO Auto-generated method stub

			}

		}, addButtons, new InterfacesListLabelProvider());
		fSuperInterfacesDialogField
				.setDialogFieldListener(new IDialogFieldListener() {

					public void dialogFieldChanged(DialogField field) {
						// TODO Auto-generated method stub

					}

				});
		fSuperInterfacesDialogField
				.setTableColumns(new ListDialogField.ColumnsDescription(1,
						false));

		if ("itype".equalsIgnoreCase(this.getPageType()))
			fSuperInterfacesDialogField
					.setLabelText(VjetWizardMessages.NewTypeWizardPage_interfaces_ifc_label);
		else
			fSuperInterfacesDialogField
					.setLabelText(VjetWizardMessages.NewTypeWizardPage_interfaces_class_label);
		fSuperInterfacesDialogField.setRemoveButtonIndex(2);

		// ////////////////////////////////
		PackageFieldAdapter adapter = new PackageFieldAdapter();
		packageDialogField = new StringButtonDialogField(adapter);
		packageDialogField.setDialogFieldListener(adapter);
		packageDialogField
				.setLabelText(VjetWizardMessages.NewTypeWizardPage_package_label);
		packageDialogField
				.setButtonLabel(VjetWizardMessages.NewTypeWizardPage_package_button);
		fCurrPackageCompletionProcessor = new VjoPackageCompletionProcessor();

		packageStatus = new StatusInfo();
		// ////////////////////////////////
		sourceMoudleStatus = new StatusInfo();

		// fileDialogField
		fileDialogField = new StringDialogField();
		fileDialogField
				.setLabelText(VjetWizardMessages.VjoSourceModulePage_file);
		fileDialogField.setDialogFieldListener(new IDialogFieldListener() {
			public void dialogFieldChanged(DialogField field) {
				sourceMoudleStatus = fileChanged();
				handleFieldChanged(FILE);
			}
		});

		// Add by Oliver. For initializing the modifier control.
		String[] buttonNames1 = new String[] {
				VjetWizardMessages.WizardPage_modifiers_public,
				VjetWizardMessages.WizardPage_modifiers_default,
				VjetWizardMessages.WizardPage_modifiers_private,
				VjetWizardMessages.WizardPage_modifiers_protected };
		fAccMdfButtons = new SelectionButtonDialogFieldGroup(SWT.RADIO,
				buttonNames1, 4);
		// fAccMdfButtons.setDialogFieldListener(adapter);
		fAccMdfButtons.setLabelText(ClassCreationWizard_modifiers);
		fAccMdfButtons.setSelection(0, true);

		String[] buttonNames2;
		// if (fTypeKind == CLASS_TYPE) {
		buttonNames2 = new String[] {
				VjetWizardMessages.WizardPage_modifiers_abstract,
				VjetWizardMessages.WizardPage_modifiers_final
		// VjetWizardMessages.WizardPage_modifiers_static
		};

		fOtherMdfButtons = new SelectionButtonDialogFieldGroup(SWT.CHECK,
				buttonNames2, 4);
		fOtherMdfButtons.setDialogFieldListener(new IDialogFieldListener() {
			public void dialogFieldChanged(DialogField field) {
				if (field == fOtherMdfButtons || field == fAccMdfButtons) {
					IStatus fModifierStatus = modifiersChanged();
					handleFieldChanged("Modifier");
					// updateStatus(new IStatus[] { containerStatus,
					// fileChanged(), fModifierStatus });
				}
			}
		});

		// Enable 'abstract' check box only for new type. Otherwise it is
		// disabled.
		if (this instanceof VjoClassCreationPage) {
			fOtherMdfButtons.enableSelectionButton(ABSTRACT_INDEX, true);
		} else {
			fOtherMdfButtons.enableSelectionButton(ABSTRACT_INDEX, false);
		}
		// Modify by Oliver.2009-05-04. Filter the modifier area to match
		// enabled and disabled of the actual wizard page.
		fAccMdfButtons.enableSelectionButton(PUBLIC_INDEX, true);
		fAccMdfButtons.enableSelectionButton(DEFAULT_INDEX, true);
		fAccMdfButtons.enableSelectionButton(PRIVATE_INDEX, true);
		fAccMdfButtons.enableSelectionButton(PROTECTED_INDEX, true);
		String pageType = getPageType();
		if (pageType.length() > 0) {
			if ("ctype".equals(pageType) || "etype".equals(pageType)
					|| "itype".equals(pageType)
					|| "mtype".equals(getPageType())) {
				fAccMdfButtons.enableSelectionButton(PROTECTED_INDEX, false);
				fAccMdfButtons.enableSelectionButton(PRIVATE_INDEX, false);
			}

		}
	}

	/**
	 * Use this flag to decide which page this instance is coming from.
	 * 
	 * @return
	 */
	protected String getPageType() {
		return "";
	}

	/**
	 * @return
	 */
	protected IStatus modifiersChanged() {
		StatusInfo status = new StatusInfo();
		String modifiers = getModifiers();
		if (modifiers.indexOf("abstract") > 0 && modifiers.indexOf("final") > 0) {
			status
					.setError(VjetWizardMessages.NewTypeWizardPage_error_ModifiersFinalAndAbstract);
		}
		return status;
	}

	protected void handleFieldChanged(String fieldName) {
		super.handleFieldChanged(fieldName);
		if (CONTAINER.equals(fieldName)) {
			IProjectFragment fragment = getProjectFragment();
			if (fragment != null)
				currentScriptFolder = fragment.getScriptFolder(""); //$NON-NLS-1$
			else
				currentScriptFolder = null;
			sourceMoudleStatus = fileChanged();
		}

		updateStatus(new IStatus[] { containerStatus, fileChanged(),
				modifiersChanged(), packageChanged() });
	}

	public ISourceModule createFile(IProgressMonitor monitor)
			throws CoreException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}

		String fileName = getFileName();
		if (packageDialogField.getText().trim().length() == 0) {
			currPackageRoot = getProjectFragment().getScriptFolder("");
		}

		if (!currPackageRoot.exists()) {
			IProjectFragment root = getProjectFragment();
			String packName = packageDialogField.getText().trim();
			packName = packName.replace('.', '/');
			currPackageRoot = root.createScriptFolder(packName, true, monitor);
		}

		// if (!pack.exists()) {
		// String packName= pack.getElementName();
		// pack= root.createPackageFragment(packName, true, new
		// SubProgressMonitor(monitor, 1));
		// }

		final ISourceModule module = currPackageRoot.createSourceModule(
				fileName, getFileContent(), true, monitor);

		return module;
	}

	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		final int nColumns = 3;

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setFont(parent.getFont());

		GridLayout layout = new GridLayout();
		layout.numColumns = nColumns;
		composite.setLayout(layout);

		createContainerControls(composite, nColumns);
		createPackageControls(composite, nColumns);
		createFileControls(composite, nColumns);

		// Add by Oliver.2009-06-22. OType does not need interface area.
		if (!"otype".equals(getPageType())) {
			createSuperInterfacesControls(composite, nColumns);
		}

		// Comment the comment control temporarily. After Kevin finishs this
		// function, enable it again.
		// createCommentControl(composite, nColumns);
		createExtraControls(composite, nColumns);
		setControl(composite);
		Dialog.applyDialogFont(composite);

		// Add by Oliver. For the wizard help(F1).
		if ("itype".equals(getPageType())) {
			PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
					IHelpContextIds.NEW_ITYPE);
		}
		if ("etype".equals(getPageType())) {
			PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
					IHelpContextIds.NEW_ETYPE);
		}
		if ("mtype".equals(getPageType())) {
			PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
					IHelpContextIds.NEW_MTYPE);
		}
		if ("otype".equals(getPageType())) {
			PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
					IHelpContextIds.NEW_OTYPE);
		}

	}

	/**
	 * create comment related controls
	 * 
	 * @param composite
	 * @param columns
	 */
	protected void createCommentControl(Composite composite, int columns) {
		Link link = new Link(composite, SWT.NONE);
		link
				.setText(VjetWizardMessages.NewTypeWizardPage_addcomment_description);
		link.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				IProject project = getProjectFragment().getResource()
						.getProject();
				if (project != null) {
					PreferenceDialog dialog = PreferencesUtil
							.createPropertyDialogOn(getShell(), null,
									"code template id", null, null);
					dialog.open();
				} else {
					String title = VjetWizardMessages.NewTypeWizardPage_configure_templates_title;
					String message = VjetWizardMessages.NewTypeWizardPage_configure_templates_message;
					MessageDialog.openInformation(getShell(), title, message);
				}
			}
		});
		link.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, false,
				false, columns, 1));

		fAddCommentButton = new SelectionButtonDialogField(SWT.CHECK);
		fAddCommentButton
				.setLabelText(VjetWizardMessages.NewTypeWizardPage_addcomment_label); //$NON-NLS-1$
		DialogField.createEmptySpace(composite);
		fAddCommentButton.doFillIntoGrid(composite, columns - 1);

		fUseAddCommentButtonValue = false; // only used when enabled
	}

	protected void createExtraControls(Composite composite, int columns) {

	}

	protected String getFileText() {
		return fileDialogField.getText();
	}

	protected String getFileName() {
		final String fileText = getFileText();

		String[] extensions = getFileExtensions();
		for (int i = 0; i < extensions.length; ++i) {
			String extension = extensions[i];
			if (extension.length() > 0 && fileText.endsWith("." + extension)) { //$NON-NLS-1$
				return fileText;
			}
		}

		return fileText + "." + extensions[0]; //$NON-NLS-1$
	}

	protected String[] getFileExtensions() {
		String requiredNature = getRequiredNature();

		IDLTKLanguageToolkit toolkit = DLTKLanguageManager
				.getLanguageToolkit(requiredNature);
		String contentType = toolkit.getLanguageContentType();
		IContentTypeManager manager = Platform.getContentTypeManager();
		IContentType type = manager.getContentType(contentType);
		if (type != null) {
			String[] extensions = type
					.getFileSpecs(IContentType.FILE_EXTENSION_SPEC);
			return extensions;
		}

		return new String[] { "" }; //$NON-NLS-1$
	}

	protected IScriptFolder chooseScriptFolder() {
		ILabelProvider labelProvider = new ModelElementLabelProvider(
				ModelElementLabelProvider.SHOW_DEFAULT);

		ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				getShell(), labelProvider);

		dialog.setIgnoreCase(false);
		dialog.setTitle(Messages.NewSourceModulePage_selectScriptFolder);
		dialog.setMessage(Messages.NewSourceModulePage_selectScriptFolder);
		dialog
				.setEmptyListMessage(Messages.NewSourceModulePage_noFoldersAvailable);

		IProjectFragment projectFragment = getProjectFragment();
		if (projectFragment != null) {
			try {
				dialog.setElements(projectFragment.getChildren());
			} catch (ModelException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
		}

		dialog.setHelpAvailable(false);

		if (currentScriptFolder != null) {
			dialog.setInitialSelections(new Object[] { currentScriptFolder });
		}

		if (dialog.open() == Window.OK) {
			Object element = dialog.getFirstResult();
			if (element instanceof IScriptFolder) {
				return (IScriptFolder) element;
			}
		}

		return null;
	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			setFocus();
		}
	}

	public void setFileText(String text) {
		fileDialogField.setText(text);
	}

	protected void setFocus() {
		fileDialogField.setFocus();
	}

	protected abstract String getPageTitle();

	protected abstract String getPageDescription();

	protected String getFileContent() {
		return ""; //$NON-NLS-1$
	}
}

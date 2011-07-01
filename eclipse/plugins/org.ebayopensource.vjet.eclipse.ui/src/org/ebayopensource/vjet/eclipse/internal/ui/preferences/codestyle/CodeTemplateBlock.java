/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.vjet.eclipse.internal.ui.preferences.codestyle;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.mod.internal.ui.editor.ScriptSourceViewer;
import org.eclipse.dltk.mod.internal.ui.preferences.ScriptSourcePreviewerUpdater;
import org.eclipse.dltk.mod.internal.ui.wizards.dialogfields.DialogField;
import org.eclipse.dltk.mod.internal.ui.wizards.dialogfields.IDialogFieldListener;
import org.eclipse.dltk.mod.internal.ui.wizards.dialogfields.ITreeListAdapter;
import org.eclipse.dltk.mod.internal.ui.wizards.dialogfields.LayoutUtil;
import org.eclipse.dltk.mod.internal.ui.wizards.dialogfields.TreeListDialogField;
import org.eclipse.dltk.mod.ui.DLTKUILanguageManager;
import org.eclipse.dltk.mod.ui.IDLTKUILanguageToolkit;
import org.eclipse.dltk.mod.ui.text.ScriptSourceViewerConfiguration;
import org.eclipse.dltk.mod.ui.text.ScriptTextTools;
import org.eclipse.dltk.mod.ui.util.PixelConverter;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.jface.text.templates.persistence.TemplatePersistenceData;
import org.eclipse.jface.text.templates.persistence.TemplateReaderWriter;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * code template block
 * 
 * 
 *
 */
public class CodeTemplateBlock {
	private class CodeTemplateAdapter extends ViewerSorter implements ITreeListAdapter, IDialogFieldListener {

		private final Object[] NO_CHILDREN= new Object[0];

		public void customButtonPressed(TreeListDialogField field, int index) {
			doButtonPressed(index, field.getSelectedElements());
		}
			
		public void selectionChanged(TreeListDialogField field) {
			List selected= field.getSelectedElements();
			field.enableButton(IDX_EDIT, canEdit(selected));
			field.enableButton(IDX_EXPORT, !selected.isEmpty());
			
			updateSourceViewerInput(selected);
		}

		public void doubleClicked(TreeListDialogField field) {
			List selected= field.getSelectedElements();
			if (canEdit(selected)) {
				doButtonPressed(IDX_EDIT, selected);
			}
		}

		public Object[] getChildren(TreeListDialogField field, Object element) {
			if (element == COMMENT_NODE) {
				return getTemplateOfCategory(element == COMMENT_NODE);
			}
			return NO_CHILDREN;
		}

		public Object getParent(TreeListDialogField field, Object element) {
			if (element instanceof TemplatePersistenceData) {
				TemplatePersistenceData data= (TemplatePersistenceData) element;
				if (data.getTemplate().getName().endsWith(CodeTemplateContextType.COMMENT_SUFFIX)) {
					return COMMENT_NODE;
				}
			}
			return null;
		}

		public boolean hasChildren(TreeListDialogField field, Object element) {
			return (element == COMMENT_NODE);
		}

		public void dialogFieldChanged(DialogField field) {
		}

		public void keyPressed(TreeListDialogField field, KeyEvent event) {
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ViewerSorter#category(java.lang.Object)
		 */
		public int category(Object element) {
			if (element == COMMENT_NODE) {
				return 1;
			} 
			
			TemplatePersistenceData data= (TemplatePersistenceData) element;
			String id= data.getId();
			
			if (CodeTemplateContextType.CTYPE_ID.equals(id)) {
				return 101;
			} 
			else if (CodeTemplateContextType.INTERFACE_ID.equals(id)) {
				return 101;
			}
			return 1000;
		}
	}

	private static class CodeTemplateLabelProvider extends LabelProvider {
	
		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
		 */
		public Image getImage(Object element) {
			return null;

		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
		 */
		public String getText(Object element) {
			if (element == COMMENT_NODE) {
				return (String) element;
			}
			TemplatePersistenceData data= (TemplatePersistenceData) element;
			String id=data.getId();
			if (CodeTemplateContextType.CTYPE_ID.equals(id)) {
				return "Ctype"; 
			} else if (CodeTemplateContextType.INTERFACE_ID.equals(id)) {
				return "Interface"; 
			} 
			return data.getTemplate().getDescription();
		}
	}		
	
	private final static int IDX_EDIT= 0;
	private final static int IDX_IMPORT= 2;
	private final static int IDX_EXPORT= 3;
	private final static int IDX_EXPORTALL= 4;
	
	protected final static Object COMMENT_NODE= "Comment";

	private TreeListDialogField fCodeTemplateTree;

	protected ProjectTemplateStore fTemplateStore;
	
	private PixelConverter fPixelConverter;
	private SourceViewer fPatternViewer;
	private Control fSWTWidget;
	private TemplateVariableProcessor fTemplateProcessor;
	
	private final org.eclipse.core.resources.IProject fProject;

	public CodeTemplateBlock(IProject project) {
		
		fProject= project;
		
		fTemplateStore= new ProjectTemplateStore(project);
		try {
			fTemplateStore.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		fTemplateProcessor= new TemplateVariableProcessor();
		
		CodeTemplateAdapter adapter= new CodeTemplateAdapter();

		String[] buttonLabels= new String[] { 
			"Edit",	
			/* */ null,  
			"Import", 
			"Export", 
			"Export All"

		};		
		fCodeTemplateTree= new TreeListDialogField(adapter, buttonLabels, new CodeTemplateLabelProvider());
		fCodeTemplateTree.setDialogFieldListener(adapter);
		fCodeTemplateTree.setLabelText("Code Template Block");
		fCodeTemplateTree.setViewerSorter(adapter);

		fCodeTemplateTree.enableButton(IDX_EXPORT, false);
		fCodeTemplateTree.enableButton(IDX_EDIT, false);
		
		fCodeTemplateTree.addElement(COMMENT_NODE);

		fCodeTemplateTree.selectFirstElement();	
	}

	public void postSetSelection(Object element) {
		fCodeTemplateTree.postSetSelection(new StructuredSelection(element));
	}

	public boolean hasProjectSpecificOptions(IProject project) {
		if (project != null) {
			return ProjectTemplateStore.hasProjectSpecificTempates(project);
		}
		return false;
	}	
	
	protected Control createContents(Composite parent) {
		fPixelConverter=  new PixelConverter(parent);
		fSWTWidget= parent;
		
		Composite composite=  new Composite(parent, SWT.NONE);
		composite.setFont(parent.getFont());
		
		GridLayout layout= new GridLayout();
		layout.marginHeight= 0;
		layout.marginWidth= 0;
		layout.numColumns= 2;
		composite.setLayout(layout);
		
		fCodeTemplateTree.doFillIntoGrid(composite, 3);
		LayoutUtil.setHorizontalSpan(fCodeTemplateTree.getLabelControl(null), 2);
		LayoutUtil.setHorizontalGrabbing(fCodeTemplateTree.getTreeControl(null));
		
		fPatternViewer= createViewer(composite, 2);
		
		return composite;
	}
	
	private Shell getShell() {
		if (fSWTWidget != null) {
			return fSWTWidget.getShell();
		}
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();			
	}	
	
	private SourceViewer createViewer(Composite parent, int nColumns) {
		Label label= new Label(parent, SWT.NONE);
		label.setText("pattern"); 
		GridData data= new GridData();
		data.horizontalSpan= nColumns;
		label.setLayoutData(data);
		
		IDocument document= new Document();
		IDLTKUILanguageToolkit toolkit = DLTKUILanguageManager.getLanguageToolkit(VjoNature.NATURE_ID);
		ScriptTextTools textTools = toolkit.getTextTools();
		textTools.setupDocumentPartitioner(document, toolkit.getPartitioningId());
		IPreferenceStore store = toolkit.getPreferenceStore();
		ScriptSourceViewer viewer = new ScriptSourceViewer(parent, null, null, false, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL, store);
		ScriptSourceViewerConfiguration configuration = toolkit.createSourceViewerConfiguration();
		viewer.configure(configuration);
		viewer.setEditable(false);
		viewer.setDocument(document);
		new ScriptSourcePreviewerUpdater(viewer, configuration, store);
		Control control= viewer.getControl();
		data= new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.FILL_VERTICAL);
		data.horizontalSpan= nColumns;
		data.heightHint= fPixelConverter.convertHeightInCharsToPixels(5);
		control.setLayoutData(data);
		
		return viewer;
	}
	
	protected TemplatePersistenceData[] getTemplateOfCategory(boolean isComment) {
		ArrayList res=  new ArrayList();
		TemplatePersistenceData[] templates= fTemplateStore.getTemplateData();
		for (int i= 0; i < templates.length; i++) {
			TemplatePersistenceData curr= templates[i];
			if (isComment == curr.getTemplate().getName().endsWith(CodeTemplateContextType.COMMENT_SUFFIX)) {
				res.add(curr);
			}
		}
		return (TemplatePersistenceData[]) res.toArray(new TemplatePersistenceData[res.size()]);
	}
	
	protected static boolean canEdit(List selected) {
		return selected.size() == 1 && (selected.get(0) instanceof TemplatePersistenceData);
	}	
	
	protected void updateSourceViewerInput(List selection) {
		if (fPatternViewer == null || fPatternViewer.getTextWidget().isDisposed()) {
			return;
		}
		if (selection.size() == 1 && selection.get(0) instanceof TemplatePersistenceData) {
			TemplatePersistenceData data= (TemplatePersistenceData) selection.get(0);
			Template template= data.getTemplate();
			TemplateContextType type= VjetUIPlugin.getDefault().getCodeTemplateContextRegistry().getContextType(template.getContextTypeId());
			fTemplateProcessor.setContextType(type);
			fPatternViewer.getDocument().set(template.getPattern());
		} else {
			fPatternViewer.getDocument().set(""); //$NON-NLS-1$
		}		
	}
		
	protected void doButtonPressed(int buttonIndex, List selected) {
		if (buttonIndex == IDX_EDIT) {
			edit((TemplatePersistenceData) selected.get(0));
		} else if (buttonIndex == IDX_EXPORT) {
			export(selected);
		} else if (buttonIndex == IDX_EXPORTALL) {
			exportAll();
		} else if (buttonIndex == IDX_IMPORT) {
			import_();
		}
	}
	
	private void edit(TemplatePersistenceData data) {
//		Template newTemplate= new Template(data.getTemplate());
//		EditTemplateDialog dialog= new EditTemplateDialog(getShell(), newTemplate, true, false, JavaPlugin.getDefault().getCodeTemplateContextRegistry());
//		if (dialog.open() == Window.OK) {
//			// changed
//			data.setTemplate(dialog.getTemplate());
//			fCodeTemplateTree.refresh(data);
//			fCodeTemplateTree.selectElements(new StructuredSelection(data));
//		}
	}
		
	private void import_() {
		FileDialog dialog= new FileDialog(getShell());
		dialog.setText("Import Template"); 
		String path= dialog.open();
		
		if (path == null)
			return;
		
		try {
			TemplateReaderWriter reader= new TemplateReaderWriter();
			File file= new File(path);
			if (file.exists()) {
				InputStream input= new BufferedInputStream(new FileInputStream(file));
				try {
					TemplatePersistenceData[] datas= reader.read(input, null);
					for (int i= 0; i < datas.length; i++) {
						updateTemplate(datas[i]);
					}
				} finally {
					try {
						input.close();
					} catch (IOException x) {
					}
				}
			}

			fCodeTemplateTree.refresh();
			updateSourceViewerInput(fCodeTemplateTree.getSelectedElements());

		} catch (FileNotFoundException e) {
			openReadErrorDialog(e);
		} catch (IOException e) {
			openReadErrorDialog(e);
		}

	}
	
	private void updateTemplate(TemplatePersistenceData data) {
		TemplatePersistenceData[] datas= fTemplateStore.getTemplateData();
		for (int i= 0; i < datas.length; i++) {
			String id= datas[i].getId();
			if (id != null && id.equals(data.getId())) {
				datas[i].setTemplate(data.getTemplate());
				break;
			}
		}
	}
	
	private void exportAll() {
		export(fTemplateStore.getTemplateData());	
	}
	
	private void export(List selected) {
		Set datas= new HashSet();
		for (int i= 0; i < selected.size(); i++) {
			Object curr= selected.get(i);
			if (curr instanceof TemplatePersistenceData) {
				datas.add(curr);
			} else {
				TemplatePersistenceData[] cat= getTemplateOfCategory(curr == COMMENT_NODE);
				datas.addAll(Arrays.asList(cat));
			}
		}
		export((TemplatePersistenceData[]) datas.toArray(new TemplatePersistenceData[datas.size()]));
	}
	
	private void export(TemplatePersistenceData[] templates) {
		FileDialog dialog= new FileDialog(getShell(), SWT.SAVE);
		dialog.setText("Export Template"); 
		dialog.setFileName("template.xml"); 
		String path= dialog.open();

		if (path == null)
			return;
		
		File file= new File(path);		

		if (file.isHidden()) {
			String title= "Export failed"; 
			String message= "Export failed"; 
			MessageDialog.openError(getShell(), title, message);
			return;
		}
		
		if (file.exists() && !file.canWrite()) {
			String title= "Export Failed"; 
			String message= "file is readonly"; 
			MessageDialog.openError(getShell(), title, message);
			return;
		}

		if (!file.exists() || confirmOverwrite(file)) {
			OutputStream output= null;
			try {
				output= new BufferedOutputStream(new FileOutputStream(file));
				TemplateReaderWriter writer= new TemplateReaderWriter();
				writer.save(templates, output);
				output.close();
			} catch (IOException e) {
				if (output != null) {
					try {
						output.close();
					} catch (IOException e2) {
						// ignore 
					}
				}
				openWriteErrorDialog(e);
			}
		}
		
	}

	private boolean confirmOverwrite(File file) {
		return MessageDialog.openQuestion(getShell(),
			"Already exists, overwrite?", 
			"Already exsits"); 
	}

	public void performDefaults() {
		fTemplateStore.restoreDefaults();
		
		// refresh
		fCodeTemplateTree.refresh();
		updateSourceViewerInput(fCodeTemplateTree.getSelectedElements());
	}
	
	public boolean performOk(boolean enabled) {
		if (fProject != null) {
			TemplatePersistenceData[] templateData= fTemplateStore.getTemplateData();
			for (int i= 0; i < templateData.length; i++) {
				fTemplateStore.setProjectSpecific(templateData[i].getId(), enabled);
			}
		}
		try {
			fTemplateStore.save();
		} catch (IOException e) {
			openWriteErrorDialog(e);
		}
		return true;
	}
	
	public void performCancel() {
		try {
			fTemplateStore.revertChanges();
		} catch (IOException e) {
			openReadErrorDialog(e);
		}
	}
	
	private void openReadErrorDialog(Exception e) {
		String title= "read error"; 
		
		String message= e.getLocalizedMessage();
		MessageDialog.openError(getShell(), title, message);
	}
	
	private void openWriteErrorDialog(Exception e) {
		String title= "write error"; 
		String message= "write error"; 
		MessageDialog.openError(getShell(), title, message);
	}
}


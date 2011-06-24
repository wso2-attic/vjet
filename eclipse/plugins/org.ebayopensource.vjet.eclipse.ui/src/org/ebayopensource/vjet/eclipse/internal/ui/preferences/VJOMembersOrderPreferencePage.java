/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.ebayopensource.vjet.eclipse.internal.ui.preferences;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.ebayopensource.vjet.eclipse.ui.VjetPreferenceConstants;
import org.ebayopensource.vjet.eclipse.ui.VjoElementImageDescriptor;
import org.ebayopensource.vjet.eclipse.ui.VjoElementImageProvider;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.dltk.mod.ui.viewsupport.ImageDescriptorRegistry;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.DialogField;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.IDialogFieldListener;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.ListDialogField;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.SelectionButtonDialogField;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class VJOMembersOrderPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	
	public static final String PREF_ID= "org.eclipse.jdt.ui.preferences.MembersOrderPreferencePage"; //$NON-NLS-1$
	
	private static final String ALL_SORTMEMBER_ENTRIES= "T,SI,SF,SM,I,F,C,M"; //$NON-NLS-1$
	private static final String ALL_VISIBILITY_ENTRIES= "B,V,R,D"; //$NON-NLS-1$
	private static final String PREF_OUTLINE_SORT_OPTION= VjetPreferenceConstants.APPEARANCE_MEMBER_SORT_ORDER;
	private static final String PREF_VISIBILITY_SORT_OPTION= VjetPreferenceConstants.APPEARANCE_VISIBILITY_SORT_ORDER;
	private static final String PREF_USE_VISIBILITY_SORT_OPTION= VjetPreferenceConstants.APPEARANCE_ENABLE_VISIBILITY_SORT_ORDER;
		
	public static final String CONSTRUCTORS= "C"; //$NON-NLS-1$
	public static final String FIELDS= "F"; //$NON-NLS-1$
	public static final String METHODS= "M"; //$NON-NLS-1$
	public static final String STATIC_METHODS= "SM"; //$NON-NLS-1$
	public static final String STATIC_FIELDS= "SF"; //$NON-NLS-1$
	public static final String INIT= "I"; //$NON-NLS-1$
	public static final String STATIC_INIT= "SI"; //$NON-NLS-1$
	public static final String TYPES= "T"; //$NON-NLS-1$
	
	public static final String PUBLIC= "B";  //$NON-NLS-1$
	public static final String PRIVATE= "V"; //$NON-NLS-1$
	public static final String PROTECTED= "R"; //$NON-NLS-1$
	public static final String DEFAULT= "D";  //$NON-NLS-1$

	private boolean fUseVisibilitySort;
	private ListDialogField fSortOrderList;
	private ListDialogField fVisibilityOrderList;
	private SelectionButtonDialogField fUseVisibilitySortField;

	private static boolean isValidEntries(List entries, String entryString) {
		StringTokenizer tokenizer= new StringTokenizer(entryString, ","); //$NON-NLS-1$
		int i= 0;
		for (; tokenizer.hasMoreTokens(); i++) {
			String token= tokenizer.nextToken();
			if (!entries.contains(token))
				return false;
		}
		return i == entries.size();
	}

	public VJOMembersOrderPreferencePage() {
		//set the preference store
		setPreferenceStore(DLTKUIPlugin.getDefault().getPreferenceStore());//must use dltk plugin preference store
		
		setDescription(VjetPreferenceMessages.MembersOrderPreferencePage_label_description); 

		String memberSortString= getPreferenceStore().getString(PREF_OUTLINE_SORT_OPTION);
		
		String upLabel= VjetPreferenceMessages.MembersOrderPreferencePage_category_button_up; 
		String downLabel= VjetPreferenceMessages.MembersOrderPreferencePage_category_button_down; 

		// category sort
		
		fSortOrderList= new ListDialogField(null,  new String[] { upLabel, downLabel }, new MemberSortLabelProvider());
		fSortOrderList.setDownButtonIndex(1);
		fSortOrderList.setUpButtonIndex(0);
		
		//validate entries stored in store, false get defaults
		List entries= parseList(memberSortString);
		if (!isValidEntries(entries, ALL_SORTMEMBER_ENTRIES)) {
			memberSortString= getPreferenceStore().getDefaultString(PREF_OUTLINE_SORT_OPTION);
			entries= parseList(memberSortString);
		}
		
		fSortOrderList.setElements(entries);
		
		// visibility sort

		fUseVisibilitySort= getPreferenceStore().getBoolean(PREF_USE_VISIBILITY_SORT_OPTION);

		String visibilitySortString= getPreferenceStore().getString(PREF_VISIBILITY_SORT_OPTION); 
		
		upLabel= VjetPreferenceMessages.MembersOrderPreferencePage_visibility_button_up; 
		downLabel= VjetPreferenceMessages.MembersOrderPreferencePage_visibility_button_down; 
		
		fVisibilityOrderList= new ListDialogField(null, new String[] { upLabel, downLabel }, new VisibilitySortLabelProvider());
		fVisibilityOrderList.setDownButtonIndex(1);
		fVisibilityOrderList.setUpButtonIndex(0);
		
		//validate entries stored in store, false get defaults
		entries= parseList(visibilitySortString);
		if (!isValidEntries(entries, ALL_VISIBILITY_ENTRIES)) {
			visibilitySortString= getPreferenceStore().getDefaultString(PREF_VISIBILITY_SORT_OPTION);
			entries= parseList(visibilitySortString);
		}
		fVisibilityOrderList.setElements(entries);
	}
	
	private static List parseList(String string) {
		StringTokenizer tokenizer= new StringTokenizer(string, ","); //$NON-NLS-1$
		List entries= new ArrayList();
		for (int i= 0; tokenizer.hasMoreTokens(); i++) {
			String token= tokenizer.nextToken();
			entries.add(token);
		}
		return entries;
	}
	
	/*
	 * @see PreferencePage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		super.createControl(parent);
		//PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), IJavaHelpContextIds.SORT_ORDER_PREFERENCE_PAGE);
	}

	/*
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		// Create both the dialog lists
		Composite sortComposite= new Composite(parent, SWT.NONE);
		sortComposite.setFont(parent.getFont());

		GridLayout layout= new GridLayout();
		layout.numColumns= 2;
		layout.marginWidth= 0;
		layout.marginHeight= 0;
		sortComposite.setLayout(layout);

		GridData gd= new GridData();
		gd.verticalAlignment= GridData.FILL;
		gd.horizontalAlignment= GridData.FILL_HORIZONTAL;
		sortComposite.setLayoutData(gd);

		createListDialogField(sortComposite, fSortOrderList);
		
		fUseVisibilitySortField= new SelectionButtonDialogField(SWT.CHECK);
		fUseVisibilitySortField.setDialogFieldListener(new IDialogFieldListener() {
			public void dialogFieldChanged(DialogField field) {
				fVisibilityOrderList.setEnabled(fUseVisibilitySortField.isSelected());
			}
		});
		fUseVisibilitySortField.setLabelText(VjetPreferenceMessages.MembersOrderPreferencePage_usevisibilitysort_label); 
		fUseVisibilitySortField.doFillIntoGrid(sortComposite, 2);
		fUseVisibilitySortField.setSelection(fUseVisibilitySort);
		
		createListDialogField(sortComposite, fVisibilityOrderList);
		fVisibilityOrderList.setEnabled(fUseVisibilitySortField.isSelected());
		
		Dialog.applyDialogFont(sortComposite);
		
		return sortComposite;
	}
	

	private void createListDialogField(Composite composite, ListDialogField dialogField) {
		Control list= dialogField.getListControl(composite);
		GridData gd= new GridData();
		gd.horizontalAlignment= GridData.FILL;
		gd.grabExcessHorizontalSpace= true;
		gd.verticalAlignment= GridData.FILL;
		gd.grabExcessVerticalSpace= true;
		gd.widthHint= convertWidthInCharsToPixels(50);

		list.setLayoutData(gd);
		
		Composite buttons= dialogField.getButtonBox(composite);
		gd= new GridData();
		gd.horizontalAlignment= GridData.FILL;
		gd.grabExcessHorizontalSpace= false;
		gd.verticalAlignment= GridData.FILL;
		gd.grabExcessVerticalSpace= true;
		
		buttons.setLayoutData(gd);
	}

	/*
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	/*
	 * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
	 */
	protected void performDefaults() {
		IPreferenceStore prefs= DLTKUIPlugin.getDefault().getPreferenceStore();
		String str= prefs.getDefaultString(PREF_OUTLINE_SORT_OPTION);
		if (str != null)
			fSortOrderList.setElements(parseList(str));
		else
			fSortOrderList.setElements(parseList(ALL_SORTMEMBER_ENTRIES));
	
		str= prefs.getDefaultString(PREF_VISIBILITY_SORT_OPTION);
		if (str != null)
			fVisibilityOrderList.setElements(parseList(str));
		else
			fVisibilityOrderList.setElements(parseList(ALL_VISIBILITY_ENTRIES));
	
		fUseVisibilitySortField.setSelection(prefs.getDefaultBoolean(PREF_USE_VISIBILITY_SORT_OPTION));
		
		super.performDefaults();
	}

	/*
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */
	//reorders elements in the Outline based on selection
	public boolean performOk() {

		//save preferences for both dialog lists
		IPreferenceStore store= getPreferenceStore();
		updateList(store, fSortOrderList, PREF_OUTLINE_SORT_OPTION);
		updateList(store, fVisibilityOrderList, PREF_VISIBILITY_SORT_OPTION);
		
		//update the button setting
		store.setValue(PREF_USE_VISIBILITY_SORT_OPTION, fUseVisibilitySortField.isSelected());
		DLTKUIPlugin.getDefault().savePluginPreferences();
		
		return true;
	}
	
	private void updateList(IPreferenceStore store, ListDialogField list, String str) {
		StringBuffer buf= new StringBuffer();
		List curr= list.getElements();
		for (Iterator iter= curr.iterator(); iter.hasNext();) {
			String s= (String) iter.next();
			buf.append(s);
			buf.append(',');
		}
		store.setValue(str, buf.toString());
	}

	private class MemberSortLabelProvider extends LabelProvider {

		public MemberSortLabelProvider() {
		}
		
		/*
		* @see org.eclipse.jface.viewers.ILabelProvider#getImage(Object)
		*/
		public Image getImage(Object element) {
			//access to image registry
			ImageDescriptorRegistry registry= DLTKUIPlugin.getImageDescriptorRegistry();
			ImageDescriptor descriptor= null;

			if (element instanceof String) {
				int visibility= Flags.AccPublic;
				String s= (String) element;
				if (s.equals(FIELDS)) {
					//0 will give the default field image	
					descriptor= VjoElementImageProvider.getFieldImageDescriptor( visibility);
				} else if (s.equals(CONSTRUCTORS)) {
					descriptor= VjoElementImageProvider.getMethodImageDescriptor( visibility);
					//add a constructor adornment to the image descriptor
					descriptor= new VjoElementImageDescriptor(descriptor, VjoElementImageDescriptor.CONSTRUCTOR, VjoElementImageProvider.SMALL_SIZE);
				} else if (s.equals(METHODS)) {
					descriptor= VjoElementImageProvider.getMethodImageDescriptor( visibility);
				} else if (s.equals(STATIC_FIELDS)) {
					descriptor= VjoElementImageProvider.getFieldImageDescriptor( visibility);
					//add a static fields adornment to the image descriptor
					descriptor= new VjoElementImageDescriptor(descriptor, VjoElementImageDescriptor.STATIC, VjoElementImageProvider.SMALL_SIZE);
				} else if (s.equals(STATIC_METHODS)) {
					descriptor= VjoElementImageProvider.getMethodImageDescriptor( visibility);
					//add a static methods adornment to the image descriptor
					descriptor= new VjoElementImageDescriptor(descriptor, VjoElementImageDescriptor.STATIC, VjoElementImageProvider.SMALL_SIZE);
				} else if (s.equals(INIT)) {
					descriptor= VjoElementImageProvider.getMethodImageDescriptor( visibility);
				} else if (s.equals(STATIC_INIT)) {
					descriptor= VjoElementImageProvider.getMethodImageDescriptor( visibility);
					
					descriptor= new VjoElementImageDescriptor(descriptor, VjoElementImageDescriptor.STATIC, VjoElementImageProvider.SMALL_SIZE);
				} else if (s.equals(TYPES)) {
					descriptor= VjoElementImageProvider.getTypeImageDescriptor( Flags.AccPublic, false);
				} else {
					descriptor= VjoElementImageProvider.getMethodImageDescriptor( Flags.AccPublic);
				}
				return registry.get(descriptor);
			}
			return null;
		}

		/*
		 * @see org.eclipse.jface.viewers.ILabelProvider#getText(Object)
		 */
		public String getText(Object element) {

			if (element instanceof String) {
				String s= (String) element;
				if (s.equals(FIELDS)) {
					return VjetPreferenceMessages.MembersOrderPreferencePage_fields_label; 
				} else if (s.equals(METHODS)) {
					return VjetPreferenceMessages.MembersOrderPreferencePage_methods_label; 
				} else if (s.equals(STATIC_FIELDS)) {
					return VjetPreferenceMessages.MembersOrderPreferencePage_staticfields_label; 
				} else if (s.equals(STATIC_METHODS)) {
					return VjetPreferenceMessages.MembersOrderPreferencePage_staticmethods_label; 
				} else if (s.equals(CONSTRUCTORS)) {
					return VjetPreferenceMessages.MembersOrderPreferencePage_constructors_label; 
				} else if (s.equals(INIT)) {
					return VjetPreferenceMessages.MembersOrderPreferencePage_initialisers_label; 
				} else if (s.equals(STATIC_INIT)) {
					return VjetPreferenceMessages.MembersOrderPreferencePage_staticinitialisers_label; 
				} else if (s.equals(TYPES)) {
					return VjetPreferenceMessages.MembersOrderPreferencePage_types_label; 
				}
			}
			return ""; //$NON-NLS-1$
		}
	}

	
	private class VisibilitySortLabelProvider extends LabelProvider {
		
		public VisibilitySortLabelProvider() {
		}
		
		/*
		 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(Object)
		 */
		public Image getImage(Object element) {
			//access to image registry
			ImageDescriptorRegistry registry= DLTKUIPlugin.getImageDescriptorRegistry();
			ImageDescriptor descriptor= null;
			
			if (element instanceof String) {
				String s= (String) element;
				if (s.equals(PUBLIC)) {
					descriptor= VjoElementImageProvider.getMethodImageDescriptor(Flags.AccPublic);
				} else if (s.equals(PRIVATE)) {
					descriptor= VjoElementImageProvider.getMethodImageDescriptor( Flags.AccPrivate);
				} else if (s.equals(PROTECTED)) {
					descriptor= VjoElementImageProvider.getMethodImageDescriptor( Flags.AccProtected);
				} else if (s.equals(DEFAULT)) {
					descriptor= VjoElementImageProvider.getMethodImageDescriptor( Flags.AccDefault);
				}
				return registry.get(descriptor);
			}
			return null;
		}
		
		/*
		 * @see org.eclipse.jface.viewers.ILabelProvider#getText(Object)
		 */
		public String getText(Object element) {
			if (element instanceof String) {
				String s= (String) element;
				
				if (s.equals(PUBLIC)) {
					return VjetPreferenceMessages.MembersOrderPreferencePage_public_label; 
				} else if (s.equals(PRIVATE)) {
					return VjetPreferenceMessages.MembersOrderPreferencePage_private_label; 
				} else if (s.equals(PROTECTED)) {
					return VjetPreferenceMessages.MembersOrderPreferencePage_protected_label; 
				} else if (s.equals(DEFAULT)) {
					return VjetPreferenceMessages.MembersOrderPreferencePage_default_label; 
				}
			}
			return ""; //$NON-NLS-1$
		}
	}

	

}

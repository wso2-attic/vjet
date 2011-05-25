package org.ebayopensource.vjet.eclipse.internal.debug.ui.launchConf;

import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.dltk.mod.core.PreferencesLookupDelegate;
import org.eclipse.dltk.mod.debug.ui.launchConfigurations.ScriptLaunchConfigurationTab;
import org.eclipse.dltk.mod.debug.ui.messages.DLTKLaunchConfigurationsMessages;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class VjetWebArtifactTab extends ScriptLaunchConfigurationTab {

	private static final String FIELD_JSFILE = "jsfile";


	public VjetWebArtifactTab(String mode) {
		super(mode);
		// TODO Auto-generated constructor stub
	}

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Text text;
	private Text fJsText;
	private Button fJsLoadButton;


	public void createControl(Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		setControl(comp);

		GridLayout topLayout = new GridLayout();
		topLayout.verticalSpacing = 0;
		comp.setLayout(topLayout);

		createProjectEditor(comp);
		
		createJsFileLoader(comp);
		createVerticalSpacer(comp, 1);

		doCreateControl(comp);
		createVerticalSpacer(comp, 1);

		createDebugOptionsGroup(comp);

		createCustomSections(comp);
		Dialog.applyDialogFont(comp);
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
		// IScriptDebugHelpContextIds.LAUNCH_CONFIGURATION_DIALOG_MAIN_TAB);
	}
	
	protected void createJsFileLoader(Composite parent) {
		final Composite editParent;
		if (needGroupForField(FIELD_JSFILE)) {
			Group group = new Group(parent, SWT.NONE);
			group
					.setText(DLTKLaunchConfigurationsMessages.mainTab_projectGroup);

			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			group.setLayoutData(gd);

			GridLayout layout = new GridLayout();
			layout.numColumns = 2;
			group.setLayout(layout);
			editParent = group;
		} else {
			createLabelForField(parent, FIELD_JSFILE,
					DLTKLaunchConfigurationsMessages.mainTab_projectGroup);
			editParent = parent;
		}
		fJsText = new Text(editParent, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		fJsText.setLayoutData(gd);
		fJsText.addModifyListener(fListener);

		fJsLoadButton = createPushButton(editParent,
				DLTKLaunchConfigurationsMessages.mainTab_projectButton, null);
		fJsLoadButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleProjectButtonSelected();
			}
		});
	}
	
	
	@Override
	public String getName() {
		return "Web Artifacts";
	}

	@Override
	protected boolean breakOnFirstLinePrefEnabled(
			PreferencesLookupDelegate delegate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean dbpgLoggingPrefEnabled(PreferencesLookupDelegate delegate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void doCreateControl(Composite composite) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doInitializeForm(ILaunchConfiguration config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doPerformApply(ILaunchConfigurationWorkingCopy config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getNatureID() {
		return VjoNature.NATURE_ID;
	}
}

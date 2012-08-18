/**
 * 
 */
package org.eclipse.dltk.mod.debug.ui.launchConfigurations;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.dltk.mod.debug.ui.ScriptDebugImages;
import org.eclipse.dltk.mod.launching.ScriptLaunchConfigurationConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * new tab for script 'build' configuration
 * 
 * @author xingzhu
 * 
 */
public class ScriptBuildTab extends AbstractLaunchConfigurationTab {
	private Button buildBeforeLaunch;

	private Group buildOpitionsGroup;
	private Button java2jsBeforeLaunch;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, true));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		setControl(composite);

		// create build before launch checkbox
		this.buildBeforeLaunch = new Button(composite, SWT.CHECK);
		this.buildBeforeLaunch.setText("Build before Launch");
		this.buildBeforeLaunch.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));
		this.buildBeforeLaunch.addSelectionListener(new SelectionHandler());

		// create build opitions group and corresponding option radio buttons
		this.buildOpitionsGroup = new Group(composite, SWT.NONE);
		this.buildOpitionsGroup.setLayout(new GridLayout(1, true));
		this.buildOpitionsGroup.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));

		this.java2jsBeforeLaunch = new Button(buildOpitionsGroup, SWT.CHECK);
		this.java2jsBeforeLaunch.setText("Java 2 JS");
		this.java2jsBeforeLaunch.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));
		this.java2jsBeforeLaunch.addSelectionListener(new SelectionHandler());
	}

	private void handleSelection(SelectionEvent e) {
		if (e.getSource() == this.buildBeforeLaunch) {
			boolean isBuildBeforeLaunch = this.buildBeforeLaunch.getSelection();
			if (isBuildBeforeLaunch) {
				this.buildOpitionsGroup.setEnabled(true);
				this.java2jsBeforeLaunch.setEnabled(true);
			} else {
				this.buildOpitionsGroup.setEnabled(false);
				this.java2jsBeforeLaunch.setSelection(false);
				this.java2jsBeforeLaunch.setEnabled(false);
			}

		}

		updateLaunchConfigurationDialog();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName() {
		return "Build";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.AbstractLaunchConfigurationTab#getImage()
	 */
	public Image getImage() {
		return ScriptDebugImages.get(ScriptDebugImages.IMG_VIEW_ARGUMENTS_TAB);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			boolean isBuildBeforeLaunch = configuration
					.getAttribute(
							ScriptLaunchConfigurationConstants.ATTR_BUILD_BEFORE_LAUNCH,
							false);
			boolean isJava2JsBeforeLaunch = configuration
					.getAttribute(
							ScriptLaunchConfigurationConstants.ATTR_JAVA2JS_BEFORE_LAUNCH,
							false);

			if (isBuildBeforeLaunch) {
				this.buildBeforeLaunch.setSelection(true);
				this.java2jsBeforeLaunch.setSelection(isJava2JsBeforeLaunch);
			} else {
				if (this.java2jsBeforeLaunch.isEnabled())
					this.java2jsBeforeLaunch.setSelection(false);

				this.buildBeforeLaunch.setSelection(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		boolean isBuildBeforeLaunch = this.buildBeforeLaunch.getSelection();
		if (isBuildBeforeLaunch) {
			configuration
					.setAttribute(
							ScriptLaunchConfigurationConstants.ATTR_BUILD_BEFORE_LAUNCH,
							this.buildBeforeLaunch.getSelection());

			configuration
					.setAttribute(
							ScriptLaunchConfigurationConstants.ATTR_JAVA2JS_BEFORE_LAUNCH,
							this.java2jsBeforeLaunch.getSelection());
		} else {
			configuration
					.setAttribute(
							ScriptLaunchConfigurationConstants.ATTR_BUILD_BEFORE_LAUNCH,
							false);

			configuration
					.setAttribute(
							ScriptLaunchConfigurationConstants.ATTR_JAVA2JS_BEFORE_LAUNCH,
							false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// update attribute values
		configuration.setAttribute(
				ScriptLaunchConfigurationConstants.ATTR_BUILD_BEFORE_LAUNCH,
				false);

		configuration.setAttribute(
				ScriptLaunchConfigurationConstants.ATTR_JAVA2JS_BEFORE_LAUNCH,
				false);

		// update corresponding UI widgets
		if (this.java2jsBeforeLaunch != null)
			this.java2jsBeforeLaunch.setSelection(false);
		if (this.buildBeforeLaunch != null)
			this.buildBeforeLaunch.setSelection(false);
	}

	private class SelectionHandler implements SelectionListener {
		public void widgetDefaultSelected(SelectionEvent e) {
			handleSelection(e);
		}

		public void widgetSelected(SelectionEvent e) {
			handleSelection(e);
		}
	}

}

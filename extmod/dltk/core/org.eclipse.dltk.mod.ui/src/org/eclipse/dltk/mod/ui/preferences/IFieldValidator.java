package org.eclipse.dltk.mod.ui.preferences;

import org.eclipse.core.runtime.IStatus;

public interface IFieldValidator {
	IStatus validate(String text);
}

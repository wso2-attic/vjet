/**
 * 
 */
package org.eclipse.dltk.mod.javascript.ui.actions;

import org.eclipse.dltk.mod.javascript.internal.ui.JavaScriptUILanguageToolkit;
import org.eclipse.dltk.mod.ui.IDLTKUILanguageToolkit;

/**
 * @author jcompagner
 * 
 */
public class OpenMethodAction extends
		org.eclipse.dltk.mod.ui.actions.OpenMethodAction {

	/**
	 * @see org.eclipse.dltk.mod.ui.actions.OpenMethodAction#getUILanguageToolkit()
	 */
	protected IDLTKUILanguageToolkit getUILanguageToolkit() {
		return new JavaScriptUILanguageToolkit();
	}

}

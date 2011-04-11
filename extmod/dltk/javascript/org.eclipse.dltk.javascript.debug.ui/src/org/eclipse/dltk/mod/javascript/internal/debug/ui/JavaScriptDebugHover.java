package org.eclipse.dltk.mod.javascript.internal.debug.ui;

import org.eclipse.dltk.mod.core.IField;
import org.eclipse.dltk.mod.debug.ui.ScriptDebugModelPresentation;
import org.eclipse.dltk.mod.internal.debug.ui.ScriptDebugHover;
import org.eclipse.dltk.mod.internal.javascript.typeinference.FakeField;
import org.eclipse.jface.preference.IPreferenceStore;

public class JavaScriptDebugHover extends ScriptDebugHover{

	protected ScriptDebugModelPresentation getModelPresentation() {
		return new JavaScriptDebugModelPresentation();
	}

	public void setPreferenceStore(IPreferenceStore store) {
		
	}
	protected String getFieldProperty(IField field) {
		if( field instanceof FakeField ) {
			return ((FakeField)field).getSnippet();
		}
		return super.getFieldProperty(field);
	}
}

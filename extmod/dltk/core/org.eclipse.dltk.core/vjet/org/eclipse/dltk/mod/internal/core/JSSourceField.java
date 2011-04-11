/**
 * 
 */
package org.eclipse.dltk.mod.internal.core;

import org.eclipse.dltk.mod.core.ModelException;

import org.ebayopensource.vjet.eclipse.core.IJSField;

/**
 * @author MPeleshchyshyn
 * 
 */
public class JSSourceField extends SourceField implements IJSField {

	/**
	 * @param parent
	 * @param name
	 */
	public JSSourceField(ModelElement parent, String name) {
		super(parent, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.core.IJSField#getConstant()
	 */
	public Object getConstant() throws ModelException {
		Object constant = null;
		JSSourceFieldElementInfo info = (JSSourceFieldElementInfo) getElementInfo();
		final String constantSourceChars = info.getInitializationSource();
		if (constantSourceChars == null) {
			return null;
		}

		constant = constantSourceChars;
		return constant;
	}
}

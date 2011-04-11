/**
 * 
 */
package org.ebayopensource.vjet.eclipse.core;

import org.eclipse.dltk.mod.core.IField;
import org.eclipse.dltk.mod.core.ModelException;

/**
 * @author MPeleshchyshyn
 * 
 */
public interface IJSField extends IField {

	public Object getConstant() throws ModelException;
}

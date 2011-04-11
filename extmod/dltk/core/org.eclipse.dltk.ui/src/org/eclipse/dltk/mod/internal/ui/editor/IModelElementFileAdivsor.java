package org.eclipse.dltk.mod.internal.ui.editor;

import java.io.File;

import org.eclipse.dltk.mod.core.IModelElement;

/**
 * get File from IModelElement, it can be used to support VjoEditor to open
 * none-source vjo type
 * 
 * @author jianliu
 * 
 */
public interface IModelElementFileAdivsor {

	/**
	 * Resolve the element into File
	 * 
	 * @param element
	 * @return
	 */
	File getFile(IModelElement element);

	/**
	 * The type which advisor supports
	 * 
	 * @return
	 */
	String getSupportedType();

}

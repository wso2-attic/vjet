/**
 * 
 */
package org.eclipse.dltk.mod.internal.core;

/**
 * @author MPeleshchyshyn
 * 
 */
public interface IJSSourceRefModelElementInfo extends IJSModelElementInfo {
	void setSourceRangeEnd(int end);

	void setSourceRangeStart(int start);
}

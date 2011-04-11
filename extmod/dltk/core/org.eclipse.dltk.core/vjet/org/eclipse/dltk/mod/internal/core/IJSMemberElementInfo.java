/**
 * 
 */
package org.eclipse.dltk.mod.internal.core;

/**
 * @author MPeleshchyshyn
 * 
 */
interface IJSMemberElementInfo extends IJSSourceRefModelElementInfo {
	void setFlags(int flags);

	public void setNameSourceEnd(int end);

	public void setNameSourceStart(int start);
}

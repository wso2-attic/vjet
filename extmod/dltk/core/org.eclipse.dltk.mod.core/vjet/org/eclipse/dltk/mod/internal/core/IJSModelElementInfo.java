package org.eclipse.dltk.mod.internal.core;

import org.eclipse.dltk.mod.core.IModelElement;

public interface IJSModelElementInfo {

	public void addChild(IModelElement child);

	public IModelElement[] getChildren();

	public void removeChild(IModelElement child);

	public void setChildren(IModelElement[] children);
}

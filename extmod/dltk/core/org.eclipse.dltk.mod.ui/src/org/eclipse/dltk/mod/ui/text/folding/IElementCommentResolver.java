package org.eclipse.dltk.mod.ui.text.folding;

import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.ISourceModule;

public interface IElementCommentResolver {

	IModelElement getElementByCommentPosition(ISourceModule module,
			int commentOffset, int commentLength);

}

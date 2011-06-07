package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Function;
import org.mozilla.mod.javascript.IWillBeScriptable;

public interface DocumentRange extends IWillBeScriptable {

	@Function
	Range createRange();
	
}

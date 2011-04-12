/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import org.ebayopensource.dsf.jsnative.anno.Function;
import org.mozilla.mod.javascript.IWillBeScriptable;
import org.mozilla.mod.javascript.NativeObject;

public interface FlashProxy extends IWillBeScriptable {

	@Function void onRemoteCall(NativeObject request);
}

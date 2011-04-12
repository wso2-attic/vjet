/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.cnr;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Interface for <code>DapCaptureData</code>
 */
public interface IDapCaptureSerializer {

	/**
	 * Serialize <code>DapCaptureData</code> to output stream
	 * @param capture DapCaptureData
	 * @param out OutputStream
	 */
	void serialize(DapCaptureData capture, OutputStream out);
	
	/**
	 * De-serialize input stream to <code>DapCaptureData</code>
	 * @param in InputStream
	 * @return DapCaptureData
	 */
	DapCaptureData deserialize(InputStream in);
}

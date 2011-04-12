/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.html.js;

public interface JSListener {

	//return value could be used to get value from dsf application.
	//targetId could be used to pass form_id, etc.
	public String doAction(
		int actionType,
		String name,
		String value,
		String targetId);

	//empty the action list
	public void reset();

	//num of action in the list
	public int getLength();

	public JSAction get(int index);
}

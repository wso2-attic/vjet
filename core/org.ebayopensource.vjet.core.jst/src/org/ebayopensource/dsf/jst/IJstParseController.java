/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst;

import java.io.File;

import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;

public interface IJstParseController {

	public IScriptUnit parse(String groupName, String fileName, String source);
	public IScriptUnit parse(String groupName, File sourceFile);
	public IScriptUnit parseAndResolve(String groupName, String fileName, String source);
	public IScriptUnit parseAndResolve(String groupName, File sourceFile);
	public void resolve(IJstType type);
	public void resolve(IJstProperty property);
	public void resolve(IJstMethod method);
	public JstTypeSpaceMgr getJstTypeSpaceMgr();
	public void resolve(String groupName, IJstType type);
	public void resolve(String groupName, IJstProperty property);
	public void resolve(String groupName, IJstMethod method);
	public void resolve(IJstType type, IJstNode node);
	
	public void setJstTSMgr(JstTypeSpaceMgr jstTSMgr);	
}

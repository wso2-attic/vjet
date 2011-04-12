/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;

public class VjoCcVisitor {

	//commented out as these vars are not used by anything in this class.
//	private int position;
//	private IJstType type;

	public VjoCcVisitor(IJstType type, int position) {
//		this.type = type;
//		this.position = position;
	}
	
//	public VjoCcCtx visit() {
//		VjoCcCtx ctx = new VjoCcCtx();
//		innerVisitor(null, null, null, null, 0);
//		//do visit here based on position and type;
//		return ctx;
//	}
	
	private void innerVisitor(IJstType jstType, String groupName, String fileName, String source, int completionPos ) {
		//commented out as it is not used anywhere in this method
		//code below where it is used was already commented out
		//so I have commented this line.
		//		VjoParser vjoParser = new VjoParser();

		TranslateCtx translateCtx = new TranslateCtx();
		translateCtx.setCompletionPos(completionPos);

//		IJstType type = vjoParser.parse(groupName, fileName, source,
//				translateCtx).getType();
//		IJstNode node = JstUtil.getNode(position, jstType);
	}
}

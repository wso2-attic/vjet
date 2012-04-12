/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust;

import java.util.List;

import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jstojava.parser.comments.CommentCollector.InactiveNeedsWrapper;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.MessageSend;

public class DefsRobustTranslator extends TypeRobustTranslator{
	public DefsRobustTranslator(TranslateCtx ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}



	protected void transformType() {
//		weakTranslator.getProvider().getTypeTranslator().processOType(
//				(MessageSend) current, jst);
		weakTranslator.getCtx().setCurrentType(jst);
	}
	
	public boolean transform() {

		// remove protos("") element from the stack
		current = astElements.pop();

		checkOnNameCompletion();
		
		//add inactive needs
		List<InactiveNeedsWrapper>ineeds = m_ctx.getCommentCollector().getInactiveNeeds();
		for (InactiveNeedsWrapper ineed : ineeds) {//TODO fix source
			final String ineedTypeName = ineed.getNeedsTypeName();
			JstSource source = new JstSource(JstSource.JS, -1, -1, ineedTypeName
					.length(), ineed.getBeginOffset(), ineed.getEndOffset()
					+ ineedTypeName.length());
			jst.addInactiveImport(TranslateHelper.getType(m_ctx, ineedTypeName, source));
		}
		// TODO change it in the future
		// check if this keyword has already been processed
		// if true put it into error chunk
		if(((MessageSend) current).arguments!=null){
			weakTranslator.getProvider().getDefsTranslator().process(
					((MessageSend) current).arguments[0], jst);
		}
		// lookup possible empty completions
		lookupEmptyCompletion();

		// move to the next iteration
		return false;

	}
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.keywords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.CompletionProposal;

public class JSTCompletionDictionary {

//	private IVjoCompletionData[] EMPTY = new IVjoCompletionData[] {};

	private static Map<Class, ICompletionHandler> handlers = new HashMap<Class, ICompletionHandler>();

	static {
		add(new NeedsCompletionHandler());
		add(new InheritsCompletionHandler());
		add(new SatisfiesCompletionHandler());
		add(new ExpectsCompletionHandler());
		add(new MixinCompletionHandler());
		add(new FieldOrMethodCompletionHandler());
		add(new MessageSendCompletionHandler());
		add(new SingleNameReferenceHandler());
		add(new QualifiedNameReferenceHandler());
		add(new MemberAccessHandler());
	}

	public static List<CompletionProposal> getCompletions(
			ISourceModule module, int position, JstCompletion completion) {
//		TypeSpaceMgr.getInstance().waitUntilLoaded();
		CompletionContext.setVariableContext(false);
		CompletionContext.setStaticContext(false);
		ICompletionHandler completionhandler;
		completionhandler = handlers.get(completion.getClass());
		List<CompletionProposal> list = new ArrayList<CompletionProposal>();
		if (completionhandler != null && module != null) {
			completionhandler.complete(module,position , completion, list);
		}
		return list;
	}

	private static void add(ICompletionHandler handler) {
		handlers.put(handler.getCompletionClass(), handler);
	}

}

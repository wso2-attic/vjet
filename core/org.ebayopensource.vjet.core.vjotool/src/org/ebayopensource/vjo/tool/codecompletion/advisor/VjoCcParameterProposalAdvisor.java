/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;

/**
 * advise the argument proposal, argument from the JstMethod where the cursor in.<br>
 * Example:<br>
 * fun : function() {<br>
 * 	xx<cursor>
 * }
 * 
 * Need attributes:
 * 1. ctx.actingToken
 * 2. ctx.selectedMethod
 * 
 * ProposalData.data: JstArgument
 * 
 * 
 * 
 */
public class VjoCcParameterProposalAdvisor extends AbstractVjoCcAdvisor
		implements IVjoCcAdvisor {
	public static final String ID = VjoCcParameterProposalAdvisor.class
			.getName();

	public void advise(VjoCcCtx ctx) {
		List<IJstMethod> methods = ctx.getSelectedJstMethods();
		if (methods.isEmpty()) {
			return;
		}
		List<String> sargs = new ArrayList<String>();
		Iterator<IJstMethod> mit = methods.iterator();
		while (mit.hasNext()) {
			IJstMethod method = mit.next();
			List<JstArg> args = method.getArgs();
			if (args.isEmpty()) {
				continue;
			}
			Iterator<JstArg> it = args.iterator();
			while (it.hasNext()) {
				JstArg arg = it.next();
				String name = arg.getName();
				String sarg = name + arg.getType();
				if (name != null && name.startsWith(ctx.getActingToken()) && !sargs.contains(sarg)) {
					appendData(ctx, arg);
					sargs.add(sarg);
				}
			}
		}

	}

}

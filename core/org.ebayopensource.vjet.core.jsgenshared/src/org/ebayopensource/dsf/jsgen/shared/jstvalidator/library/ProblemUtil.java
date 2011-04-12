/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.jstvalidator.library;

import org.ebayopensource.dsf.jsgen.shared.jstvalidator.DefaultJstProblem;
import org.ebayopensource.dsf.jsgen.shared.jstvalidator.ValidationCtx;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IScriptProblem;
import org.ebayopensource.dsf.jst.JstProblemId;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.ProblemSeverity;

public class ProblemUtil {

	public static IScriptProblem problem(JstProblemId id,
			IJstNode node, ValidationCtx ctx) {
			JstSource source = node.getSource();
			int startOffset = source.getStartOffSet();
			int endOffset = source.getEndOffSet();
			
			int line = source.getRow();
			int col = source.getColumn();
			IScriptProblem prb =new DefaultJstProblem(null,id,null,ctx.getFilePath().toCharArray(),startOffset,endOffset,line,col,ProblemSeverity.error);
			return prb;
			
	}
	
}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.util;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.stmt.DoStmt;
import org.ebayopensource.dsf.jst.stmt.ForInStmt;
import org.ebayopensource.dsf.jst.stmt.ForStmt;
import org.ebayopensource.dsf.jst.stmt.SwitchStmt;
import org.ebayopensource.dsf.jst.stmt.WhileStmt;

public class JstLoopUtil {

	
	public static boolean isLoopStmt(final IJstNode stmt){
		if(stmt == null){
			return false;
		}
		
		if(stmt instanceof WhileStmt
				|| stmt instanceof DoStmt
				|| stmt instanceof ForStmt
				|| stmt instanceof ForInStmt){
			return true;
		}
		
		return false;
	}

	public static boolean withinSwitchStmt(final IJstNode breakStmt, final IJstNode stop){
		if(breakStmt.getParentNode() == null || breakStmt == stop){
			return false;
		}
		else if(breakStmt.getParentNode() instanceof SwitchStmt){
			return true;
		}
		else{
			return withinSwitchStmt(breakStmt.getParentNode(), stop);
		}
	}
	
	public static boolean withinLoopStmt(final IJstNode breakStmt, final IJstNode stop){
		if(breakStmt.getParentNode() == null || breakStmt == stop){
			return false;
		}
		else if(JstLoopUtil.isLoopStmt(breakStmt.getParentNode())){
			return true;
		}
		else{
			return withinLoopStmt(breakStmt.getParentNode(), stop);
		}
	}
}

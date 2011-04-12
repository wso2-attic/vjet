/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.util;

import java.util.Comparator;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.declaration.JstArg;

public class JstMethodHelper {
	
	public static MtdComparator s_mtdComparator = new MtdComparator();
	public static class MtdComparator implements Comparator<IJstMethod> {
		public int compare(IJstMethod mtd1, IJstMethod mtd2){
			if (mtd1 == null || mtd2 == null){
				return 0;
			}
			List<JstArg> args1 = mtd1.getArgs();
			List<JstArg> args2 = mtd2.getArgs();
			if (args1.size() != args2.size()){
				return 0;
			}
			for (int i=0; i<args1.size(); i++){
				if (JstTypeHelper.isTypeOf(args2.get(i).getType(), args1.get(i).getType())){
					return 1;
				}
			}
			return 0;
		}
		
		public boolean equals(Object obj){
			return false;
		}
	}

}

/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.util;

import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jst.IJstAnnotation;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.token.IExpr;

/**
 * Utility class to help with DomLevel information in JstAnnotation
 *
 */
public class SupportedDomLevel {
	
	/**
	 * Return true if given IJstAnnotation indicates it is valid for DOM Level 0
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean isLevelZero(IJstAnnotation annot) {
		return isSupportedBy(annot, DomLevel.ZERO);
	}
	
	/**
	 * Return true if given IJstAnnotation indicates it is valid for DOM Level 1
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean isLevelOne(IJstAnnotation annot) {
		return isSupportedBy(annot, DomLevel.ONE);
	}
	
	/**
	 * Return true if given IJstAnnotation indicates it is valid for DOM Level 2
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean isLevelTwo(IJstAnnotation annot) {
		return isSupportedBy(annot, DomLevel.TWO);
	}
	
	/**
	 * Return true if given IJstAnnotation indicates it is valid for DOM Level 3
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean isLevelThree(IJstAnnotation annot) {
		return isSupportedBy(annot, DomLevel.THREE);
	}
	
	
	public static boolean isSupportedBy(IJstAnnotation annot, DomLevel domLevel) {
		if (!DOMSupport.class.getSimpleName().equals(annot.getName().toString())) {
			return false;
		}
		for (IExpr expr : annot.values()) {
			if (expr instanceof AssignExpr) {
				String lhs = ((AssignExpr)expr).getLHS().toLHSText();
				if (lhs.contains("domLevel")) {
					String level = ((AssignExpr)expr).getExpr().toExprText();
					int dotIndex = level.indexOf('.');
					if (dotIndex > 0) {
						String name = level.substring(dotIndex+1);
						return domLevel.name().equals(name);
					}
				}
			}
		}
		return false;
	}

}

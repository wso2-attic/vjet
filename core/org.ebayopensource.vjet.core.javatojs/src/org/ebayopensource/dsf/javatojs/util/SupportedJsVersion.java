/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.util;

import org.ebayopensource.dsf.jsnative.anno.JsSupport;
import org.ebayopensource.dsf.jsnative.anno.JsVersion;
import org.ebayopensource.dsf.jst.IJstAnnotation;
import org.ebayopensource.dsf.jst.expr.JstArrayInitializer;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;

/**
 * Utility class to help with JsVersion information in JstAnnotation
 *
 */
public class SupportedJsVersion {
	
	/**
	 * Return true if given IJstAnnotation contains JsVersion information and
	 * specified JsVersion is greater than or equal to Mozilla JavaScript 
	 * version 1.0
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean mozillaOneDotZero(IJstAnnotation annot) {
		return isSupportedBy(annot, JsVersion.MOZILLA_ONE_DOT_ZERO);
	}
	
	/**
	 * Return true if given IJstAnnotation contains JsVersion information and
	 * specified JsVersion is greater than or equal to Mozilla JavaScript 
	 * version 1.1
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean mozillaOneDotOne(IJstAnnotation annot) {
		return isSupportedBy(annot, JsVersion.MOZILLA_ONE_DOT_ONE);
	}
	
	/**
	 * Return true if given IJstAnnotation contains JsVersion information and
	 * specified JsVersion is greater than or equal to Mozilla JavaScript 
	 * version 1.2
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean mozillaOneDotTwo(IJstAnnotation annot) {
		return isSupportedBy(annot, JsVersion.MOZILLA_ONE_DOT_TWO);
	}
	
	/**
	 * Return true if given IJstAnnotation contains JsVersion information and
	 * specified JsVersion is greater than or equal to Mozilla JavaScript 
	 * version 1.3
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean mozillaOneDotThree(IJstAnnotation annot) {
		return isSupportedBy(annot, JsVersion.MOZILLA_ONE_DOT_THREE);
	}
	
	/**
	 * Return true if given IJstAnnotation contains JsVersion information and
	 * specified JsVersion is greater than or equal to Mozilla JavaScript 
	 * version 1.4
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean mozillaOneDotFour(IJstAnnotation annot) {
		return isSupportedBy(annot, JsVersion.MOZILLA_ONE_DOT_FOUR);
	}
	
	/**
	 * Return true if given IJstAnnotation contains JsVersion information and
	 * specified JsVersion is greater than or equal to Mozilla JavaScript 
	 * version 1.5
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean mozillaOneDotFive(IJstAnnotation annot) {
		return isSupportedBy(annot, JsVersion.MOZILLA_ONE_DOT_FIVE);
	}
	
	/**
	 * Return true if given IJstAnnotation contains JsVersion information and
	 * specified JsVersion is greater than or equal to Mozilla JavaScript 
	 * version 1.6
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean mozillaOneDotSix(IJstAnnotation annot) {
		return isSupportedBy(annot, JsVersion.MOZILLA_ONE_DOT_SIX);
	}
	
	/**
	 * Return true if given IJstAnnotation contains JsVersion information and
	 * specified JsVersion is greater than or equal to Mozilla JavaScript 
	 * version 1.7
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean mozillaOneDotSeven(IJstAnnotation annot) {
		return isSupportedBy(annot, JsVersion.MOZILLA_ONE_DOT_SEVEN);
	}
	
	/**
	 * Return true if given IJstAnnotation contains JsVersion information and
	 * specified JsVersion is greater than or equal to Mozilla JavaScript 
	 * version 1.8
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean mozillaOneDotEight(IJstAnnotation annot) {
		return isSupportedBy(annot, JsVersion.MOZILLA_ONE_DOT_EIGHT);
	}
	
	/**
	 * Return true if given IJstAnnotation contains JsVersion information and
	 * specified JsVersion is greater than or equal to JScript 1.0
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean jScriptOneDotZero(IJstAnnotation annot) {
		return isSupportedBy(annot, JsVersion.JSCRIPT_ONE_DOT_ZERO);
	}
	
	/**
	 * Return true if given IJstAnnotation contains JsVersion information and
	 * specified JsVersion is greater than or equal to JScript 2.0
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean jScriptTwoDotZero(IJstAnnotation annot) {
		return isSupportedBy(annot, JsVersion.JSCRIPT_TWO_DOT_ZERO);
	}
	
	/**
	 * Return true if given IJstAnnotation contains JsVersion information and
	 * specified JsVersion is greater than or equal to JScript 3.0
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean jScriptThreeDotZero(IJstAnnotation annot) {
		return isSupportedBy(annot, JsVersion.JSCRIPT_THREE_DOT_ZERO);
	}
	
	/**
	 * Return true if given IJstAnnotation contains JsVersion information and
	 * specified JsVersion is greater than or equal to JScript 4.0
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean jScriptFourDotZero(IJstAnnotation annot) {
		return isSupportedBy(annot, JsVersion.JSCRIPT_FOUR_DOT_ZERO);
	}
	
	/**
	 * Return true if given IJstAnnotation contains JsVersion information and
	 * specified JsVersion is greater than or equal to JScript 5.0
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean jScriptFiveDotZero(IJstAnnotation annot) {
		return isSupportedBy(annot, JsVersion.JSCRIPT_FIVE_DOT_ZERO);
	}
	
	/**
	 * Return true if given IJstAnnotation contains JsVersion information and
	 * specified JsVersion is greater than or equal to JScript 5.1
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean jScriptFiveDotOne(IJstAnnotation annot) {
		return isSupportedBy(annot, JsVersion.JSCRIPT_FIVE_DOT_ONE);
	}
	
	/**
	 * Return true if given IJstAnnotation contains JsVersion information and
	 * specified JsVersion is greater than or equal to JScript 5.5
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean jScriptFiveDotFive(IJstAnnotation annot) {
		return isSupportedBy(annot, JsVersion.JSCRIPT_FIVE_DOT_FIVE);
	}
	
	/**
	 * Return true if given IJstAnnotation contains JsVersion information and
	 * specified JsVersion is greater than or equal to JScript 5.6
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean jScriptFiveDotSix(IJstAnnotation annot) {
		return isSupportedBy(annot, JsVersion.JSCRIPT_FIVE_DOT_SIX);
	}
	
	/**
	 * Return true if given IJstAnnotation contains JsVersion information and
	 * specified JsVersion is greater than or equal to JScript 8.0
	 * @param annot IJstAnnotation
	 * @return
	 */
	public static boolean jScriptEightDotZero(IJstAnnotation annot) {
		return isSupportedBy(annot, JsVersion.JSCRIPT_EIGHT_DOT_ZERO);
	}
	
	public static boolean isSupportedBy(IJstAnnotation annot, JsVersion jsVersion) {
		if (!JsSupport.class.getSimpleName().equals(annot.getName().toString())) {
			return false;
		}
		for (IExpr expr : annot.values()) {
			
			if(expr instanceof JstIdentifier){
				return getVersion(jsVersion, expr.toExprText());
			}else if (expr instanceof JstArrayInitializer){
				JstArrayInitializer init = (JstArrayInitializer)expr;
				String jsruntimes = init.toExprText();
				jsruntimes = jsruntimes.substring(jsruntimes.indexOf('[')+1, jsruntimes.indexOf(']'));
				String[] supportedJsRuntimes = jsruntimes.split(",");
				for (String ver : supportedJsRuntimes) {
					return getVersion(jsVersion, ver);
				}
			}
		}
		return false;
	}

	private static boolean getVersion(JsVersion jsVersion, String ver) {
		int dotIndex = ver.indexOf('.');
		if (dotIndex > 0) {
			JsVersion supVer = JsVersion.valueOf(ver.substring(dotIndex+1));
			if (supVer != null && supVer.getName().equals(jsVersion.getName())) {
				try {
					double aVersion = Double.valueOf(supVer.getVersion());
					double jVersion = Double.valueOf(jsVersion.getVersion());
					if (aVersion >= jVersion) {
						return true;
					}
				} catch (NumberFormatException e) {
					// NOPMD - ignore and return false
				}
			}
		}
		return false;
	}
	

	

}

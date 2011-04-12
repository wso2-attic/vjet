/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion;

import java.util.List;

import org.ebayopensource.dsf.jst.IScriptUnit;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.vjo.meta.VjoKeywords;

/**
 * Some util methods on VjoCcCtx
 * 
 *
 */
public class VjoCcCtxUtil {
	
	public static final String KEYWORD_TYPENAME = "@TYPENAME";
	public static final String WRAPPER_PREFIX = "vjo.ctype('" +
			KEYWORD_TYPENAME +
			"')\n" +
			".props({\n" +
			"	//>public void main(String... args)\n" +
			"	main : function(args){\n" +
			"		";
	public static final String WRAPPER_SURFIX = "\n	}\n" +
			"})\n.endType();";
	
	/**
	 * To deal with the common js code, this is the way to check if the code is common js code 
	 * @param ctx {@link VjoCcCtx}
	 * @return
	 */
	public static boolean isAnalyzableCtx(VjoCcCtx ctx) {
		if (!ctx.isInSciptUnitArea()) {
			return true;
		}
		JstIdentifier jidentifier = ctx.getSFirstIdentifer();
		//the file is not begin with "vjo", then consider it as common js code
		if (jidentifier == null || !VjoKeywords.VJO.equals(jidentifier.getName())) {
			return false;
		} else {
			return true;
		}
		
	}

	/**
	 * Wrap content into a valid vjo structure
	 * @param content
	 * @return
	 */
	public static String wrapContent(String content, String typeName) {
		return WRAPPER_PREFIX.replace(KEYWORD_TYPENAME, typeName.replace('\\', '.')) + content + WRAPPER_SURFIX;
	}
	
	/**
	 * Get the offset change for the vjo wrapper 
	 * @return
	 */
	public static int getPrefixOffset(String typeName) { 
		return WRAPPER_PREFIX.length() - KEYWORD_TYPENAME.length() + typeName.length();
	}

	public static String getDotTypeName(String typeName) {
		int index = typeName.indexOf(".");
		if (index != -1) {
			typeName = typeName.substring(0, index);
		}
		typeName = typeName.replace('\\', '.');
		if (typeName.startsWith(".")) {
			typeName = typeName.substring(1);
		}
		return typeName;
	}
	
	/**
	 * If a js file is not valid vjo file, return the exact block which contains the position.
	 * case:
	 * 	var a;
	 *	vjo.ctype('BugJsFiles.Bug5076') //< public
	 *	.<cursor>
	 *	.endType();
  	 *
	 * @param unit
	 * @param position
	 * @return
	 */
	public static JstBlock getExactBlock(IScriptUnit unit, int position) {
		List<JstBlock> blocks = unit.getJstBlockList();
		if (blocks != null && !blocks.isEmpty()) {
			for (JstBlock block : blocks) {
				JstSource source = block.getSource();
				if (isInSource(source, position)) {
					return block;
				}
																// API
			}
		}
		//return default
		return unit.getSyntaxRoot();
//		return null;
	}
	
	public static boolean isInSource(JstSource source, int position) {
		if (source == null) {
			return false;
		}
		return position > source.getStartOffSet() && position <= source.getEndOffSet();
	}
}

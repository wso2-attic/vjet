/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.ui.formatter;

import java.util.Map;

import org.ebayopensource.dsf.jsgen.shared.generate.CodeStyle;
import org.ebayopensource.dsf.jsgen.shared.vjo.GeneratorCtx;
import org.ebayopensource.dsf.jsgen.shared.vjo.VjoGenerator;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jstojava.parser.SyntaxTreeFactory2;
import org.ebayopensource.dsf.jstojava.translator.TranslateCtx;
import org.eclipse.dltk.mod.ui.formatter.AbstractScriptFormatter;
import org.eclipse.dltk.mod.ui.formatter.FormatterException;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;

public class VjetFormatter extends AbstractScriptFormatter {

	public VjetFormatter(Map preferences) {
		super(preferences);
		// TODO Auto-generated constructor stub
	}

	public TextEdit format(String source, int offset, int length,
			int indentationLevel) throws FormatterException {
		
		TranslateCtx ctx = new TranslateCtx();
		char[] charSource = source.toCharArray();
		IJstType type = SyntaxTreeFactory2.createJST(null, charSource, "",
				null, ctx);
		
		// Do not format text if errors present
		if(type.getName() == null){
			return new MultiTextEdit();
		}
		
		GeneratorCtx generatorCtx = new GeneratorCtx(CodeStyle.PRETTY);
//		generatorCtx.setAddCodeGenAnnotation(false);
		VjoGenerator writer = new VjoGenerator(generatorCtx);
		
		writer.writeVjo(type); 
		
		final String output = writer.getGeneratedText();
		if (output != null) {
			if (!source.equals(output)) {
					return new ReplaceEdit(0, source.length(), output);
			} else {
				return new MultiTextEdit(); // NOP
			}
		}
		return null;
	}
}

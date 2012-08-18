package org.eclipse.dltk.mod.internal.javascript.parser;

import org.eclipse.dltk.mod.ast.parser.ISourceParser;
import org.eclipse.dltk.mod.ast.parser.ISourceParserFactory;

/**
 * Returns instances of the JavaScript source parser 
 */
public class JavaScriptSourceParserFactory implements ISourceParserFactory {

	public ISourceParser createSourceParser() {
		return new JavaScriptSourceParser();
	}

}

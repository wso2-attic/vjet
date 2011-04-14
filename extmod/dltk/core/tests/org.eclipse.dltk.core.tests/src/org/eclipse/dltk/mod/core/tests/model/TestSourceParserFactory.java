package org.eclipse.dltk.mod.core.tests.model;

import org.eclipse.dltk.mod.ast.parser.ISourceParser;
import org.eclipse.dltk.mod.ast.parser.ISourceParserFactory;

public class TestSourceParserFactory implements ISourceParserFactory {

	/*
	 * @see org.eclipse.dltk.mod.ast.parser.ISourceParserFactory#createSourceParser()
	 */
	public ISourceParser createSourceParser() {
		return new TestSourceParser();
	}
}

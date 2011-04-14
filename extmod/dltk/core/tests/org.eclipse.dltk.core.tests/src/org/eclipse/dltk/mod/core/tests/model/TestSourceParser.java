package org.eclipse.dltk.mod.core.tests.model;

import org.eclipse.dltk.mod.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.mod.ast.parser.AbstractSourceParser;
import org.eclipse.dltk.mod.compiler.problem.IProblemReporter;

public class TestSourceParser extends AbstractSourceParser {

	public ModuleDeclaration parse(char[] fileName, char[] source,
			IProblemReporter reporter) {
		return new ModuleDeclaration(source.length);
	}

}

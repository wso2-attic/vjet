/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.mod.javascript.internal.ui.text;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.mod.javascript.ui.text.IJavaScriptPartitions;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;

public class JavascriptPartitionScanner extends RuleBasedPartitionScanner {

	/**
	 * Creates the partitioner and sets up the appropriate rules.
	 */
	public JavascriptPartitionScanner() {
		super();

		IToken string = new Token(IJavaScriptPartitions.JS_STRING);
		IToken singleComment = new Token(
				IJavaScriptPartitions.JS_SINGLE_COMMENT);
		IToken multiComment = new Token(IJavaScriptPartitions.JS_MULTI_COMMENT);
		IToken jsString = new Token(IJavaScriptPartitions.JS_STRING);

		IToken jsDoc = new Token(IJavaScriptPartitions.JS_DOC);

		List/* < IPredicateRule > */rules = new ArrayList/* <IPredicateRule> */();
		rules.add(new MultiLineRule("/**", "*/", jsDoc, (char) 0, true));

		rules.add(new MultiLineRule("/*", "*/", multiComment, (char) 0, true)); //$NON-NLS-1$ //$NON-NLS-2$
		//rules.add(new MultiLineRule("<%//", "*>", multiComment, (char) 0, true)); //$NON-NLS-1$ //$NON-NLS-2$
		//	rules.add(new MultiLineRule("<%", "%>", multiComment, (char) 0, true)); //$NON-NLS-1$ //$NON-NLS-2$
		rules.add(new EndOfLineRule("//", singleComment)); //$NON-NLS-1$	
		// Add rule for character constants.
		rules.add(new SingleLineRule("'", "'", string, '\\'));
		rules.add(new MultiLineRule("\"", "\"", string, '\\'));

		// rules.add(new MultiLineRule("\"", "\"", jsDoc, '\\'));

		IPredicateRule[] result = new IPredicateRule[rules.size()];
		rules.toArray(result);
		setPredicateRules(result);

		// IToken javaDoc= new Token(JAVA_DOC);
		// IToken comment= new Token(JAVA_MULTILINE_COMMENT);
		//
		// List rules= new ArrayList();
		// // Add rule for single line comments.
		// rules.add(new EndOfLineRule("//", Token.UNDEFINED));
		//
		// // Add rule for strings and character constants.
		// rules.add(new SingleLineRule("\"", "\"", Token.UNDEFINED, '\\'));
		// rules.add(new SingleLineRule("'", "'", Token.UNDEFINED, '\\'));
		//
		// // Add special case word rule.
		// rules.add(new WordPredicateRule(comment));
		//
		// // Add rules for multi-line comments and javadoc.
		// rules.add(new MultiLineRule("/**", "*/", javaDoc, (char) 0, true));
		// rules.add(new MultiLineRule("/*", "*/", comment, (char) 0, true));
		//
		// IPredicateRule[] result= new IPredicateRule[rules.size()];
		// rules.toArray(result);
		// setPredicateRules(result);

	}

}
/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.advisor.keyword;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.ebayopensource.dsf.jst.reserved.JsCoreKeywords;
import org.ebayopensource.vjo.meta.VjoKeywords;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCCVjoUtilityAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcThisProposalAdvisor;

/**
 * Used for creating vjo keywords.
 */
public class VjoKeywordFactory {

	public static final IVjoKeywordCompletionData KWD_FUNC = new FuncKeyword();

	public static final IVjoKeywordCompletionData KWD_WHILE = new WhileKeyword();
	public static final IVjoKeywordCompletionData KWD_DO = new DoKeyword();
	public static final IVjoKeywordCompletionData KWD_FOR = new ForKeyword();
	public static final IVjoKeywordCompletionData KWD_THIS = new ThisKeyword();
	// TODO window and document is used temperorily, need Global method support
	public static final IVjoKeywordCompletionData KWD_WINDOW = new WindowKeyword();
	public static final IVjoKeywordCompletionData KWD_DOCUMENT = new DocumentKeyword();
	public static final IVjoKeywordCompletionData KWD_RETURN = new ReturnKeyword();
	public static final IVjoKeywordCompletionData KWD_VAR = new VarKeyword();
	public static final IVjoKeywordCompletionData KWD_NEW = new NewKeyword();
	public static final IVjoKeywordCompletionData KWD_DELETE = new DELETEKeyword();
	public static final IVjoKeywordCompletionData KWD_IF = new IfKeyword();
	public static final IVjoKeywordCompletionData KWD_ELSE = new ElseKeyword();
	public static final IVjoKeywordCompletionData KWD_CONTINUE = new ContinueKeyword();
	public static final IVjoKeywordCompletionData KWD_BREACK = new BreakKeyword();
	public static final IVjoKeywordCompletionData KWD_SWITCH = new SwitchKeyword();
	public static final IVjoKeywordCompletionData KWD_CASE = new CaseKeyword();
	public static final IVjoKeywordCompletionData KWD_DEFAULT = new DefaultKeyword();
	public static final IVjoKeywordCompletionData KWD_INSTANCE_OF = new InstanceOfKeyword();
	public static final IVjoKeywordCompletionData KWD_TYPE_OF = new TypeOfKeyword();

	public static final IVjoKeywordCompletionData KWD_BASE = new BaseKeyword();
	public static final IVjoKeywordCompletionData KWD_OUTER = new OuterKeyword();
	public static final IVjoKeywordCompletionData KWD_PARENT = new ParentKeyword();

	public static final IVjoKeywordCompletionData KWD_TYPE = new TypeKeyword();//in comment
	public static final IVjoKeywordCompletionData KWD_NEEDS = new NeedsKeyword();//in comment
	
	public static final IVjoKeywordCompletionData KWD_SYSTEM_OUT = new SysoutKeyword();
	public static final IVjoKeywordCompletionData KWD_SYSTEM_ERR = new SyserrKeyword();
	public static final IVjoKeywordCompletionData KWD_PRINTLN = new PrintlnKeyword();
	public static final IVjoKeywordCompletionData KWD_PRINT = new PrintKeyword();
	public static final IVjoKeywordCompletionData KWD_PRINT_STACK_TRACE = new PrintStackTrace();


	private static final IVjoKeywordCompletionData KWD_WHILE_AS_COMPLEMENTED = new AbstractVjoCompletionData(
			"while", "();") {

	};

	public static final IVjoKeywordCompletionData KWD_TRY = new TryKeyword();

	public static final IVjoKeywordCompletionData KWD_CATCH = new CatchKeyword();

	public static final IVjoKeywordCompletionData KWD_FINALLY = new FinallyKeyword();

	public static final IVjoKeywordCompletionData KWD_VJO_UTILITY = new VjoUtilityKeyword();
	public static final IVjoKeywordCompletionData KWD_NULL = new NullKeyword();
	public static final IVjoKeywordCompletionData KWD_FALSE = new FalseKeyword();
	public static final IVjoKeywordCompletionData KWD_TRUE = new TrueKeyword();
	
	//ACCESS IN COMMENT
	public static final IVjoKeywordCompletionData KWD_PUBLIC = new PublicKeyword();
	public static final IVjoKeywordCompletionData KWD_PROTECTED = new ProtectedKeyword();
	public static final IVjoKeywordCompletionData KWD_PRIVATE = new PrivateKeyword();
	//ACCESS IN COMMENT
	public static final IVjoKeywordCompletionData KWD_FINAL = new FinalKeyword();
	public static final IVjoKeywordCompletionData KWD_STATIC = new StaticKeyword();
	public static final IVjoKeywordCompletionData KWD_ABSTRACT = new AbstractKeyword();
	
	public static final IVjoKeywordCompletionData KWD_SUPPRESSTYPECHECK = new SuppressTypeCheckKeyword();
	//THROWS IN COMMENT
	public static final IVjoKeywordCompletionData KWD_THROWS = new ThrowsKeyword();

	protected static final IVjoKeywordCompletionData[] UTILITY_METHOD_KEYWORDS = new IVjoKeywordCompletionData[] {
			KWD_BASE, KWD_SYSTEM_OUT, KWD_SYSTEM_ERR, KWD_PRINTLN,
			KWD_PRINT, KWD_PRINT_STACK_TRACE };

	private final static IVjoKeywordCompletionData[] METHOD_KEYWORDS = new IVjoKeywordCompletionData[] {
			KWD_CATCH, KWD_DO, KWD_ELSE, KWD_FINALLY, KWD_FOR, KWD_IF, KWD_NEW, KWD_DELETE,
			KWD_RETURN, KWD_TRY, KWD_VAR, KWD_WHILE, KWD_CONTINUE, KWD_SWITCH,
			KWD_BREACK, KWD_CASE, KWD_DEFAULT, KWD_INSTANCE_OF, KWD_TYPE_OF,
			KWD_VJO_UTILITY, KWD_TRUE, KWD_FALSE, KWD_NULL };

	private static class FuncKeyword extends AbstractVjoCompletionData {

		FuncKeyword() {
			super("function", "");
		}

	}

	private static class ThisKeyword extends AbstractVjoCompletionData {
		ThisKeyword() {
			super("this", "");
		}

		@Override
		public String getAdvisor() {
			return VjoCcThisProposalAdvisor.ID;
		}
	}

	private static class WindowKeyword extends AbstractVjoCompletionData {
		WindowKeyword() {
			super("window", "");
		}

		@Override
		public String getAdvisor() {
			return VjoCcThisProposalAdvisor.ID;
		}
	}

	private static class DocumentKeyword extends AbstractVjoCompletionData {
		DocumentKeyword() {
			super("document", "");
		}

		@Override
		public String getAdvisor() {
			return VjoCcThisProposalAdvisor.ID;
		}
	}

	private static class BaseKeyword extends AbstractVjoCompletionData {
		BaseKeyword() {
			super("base", "");
		}

		public boolean canComplete(String text) {
			// int idx = text.lastIndexOf('.');
			// if (idx != -1) {
			// text = text.substring(idx + 1).trim();
			// }
			return text.equals("")
					|| getName().toLowerCase().startsWith(text.toLowerCase());
		}
	}

	private static class OuterKeyword extends AbstractVjoCompletionData {
		OuterKeyword() {
			super("outer", "");
		}

		public boolean canComplete(String text) {
			return text.equals("")
					|| getName().toLowerCase().startsWith(text.toLowerCase());
		}
	}

	private static class ParentKeyword extends AbstractVjoCompletionData {
		ParentKeyword() {
			super("parent", "");
		}

		public boolean canComplete(String text) {
			return text.equals("")
					|| getName().toLowerCase().startsWith(text.toLowerCase());
		}
	}
	private static class TypeKeyword extends AbstractVjoCompletionData {
		TypeKeyword() {
			super("Type::", "");
		}
		
		public boolean canComplete(String text) {
			return text.equals("")
			|| getName().toLowerCase().startsWith(text.toLowerCase());
		}
	}
	private static class NeedsKeyword extends AbstractVjoCompletionData {
		NeedsKeyword() {
			super("needs", "");
		}
		
		public boolean canComplete(String text) {
			return text.equals("")
			|| getName().toLowerCase().startsWith(text.toLowerCase());
		}
	}

	private static class ReturnKeyword extends AbstractVjoCompletionData {
		ReturnKeyword() {
			super("return", " ");
		}
	}

	private static class VarKeyword extends AbstractVjoCompletionData {
		VarKeyword() {
			super("var", " ");
		}
	}

	private static class NewKeyword extends AbstractVjoCompletionData {
		NewKeyword() {
			super("new", " ");
		}
	}
	private static class DELETEKeyword extends AbstractVjoCompletionData {
		DELETEKeyword() {
			super(JsCoreKeywords.DELETE, " ");
		}
	}

	private static class TryKeyword extends AbstractVjoCompletionData {

		public TryKeyword() {
			super("try", "");
		}

	}

	private static class CatchKeyword extends AbstractVjoCompletionData {

		public CatchKeyword() {
			super("catch", "");
		}

	}

	private static class FinallyKeyword extends AbstractVjoCompletionData {

		public FinallyKeyword() {
			super("finally", "");
		}

	}

	private static class DoKeyword extends AbstractVjoCompletionData {

		public DoKeyword() {
			super("do", "");
		}

	}

	private static class WhileKeyword extends AbstractVjoCompletionData {

		public WhileKeyword() {
			super("while", "");
		}

		public List<IVjoKeywordCompletionData> getAllowedEnclosedKeywords() {
			return Arrays.asList(KWD_THIS, KWD_RETURN, KWD_VAR, KWD_NEW, KWD_DELETE,
					KWD_IF, KWD_FOR, KWD_WHILE, KWD_DO, KWD_TRY);
		}

	}

	private static class ForKeyword extends AbstractVjoCompletionData {

		ForKeyword() {
			super("for", "");
		}

	}

	private static class IfKeyword extends AbstractVjoCompletionData {

		IfKeyword() {
			super("if", "");
		}

	}

	private static class ElseKeyword extends AbstractVjoCompletionData {

		public ElseKeyword() {
			super("else", " ");
		}

	}

	private static class ContinueKeyword extends AbstractVjoCompletionData {

		public ContinueKeyword() {
			super("continue", " ");

		}

	}

	private static class BreakKeyword extends AbstractVjoCompletionData {

		public BreakKeyword() {
			super("break", " ");

		}

	}

	private static class SwitchKeyword extends AbstractVjoCompletionData {

		public SwitchKeyword() {
			super("switch", " ");

		}

	}

	private static class CaseKeyword extends AbstractVjoCompletionData {

		public CaseKeyword() {
			super("case", " ");

		}

	}

	private static class DefaultKeyword extends AbstractVjoCompletionData {

		public DefaultKeyword() {
			super("default", " ");

		}

	}

	private static class TypeOfKeyword extends AbstractVjoCompletionData {

		public TypeOfKeyword() {
			super("typeof()", " ");

		}

	}

	private static class InstanceOfKeyword extends AbstractVjoCompletionData {

		public InstanceOfKeyword() {
			super("instanceof", " ");

		}
	}

	private static class VjoUtilityKeyword extends AbstractVjoCompletionData {
		public VjoUtilityKeyword() {
			super(VjoKeywords.VJO, "");

		}

		@Override
		public String getAdvisor() {
			return VjoCCVjoUtilityAdvisor.ID;
		}
	}

	private static class NullKeyword extends AbstractVjoCompletionData {

		public NullKeyword() {
			super("null", " ");

		}
	}
	private static class TrueKeyword extends AbstractVjoCompletionData {
		
		public TrueKeyword() {
			super("true", " ");
			
		}
	}
	private static class ThrowsKeyword extends AbstractVjoCompletionData {
		
		public ThrowsKeyword() {
			super(JsCoreKeywords.EXT_THROWS, " ");
			
		}
	}
	private static class StaticKeyword extends AbstractVjoCompletionData {
		
		public StaticKeyword() {
			super(JsCoreKeywords.EXT_STATIC, " ");
			
		}
	}
	private static class AbstractKeyword extends AbstractVjoCompletionData {
		
		public AbstractKeyword() {
			super(JsCoreKeywords.EXT_ABSTRACT, " ");
			
		}
	}
	private static class SuppressTypeCheckKeyword extends AbstractVjoCompletionData {
		
		public SuppressTypeCheckKeyword() {
			super("@SUPPRESSTYPECHECK", " ");
			
		}
	}
	private static class FinalKeyword extends AbstractVjoCompletionData {
		
		public FinalKeyword() {
			super(JsCoreKeywords.EXT_FINAL, " ");
			
		}
	}
	private static class PrivateKeyword extends AbstractVjoCompletionData {
		
		public PrivateKeyword() {
			super(JsCoreKeywords.EXT_PRIVATE, " ");
			
		}
	}
	private static class ProtectedKeyword extends AbstractVjoCompletionData {
		
		public ProtectedKeyword() {
			super(JsCoreKeywords.EXT_PROTECTED, " ");
			
		}
	}
	private static class PublicKeyword extends AbstractVjoCompletionData {
		
		public PublicKeyword() {
			super(JsCoreKeywords.EXT_PUBLIC, " ");
			
		}
	}
	private static class FalseKeyword extends AbstractVjoCompletionData {
		
		public FalseKeyword() {
			super("false", " ");
			
		}
	}
	private static class SysoutKeyword extends AbstractVjoCompletionData {

		public SysoutKeyword() {
			super("sysout", "");
		}

		public boolean canComplete(String text) {
			int idx = text.lastIndexOf('.');
			if (idx != -1) {
				text = text.substring(idx + 1).trim();
			}
			return text == ""
					|| getName().toLowerCase().startsWith(text.toLowerCase());
		}

	}

	private static class SyserrKeyword extends AbstractVjoCompletionData {

		public SyserrKeyword() {
			super("syserr", "");
		}

		public boolean canComplete(String text) {
			int idx = text.lastIndexOf('.');
			if (idx != -1) {
				text = text.substring(idx + 1).trim();
			}
			return text == ""
					|| getName().toLowerCase().startsWith(text.toLowerCase());
		}

	}

	private static class PrintlnKeyword extends AbstractVjoCompletionData {

		public PrintlnKeyword() {
			super("println", "(\"\");");
		}

		public boolean canComplete(String text) {
			int idx = text.lastIndexOf('.');
			if (idx != -1) {
				text = text.substring(idx + 1).trim();
			}
			return text == ""
					|| getName().toLowerCase().startsWith(text.toLowerCase());
		}

	}

	private static class PrintKeyword extends AbstractVjoCompletionData {

		public PrintKeyword() {
			super("print", "(\"\");");
		}

		public boolean canComplete(String text) {
			int idx = text.lastIndexOf('.');
			if (idx != -1) {
				text = text.substring(idx + 1).trim();
			}
			return text == ""
					|| getName().toLowerCase().startsWith(text.toLowerCase());
		}

	}

	private static class PrintStackTrace extends AbstractVjoCompletionData {

		public PrintStackTrace() {
			super("printStackTrace", "(\"\");");
		}

		public boolean canComplete(String text) {
			int idx = text.lastIndexOf('.');
			if (idx != -1) {
				text = text.substring(idx + 1).trim();
			}
			return text == ""
					|| getName().toLowerCase().startsWith(text.toLowerCase());
		}

	}

	/**
	 * Gets the collection of installed keywords.
	 * 
	 * @return the collection of installed keywords.
	 */
	public static Collection<IVjoKeywordCompletionData> getInstalledKeywords() {

		return AbstractVjoCompletionData.installedKeywords.values();

	}

	/**
	 * Gets keyword by its name.
	 * 
	 * @param keyword
	 *            the keyword name
	 * @return Vjo keyword
	 */
	public static IVjoKeywordCompletionData getKeywordByName(String keyword) {
		return AbstractVjoCompletionData.installedKeywords.get(keyword);
	}

	public static List<IVjoKeywordCompletionData> getMethodKeyworkds() {
		return Arrays.asList(METHOD_KEYWORDS);
	}

}